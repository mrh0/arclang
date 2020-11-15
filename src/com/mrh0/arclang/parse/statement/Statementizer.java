package com.mrh0.arclang.parse.statement;

import java.util.ArrayList;
import java.util.List;

import com.mrh0.arclang.exception.ArcException;
import com.mrh0.arclang.parse.token.IToken;
import com.mrh0.arclang.parse.token.Token;
import com.mrh0.arclang.parse.token.TokenVal;
import com.mrh0.arclang.parse.token.Tokens;
import com.mrh0.arclang.type.IVal;
import com.mrh0.arclang.vm.VM;

public class Statementizer {
	private List<Token> t;
	private int line = 1;
	private int i = 0;
	@SuppressWarnings("unused")
	private VM vm;
	
	public Statementizer(VM vm, List<Token> tokens) {
		this.vm = vm;
		this.t = tokens;
	}
	
	/* Assigns each token to a statement recursively for every block.
	 * Statement as in 'x = 5 + 2;'  and Block as in 'if true {BLOCK..}'*/
	public StatementBlock Statementize() throws ArcException {
		List<IStatement> r = new ArrayList<IStatement>();
		List<IToken> sl = new ArrayList<IToken>();
		//boolean hasEndedBlock = false;
		while(i < t.size()) {
			Token token = t.get(i++);
			if(token.isStatementEnd()) {
				if(sl.size() > 0) {
					r.add(new Statement(sl, line));
					sl = new ArrayList<IToken>();
				}
				continue;
			}
			else if(token.isLnEnd()) {
				line++;
				continue;
			}
			else if(token.isBlock()) {
				r.add(new Statement(sl, line));
				r.add(Statementize());
				sl = new ArrayList<IToken>();
				continue;
			}
			else if(token.isBlockEnd()) {
				break;
			}
			else {
				IVal tv = Tokens.toValue(token);
				if(tv != null) 
					sl.add(new TokenVal(tv));
				else
					sl.add(token);
			}
		}
		return new StatementBlock(r.toArray(new IStatement[0]));
	}
}
