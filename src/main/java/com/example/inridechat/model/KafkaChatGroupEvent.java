package com.example.inridechat.model;

public class KafkaChatGroupEvent {
    public enum Action {
        CREATE,
        ADD_USER,
        DELETE
    }

    private String rideId;
    private Location origin;
    private Location destination;
    private Action action;
    private String userName;

    // Constructors
    public KafkaChatGroupEvent() {
    }

    public KafkaChatGroupEvent(String rideId, Location origin, Location destination, Action action, String userName) {
        this.rideId = rideId;
        this.origin = origin;
        this.destination = destination;
        this.action = action;
        this.userName = userName;
    }

    // Getters and setters
    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
