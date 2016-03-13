package integration;

import org.juz.common.config.Profiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.Resource;

@Configuration
@Profile({Profiles.Test, Profiles.Development})
class TestingPropertiesConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propsConfigurer(@Value("classpath:sample-app.properties") Resource defaultBundle,
																	   @Value("file:///${application.home}/:sample-app.properties") Resource appHomeBundle,
																	   ConfigurableApplicationContext applicationContext) {

		PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
		configurer.setLocations(new Resource[]{defaultBundle, appHomeBundle});
		configurer.setIgnoreResourceNotFound(true);
		return configurer;
	}
}
