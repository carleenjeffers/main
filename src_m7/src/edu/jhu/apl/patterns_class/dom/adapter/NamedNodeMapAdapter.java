package edu.jhu.apl.patterns_class.dom.adapter;

// stubbed out Adapter class
public class NamedNodeMapAdapter implements org.w3c.dom.NamedNodeMap {
    private edu.jhu.apl.patterns_class.dom.NamedNodeMap wrappedMap;

    public NamedNodeMapAdapter(edu.jhu.apl.patterns_class.dom.NamedNodeMap map) {
        this.wrappedMap = map;
    }
}
