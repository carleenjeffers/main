package edu.jhu.apl.patterns_class.strategy;

// Minimal Whitespace Concrete Strategy
public class MinimalWhitespaceStrategy implements WhitespaceStrategy {
	// Minimal strategy inserts no whitespace
    @Override public void handleIndent(java.io.BufferedWriter	writer) throws java.io.IOException {};
	@Override public void handleNewline(java.io.BufferedWriter	writer) throws java.io.IOException {};
	@Override public void handleAttributes(java.io.BufferedWriter	writer) throws java.io.IOException {};
	@Override public void incrementIndentationLevel() {};
	@Override public void decrementIndentationLevel() {};
	@Override public void incrementAttributeCount() {};
}