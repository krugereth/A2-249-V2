import java.io.*;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class SortingBooks {

    //these attributes contain the amount of syntactically valid books of each genre as they are used in 2 seperate methods
    private static int CCBcountCopy;
    private static int HCBcountCopy;
    private static int MTVcountCopy;
    private static int MRBcountCopy;
    private static int NEBcountCopy;
    private static int OTRcountCopy;
    private static int SSMcountCopy;
    private static int TPAcountCopy;

    public static void main(String[] args) {
        do_part1(); // validating syntax, partition book records based on genre.
        do_part2(); // validating semantics, read the genre files each into arrays of Book objects,
        // then serialize the arrays of Book objects each into binary files
        do_part3(); // reading the binary files, deserialize the array objects in each file, and
        // then provide an interacive program to allow the user to navigate the arrays.
    }

    /**
     * This method is in charge of validating syntax and the partition of book records based on genre.
     */
    public static void do_part1() {

        //display welcome message
        System.out.print("Welcome to the Book Sorting Program by Sonali Patel!\n");

        //declare Scanner object and PrintWriter objects that will read from the input file and write in the syntax error file, respectively
        Scanner fileScanner = null;
        PrintWriter pw = null;

        //instanciate both objects
        try {
            fileScanner = new Scanner(new FileInputStream("part1_input_file_names.txt"));
            pw = new PrintWriter(new FileOutputStream("syntax_error_file.txt", true));

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: ");
            System.exit(0); //terminate
        }

        int fileCount = 0; //number of files that will be scanned
        int numFiles = fileScanner.nextInt(); //read the integer at the first line of the file = number of files
        fileScanner.nextLine(); //go to next line

        //count the occurence of each syntax error and each syntactically valid record
        int invalidGenreCount = 0, tooManyFieldsCount = 0, tooFewFieldsCount = 0, missingFieldCount = 0;
        int CCBcount = 0, HCBcount = 0, MTVcount = 0, MRBcount = 0, NEBcount = 0, OTRcount = 0, SSMcount = 0, TPAcount = 0;

        //declare each field type
        String title, authors, price, isbn, genre, year;

        //while loop to go through each line of the input file
        while (fileCount < numFiles) {

            fileCount++; //increment after each iteration
            String fileName = fileScanner.nextLine(); //file name to be read next
            Scanner recordScanner = null; //declare new Scanner object to read data in the book files

            try {
                recordScanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Error: Record File not found: " + fileName);
               continue;
            }

            //while loop to go through each line of the book record file
            while (recordScanner.hasNextLine()) {

                String record = recordScanner.nextLine(); //read the record
                String originalRecord = record; //store the whole line for futur purposes

                //read the title of each record (with or without " ")
                if (record.charAt(0) == '"') {
                    title = "\"" + record.substring(1, record.indexOf("\"", 2)) + "\"";
                    record = record.substring(record.indexOf('\"', 3));
                } else {
                    title = record.substring(0, record.indexOf(','));
                    record = record.substring(record.indexOf(',', 2));
                }

                //read the other fields of the record
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
                } catch (TooManyFieldsException e) {
                    e.getMessage();
                } catch (TooFewFieldsException e) {
                    e.getMessage();
                } catch (UnknownGenreException e) {
                    e.getMessage();
                } catch (MissingFieldException e) {
                    e.getMessage();
                }

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
                    //print writer object that will write in each genre's file
                    PrintWriter genreFileWriter = null;

                    //switch statement to send each record to its appropriate location
                    switch (genre) {

                        case "CCB":
                            CCBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Cartoons_Comics_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "HCB":
                            HCBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Hobbies_Collectibles_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "MTV":
                            MTVcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Movies_TV.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "MRB":
                            MRBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Music_Radio_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "NEB":
                            NEBcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Nostalgia_Eclectic_Books.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "OTR":
                            OTRcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Old_Time_Radio.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "SSM":
                            SSMcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Sports_Sports_Memorabilia.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
                            genreFileWriter.flush();
                            genreFileWriter.close();
                            break;

                        case "TPA":
                            TPAcount++;
                            try {
                                genreFileWriter = new PrintWriter(new FileOutputStream("Trains_Planes_Automobiles.csv.txt", true));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            genreFileWriter.println(title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year);
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

        //display results
        pw.println("\nThere are " + tooManyFieldsCount + " records with too many fields."
                + "\nThere are " + tooFewFieldsCount + " records with too few fields."
                + "\nThere are " + missingFieldCount + " records with one or more missing fields."
                + "\nThere are " + invalidGenreCount + " records with an invalid genre.");

        pw.println("\nIn total, there are " + (tooManyFieldsCount + tooFewFieldsCount + missingFieldCount + invalidGenreCount) + " records with syntax errors,"
                + " and " + (CCBcount + HCBcount + MTVcount + MRBcount + NEBcount + OTRcount + SSMcount + TPAcount) + " syntactically valid records.");

        pw.flush();
        pw.close();
        fileScanner.close();

        //assign values to the static properties
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
     * This method is in charge of validating semantics, reading the genre files each into arrays of Book objects,
     * then serialize the arrays of Book objects each into binary files
     */
    public static void do_part2() {

        //declare Scanner object and PrintWriter objects that will read from the input file and write in the syntax error file, respectively
        PrintWriter pw = null;
        Scanner fileScanner = null;

        try {
            String fileName = "part2_input_file_names.txt";
            fileScanner = new Scanner(new FileInputStream(fileName));
            pw = new PrintWriter(new FileOutputStream("semantic_error_file.txt",true));

        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: ");
            System.exit(0); //terminate
        }

        int fileCount = 0; //number of files that will be scanned
        int numFiles = fileScanner.nextInt(); //read the integer at the first line of the file
        fileScanner.nextLine(); //go to next line

        String title, authors, isbn, genre;
        double price;
        int year;

        //count the number of semantic errors and the number of valid book records
        int isbn10ErrorCount = 0, isbn13ErrorCount = 0, priceErrorCount = 0, yearErrorCount = 0;
        int ccb = 0, hcb = 0, mtv = 0, mrb = 0, neb = 0, otr = 0, ssm = 0, tpa = 0;

        System.out.println(CCBcountCopy+" "+HCBcountCopy+" "+MTVcountCopy+" "+MRBcountCopy+" "+NEBcountCopy+" "+OTRcountCopy+" "+SSMcountCopy+" "+TPAcountCopy);


        //create Book arrays for each genre where length = number of syntactically valid records
        Book[] CCBbookArray = new Book[CCBcountCopy];
        Book[] HCBbookArray = new Book[HCBcountCopy];
        Book[] MTVbookArray = new Book[MTVcountCopy];
        Book[] MRBbookArray = new Book[MRBcountCopy];
        Book[] NEBbookArray = new Book[NEBcountCopy];
        Book[] OTRbookArray = new Book[OTRcountCopy];
        Book[] SSMbookArray = new Book[SSMcountCopy];
        Book[] TPAbookArray = new Book[TPAcountCopy];

        //while loop to go through each line of the input file
        while (fileCount < numFiles) {

            fileCount++; //increment at each iteration

            String fileName = fileScanner.nextLine();
            Scanner recordScanner = null;

            try {
                recordScanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                System.out.println("Error: File not found." + fileName);
            }

            //while loop to keep going until the file has no other line
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

                authors = record.substring(record.indexOf(',') + 1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                price = Double.parseDouble(record.substring(1, record.indexOf(',', 2)));
                record = record.substring(record.indexOf(',', 2));
                isbn = record.substring(1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                genre = record.substring(1, record.indexOf(',', 2));
                record = record.substring(record.indexOf(',', 2));
                year = Integer.parseInt(record.substring(1));

                //check for errors and write them in the semantic error file
                try {
                    if (!isValidISBN(isbn)) {
                        pw.println("\nsemantic error in file: " + fileName
                                + "\n===================="
                                + "\nError: invalid ISBN"
                                + "\nRecord: " + originalRecord);
                        pw.flush();
                        if (isbn.length() == 10) {
                            isbn10ErrorCount++;
                            throw new BadIsbn10Exception("Error: This record's ISBN is invalid.");
                        }
                        if (isbn.length() == 13) {
                            isbn13ErrorCount++;
                            throw new BadIsbn13Exception("Error: This record's ISBN is invalid.");
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
                        //construct new Book object and call the parametrized constructor
                        Book newBook = new Book(title, authors, price, isbn, genre, year);

                        //declare new print writer object that will write the valid book records in each respective plain text file
                        PrintWriter genreFileWriter = null;

                        //switch statement to send each record to its appropriate location
                        switch (newBook.getGenre()) {

                            case "CCB":
                                try {
                                    genreFileWriter = new PrintWriter(new FileOutputStream("CartoonsComics.csv.txt", true));
                                } catch (FileNotFoundException e) {
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
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
                                    e.getMessage();
                                }
                                TPAbookArray[tpa] = newBook;
                                genreFileWriter.println(TPAbookArray[tpa]);
                                tpa++;
                                genreFileWriter.flush();
                                genreFileWriter.close();
                                break;
                        }
                    }
                } catch (BadIsbn10Exception e) {
                    e.getMessage();
                } catch (BadIsbn13Exception e) {
                    e.getMessage();
                } catch (BadPriceException e) {
                    e.getMessage();
                } catch (BadYearException e) {
                    e.getMessage();
                }
            }
            recordScanner.close();
        }

        //display the results
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
            e.printStackTrace();
        }
    }

    /**
     * This method determines whether a given ISBN is valid according to its checksum formula.
     * An ISBN must be either 10 digits (for an ISBN-10) or 13 digits (for an ISBN-13).
     * <p>
     * For an ISBN-10, the checksum formula is:
     * (10x1 + 9x2 + 8x3 + 7x4 + 6x5 + 5x6 + 4x7 + 3x8 + 2x9 + 1x10) % 11 == 0
     * <p>
     * For an ISBN-13, the checksum formula is:
     * (x1 + 3x2 + x3 + 3x4 + x5 + 3x6 + x7 + 3x8 + x9 + 3x10 + x11 + 3x12 + x13) % 10 == 0
     *
     * @param isbn the ISBN to be validated, as a String
     * @return true if the ISBN is valid, false otherwise
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
        if (price >= 0)
            return true;
        else
            return false;
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

    /**
     * Serializes an array of Book objects to a file.
     *
     * @param array    the array of Book objects to be serialized
     * @param fileName the name of the file to which the Book objects will be serialized
     * @throws IOException if an I/O error occurs while writing to the file
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

        try {
            fileScanner = new Scanner(new FileInputStream("part3_input_file_names.txt"));
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }

        int numFiles = fileScanner.nextInt(); //read user input
        fileScanner.nextLine(); //skip to next line
        int fileCount = 0;

        //declare each array to store info from deserialized binary files
        Book[] CCBarray = null;
        Book[] HCBarray = null;
        Book[] MTVarray = null;
        Book[] MRBarray = null;
        Book[] NEBarray = null;
        Book[] OTRarray = null;
        Book[] SSMarray = null;
        Book[] TPAarray = null;

        //create shortcuts for each serialized file name
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

            String fileName = fileScanner.nextLine(); //read file name

            //switch statement to deserialize each file one-by-one and store the information in the appropriate array
            switch (fileName) {

                case "Cartoons_Comics.csv.ser":
                    try {
                        CCBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Hobbies_Collectibles.csv.ser":
                    try {
                        HCBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Movies_TV_Books.csv.ser":
                    try {
                        MTVarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Music_Radio_Books.csv.ser":
                    try {
                        MRBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Nostalgia_Eclectic_Books.csv.ser":
                    try {
                        NEBarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Old_Time_Radio_Books.csv.ser":
                    try {
                        OTRarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Sports_Sports_Memorabilia.csv.ser":
                    try {
                        SSMarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
                case "Trains_Planes_Automobiles.csv.ser":
                    try {
                        TPAarray = deserialize(fileName);
                    } catch (IOException e) {
                        System.out.println("Error: File not found.");
                    }
                    break;
            }
            fileCount++; //increment at each iteration
        }

        String selectedFile = ccb; //default file

        Scanner keyboard = new Scanner(System.in); //new Scanner object that reads user input
        String userInput = null;

        //do-while loop that keeps going until user decides to exit
        do {
            System.out.print("\n\n---------------------------------"
                    + "\n            Main Menu"
                    + "\n---------------------------------"
                    + "\n   v   View the selected file: " + selectedFile
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
            if (selectedFile.equals(ccb))
                length = 21;
            else if (selectedFile.equals(hcb))
                length = 33;
            else if (selectedFile.equals(mtv))
                length = 695;
            else if (selectedFile.equals(mrb))
                length = 463;
            else if (selectedFile.equals(neb))
                length = 49;
            else if (selectedFile.equals(otr))
                length = 7;
            else if (selectedFile.equals(ssm))
                length = 179;
            else if (selectedFile.equals(tpa))
                length = 34;

            //terminate if user wishes to
            if (userInput.equals("x") || userInput.equals("X")) {
                System.out.println("Thank you for using the Book Sorting Program.");
                System.exit(0);
            } else if (userInput.equals("s")) { //display sub-menu if user enters 's'
                System.out.print("\n---------------------------------"
                        + "\n          File Sub-Menu"
                        + "\n---------------------------------"
                        + "\n  1  Cartoons_Comics.csv.ser\t\t(" + 21 + "  records)"
                        + "\n  2  Hobbies_Collectibles.csv.ser\t(" + 33 + "  records)"
                        + "\n  3  Movies_TV_Books.csv.ser\t\t(" + 695 + " records)"
                        + "\n  4  Music_Radio_Books.csv.ser\t\t(" + 463 + " records)"
                        + "\n  5  Nostalgia_Eclectic_Books.csv.ser\t(" + 49 + "  records)"
                        + "\n  6  Old_Time_Radio_Books.csv.ser\t(" + 7 + "   records)"
                        + "\n  7  Sports_Sports_Memorabilia.csv.ser\t(" + 179 + " records)"
                        + "\n  8  Trains_Planes_Automobiles.csv.ser\t(" + 34 + "  records)"
                        + "\n  9  Exit"
                        + "\n---------------------------------"
                        + "\n"
                        + "\nEnter Your Choice: ");

                //read user's choice
                int input = keyboard.nextInt();

                if (input == 1) {
                    selectedFile = ccb;
                } else if (input == 2) {
                    selectedFile = hcb;
                } else if (input == 3) {
                    selectedFile = mtv;
                } else if (input == 4) {
                    selectedFile = mrb;
                } else if (input == 5) {
                    selectedFile = neb;
                } else if (input == 6) {
                    selectedFile = otr;
                } else if (input == 7) {
                    selectedFile = ssm;
                } else if (input == 8) {
                    selectedFile = tpa;
                } else if (input == 9) {
                    System.out.println("Thank you for using the Book Sorting Program.");
                    System.exit(0);
                } else {
                    System.out.println("Invalid Input. Please try again.");
                }
            }
            if (userInput.equals("v")) { //if user enters 'v', display the following message
                System.out.println("viewing " + selectedFile + " (" + length + " records)");

                //prompt user to enter the value of 'n' (positive or negative)
                System.out.print("\nPlease enter how many consecutive records you wish to display: ");
                int n = keyboard.nextInt();

                //prompt user to enter the index of the first record they wish to see
                System.out.print("\nNow, enter the index of the record you want to start from: ");
                int index = keyboard.nextInt();

                if (n == 0) { //if n = 0, end iteration

                } else if (n > 0) {
                    try {
                        if (selectedFile.equals(ccb)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(CCBarray[index - 1]);
                                if (index == 21) { //if index exceeds the last element of the array, display the EOF message
                                    throw new EOFException();
                                }
                            }
                        } else if (selectedFile.equals(hcb)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(HCBarray[index - 1]);
                                if (index == 33)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(mtv)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(MTVarray[index - 1]);
                                if (index == 695)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(mrb)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(MRBarray[index - 1]);
                                if (index == 463)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(neb)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(NEBarray[index - 1]);
                                if (index == 49)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(otr)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(OTRarray[index - 1]);
                                if (index == 7)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(ssm)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(SSMarray[index - 1]);
                                if (index == 179)
                                    throw new EOFException();
                            }
                        } else if (selectedFile.equals(tpa)) {
                            for (int i = 0; i < n; i++, index++) {
                                System.out.println(TPAarray[index - 1]);
                                if (index == 34)
                                    throw new EOFException();
                            }
                        }
                    } catch (EOFException e) {
                        System.out.println("Error: EOF has been reached.");
                    }

                } else if (n < 0) {
                    try {
                        if (selectedFile.equals(ccb)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(CCBarray[index - 1]);
                            }
                        } else if (selectedFile.equals(hcb)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(HCBarray[index - 1]);
                            }
                        } else if (selectedFile.equals(mtv)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(MTVarray[index - 1]);
                            }
                        } else if (selectedFile.equals(mrb)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(MRBarray[index - 1]);
                            }
                        } else if (selectedFile.equals(neb)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(NEBarray[index - 1]);
                            }
                        } else if (selectedFile.equals(otr)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(OTRarray[index - 1]);
                            }
                        } else if (selectedFile.equals(ssm)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(SSMarray[index - 1]);
                            }
                        } else if (selectedFile.equals(tpa)) {
                            for (int i = 0; i < (-n); i++, index--) {
                                System.out.println(TPAarray[index - 1]);
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Error: BOF has been reached."); //if index exceeds the first element of the array, display the BOF message
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
     * @param fileName the name of the file from which the Book objects will be deserialized
     * @return an array of deserialized Book objects
     * @throws IOException if an I/O error occurs while reading from the file
     */
    public static Book[] deserialize(String fileName) throws IOException {
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);

        //read integer on the first line that says how many lines this file contains
        int length = ois.readInt();

        Book[] array = new Book[length];

        for (int i = 0; i < length; i++) {
            try {
                array[i] = (Book) ois.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (EOFException eofe) {
                System.out.println("End of file reached");
                break;
            }
        }
        ois.close();
        fis.close();

        return array;
    }

    /**
     * Removes any empty cells from the given Book array and returns a new array without the empty cells.
     *
     * @param array the Book array to remove empty cells from
     * @return a new Book array without empty cells
     */
    public static Book[] removeEmptyCells(Book[] array) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (!(array[i] == null))
                count++;
        }

        Book[] newArray = new Book[count];
        int j = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null)
                continue;
            else {
                newArray[j] = array[i];
                j++;
            }
        }
        return newArray;
    }
}
