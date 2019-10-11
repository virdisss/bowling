package com.bowling.challenge.dao;

public class Chance {
    /**
     * cardinal refers to the number, for example 3, 7 9, etc.
     */
    private int cardinal;
    /**
     * value refers to some special cases like 'F' which is a fault but it's cardinal would be 0
     */
    private String value;

    public Chance(int cardinal, String value) {
        this.cardinal = cardinal;
        this.value = value;
    }
    
    public int getCardinal() {
        return cardinal;
    }

    public void setCardinal(int cardinal) {
        this.cardinal = cardinal;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    } 
}
