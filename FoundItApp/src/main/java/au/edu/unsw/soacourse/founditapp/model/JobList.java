package au.edu.unsw.soacourse.founditapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Jobs")
@XmlAccessorType(XmlAccessType.FIELD)
public class JobList {
	@XmlElement(name = "Job")
	private List<Job> list = new ArrayList<Job>();

	public JobList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JobList(List<Job> list) {
		super();
		this.list = list;
	}

	public List<Job> getList() {
		return list;
	}

	public void setList(List<Job> list) {
		this.list = list;
	}

	public Job getJob(String id) {
		for (Job j : list)
			if (j.getId().equals(id))
				return j;
		return null;
	}

	public void addJob(Job job) {
		list.add(job);
	}

	public int getCount() {
		return list.size();
	}

	public boolean deleteJob(Job job) {
		if (!list.contains(job))
			return false;
		list.remove(job);
		return true;
	}
}
