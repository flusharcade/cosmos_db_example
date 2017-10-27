package microsoft.cosmos_db_example.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import microsoft.cosmos_db_example.R;

/**
 * Created by mww121 on 27/10/17.
 */

public class DatabaseViewHolder extends RecyclerView.ViewHolder {
    public TextView idTextView;
    public TextView descriptionTextView;
    public TextView ownerTextView;

    public DatabaseViewHolder(View itemView){
        super(itemView);

        idTextView = (TextView) itemView.findViewById(R.id.id);
        descriptionTextView = (TextView) itemView.findViewById(R.id.description);
        ownerTextView = (TextView) itemView.findViewById(R.id.owner);
    }
}
