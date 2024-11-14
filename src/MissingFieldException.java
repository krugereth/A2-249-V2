// -----------------------------------------------------
// Assignment 3
// Written by: Sonali Patel - 40176580
// -----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * Exception indicating that a record is missing a required field.
 */
public class MissingFieldException extends Exception {

    /**
     * Constructs a new MissingFieldException with the given message.
     * @param message the message to include with the exception
     */
    public MissingFieldException(String message) {
        super(message);
    }

    /**
     * Constructs a new MissingFieldException with a default message.
     */
    public MissingFieldException() {
        super("This record is missing a field.");
    }
}

