package com.example.letanhao_bt02_buoi3;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Contact {
    int Id;
    String Fname;
    String Lname;
    String Image;
    String Phone;
    String Mail;
    int ImageID;

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public int getId() {
        return Id;
    }

    @JsonSetter("Id")
    public void setId(int id) {
        Id = id;
    }

    public String getFname() {
        return Fname;
    }

    @JsonSetter("Fname")
    public void setFname(String fname) {
        Fname = fname;
    }

    public String getLname() {
        return Lname;
    }
    @JsonSetter("Lname")
    public void setLname(String lname) {
        Lname = lname;
    }

    public String getImage() {
        return Image;
    }
    @JsonSetter("Image")
    public void setImage(String image) {
        Image = image;
    }

    public String getPhone() {
        return Phone;
    }
    @JsonSetter("Phone")
    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getMail() {
        return Mail;
    }
    @JsonSetter("Mail")
    public void setMail(String mail) {
        Mail = mail;
    }
}
