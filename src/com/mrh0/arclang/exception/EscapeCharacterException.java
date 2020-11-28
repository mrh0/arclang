package com.mrh0.arclang.exception;

public class EscapeCharacterException extends ArcException{

	private static final long serialVersionUID = 1L;

	public EscapeCharacterException(char escape) {
		super("Unknown string escape character '\\" + escape + "'.");
	}
	
	public EscapeCharacterException() {
		super("Malformed string escape character.");
	}
}
