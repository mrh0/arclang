package com.mrh0.arclang.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.exception.CastException;
import com.mrh0.arclang.exception.EscapeCharacterException;
import com.mrh0.arclang.exception.accessor.AccessorArgumentException;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class TList implements IVal, Iterable<IVal> {
	private final List<IVal> list;
	
	public TList() {
		list = new ArrayList<IVal>();
	}
	
	public TList(List<IVal> list) {
		this.list = new ArrayList<IVal>();
		for(IVal val : list)
			this.push(val);
	}
	
	public TList(String[] list) throws EscapeCharacterException {
		this.list = new ArrayList<IVal>();
		for(String s : list)
			this.list.add(TString.create(s));
	}
	
	public TList(IVal...list) {
		this.list = new ArrayList<IVal>();
		for(IVal v : list)
			this.push(v);
	}

	@Override
	public String getTypeName() {
		return "list";
	}
	
	@Override
	public String toString() {
		return list.toString();
	}
	
	@Override
	public boolean booleanValue() {
		return list.size() > 0;
	}
	
	public int size() {
		return list.size();
	}
	
	public IVal get(int i) {
		return list.get(i);
	}
	
	public void push(IVal val) {
		list.add(IVal.get(val));
	}
	
	public void pushVars(IVal val) {
		list.add(val);
	}
	
	@Override
	public IVal accessor(IVal key, VM vm, Context context) throws ArcException {
		if(key.isNumber())
			return get(TNumber.from(key).getIntegerValue());
		
		TList args = from(key);
		if(args.size() == 0)
			return TNumber.create(list.size());
		else if(args.size() == 1) {
			if(args.get(0).isNumber()) {
				return get(TNumber.from(args.get(0)).getIntegerValue());
			}
			else if(args.get(0) instanceof Iterable) {
				TList a = new TList();
				
				@SuppressWarnings("unchecked")
				Iterable<IVal> iter = (Iterable<IVal>) args.get(0);
				for(IVal v : iter)
					a.push(this.get(TNumber.from(v).getIntegerValue()));
				return a;
			}
		}
		else if(args.size() == 2) {
			if(args.get(0).isNumber() && args.get(1).isNumber()) {
				TList a = new TList();
				int as = TNumber.from(args.get(0)).getIntegerValue();
				int ae = TNumber.from(args.get(1)).getIntegerValue();
				
				if(as == ae) {
					a.push(get(as));
				}
				else if(as < ae && as < 0 && ae >= 0) {
					for(int i = size()+as; i > ae-1; i--) {
						a.push(get(i));
					}
				}
				else if(as > ae && as >= 0 && ae < 0){
					for(int i = as; i < size()+ae+1; i++) {
						a.push(get(i));
					}
				}
				else if(as < ae) {
					for(int i = as; i < ae+1; i++) {
						a.push(get(i));
					}
				}
				else if(as > ae){
					for(int i = as; i > ae-1; i--) {
						a.push(get(i));
					}
				}
				return a;
			}
		}
		throw new AccessorArgumentException(this, args.size(), 0, 1, 2);
	}

	@Override
	public Iterator<IVal> iterator() {
		return list.iterator();
	}
	
	public static TList from(IVal v) throws ArcException {
		v = IVal.get(v);
		if(!(v instanceof TList))
			throw new CastException(v, "list");
		return (TList) v;
	}
	
	@Override
	public boolean isList() {
		return true;
	}
	
	public List<IVal> getList() {
		return list;
	}
	
	@Override
	public IVal add(IVal v) throws ArcException {
		list.addAll(from(v).list);
		return this;
	}
	
	@Override
	public IVal mul(IVal v) throws ArcException {
		TString.from(v);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			sb.append(list.get(i).toString());
			if(i+1 < list.size())
				sb.append(v.toString());
		}
		return TString.create(sb.toString());
	}
}
