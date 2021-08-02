package com.mrh0.arclang.type.iter;

import java.util.Iterator;

import com.mrh0.arclang.exception.ArcException;

public interface KeyIterable<E> {
	public Iterator<E> keyIterator();
}
