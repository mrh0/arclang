package com.mrh0.arclang.parse.token;

import com.mrh0.arclang.type.IVal;

public interface IToken {
	public TokenType getType();
	public String getLabel();
	public default IVal getValue() {
		return null;
	}
}
