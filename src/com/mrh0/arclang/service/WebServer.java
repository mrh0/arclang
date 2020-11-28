package com.mrh0.arclang.service;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import com.mrh0.arclang.service.route.RouteClient;

public class WebServer {
	IWebService ws;
	
	public void serve(int port, IWebService ws) {
		ServerSocket s;
		this.ws = ws;
		try {
			s = new ServerSocket(80);
			while(true) {
				Socket client = s.accept();
				new Thread(() -> {
					try {
						clientHandler(client);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}).start(); 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void clientHandler(Socket c) throws Exception {
		System.out.println("Connected");
		ws.spawnClient(c);
		/*try {
			String output = "";
			byte[] content = output.getBytes();
			c.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
			c.getOutputStream().write("Server: arclang HTTP\r\n".getBytes());
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
		}*/
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