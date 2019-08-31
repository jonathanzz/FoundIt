package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HiringTeam")
@XmlAccessorType(XmlAccessType.FIELD)
public class HiringTeam {

	@XmlElement(name = "id")
	private String id;
	@XmlElement(name = "jobId")
	private String jobId;
	@XmlElement(name = "Members")
	private ReviewerList members = new ReviewerList();
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public HiringTeam() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HiringTeam(String id,String jobId, ReviewerList members) {
		super();
		this.id = id;
		this.jobId = jobId;
		this.members = members;
		mapBuild();
	}

	private void mapBuild() {
		map.put("id", id);
		map.put("jobId", jobId);
		map.put("Members", "$");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
		mapBuild();
	}
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public ReviewerList getMembers() {
		return members;
	}

	public void setMembers(ReviewerList members) {
		this.members = members;
		mapBuild();
	}

	public Reviewer getMemberById(String id) {
		for (Reviewer r : members.getReviewers())
			if (r.getId().equals(id))
				return r;
		return null;
	}

	public void addMember(Reviewer r) {
		members.addReviewer(r);
	}

	public Map<String, String> getMap() {
		return map;
	}
}
