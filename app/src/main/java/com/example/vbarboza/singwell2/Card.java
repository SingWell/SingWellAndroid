package com.example.vbarboza.singwell2;

/**
 * Created by evaramirez on 11/18/17.
 * This file will work for any card display.  It contains image and title only, other info can be added as needed
 * Following mitchtabian instagram tutorials https://www.youtube.com/watch?v=Cdn0jEFW6FMs
 */

public class Card {
    private String imgURL;
    private String title;
    private String meetingDay, startTime, endTime;

    public Card(String imgURL, String title, String day, String start, String end) {
        this.imgURL = imgURL;
        this.title = title;
        this.meetingDay = day;
        this.startTime = start;
        this.endTime = end;
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

    public void setDay(String day) {

        this.meetingDay = day;
    }
    public String getDay() {

        return meetingDay;
    }

    public void setStart(String start) {

        this.startTime = start;
    }

    public String getStart() {

        return startTime;
    }

    public void setEnd(String end) {

        this.endTime = end;
    }

    public String getEnd() {

        return endTime;
    }
}

