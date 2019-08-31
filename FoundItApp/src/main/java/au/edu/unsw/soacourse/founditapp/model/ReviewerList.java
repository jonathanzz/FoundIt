package au.edu.unsw.soacourse.founditapp.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Members")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReviewerList {
	@XmlElement(name = "Reviewer")
	private List<Reviewer> reviewers = new ArrayList<Reviewer>();

	public ReviewerList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReviewerList(List<Reviewer> reviewers) {
		super();
		this.reviewers = reviewers;
	}

	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

	public void addReviewer(Reviewer r) {
		reviewers.add(r);
	}

	public void deleteReviewer(Reviewer r) {
		reviewers.remove(r);
	}

}
