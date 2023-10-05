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
    private int LibraryNum;
    
    
    Member(int memNum, String name ) {
        this.LibraryNum = memNum;
        this.name = name;
    }

    /**
     * @return the LibraryNum
     */
    public int getMemNum() {
        return LibraryNum;
    }

    /**
     * @param memNum the LibraryNum to set
     */
    public void setMemNum(int memNum) {
        this.LibraryNum = memNum;
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
