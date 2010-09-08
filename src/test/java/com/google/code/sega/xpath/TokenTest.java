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
		String regexp = "(\"[^\"]*\")|('[^']*')";
		logger.debug(regexp);
		Token token = new Token(TokenType.TKN_LITERAL, regexp);
		String lexeme = "\"lala\"";
		assertMatch(lexeme,token);
		lexeme = "'lala'";
		assertMatch(lexeme,token);
	}
	
	/**
	 * Cited from http://www.w3.org/TR/xpath/#exprlex
	 * 
	 * When tokenizing, the longest possible token is always returned.
     * For readability, whitespace may be used in expressions even though 
     * not explicitly allowed by the grammar: ExprWhitespace may be freely 
     * added within patterns before or after any ExprToken.
	 * 
	 * The following special tokenization rules must be applied in the order 
	 * specified to disambiguate the ExprToken grammar:
	 *    If there is a preceding token and the preceding token 
	 *    is not one of @, ::, (, [, , or an Operator, 
	 *    then a * must be recognized as a MultiplyOperator 
	 *    and an NCName must be recognized as an OperatorName.
	 * If the character following an NCName (possibly after intervening ExprWhitespace) 
	 * is (, then the token must be recognized as a NodeType or a FunctionName.
	 * 
	 * If the two characters following an NCName (possibly after intervening ExprWhitespace) are ::,
	 * then the token must be recognized as an AxisName.
	 * 
	 * Otherwise, the token must not be recognized as a MultiplyOperator, 
	 * an OperatorName, a NodeType, a FunctionName, or an AxisName.
	 * 
	 */
	@Test
	public void tokenExpr() {
		
	}
	
	
	private void assertMatch(String lexeme, Token token) {
		if ( token.matches(lexeme) )
			token.setLexeme(lexeme);
		assertEquals(lexeme, token.getLexeme());
	}

}
