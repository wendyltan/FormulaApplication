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

import com.ddz.floatingactionbutton.FloatingActionButton;
import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.speedystone.greendaodemo.db.DaoSession;
import com.speedystone.greendaodemo.db.FormulaDao;

import java.util.List;

import xyz.wendyltanpcy.formulaapplication.Adapter.FormulaAdapter;
import xyz.wendyltanpcy.formulaapplication.Model.Formula;
import xyz.wendyltanpcy.formulaapplication.MyApp;
import xyz.wendyltanpcy.formulaapplication.R;
import xyz.wendyltanpcy.formulaapplication.onFloatMenuActionListener;


public class FormulaFragment extends Fragment{

    private MyApp app;
    private FloatingActionMenu fl_menu;
    private FloatingActionButton add_button;
    private FloatingActionButton delete_button;
    private FormulaDao mFormulaDao;
    private List<Formula> formulaList;
    private RecyclerView FormulaRecyclerView;
    private FormulaAdapter adapter;
    private static onFloatMenuActionListener mOnFloatMenuActionListener;


    @SuppressLint("ValidFragment")
    public FormulaFragment(Application application) {
        // Required empty public constructor
        app = (MyApp) application;
    }

    public FormulaFragment(){

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
        DaoSession daoSession = app.getDaoSession();
        mFormulaDao = daoSession.getFormulaDao();
        formulaList = mFormulaDao.loadAll();
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_formula, container, false);
        fl_menu = (FloatingActionMenu) v.findViewById(R.id.fab2);
        adapter = new FormulaAdapter(mFormulaDao,formulaList,getContext());
        FormulaRecyclerView =  v.findViewById(R.id.formula_recycle);
        FormulaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FormulaRecyclerView.setAdapter(adapter);
        add_button = v.findViewById(R.id.add);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fl_menu.collapse();
                action("add");
            }
        });
        delete_button = v.findViewById(R.id.delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fl_menu.collapse();
                action("delete");
            }
        });
        return v;
    }

}
