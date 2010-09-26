package sega.grammar;

import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.lexer.Token;
import sega.lexer.TokenType;



/**
 * 
 * 
 * @author Vicente Reig Rincón de Arellano <vicente.reig@gmail.com>
 *
 */
public class Production implements Symbol {
	

	private String name;
	private List<ProductionBuilder> builders;
	private Set<Token> firstSet;
	private Set<Token> nextSet;
	private List<List<Symbol>> rightSides;
	
	public static final Production EPSILON = new Production("epsilon");
	
	private boolean hasEpsilon;
	
	private static final Logger logger = LoggerFactory.getLogger(Production.class);
	
	public Production(String name) {
		this.name = name;
		hasEpsilon = false;
		builders = new LinkedList<ProductionBuilder>();
		firstSet = new HashSet<Token>();
		nextSet = new HashSet<Token>();
		rightSides = new LinkedList<List<Symbol>>();
	}


	public void calculateFirstSet() {
		for(List<Symbol> rightSide:rightSides){
			for(Symbol symbol:rightSide) {
				if ( symbol instanceof Token ) {
					firstSet.add((Token)symbol);
					break;
				} else {
					((Production)symbol).calculateFirstSet();
					firstSet.addAll(((Production)symbol).firstSet);
				}
			}
		}
	}
	
	public ProductionBuilder generates(TokenType tkn) {
		return generates(new Token(tkn, ""));
	}
	
	public ProductionBuilder generates(Symbol sym) {
		List<Symbol> rightSide = new LinkedList<Symbol>();
		ProductionBuilder builder = new ProductionBuilder(this, rightSide);
		rightSides.add(rightSide);
		builder.add(sym);
		builders.add(builder);
		return builder;
	}
	
	
	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}
	
	private boolean checkFirstAndEval(Production p, Stack<Token> tokens) throws EmptyStackException, SyntaxError {
		if ( p.firstSet.contains(tokens.peek()) ) {						
			p.eval(tokens);
			return true;
		}			
		return false;
	}
	
	public void eval(Stack<Token> tokens) throws SyntaxError {		
		Token t = tokens.pop();
		logger.debug("[{}] Current symbol: {}", name, t);		
		for(List<Symbol> rightSymbols:rightSides) {			
//			logger.debug("Builder '{}'.", builder.getRightSymbols());
			for(Symbol symbol:rightSymbols) {
				if( symbol instanceof Production ) {
					Production p = (Production) symbol;
					//logger.debug("["+name+"] Production {}: {}",p.name, p.firstSet);
					boolean stop = false;
					try {
						stop = checkFirstAndEval(p,tokens);
					}catch(EmptyStackException e) {
						break;
					}
					if ( stop )
						break;
				} else {
					if ( !hasEpsilon() && ((Token)symbol).getType() != t.getType() ) {
						logger.error("["+name+"] ("+hasEpsilon()+")Syntax error: expected {} and was {}", ((Token)symbol), t);
						throw new SyntaxError((Token)symbol,t);
					}					
				}
			}
		}
	}
	
	public void setHasEpsilon(boolean b) {
		hasEpsilon = b;
	}
	

	public boolean hasEpsilon() {
		return hasEpsilon;
	}


	public List<ProductionBuilder> getBuilders() {
		return builders;
	}


	public List<List<Symbol>> getRightSides() {
		return rightSides;
	}

}
