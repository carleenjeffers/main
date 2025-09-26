package edu.jhu.apl.patterns_class.iterator;

import edu.jhu.apl.patterns_class.dom.replacement.*;

import java.util.NoSuchElementException;
import java.util.Stack;

// ConcreteIterator
public class NodeIterator implements Iterator<Node> {
    // represent Node tree with Stack
    private final Stack<Node> nodes = new Stack<>();
    public NodeIterator(Node root) {
        nodes.push(root);
    }

    public boolean hasNext() {
        return !nodes.isEmpty();
    }

    public Node next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Node curr = nodes.pop();

        // repopulate stack with children
        // traverse child nodes in reverse order to preserve tree order
        if (curr instanceof ParentNode) {
            ParentNode currParent = (ParentNode) curr;
            if (currParent.getChildNodes() != null) {
                NodeList children = currParent.getChildNodes();
                for (int i = children.getLength() - 1; i >= 0; i--) {
                    nodes.push((Node) children.item(i));
            }
        }
        }

        return curr;
    }
}
