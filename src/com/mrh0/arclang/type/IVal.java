package com.mrh0.arclang.type;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.OperationException;
import com.mrh0.arclang.exception.ParseException;
import com.mrh0.arclang.exception.accessor.AccessorTypeException;
import com.mrh0.arclang.type.var.Var;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;
import com.mrh0.arclang.vm.Variables;

public interface IVal {
	
	public default boolean booleanValue() {
		return true;
	}
	
	public String getTypeName();
	
	public default boolean isUndefined() {
		return false;
	}
	
	public default boolean isNumber() {
		return false;
	}
	
	public default boolean isString() {
		return false;
	}
	
	public default boolean isObject() {
		return false;
	}
	
	public default boolean isList() {
		return false;
	}
	
	public default boolean isFunction() {
		return false;
	}
	
	//Arithmetic Operations:
	public default IVal add(IVal v) throws ArcException {
		throw new OperationException("add", this, v);
	}
	
	public default IVal sub(IVal v) throws ArcException {
		throw new OperationException("subtract", this, v);
	}
	
	public default IVal mul(IVal v) throws ArcException {
		throw new OperationException("multiply", this, v);
	}
	
	public default IVal div(IVal v) throws ArcException {
		throw new OperationException("divide", this, v);
	}
	
	public default IVal mod(IVal v) throws ArcException {
		throw new OperationException("modulo", this, v);
	}
	
	public default IVal pow(IVal v) throws ArcException {
		throw new OperationException("powerof", this, v);
	}
	
	public default IVal as(IVal v) throws ArcException {
		throw new OperationException("as", this, v);
	}
	
	public default IVal is(IVal v) throws ArcException {
		throw new OperationException("is", this, v);
	}
	
	public default IVal greaterThan(IVal v) throws ArcException {
		return TNumber.create(getComparableValue() > v.getComparableValue());
	}
	
	public default IVal lessThan(IVal v) throws ArcException {
		return TNumber.create(getComparableValue() < v.getComparableValue());
	}
	
	public default IVal equals(IVal v) throws ArcException {
		return TNumber.create(getComparableValue() == v.getComparableValue());
	}
	
	public default IVal notEquals(IVal v) throws ArcException {
		return TNumber.create(!equals(v).booleanValue());
	}
	
	public default IVal greaterThanOrEquals(IVal v) throws ArcException {
		return TNumber.create(getComparableValue() >= v.getComparableValue());
	}
	
	public default IVal lessThanOrEquals(IVal v) throws ArcException {
		return TNumber.create(getComparableValue() <= v.getComparableValue());
	}
	
	public default double getComparableValue() {
		return hashCode();
	}
	
	public default IVal assign(IVal v, Variables vars) throws ArcException {
		throw new OperationException("assign", this, v);
	}
	
	public default IVal walrusAssign(IVal v, Variables vars) throws ArcException {
		throw new OperationException("walrus", this, v);
	}
	
	public default IVal addAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.add(v), vars);
	}
	
	public default IVal subAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.sub(v), vars);
	}
	
	public default IVal mulAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.mul(v), vars);
	}
	
	public default IVal divAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.div(v), vars);
	}
	
	public default IVal modAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.mod(v), vars);
	}
	
	public default IVal powAssign(IVal v, Variables vars) throws ArcException {
		return this.assign(this.pow(v), vars);
	}
	
	public default IVal logicalAnd(IVal v) throws ArcException {
		return TNumber.create(this.booleanValue() && v.booleanValue());
	}
	
	public default IVal logicalOr(IVal v) throws ArcException {
		return this.booleanValue()?this:v;
	}
	
	public default IVal logicalXor(IVal v) throws ArcException {
		return this.booleanValue()?(v.booleanValue()?TNumber.create(false):this):v;
	}
	
	public default IVal logicalNot() throws ArcException {
		return TNumber.create(!this.booleanValue());
	}
	
	public static IVal fromString(String value) throws ArcException {
		throw new ParseException(value);
	}
	
	public default IVal accessor(IVal key, VM vm, Context context) throws ArcException {
		throw new AccessorTypeException(this);
	}
	
	public static IVal get(IVal val) {
		if(val instanceof Var)
			return ((Var) val).getValue();
		return val;
	}
}
