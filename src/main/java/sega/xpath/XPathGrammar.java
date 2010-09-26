package sega.xpath;

import java.util.List;

import sega.grammar.Grammar;
import sega.grammar.Production;
import sega.grammar.SyntaxError;
import sega.lexer.Token;
import sega.lexer.TokenType;



/**
 * Subset of the LL(1) XPath 1.0 grammar
 * 
 * @author Vicente Reig Rincón de Arellano <vicente.reig@gmail.com>
 */
public class XPathGrammar {
	
	private Grammar grammar;
	
	public XPathGrammar() {
		Production xpathExpression = new Production("XPathExpression");
		
		Production wordList = new Production("WordList");
		wordList.generates(TokenType.TKN_ID).and(wordList);
		wordList.generates(TokenType.TKN_QUOTE);
		
		Production literal = new Production("Literal");		
		literal.generates(TokenType.TKN_QUOTE).and(wordList);
		
		Production operator = new Production("Operator");
		operator.generates(TokenType.TKN_EQUALS);
		operator.generates(TokenType.TKN_GTE);
		operator.generates(TokenType.TKN_LTE);
		operator.generates(TokenType.TKN_LT);
		operator.generates(TokenType.TKN_GT);
		
		Production predicate = new Production("Predicate");
		predicate.generates(TokenType.TKN_OPEN_BRACKET).and(TokenType.TKN_AT).and(TokenType.TKN_ID).and(operator).and(literal).and(TokenType.TKN_CLOSE_BRACKET);
		predicate.generates(Production.EPSILON);
		
		Production step = new Production("Step");
		step.generates(TokenType.TKN_ID).and(predicate);
		
		xpathExpression.generates(step);
		//TKN_DOUBLE_SLASH!
		xpathExpression.generates(TokenType.TKN_SLASH).and(step);
		
		grammar = new Grammar(xpathExpression);
		xpathExpression.calculateFirstSet();
	}
	
	
	
	public void eval(List<Token> tokens) throws SyntaxError {
		grammar.eval(tokens);
	}

}
