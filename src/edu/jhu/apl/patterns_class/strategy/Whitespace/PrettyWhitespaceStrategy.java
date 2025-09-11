package edu.jhu.apl.patterns_class.strategy;

// Concrete Whitespace Strategy
public class PrettyWhitespaceStrategy implements WhitespaceStrategy {
    private int indentationLevel = 0;
    private int attrCount = 0;

    @Override
    public void handleIndent(java.io.BufferedWriter	writer) throws java.io.IOException
    {
        for (int i = 0; i < indentationLevel; i++)
            writer.write("\t");
    }

    @Override
    public void handleNewline(java.io.BufferedWriter	writer) throws java.io.IOException {
        writer.write("\n");
    };

    @Override
    public void handleAttributes(java.io.BufferedWriter	writer) throws java.io.IOException {
        if (attrCount > 0)
            writer.write(" ");
        
        // reset count
        attrCount = 0;
    }

    @Override
    public void incrementIndentationLevel() {
        this.indentationLevel++;
    }

    @Override
    public void decrementIndentationLevel() {
        this.indentationLevel--;
    }

    @Override
    public void incrementAttributeCount() {
        this.attrCount++;
    }
}