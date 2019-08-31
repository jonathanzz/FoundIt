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

import au.edu.unsw.soacourse.founditrest.dao.CompaniesDao;
import au.edu.unsw.soacourse.founditrest.model.Company;

@Path("/Company")
public class CompaniesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("/id/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Company getCompanyById(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		Company u = CompaniesDao.instance.queryCompanyById(id);
		return u;
	}

	@GET
	@Path("/email/{email}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Company getCompanyByEmail(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@PathParam("email") String email) {
		if (!"i-am-foundit".equals(securityKey)
				|| (!"app-candidate".equals(shortKey)
						&& !"app-manager".equals(shortKey) && !"app-reviewer"
							.equals(shortKey)))
			return null;
		List<Company> u = CompaniesDao.instance.queryCompanyByManager(email);
		if(u.size()==0)
			return null;
		return u.get(0);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createCompany(
			@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Company company) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return null;
		return postAndGetResponse(company);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public void updateCompany(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, Company company) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!CompaniesDao.instance.updateCompany(company))
			throw new RuntimeException("POST: Company with" + company.getId()
					+ " error when update.");
	}

	@DELETE
	public void deleteCompany(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey, @PathParam("id") String id) {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-manager".equals(shortKey))
			return;
		if (!CompaniesDao.instance.deleteCompany(id))
			throw new RuntimeException("DELETE: Company with " + id
					+ " not found when delete.");
	}

	private Response postAndGetResponse(Company company) {
		Response res;
		if (CompaniesDao.instance.insertCompany(company))
			res = Response.created(uriInfo.getAbsolutePath()).build();
		else
			res = Response.noContent().build();
		return res;
	}
}
