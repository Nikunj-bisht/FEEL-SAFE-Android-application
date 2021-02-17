package com.safero.fellsafe.datastorageclasses;

public class Closecontactdetails extends Usersdata {

    private String number;
private String token;
private String proff;

    public Closecontactdetails(String num,String toke , String proo){
        super();

         this.number = num;
         this.token = toke;
         this.proff = proo;

    }

    @Override
    public String getName() {
        return "Popla";
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getProfession() {
        return this.proff;
    }

    @Override
    public String getToken() {
        return this.token;
    }
}

