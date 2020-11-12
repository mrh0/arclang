package com.mrh0.arclang.exception;

public class AssertionException extends ArcException {

	private static final long serialVersionUID = 1L;

	public AssertionException(String reason) {
		super("Assertion '"+reason+"' was not fulfilled.");
	}
}
