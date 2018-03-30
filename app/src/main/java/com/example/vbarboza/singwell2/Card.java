package com.example.vbarboza.singwell2;

/**
 * Created by evaramirez on 11/18/17.
 * This file will work for any card display.  It contains image and title only, other info can be added as needed
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FMs
 */

public class Card {
    private String imgURL;
    private String title;
    private String email;
    private String name;
    private String time;
    private String date;
    private String location;




    public Card(String imgURL, String title) {
        this.imgURL = imgURL;
        this.title = title;
    }

    public Card(String imgURL, String title, String email){
        this.imgURL = imgURL;
        this.title = title;
        this.email = email;
    }

    public Card(String imgURL, String name, String date, String location, String time){
        this.imgURL = imgURL;
        this.name = name;
        this.date = date;
        this.location = location;
        this.time = time;
    }

    public String getImgURL() {

        return imgURL;
    }

    public void setImgURL(String imgURL) {

        this.imgURL = imgURL;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public void setEmail() {

        this.email = email;
    }

    public String getEmail() {

        return email;
    }

    public String getName(){
        return name;
    }

    public String getTime(){
        return time;
    }

    public String getDate(){
        return date;
    }

    public String getLocation(){
        return location;
    }
}

