package com.mrh0.arclang.exception;

import com.mrh0.arclang.parse.token.IToken;

public class UnexpectedSymbolException extends ArcException{
	private static final long serialVersionUID = 1L;

	public UnexpectedSymbolException(String message) {
		super("Unexpected symbol: " + message);
	}
	
	public UnexpectedSymbolException(IToken token) {
		super("Unexpected symbol: '" + token.getLabel() + "' of type '" + token.getType() + "'.");
	}
}
