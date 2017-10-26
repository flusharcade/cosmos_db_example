package microsoft.cosmos_db_example.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import microsoft.cosmos_db_example.Models.TodoItem;
import microsoft.cosmos_db_example.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<TodoItem> mItems;

    public CardAdapter() {
        super();
        mItems = new ArrayList<TodoItem>();
    }

    public void addData(TodoItem TodoItem) {
        mItems.add(TodoItem);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TodoItem TodoItem = mItems.get(i);
        viewHolder.idTextView.setText(TodoItem.getID());
        viewHolder.descriptionTextView.setText("repos: " + TodoItem.getDescription());
        viewHolder.ownerTextView.setText("blog: " + TodoItem.getOwner());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView idTextView;
        public TextView descriptionTextView;
        public TextView ownerTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            idTextView = (TextView) itemView.findViewById(R.id.id);
            descriptionTextView = (TextView) itemView.findViewById(R.id.description);
            ownerTextView = (TextView) itemView.findViewById(R.id.owner);
        }
    }
}