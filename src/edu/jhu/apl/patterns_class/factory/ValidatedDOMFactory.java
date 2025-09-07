package edu.jhu.apl.patterns_class.factory;

import edu.jhu.apl.patterns_class.schema.SchemaManager;
import edu.jhu.apl.patterns_class.decorator.*;
import edu.jhu.apl.patterns_class.dom.*;

public class ValidatedDOMFactory implements DOMFactory {
    private final SchemaManager schemaManager;

    public ValidatedDOMFactory(SchemaManager schemaManager) {
        this.schemaManager = schemaManager;
    }

    // creates validated document node
    @Override
    public edu.jhu.apl.patterns_class.dom.replacement.Document createDocument() {
        return new DocumentValidationDecorator(new Document(), this.schemaManager);
    }

    // creates validated element node
    @Override
    public edu.jhu.apl.patterns_class.dom.replacement.Element createElement(String tagName, edu.jhu.apl.patterns_class.dom.replacement.Document document) {
        return new ElementValidationDecorator(document.createElement(tagName), this.schemaManager);
    }
}