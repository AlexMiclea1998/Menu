package com.example.menu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menu.Adapter.CartAdapter;
import com.example.menu.Database.ModelDB.Cart;
import com.example.menu.Retrofit.IMenuAPI;
import com.example.menu.Utils.Common;
import com.google.gson.Gson;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    RecyclerView recycler_cart;
    Button btn_place_order;
    IMenuAPI mService;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        compositeDisposable = new CompositeDisposable();
        mService = Common.getApi();

        recycler_cart = (RecyclerView)findViewById(R.id.recycler_cart);
        recycler_cart.setLayoutManager(new LinearLayoutManager(this));
        recycler_cart.setHasFixedSize(true);

        btn_place_order=(Button)findViewById(R.id.btn_place_order);
        btn_place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });

        loadCartItems();
    }

    private void placeOrder() {
        //Create Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Submit Order");
        View submit_order_layout = LayoutInflater.from(this).inflate(R.layout.submit_order_layout,null);

        final EditText edt_comment=(EditText)submit_order_layout.findViewById(R.id.edt_comment);
        final EditText edt_nrmasa=(EditText)submit_order_layout.findViewById(R.id.edt_nrmasa);

        builder.setView(submit_order_layout);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String orderComment = edt_comment.getText().toString();
                 final String nrMasa = edt_nrmasa.getText().toString();

                //Submit Order

                compositeDisposable.add(
                        Common.cartRepository.getCartItems()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<List<Cart>>() {
                            @Override
                            public void accept(List<Cart> carts) throws Exception {

                                if(!TextUtils.isEmpty(nrMasa)){
                                    sendOrderToServer(Common.cartRepository.sumPrice(),carts,
                                            orderComment,nrMasa);
                                }else{
                                    Toast.makeText(CartActivity.this, "Table id can not be empty!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                );

            }
        });
        builder.show();

    }

    private void sendOrderToServer(float sumPrice, List<Cart> carts, String orderComment,String nrMasa) {
        if(carts.size()>0){
            String orderDetail = new Gson().toJson(carts);

            mService.submitOrder(sumPrice,orderDetail,orderComment,nrMasa).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    Toast.makeText(CartActivity.this, "Order was placed", Toast.LENGTH_SHORT).show();

                    // Clear Cart
                    Common.cartRepository.emptyCart();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Error",t.getMessage());


                }
            });
        }
    }


    private void loadCartItems() {
        compositeDisposable.add(
                Common.cartRepository.getCartItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Cart>>() {
                    @Override
                    public void accept(List<Cart> carts) throws Exception {
                        displayCartItem(carts);
                    }
                })
        );
    }

    private void displayCartItem(List<Cart> carts) {
        CartAdapter cartAdapter = new CartAdapter(this,carts);
        recycler_cart.setAdapter(cartAdapter);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
