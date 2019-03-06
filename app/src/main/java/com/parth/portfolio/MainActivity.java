package com.parth.portfolio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.text.TextUtils;


import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.*;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {


    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this,Choose.class));
        finish();
    }

    Button register,ar;
    EditText name,email,password,mobno,pass,cpass;
    private static String Register_URL="https://medicaljava.000webhostapp.com/register.php";
    private ProgressBar loading;

    boolean isEmail(String text){
        CharSequence email = text.toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }
    boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.register);


        register= findViewById(R.id.register);
        ar= findViewById(R.id.button2);
        email= findViewById(R.id.remail);
        name= findViewById(R.id.rname);
        mobno= findViewById(R.id.rnumber);
        pass= findViewById(R.id.rpass);
        cpass= findViewById(R.id.rcpass);
        loading = findViewById(R.id.pbLoading);




ar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startActivity(new Intent(MainActivity.this,Login.class));
        finish();
    }
});

register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String u_name = name.getText().toString().trim();
        String u_mob = mobno.getText().toString().trim();
        String u_email = email.getText().toString().trim();
        String u_password = pass.getText().toString().trim();
        String u_c_password = cpass.getText().toString().trim();







        if(u_name.isEmpty())
            name.setError("Enter Your Name");
        else if(u_email.isEmpty())
            email.setError("Enter Your Email Address");
        else if(isEmail(u_email) == false)
            email.setError("Enter Valid Email Address");
        else if(u_mob.isEmpty())
            mobno.setError("Enter Your Mobile Number");
        else if(isValidMobile(u_mob) == false)
            mobno.setError("Enter Valid Mobile Number");
        else if(u_password.isEmpty())
            pass.setError("Enter Your Password");
        else if(u_c_password.isEmpty())
            cpass.setError("Re-enter Your Password");
        else if(!u_password.equals(u_c_password))
            cpass.setError("Passwords Don't Match");
        else {

            Toast.makeText(getApplicationContext(),
                    "Registration In Progress", Toast.LENGTH_SHORT).show();
            registration(u_name,u_email,u_password,u_mob);

        }

    }
});
    }

    private void registration(final String u_name, final String u_email, final String u_password,final String u_mob)
    {
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);
        ar.setVisibility(View.GONE);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, Register_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
try {
    Toast.makeText(getApplicationContext(),
            response, Toast.LENGTH_SHORT).show();
    JSONObject jsonObject = new JSONObject(response);

    String msg = jsonObject.getString("success");


    if(msg.equals("3"))
    {Toast.makeText(MainActivity.this, "Email-ID Already Registered", Toast.LENGTH_SHORT).show();
        loading.setVisibility(View.GONE);
        register.setVisibility(View.VISIBLE);
        ar.setVisibility(View.VISIBLE);
    }
    else if(msg.equals("4"))
    {Toast.makeText(MainActivity.this, "Mobile Number Already Registered", Toast.LENGTH_SHORT).show();
        loading.setVisibility(View.GONE);
        register.setVisibility(View.VISIBLE);
        ar.setVisibility(View.VISIBLE);
    }
    else if (msg.equals("1")) {
        Toast.makeText(MainActivity.this, "Registration Done !", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, Login.class));
        finish();
    }
}
catch(JSONException e)

{


    e.printStackTrace();
    Toast.makeText(MainActivity.this, "Error in Registration !"+e.toString(),
            Toast.LENGTH_SHORT).show();
    loading.setVisibility(View.GONE);
    register.setVisibility(View.VISIBLE);
    ar.setVisibility(View.VISIBLE);

}

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        register.setVisibility(View.VISIBLE);
                        ar.setVisibility(View.VISIBLE);

                        Toast.makeText(MainActivity.this, "Error in Registration !"+error.toString(),
                                Toast.LENGTH_SHORT).show();

                    }
                }
        )
        {
                @Override
        protected Map<String,String> getParams() {


        Map<String,String> params = new HashMap<>();
        params.put("name",u_name);
        params.put("email",u_email);
        params.put("password",u_password);
        params.put("mobno",u_mob);
        return params;


        }
    };





        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);




    }
}
