package edu.jhu.apl.patterns_class.factory;

import edu.jhu.apl.patterns_class.schema.*;
import edu.jhu.apl.patterns_class.dom.*;
import edu.jhu.apl.patterns_class.decorator.*;

// Factory Method Creator
public interface DOMFactory {

    public default edu.jhu.apl.patterns_class.dom.replacement.Document createDocument() {
        return new edu.jhu.apl.patterns_class.dom.Document();
    }

    public default edu.jhu.apl.patterns_class.dom.replacement.Element createElement(
        String tagName, edu.jhu.apl.patterns_class.dom.replacement.Document document) throws org.w3c.dom.DOMException {
        return document.createElement(tagName);
    }

    public default edu.jhu.apl.patterns_class.dom.replacement.Attr createAttribute(
        String name, edu.jhu.apl.patterns_class.dom.replacement.Document document) throws org.w3c.dom.DOMException {
        return document.createAttribute(name);
    }

    public default edu.jhu.apl.patterns_class.dom.replacement.Text createTextNode(
        String data, edu.jhu.apl.patterns_class.dom.replacement.Document document) throws org.w3c.dom.DOMException {
        return document.createTextNode(data);
    }
}