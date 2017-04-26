package cs3773group.meme_01.AdminFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/26/2017.
 *
 * THIS CONTAINS THE FUNCTIONS THAT WILL DISPLAY A LIST OF ALL USERS
 */

public class UserListActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bSelectUser;
    private Button bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        bSelectUser = (Button) findViewById(R.id.bSelectUser);
        bSelectUser.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == bBack){
            Intent intent = new Intent(UserListActivity.this, AdminAreaActivity.class);
            UserListActivity.this.startActivity(intent);
        }
        else if(v == bSelectUser){
            Intent intent = new Intent(UserListActivity.this, AdminViewUserActivity.class);
            UserListActivity.this.startActivity(intent);
        }
    }
}