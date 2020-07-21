package com.vedaaz.Module;

import com.google.gson.annotations.SerializedName;

public class BillResponse {

    @SerializedName("success")
    String success;

    @SerializedName("message")
    String message;

    @SerializedName("subscription_id")
    String subscription_id;

    @SerializedName("product_id_fk")
    String product_id_fk;

    @SerializedName("subscription_type_id")
    String subscription_type_id;

    @SerializedName("daily_amt")
    String daily_amt;

    @SerializedName("dailyQty")
    String dailyQty;

    @SerializedName("end_date")
    String end_date;

    @SerializedName("start_date")
    String start_date;

    @SerializedName("daily_product_name")
    String daily_product_name;

    @SerializedName("daily_product_image")
    String daily_product_image;

    @SerializedName("daily_product_rate")
    String daily_product_rate;

    @SerializedName("daily_product_desc")
    String daily_product_desc;

    @SerializedName("days")
    String days;
    @SerializedName("year")
    String year;
    @SerializedName("month")
    String month;

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

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getProduct_id_fk() {
        return product_id_fk;
    }

    public void setProduct_id_fk(String product_id_fk) {
        this.product_id_fk = product_id_fk;
    }

    public String getSubscription_type_id() {
        return subscription_type_id;
    }

    public void setSubscription_type_id(String subscription_type_id) {
        this.subscription_type_id = subscription_type_id;
    }

    public String getDaily_amt() {
        return daily_amt;
    }

    public void setDaily_amt(String daily_amt) {
        this.daily_amt = daily_amt;
    }

    public String getDailyQty() {
        return dailyQty;
    }

    public void setDailyQty(String dailyQty) {
        this.dailyQty = dailyQty;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
