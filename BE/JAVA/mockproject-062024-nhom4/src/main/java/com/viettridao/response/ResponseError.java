package com.viettridao.response;

public class ResponseError<T> extends ResponseData<Object> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 652132475111903283L;

	public ResponseError(int status, String message) {
        super(status, message);
    }
}
