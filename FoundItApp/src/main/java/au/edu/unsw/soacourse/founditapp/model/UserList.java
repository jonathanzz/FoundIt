package au.edu.unsw.soacourse.founditapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Users")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserList {
	@XmlElement(name = "User")
	private List<User> list = new ArrayList<User>();

	public UserList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserList(List<User> list) {
		super();
		this.list = list;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public User getUser(String email) {
		for (User u : list)
			if (u.getEmail().equals(email))
				return u;
		return null;
	}

	public void addUser(User u) {
		list.add(u);
	}

	public boolean deleteUser(User u) {
		if (!list.contains(u))
			return false;
		list.remove(u);
		return true;
	}
}
