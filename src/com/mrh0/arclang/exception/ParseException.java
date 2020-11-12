package com.mrh0.arclang.exception;

public class ParseException extends ArcException {
	private static final long serialVersionUID = 1L;

	public ParseException(String parse) {
		super("Cannot parse '" + parse + "'");
	}
}
