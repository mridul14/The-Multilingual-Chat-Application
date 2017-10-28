package trainedge.demotraining.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.ChatActivity;
import trainedge.demotraining.holder.MessageListHolder;
import trainedge.demotraining.model.MessgaeList;


public class MessageListAdapter extends RecyclerView.Adapter<MessageListHolder> {

    private final List<MessgaeList> chatList;
    private final ChatActivity chatActivity;

    public MessageListAdapter(ChatActivity chatActivity, List<MessgaeList> chatList) {

        this.chatList = chatList;
        this.chatActivity = chatActivity;
    }

    @Override
    public MessageListHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.receiver_chat_item,parent,false);
        return new MessageListHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageListHolder holder, int position) {
        MessgaeList messgaeList=chatList.get(position);
        holder.text_message_body.setText(messgaeList.content);
        holder.text_message_time.setText(String.valueOf(messgaeList.time));
        holder.image_message_profile.setVisibility(View.GONE);
        holder.text_message_name.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return chatList.size();

    }
}
