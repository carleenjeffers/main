package edu.jhu.apl.patterns_class.builder;

import edu.jhu.apl.patterns_class.XMLTokenizer;
import edu.jhu.apl.patterns_class.dom.ElementProxy;
import edu.jhu.apl.patterns_class.dom.replacement.*;

// Builder
public interface DOMBuilder {
    // Product is Document
    public Document getResult();

    // builder functions
    public void buildDocument();
    public void buildElementStart(String tagName, int tokenNum, XMLTokenizer tokenizer, ElementProxy parent);
    public void buildElementEnd(int tokenNum);
    public void buildAttribute(String name, String value);
    public void buildText(String text);

    public boolean isEmpty();
}
