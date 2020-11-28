package com.mrh0.arclang.exception;

import com.mrh0.arclang.type.IVal;

public class AccessorException extends ArcException {

	private static final long serialVersionUID = 1L;

	public AccessorException(IVal access) {
		super("Cannot apply accessor on type '" + access.getTypeName() + "'.");
	}
}
