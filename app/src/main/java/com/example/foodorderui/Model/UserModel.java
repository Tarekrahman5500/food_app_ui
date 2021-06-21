package com.example.foodorderui.Model;

public class UserModel {

    String profilePic;
    String userName;
    String phone;
    String password;
    String conframPassword;
    String email;
    String userId;

    public UserModel(String userName, String phone, String password, String conframPassword, String email, String profilePic, String userId ) {
        this.userName = userName;
        this.phone = phone;
        this.password = password;
        this.conframPassword = conframPassword;
        this.email = email;
        this.profilePic = profilePic;
        this.userId = userId;
    }

    public UserModel() {}

    public  UserModel(String userName, String password, String phone, String email, String profilePic) {

        this.profilePic = profilePic;
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public UserModel(String userName, String phone, String email, String password) {
        this.userName = userName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConframPassword() {
        return conframPassword;
    }

    public void setConframPassword(String conframPassword) {
        this.conframPassword = conframPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
