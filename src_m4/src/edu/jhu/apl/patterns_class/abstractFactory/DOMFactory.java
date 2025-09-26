package edu.jhu.apl.patterns_class.abstractFactory;

import edu.jhu.apl.patterns_class.dom.replacement.*;

// AbstractFactory
public interface DOMFactory {
    public Element createElement(String tagName) throws org.w3c.dom.DOMException;
	public Text createTextNode(String data);
	public Attr createAttribute(String name) throws org.w3c.dom.DOMException;
}
