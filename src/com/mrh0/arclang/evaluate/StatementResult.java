package com.mrh0.arclang.evaluate;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TUndefined;

public class StatementResult {
	public final IVal result;
	
	public StatementResult(IVal result) {
		this.result = result;
	}
	
	public StatementResult() {
		this.result = TUndefined.getInstance();
	}
}
