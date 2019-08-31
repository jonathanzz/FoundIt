package au.edu.unsw.soacourse.founditrest.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "email", "detail", "skills", "experience", "education" })
@XmlRootElement(name = "User")
public class User {
	private String email;
	private UserDetails detail;
	private String skills;
	private String experience;
	private String education;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(String email, UserDetails detail, String skills,
			String experience, String education) {
		super();
		this.email = email;
		this.detail = detail;
		this.skills = skills;
		this.experience = experience;
		this.education = education;
		mapBuild();
	}

	private void mapBuild() {
		map.put("email", email);
		map.put("detail", "$");
		map.put("skills", skills);
		map.put("experience", experience);
		map.put("education", education);
	}

	public String getEmail() {
		return email;
	}

	@XmlElement(name = "email")
	public void setEmail(String email) {
		this.email = email;
		mapBuild();
	}

	public UserDetails getDetail() {
		return detail;
	}

	@XmlElement(name = "detail")
	public void setDetail(UserDetails detail) {
		this.detail = detail;
	}

	public String getSkills() {
		return skills;
	}

	@XmlElement(name = "skills")
	public void setSkills(String skills) {
		this.skills = skills;
		mapBuild();
	}

	public String getExperience() {
		return experience;
	}

	@XmlElement(name = "experience")
	public void setExperience(String experience) {
		this.experience = experience;
		mapBuild();
	}

	public String getEducation() {
		return education;
	}

	@XmlElement(name = "education")
	public void setEducation(String education) {
		this.education = education;
		mapBuild();
	}

	public Map<String, String> getMap() {
		return map;
	}

}
