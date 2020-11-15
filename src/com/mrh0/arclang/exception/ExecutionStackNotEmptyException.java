package com.mrh0.arclang.exception;

public class ExecutionStackNotEmptyException extends ArcException {

	private static final long serialVersionUID = 1L;
	
	public ExecutionStackNotEmptyException(int size) {
		super("Execution stack has a size of " + size + " when expected to be 0.");
	}
}
