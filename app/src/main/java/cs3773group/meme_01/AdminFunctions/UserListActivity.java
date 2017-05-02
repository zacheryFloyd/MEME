package cs3773group.meme_01.AdminFunctions;

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

import java.util.ArrayList;

import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.Models.userModels;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/26/2017.
 *
 * THIS CONTAINS THE FUNCTIONS THAT WILL DISPLAY A LIST OF ALL USERS
 */

public class UserListActivity extends AppCompatActivity implements View.OnClickListener{
    private String adminUsername;
    private Button bSelectUser;
    private Button bBack;
    private RadioGroup userList;
    private RadioButton userButton;
    private int listSz;
    private ArrayList <userModels> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        bSelectUser = (Button) findViewById(R.id.bSelectUser);
        bSelectUser.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

        final TextView logoutLink = (TextView) findViewById(R.id.linkLogout);
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserListActivity.this, MainActivity.class);
                UserListActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();
        adminUsername = intent.getStringExtra("admin");
        users = (ArrayList<userModels>) intent.getSerializableExtra("users");
        for (userModels u : users) {
            Log.d("USER", u.getPassword());
        }
        listSz = users.size();
        createUserList(listSz);
    }

    @Override
    public void onClick(View v) {
        if(v == bBack){
            Intent intent = new Intent(UserListActivity.this, AdminAreaActivity.class);
            UserListActivity.this.startActivity(intent);
        }
        else if(v == bSelectUser){
            if(userList == null) {
                return;
            }
            int selectedId = userList.getCheckedRadioButtonId();
            if(selectedId == -1) {
                return;
            }
            userButton = (RadioButton) findViewById(selectedId);

            Intent intent = new Intent(UserListActivity.this, AdminViewUserActivity.class);
            intent.putExtra("admin", adminUsername);
            intent.putExtra("username", userButton.getText());
            if(users.get(selectedId).isOnline()){
                intent.putExtra("online", "ONLINE");
            }
            else{
                intent.putExtra("online","OFFLINE");
            }
            if(users.get(selectedId).isLocked()){
                intent.putExtra("lock", "LOCKED");
            }
            else{
                intent.putExtra("lock","UNLOCKED");
            }
            UserListActivity.this.startActivity(intent);
        }
    }

    public void createUserList(int listLength) {
        userList = new RadioGroup(this);
        userList.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < listLength; i++) {
            RadioButton rbUser = new RadioButton(this);

            // REPLACE i WITH VARIABLE CONTAINING USERNAME
            rbUser.setId(i);
            rbUser.setText(users.get(i).getUsername());
            userList.addView(rbUser);
        }
        ((ViewGroup) findViewById(R.id.listUsers)).addView(userList);
    }
}