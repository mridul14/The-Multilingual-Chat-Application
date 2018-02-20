package trainedge.demotraining.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;


public class BasicActivity extends AppCompatActivity {
    public static final String PACKAGE_NAME="trainedge.demotraining";
    public static final String APP_NAME="BLAH";
    public Context context=this;
    private ProgressDialog dialog;

    public void showProgressDialog(String msg){
        dialog = new ProgressDialog(this);
        dialog.setMessage(msg);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void hideProgressDialog(){
        if (dialog!=null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }

    public void message(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void messageBar(View v, String msg){

        //requires design library
        // Snackbar.make(v,msg,Snackbar.LENGTH_LONG).show();
    }

    public void showAlert(String title,String message,String yes,String no){

        AlertDialog dialog=new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        LoginManager.getInstance().logOut();
                        Intent intent= new Intent(BasicActivity.this,MainActivity.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(context, "you cancelled logout", Toast.LENGTH_SHORT).show();

                    }
                })
                .create();
        dialog.show();
    }

    //to show output on android monitor

    public  void log(String data){

        Log.d(" trainedge.training",data);
    }

}


