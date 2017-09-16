package trainedge.demotraining.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import trainedge.demotraining.R;

/**
 * Created by dell on 31-08-2017.
 */

public class UserHolder extends RecyclerView.ViewHolder {


    public TextView tvUser;
    public TextView tvUserMail;
    public Button btnAdd;
    public CardView container1;
    public ImageView ivPhoto;

    public UserHolder(View itemView) {
        super(itemView);

        tvUser = itemView.findViewById(R.id.tvUser);
        tvUserMail = itemView.findViewById(R.id.tvUserMail);
        btnAdd = itemView.findViewById(R.id.btnAdd);
        container1 = itemView.findViewById(R.id.container1);
        ivPhoto = itemView.findViewById(R.id.ivPhoto);
    }
}
