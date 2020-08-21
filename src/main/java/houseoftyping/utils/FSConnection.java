package houseoftyping.utils;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.filter.LoggingFilter;

import factuursturen.domain.FSClient;
import factuursturen.domain.Country;

public class FSConnection {
	private String username;
	private String password;

	public FSConnection(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void getCountryList() {

		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(username, password)
				.build();
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);

		Client client = ClientBuilder.newClient((Configuration) clientConfig);
		WebTarget webTarget = client.target("https://www.factuursturen.nl/api/v1").path("countrylist/nl");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		List<Country> countryList = response.readEntity(new GenericType<List<Country>>() {
		});

		// List<Country> countryList = countries.getCountryList();

		System.out.println(response.getStatus());
		System.out.println(Arrays.toString(countryList.toArray(new Country[countryList.size()])));
	}

	public void saveNewClient(FSClient fsClient) {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder().credentials(username, password)
				.build();
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);

		Client client = ClientBuilder.newClient(new ClientConfig().register(LoggingFilter.class));
		WebTarget webTarget = client.target("https://www.factuursturen.nl/api/v1").path("clients");

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.post(Entity.entity(fsClient, MediaType.APPLICATION_JSON));


		System.out.println(response.getStatus());
	}
}
