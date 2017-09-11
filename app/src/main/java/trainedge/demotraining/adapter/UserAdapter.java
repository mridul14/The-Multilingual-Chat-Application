package trainedge.demotraining.adapter;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.ContactsActivity;
import trainedge.demotraining.holder.UserHolder;
import trainedge.demotraining.model.User;

import static android.R.attr.data;
import static android.R.id.list;


public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

    Context context;
    private DatabaseReference ContactChoice;
    List<User> userList;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();


    public UserAdapter(List<User> userList, Context context) {
        this.context = context;
        this.userList = userList;
        ContactChoice = FirebaseDatabase.getInstance().getReference("Contact");
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


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
        final String a=user.email;
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user=mAuth.getCurrentUser();
                DatabaseReference contactDb=FirebaseDatabase.getInstance().getReference(user.getUid());
                contactDb.child("contacts");
                HashMap<String,Object> map=new HashMap<>();
                map.put("email",a);
            }
        });
        
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
            userList.add(pos, user);
            notifyItemInserted(pos);


    }

   
}
