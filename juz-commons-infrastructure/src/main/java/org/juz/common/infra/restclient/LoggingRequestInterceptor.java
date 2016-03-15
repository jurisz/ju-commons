package org.juz.common.infra.restclient;

import com.google.common.base.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

	final static Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

	private int maxEntitySize = 2048;

	/**
	 * response stream is read, use BufferingClientHttpRequestFactory
	 */
	public LoggingRequestInterceptor() {
	}

	public LoggingRequestInterceptor(int maxEntitySize) {
		this.maxEntitySize = maxEntitySize;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

		logRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		logResponse(response);
		return response;
	}

	private void logRequest(HttpRequest request, byte[] body) throws IOException {
		log.info("> ==== request begin");
		log.info("> URI : " + request.getURI());
		log.info("> Method : " + request.getMethod());
		log.info("> Request Body : " + new String(body, Charsets.UTF_8));
		log.info("> ==== request end ");
	}

	private void logResponse(ClientHttpResponse response) throws IOException {
		log.info("< ==== response ");
		log.info("< status code: " + response.getStatusCode());
		log.info("< status text: " + response.getStatusText());
		StringBuilder responseBuilder = new StringBuilder();
		logInboundEntity(responseBuilder, response.getBody(), Charsets.UTF_8);
		log.info("< {}", responseBuilder.toString());
		log.debug("< ==== response end");
	}

	private InputStream logInboundEntity(final StringBuilder b, InputStream stream, final Charset charset) throws IOException {
		if (!stream.markSupported()) {
			stream = new BufferedInputStream(stream);
		}
		stream.mark(maxEntitySize + 1);
		final byte[] entity = new byte[maxEntitySize + 1];
		final int entitySize = stream.read(entity);
		b.append(new String(entity, 0, Math.min(entitySize, maxEntitySize), charset));
		if (entitySize > maxEntitySize) {
			b.append("...more...");
		}
		b.append('\n');
		stream.reset();
		return stream;
	}
}