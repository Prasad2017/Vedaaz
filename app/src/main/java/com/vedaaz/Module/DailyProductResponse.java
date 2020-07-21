package com.vedaaz.Module;

import com.google.gson.annotations.SerializedName;

public class DailyProductResponse {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("daily_product_id")
    String daily_product_id;

    @SerializedName("daily_product_name")
    String daily_product_name;

    @SerializedName("daily_product_image")
    String daily_product_image;

    @SerializedName("daily_product_rate")
    String daily_product_rate;

    @SerializedName("daily_product_desc")
    String daily_product_desc;

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

    public String getDaily_product_id() {
        return daily_product_id;
    }

    public void setDaily_product_id(String daily_product_id) {
        this.daily_product_id = daily_product_id;
    }

    public String getDaily_product_name() {
        return daily_product_name;
    }

    public void setDaily_product_name(String daily_product_name) {
        this.daily_product_name = daily_product_name;
    }

    public String getDaily_product_image() {
        return daily_product_image;
    }

    public void setDaily_product_image(String daily_product_image) {
        this.daily_product_image = daily_product_image;
    }

    public String getDaily_product_rate() {
        return daily_product_rate;
    }

    public void setDaily_product_rate(String daily_product_rate) {
        this.daily_product_rate = daily_product_rate;
    }

    public String getDaily_product_desc() {
        return daily_product_desc;
    }

    public void setDaily_product_desc(String daily_product_desc) {
        this.daily_product_desc = daily_product_desc;
    }
}
