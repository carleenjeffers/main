package edu.jhu.apl.patterns_class.decorator;

import edu.jhu.apl.patterns_class.*;
import edu.jhu.apl.patterns_class.schema.*;
import edu.jhu.apl.patterns_class.dom.replacement.*;

// Decorator pattern
// Decorator interface
public abstract class NodeValidationDecorator implements edu.jhu.apl.patterns_class.dom.replacement.Node {
    protected Node decoratedNode;
    protected SchemaManager schemaManager;

    public NodeValidationDecorator(Node node, SchemaManager schemaManager) {
        this.decoratedNode = node;
        this.schemaManager = schemaManager;
    }

    // helper for getting concrete Node
    public Node getNode() {
        return this.decoratedNode;
    }

    //
	// Implemented Interface Members
	//
    @Override
	public String	getNodeName() { return decoratedNode.getNodeName(); };
	@Override
    public String	getNodeValue() throws org.w3c.dom.DOMException { return decoratedNode.getNodeValue(); };
	@Override
    public void	setNodeValue(String nodeValue) throws org.w3c.dom.DOMException { { decoratedNode.setNodeValue(nodeValue); }};
	@Override
    public short	getNodeType() { return decoratedNode.getNodeType(); };
	@Override
    public Node	getParentNode(){ return decoratedNode.getParentNode(); };
	@Override
    public NodeList	getChildNodes() { return decoratedNode.getChildNodes(); };
	@Override
    public Node	getFirstChild() { return decoratedNode.getFirstChild(); };
	@Override
    public Node	getLastChild() { return decoratedNode.getLastChild(); };
	@Override
    public Node	getPreviousSibling() { return decoratedNode.getPreviousSibling(); };
	@Override
    public Node	getNextSibling(){ return decoratedNode.getNextSibling(); };
	@Override
    public Document	getOwnerDocument() { return decoratedNode.getOwnerDocument(); };
	@Override
    public Node	insertBefore(Node newChild, Node refChild) throws org.w3c.dom.DOMException { return decoratedNode.insertBefore(newChild, refChild); };
	@Override
    public Node	replaceChild(Node newChild, Node oldChild) throws org.w3c.dom.DOMException { return decoratedNode.replaceChild(newChild, oldChild); };
	@Override
    public Node	removeChild(Node oldChild) throws org.w3c.dom.DOMException { return decoratedNode.removeChild(oldChild); };
	@Override
    public Node	appendChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild) throws org.w3c.dom.DOMException { return decoratedNode.appendChild(newChild); };
	@Override
    public boolean	hasChildNodes() { return decoratedNode.hasChildNodes(); };
	@Override
    public String	getLocalName() { return decoratedNode.getLocalName(); };

	//
	// Unimplemented Interface Members
	//
    @Override
	public void normalize() { decoratedNode.normalize(); };
	@Override
    public boolean isSupported(String feature, String version) { return decoratedNode.isSupported(feature, version); };
	@Override
    public String getNamespaceURI() { return decoratedNode.getNamespaceURI(); };
	@Override
    public String getPrefix() { return decoratedNode.getPrefix(); };
	@Override
    public void setPrefix(String prefix) throws org.w3c.dom.DOMException { decoratedNode.setPrefix(prefix); };
	@Override
    public Node cloneNode(boolean deep) { return decoratedNode.cloneNode(deep); };
	@Override
    public boolean hasAttributes() { return decoratedNode.hasAttributes(); };
	@Override
    public NamedNodeMap getAttributes() { return decoratedNode.getAttributes(); };
	@Override
    public Object getUserData(String key) { return decoratedNode.getUserData(key); };
	@Override
    public Object setUserData(String key, Object data, org.w3c.dom.UserDataHandler handler) { return decoratedNode.setUserData(key, data, handler); };
	@Override
    public Object getFeature(String feature, String version) { return decoratedNode.getFeature(feature, version); };
	@Override
    public boolean isEqualNode(Node arg) { return decoratedNode.isEqualNode(arg); };
	@Override
    public String lookupNamespaceURI(String prefix) { return decoratedNode.lookupNamespaceURI(prefix); };
	@Override
    public boolean isDefaultNamespace(String namespaceURI) { return decoratedNode.isDefaultNamespace(namespaceURI); };
	@Override
    public String lookupPrefix(String namespaceURI) { return decoratedNode.lookupPrefix(namespaceURI); };
	@Override
    public boolean isSameNode(Node other) { return decoratedNode.isSameNode(other); };
	@Override
    public void setTextContent(String textContent){ decoratedNode.setTextContent(textContent); };
	@Override
    public String getTextContent() { return decoratedNode.getTextContent(); };
	@Override
    public short compareDocumentPosition(Node other) { return decoratedNode.compareDocumentPosition(other); };
	@Override
    public String getBaseURI() { return decoratedNode.getBaseURI(); };
    @Override
    public void serialize(java.io.BufferedWriter	writer, edu.jhu.apl.patterns_class.strategy.WhitespaceStrategy strategy) throws java.io.IOException { decoratedNode.serialize(writer, strategy); };

    // protected helper functions
    protected boolean canAddElement(edu.jhu.apl.patterns_class.dom.replacement.Element element, String newElement)
	{
		ValidChildren	schemaElement	= schemaManager.findSchemaElement(element == null ? null : element.getTagName());

		return schemaElement == null ? true : schemaElement.childIsValid(newElement, false);
	}

}