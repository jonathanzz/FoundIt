package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "password", "skills", "appIds" })
@XmlRootElement(name = "Reviewer")
public class Reviewer {
	private String id;
	private String password;
	private String skills;
	private AppIdList appIds;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Reviewer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Reviewer(String id, String password, String skills, AppIdList appIds) {
		super();
		this.id = id;
		this.password = password;
		this.skills = skills;
		this.appIds = appIds;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("password", password);
		map.put("skills", skills);
		map.put("appIds", "$");
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
		mapBuild();
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
		mapBuild();
	}

	public String getSkills() {
		return skills;
	}

	@XmlElement(name = "skills")
	public void setSkills(String skills) {
		this.skills = skills;
		mapBuild();
	}

	public AppIdList getAppIds() {
		return appIds;
	}

	@XmlElement(name = "appIds")
	public void setAppIds(AppIdList appIds) {
		this.appIds = appIds;
		mapBuild();
	}

	public Map<String, String> getMap() {
		return map;
	}
}
