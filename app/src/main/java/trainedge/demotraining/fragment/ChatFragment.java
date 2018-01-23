package trainedge.demotraining.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import trainedge.demotraining.adapter.ChatAdapter;
import trainedge.demotraining.adapter.ContactsAdapter;
import trainedge.demotraining.model.ChatModel;
import trainedge.demotraining.model.User;

public class ChatFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView rv_chat;
    private ImageView iv;
    private boolean isLoaded;
    private TextView tv_name;
    private List<ChatModel> chatName;
    private FirebaseUser currentUser;
    private ArrayList<String> chatKeys;
    private ChatAdapter chatAdapter;





    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        rv_chat = view.findViewById(R.id.rv_chat);
        chatName=new ArrayList<>();
        setupChats(chatName);
        currentUser= FirebaseAuth.getInstance().getCurrentUser();
        final String emailPart=currentUser.getEmail().split("@")[0];
        chatKeys=new ArrayList<String>();
        FirebaseDatabase.getInstance().getReference("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()){
                    chatName.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        findContacts(snapshot);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        iv = view.findViewById(R.id.ivPhoto);
        tv_name = view.findViewById(R.id.tvUser);

        return view;
    }

    private void setupChats(List<ChatModel> chatName) {
        chatAdapter = new ChatAdapter(chatName,getActivity());
        rv_chat.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        rv_chat.setItemAnimator(itemAnimator);
        rv_chat.setAdapter(chatAdapter);
    }

    private void findContacts(DataSnapshot snapshot) {
        String person1Id = snapshot.child("person1").getValue(String.class);
        String person2Id = snapshot.child("person2").getValue(String.class);
        if (!person1Id.equals(currentUser.getUid())) {
            findUserById(person1Id,snapshot);
        } else {
            findUserById(person2Id,snapshot);
        }
    }


    public void findUserById(final String uid, DataSnapshot snapshot){
        final String chatKey=snapshot.getKey();
        DatabaseReference users = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot.hasChildren()){
//                User user = dataSnapshot.getValue(User.class);
//                tv_name.setText(user.name);
//                Glide.with(getActivity()).load(user.photo).into(iv);
                String name=dataSnapshot.child("name").getValue(String.class);
                String photo=dataSnapshot.child("photo").getValue(String.class);

                chatName.add(new ChatModel(uid,name,photo,chatKey));
                chatAdapter.notifyDataSetChanged();
            }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
