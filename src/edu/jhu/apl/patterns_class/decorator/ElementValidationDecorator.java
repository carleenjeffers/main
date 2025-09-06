package edu.jhu.apl.patterns_class.decorator;

import edu.jhu.apl.patterns_class.schema.ValidChildren;
import edu.jhu.apl.patterns_class.dom.replacement.*;

public class ElementValidationDecorator extends NodeValidationDecorator implements Element {
    protected final Element decoratedElement;

    public ElementValidationDecorator(Node node, java.util.Vector<ValidChildren> schema) {
        super(node, schema);

        // ensure node is element
        if (!(node instanceof Element)) {
            throw new IllegalArgumentException("ElementValidationDecorator can only wrap Elements.");
        }

        this.decoratedElement = (Element) node;
    }

    // element specific override functions
    @Override
    public String getAttribute(String name) { return decoratedElement.getAttribute(name); };
	@Override
    public Attr getAttributeNode(String name){ return decoratedElement.getAttributeNode(name); };
	@Override
    public NodeList getElementsByTagName(String tagName) { return decoratedElement.getElementsByTagName(tagName); };
	@Override
    public String getTagName() { return decoratedElement.getTagName(); };
	@Override
    public boolean hasAttribute(String name) { return decoratedElement.hasAttribute(name); };
	@Override
    public void removeAttribute(String name) { decoratedElement.removeAttribute(name); };
	@Override
    public Attr removeAttributeNode(Attr oldAttr) { return decoratedElement.removeAttributeNode(oldAttr); };
	@Override
    public void setAttribute(String name, String value) { decoratedElement.setAttribute(name, value); };
	@Override
    public Attr setAttributeNode(Attr newAttr) { return decoratedElement.setAttributeNode(newAttr); };

	//
	// Unimplemented Element members.
	//
    @Override
	public Attr getAttributeNodeNS(String namespaceURI, String localName) { return decoratedElement.getAttributeNodeNS(namespaceURI, localName); };
	@Override
    public String getAttributeNS(String namespaceURI, String localName) { return decoratedElement.getAttributeNS(namespaceURI, localName); };
	@Override
    public NodeList getElementsByTagNameNS(String tagName) { return decoratedElement.getElementsByTagNameNS(tagName); };
    @Override
    public boolean hasAttributeNS(String namespaceURI, String localName) { return decoratedElement.hasAttributeNS(namespaceURI, localName); };
	@Override
    public void removeAttributeNS(String namespaceURI, String localName) { decoratedElement.removeAttributeNS(namespaceURI, localName); };
	@Override
    public Attr setAttributeNodeNS(Attr newAttr) { return decoratedElement.setAttributeNodeNS(newAttr); };
	@Override
    public void setAttributeNS(String namespaceURI, String localName, String value) { decoratedElement.setAttributeNS(namespaceURI, localName, value); };
    @Override
    public Attr setAttributeNS(Attr newAttr) { return decoratedElement.setAttributeNS(newAttr);}
    @Override
    public NodeList getElementsByTagNameNS(String namespaceURI, String localName) { return decoratedElement.getElementsByTagNameNS(namespaceURI, localName);}
    @Override
    public void setIdAttributeNode(Attr idAttr, boolean isId) { decoratedElement.setIdAttributeNode(idAttr, isId); }
    @Override
    public void setIdAttributeNS(String namespaceURI, String localName, boolean isId) { decoratedElement.setIdAttributeNS(namespaceURI, localName, isId); }
    @Override
    public void setIdAttribute(String name, boolean isId) { decoratedElement.setIdAttribute(name, isId); }
    @Override
    public org.w3c.dom.TypeInfo getSchemaTypeInfo() { return decoratedElement.getSchemaTypeInfo(); }
    

}