package com.example.ucompensareasytaskas.api.model;

public class User {
    private String name;
    private String secondName;
    private String password;
    private String phoneNumber;
    private String email;
    private String imgProfile;

    // Constructor
    public User(String name, String secondName, String password, String phoneNumber, String email, String imgProfile) {
        this.name = name;
        this.secondName = secondName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imgProfile = imgProfile;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }
}
