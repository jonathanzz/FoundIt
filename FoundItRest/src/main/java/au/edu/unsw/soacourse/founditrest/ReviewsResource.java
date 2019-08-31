package au.edu.unsw.soacourse.founditrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.springframework.hateoas.Link;

import au.edu.unsw.soacourse.founditrest.dao.ReviewsDao;
import au.edu.unsw.soacourse.founditrest.model.Review;

@Path("/Review")
public class ReviewsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Review> getAllReviews(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		List<Review> result = ReviewsDao.instance.queryReview("", "");
		if (result.size() == 0)
			return null;
		for (Review r : result) {
			Link l1 = new Link("http://localhost:8080/Review", "self");
			Link l2 = new Link("http://localhost:8080/Review/id/" + r.getId(),
					"item");
			r.addLink(l1);
			r.addLink(l2);
		}
		return result;
	}

	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Review getReviewById(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;

		List<Review> result = ReviewsDao.instance.queryReview("id", id);
		if (result.size() == 0)
			return null;
		Review r = result.get(0);
		Link l1 = new Link("http://localhost:8080/Review/id/" + r.getId(),
				"self");
		Link l2 = new Link("http://localhost:8080/Review/reviewer/"
				+ r.getuId(), "collection");
		r.addLink(l1);
		r.addLink(l2);
		return r;
	}

	@GET
	@Path("/app/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Review> getReviewByApp(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Review> result = ReviewsDao.instance.queryReview("appId", id);
		if (result.size() == 0)
			return null;
		for (Review r : result) {
			Link l1 = new Link("http://localhost:8080/Review/app/" + id, "self");
			Link l2 = new Link("http://localhost:8080/Review/id/" + r.getId(),
					"item");
			r.addLink(l1);
			r.addLink(l2);
		}
		return result;
	}

	@GET
	@Path("/reviewer/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Review> getReviewByReviewerId(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("uId") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;
		List<Review> result = ReviewsDao.instance.queryReview("", id);
		if (result.size() == 0)
			return null;
		for (Review r : result) {
			Link l1 = new Link("http://localhost:8080/Review/reviewer/" + id,
					"self");
			Link l2 = new Link("http://localhost:8080/Review/id/" + r.getId(),
					"item");
			r.addLink(l1);
			r.addLink(l2);
		}
		return result;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createReview(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Review review) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-reviewer".equals(shortKey))
			return null;
		return postAndGetResponse(review);
	}

	@DELETE
	@Path("{id}")
	public void deleteReview(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!ReviewsDao.instance.deleteReview(id))
			throw new RuntimeException("DELETE: Review with " + id
					+ " not found when delete.");
	}

	private Response postAndGetResponse(Review review) {
		Response res;
		if (ReviewsDao.instance.insertReview(review))
			res = Response.created(uriInfo.getAbsolutePath()).build();
		else
			res = Response.noContent().build();
		return res;
	}
}
