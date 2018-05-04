package xyz.wendyltanpcy.formulaapplication.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.speedystone.greendaodemo.db.FormulaDao;

import java.util.List;

import xyz.wendyltanpcy.formulaapplication.Fragment.FormulaFragment;
import xyz.wendyltanpcy.formulaapplication.Model.Formula;
import xyz.wendyltanpcy.formulaapplication.R;
import xyz.wendyltanpcy.formulaapplication.onFloatMenuActionListener;

/**
 * Created by Wendy on 2018/5/4.
 */

public class FormulaAdapter extends RecyclerView.Adapter<FormulaAdapter.ViewHolder> implements onFloatMenuActionListener {

    private List<Formula> mFormulaList;
    private FormulaDao f_dao;
    private Context context;


    public FormulaAdapter(){

    }


    public FormulaAdapter(FormulaDao dao,List<Formula> formulas,Context con){
        mFormulaList = formulas;
        f_dao = dao;
        FormulaFragment.setListener(this);
        context = con;

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void handleAction(String action) {
        if (action.equals("delete")){
            int count = 0;
            for (Formula formula:mFormulaList){
                if (formula.isChecked()){
                    //delete this formula
                    f_dao.delete(formula);
                    mFormulaList.remove(formula);
                    notifyDataSetChanged();
                    count++;
                }
            }
            if (count==0){
                Toast.makeText(context,"no record choosen!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"record deleted!",Toast.LENGTH_SHORT).show();
            }

        }else if(action.equals("add")){
            View dialog = View.inflate(context, R.layout.dialog_layout,null);
            final EditText name = dialog.findViewById(R.id.edit_name);
            final EditText body = dialog.findViewById(R.id.edit_body);

            final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
            builder.setTitle("添加公式");
            builder.setMessage("在此处键入公式");
            builder.setView(dialog);
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Formula formula = new Formula();
                    formula.setId(mFormulaList.size()+1);
                    formula.setFormulaName(name.getText().toString());
                    formula.setFormulaBody(body.getText().toString());
                    f_dao.insertOrReplace(formula);
                    mFormulaList = f_dao.loadAll();
                    notifyDataSetChanged();
                    Toast.makeText(context,"insert formula success!",Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView analyteName;
        TextView analyteBody;
        CheckBox checkAnalyte;


        public ViewHolder(View itemView) {
            super(itemView);
            analyteName = itemView.findViewById(R.id.analyte_name);
            analyteBody = itemView.findViewById(R.id.analyte_body);
            checkAnalyte = itemView.findViewById(R.id.checkAnalyte);
        }
    }
    @Override
    public FormulaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_formula, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FormulaAdapter.ViewHolder holder, final int position) {
        holder.analyteName.setText(mFormulaList.get(position).getFormulaName());
        holder.analyteBody.setText(mFormulaList.get(position).getFormulaBody());
        holder.checkAnalyte.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mFormulaList.get(position).setChecked(b);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mFormulaList.size();
    }
}
