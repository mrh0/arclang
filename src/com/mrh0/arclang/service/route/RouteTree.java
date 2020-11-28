package com.mrh0.arclang.service.route;

import java.util.HashMap;
import java.util.Map;
import com.mrh0.arclang.exception.net.NoSuchRouteException;
import com.mrh0.arclang.type.func.TRoute;

public class RouteTree {
	private Map<String, RouteTree> tree;
	private TRoute here;
	public final String name;
	
	public RouteTree(String name, TRoute here) {
		this.tree = new HashMap<String, RouteTree>();
		this.here = here;
		this.name = name;
	}
	
	public TRoute getHere() {
		return this.here;
	}
	
	public RouteTree getNext(String key) {
		if(key.equals("*"))
			return tree.getOrDefault(key, null);
		return tree.getOrDefault(key, getNext("*"));
	}
	
	public RouteTree getNext(String key, RouteTree def) {
		return tree.getOrDefault(key, def);
	}
	
	public RouteTree build(String key) {
		RouteTree rt = tree.get(key);
		if(rt == null) {
			rt = new RouteTree(key, null);
			tree.put(key, rt);
		}
		return rt;
	}
	
	public RouteTree navigate(String path) throws NoSuchRouteException {
		String[] p = RouteTree.parseRoute(path);
		RouteTree c = this;
		for(int i = 0; i < p.length; i++) {
			if(p[i].length() == 0)
				continue;
			c = c.getNext(p[i]);
			if(c == null)
				throw new NoSuchRouteException(p[i]);
		}
		return c;
	}
	
	public static String[] parseRoute(String path) {
		return path.split("/");
	}
	
	public void addRoute(TRoute here) {
		this.here = here;
	}
	
	public void addRoute(TRoute here, String path) {
		String[] p = RouteTree.parseRoute(path);
		RouteTree c = this;
		for(int i = 0; i < p.length; i++) {
			if(p[i].length() == 0)
				continue;
			c = c.build(p[i]);
		}
		c.here = here;
	}
}
