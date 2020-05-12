package com.bizcom.MICI_Station;

public class ErrorCode {
	private String errorCode;
	private String description;
	public ErrorCode(String errorCode, String description) {
		
		this.errorCode = errorCode;
		this.description = description;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ErrorCode [errorCode=" + errorCode + ", description=" + description + "]";
	}
	
}
