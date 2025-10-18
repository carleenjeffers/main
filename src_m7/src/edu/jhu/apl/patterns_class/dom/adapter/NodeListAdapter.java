package edu.jhu.apl.patterns_class.dom.adapter;

// Stubbed adapter class
public class NodeListAdapter implements org.w3c.dom.NodeList {
    private edu.jhu.apl.patterns_class.dom.NodeList wrappedList;

    public NodeListAdapter(edu.jhu.apl.patterns_class.dom.NodeList list) {
        this.wrappedList = list;
    }
}
