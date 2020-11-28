package com.mrh0.arclang.parse.token;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TNumber;
import com.mrh0.arclang.type.TString;
import com.mrh0.arclang.type.TUndefined;

public class Tokens {
	public static boolean isWhitespace(char c) {
		return c == ' ' || c == '\t';
	}
	
	public static boolean isDefiniteEnd(char c) {
		return c == ';';
	}
	
	public static boolean isLineEnd(char c) {
		return c == '\n' || c == '\r';
	}
	
	public static boolean isEnd(char c) {
		return isDefiniteEnd(c) || isLineEnd(c);
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
	
	public static boolean isOpenBracket(char c) {
		return c == '(' || c == '[' || c == '{';
	}
	
	public static boolean isCloseBracket(char c) {
		return c == ')' || c == ']' || c == '}';
	}
	
	public static char getOpener(char c) {
		switch(c) {
			case ')':
				return '(';
			case ']':
				return '[';
			case '}':
				return '{';
		}
		return '\0';
	}
	
	public static char getCloser(char c) {
		switch(c) {
			case '(':
				return ')';
			case '[':
				return ']';
			case '{':
				return '}';
		}
		return '\0';
	}
	
	//Comments
	public static String getLineComment() {
		return "//";
	}
	
	public static String getBlockCommentStart() {
		return "/*";
	}
	
	public static String getBlockCommentEnd() {
		return "*/";
	}
	
	//Keywords that should be sorted as operators.
	public static boolean isOpKeyword(String ident) {
		switch(ident) {
			case "out":
				return true;
			case "error":
				return true;
			case "log":
				return true;
			case "assert":
				return true;
			case "bench":
				return true;
				
			case "if":
				return true;
			case "else":
				return true;
			case "elseif":
				return true;
			case "while":
				return true;
			case "iter":
				return true;
			case "func":
				return true;
				
			case "route":
				return true;
			case "header":
				return true;
		}
		return false;
	}
	
	//Symbols that make up a operator.
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
			case '#':
				return true;
		}
		return false;
	}
	
	//Identifiers that should be treated as an operator.
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
	
	//Get the sorted order value of a operator.
	public static int opValue(Token t) {
		if(t.type == TokenType.OP_KW)
			return -10000;
		switch(t.label) {
			case "(":
				return -1000;
				
			case "#":
				return 100;
				
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
					case "GET":
						return TString.create("GET");
					case "POST":
						return TString.create("POST");
						
					case "H_OK":
						return TString.create("HTTP/1.1 200 OK\r\n");
					case "H_HTTP":
						return TString.create("Content-Type: text/html\r\n");
					case "H_SERVER":
						return TString.create("Server: arclang HTTP\r\n");
					case "H_LENGTH":
						return TString.create("<LENGTH>");
				}
				return null;
			default:
				break;
		}
		return null;
	}
}
