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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cs3773group.meme_01.Models.messageModels;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/28/2017.
 */

public class ViewMessageActivity  extends AppCompatActivity implements View.OnClickListener {

    private messageModels message;
    private TextView messageText;
    private TextView messageTime;
    private TextView messageSender;
    private Button bDeleteMessage;
    private Button bBack;
    private String username;
    public int deleteId;
    private static final String DELETE_URL = "http://galadriel.cs.utsa.edu/~group1/android_login_api/deleteMessage.php";
    public static final String KEY_ID = "message_id";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_view_message);
        message = (messageModels) intent.getSerializableExtra("message");
        bDeleteMessage = (Button) findViewById(R.id.bDeleteMessage);
        bDeleteMessage.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);
        messageText = (TextView) findViewById(R.id.txMessage);
        messageText.setText(message.getText());
        messageTime = (TextView) findViewById(R.id.txTime);
        messageTime.setText(message.getLifeSpan()+"");
        messageSender = (TextView) findViewById(R.id.txSender);
        messageSender.setText(message.getSenderID());
        username = message.getReceiverID();
    }

    @Override
    public void onClick(View v){
        if(v == bBack){
            Intent intent = new Intent(ViewMessageActivity.this, UserAreaActivity.class);
            intent.putExtra("username",username);
            ViewMessageActivity.this.startActivity(intent);
        }
        else if(v == bDeleteMessage){
            deleteId = message.getMsgID();
            Log.d("DELETE: ",deleteId+"");
            userDeleteMessage();
            Intent intent = new Intent(ViewMessageActivity.this, UserAreaActivity.class);
            intent.putExtra("username",username);
            ViewMessageActivity.this.startActivity(intent);
        }
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
                                    Toast.makeText(ViewMessageActivity.this, "Message Successfully Deleted", Toast.LENGTH_LONG).show();
                                else if(jsonResponse.getString("success").equals("false"))
                                    Toast.makeText(ViewMessageActivity.this, "Message Deletion Failed", Toast.LENGTH_LONG).show();
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
                map.put(KEY_ID, message.getMsgID()+"");
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
