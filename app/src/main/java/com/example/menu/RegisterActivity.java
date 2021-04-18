package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.Interface.OnEventListener;


public class RegisterActivity extends AppCompatActivity implements OnEventListener {
    private OnEventListener<String> mCallBack;

    EditText UsernameEt, PasswordEt,PhoneEt,BirthdateEt,AddressEt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);


        UsernameEt = (EditText) findViewById(R.id.edt_name);
        PasswordEt = (EditText) findViewById(R.id.edt_password);
        PhoneEt = (EditText) findViewById(R.id.edt_phone);
        BirthdateEt = (EditText) findViewById(R.id.edt_birthdate);
        AddressEt = (EditText) findViewById(R.id.edt_address);

    }

    public void OnRegister2(View view) {
        String username = UsernameEt.getText().toString();
        String password = PasswordEt.getText().toString();
        String phone = PhoneEt.getText().toString();
        String birthdate = BirthdateEt.getText().toString();
        String address = AddressEt.getText().toString();

        String type= "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(this);
        backgroundWorker.execute(type,username,password,phone,birthdate,address);

    }


    @Override
    public void onSuccess() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(Exception e) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    }




