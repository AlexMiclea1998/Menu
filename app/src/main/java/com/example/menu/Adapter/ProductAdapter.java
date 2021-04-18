package com.example.menu.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.menu.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.menu.Database.ModelDB.Cart;
import com.example.menu.Interface.IItemClickListener;
import com.example.menu.Model.Product;
import com.example.menu.Utils.Common;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {

    Context context;
    List<Product>productList;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(this.context).inflate(R.layout.product_item_layout,null);

        return new ProductViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, final int position) {

        holder.txt_price.setText(new StringBuffer("$").append(productList.get(position).Price).toString());
        holder.txt_product_name.setText(productList.get(position).Name);
        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddToCartDialog(position);
            }
        });

        Picasso.with(context)
                .load(productList.get(position).Link)
                .into(holder.img_product);

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Clicked",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showAddToCartDialog(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                    .inflate(R.layout.add_to_cart_layout,null);


        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_cart_product);
        final ElegantNumberButton txt_count = (ElegantNumberButton)itemView.findViewById(R.id.txt_count);
        txt_count.setNumber("1");
        TextView txt_product_dialog =(TextView)itemView.findViewById(R.id.txt_cart_product_name);

        EditText edt_commnet =(EditText)itemView.findViewById(R.id.edt_comment);

        RadioButton rdi_sizeM =(RadioButton)itemView.findViewById(R.id.rdi_sizeM);
        RadioButton rdi_sizeL =(RadioButton)itemView.findViewById(R.id.rdi_sizeL);

        rdi_sizeM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   Common.sizOfProduct=0;
                }
            }
        });

        rdi_sizeL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Common.sizOfProduct=1;
                }
            }
        });


        RadioButton rdi_here =(RadioButton)itemView.findViewById(R.id.rdi_here);
        RadioButton rdi_togo =(RadioButton)itemView.findViewById(R.id.rdi_togo);


        rdi_here.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Common.where=0;
                }
            }
        });

        rdi_togo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    Common.where=1;
                }
            }
        });



        RecyclerView recycler_topping = (RecyclerView)itemView.findViewById(R.id.recycler_topping);
        recycler_topping.setLayoutManager(new LinearLayoutManager(context));
        recycler_topping.setHasFixedSize(true);

        MultiChoiceAdapter adapter = new MultiChoiceAdapter(context, Common.toppingList);
        recycler_topping.setAdapter(adapter);


        //Get Data

        Picasso.with(context)
                .load(productList.get(position).Link)
                .into(img_product_dialog);
        txt_product_dialog.setText(productList.get(position).Name);

        builder.setView(itemView);
        builder.setNegativeButton("ADD TO CART", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(Common.sizOfProduct==-1){
                    Toast.makeText(context, "Please choose a size",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Common.where==-1){
                    Toast.makeText(context, "Please choose here or to go",Toast.LENGTH_SHORT).show();
                    return;
                }

                showConfirmDialog(position,txt_count.getNumber());
                dialog.dismiss();
            }
        });

        builder.show();



    }

    private void showConfirmDialog(final int position, final String number) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.confirm_add_to_cart_layout,null);


        //View

        ImageView img_product_dialog = (ImageView)itemView.findViewById(R.id.img_product);
        final TextView txt_product_dialog =(TextView)itemView.findViewById(R.id.txt_cart_product_name);
        final TextView txt_product_price =(TextView)itemView.findViewById(R.id.txt_cart_product_price);
        TextView txt_product_sugar =(TextView)itemView.findViewById(R.id.txt_sugar);
        final TextView txt_topping_extra =(TextView)itemView.findViewById(R.id.txt_topping_extra);

        //Set Data
        Picasso.with(context).load(productList.get(position).Link).into(img_product_dialog);
        txt_product_dialog.setText(new StringBuilder(productList.get(position).Name).append(" x ")
        .append(number)
        .append(Common.sizOfProduct == 0 ? " Size M ":" Size L ").toString());

        double price = ((Double.parseDouble(productList.get(position).Price)+Common.toppingPrice)*Double.parseDouble(number));

        if(Common.sizOfProduct==1){
            price+=2.0*Double.parseDouble(number);
        }

        if(Common.where==0){
            txt_product_sugar.setText("Here");

        }if(Common.where==1){

            txt_product_sugar.setText("ToGo");
            price+=1.0*Double.parseDouble(number);
        }

        txt_product_price.setText(new StringBuilder("$").append(price));

        StringBuilder topping_final_comment = new StringBuilder("");
        for(String line : Common.toppingAdded)
            topping_final_comment.append(line).append("\n");

        txt_topping_extra.setText(topping_final_comment);
        final double finalPrice = price;
        builder.setNegativeButton("CONFIRM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        dialog.dismiss();
                        try{
                        //add SQLite
                        //create new Cart item
                        Cart cartItem = new Cart();
                        cartItem.name=txt_product_dialog.getText().toString();
                        cartItem.amount=Integer.parseInt(number);
                        cartItem.where=Common.where;
                        cartItem.price= finalPrice;
                        cartItem.toppingExtra = txt_topping_extra.getText().toString();
                        cartItem.link = productList.get(position).Link;


                        //Add to DB

                        Common.cartRepository.insertToCart(cartItem);

                        Log.d("MENU_DEBUG", new Gson().toJson(cartItem));
                        Toast.makeText(context, "Item Succesfully saved to cart",Toast.LENGTH_SHORT).show();
                       Common.toppingAdded.clear();
                       Common.toppingPrice=0.0;


            }catch (Exception ex){
                            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
        });

        builder.setView(itemView);
        builder.show();



    }


    @Override
    public int getItemCount() {

        return productList.size();
    }
}
