package com.vedaaz.Module;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllList {


    @SerializedName("dialyProductSuccess")
    private List<DailyProductResponse> dailyProductResponseList;

    @SerializedName("profileSuccess")
    private List<ProfileResponse> profileResponseList ;

    @SerializedName("productSuccess")
    private List<ProductResponse> productResponseList;

    @SerializedName("subscriptionSuccess")
    private List<SubscriptionResponse> subscriptionResponseList;

    @SerializedName("citySuccess")
    private List<AddressResponse> cityresponseList;

    @SerializedName("areaSuccess")
    private List<AddressResponse> areaResponseList;

    @SerializedName("billSuccess")
    private List<BillResponse> billResonseList;

    @SerializedName("billDetailsSuccess")
    private List<BillResponse> billDetailsResonseList;

    @SerializedName("subscriptionPauseSuccess")
    private List<SubscriptionResponse> getPauseResponseList;

    @SerializedName("subscriptionAdHocSuccess")
    private List<SubscriptionResponse> adHocResponseList;


    public List<DailyProductResponse> getDailyProductResponseList() {
        return dailyProductResponseList;
    }

    public void setDailyProductResponseList(List<DailyProductResponse> dailyProductResponseList) {
        this.dailyProductResponseList = dailyProductResponseList;
    }

    public List<ProductResponse> getProductResponseList() {
        return productResponseList;
    }

    public void setProductResponseList(List<ProductResponse> productResponseList) {
        this.productResponseList = productResponseList;
    }

    public List<SubscriptionResponse> getSubscriptionResponseList() {
        return subscriptionResponseList;
    }

    public void setSubscriptionResponseList(List<SubscriptionResponse> subscriptionResponseList) {
        this.subscriptionResponseList = subscriptionResponseList;
    }

    public List<AddressResponse> getCityresponseList() {
        return cityresponseList;
    }

    public void setCityresponseList(List<AddressResponse> cityresponseList) {
        this.cityresponseList = cityresponseList;
    }

    public List<AddressResponse> getAreaResponseList() {
        return areaResponseList;
    }

    public void setAreaResponseList(List<AddressResponse> areaResponseList) {
        this.areaResponseList = areaResponseList;
    }

    public List<BillResponse> getBillResonseList() {
        return billResonseList;
    }

    public void setBillResonseList(List<BillResponse> billResonseList) {
        this.billResonseList = billResonseList;
    }

    public List<SubscriptionResponse> getGetPauseResponseList() {
        return getPauseResponseList;
    }

    public void setGetPauseResponseList(List<SubscriptionResponse> getPauseResponseList) {
        this.getPauseResponseList = getPauseResponseList;
    }

    public List<SubscriptionResponse> getAdHocResponseList() {
        return adHocResponseList;
    }

    public void setAdHocResponseList(List<SubscriptionResponse> adHocResponseList) {
        this.adHocResponseList = adHocResponseList;
    }

    public List<BillResponse> getBillDetailsResonseList() {
        return billDetailsResonseList;
    }

    public void setBillDetailsResonseList(List<BillResponse> billDetailsResonseList) {
        this.billDetailsResonseList = billDetailsResonseList;
    }

    public List<ProfileResponse> getProfileResponseList() {
        return profileResponseList;
    }

    public void setProfileResponseList(List<ProfileResponse> profileResponseList) {
        this.profileResponseList = profileResponseList;
    }
}
