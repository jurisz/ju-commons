package org.juz.common.service.service;

import org.juz.common.config.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;

@Component
@Profile(Profiles.Test)
class ImmediateTransactionBinder implements TransactionBinder {

	@Autowired
	PlatformTransactionManager transactionManager;

	TransactionTemplate transactionTemplate;

	@PostConstruct
	public void init() {
		transactionTemplate = new TransactionTemplate(transactionManager);
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}

	@Override
	public void bind(final Runnable callback) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				transactionTemplate.execute(status -> {
					callback.run();
					return null;
				});

			}
		});
	}
}
