package com.hummingbird.maaccount.vo;

public class QueryJifenOrderVO extends BaseTransOrderVO{
	protected JifenVO query;

	
	
	public JifenVO getQuery() {
		return query;
	}



	public void setQuery(JifenVO query) {
		this.query = query;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "QueryJifenOrderVO [query=" + query + ", app="
				+ app + "]";
	}
}
