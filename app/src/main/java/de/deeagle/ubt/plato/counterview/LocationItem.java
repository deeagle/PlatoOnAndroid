package de.deeagle.ubt.plato.counterview;

public class LocationItem {
    private int id;
    private String name;
    private String location;
    private String state;
    private int currentPercent;
    private int openTime;
    private int closeTime;

    public LocationItem(int id, String name, String location, String state, int currentPercent, int openTime, int closeTime) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.state = state;
        this.currentPercent = currentPercent;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getCurrentPercent() {
        return currentPercent;
    }

    public void setCurrentPercent(int currentPercent) {
        this.currentPercent = currentPercent;
    }

    public int getOpenTime() {
        return openTime;
    }

    public void setOpenTime(int openTime) {
        this.openTime = openTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    @Override
    public String toString() {
        return "LocationItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", currentPercent=" + currentPercent +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                '}';
    }
}
