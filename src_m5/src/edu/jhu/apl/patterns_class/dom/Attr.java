package edu.jhu.apl.patterns_class.dom;

public class Attr extends Node implements edu.jhu.apl.patterns_class.dom.replacement.Attr
{
	Attr(String tagName, Document document)
	{
		super(tagName, org.w3c.dom.Node.ATTRIBUTE_NODE);
		this.document	= document;
	}

	Attr(String tagName, String value, Document document)
	{
		super(tagName, org.w3c.dom.Node.ATTRIBUTE_NODE);
		this.document	= document;
		setValue(value);
	}

	// Override relevant PrimitiveMethods
	@Override
	public void writeOpenTags(java.io.BufferedWriter writer) throws java.io.IOException {
		writer.write(" " + this.getName() + "=\"" + this.getValue() + "\"");
	}

	//
	// Implemented Attr members.
	//
	public String getName()
	{
		return getNodeName();
	}
	public String getValue()
	{
		return getNodeValue();
	}
	public void setValue(String value)
	{
		// TODO:  Check for readonly status.  NO_MODIFICATION_ALLOWED_ERR

		setNodeValue(value);
	}
	public edu.jhu.apl.patterns_class.dom.replacement.Element getOwnerElement()
	{
		return (Element )getParentNode();
	}

	//
	// Unimplemented Attr members.
	//
	public boolean getSpecified()	{ return true; }
	public boolean isId()		{ return false; }
	public org.w3c.dom.TypeInfo getSchemaTypeInfo() { return null; }
}
