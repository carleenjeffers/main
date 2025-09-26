package edu.jhu.apl.patterns_class.iterator;

import edu.jhu.apl.patterns_class.dom.replacement.*;

// ConcreteAggregate
public class NodeListAggregate implements Aggregate<Node> {
    private NodeList root;

    public NodeListAggregate(NodeList root) {
        this.root = root;
    }
    public NodeListIterator createIterator() {
        return new NodeListIterator(root);
    }
}
