package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.type.IVal;

public abstract class TIterator implements IVal, Iterator<IVal> {

	@Override
	public String getTypeName() {
		return "iterator";
	}
	
	@Override
	public boolean isIterable() {
		return true;
	}
	
	@Override
	public boolean isKeyIterable() {
		return true;
	}
	
	public abstract IVal nextKey();
}
