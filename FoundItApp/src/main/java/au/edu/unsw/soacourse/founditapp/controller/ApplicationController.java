package au.edu.unsw.soacourse.founditapp.controller;

import java.io.StringWriter;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import au.edu.unsw.soacourse.founditapp.model.Application;
import au.edu.unsw.soacourse.founditapp.model.ApplicationList;
import au.edu.unsw.soacourse.founditapp.model.Job;
import au.edu.unsw.soacourse.founditapp.model.User;
import au.edu.unsw.soacourse.founditapp.model.UserList;

@Controller
public class ApplicationController {

	private String REST_URI = "http://localhost:8080/FoundItRest";
	private WebClient client = WebClient.create(REST_URI);

	@RequestMapping("/showapplication")
	public ModelAndView showApplications(
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			HttpServletRequest request) throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		client.reset();
		client.path("/Application/candidate/" + email).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		ApplicationList al = client.get(ApplicationList.class);
		ModelAndView mv = new ModelAndView("showapplication");
		if (al != null)
			mv.addObject("appList", al.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("/createapplication")
	public ModelAndView createApplication(
			@RequestParam(value = "jobid") String jobid,
			@RequestParam(value = "coverletter", required = false, defaultValue = "") String letter,
			HttpServletRequest request) throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		Application app = new Application("", email, jobid, letter, "open");
		client.reset();
		client.path("/Application").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		Response response = client.post(app);
		ModelAndView mv = new ModelAndView("applysuccess");
		mv.addObject("status", response.getEntity());
		mv.addObject("jobid", jobid);
		mv.addObject("coverletter", letter);
		return mv;
	}

	@RequestMapping("/updateapplication")
	public ModelAndView updateApplication(
			@RequestParam(value = "appid") String appid,
			@RequestParam(value = "jobid") String jobid,
			@RequestParam(value = "coverletter") String letter,
			HttpServletRequest request) throws Exception {
		String email = (String) request.getSession().getAttribute("username");
		Application app = new Application(appid, email, jobid, letter, "open");
		client.reset();
		client.path("/Application").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		client.put(app);
		client.reset();
		client.path("/Application/id/" + appid).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		Application result = client.get(Application.class);
		ModelAndView mv = new ModelAndView("appdetail");
		mv.addObject("appdetail", result);
		return mv;
	}

	@RequestMapping("/removeapplication")
	public ModelAndView removeApplication(
			@RequestParam(value = "appid") String appid,
			@RequestParam(value = "pageId", required = false, defaultValue = "0") String pageId,
			HttpServletRequest request) throws Exception {
		client.reset();
		client.path("/Application/" + appid).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		client.delete();
		String email = (String) request.getSession().getAttribute("username");
		client.reset();
		client.path("/Application/candidate/" + email).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		ApplicationList al = client.get(ApplicationList.class);
		ModelAndView mv = new ModelAndView("showapplication");
		if (al != null)
			mv.addObject("appList", al.getList());
		mv.addObject("pageId", pageId);
		return mv;
	}

	@RequestMapping("/appdetail")
	public ModelAndView appDetail(@RequestParam(value = "appid") String appid)
			throws Exception {
		client.reset();
		client.path("/Application/id/" + appid).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		Application result = client.get(Application.class);
		ModelAndView mv = new ModelAndView("appdetail");
		mv.addObject("appdetail", result);
		return mv;
	}

	@RequestMapping("/runautocheck")
	public ModelAndView runAutoCheck(@RequestParam(value = "jobid") String jobid)
			throws Exception {
		client.reset();
		client.path("/Job/" + jobid).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		Job j = client.get(Job.class);
		j.setStatus("checked");
		client.reset();
		client.path("/Job").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(j);
		client.reset();
		client.path("/Application/job/" + jobid).accept(
				MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		ApplicationList apps = client.get(ApplicationList.class);
		UserList ul = new UserList();
		for (int i = 0; i < apps.getCount(); i++) {
			Application a = apps.getList().get(i);
			client.reset();
			client.path("/User/" + a.getCandidateEmail()).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-manager");
			User u = client.get(User.class);
			if (autoCheck(u)) {
				ul.addUser(u);
				a.setStatus("checked");
			} else
				a.setStatus("checkedfail");
			client.reset();
			client.path("/Application").accept(MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-manager");
			client.put(a);
		}
		ModelAndView mv = new ModelAndView("jobdetail");
		mv.addObject("result", j);
		return mv;
	}

	private boolean autoCheck(User u) {
		try {
			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory
					.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory
					.createConnection();
			// Send SOAP Message to SOAP Server
			String url = "http://localhost:6060/ode/processes/AutoCheckService";
			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage soapMessage = messageFactory.createMessage();
			SOAPPart soapPart = soapMessage.getSOAPPart();

			String serverURI = "http://soacourse.unsw.edu.au/autocheck";
			SOAPEnvelope envelope = soapPart.getEnvelope();
			envelope.addNamespaceDeclaration("q0", serverURI);
			SOAPBody soapBody = envelope.getBody();
			SOAPElement soapBodyElem = soapBody.addChildElement(
					"AutoCheckServiceRequest", "q0");
			SOAPElement soapBodyElem1 = soapBodyElem.addChildElement(
					"fullName", "q0");
			soapBodyElem1.addTextNode(u.getDetail().getFirstName() + " "
					+ u.getDetail().getLastName());
			SOAPElement soapBodyElem2 = soapBodyElem.addChildElement(
					"dlNumber", "q0");
			soapBodyElem2.addTextNode(u.getDetail().getDlNumber());

			SOAPElement soapBodyElem3 = soapBodyElem.addChildElement("address",
					"q0");
			soapBodyElem3.addTextNode(u.getDetail().getAddress());
			MimeHeaders headers = soapMessage.getMimeHeaders();
			headers.addHeader("SOAPAction", serverURI);
			soapMessage.saveChanges();
			SOAPMessage soapResponse = soapConnection.call(soapMessage, url);
			// Process the SOAP Response
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			StringWriter sw = new StringWriter();
			StreamResult result = new StreamResult(sw);
			transformer.transform(sourceContent, result);
			System.out.println(sw.toString());
			if (sw.toString().contains("Y"))
				return true;
			soapConnection.close();
		} catch (Exception e) {
			System.err
					.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}
		return false;
	}

}
