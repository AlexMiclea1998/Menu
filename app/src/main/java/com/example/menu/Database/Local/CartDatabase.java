package com.example.menu.Database.Local;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.menu.Database.ModelDB.Cart;

@Database(entities = {Cart.class},version = 1)
public abstract class CartDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
    private static CartDatabase instance;

    public static CartDatabase getInstance(Context context){
        if(instance==null)
            instance = Room.databaseBuilder(context,CartDatabase.class,"Cart_Menu")
                    .allowMainThreadQueries()
                    .build();
        return instance;
    }

}
