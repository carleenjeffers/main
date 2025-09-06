package edu.jhu.apl.patterns_class.strategy;

// Concrete Serialization Strategy
public class MinimalSerializationStrategy implements NodeSerializationStrategy {
    @Override
    public void serialize(edu.jhu.apl.patterns_class.dom.replacement.Node node, java.io.BufferedWriter	writer) throws java.io.IOException
	{
		if (node instanceof edu.jhu.apl.patterns_class.dom.Document)
		{
			writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
			serialize(((edu.jhu.apl.patterns_class.dom.replacement.Document )node).getDocumentElement(), writer);
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Element)
		{
			writer.write("<" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName());

			for (java.util.ListIterator i =
			  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getAttributes()).listIterator(0);
			  i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				serialize(attr, writer);
			}

			if (!((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0).hasNext())
				writer.write("/>");
			else
			{
				writer.write(">");

				for (java.util.ListIterator i =
				  ((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0);
				  i.hasNext();)
				{
					edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					  (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

					if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					  child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
						serialize(child, writer);
				}

				writer.write("</" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName() + ">");
			}
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Attr)
		{
			writer.write(" " + ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getName() + "=\"" +
			  ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getValue() + "\"");
		}
		else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
		{
			writer.write(((edu.jhu.apl.patterns_class.dom.replacement.Text )node).getData());
		}
	}
}