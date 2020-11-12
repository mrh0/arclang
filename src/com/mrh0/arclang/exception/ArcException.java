package com.mrh0.arclang.exception;

public class ArcException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public ArcException(String message) {
		this.message = message;
	}
	
	public ArcException() {
		this("Unexpected exception.");
	}
	
	@Override
	public String getMessage() {
		return "[ERR:<#LINE>]:" + message;
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
