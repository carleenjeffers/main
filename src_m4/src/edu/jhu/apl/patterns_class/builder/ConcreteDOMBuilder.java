package edu.jhu.apl.patterns_class.builder;

import java.util.Stack;
import edu.jhu.apl.patterns_class.dom.replacement.*;

// ConcreteBuilder
public class ConcreteDOMBuilder implements DOMBuilder {
    private Document factory;
    private Document document;
    private Element root;
    private Stack<Element> elementStack = new Stack<>();

    public ConcreteDOMBuilder(Document factory) {
        this.factory = factory;
    }

    @Override
    public Document getResult() {
        document.appendChild(root);
        return document;
    }

    @Override
    public boolean isEmpty() {
        return elementStack.isEmpty();
    }

    // Builder functions
    @Override
    public void buildDocument() {
        this.document = factory;
    }

    @Override
    public void buildElementStart(String tagName) {
        if (this.root == null) {
            System.out.println("setting root to Node with tagName: " + tagName);
            this.root = factory.createElement(tagName);
            elementStack.push(root);
            return;
        }

        // otherwise adding to stack
        Element elem = factory.createElement(tagName);
        if (elementStack.isEmpty()) {
            root.appendChild(elem);
        } else {
            elementStack.peek().appendChild(elem);
        }
        elementStack.push(elem);
    }

    @Override
    public void buildElementEnd() {
        elementStack.pop();
    }

    @Override
    public void buildAttribute(String name, String value) {
        // remove parser characters
        name = name.replaceAll("[=\"]", "");
        value = value.replaceAll("[=\"]", "");
        Attr attr = factory.createAttribute(name);
        attr.setNodeValue(value);
        elementStack.peek().setAttributeNode(attr);
    }

    @Override
    public void buildText(String text) {
        Node textNode = factory.createTextNode(text);
        elementStack.peek().appendChild(textNode);
    }
}
