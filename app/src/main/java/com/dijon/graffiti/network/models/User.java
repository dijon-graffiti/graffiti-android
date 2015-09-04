package com.dijon.graffiti.network.models;

/**
 * User POJO.
 * Implemented with Data Binding in mind.
 */
public class User {

    private int birthYear;
    private String email;
    private String firstName;
    private String gender;
    private String lastName;
    private String phoneManufacturer;
    private String phoneModel;
    private String phoneProduct;
    private String phoneVersion;
    private String profileImage;

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneDevice() {
        return phoneManufacturer;
    }

    public void setPhoneManufacturer(String phoneDevice) {
        this.phoneManufacturer = phoneDevice;
    }

    public String getPhoneModel() {
        return phoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        this.phoneModel = phoneModel;
    }

    public String getPhoneProduct() {
        return phoneProduct;
    }

    public void setPhoneProduct(String phoneProduct) {
        this.phoneProduct = phoneProduct;
    }

    public String getPhoneVersion() {
        return phoneVersion;
    }

    public void setPhoneVersion(String phoneVersion) {
        this.phoneVersion = phoneVersion;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

}
