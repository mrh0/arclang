package com.mrh0.arclang.parse.token;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.var.Var;

public class TokenVarCache implements IToken {
	public final String label;
	public final TokenType type;
	private final Var var;
	
	public TokenVarCache(String n, TokenType t, Var v) {
		label = n;
		type = t;
		var = v;
	}
	
	public TokenVarCache(IToken t, Var v) {
		label = t.getLabel();
		type = t.getType();
		var = v;
	}
	
	@Override
	public TokenType getType() {
		return type;
	}

	@Override
	public String getLabel() {
		return label;
	}
	
	public Var getVar() {
		return var;
	}
	
	@Override
	public IVal getValue() {
		return getVar();
	}
}
