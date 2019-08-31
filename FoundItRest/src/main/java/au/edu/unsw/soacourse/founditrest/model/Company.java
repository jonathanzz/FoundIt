package au.edu.unsw.soacourse.founditrest.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "name", "managerEmail", "industry", "description",
		"website", "location" })
@XmlRootElement(name = "Company")
public class Company {

	private String id;
	private String name;
	private String managerEmail;
	private String industry;
	private String description;
	private String website;
	private String location;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Company() {
		super();
	}

	public Company(String id, String name, String managerEmail,
			String industry, String description, String website, String location) {
		super();
		this.id = id;
		this.name = name;
		this.managerEmail = managerEmail;
		this.industry = industry;
		this.description = description;
		this.website = website;
		this.location = location;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("name", name);
		map.put("managerEmail", managerEmail);
		map.put("industry", industry);
		map.put("description", description);
		map.put("website", website);
		map.put("location", location);
	}

	public String getId() {
		return id;
	}

	@XmlElement(name = "id")
	public void setId(String id) {
		this.id = id;
		mapBuild();
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
		mapBuild();
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	@XmlElement(name = "managerEmail")
	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getIndustry() {
		return industry;
	}

	@XmlElement(name = "industry")
	public void setIndustry(String industry) {
		this.industry = industry;
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

	public String getWebsite() {
		return website;
	}

	@XmlElement(name = "website")
	public void setWebsite(String website) {
		this.website = website;
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

	public Map<String, String> getMap() {
		return map;
	}

}
