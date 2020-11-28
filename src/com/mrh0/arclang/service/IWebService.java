package com.mrh0.arclang.service;

import java.net.Socket;

public interface IWebService {
	
	public void spawnClient(Socket c) throws Exception;
}
