package model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Delivery {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String houseFlatNumber;
    private String streetName;
    private String zipCode;
    private String city;
    private String note;
    private Timestamp createdAt;

    public Delivery() {
    }

    public Delivery(Integer id, String firstName, String lastName, String email,
                    String mobileNumber, String houseFlatNumber,
                    String streetName, String zipCode, String city, String note) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.houseFlatNumber = houseFlatNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.note = note;
    }

    public Delivery(String firstName, String lastName, String email,
                    String mobileNumber, String houseFlatNumber,
                    String streetName, String zipCode, String city,
                    String note) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.houseFlatNumber = houseFlatNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.note = note;
    }

    public Delivery(Integer id, String firstName, String lastName, String email,
                    String mobileNumber, String houseFlatNumber, String streetName,
                    String zipCode, String city, String note, Timestamp createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.houseFlatNumber = houseFlatNumber;
        this.streetName = streetName;
        this.zipCode = zipCode;
        this.city = city;
        this.note = note;
        this.createdAt = createdAt;
    }
}
