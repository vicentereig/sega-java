package sega.lexer;

import sega.grammar.Symbol;

public class Token implements Symbol {
	private String lexeme;	
	private TokenType type;
	
	public Token(TokenType type) {	
		this(type,"");
	}
	
	public Token(TokenType type, String lexeme) {
		this.type = type;
		this.lexeme = lexeme;	
	}
	
	public Token(Token t) {
		type = t.type;
	}
	
	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;
	}
	
	public String getLexeme() {
		return lexeme;
	}

	@Override
	public String toString() {
		return String.format("<%s,%s>", type.toString(), lexeme);		
	}
	
	@Override
	public boolean equals(Object o) {
		if ( !(o instanceof Token) ) 
			return false;
		
		Token t = (Token) o;
		
		return t.lexeme.equals(lexeme) && t.type == type; 
	}
}
