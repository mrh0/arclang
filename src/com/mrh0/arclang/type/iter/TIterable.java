package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.type.IVal;

public abstract class TIterable implements IVal, Iterable<IVal>, KeyIterable<IVal> {

	@Override
	public String getTypeName() {
		return "iterable";
	}
	
	public static TIterable from(IVal v) throws CastException {
		v = IVal.get(v);
		if(!(v instanceof TIterable))
			throw new CastException(v, "iterable");
		return (TIterable) v;
	}
	
	@Override
	public Iterator<IVal> keyIterator() {
		return null;
	}
}
