package com.mrh0.arclang.exception.net;

import com.mrh0.arclang.exception.ArcException;

public class RouteArcException extends ArcException {

	private static final long serialVersionUID = 1L;

	public RouteArcException(String msg) {
		super("No such route: '" + msg + "'.");
	}
}
