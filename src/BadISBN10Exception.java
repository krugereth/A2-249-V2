//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------
/**
 * The BadIsbn10Exception class is a subclass of Exception that handles errors related to invalid ISBN-10 records.
 * It is thrown when an ISBN-10 record fails to meet the required format or validation rules.
 */
public class BadISBN10Exception extends Exception{

    /**
     * Creates a new BadIsbn10Exception with the provided detail message.
     * @param message the detail message that explains the reason for the exception.
     */
    public BadISBN10Exception(String message) {
        super(message);
    }

    /**
     * Creates a new BadIsbn10Exception with a default detail message.
     * The default message is: "This record's ISBN is invalid."
     */
    public BadISBN10Exception() {
        super("This record's ISBN is invalid.");
    }
}
