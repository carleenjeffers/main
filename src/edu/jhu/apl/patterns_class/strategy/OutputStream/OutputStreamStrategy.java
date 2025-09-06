package edu.jhu.apl.patterns_class.strategy;

// Output Stream Strategy Interface
public interface OutputStreamStrategy {
    java.io.BufferedWriter createOutputWriter(java.io.File file) throws java.io.IOException;
}