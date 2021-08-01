package com.mrh0.arclang.vm;

import java.util.Stack;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.ExceptionManager;
import com.mrh0.arclang.exception.ExecutionStackNotEmptyException;
import com.mrh0.arclang.service.route.RouteTree;
import com.mrh0.arclang.type.IVal;

public class VM {
	public final ExceptionManager exceptionManager;
	public final Variables globals;
	public final RouteTree routes;
	public final Stack<IVal> stack;
	
	public VM() {
		stack = new Stack<IVal>();
		exceptionManager = new ExceptionManager();
		globals = new Variables();
		routes = new RouteTree("$ROOT", null);
	}
	
	public void debug() throws ArcException {
		
	}
}
