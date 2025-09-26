package edu.jhu.apl.patterns_class.iterator;

import java.util.NoSuchElementException;
import edu.jhu.apl.patterns_class.dom.replacement.*;

// ConcreteIterator
public class NodeListIterator implements Iterator<Node> {
    private final NodeList nodeList;
    private int index = 0;

    public NodeListIterator(NodeList nodeList) {
        this.nodeList = nodeList;
    }

    public boolean hasNext() {
        return index < nodeList.getLength();
    }

    public Node next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return (edu.jhu.apl.patterns_class.dom.replacement.Node) nodeList.item(index++);
    }
}
