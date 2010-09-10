package sega.xpath;

import java.util.List;

import sega.grammar.Grammar;
import sega.grammar.Production;
import sega.grammar.SyntaxError;
import sega.lexer.Token;



/**
 * Subset of the LL(1) XPath 1.0 grammar
 * 
 * @author Vicente Reig Rincón de Arellano <vicente.reig@gmail.com>
 */
public class XPathGrammar {
	
	private Grammar grammar;
	
	public XPathGrammar() {
		Production xpathExpression = new Production("XPathExpression");
		grammar = new Grammar(xpathExpression);
	}
	
	public void eval(List<Token> tokens) throws SyntaxError {
		grammar.eval(tokens);
	}

}
