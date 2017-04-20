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

        final EditText txUsername = (EditText) findViewById(R.id.txUsername);
        final EditText txType = (EditText) findViewById(R.id.txType);
        final TextView loginLink = (TextView) findViewById(R.id.linkLogin);

        loginLink.setOnClickListener(new View.OnClickListener() {
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
