package com.google.code.sega.grammar;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import com.google.code.sega.lexer.Token;

public class Grammar {
	private List<Production> rules;
	private Production start;
	
	public Grammar(Production s) {
		rules = new LinkedList<Production>();
		rules.add(s);
		start = s;
	}
	
	public void add(Production r) {
		rules.add(r);
	}
	
	public void eval(List<Token> tokens) {
		Stack<Token> stackOfTokens = new Stack<Token>();
		stackOfTokens.addAll(tokens);
		start.eval(stackOfTokens);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("{ ");
		for(Production p:rules)
			buffer.append(p.toString());		
		buffer.append("}");
		return buffer.toString();
	}
}
