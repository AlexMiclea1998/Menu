package com.example.menu.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.menu.Database.DataSource.CartRepository;
import com.example.menu.Database.Local.CartDatabase;
import com.example.menu.Model.Category;
import com.example.menu.Model.Product;
import com.example.menu.Retrofit.IMenuAPI;
import com.example.menu.Retrofit.RetrofitClient;

public class Common {
    //in emulator localhost is 10.0.2.2
    private static final String BASE_URL = "http://10.0.2.2/menu/";



    public static final String TOPPING_MENU_ID="11";
    public static String userName="";


    //For recover and change pass
    public static String phone="";
    public static String birthdate="";
    ///////////////////////////////////



    public static Category currentCategory=null;
    public static List<Product> toppingList= new ArrayList<>();

    public static  double toppingPrice=0.00;
    public static List<String> toppingAdded = new ArrayList<>();

    //Hold Field
    public static  int sizOfProduct=-1;//-1 (error), 0 : M  , 1 : L
    public static int where = -1; //0 : here , 1 : togo


    //Room com.example.menu.Database
    public static CartDatabase cartDatabase;
    public static CartRepository cartRepository;

    public static IMenuAPI getApi(){
        return RetrofitClient.getClient(BASE_URL).create(IMenuAPI.class);
    }
}
