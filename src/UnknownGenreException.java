// -----------------------------------------------------
// Assignment 3
// Written by: Sonali Patel - 40176580
// -----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * Exception indicating that a record has an unknown or invalid genre.
 */
public class UnknownGenreException extends Exception {

    /**
     * Constructs a new UnknownGenreException with the given message.
     * @param message the message to include with the exception
     */
    public UnknownGenreException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnknownGenreException with a default message.
     */
    public UnknownGenreException() {
        super("This record's genre is invalid.");
    }
}
