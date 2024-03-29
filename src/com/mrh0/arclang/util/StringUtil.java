package com.mrh0.arclang.util;

import java.util.Iterator;
import java.util.List;

public class StringUtil {
	public static String arrayToString(String start, Object[] arr, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		for(int i = 0; i < arr.length; i++) {
			r.append(arr[i].toString());
			if(i+1 < arr.length)
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(String start, List<?> arr, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		for(int i = 0; i < arr.size(); i++) {
			r.append(arr.get(i).toString());
			if(i+1 < arr.size())
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(String start, int[] arr, String end, String sep, String last) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		for(int i = 0; i < arr.length; i++) {
			r.append(arr[i]);
			
			if(i+1 < arr.length)
				r.append(sep);
			if(i+2 == arr.length)
				r.append(last);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(String start, Iterable<?> iterable, String end, String sep) {
		StringBuilder r = new StringBuilder();
		r.append(start);
		Iterator<?> it = iterable.iterator();
		while(it.hasNext()) {
			r.append(it.next().toString());
			if(it.hasNext())
				r.append(sep);
		}
		r.append(end);
		return r.toString();
	}
	
	public static String arrayToString(Object[] arr, String sep) {
		return arrayToString("", arr, "", sep);
	}
	
	public static String arrayToString(String start, Object[] arr, String end) {
		return arrayToString(start, arr, end, ", ");
	}
	
	public static String arrayToString(Object[] arr) {
		return arrayToString("", arr, "", ", ");
	}
	
	public static String arrayToString(Iterable<?> iterable) {
		return arrayToString("", iterable, "", ", ");
	}
	
	public static String arrayToString(int[] arr, String last) {
		return arrayToString("", arr, "", ", ", last+" ");
	}
	
	public static String push(String s, char c) {
		return s+c;
	}
	
	public static String pop(String s) {
		return s.substring(s.length()-1);
	}
}
