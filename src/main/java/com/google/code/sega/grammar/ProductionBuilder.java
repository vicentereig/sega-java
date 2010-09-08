package com.google.code.sega.grammar;

import java.util.LinkedList;
import java.util.List;

import com.google.code.sega.xpath.Token;

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