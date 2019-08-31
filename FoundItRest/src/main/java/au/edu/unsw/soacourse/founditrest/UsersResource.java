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

import au.edu.unsw.soacourse.founditrest.dao.UsersDao;
import au.edu.unsw.soacourse.founditrest.model.User;

@Path("/User")
public class UsersResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("{email}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public User getUser(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("email") String email) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		User u = UsersDao.instance.queryUser(email);
		if (u == null)
			return null;
		return u;
	}

	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<User> getAllUsers(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		List<User> results = UsersDao.instance.getAllUsers();
		if (results.size() == 0)
			return null;
		return results;
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createUser(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, User user) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-candidate".equals(shortKey))
			return null;
		return postAndGetResponse(user);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateUser(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, User user) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-candidate".equals(shortKey))
			return;
		if (!UsersDao.instance.updateUser(user))
			throw new RuntimeException("POST: User with" + user.getEmail()
					+ " error when update.");
	}

	@DELETE
	@Path("{email}")
	public void deleteUser(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("email") String email) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-candidate".equals(shortKey))
			return;
		if (!UsersDao.instance.deleteUser(email))
			throw new RuntimeException("DELETE: User with " + email
					+ " not found when delete.");
	}

	private Response postAndGetResponse(User user) {
		Response res;
		if (UsersDao.instance.insertUser(user)) {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			System.out.println(uriInfo.getAbsolutePath());
		} else
			res = Response.noContent().build();
		return res;
	}
}
