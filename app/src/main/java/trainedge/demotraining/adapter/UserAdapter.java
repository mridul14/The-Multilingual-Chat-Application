package trainedge.demotraining.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.AddContactsActivity;
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.holder.UserHolder;
import trainedge.demotraining.model.User;

public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    NextActivity context;
    private DatabaseReference ContactChoice;
    List<User> list;
    private final FirebaseUser currentUser;
    private final DatabaseReference invitedUser;



    public UserAdapter(List<User> list, NextActivity context) {
        this.context = context;
        this.list = list;
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        ContactChoice = FirebaseDatabase.getInstance().getReference(currentUser.getUid());
        invitedUser = ContactChoice.child("Contacts");

    }

    @Override
    public UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_item_2, parent, false);
        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(final UserHolder holder, final int position) {
        final User data = list.get(position);
        holder.tvUser.setText(data.name);
        holder.tvUserMail.setText(data.email);
        Glide.with(context).load(data.photo).into(holder.ivPhoto);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgressDialog("Adding....");
                addToFirebase(data, holder.btnAdd);
                //view.setEnabled(false);

            }
        });

    }

    private void addToFirebase(User data, Button btnAdd) {
        invitedUser.child(data.id).setValue(data.name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    context.hideProgressDialog();
                }
            }
        });
        btnAdd.setText("added");


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, User data) {
        // Insert a new item to the RecyclerView on a predefined position
        list.add(data);
        notifyItemInserted(position);
    }
    public void remove(User data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);

    }




}
