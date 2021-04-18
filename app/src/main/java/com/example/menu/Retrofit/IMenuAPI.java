package com.example.menu.Retrofit;

import java.util.List;

import com.example.menu.Model.Banner;
import com.example.menu.Model.Category;
import com.example.menu.Model.Product;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IMenuAPI {




    @FormUrlEncoded
    @POST("getproduct.php")
    Observable<List<Product>> newProduct (@Field("categoryid")String categoryid);


    @GET("getbanner.php")
    Observable<List<Banner>> getBanners();

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("submitorder.php")
    Call<String> submitOrder(@Field("orderPrice")float orderPrice,
                             @Field("orderDetail") String orderDetail,
                             @Field("orderComment")String comment,
                             @Field("nrTable")String nrTable);


}
