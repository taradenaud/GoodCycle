package com.example.seg2105;

public class Userlistitem {
    private String Username;
    private String Role;

    public Userlistitem ( String Username, String role){
        this.Username = Username;
        this.Role = role;
    }

    public String getUsername(){
        return Username;
    }

    public  String getRole(){
        return Role;
    }
}
