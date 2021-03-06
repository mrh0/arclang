package com.mrh0.arclang.type.var;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TUndefined;
import com.mrh0.arclang.vm.Variables;

public class Var implements IVal {

	private IVal value;
	private final String name;
	
	public Var(String name) {
		this.name = name;
		value = TUndefined.getInstance();
	}
	
	public Var(String name, IVal value) {
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}
	
	public IVal getValue() {
		return value;
	}
	
	@Override
	public String getTypeName() {
		return value.getTypeName();
	}

	public boolean isUndefined() {
		return value.isUndefined();
	}
	
	public boolean isNumber() {
		return value.isNumber();
	}
	
	public boolean isString() {
		return value.isString();
	}
	
	public boolean isObject() {
		return value.isObject();
	}
	
	public boolean isList() {
		return value.isList();
	}
	
	public boolean isFunction() {
		return value.isFunction();
	}
	
	@Override
	public IVal add(IVal v) throws ArcException {
		return value.add(v);
	}
	
	@Override
	public IVal sub(IVal v) throws ArcException {
		return value.sub(v);
	}
	
	@Override
	public IVal mul(IVal v) throws ArcException {
		return value.mul(v);
	}
	
	@Override
	public IVal div(IVal v) throws ArcException {
		return value.div(v);
	}
	
	@Override
	public IVal mod(IVal v) throws ArcException {
		return value.mod(v);
	}
	
	@Override
	public IVal pow(IVal v) throws ArcException {
		return value.pow(v);
	}
	
	@Override
	public IVal as(IVal v) throws ArcException {
		return value.as(v);
	}
	
	@Override
	public IVal is(IVal v) throws ArcException {
		return value.is(v);
	}
	
	@Override
	public IVal logicalAnd(IVal v) throws ArcException {
		return value.logicalAnd(v);
	}
	
	@Override
	public IVal logicalOr(IVal v) throws ArcException {
		return value.logicalOr(v);
	}
	
	@Override
	public IVal logicalXor(IVal v) throws ArcException {
		return value.logicalXor(v);
	}
	
	@Override
	public IVal logicalNot() throws ArcException {
		return value.logicalNot();
	}
	
	@Override
	public IVal accessor(IVal key) throws ArcException {
		return value.accessor(key);
	}
	
	@Override
	public IVal assign(IVal v, Variables vars) throws ArcException {
		this.value = v;
		vars.set(this);
		return this;
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public boolean booleanValue() {
		return value.booleanValue();
	}
}
