package com.mrh0.arclang.type.func;

import java.util.ArrayList;
import java.util.List;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TList;

public class Arguments {
	List<IVal> args;
	public Arguments(List<IVal> args) {
		this.args = args;
	}
	
	public Arguments(TList args) {
		this.args = args.getList();
	}
	
	public Arguments(IVal...args) {
		this.args = new ArrayList<IVal>(args.length);
		for(int i = 0; i < args.length; i++)
			this.args.set(i, args[i]);
	}
	
	public IVal get(int i) {
		return args.get(i);
	}
}
