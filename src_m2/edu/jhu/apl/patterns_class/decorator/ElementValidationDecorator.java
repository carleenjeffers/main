package edu.jhu.apl.patterns_class.decorator;

import edu.jhu.apl.patterns_class.schema.*;
import edu.jhu.apl.patterns_class.dom.replacement.*;
import edu.jhu.apl.patterns_class.exception.InvalidSchemaOperationException;

// Concrete Decorator
public class ElementValidationDecorator extends NodeValidationDecorator implements Element {
    protected final Element decoratedElement;

    public ElementValidationDecorator(Node node, SchemaManager schemaManager) {
        super(node, schemaManager);

        // ensure node is element
        if (!(node instanceof Element)) {
            throw new IllegalArgumentException("ElementValidationDecorator can only wrap Elements.");
        }

        this.decoratedElement = (Element) node;
    }

    // add validation to setAttributeNode
    @Override
    public Attr setAttributeNode(Attr newAttr) {
        if (!canAddAttribute(newAttr.getName())) {
            throw new InvalidSchemaOperationException("Cannot add input attribute");
        }

        return decoratedElement.setAttributeNode(newAttr);
    };

    @Override
    public void setAttribute(String name, String value) {
        if (!canAddAttribute(name)) {
            throw new InvalidSchemaOperationException("Cannot set input attribute");
        }

        decoratedElement.setAttribute(name, value);
    }

    @Override
    public Node	appendChild(edu.jhu.apl.patterns_class.dom.replacement.Node newChild) throws org.w3c.dom.DOMException {
        short type = newChild.getNodeType();

        switch (type) {
            case org.w3c.dom.Node.ELEMENT_NODE:
                if (!canAddElement(decoratedElement, newChild.getNodeName())) {
                    throw new InvalidSchemaOperationException("Cannot add input element");
                }
                break;
            case org.w3c.dom.Node.TEXT_NODE:
                if (!canAddText()) {
                    throw new InvalidSchemaOperationException("Cannot add input text");
                }
                break;
            default:
                break;
        }

        return decoratedElement.appendChild(newChild);
    }

    // validation helpers
    private boolean canAddAttribute(String newAttribute)
	{
		ValidChildren	schemaElement	= schemaManager.findSchemaElement(decoratedElement.getTagName());

		return schemaElement == null ? true : schemaElement.childIsValid(newAttribute, true);
	}

    private boolean canAddText()
	{
		ValidChildren	schemaElement	= schemaManager.findSchemaElement(decoratedElement.getTagName());

		return schemaElement == null ? true : schemaElement.canHaveText();
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