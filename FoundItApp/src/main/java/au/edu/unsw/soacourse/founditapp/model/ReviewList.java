package au.edu.unsw.soacourse.founditapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Reviews")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewList {
	@XmlElement(name = "Review")
	private List<Review> list = new ArrayList<Review>();

	public ReviewList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewList(List<Review> list) {
		super();
		this.list = list;
	}

	public List<Review> getList() {
		return list;
	}

	public void setList(List<Review> list) {
		this.list = list;
	}

	public Review getReview(String id) {
		for (Review j : list)
			if (j.getId().equals(id))
				return j;
		return null;
	}

	public void addReview(Review review) {
		list.add(review);
	}

	public int getCount() {
		return list.size();
	}

	public boolean deleteReview(Review review) {
		if (!list.contains(review))
			return false;
		list.remove(review);
		return true;
	}
}
