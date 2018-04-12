package Interfaces;

import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eslbuddy.juanmartinez.eslbuddy.R;

import org.w3c.dom.Text;

public class CustomListAdapter extends WearableRecyclerView.Adapter<CustomListAdapter.ViewHolder>{

    //Simple data as array of strings
    private String[] dataSet;

    //Click listener
    private CircularViewClickListener listener;

    //Constructor
    public CustomListAdapter(String[] data, CircularViewClickListener listener){
        this.dataSet = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public static class ViewHolder extends WearableRecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolder(LinearLayout v, final CircularViewClickListener listener) {
            super(v);
            mTextView = (TextView) v.getChildAt(1);
            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListItem(mTextView.getText().toString());
                }
            });
        }
    }


}
