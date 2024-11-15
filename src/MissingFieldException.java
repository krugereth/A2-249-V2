//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * Exception that shows that a record is missing a required field.
 */
public class MissingFieldException extends Exception {

    /**
     * Constructs a new MissingFieldException with the provided message.
     * @param message the message to be associated with the exception
     */

    public MissingFieldException(String message) {
        super(message);
    }

    /**
     * Constructs a new MissingFieldException with the default message.
     */

    public MissingFieldException() {
        super("This record is missing a field.");
    }
}

