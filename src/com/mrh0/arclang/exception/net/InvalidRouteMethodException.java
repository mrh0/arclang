package com.mrh0.arclang.exception.net;

import com.mrh0.arclang.exception.ArcException;

public class InvalidRouteMethodException extends ArcException {

	private static final long serialVersionUID = 1L;

	public InvalidRouteMethodException(String method) {
		super("Invalid route method '"+method+"'.");
	}
}
