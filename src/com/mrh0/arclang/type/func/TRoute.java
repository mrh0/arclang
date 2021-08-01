package com.mrh0.arclang.type.func;

import com.mrh0.arclang.evaluate.Evalizer;
import com.mrh0.arclang.evaluate.StatementResult;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.service.HttpMethod;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class TRoute implements IFunc {
	
	public final IStatement first;
	public final HttpMethod method;
	
	public TRoute(HttpMethod method, IStatement first) {
		this.method = method;
		this.first = first;
	}
	
	@Override
	public String getTypeName() {
		return "route";
	}

	@Override
	public IVal execute(Arguments args, VM vm, Context context) throws ArcException {
		return Evalizer.evalStatement(first, vm, context, null, null);
	}
	
	public HttpMethod getMethod() {
		return method;
	}
	
	public IStatement getStatement() {
		return first;
	}
	
	@Override
	public String toString() {
		return getTypeName();
	}
}
