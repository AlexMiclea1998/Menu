package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.Interface.OnEventListener;

public class ForgotActivity extends AppCompatActivity implements OnEventListener {


        private OnEventListener<String> mCallBack;

        EditText  BirthdateEt,PhoneEt;

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.recover1_layout);



            PhoneEt = (EditText) findViewById(R.id.edt_phone);
            BirthdateEt = (EditText) findViewById(R.id.edt_birthdate);


        }

        public void OnChange(View view) {
            String phone = PhoneEt.getText().toString();
            String birthdate = BirthdateEt.getText().toString();


            String type= "checkUser";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type,phone,birthdate);

        }


        @Override
        public void onSuccess() {
            Intent intent = new Intent(this,ChangePassActivity.class);
            startActivity(intent);
        }

        @Override
        public void onFailure(Exception e) {
            Toast.makeText(this, "Cont Inexistent", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, com.example.menu.RegisterActivity.class);
            startActivity(intent);
        }

    }






