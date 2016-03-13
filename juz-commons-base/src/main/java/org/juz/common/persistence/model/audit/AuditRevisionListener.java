package org.juz.common.persistence.model.audit;

import org.hibernate.envers.RevisionListener;
import org.juz.common.security.SecurityHelper;

public class AuditRevisionListener implements RevisionListener {

	private static ThreadLocal<String> revisionDetails = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return "";
		}
	};

	@Override
	public void newRevision(Object revisionEntity) {
		Revision revision = (Revision) revisionEntity;
		String details = revisionDetails.get();
		revision.setDetails(details);
		String user = SecurityHelper.getCurrentUserName();
		revision.setUser(user);
		revisionDetails.remove();
	}

	public static void addDetail(String newDetail) {
		String details = revisionDetails.get();
		if (details.contains(newDetail)) {
			return;
		}
		if (!details.isEmpty()) {
			details += ", ";
		}
		details += newDetail;
		revisionDetails.set(details);
	}
}
