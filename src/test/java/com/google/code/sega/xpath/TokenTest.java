package com.google.code.sega.xpath;


import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenTest {
	
	private final static Logger logger = LoggerFactory.getLogger(TokenTest.class);
	
	@Test
	public void tokenDigits() {
		Token token = new Token(TokenType.TKN_NUMBER, "[0-9]+");
		if ( token.matches("1234") )
			token.setLexeme("1234");
		assertEquals("1234", token.getLexeme());
	}
	
	@Test
	public void tokenNumber() {
		Token token = new Token(TokenType.TKN_NUMBER, ".[0-9]+|([0-9]+(.[0-9]+)?)");
		if ( token.matches("1234") )
			token.setLexeme("1234");
		assertEquals("1234", token.getLexeme());
		
		if ( token.matches("12.34") )
			token.setLexeme("12.34");
		assertEquals("12.34", token.getLexeme());
		
		if ( token.matches(".1234") )
			token.setLexeme(".1234");
		assertEquals(".1234", token.getLexeme());
	}
	
	@Test
	public void tokenLiteral() {
		String regexp = "\"[^\"]\"";
		logger.debug(regexp);
		Token token = new Token(TokenType.TKN_LITERAL, regexp);
		String lexeme = "\"lala\"";
		assertMatch(lexeme,token);
	}
	
	private void assertMatch(String lexeme, Token token) {
		if ( token.matches(lexeme) )
			token.setLexeme(lexeme);
		assertEquals(lexeme, token.getLexeme());
	}

}
