package com.goeuro.test.model;

import com.google.gson.annotations.SerializedName;

public class Location {
    private final long _id;
    private final String name;
    private final String type;

    @SerializedName("geo_position")
    private final PositionCoords positionCoords;

    public Location(int id, String name, String type, PositionCoords positionCoords) {
        _id = id;
        this.name = name;
        this.type = type;
        this.positionCoords = positionCoords;
    }

    public long get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public PositionCoords getPositionCoords() {
        return positionCoords;
    }

    @Override
    public String toString() {
        return "Location{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", positionCoords=" + positionCoords +
                '}';
    }
}
