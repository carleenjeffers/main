package edu.jhu.apl.patterns_class.dom;

import edu.jhu.apl.patterns_class.XMLSerializer;
import edu.jhu.apl.patterns_class.iterator.*;

// Composite
public class Element extends Node implements edu.jhu.apl.patterns_class.dom.replacement.Element
{
	private NamedNodeMap		attributes	= null;
	private NodeList	nodes		= null;

	Element(String tagName, Document document)
	{
		super(tagName, org.w3c.dom.Node.ELEMENT_NODE);
		this.document	= document;
		attributes	= new NamedNodeMap(document);
		nodes		= new NodeList();
	}

	public void serializePretty(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.prettyIndentation();
		ctx.writer.write("<" + this.getTagName());

		int	attrCount	= 0;
		NamedNodeMapAggregate attrAggregate = new NamedNodeMapAggregate(this.getAttributes());
		NamedNodeMapIterator attrIterator = attrAggregate.createIterator();
		while (attrIterator.hasNext()) {
			edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				(edu.jhu.apl.patterns_class.dom.replacement.Node )attrIterator.next();
			attr.serializePretty(ctx);
			attrCount++;
		}

		if (attrCount > 0)
			ctx.writer.write(" ");
		
		NodeListAggregate childListAggregate = new NodeListAggregate(this.getChildNodes());
		NodeListIterator childListIterator = childListAggregate.createIterator();

		if (!childListIterator.hasNext()) {
			ctx.writer.write("/>");
			ctx.writer.write("\n");
		} else
		{
			ctx.writer.write(">");
			ctx.writer.write("\n");
			ctx.incrementIndentationLevel();

			while (childListIterator.hasNext()) {
				edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					(edu.jhu.apl.patterns_class.dom.replacement.Node )childListIterator.next();

				child.serializePretty(ctx);
			}
			ctx.decrementIndentationLevel();
			ctx.prettyIndentation();
			ctx.writer.write("</" + this.getTagName() + ">");
			ctx.writer.write("\n");
		}
	}

	public void serializeMinimal(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.writer.write("<" + this.getTagName());

		NodeListAggregate attrListAggregate = new NodeListAggregate(this.getAttributes());
		NodeListIterator attrListIterator = attrListAggregate.createIterator();
		while (attrListIterator.hasNext()) {
			edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				(edu.jhu.apl.patterns_class.dom.replacement.Node )attrListIterator.next();
			attr.serializeMinimal(ctx);
		}

		NodeListAggregate childListAggregate = new NodeListAggregate(this.getChildNodes());
		NodeListIterator childListIterator = childListAggregate.createIterator();

		if (!childListIterator.hasNext()) {
			ctx.writer.write("/>");
		} else {
			ctx.writer.write(">");

			while (childListIterator.hasNext()) {
				edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					(edu.jhu.apl.patterns_class.dom.replacement.Node )childListIterator.next();
				child.serializeMinimal(ctx);
			}

			ctx.writer.write("</" + this.getTagName() + ">");
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
