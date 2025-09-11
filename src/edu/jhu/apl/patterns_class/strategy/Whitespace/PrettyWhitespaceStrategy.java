package edu.jhu.apl.patterns_class.strategy;

// Concrete Whitespace Strategy
public class PrettyWhitespaceStrategy implements WhitespaceStrategy {
    int indentationLevel = 0;

    private void prettyIndentation(java.io.BufferedWriter	writer) throws java.io.IOException
    {
        for (int i = 0; i < indentationLevel; i++)
            writer.write("\t");
    }

    @Override
    public void serialize(edu.jhu.apl.patterns_class.dom.replacement.Node node, java.io.BufferedWriter	writer) throws java.io.IOException {
        if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Document)
        {
            writer.write("<? xml version=\"1.0\" encoding=\"UTF-8\"?>");
            writer.write("\n");
            serialize(((edu.jhu.apl.patterns_class.dom.replacement.Document )node).getDocumentElement(), writer);
        }
        else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Element)
        {
            prettyIndentation(writer);
            writer.write("<" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName());

            int	attrCount	= 0;

            for (java.util.ListIterator i =
            ((edu.jhu.apl.patterns_class.dom.NodeList )node.getAttributes()).listIterator(0);
            i.hasNext();)
            {
                edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
                (edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

                serialize(attr, writer);
                attrCount++;
            }

            if (attrCount > 0)
                writer.write(" ");

            if (!((edu.jhu.apl.patterns_class.dom.NodeList )node.getChildNodes()).listIterator(0).hasNext())
            {
                writer.write("/>");
                writer.write("\n");
            }
            else
            {
                writer.write(">");
                writer.write("\n");
                indentationLevel++;

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

                indentationLevel--;
                prettyIndentation(writer);
                writer.write("</" + ((edu.jhu.apl.patterns_class.dom.replacement.Element )node).getTagName() + ">");
                writer.write("\n");
            }
        }
        else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Attr)
        {
            writer.write(" " + ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getName() + "=\"" +
            ((edu.jhu.apl.patterns_class.dom.replacement.Attr )node).getValue() + "\"");
        }
        else if (node instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
        {
            prettyIndentation(writer);
            writer.write(((edu.jhu.apl.patterns_class.dom.replacement.Text )node).getData());
            writer.write("\n");
        }
    }
}