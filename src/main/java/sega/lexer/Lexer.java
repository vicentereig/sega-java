package sega.lexer;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lexer {
	
	private List<TokenListener> listeners;
	private List<Scanner> scanners;
	
	private int currentLine, currentColumn;		

	private final static Logger logger = LoggerFactory.getLogger(Lexer.class);
	
	public Lexer() {
		listeners = new LinkedList<TokenListener>();
		scanners = new LinkedList<Scanner>();
		currentLine = 1;
		currentColumn = 1;
	}
	private Scanner lastMatched;
	public void tokenize(PushbackReader reader) throws IOException {
		int c = 0;
		while ( (c = reader.read()) != -1 ) {	
			//update line and columns pointers
			if ( lastMatched != null ) {
				if ( lastMatched.matches(c) ) {
					lastMatched.addToLexeme(c);		
				} else {
					Token t = lastMatched.buildToken();
					reader.unread(c);
					updateListeners(t);
					lastMatched = null;
				}
			} else {
				for (Scanner scan : scanners) {
					if (scan.matches(c)) {
						scan.addToLexeme(c);
						lastMatched = scan;
						break;
					}
				}
			}			
		}
		if ( lastMatched != null ) {
			updateListeners(lastMatched.buildToken());
			lastMatched = null;
		}
	}
		
	private void updateListeners(Token t) {
		for(TokenListener l:listeners)
			l.tokenRecognised(t);
	}
	
	public void tokenize(String input) {
		PushbackReader reader = new PushbackReader(new StringReader(input));
		
		try {
			tokenize(reader);
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}

	public void addTokenListener(TokenListener tokenListener) {
		listeners.add(tokenListener);
	}

	public void addScanner(Scanner scan) {
		scanners.add(scan);
	}
}
