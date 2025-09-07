package edu.jhu.apl.patterns_class.factory;

import edu.jhu.apl.patterns_class.schema.*;
import edu.jhu.apl.patterns_class.dom.*;
import edu.jhu.apl.patterns_class.decorator.*;

public interface DOMFactory {

    public static edu.jhu.apl.patterns_class.dom.replacement.Document createDocument() {
        return new edu.jhu.apl.patterns_class.dom.Document();
    }

    public static edu.jhu.apl.patterns_class.dom.replacement.Document createValidatedDocument(SchemaManager schemaManager) {
        return new DocumentValidationDecorator(createDocument(), schemaManager);
    }

    public static edu.jhu.apl.patterns_class.dom.replacement.Element createValidatedElement(String tagName, edu.jhu.apl.patterns_class.dom.replacement.Document document, SchemaManager schemaManager) {
        return new ElementValidationDecorator(document.createElement(tagName), schemaManager);
    }
}