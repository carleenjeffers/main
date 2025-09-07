package edu.jhu.apl.patterns_class.decorator;

import edu.jhu.apl.patterns_class.*;
import edu.jhu.apl.patterns_class.schema.*;
import edu.jhu.apl.patterns_class.dom.replacement.*;
import edu.jhu.apl.patterns_class.exception.InvalidSchemaOperationException;

public class DocumentValidationDecorator extends NodeValidationDecorator implements Document {
    protected final Document decoratedDocument;

    public DocumentValidationDecorator(Node node, SchemaManager schemaManager) {
        super(node, schemaManager);

        // ensure node is document
        if (!(node instanceof Document)) {
            throw new IllegalArgumentException("DocumentValidationDecorator can only wrap Documents.");
        }

        this.decoratedDocument = (Document) node;
    }

    // Add validation before appending child to document
    @Override
    public Node appendChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild) throws org.w3c.dom.DOMException {
        // validation checks
        if (!canRootElement(newChild.getNodeName())) {
            throw new InvalidSchemaOperationException();
        }
        return decoratedNode.appendChild(newChild);
    }

    // private helper
    protected boolean canRootElement(String newElement)
	{
		return canAddElement(null, newElement);
	}

    // document class specific functions
    public Element createElement(String tagName) throws org.w3c.dom.DOMException { return decoratedDocument.createElement(tagName); };
	public Text createTextNode(String data) { return decoratedDocument.createTextNode(data); };
	public Attr createAttribute(String name) throws org.w3c.dom.DOMException { return decoratedDocument.createAttribute(name); };
	public Element getDocumentElement() { return decoratedDocument.getDocumentElement(); };

	//
	// Unimplemented Document members.
	//
	public org.w3c.dom.DOMImplementation getImplementation() { return decoratedDocument.getImplementation(); };
	public org.w3c.dom.DocumentType getDoctype() { return decoratedDocument.getDoctype(); };
	public org.w3c.dom.DocumentFragment createDocumentFragment() { return decoratedDocument.createDocumentFragment(); };
	public org.w3c.dom.Comment createComment(String data) { return decoratedDocument.createComment(data); };
	public org.w3c.dom.CDATASection createCDATASection(String data) throws org.w3c.dom.DOMException { return decoratedDocument.createCDATASection(data); };
	public org.w3c.dom.ProcessingInstruction createProcessingInstruction(String target, String data)
	  throws org.w3c.dom.DOMException { return decoratedDocument.createProcessingInstruction(target, data); };
	public org.w3c.dom.EntityReference createEntityReference(String name) throws org.w3c.dom.DOMException { return decoratedDocument.createEntityReference(name); };
	public Node importNode(Node importedNode, boolean deep) throws org.w3c.dom.DOMException { return decoratedDocument.importNode(importedNode, deep); };
	public Element createElementNS(String namespaceURI, String qualifiedName) throws org.w3c.dom.DOMException { return decoratedDocument.createElementNS(namespaceURI, qualifiedName); };
	public Attr createAttributeNS(String namespaceURI, String qualifiedName) throws org.w3c.dom.DOMException { return decoratedDocument.createAttributeNS(namespaceURI, qualifiedName); };
	public NodeList getElementsByTagNameNS(String namespaceURI, String localName) { return decoratedDocument.getElementsByTagNameNS(namespaceURI, localName); };
	public Element getElementById(String elementId) { return decoratedDocument.getElementById(elementId); };
	public Node cloneNode(boolean deep) { return decoratedDocument.cloneNode(deep); };
	public Node renameNode(Node n, String namespaceURI, String qualifiedName) { return decoratedDocument.renameNode(n, namespaceURI, qualifiedName); };
	public void normalizeDocument() { decoratedDocument.normalizeDocument(); };
	public org.w3c.dom.DOMConfiguration getDomConfig() { return decoratedDocument.getDomConfig(); };
	public Node adoptNode(Node source) { return decoratedDocument.adoptNode(source); };
	public void setDocumentURI(String documentURI) { decoratedDocument.setDocumentURI(documentURI); };
	public String getDocumentURI() { return decoratedDocument.getDocumentURI(); };
	public void setStrictErrorChecking(boolean strictErrorChecking) { decoratedDocument.setStrictErrorChecking(strictErrorChecking); };
	public boolean getStrictErrorChecking() { return decoratedDocument.getStrictErrorChecking(); };
	public void setXmlVersion(String xmlVersion) { decoratedDocument.setXmlVersion(xmlVersion); };
	public String getXmlVersion() { return decoratedDocument.getXmlVersion(); };
	public void setXmlStandalone(boolean xmlStandalone) { decoratedDocument.setXmlStandalone(xmlStandalone); };
	public boolean getXmlStandalone() { return decoratedDocument.getXmlStandalone(); };
	public String getXmlEncoding() { return decoratedDocument.getXmlEncoding(); };
	public String getInputEncoding() { return decoratedDocument.getInputEncoding(); };

    
}