package org.juz.common.service.service;

import org.juz.common.config.Profiles;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Profile({Profiles.Production, Profiles.Development})
class OnTransactionCommitBinder implements TransactionBinder {

	@Override
	public void bind(final Runnable callback) {
		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCompletion(int status) {
				if (status == STATUS_COMMITTED) {
					callback.run();
				}
			}
		});
	}
}
