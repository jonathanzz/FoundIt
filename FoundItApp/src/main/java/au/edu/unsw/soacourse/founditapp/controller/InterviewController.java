package au.edu.unsw.soacourse.founditapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import au.edu.unsw.soacourse.founditapp.model.Application;
import au.edu.unsw.soacourse.founditapp.model.ApplicationList;
import au.edu.unsw.soacourse.founditapp.model.Job;

@Controller
public class InterviewController {
	private String REST_URI = "http://localhost:8080/FoundItRest";
	private WebClient client = WebClient.create(REST_URI);

	/* Manager use only */
	@RequestMapping("/startinterview")
	public ModelAndView startInterview(
			@RequestParam(value = "jobId") String jobId) throws Exception {
		ModelAndView mv;
		client.reset();
		client.path("/Application/job/" + jobId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList al = client.get(ApplicationList.class);
		for (Application a : al.getList()) {
			if (!a.getStatus().equals("reviewpass")
					&& !a.getStatus().equals("reviewfail")) {
				mv = new ModelAndView("jobdetail");
				client.reset();
				client.path("/Job/" + jobId).accept(MediaType.APPLICATION_XML);
				client.header("SecurityKey", "i-am-foundit").header("ShortKey",
						"app-manager");
				Job j = client.get(Job.class);
				mv.addObject("result", j);
				mv.addObject("status", "Reviewing is not finished.");
				return mv;
			} else if (a.getStatus().equals("reviewpass")) {
				a.setStatus("pending");
				client.reset();
				client.path("/Application").accept(MediaType.APPLICATION_XML);
				client.header("SecurityKey", "i-am-foundit").header("ShortKey",
						"app-manager");
				client.put(a);
			}
		}
		client.reset();
		client.path("/Job/" + jobId).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		Job job = client.get(Job.class);
		job.setStatus("interviewing");
		client.reset();
		client.path("/Job").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(job);
		mv = new ModelAndView("shortlist");
		mv.addObject("apps", al.getList());
		mv.addObject("jobId", jobId);
		return mv;
	}

	@RequestMapping("/showshortlist")
	public ModelAndView showShortList(
			@RequestParam(value = "jobId") String jobId) throws Exception {
		ModelAndView mv = new ModelAndView("shortlist");
		client.reset();
		client.path("/Application/job/" + jobId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList al = client.get(ApplicationList.class);
		mv.addObject("apps", al.getList());
		mv.addObject("jobId", jobId);
		return mv;
	}

	/* Candidate use only */
	@RequestMapping("/accpetinverview")
	public ModelAndView accpetInverview(
			@RequestParam(value = "appid") String appid,
			@RequestParam(value = "accept") String accept,
			HttpServletRequest request) throws Exception {
		client.reset();
		client.path("/Application/id/" + appid).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		Application app = client.get(Application.class);
		if (accept.equals("Y"))
			app.setStatus("acceptinterview");
		else
			app.setStatus("rejectinterview");
		client.reset();
		client.path("/Application").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(app);
		ModelAndView mv = new ModelAndView("appdetail");
		mv.addObject("appdetail", app);
		return mv;
	}

	@RequestMapping("/interviewresult")
	public ModelAndView interviewResult(
			@RequestParam(value = "candidate") String candidate,
			@RequestParam(value = "result") String result,
			@RequestParam(value = "jobId") String jobId) throws Exception {
		client.reset();
		client.path("/Application/candidate/" + candidate).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList apps = client.get(ApplicationList.class);
		String decision = result.equals("Y") ? "inteviewpass" : "interviewfail";
		for (Application a : apps.getList()) {
			if (a.getApplyJobId().equals(jobId)) {
				a.setStatus(decision);
				client.reset();
				client.path("/Application").accept(MediaType.APPLICATION_XML);
				client.header("SecurityKey", "i-am-foundit").header("ShortKey",
						"app-manager");
				client.put(a);
				break;
			}
		}
		ModelAndView mv = new ModelAndView("shortlist");
		client.reset();
		client.path("/Application/job/" + jobId).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList al = client.get(ApplicationList.class);
		mv.addObject("apps", al.getList());
		return mv;
	}
}
