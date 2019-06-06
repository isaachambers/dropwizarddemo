package demo.basicapi.domain;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.dropwizard.validation.ValidationMethod;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;
	private long id;

	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	@Length(min = 2, max = 30)
	private String phone;

	public Contact(long id, String firstName, String lastName, String phone) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public Contact(String firstName, String lastName, String phone) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
	}

	public Contact() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ValidationMethod(message = "John Doe is not a valid person!")
	public boolean isPersonValid() {
		if (firstName.equals("John") && lastName.equals("Doe")) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public String toString() {
		return "Contact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", phone=" + phone + "]";
	}

}
