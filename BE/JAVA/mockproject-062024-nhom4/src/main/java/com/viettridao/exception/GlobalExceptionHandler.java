package com.viettridao.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.viettridao.response.ApiResponse;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {
	
//	@ExceptionHandler(value = RuntimeException.class)
//	ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
//		ApiResponse apiResponse = new ApiResponse<>();
//		apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
//		apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
//		
//		return ResponseEntity.badRequest().body(apiResponse);
//	}
//	
//	@ExceptionHandler(value = AppException.class)
//	ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
//		ErrorCode errorCode = exception.getErrorCode();
//		ApiResponse apiResponse = new ApiResponse<>();
//		apiResponse.setCode(errorCode.getCode());
//		apiResponse.setMessage(errorCode.getMessage());
//		
//		return ResponseEntity.badRequest().body(apiResponse);
//	}
//	
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//    public ResponseEntity<ApiResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
//		ApiResponse apiResponse = new ApiResponse<>();
//		apiResponse.setCode(ErrorCode.DATETIME_FORMAT_INVALID.getCode());
//		apiResponse.setMessage(ErrorCode.DATETIME_FORMAT_INVALID.getMessage());
//		
//		return ResponseEntity.badRequest().body(apiResponse);
//    }
//	
//	@ExceptionHandler(value = MethodArgumentNotValidException.class)
//	ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {
//		String enumKey = exception.getFieldError().getDefaultMessage();
//		ErrorCode errorCode = ErrorCode.INVALID_KEY;
//		
//		try {
//			errorCode = ErrorCode.valueOf(enumKey);
//		} catch (IllegalArgumentException e) {
//			
//		}
//		
//		ApiResponse apiResponse = new ApiResponse<>();
//		
//		apiResponse.setCode(errorCode.getCode());
//		apiResponse.setMessage(errorCode.getMessage());
//		
//		return ResponseEntity.badRequest().body(apiResponse);
//	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationException(Exception e, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimestamp(new Date());
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setPath(request.getDescription(false).replace("uri", ""));
		errorResponse.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		String message = e.getMessage();
		int start = message.lastIndexOf("[");
		int end = message.lastIndexOf("]");
		message = message.substring(start + 1, end - 1);
		errorResponse.setMessage(message);
		
		return errorResponse;
	}
}
