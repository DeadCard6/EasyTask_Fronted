package com.example.ucompensareasytaskas.api.model;

public class Note {
    private long id;
    private String title;
    private String description;
    private String date;
    private String hour;
    private String image;
    private Location location;
    private Group group;
    private User user;

    // Getters y setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public Group getGroup() { return group; }
    public void setGroup(Group group) { this.group = group; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
