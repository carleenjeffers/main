package edu.jhu.apl.patterns_class;

import edu.jhu.apl.patterns_class.iterator.*;
import edu.jhu.apl.patterns_class.dom.replacement.*;;

public class XMLSerializer
{
	java.io.File		file			= null;
	java.io.BufferedWriter	writer			= null;
	int			indentationLevel	= 0;

	public XMLSerializer(String filename) throws java.io.FileNotFoundException
	{
		file		= new java.io.File(filename);
		writer		= new java.io.BufferedWriter(new java.io.OutputStreamWriter(new java.io.FileOutputStream(file)));
	}

	public void close() throws java.io.IOException
	{
		writer.close();
	}

	private void prettyIndentation() throws java.io.IOException
	{
		for (int i = 0; i < indentationLevel; i++)
			writer.write("\t");
	}

	//
	// Strategize Node data printing.
	// Strategize whitespace insertion.
	// Strategize output stream
	//
	// todo- read through and refactor
	public void serializePretty(Node rootNode) throws java.io.IOException {
		if (rootNode instanceof edu.jhu.apl.patterns_class.dom.Document) {
			writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			rootNode = ((Document) rootNode).getDocumentElement();
		}

		// Use your NodeIterator
		NodeIterator iterator = new NodeIterator(rootNode);

		// Stack to track open elements for indentation and closing
		java.util.Stack<Element> elementStack = new java.util.Stack<>();

		while (iterator.hasNext()) {
			Node currentNode = iterator.next();

			// Pop the stack if we've moved up the tree
			while (!elementStack.isEmpty() && !isParentOf(elementStack.peek(), currentNode)) {
				indentationLevel--;
				prettyIndentation();
				writer.write("</" + elementStack.pop().getTagName() + ">\n");
			}

			if (currentNode instanceof Element) {
				Element elem =
						(Element) currentNode;

				prettyIndentation();
				writer.write("<" + elem.getTagName());

				// Write attributes
				NamedNodeMapIterator attrIterator = new NamedNodeMapIterator(elem.getAttributes());
				while (attrIterator.hasNext()) {
					Attr attr =
							(Attr) attrIterator.next();
					writer.write(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
				}

				// Check if this element has child Elements or Text nodes
				boolean hasChildElementsOrText = false;
				NodeList children = elem.getChildNodes();
				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					if (child instanceof Element ||
						child instanceof Text) {
						hasChildElementsOrText = true;
						break;
					}
				}

				if (hasChildElementsOrText) {
					writer.write(">\n");
					elementStack.push(elem);
					indentationLevel++;
				} else {
					writer.write("/>\n");
				}
			} else if (currentNode instanceof Text) {
				prettyIndentation();
				writer.write(((Text) currentNode).getData());
				writer.write("\n");
			}
		}

		// Close any remaining open tags
		while (!elementStack.isEmpty()) {
			indentationLevel--;
			prettyIndentation();
			writer.write("</" + elementStack.pop().getTagName() + ">\n");
		}
	}


	private boolean isParentOf(Element parent,
                           Node child) {
		Node current = child.getParentNode();
		while (current != null) {
			if (current == parent)
				return true;
			current = current.getParentNode();
		}
		return false;
	}

	public void serializeMinimal(Node rootNode) throws java.io.IOException {
		if (rootNode instanceof edu.jhu.apl.patterns_class.dom.Document) {
			writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
			rootNode = ((Document) rootNode).getDocumentElement();
		}

		NodeIterator iterator =
			new NodeIterator(rootNode);

		java.util.Stack<Element> elementStack = new java.util.Stack<>();

		while (iterator.hasNext()) {
			Node currentNode = iterator.next();

			// Close tags if we're moving up in the tree
			while (!elementStack.isEmpty() && !isParentOf(elementStack.peek(), currentNode)) {
				writer.write("</" + elementStack.pop().getTagName() + ">");
			}

			if (currentNode instanceof Element) {
				Element elem =
					(Element) currentNode;

				writer.write("<" + elem.getTagName());
				NodeList attrs = elem.getAttributes();
				for (int i = 0; i < attrs.getLength(); i++) {
					Attr attr =
						(Attr) attrs.item(i);
					writer.write(" " + attr.getName() + "=\"" + attr.getValue() + "\"");
				}

				NodeList children = elem.getChildNodes();
				boolean hasChildren = false;

				for (int i = 0; i < children.getLength(); i++) {
					Node child = children.item(i);
					if (child instanceof Element ||
						child instanceof Text) {
						hasChildren = true;
						break;
					}
				}

				if (!hasChildren) {
					writer.write("/>");
				} else {
					writer.write(">");
					elementStack.push(elem);  // We'll close it later
				}

			} else if (currentNode instanceof Text) {
				writer.write(((Text) currentNode).getData());
			}
		}

		// Close any remaining open tags
		while (!elementStack.isEmpty()) {
			writer.write("</" + elementStack.pop().getTagName() + ">");
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
		Document	document	=
		  new edu.jhu.apl.patterns_class.dom.Document();
		Element	root		= document.createElement("document");
		document.appendChild(root);

		Element	child		= document.createElement("element");
		Attr		attr		= document.createAttribute("attribute");
		attr.setValue("attribute value");
		child.setAttributeNode(attr);
		root.appendChild(child);

		child	= document.createElement("element");
		root.appendChild(child);

		child	= document.createElement("element");
		child.setAttribute("attribute", "attribute value");
		child.setAttribute("attribute2", "attribute2 value");
		Text		text		= document.createTextNode("Element Valu");
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
