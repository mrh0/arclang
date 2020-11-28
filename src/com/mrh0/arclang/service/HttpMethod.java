package com.mrh0.arclang.service;

import com.mrh0.arclang.exception.net.InvalidRouteMethodException;

public enum HttpMethod {
	GET("GET"),
	POST("POST");
	
	public final String name;
	
	private HttpMethod(String name) {
		this.name = name;
	}
	
	public static HttpMethod getMethod(String name) throws InvalidRouteMethodException {
		switch(name) {
			case "GET":
				return GET;
			case "POST":
				return POST;
		}
		throw new InvalidRouteMethodException(name);
	}
}
