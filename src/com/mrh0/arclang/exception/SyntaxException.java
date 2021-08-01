package com.mrh0.arclang.exception;

public class SyntaxException extends ArcException {

	private static final long serialVersionUID = 1L;

	public SyntaxException(String expected, String got) {
		super("Syntax exception, expected: " + expected + ", got: " + got);
	}
	
	public SyntaxException(String expected) {
		super("Syntax exception, expected: " + expected + ", got: nil");
	}
}
