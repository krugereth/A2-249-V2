//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * The BadPriceException class is a subclass of Exception that signifies an invalid price value.
 * It may be thrown when a price value is incorrectly formatted or fails to meet specific validation standards.
 */

public class BadPriceException extends Exception{
    /**
    * Creates a new BadPriceException with the provided detail message.
    * @param message the detail message, which explains the reason for the exception.
    */

public BadPriceException(String message) {

        super(message);

    }

    /**
     * Creates a new BadPriceException with a default detail message.
     * The default message is "The price in this record is invalid."
     */

    public BadPriceException() {

        super("This record's price is invalid.");

    }
}

