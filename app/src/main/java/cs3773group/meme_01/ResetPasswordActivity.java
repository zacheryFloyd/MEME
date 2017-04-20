package cs3773group.meme_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        final TextView loginLink = (TextView) findViewById(R.id.linkLogin);

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                ResetPasswordActivity.this.startActivity(registerIntent);
            }
        });
    }
}
