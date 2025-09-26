package edu.jhu.apl.patterns_class.dom;

import edu.jhu.apl.patterns_class.XMLSerializer;

// Composite
public class Document extends Node implements edu.jhu.apl.patterns_class.dom.replacement.Document
{
	private NodeList	nodes		= null;

	public Document()
	{
		super(null, org.w3c.dom.Node.DOCUMENT_NODE);
		document	= this;
		nodes		= new NodeList();
	}

	public void serializePretty(XMLSerializer.XMLSerializerContext	ctx) throws java.io.IOException {
		ctx.writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
		ctx.writer.write("\n");

		edu.jhu.apl.patterns_class.dom.replacement.Node rootNode = this.getDocumentElement();

		if (rootNode != null) {
			rootNode.serializePretty(ctx);
		}
	}
	public void serializeMinimal(XMLSerializer.XMLSerializerContext	ctx) throws java.io.IOException {
		ctx.writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
		edu.jhu.apl.patterns_class.dom.replacement.Node rootNode = this.getDocumentElement();

		if (rootNode != null) {
			rootNode.serializeMinimal(ctx);
		}
	}

	// Composite pattern inherited methods
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getChildNodes()	{ return nodes; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node getFirstChild()
	  {return (edu.jhu.apl.patterns_class.dom.replacement.Node)nodes.getFirst();}
	public edu.jhu.apl.patterns_class.dom.replacement.Node getLastChild()
	  {return (edu.jhu.apl.patterns_class.dom.replacement.Node )nodes.getLast();}
	public edu.jhu.apl.patterns_class.dom.replacement.Node
	  insertBefore(edu.jhu.apl.patterns_class.dom.replacement.Node newChild,
	  edu.jhu.apl.patterns_class.dom.replacement.Node refChild) throws org.w3c.dom.DOMException
	{
		// TODO:  Do readonly checks on this node and current parent of newChild.  NO_MODIFICATION_ALLOWED_ERR
		// TODO:  Exclude child types not permitted for this element here.  HIERARCHY_REQUEST_ERR

		if (newChild.getOwnerDocument() != getOwnerDocument())
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.WRONG_DOCUMENT_ERR,
			  "New Child is not a part of this document.");

		if (newChild.getParentNode() != null)
			newChild.getParentNode().removeChild(newChild);

		if (refChild == null)
		{
			nodes.addLast(newChild);
			((Node )newChild).setParent(this);
			return newChild;
		}

		int index	= nodes.indexOf(refChild);

		if (index == -1)
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.NOT_FOUND_ERR,
			  "Reference Child is not a child of this node.");

		nodes.add(index, newChild);
		((Node )newChild).setParent(this);

