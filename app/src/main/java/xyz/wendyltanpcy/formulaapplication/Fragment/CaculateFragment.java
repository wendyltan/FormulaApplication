package xyz.wendyltanpcy.formulaapplication.Fragment;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.speedystone.greendaodemo.db.DaoSession;
import com.speedystone.greendaodemo.db.FormulaDao;
import com.speedystone.greendaodemo.db.YValueDao;

import java.util.ArrayList;
import java.util.List;

import xyz.wendyltanpcy.formulaapplication.Adapter.YValueAdapter;
import xyz.wendyltanpcy.formulaapplication.Model.Formula;
import xyz.wendyltanpcy.formulaapplication.Model.YValue;
import xyz.wendyltanpcy.formulaapplication.MyApp;
import xyz.wendyltanpcy.formulaapplication.R;
import xyz.wendyltanpcy.formulaapplication.onFloatMenuActionListener;

public class CaculateFragment extends Fragment {

    private ArrayAdapter<String> adapter;
    private List<Formula> mFormulaList;
    private List<YValue> mYValues;
    private MyApp mMyApp;
    private FormulaDao mFormulaDao;
    private YValueDao mYValueDao;
    private List<String> formulaNames = new ArrayList<>();
    private List<String> formulaBodys = new ArrayList<>();
    private List<String> formulaUnits = new ArrayList<>();
    private int formulaPos=0;
    private boolean isLetter=false;
    private double result;
    private static onFloatMenuActionListener mOnFloatMenuActionListener;

    @SuppressLint("ValidFragment")
    public CaculateFragment(Application app) {
        // Required empty public constructor
        mMyApp = (MyApp) app;
    }

    public CaculateFragment(){


    }

    public static void setListener(onFloatMenuActionListener listener) {
        mOnFloatMenuActionListener = listener;
    }

    public void action(String string) {

        if (mOnFloatMenuActionListener != null) {
            mOnFloatMenuActionListener.handleAction(string);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        DaoSession daoSession = mMyApp.getDaoSession();
        mFormulaDao = daoSession.getFormulaDao();
        mYValueDao = daoSession.getYValueDao();
        mFormulaList = mFormulaDao.loadAll();
        mYValues = mYValueDao.loadAll();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_caculate, container, false);
        final EditText x_enter = v.findViewById(R.id.x_enter);
        Spinner analyte_selector = v.findViewById(R.id.analyte_selector);
        final TextView y_result = v.findViewById(R.id.y_result);
        final TextView x_unit = v.findViewById(R.id.unit_text);

        Button caculate = v.findViewById(R.id.caculate_btn);
        Button save = v.findViewById(R.id.save_btn);
        caculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = caculate(formulaPos,x_enter,y_result);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (x_enter.getText()!=null&&!y_result.getText().equals("")){
                    YValue yValue = new YValue();
                    yValue.setId(mYValues.size());
                    yValue.setY(result);
                    mYValueDao.insert(yValue);
                    mYValues.add(yValue);
                    action("save");

                    Toast.makeText(getContext(),"y save success!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"You haven't caculate yet!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        analyte_selector.setPrompt("Please choose formula");
        initDatas();

        adapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,formulaNames);
        analyte_selector.setAdapter(adapter);
        analyte_selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                formulaPos = position;
                x_unit.setText(formulaUnits.get(formulaPos));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final YValueAdapter y_adapter = new YValueAdapter(mYValues,getContext());
        RecyclerView recyclerView = v.findViewById(R.id.y_resycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(y_adapter);



        return v;
    }

    /**
     * if formula is valid,caculate the result,else do nothing
     * @param position
     * @param x
     * @param y
     */
    private double caculate(int position,EditText x,TextView y) {
        if (mFormulaList.isEmpty()){
            Toast.makeText(getContext(),"No formula yet!",Toast.LENGTH_SHORT).show();

        }
        String formulaApply = formulaBodys.get(position);
        for(int i=0 ; i<x.getText().length() ; i++){ //循环遍历字符串
            if(Character.isLetter(x.getText().charAt(i))){   //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        if (!isLetter&&x.getText().length()!=0){
            formulaApply = formulaApply.replace("x",x.getText()).replace("y=","");
            Toast.makeText(getContext(),formulaApply,Toast.LENGTH_SHORT).show();
            double result = eval(formulaApply);
            y.setText(String.format("%.2f",result));
            return Double.valueOf(String.format("%.2f",result));


        }else{
            Toast.makeText(getContext(),"You didn't enter a valid number for x!",Toast.LENGTH_SHORT).show();
            x.getText().clear();
        }
        return -1;
    }

    private void initDatas() {
        for (Formula formula : mFormulaList){
            formulaNames.add(formula.getFormulaName());
            formulaBodys.add(formula.getFormulaBody());
            formulaUnits.add(formula.getFormulaUnit());
        }
    }

    /**
     * transform formula str into caculable formula
     * @param str
     * @return
     */
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if(func.equals("lg")) x=Math.log10(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

}
