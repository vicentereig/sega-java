package com.google.code.sega.xpath;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LexerTest {
	
	private Lexer lexer;
	
	@Before
	public void setUp() throws Exception {
		lexer = new Lexer();
	}

	@After
	public void tearDown() throws Exception {
		lexer = null;
	}

	@Test
	public void testSimplePath() {
		String path = "book[@id >= 1]";
		List<Token> expected = new LinkedList<Token>();
	}

}
