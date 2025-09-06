package edu.jhu.apl.patterns_class.decorator;

import edu.jhu.apl.patterns_class.*;
import edu.jhu.apl.patterns_class.schema.ValidChildren;
import edu.jhu.apl.patterns_class.dom.replacement.*;
import edu.jhu.apl.patterns_class.exception.InvalidSchemaOperationException;

public class DocumentValidationDecorator extends NodeValidationDecorator implements Document {
    public DocumentValidationDecorator(Node node, java.util.Vector<ValidChildren> schema) {
        super(node, schema);
    }

    // Add validation before appending child to document
    @Override
    public Node appendChild(Node newChild) throws org.w3c.dom.DOMException {
        // validation checks
        if (!canRootElement(newChild.getNodeName())) {
            throw new InvalidSchemaOperationException();
        }
        return decoratedNode.appendChild(newChild);
    }

    // private helper
    protected boolean canRootElement(String newElement)
	{
		return canAddElement(null, newElement);
	}

    
}