package com.mrh0.arclang.vm;

import com.mrh0.arclang.exception.ExceptionManager;

public class VM {
	public final ExceptionManager exceptionManager;
	
	public VM() {
		exceptionManager = new ExceptionManager();
	}
}
