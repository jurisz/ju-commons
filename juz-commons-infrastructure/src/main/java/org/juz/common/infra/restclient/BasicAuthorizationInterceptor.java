package org.juz.common.infra.restclient;

import com.google.common.base.Charsets;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;

import java.io.IOException;

public class BasicAuthorizationInterceptor implements ClientHttpRequestInterceptor {

	private final String username;
	private final String password;

	BasicAuthorizationInterceptor(String username, String password) {
		this.username = username;
		this.password = (password == null ? "" : password);
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body,
										ClientHttpRequestExecution execution) throws IOException {
		String token = Base64Utils.encodeToString((username + ":" + password).getBytes(Charsets.UTF_8));
		request.getHeaders().add("Authorization", "Basic " + token);
		return execution.execute(request, body);
	}
}