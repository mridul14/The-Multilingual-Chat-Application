package trainedge.demotraining.activity;

import android.content.ActivityNotFoundException;
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

public class InviteActivity extends BasicActivity implements View.OnClickListener {

    private GoogleApiClient mGoogleApiClient;
    boolean autodeeplink = true;
    private Button btnInvite;
    private Button btnFeedback;
    private Button btnRate;
    private Button btnAbout;
    private static final int INVITE_REQUEST_CODE = 99;


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

        /*//coding
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
                );*/
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnInvite:
                try {
                    Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                            .setMessage("Join your friends")
                            .setDeepLink(Uri.parse("/link"))
                            .setCallToActionText(getString(R.string.invitation_cta))
                            .build();
                    startActivityForResult(intent,INVITE_REQUEST_CODE  );
                } catch (ActivityNotFoundException ac) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    //sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.sharing_book_title, bookTitle));
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
               /* Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                        .setMessage(getString(R.string.invitation_message))
                        //.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                        .setCallToActionText(getString(R.string.invitation_cta))
                        .setDeepLink(Uri.parse("/link"))
                        // .setOtherPlatformsTargetApplication(
                        //AppInviteInvitation.IntentBuilder.PlatformMode.PROJECT_PLATFORM_IOS,
                        //getString(R.string.ios_app_client_id))
                        .build();
                startActivity(intent);*/
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INVITE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                // Log.d(TAG, getString(R.string.sent_invitations_fmt, ids.length));
                //log("invite");
            } else {

                //Log.d(TAG, "invite send failed or cancelled:" + requestCode + ",resultCode:" + resultCode);
                //log("invite send failed or cancelled");
            }
        }
    }
    private void checkIfComingFromInvite(){
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(AppInvite.API)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        //log( "onConnectionFailed: onResult:" + connectionResult.toString());
                    }
                })
                .build();
        AppInvite.AppInviteApi.getInvitation(googleApiClient, this, true)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(AppInviteInvitationResult result) {
                                //log("getInvitation:onResult:" + result.getStatus());

                            }
                        });
    }




}