package com.mrh0.arclang;

import java.util.List;
import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.statement.StatementBlock;
import com.mrh0.arclang.parse.statement.Statementizer;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.parse.token.Tokenizer;
import com.mrh0.arclang.vm.VM;

public class ArcLang {
	public static void SystemsTest(String code) {
		VM vm = new VM();
		try {
			Tokenizer t = new Tokenizer(vm);
			List<Token> tokens = t.tokenize(code);
			
			Statementizer s = new Statementizer(vm, tokens);
			StatementBlock block = s.Statementize();
			
			System.out.println(block.toString());
		}
		catch(ArcException e) {
			System.err.println(e.getMessage().replaceAll("<#LINE>", ""+vm.exceptionManager.currentLine));
		}
	}
}
