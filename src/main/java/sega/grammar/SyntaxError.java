package sega.grammar;

import sega.lexer.Token;

public class SyntaxError extends Exception {

	public SyntaxError(Token symbol, Token t) {
		super(String.format("SyntaxError: Expected %s and was %s", symbol, t));
	}

}
