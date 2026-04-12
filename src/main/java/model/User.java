package model;

public class User {

    // User Model Class with necessary fields and constructor
    private String username;
    private String password;
    private String phoneNumber;
    private String firstName;
    private String lastName;


    //Constructor to initialize the User object
    public User(String username, String password, String phoneNumber, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    // Getters and Setters Methods
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
}

