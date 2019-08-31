package au.edu.unsw.soacourse.founditapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.founditapp.model.AppIdList;
import au.edu.unsw.soacourse.founditapp.model.Application;
import au.edu.unsw.soacourse.founditapp.model.ApplicationList;
import au.edu.unsw.soacourse.founditapp.model.HiringTeam;
import au.edu.unsw.soacourse.founditapp.model.Job;
import au.edu.unsw.soacourse.founditapp.model.Review;
import au.edu.unsw.soacourse.founditapp.model.ReviewList;
import au.edu.unsw.soacourse.founditapp.model.Reviewer;
import au.edu.unsw.soacourse.founditapp.model.ReviewerList;

@Controller
public class ReviewerController {
	private String REST_URI = "http://localhost:8080/FoundItRest";
	private WebClient client = WebClient.create(REST_URI);

	/* Manager use */
	@RequestMapping("/assignapps")
	public ModelAndView assignApps(@RequestParam(value = "jobid") String jobid,
			@RequestParam(value = "teamid") String teamid,
			HttpServletRequest request) throws Exception {
		client.reset();
		client.path("/Application/job/" + jobid).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList apps = client.get(ApplicationList.class);
		client.reset();
		client.path("/HiringTeam/" + teamid).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		HiringTeam ht = client.get(HiringTeam.class);
		for (int i = apps.getCount() - 1; i >= 0; i--)
			if (!apps.getList().get(i).getStatus().equals("checked"))
				apps.deleteApplication(apps.getList().get(i));
		Map<String, List<String>> result = reviewAllocate(apps, ht);
		for (int i = 0; i < ht.getMembers().getReviewers().size(); i++) {
			Reviewer rv = ht.getMembers().getReviewers().get(i);
			rv.setAppIds(new AppIdList(result.get(rv.getId())));
			client.reset();
			client.path("/HiringTeam/reviewer").accept(MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-manager");
			client.put(rv);
		}
		client.reset();
		client.path("/Job/" + jobid).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		Job job = client.get(Job.class);
		job.setStatus("reviewing");
		client.reset();
		client.path("/Job").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(job);
		ModelAndView mv = new ModelAndView("teamdetail");
		mv.addObject("hiringteam", ht.getMembers().getReviewers());
		mv.addObject("jobId", jobid);
		return mv;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/addreviewer")
	public String addReviewer(
			@RequestParam(value = "reviewerid") String reviewerid,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "skills") String skills,
			HttpServletRequest request) throws Exception {

		Reviewer r = new Reviewer(reviewerid, password, skills, null);
		ReviewerList rl = new ReviewerList();
		if (request.getSession().getAttribute("reviewerlist") != null)
			rl.setReviewers((List<Reviewer>) request.getSession().getAttribute(
					"reviewerlist"));
		rl.addReviewer(r);
		request.getSession().setAttribute("reviewerlist", rl.getReviewers());
		return "addreviewer";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/createhiringteam")
	public ModelAndView createHiringTeam(
			@RequestParam(value = "jobId") String jobId,
			HttpServletRequest request) throws Exception {
		ReviewerList rl = new ReviewerList();
		rl.setReviewers((List<Reviewer>) request.getSession().getAttribute(
				"reviewerlist"));
		HiringTeam ht = new HiringTeam("", jobId, rl);
		request.getSession().removeAttribute("reviewerlist");
		client.reset();
		client.path("/HiringTeam").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.post(ht);
		client.reset();
		client.path("/HiringTeam/job/" + jobId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ht = client.get(HiringTeam.class);
		ModelAndView mv = new ModelAndView("hiringteamlist");
		mv.addObject("jobId", jobId);
		mv.addObject("teamId", ht.getId());
		mv.addObject("hteam", ht.getMembers().getReviewers());
		return mv;
	}

	/* Reviewer use */
	@RequestMapping("/reviewing")
	public ModelAndView showAllApps(HttpServletRequest request) {
		String id = (String) request.getSession().getAttribute("username");
		client.reset();
		client.path("/HiringTeam/reviewer/" + id).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		Reviewer r = client.get(Reviewer.class);
		List<String> apps = r.getAppIds().getAppIds();
		ModelAndView mv = new ModelAndView("reviewing");
		ApplicationList applist = new ApplicationList();
		for (int i = 0; i < apps.size(); i++) {
			client.reset();
			client.path("/Application/id/" + apps.get(i)).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-reviewer");
			applist.addApplication(client.get(Application.class));
		}
		mv.addObject("result", applist.getList());
		return mv;
	}

	@RequestMapping("/createreview")
	public ModelAndView buildReview(
			@RequestParam(value = "appId") String appId,
			@RequestParam(value = "comments") String comments,
			@RequestParam(value = "decision") String decision,
			HttpServletRequest request) {
		String uId = (String) request.getSession().getAttribute("username");
		Review r = new Review("", appId, uId, comments, decision);
		client.reset();
		client.path("/Review").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		client.post(r);
		ModelAndView mv = new ModelAndView("reviewdetail");
		client.reset();
		client.path("/HiringTeam/reviewer/" + uId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		Reviewer reviewer = client.get(Reviewer.class);
		AppIdList appIds = reviewer.getAppIds();
		appIds.removeAppId(appId);
		reviewer.setAppIds(appIds);
		client.reset();
		client.path("/HiringTeam/reviewer").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		client.put(reviewer);
		// handling if this app id passed
		client.reset();
		client.path("/Review/app/" + appId).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		ReviewList rl = client.get(ReviewList.class);
		if (rl.getList().size() > 1)
			handlingAppStatus(appId, rl);
		mv.addObject("review", r);
		return mv;
	}

	private void handlingAppStatus(String appId, ReviewList rl) {
		client.reset();
		client.path("/Application/id/" + appId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		Application app = client.get(Application.class);
		if (rl.getList().get(0).getDecision().equals("Y")
				&& rl.getList().get(1).getDecision().equals("Y"))
			app.setStatus("reviewpass");
		else
			app.setStatus("reviewfail");
		client.reset();
		client.path("/Application").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-reviewer");
		client.put(app);
	}

	private Map<String, List<String>> reviewAllocate(
			ApplicationList applications, HiringTeam reviewers) {
		Map<String, List<String>> result = new HashMap<String, List<String>>();
		int i = 0;
		while (i < applications.getCount()) {
			for (int k = 0; k < reviewers.getMembers().getReviewers().size()
					&& i < applications.getCount(); k++, i++) {
				List<String> l = new ArrayList<String>();
				l.add(applications.getList().get(i).getId());
				result.put(
						reviewers.getMembers().getReviewers().get(k).getId(), l);
			}
		}
		i = 0;
		while (i < applications.getCount()) {
			for (int k = reviewers.getMembers().getReviewers().size() - 1; k >= 0
					&& i < applications.getCount(); k--, i++) {
				if (result.get(reviewers.getMembers().getReviewers().get(k)
						.getId()) != null)
					result.get(
							reviewers.getMembers().getReviewers().get(k)
									.getId()).add(
							applications.getList().get(i).getId());
				else {
					List<String> l = new ArrayList<String>();
					l.add(applications.getList().get(i).getId());
					result.put(reviewers.getMembers().getReviewers().get(k)
							.getId(), l);
				}
			}
		}
		return result;
	}
}
