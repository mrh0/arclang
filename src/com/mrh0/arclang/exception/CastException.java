package com.mrh0.arclang.exception;

import com.mrh0.arclang.type.IVal;

public class CastException extends ArcException {
	private static final long serialVersionUID = 1L;

	public CastException(IVal on, IVal to) {
		super("Cannot cast " + on.getTypeName() + " to " + to.getTypeName());
	}
	
	public CastException(String on, IVal to) {
		super("Cannot cast " + on + " to " + to.getTypeName());
	}
	
	public CastException(IVal on, String to) {
		super("Cannot cast " + on.getTypeName() + " to " + to);
	}
	
	public CastException(String on, String to) {
		super("Cannot cast " + on + " to " + to);
	}
}
