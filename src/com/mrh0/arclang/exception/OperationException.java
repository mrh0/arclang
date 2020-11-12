package com.mrh0.arclang.exception;

import com.mrh0.arclang.type.IVal;

public class OperationException extends ArcException{
	private static final long serialVersionUID = 1L;

	public OperationException(String name, IVal on, IVal with) {
		super("Cannot preform operation '"+name+"' on " + on.getTypeName() + " with " + with.getTypeName());
	}
}
