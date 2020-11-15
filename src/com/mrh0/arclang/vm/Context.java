package com.mrh0.arclang.vm;

import java.io.PrintStream;
import java.util.Stack;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.ExecutionStackNotEmptyException;
import com.mrh0.arclang.type.IVal;

public class Context {
	public enum ChainControl {
		IGNORE, 		//Ignore all blocks.
		CONSUME, 		//Do block then return to IGNORE.
		PASS 			//Keep searching.
	}
	
	public final Stack<IVal> stack;
	public ChainControl chain;
	public PrintStream out;
	public boolean usesBlock = false;
	
	public Context() {
		stack = new Stack<IVal>();
		chain = ChainControl.IGNORE;
		out = System.out;
	}
	
	public void contextDebug() throws ArcException {
		if(stack.size() > 0) {
			throw new ExecutionStackNotEmptyException(stack.size());
		}
	}
}
