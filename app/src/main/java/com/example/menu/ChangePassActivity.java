package com.example.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.menu.Interface.OnEventListener;
import com.example.menu.Utils.Common;

public class ChangePassActivity extends AppCompatActivity implements OnEventListener {


    private OnEventListener<String> mCallBack;

    EditText PasswordEt1, PasswordEt2;
    TextView username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recover2_layout);

        PasswordEt1 = (EditText) findViewById(R.id.edt_pass);
        PasswordEt2 = (EditText) findViewById(R.id.edt_pass2);
        username=(TextView) findViewById(R.id.txt_user_name);
        if(Common.userName.contains("/n/")){
            String aux1 =Common.userName.replace("/n/"," ");
            String aux= "Username: ".concat(aux1);
            username.setText(aux);
        }




    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onFailure(Exception e) {

        Toast.makeText(this, "Parolele nu coincid!", Toast.LENGTH_SHORT).show();

    }

    public void OnChange(View view) {


        String pass1=PasswordEt1.getText().toString();
        String pass2=PasswordEt2.getText().toString();
      if(pass1.equals(pass2)) {
          String type = "upPass";
          BackgroundWorker backgroundWorker = new BackgroundWorker(this);
          backgroundWorker.execute(type, pass1);
      }else{
          Toast.makeText(this, "Parola1 :"+pass1, Toast.LENGTH_SHORT).show();
          Toast.makeText(this, pass2, Toast.LENGTH_SHORT).show();
          Toast.makeText(this, "Parolele nu aici coincid!", Toast.LENGTH_SHORT).show();
      }
    }
}
