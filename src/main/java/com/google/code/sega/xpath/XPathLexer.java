package com.google.code.sega.xpath;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.code.sega.lexer.Lexer;
import com.google.code.sega.lexer.RegExpScanner;
import com.google.code.sega.lexer.Scanner;
import com.google.code.sega.lexer.SymbolScanner;
import com.google.code.sega.lexer.Token;
import com.google.code.sega.lexer.TokenListener;
import com.google.code.sega.lexer.TokenType;

public class XPathLexer {
	
	private Lexer lexer;
	
	private final Map<TokenType,String> symbols = new HashMap<TokenType,String>() {{
		put(TokenType.TKN_AT, "@");
		put(TokenType.TKN_OPEN_BRACKET, "[");
		put(TokenType.TKN_CLOSE_BRACKET, "]");
		put(TokenType.TKN_EQUALS, "==");
		put(TokenType.TKN_GTE, ">=");
		put(TokenType.TKN_LTE, "<=");
		put(TokenType.TKN_GT, ">");
		put(TokenType.TKN_LT, "<");
		put(TokenType.TKN_QUOTE, "'");

	}};
	
	private final Map<TokenType, String> regexps = new HashMap<TokenType, String>() {{
		put(TokenType.TKN_NUMBER, "[0-9]+");
		put(TokenType.TKN_REAL, "[0-9]*.[0-9]+");
		put(TokenType.TKN_ID, "[a-zA-Z]+[a-zA-Z\\-0-9]*");
	}};
	
	public XPathLexer() {
		lexer = new Lexer();
		Scanner scan = null;
		for(TokenType type:symbols.keySet()) {
			String symbol = symbols.get(type);
			scan = new SymbolScanner(type,symbol);
			lexer.addScanner(scan);			
		}
		for( TokenType type:regexps.keySet() ) {
			String regexp = regexps.get(type);
			scan = new RegExpScanner(type, regexp);
			lexer.addScanner(scan);
		}		
	}
	
	public List<Token> parse(String input) {
		final List<Token> tokens = new LinkedList<Token>();
		lexer.addTokenListener(new TokenListener() {
			public void tokenRecognised(Token t) {
				tokens.add(t);
			}
		});
		lexer.tokenize(input);
		return tokens;
	}
}
