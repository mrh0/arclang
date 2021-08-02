package com.mrh0.arclang.exception;

public class ExpectationException extends ArcException{
	private static final long serialVersionUID = 1L;
	
	public ExpectationException(String expected) {
		super("Expected: '" + expected + "', got: nil");
	}
}
