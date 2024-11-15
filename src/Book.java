//-----------------------------------------------------
//Assignment 3
//Written by: Ayush Patel (40285846) and Krishna Patel (40200870)
//-----------------------------------------------------

import java.io.Serializable;

public class Book implements Serializable{

    private String title;
    private String authors;
    private double price;
    private String isbn;
    private String genre;
    private int year;

    //Default constructor
    public Book() {

    }

    //Parameterized constructor
    public Book(String title, String authors, double price, String isbn, String genre, int year) {
        this.title = title;
        this.authors = authors;
        this.price = price;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
    }

    //Getter and setter methods for the attributes are declared
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    //Equals method is declared
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Book))
            return false;

        Book other = (Book) obj;

        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;

        if (authors == null) {
            if (other.authors != null)
                return false;
        } else if (!authors.equals(other.authors))
            return false;

        if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
            return false;

        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;

        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;

        if (year != other.year)
            return false;

        return true;
    }

    //toString method is declared
    public String toString() {
        return title + "," + authors + "," + price + "," + isbn + "," + genre + "," + year;
    }

}

