package com.mrh0.arclang.exception.accessor;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.util.StringUtil;

public class AccessorArgumentException extends AccessorException {

	private static final long serialVersionUID = 1L;

	public AccessorArgumentException(IVal access, int args, int...expected) {
		super("Cannot apply accessor on type '" + access.getTypeName() + "' with "+args+" arguments, expected "+StringUtil.arrayToString(expected, "or")+" arguments.");
	}
}
