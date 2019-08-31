package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "appId", "uId", "comments", "decision" })
@XmlRootElement(name = "Review")
public class Review {
	private String id;
	private String appId;
	private String uId;
	private String comments;
	private String decision;
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

	public Map<String, String> getMap() {
		return map;
	}
}
