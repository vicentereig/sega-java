package sega.lexer;

public interface Scanner {

	public boolean matches(int c);

	public void addToLexeme(int c);

	public Token buildToken();

}
