package trainedge.demotraining.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.adapter.ContactsAdapter;
import trainedge.demotraining.adapter.UserAdapter;
import trainedge.demotraining.model.User;

public class AddContactFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rvUser;
    private EditText etSearchTerm;
    private List<User> data;
    private boolean isLoaded=false;
    private ImageView ivSearch;
    private ContactsAdapter cAdapter;
    private View view;
    private TextView tv_name;


    public AddContactFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddContactFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddContactFragment newInstance(String param1, String param2) {
        AddContactFragment fragment = new AddContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        data = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        rvUser = view.findViewById(R.id.rvUser);
        etSearchTerm = view.findViewById(R.id.etSearchTerm);
        ivSearch = view.findViewById(R.id.ivSearch);
        tv_name = view.findViewById(R.id.tv_name);

        final List<User> myContacts = new ArrayList<>();
        final List<User> contacts= new ArrayList<>();
        final List<String> frdId=new ArrayList<String>();

        cAdapter = new ContactsAdapter(myContacts,(NextActivity)getActivity());
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvUser.setItemAnimator(itemAnimator);
        rvUser.setAdapter(cAdapter);

        final DatabaseReference allDb = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference myContactsDb = FirebaseDatabase.getInstance().getReference(currentUser.getUid()).child("Contacts");
        myContactsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myContacts.clear();
                if (dataSnapshot.getChildrenCount() >0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        frdId.add(snapshot.getKey());

                    }

                }
                isLoaded=true;
                findContacts(allDb,myContacts,frdId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                }
            }
        });

        return view;
    }

    private void findContacts(DatabaseReference allDb, final List<User> myContacts, final List<String> frdId) {
        if (isLoaded) {
            allDb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int pos = 0;
                    myContacts.clear();
                    if (dataSnapshot.hasChildren()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (frdId.contains(snapshot.getKey())) {
                                myContacts.add(snapshot.getValue(User.class));
                            }
                        }
                    } else {
                        Toast.makeText(getActivity(), "could not find data", Toast.LENGTH_SHORT).show();
                    }
                    if (myContacts.size() > 0) {
                        cAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // hideProgressDialog();
                    if (databaseError != null) {
                        Toast.makeText(getActivity(), "error", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }
}
