package de.upb.sede.exceptions;

/**
 * TODO do we need this exception type?
 * Exception which is thrown when accessing unassigned field of data objects.
 */
public class UnassignedFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public UnassignedFieldException(){

    }

    /**
     * Constructor that sets the message field of exception.
     * The message is created using dataobject.getClass().getSimpleName() and fieldname.
     */
    public UnassignedFieldException(Object dataobject, String fieldname) {
        super(String.format("Accessed unassigned field:{} of an instance of class{}.", fieldname, dataobject.getClass().getSimpleName()));
    }


}