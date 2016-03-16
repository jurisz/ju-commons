
package org.juz.common.api.page;

import java.util.ArrayList;
import java.util.List;

public class PageableListResult<T> {

	List<T> results = new ArrayList<>();

	long totalRecords;

	int page;

	public void setResults(List<T> results) {
		this.results = results;
	}

	public List<T> getResults() {
		return results;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
