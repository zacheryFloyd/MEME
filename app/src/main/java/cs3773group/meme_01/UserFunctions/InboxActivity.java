package cs3773group.meme_01.UserFunctions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Button;

import cs3773group.meme_01.MainActivity;
import cs3773group.meme_01.R;

/**
 * Created by Tom on 4/28/2017.
 */

public class InboxActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup messageList;
    private RadioButton messageButton;
    private Button bViewMessage;
    private Button bDeleteMessage;
    private Button bBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_inbox);

        bViewMessage = (Button) findViewById(R.id.bViewMessage);
        bViewMessage.setOnClickListener(this);
        bBack = (Button) findViewById(R.id.bBack);
        bBack.setOnClickListener(this);

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
          //  rbMessage.setText(messageList.getId());
            messageList.addView(rbMessage);
        }
        ((ViewGroup) findViewById(R.id.listUsers)).addView(messageList);
    }
}