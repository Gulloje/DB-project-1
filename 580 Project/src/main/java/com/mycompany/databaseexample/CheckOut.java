/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author jessi
 */
public class CheckOut {
    private int BookID;
    private int LibraryNumber;
    private String name;
    private String author;
    private int year;
    //dont need to have constructor for all data, just for what you want to show in table
    public CheckOut(int LibraryNumber , int bookID, String name) {
        this.BookID = bookID;
        this.LibraryNumber = LibraryNumber;
        this.name = name;
    }

    /**
     * @return the bookID
     */
    public int getBookID() {
        return BookID;
    }

    /**
     * @param bookID the bookID to set
     */
    public void setBookID(int bookID) {
        this.BookID = bookID;
    }

    /**
     * @return the LibraryNumber
     */
    public int getLibraryNumber() {
        return LibraryNumber;
    }

    /**
     * @param LibraryNumber the LibraryNumber to set
     */
    public void setLibraryNumber(int LibraryNumber) {
        this.LibraryNumber = LibraryNumber;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the year
     */
    public int getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(int year) {
        this.year = year;
    }
    
}
