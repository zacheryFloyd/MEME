package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import cs3773group.meme_01.Models.messageModels;
import cs3773group.meme_01.Models.userModels;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/28/2017.
 */

public class ComposeMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String SEND_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/sendMessage.php";
    private static final String ONLINE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/getUser.php";
    public static final String KEY_USERNAME = "user_name";
    public static final String KEY_SENDERNAME = "sender_name";
    public static final String KEY_RECEIVERNAME = "receiver_name";
    public static final String KEY_ENC = "message_key";
    public static final String KEY_TEXT= "message_text";
    public static final String KEY_LIFESPAN = "message_life";

    private EditText recepients;
    private EditText timeLimit;
    private EditText messageText;
    private EditText encryption;
    private CheckBox encryptionCheck;
    private Button bSend;
    private Button bBack;
    private messageModels message;
    private Intent intent;
    public boolean ret = false;
    public String strRet = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        intent = getIntent();
        message = new messageModels();
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bSend = (Button) findViewById(R.id.bSend);
        bSend.setOnClickListener(this);
        recepients = (EditText) findViewById((R.id.txRecipient));
        timeLimit = (EditText) findViewById(R.id.txTime);
        messageText = (EditText) findViewById(R.id.editText4);
        encryption = (EditText) findViewById(R.id.txEncryption);
        encryptionCheck = (CheckBox) findViewById(R.id.checkBox);
    }

    public void createMessage(){
        if(encryptionCheck.isChecked()) {
            message.setEncryptionKey(encryption.getText().toString());
        }
        message.setText(messageText.getText().toString());
        message.setLifeSpan(Integer.parseInt(timeLimit.getText().toString()));
        message.setReceiverID(recepients.getText().toString());
        message.setSenderID(intent.getStringExtra("sender"));
    }

    public void checkIfRecepientIsOnline(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ONLINE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("JSON",jsonResponse.toString());
                            if (response != null) {
                                if(jsonResponse.getJSONObject("user").getString("online").equals("1"))
                                {
                                    Log.d("ONLINE FOR FUCKING SURE",jsonResponse.toString());
                                    sendMessage();
                                }
                                else if(jsonResponse.getJSONObject("user").getString("online").equals("0")) {
                                    Toast.makeText(ComposeMessageActivity.this, "Recepient Not Online", Toast.LENGTH_LONG).show();
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
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put(KEY_USERNAME, message.getReceiverID());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void sendMessage(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SEND_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            if (response != null) {
                                if(jsonResponse.getString("success").equals("true"))
                                    Toast.makeText(ComposeMessageActivity.this, "Message Sent", Toast.LENGTH_LONG).show();
                                else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(ComposeMessageActivity.this, "Message Not Sent", Toast.LENGTH_LONG).show();
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
                map.put(KEY_SENDERNAME, message.getSenderID());
                map.put(KEY_RECEIVERNAME, message.getReceiverID());
                map.put(KEY_ENC, message.getEncryptionKey());
                map.put(KEY_TEXT, message.getText());
                map.put(KEY_LIFESPAN, message.getLifeSpan()+"");
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v){
        if(v == bBack){
            Intent intent = new Intent(ComposeMessageActivity.this, UserAreaActivity.class);
            intent.putExtra("username",message.getSenderID());
            ComposeMessageActivity.this.startActivity(intent);
        }
        if(v == bSend){
            createMessage();
            checkIfRecepientIsOnline();
            Intent intent = new Intent(ComposeMessageActivity.this, UserAreaActivity.class);
            intent.putExtra("username",message.getSenderID());
            ComposeMessageActivity.this.startActivity(intent);

        }
    }
}
