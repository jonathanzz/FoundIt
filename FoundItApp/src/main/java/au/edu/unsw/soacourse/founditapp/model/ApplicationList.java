package au.edu.unsw.soacourse.founditapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Applications")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationList {
	@XmlElement(name="Application")
	private List<Application> list = new ArrayList<Application>();

	public ApplicationList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApplicationList(List<Application> list) {
		super();
		this.list = list;
	}

	public List<Application> getList() {
		return list;
	}

	public void setList(List<Application> list) {
		this.list = list;
	}

	public Application getApplication(String id) {
		for (Application a : list)
			if (a.getId().equals(id))
				return a;
		return null;
	}

	public void addApplication(Application Application) {
		list.add(Application);
	}

	public int getCount() {
		return list.size();
	}

	public boolean deleteApplication(Application Application) {
		if (!list.contains(Application))
			return false;
		list.remove(Application);
		return true;
	}
}
