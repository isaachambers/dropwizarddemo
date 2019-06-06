package demo.basicapi.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.basicapi.dao.ContactDao;
import demo.basicapi.domain.Contact;

@Path("/contact")
@Produces(MediaType.APPLICATION_JSON)
public class ContactsResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContactsResource.class);

	private final ContactDao contactDao;
	private final Validator validator;

	public ContactsResource(DBI jdbi, Validator validator) {
		contactDao = jdbi.onDemand(ContactDao.class);
		this.validator = validator;
	}

	@GET
	@Path("/all")
	public Response getAll() {
		try {
			List<Contact> contacts = contactDao.getAll();
			return Response.ok(contacts).build();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

	}

	@GET
	@Path("/{id}")
	public Response getContact(@PathParam("id") long id) {
		try {
			Contact contact = contactDao.getContactById(id);
			if (null == contact) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				return Response.ok(contact).build();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

	}

	@POST
	public Response createContact(Contact contact) {
		try {
			Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
			if (violations.size() > 0) {
				ArrayList<String> validationMessages = new ArrayList<String>();
				for (ConstraintViolation<Contact> violation : violations) {
					validationMessages.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
				}
				return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();

			} else {
				long newContactId = contactDao.createContact(contact.getFirstName(), contact.getLastName(),
						contact.getPhone());
				return Response.created(new URI(String.valueOf(newContactId))).build();
			}
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

	}

	@DELETE
	@Path("/{id}")
	public Response deleteContact(@PathParam("id") long id) {
		try {
			Contact contact = contactDao.getContactById(id);
			if (null != contact) {
				contactDao.deleteContact(id);
				return Response.noContent().build();
			} else {
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}
	}

	@PUT
	@Path("/{id}")
	public Response updateContact(@PathParam("id") long id, Contact contact) {
		try {

			Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
			if (violations.size() > 0) {
				ArrayList<String> validationMessages = new ArrayList<String>();
				for (ConstraintViolation<Contact> violation : violations) {
					validationMessages.add(violation.getPropertyPath().toString() + ":" + violation.getMessage());
				}
				return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();

			} else {
				Contact c = contactDao.getContactById(id);
				if (null != c) {
					contactDao.updateContact(id, contact.getFirstName(), contact.getLastName(), contact.getPhone());
					return Response.ok(contactDao.getContactById(id)).build();
				} else {
					return Response.status(Status.NOT_FOUND).build();
				}
			}

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e.fillInStackTrace());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
		}

	}
}
