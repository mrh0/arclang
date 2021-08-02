package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TString;

public abstract class TIterator implements IVal, Iterator<IVal>{

	@Override
	public String getTypeName() {
		return "iterator";
	}
}
