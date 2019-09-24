package de.upb.sede.composition.graphs.nodes;

/**
 * This interface is extended by other node model Interfaces in this package.
 *
 * Nodes with this interface refer to a fieldname.
 */
interface IFieldNameAware {

    /**
     * Returns the field name that this node is referencing.
     * @return Referenced field name
     */
    String getFieldName();

}
