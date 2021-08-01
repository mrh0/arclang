package com.mrh0.arclang.evaluate;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.AssertionException;
import com.mrh0.arclang.exception.ExecutionStackNotEmptyException;
import com.mrh0.arclang.exception.SyntaxException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.parse.statement.Statement;
import com.mrh0.arclang.parse.statement.StatementBlock;
import com.mrh0.arclang.parse.token.IToken;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.parse.token.TokenVal;
import com.mrh0.arclang.parse.token.TokenVarCache;
import com.mrh0.arclang.service.HttpMethod;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TList;
import com.mrh0.arclang.type.TNumber;
import com.mrh0.arclang.type.TString;
import com.mrh0.arclang.type.TUndefined;
import com.mrh0.arclang.type.func.TFunc;
import com.mrh0.arclang.type.func.TRoute;
import com.mrh0.arclang.type.iter.TRangeIterator;
import com.mrh0.arclang.type.var.Var;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.Context.ChainControl;
import com.mrh0.arclang.vm.VM;
import com.mrh0.arclang.vm.Variables;

public class Evalizer {
	
	public static IVal evalStatement(IStatement statement, VM vm) throws ArcException {
		return evalStatement(statement, vm, new Context(), null, null);
	}
	
	public static IVal evalRootStatement(IStatement statement, VM vm) throws ArcException {
		return evalStatement(statement, vm, new Context(true), null, null);
	}
	
	public static IVal evalStatement(IStatement statement, VM vm, Context con, IVal last/*StatementResult last*/, IStatement next) throws ArcException {
		// Eval block.
		if(statement instanceof StatementBlock) {
			IStatement[] list = ((StatementBlock)statement).getStatements();
			if(list.length == 0)
				return null;
			ChainControl tchain = con.chain;
			boolean tusesBlock = con.usesBlock;
			
			if((tchain == ChainControl.IGNORE && last != null) || tchain == ChainControl.PASS)
				return null;
			
			con.chain = ChainControl.IGNORE;
			
			for(int i = 0; i < list.length; i++) {
				evalStatement(list[i], vm, con, TUndefined.getInstance(), (i+1 < list.length ? list[i+1] : null));
			}
			
			con.chain = tchain;
			con.usesBlock = tusesBlock;
			
			if(con.chain == ChainControl.CONSUME)
				con.chain = ChainControl.IGNORE;
			
			return null;
		}
		
		Stack<List<IVal>> blockBuilder = null;
		
		//Eval statement.
		con.usesBlock = false;
		Statement c = (Statement)statement;
		vm.exceptionManager.currentLine = c.line;
		IToken lt = null;
		for(int i = 0; i < c.length(); i++) {
			IToken t = c.getToken(i);
			switch(t.getType()) {
				case BLOCK:
					break;
				case END:
					break;
				case IDENT:
					// No notable performance gain :/
					if(t.getValue() == null) {
						Var v = con.local.getOrDefault(t.getLabel(), vm.globals);
						vm.stack.push(v);
						c.setToken(i, new TokenVarCache(t, v));
						//System.out.println("Cache:" + t.getLabel());
						break;
					}
					vm.stack.push(t.getValue());
					break;
				case KW:
					if(keyword(t, vm, con, next))
						i = -1;
					break;
				case OP:
					vm.stack.push(operate(t, vm.stack.pop(), vm.stack.pop(), vm, con));
					break;
				case OP_SHORT:
					vm.stack.push(operate(t, vm.stack.pop(), vm, con));
					break;
				case SEP:
					Token tt = (Token)t;
					if(tt.isOpenAccessorBracket()) {
						if(blockBuilder == null)
							blockBuilder = new Stack<List<IVal>>();
						blockBuilder.add(new ArrayList<IVal>());
					}
					else if(tt.isCloseAccessorBracket()) {
						if(lt != null && !lt.getLabel().equals("["))
							blockBuilder.peek().add(vm.stack.pop());
						vm.stack.push(new TList(blockBuilder.pop()));
					}
					else if(tt.isComma()) {
						blockBuilder.peek().add(vm.stack.pop());
					}
					break;
				case VAL:
					vm.stack.push(((TokenVal)t).value);
					break;
				default:
					break;
			}
			lt = t;
		}
		if(vm.stack.isEmpty()) {
			vm.debug();
			return TUndefined.getInstance();// StatementResult();
		}
		
		IVal result = vm.stack.pop();
		if(result instanceof Var)
			getAssignVariables(vm, con).set((Var) result);
		
		if(vm.stack.size() > 0)
			throw new ExecutionStackNotEmptyException(vm.stack.size());
		vm.debug();
		return result;//new StatementResult(result);
	}
	
