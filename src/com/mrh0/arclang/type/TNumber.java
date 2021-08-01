package com.mrh0.arclang.type;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.exception.ParseException;
import com.mrh0.arclang.vm.Variables;

public class TNumber implements IVal{

	private double value;
	
	private TNumber(double v) {
		this.value = v;
	}
	
	private TNumber(boolean v) {
		this.value = v?1:0;
	}
	
	@Override
	public String getTypeName() {
		return getName();
	}
	
	public static String getName() {
		return "number";
	}
	
	public double get() {
		return value;
	}
	
	public int getIntegerValue() {
		return (int)value;
	}
	
	public static TNumber from(IVal v) throws ArcException {
		v = IVal.get(v);
		if(!v.isNumber())
			throw new CastException(v, getName());
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
	
	public static TNumber True() {
		return new TNumber(true);
	}
	
	public static TNumber False() {
		return new TNumber(false);
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
		/*if(v.isNumber()) {
			value += from(v).get();
			return this;
		}
		if(v.isString())
			return TString.create(value + TString.from(v).getValue());*/
		
		if(v.isNumber())
			return TNumber.create(value + from(v).get());
		if(v.isString())
			return TString.create(value + TString.from(v).getValue());
		throw new CastException(v, this);
	}
	
	@Override
	public IVal sub(IVal v) throws ArcException {
		if(v.isNumber())
			return TNumber.create(value - from(v).get());
		throw new CastException(v, this);
	}
	
	@Override
	public IVal mul(IVal v) throws ArcException {
		if(v.isNumber())
			return TNumber.create(value * from(v).get());
		throw new CastException(v, this);
	}
	
	@Override
	public IVal div(IVal v) throws ArcException {
		if(v.isNumber())
			return TNumber.create(value / from(v).get());
		throw new CastException(v, this);
	}
	
	@Override
	public double getComparableValue() {
		return get();
	}
	
	@Override
	public boolean booleanValue() {
		return value > 0;
	}
	
	@Override
	public boolean isNumber() {
		return true;
	}
	
	public boolean equals(double num) {
		return value == num;
	}
}
