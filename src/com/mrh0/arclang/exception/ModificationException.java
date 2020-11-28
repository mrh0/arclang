package com.mrh0.arclang.exception;

public class ModificationException extends ArcException {
	private static final long serialVersionUID = 1L;

	public ModificationException(String exception) {
		super("Modification exception: " + exception);
	}
}
