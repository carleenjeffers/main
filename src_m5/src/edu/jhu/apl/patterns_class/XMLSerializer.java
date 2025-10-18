package edu.jhu.apl.patterns_class;

import java.io.IOException;

public class XMLSerializer
{
	java.io.File		file			= null;
	java.io.BufferedWriter	writer			= null;

	public XMLSerializer(String filename) throws java.io.FileNotFoundException
	{
		file		= new java.io.File(filename);
		writer		= new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file)));
	}

	// nested class to provide context for Node serialization
	public class XMLSerializerContext {
		public java.io.BufferedWriter writer;
		private int indentationLevel = 0;

		public XMLSerializerContext(java.io.BufferedWriter writer) {
			this.writer = writer;
		}

		public void prettyIndentation() throws IOException {
			for (int i = 0; i < indentationLevel; i++) {
				writer.write("  ");
			}
		}

		public void incrementIndentationLevel() {
			indentationLevel++;
		}

		public void decrementIndentationLevel() {
			indentationLevel--;
		}
	}

	public void close() throws java.io.IOException
	{
		writer.close();
	}

	//
	// Strategize Node data printing.
	// Strategize whitespace insertion.
	// Strategize output stream
	//
	public void serializePretty(edu.jhu.apl.patterns_class.dom.replacement.Node node) throws java.io.IOException {
		XMLSerializerContext ctx = new XMLSerializerContext(writer);
		node.serializePretty(ctx);
	}

	public void serializeMinimal(edu.jhu.apl.patterns_class.dom.replacement.Node node) throws java.io.IOException {
		node.serializeMinimal(writer);
	}

	public static void main(String args[])
	{
		if (args.length < 2)
		{
			System.out.println("No output filenames provided.");
			System.exit(0);
		}

		//
		// Create tree of this document:
		// <? xml version="1.0" encoding="UTF-8"?>
		// <document>
		//   <element attribute="attribute value"/>
		//   <element/>
		//   <element attribute="attribute value" attribute2="attribute2 value">
		//     Element Value
		//   </element>
		//   <element>
		//   </element>
		// </document>
		//
		edu.jhu.apl.patterns_class.dom.replacement.Document	document	=
		  new edu.jhu.apl.patterns_class.dom.Document();
		edu.jhu.apl.patterns_class.dom.replacement.Element	root		= document.createElement("document");
		document.appendChild(root);

		edu.jhu.apl.patterns_class.dom.replacement.Element	child		= document.createElement("element");
		edu.jhu.apl.patterns_class.dom.replacement.Attr		attr		= document.createAttribute("attribute");
		attr.setValue("attribute value");
		child.setAttributeNode(attr);
		root.appendChild(child);

		child	= document.createElement("element");
		root.appendChild(child);

		child	= document.createElement("element");
		child.setAttribute("attribute", "attribute value");
		child.setAttribute("attribute2", "attribute2 value");
		edu.jhu.apl.patterns_class.dom.replacement.Text		text		= document.createTextNode("Element Value");
		child.appendChild(text);
		root.appendChild(child);

		child	= document.createElement("element");
		root.appendChild(child);

		//
		// Serialize
		//
		try
		{
			XMLSerializer	xmlSerializer	= new XMLSerializer(args[0]);
			xmlSerializer.serializePretty(document);
			xmlSerializer.close();
			xmlSerializer	= new XMLSerializer(args[1]);
			xmlSerializer.serializeMinimal(document);
			xmlSerializer.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error writing file.");
			e.printStackTrace();
		}
	}
}
