package com.mrh0.arclang.type;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.CastException;

public class TString implements IVal{
	
	private final String value;

	private TString(String str) {
		value = str;
	}
	
	@Override
	public String getTypeName() {
		return "string";
	}

	public String getValue() {
		return value;
	}
	
	public static TString from(IVal v) throws ArcException {
		if(!(v instanceof TString))
			throw new CastException(v, "string");
		return (TString) v;
	}
	
	public static TString create(String v) {
		return new TString(v);
	}
	
	public static IVal fromString(String value) {
		return create(value);
	}
	
	@Override
	public String toString() {
		return value;
	}
	
	@Override
	public boolean booleanValue() {
		return value.length() > 0;
	}
}
