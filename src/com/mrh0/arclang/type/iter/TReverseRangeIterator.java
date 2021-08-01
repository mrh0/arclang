package com.mrh0.arclang.type.iter;

import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.type.TNumber;

public class TReverseRangeIterator extends TIterator {
	private int from;
	private int to;
	private int key;
	public TReverseRangeIterator(int from, int to) {
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
		return TNumber.create(from+key--);
	}

	@Override
	public IVal nextKey() {
		return TNumber.create(key);
	}
	
	public static TIterator create(int from, int to) {
		return TRangeIterator.create(from, to);
	}
}

