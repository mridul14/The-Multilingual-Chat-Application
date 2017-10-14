package trainedge.demotraining.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.holder.DataHolder;
import trainedge.demotraining.model.Data;
import trainedge.demotraining.model.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by dell on 19-08-2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataHolder> {

    Context context;
    ArrayList<Data> dataItems;
    ArrayList<Data> actualData;


    private DatabaseReference languageChoice;
    private SharedPreferences lang_pref;

    public DataAdapter(Context context, ArrayList<Data> dataItems, ArrayList<Data> actualData) {
        this.context = context;
        this.dataItems = dataItems;
        this.actualData=actualData;

        languageChoice = FirebaseDatabase.getInstance().getReference("Users");
        lang_pref = context.getSharedPreferences("lang_pref", MODE_PRIVATE);
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.simple_card_item, parent, false);
        return new DataHolder(v);

        //db = fd.getReference("Language Choice");
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        Data data = dataItems.get(position);
        final Data actualItem=actualData.get(position);

        holder.tvLanguages.setText(data.getLanguage());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lang_name = actualItem.getLanguage();
                addToFirebase(lang_name);

            }
        });


    }

    private void addToFirebase(final String lang_name) {

        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();

        languageChoice.child(currentuser.getUid()).child("language").setValue(lang_name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                if (databaseError == null) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = lang_pref.edit();
                    editor.putString("lang_key", lang_name);
                    editor.putBoolean("is_visited", true);
                    editor.apply();

                    Intent intent=new Intent(context,NextActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(intent);

                } else {

                    Toast.makeText(context, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


}
