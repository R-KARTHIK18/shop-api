package com.pubsub.process.GCPStorage;

public enum ResponseStatusCode {

	SUCCESS(2002, "SUCCESS"), ERROR(2001, "ERROR"), RECORD_SAVED(2000, "Record Saved Successfully"),
	INVALID_REQUEST(2003, "Invalid Request"), RECORD_NOT_FOUND(2004, "UserId not found"),
	INTERNAL_SERVER_ERROR(2005, "Internal Server Error"), GCP_STORAGE_ERROR(2006, "GCP Data Storage Error"),
	EQUIFAX_ERROR(2007, "Something went wrong, please try after sometime"),
	AUTH_TOKEN_ERROR(2008, "Error Getting Authentication Token."),
	ACCOUNT_NOT_FOUND(2010, "Record not found with accountId");

	private String value;
	private int code;

	private ResponseStatusCode(int code) {
		this.code = code;
	}

	private ResponseStatusCode(String value) {
		this.value = value;
	}

	private ResponseStatusCode(int code, String value) {
		this.value = value;
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public String getCode() {
		return "vas-" + this.code;
	}

	public static ResponseStatusCode getResponseStatusCode(String cValue) {
		for (ResponseStatusCode cEnum : values()) {
			if (cEnum.getValue().equalsIgnoreCase(cValue)) {
				return cEnum;
			}
		}
		return null;
	}
}
