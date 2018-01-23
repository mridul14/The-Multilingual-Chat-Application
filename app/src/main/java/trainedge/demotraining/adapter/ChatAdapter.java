package trainedge.demotraining.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.ChatActivity;
import trainedge.demotraining.holder.ContactsHolder;
import trainedge.demotraining.model.ChatModel;

/**
 * Created by dell on 23-01-2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ContactsHolder> {

        List<ChatModel> chatName;
        FragmentActivity context;
        boolean keyLoaded=false;


public ChatAdapter(List<ChatModel> chatName, FragmentActivity context) {
        this.chatName=chatName;
        this.context=context;
        }

@Override
public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_item_3, parent, false);
        return new ContactsHolder(v);
        }

@Override
public void onBindViewHolder(ContactsHolder holder, int position) {

final ChatModel data=chatName.get(position);
        holder.tvUser.setText(data.name);
        Glide.with(context).load(data. photo).into(holder.ivPhoto);

        holder.container1.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        Intent intent=new Intent(context, ChatActivity.class);
        context.startActivity(intent);


        }
        });

        }

@Override
public int getItemCount() {
        return 0;
        }




        }
