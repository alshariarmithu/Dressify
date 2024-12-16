package com.mithu.dressify20;

public class User {
    public String fullName;
    public String email;
    public String mobile;
    public String address;
    public String country;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String fullName, String email, String mobile, String address, String country) {
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.country = country;
    }
}
