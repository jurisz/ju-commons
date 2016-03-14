package integration;

import org.juz.common.config.ScanPersistencePackages;

import java.util.Collection;

import static com.google.common.collect.ImmutableList.of;

public class ModelPersistenceScan implements ScanPersistencePackages {

	@Override
	public Collection<String> asStrings() {
		return of("integration.persistence");
	}
}
