package com.mrh0.arclang.parse.token;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TNumber;
import com.mrh0.arclang.type.TString;
import com.mrh0.arclang.type.TUndefined;

public class Tokens {
	private static String[] opKws = {
		"out", "error", "log", "assert", "if", "else", "while", "iter"
	};
	
	public static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t';
	}
	
	public static boolean isEnd(char c) {
		return isDefiniteEnd(c) || isLineEnd(c);
	}
	
	public static boolean isDefiniteEnd(char c) {
		return c == ';';
	}
	
	public static boolean isLineEnd(char c) {
		return c == '\n' || c == '\r';
	}
	
	public static boolean isBlock(char c) {
		return c == ':';
	}
	
	public static boolean isNumber(char c) {
		return c >= '0' && c <= '9' || c == '.';
	}
	
	public static boolean isSep(char c) {
		return c == ',' || c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}';
	}
	
	public static boolean isOpKeyword(String ident) {
		for(String s : opKws) {
			if(ident.equals(s))
				return true;
		}
		return false;
	}
	
	public static String getLineComment() {
		return "//";
	}
	
	public static String getBlockCommentStart() {
		return "/*";
	}
	
	public static String getBlockCommentEnd() {
		return "*/";
	}
	
	public static boolean isOp(char c) {
		switch(c) {
			case '+':
				return true;
			case '-':
				return true;
			case '*':
				return true;
			case '/':
				return true;
			case '%':
				return true;
			case '<':
				return true;
			case '>':
				return true;
			case '=':
				return true;
			case '~':
				return true;
			case '!':
				return true;
		}
		return false;
	}
	
	public static boolean isIdentOp(String id) {
		switch(id) {
			case "and":
				return true;
			case "or":
				return true;
			case "xor":
				return true;
			case "not":
				return true;
			case "in":
				return true;
			case "of":
				return true;
		}
		return false;
	}
	
	public static int opValue(Token t) {
		if(t.type == TokenType.OP_KW)
			return -10000;
		switch(t.label) {
			case "(":
				return -1000;
				
			case "!":
				return 11;
			case "not":
				return 11;
			case "~":
				return 11;
			
			case "*":
				return 10;
			case "/":
				return 10;
			case "%":
				return 10;
			
			case "+":
				return 9;
			case "-":
				return 9;
			
			case "<":
				return 8;
			case "<=":
				return 8;
			case ">":
				return 8;
			case ">=":
				return 8;
			case "!=":
				return 7;
			case "==":
				return 7;
			
			case "and":
				return 3;
			case "or":
				return 2;
			case "xor":
				return 2;
				
			case "in":
				return 1;
			case "as":
				return 3;
			case "is":
				return 3;
			
			case "=":
				return 0;
			case ":=":
				return 0;
		}
		return 0;
	}
	
	
	public static IVal toValue(IToken t) throws ArcException {
		switch(t.getType()) {
			case NUM:
				return TNumber.fromString(t.getLabel());
			case STR:
				return TString.create(t.getLabel());
			case VAL:
				return ((TokenVal)t).getValue();
			case IDENT:
				switch(t.getLabel()) {
					case "true":
						return TNumber.create(true);
					case "false":
						return TNumber.create(false);
					case "undefined":
						return TUndefined.getInstance();
					case "null":
						return TUndefined.getInstance();
				}
				return null;
			default:
				break;
		}
		return null;
	}
}
