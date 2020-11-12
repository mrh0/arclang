package com.mrh0.arclang.type;

public class TUndefined implements IVal{
private static TUndefined instance = null;
	
	public TUndefined() {
		if(instance == null)
			instance = this;
	}

	@Override
	public String getTypeName() {
		return "undefined";
	}
	
	@Override
	public boolean isUndefined() {
		return true;
	}
	
	public static TUndefined getInstance() {
		if(instance == null)
			return new TUndefined();
		return instance;
	}
	
	@Override
	public boolean booleanValue() {
		return false;
	}
}
