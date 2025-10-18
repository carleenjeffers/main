package edu.jhu.apl.patterns_class.dom;

import edu.jhu.apl.patterns_class.XMLSerializer;

public class Element extends Node implements edu.jhu.apl.patterns_class.dom.replacement.Element
{
	private NamedNodeMap		attributes	= null;

	Element(String tagName, Document document)
	{
		super(tagName, org.w3c.dom.Node.ELEMENT_NODE);
		this.document	= document;
		attributes	= new NamedNodeMap(document);
	}

	// Override relevant PrimitiveMethods
	@Override
	public void writeOpenTags(java.io.BufferedWriter writer) throws java.io.IOException {
		writer.write("<" + this.getTagName());
	}

	@Override
	public void writeAttributes(java.io.BufferedWriter writer) throws java.io.IOException {
		for (java.util.ListIterator i =
			  ((edu.jhu.apl.patterns_class.dom.NodeList )this.getAttributes()).listIterator(0);
			  i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				attr.serializeMinimal(writer);
			}
	}

	@Override
	public void writeChildren(java.io.BufferedWriter writer) throws java.io.IOException {
		if (((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0).hasNext()) {
			writer.write(">");

			for (java.util.ListIterator i =
				((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0);
				i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					(edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
					child.serializeMinimal(writer);
			}
		}
	}

	@Override
	public void writeClosingTags(java.io.BufferedWriter writer) throws java.io.IOException {
		if (!((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0).hasNext())
			writer.write("/>");
		else
		{
			writer.write("</" + this.getTagName() + ">");
		}
	}

	@Override
	public void formatStart(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.prettyIndentation();
	}

	@Override
	public void writeAttributesPretty(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		int	attrCount	= 0;

		for (java.util.ListIterator i =
			((edu.jhu.apl.patterns_class.dom.NodeList )this.getAttributes()).listIterator(0);
			i.hasNext();)
		{
			edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				(edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

			attr.serializePretty(ctx);
			attrCount++;
		}

		if (attrCount > 0)
			ctx.writer.write(" ");
	}

	@Override
	public void writeChildrenPretty(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		if (((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0).hasNext()) {
			ctx.writer.write(">");
			ctx.writer.write("\n");
			ctx.incrementIndentationLevel();

			for (java.util.ListIterator i =
				((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0);
				i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					(edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
					child.serializePretty(ctx);
			}

			ctx.decrementIndentationLevel();
			ctx.prettyIndentation();
		}
	}

	@Override
	public void formatEnd(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.writer.write("\n");
	}

	//
	// Implemented Element members.
	//
	public String getAttribute(String name)
	{
		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(name) == 0)
				return attribute.getValue();
		}

		return null;
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Attr getAttributeNode(String name)
	{
		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(name) == 0)
				return attribute;
		}

		return null;
	}
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagName(String tagName)
	{
		// TODO:  Do a preorder traversal of the entire tree.
		NodeList	nodeList	= new NodeList();

		for (java.util.ListIterator i = ((NodeList )getChildNodes()).listIterator(0); i.hasNext();)
		{
			edu.jhu.apl.patterns_class.dom.replacement.Node	element =
			  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

			if (element instanceof Element && ((Element )element).getTagName().compareTo(tagName) == 0)
				nodeList.addLast(element);
		}

		return nodeList;
	}
	public String getTagName()
	{
		return getNodeName();
	}
	public boolean hasAttribute(String name)
	{
		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(name) == 0)
				return true;
		}

		return false;
	}
	public void removeAttribute(String name)
	{
		// TODO:  Check for readonly status.  NO_MODIFICATION_ALLOWED_ERR

		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(name) == 0)
			{
				attributes.remove(attribute);
				return;
			}
		}
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Attr
	  removeAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr oldAttr)
	{
		// TODO:  Check for readonly status.  NO_MODIFICATION_ALLOWED_ERR

		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute == oldAttr)
			{
				attributes.remove(attribute);
				return attribute;
			}
		}

		throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.NOT_FOUND_ERR, "Attribute not found.");
	}
	public void setAttribute(String name, String value)
	{
		// TODO:  Check for readonly status.  NO_MODIFICATION_ALLOWED_ERR
		// TODO:  Check for illegal characters in name.  INVALID_CHARACTER_ERR

		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(name) == 0)
			{
				attribute.setValue(value);
				return;
			}
		}

		Attr	attribute;
		attributes.addLast(attribute = new Attr(name, value, (Document )getOwnerDocument()));
		attribute.setParent(this);
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Attr
	  setAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr)
	{
		if (newAttr.getOwnerDocument() != getOwnerDocument())
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.WRONG_DOCUMENT_ERR,
			  "Attribute not created by this document.");

		if (newAttr.getParentNode() != null)
			throw new org.w3c.dom.DOMException(org.w3c.dom.DOMException.INUSE_ATTRIBUTE_ERR,
			  "Attribute in use by other element.");

		Attr	oldAttribute	= null;

		for (java.util.ListIterator i = attributes.listIterator(0); i.hasNext();)
		{
			Attr	attribute	= (Attr )i.next();

			if (attribute.getName().compareTo(newAttr.getName()) == 0)
			{
				attributes.remove(attribute);
				oldAttribute	= attribute;
				break;
			}
		}

		((Node )newAttr).setParent(this);
		attributes.addLast(newAttr);
		return oldAttribute;
	}

	//
	// Unimplemented Element members.
	//
	public edu.jhu.apl.patterns_class.dom.replacement.Attr getAttributeNodeNS(String namespaceURI, String localName)
	  { return null; }
	public String getAttributeNS(String namespaceURI, String localName) { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagNameNS(String tagName) { return null; }
	public boolean hasAttributeNS(String namespaceURI, String localName) { return false; }
	public void removeAttributeNS(String namespaceURI, String localName) {}
	public edu.jhu.apl.patterns_class.dom.replacement.Attr
	  setAttributeNodeNS(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr) { return null; }
	public void setAttributeNS(String namespaceURI, String localName, String value) {}
	public edu.jhu.apl.patterns_class.dom.replacement.Attr
	  setAttributeNS(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr) { return null; }
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList
	  getElementsByTagNameNS(String namespaceURI, String localName) { return null; }
	public void setIdAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr idAttr, boolean isId) {}
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {}
	public void setIdAttribute(String name, boolean isId) {}
	public org.w3c.dom.TypeInfo getSchemaTypeInfo() { return null; }



	//
	// Reimplemented Node members.
	//
	public edu.jhu.apl.patterns_class.dom.replacement.NamedNodeMap getAttributes()	{ return attributes; }
	public boolean hasAttributes()			{ return attributes.getLength() > 0; }
}
