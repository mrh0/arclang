package com.mrh0.arclang.parse.statement;

import java.util.List;
import com.mrh0.arclang.parse.token.IToken;
import com.mrh0.arclang.util.StringUtil;

public class Statement implements IStatement {
	private IToken[] tokens;
	public final int line;
	
	public Statement(List<IToken> tokens, int line) {
		this.tokens = tokens.toArray(new IToken[0]);
		this.line = line;
		//System.out.println("Created Statement: " + tokens.toString());
	}
	
	public IToken[] getTokens() {
		return tokens;
	}
	
	public IToken getToken(int i) {
		return tokens[i];
	}
	
	public void setToken(int i, IToken t) {
		tokens[i] = t;
	}
	
	public int length() {
		return tokens.length;
	}
	
	@Override
	public String toString() {
		return "["+line+":"+StringUtil.arrayToString(tokens)+"]";
	}
}