	public static IVal operate(IToken op, IVal left, VM vm, Context con) throws ArcException {
		switch(op.getLabel()) {
			case "!":
				return left.logicalNot();
			case "not":
				return left.logicalNot();
		}
		throw new SyntaxException("operator", op.getLabel());
	}
	
	public static IVal operate(IToken op, IVal right, IVal left, VM vm, Context con) throws ArcException {
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
			
			case ">":
				return left.greaterThan(right);
			case "<":
				return left.lessThan(right);
			case ">=":
				return left.greaterThanOrEquals(right);
			case "<=":
				return left.lessThanOrEquals(right);
			case "==":
				return left.equals(right);
			case "!=":
				return left.notEquals(right);
			case "=":
				return left.assign(right, getAssignVariables(vm, con));
			case ":=":
				return left.walrusAssign(right, getAssignVariables(vm, con));
			case "+=":
				return left.addAssign(right, getAssignVariables(vm, con));
			case "-=":
				return left.subAssign(right, getAssignVariables(vm, con));
			case "*=":
				return left.mulAssign(right, getAssignVariables(vm, con));
			case "/=":
				return left.divAssign(right, getAssignVariables(vm, con));
			case "%=":
				return left.modAssign(right, getAssignVariables(vm, con));
			case "^=":
				return left.powAssign(right, getAssignVariables(vm, con));
			case "..":
				return TRangeIterator.create(TNumber.from(left).getIntegerValue(), TNumber.from(right).getIntegerValue());
				
			case "#":
				return left.accessor(right, vm, con);
		}
		throw new SyntaxException("operator", op.getLabel());
	}
	
	public static boolean keyword(IToken kw, VM vm, Context con, IStatement next) throws ArcException {
		switch(kw.getLabel()) {
			case "log":
				if(vm.stack.isEmpty())
					throw new SyntaxException("value");
				System.out.println("[Log:"+vm.exceptionManager.currentLine+"]:" + vm.stack.peek());
				break;
			case "assert":
				if(vm.stack.isEmpty())
					throw new SyntaxException("boolean assertion");
				if(!vm.stack.pop().booleanValue())
					throw new AssertionException(vm.exceptionManager.getLine());
				break;
			case "out":
				if(vm.stack.isEmpty())
					throw new SyntaxException("value");
				con.out.print(vm.stack.peek());
				break;
				
			case "if":
				if(vm.stack.isEmpty())
					throw new SyntaxException("boolean branch condition");
				con.usesBlock = true;
				if(vm.stack.pop().booleanValue())
					con.chain = ChainControl.CONSUME;
				else
					con.chain = ChainControl.PASS;
				break;
			case "else":
				con.usesBlock = true;
				if(con.chain == ChainControl.PASS)
					con.chain = ChainControl.CONSUME;
				break;
			case "elseif":
				if(vm.stack.isEmpty())
					throw new SyntaxException("boolean branch condition");
				con.usesBlock = true;
				if(vm.stack.pop().booleanValue() && con.chain == ChainControl.PASS)
					con.chain = ChainControl.CONSUME;
				break;
			case "while":
				if(vm.stack.isEmpty())
					throw new SyntaxException("boolean loop condition");
				if(vm.stack.pop().booleanValue()) {
					evalStatement(next, vm, con, null, null);
					con.chain = ChainControl.IGNORE;
					return true;
				}
				else
					con.chain = ChainControl.PASS;
				break;
				
			case "bench":
				long start = System.nanoTime();
				evalStatement(next, vm, con, null, null);
				con.chain = ChainControl.IGNORE;
				System.out.println("[Benchmark:"+vm.exceptionManager.currentLine+"]:" + (Math.round(((double)(System.nanoTime()-start))/100000d)/10d) + "ms");
				break;
				
			case "func":
				con.chain = ChainControl.IGNORE;
				IVal args = vm.stack.pop();
				if(!(args instanceof TList))
					throw new SyntaxException("arguments", args.getTypeName());
				vm.stack.pop().walrusAssign(new TFunc(next), getAssignVariables(vm, con));
				break;
				
			case "route":
				con.chain = ChainControl.IGNORE;
				String route = TString.stringFrom(vm.stack.pop());
				HttpMethod method = HttpMethod.getMethod(TString.stringFrom(vm.stack.pop()));
				vm.routes.addRoute(new TRoute(method, next), route);
				break;
		}
		return false;
	}
	
	public static Variables getAssignVariables(VM vm, Context context) {
		if(context.root)
			return vm.globals;
		return context.local;
	} 
}
