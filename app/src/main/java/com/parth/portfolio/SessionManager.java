package com.parth.portfolio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String MOBNO = "MOBNO";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String name, String email, String mobno){

        editor.putBoolean(LOGIN,true);
        editor.putString(NAME,name);
        editor.putString(EMAIL,email);
        editor.putString(MOBNO,mobno);
        editor.apply();
        editor.commit();
    }

    public boolean is_loggin(){

        return sharedPreferences.getBoolean(LOGIN,false);
    }

    public void checkLogin(){

        if(!this.is_loggin()){

            Intent intent = new Intent(context,Choose.class);
            context.startActivity(intent);
            ((HomeActivity) context).finish();

        }

    }

    public HashMap<String,String> getUserDetails(){

        HashMap<String,String> user = new HashMap<>();
        user.put(NAME,sharedPreferences.getString(NAME,null));
        user.put(EMAIL,sharedPreferences.getString(EMAIL,null));
        user.put(MOBNO,sharedPreferences.getString(MOBNO,null));
        return user;
    }

    public void logout(){

        editor.clear();
        editor.commit();

        Intent intent = new Intent(context,Choose.class);
        context.startActivity(intent);
        ((HomeActivity) context).finish();
    }

}
