package com.viettridao.exception;

public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(9999, "Uncategorized exception"),
	INVALID_KEY(1001, "Invalid message key"),
	USER_NOT_EXISTED(1004, "User does not exist"),
	TAX_NOT_EXISTED(1005, "Tax does not exist"),
	ASSET_NOT_EXISTED(1006, "Asset does not exist"),
	BILL_NOT_EXISTED(1007, "Bill does not exist"),
	ID_NOT_BLANK(1002, "Id must not be blank"),
	ID_INVALID(1003, "Id must be at most 10 characters"),
	ID_DUPLICATED(1008, "Id is duplicated"),
	DATETIME_FORMAT_INVALID(1009, "Date must be in format 'YYYY-MM_DD'T'HH:mm:ss' -> '2023-12-15T14:05:40'")
	;
	
	private ErrorCode() {
	}
	
	ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	private int code;
	private String message;
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
