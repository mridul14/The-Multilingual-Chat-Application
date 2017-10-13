package trainedge.demotraining.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import trainedge.demotraining.R;
import trainedge.demotraining.adapter.ContactsAdapter;

public class ChatActivity extends AppCompatActivity{

    String id_key="trainedge.demotraining";
    private FirebaseUser currentuser;
    private DatabaseReference sender;
    String senderId;
    String senderEmail;
    String receiverId;
    String receiverEmail;
    String lang;
    private String concatEmail;
    private EditText et_chatbox;
    private Button btn_chatbox_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_chatbox = (EditText) findViewById(R.id.et_chatbox);
        btn_chatbox_send = (Button) findViewById(R.id.btn_chatbox_send);


        currentuser = FirebaseAuth.getInstance().getCurrentUser();
        sender = FirebaseDatabase.getInstance().getReference(currentuser.getUid());
        senderEmail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Bundle extras=getIntent().getExtras();
        if(extras!=null){
            receiverId=getIntent().getStringExtra("id");
            receiverEmail=getIntent().getStringExtra("email");
        }
        concatEmails(senderEmail,receiverEmail);
        final DatabaseReference chatDb=FirebaseDatabase.getInstance().getReference("messages");
        btn_chatbox_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myContactsDb=FirebaseDatabase.getInstance().getReference(currentuser.getUid()).child(concatEmail);

            }
        });
    }

    private String concatEmails(String senderEmail, String receiverEmail) {
        String s=senderEmail.replace('@','.');
        String r=receiverEmail.replace('@','.');
        concatEmail = s+"_"+r;
        return concatEmail;
    }

    DatabaseReference myContactsDb=FirebaseDatabase.getInstance().getReference(currentuser.getUid()).child(concatEmail);



}
