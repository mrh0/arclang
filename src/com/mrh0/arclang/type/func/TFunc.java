package com.mrh0.arclang.type.func;

import com.mrh0.arclang.evaluate.Evalizer;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.accessor.AccessorTypeException;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TList;
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
	public IVal execute(Arguments args, VM vm, Context context) throws ArcException {
		return Evalizer.evalStatement(first, vm, context, null, null);
	}
	
	@Override
	public String toString() {
		return getTypeName();
	}
	
	@Override
	public IVal accessor(IVal key, VM vm, Context context) throws ArcException {
		if(key instanceof TList) {
			return execute(new Arguments((TList)key), vm, context);
		}
		throw new AccessorTypeException(this, key);
	}
}
