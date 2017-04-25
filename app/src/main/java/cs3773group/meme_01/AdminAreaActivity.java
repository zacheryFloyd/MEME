package cs3773group.meme_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdminAreaActivity extends AppCompatActivity implements View.OnClickListener{
    private String userUsername;
    private String userPassword;

    private static final String LOGIN_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/insertUser.php";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "user_pw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        final EditText txUsername = (EditText) findViewById(R.id.txUsername);
        final EditText txType = (EditText) findViewById(R.id.txType);
        final TextView loginLink = (TextView) findViewById(R.id.linkLogin);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(AdminAreaActivity.this, MainActivity.class);
                AdminAreaActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        String type = intent.getStringExtra("type");

        txUsername.setText(username);
        txType.setText(type);
    }
    public void addUserToDatabase() {
        generateNewUser();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (response != null) {
                                Log.d("JSON", jsonResponse.toString());
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, userUsername);
                map.put(KEY_PASSWORD, userPassword);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void generateNewUser() {

        //password requirements
        String UpperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LowerCase = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$";
        SecureRandom sr = new SecureRandom();

        //random 10 string username
        Random rand = new Random();
        char[] newUsername = new char[10];

        for(int i=0; i<10; i++){
            newUsername[i] = numbers.toCharArray()[sr.nextInt(numbers.toCharArray().length)];
        }

        //password size 16-20
        int passwordSize = 16 + rand.nextInt(5);
        char[] password = new char[passwordSize];

        for (int i=0; i < passwordSize; i++){
            password[i] = UpperCase.toCharArray()[sr.nextInt(UpperCase.toCharArray().length)];
            i++;
            if(i < passwordSize) {
                password[i] = LowerCase.toCharArray()[sr.nextInt(LowerCase.toCharArray().length)];
                i++;
            }
            if(i < passwordSize) {
                password[i] = numbers.toCharArray()[sr.nextInt(numbers.toCharArray().length)];
                i++;
            }
            if(i < passwordSize) {
                password[i] = symbols.toCharArray()[sr.nextInt(symbols.toCharArray().length)];
            }
        }
        userPassword = new String(password);
        userUsername = new String(newUsername);
        Log.d("Password",userPassword);
        Log.d("Username", userUsername);
    }

    @Override
    public void onClick(View v) {
        /*if(v == bCreateUser){
            addUserToDatabase();
        }*/
    }
}
