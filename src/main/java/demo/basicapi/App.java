package demo.basicapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import demo.basicapi.configuration.BasicConfiguration;
import demo.basicapi.rest.ContactsResource;
import io.dropwizard.Application;
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
		e.jersey().register(new ContactsResource());
	}
}
