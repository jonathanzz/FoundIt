package au.edu.unsw.soacourse.founditapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.founditapp.model.ApplicationList;
import au.edu.unsw.soacourse.founditapp.model.Company;
import au.edu.unsw.soacourse.founditapp.model.Job;
import au.edu.unsw.soacourse.founditapp.model.JobList;
import au.edu.unsw.soacourse.founditapp.util.EmailUtil;

@Controller
public class JobController {

	private String REST_URI = "http://localhost:8080/FoundItRest";
	private WebClient client = WebClient.create(REST_URI);
	private String xmlPath = this.getClass().getResource("savedJob.xml")
			.getPath();

	/* For Manager only */
	@RequestMapping("showjoblist")
	public ModelAndView showJobList(
			@RequestParam(value = "pageId", defaultValue = "0") String pageId,
			HttpServletRequest request) throws Exception {
		Company company = (Company) request.getSession()
				.getAttribute("company");
		client.reset();
		client.path("/Job/query").query("query", "companyid")
				.query("value", company.getId())
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		JobList jl = client.get(JobList.class);
		ModelAndView mv = new ModelAndView("joblist");
		if (jl != null && jl.getList().size() != 0)
			mv.addObject("result", jl.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("/addjob")
	public ModelAndView addJob(@RequestParam(value = "title") String title,
			@RequestParam(value = "positionType") String positionType,
			@RequestParam(value = "salary") String salary,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "skills") String skills,
			@RequestParam(value = "link") String link,
			@RequestParam(value = "location") String location,
			HttpServletRequest request) throws Exception {
		Company company = (Company) request.getSession()
				.getAttribute("company");
		Job job = new Job("", title, company.getId(), positionType, salary,
				description, skills, link, location, "open");
		client.reset();
		client.path("/Job").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.post(job);
		client.reset();
		client.path("/Job/query").query("query", "companyid")
				.query("value", company.getId())
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		JobList jl = client.get(JobList.class);
		ModelAndView mv = new ModelAndView("joblist");
		mv.addObject("result", jl.getList());
		return mv;
	}

	@RequestMapping("editjob")
	public ModelAndView editJob(@RequestParam(value = "id") String id,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "positionType") String positionType,
			@RequestParam(value = "salary") String salary,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "skills") String skills,
			@RequestParam(value = "link") String link,
			@RequestParam(value = "location") String location,
			HttpServletRequest request) throws Exception {
		Company company = (Company) request.getSession()
				.getAttribute("company");
		Job job = new Job(id, title, company.getId(), positionType, salary,
				description, skills, link, location, "open");
		client.reset();
		client.path("/Job").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(job);
		ModelAndView mv = new ModelAndView("jobdetail");
		mv.addObject("result", job);
		return mv;
	}

	@RequestMapping("deletejob")
	public ModelAndView deleteJob(@RequestParam(value = "id") String id,
			HttpServletRequest request) throws Exception {
		Company company = (Company) request.getSession()
				.getAttribute("company");
		client.reset();
		client.path("/Job/" + id).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.delete();
		client.reset();
		client.path("/Job/query").query("query", "companyid")
				.query("value", company.getId())
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		JobList jl = client.get(JobList.class);
		ModelAndView mv = new ModelAndView("joblist");
		if (jl != null)
			mv.addObject("result", jl.getList());
		return mv;
	}

	/* For Candidate only */
	@RequestMapping("createjobalert")
	public ModelAndView createJobAlert(
			@RequestParam(value = "keyword") String keyword,
			@RequestParam(value = "sortby") String sortby,
			HttpServletRequest request) throws Exception {
		String path = "/Jobalert";
		client.reset();
		client.path(path).query("keyword", keyword).query("sortby", sortby)
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		String result = client.get(String.class);
		String email = (String) request.getSession().getAttribute("username");
		EmailUtil.sendAlert(result, email);
		ModelAndView mv = new ModelAndView("alertsuccess");
		return mv;
	}

	@RequestMapping("showsavedjob")
	public ModelAndView showSavedJob(
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			HttpServletRequest request) throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		List<String> result = jobXmlManage(email, "", 1);
		JobList jobList = new JobList();
		for (int i = 0; i < result.size(); i++) {
			client.reset();
			client.path("/Job/" + result.get(i)).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-candidate");
			jobList.addJob(client.get(Job.class));

		}
		ModelAndView mv = new ModelAndView("savedjob");
		if (!jobList.getList().isEmpty() && jobList.getList().size() > 0)
			mv.addObject("jobList", jobList.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("addsavedjob")
	public ModelAndView addSavedJob(
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			@RequestParam(value = "jobid") String id, HttpServletRequest request)
			throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		List<String> result = jobXmlManage(email, id, 2);
		JobList jobList = new JobList();
		for (int i = 0; i < result.size(); i++) {
			client.reset();
			client.path("/Job/" + result.get(i)).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-candidate");
			jobList.addJob(client.get(Job.class));

		}
		ModelAndView mv = new ModelAndView("savedjob");
		if (!jobList.getList().isEmpty() && jobList.getList().size() > 0)
			mv.addObject("jobList", jobList.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("deletesavedjob")
	public ModelAndView deleteSavedJob(
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			@RequestParam(value = "jobid") String id, HttpServletRequest request)
			throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		List<String> result = jobXmlManage(email, id, 3);
		JobList jobList = new JobList();
		for (int i = 0; i < result.size(); i++) {
			client.reset();
			client.path("/Job/" + result.get(i)).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-candidate");
			jobList.addJob(client.get(Job.class));

		}
		ModelAndView mv = new ModelAndView("savedjob");
		if (!jobList.getList().isEmpty() && jobList.getList().size() > 0)
			mv.addObject("jobList", jobList.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	/* For All */
	@RequestMapping("searchjob")
	public ModelAndView searchJob(
			@RequestParam(value = "key") String key,
			@RequestParam(value = "value") String value,
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			HttpServletRequest request) throws Exception {
		String path = "/Job/query";
		client.reset();
		client.path(path).query("query", key).query("value", value)
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		JobList result = client.get(JobList.class);
		ModelAndView mv = new ModelAndView("searchresult");
		if (result != null)
			mv.addObject("result", result.getList());
		mv.addObject("key", key);
		mv.addObject("value", value);
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("jobdetail")
	public ModelAndView jobDetail(
			@RequestParam(value = "id") String id,
			@RequestParam(value = "coverletter", required = false, defaultValue = "") String letter,
			HttpServletRequest request) throws Exception {
		client.reset();
		client.path("/Job/" + id).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		Job result = client.get(Job.class);
		ModelAndView mv = new ModelAndView("jobdetail");
		mv.addObject("result", result);
		mv.addObject("id", id);
		mv.addObject("coverletter", letter);
		if ("2".equals((String) request.getSession().getAttribute("userType"))) {
			client.reset();
			client.path("/Application/job/" + id).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-manager");
			ApplicationList al = client.get(ApplicationList.class);
			if (al != null)
				mv.addObject("appcount", al.getCount());
			else
				mv.addObject("appcount", 0);
		}
		return mv;
	}

	private List<String> jobXmlManage(String email, String job, int option)
			throws Exception {
		Document doc;
		XPath xpath;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(new FileInputStream(new File(xmlPath)));
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		List<String> results = new ArrayList<String>();
		NodeList nodeList = (NodeList) xpath.evaluate(
				"Records/child::User[email='" + email + "']/job", doc,
				XPathConstants.NODESET);
		if (option == 1) {
			if (nodeList.getLength() != 0)
				for (int i = 0; i < nodeList.getLength(); i++)
					results.add(nodeList.item(i).getTextContent().trim());
		} else if (option == 2) {
			NodeList nodeList1 = (NodeList) xpath.evaluate(
					"Records/child::User[email='" + email + "']", doc,
					XPathConstants.NODESET);
			Element root = doc.getDocumentElement();
			if (nodeList1.getLength() == 0) {
				Element u = doc.createElement("User");
				root.appendChild(u);
				Element e = doc.createElement("email");
				e.setTextContent(email);
				Element j = doc.createElement("job");
				j.setTextContent(job);
				u.appendChild(e);
				u.appendChild(j);
			} else {
				Node n = nodeList1.item(0);
				Element j = doc.createElement("job");
				j.setTextContent(job);
				n.appendChild(j);
				for (int i = 0; i < nodeList.getLength(); i++)
					results.add(nodeList.item(i).getTextContent().trim());
			}
			rewriteToXml(doc);
			results.add(job);
		} else {
			if (nodeList.getLength() != 0) {
				for (int i = 0; i < nodeList.getLength(); i++) {
					if (nodeList.item(i).getTextContent().trim().equals(job)) {
						Node parent = nodeList.item(i).getParentNode();
						parent.removeChild(nodeList.item(i));
					} else
						results.add(nodeList.item(i).getTextContent().trim());
				}
			}
			rewriteToXml(doc);
		}
		return results;
	}

	private void rewriteToXml(Document doc) throws Exception {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new java.io.File(xmlPath));
		transformer.transform(source, result);
	}
}
