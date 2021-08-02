package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TNumber;

public class TRangeIterable extends TIterable {
	private int from;
	private int to;
	public TRangeIterable(int from, int to) {
		this.from = from;
		this.to = to;
	}
	
	public class RangeIterator implements Iterator<IVal> {
		private int from;
		private int to;
		private int key;
		
		public RangeIterator(int from, int to) {
			//System.out.println(from + ":" + to);
			this.from = from;
			this.to = to;
			this.key = 0;
		}
		
		@Override
		public boolean hasNext() {
			return from + key < to;
		}

		@Override
		public IVal next() {
			return TNumber.create(from+key++);
		}
	}
	
	public class RangeKeyIterator implements Iterator<IVal> {
		private int from;
		private int to;
		private int key;
		
		public RangeKeyIterator(int from, int to) {
			this.from = from;
			this.to = to;
			this.key = 0;
		}
		
		@Override
		public boolean hasNext() {
			return from + key < to;
		}

		@Override
		public IVal next() {
			return TNumber.create(key++);
		}
	}

	@Override
	public Iterator<IVal> keyIterator() {
		return new RangeKeyIterator(from, to);
	}

	@Override
	public Iterator<IVal> iterator() {
		return new RangeIterator(from, to);
	}
	
	public static TIterable create(int from, int to) {
		if(to >= from)
			return new TRangeIterable(from, to);
		else
			return new TReverseRangeIterable(from, to);
	}
}
