package trainedge.demotraining.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.adapter.ContactsAdapter;
import trainedge.demotraining.adapter.MessageListAdapter;
import trainedge.demotraining.model.MessgaeList;

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
    private String conv_key;
    private RecyclerView rv_message_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_chatbox = (EditText) findViewById(R.id.et_chatbox);
        btn_chatbox_send = (Button) findViewById(R.id.btn_chatbox_send);
        time = System.currentTimeMillis();
        final List<MessgaeList> chatList=new ArrayList<>();
        rv_message_list = (RecyclerView) findViewById(R.id.rv_message_list);
        rv_message_list.setLayoutManager(new LinearLayoutManager(this));
        final MessageListAdapter mAdapter=new MessageListAdapter(this,chatList);
        rv_message_list.setAdapter(mAdapter);
        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        senderId=currentuser.getUid();
        senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiverId = getIntent().getStringExtra("id");
            receiverEmail = getIntent().getStringExtra("email");
            receiver_lang = getIntent().getStringExtra("lang");
            conv_key = getIntent().getStringExtra("conv_key");
        }
        lang_pref = getSharedPreferences("lang_pref", MODE_PRIVATE);
        sender_lang = lang_pref.getString("lang_key", "");
        final DatabaseReference myContactsDb = FirebaseDatabase.getInstance().getReference("messages");
        myContactsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount()>0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        chatList.add(snapshot.getValue(MessgaeList.class));
                    }
                        mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

                DatabaseReference chatRef = myContactsDb.child(conv_key);
                chatRef.push().setValue(msgHashMap, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError == null) {
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
