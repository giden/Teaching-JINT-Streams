package entities;

public class Address {

    private String streetName;
    private Integer houseNumber;
    private Integer flatNumber;
    private String city;
    private String postCode;
    private String country;

    public Address(Integer houseNumber, String streetName, Integer flatNumber, String city, String country, String postCode) {
        this.houseNumber = houseNumber;
        this.streetName = streetName;
        this.flatNumber = flatNumber;
        this.city = city;
        this.country = country;
        this.postCode = postCode;
    }

    public String getStreetName() {
        return streetName;
    }

    public Address setStreetName(String streetName) {
        this.streetName = streetName;
        return this;
    }

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public Address setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public Integer getFlatNumber() {
        return flatNumber;
    }

    public Address setFlatNumber(Integer flatNumber) {
        this.flatNumber = flatNumber;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public Address setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }
}
