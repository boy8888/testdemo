package com.hummingbird.maaccount.vo;

public class QueryRedPaperOrderVO extends BaseTransOrderVO{
	protected RedPaperVO query;

	public RedPaperVO getQuery() {
		return query;
	}

	public void setQuery(RedPaperVO query) {
		this.query = query;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueryRedPaperOrderVO [query=" + query + ", app="
				+ app + "]";
	}
	
}
