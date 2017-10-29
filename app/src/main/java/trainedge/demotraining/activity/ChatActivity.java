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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import trainedge.demotraining.HideShowScrollListener;
import trainedge.demotraining.R;
import trainedge.demotraining.adapter.ContactsAdapter;
import trainedge.demotraining.adapter.MessageListAdapter;
import trainedge.demotraining.model.MessgaeList;

public class ChatActivity extends AppCompatActivity {

    private FirebaseUser currentuser;
    private String senderId;
    String senderEmail;
    String receiverId;
    String receiverEmail;
    String receiver_lang;
    private EditText et_chatbox;
    private Button btn;
    private long time;
    private String content;
    private SharedPreferences lang_pref;
    private String sender_lang;
    private String conv_key;
    private RecyclerView rv_message_list;
    private List<MessgaeList> chatList;
    private MessageListAdapter mAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiverId = getIntent().getStringExtra("id");
            receiverEmail = getIntent().getStringExtra("email");
            receiver_lang = getIntent().getStringExtra("lang");
            conv_key = getIntent().getStringExtra("conv_key");
        }

        et_chatbox = (EditText) findViewById(R.id.et_chatbox);
        btn= (Button) findViewById(R.id.btn_chatbox_send);

        chatList = new ArrayList<>();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        lang_pref = getSharedPreferences("lang_pref", MODE_PRIVATE);

        final DatabaseReference myContactsDb = FirebaseDatabase.getInstance().getReference("messages").child(conv_key);

        senderId=currentuser.getUid();
        senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        sender_lang = lang_pref.getString("lang_key", "");

        mAdapter = new MessageListAdapter(this,chatList);

        rv_message_list = (RecyclerView) findViewById(R.id.rv_message_list);
        rv_message_list.setLayoutManager(new LinearLayoutManager(this));
        rv_message_list.setAdapter(mAdapter);

        time = Calendar.getInstance().getTime().getTime();


        myContactsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //rv_message_list.smoothScrollToPosition(rv_message_list.getAdapter().getItemCount());

                content = et_chatbox.getText().toString();
                if (content.isEmpty()){
                    Toast.makeText(ChatActivity.this, "Write some message", Toast.LENGTH_SHORT).show();
                    return;
                }
                MessgaeList msgTobeSent=new MessgaeList(receiverId,senderId,time,content,receiver_lang,sender_lang);
                myContactsDb.push().setValue(msgTobeSent);
                et_chatbox.setText("");
                /*HashMap<String, Object> msgHashMap = new HashMap<>();
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
*/
            }
        });
       /* rv_message_list.addOnScrollListener(
                new HideShowScrollListener() {
                    @Override
                    public void onHide() {
                        //btn.hide();
                    }

                    @Override
                    public void onShow() {
                       // fab.show();
                    }
                });*/
    }

   /* private void setupRecylerView() {
        rv_message_list.setHasFixedSize(true);
        rv_message_list.setLayoutManager(new LinearLayoutManager(this));
        rv_message_list.setAdapter(mAdapter);
    }
    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }*/
}
