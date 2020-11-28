package com.mrh0.arclang.service.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class RouteClient {
	
	private List<String> headers;
	
	public RouteClient(Socket c, RouteTree tree, VM vm) throws IOException {
		headers = new ArrayList<String>();
		
		BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
		StringBuilder request = new StringBuilder();
    	
    	String data = "";
    	while(data.length() > 0 && (data = in.readLine()) != null) {
    		request.append(data+"\r\n");
		}
    	String r = request.toString();
    	
    	String content = process(r, tree, vm);
    	
    	StringBuilder response = new StringBuilder();
    	
    	for(String h : headers) {
    		response.append(h);
    		response.append("\r\n");
    	}
    	response.append(content);
	}
	
	public String process(String request, RouteTree tree, VM vm) {
		System.out.println("\r\nRUNTIME LOG:");
		Context con = new Context();
		//Evalizer.evalStatement(block, vm);
		return "";
	}
}
