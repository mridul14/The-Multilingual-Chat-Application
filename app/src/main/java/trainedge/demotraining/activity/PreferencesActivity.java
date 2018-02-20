package trainedge.demotraining.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import trainedge.demotraining.adapter.DataAdapter;
import trainedge.demotraining.R;
import trainedge.demotraining.model.Data;
import trainedge.demotraining.model.InfoModel;

public class PreferencesActivity extends BasicActivity implements View.OnClickListener {

    public static final String LANG_PREF = "lang_pref";
    public static final String IS_VISITED = "is_visited";
    public static final String LANG_KEY = "lang_key";
    private RecyclerView rvLanguages;

    private ArrayList<InfoModel> data,actualData;
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

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        dataGenerator();
        rvLanguages = (RecyclerView) findViewById(R.id.rvLanguage);
        rvLanguages.setLayoutManager(new LinearLayoutManager(this));
        DataAdapter dAdapter=new DataAdapter(this,data,actualData);
        rvLanguages.setAdapter(dAdapter);




        //lang_pref = getSharedPreferences("lang_pref",MODE_PRIVATE);
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

        data = new ArrayList<>();
        actualData = new ArrayList<>();
        data.add(new InfoModel("English"));
        actualData.add(new InfoModel("en"));
        data.add(new InfoModel("Hindi"));
        actualData.add(new InfoModel("hi"));
        data.add(new InfoModel("Arabic"));
        actualData.add(new InfoModel("ar"));
        data.add(new InfoModel("Armenian"));
        actualData.add(new InfoModel("hy"));
        data.add(new InfoModel("Albanian"));
        actualData.add(new InfoModel("sq"));
        data.add(new InfoModel("Azerbaijan"));
        actualData.add(new InfoModel("az"));
        data.add(new InfoModel("Afrikaans"));
        actualData.add(new InfoModel("af"));
        data.add(new InfoModel("Basque"));
        actualData.add(new InfoModel("eu"));
        data.add(new InfoModel("Belarusian"));
        actualData.add(new InfoModel("be"));
        data.add(new InfoModel("Bulgarian"));
        actualData.add(new InfoModel("bg"));
        data.add(new InfoModel("Welsh"));
        actualData.add(new InfoModel("cy"));
        data.add(new InfoModel("Vietnamese"));
        actualData.add(new InfoModel("vi"));
        data.add(new InfoModel("Hungarian"));
        actualData.add(new InfoModel("hu"));
        data.add(new InfoModel("Haitian (Creole)"));
        actualData.add(new InfoModel("ht"));
        data.add(new InfoModel("Galician"));
        actualData.add(new InfoModel("gl"));
        data.add(new InfoModel("Dutch"));
        actualData.add(new InfoModel("nl"));
        data.add(new InfoModel("Greek"));
        actualData.add(new InfoModel("el"));
        data.add(new InfoModel("Georgian"));
        actualData.add(new InfoModel("ka"));
        data.add(new InfoModel("Danish"));
        actualData.add(new InfoModel("da"));
        data.add(new InfoModel("Yiddish"));
        actualData.add(new InfoModel("he"));
        data.add(new InfoModel("Indonesian"));
        actualData.add(new InfoModel("id"));
        data.add(new InfoModel("Irish"));
        actualData.add(new InfoModel("ga"));
        data.add(new InfoModel("Italian"));
        actualData.add(new InfoModel("it"));
        data.add(new InfoModel("Icelandic"));
        actualData.add(new InfoModel("is"));
        data.add(new InfoModel("Spanish"));
        actualData.add(new InfoModel("es"));
        data.add(new InfoModel("Chinese"));
        actualData.add(new InfoModel("zh"));
        data.add(new InfoModel("Korean"));
        actualData.add(new InfoModel("ko"));
        data.add(new InfoModel("Latin"));
        actualData.add(new InfoModel("la"));
        data.add(new InfoModel("German"));
        actualData.add(new InfoModel("de"));
        data.add(new InfoModel("Polish"));
        actualData.add(new InfoModel("pl"));


    }

    @Override
    public void onClick(View view) {

    }
}

