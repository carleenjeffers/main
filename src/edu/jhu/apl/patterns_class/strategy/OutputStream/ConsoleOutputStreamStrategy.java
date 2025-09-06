package edu.jhu.apl.patterns_class.strategy;

// Concrete OutputStream Strategy
public class ConsoleOutputStreamStrategy implements OutputStreamStrategy {
    @Override
    public java.io.BufferedWriter createOutputWriter(java.io.File file) throws java.io.IOException {
        return new java.io.BufferedWriter(new java.io.OutputStreamWriter(System.out));
    }
}