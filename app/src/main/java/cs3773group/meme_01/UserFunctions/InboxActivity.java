package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
<<<<<<< Updated upstream
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
=======
import android.widget.Button;
>>>>>>> Stashed changes

import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/28/2017.
 */

public class InboxActivity extends AppCompatActivity implements View.OnClickListener {
<<<<<<< Updated upstream
    private Button bSelectUser;
    private Button bBack;
    private RadioGroup messageList;
    private RadioButton messageButton;

=======
    private Button bViewMessage;
    private Button bDeleteMessage;
    private Button bBack;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inbox);

        bSelectUser = (Button) findViewById(R.id.bSelectUser);
        bSelectUser.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

        final TextView logoutLink = (TextView) findViewById(R.id.linkLogout);
        logoutLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(InboxActivity.this, MainActivity.class);
                InboxActivity.this.startActivity(registerIntent);
            }
        });

        Intent intent = getIntent();

        // REPLACE 0 WITH listsz
        createMessageList(0);
    }

    @Override
    public void onClick(View v){

    }

    public void createMessageList(int listLength) {
        messageList = new RadioGroup(this);
        messageList.setOrientation(LinearLayout.VERTICAL);

        for (int i = 0; i < listLength; i++) {
            RadioButton rbMessage = new RadioButton(this);

            // REPLACE messageList.getID() with stuff
            rbMessage.setId(i);
            rbMessage.setText(messageList.getId());
            messageList.addView(rbMessage);
        }
        ((ViewGroup) findViewById(R.id.listUsers)).addView(messageList);
    }
}