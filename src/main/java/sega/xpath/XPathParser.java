package sega.xpath;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.grammar.SyntaxError;
import sega.lexer.Token;

public class XPathParser {
	
	private XPathLexer lexer;
	private XPathGrammar grammar;	
	
	private static final Logger logger = LoggerFactory.getLogger(XPathParser.class);
	
	public XPathParser() {
		lexer = new XPathLexer();
		grammar = new XPathGrammar();
	}
	
	public void parse(String input) throws SyntaxError {
		List<Token> tokens = lexer.parse(input);
		logger.debug("{}", tokens);
		grammar.eval(tokens);
	}
	
}
