package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.var.Var;

public class TKeyIterable extends TIterable {
	private Var var;
	private TIterable iterable;
	
	public TKeyIterable(Var var, TIterable iterable) {
		this.var = var;
		this.iterable = iterable;
	}
	
	private class ValueIterator implements Iterator<IVal> {
		private Var var;
		private Iterator<IVal> iterator;
		
		public ValueIterator(Var var, Iterator<IVal> iterator) {
			this.var = var;
			this.iterator = iterator;
		}
		
		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public IVal next() {
			return var.set(iterator.next());
		}
	}
	
	@Override
	public Iterator<IVal> iterator() {
		return new ValueIterator(var, iterable.keyIterator());
	}
}
