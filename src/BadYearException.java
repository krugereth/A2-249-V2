//-----------------------------------------------------
//Assignment 3
//Written by: Sonali Patel - 40176580
//-----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * The BadYearException class is an Exception subclass that represents an invalid year value.
 * It can be thrown when a year value is not properly formatted or does not meet certain validation criteria.
 */

public class BadYearException extends Exception{

    /**
     * Constructs a new BadYearException with the specified detail message.
     * @param message the detail message. This message is used to describe the cause of the exception.
     */
    public BadYearException(String message) {

        super(message);

    }

    /**
     * Constructs a new BadYearException with a default detail message.
     * The default message is "This record's year is invalid."
     */
    public BadYearException() {

        super("This record's year is invalid.");

    }
}
