/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.databaseexample;

/**
 *
 * @author jessi
 */
public class Member {
    private String name;
    private int LibraryNumber;
    
    
    Member(int LibraryNumber, String name ) {
        this.LibraryNumber = LibraryNumber;
        this.name = name;
    }

    /**
     * @return the LibraryNumber
     */
    public int getLibraryNumber() {
        return LibraryNumber;
    }

    /**
     * @param memNum the LibraryNumber to set
     */
    public void setMemNum(int memNum) {
        this.LibraryNumber = memNum;
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
    
}
