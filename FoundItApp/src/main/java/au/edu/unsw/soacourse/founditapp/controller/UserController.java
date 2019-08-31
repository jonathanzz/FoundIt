package au.edu.unsw.soacourse.founditapp.controller;

import java.io.File;
import java.io.FileInputStream;
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

import au.edu.unsw.soacourse.founditapp.model.Company;
import au.edu.unsw.soacourse.founditapp.model.Reviewer;
import au.edu.unsw.soacourse.founditapp.model.User;
import au.edu.unsw.soacourse.founditapp.model.UserDetails;

@Controller
public class UserController {
	private String filePath = this.getClass().getResource("users.xml")
			.getPath();
	private String REST_URI = "http://localhost:8080/FoundItRest";

	@RequestMapping("/login")
	public ModelAndView login(
			@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "userType") String userType,
			HttpServletRequest request) throws Exception {
		ModelAndView mv;
		if (!userType.equals("3")) {
			if (userXmlManage(username, password, userType, 1) == 0) {
				mv = new ModelAndView("login");
				mv.addObject("status", "Incorrect email or password!");
				return mv;
			} else {
				mv = new ModelAndView("index");
				request.getSession().setAttribute("userType", userType);
				request.getSession().setAttribute("username", username);
				if (userType.equals("2")) {
					WebClient client = WebClient.create(REST_URI);
					client.path("/Company/email/" + username).accept(
							MediaType.APPLICATION_XML);
					client.header("SecurityKey", "i-am-foundit").header(
							"ShortKey", "app-manager");
					Company c = client.get(Company.class);
					if (c != null)
						request.getSession().setAttribute("company", c);
				}
				return mv;
			}
		} else {
			WebClient client = WebClient.create(REST_URI);
			client.path("/HiringTeam/reviewer/" + username).accept(
					MediaType.APPLICATION_XML);
			client.header("SecurityKey", "i-am-foundit").header("ShortKey",
					"app-reviewer");
			Reviewer r = client.get(Reviewer.class);
			if (r == null || !r.getPassword().equals(password)) {
				mv = new ModelAndView("login");
				mv.addObject("status",
						"Wrong username or password, please check.");
				return mv;
			} else {
				mv = new ModelAndView("index");
				request.getSession().setAttribute("userType", userType);
				request.getSession().setAttribute("username", username);
				return mv;
			}
		}
	}

	@RequestMapping("/register")
	public ModelAndView register(@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "confirm") String confirm,
			@RequestParam(value = "userType") String userType,
			HttpServletRequest request) throws Exception {
		ModelAndView mv;
		if (!password.equals(confirm)) {
			mv = new ModelAndView("register");
			mv.addObject("status", "Passwords must match!");
			mv.addObject("email", email);
			return mv;
		}
		int u = userXmlManage(email, password, userType, 2);
		if (u == 0) {
			mv = new ModelAndView("register");
			mv.addObject("status",
					"The email address has been registered, please change another one.");
			mv.addObject("email", email);
			return mv;
		} else {
			mv = new ModelAndView("login");
			return mv;
		}
	}

	@RequestMapping("/pwdchange")
	public ModelAndView pwdChange(
			@RequestParam(value = "oldpass") String oldpass,
			@RequestParam(value = "newpass") String newpass,
			@RequestParam(value = "confirm") String confirm,
			HttpServletRequest request) throws Exception {
		String username = (String) request.getSession()
				.getAttribute("username");
		String userType = (String) request.getSession()
				.getAttribute("userType");
		ModelAndView mv = null;
		if (userXmlManage(username, oldpass, userType, 1) == 0) {
			mv = new ModelAndView("changepass");
			mv.addObject("status", "Incorrect password!");
			return mv;
		}
		if (!newpass.equals(confirm)) {
			mv = new ModelAndView("changepass");
			mv.addObject("status", "Passwords must match!");
			return mv;
		}
		userXmlManage(username, newpass, userType, 3);
		mv = new ModelAndView("index");
		return mv;
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) throws Exception {
		request.getSession().removeAttribute("username");
		request.getSession().removeAttribute("userType");
		return "index";
	}

	@RequestMapping("/uProfile")
	public ModelAndView uProfile(HttpServletRequest request) {
		String email = (String) request.getSession().getAttribute("username");
		WebClient client = WebClient.create(REST_URI);
		client.path("/User/" + email).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		User u = client.get(User.class);
		ModelAndView mv = null;
		if (u == null) {
			mv = new ModelAndView("createUProfile");
		} else {
			mv = new ModelAndView("uprofile");
			mv.addObject("user", u);
		}
		return mv;
	}

	@RequestMapping("/uProfileCreate")
	// Candidate profile edit
	public String uProfileCreate(
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "dlNumber") String dlNumber,
			@RequestParam(value = "skills") String skills,
			@RequestParam(value = "experience") String experience,
			@RequestParam(value = "education") String education,
			HttpServletRequest request) {
		UserDetails detail = new UserDetails(firstName, lastName, address,
				dlNumber);
		String email = (String) request.getSession().getAttribute("username");
		User u = new User(email, detail, skills, experience, education);
		WebClient client = WebClient.create(REST_URI);
		client.path("/User").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		client.post(u);
		request.setAttribute("user", u);
		return "uprofile";
	}

	@RequestMapping("/uProfileEdit")
	// Candidate profile edit
	public ModelAndView uProfileEdit(
			@RequestParam(value = "firstName") String firstName,
			@RequestParam(value = "lastName") String lastName,
			@RequestParam(value = "address") String address,
			@RequestParam(value = "dlNumber") String dlNumber,
			@RequestParam(value = "skills") String skills,
			@RequestParam(value = "experience") String experience,
			@RequestParam(value = "education") String education,
			HttpServletRequest request) {
		UserDetails detail = new UserDetails(firstName, lastName, address,
				dlNumber);
		String email = (String) request.getSession().getAttribute("username");
		User user = new User(email, detail, skills, experience, education);
		WebClient client = WebClient.create(REST_URI);
		client.path("/User/").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-candidate");
		client.put(user);
		ModelAndView mv = new ModelAndView("uprofile");
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping("/cProfile")
	public ModelAndView cProfile(HttpServletRequest request) {
		String email = (String) request.getSession().getAttribute("username");
		WebClient client = WebClient.create(REST_URI);
		client.path("/Company/email/" + email)
				.accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		Company company = client.get(Company.class);
		ModelAndView mv = null;
		if (company == null) {
			mv = new ModelAndView("createCProfile");
		} else {
			mv = new ModelAndView("cprofile");
			mv.addObject("company", company);
		}
		return mv;
	}

	@RequestMapping("/cProfileCreate")
	// Company profile edit
	public String cProfileCreate(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "industry") String industry,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "website") String website,
			@RequestParam(value = "location") String location,
			HttpServletRequest request) {
		String email = (String) request.getSession().getAttribute("username");
		Company company = new Company(id, name, email, industry, description,
				website, location);
		WebClient client = WebClient.create(REST_URI);
		client.path("/Company/").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.post(company);
		request.setAttribute("company", company);
		request.getSession().setAttribute("company", company);
		return "cprofile";
	}

	@RequestMapping("/cProfileEdit")
	// Company profile edit
	public ModelAndView cProfileEdit(@RequestParam(value = "id") String id,
			@RequestParam(value = "name") String name,
			@RequestParam(value = "industry") String industry,
			@RequestParam(value = "description") String description,
			@RequestParam(value = "website") String website,
			@RequestParam(value = "location") String location,
			HttpServletRequest request) {
		String email = (String) request.getSession().getAttribute("username");
		Company company = new Company(id, name, email, industry, description,
				website, location);
		WebClient client = WebClient.create(REST_URI);
		client.path("/Company/").accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		client.put(company);
		ModelAndView mv = new ModelAndView("cprofile");
		mv.addObject("company", company);
		request.getSession().setAttribute("company", company);
		return mv;
	}

	@RequestMapping("/candidatedetail")
	public ModelAndView candidateDetail(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "jobId", defaultValue = "0") String jobId,
			@RequestParam(value = "status", defaultValue = "Undecided") String status,
			HttpServletRequest request) {
		WebClient client = WebClient.create(REST_URI);
		client.path("/User/" + email).accept(MediaType.APPLICATION_XML);
		client.header("SecurityKey", "i-am-foundit").header("ShortKey",
				"app-manager");
		User u = client.get(User.class);
		ModelAndView mv = null;
		if (u == null) {
			mv = new ModelAndView("createUProfile");
		} else {
			mv = new ModelAndView("uprofile");
			mv.addObject("user", u);
			mv.addObject("email", email);
			mv.addObject("jobId", jobId);
			mv.addObject("status", status);
		}
		return mv;
	}

	private int userXmlManage(String e, String p, String userType, int option)
			throws Exception {
		Document doc;
		XPath xpath;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		doc = db.parse(new FileInputStream(new File(filePath)));
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
		NodeList nodeList = (NodeList) xpath.evaluate(
				"Records/child::User[username='" + e + "']/password", doc,
				XPathConstants.NODESET);
		if (option == 1) {
			if (nodeList.getLength() == 0)
				return 0;
			else {
				for (int i = 0; i < nodeList.getLength(); i++)
					if (p.equals(nodeList.item(i).getTextContent().trim())) {
						NodeList nodeList1 = (NodeList) xpath.evaluate(
								"Records/child::User[username='" + e
										+ "']/userType", doc,
								XPathConstants.NODESET);
						if (!userType.equals(nodeList1.item(0).getTextContent()
								.trim()))
							return 0;
						else
							return Integer.parseInt(userType);
					}
				return 0;
			}
		} else {
			if (option == 2 && nodeList.getLength() != 0)
				return 0;
			if (option == 3) {
				Node parent = nodeList.item(0).getParentNode().getParentNode();
				parent.removeChild(nodeList.item(0).getParentNode());
			}
			Element root = doc.getDocumentElement();
			Element user = doc.createElement("User");
			root.appendChild(user);
			Element email = doc.createElement("username");
			Element password = doc.createElement("password");
			Element ut = doc.createElement("userType");
			email.setTextContent(e);
			password.setTextContent(p);
			ut.setTextContent(userType);
			user.appendChild(email);
			user.appendChild(password);
			user.appendChild(ut);
			TransformerFactory tFactory = TransformerFactory.newInstance();
			try {
				Transformer transformer = tFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new java.io.File(
						filePath));
				transformer.transform(source, result);
			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			}
			return Integer.parseInt(userType);
		}
	}
}
