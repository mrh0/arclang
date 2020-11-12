package com.mrh0.arclang.type;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.exception.ParseException;

public class TNumber implements IVal{

	private final double value;
	
	private TNumber(double v) {
		this.value = v;
	}
	
	private TNumber(boolean v) {
		this.value = v?1:0;
	}
	
	@Override
	public String getTypeName() {
		return "number";
	}
	
	public double get() {
		return value;
	}
	
	public int getIntegerValue() {
		return (int)value;
	}
	
	public static TNumber from(IVal v) throws ArcException {
		if(!(v instanceof TNumber))
			throw new CastException(v, "number");
		return (TNumber) v;
	}
	
	public static TNumber create(double v) {
		return new TNumber(v);
	}
	
	public static TNumber create(int v) {
		return new TNumber(v);
	}
	
	public static TNumber create(float v) {
		return new TNumber(v);
	}
	
	public static TNumber create(boolean v) {
		return new TNumber(v);
	}
	
	public static IVal fromString(String value) throws ArcException {
		try {
			return create(Double.parseDouble(value));
		}
		catch(NumberFormatException e) {
			throw new ParseException(value);
		}
	}
	
	@Override
	public String toString() {
		return ""+value;
	}
	
	@Override
	public IVal add(IVal v) throws ArcException {
		if(v instanceof TNumber)
			return TNumber.create(value + from(v).get());
		return TUndefined.getInstance();
	}
}
