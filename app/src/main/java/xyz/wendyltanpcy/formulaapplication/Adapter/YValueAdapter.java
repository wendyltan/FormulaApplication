package xyz.wendyltanpcy.formulaapplication.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import xyz.wendyltanpcy.formulaapplication.Fragment.CaculateFragment;
import xyz.wendyltanpcy.formulaapplication.Model.YValue;
import xyz.wendyltanpcy.formulaapplication.R;
import xyz.wendyltanpcy.formulaapplication.onFloatMenuActionListener;

/**
 * Created by Wendy on 2018/5/4.
 */

public class YValueAdapter extends RecyclerView.Adapter<YValueAdapter.ViewHolder> implements onFloatMenuActionListener {

    private Context mContext;
    private List<YValue> mYValues;

    public YValueAdapter(){

    }

    public YValueAdapter(List<YValue> yValues, Context con){
        mYValues = yValues;
        mContext = con;
        CaculateFragment.setListener(this);
    }

    @Override
    public YValueAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_y,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(YValueAdapter.ViewHolder holder, int position) {
        holder.y_id.setText(String.valueOf(mYValues.get(position).getId()));
        holder.y_value.setText(String.valueOf(mYValues.get(position).getY()));
    }

    @Override
    public int getItemCount() {
        return mYValues.size();
    }

    @Override
    public void handleAction(String action) {
        if (action=="save"){
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView y_id;
        TextView y_value;
        public ViewHolder(View itemView) {
            super(itemView);
            y_id = itemView.findViewById(R.id.y_id);
            y_value = itemView.findViewById(R.id.y_value);
        }
    }
}
