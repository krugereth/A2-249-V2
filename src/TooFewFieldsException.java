// -----------------------------------------------------
// Assignment 3
// Written by: Sonali Patel - 40176580
// -----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * Exception indicating that a record contains too few fields.
 */
public class TooFewFieldsException extends Exception {

    /**
     * Constructs a new TooFewFieldsException with the given message.
     * @param message the message to include with the exception
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
