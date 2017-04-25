package cs3773group.meme_01.Models;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.lang.Object;
import java.security.SecureRandom;

import cs3773group.meme_01.AdminAreaActivity;
import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.UserAreaActivity;

/**
 * Created by Tom on 4/25/2017.
 */

public class adminModelz {
    private int username;
    private String password;

    public adminModelz(){
        this.username = 0;
        this.password = "";
        //AdminAreaActivity john = new AdminAreaActivity();
        //john.addUserToDatabase();
    }

    public adminModelz(int username, String password){
        this.username = username;
        this.password = password;
    }

    public int getUsername(){
        return this.username;
    }

    public void setUsername(int username){
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



 }
