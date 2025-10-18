package edu.jhu.apl.patterns_class.dom.adapter;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

// https://www.geeksforgeeks.org/system-design/adapter-pattern/
// Fully realized adapter pattern
public class NodeAdapter implements org.w3c.dom.Node {
    private edu.jhu.apl.patterns_class.dom.Node wrappedNode;

    // todo
    public NodeAdapter(edu.jhu.apl.patterns_class.dom.Node node) {
        this.wrappedNode = node;
    }

    // core node getter
    public edu.jhu.apl.patterns_class.dom.Node getCoreNode() {
        return wrappedNode;
    }

    // unwrapping helper
    private edu.jhu.apl.patterns_class.dom.Node unwrap(Node adapter) {
        if (!(adapter instanceof NodeAdapter)) {
            throw new IllegalArgumentException("Non-adapter Node provided, cannot unwrap");
        }
        return ((NodeAdapter)adapter).getCoreNode();
    }

    @Override
    public Node appendChild(Node newChild) {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.appendChild(this.unwrap(newChild)));
    }

    @Override
    public Node cloneNode(boolean deep) {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.cloneNode(deep));
    }

    @Override
    public short compareDocumentPosition(Node other) throws DOMException {
        return wrappedNode.compareDocumentPosition(this.unwrap(other));
    }

    @Override
    public NamedNodeMap getAttributes() {
        return new NamedNodeMapAdapter((edu.jhu.apl.patterns_class.dom.NamedNodeMap)wrappedNode.getAttributes());
    }

    @Override
    public String getBaseURI() {
        return wrappedNode.getBaseURI();
    }

    @Override
    public NodeList getChildNodes() {
        return new NodeListAdapter((edu.jhu.apl.patterns_class.dom.NodeList)wrappedNode.getChildNodes());
    }

    @Override
    public Object getFeature(String feature, String version) {
        return wrappedNode.getFeature(feature, version);
    }

    @Override
    public Node getFirstChild() {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.getFirstChild());
    }

    @Override
    public Node getLastChild() {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.getLastChild());
    }

    @Override
    public String getLocalName() {
        return wrappedNode.getLocalName();
    }

    @Override
    public String getNamespaceURI() {
        return wrappedNode.getNamespaceURI();
    }

    @Override
    public Node getNextSibling() {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.getNextSibling());
    }

    @Override
    public String getNodeName() {
        return wrappedNode.getNodeName();
    }

    @Override
    public short getNodeType() {
        return wrappedNode.getNodeType();
    }

    @Override
    public String getNodeValue() {
        return wrappedNode.getNodeValue();
    }

    @Override
    public Document getOwnerDocument() {
        return new DocumentAdapter((edu.jhu.apl.patterns_class.dom.Document)wrappedNode.getOwnerDocument());
    }

    @Override
    public Node getParentNode() {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.getParentNode());
    }

    @Override
    public String getPrefix() {
        return wrappedNode.getPrefix();
    }

    @Override
    public Node getPreviousSibling() {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.getPreviousSibling());
    }

    @Override
    public String getTextContent() throws DOMException {
        return wrappedNode.getTextContent();
    }

    @Override
    public Object getUserData(String key) {
        return wrappedNode.getUserData(key);
    }

    @Override
    public boolean hasAttributes() {
        return wrappedNode.hasAttributes();
    }

    @Override
    public boolean hasChildNodes() {
        return wrappedNode.hasChildNodes();
    }

    @Override
    public Node insertBefore(Node newChild, Node refChild) {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.insertBefore(this.unwrap(newChild), this.unwrap(refChild)));
    }

    @Override
    public boolean isDefaultNamespace(String namespaceURI) {
        return wrappedNode.isDefaultNamespace(namespaceURI);
    }

    @Override
    public boolean isEqualNode(Node arg) {
        return wrappedNode.isEqualNode(this.unwrap(arg));
    }

    @Override
    public boolean isSameNode(Node other) {
        return wrappedNode.isSameNode(this.unwrap(other));
    }

    @Override
    public boolean isSupported(String feature, String version) {
        return wrappedNode.isSupported(feature, version);
    }

    @Override
    public String lookupNamespaceURI(String prefix) {
        return wrappedNode.lookupNamespaceURI(prefix);
    }

    @Override
    public String lookupPrefix(String namespaceURI) {
        return wrappedNode.lookupPrefix(namespaceURI);
    }

    @Override
    public void normalize() {
        wrappedNode.normalize();
    }

    @Override
    public Node removeChild(Node oldChild) {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.removeChild(this.unwrap(oldChild)));
    }

    @Override
    public Node replaceChild(Node newChild, Node oldChild) {
        return new NodeAdapter((edu.jhu.apl.patterns_class.dom.Node)wrappedNode.replaceChild(this.unwrap(newChild), this.unwrap(oldChild)));
    }

    @Override
    public void setNodeValue(String nodeValue) {
        wrappedNode.setNodeValue(nodeValue);
    }

    @Override
    public void setPrefix(String prefix) {
        wrappedNode.setPrefix(prefix);
    }

    @Override
    public void setTextContent(String textContent) {
        wrappedNode.setTextContent(textContent);
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        return wrappedNode.setUserData(key, data, handler);
    }

}
