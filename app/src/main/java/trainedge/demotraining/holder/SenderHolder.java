package trainedge.demotraining.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import trainedge.demotraining.R;

/**
 * Created by dell on 20-11-2017.
 */

public class SenderHolder extends RecyclerView.ViewHolder {
    public TextView text_message_body;
    public TextView text_message_time;


    public SenderHolder(View itemView) {
        super(itemView);

        text_message_body = itemView.findViewById(R.id.text_message_body);
        text_message_time = itemView.findViewById(R.id.text_message_time);

    }
}
