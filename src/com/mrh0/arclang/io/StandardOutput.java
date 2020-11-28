package com.mrh0.arclang.io;

public class StandardOutput implements IOutputFunction{
	@Override
	public void print(Object str) {
		System.out.print(str);
	}
}
