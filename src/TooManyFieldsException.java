// -----------------------------------------------------
// Assignment 3
// Written by: Sonali Patel - 40176580
// -----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * Exception indicating that a record contains too many fields.
 */
public class TooManyFieldsException extends Exception {

    /**
     * Constructs a new TooManyFieldsException with the given message.
     * @param message the message to include with the exception
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
