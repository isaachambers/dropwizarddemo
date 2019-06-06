package demo.basicapi;

import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.basicapi.configuration.BasicConfiguration;
import demo.basicapi.rest.ContactsResource;
import io.dropwizard.Application;
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

	}
}
