package com.google.code.sega.lexer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegExpScanner implements Scanner {
	private StringBuffer lexeme;
	private Pattern pattern;
	private String regexp;
	private TokenType type;
	
	private static final Logger logger = LoggerFactory.getLogger(RegExpScanner.class);
	
	public RegExpScanner(TokenType type, String regexp) {
		pattern = Pattern.compile(regexp, Pattern.UNICODE_CASE);		
		this.type = type;
		lexeme = new StringBuffer();
	}
	
	@Override
	public void addToLexeme(int c) {
		char character = (char) c;
		lexeme.append(character);
//		logger.debug("Lexeme is: {}", lexeme.toString());
	}
	
	

	@Override
	public Token buildToken() {
		Token t = new Token(type);
		t.setLexeme(lexeme.toString());
		lexeme = new StringBuffer();
//		logger.debug("Token built");
		return t;
	}

	@Override
	public boolean matches(int c) {
		String input = new String();
		input += (char) c;
		return matches(input);
	}

	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);		
		return matcher.matches();
	}
	
}
