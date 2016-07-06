package integration;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.SpringCamelContext;
import org.juz.common.config.ScanPersistencePackages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "integration")
public class IntegrationTestingConfiguration {

	@Bean
	ScanPersistencePackages scanPersistencePackages() {
		return new ModelPersistenceScan();
	}

	@Bean
	@Autowired
	CamelContext camelContext(ApplicationContext applicationContext) {
		return new SpringCamelContext(applicationContext);
	}
}
