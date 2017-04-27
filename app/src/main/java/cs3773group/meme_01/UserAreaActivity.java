package cs3773group.meme_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

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
        String username = intent.getStringExtra("username");
        String type = intent.getStringExtra("type");

        txUsername.setText(username);
        txType.setText(type);
    }
}
