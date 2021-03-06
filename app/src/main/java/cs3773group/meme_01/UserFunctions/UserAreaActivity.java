package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs3773group.meme_01.AdminFunctions.AdminAreaActivity;
import cs3773group.meme_01.AdminFunctions.UserListActivity;
import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.Models.messageModels;
import cs3773group.meme_01.R;

public class UserAreaActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String OFFLINE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/userOffline.php";
    public static final String KEY_OFFLINENAME = "user_name";
    private static final String GET_MESSAGES_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/getInbox.php";
    public static final String KEY_USERNAME = "receiver_name";
    public static final String DELETE_ALL_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/deleteInbox.php";

    private Button bCompose;
    private Button bInbox;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);
        bCompose = (Button) findViewById(R.id.bCompose);
        bCompose.setOnClickListener(this);
        bInbox = (Button) findViewById(R.id.bInbox);
        bInbox.setOnClickListener(this);

        final TextView txUsername = (TextView) findViewById(R.id.txUsername);
        final TextView logoutLink = (TextView) findViewById(R.id.linkLogout);

        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDeleteAllMessages();
                userOffline();
                Intent registerIntent = new Intent(UserAreaActivity.this, MainActivity.class);
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        txUsername.setText(username);
    }

    private void userOffline(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, OFFLINE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if(response != null) {
                                if (response != null) {
                                    if (jsonResponse.getString("success").equals("true"))
                                        Toast.makeText(UserAreaActivity.this, "User Successfully Offline", Toast.LENGTH_LONG).show();
                                    else if (jsonResponse.getString("success").equals("false"))
                                        Toast.makeText(UserAreaActivity.this, "User Offlining Failed", Toast.LENGTH_LONG).show();
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
                map.put(KEY_OFFLINENAME,username);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void getMessages() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_MESSAGES_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            /* ON Response we want to parse the JSON into users, and create
                               an ArrayList of userModels. We then put the list as an extra for
                               the UserListActivity
                             */
                            JSONArray jsonMessageArray = new JSONArray(response);
                            if (response != null) {
                                ArrayList<messageModels> messages = new ArrayList<messageModels>(); // will hold list of messages
                                for (int i = 0; i<jsonMessageArray.length(); i++) {
                                    JSONObject jsonMessage = new JSONObject();
                                    jsonMessage = jsonMessageArray.getJSONObject(i);  //one user object from the JSON
                                    Log.d("MESSAGE:",jsonMessage.toString());
                                    messageModels message = new messageModels();
                                    //create a user object from the JSON, and add to list
                                    message.setText(jsonMessage.getJSONObject("message").getString("text"));
                                    message.setSenderID(jsonMessage.getJSONObject("message").getString("sender"));
                                    message.setReceiverID(jsonMessage.getJSONObject("message").getString("receiver"));
                                    message.setLifeSpan(jsonMessage.getJSONObject("message").getInt("life"));
                                    message.setMsgID(jsonMessage.getJSONObject("message").getInt("id"));
                                    if(!jsonMessage.getJSONObject("message").getString("key").isEmpty()){
                                        message.setEncryptionKey(jsonMessage.getJSONObject("message").getString("key"));
                                    }
                                    Log.d("MESSAGE", message.getText());
                                    messages.add(message);
                                }
                                Intent intent = new Intent(UserAreaActivity.this, InboxActivity.class);
                                intent.putExtra("messages", messages);
                                intent.putExtra("username",username);
                                UserAreaActivity.this.startActivity(intent);
                            }
                            else{
                                Toast.makeText(UserAreaActivity.this, "No Messages To Display", Toast.LENGTH_LONG).show();
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
                map.put(KEY_USERNAME, username);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void userDeleteAllMessages(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_ALL_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (response != null) {
                                if(jsonResponse.getString("success").equals("true"))
                                    Toast.makeText(UserAreaActivity.this, "Message Successfully Deleted", Toast.LENGTH_LONG).show();
                                else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(UserAreaActivity.this, "Message Deletion Failed", Toast.LENGTH_LONG).show();
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

    @Override
    public void onClick(View v){
        if(v == bCompose) {
            Intent intent = new Intent(UserAreaActivity.this, ComposeMessageActivity.class);
            intent.putExtra("sender",username);
            UserAreaActivity.this.startActivity(intent);
        }
        else if(v == bInbox){
            getMessages();
        }
    }
}
