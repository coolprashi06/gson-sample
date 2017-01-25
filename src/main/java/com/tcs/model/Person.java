package com.tcs.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;

import java.util.List;


public class Person {

    @SerializedName("firstNAME")//Serialized name is used if you want different name to be displayed in JSON Format
    @Expose     //Use if you want certain fields only to be exposed while converting to JSON
    @Since(1.1)
    private String firstName;   //Appending transient, volatile or static modifier will not enable property to be added to JSON format

    @Expose
    private String lastName;

    @SerializedName("sex")
    @Expose
    private String gender;

    private int age;

    private Address address;
    private List<Integer> numbers;

    public Person() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

   public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Integer> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }
}
