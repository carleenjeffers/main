package edu.jhu.apl.patterns_class;

import edu.jhu.apl.patterns_class.factory.*;
import edu.jhu.apl.patterns_class.strategy.*;
import edu.jhu.apl.patterns_class.schema.*;

import edu.jhu.apl.patterns_class.exception.InvalidSchemaOperationException;

public class XMLValidator
{
	SchemaManager schemaManager = new SchemaManager(new java.util.Vector<ValidChildren>());

	public boolean canAddText(edu.jhu.apl.patterns_class.dom.replacement.Element element)
	{
		ValidChildren	schemaElement	= schemaManager.findSchemaElement(element.getTagName());

		return schemaElement == null ? true : schemaElement.canHaveText();
	}

	public boolean canAddAttribute(edu.jhu.apl.patterns_class.dom.replacement.Element element, String newAttribute)
	{
		ValidChildren	schemaElement	= schemaManager.findSchemaElement(element.getTagName());

		return schemaElement == null ? true : schemaElement.childIsValid(newAttribute, true);
	}

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

		edu.jhu.apl.patterns_class.dom.replacement.Document	document	=
		  DOMFactory.createValidatedDocument();
		edu.jhu.apl.patterns_class.dom.replacement.Element	root		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Element	child		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Attr		attr		= null;

		try {
			root	= document.createValidatedElement("document");
			document.appendChild(root);

		} catch (InvalidSchemaOperationException e) {
			System.out.println("Attempted invalid schema operation: " + e.getMessage());
			System.exit(0);
		}

		if (xmlValidator.canRootElement("document"))
		{
			root	= document.createElement("document");
			document.appendChild(root);
		}
		else
		{
			System.out.println("Attempted invalid schema operation.");
			System.exit(0);
		}

		if (xmlValidator.canAddElement(root, "element"))
		{
			child	= document.createElement("element");

			if (xmlValidator.canAddAttribute(child, "attribute"))
			{
				attr	= document.createAttribute("attribute");
				attr.setValue("attribute value");
				child.setAttributeNode(attr);
			}
			else
			{
				System.out.println("Attempted invalid schema operation.");
				System.exit(0);
			}

			root.appendChild(child);
		}
		else
		{
			System.out.println("Attempted invalid schema operation.");
			System.exit(0);
		}

		if (xmlValidator.canAddElement(root, "element"))
		{
			child	= document.createElement("element");
			root.appendChild(child);
		}
		else
		{
			System.out.println("Attempted invalid schema operation.");
			System.exit(0);
		}

		if (xmlValidator.canAddElement(root, "element"))
		{
			child	= document.createElement("element");

			if (xmlValidator.canAddAttribute(child, "attribute"))
				child.setAttribute("attribute", "attribute value");
			else
			{
				System.out.println("Attempted invalid schema operation.");
				System.exit(0);
			}

			if (xmlValidator.canAddAttribute(child, "attribute2"))
				child.setAttribute("attribute2", "attribute2 value");
			else
			{
				System.out.println("Attempted invalid schema operation.");
				System.exit(0);
			}

			if (xmlValidator.canAddText(child))
			{
				edu.jhu.apl.patterns_class.dom.replacement.Text text = document.createTextNode("Element Value");
				child.appendChild(text);
			}
			else
			{
				System.out.println("Attempted invalid schema operation.");
				System.exit(0);
			}

			root.appendChild(child);
		}
		else
		{
			System.out.println("Attempted invalid schema operation.");
			System.exit(0);
		}

		if (xmlValidator.canAddElement(root, "element"))
		{
			child	= document.createElement("element");
			root.appendChild(child);
		}
		else
		{
			System.out.println("Attempted invalid schema operation.");
			System.exit(0);
		}

		//
		// Serialize
		//
		try
		{
			XMLSerializer	xmlSerializer	= new XMLSerializer(args[0], new PrettySerializationStrategy(), new FileOutputStreamStrategy());
			xmlSerializer.serialize(document);
			xmlSerializer.close();

			// demonstrate console output stream strategy
			xmlSerializer = new XMLSerializer(args[0], new PrettySerializationStrategy(), new ConsoleOutputStreamStrategy());
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
