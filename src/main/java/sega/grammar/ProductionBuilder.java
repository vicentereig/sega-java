package sega.grammar;

import java.util.LinkedList;
import java.util.List;

import sega.lexer.Token;


public class ProductionBuilder {
	private List<Symbol> rightSymbols;
	
	public ProductionBuilder() {
		rightSymbols = new LinkedList<Symbol>();
	}
	
	public ProductionBuilder add(Symbol sym) {
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
