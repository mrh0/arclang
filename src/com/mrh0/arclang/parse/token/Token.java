package com.mrh0.arclang.parse.token;

public class Token implements IToken {
	public final String label;
	public final TokenType type;
	
	public Token(String n, TokenType t) {
		label = n;
		type = t;
	}
	
	@Override
	public String toString() {
		if(type == TokenType.STR)
			return type+":'"+label+"'";
		return type+":"+label;
	}
	
	public boolean isIdentifier() {
		return type == TokenType.IDENT;
	}
	
	public boolean isNumber() {
		return type == TokenType.NUM;
	}
	
	public boolean isString() {
		return type == TokenType.STR;
	}
	
	public boolean isOperator() {
		return type == TokenType.OP;
	}
	
	public boolean isSeparator() {
		return type == TokenType.SEP;
	}
	
	public boolean isStatementEnd() {
		return type == TokenType.END;
	}
	
	public boolean isLnEnd() {
		return type == TokenType.LN;
	}
	
	public boolean isBlock() {
		return type == TokenType.BLOCK;
	}
	
	public boolean isBlockEnd() {
		return label.equals("end");
	}
	
	public boolean isOpenBracket() {
		return label.equals("(");
	}
	
	public boolean isCloseBracket() {
		return label.equals(")");
	}
	
	public boolean isOpenAccessorBracket() {
		return label.equals("[");
	}
	
	public boolean isCloseAccessorBracket() {
		return label.equals("]");
	}
	
	public boolean isOpenObjectBracket() {
		return label.equals("{");
	}
	
	public boolean isCloseObjectBracket() {
		return label.equals("}");
	}

	@Override
	public TokenType getType() {
		return type;
	}
	
	@Override
	public String getLabel() {
		return label;
	}
}
