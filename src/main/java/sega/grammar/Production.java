package sega.grammar;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;

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
	
	public Production(String name) {
		this.name = name;
		builders = new LinkedList<ProductionBuilder>();
		firstSet = new HashSet<Token>();
		nextSet = new HashSet<Token>();
	}
	
	public ProductionBuilder generates(Symbol sym) {
		ProductionBuilder builder = new ProductionBuilder();
		builder.add(sym);
		//update first symbols set
		//update next symbols set
		builders.add(builder);
		return builder;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(ProductionBuilder b:builders) {
			buffer.append(name);
			buffer.append("->");
			buffer.append(b.toString());
			buffer.append(" \n ");
		}		
		return buffer.toString();
	}

	public String getName() {
		return name;
	}

	public void eval(Stack<Token> tokens) {
		Token t = tokens.pop();
		//reminder: improve this loops!
		for(ProductionBuilder b:builders) 
			for(Symbol symbol:b.getRightSymbols()) {
				if( symbol instanceof Production ) {
					Production p = (Production) symbol;
					if ( p.firstSet.contains(t) ) { 
						p.eval(tokens);
						break;
					}					
				}
			}
	}
}
