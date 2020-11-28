package com.mrh0.arclang.type.func;

import com.mrh0.arclang.evaluate.Evalizer;
import com.mrh0.arclang.evaluate.StatementResult;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class TFunc implements IFunc {
	public final IStatement first;
	
	public TFunc(IStatement first) {
		this.first = first;
	}

	@Override
	public String getTypeName() {
		return "function";
	}

	@Override
	public StatementResult execute(Arguments args, VM vm, Context context) throws ArcException {
		return Evalizer.evalStatement(first, vm, context, null, null);
	}
	
	@Override
	public String toString() {
		return getTypeName();
	}
}
