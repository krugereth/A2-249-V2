//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

/**
 * The BadISBN13Exception class is an Exception subclass that represents an invalid ISBN-13 record.
 * It can be thrown when an ISBN-13 record is not properly formatted or does not meet certain validation criteria.
 */
public class BadISBN13Exception extends Exception{

    /**
     * Creates a new BadIsbn13Exception with the provided detail message.
     * @param message the detail message, which explains the cause of the exception.
     */
    public BadISBN13Exception(String message) {
        super(message);
    }

    /**
     * Creates a new BadIsbn13Exception with a default message.
     * The default message is "This record's ISBN is invalid."
     */
    public BadISBN13Exception() {
        super("This record's ISBN is invalid.");
    }
}
