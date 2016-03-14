package integration;

import org.juz.common.config.ScanPersistencePackages;
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
}
