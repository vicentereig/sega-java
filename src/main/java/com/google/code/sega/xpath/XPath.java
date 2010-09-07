package com.google.code.sega.xpath;

import java.util.LinkedList;
import java.util.List;

public class XPath {
	private List<Step> steps;
	
	public XPath() {
		steps = new LinkedList<Step>();
	}
	
	public void add(Step step) {
		steps.add(step);
	}
	
	public String toXPath() {
		return null;
	}
	
	@Override
	public String toString() {
		return toXPath();
	}
}
