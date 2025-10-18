package edu.jhu.apl.patterns_class.dom;

import edu.jhu.apl.patterns_class.XMLSerializer;
import edu.jhu.apl.patterns_class.XMLTokenizer;
import edu.jhu.apl.patterns_class.builder.DOMDirectorSingleton;

public class ElementProxy extends Node implements edu.jhu.apl.patterns_class.dom.replacement.Element {
    private edu.jhu.apl.patterns_class.dom.Element realElement;
    private int startToken;
    private int endToken;
    private String tagName;
    private boolean childrenLoaded = false;
    private XMLTokenizer tokenizer;

    public ElementProxy(String tagName, int tokenNum, edu.jhu.apl.patterns_class.dom.Document document, XMLTokenizer tokenizer) {
        super(tagName, org.w3c.dom.Node.ELEMENT_NODE);
        this.document	= document;
        this.startToken = tokenNum;
        this.tagName = tagName;
        this.tokenizer = tokenizer;
    }

    public void setEndToken(int tokenNum) {
        this.endToken = tokenNum;
    }

    @Override
	public void writeOpenTags(java.io.BufferedWriter writer) throws java.io.IOException {
		setRealElement();
        realElement.writeOpenTags(writer);
	}

	@Override
	public void writeAttributes(java.io.BufferedWriter writer) throws java.io.IOException {
        setRealElement();
        realElement.writeAttributes(writer);
	}

	@Override
	public void writeChildren(java.io.BufferedWriter writer) throws java.io.IOException {
        setRealElement();
        realElement.writeChildren(writer);
	}

	@Override
	public void writeClosingTags(java.io.BufferedWriter writer) throws java.io.IOException {
		if (!((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0).hasNext())
			writer.write("/>");
		else
		{
			writer.write("</" + this.getTagName() + ">");
		}
	}

	@Override
	public void formatStart(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.prettyIndentation();
	}

	@Override
	public void writeAttributesPretty(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		int	attrCount	= 0;

		for (java.util.ListIterator i =
			((edu.jhu.apl.patterns_class.dom.NodeList )this.getAttributes()).listIterator(0);
			i.hasNext();)
		{
			edu.jhu.apl.patterns_class.dom.replacement.Node	attr =
				(edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

			attr.serializePretty(ctx);
			attrCount++;
		}

		if (attrCount > 0)
			ctx.writer.write(" ");
	}

	@Override
	public void writeChildrenPretty(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		if (((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0).hasNext()) {
			ctx.writer.write(">");
			ctx.writer.write("\n");
			ctx.incrementIndentationLevel();

			for (java.util.ListIterator i =
				((edu.jhu.apl.patterns_class.dom.NodeList )this.getChildNodes()).listIterator(0);
				i.hasNext();)
			{
				edu.jhu.apl.patterns_class.dom.replacement.Node	child =
					(edu.jhu.apl.patterns_class.dom.replacement.Node )i.next();

				if (child instanceof edu.jhu.apl.patterns_class.dom.replacement.Element ||
					child instanceof edu.jhu.apl.patterns_class.dom.replacement.Text)
					child.serializePretty(ctx);
			}

			ctx.decrementIndentationLevel();
			ctx.prettyIndentation();
		}
	}

	@Override
	public void formatEnd(XMLSerializer.XMLSerializerContext ctx) throws java.io.IOException {
		ctx.writer.write("\n");
	}


    private void setRealElement() {
        if (realElement == null) {
            realElement = (edu.jhu.apl.patterns_class.dom.Element)document.createElement(this.tagName);
        }
    }

    @Override
    public edu.jhu.apl.patterns_class.dom.replacement.Node appendChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild)
	  throws org.w3c.dom.DOMException
	{
        setRealElement();
        return realElement.appendChild(newChild);
    }

    private void loadChildren() {
        setRealElement();
        if (childrenLoaded) return;
		DOMDirectorSingleton director = DOMDirectorSingleton.getInstance();
        System.out.println("calling build from " + startToken + " tp " + endToken);
        director.buildDOMTree(tokenizer, true, startToken, endToken, this);

        this.childrenLoaded = true;
    }

    public String getAttribute(String name) {
        loadChildren();
        return realElement.getAttribute(name);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.Attr getAttributeNode(String name) {
        loadChildren();
        return realElement.getAttributeNode(name);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagName(String tagName) {
        loadChildren();
        return realElement.getElementsByTagName(tagName);
    };
	public String getTagName() {
        return this.tagName;
    };
	public boolean hasAttribute(String name) {
        loadChildren();
        return realElement.hasAttribute(name);
    };
	public void removeAttribute(String name) {
        loadChildren();
        realElement.removeAttribute(name);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.Attr removeAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr oldAttr) {
        loadChildren();
        return realElement.removeAttributeNode(oldAttr);
    };
	public void setAttribute(String name, String value) {
        loadChildren();
        realElement.setAttribute(name,value);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.Attr setAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr) {
        loadChildren();
        return realElement.setAttributeNode(newAttr);
    };

	//
	// Unimplemented Element members.
	//
	public edu.jhu.apl.patterns_class.dom.replacement.Attr getAttributeNodeNS(String namespaceURI, String localName) {
        loadChildren();
        return realElement.getAttributeNodeNS(namespaceURI, localName);
    };
	public String getAttributeNS(String namespaceURI, String localName) {
        loadChildren();
        return realElement.getAttributeNS(namespaceURI, localName);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagNameNS(String tagName) {
        loadChildren();
        return realElement.getElementsByTagNameNS(tagName);
    };
	public boolean hasAttributeNS(String namespaceURI, String localName) {
        loadChildren();
        return realElement.hasAttributeNS(namespaceURI, localName);
    };
	public void removeAttributeNS(String namespaceURI, String localName) {
        loadChildren();
        realElement.removeAttributeNS(namespaceURI, localName);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.Attr setAttributeNodeNS(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr) {
        loadChildren();
        return realElement.setAttributeNodeNS(newAttr);
    };
	public void setAttributeNS(String namespaceURI, String localName, String value) {
        loadChildren();
        realElement.setAttributeNS(namespaceURI, localName, value);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.Attr setAttributeNS(edu.jhu.apl.patterns_class.dom.replacement.Attr newAttr) {
        loadChildren();
        return realElement.setAttributeNS(newAttr);
    };
	public edu.jhu.apl.patterns_class.dom.replacement.NodeList getElementsByTagNameNS(String namespaceURI, String localName) {
        loadChildren();
        return realElement.getElementsByTagNameNS(namespaceURI, localName);
    };
	public void setIdAttributeNode(edu.jhu.apl.patterns_class.dom.replacement.Attr idAttr, boolean isId) {
        loadChildren();
        realElement.setIdAttributeNode(idAttr, isId);
    };
	public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) {
        loadChildren();
        realElement.setIdAttributeNS(namespaceURI, localName,isId);
    };
	public void setIdAttribute(String name, boolean isId) {
        loadChildren();
        realElement.setIdAttribute(name, isId);
    };
	public org.w3c.dom.TypeInfo getSchemaTypeInfo() {
        loadChildren();
        return realElement.getSchemaTypeInfo();
    };



	//
	// Reimplemented Node members.
	//
	public edu.jhu.apl.patterns_class.dom.replacement.NamedNodeMap getAttributes() {
        loadChildren();
        return realElement.getAttributes();
    };
	public boolean hasAttributes() {
        loadChildren();
        return realElement.hasAttributes();
    };
}
