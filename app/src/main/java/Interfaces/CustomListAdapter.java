package Interfaces;

import android.os.Debug;
import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableRecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eslbuddy.juanmartinez.eslbuddy.R;

import org.w3c.dom.Text;

public class CustomListAdapter extends WearableRecyclerView.Adapter<CustomListAdapter.ViewHolder>{

    //Simple data as array of strings
    private String[] dataSet;

    //Constructor
    public CustomListAdapter(String[] data){
        this.dataSet = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view

        TextView v = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
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
        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }


}
