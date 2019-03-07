package com.parth.portfolio;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    Button register,login;
    EditText lmail,lpass;
    private ProgressBar loading;
    String Login_URL="https://medicaljava.000webhostapp.com/login.php";
    SessionManager sessionManager;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this,Choose.class));
        finish();
    }


    boolean isEmail(String text){
        CharSequence email = text.toString();
        return(!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        register=(Button)findViewById(R.id.button2);
        login=(Button)findViewById(R.id.login);
        lmail=(EditText)findViewById(R.id.lemail);
        lpass=(EditText)findViewById(R.id.lpassword);
        loading = findViewById(R.id.pbLoading);
        sessionManager = new SessionManager(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
            }
        });



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u_email = lmail.getText().toString().trim();
                String u_password = lpass.getText().toString().trim();


                if(u_email.isEmpty())
                    lmail.setError("Enter Your Email Address");
                else if(isEmail(u_email) == false)
                    lmail.setError("Enter Valid Email Address");
                else if(u_password.isEmpty())
                    lpass.setError("Enter Your Password");
                else{
                    Toast.makeText(getApplicationContext(),
                            "Login In Progress", Toast.LENGTH_SHORT).show();
                    loginnow(u_email,u_password);
                }


            }
        });

    }




    private void loginnow(final String u_email,final String u_password)
    {
        login.setVisibility(View.GONE);
        loading.setVisibility(View.VISIBLE);
        register.setVisibility(View.GONE);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, Login_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String name,email,mobno;



                        try {
                            Toast.makeText(getApplicationContext(),
                                    response, Toast.LENGTH_SHORT).show();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jArray = jsonObject.getJSONArray("login");
                            JSONObject object=new JSONObject();
                            String msg = jsonObject.getString("success");



                            if(msg.equals("1"))
                            {
                                Toast.makeText(Login.this, "Logged In  1 !", Toast.LENGTH_SHORT).show();

                                for(int i=0;i<jArray.length();i++)
                                {
                                    object = jArray.getJSONObject(i);
                                }
                                    name = object.getString("name").trim();
                                    email = object.getString("email").trim();
                                    mobno = object.getString("mobno").trim();
                                Toast.makeText(Login.this, ""+name, Toast.LENGTH_SHORT).show();
                                Toast.makeText(Login.this, ""+mobno, Toast.LENGTH_SHORT).show();

                                    sessionManager.createSession(name, email, mobno);
                                    Toast.makeText(Login.this, "Logged In !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Login.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();


                            }
                            else if(msg.equals("0"))
                            {Toast.makeText(Login.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, HomeActivity.class));
                                finish();
                            }

                        }
                        catch(JSONException e)

                        {


                            e.printStackTrace();
                            Toast.makeText(Login.this, "Error in Logging In!"+e.toString(),
                                    Toast.LENGTH_SHORT).show();
                            login.setVisibility(View.VISIBLE);
                            loading.setVisibility(View.GONE);
                            register.setVisibility(View.VISIBLE);


                        }





                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                loading.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
                login.setVisibility(View.VISIBLE);
                //ar.setVisibility(View.VISIBLE);

                Toast.makeText(Login.this, "Error in Logging In !"+error.toString(),
                        Toast.LENGTH_SHORT).show();

            }
        })
        {

            @Override
            protected Map<String,String> getParams() {


                Map<String,String> params = new HashMap<>();
                params.put("email",u_email);
                params.put("password",u_password);
                return params;


            }



        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}



