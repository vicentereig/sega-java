package com.google.code.sega.xml;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Element {
	private String namespace;
	private String qName;
	private Map<String,String> attributes;
	
	private List<Element> children;
	
	public Element() {
		children = new LinkedList<Element>();
		attributes = new HashMap<String,String>();
	}
	
	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	@Override
	public String toString() {
		return String.format("<%s>", qName);
	}
}
