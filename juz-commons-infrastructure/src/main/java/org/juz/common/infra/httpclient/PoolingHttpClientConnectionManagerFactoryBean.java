package org.juz.common.infra.httpclient;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLInitializationException;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.juz.common.infra.ssl.TrustAllStrategy;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

public class PoolingHttpClientConnectionManagerFactoryBean {

	private int maxConnections;

	private int connectionTTLSeconds;

	private boolean ignoreCertificateErrors;

	public PoolingHttpClientConnectionManager create() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(getSocketRegistry(), null, null, null, connectionTTLSeconds, TimeUnit.SECONDS);
		connectionManager.setMaxTotal(maxConnections);
		connectionManager.setDefaultMaxPerRoute(maxConnections);
		return connectionManager;
	}

	private Registry<ConnectionSocketFactory> getSocketRegistry() {
		ConnectionSocketFactory sslFactory;
		if (ignoreCertificateErrors) {
			try {
				SSLContextBuilder builder = new SSLContextBuilder();
				builder.loadTrustMaterial(null, new TrustAllStrategy());
				sslFactory = new SSLConnectionSocketFactory(builder.build(), new NoopHostnameVerifier());
			} catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
				throw new SSLInitializationException("Trusting all SSL context initialization error", e);
			}
		} else {
			sslFactory = SSLConnectionSocketFactory.getSocketFactory();
		}

		return RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslFactory)
				.build();
	}


	public PoolingHttpClientConnectionManagerFactoryBean withMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
		return this;
	}

	public PoolingHttpClientConnectionManagerFactoryBean withConnectionTTLSeconds(int connectionTTLSeconds) {
		this.connectionTTLSeconds = connectionTTLSeconds;
		return this;
	}

	public PoolingHttpClientConnectionManagerFactoryBean withIgnoreCertificateErrors(boolean ignoreCertificateErrors) {
		this.ignoreCertificateErrors = ignoreCertificateErrors;
		return this;
	}
}
