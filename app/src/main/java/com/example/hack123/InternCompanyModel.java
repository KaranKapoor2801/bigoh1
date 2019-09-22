package com.example.hack123;

public class InternCompanyModel {

    String location,name,profile,stipend;

    public InternCompanyModel(){}

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public InternCompanyModel(String location, String name, String profile, String stipend) {
        this.location = location;
        this.name = name;
        this.profile = profile;
        this.stipend = stipend;
    }
}
