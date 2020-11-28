package com.mrh0.arclang.exception;

import java.util.List;

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
	public String code;
	private List<Integer> lineIndecies;
	
	public void setPhase(ExecutionPhase phase) {
		currentPhase = phase;
	}
	
	public ExecutionPhase getPhase() {
		return currentPhase;
	}
	
	public void setCode(String code, List<Integer> lines) {
		this.code = code;
		this.lineIndecies = lines;
	}
	
	public String getLine(int line) {
		return code.substring(lineIndecies.get(line-1), lineIndecies.get(line)-1);
	}
	
	public String getLine() {
		return getLine(currentLine);
	}
}
