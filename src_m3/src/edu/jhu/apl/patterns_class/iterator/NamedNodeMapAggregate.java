package edu.jhu.apl.patterns_class.iterator;

import edu.jhu.apl.patterns_class.dom.replacement.*;

// ConcreteAggregate
public class NamedNodeMapAggregate implements Aggregate<Node> {
    private NamedNodeMap root;

    public NamedNodeMapAggregate(NamedNodeMap root) {
        this.root = root;
    }
    public NamedNodeMapIterator createIterator() {
        return new NamedNodeMapIterator(root);
    }
}
