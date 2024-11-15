//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * The BadYearException class is a subclass of Exception that signifies an invalid year value.
 * It may be thrown when a year is improperly formatted or does not satisfy specific validation requirements.
 */


public class BadYearException extends Exception{

    /**
     * Constructs a new BadYearException with the provided detail message.
     * @param message the detail message that describes the reason for the exception.
     */

    public BadYearException(String message) {

        super(message);

    }

    /**
     * Constructs a new BadYearException with a default message.
     * The default message is "The year in this record is invalid."
     */

    public BadYearException() {

        super("This record's year is invalid.");

    }
}
