package com.goeuro.test.model;

public class PositionCoords {
    private final double latitude;
    private final double longitude;

    public PositionCoords(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "PositionCoords{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
