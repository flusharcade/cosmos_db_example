package microsoft.cosmos_db_example.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    private List<T> _mItems;

    private int _viewLayoutId;
    private ViewHolder _viewHolder;

    private Callback<Object> _onBindViewHolderMethod;
    private Class<?> _viewHolderClazz;
    private Activity _activity;

    public CardAdapter(int viewLayoutId, Callback<Object> onBindViewHolderMethod, Class<?> viewHolderClazz, Activity activity) {
        super();

        _viewLayoutId = viewLayoutId;
        _onBindViewHolderMethod = onBindViewHolderMethod;
        _viewHolderClazz = viewHolderClazz;
        _activity = activity;

        _mItems = new ArrayList<T>();
    }

    public void addData(T item) {
        _mItems.add(item);

        notifyDataSetChanged();
    }

    public void clear() {
        _mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(_viewLayoutId, viewGroup, false);

        _viewHolder = (ViewHolder)ViewHolderFactory.create(_viewHolderClazz, v);

        return _viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Object item = _mItems.get(i);

        _onBindViewHolderMethod.setResult(item, viewHolder);

        if (item != null) {
            _onBindViewHolderMethod.call();
        }
    }

    @Override
    public int getItemCount() {
        return _mItems.size();
    }
}