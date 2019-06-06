package demo.basicapi.configuration;

import javax.validation.constraints.Max;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class BasicConfiguration extends Configuration {

	@JsonProperty
	@Max(10)
	private String message;
	@JsonProperty
	@NotEmpty
	private int messageRepetitions;

	@JsonProperty
	private String appName = "This is a default value";

	public String getMessage() {
		return message;
	}

	public int getMessageRepetitions() {
		return messageRepetitions;
	}

	public String getAppName() {
		return appName;
	}

}
