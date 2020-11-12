package com.mrh0.arclang.evaluate;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.parse.token.IToken;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class Evalizer {
	public static StatementResult evalStatement(IStatement statement, VM vm, Context con) {
		
		return null;
	}
	
	public IVal operate(IToken op, IVal left) throws ArcException {
		switch(op.getLabel()) {
			case "!":
				return left.logicalNot();
			case "not":
				return left.logicalNot();
		}
		return null;
	}
	
	public IVal operate(Token op, IVal left, IVal right) throws ArcException {
		switch(op.getLabel()) {
			case "+":
				return left.add(right);
			case "-":
				return left.sub(right);
			case "*":
				return left.mul(right);
			case "/":
				return left.div(right);
		}
		return null;
	}
}
