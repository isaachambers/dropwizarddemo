package demo.basicapi.rest;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

//Adding @Auth annotation will trigger authentication filter on any API where it is put as parameter.

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import demo.basicapi.auth.User;
import demo.basicapi.domain.Contact;
import io.dropwizard.auth.Auth;



@Path("client")
public class ClientResource {

	private final Client client;
	
	public ClientResource(Client client) {
		this.client = client;
	}

	@PermitAll
	@GET
	@Path("/showContact/{id}")
	public String showContact(@PathParam("id") int id, @Auth User user) {

		WebTarget target = client.target("http://localhost:8080/contact/" + id);
		Contact c = target.request().get(Contact.class);

		String output = "ID: " + id + "\nFirst name: " + c.getFirstName() + "\nLast name: " + c.getLastName()
				+ "\nPhone: " + c.getPhone();
		return output;
	}

	@POST
	@Path("/newContact")
	public Response newContact(Contact contact) {

		WebTarget target = client.target("http://localhost:8080/contact");
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(contact, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 201) {
			// Created
			return Response.status(302).entity("The contact was created successfully! The new contact can be found at "
					+ response.getHeaders().getFirst("Location")).build();
		} else {
			// Other Status code, indicates an error
			return Response.status(422).entity(response.getEntity()).build();
		}
	}

	@POST
	@Path("/updateContact/{id}")
	public Response updateContact(@PathParam("id") long id, Contact contact) {

		WebTarget target = client.target("http://localhost:8080/contact/" + id);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity(contact, MediaType.APPLICATION_JSON));

		if (response.getStatus() == 200) {
			// Created
			return Response.status(302).entity("The contact was updated successfully!").build();
		} else {
			// Other Status code, indicates an error
			return Response.status(422).entity(response.getEntity()).build();
		}
	}

	@RolesAllowed({ "ADMIN" })
	@GET
	@Path("/deleteContact/{id}")
	public Response deleteContact(@PathParam("id") int id, @Auth User user) {
		WebTarget target = client.target("http://localhost:8080/contact/" + id);
		Response response = target.request().delete();
		return Response.status(response.getStatus()).entity(response.getEntity()).build();
	}

}
