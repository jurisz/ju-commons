package org.juz.common.infra.restclient;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.juz.common.infra.httpclient.ApacheHttpClientFactoryBean;
import org.juz.common.util.ExceptionUtils;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateFactoryBean extends AbstractFactoryBean<RestTemplate> {

	private boolean ignoreCertificateErrors = true;

	private int connectionTimeout = 30 * 1000;

	private int socketTimeout = 30 * 1000;

	private int connectionTTLSeconds = 120;

	private int maxHttpConnections = 20;

	private boolean enableLoggingFilter = false;

	private String basicAuthUsername;

	private String basicAuthPassword;

	@Override
	public Class<?> getObjectType() {
		return RestTemplate.class;
	}

	@Override
	protected RestTemplate createInstance() throws Exception {

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(createHttpClient());
		if (enableLoggingFilter) {
			requestFactory = new BufferingClientHttpRequestFactory(requestFactory);
		}

		RestTemplate restTemplate = new RestTemplate(requestFactory);

		if (enableLoggingFilter) {
			restTemplate.getInterceptors().add(new LoggingRequestInterceptor());
		}

		if (StringUtils.isNotEmpty(basicAuthUsername)) {
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(basicAuthUsername, basicAuthPassword));
		}

		return restTemplate;
	}

	private CloseableHttpClient createHttpClient() {
		return ExceptionUtils.propagateCatchableException(() -> {
			ApacheHttpClientFactoryBean httpClientFactory = new ApacheHttpClientFactoryBean()
					.withMaxHttpConnections(maxHttpConnections)
					.withConnectionTimeout(connectionTimeout)
					.withConnectionTTLSeconds(connectionTTLSeconds)
					.withIgnoreCertificateErrors(ignoreCertificateErrors)
					.withSocketTimeout(socketTimeout);
			httpClientFactory.afterPropertiesSet();
			return httpClientFactory.getObject();
		});
	}

	public RestTemplateFactoryBean withMaxHttpConnections(int maxConnections) {
		this.maxHttpConnections = maxConnections;
		return this;
	}

	public RestTemplateFactoryBean withIgnoreCertificateErrors(boolean ignore) {
		this.ignoreCertificateErrors = ignore;
		return this;
	}

	public RestTemplateFactoryBean withConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public RestTemplateFactoryBean withSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public RestTemplateFactoryBean withConnectionTTLSeconds(int connectionTTLSeconds) {
		this.connectionTTLSeconds = connectionTTLSeconds;
		return this;
	}

	public RestTemplateFactoryBean withEnableLoggingFilter(boolean enableLoggingFilter) {
		this.enableLoggingFilter = enableLoggingFilter;
		return this;
	}

	public RestTemplateFactoryBean withBasicAuthUsername(String basicAuthUsername) {
		this.basicAuthUsername = basicAuthUsername;
		return this;
	}

	public RestTemplateFactoryBean withBasicAuthPassword(String basicAuthPassword) {
		this.basicAuthPassword = basicAuthPassword;
		return this;
	}
}
