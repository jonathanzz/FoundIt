package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "title", "companyid", "positionType", "salary",
		"description", "skills", "link", "location", "status" })
@XmlRootElement(name = "Job")
public class Job {
	private String id;
	private String title;
	private String companyid;
	private String positionType;
	private String salary;
	private String description;
	private String skills;
	private String link;
	private String location;
	private String status;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Job() {
		super();
	}

	public Job(String id, String title, String companyid, String positionType,
			String salary, String description, String skills, String link,
			String location, String status) {
		super();
		this.id = id;
		this.title = title;
		this.companyid = companyid;
		this.positionType = positionType;
		this.salary = salary;
		this.description = description;
		this.skills = skills;
		this.link = link;
		this.location = location;
		this.status = status;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("title", title);
		map.put("companyid", companyid);
		map.put("positionType", positionType);
		map.put("salary", salary);
		map.put("description", description);
		map.put("skills", skills);
		map.put("link", link);
		map.put("location", location);
		map.put("status", status);
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
		mapBuild();
	}

	public String getTitle() {
		return title;
	}

	@XmlElement(name = "title")
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyid() {
		return companyid;
	}

	@XmlElement(name = "companyid")
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
		mapBuild();
	}

	public String getPositionType() {
		return positionType;
	}

	@XmlElement(name = "positionType")
	public void setPositionType(String positionType) {
		this.positionType = positionType;
		mapBuild();
	}

	public String getSalary() {
		return salary;
	}

	@XmlElement(name = "salary")
	public void setSalary(String salary) {
		this.salary = salary;
		mapBuild();
	}

	public String getDescription() {
		return description;
	}

	@XmlElement(name = "description")
	public void setDescription(String description) {
		this.description = description;
		mapBuild();
	}

	public String getSkills() {
		return skills;
	}

	@XmlElement(name = "skills")
	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getLink() {
		return link;
	}

	@XmlElement(name = "link")
	public void setLink(String link) {
		this.link = link;
		mapBuild();
	}

	public String getLocation() {
		return location;
	}

	@XmlElement(name = "location")
	public void setLocation(String location) {
		this.location = location;
		mapBuild();
	}

	public String getStatus() {
		return status;
	}

	@XmlElement(name = "status")
	public void setStatus(String status) {
		this.status = status;
		mapBuild();
	}

	public String getKeyWords() {
		return title + " " + positionType + ", " + description + ", "
				+ location + ", " + " " + skills + link + ", " + salary;
	}

	public Map<String, String> getMap() {
		return map;
	}

}
