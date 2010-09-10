package sega.grammar;

import sega.lexer.Token;

public class SyntaxError extends Exception {
	
	private Token expected, symbol;
	
	public SyntaxError(Token symbol, Token t) {
		super(String.format("SyntaxError: Expected %s and was %s", symbol, t));
		symbol = t;
		expected = symbol;
	}

	public Token getSymbol() {
		return symbol;
	}

	public Object getExpected() {
		return expected;
	}

}
