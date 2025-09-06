package edu.jhu.apl.patterns_class.schema;

public class SchemaManager {
    protected java.util.Vector<ValidChildren>	schema;

    public SchemaManager(java.util.Vector<ValidChildren>	schema) {
        this.schema = schema;
    }
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
		for (int i = 0; i < schema.size(); i++)
			if ((schema.elementAt(i).getThisElement() == null && element == null) ||
			 (schema.elementAt(i).getThisElement()!=null && schema.elementAt(i).getThisElement().compareTo(element)==0))
				return schema.elementAt(i);

		return null;
	}
}