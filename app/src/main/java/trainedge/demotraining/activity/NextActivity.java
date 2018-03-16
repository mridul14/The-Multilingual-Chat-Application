package trainedge.demotraining.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;
import trainedge.demotraining.R;
import trainedge.demotraining.fragment.AddContactFragment;
import trainedge.demotraining.fragment.ChatFragment;
import trainedge.demotraining.fragment.ContactsFragment;
import trainedge.demotraining.fragment.InviteFragment;

public class NextActivity extends BasicActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final int INVITE_REQUEST_CODE = 99;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wrapper_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_contacts_black_24dp),
                        Color.parseColor(colors[2]))
                        .title("Chat")
                        .build()
        );
/*        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_outline_black_24dp),
                        Color.parseColor(colors[2]))
                        .title("Contacts")
                        .build()
        );*/
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_person_add_black_24dp),
                        Color.parseColor(colors[0]))
                        .title("Add Contact")
                        .build()
        );

       /* models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_share_black_24dp),
                        Color.parseColor(colors[3]))
                        .title("Invite")
                        .build()
        );*/

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 1);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        int count = navigationView.getHeaderCount();
        if (count == 1) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            View header = navigationView.getHeaderView(0);
            TextView tv_name= header.findViewById(R.id.tv_name);
            TextView tv_email= header.findViewById(R.id.tv_email);
            ImageView ivMain = header.findViewById(R.id.iv_main);

            if (user != null) {
                Uri photoUrl = user.getPhotoUrl();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Glide.with(this).load(photoUrl).into(ivMain).onLoadFailed(getDrawable(R.drawable.ic_person_outline_black_24dp));
                }
                String email = user.getEmail();
                String name = user.getDisplayName();
                tv_name.setText(name);
                tv_email.setText(email);

            }
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_chat) {
            viewPager.setCurrentItem(0);

        }else if (id == R.id.nav_add_contacts) {
            viewPager.setCurrentItem(1);

        }else if (id==R.id.nav_about){
            Intent intent=new Intent(NextActivity.this,InviteActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_share) {
            try {
                Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage("Join your friends")
                        .setDeepLink(Uri.parse("/link"))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .build();
                startActivityForResult(intent, INVITE_REQUEST_CODE);
            } catch (ActivityNotFoundException ac) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                //sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_book_title, bookTitle));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }

        } else if (id == R.id.nav_send) {
            Intent feedbackIntent = new Intent(android.content.Intent.ACTION_SEND);
            feedbackIntent.setType("text/html");
            feedbackIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.mail_feedback_email)});
            feedbackIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
            feedbackIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
            startActivity(Intent.createChooser(feedbackIntent, getString(R.string.title_send_feedback)));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
                /*case 1:
                    return ChatFragment.newInstance("","");*/
                case 1:
                    return ContactsFragment.newInstance("","");

            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}





