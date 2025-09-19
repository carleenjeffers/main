package edu.jhu.apl.patterns_class.iterator;

import edu.jhu.apl.patterns_class.dom.replacement.*;

// Concrete Aggregate
public class NodeAggregate implements Aggregate<Node> {
    private Node root;

    public NodeAggregate(Node root) {
        this.root = root;
    }
    public NodeIterator createIterator() {
        return new NodeIterator(root);
    }
}
