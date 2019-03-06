package com.parth.portfolio;


import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Choose extends AppCompatActivity {
    Button register,login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainchoose);

        register= findViewById(R.id.register);
       login= findViewById(R.id.login);

       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Choose.this,MainActivity.class));
               finish();
           }
       });
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Choose.this,Login.class));
               finish();
           }
       });

    }
}
