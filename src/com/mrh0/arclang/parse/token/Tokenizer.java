package com.mrh0.arclang.parse.token;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.ExceptionManager.ExecutionPhase;
import com.mrh0.arclang.exception.UnexpectedSymbolException;
import com.mrh0.arclang.util.StringUtil;
import com.mrh0.arclang.vm.VM;

public class Tokenizer {
	// Current token type.
	private TokenType t = TokenType.NONE;
	// Current token label.
	private StringBuilder s = new StringBuilder();
	// Token list.
	private ArrayList<Token> l = new ArrayList<Token>();
	// Postfix list.
	private List<Token> postfix = new ArrayList<Token>();
	
	private List<Integer> lineIndecies = new ArrayList<Integer>();
	
	private final VM vm;
	
	public Tokenizer(VM vm) {
		this.vm = vm;
	}
	
	public List<Token> tokenize(String code) throws ArcException {
		vm.exceptionManager.setPhase(ExecutionPhase.PARSE);
		vm.exceptionManager.setCode(code, lineIndecies);
		code = preprocess(code);
		
		for(int i = 0; i < code.length(); i++)
			classify(code.charAt(i));
		
		vm.exceptionManager.setPhase(ExecutionPhase.POSTFIXIFY);
		blockEndCheck();
		System.out.println(l);
		postfix = postfixify(0, null);
		cleanup();
		System.out.println(postfix);
		return postfix;
	}
	
	/* Tokenize step #1.
	 * Removes comments from raw code. 
	 * TODO: Check parentheses balance. */
	public String preprocess(String code) {
		code = code + "\r\n\0";
		StringBuilder s = new StringBuilder();
		boolean inLineComment = false;
		boolean inBlockComment = false;
		
		lineIndecies.add(0);
		
		for(int i = 1; i < code.length(); i++) {
			char c0 = code.charAt(i-1);
			char c1 = code.charAt(i);
			String ob = (""+c0)+c1;
			
			if(Tokens.isLineEnd(c0))
				lineIndecies.add(i);

			
			if(ob.equals(Tokens.getLineComment()) && !inBlockComment)
				inLineComment = true;
			else if(ob.equals(Tokens.getBlockCommentStart()) && !inLineComment)
				inBlockComment = true;
			else if(ob.equals(Tokens.getBlockCommentEnd())) {
				inBlockComment = false;
				i++;
				continue;
			}
			
			if(inLineComment) {
				if(Tokens.isLineEnd(c0)) { 
					inLineComment = false;
					s.append(c0);
				}
				continue;
			}
			else if(inBlockComment) {
				if(Tokens.isLineEnd(c0))
					s.append(c0);
				continue;
			}
			s.append(c0);
		}
		return s.toString();
	}
	
	/* Tokenize step #2.
	 * Classify each char and assign them to tokens. */
	private void classify(char c) {
		if(c == '\"') {
			TokenType lt = t;
			end();
			if(lt != TokenType.STR) {
				push(TokenType.STR);
			}
			return;
		}
		else if(t == TokenType.STR) {
			push(t, c);
			return;
		}
		else if(Tokens.isWhitespace(c)) {
			end();
			return;
		}
		else if(Tokens.isDefiniteEnd(c)) {
			end();
			push(TokenType.D_END, ';');
			end();
			return;
		}
		else if(Tokens.isLineEnd(c)) {
			end();
			push(TokenType.END, ';');
			end();
			push(TokenType.LN, '\n');
			end();
			return;
		}
		else if(Tokens.isBlock(c)) {
			end();
			push(TokenType.BLOCK, ':');
			end();
			return;
		}
		else if(Tokens.isSep(c)) {
			if(c == '[') {
				if(t == TokenType.IDENT) {
					end();
					push(TokenType.OP, '#');
					end();
				}
			}
					
			end();
			push(TokenType.SEP, c);
			end();
			return;
		}
		else if(Tokens.isOp(c)) {
			if(t != TokenType.OP)
				end();
			push(TokenType.OP, c);
			return;
		}
		else if(t == TokenType.OP) {
			end();
		}
		else if(t == TokenType.IDENT) {
			push(TokenType.IDENT, c);
			return;
		}
		else if(t == TokenType.NUM) {
			push(TokenType.NUM, c);
			return;
		}
		if(Tokens.isNumber(c)) {
			push(TokenType.NUM, c);
			return;
		}
		push(TokenType.IDENT, c);
		return;
	}
	
	
	/* Determines if a completed token should change type. */
	private TokenType firstCheck(String token, TokenType type) {
		if(type == TokenType.IDENT) {
			if(Tokens.isIdentOp(token))
				return TokenType.OP;
			if(Tokens.isOpKeyword(token))
				return TokenType.OP_KW;
		}
		return type;
	}
	
