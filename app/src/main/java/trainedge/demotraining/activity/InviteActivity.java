package trainedge.demotraining.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;

import trainedge.demotraining.R;

public class InviteActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    boolean autodeeplink = true;
    private Button btnInvite;
    private Button btnFeedback;
    private Button btnRate;
    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnInvite = (Button) findViewById(R.id.btnInvite);
        btnFeedback = (Button) findViewById(R.id.btnFeedback);
        btnRate = (Button) findViewById(R.id.btnRate);
        btnAbout = (Button) findViewById(R.id.btnAbout);


        btnInvite.setOnClickListener(this);
        btnFeedback.setOnClickListener(this);
        btnRate.setOnClickListener(this);
        btnAbout.setOnClickListener(this);

        //coding
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, this)
                .build();


        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autodeeplink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    //Get intent information
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);
                                    String invitationId = AppInviteReferral.getInvitationId(intent);
                                }
                            }
                        }
                );
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "no connection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnInvite:
                Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage(getString(R.string.invitation_message))
                        //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .setDeepLink(Uri.parse("/link"))
                        // .setOtherPlatformsTargetApplication(
                        //AppInviteInvitation.IntentBuilder.PlatformMode.PROJECT_PLATFORM_IOS,
                        //getString(R.string.ios_app_client_id))
                        .build();
                startActivity(intent);
                break;

            case R.id.btnFeedback:
                Intent feedbackIntent = new Intent(android.content.Intent.ACTION_SEND);
                feedbackIntent.setType("text/html");
                feedbackIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ getString(R.string.mail_feedback_email) });
                feedbackIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.mail_feedback_subject));
                feedbackIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.mail_feedback_message));
                startActivity(Intent.createChooser(feedbackIntent, getString(R.string.title_send_feedback)));
                break;
            case R.id.btnRate:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=PackageName")));
                break;
            case R.id.btnAbout:
                Intent intent1=new Intent(InviteActivity.this,AboutActivity.class);
                startActivity(intent1);
                break;

        }

    }
}