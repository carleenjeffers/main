package edu.jhu.apl.patterns_class;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class XMLValidator
{
	private java.util.Vector<ValidChildren>	schema	= new java.util.Vector<ValidChildren>();

	//
	// Supercedes any existing description for this element.
	//
	public ValidChildren addSchemaElement(String element)
	{
		ValidChildren	schemaElement	= findSchemaElement(element);

		if (schemaElement != null)
			schema.remove(schemaElement);

		schema.add(schemaElement = new ValidChildren(element));
		return schemaElement;
	}

	public ValidChildren findSchemaElement(String element)
	{
		// Using Java Idiom "replace for loop with Stream"
		// https://medium.com/@cleanCompile/10-java-idioms-every-developer-should-know-by-now-00208336dec6
		return schema.stream()
        .filter(child -> 
            (child.getThisElement() == null && element == null) ||
            (child.getThisElement() != null && child.getThisElement().equals(element))
        )
        .findFirst()
        .orElse(null);
	}

	public boolean canRootElement(String newElement)
	{
		return canAddElement(null, newElement);
	}

	public boolean canAddElement(edu.jhu.apl.patterns_class.dom.replacement.Element element, String newElement)
	{
		ValidChildren	schemaElement	= findSchemaElement(element == null ? null : element.getTagName());

		return schemaElement == null ? true : schemaElement.childIsValid(newElement, false);
	}

	public boolean canAddText(edu.jhu.apl.patterns_class.dom.replacement.Element element)
	{
		ValidChildren	schemaElement	= findSchemaElement(element.getTagName());

		return schemaElement == null ? true : schemaElement.canHaveText();
	}

	public boolean canAddAttribute(edu.jhu.apl.patterns_class.dom.replacement.Element element, String newAttribute)
	{
		ValidChildren	schemaElement	= findSchemaElement(element.getTagName());

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
		ValidChildren	schemaElement	= xmlValidator.addSchemaElement(null);
		schemaElement.addValidChild("document", false);
		schemaElement	= xmlValidator.addSchemaElement("document");
		schemaElement.addValidChild("element", false);
		schemaElement	= xmlValidator.addSchemaElement("element");
		schemaElement.addValidChild("element", false);
		schemaElement.addValidChild("attribute", true);
		schemaElement.addValidChild("attribute2", true);
		schemaElement.setCanHaveText(true);

		edu.jhu.apl.patterns_class.dom.replacement.Document	document	=
		  new edu.jhu.apl.patterns_class.dom.Document();
		edu.jhu.apl.patterns_class.dom.replacement.Element	root		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Element	child		= null;
		edu.jhu.apl.patterns_class.dom.replacement.Attr		attr		= null;

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
			doSerialize(args[0], document);
		}
		catch (java.io.IOException e)
		{
			System.out.println("Error writing file.");
			e.printStackTrace();
		}
	}

	private static void doSerialize(String filename, edu.jhu.apl.patterns_class.dom.replacement.Document document) throws IOException {
		// do check part of CheckDontCatch idiom
		Path filePath = Path.of(filename);

		if (!Files.exists(filePath)) {
			System.out.println("File does not exist. Exiting");
			return;
		}

		XMLSerializer	xmlSerializer	= new XMLSerializer(filename);
		xmlSerializer.serializePretty(document);
		xmlSerializer.close();
	}
}

class ValidChildren
{
	private String				thisElement		= null;	// A value of null represents Document.
	private java.util.Vector<String>	validChildren		= new java.util.Vector<String>();
	private java.util.Vector<Boolean>	childIsAttribute	= new java.util.Vector<Boolean>();
	private boolean				_canHaveText		= false;

	public ValidChildren(String thisElement)		{ this.thisElement = thisElement; }

	public String	getThisElement()			{ return thisElement; }
	public boolean	canHaveText()				{ return _canHaveText; }
	public void	setCanHaveText(boolean _canHaveText)	{ this._canHaveText = _canHaveText; }

	public void	addValidChild(String child, boolean isAttribute)
	{
		if (childIsValid(child, isAttribute))
			return;

		validChildren.add(child);
		childIsAttribute.add(new Boolean(isAttribute));
	}

	public boolean	childIsValid(String child, boolean isAttribute)
	{
		// Using Java Idiom "replace for loop with Stream"
		// https://medium.com/@cleanCompile/10-java-idioms-every-developer-should-know-by-now-00208336dec6
		return IntStream.range(0, validChildren.size())
        .anyMatch(i ->
            childIsAttribute.elementAt(i).booleanValue() == isAttribute &&
            validChildren.elementAt(i).equals(child)
        );
	}
}
