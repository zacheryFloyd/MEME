package cs3773group.meme_01.UserFunctions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
    private static final String GET_MESSAGES_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/getInbox.php";
    public static final String KEY_USERNAME = "receiver_name";
    private RadioGroup messageList;
    private RadioButton messageButton;
    private Button bViewMessage;
    private Button bDeleteMessage;
    private Button bBack;
    private Button bRefresh;
    private LinearLayout layoutInbox;
    private String username;
    private ArrayList<messageModels> messages;
    public int deleteId;
    private String mUserInputKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inbox);

        bViewMessage = (Button) findViewById(R.id.bViewMessage);
        bViewMessage.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        bRefresh = (Button) findViewById(R.id.bRefresh);
        bRefresh.setOnClickListener(this);
        bDeleteMessage = (Button) findViewById(R.id.bDeleteMessage);
        bDeleteMessage.setOnClickListener(this);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        messages = (ArrayList<messageModels>) intent.getSerializableExtra("messages");
        for (messageModels u : messages) {
            Log.d("MESSAGE", u.getText());
        }
        int listSz = messages.size();
        if(listSz > 0)
            createMessageList(listSz);
        else{
            layoutInbox = (LinearLayout) findViewById(R.id.layoutInbox);
            TextView emptyInbox = new TextView(this);
            emptyInbox.setText("No New Messages");
            layoutInbox.addView(emptyInbox);
        }

    }

    @Override
    public void onClick(View v){
        if(v == bBack){
            Intent intent = new Intent(InboxActivity.this, UserAreaActivity.class);
            intent.putExtra("username",username);
            InboxActivity.this.startActivity(intent);
        }
        else if(v == bViewMessage){
            if(messageList == null) {
                return;
            }
            final int selectedID = messageList.getCheckedRadioButtonId();
            if(selectedID == -1) {
                return;
            }
            Intent intent = new Intent(InboxActivity.this, ViewMessageActivity.class);
            intent.putExtra("message",messages.get(selectedID));
            if(messages.get(selectedID).getEncryptionKey().isEmpty())
                InboxActivity.this.startActivity(intent);
            else {
                //Popup asking for encryption key
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Please Enter Encryption Key");

                // Set up the input
                final EditText input = new EditText(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mUserInputKey = input.getText().toString();
                        if(mUserInputKey.equals((messages.get(selectedID).getEncryptionKey()))) {
                            Intent intent = new Intent(InboxActivity.this, ViewMessageActivity.class);
                            intent.putExtra("message", messages.get(selectedID));
                            InboxActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(InboxActivity.this, "Incorrect Encryption Key", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        }
        else if(v == bDeleteMessage){
            if(messageList == null) {
                return;
            }
            int selectedID = messageList.getCheckedRadioButtonId();
            if (selectedID == -1) {
                return;
            }
            deleteId = messages.get(selectedID).getMsgID();
            Log.d("DELETE: ",deleteId+"");
            userDeleteMessage();
            Intent intent = new Intent(InboxActivity.this, UserAreaActivity.class);
            intent.putExtra("username",username);
            InboxActivity.this.startActivity(intent);
        }
        else if(v == bRefresh){
            getMessages();
        }
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
                                Intent intent = new Intent(InboxActivity.this, InboxActivity.class);
                                intent.putExtra("messages", messages);
                                intent.putExtra("username",username);
                                InboxActivity.this.startActivity(intent);
                            }
                            else{
                                Toast.makeText(InboxActivity.this, "No Messages To Display", Toast.LENGTH_LONG).show();
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

    public void createMessageList(int listLength) {
        messageList = new RadioGroup(this);
        messageList.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < listLength; i++) {
            RadioButton rbMessage = new RadioButton(this);
            rbMessage.setId(i);
            rbMessage.setText("Message from: " + messages.get(i).getSenderID() + "  - Timer: " + messages.get(i).getLifeSpan());
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