package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "candidateEmail", "applyJobId", "coverLetter",
		"status" })
@XmlRootElement(name = "Application")
public class Application {
	private String id;
	private String candidateEmail;
	private String applyJobId;
	private String coverLetter;
	private String status;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public Application() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Application(String id, String candidateEmail, String applyJobId,
			String coverLetter, String status) {
		super();
		this.id = id;
		this.candidateEmail = candidateEmail;
		this.applyJobId = applyJobId;
		this.coverLetter = coverLetter;
		this.status = status;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("candidateEmail", candidateEmail);
		map.put("applyJobId", applyJobId);
		map.put("coverLetter", coverLetter);
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

	public String getCandidateEmail() {
		return candidateEmail;
	}

	@XmlElement(name = "candidateEmail")
	public void setCandidateEmail(String candidateEmail) {
		this.candidateEmail = candidateEmail;
		mapBuild();
	}

	public String getApplyJobId() {
		return applyJobId;
	}

	@XmlElement(name = "applyJobId")
	public void setApplyJobId(String applyJobId) {
		this.applyJobId = applyJobId;
		mapBuild();
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	@XmlElement(name = "coverLetter")
	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
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

	public Map<String, String> getMap() {
		return map;
	}

}
