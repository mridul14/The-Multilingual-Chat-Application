package trainedge.demotraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

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
    private DatabaseReference db;
    private String name;
    private String email;


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
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = et_name.getText().toString().trim();
                email = et_email.getText().toString().trim();
                String password=et_pass.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDialog("registering");
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignupActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                hideProgressDialog();
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    saveToDatabase(user);
                                    startActivity(new Intent(SignupActivity.this, PreferencesActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });



    }
    private void saveToDatabase(final FirebaseUser user) {
        db = FirebaseDatabase.getInstance().getReference("Users");
        HashMap<String, Object> map=new HashMap<>();
        map.put("email",email);
        //map.put("photo",user.getPhotoUrl().toString());
        map.put("name",name);
        map.put("id",user.getUid());
        db.child(user.getUid()).setValue(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null) {
                    //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    hideProgressDialog();
                    //updateUI(user);

                }
            }
        });

    }
}
