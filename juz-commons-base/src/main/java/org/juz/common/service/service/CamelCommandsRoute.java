package org.juz.common.service.service;

import org.apache.camel.Predicate;
import org.apache.camel.processor.aggregate.UseLatestAggregationStrategy;
import org.apache.camel.spring.SpringRouteBuilder;
import org.apache.commons.lang3.StringUtils;
import org.juz.common.command.CommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class CamelCommandsRoute extends SpringRouteBuilder {

	private static final Logger log = LoggerFactory.getLogger(CamelCommandsRoute.class);

	@Value("${command.queue:}")
	private String commandQueue;

	@Value("${command.queue.aggregationMillis:2000}")
	private Long aggregationMillis = 2000L;

	@Autowired
	private CommandService commandService;

	@Override
	public void configure() {
		if (StringUtils.isEmpty(commandQueue)) {
			return;
		}
		log.info("Configuring commands route");
		errorHandler(defaultErrorHandler().disableRedelivery());

		Predicate noCorrelationId = header("correlationId").isNull();
		from(commandQueue).transacted()
				.choice()
				.when(noCorrelationId).bean(commandService, "execute")
				.otherwise()
				.aggregate(header("correlationId"), new UseLatestAggregationStrategy()).completionTimeout(aggregationMillis)
				.bean(commandService, "execute")
				.endChoice();
	}
}
