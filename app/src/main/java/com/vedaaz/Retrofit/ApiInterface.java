package com.vedaaz.Retrofit;

import com.vedaaz.Module.AllList;
import com.vedaaz.Module.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {


    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/Login.php")
    Call<LoginResponse> Login(@Field("mobile") String mobile);


    @GET("/androidApp/GGD/TaskManager/SMS.php")
    Call<LoginResponse> SMS(@Query("mobile") String mobileNumber,
                            @Query("message") String message);


    @GET("/androidApp/Vedaaz/Customer/getCity.php")
    Call<AllList> getCity();


    @GET("/androidApp/Vedaaz/Customer/getArea.php")
    Call<AllList> getArea(@Query("city_id_fk") String cityid);


    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/UpdateProfile.php")
    Call<LoginResponse> UpdateProfile(@Field("user_id") String user_id,
                                      @Field("userCityId") String userCityId ,
                                      @Field("userAreaId") String userAreaId ,
                                      @Field("first_name") String first_name ,
                                      @Field("last_name") String last_name,
                                      @Field("user_email") String user_email);

    @GET("/androidApp/Vedaaz/Customer/getDailyProduct.php")
    Call<AllList> getDailyProduct();

    @GET("/androidApp/Vedaaz/Customer/getProducts.php")
    Call<AllList> getProducts();


    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/AddToCart.php")
    Call<LoginResponse> AddToCart(@Field("user_fk") String user_fk,
                                  @Field("cart_pk") String cart_pk,
                                  @Field("productid_fk") String productid_fk,
                                  @Field("subproductid_fk") String subproductid_fk,
                                  @Field("daily_product_id_fk") String daily_product_id_fk,
                                  @Field("qty") String qty,
                                  @Field("price") String price,
                                  @Field("total") String total);
    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/AddSubscription.php")
    Call<LoginResponse> AddSubscription(@Field("userId") String user_fk,
                                        @Field("userCityId") String userCityId ,
                                        @Field("userAreaId") String userAreaId ,
                                        @Field("productId") String productId ,
                                        @Field("subscribeId") String subscribeId,
                                        @Field("dailyAmt") String dailyAmt,
                                        @Field("dailyQty") String dailyQty,
                                        @Field("days") String days,
                                        @Field("startDate") String startDate);

    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/PauseSub.php")
    Call<LoginResponse> pauseSubscription(@Field("user_id") String user_fk,
                                          @Field("pause_strdate") String pause_strdate ,
                                          @Field("pause_enddate") String pause_enddate ,
                                          @Field("subscription_id") String subscription_id);

    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/AdHoc.php")
    Call<LoginResponse> adHocSubscription(@Field("user_id") String user_fk,
                                          @Field("adhoc_strdate") String adhoc_strdate ,
                                          @Field("adhoc_enddate") String adhoc_enddate ,
                                          @Field("adhoc_qty") String adhoc_qty ,
                                          @Field("subscription_id") String subscription_id);


    @GET("/androidApp/Vedaaz/Customer/getSubscriptionList.php")
    Call<AllList> getSubscriptionList(@Query("user_id") String user_id);


    @GET("/androidApp/Vedaaz/Customer/getPauseSub.php")
    Call<AllList> getPauseSub(@Query("user_id") String user_id);


    @GET("/androidApp/Vedaaz/Customer/getAdHocSub.php")
    Call<AllList> getAdHocSub(@Query("user_id") String user_id);


    @GET("/androidApp/Vedaaz/Customer/getBillList.php")
    Call<AllList> getBillList(@Query("user_id") String user_id);



    @GET("/androidApp/Vedaaz/Customer/getBillDetails.php")
    Call<AllList> getBillDetails(@Query("user_id") String user_id,
                                 @Query("date") String date);


    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/Addwallet.php")
    Call<LoginResponse> addWallet(@Field("user_id") String userId,
                                  @Field("transaction_id") String razorpayPaymentID,
                                  @Field("payment") String amount);


    @FormUrlEncoded
    @POST("/androidApp/Vedaaz/Customer/AddPendingAmount.php")
    Call<LoginResponse> addPendingAmount(@Field("user_id") String userId,
                                         @Field("transaction_id") String razorpayPaymentID,
                                         @Field("pendingAmount") String pendingAmount);


    @GET("/androidApp/Vedaaz/Customer/getProfile.php")
    Call<AllList> getProfile(@Query("user_id") String userId);

}
