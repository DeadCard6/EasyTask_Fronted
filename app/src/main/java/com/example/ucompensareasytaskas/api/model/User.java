package com.example.ucompensareasytaskas.api.model;

public class User {
    private Long id; // Agregamos el campo id de tipo Long
    private String name;
    private String secondName;
    private String password;
    private String phoneNumber;
    private String email;
    private String imgProfile;

    public User() {
    }

    public User(Long id, String name, String secondName, String password, String phoneNumber, String email, String imgProfile) {
        this.id = id;
        this.name = name;
        this.secondName = secondName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imgProfile = imgProfile;
    }

    public User(Long id) {
        this.id = id;
    }



    // Getter y Setter para el campo id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Otros getters y setters
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
