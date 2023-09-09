package com.example.mazdooruser;

public class UserInfoModel {

    private String image,name,phonenumber,city,description,latitude,longitude,userType,serviceType,userId;
    boolean isVerified;
    private String pushId;
    private boolean isCompleted;
    private String userId2;
    private String userName;
    private String userAddress;



    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public UserInfoModel() {
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserInfoModel(String image, String name, String phonenumber, String city, String description, String latitude, String longitude, String userType, String serviceType, String userId, boolean isVerified, String pushId, boolean isCompleted, String userId2, String userName, String userAddress) {
        this.image = image;
        this.name = name;
        this.phonenumber = phonenumber;
        this.city = city;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userType = userType;
        this.serviceType = serviceType;
        this.userId = userId;
        this.isVerified = isVerified;
        this.pushId = pushId;
        this.isCompleted = isCompleted;
        this.userId2 = userId2;
        this.userName = userName;
        this.userAddress = userAddress;
    }

    public String getUserId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
}
