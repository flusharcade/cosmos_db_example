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
    public TextView ridTextView;
    public TextView selfTextView;
    public TextView eTagTextView;
    public TextView collsTextView;
    public TextView usersTextView;
    public TextView tsTextView;

    public DatabaseViewHolder(View itemView){
        super(itemView);

        idTextView = (TextView) itemView.findViewById(R.id.id);
        ridTextView = (TextView) itemView.findViewById(R.id.rid);
        selfTextView = (TextView) itemView.findViewById(R.id.self);
        eTagTextView = (TextView) itemView.findViewById(R.id.eTag);
        collsTextView = (TextView) itemView.findViewById(R.id.colls);
        usersTextView = (TextView) itemView.findViewById(R.id.users);
        tsTextView = (TextView) itemView.findViewById(R.id.ts);
    }
}
