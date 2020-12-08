package com.safero.fellsafe.datastorageclasses;

public class Crimstorageclass {

    private String name;
    private String disciption;
    private String crimlocation;
    private String imageurl;

    public Crimstorageclass(String name, String disciption, String crimlocation, String imageurl) {
        this.name = name;
        this.disciption = disciption;
        this.crimlocation = crimlocation;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public String getDisciption() {
        return disciption;
    }

    public String getCrimlocation() {
        return crimlocation;
    }

    public String getImageurl() {
        return imageurl;
    }
}
