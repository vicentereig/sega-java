package com.google.code.sega.grammar;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.sega.xpath.Token;
import com.google.code.sega.xpath.TokenType;

public class ProductionTest {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductionTest.class);
	
	/**
	 * Predicate -> '[' Expr ']'
	 * Expr -> '@' [a-z]+ '==' [0-9]+
	 */
	@Test public void predicateProdutions() {
		Production predicate = new Production("Predicate");
		Production expr = new Production("Expr");
		
		Token at = new Token(TokenType.TKN_AT, "@");
		Token word = new Token(TokenType.TKN_LITERAL, "[a-z]+");
		Token obracket = new Token(TokenType.OPEN_BRACKET, "\\[");
		Token cbracket = new Token(TokenType.CLOSED_BRACKET, "\\]");
		Token digits = new Token(TokenType.TKN_DIGITS, "[0-9]+");
		Token equals = new Token(TokenType.TKN_EQUALS, "==");
		predicate.add(obracket).and(expr).and(cbracket);
		expr.add(at).and(word).and(equals).and(digits);
		
		logger.debug("{}", predicate);
		logger.debug("{}", expr);
	}

}
