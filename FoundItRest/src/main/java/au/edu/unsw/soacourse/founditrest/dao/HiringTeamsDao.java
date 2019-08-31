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

import au.edu.unsw.soacourse.founditrest.model.HiringTeam;
import au.edu.unsw.soacourse.founditrest.model.Reviewer;

public enum HiringTeamsDao {
	instance;
	private String xmlPath = this.getClass().getResource("HiringTeams.xml")
			.getPath();
	private static Document doc;
	private static XPath xpath;

	private HiringTeamsDao() {
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

	public List<HiringTeam> queryHiringTeam(String queryItem, String queryString) {
		List<HiringTeam> results = new ArrayList<HiringTeam>();
		NodeList nodeList = null;
		try {
			if (queryItem.equals("") && queryString.equals(""))
				nodeList = (NodeList) xpath.evaluate(
						"HiringTeams/child::HiringTeam", doc,
						XPathConstants.NODESET);
			else
				nodeList = (NodeList) xpath.evaluate(
						"HiringTeams/child::HiringTeam[" + queryItem + "='"
								+ queryString + "']", doc,
						XPathConstants.NODESET);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {
				JAXBContext jaxbContexts;
				jaxbContexts = JAXBContext.newInstance(HiringTeam.class);
				Unmarshaller jaxbUnmarshaller = jaxbContexts
						.createUnmarshaller();
				HiringTeam hiringTeam = (HiringTeam) jaxbUnmarshaller
						.unmarshal(nodeList.item(i));
				results.add(hiringTeam);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		return results;
	}

	public Reviewer queryReviewerById(String id) {
		NodeList nodeList = null;
		Reviewer reviewer = null;
		try {
			nodeList = (NodeList) xpath.evaluate(
					"HiringTeams/HiringTeam/Members/Reviewer[id='" + id + "']",
					doc, XPathConstants.NODESET);
			JAXBContext jaxbContexts;
			jaxbContexts = JAXBContext.newInstance(Reviewer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContexts.createUnmarshaller();
			reviewer = (Reviewer) jaxbUnmarshaller.unmarshal(nodeList.item(0));

		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		return reviewer;
	}

	public String insertHiringTeam(HiringTeam hiringTeam) {
		List<HiringTeam> ht = queryHiringTeam("", "");
		int lastId = 0;
		if (ht.size() != 0)
			lastId = Integer.parseInt(ht.get(ht.size() - 1).getId());
		hiringTeam.setId(String.valueOf(lastId + 1));
		Element root = doc.getDocumentElement();
		Element u = doc.createElement("HiringTeam");
		root.appendChild(u);
		for (Map.Entry<String, String> a : hiringTeam.getMap().entrySet()) {
			String key = a.getKey();
			String value = a.getValue();
			if (!"$".equals(value)) {
				Element k = doc.createElement(key);
				k.appendChild(doc.createTextNode(value));
				u.appendChild(k);
			} else {
				Element k = doc.createElement(key);
				u.appendChild(k);
				for (Reviewer reviewer : hiringTeam.getMembers().getReviewers()) {
					Element r = doc.createElement("Reviewer");
					k.appendChild(r);
					for (Map.Entry<String, String> b : reviewer.getMap()
							.entrySet()) {
						String key1 = b.getKey();
						String value1 = b.getValue();
						Element d = doc.createElement(key1);
						if (!"$".equals(value1))
							d.appendChild(doc.createTextNode(value1));
						r.appendChild(d);
					}
				}
			}
		}
		rewriteToXml();
		return hiringTeam.getId();
	}

	public boolean deleteHiringTeam(String hiringTeamId) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(
					"HiringTeams/child::HiringTeam[id='" + hiringTeamId + "']",
					doc, XPathConstants.NODESET);
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

	public boolean updateReviewer(Reviewer reviewer) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(
					"HiringTeams/HiringTeam/Members/Reviewer[id='"
							+ reviewer.getId() + "']", doc,
					XPathConstants.NODESET);
			if (nodeList.getLength() == 0)
				return false;
			Element r = (Element) nodeList.item(0);
			for (Map.Entry<String, String> b : reviewer.getMap().entrySet()) {
				String key = b.getKey();
				String value = b.getValue();
				if (!"$".equals(value))
					r.getElementsByTagName(key).item(0).setTextContent(value);
				else {
					r.removeChild(r.getElementsByTagName("appIds").item(0));
					Element j = doc.createElement("appIds");
					r.appendChild(j);
					for (String s : reviewer.getAppIds().getAppIds()) {
						Element l = doc.createElement("appId");
						j.appendChild(l);
						l.setTextContent(s);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		rewriteToXml();
		return true;
	}

	public boolean updateHiringTeam(HiringTeam hiringTeam) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(
					"HiringTeams/child::HiringTeam[id='" + hiringTeam.getId()
							+ "']", doc, XPathConstants.NODESET);
			if (nodeList.getLength() == 0)
				return false;
			Element n = (Element) nodeList.item(0);
			for (Map.Entry<String, String> a : hiringTeam.getMap().entrySet()) {
				String key = a.getKey();
				String value = a.getValue();
				if (!"$".equals(value))
					n.getElementsByTagName(key).item(0).setTextContent(value);
				else {
					Element e = (Element) n.getElementsByTagName(key).item(0);
					int k = 0;
					for (Reviewer reviewer : hiringTeam.getMembers()
							.getReviewers()) {
						Element r = (Element) e
								.getElementsByTagName("Reviewer").item(k);
						for (Map.Entry<String, String> b : reviewer.getMap()
								.entrySet()) {
							String key1 = b.getKey();
							String value1 = b.getValue();
							if (!"$".equals(value1))
								r.getElementsByTagName(key1).item(0)
										.setTextContent(value1);
							else {
								r.removeChild(r.getElementsByTagName("appIds").item(0));
								Element j = doc.createElement("appIds");
								for (String s : reviewer.getAppIds().getAppIds()) {
									Element l = doc.createElement("appId");
									l.setTextContent(s);
									j.appendChild(l);
								}
								r.appendChild(j);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
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
