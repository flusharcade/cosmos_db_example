package microsoft.cosmos_db_example.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import microsoft.cosmos_db_example.R;

/**
 * Created by mww121 on 27/10/17.
 */

public class DocumentCollectionViewHolder extends RecyclerView.ViewHolder {
    public TextView idTextView;
    public TextView ridTextView;
    public TextView selfTextView;
    public TextView eTagTextView;

    public DocumentCollectionViewHolder(View itemView){
        super(itemView);

        idTextView = (TextView) itemView.findViewById(R.id.id);
        ridTextView = (TextView) itemView.findViewById(R.id.rid);
        selfTextView = (TextView) itemView.findViewById(R.id.self);
        eTagTextView = (TextView) itemView.findViewById(R.id.eTag);
    }
}
