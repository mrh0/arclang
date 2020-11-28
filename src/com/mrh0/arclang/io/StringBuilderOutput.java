package com.mrh0.arclang.io;

public class StringBuilderOutput implements IOutputFunction{

	private StringBuilder builder;
	
	public StringBuilderOutput() {
		builder = new StringBuilder();
	}
	
	@Override
	public void print(Object str) {
		builder.append(str);
	}

	@Override
	public String toString() {
		return builder.toString();
	}
}
