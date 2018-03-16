package trainedge.demotraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import trainedge.demotraining.R;

public class SignupActivity extends BasicActivity {

    private TextView link_login;
    private Button btn_signup;
    private EditText et_pass;
    private EditText et_email;
    private EditText et_name;
    private ImageView iv_logo;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final int REQUEST_SIGNUP = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        et_name = (EditText) findViewById(R.id.et_name);
        et_email = (EditText) findViewById(R.id.et_email);
        et_pass = (EditText) findViewById(R.id.et_pass);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        link_login = (TextView) findViewById(R.id.link_login);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(SignupActivity.this, NextActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
            }
        };

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        btn_signup.setEnabled(false);
        showProgressDialog("creating account");

        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String password = et_pass.getText().toString();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        hideProgressDialog();
                    }
                }, 3000);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onSignupSuccess() {
        btn_signup.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        btn_signup.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String password = et_pass.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            et_name.setError("at least 3 characters");
            valid = false;
        } else {
            et_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            et_email.setError("enter a valid email address");
            valid = false;
        } else {
            et_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            et_pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            et_pass.setError(null);
        }

        return valid;



    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

/*    @Override
    public void onClick(View view) {
        int id;
        id=view.getId();
        if (id==R.id.link_login){
            Intent intent=new Intent(SignupActivity.this,MainActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        }
        if (id==R.id.btn_signup){
            name = et_name.getText().toString();
            email = et_email.getText().toString();
            password = et_pass.getText().toString();



            if (name.isEmpty() || name.length() < 3) {
                et_name.setError("at least 3 characters");
            } else {
                et_name.setError(null);
            }

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                et_email.setError("enter a valid email address");
            } else {
                et_email.setError(null);
            }

            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                et_pass.setError("between 4 and 10 alphanumeric characters");
            } else {
                et_pass.setError(null);
            }


            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this,"User Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(SignupActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });

        }


    }*/
}
