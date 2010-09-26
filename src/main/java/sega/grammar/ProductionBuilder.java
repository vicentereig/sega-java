package sega.grammar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.lexer.Token;
import sega.lexer.TokenType;


public class ProductionBuilder {
	private List<Symbol> rightSymbols;	
	private Production production;
	private final static Logger logger = LoggerFactory.getLogger(ProductionBuilder.class);
	
	
	public ProductionBuilder(Production p, List<Symbol> rightSide) {
		rightSymbols = rightSide;		
		this.production = p;
	}
	
	public ProductionBuilder add(Symbol sym) {
		if ( sym instanceof Production && ((Production)sym) == Production.EPSILON ) {
			logger.debug("[{}] There is an epsilon rule!", production.getName() ); 
			production.setHasEpsilon(true);
		}		
		rightSymbols.add(sym);
		return this;
	}

	public ProductionBuilder and(Symbol p) {
		add(p);
		return this;
	}
	
	public ProductionBuilder and(TokenType type) {
		and(new Token(type, ""));
		return this;
	}	
}
