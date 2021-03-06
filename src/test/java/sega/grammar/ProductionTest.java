package sega.grammar;


import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.lexer.Token;
import sega.lexer.TokenType;


public class ProductionTest {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductionTest.class);
	private Grammar g;
	private Production e,ep,t,tp,f;
	private Token plus,minus, star, div, parOp, parCl, num, id;
	
	/**
	 * A grammar to generate arithmetic expressions.
	 * 
	 * E -> T Ep
	 * Ep -> + TE | - TEp | epsilon
	 * T -> FTp
	 * Tp -> * FTp | / FTp | epsilon
	 * F -> ( E ) | num | id 
	 * 
	 */
	//@Before
	public void setUp() throws Exception {
		e = new Production("E");
		ep = new Production("Ep");
		t = new Production("T");
		tp = new Production("Tp");
		f = new Production("F");
		
		g = new Grammar(e);
		g.add(ep);
		g.add(t);
		g.add(tp);
		g.add(f);
		/**
		 * At this point, this tokens are build to later check
		 * the TokenType field while performing top-down parsing.
		 */
		plus = new Token(TokenType.TKN_PLUS, "+");
		minus = new Token(TokenType.TKN_MINUS, "-");
		star = new Token(TokenType.TKN_STAR, "*");
		div = new Token(TokenType.TKN_DIV, "/");
		parOp = new Token(TokenType.TKN_OPEN_PARENTHESIS, "(");
		parCl = new Token(TokenType.TKN_CLOSE_PARENTHESIS, ")");
		num = new Token(TokenType.TKN_NUMBER, "10");
		id = new Token(TokenType.TKN_ID, "var");
		
		// E -> T Ep
		e.generates(t).and(ep);
		
		// Ep -> + TEp | - TEp | epsilon
		ep.generates(plus).and(t).and(ep);
		ep.generates(minus).and(t).and(ep);
		ep.generates(Production.EPSILON);
		
		//T -> FTp
		t.generates(f).and(tp);
		
		//Tp -> * FTp | / FTp | epsilon
		tp.generates(star).and(f).and(tp);
		tp.generates(div).and(f).and(tp);
		tp.generates(Production.EPSILON);
		
		//F -> ( E ) | num | id
		f.generates(parOp).and(e).and(parCl);
		f.generates(num);
		f.generates(id);
	}	
	
	//@Test
	public void arithmethicGrammar() {
		logger.debug("{}", g);
	}
	
	/**
	 * Simple-stupid grammar to test initial top-down parsing!
	 * A -> aB
	 * B -> bA | cB | | epsilon 
	 */
	@Test public void asAndBs() {
		Production a = new Production("A");
		Production b = new Production("B");
		Token tknA = new Token(TokenType.TKN_A, "a");
		Token tknB = new Token(TokenType.TKN_B, "b");
		Token tknC = new Token(TokenType.TKN_C, "c");
		
		a.generates(tknA).and(b);
		
		b.generates(tknB).and(a);
		b.generates(tknC).and(b);
		b.generates(Production.EPSILON);
		
		Grammar g = new Grammar(a);
		g.add(b); //not necessary at all since production rule A is the root of the object graph!
		
		List<Token> tokens = new LinkedList<Token>();
		tokens.add(tknA);
		tokens.add(tknB);
		tokens.add(tknA);
		try {
			g.eval(tokens);
		} catch (SyntaxError e) {			
			e.printStackTrace();
			Assert.fail("Syntax error not expected here");
		}
	}
	
	/**
	 * Simple-stupid grammar to test initial top-down parsing!
	 * A -> aB
	 * B -> bA | cB | epsilon 
	 */
	@Test public void asAndBsandCsFails() {
		Production a = new Production("A");
		Production b = new Production("B");
		Token tknA = new Token(TokenType.TKN_A, "a");
		Token tknB = new Token(TokenType.TKN_B, "b");
		Token tknC = new Token(TokenType.TKN_C, "c");
		
		a.generates(tknA).and(b);
		
		b.generates(tknB).and(a);
		b.generates(tknC).and(b);
		b.generates(Production.EPSILON);
		
		Grammar g = new Grammar(a);
		g.add(b); //not necessary at all since production rule A is the root of the object graph!
		
		List<Token> tokens = new LinkedList<Token>();
		tokens.add(tknC);
		tokens.add(tknB);
		tokens.add(tknA);
		try {			
			g.eval(tokens);
		} catch (SyntaxError e) {
			Assert.assertEquals(e.getExpected(), tknC);
		}
	}

}
