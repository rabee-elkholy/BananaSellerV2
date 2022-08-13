package com.androdu.bananaSeller.data.api;


import com.androdu.bananaSeller.data.model.requestBody.AddLocationRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.CancelOfferRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.CancelOrderRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ChangeLangRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ChangePhoneRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.CheckCodeRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.CheckPayRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ComplaintIdRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ContactUsRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.DeliveryLoginRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.EditMobileRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.EditNameRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.EditPasswordRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.ForgotCheckCodeRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.LoginRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.LogoutRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.NewPassRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.NotificationSettingsRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.PullBalanceRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.SignUpRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.AddOfferRequestBody;
import com.androdu.bananaSeller.data.model.requestBody.addOffer.FieldRequestBody;
import com.androdu.bananaSeller.data.model.response.GeneralResponse;
import com.androdu.bananaSeller.data.model.response.SellerFieldsResponse;
import com.androdu.bananaSeller.data.model.response.checkout.CheckOutResponse;
import com.androdu.bananaSeller.data.model.response.clientInfo.ClientInfoResponse;
import com.androdu.bananaSeller.data.model.response.complaints.ComplaintsResponse;
import com.androdu.bananaSeller.data.model.response.deliveryLogin.DeliveryLoginResponse;
import com.androdu.bananaSeller.data.model.response.login.LoginResponse;
import com.androdu.bananaSeller.data.model.response.myAddresses.MyAddressesResponse;
import com.androdu.bananaSeller.data.model.response.notificationSettings.NotificationSettingsResponse;
import com.androdu.bananaSeller.data.model.response.notifications.NotificationsResponse;
import com.androdu.bananaSeller.data.model.response.offers.MyOffersResponse;
import com.androdu.bananaSeller.data.model.response.order.OrderResponse;
import com.androdu.bananaSeller.data.model.response.orders.OrdersResponse;
import com.androdu.bananaSeller.data.model.response.products.ProductsResponse;
import com.androdu.bananaSeller.data.model.response.sellerInfo.SellerInfoResponse;
import com.androdu.bananaSeller.data.model.response.termsPrivacy.TermsPrivacyResponse;
import com.androdu.bananaSeller.data.model.response.wallet.WalletResponse;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitApi {
    @PUT("seller/signup")
    Call<LoginResponse> signUp(@Body SignUpRequestBody signUpRequestBody);

    @POST("seller/login")
    Call<LoginResponse> login(@Body LoginRequestBody loginRequestBody);

    @POST("delivery/login")
    Call<DeliveryLoginResponse> loginDelivery(@Body DeliveryLoginRequestBody deliveryLoginRequestBody);


    @GET("client/guest/products/{type}")
    Call<ProductsResponse> getProductsGuest(@Path("type") String type,
                                            @Header("Authorization") String token,
                                            @Query("page") int page,
                                            @Query("date") int date,
                                            @Query("sold") int sold,
                                            @Query("filter[]") List<String> filter);

    @GET("client/guest/search/products/{type}")
    Call<ProductsResponse> searchProductsGuest(@Path("type") String type,
                                               @Header("Authorization") String token,
                                               @Query("page") int page,
                                               @Query("searchQ") String searchQ);

    @GET("seller/orders")
    Call<OrdersResponse> getOrders(@Header("Authorization") String token,
                                   @Query("page") Integer page,
                                   @Query("filter") Integer filter,
                                   @Query("select[]") List<String> select);

    @GET("delivery/home")
    Call<MyOffersResponse> getDeliveryOffers(@Header("Authorization") String token,
                                           @Query("page") Integer page,
                                           @Query("filter") Integer filter);

    @GET("seller/guest/orders")
    Call<OrdersResponse> getOrdersGuest(@Query("page") Integer page,
                                        @Query("filter") Integer filter,
                                        @Query("select[]") List<String> select);

    @GET("seller/home")
    Call<SellerFieldsResponse> getFields(@Header("Authorization") String token);

    @GET("seller/myOrders")
    Call<MyOffersResponse> getMyOffers(@Header("Authorization") String token,
                                       @Query("page") Integer page,
                                       @Query("filter") String filter);

    @GET("seller/issues")
    Call<ComplaintsResponse> getComplaints(@Header("Authorization") String token,
                                           @Query("page") Integer page,
                                           @Query("filter") String filter);

    @POST("seller/order/delevered")
    Call<GeneralResponse> offerDelivered(@Header("Authorization") String token,
                                         @Body CancelOrderRequestBody cancelOrderRequestBody);

    @POST("delivery/offer/delivered")
    Call<GeneralResponse> offerDeliveredD(@Header("Authorization") String token,
                                         @Body CancelOrderRequestBody cancelOrderRequestBody);

    @GET("seller/wallet")
    Call<WalletResponse> getMyTransactions(@Header("Authorization") String token,
                                           @Query("page") int page);


    @GET("seller/notfication")
    Call<NotificationsResponse> getNotifications(@Header("Authorization") String token,
                                                 @Query("page") int page);

    @POST("seller/notfication/send")
    Call<GeneralResponse> notificationSettings(@Header("Authorization") String token,
                                               @Body NotificationSettingsRequestBody notificationSettingsRequestBody);


    @GET("seller/notfication/settings")
    Call<NotificationSettingsResponse> getNotificationSettings(@Header("Authorization") String token);


    @GET("seller/order/single/{orderId}")
    Call<OrderResponse> getOrder(@Header("Authorization") String token,
                                 @Path("orderId") String orderId);

    @GET("delivery/client/info/{orderId}")
    Call<ClientInfoResponse> getClientInfoD(@Header("Authorization") String token,
                                 @Path("orderId") String orderId);

    @GET("delivery/seller/info/{orderId}")
    Call<SellerInfoResponse> getSellerInfoD(@Header("Authorization") String token,
                                            @Path("orderId") String orderId);

    @GET("seller/support/conditions")
    Call<TermsPrivacyResponse> getTerms();

    @GET("seller/support/policy")
    Call<TermsPrivacyResponse> getPrivacy();

    @POST("seller/order/cancel")
    Call<GeneralResponse> cancelOrder(@Header("Authorization") String token,
                                      @Body CancelOrderRequestBody cancelOrderRequestBody);

    @POST("seller/offers/cancel")
    Call<GeneralResponse> cancelOffer(@Header("Authorization") String token,
                                      @Body CancelOfferRequestBody cancelOfferRequestBody);

    @POST("seller/offers/CreateCheckOut")
    Call<CheckOutResponse> createCheckout(@Header("Authorization") String token,
                                          @Body CancelOfferRequestBody cancelOfferRequestBody);

    @POST("seller/offers/checkPayment")
    Call<GeneralResponse> checkPay(@Header("Authorization") String token,
                                   @Body CheckPayRequestBody checkPayRequestBody);

    @POST("seller/signup/verfication/send")
    Call<GeneralResponse> signUpVerification(@Header("Authorization") String token);

    @POST("seller/signup/verfication/check")
    Call<GeneralResponse> signUpCheckCode(@Header("Authorization") String token,
                                          @Body CheckCodeRequestBody checkCodeRequestBody);

    @POST("seller/signup/verfication/changeMobile")
    Call<GeneralResponse> signUpChangePhone(@Header("Authorization") String token,
                                            @Body ChangePhoneRequestBody changePhoneRequestBody);

    @POST("seller/forgetPassword/mobile/sendSMS")
    Call<GeneralResponse> forgotPassVerification(@Header("Authorization") String token,
                                                 @Body EditMobileRequestBody editMobileRequestBody);

    @POST("seller/forgetPassword/mobile/verfy")
    Call<GeneralResponse> forgotPassCheckCode(@Header("Authorization") String token,
                                              @Body ForgotCheckCodeRequestBody forgotCheckCodeRequestBody);

    @POST("seller/forgetPassword/changePassword")
    Call<GeneralResponse> forgotPassChangePass(@Body NewPassRequestBody newPassRequestBody);

    @POST("seller/profile/edit/mobile")
    Call<GeneralResponse> profileEditMobileNum(@Header("Authorization") String token,
                                               @Body ChangePhoneRequestBody changePhoneRequestBody);

    @POST("seller/profile/edit/mobile/sendSMS")
    Call<GeneralResponse> editMobileVerification(@Header("Authorization") String token);

    @POST("seller/profile/edit/mobile/checkCode")
    Call<GeneralResponse> editMobileNumCheckCode(@Header("Authorization") String token,
                                                 @Body CheckCodeRequestBody checkCodeRequestBody);

    @GET("seller/profile/location")
    Call<MyAddressesResponse> getMyAddresses(@Header("Authorization") String token);

    @GET("seller/single/order/details/{orderId}")
    Call<ClientInfoResponse> getClientInfo(@Header("Authorization") String token,
                                           @Path("orderId") String orderId);

    @POST("seller/profile/add/location")
    Call<GeneralResponse> addNewLocation(@Header("Authorization") String token,
                                         @Body AddLocationRequestBody addLocationRequestBody);

    @POST("seller/profile/edit/name")
    Call<GeneralResponse> editName(@Header("Authorization") String token,
                                   @Body EditNameRequestBody editNameRequestBody);

    @PUT("seller/offer")
    Call<GeneralResponse> addOffer(@Header("Authorization") String token,
                                   @Body AddOfferRequestBody addOfferRequestBody);

    @POST("seller/profile/edit/password")
    Call<GeneralResponse> editPassword(@Header("Authorization") String token,
                                       @Body EditPasswordRequestBody editPasswordRequestBody);

    @POST("seller/support/contactUs")
    Call<GeneralResponse> contactUs(@Header("Authorization") String token,
                                    @Body ContactUsRequestBody contactUsRequestBody);

    @POST("seller/issue/refuse")
    Call<GeneralResponse> refuseComplaint(@Header("Authorization") String token,
                                          @Body ComplaintIdRequestBody complaintIdRequestBody);

    @POST("seller/issue/accept")
    Call<GeneralResponse> acceptComplaint(@Header("Authorization") String token,
                                          @Body ComplaintIdRequestBody complaintIdRequestBody);

    @POST("seller/profile/category/add")
    Call<GeneralResponse> addField(@Header("Authorization") String token,
                                   @Body FieldRequestBody fieldRequestBody);

    @POST("seller/profile/category/delete")
    Call<GeneralResponse> removeField(@Header("Authorization") String token,
                                      @Body FieldRequestBody fieldRequestBody);

    @POST("seller/support/issue")
    @Multipart
    Call<GeneralResponse> sendComplaint(@Header("Authorization") String token,
                                        @Part("orderId") String orderId,
                                        @Part("reason") String reason,
                                        @Part("demands") String demands,
                                        @Part MultipartBody.Part[] image);

    @POST("seller/profile/certificate")
    @Multipart
    Call<GeneralResponse> addCertificate(@Header("Authorization") String token,
                                         @Part("expiresAt") Long expiresAt,
                                         @Part("StringAdress") String stringAdress,
                                         @Part("long1") Double long1,
                                         @Part("lat1") Double lat1,
                                         @Part("openFrom") String openFrom,
                                         @Part("openTo") String openTo,
                                         @Part MultipartBody.Part[] image);


    @POST("seller/pullMony")
    Call<GeneralResponse> pullMoney(@Header("Authorization") String token, @Body PullBalanceRequestBody pullBalanceRequestBody);

    @POST("seller/profile/edit/lang")
    Call<GeneralResponse> changeLanguage(@Header("Authorization") String token,
                                         @Body ChangeLangRequestBody changeLangRequestBody);

    @POST("seller/logout")
    Call<GeneralResponse> logout(@Header("Authorization") String token, @Body LogoutRequestBody logoutRequestBody);

}

