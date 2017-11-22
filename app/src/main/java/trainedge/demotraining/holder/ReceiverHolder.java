package trainedge.demotraining.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import trainedge.demotraining.R;


public class ReceiverHolder extends RecyclerView.ViewHolder {

    public ImageView image_message_profile;
    public TextView text_message_name;
    public TextView text_message_body;
    public TextView text_message_time;

    public ReceiverHolder(View itemView) {
        super(itemView);

        image_message_profile = itemView.findViewById(R.id.image_message_profile);
        text_message_name = itemView.findViewById(R.id.text_message_name);
        text_message_body = itemView.findViewById(R.id.text_message_body);
        text_message_time = itemView.findViewById(R.id.text_message_time);
    }
}