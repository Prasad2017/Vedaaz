package com.vedaaz.Module;

import com.google.gson.annotations.SerializedName;

public class AddressResponse {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("city_id")
    String city_id;

    @SerializedName("city_name")
    String city_name;

    @SerializedName("area_id")
    String area_id;

    @SerializedName("area_name")
    String area_name;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
}
