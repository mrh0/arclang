package com.mrh0.arclang.parse.statement;

import com.mrh0.arclang.util.StringUtil;

public class StatementBlock implements IStatement {
	private IStatement[] statements;
	
	public StatementBlock(IStatement...statements) {
		this.statements = statements;
	}
	
	public IStatement[] getStatements() {
		return statements;
	}
	
	public IStatement getStatement(int i) {
		return statements[i];
	}
	
	public int size() {
		return statements.length;
	}
	
	@Override
	public String toString() {
		return "{"+StringUtil.arrayToString(statements)+"}";
	}
}
