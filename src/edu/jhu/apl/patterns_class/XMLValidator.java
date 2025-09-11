package edu.jhu.apl.patterns_class;

import edu.jhu.apl.patterns_class.factory.*;
import edu.jhu.apl.patterns_class.strategy.*;
import edu.jhu.apl.patterns_class.schema.*;

import edu.jhu.apl.patterns_class.exception.InvalidSchemaOperationException;

public class XMLValidator
{
	SchemaManager schemaManager = new SchemaManager(new java.util.Vector<ValidChildren>());

	//
	// Optional for schema implementation:
	//
	// public static boolean canValue(edu.jhu.apl.patterns_class.dom.replacement.Attribute attribute, String value)
	// {
	// }

	public static void main(String args[])
	{
		if (args.length < 1)
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
		// Schema for this document:
		// document contains:  element
		// element contains:  element
		// element contains attributes:  attribute, attribute2
		//
		XMLValidator	xmlValidator	= new XMLValidator();
		ValidChildren	schemaElement	= xmlValidator.schemaManager.addSchemaElement(null);
		schemaElement.addValidChild("document", false);
		schemaElement	= xmlValidator.schemaManager.addSchemaElement("document");
		schemaElement.addValidChild("element", false);
		schemaElement	= xmlValidator.schemaManager.addSchemaElement("element");
		schemaElement.addValidChild("element", false);
		schemaElement.addValidChild("attribute", true);
		schemaElement.addValidChild("attribute2", true);
		schemaElement.setCanHaveText(true);

		// use validated DOM factory
		DOMFactory factory = new ValidatedDOMFactory(xmlValidator.schemaManager);

		edu.jhu.apl.patterns_class.dom.replacement.Document	document	=
		  factory.createDocument();
		edu.jhu.apl.patterns_class.dom.replacement.Element	root		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Element	child		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Attr		attr		= null;

		// demonstrate proper validation via Decorator
		try {
			root = factory.createElement("element", document);
			document.appendChild(root);
			System.out.print("Failed to catch invalid operation!");
		} catch (InvalidSchemaOperationException e) {
			System.out.println("Successfully caught invalid operation with message: " + e.getMessage());
		}
		
		try {
			root = factory.createElement("document", document);
			document.appendChild(root);

			child = factory.createElement("element", document);
			attr	= factory.createAttribute("attribute", document);
			attr.setValue("attribute value");
			child.setAttributeNode(attr); // child validates canAddAttribute

			root.appendChild(child); // root validates canAddElement

			child = factory.createElement("element", document);
			root.appendChild(child);

			child = factory.createElement("element", document);
			child.setAttribute("attribute", "attribute value");
			child.setAttribute("attribute2", "attribute2 value");

			edu.jhu.apl.patterns_class.dom.replacement.Text text = factory.createTextNode("Element Value", document);
			child.appendChild(text);
			root.appendChild(child);

			// empty element
			child = factory.createElement("element", document);
			root.appendChild(child);

		} catch (InvalidSchemaOperationException e) {
			System.out.println("Attempted invalid schema operation: " + e.getMessage());
			System.exit(0);
		}

		//
		// Serialize
		//
		try
		{
			XMLSerializer	xmlSerializer	= new XMLSerializer(args[0], new PrettyWhitespaceStrategy(), new FileOutputStreamStrategy());
			xmlSerializer.serialize(document);
			xmlSerializer.close();

			// demonstrate console output stream strategy
			xmlSerializer = new XMLSerializer(args[0], new MinimalWhitespaceStrategy(), new ConsoleOutputStreamStrategy());
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