		return newChild;
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Node
	  replaceChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild,
	  edu.jhu.apl.patterns_class.dom.replacement.Node oldChild) throws org.w3c.dom.DOMException
	{
		// TODO:  Do readonly checks on this node and current parent of newChild.  NO_MODIFICATION_ALLOWED_ERR
		// TODO:  Exclude child types not permitted for this element here.  HIERARCHY_REQUEST_ERR

		if (newChild.getOwnerDocument() != getOwnerDocument())
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.WRONG_DOCUMENT_ERR,
			  "New Child is not a part of this document.");

		if (newChild.getParentNode() != null)
			newChild.getParentNode().removeChild(newChild);

		int index	= nodes.indexOf(oldChild);

		if (index == -1)
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.NOT_FOUND_ERR,
			  "Old Child is not a child of this node.");

		nodes.add(index, newChild);
		((Node )newChild).setParent(this);
		((Node )nodes.get(index + 1)).setParent(null);
		nodes.remove(index + 1);

		return oldChild;
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Node removeChild(edu.jhu.apl.patterns_class.dom.replacement.Node oldChild)
	  throws org.w3c.dom.DOMException
	{
		// TODO:  Do readonly checks on this node.  NO_MODIFICATION_ALLOWED_ERR

		int index	= nodes.indexOf(oldChild);

		if (index == -1)
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.NOT_FOUND_ERR,
			  "Old Child is not a child of this node.");

		((Node )nodes.get(index)).setParent(null);
		nodes.remove(index);

		return oldChild;
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Node appendChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild)
	  throws org.w3c.dom.DOMException
	{
		// TODO:  Do readonly checks on this node and current parent of newChild.  NO_MODIFICATION_ALLOWED_ERR
		// TODO:  Exclude child types not permitted for this element here.  HIERARCHY_REQUEST_ERR

		if (newChild.getOwnerDocument() != getOwnerDocument())
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.WRONG_DOCUMENT_ERR,
			  "New Child is not a part of this document.");

		if (newChild.getParentNode() != null)
			newChild.getParentNode().removeChild(newChild);

		nodes.addLast(newChild);
		((Node )newChild).setParent(this);

		return newChild;
	}
	@Override
	public boolean hasChildNodes()					{ return nodes.size() > 0; }

	//
	// Implemented Document members.
	//
	public edu.jhu.apl.patterns_class.dom.replacement.Element createElement(String tagName) throws org.w3c.dom.DOMException
	  {return new Element(tagName,this);}
	public edu.jhu.apl.patterns_class.dom.replacement.Text createTextNode(String data) { return new Text(data, this); }
	public edu.jhu.apl.patterns_class.dom.replacement.Attr createAttribute(String name) throws org.w3c.dom.DOMException
	  { return new Attr(name, this); }
	public edu.jhu.apl.patterns_class.dom.replacement.Element getDocumentElement()
	{
		for (java.util.ListIterator i = ((NodeList )getChildNodes()).listIterator(0); i.hasNext();)
		{
			edu.jhu.apl.patterns_class.dom.replacement.Node	element =
			  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

			if (element instanceof edu.jhu.apl.patterns_class.dom.replacement.Element)
				return (edu.jhu.apl.patterns_class.dom.replacement.Element )element;
		}

		return null;
	}

	//
	// Unimplemented Document members.
	//
	public org.w3c.dom.DOMImplementation getImplementation() { return null; }
	public org.w3c.dom.DocumentType getDoctype() { return null; }
	public org.w3c.dom.DocumentFragment createDocumentFragment() { return null; }
	public org.w3c.dom.Comment createComment(String data) { return null; }
	public org.w3c.dom.CDATASection createCDATASection(String data) throws org.w3c.dom.DOMException { return null; }
	public org.w3c.dom.ProcessingInstruction createProcessingInstruction(String target, String data)
	  throws org.w3c.dom.DOMException
	  { return null; }
	public org.w3c.dom.EntityReference createEntityReference(String name) throws org.w3c.dom.DOMException { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node
	  importNode(edu.jhu.apl.patterns_class.dom.replacement.Node importedNode, boolean deep) throws org.w3c.dom.DOMException
	  { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Element createElementNS(String namespaceURI, String qualifiedName)
	  throws org.w3c.dom.DOMException
	  { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Attr createAttributeNS(String namespaceURI, String qualifiedName)
	  throws org.w3c.dom.DOMException
	  { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagNameNS(String namespaceURI, String localName)
	  { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Element getElementById(String elementId) { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node cloneNode(boolean deep) { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node
	  renameNode(edu.jhu.apl.patterns_class.dom.replacement.Node n, String namespaceURI, String qualifiedName) { return null; }
	public void normalizeDocument() {}
	public org.w3c.dom.DOMConfiguration getDomConfig() { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node
	  adoptNode(edu.jhu.apl.patterns_class.dom.replacement.Node source) { return null; }
	public void setDocumentURI(String documentURI) {}
	public String getDocumentURI() { return null; }
	public void setStrictErrorChecking(boolean strictErrorChecking) {}
	public boolean getStrictErrorChecking() { return false; }
	public void setXmlVersion(String xmlVersion) {}
	public String getXmlVersion() { return null; }
	public void setXmlStandalone(boolean xmlStandalone) {}
	public boolean getXmlStandalone() { return false; }
	public String getXmlEncoding() { return null; }
	public String getInputEncoding() { return null; }
}
