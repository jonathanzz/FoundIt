package au.edu.unsw.soacourse.founditrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.founditrest.dao.ApplicationsDao;
import au.edu.unsw.soacourse.founditrest.model.Application;

@Path("/Application")
public class ApplicationsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Application> getAllApplications(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		List<Application> result = ApplicationsDao.instance.queryApplication(
				"", "");
		if (result.size() == 0)
			return null;
		return result;
	}

	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Application getApplicationById(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Application> result = ApplicationsDao.instance.queryApplication(
				"id", id);
		if (result.size() == 0)
			return null;
		return result.get(0);
	}

	@GET
	@Path("/candidate/{email}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Application> getApplicationByCandidate(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("email") String email) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Application> result = ApplicationsDao.instance.queryApplication(
				"candidateEmail", email);
		if (result.size() == 0)
			return null;
		return result;
	}

	@GET
	@Path("/job/{jobId}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Application> getApplicationsByJob(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("jobId") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;
		List<Application> result = ApplicationsDao.instance.queryApplication(
				"applyJobId", id);
		if (result.size() == 0)
			return null;
		return result;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createApplication(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Application application) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-candidate".equals(shortKey))
			return null;
		return postAndGetResponse(application);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateApplication(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Application application) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return;
		if (!ApplicationsDao.instance.updateApplication(application))
			throw new RuntimeException("POST: Application with"
					+ application.getId() + " error when update.");
	}

	@DELETE
	@Path("{id}")
	public void deleteApplication(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return;
		if (!ApplicationsDao.instance.deleteApplication(id))
			throw new RuntimeException("DELETE: Application with " + id
					+ " not found when delete.");
	}

	private Response postAndGetResponse(Application application) {
		Response res;
		if (ApplicationsDao.instance.insertApplication(application))
			res = Response.created(uriInfo.getAbsolutePath()).build();
		else
			res = Response.noContent().build();
		return res;
	}
}
