package demo.basicapi.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.basicapi.domain.Contact;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactsResource.class);

	@GET
	@Path("/{id}")
	public Response getContact(@PathParam("id") long id) {
		Contact c = new Contact(id, "Mark", "Nyangoye", "0988438934");
		LOGGER.info(c.toString());
		return Response.ok(c).build();
	}

	@POST
	public Response createContact(Contact contact) {
		LOGGER.info(contact.toString());
		return Response.created(null).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteContact(@PathParam("id") long id) {
		LOGGER.info("Deleting " + id);
		return Response.noContent().build();
	}

	@PUT
	@Path("/{id}")
	public Response updateContact(Contact contact) {
		LOGGER.info(contact.toString());
		return Response.ok(contact).build();
	}
}
