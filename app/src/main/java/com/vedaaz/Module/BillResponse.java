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

    @SerializedName("date")
    String date;

    @SerializedName("year")
    String year;

    @SerializedName("month")
    String month;

    @SerializedName("totalAmount")
    String totalAmount;

    @SerializedName("del_status")
    String del_status;

    @SerializedName("subsription_type")
    String subsription_type;

    @SerializedName("frequency")
    String frequency;

    @SerializedName("first_name")
    String first_name;

    @SerializedName("last_name")
    String last_name;

    @SerializedName("adhoc_qty")
    String adhoc_qty;

    @SerializedName("product_name")
    String product_name;

    // All Getter and Setter method

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


    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDel_status() {
        return del_status;
    }

    public void setDel_status(String del_status) {
        this.del_status = del_status;
    }

    public String getSubsription_type() {
        return subsription_type;
    }

    public void setSubsription_type(String subsription_type) {
        this.subsription_type = subsription_type;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAdhoc_qty() {
        return adhoc_qty;
    }

    public void setAdhoc_qty(String adhoc_qty) {
        this.adhoc_qty = adhoc_qty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
