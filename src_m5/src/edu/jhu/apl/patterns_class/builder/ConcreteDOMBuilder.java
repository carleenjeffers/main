package edu.jhu.apl.patterns_class.builder;

import java.util.Stack;

import edu.jhu.apl.patterns_class.XMLTokenizer;
import edu.jhu.apl.patterns_class.dom.ElementProxy;
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
    // todo: couldn't figure out how to rework buildElementStart and buildElementEnd so that the stack was working properly
    public void buildElementStart(String tagName, int tokenNum, XMLTokenizer tokenizer, ElementProxy parent) {
        Element elem = factory.createElementProxy(tagName, tokenNum, tokenizer);

        if (this.root == null) {
            System.out.println("Setting root to Node with tagName: " + tagName + " and tokenNum: " + tokenNum);
            this.root = elem;
        } else {
            // decide if parent is top of stack or the passed parent (caller)
            Element actualParent = elementStack.isEmpty() ? parent : elementStack.peek();
            actualParent.appendChild(elem);
        }
        elementStack.push(elem);
    }


    @Override
    public void buildElementEnd(int tokenNum) {
        System.out.println(elementStack.toString());
        ElementProxy e = (ElementProxy)elementStack.pop();
        System.out.println("setting element end for proxy with tag " + e.getTagName() + " and end token " + tokenNum);
        e.setEndToken(tokenNum);
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
