package au.edu.unsw.soacourse.founditapp.util;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "email", "password", "userType" })
@XmlRootElement(name = "User")
public class UserBean {
	private String email;
	private String password;
	private String userType;

	public UserBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserBean(String email, String password, String userType) {
		super();
		this.email = email;
		this.password = password;
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	@XmlElement(name = "email")
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	@XmlElement(name = "password")
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	@XmlElement(name = "userType")
	public void setUserType(String userType) {
		this.userType = userType;
	}

}
