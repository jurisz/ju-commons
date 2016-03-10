package org.juz.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.juz.common.persistence", "org.juz.common.service"})
public class ModuleConfiguration {

}
