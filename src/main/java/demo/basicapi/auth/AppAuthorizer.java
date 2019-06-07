package demo.basicapi.auth;

import io.dropwizard.auth.Authorizer;

//Authorizer class is responsible for matching the roles and 
//decide whether user is allowed to perform certain action or not.

public class AppAuthorizer implements Authorizer<User> {

	@Override
	public boolean authorize(User principal, String role) {
		return principal.getRoles() != null && principal.getRoles().contains(role);
	}

}
