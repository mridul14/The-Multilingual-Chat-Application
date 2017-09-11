package trainedge.demotraining.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import trainedge.demotraining.adapter.DataAdapter;
import trainedge.demotraining.R;
import trainedge.demotraining.model.Data;

public class PreferencesActivity extends AppCompatActivity implements View.OnClickListener {


    //private SharedPreferences lang_pref;
    //private DatabaseReference db;
    private RecyclerView rvLanguages;

    private ArrayList<Data> dataItems;
    private SharedPreferences lang_pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        lang_pref = getSharedPreferences("lang_pref", MODE_PRIVATE);
        if (lang_pref.getBoolean("is_visited",false)){
            Intent intent=new Intent(this,NextActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvLanguages = (RecyclerView) findViewById(R.id.rvLanguage);
        dataGenerator();
        rvLanguages.setLayoutManager(new LinearLayoutManager(this));
        rvLanguages.setAdapter(new DataAdapter(this,dataItems));




        lang_pref = getSharedPreferences("lang_pref",MODE_PRIVATE);
/*
        if (lang_pref.getBoolean("is_visited",false)){
            Intent intent=new Intent(PreferencesActivity.this,Main2Activity.class);
            startActivity(intent);
            finish();
        }*/

        /*FirebaseDatabase fd=FirebaseDatabase.getInstance();
        db = fd.getReference("Language Choice");*/







    }

    private void dataGenerator() {

        dataItems=new ArrayList<>();
        dataItems.add(new Data("English"));
        dataItems.add(new Data("हिंदी Hindi"));
        dataItems.add(new Data("தமிழ் Tamil"));
        dataItems.add(new Data("తెలుగు Telugu"));
        dataItems.add(new Data("ગુજરાત Gujrati"));
        dataItems.add(new Data("मराठी Marathi"));
        dataItems.add(new Data("français French"));
        dataItems.add(new Data("Deutsche German"));
        dataItems.add(new Data("Español Spanish"));
        dataItems.add(new Data("italiano Italian"));
        dataItems.add(new Data("русский Russian"));
        dataItems.add(new Data("Latine Latin"));

    }

   /* private void addToFirebase(String lang_name) {

        FirebaseUser currentuser= FirebaseAuth.getInstance().getCurrentUser();


        db.child(currentuser.getUid()).setValue(lang_name, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {



                if(databaseError==null){
                    Toast.makeText(PreferencesActivity.this,"Success",Toast.LENGTH_SHORT).show();

                }
                else {

                    Toast.makeText(PreferencesActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                }
            }
        });

    }*/


    @Override
    public void onClick(View view) {

    }
}