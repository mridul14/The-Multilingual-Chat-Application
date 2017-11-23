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


    private RecyclerView rvLanguages;

    private ArrayList<Data> dataItems,actualData;
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

        dataGenerator();
        rvLanguages = (RecyclerView) findViewById(R.id.rvLanguage);
        rvLanguages.setLayoutManager(new LinearLayoutManager(this));
        rvLanguages.setAdapter(new DataAdapter(this,dataItems,actualData));




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

        dataItems = new ArrayList<>();
        actualData = new ArrayList<>();
        dataItems.add(new Data("English"));
        actualData.add(new Data("en"));
        dataItems.add(new Data("Hindi"));
        actualData.add(new Data("hi"));
        dataItems.add(new Data("Arabic"));
        actualData.add(new Data("ar"));
        dataItems.add(new Data("Armenian"));
        actualData.add(new Data("hy"));
        dataItems.add(new Data("Albanian"));
        actualData.add(new Data("sq"));
        dataItems.add(new Data("Azerbaijan"));
        actualData.add(new Data("az"));
        dataItems.add(new Data("Afrikaans"));
        actualData.add(new Data("af"));
        dataItems.add(new Data("Basque"));
        actualData.add(new Data("eu"));
        dataItems.add(new Data("Belarusian"));
        actualData.add(new Data("be"));
        dataItems.add(new Data("Bulgarian"));
        actualData.add(new Data("bg"));
        dataItems.add(new Data("Welsh"));
        actualData.add(new Data("cy"));
        dataItems.add(new Data("Vietnamese"));
        actualData.add(new Data("vi"));
        dataItems.add(new Data("Hungarian"));
        actualData.add(new Data("hu"));
        dataItems.add(new Data("Haitian (Creole)"));
        actualData.add(new Data("ht"));
        dataItems.add(new Data("Galician"));
        actualData.add(new Data("gl"));
        dataItems.add(new Data("Dutch"));
        actualData.add(new Data("nl"));
        dataItems.add(new Data("Greek"));
        actualData.add(new Data("el"));
        dataItems.add(new Data("Georgian"));
        actualData.add(new Data("ka"));
        dataItems.add(new Data("Danish"));
        actualData.add(new Data("da"));
        dataItems.add(new Data("Yiddish"));
        actualData.add(new Data("he"));
        dataItems.add(new Data("Indonesian"));
        actualData.add(new Data("id"));
        dataItems.add(new Data("Irish"));
        actualData.add(new Data("ga"));
        dataItems.add(new Data("Italian"));
        actualData.add(new Data("it"));
        dataItems.add(new Data("Icelandic"));
        actualData.add(new Data("is"));
        dataItems.add(new Data("Spanish"));
        actualData.add(new Data("es"));
        dataItems.add(new Data("Chinese"));
        actualData.add(new Data("zh"));
        dataItems.add(new Data("Korean"));
        actualData.add(new Data("ko"));
        dataItems.add(new Data("Latin"));
        actualData.add(new Data("la"));
        dataItems.add(new Data("German"));
        actualData.add(new Data("de"));
        dataItems.add(new Data("Polish"));
        actualData.add(new Data("pl"));


    }

    @Override
    public void onClick(View view) {

    }
}

