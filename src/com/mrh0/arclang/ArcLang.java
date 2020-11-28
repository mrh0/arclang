package com.mrh0.arclang;

import java.util.List;
import com.mrh0.arclang.evaluate.Evalizer;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.StatementBlock;
import com.mrh0.arclang.parse.statement.Statementizer;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.parse.token.Tokenizer;
import com.mrh0.arclang.service.WebServer;
import com.mrh0.arclang.service.route.HttpRouteService;
import com.mrh0.arclang.vm.VM;

public class ArcLang {
	public static void SystemsTest(String code) {
		VM vm = new VM();
		try {
			Tokenizer t = new Tokenizer(vm);
			List<Token> tokens = t.tokenize(code);
			
			Statementizer s = new Statementizer(vm, tokens);
			StatementBlock block = s.Statementize();
			
			//Evalizer e = new Evalizer();
			System.out.println(block.toString());
			
			System.out.println("\r\nRUNTIME LOG:");
			
			Evalizer.evalStatement(block, vm);
		}
		catch(Exception e) {
			String m = e.getMessage();
			if(m != null && e instanceof ArcException)
				System.err.println(m.replaceAll("<#LINE>", ""+vm.exceptionManager.currentLine));
			else {
				System.err.print("[PANIC@"+(vm.exceptionManager.currentLine > 0 ? vm.exceptionManager.currentLine : "UNKNOWN")+"]: ");
				e.printStackTrace();
			}
		}
	}
	
	public static void RoutesTest(String code) throws Exception {
		new WebServer().serve(80, new HttpRouteService().load(code));
	}
}
