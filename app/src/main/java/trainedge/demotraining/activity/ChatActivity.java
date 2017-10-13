package trainedge.demotraining.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import trainedge.demotraining.R;
import trainedge.demotraining.adapter.ContactsAdapter;

public class ChatActivity extends AppCompatActivity {

    String id_key = "trainedge.demotraining";
    private FirebaseUser currentuser;
    private String senderId;
    String senderEmail;
    String receiverId;
    String receiverEmail;
    String receiver_lang;
    private String concatEmail;
    private EditText et_chatbox;
    private Button btn_chatbox_send;
    private long time;
    private String content;
    private String lang1;
    private SharedPreferences lang_pref;
    private String sender_lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_chatbox = (EditText) findViewById(R.id.et_chatbox);
        btn_chatbox_send = (Button) findViewById(R.id.btn_chatbox_send);
        time = System.currentTimeMillis();

        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        senderId=currentuser.getUid();
      //  senderId = FirebaseDatabase.getInstance().getReference(currentuser.getUid());
        senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiverId = getIntent().getStringExtra("id");
            receiverEmail = getIntent().getStringExtra("email");
            receiver_lang = getIntent().getStringExtra("lang");
        }
        lang_pref = getSharedPreferences("lang_pref", MODE_PRIVATE);
        sender_lang = lang_pref.getString("lang_key", "");
        final DatabaseReference myContactsDb = FirebaseDatabase.getInstance().getReference("messages");
        btn_chatbox_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = et_chatbox.getText().toString().trim();
                HashMap<String, Object> msgHashMap = new HashMap<>();
                msgHashMap.put("senderId", senderId);
                msgHashMap.put("receiverId", receiverId);
                msgHashMap.put("time", time);
                msgHashMap.put("content", content);
                msgHashMap.put("sengerlang", sender_lang);
                msgHashMap.put("receiverlang", receiver_lang);
                DatabaseReference chatRef = myContactsDb.child(concatEmails(senderEmail, receiverEmail));
                chatRef.push().setValue(msgHashMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(ChatActivity.this, "msg sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChatActivity.this, "error sending msg", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private String concatEmails(String senderEmail, String receiverEmail) {
        String temp = senderEmail + receiverEmail;
        temp = temp.replace(".", "_");
        temp = temp.replace("@", "__");
        return temp;

    }


}
