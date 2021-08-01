package com.mrh0.arclang.vm;

import com.mrh0.arclang.io.IOutputFunction;
import com.mrh0.arclang.io.StandardOutput;

public class Context {
	private final static IOutputFunction STANDARD_OUT = new StandardOutput();
	public enum ChainControl {
		IGNORE, 		//Ignore all blocks.
		CONSUME, 		//Do block then return to IGNORE.
		PASS 			//Keep searching.
	}
	
	public ChainControl chain;
	public IOutputFunction out;
	public boolean usesBlock = false;
	public final Variables local;
	public final boolean root;
	
	public Context() {
		local = new Variables();
		chain = ChainControl.IGNORE;
		out = STANDARD_OUT;
		root = false;
	}
	
	public Context(boolean root) {
		local = new Variables();
		chain = ChainControl.IGNORE;
		out = STANDARD_OUT;
		this.root = root;
	}
}
