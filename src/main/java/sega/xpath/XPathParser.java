package sega.xpath;

import java.util.List;

import sega.grammar.SyntaxError;
import sega.lexer.Token;

public class XPathParser {
	
	private XPathLexer lexer;
	private XPathGrammar grammar;	
	
	public XPathParser() {
		lexer = new XPathLexer();
		grammar = new XPathGrammar();
	}
	
	public void parse(String input) throws SyntaxError {
		List<Token> tokens = lexer.parse(input);
		grammar.eval(tokens);
	}
	
}
