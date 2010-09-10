package sega.grammar;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import sega.lexer.Token;

/**
 * Aims to show how a LL(1) grammar can be described using OO techniques 
 * and implements top-down parsing.
 * 
 * @author Vicente Reig Rincón de Arellano <vicente.reig@gmail.com>
 */
public class Grammar {
	/**
	 * A set production rules
	 */
	private List<Production> rules;
	/**
	 * The starting production that holds the whole object graph
	 * that represents all the production rules used in this grammar
	 */
	private Production start;
	
	/**
	 * Builds a grammar.
	 * @param s
	 */
	public Grammar(Production s) {
		rules = new LinkedList<Production>();
		rules.add(s);
		start = s;
	}
	
	public void add(Production r) {
		rules.add(r);
	}
	
	/**
	 * A wrapper method for the top-down parsing process.
	 * @param tokens
	 * @throws SyntaxError 
	 */
	public void eval(List<Token> tokens) throws SyntaxError {
		Stack<Token> stackOfTokens = new Stack<Token>();
		Collections.reverse(tokens);
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
