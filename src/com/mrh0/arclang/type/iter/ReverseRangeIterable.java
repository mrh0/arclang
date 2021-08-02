package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TNumber;

public class ReverseRangeIterable extends TIterable {
	private int from;
	private int to;
	public ReverseRangeIterable(int from, int to) {
		this.from = from;
		this.to = to;
	}

	public class ReverseRangeIterator implements Iterator<IVal> {
		private int from;
		private int to;
		private int key;
		
		public ReverseRangeIterator(int from, int to) {
			this.from = from;
			this.to = to;
			this.key = 0;
		}

		@Override
		public boolean hasNext() {
			return from - key-1 > to;
		}

		@Override
		public IVal next() {
			return TNumber.create(from+key++);
		}
	}
	
	public class ReverseRangeKeyIterator implements Iterator<IVal> {
		private int from;
		private int to;
		private int key;
		
		public ReverseRangeKeyIterator(int from, int to) {
			this.from = from;
			this.to = to;
			this.key = 0;
		}

		@Override
		public boolean hasNext() {
			return from - key-1 > to;
		}

		@Override
		public IVal next() {
			return TNumber.create(key--);
		}
	}

	@Override
	public Iterator<IVal> keyIterator() {
		return new ReverseRangeKeyIterator(from, to);
	}

	@Override
	public Iterator<IVal> iterator() {
		return new ReverseRangeIterator(from, to);
	}
	
	public static TIterable create(int from, int to) {
		return RangeIterable.create(from, to);
	}
}

