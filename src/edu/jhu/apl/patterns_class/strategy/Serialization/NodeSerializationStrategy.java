package edu.jhu.apl.patterns_class.strategy;

// Serialization Strategy Interface
public interface NodeSerializationStrategy {
    public void serialize(edu.jhu.apl.patterns_class.dom.replacement.Node node, java.io.BufferedWriter	writer) throws java.io.IOException ;
}