package trainedge.demotraining.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.adapter.UserAdapter;
import trainedge.demotraining.model.User;

public class ContactsActivity extends BasicActivity{


    private RecyclerView rvUser;
    private EditText etSearchTerm;
    private String searchTerm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvUser = (RecyclerView) findViewById(R.id.rvUser);
        etSearchTerm = (EditText) findViewById(R.id.etSearchTerm);



        ImageView ivSearch = (ImageView) findViewById(R.id.ivSearch);
        final List<User> userData = new ArrayList<>();
        final UserAdapter uAdapter = new UserAdapter(userData,getApplication());
        rvUser.setAdapter(uAdapter);
        rvUser.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.ItemAnimator itemAnimator=new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvUser.setItemAnimator(itemAnimator);

        ivSearch.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference("Users");
                searchTerm = etSearchTerm.getText().toString().trim();
                showProgressDialog("Finding...");
                usersDb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int pos=0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String email = snapshot.child("email").getValue(String.class);
                            String name = snapshot.child("name").getValue(String.class);
                            String photo = snapshot.child("photo").getValue(String.class);
                            String id = snapshot.getKey();
                            if (email != null && email.contains(searchTerm)) {
                                //add to arraylist
                                uAdapter.insert(pos,new User(name,email,id,photo));

                            }
                            pos++;
                        }
                        if(userData.size()==0){
                            userData.clear();
                            uAdapter.notifyDataSetChanged();
                        }
                        hideProgressDialog();
                        //pass this to adapter
                        //pass adapter to recyclerview
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        hideProgressDialog();

                    }
                });
            }
        });


    }

}
