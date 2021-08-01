package com.mrh0.arclang.exception.accessor;

import com.mrh0.arclang.type.IVal;

public class AccessorTypeException extends AccessorException {

	private static final long serialVersionUID = 1L;

	public AccessorTypeException(IVal access, IVal with) {
		super("Cannot apply accessor on type '" + access.getTypeName() + "' with type '" + with.getTypeName() + "'.");
	}
	
	public AccessorTypeException(IVal access) {
		super("Cannot apply accessor on type '" + access.getTypeName() + "'.");
	}
}
