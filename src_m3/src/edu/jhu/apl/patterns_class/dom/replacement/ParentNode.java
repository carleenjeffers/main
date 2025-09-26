package edu.jhu.apl.patterns_class.dom.replacement;

// Composite Pattern - Component
public interface ParentNode extends Node {

    // Composite Pattern methods
	public Node	appendChild(Node newChild) throws org.w3c.dom.DOMException;
    public Node	removeChild(Node oldChild) throws org.w3c.dom.DOMException;
    public NodeList	getChildNodes();
    public Node	getFirstChild();
	public Node	getLastChild();
    public Node	insertBefore(Node newChild, Node refChild) throws org.w3c.dom.DOMException;
	public Node	replaceChild(Node newChild, Node oldChild) throws org.w3c.dom.DOMException;
}
