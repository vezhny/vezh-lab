package core.json;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class UserAddress {
    @Expose
    private String country;

    @Expose
    private String region;

    @Expose
    private String city;

    @Expose
    private String street;

    @Expose
    private String house;

    @Expose
    private String housePart;

    @Expose
    private String room;

    public UserAddress() {
    }

    public UserAddress(String country, String region, String city, String street, String house,
                       String housePart, String room) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.housePart = housePart;
        this.room = room;
    }

    public UserAddress(String country, String region, String city, String street, String house, String room) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.street = street;
        this.house = house;
        this.room = room;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        if (housePart != null && housePart.length() > 0) {
            return house + "/" + housePart;
        }
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setHousePart(String housePart) {
        this.housePart = housePart;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getAddress() {
        return country + ", " + region + ", " + city + ", " + street + ", " + getHouse() + ", " + room;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
