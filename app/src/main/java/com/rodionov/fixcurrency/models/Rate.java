
package com.rodionov.fixcurrency.models;

public class Rate {

    private String name;
    private String value;

    public Rate(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {

        if(value == null || value.length() == 0 || value.equals("0") || value.equals("0."))
            return false;
        else
            return true;
    }

}
