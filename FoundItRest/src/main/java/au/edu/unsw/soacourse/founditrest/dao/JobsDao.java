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

import au.edu.unsw.soacourse.founditrest.model.Job;

public enum JobsDao {
	instance;
	private String xmlPath = "E:\\eclipse\\Jobs.xml";
	private static Document doc;
	private static XPath xpath;

	private JobsDao() {
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

	public List<Job> queryJobs(String query, String value) {
		List<Job> results = new ArrayList<Job>();
		NodeList nodeList = null;
		try {
			if (query.equals("id") || query.equals("companyid")
					|| query.equals("status")) {
				nodeList = (NodeList) xpath.evaluate("Jobs/child::Job[" + query
						+ "='" + value + "']", doc, XPathConstants.NODESET);
			} else
				nodeList = (NodeList) xpath.evaluate("Jobs/child::Job", doc,
						XPathConstants.NODESET);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		try {
			for (int i = 0; i < nodeList.getLength(); i++) {
				JAXBContext jaxbContexts;
				jaxbContexts = JAXBContext.newInstance(Job.class);
				Unmarshaller jaxbUnmarshaller = jaxbContexts
						.createUnmarshaller();
				Job job = (Job) jaxbUnmarshaller.unmarshal(nodeList.item(i));
				results.add(job);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + e.getCause());
		}
		if (query.equals("keyword")) {
			for (int i = results.size() - 1; i >= 0; i--) {
				if (!results.get(i).getKeyWords().contains(value)){
					results.remove(i);
				}
			}
		}
		return results;
	}

	public boolean insertJob(Job job) {
		List<Job> jobList = queryJobs("", "");
		int lastId = Integer.parseInt(jobList.get(jobList.size() - 1).getId());
		job.setId(String.valueOf(lastId + 1));
		Element root = doc.getDocumentElement();
		Element u = doc.createElement("Job");
		root.appendChild(u);
		for (Map.Entry<String, String> a : job.getMap().entrySet()) {
			String key = a.getKey();
			String value = a.getValue();
			Element k = doc.createElement(key);
			k.appendChild(doc.createTextNode(value));
			u.appendChild(k);
		}
		rewriteToXml();
		return true;
	}

	public boolean deleteJob(String jobId) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate("Jobs/child::Job[id='" + jobId
					+ "']", doc, XPathConstants.NODESET);
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

	public boolean updateJob(Job job) {
		NodeList nodeList = null;
		try {
			nodeList = (NodeList) xpath.evaluate(
					"Jobs/child::Job[id='" + job.getId() + "']", doc,
					XPathConstants.NODESET);
			if (nodeList.getLength() == 0)
				return false;
			Element n = (Element) nodeList.item(0);
			for (Map.Entry<String, String> a : job.getMap().entrySet())
				n.getElementsByTagName(a.getKey()).item(0)
						.setTextContent(a.getValue());

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
