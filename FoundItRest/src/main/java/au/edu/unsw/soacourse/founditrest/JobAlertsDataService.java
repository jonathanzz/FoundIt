package au.edu.unsw.soacourse.founditrest;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import au.edu.unsw.soacourse.founditrest.dao.DataServiceXquery;

import com.ddtek.xquery3.XQException;

@Path("/Jobalert")
public class JobAlertsDataService {
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public String getJob(@HeaderParam("SecurityKey") String securityKey,
			@HeaderParam("ShortKey") String shortKey,
			@QueryParam("keyword") String keyword,
			@QueryParam("sort_by") String sortby) throws XQException {
		if (!"i-am-foundit".equals(securityKey)
				|| !"app-candidate".equals(shortKey))
			return null;
		DataServiceXquery dsx = new DataServiceXquery("Jobs.xml", keyword,
				sortby);
		String reString = dsx.run();
		return reString;
	}
}
