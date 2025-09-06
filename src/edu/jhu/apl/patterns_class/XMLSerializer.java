package edu.jhu.apl.patterns_class;

import edu.jhu.apl.patterns_class.factory.*;
import edu.jhu.apl.patterns_class.strategy.*;

// Serves as Context for Serialization and Whitespace
public class XMLSerializer
{

	java.io.File		file			= null;
	java.io.BufferedWriter	writer			= null;

	private NodeSerializationStrategy serializationStrategy;
	private OutputStreamStrategy outputStrategy;

	public XMLSerializer(String filename, NodeSerializationStrategy serializationStrategy, OutputStreamStrategy outputStrategy) throws java.io.FileNotFoundException, java.io.IOException
	{
		file		= new java.io.File(filename);
		this.writer = outputStrategy.createOutputWriter(file);
		this.serializationStrategy = serializationStrategy;
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
	
	// function to use serialization strategy
	public void serialize(edu.jhu.apl.patterns_class.dom.replacement.Node node)  throws java.io.IOException {
		serializationStrategy.serialize(node, this.writer);
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
		  DOMFactory.createValidatedDocument();
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
			XMLSerializer	xmlSerializer	= new XMLSerializer(args[0], new PrettySerializationStrategy(), new FileOutputStreamStrategy());
			xmlSerializer.serialize(document);
			xmlSerializer.close();
			xmlSerializer	= new XMLSerializer(args[1], new MinimalSerializationStrategy(), new FileOutputStreamStrategy());
			xmlSerializer.serialize(document);
			xmlSerializer.close();
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error writing file.");
			e.printStackTrace();
		}
	}
}
