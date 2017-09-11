package trainedge.demotraining.holder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import trainedge.demotraining.R;

/**
 * Created by dell on 19-08-2017.
 */

public class DataHolder extends RecyclerView.ViewHolder {

   public final TextView tvLanguages;
   public final CardView container;

    public DataHolder(View itemView) {
        super(itemView);

        tvLanguages = itemView.findViewById(R.id.tvLanguage);
        container = itemView.findViewById(R.id.container);
    }
}
