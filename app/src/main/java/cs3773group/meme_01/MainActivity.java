package cs3773group.meme_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cs3773group.meme_01.AdminFunctions.AdminAreaActivity;
import cs3773group.meme_01.AdminFunctions.AdminViewUserActivity;
import cs3773group.meme_01.Models.adminModelz;
import cs3773group.meme_01.UserFunctions.UserAreaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String LOGIN_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/login.php";
    private static final String ONLINE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/userOnline.php";
    public static final String KEY_ONLINENAME = "user_name";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "user_pw";

    String user_name;
    String user_pw;
    private EditText txUsername;
    private EditText txPassword;

    private adminModelz admin = new adminModelz();


    private Button bLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txUsername = (EditText) findViewById(R.id.txUsername);
        txPassword = (EditText) findViewById(R.id.txPassword);
        final TextView registerLink = (TextView) findViewById(R.id.linkRegister);
        final TextView resetPasswordLink = (TextView) findViewById(R.id.linkResetPassword);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                MainActivity.this.startActivity(registerIntent);
            }
        });

        resetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resetPasswordIntent = new Intent(MainActivity.this, ResetPasswordActivity.class);
                MainActivity.this.startActivity(resetPasswordIntent);
            }
        });

        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(this);
    }

    private void userOnline(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ONLINE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(response != null) {
                                if (response != null) {
                                    if (jsonResponse.getString("success").equals("true"))
                                        Toast.makeText(MainActivity.this, "User Successfully Online", Toast.LENGTH_LONG).show();
                                    else if (jsonResponse.getString("success").equals("false"))
                                        Toast.makeText(MainActivity.this, "User Onlining Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put(KEY_ONLINENAME,user_name.toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void userLogin() {
        user_name = txUsername.getText().toString().trim();
        user_pw = txPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(response != null) {
                                Log.d("login json: ", jsonResponse.toString());
                                String username = jsonResponse.getJSONObject("user").getString("name");
                                //String username = jsonResponse.getJSONObject("user").getInt("name")+"";
                                //Integer username = jsonResponse.getJSONObject("user").getInt("name");
                                String user_type = jsonResponse.getJSONObject("user").getString("type");
                                String user_lock_status = jsonResponse.getJSONObject("user").getString("locked");
                                String type = jsonResponse.getString("user_id");
                                Log.d("Test", response.toString());
                                Intent intent = new Intent();
                                if(user_type.equals("A")) {
                                    intent = new Intent(MainActivity.this, AdminAreaActivity.class);
                                    intent.putExtra("username", username);
                                    MainActivity.this.startActivity(intent);
                                    userOnline();
                                }
                                else if(user_type.equals("U") && user_lock_status.equals("0")) {
                                    intent = new Intent(MainActivity.this, UserAreaActivity.class);
                                    intent.putExtra("username", username);
                                    MainActivity.this.startActivity(intent);
                                    userOnline();
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Login Failed, You Are Locked", Toast.LENGTH_LONG).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                Log.d("username: ", user_name);
                Log.d("password: ", user_pw);
                map.put(KEY_USERNAME,user_name.toString());
                map.put(KEY_PASSWORD,user_pw.toString());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == bLogin){
            userLogin();
        }
    }
}
