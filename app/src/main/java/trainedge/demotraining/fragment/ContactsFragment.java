package trainedge.demotraining.fragment;


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
import trainedge.demotraining.adapter.UserAdapter;
import trainedge.demotraining.model.User;

public class ContactsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private RecyclerView rvUser;
    private EditText etSearchTerm;
    private ImageView ivSearch;
    private List<User> data;
    private boolean isLoaded = false;
    private TextView tv_name;
    private DatabaseReference userDb;


    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
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
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        rvUser = view.findViewById(R.id.rvUser);
        tv_name = view.findViewById(R.id.tv_name);
        etSearchTerm = view.findViewById(R.id.etSearchTerm);
        ivSearch = view.findViewById(R.id.ivSearch);
        /*final List<User> myContacts=new ArrayList<>();
        final List<User> contacts=new ArrayList<>();
        final List<String> frdId=new ArrayList<>();*/
        //sAdapter = new UserAdapter(data, (NextActivity) getActivity());
        //rvUser.setAdapter(sAdapter);
        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rvUser.setItemAnimator(itemAnimator);
        final List<String> loadMyContacts = new ArrayList<>();
        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userDb = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference myContactsDb = FirebaseDatabase.getInstance().getReference(currentUser.getUid()).child("Contacts");
        myContactsDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadMyContacts.clear();
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        loadMyContacts.add(snapshot.getKey());

                    }
                    //Toast.makeText(getActivity() , "Data has been loaded", Toast.LENGTH_SHORT).show();
                }
                isLoaded = true;
                //findContact(allDb, myContacts, frdId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError != null) {
                    //Toast.makeText(getActivity(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String searchTerm = etSearchTerm.getText().toString().trim();

                if (isLoaded) {

                    try {
                        fetchData(searchTerm, userDb, loadMyContacts, currentUser);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //FabToast.makeText(getActivity(), "could not find data", FabToast.LENGTH_SHORT, FabToast.ERROR, FabToast.POSITION_CENTER).show();
                }
            }
        });

        return view;
    }

    private void fetchData(final String searchTerm, DatabaseReference usersDb, final List<String> loadMyContacts, final FirebaseUser currentUser) {
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int pos = 0;
                data.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    boolean isPresent = false;
                    String email = snapshot.child("email").getValue(String.class);
                    String name = snapshot.child("name").getValue(String.class);
                    String photo = snapshot.child("photo").getValue(String.class);
                    String lang = snapshot.child("language").getValue(String.class);
                    String id = snapshot.getKey();
                    for (int i = 0; i < loadMyContacts.size(); i++) {
                        if (loadMyContacts.get(i).equals(id)) {
                            isPresent = true;
                        }
                    }
                    try {
                        if (email != null && email.contains(searchTerm) && !email.equals(currentUser.getEmail()) && !isPresent) {
                            //add to array list
                            data.add(new User(name, email, id, photo, lang));
                        }
                        pos++;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (data.size() == 0) {
                    data.clear();
                    //FabToast.makeText(getActivity(), "no person found", FabToast.LENGTH_SHORT,FabToast.ERROR,FabToast.POSITION_CENTER).show();
                } else {

                    final UserAdapter sAdapter = new UserAdapter(data, (NextActivity) getActivity());
                    rvUser.setAdapter(sAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}