package edu.jhu.apl.patterns_class.factory;

import edu.jhu.apl.patterns_class.schema.ValidChildren;
import edu.jhu.apl.patterns_class.dom.*;
import edu.jhu.apl.patterns_class.decorator.*;

public class DOMFactory {
    public static DocumentValidationDecorator createValidatedDocument() {
        return new DocumentValidationDecorator(new edu.jhu.apl.patterns_class.dom.Document(), new java.util.Vector<ValidChildren>());
    }

    public static ElementValidationDecorator createValidatedElement(String tagName, Document document) {
        return new ElementValidationDecorator(document.createElement(tagName), new java.util.Vector<ValidChildren>());
    }
}