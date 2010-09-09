package com.google.code.sega.xpath;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.sega.lexer.Token;
import com.google.code.sega.lexer.TokenType;
import com.google.code.sega.xpath.XPathLexer;

public class XPathLexerTest {
	
	private XPathLexer lexer;
	private static final Logger logger = LoggerFactory.getLogger(XPathLexerTest.class);
	
	@Before
	public void setUp() throws Exception {
		lexer = new XPathLexer();
	}

	@After
	public void tearDown() throws Exception {
		lexer = null;
	}

	@Test
	public void qNameWithPredicate() {
		String input = "book[@id=='dos']";
		List<Token> expected = new LinkedList<Token>();
		expected.add(new Token(TokenType.TKN_ID, "book"));
		expected.add(new Token(TokenType.TKN_OPEN_BRACKET, "["));
		expected.add(new Token(TokenType.TKN_AT, "@"));
		expected.add(new Token(TokenType.TKN_ID, "id"));
		expected.add(new Token(TokenType.TKN_EQUALS, "=="));
		expected.add(new Token(TokenType.TKN_QUOTE, "'"));
		expected.add(new Token(TokenType.TKN_ID, "dos"));
		expected.add(new Token(TokenType.TKN_QUOTE, "'"));
		expected.add(new Token(TokenType.TKN_CLOSE_BRACKET, "]"));
		List<Token> tokens = lexer.parse(input);
		logger.debug("{}", tokens);
		logger.debug("{}", expected);
		Assert.assertEquals(expected, tokens);		
	}

}
