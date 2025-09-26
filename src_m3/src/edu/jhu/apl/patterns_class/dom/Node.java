package edu.jhu.apl.patterns_class.dom;

import java.io.IOException;

import edu.jhu.apl.patterns_class.XMLSerializer;
import edu.jhu.apl.patterns_class.dom.replacement.ParentNode;

public class Node implements edu.jhu.apl.patterns_class.dom.replacement.Node
{
	private String		name		= null;
	private String		value		= null;
	private short		nodeType	= -1;
	private ParentNode		parent		= null;
	protected Document	document	= null;

	Node(String name, short type)
	{
		this.name	= name;
		nodeType	= type;
	}
	public void serializePretty(XMLSerializer.XMLSerializerContext ctx) throws IOException {}; //blank implementation
	public void serializeMinimal(XMLSerializer.XMLSerializerContext ctx) throws IOException {}; //blank implementation

	void setParent(ParentNode parent)							{ this.parent = parent; }

	//
	// Implemented Interface Members
	//
	public String getNodeName()							{ return name; }
	public String getNodeValue() throws org.w3c.dom.DOMException			{ return value; }
	public void setNodeValue(String nodeValue) throws org.w3c.dom.DOMException	{ value = nodeValue; }
	public short getNodeType()							{ return nodeType; }
	public edu.jhu.apl.patterns_class.dom.replacement.ParentNode getParentNode()		{ return parent; }
	public edu.jhu.apl.patterns_class.dom.replacement.Node getPreviousSibling()
	  { return (edu.jhu.apl.patterns_class.dom.replacement.Node )getSibling(-1);}
	public edu.jhu.apl.patterns_class.dom.replacement.Node getNextSibling()
	  { return (edu.jhu.apl.patterns_class.dom.replacement.Node )getSibling(1); }
	public edu.jhu.apl.patterns_class.dom.replacement.Document getOwnerDocument()	{ return document; }
	public boolean hasChildNodes()					{ return false; }
	public String getLocalName()					{ return name; }

	//
	// Unimplemented Interface Members
	//
	public void normalize() {}
	public boolean isSupported(String feature, String version)					{ return false; }
	public String getNamespaceURI()									{ return null; }
	public String getPrefix()									{ return null; }
	public void setPrefix(String prefix) throws org.w3c.dom.DOMException				{}
	public edu.jhu.apl.patterns_class.dom.replacement.Node cloneNode(boolean deep)			{ return null; }
	public boolean hasAttributes()									{ return false; }
	public edu.jhu.apl.patterns_class.dom.replacement.NamedNodeMap getAttributes()			{ return null; }
	public Object getUserData(String key)								{ return null; }
	public Object setUserData(String key, Object data, org.w3c.dom.UserDataHandler handler)		{ return null; }
	public Object getFeature(String feature, String version)					{ return null; }
	public boolean isEqualNode(edu.jhu.apl.patterns_class.dom.replacement.Node arg)			{ return false; }
	public String lookupNamespaceURI(String prefix)							{ return null; }
	public boolean isDefaultNamespace(String namespaceURI)						{ return false; }
	public String lookupPrefix(String namespaceURI)							{ return null; }
	public boolean isSameNode(edu.jhu.apl.patterns_class.dom.replacement.Node other)		{ return false; }
	public void setTextContent(String textContent)							{}
	public String getTextContent()									{ return null; }
	public short compareDocumentPosition(edu.jhu.apl.patterns_class.dom.replacement.Node other)	{ return (short )0; }
	public String getBaseURI()									{ return null; }

	//
	// Class Members
	//
	private Node getSibling(int direction)
	{
		if (parent == null)
			return null;

		// sanity check, should never happen
		if (!(parent instanceof ParentNode)) System.out.println("Invalid parent: parent must be composite");

		ParentNode parentNode = (ParentNode) parent;
		java.util.LinkedList	siblings	= (java.util.LinkedList )parentNode.getChildNodes();

		try
		{
			return (Node )siblings.get(siblings.indexOf(this) + direction);
		}
		catch (java.lang.IndexOutOfBoundsException e)
		{
			return null;
		}
	}
}
