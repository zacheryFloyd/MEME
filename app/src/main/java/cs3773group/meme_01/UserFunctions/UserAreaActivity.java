package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs3773group.meme_01.AdminFunctions.AdminAreaActivity;
import cs3773group.meme_01.AdminFunctions.UserListActivity;
import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.R;

public class UserAreaActivity extends AppCompatActivity implements View.OnClickListener{
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
        final TextView txType = (TextView) findViewById(R.id.txType);
        final TextView logoutLink = (TextView) findViewById(R.id.linkLogout);

        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(UserAreaActivity.this, MainActivity.class);
                UserAreaActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        txUsername.setText(username);
    }

    @Override
    public void onClick(View v){
        if(v == bCompose) {
            Intent intent = new Intent(UserAreaActivity.this, ComposeMessageActivity.class);
            intent.putExtra("sender",username);
            UserAreaActivity.this.startActivity(intent);
        }
        else if(v == bInbox){
            Intent intent = new Intent(UserAreaActivity.this, InboxActivity.class);
            UserAreaActivity.this.startActivity(intent);
        }
    }
}