	private void blockEndCheck() {
		for(int i = 0; i < l.size(); i++) {
			IToken t = l.get(i);
			if(t.getLabel().equals("else") || t.getLabel().equals("elseif")) {
				l.add(i++, new Token("end", TokenType.IDENT));
				l.add(i++, new Token(";", TokenType.END));
			}
		}
	}
	
	/* Add char and sets type to current token. */
	private void push(TokenType t, char c) {
		this.t = t;
		s.append(c);
	}
	
	/* Sets current type. */
	private void push(TokenType t) {
		this.t = t;
	}
	
	/* End current token. */
	private void end() {
		if(s.length() == 0 && t != TokenType.STR)
			return;
		String r = s.toString();
		s = new StringBuilder();
		
		t = firstCheck(r, t);
		
		l.add(new Token(r, t));
		
		t = TokenType.NONE;
	}
	
	private Stack<Token> brackStack = new Stack<Token>();
	
	/* Tokenize step #3.
	 * Transforms array of tokens into postfix order. */
	public List<Token> postfixify(int start, Token escape) throws ArcException {
		// Postfix list.
		ArrayList<Token> p = new ArrayList<Token>();
		// Postfix stack.
		Stack<Token> op = new Stack<Token>();
		
		int line = 1;
		
		for(int i = start;i < l.size(); i++) {
			Token t = l.get(i);
			//Checks for the escape token.
			if(escape != null && t.type == escape.type && t.label.equals(escape.label)) {
				bracketCheck(t);
				while(!op.isEmpty())
					p.add(op.pop());
				p.add(t);
				return p;
			}
			if(t.isIdentifier() || t.isNumber() || t.isString())
				p.add(t);
			//Bracket before
			else if(t.isLnEnd()) {
				p.add(new Token("\n", TokenType.LN));
				line++;
				vm.exceptionManager.currentLine = line;
			}
			else if(t.isStatementEnd() || t.isBlock()) {
				if(t.type == TokenType.D_END && brackStack.size() > 0) {
					throw new UnexpectedSymbolException("end of statement.");
				}
				else if(t.type == TokenType.END && brackStack.size() > 0) {
					continue;
				}
				while(!op.isEmpty())
					p.add(op.pop());
				p.add(t);
			}
			else if(t.isSeparator()) {
				//Handles array/accessor blocks (also object blocks later).
				
				bracketCheck(t);
				
				if(t.isOpenBracket()) {
					op.push(t);
					continue;
				}
				else if(t.isCloseBracket()) {
					Token top = op.pop();
					while(!top.isOpenBracket()) {
						p.add(top);
						top = op.pop();
					}
					continue;
				}
				
				if(t.label.equals("[")) {
					p.add(t);
					List<Token> rp = postfixify(i+1, new Token("]",TokenType.SEP));
					p.addAll(rp);
					i+=rp.size();
					continue;
				}
				
				while(!op.isEmpty())
					p.add(op.pop());
				p.add(t);
			}
			else {
				while(!op.isEmpty() && (Tokens.opValue(op.peek()) >= Tokens.opValue(t))) {
					p.add(op.pop());
				}
				op.push(t);
			}
		}
		while(!op.isEmpty())
			p.add(op.pop());
		return p;
	}
	
	private void bracketCheck(Token t) throws ArcException {
		if(t.isAnyOpenBracket()) {
			brackStack.push(t);
		}
		else if(t.isAnyCloseBracket()) {
			char opener = Tokens.getOpener(t.label.charAt(0));
			if(brackStack.isEmpty()) {
				throw new ArcException("[DEV] brackets unbalanced no open bracket for '"+t.label.charAt(0)+"'.");
			}
			char tbc = brackStack.pop().label.charAt(0);
			
			if(opener != tbc) {
				throw new ArcException("[DEV] brackets unbalanced expected: '"+Tokens.getCloser(tbc)+"' got '"+tbc+"'.");
			}
		}
	}
	
	/* Tokenize step #4
	 * Final pass. */
	public void cleanup() throws UnexpectedSymbolException {
		for(int i = 0; i < postfix.size(); i++) {
			IToken t = postfix.get(i);
			TokenType type = finalCheck(t);
			if(t.getType() != type)
				postfix.set(i, new Token(t.getLabel(), type));
			if(t.getLabel().equals("func")) {
				if(i-1 < 0)
					throw new UnexpectedSymbolException(t);
				if(postfix.get(i-1).getLabel().equals("#")) {
					postfix.remove(i-1);
					i++;
				}
			}
		}
	}
	
	/* Determines if a completed token should change type again. */
	private TokenType finalCheck(IToken t) {
		if(t.getType() == TokenType.OP) {
			switch(t.getLabel()) {
				case "!":
					return TokenType.OP_SHORT;
				case "not":
					return TokenType.OP_SHORT;
				case "~":
					return TokenType.OP_SHORT;
			}
		}
		else if(t.getType() == TokenType.OP_KW)
			return TokenType.KW;
		return t.getType();
	}
}
