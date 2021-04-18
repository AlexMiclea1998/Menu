package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.Interface.OnEventListener;
import com.example.menu.Utils.Common;


public class LoginActivity extends AppCompatActivity implements OnEventListener {
    private OnEventListener<String> mCallBack;

    EditText UsernameEt, PasswordEt;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        UsernameEt = (EditText) findViewById(R.id.edt_nameLogin);
        PasswordEt = (EditText) findViewById(R.id.edt_pass);


    }


    public void OnLogin2(View view) {
       String username ="";
        String password ="";
       username = UsernameEt.getText().toString();
       password = PasswordEt.getText().toString();


        String type= "login";


        BackgroundWorker backgroundWorker = new BackgroundWorker(this);

        backgroundWorker.execute(type,username,password);



    }
    public void OnForgot(View view) {

        Intent intent = new Intent(this, ForgotActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccess() {

      Intent intent = new Intent(this, HomeActivity.class);
       startActivity(intent);


    }

    @Override
    public void onFailure(Exception e) {

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}



