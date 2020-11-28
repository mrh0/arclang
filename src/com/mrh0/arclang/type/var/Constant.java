package com.mrh0.arclang.type.var;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.ModificationException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TUndefined;
import com.mrh0.arclang.vm.Variables;

public class Constant extends Var {

	private boolean set = false;
	
	public Constant(String name, IVal value) {
		super(name, value);
	}
	
	public Constant(String name) {
		super(name, TUndefined.getInstance());
	}

	@Override
	public IVal assign(IVal v, Variables vars) throws ArcException {
		if(set)
			throw new ModificationException(" '"+getName()+"' is a constant variable.");
		return super.assign(v, vars);
	}
	
	@Override
	public IVal walrusAssign(IVal v, Variables vars) throws ArcException {
		if(set)
			throw new ModificationException(" '"+getName()+"' is a constant variable.");
		return super.walrusAssign(v, vars);
	}
}
