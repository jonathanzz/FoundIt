package au.edu.unsw.soacourse.founditrest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import au.edu.unsw.soacourse.founditrest.dao.JobsDao;
import au.edu.unsw.soacourse.founditrest.model.Job;

@Path("/Job")
public class JobsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Job getJobById(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Job> jobs = JobsDao.instance.queryJobs("id", id);
		if (jobs.size() == 0)
			return null;
		return jobs.get(0);
	}

	@GET
	@Path("/all")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Job> getAllJobs(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		List<Job> jobs = JobsDao.instance.queryJobs("", "");
		if (jobs.size() == 0)
			return null;
		return jobs;
	}

	@GET
	@Path("/query")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Job> getJobsByQuery(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@QueryParam("query") @DefaultValue("") String queryItem,
			@QueryParam("value") @DefaultValue("") String queryString) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Job> jobs = JobsDao.instance.queryJobs(queryItem, queryString);
		if (jobs.size() == 0)
			return null;
		return jobs;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createJob(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Job job) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		return postAndGetResponse(job);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateJob(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Job job) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!JobsDao.instance.updateJob(job))
			throw new RuntimeException("POST: Job with" + job.getId()
					+ " error when update.");
	}

	@DELETE
	@Path("{id}")
	public void deleteJob(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!JobsDao.instance.deleteJob(id))
			throw new RuntimeException("DELETE: Job with " + id
					+ " not found when delete.");
	}

	private Response postAndGetResponse(Job job) {
		Response res;
		if (JobsDao.instance.insertJob(job)) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		} else
			res = Response.noContent().build();
		return res;
	}

}
