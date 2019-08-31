package au.edu.unsw.soacourse.founditrest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
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

import au.edu.unsw.soacourse.founditrest.dao.*;
import au.edu.unsw.soacourse.founditrest.model.*;

@Path("/HiringTeam")
public class HiringTeamsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public HiringTeam getTeamMembersById(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;
		List<HiringTeam> hiringTeam = HiringTeamsDao.instance.queryHiringTeam(
				"id", id);
		if (hiringTeam.size() == 0)
			return null;
		return hiringTeam.get(0);
	}

	@GET
	@Path("/job/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public HiringTeam getTeamMembersByJob(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;
		List<HiringTeam> hiringTeam = HiringTeamsDao.instance.queryHiringTeam(
				"jobId", id);
		if (hiringTeam.size() == 0)
			return null;
		return hiringTeam.get(0);
	}
	
	@GET
	@Path("/reviewer/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Reviewer getReviewerById(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return null;
		Reviewer Reviewer = HiringTeamsDao.instance.queryReviewerById(id);
		return Reviewer;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<HiringTeam> getAllReviewers(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		List<HiringTeam> teams = HiringTeamsDao.instance
				.queryHiringTeam("", "");
		if (teams.size() == 0)
			return null;
		return teams;
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateHiringTeam(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, HiringTeam team) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!HiringTeamsDao.instance.updateHiringTeam(team))
			throw new RuntimeException("PUT: Hiring team with id "
					+ team.getId() + " error when update.");
	}

	@PUT
	@Path("/reviewer")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateReviewer(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Reviewer r) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-manager".equals(shortKey) && !"app-reviewer"
						.equals(shortKey)))
			return;
		if (!HiringTeamsDao.instance.updateReviewer(r))
			throw new RuntimeException("PUT: Hiring team with id " + r.getId()
					+ " error when update.");
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createHiringTeam(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, HiringTeam team) throws Exception {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		String id = HiringTeamsDao.instance.insertHiringTeam(team);
		Response res = Response.created(new URI(id)).build();
		return res;
	}
}
