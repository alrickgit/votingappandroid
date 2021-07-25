package com.example.artistvoting;

public class Users {
    public  String name,email;
    public Boolean hasVoted;
    public Users(){}
    public Users(String name,String email,Boolean hasVoted){
        this.name=name;
        this.email=email;
        this.hasVoted =hasVoted;
    }
}
