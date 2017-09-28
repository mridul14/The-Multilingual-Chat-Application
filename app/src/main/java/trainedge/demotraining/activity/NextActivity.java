package trainedge.demotraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import trainedge.demotraining.R;

public class NextActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_add_contact:
                    Intent contactsintent=new Intent(NextActivity.this,AddContactsActivity.class);
                    startActivity(contactsintent);
                    return true;
                case R.id.navigation_chat:
                    Intent chatsintent=new Intent(NextActivity.this,ChatActivity.class);
                    startActivity(chatsintent);
                    return true;
                case R.id.navigation_invite:
                    Intent invitesintent=new Intent(NextActivity.this,InviteActivity.class);
                    startActivity(invitesintent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }



}
