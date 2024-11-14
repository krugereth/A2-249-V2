//-----------------------------------------------------
//Assignment 3
//Written by: Sonali Patel - 40176580
//-----------------------------------------------------

/* Name and ID: Sonali Patel - 40176580
 * COMP249 Assignment #3
 * Due Date: 37/03/2023
 */

/**
 * The BadIsbn10Exception class is an Exception subclass that represents an invalid ISBN-10 record.
 * It can be thrown when an ISBN-10 record is not properly formatted or does not meet certain validation criteria.
 */
public class BadIsbn10Exception extends Exception{

    /**
     * Constructs a new BadIsbn10Exception with the specified detail message.
     * @param message the detail message. This message is used to describe the cause of the exception.
     */
    public BadIsbn10Exception(String message) {
        super(message);
    }

    /**
     * Constructs a new BadIsbn10Exception with a default detail message.
     * The default message is "This record's ISBN is invalid."
     */
    public BadIsbn10Exception() {
        super("This record's ISBN is invalid.");
    }
}
