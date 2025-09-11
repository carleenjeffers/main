package edu.jhu.apl.patterns_class.strategy;

// Concrete Whitespace Strategy
public class MinimalWhitespaceStrategy implements WhitespaceStrategy {
    @Override
    public void serialize(edu.jhu.apl.patterns_class.dom.replacement.Node node, java.io.BufferedWriter	writer) throws java.io.IOException
	{
		node.serialize(writer, this);
	}
}