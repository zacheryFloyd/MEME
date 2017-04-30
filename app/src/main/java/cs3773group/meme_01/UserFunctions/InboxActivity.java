package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cs3773group.meme_01.AdminFunctions.AdminAreaActivity;
import cs3773group.meme_01.AdminFunctions.AdminViewUserActivity;
import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.Models.messageModels;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/28/2017.
 */

public class InboxActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String DELETE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/deleteMessage.php";
    public static final String KEY_ID = "message_id";
    private RadioGroup messageList;
    private RadioButton messageButton;
    private Button bViewMessage;
    private Button bDeleteMessage;
    private Button bBack;
    private String username;
    private ArrayList<messageModels> messages;
    public int deleteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inbox);

        bViewMessage = (Button) findViewById(R.id.bViewMessage);
        bViewMessage.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bDeleteMessage = (Button) findViewById(R.id.bDeleteMessage);
        bDeleteMessage.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        messages = (ArrayList<messageModels>) intent.getSerializableExtra("messages");
        for (messageModels u : messages) {
            Log.d("MESSAGE", u.getText());
        }
        int listSz = messages.size();
        createMessageList(listSz);
    }

    @Override
    public void onClick(View v){
        if(v == bBack){
            Intent intent = new Intent(InboxActivity.this, UserAreaActivity.class);
            intent.putExtra("username",username);
            InboxActivity.this.startActivity(intent);
        }
        else if(v == bViewMessage){
            int selectedId = messageList.getCheckedRadioButtonId();
            Intent intent = new Intent(InboxActivity.this, ViewMessageActivity.class);
            intent.putExtra("message",messages.get(selectedId));
        }
        else if(v == bDeleteMessage){
            int selectedID = messageList.getCheckedRadioButtonId();
            deleteId = messages.get(selectedID).getMsgID();
            Log.d("DELETE: ",deleteId+"");
            userDeleteMessage();
        }
    }

    public void createMessageList(int listLength) {
        messageList = new RadioGroup(this);
        messageList.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < listLength; i++) {
            RadioButton rbMessage = new RadioButton(this);
            rbMessage.setId(i);
            rbMessage.setText(messages.get(i).getMsgID() + ":Message from: " + messages.get(i).getSenderID());
            messageList.addView(rbMessage);
        }
        ((ViewGroup) findViewById(R.id.listMessages)).addView(messageList);
    }
    
    public void userDeleteMessage(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DELETE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (response != null) {
                                if(jsonResponse.getString("success").equals("true"))
                                    Toast.makeText(InboxActivity.this, "Message Successfully Deleted", Toast.LENGTH_LONG).show();
                                else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(InboxActivity.this, "Message Deletion Failed", Toast.LENGTH_LONG).show();
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
                map.put(KEY_ID, deleteId+"");
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}