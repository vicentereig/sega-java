package sega.lexer;

import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.lexer.Lexer;
import sega.lexer.RegExpScanner;
import sega.lexer.Scanner;
import sega.lexer.SymbolScanner;
import sega.lexer.Token;
import sega.lexer.TokenListener;
import sega.lexer.TokenType;

public class LexerTest {
	
	private Lexer lexer;
	private final static Logger logger = LoggerFactory.getLogger(LexerTest.class);
	
	@Before
	public void setUp() throws Exception {
		lexer = new Lexer();
	}

	@After
	public void tearDown() throws Exception {
		lexer = null;
	}

	@Test
	public void testSimplePath() {
		String xpath = "book1234";
		final Token expected = new Token(TokenType.TKN_ID);
		expected.setLexeme("book");
		logger.debug("Hello!");
		Scanner scan = new RegExpScanner(TokenType.TKN_ID, "[a-zA-z][a-zA-z]*");
		lexer.addScanner(scan);
		lexer.addTokenListener( new TokenListener() {
			@Override
			public void tokenRecognised(Token t) {
				logger.debug("Hell yes! {}", t);
				Assert.assertEquals(expected.getLexeme(), t.getLexeme());
			}			
		});
		
		lexer.tokenize(xpath);
	}
	
	@Test
	public void twoNonsenseTokens() {
		String xpath = "book1234";
		Token book = new Token(TokenType.TKN_ID);
		book.setLexeme("book");
		Token digit = new Token(TokenType.TKN_NUMBER);
		digit.setLexeme("1234");
		
		final List<Token> expected = new LinkedList<Token>();
		expected.add(book);
		expected.add(digit);
		
		Scanner scan = new RegExpScanner(TokenType.TKN_ID, "[a-zA-z][a-zA-z]*");		
		Scanner digits = new RegExpScanner(TokenType.TKN_NUMBER, "[0-9]+");
		lexer.addScanner(digits);
		lexer.addScanner(scan);
		
		final List<Token> tokens = new LinkedList<Token>();
		lexer.addTokenListener( new TokenListener() {
			@Override
			public void tokenRecognised(Token t) {
				logger.debug("Hell yes! {}", t);
				tokens.add(t);
				if ( tokens.size() > 1 ) 
					Assert.assertEquals(expected, tokens);
			}			
		});
		
		lexer.tokenize(xpath);
	}
	
	@Test
	public void anExpression() {
		String expression = "@id=='dos'";
		final List<Token> expected = new LinkedList<Token>();
		expected.add(new Token(TokenType.TKN_AT, "@"));
		expected.add(new Token(TokenType.TKN_ID, "id"));
		expected.add(new Token(TokenType.TKN_EQUALS, "=="));
		expected.add(new Token(TokenType.TKN_QUOTE, "'"));
		expected.add(new Token(TokenType.TKN_ID, "dos"));
		expected.add(new Token(TokenType.TKN_QUOTE, "'"));
	
		Scanner atScan = new SymbolScanner(TokenType.TKN_AT, "@");
		Scanner idScan = new RegExpScanner(TokenType.TKN_ID, "[a-zA-z][a-zA-z]*");
		Scanner equalsScan = new SymbolScanner(TokenType.TKN_EQUALS, "==");
		Scanner literalScan = new SymbolScanner(TokenType.TKN_QUOTE, "'");
		
		lexer.addScanner(literalScan);
		lexer.addScanner(idScan);
		lexer.addScanner(atScan);		
		lexer.addScanner(equalsScan);		
		
		final List<Token> tokens = new LinkedList<Token>();
		lexer.addTokenListener( new TokenListener() {
			@Override
			public void tokenRecognised(Token t) {
				logger.debug("Hell yes! {}", t);
				tokens.add(t);
				if ( tokens.size() > 5 ) 
					Assert.assertEquals(expected, tokens);
			}			
		});
		lexer.tokenize(expression);
	}

}
