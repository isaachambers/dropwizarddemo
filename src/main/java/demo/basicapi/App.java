package demo.basicapi;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.basicapi.auth.AppAuthorizer;
import demo.basicapi.auth.ApplicationAuthenticator;
import demo.basicapi.auth.User;
import demo.basicapi.configuration.BasicConfiguration;
import demo.basicapi.rest.ClientResource;
import demo.basicapi.rest.ContactsResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class App extends Application<BasicConfiguration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws Exception {
		System.out.println("Hello World!");
		new App().run(args);
	}

	@Override
	public void initialize(Bootstrap<BasicConfiguration> b) {
	}

	@Override
	public void run(BasicConfiguration c, Environment e) throws Exception {
		LOGGER.info("Method App#run() called");
		System.out.println("Hello world, by Dropwizard!");

		for (int i = 0; i < c.getMessageRepetitions(); i++) {
			System.out.println(c.getMessage());
		}

		// Create a DBI factory and build a JDBI instance
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");
		// Add the resource to the environment
		e.jersey().register(new ContactsResource(jdbi, e.getValidator()));

		final Client client = new JerseyClientBuilder(e).using(c.getJerseyClientConfiguration()).using(e)
				.build("REST Client");
		e.jersey().register(new ClientResource(client));
		// ****** Dropwizard security - custom classes ***********/
		// Security Features will be demonstrated on the ClientResource class
		e.jersey()
				.register(new AuthDynamicFeature(
						new BasicCredentialAuthFilter.Builder<User>().setAuthenticator(new ApplicationAuthenticator())
								.setAuthorizer(new AppAuthorizer()).setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
		e.jersey().register(RolesAllowedDynamicFeature.class);
		e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

	}
}
