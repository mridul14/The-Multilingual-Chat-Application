package trainedge.demotraining.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.ChatActivity;
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.holder.ContactsHolder;
import trainedge.demotraining.model.Chat;
import trainedge.demotraining.model.ChatModel;
import trainedge.demotraining.model.ContactsModel;
import trainedge.demotraining.model.User;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsHolder> {

    List<User> list;
    NextActivity context;
    boolean keyLoaded=false;


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

        final User data=list.get(position);
        holder.tvUser.setText(data.name);
        holder.tvUserMail.setText(data.email);
        Glide.with(context).load(data. photo).into(holder.ivPhoto);

        holder.container1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, ChatActivity.class);
                Bundle extras=new Bundle();
                extras.putString("id",data.id);
                extras.putString("email",data.email);
                extras.putString("lang",data.language);
                extras.putString("name",data.name);
                intent.putExtras(extras);
                ConverstationNodeKey(FirebaseAuth.getInstance().getCurrentUser().getEmail(),data.email,intent);


            }
        });

    }

    private void ConverstationNodeKey(String senderEmail, String recieverEmail, final Intent intent) {
        final String testNode1=concatEmails(senderEmail,recieverEmail);
        final String testNode2=concatEmails(recieverEmail,senderEmail);

        FirebaseDatabase.getInstance().getReference("messages").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = null;
                if (dataSnapshot.hasChildren()){
                    if (dataSnapshot.hasChild(testNode1)){
                        key=testNode1;
                    }
                    else if(dataSnapshot.hasChild(testNode2)){
                        key=testNode2;
                    }
                    else{
                        key=testNode1;
                    }
                }
                else{
                    key=testNode1;
                }
                intent.putExtra("conv_key",key);
                context.startActivity(intent);
                keyLoaded =true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError!=null){
                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private String concatEmails(String senderEmail, String receiverEmail) {
        String temp = senderEmail + receiverEmail;
        temp = temp.replace(".", "_");
        temp = temp.replace("@", "_");
        return temp;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public  void insert(int position , User data){
        list.add(data);
        notifyItemInserted(position);
    }
    public void remove(ContactsModel data){
        int position=list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
