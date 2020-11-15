package com.mrh0.arclang.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	public static void server(int port) {
		ServerSocket s;
		try {
			s = new ServerSocket(80);
			while(true) {
				Socket client = s.accept();
				new Thread(() -> clientHandler(client)).start(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void clientHandler(Socket c) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
	    	StringBuilder result = new StringBuilder();
	    	
	    	String data = " ";
	    	while(data.length() > 0 && (data = in.readLine()) != null) {
	    		result.append(data+"\r\n");
			}
	    	
	    	String r = result.toString();
			
			String output = "";
			
			byte[] content = output.getBytes();
			
			c.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
			c.getOutputStream().write("Server: hulind HTTP\r\n".getBytes());
			c.getOutputStream().write("Content-Type: text/html\r\n".getBytes());
			//c.getOutputStream().write(("Set-Cookie: session="+session+"\r\n").getBytes());
			c.getOutputStream().write(("Content-Length: " + content.length + "\r\n\r\n").getBytes());
			c.getOutputStream().write(content);
			c.getOutputStream().flush();
			
			c.shutdownInput();
			c.close();
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void send(Socket c, String message) throws IOException {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(c.getOutputStream()));
		out.write(message);
		out.newLine();
		out.flush();
	}
	
	/*private static int SESSION_KEY_LENGTH = 10;
	private static final String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static Random random = new Random();

	public static String generateSessionKey() {
		StringBuilder r = new StringBuilder(SESSION_KEY_LENGTH);
		for(int i = 0; i < SESSION_KEY_LENGTH; i++) {
			r.append(chars.charAt(random.nextInt(chars.length())));
		}
		return r.toString();
	}*/
}