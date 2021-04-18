package com.example.menu.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.menu.ProductsActivity;
import com.example.menu.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.example.menu.Interface.IItemClickListener;
import com.example.menu.Model.Category;
import com.example.menu.Utils.Common;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    Context context;

    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    List<Category>categories;


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(this.context).inflate(R.layout.menu_item_layout,null);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position) {
      //Load Image category
        Picasso.with(context)
                .load(categories.get(position).Link)
                .into(holder.img_product);

        holder.txt_menu_name.setText(categories.get(position).Name);

        //Event
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentCategory=categories.get(position);

                //Start newActivity
                context.startActivity(new Intent(context,ProductsActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }
}
