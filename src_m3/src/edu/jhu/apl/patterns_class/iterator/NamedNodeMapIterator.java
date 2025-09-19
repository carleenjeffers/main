package edu.jhu.apl.patterns_class.iterator;

import java.util.NoSuchElementException;

import edu.jhu.apl.patterns_class.dom.replacement.*;

public class NamedNodeMapIterator implements Iterator<Node> {
    private final NamedNodeMap nodeMap;
    private int index = 0;

    public NamedNodeMapIterator(NamedNodeMap nodeMap) {
        this.nodeMap = nodeMap;
    }

    public boolean hasNext() {
        return index < nodeMap.getLength();
    }

    public Node next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return (edu.jhu.apl.patterns_class.dom.replacement.Node) nodeMap.item(index++);
    }
}
