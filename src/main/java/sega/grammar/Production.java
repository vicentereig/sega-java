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

	public static final Production EPSILON = new Production("epsilon");
	
	private boolean hasEpsilon;
	
	private static final Logger logger = LoggerFactory.getLogger(Production.class);
	
	public Production(String name) {
		this.name = name;
		hasEpsilon = false;
		builders = new LinkedList<ProductionBuilder>();
		firstSet = new HashSet<Token>();
		nextSet = new HashSet<Token>();
	}
	
	public ProductionBuilder generates(Symbol sym) {
		ProductionBuilder builder = new ProductionBuilder(this);
		builder.add(sym);
		//update first symbols set
		if ( sym instanceof Token ) {
			firstSet.add((Token) sym);
		} else {
			//not pretty sure, at this time "sym" production rule
			//may not have been defined but declared
			firstSet.addAll(((Production)sym).firstSet);
		}
		//update next symbols set
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
		for(ProductionBuilder builder:builders) {			
//			logger.debug("Builder '{}'.", builder.getRightSymbols());
			for(Symbol symbol:builder.getRightSymbols()) {
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

}
