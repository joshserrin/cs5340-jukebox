package org.jukebox.model;

public class SearchParameters {
	private final String containing;

	public SearchParameters(String containing) {
		if (null == containing) {
			throw new IllegalArgumentException("containing cannot be null");
		}
		this.containing = containing;
	}

	public String getSearchString() {
		return containing;
	}
}
