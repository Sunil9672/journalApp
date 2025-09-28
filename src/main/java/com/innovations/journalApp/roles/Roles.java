package com.innovations.journalApp.roles;

public enum Roles {
	ADMIN("admin", "all"), USER("user", "read-own-journal,write-own-journal");

	String strValue;
	String accessString;

	private Roles(String value, String access) {
		this.strValue = value;
		this.accessString = access;
	}

	public String getStringValue() {
		return this.strValue;
	}

	public void updateAccess(String newAccess) {
		this.accessString = newAccess;
	}
}
