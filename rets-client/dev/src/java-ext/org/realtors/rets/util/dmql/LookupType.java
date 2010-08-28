package org.realtors.rets.util.dmql;


public enum LookupType {
	AND("+"),
	OR("|"),
	NOT("~");

	private String operator;

	private LookupType(String operator) {
		this.operator = operator;
	}

	public String getOperator() {
		return this.operator;
	}
}