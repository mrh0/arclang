package com.mrh0.arclang.vm;

import java.util.HashMap;
import java.util.Map;
import com.mrh0.arclang.type.var.Var;

public class Variables {
	public Map<String, Var> vars;
	
	public Variables() {
		vars = new HashMap<String, Var>();
	}
	
	public Var get(String key) {
		return vars.getOrDefault(key, new Var(key));
	}
	
	public Var get(String key, Variables above) {
		return vars.getOrDefault(key, above.get(key));
	}
	
	public Var getOrDefault(String key, Var def) {
		return vars.getOrDefault(key, def);
	}
	
	public Var getOrDefault(String key, Variables globals) {
		Var r = vars.get(key);
		if(r == null)
			return globals.get(key);
		return r;
	}
	
	public void set(Var var) {
		vars.put(var.getName(), var);
	}
}
