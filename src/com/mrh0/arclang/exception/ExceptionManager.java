package com.mrh0.arclang.exception;

public class ExceptionManager {
	public enum ExecutionPhase {
		INIT,
		PARSE,
		POSTFIXIFY,
		OPTIMIZE,
		EXECUTION
	}
	
	public int currentLine = -1; // Public for speed.
	private ExecutionPhase currentPhase = ExecutionPhase.INIT;
	
	public void setPhase(ExecutionPhase phase) {
		currentPhase = phase;
	}
	
	public ExecutionPhase getPhase() {
		return currentPhase;
	}
}
