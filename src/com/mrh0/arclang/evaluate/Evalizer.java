package com.mrh0.arclang.evaluate;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.parse.statement.Statement;
import com.mrh0.arclang.parse.statement.StatementBlock;
import com.mrh0.arclang.parse.token.IToken;
import com.mrh0.arclang.parse.token.TokenVal;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TUndefined;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.Context.ChainControl;
import com.mrh0.arclang.vm.VM;

public class Evalizer {
	
	public static StatementResult evalStatement(IStatement statement, VM vm) throws ArcException {
		return evalStatement(statement, vm, new Context(), null);
	}
	
	public static StatementResult evalStatement(IStatement statement, VM vm, Context con, StatementResult last) throws ArcException {
		// Eval block.
		if(statement instanceof StatementBlock) {
			ChainControl tchain = con.chain;
			boolean tusesBlock = con.usesBlock;
			
			if((tchain == ChainControl.IGNORE && last != null) || tchain == ChainControl.PASS)
				return null;
			
			con.chain = ChainControl.IGNORE;
			//con.usesBlock = false;
			
			
			
			for(IStatement sub : ((StatementBlock)statement).getStatements()) {
				evalStatement(sub, vm, con, new StatementResult());
			}
			
			con.chain = tchain;
			con.usesBlock = tusesBlock;
			
			if(con.chain == ChainControl.CONSUME)
				con.chain = ChainControl.IGNORE;
			
			return null;
		}
		
		//Eval statement.
		con.usesBlock = false;
		Statement c = (Statement)statement;
		vm.exceptionManager.currentLine = c.line;
		for(int i = 0; i < c.length(); i++) {
			
			IToken t = c.getToken(i);
			switch(t.getType()) {
				case BLOCK:
					break;
				case END:
					break;
				case IDENT:
					break;
				case KW:
					keyword(t, con);
					break;
				case OP:
					con.stack.push(operate(t, con.stack.pop(), con.stack.pop()));
					break;
				case OP_SHORT:
					con.stack.push(operate(t, con.stack.pop()));
					break;
				case SEP:
					break;
				case VAL:
					con.stack.push(((TokenVal)t).value);
					break;
				default:
					break;
			}
		}
		//if(!con.usesBlock)
		//	con.chain = ChainControl.IGNORE;
		//if(con.chain == ChainControl.CONSUME)
		//	con.chain = ChainControl.IGNORE;
		
		if(con.stack.isEmpty()) {
			con.contextDebug();
			return new StatementResult(TUndefined.getInstance());
		}
		IVal result = con.stack.pop();
		con.contextDebug();
		return new StatementResult(result);
	}
	
	public static IVal operate(IToken op, IVal left) throws ArcException {
		switch(op.getLabel()) {
			case "!":
				return left.logicalNot();
			case "not":
				return left.logicalNot();
		}
		return null;
	}
	
	public static IVal operate(IToken op, IVal right, IVal left) throws ArcException {
		switch(op.getLabel()) {
			case "+":
				return left.add(right);
			case "-":
				return left.sub(right);
			case "*":
				return left.mul(right);
			case "/":
				return left.div(right);
			case "%":
				return left.mod(right);
				
			case "and":
				return left.logicalAnd(right);
			case "or":
				return left.logicalOr(right);
			case "xor":
				return left.logicalXor(right);
		}
		return null;
	}
	
	public static void keyword(IToken kw, Context con) {
		switch(kw.getLabel()) {
			case "log":
				System.out.println("[LOG]:" + con.stack.peek());
				break;
			case "out":
				con.out.print(con.stack.peek());
				break;
			case "if":
				con.usesBlock = true;
				if(con.stack.pop().booleanValue())
					con.chain = ChainControl.CONSUME;
				else
					con.chain = ChainControl.PASS;
				break;
			case "else":
				con.usesBlock = true;
				if(con.chain == ChainControl.PASS)
					con.chain = ChainControl.CONSUME;
				/*if(con.chain == ChainControl.PASS)
					break;
				if(con.stack.isEmpty()) {
					con.chain = ChainControl.CONSUME;
				}
				else {
					if(con.stack.pop().booleanValue())
						con.chain = ChainControl.PASS;
					else
						con.chain = ChainControl.FAIL;
				}*/
				break;
		}
	}
}
