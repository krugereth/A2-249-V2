//-----------------------------------------------------
//Assignment 3
//Written by: Sonali Patel - 40176580
//-----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * The BadPriceException class is an Exception subclass that represents an invalid price value.
 * It can be thrown when a price value is not properly formatted or does not meet certain validation criteria.
 */

public class BadPriceException extends Exception{
    /**
     * Constructs a new BadPriceException with the specified detail message.
     * @param message the detail message. This message is used to describe the cause of the exception.
     */
    public BadPriceException(String message) {

        super(message);

    }

    /**
     * Constructs a new BadPriceException with a default detail message.
     * The default message is "This record's price is invalid."
     */
    public BadPriceException() {

        super("This record's price is invalid.");

    }
}

