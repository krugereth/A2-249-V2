//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * Exception indicating that a record has an unknown or invalid genre.
 */
public class UnknownGenreException extends Exception {

    /**
     * Constructs a new UnknownGenreException with the provided message.
     * @param message the message for the exception
     */

    public UnknownGenreException(String message) {
        super(message);
    }

    /**
     * Constructs a new UnknownGenreException with the default message.
     */

    public UnknownGenreException() {
        super("This record's genre is invalid.");
    }
}
