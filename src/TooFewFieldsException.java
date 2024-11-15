//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * Exception that shows that a record contains too few fields.
 */
public class TooFewFieldsException extends Exception {

    /**
     * Constructs a new TooFewFieldsException with the specified message.
     * @param message the message to be included with the exception
     */

    public TooFewFieldsException(String message) {
        super(message);
    }

    /**
     * Constructs a new TooFewFieldsException with a default message.
     */

    public TooFewFieldsException() {
        super("This record contains too few fields.");
    }
}
