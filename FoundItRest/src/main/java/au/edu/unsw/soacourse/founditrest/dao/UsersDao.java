package au.edu.unsw.soacourse.founditrest.dao;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import au.edu.unsw.soacourse.founditrest.model.User;

public enum UsersDao {
	instance;
	private String xmlPath = this.getClass().getResource("Users.xml").getPath();
	private static Document doc;
	private static XPath xpath;

	private UsersDao() {
		init(xmlPath);
	}

	private static void init(String xmlFilePath) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		DocumentBuilder db;

		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(new FileInputStream(new File(xmlFilePath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		XPathFactory factory = XPathFactory.newInstance();
		xpath = factory.newXPath();
	}

	public User queryUser(String email) {
		User result = null;
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate("Users/child::User[email='"
					+ email + "']", doc, XPathConstants.NODESET);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {
				JAXBContext jaxbContexts;
				jaxbContexts = JAXBContext.newInstance(User.class);
				Unmarshaller jaxbUnmarshaller = jaxbContexts
						.createUnmarshaller();

				User user = (User) jaxbUnmarshaller.unmarshal(nodeList.item(i));
				result = user;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		return result;
	}

	public List<User> getAllUsers() {
		List<User> results = new ArrayList<User>();
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate("Users/child::User", doc,
					XPathConstants.NODESET);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {

				JAXBContext jaxbContexts;
				jaxbContexts = JAXBContext.newInstance(User.class);
				Unmarshaller jaxbUnmarshaller = jaxbContexts
						.createUnmarshaller();

				User user = (User) jaxbUnmarshaller.unmarshal(nodeList.item(i));
				results.add(user);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		return results;
	}

	public boolean insertUser(User user) {
		if (queryUser(user.getEmail()) != null)
			return false;
		Element root = doc.getDocumentElement();
		Element u = doc.createElement("User");
		root.appendChild(u);
		for (Map.Entry<String, String> a : user.getMap().entrySet()) {
			String key = a.getKey();
			String value = a.getValue();
			if (!"$".equals(value)) {
				Element k = doc.createElement(key);
				k.appendChild(doc.createTextNode(value));
				u.appendChild(k);
			} else {
				Element k = doc.createElement(key);
				u.appendChild(k);
				for (Map.Entry<String, String> b : user.getDetail().getMap()
						.entrySet()) {
					String key1 = b.getKey();
					String value1 = b.getValue();
					Element d = doc.createElement(key1);
					d.appendChild(doc.createTextNode(value1));
					k.appendChild(d);
				}
			}
		}
		rewriteToXml();
		return true;
	}

	public boolean deleteUser(String email) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate("Users/child::User[email='"
					+ email + "']", doc, XPathConstants.NODESET);
			if (nodeList.getLength() == 0)
				return false;
			Node parent = nodeList.item(0).getParentNode();
			parent.removeChild(nodeList.item(0));
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		rewriteToXml();
		return true;
	}

	public boolean updateUser(User user) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate("Users/child::User[email='"
					+ user.getEmail() + "']", doc, XPathConstants.NODESET);
			if (nodeList.getLength() == 0)
				return false;
			Element n = (Element) nodeList.item(0);
			for (Map.Entry<String, String> a : user.getMap().entrySet()) {
				String key = a.getKey();
				String value = a.getValue();
				if (!"$".equals(value)) {
					n.getElementsByTagName(key).item(0).setTextContent(value);
				} else {
					Element e = (Element) n.getElementsByTagName(key).item(0);
					for (Map.Entry<String, String> b : user.getDetail()
							.getMap().entrySet()) {
						String key1 = b.getKey();
						String value1 = b.getValue();
						e.getElementsByTagName(key1).item(0)
								.setTextContent(value1);
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		rewriteToXml();
		return true;
	}

	private void rewriteToXml() {
		TransformerFactory tFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = tFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new java.io.File(xmlPath));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
