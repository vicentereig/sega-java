package sega.grammar;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sega.lexer.Token;


public class ProductionBuilder {
	private List<Symbol> rightSymbols;	
	private Production production;
	private final static Logger logger = LoggerFactory.getLogger(ProductionBuilder.class);
	
	
	public ProductionBuilder(Production p) {
		rightSymbols = new LinkedList<Symbol>();		
		production = p;
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
	
	public List<Symbol> getRightSymbols() {
		return rightSymbols;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for(Symbol sym: rightSymbols) {
			if ( sym instanceof Token) {
				b.append(sym.toString());
			} else {
				b.append(((Production)sym).getName());
			}
			
			b.append(" ");
		}
		
		return b.toString();
	}

}
