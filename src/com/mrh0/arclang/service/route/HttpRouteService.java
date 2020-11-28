package com.mrh0.arclang.service.route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import com.mrh0.arclang.evaluate.Evalizer;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.io.StringBuilderOutput;
import com.mrh0.arclang.parse.statement.IStatement;
import com.mrh0.arclang.parse.statement.StatementBlock;
import com.mrh0.arclang.parse.statement.Statementizer;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.parse.token.Tokenizer;
import com.mrh0.arclang.service.IWebService;
import com.mrh0.arclang.type.func.Arguments;
import com.mrh0.arclang.vm.Context;
import com.mrh0.arclang.vm.VM;

public class HttpRouteService implements IWebService {

	private VM vm = new VM();
	
	public HttpRouteService() throws Exception {
		super();
	}
	
	public HttpRouteService load(String code) {
		try {
			Tokenizer t = new Tokenizer(vm);
			List<Token> tokens = t.tokenize(code);
			
			Statementizer s = new Statementizer(vm, tokens);
			IStatement r = s.Statementize();
			Evalizer.evalStatement(r, vm);
		}
		catch(Exception e) {
			String m = e.getMessage();
			if(m != null && e instanceof ArcException)
				System.err.println(m.replaceAll("<#LINE>", ""+vm.exceptionManager.currentLine));
			else {
				System.err.print("[PANIC:"+(vm.exceptionManager.currentLine > 0 ? vm.exceptionManager.currentLine : "UNKNOWN")+"]: ");
				e.printStackTrace();
			}
		}
		return this;
	}

	@Override
	public void spawnClient(Socket c) throws IOException, ArcException {
		
		BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
    	StringBuilder result = new StringBuilder();
    	
    	String data = " ";
    	while(data.length() > 0 && (data = in.readLine()) != null) {
    		result.append(data+"\r\n");
		}
    	
    	String request = result.toString();
		
		String[] r = request.split(" ");
		//System.out.println("REQUEST: " + request);
		RouteTree rt = vm.routes.navigate(r[1]);
		/*RouteTree rt;
		try {
			rt = vm.routes.navigate(r[1]);
		}
		catch(NoSuchRouteException e) {
			byte[] content = "404 Not Found".getBytes();
			c.getOutputStream().write("HTTP/1.1 404 Not Found\r\n".getBytes());
			c.getOutputStream().write("Server: arclang HTTP\r\n".getBytes());
			c.getOutputStream().write("Content-Type: text/html\r\n".getBytes());
			c.getOutputStream().write(("Content-Length: " + content.length + "\r\n\r\n").getBytes());
			c.getOutputStream().write(content);
			c.getOutputStream().flush();
			c.close();
			System.err.println("404 Not Found");
			return;
		}*/
		
		StringBuilderOutput sbo = new StringBuilderOutput();
		Context context = new Context();
		context.out = sbo;
		if(rt != null && rt.getHere() != null) {
			rt.getHere().execute(new Arguments(), vm, context);
		}
		
		String output = sbo.toString();
		byte[] content = output.getBytes();
		c.getOutputStream().write("HTTP/1.1 200 OK\r\n".getBytes());
		c.getOutputStream().write("Server: arclang HTTP\r\n".getBytes());
		c.getOutputStream().write("Content-Type: text/html\r\n".getBytes());
		//c.getOutputStream().write(("Set-Cookie: session="+session+"\r\n").getBytes());
		c.getOutputStream().write(("Content-Length: " + content.length + "\r\n\r\n").getBytes());
		c.getOutputStream().write(content);
		c.getOutputStream().flush();
		
		System.out.println("Ended");
		c.close();
	}
}
