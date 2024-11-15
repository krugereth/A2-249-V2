/**
 * BookSorter class that performs part 1, part 2 and part 3 methods as instructed
 * Assignment 3
 * Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
 *
 * This program is a book sorting program that reads a bunch of files storing information about books, identifies the different attributes of
 * the book records (title, authors, price, isbn, genre and year) and separates the syntactically and semantically correct records by genre.
 * This program also allows the user to navigate through the records and view them as the invalid ones were already removed.
 */

import java.io.*;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class BookSorter {

    // Static variables are declared to keep same count through the program
    private static int CCBcountCopy;
    private static int HCBcountCopy;
    private static int MTVcountCopy;
    private static int MRBcountCopy;
    private static int NEBcountCopy;
    private static int OTRcountCopy;
    private static int SSMcountCopy;
    private static int TPAcountCopy;

    public static void main(String[] args) {

        //Each part methods are called
        do_part1();
        do_part2();
        do_part3();

    }

    /**
     * * This method is responsible for validating syntax and partitioning book records based on genre.
     */
    public static void do_part1() {

        //Print the welcome message
        System.out.print("Welcome to the Concordia Book Sorting Program! \n");

        //declare Scanner object and PrintWriter objects that will read from the input file and write in the syntax error file, respectively
        Scanner fileScanner = null;
        PrintWriter pw = null;

        //Open and create the txt files and catch any errors in the try catch block
        try {
            fileScanner = new Scanner(new FileInputStream("part1_input_file_names.txt"));
            pw = new PrintWriter(new FileOutputStream("syntax_error_file.txt", true));

        } catch (FileNotFoundException e) {
            System.out.println("Error has been found: File not found: ");
            System.exit(0); //terminate
        }

        int fileCount = 0; // number of files that will be scanned
        int numberOfFiles = fileScanner.nextInt(); //read the integer at the first line of the file = number of files
        fileScanner.nextLine();

        //The count of syntax and semantic errors
        int invalidGenreCount = 0, tooManyFieldsCount = 0, tooFewFieldsCount = 0, missingFieldCount = 0;
        int CCBcount = 0, HCBcount = 0, MTVcount = 0, MRBcount = 0, NEBcount = 0, OTRcount = 0, SSMcount = 0, TPAcount = 0;

        //Field types are declared
        String title, authors, price, isbn, genre, year;

        //while loop to go through each line of the input file
        while (fileCount < numberOfFiles) {

            fileCount++; //increment after each iteration
            String fileName = fileScanner.nextLine(); //file name to be read next
            Scanner recordScanner; //declare new Scanner object to read data in the book files

            try {
                recordScanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + fileName);
               continue;
            }

            //while loop to go through each line of the book record file
            while (recordScanner.hasNextLine()) {

                String record = recordScanner.nextLine(); //read the record
                String originalRecord = record; //store the whole line for use after

                //read the title of each record (with or without " ")
                if (record.charAt(0) == '"') {
                    title = "\"" + record.substring(1, record.indexOf("\"", 2)) + "\"";
                    record = record.substring(record.indexOf('\"', 3));
                } else {
                    title = record.substring(0, record.indexOf(','));
                    record = record.substring(record.indexOf(',', 2));
                }

                //read the other fields of the record using substring
                authors = record.substring(record.indexOf(",") + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                price = record.substring(record.indexOf(',') + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                isbn = record.substring(record.indexOf(',') + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                genre = record.substring(record.indexOf(',') + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                year = record.substring(record.indexOf(',') + 1);

                //count the number of fields in the record
                char ch = ',';
                int charCount = 0;
                String rec = originalRecord;

                if (originalRecord.charAt(0) == '\"') {
                    rec = originalRecord.substring(originalRecord.indexOf('\"', 3));
                }

                for (int i = 0; i < rec.length(); i++) {
                    if (rec.charAt(i) == ch)
                        charCount++;
                }

                int numFields = charCount + 1;

                //If else statement to parse through the different type of errors in a try catch block
                try {
                    if (numFields > 6) {
                        throw new TooManyFieldsException("Error: Too Many Fields");
                    } else if (numFields < 6) {
                        throw new TooFewFieldsException("Error: Too Few Fields");
                    } else if (genre.equals("CCB") || genre.equals("HCB") || genre.equals("MTV") || genre.equals("MRB") ||
                            genre.equals("NEB") || genre.equals("OTR") || genre.equals("SSM") || genre.equals("TPA")) {
                        throw new UnknownGenreException("Error: Unknown Genre");
                    } else if (title.equals(" ") || authors.equals(" ") || price.equals(" ") || isbn.equals(" ") || genre.equals(" ") || year.equals(" ")) {
                        throw new MissingFieldException("Error: Missing Field");
                    }
                } catch (TooManyFieldsException | TooFewFieldsException | UnknownGenreException | MissingFieldException e) {
                    e.getMessage();
                }

                //If else statements to write the error type in the txt files
                if (title.equals(" ") || authors.equals(" ") || price.equals(" ") || isbn.equals(" ") || genre.equals(" ") || year.equals(" ") || year.isEmpty()) {
                    missingFieldCount++;
                    pw.println("\nsyntax error in file: " + fileName
                            + "\n===================="
                            + "\nError: missing field"
                            + "\nRecord: " + originalRecord);
                    pw.flush();
                } else if (numFields > 6) {
                    tooManyFieldsCount++;
                    pw.println("\nsyntax error in file: " + fileName
                            + "\n===================="
                            + "\nError: too many fields"
                            + "\nRecord: " + originalRecord);
                    pw.flush();
                } else if (numFields < 6) {
                    tooFewFieldsCount++;
                    pw.println("\nsyntax error in file: " + fileName
                            + "\n===================="
                            + "\nError: too few fields"
                            + "\nRecord: " + originalRecord);
                    pw.flush();
                } else {

                    PrintWriter genreFileWriter = null;

                    //String x declared for clearer code
                    String x = title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year;

                    //switch case statement to send each record to its appropriate location based on genre
                    switch (genre) {

                        case "CCB":
                            CCBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Cartoons_Comics_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "HCB":
                            HCBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Hobbies_Collectibles_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "MTV":
                            MTVcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Movies_TV.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "MRB":
                            MRBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Music_Radio_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "NEB":
                            NEBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Nostalgia_Eclectic_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "OTR":
                            OTRcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Old_Time_Radio.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "SSM":
                            SSMcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Sports_Sports_Memorabilia.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "TPA":
                            TPAcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Trains_Planes_Automobiles.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                System.out.println("File not found: " + e.getMessage());
                            }
                            genreFileWriter.println(x);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        default:
                            invalidGenreCount++;
                            pw.println("\nsyntax error in file: " + fileName
                                    + "\n===================="
                                    + "\nError: invalid genre"
                                    + "\nRecord: " + originalRecord);
                            pw.flush();
                    }
                }
            }
            recordScanner.close();
        }

        //Prints the results
        pw.println("\nThere are " + tooManyFieldsCount + " records with too many fields."
                + "\nThere are " + tooFewFieldsCount + " records with too few fields."
                + "\nThere are " + missingFieldCount + " records with one or more missing fields."
                + "\nThere are " + invalidGenreCount + " records with an invalid genre.");

        pw.println("\nIn total, there are " + (tooManyFieldsCount + tooFewFieldsCount + missingFieldCount + invalidGenreCount) + " records with syntax errors,"
                + " and " + (CCBcount + HCBcount + MTVcount + MRBcount + NEBcount + OTRcount + SSMcount + TPAcount) + " syntactically valid records.");

        pw.flush();
        pw.close();
        fileScanner.close();

        //Assign the count values
        CCBcountCopy = CCBcount;
        HCBcountCopy = HCBcount;
        MTVcountCopy = MTVcount;
        MRBcountCopy = MRBcount;
        NEBcountCopy = NEBcount;
        OTRcountCopy = OTRcount;
        SSMcountCopy = SSMcount;
        TPAcountCopy = TPAcount;
    }

    /**
     * This method is responsible for validating semantics, loading each genre file into arrays of Book objects,
     * and then serializing each array of Book objects into binary files.
     */
    public static void do_part2() {

        // Declare a Scanner object for reading from the input file and PrintWriter objects for writing to the syntax error file.
        PrintWriter pw = null;
        Scanner fileScanner = null;

        try {
            String fileName = "part2_input_file_names.txt";
            fileScanner = new Scanner(new FileInputStream(fileName));
            pw = new PrintWriter(new FileOutputStream("semantic_error_file.txt",true));

        } catch (FileNotFoundException e) {
            System.out.println("File not found. The program will now terminate. ");
            System.exit(0); //terminate
        }

        int fileCount = 0;
        int numFiles = fileScanner.nextInt();
        fileScanner.nextLine(); // Goes to next line

        String title, authors, isbn, genre;
        double price;
        int year;

        // Track the count of semantic errors and the number of valid book records.
        int isbn10ErrorCount = 0, isbn13ErrorCount = 0, priceErrorCount = 0, yearErrorCount = 0;
        int ccb = 0, hcb = 0, mtv = 0, mrb = 0, neb = 0, otr = 0, ssm = 0, tpa = 0;


        // Create Book arrays for each genre with a length equal to the number of syntactically valid records.
        Book[] CCBbookArray = new Book[CCBcountCopy];
        Book[] HCBbookArray = new Book[HCBcountCopy];
        Book[] MTVbookArray = new Book[MTVcountCopy];
        Book[] MRBbookArray = new Book[MRBcountCopy];
        Book[] NEBbookArray = new Book[NEBcountCopy];
        Book[] OTRbookArray = new Book[OTRcountCopy];
        Book[] SSMbookArray = new Book[SSMcountCopy];
        Book[] TPAbookArray = new Book[TPAcountCopy];

        // Use a while loop to iterate through each line of the input file.
        while (fileCount < numFiles) {

            fileCount++;

            String fileName = fileScanner.nextLine();
            Scanner recordScanner = null;

            try {
                recordScanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("File not found:  " + fileName);
            }

            // Process each line/record in the file
            while (recordScanner.hasNextLine()) {

                String record = recordScanner.nextLine();
                String originalRecord = record;

                if (record.charAt(0) == '\"') {
                    title = record.substring(0, record.indexOf('\"', 3)) + "\"";
                    record = record.substring(record.indexOf('\"', 3));
                } else {
                    title = record.substring(0, record.indexOf(','));
                    record = record.substring(record.indexOf(',', 2));
                }

                // Parse authors, price, ISBN, genre, and year from the record
                authors = record.substring(record.indexOf(',') + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                price = Double.parseDouble(record.substring(1, record.indexOf(',', 2)));
                record = record.substring(record.indexOf(',', 2));
                isbn = record.substring(1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                genre = record.substring(1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                year = Integer.parseInt(record.substring(1));

                // Validate record fields and log errors if found
                try {
                    if (!isValidISBN(isbn)) {
                        pw.println("\nsemantic error in file: " + fileName
                                + "\n===================="
                                + "\nError: invalid ISBN"
                                + "\nRecord: " + originalRecord);
                        pw.flush();
                        if (isbn.length() == 10) {
                            isbn10ErrorCount++;
                            throw new BadISBN10Exception("Error: This record's ISBN is invalid.");
                        }
                        if (isbn.length() == 13) {
                            isbn13ErrorCount++;
                            throw new BadISBN13Exception("Error: This record's ISBN is invalid.");
                        }
                    } else if (!isValidPrice(price)) {
                        pw.println("\nsemantic error in file: " + fileName
                                + "\n===================="
                                + "\nError: invalid price"
                                + "\nRecord: " + originalRecord);
                        priceErrorCount++;
                        pw.flush();
                        throw new BadPriceException("Error: This record's price is invalid.");
                    } else if (!isValidYear(year)) {
                        pw.println("\nsemantic error in file: " + fileName
                                + "\n===================="
                                + "\nError: invalid year"
                                + "\nRecord: " + originalRecord);
                        yearErrorCount++;
                        pw.flush();
                        throw new BadYearException("Error: This record's year of publication is invalid.");
                    } else {
                        // Create a new Book object if the record is valid
                        Book newBook = new Book(title, authors, price, isbn, genre, year);

                        // Initialize PrintWriter for writing to genre-specific files
                        PrintWriter genreFileWriter = null;

                        // Assign valid records to their respective genre arrays and files
                        switch (newBook.getGenre()) {

                            case "CCB":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("CartoonsComics.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                CCBbookArray[ccb] = newBook;
                                genreFileWriter.println(CCBbookArray[ccb]);
                                ccb++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "HCB":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("HobbiesCollectibles.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                HCBbookArray[hcb] = newBook;
                                genreFileWriter.println(HCBbookArray[hcb]);
                                hcb++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "MTV":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("MoviesTV.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                MTVbookArray[mtv] = newBook;
                                genreFileWriter.println(MTVbookArray[mtv]);
                                mtv++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "MRB":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("MusicRadioBooks.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                MRBbookArray[mrb] = newBook;
                                genreFileWriter.println(MRBbookArray[mrb]);
                                mrb++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "NEB":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("NostalgiaEclecticBooks.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                NEBbookArray[neb] = newBook;
                                genreFileWriter.println(NEBbookArray[neb]);
                                neb++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "OTR":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("OldTimeRadio.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                OTRbookArray[otr] = newBook;
                                genreFileWriter.println(OTRbookArray[otr]);
                                otr++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "SSM":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("SportsSportsMemorabilia.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                SSMbookArray[ssm] = newBook;
                                genreFileWriter.println(SSMbookArray[ssm]);
                                ssm++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;

                            case "TPA":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("TrainsPlanesAutomobiles.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    System.out.println("File not found: " + e.getMessage());
                                }
                                TPAbookArray[tpa] = newBook;
                                genreFileWriter.println(TPAbookArray[tpa]);
                                tpa++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;
                        }
                    }
                } catch (BadISBN10Exception | BadISBN13Exception | BadPriceException | BadYearException e) {
                    e.getMessage();
                }
            }
            recordScanner.close();
        }

        // Display summary of semantic errors
        pw.println("\nThere are " + isbn10ErrorCount + " records with an invalid ISBN-10."
                + "\nThere are " + isbn13ErrorCount + " records with an invalid ISBN-13."
                + "\nThere are " + priceErrorCount + " records with an invalid price."
                + "\nThere are " + yearErrorCount + " records with an invalid publication year.");
        pw.println("\nIn total, there are " + (isbn10ErrorCount + isbn13ErrorCount + priceErrorCount + yearErrorCount) + " semantic errors.");

        pw.flush();
        pw.close();
        fileScanner.close();

        //remove empty cells from the initial arrays
        removeEmptyCells(CCBbookArray);
        removeEmptyCells(HCBbookArray);
        removeEmptyCells(MTVbookArray);
        removeEmptyCells(MRBbookArray);
        removeEmptyCells(NEBbookArray);
        removeEmptyCells(OTRbookArray);
        removeEmptyCells(SSMbookArray);
        removeEmptyCells(TPAbookArray);

        //serialize each array and write it in a binary file
        try {
            serialize(CCBbookArray, "Cartoons_Comics.csv.ser");
            serialize(HCBbookArray, "Hobbies_Collectibles.csv.ser");
            serialize(MTVbookArray, "Movies_TV_Books.csv.ser");
            serialize(MRBbookArray, "Music_Radio_Books.csv.ser");
            serialize(NEBbookArray, "Nostalgia_Eclectic_Books.csv.ser");
            serialize(OTRbookArray, "Old_Time_Radio_Books.csv.ser");
            serialize(SSMbookArray, "Sports_Sports_Memorabilia.csv.ser");
            serialize(TPAbookArray, "Trains_Planes_Automobiles.csv.ser");
        } catch (IOException e) {
            System.out.println("IO Exception found:  " + e.getMessage());
        }
    }

    /**
      * This method validates an ISBN based on its checksum formula.
      * An ISBN can be either 10 digits (ISBN-10) or 13 digits (ISBN-13).
      * For ISBN-10, the checksum is calculated using the formula:
      * (10x1 + 9x2 + 8x3 + 7x4 + 6x5 + 5x6 + 4x7 + 3x8 + 2x9 + 1x10) % 11 == 0 * For ISBN-13, the checksum is calculated using the formula:
      * (x1 + 3x2 + x3 + 3x4 + x5 + 3x6 + x7 + 3x8 + x9 + 3x10 + x11 + 3x12 + x13) % 10 == 0
      * @param isbn the ISBN string to validate
      * @return true if the ISBN is valid; false otherwise
     */
    public static boolean isValidISBN(String isbn) {

        if (isbn.length() != 10 && isbn.length() != 13)
            return false;

        if (isbn.length() == 10) {
            int sum = 0;

            for (int i = 0; i < 10; i++) {
                char c = isbn.charAt(i);

                if (!Character.isDigit(c))
                    return false; // ISBN must consist only of digits, thus return false is letter appears

                int digit = Character.getNumericValue(c);
                sum += (10 - i) * digit;
            }
            return (sum % 11 == 0);

        } else {
            int sum = 0;

            for (int i = 0; i < 13; i++) {
                char c = isbn.charAt(i);

                if (!Character.isDigit(c))
                    return false;

                int digit = Character.getNumericValue(c);
                sum += ((i % 2 == 0) ? digit : 3 * digit);
            }
            return (sum % 10 == 0);
        }
    }

    /**
     * This method determines if the given price is valid.
     *
     * @param price the price to validate
     * @return true if the price is non-negative, false otherwise
     */
    public static boolean isValidPrice(double price) {
        return price >= 0;
    }

    /**
     * This method determines if the given year is valid.
     *
     * @param year the year to validate
     * @return true if the year is [1995, 2010], false otherwise
     */
    public static boolean isValidYear(int year) {
        if (year < 1995 || year > 2010)
            return false;
        else
            return true;
    }

    /** * Serializes an array of Book objects into a file.
     * @param array the array of Book objects to serialize
     * @param fileName the name of the file where the Book objects will be saved
     * @throws IOException if an error occurs during the file writing process
     */
    public static void serialize(Book[] array, String fileName) throws IOException {
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        //first line states how many records this file contains
        oos.writeInt(array.length);

        //loop through each element of the array and write it to the ObjectOutputStream
        for (int i = 0; i < array.length; i++) {
            //if the current element is null, break out of the loop
            oos.writeObject(array[i]);
        }

        oos.close();
        fos.close();
    }

    /**
     * This method is in charge of reading the binary files, deserialize the array objects in each file, and
     * then provide an interacive program to allow the user to navigate the arrays.
     */
    public static void do_part3() {

        Scanner fileScanner = null;

        // Attempt to open the file that contains the list of serialized file names
        try {
            fileScanner = new Scanner(new FileInputStream("part3_input_file_names.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

        // Read the number of files listed in the input file
        int numFiles = fileScanner.nextInt(); //read user input
        fileScanner.nextLine(); //skip to next line
        int fileCount = 0;

        // Declare arrays to store the deserialized Book objects for each file
        Book[] CCBarray = null;
        Book[] HCBarray = null;
        Book[] MTVarray = null;
        Book[] MRBarray = null;
        Book[] NEBarray = null;
        Book[] OTRarray = null;
        Book[] SSMarray = null;
        Book[] TPAarray = null;

        // Define the file names as string variables for easy reference
        String ccb = "Cartoons_Comics.csv.ser";
        String hcb = "Hobbies_Collectibles.csv.ser";
        String mtv = "Movies_TV_Books.csv.ser";
        String mrb = "Music_Radio_Books.csv.ser";
        String neb = "Nostalgia_Eclectic_Books.csv.ser";
        String otr = "Old_Time_Radio_Books.csv.ser";
        String ssm = "Sports_Sports_Memorabilia.csv.ser";
        String tpa = "Trains_Planes_Automobiles.csv.ser";

        //while loop to go through each line of the input file
        while (fileCount < numFiles) {

            String fileName = fileScanner.nextLine();

            // Switch statement to deserialize each file one-by-one and store the information in the appropriate array
            switch (fileName) {

                case "Cartoons_Comics.csv.ser":
                    try {
                        CCBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Hobbies_Collectibles.csv.ser":
                    try {
                        HCBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Movies_TV_Books.csv.ser":
                    try {
                        MTVarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Music_Radio_Books.csv.ser":
                    try {
                        MRBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Nostalgia_Eclectic_Books.csv.ser":
                    try {
                        NEBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Old_Time_Radio_Books.csv.ser":
                    try {
                        OTRarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Sports_Sports_Memorabilia.csv.ser":
                    try {
                        SSMarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
                case "Trains_Planes_Automobiles.csv.ser":
                    try {
                        TPAarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("IO Exception: " + e.getMessage());
                    }
                    break;
            }
            fileCount++; //increment at each iteration
        }

        String recordFileSelected = ccb; // Default file

        Scanner keyboard = new Scanner(System.in); // New Scanner object that reads user input
        String userInput = null;

        // Do-while loop allows the code to run until user enters x or X
        do {
            System.out.print("\n\n---------------------------------"
                    + "\n            Main Menu"
                    + "\n---------------------------------"
                    + "\n   v   View the selected file: " + recordFileSelected
                    + "\n   s   Select a file to view"
                    + "\n   x   Exit"
                    + "\n---------------------------------"
                    + "\n"
                    + "\nEnter Your Choice: ");

            userInput = keyboard.next();

            //if user enters invalid input, leave this iteration and start over
            if (!(userInput.equals("v") || userInput.equals("s") || userInput.equals("x") || userInput.equals("X"))) {
                System.out.println("Invalid Input. Please try again.");
                continue;
            }

            //set the value of 'length' to the number of records in each array
            int length = 0;
            if (recordFileSelected.equals(ccb))
                length = CCBcountCopy;
            else if (recordFileSelected.equals(hcb))
                length = HCBcountCopy;
            else if (recordFileSelected.equals(mtv))
                length = MTVcountCopy;
            else if (recordFileSelected.equals(mrb))
                length = MRBcountCopy;
            else if (recordFileSelected.equals(neb))
                length = NEBcountCopy;
            else if (recordFileSelected.equals(otr))
                length = OTRcountCopy;
            else if (recordFileSelected.equals(ssm))
                length = SSMcountCopy;
            else if (recordFileSelected.equals(tpa))
                length = TPAcountCopy;

            //terminate if user wishes to
            if (userInput.equals("x") || userInput.equals("X")) {
                System.out.println("Thank you for using the Book Sorting Program. The program will now exit.");
                System.exit(0);
            } else if (userInput.equals("s")) { //display sub-menu if user enters 's'
                System.out.print("\n---------------------------------"
                        + "\n          File Sub-Menu"
                        + "\n---------------------------------"
                        + "\n  1  Cartoons_Comics.csv.ser\t\t(" + CCBcountCopy + " records)"
                        + "\n  2  Hobbies_Collectibles.csv.ser\t(" + HCBcountCopy + " records)"
                        + "\n  3  Movies_TV_Books.csv.ser\t\t(" + MTVcountCopy + " records)"
                        + "\n  4  Music_Radio_Books.csv.ser\t\t(" + MRBcountCopy + " records)"
                        + "\n  5  Nostalgia_Eclectic_Books.csv.ser\t(" + NEBcountCopy + " records)"
                        + "\n  6  Old_Time_Radio_Books.csv.ser\t(" + OTRcountCopy + " records)"
                        + "\n  7  Sports_Sports_Memorabilia.csv.ser\t(" + SSMcountCopy + " records)"
                        + "\n  8  Trains_Planes_Automobiles.csv.ser\t(" + TPAcountCopy + " records)"
                        + "\n  9  Exit"
                        + "\n---------------------------------"
                        + "\n"
                        + "\nEnter Your Choice: ");

                // Read user's choice
                int recordFileInput = keyboard.nextInt();

                // Selects genre based on user recordFileInput
                if (recordFileInput == 1) {
                    recordFileSelected = ccb;
                } else if (recordFileInput == 2) {
                    recordFileSelected = hcb;
                } else if (recordFileInput == 3) {
                    recordFileSelected = mtv;
                } else if (recordFileInput == 4) {
                    recordFileSelected = mrb;
                } else if (recordFileInput == 5) {
                    recordFileSelected = neb;
                } else if (recordFileInput == 6) {
                    recordFileSelected = otr;
                } else if (recordFileInput == 7) {
                    recordFileSelected = ssm;
                } else if (recordFileInput == 8) {
                    recordFileSelected = tpa;
                } else if (recordFileInput == 9) {
                    System.out.println("Thank you for using our program ! The program will now terminate");
                    System.exit(0);
                } else {
                    System.out.println("Your input is invalid. Please select the proper number.");
                }
            }
            if (userInput.equals("v")) { //if user enters 'v', display the following message
                System.out.println("Now viewing " + recordFileSelected + " (" + length + " records)");

                // Prompt the user to enter how many records they wish to display and the starting indexOfRecord
                System.out.print("\nPlease enter how many consecutive records you wish to display: ");
                int n = keyboard.nextInt();

                System.out.print("\nNow, enter the index of the record you want to start from: ");
                int indexOfRecord = keyboard.nextInt();

                // Handle positive or negative values of 'n'
                if (n == 0) { // If n is 0, skip the display process

                } else if (n > 0) {
                    try {
                        // Any if statement that will trigger, it will print the records inside the array.
                        if (recordFileSelected.equals(ccb)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(CCBarray[indexOfRecord - 1]);
                                if (indexOfRecord == 27) {
                                    throw new EOFException(); // Will display EOF message if indexOfRecord = 21
                                }
                            }
                        } else if (recordFileSelected.equals(hcb)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(HCBarray[indexOfRecord - 1]);
                                if (indexOfRecord == 37)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(mtv)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(MTVarray[indexOfRecord - 1]);
                                if (indexOfRecord == 687)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(mrb)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(MRBarray[indexOfRecord - 1]);
                                if (indexOfRecord == 506)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(neb)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(NEBarray[indexOfRecord - 1]);
                                if (indexOfRecord == 54)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(otr)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(OTRarray[indexOfRecord - 1]);
                                if (indexOfRecord == 8)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(ssm)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(SSMarray[indexOfRecord - 1]);
                                if (indexOfRecord == 168)
                                    throw new EOFException();
                            }
                        } else if (recordFileSelected.equals(tpa)) {
                            for (int i = 0; i < n; i++, indexOfRecord++) {
                                System.out.println(TPAarray[indexOfRecord - 1]);
                                if (indexOfRecord == 37)
                                    throw new EOFException();
                            }
                        }
                    } catch (EOFException e) {
                        System.out.println("Error has been found: EOF has been reached.");
                    }

                } else if (n < 0) {
                    try {
                        if (recordFileSelected.equals(ccb)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(CCBarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(hcb)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(HCBarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(mtv)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(MTVarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(mrb)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(MRBarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(neb)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(NEBarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(otr)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(OTRarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(ssm)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(SSMarray[indexOfRecord - 1]);
                            }
                        } else if (recordFileSelected.equals(tpa)) {
                            for (int i = 0; i < (-n); i++, indexOfRecord--) {
                                System.out.println(TPAarray[indexOfRecord - 1]);
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error has been found: BOF has been reached."); //if indexOfRecord exceeds the first element of the array, display the BOF message
                    }
                }
            }
        } while (!(userInput.equals("x") || userInput.equals("X")));

        keyboard.close();
        fileScanner.close();
    }

    /**
     * Deserializes an array of Book objects from a binary file.
     *
     * This method reads a binary file containing serialized Book objects, creates an array of the
     * appropriate size, and populates it with the deserialized Book objects.
     * The first integer in the file indicates the number of Book objects, which is used to initialize
     * the array. Each subsequent object in the file is read and stored in the array.
     *
     * @param fileName the name of the file from which the Book objects will be deserialized
     * @return an array of Book objects read from the file
     * @throws IOException if an I/O error occurs during file reading or deserialization
     */
    public static Book[] deserialize(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        //It will read the binary file and then create an array of that length
        int length = ois.readInt();

        Book[] array = new Book[length];

        for (int i = 0; i < length; i++) {
            try {
                array[i] = (Book) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException eofe) {
                System.out.println("The end of the file has been reached");
                break;
            }
        }
        ois.close();
        fis.close();

        return array;
    }

    /**
     * Removes empty (null) cells from an array of Book objects.
     * This method creates a new array containing only the non-null elements of the input array,
     * preserving the original order of the elements.
     *
     * @param array the array of Book objects, possibly containing null elements
     * @return a new Book array with all null elements removed
     */
    public static Book[] removeEmptyCells(Book[] array) {
        int count = 0;
        for (Book value : array) {
            if (!(value == null))
                count++;
        }

        Book[] newArray = new Book[count];
        int j = 0;
        for (Book book : array) {
            if (book == null) {
            } else {
                newArray[j] = book;
                j++;
            }
        }
        return newArray;
    }
}
