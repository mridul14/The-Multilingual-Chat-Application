package trainedge.demotraining.adapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import trainedge.demotraining.R;
import trainedge.demotraining.activity.NextActivity;
import trainedge.demotraining.activity.PreferencesActivity;
import trainedge.demotraining.holder.DataHolder;
import trainedge.demotraining.model.Data;
import trainedge.demotraining.model.InfoModel;

import static android.content.Context.MODE_PRIVATE;

public class DataAdapter extends RecyclerView.Adapter<DataHolder> {

    private PreferencesActivity activity;
    ArrayList<InfoModel> data;
    ArrayList<InfoModel> actualData;


    private DatabaseReference languageChoice;
    private SharedPreferences lang_pref;

    public DataAdapter(PreferencesActivity activity, ArrayList<InfoModel> data, ArrayList<InfoModel> actualData) {
        this.data=data;
        this.activity = activity;
        this.actualData = actualData;
        languageChoice = FirebaseDatabase.getInstance().getReference("Users");
        lang_pref = activity.getSharedPreferences(PreferencesActivity.LANG_PREF, MODE_PRIVATE);
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(activity).inflate(R.layout.simple_card_item, parent, false);
        return new DataHolder(v);

        //db = fd.getReference("Language Choice");
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        InfoModel data = this.data.get(position);
        final InfoModel actualItem = actualData.get(position);

        holder.tvLanguages.setText(data.getTitle());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.showProgressDialog("Updating your Preferences");
                String lang_name = actualItem.getTitle();
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
                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = lang_pref.edit();
                    editor.putString(PreferencesActivity.LANG_PREF, lang_name);
                    editor.putBoolean(PreferencesActivity.IS_VISITED, true);
                    editor.apply();

                    Intent intent = new Intent(activity, NextActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.hideProgressDialog();
                    activity.startActivity(intent);
                    activity.finish();


                } else {

                    Toast.makeText(activity, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
