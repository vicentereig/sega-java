package com.google.code.sega.xpath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
	private String lexeme;
	private Pattern pattern;	
	private TokenType type;
	
	public Token(TokenType type, String regexp) {
		pattern = Pattern.compile(regexp);		
		this.type = type;
	}
	
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	
	public String getLexeme() {
		return lexeme;
	}

	public boolean matches(String input) {
		Matcher matcher = pattern.matcher(input);		
		return matcher.matches();
	}
	
	
}
