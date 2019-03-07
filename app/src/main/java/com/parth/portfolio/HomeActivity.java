package com.parth.portfolio;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {


    TextView name,email,mobno;
    Button logout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);
        name=findViewById(R.id.sname);
        email=findViewById(R.id.semail);
        mobno=findViewById(R.id.smobno);
        logout=findViewById(R.id.logout);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String,String> user = sessionManager.getUserDetails();



        name.setText(user.get(sessionManager.NAME));
        email.setText(user.get(sessionManager.EMAIL));
        mobno.setText(user.get(sessionManager.MOBNO));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });


    }
}
