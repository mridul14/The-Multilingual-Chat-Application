package trainedge.demotraining.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import trainedge.demotraining.R;

/**
 * Created by dell on 09-10-2017.
 */

public class ContactsHolder extends RecyclerView.ViewHolder {


    public final TextView tvUser;
    public final TextView tvUserMail;
    public final CardView container1;
    public final ImageView ivPhoto;

public ContactsHolder(View itemView) {
        super(itemView);

        tvUser = itemView.findViewById(R.id.tvUser);
        tvUserMail = itemView.findViewById(R.id.tvUserMail);
        container1 = itemView.findViewById(R.id.container1);
        ivPhoto = itemView.findViewById(R.id.ivPhoto);
        }
}
