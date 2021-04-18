package com.example.menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import com.example.menu.Adapter.ProductAdapter;

import com.example.menu.Model.Product;
import com.example.menu.Retrofit.IMenuAPI;
import com.example.menu.Utils.Common;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProductsActivity extends AppCompatActivity {

    IMenuAPI mService;
    RecyclerView firstProduct ;
    TextView txt_banner_name;

    //Rxjava
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        mService= Common.getApi();

        firstProduct=(RecyclerView)findViewById(R.id.recicler_products);
        firstProduct.setLayoutManager(new GridLayoutManager(this,2));
        firstProduct.setHasFixedSize(true);

        txt_banner_name = (TextView)findViewById(R.id.txt_product_name);
       // txt_banner_name.setText(Common.currentCategory.Name);



        loadListProducts((Common.currentCategory.ID).toString());
        
    }

    private void loadListProducts(String categoryid) {
        compositeDisposable.add(mService.newProduct(categoryid)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                })
        .subscribe(new Consumer<List<Product>>() {
            @Override
            public void accept(List<Product> products) throws Exception {
                displayProductList(products);
            }
        }));
    }

    private void displayProductList(List<Product> products) {
        ProductAdapter adapter = new ProductAdapter(this,products);
        firstProduct.setAdapter(adapter);

    }


}

