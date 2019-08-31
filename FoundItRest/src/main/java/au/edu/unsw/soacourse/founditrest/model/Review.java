package au.edu.unsw.soacourse.founditrest.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.hateoas.Link;

@XmlType(propOrder = { "id", "appId", "uId", "comments", "decision", "links" })
@XmlRootElement(name = "Review")
public class Review {
	private String id;
	private String appId;
	private String uId;
	private String comments;
	private String decision;
	private List<Link> links = new ArrayList<Link>();
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Review() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Review(String id, String appId, String uId, String comments,
			String decision) {
		super();
		this.id = id;
		this.appId = appId;
		this.uId = uId;
		this.comments = comments;
		this.decision = decision;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("appId", appId);
		map.put("uId", uId);
		map.put("comments", comments);
		map.put("decision", decision);
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
		mapBuild();
	}

	public String getAppId() {
		return appId;
	}

	@XmlElement(name = "appId")
	public void setAppId(String appId) {
		this.appId = appId;
		mapBuild();
	}

	public String getuId() {
		return uId;
	}

	@XmlElement(name = "uId")
	public void setuId(String uId) {
		this.uId = uId;
		mapBuild();
	}

	public String getComments() {
		return comments;
	}

	@XmlElement(name = "comments")
	public void setComments(String comments) {
		this.comments = comments;
		mapBuild();
	}

	public String getDecision() {
		return decision;
	}

	@XmlElement(name = "decision")
	public void setDecision(String decision) {
		this.decision = decision;
		mapBuild();
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}

	public void addLink(Link link) {
		links.add(link);
	}

	public Map<String, String> getMap() {
		return map;
	}
}
