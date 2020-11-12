package com.mrh0.arclang.evaluate;

import com.mrh0.arclang.type.IVal;

public class StatementResult {
	public enum ChainControl {
		IGNORE, 		//Ignore all blocks.
		CONSUME, 		//Do block then return to IGNORE.
		PASS, 			//Do block.
		FAIL,			//Dosn't block.
	}
	
	public final IVal result;
	public final ChainControl control;
	
	public StatementResult(IVal result, ChainControl control) {
		this.result = result;
		this.control = control;
	}
}
