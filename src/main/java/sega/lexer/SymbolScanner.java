package sega.lexer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SymbolScanner implements Scanner {
	private StringBuffer lexeme;	
	private String symbol;
	private int scanning;
	private TokenType type;
	private final static Logger logger = LoggerFactory.getLogger(SymbolScanner.class);
	
	public SymbolScanner(TokenType type, String symbol) {
		scanning = 0;
		this.symbol = symbol;
		lexeme = new StringBuffer();
		this.type = type;
	}

	@Override
	public void addToLexeme(int c) {
		lexeme.append((char)c);
//		logger.debug("{}", lexeme.toString());
	}

	@Override
	public Token buildToken() {
		Token t = new Token(type);
		t.setLexeme(lexeme.toString());
		lexeme = new StringBuffer();
		scanning = 0;
		return t;
	}

	@Override
	public boolean matches(int c) {		
		boolean result = symbol.charAt(scanning) == c;
		scanning = (scanning+1)%symbol.length();
//		logger.debug("{} {} "+result, scanning, symbol);
		return result;
	}
}
