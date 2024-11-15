//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * Exception that shows that a record contains too many fields.
 */
public class TooManyFieldsException extends Exception {

    /**
     * Constructs a new TooManyFieldsException with the specified message.
     * @param message the message to associate with the exception
     */

    public TooManyFieldsException(String message) {
        super(message);
    }

    /**
     * Constructs a new TooManyFieldsException with a default message.
     */

    public TooManyFieldsException() {
        super("This record contains too many fields.");
    }
}
