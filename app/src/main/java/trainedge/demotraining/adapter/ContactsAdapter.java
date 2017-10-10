package trainedge.demotraining.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.ChatActivity;
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.holder.ContactsHolder;
import trainedge.demotraining.model.ContactsModel;
import trainedge.demotraining.model.User;

/**
 * Created by dell on 09-10-2017.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {

    List<User> list;
    NextActivity context;
    public ContactsAdapter(List<User> list, NextActivity context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public ContactsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_item_3, parent, false);

        return new ContactsHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactsHolder holder, int position) {
        final User data = list.get(position);
        holder.tvUser.setText(data.name);
        holder.tvUserMail.setText(data.email);
        Glide.with(context).load(data.photo).into(holder.ivPhoto);
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
        return list.size();
    }
}
