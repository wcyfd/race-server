package com.randioo.race_server.entity.po;

public class RaceEntity {
    private int raceId;
    private String name;

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RaceEntity [raceId=").append(raceId).append(", name=").append(name).append("]");
        return builder.toString();
    }

}
