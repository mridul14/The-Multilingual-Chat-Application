package trainedge.demotraining.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapzen.speakerbox.Speakerbox;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import trainedge.demotraining.R;
import trainedge.demotraining.adapter.MessageListAdapter;
import trainedge.demotraining.model.MessageList;

public class ChatActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private FirebaseUser currentuser;
    private String senderId;
    String senderEmail;
    String receiverId;
    String receiverEmail;
    String receiver_lang;
    private EditText et_chatbox;
    private Button btn;
    private SharedPreferences lang_pref;
    private String sender_lang;
    private String conv_key;
    private RecyclerView rv_message_list;
    private List<MessageList> chatList;
    private MessageListAdapter mAdapter;
    public OkHttpClient client = new OkHttpClient();
    private String recName;
    private DatabaseReference myContactsDb;
    private String url_format;
    private Speakerbox sbox;
    private String a="";
    //private Button btn_sbox;
    private TextToSpeech tts;
    private int lang;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private String b;
    private String d;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        sbox = new Speakerbox(getApplication());

        tts = new TextToSpeech(this,this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            receiverId = getIntent().getStringExtra("id");
            receiverEmail = getIntent().getStringExtra("email");
            receiver_lang = getIntent().getStringExtra("lang");
            conv_key = getIntent().getStringExtra("conv_key");
            recName = getIntent().getStringExtra("name");
            getSupportActionBar().setTitle(recName);
        }

        et_chatbox = (EditText) findViewById(R.id.et_chatbox);
        btn = (Button) findViewById(R.id.btn_chatbox_send);

        chatList = new ArrayList<>();
        currentuser = FirebaseAuth.getInstance().getCurrentUser();

        lang_pref = getSharedPreferences("lang_pref", MODE_PRIVATE);

        myContactsDb = FirebaseDatabase.getInstance().getReference("messages").child(conv_key);

        senderId = currentuser.getUid();
        senderEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        sender_lang = lang_pref.getString("lang_key", "");
        myContactsDb.child("person1").setValue(senderId);
        myContactsDb.child("person2").setValue(receiverId);

      /*  HashMap<String, String> map = new HashMap<>();
        map.put("person1", senderId);
        map.put("person2", receiverId);
        myContactsDb.setValue(map);*/

       /* btn_sbox = (Button) findViewById(R.id.btn_sbox);
        btn_sbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(ChatActivity.this, a, Toast.LENGTH_SHORT).show();
                et_chatbox.setText(a);
                //sbox.play(a);
                speakOut();
                a="";


            }
        });*/
        mAdapter = new MessageListAdapter(this, chatList);

        rv_message_list = (RecyclerView) findViewById(R.id.rv_message_list);
        rv_message_list.setLayoutManager(new LinearLayoutManager(this));
        rv_message_list.setAdapter(mAdapter);



        myContactsDb.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                chatList.clear();

                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if (snapshot.getKey().equals("person1") || snapshot.getKey().equals("person2")) {
                            continue;
                        } else {
                            chatList.add(snapshot.getValue(MessageList.class));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    int size = chatList.size();
                    //rv_message_list.smoothScrollToPosition(size - 1);
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

                final long Time = Calendar.getInstance().getTime().getTime();

                String content = et_chatbox.getText().toString();

                if (content.isEmpty()) {
                    Toast.makeText(ChatActivity.this, "Write some message", Toast.LENGTH_SHORT).show();
                    return;
                }
                MessageList msgTobeSent = new MessageList(receiverId, senderId, Time, content, receiver_lang, sender_lang);
                new TranslationTask().execute(msgTobeSent);
               // myContactsDb.push().setValue(msgTobeSent);
                et_chatbox.setText("");

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_chatbox.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_text) {
            speakOut();
        }
        if (id == R.id.action_speech){
            promptSpeechInput();
        }

        return super.onOptionsItemSelected(item);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Translate a given text between a source and a destination language
     */
    public void translate(MessageList message) {


        String translated = "";
        String original=message.content;

        String senderLang=message.senderlang;
        String receiverLang=message.receiverlang;

        url_format ="http://mymemory.translated.net/api/get?q=%s!&langpair=%s|%s&key=%s";
        String url=String.format(url_format,
                                original,
                                senderLang,
                                receiverLang,
                                getResources().getString(R.string.translation_key));

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONObject jObject = null;
            jObject = new JSONObject(response.body().string());
            JSONObject data = jObject.getJSONObject("responseData");
            translated=data.getString("translatedText");
            a = a +translated;
            change(receiverLang);
            message.setTranslated(translated);
            myContactsDb.push().setValue(message);
            //result.setText(data.getString("translatedText"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int change(String receiverLang) {
        lang = 0;
        if (receiverLang=="en"){
            lang = tts.setLanguage(Locale.ENGLISH);
        } else if (receiverLang=="hi"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="ar"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="hy"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="sq"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="az"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="af"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="eu"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="be"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="bg"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="cy"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="vi"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="hu"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="ht"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="gl"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="nl"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="el"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="ka"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="da"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="he"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="id"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="ga"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="it"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="is"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="es"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="zh"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="ko"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="la"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="de"){
            lang=tts.setLanguage(new Locale(receiverLang));
        } else if (receiverLang=="pl"){
            lang=tts.setLanguage(new Locale(receiverLang));
        }

        return lang;
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = lang;

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                //Log.e("TTS", "This Language is not supported");
                Toast.makeText(this, "language not supported", Toast.LENGTH_SHORT).show();
            } else {
                //btn_sbox.setEnabled(true);
                speakOut();
            }

        } else {
            //Log.e("TTS", "Initilization Failed!");
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

    }

    private void speakOut() {

        //String text = txtText.getText().toString();

        tts.speak(a, TextToSpeech.QUEUE_FLUSH, null);
    }


    public class TranslationTask extends AsyncTask<MessageList,Void ,String>{


        @Override
        protected String doInBackground(MessageList... objects) {
            MessageList message = (MessageList) objects[0];
            translate(message);
            return null;
        }
    }
}

