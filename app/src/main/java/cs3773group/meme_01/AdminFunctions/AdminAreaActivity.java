package cs3773group.meme_01.AdminFunctions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.R;
import cs3773group.meme_01.Models.userModels;
import cs3773group.meme_01.UserFunctions.InboxActivity;
import cs3773group.meme_01.UserFunctions.ViewMessageActivity;

public class AdminAreaActivity extends AppCompatActivity implements View.OnClickListener{
    private String adminUsername;
    private String userUsername;
    private String userPassword;
    private Button bCreateUser;
    private Button bDisplayUsers;

    private static final String LOGIN_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/insertUser.php";
    private static final String GET_USERS_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/getUsers.php";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_PASSWORD = "user_pw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_area);

        final TextView txUsername = (TextView) findViewById(R.id.txUsername);
        final TextView logoutLink = (TextView) findViewById(R.id.linkLogout);
        bCreateUser = (Button) findViewById(R.id.bCreateUser);
        bCreateUser.setOnClickListener(this);
        bDisplayUsers = (Button) findViewById(R.id.bDisplayUsers);
        bDisplayUsers.setOnClickListener(this);
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(AdminAreaActivity.this, MainActivity.class);
                AdminAreaActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();
        adminUsername = intent.getStringExtra("username");

        txUsername.setText(adminUsername);
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
                                if(jsonResponse.getString("success").equals("true"))
                                    Toast.makeText(AdminAreaActivity.this, "User Successfully Created", Toast.LENGTH_LONG).show();
                                else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(AdminAreaActivity.this, "User Creation Failed", Toast.LENGTH_LONG).show();
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
                Map<String, String> map = new HashMap<>();
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
        int usernameSize = 10 + rand.nextInt(6);
        char[] newUsername = new char[usernameSize];

        for(int i=0; i<usernameSize; i++){
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
    public void getUsers() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USERS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /* ON Response we want to parse the JSON into users, and create
                               an ArrayList of userModels. We then put the list as an extra for
                               the UserListActivity
                             */
                            JSONArray jsonUserArray = new JSONArray(response);
                            if (response != null) {
                                ArrayList<userModels> users = new ArrayList<userModels>(); // will hold list of users
                                Integer len = response.length();
                                for (int i = 0; i<jsonUserArray.length(); i++) {
                                    JSONObject jsonUser = new JSONObject();
                                    jsonUser = jsonUserArray.getJSONObject(i);  //one user object from the JSON
                                    userModels user = new userModels();
                                    //create a user object from the JSON, and add to list
                                    user.setUsername(jsonUser.getJSONObject("user").getString("name"));
                                    user.setPassword(jsonUser.getJSONObject("user").getString("pw"));
                                    user.setLocked(Integer.parseInt(jsonUser.getJSONObject("user").getString("locked")));
                                    user.setOnline(Integer.parseInt(jsonUser.getJSONObject("user").getString("online")));
                                    Log.d("USERNAME", user.getUsername());
                                    users.add(user);
                                }
                                Intent intent = new Intent(AdminAreaActivity.this, UserListActivity.class);
                                intent.putExtra("admin", adminUsername);
                                intent.putExtra("users", users);
                                AdminAreaActivity.this.startActivity(intent);
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
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public void onClick(View v) {
        if(v == bCreateUser){
            addUserToDatabase();
            //Popup displaying new user creds
            String usernameString = ("Username: " + userUsername);
            String userpasswordString = ("Password: " + userPassword);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New User Credentials");
            builder.setMessage(usernameString + "\n\n" + userpasswordString);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }
        else if(v == bDisplayUsers){
            //get list of users, and hand it off to next activity
            getUsers();
        }
    }
}
