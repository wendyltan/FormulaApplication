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
            final EditText unit = dialog.findViewById(R.id.edit_unit);

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
                    formula.setFormulaUnit(unit.getText().toString());
                    f_dao.insertOrReplace(formula);
                    mFormulaList = f_dao.loadAll();
                    notifyDataSetChanged();
                    Toast.makeText(context,"insert formula success!",Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();

        }else if(action.equals("edit")){
            int count = 0;
            int pos= 0;
            for (Formula formula:mFormulaList){
                if (formula.isChecked()){
                    //delete this formula
                    count++;
                    pos = mFormulaList.indexOf(formula);
                }
            }
            if (count>1){
                Toast.makeText(context,"Please choose one formula to edit!",Toast.LENGTH_SHORT).show();
            }
            else{
                View dialog = View.inflate(context, R.layout.dialog_layout,null);
                final EditText name = dialog.findViewById(R.id.edit_name);
                final EditText body = dialog.findViewById(R.id.edit_body);
                final EditText unit = dialog.findViewById(R.id.edit_unit);
                final Formula formula = mFormulaList.get(pos);
                name.setText(formula.getFormulaName());
                body.setText(formula.getFormulaBody());
                unit.setText(formula.getFormulaUnit());
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("编辑公式");
                builder.setMessage("对公式编辑");
                builder.setView(dialog);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        formula.setFormulaBody(body.getText().toString());
                        formula.setFormulaName(name.getText().toString());
                        formula.setFormulaUnit(unit.getText().toString());
                        f_dao.insertOrReplace(formula);
                        mFormulaList = f_dao.loadAll();
                        notifyDataSetChanged();
                        Toast.makeText(context,"edit formula success!",Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView analyteName;
        TextView analyteBody;
        TextView analyteUnit;
        CheckBox checkAnalyte;


        public ViewHolder(View itemView) {
            super(itemView);
            analyteName = itemView.findViewById(R.id.analyte_name);
            analyteBody = itemView.findViewById(R.id.analyte_body);
            checkAnalyte = itemView.findViewById(R.id.checkAnalyte);
            analyteUnit = itemView.findViewById(R.id.analyte_unit);
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
        holder.analyteUnit.setText(mFormulaList.get(position).getFormulaUnit());
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
