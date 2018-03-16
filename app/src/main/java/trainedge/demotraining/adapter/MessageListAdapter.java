package trainedge.demotraining.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.text.format.DateFormat;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import trainedge.demotraining.R;
import trainedge.demotraining.activity.ChatActivity;
import trainedge.demotraining.holder.ReceiverHolder;
import trainedge.demotraining.holder.SenderHolder;
import trainedge.demotraining.model.MessageList;


public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<MessageList> chatList;
    private final ChatActivity chatActivity;
    public int SENDER = 0;
    public int RECEIVER = 1;
    private final FirebaseUser user;
    public OkHttpClient client = new OkHttpClient();

    public MessageListAdapter(ChatActivity chatActivity, List<MessageList> chatList) {

        this.chatList = chatList;
        this.chatActivity = chatActivity;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vReceive = LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_chat_item, parent, false);
        View vSend = LayoutInflater.from(parent.getContext()).inflate(R.layout.sender_chat_item, parent, false);
        if (viewType == SENDER) {
            return new SenderHolder(vSend);
        }
        return new ReceiverHolder(vReceive);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderHolder) {
            SenderHolder sh = (SenderHolder) holder;
            MessageList messageList = chatList.get(position);
            sh.text_message_body.setText(messageList.content);
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(messageList.Time);
            String date = DateFormat.format("hh:mm", cal).toString();
            sh.text_message_time.setText(date);

        } else {
            ReceiverHolder rh = (ReceiverHolder) holder;
            MessageList messageList = chatList.get(position);
            rh.text_message_body.setText(messageList.translated);
            //rh.text_message_name.setText("");
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(messageList.Time);
            String date = DateFormat.format("hh:mm", cal).toString();
            rh.text_message_time.setText(date);
            Object[] translationParams = new Object[]{rh.text_message_body, messageList.content, messageList.senderlang, messageList.receiverlang};

            //Glide.with(chatActivity).load(R.drawable.ic_person_outline_black_24dp).into(rh.image_message_profile);

        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();

    }

    @Override
    public int getItemViewType(int position) {
        MessageList msgList = chatList.get(position);
        if (msgList.senderId.equals(user.getUid())) {
            return SENDER;
        }
        return RECEIVER;
    }
}

    /**
     * Translate a given text between a source and a destination language
     */
    /*public String translate(TextView text_message_body, String text, String firstLang, String secondLang) {
        String translated = "";
        String url = String.format("http://mymemory.translated.net/api/get?q=%s!&langpair=%s|%s&key=%s", text, firstLang, secondLang, chatActivity.getResources().getString(R.string.translation_key));
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
            text_message_body.setText(data.getString("translatedText"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return translated;
    }
}*/
