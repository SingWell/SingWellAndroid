package com.example.vbarboza.singwell2;


/**
 * Created by evaramirez on 1/25/18.
 */

public class User {
    private String email, id, token, firstName, lastName, cellNumber;

    public User(String email, String id, String token,String firstName, String lastName, String cellNumber){

        this.token = token;
        this.id = id;
        this.email = email;
        this.cellNumber = cellNumber;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public User (String email, String token, String id) {

        this.email = email;
        this.token = token;
        this.id = id;

    }
    public String getToken() {

        return token;
    }

    public String getId() {

        return id;
    }

    public String getEmail(){

        return email;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getFullName() {

        return firstName + lastName;
    }

    public String getCellNumber() {

        if (cellNumber != "")
            return cellNumber;
        else
            return "No cell phone number on file";
    }

}
