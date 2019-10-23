package de.upb.sede;

/**
 * This interface is extended by other model Interfaces that contain or refer to a fieldname.
 *
 */
public interface IFieldContainer {

    // TODO create a specific fieldreference type instead of referencing to a name.
    /**
     * Returns the field name that is being refered at.
     * @return Referenced field name
     */
    String getFieldName();

}
