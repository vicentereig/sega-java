package com.google.code.sega.grammar;

import java.util.Set;

import com.google.code.sega.xpath.Token;

/**
 * 
 * 
 * @author Vicente Reig Rincón de Arellano <vicente.reig@gmail.com>
 *
 */
public class Production implements Symbol {
	
	private String name;
	private ProductionBuilder builder;
	private Set<Token> firstSymbols;
	private Set<Token> nextSymbols;
	
	public Production(String name) {
		this.name = name;
		builder = new ProductionBuilder();
	}
	
	public ProductionBuilder add(Token t) {
		builder.add(t);
		return builder;
	}
	
	public ProductionBuilder add(Production p) {
		builder.add(p);
		return builder;
	}
	
	@Override
	public String toString() {
		return String.format("%s -> %s", name, builder.toString());
	}

	public String getName() {
		return name;
	}
}
