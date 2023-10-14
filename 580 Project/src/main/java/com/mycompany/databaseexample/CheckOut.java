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
    private int bookID;
    private int LibraryNumber;
    
    public CheckOut(int bookID, int LibraryNumber) {
        this.bookID = bookID;
        this.LibraryNumber = LibraryNumber;
    }

    /**
     * @return the bookID
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * @param bookID the bookID to set
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
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
    
}
