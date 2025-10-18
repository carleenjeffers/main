package edu.jhu.apl.patterns_class.dom.adapter;

// Fully realized adapter pattern
public class DocumentAdapter implements org.w3c.dom.Document {
    private edu.jhu.apl.patterns_class.dom.Document wrappedDocument;

    public DocumentAdapter(edu.jhu.apl.patterns_class.dom.Document document) {
        this.wrappedDocument = document;
    }

}
