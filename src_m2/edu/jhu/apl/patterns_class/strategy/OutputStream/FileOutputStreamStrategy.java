package edu.jhu.apl.patterns_class.strategy;

// File Output Concrete Strategy
public class FileOutputStreamStrategy implements OutputStreamStrategy {
    @Override
    public java.io.BufferedWriter createOutputWriter(java.io.File file) throws java.io.IOException {
        return new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file)));
    }
}