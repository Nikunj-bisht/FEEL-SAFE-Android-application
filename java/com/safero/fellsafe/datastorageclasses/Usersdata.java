package com.safero.fellsafe.datastorageclasses;

import java.io.Serializable;

public class Usersdata implements Serializable {

    private String name;
    private String number;
    private String location1;
    private String profession;
    private String token;

    Usersdata(){

    }

    public Usersdata(String name, String number, String location1, String profession, String token) {
        this.name = name;
        this.number = number;
        this.location1 = location1;
        this.profession = profession;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getLocation1() {
        return location1;
    }

    public String getProfession() {
        return profession;
    }

    public String getToken() {
        return token;
    }
}
