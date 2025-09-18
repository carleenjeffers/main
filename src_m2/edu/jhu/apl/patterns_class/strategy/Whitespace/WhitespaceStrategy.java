package edu.jhu.apl.patterns_class.strategy;

// Whitespace Strategy Interface
public interface WhitespaceStrategy {
    public void handleIndent(java.io.BufferedWriter	writer) throws java.io.IOException;
    public void handleNewline(java.io.BufferedWriter	writer) throws java.io.IOException;
    public void handleAttributes(java.io.BufferedWriter	writer) throws java.io.IOException;
    public void incrementIndentationLevel();
    public void decrementIndentationLevel();
    public void incrementAttributeCount();
}