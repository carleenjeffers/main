package edu.jhu.apl.patterns_class;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class XMLSerializer
{
	// Using Java Idiom for writing text to a file:
	// https://www.nayuki.io/page/good-java-idioms#writing-text-file
	java.io.File		file			= null;
	PrintWriter	writer			= null;
	int			indentationLevel	= 0;

	public XMLSerializer(String filename) throws java.io.FileNotFoundException
	{
		file		= new java.io.File(filename);
		writer      = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));
	}

	public void close() throws java.io.IOException
	{
		writer.close();
	}

	private void prettyIndentation() throws java.io.IOException
	{
		// Using Java Idiom "replace for loop with Stream"
		// https://medium.com/@cleanCompile/10-java-idioms-every-developer-should-know-by-now-00208336dec6
		IntStream.range(0, indentationLevel)
         .forEach(i -> writer.print("\t"));
	}

	//
	// Strategize Node data printing.
	// Strategize whitespace insertion.
	// Strategize output stream
	//
	public void serializePretty(edu.jhu.apl.patterns_class.dom.replacement.Node node) throws java.io.IOException
	{
		if (node instanceof edu.jhu.apl.patterns_class.dom.Document)
		{
			writer.print("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
			writer.print("\n");
			serializePretty(((edu.jhu.apl.patterns_class.dom.replacement.Document )node).getDocumentElement());
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Element)
		{
			prettyIndentation();
			writer.print("<" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName());

			int	attrCount	= 0;

			for (java.util.ListIterator i =
			  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getAttributes()).listIterator(0);
			  i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				serializePretty(attr);
				attrCount++;
			}

			if (attrCount > 0)
				writer.print(" ");

			if (!((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0).hasNext())
			{
				writer.print("/>");
				writer.print("\n");
			}
			else
			{
				writer.print(">");
				writer.print("\n");
				indentationLevel++;

				for (java.util.ListIterator i =
				  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0);
				  i.hasNext();)
				{
					edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

					if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					  child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
						serializePretty(child);
				}

				indentationLevel--;
				prettyIndentation();
				writer.print("</" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName() + ">");
				writer.print("\n");
			}
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Attr)
		{
			writer.print(" " + ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getName() + "=\"" +
			  ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getValue() + "\"");
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
		{
			prettyIndentation();
			writer.print(((edu.jhu.apl.patterns_class.dom.replacement.Text )node).getData());
			writer.print("\n");
		}
	}

	public void serializeMinimal(edu.jhu.apl.patterns_class.dom.replacement.Node node) throws java.io.IOException
	{
		if (node instanceof edu.jhu.apl.patterns_class.dom.Document)
		{
			writer.print("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
			serializeMinimal(((edu.jhu.apl.patterns_class.dom.replacement.Document )node).getDocumentElement());
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Element)
		{
			writer.print("<" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName());

			for (java.util.ListIterator i =
			  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getAttributes()).listIterator(0);
			  i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				serializeMinimal(attr);
			}

			if (!((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0).hasNext())
				writer.print("/>");
			else
			{
				writer.print(">");

				for (java.util.ListIterator i =
				  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0);
				  i.hasNext();)
				{
					edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

					if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					  child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
						serializeMinimal(child);
				}

				writer.print("</" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName() + ">");
			}
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Attr)
		{
			writer.print(" " + ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getName() + "=\"" +
			  ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getValue() + "\"");
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
		{
			writer.print(((edu.jhu.apl.patterns_class.dom.replacement.Text )node).getData());
		}
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

		// restructured using Java idiom CheckDontCatch
		// https://wiki.c2.com/?CheckDontCatch
		// only catching at top level (main function)
		try {
			doSerialize(args[0], args[1], document);
		} catch (java.io.IOException e)
		{
			System.out.println("Error writing file: " + e.getMessage());
			System.exit(0);
		}
		
	}

	private static void doSerialize(String filePretty, String fileMinimal, edu.jhu.apl.patterns_class.dom.replacement.Document document) throws java.io.IOException {

		// do check part of CheckDontCatch idiom
		Path prettyPath = Path.of(filePretty);
		Path minimalPath = Path.of(fileMinimal);

		if (!Files.exists(prettyPath) || !Files.exists(minimalPath)) {
			System.out.println("File does not exist. Exiting");
			return;
		}
		
		XMLSerializer	xmlSerializer	= new XMLSerializer(filePretty);
		xmlSerializer.serializePretty(document);
		xmlSerializer.close();
		xmlSerializer	= new XMLSerializer(fileMinimal);
		xmlSerializer.serializeMinimal(document);
		xmlSerializer.close();
	}
}
