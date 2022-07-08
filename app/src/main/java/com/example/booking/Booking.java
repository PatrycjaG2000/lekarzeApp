package com.example.booking;

public class Booking {

    String id;
    String date;
    String time;
    String doctor;

    public Booking(String id,String date,String time, String doctor){
        this.id =id;
        this.date = date;
        this.time = time;
        this.doctor = doctor;
    }

    public String getId() {
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date){
        this.date = date;
    }

    public String getTime() {

        return time;
    }
    public void setTime(String time){
        this.time = time;
    }


    public String getDoctor() {
        return doctor;
    }
    public void setDoctor(String doctor){
        this.doctor = doctor;
    }

}
