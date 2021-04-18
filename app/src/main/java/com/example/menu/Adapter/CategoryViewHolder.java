package com.example.menu.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menu.R;

import com.example.menu.Interface.IItemClickListener;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView img_product;
    TextView txt_menu_name;
    IItemClickListener itemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener){
        this.itemClickListener=itemClickListener;
    }

    //Category layout
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);


        img_product = (ImageView)itemView.findViewById(R.id.image_product);
        txt_menu_name=(TextView)itemView.findViewById(R.id.txt_menu_name);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
     itemClickListener.onClick(v);
    }
}
