package trainedge.demotraining.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;
import trainedge.demotraining.R;
import trainedge.demotraining.fragment.AddContactFragment;
import trainedge.demotraining.fragment.ChatFragment;
import trainedge.demotraining.fragment.ContactsFragment;
import trainedge.demotraining.fragment.InviteFragment;

public class NextActivity extends BasicActivity {


  /*  private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
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

    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_add_black_24dp),
                        Color.parseColor(colors[0]))
                        .title("Add Contact")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_contacts_black_24dp),
                        Color.parseColor(colors[1]))
                        .title("Chat")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_outline_black_24dp),
                        Color.parseColor(colors[2]))
                        .title("Contacts")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_share_black_24dp),
                        Color.parseColor(colors[2]))
                        .title("Invite")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 1);
    }



    public class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return AddContactFragment.newInstance("","");
                case 1:
                    return ChatFragment.newInstance("","");
                case 2:
                    return ContactsFragment.newInstance("","");
                case 3:
                    return InviteFragment.newInstance("","");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }


}
