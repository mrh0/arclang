package com.mrh0.arclang.type;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.exception.EscapeCharacterException;

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
	
	public static TString from(IVal v) throws CastException {
		v = IVal.get(v);
		if(!(v instanceof TString))
			throw new CastException(v, "string");
		return (TString) v;
	}
	
	public static String stringFrom(IVal v) throws CastException {
		if(!(v instanceof TString))
			throw new CastException(v, "string");
		return v.toString();
	}
	
	public static TString create(String v) throws EscapeCharacterException {
		return new TString(format(v));
	}
	
	public static IVal fromString(String value) throws EscapeCharacterException {
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
	
	public static char getEscapeChar(char escape) throws EscapeCharacterException {
		switch(escape) {
			case 't': return '\t';
			case 'b': return '\b';
			case 'n': return '\n';
			case 'r': return '\r';
			case 'f': return '\f';
			case '\'': return '\'';
			case '"': return '\"';
			case '\\': return '\\';
		}
		throw new EscapeCharacterException(escape);
	}
	
	public static String format(String str) throws EscapeCharacterException {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if(c == '\\') {
				if(i+1 < str.length()) {
					sb.append(getEscapeChar(str.charAt(++i)));
					continue;
				}
				throw new EscapeCharacterException();
			}
			sb.append(c);
		}
		return sb.toString();
	}
	
	@Override
	public boolean isString() {
		return true;
	}
	
	@Override
	public IVal add(IVal v) throws ArcException {
		return create(value+v.toString());
	}
}
