package com.mrh0.arclang.parse.token;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.type.IVal;

public class TokenVal implements IToken {
	public final IVal value;
	
	public TokenVal(IVal value) {
		this.value = value;
	}
	
	public TokenVal(Token t) throws ArcException {
		this.value = Tokens.toValue(t);
	}
	
	@Override
	public TokenType getType() {
		return TokenType.VAL;
	}

	public IVal getValue() {
		return value;
	}

	@Override
	public String getLabel() {
		return value.toString();
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
}
