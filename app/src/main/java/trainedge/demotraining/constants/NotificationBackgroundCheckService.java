package trainedge.demotraining.constants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by dell on 21-02-2018.
 */

public class NotificationBackgroundCheckService extends Service {

    private FirebaseUser currentUser;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
        //currentUser = FirebaseAuth.getInstance().getCurrentUser();
        // final DatabaseReference usersDb=FirebaseDatabase.getInstance().getReference("Users");
        //DatabaseReference dbNotify= FirebaseDatabase.getInstance().getReference(currentUser.getUid()).child("messages");
        //dbNotify.addValueEventListener(new ValueEventListener() {
        //  @Override
        // public void onDataChange(DataSnapshot dataSnapshot) {

        //          }
//
        //        @Override
        //      public void onCancelled(DatabaseError databaseError) {

//            }
        //      });
    }

}
