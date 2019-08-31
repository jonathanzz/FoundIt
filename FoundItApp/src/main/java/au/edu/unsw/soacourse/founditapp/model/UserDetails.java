package au.edu.unsw.soacourse.founditapp.model;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "firstName", "lastName", "address", "dlNumber" })
@XmlRootElement(name = "detail")
public class UserDetails {
	private String firstName;
	private String lastName;
	private String address;
	private String dlNumber;
	private Map<String, String> map = new LinkedHashMap<String, String>();

	public UserDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDetails(String firstName, String lastName, String address,
			String dlNumber) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.dlNumber = dlNumber;
		mapBuild();
	}

	private void mapBuild() {
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("address", address);
		map.put("dlNumber", dlNumber);
	}

	public String getFirstName() {
		return firstName;
	}

	@XmlElement(name = "firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
		mapBuild();
	}

	public String getLastName() {
		return lastName;
	}

	@XmlElement(name = "lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
		mapBuild();
	}

	public String getAddress() {
		return address;
	}

	@XmlElement(name = "address")
	public void setAddress(String address) {
		this.address = address;
		mapBuild();
	}

	public String getDlNumber() {
		return dlNumber;
	}

	@XmlElement(name = "dlNumber")
	public void setDlNumber(String dlNumber) {
		this.dlNumber = dlNumber;
		mapBuild();
	}

	public Map<String, String> getMap() {
		return map;
	}
}
