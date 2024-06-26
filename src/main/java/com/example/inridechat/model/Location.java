package com.example.inridechat.model;

public class Location {
    private double latitude;
    private double longitude;
    private String locationName;

    // Constructor
    public Location(double latitude, double longitude, String locationName) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.locationName = locationName;
    }

    // Getters and Setters
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
