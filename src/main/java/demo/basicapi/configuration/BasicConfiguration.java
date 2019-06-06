package demo.basicapi.configuration;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class BasicConfiguration extends Configuration {

	@JsonProperty
	@NotNull
	private String message;
	@JsonProperty
	@Max(10)
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
