package org.juz.common.infra.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.config.AbstractFactoryBean;

public class ApacheHttpClientFactoryBean extends AbstractFactoryBean<CloseableHttpClient> {

	private boolean ignoreCertificateErrors = true;

	private int connectionTimeout = 30 * 1000;

	private int socketTimeout = 30 * 1000;

	private int connectionTTLSeconds = 120;

	private int maxHttpConnections = 20;

	@Override
	public Class<?> getObjectType() {
		return CloseableHttpClient.class;
	}

	@Override
	protected CloseableHttpClient createInstance() throws Exception {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(connectionTimeout)
				.setSocketTimeout(socketTimeout)
				.build();
		HttpClientBuilder httpClientBuilder = HttpClients.custom()
				.setConnectionManager(createConnectionManager())
				.setDefaultRequestConfig(requestConfig);

		CloseableHttpClient httpClient = httpClientBuilder.build();
		return httpClient;
	}

	private HttpClientConnectionManager createConnectionManager() {
		return new PoolingHttpClientConnectionManagerFactoryBean()
				.withMaxConnections(maxHttpConnections)
				.withConnectionTTLSeconds(connectionTTLSeconds)
				.withIgnoreCertificateErrors(ignoreCertificateErrors)
				.create();
	}

	public ApacheHttpClientFactoryBean withMaxHttpConnections(int maxConnections) {
		this.maxHttpConnections = maxConnections;
		return this;
	}

	public ApacheHttpClientFactoryBean withIgnoreCertificateErrors(boolean ignore) {
		this.ignoreCertificateErrors = ignore;
		return this;
	}

	public ApacheHttpClientFactoryBean withConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public ApacheHttpClientFactoryBean withSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public ApacheHttpClientFactoryBean withConnectionTTLSeconds(int connectionTTLSeconds) {
		this.connectionTTLSeconds = connectionTTLSeconds;
		return this;
	}
}
