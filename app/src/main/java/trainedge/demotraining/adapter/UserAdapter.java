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
    List<User> userList;
    private final FirebaseUser currentUser;
    private final DatabaseReference invitedUser;


    public UserAdapter(List<User> userList, NextActivity context) {
        this.context = context;
        this.userList = userList;
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
        final User user = userList.get(position);
        holder.tvUser.setText(user.name);
        holder.tvUserMail.setText(user.email);
        Glide.with(context).load(user.photo).into(holder.ivPhoto);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showProgressDialog("Adding....");
                addToFirebase(user, holder.btnAdd);
                //view.setEnabled(false);

            }
        });

    }

    private void addToFirebase(User user, Button btnAdd) {
        invitedUser.child(user.id).setValue(user.name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    context.hideProgressDialog();
                }
            }
        });
        btnAdd.setText("added");


    }


    public void remove(User data) {
        int position = userList.indexOf(data);
        userList.remove(position);
        notifyItemRemoved(position);

    }


    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int pos, User user) {
        // Insert a new item to the RecyclerView on a predefined position
        userList.add(user);
        notifyDataSetChanged();
    }

}
