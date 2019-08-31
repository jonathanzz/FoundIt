package au.edu.unsw.soacourse.founditrest.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "appIds")
@XmlAccessorType(XmlAccessType.FIELD)
public class AppIdList {
	@XmlElement(name = "appId")
	private List<String> appIds = new ArrayList<String>();

	public AppIdList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppIdList(List<String> appIds) {
		super();
		this.appIds = appIds;
	}

	public List<String> getAppIds() {
		return appIds;
	}

	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
	}

	public void insertAppId(String appId) {
		appIds.add(appId);
	}

	public void removeAppId(String appId) {
		appIds.remove(appId);
	}
}
