package cs3773group.meme_01.AdminFunctions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/26/2017.
 */

public class AdminViewUserActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bBack;
    private Button bLock;
    private Button bUnlock;
    private Button bDelete;
    private String adminUsername;
    private String username;
    private String online;
    private String lock;

    private static final String LOCK_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/lockUser.php";
    private static final String UNLOCK_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/unlockUser.php";
    private static final String DELETE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/deleteUser.php";

    public static final String KEY_USERNAME = "user_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user);

        final TextView txUsername = (TextView) findViewById(R.id.txUsername);
        final TextView txOnline = (TextView) findViewById(R.id.txOnline);
        final TextView txLock = (TextView) findViewById(R.id.txLock);

        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bLock = (Button) findViewById(R.id.bLock);
        bLock.setOnClickListener(this);
        bUnlock = (Button) findViewById(R.id.bUnlock);
        bUnlock.setOnClickListener(this);
        bDelete = (Button) findViewById(R.id.bDelete);
        bDelete.setOnClickListener(this);

        Intent intent = getIntent();
        adminUsername = intent.getStringExtra("admin");
        username = intent.getStringExtra("username");
        online = intent.getStringExtra("online");
        lock = intent.getStringExtra("lock");

        txUsername.setText(username);
        if(online.equals("ONLINE"))
            txOnline.setTextColor(Color.GREEN);
        else
            txOnline.setTextColor(Color.RED);
        txOnline.setText(online);
        if(lock.equals("UNLOCKED"))
            txLock.setTextColor(Color.GREEN);
        else
            txLock.setTextColor(Color.RED);
        txLock.setText(lock);
    }

    @Override
    public void onClick(View v) {
        if(v == bBack){
            Intent intent = new Intent(AdminViewUserActivity.this, AdminAreaActivity.class);
            intent.putExtra("username", adminUsername);
            AdminViewUserActivity.this.startActivity(intent);
        }
        else if(v == bLock){
            userChangeLockStatus(1);
            Intent intent = new Intent(AdminViewUserActivity.this, AdminAreaActivity.class);
            intent.putExtra("username", adminUsername);
            AdminViewUserActivity.this.startActivity(intent);
        }
        else if(v == bUnlock){
            userChangeLockStatus(0);
            Intent intent = new Intent(AdminViewUserActivity.this, AdminAreaActivity.class);
            intent.putExtra("username", adminUsername);
            AdminViewUserActivity.this.startActivity(intent);
        }
        else if(v == bDelete){
            deleteUser();
            Intent intent = new Intent(AdminViewUserActivity.this, AdminAreaActivity.class);
            intent.putExtra("username", adminUsername);
            AdminViewUserActivity.this.startActivity(intent);
        }
    }

    public void userChangeLockStatus(int status){
        if(status == 0) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UNLOCK_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (response != null) {
                                    if (jsonResponse.getString("success").equals("true"))
                                        Toast.makeText(AdminViewUserActivity.this, "User Successfully Unlocked", Toast.LENGTH_LONG).show();
                                    else if (jsonResponse.getString("success").equals("false"))
                                        Toast.makeText(AdminViewUserActivity.this, "User Unlocking Failed", Toast.LENGTH_LONG).show();
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
                    map.put(KEY_USERNAME, username);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
        else{
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOCK_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                if (response != null) {
                                    if (jsonResponse.getString("success").equals("true"))
                                        Toast.makeText(AdminViewUserActivity.this, "User Successfully Locked", Toast.LENGTH_LONG).show();
                                    else if (jsonResponse.getString("success").equals("false"))
                                        Toast.makeText(AdminViewUserActivity.this, "User Locking Failed", Toast.LENGTH_LONG).show();
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
                    map.put(KEY_USERNAME, username);
                    return map;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    public void deleteUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (response != null) {
                            if(jsonResponse.getString("success").equals("true"))
                                Toast.makeText(AdminViewUserActivity.this, "User Successfully Deleted", Toast.LENGTH_LONG).show();
                            else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(AdminViewUserActivity.this, "User Deletion Failed", Toast.LENGTH_LONG).show();
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
                map.put(KEY_USERNAME, username);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

/*public void userChangeLockStatus(int status){
    StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("delete response)", response);
                    try {
                        JSONObject jsonResponse = new JSONObject(response);

                        if (response != null) {
                            if(jsonResponse.getString("success").equals("true"))
                                Toast.makeText(AdminViewUserActivity.this, "User Successfully Deleted", Toast.LENGTH_LONG).show();
                            else if(jsonResponse.getString("success").equals("false"))
                                Toast.makeText(AdminViewUserActivity.this, "User Deletion Failed", Toast.LENGTH_LONG).show();
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
            map.put(KEY_USERNAME, username);
            return map;
        }
    };
}
*/
/* JSON SHIT FOR (UN)LOCKING AND DELETE USERS
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
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, userUsername);
                map.put(KEY_PASSWORD, userPassword);
                return map;
            }
        };
*/