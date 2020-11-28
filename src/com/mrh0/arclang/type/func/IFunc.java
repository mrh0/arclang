package com.mrh0.arclang.type.func;

import com.mrh0.arclang.evaluate.StatementResult;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public interface IFunc extends IVal{
	public StatementResult execute(Arguments args, VM vm, Context context) throws ArcException;
	
	@Override
	public default boolean isFunction() {
		return true;
	}
}
