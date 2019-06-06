package demo.basicapi.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsResource {

	@GET
	@Path("/{id}")
	public Response getContact(@PathParam("id") int id) {
		// retrieve information about the contact with the provided id
		// ...
		return Response.ok("{contact_id: " + id + ", name: \"Dummy Name\",phone: \"+0123456789\" }").build();
	}
}
