package com.example.letanhao_bt02_buoi3;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Contact implements Comparable<Contact> {
    int Id;
    String Fname;
    String Lname;
    String Image;
    String Phone;
    String Mail;
    String Birthday;

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }
    public Contact(){
        super();
    }
    public Contact(int id, String fname, String lname, int image, String phone, String mail, String birthday) {
        super();
        Id = id;
        Fname = fname;
        Lname = lname;
        ImageID = image;
        Phone = phone;
        Mail = mail;
        Birthday = birthday;
    }

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

    public String getFullName() {
        return this.Fname + this.Lname;
    }

    @Override
    public int compareTo(Contact o) {
        if (o == null) return 0;
        return this.Id - o.getId();
    }
}
