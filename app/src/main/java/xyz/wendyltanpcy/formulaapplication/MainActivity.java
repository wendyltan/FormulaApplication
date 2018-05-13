package xyz.wendyltanpcy.formulaapplication;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.speedystone.greendaodemo.db.DaoSession;
import com.speedystone.greendaodemo.db.YValueDao;

import xyz.wendyltanpcy.formulaapplication.Fragment.AboutFragment;
import xyz.wendyltanpcy.formulaapplication.Fragment.CaculateFragment;
import xyz.wendyltanpcy.formulaapplication.Fragment.FormulaFragment;

public class MainActivity extends AppCompatActivity {

    private MyApp myApp;
    private TextView mainText;
    private boolean isCalculate;
    private FrameLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (MyApp) getApplication();
        mainText = findViewById(R.id.main_text);
        mLayout = findViewById(R.id.main_layout);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mainText.setVisibility(View.GONE);
        switch (item.getItemId()){

            case R.id.about_menu:
                AboutFragment a_fragment = new AboutFragment();
                isCalculate=false;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,a_fragment).commit();
                break;
            case R.id.caculate_menu:
                CaculateFragment c_fragment = new CaculateFragment(myApp);
                isCalculate=true;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,c_fragment).commit();
                break;
            case R.id.formula_db_menu:
                FormulaFragment f_fragment = new FormulaFragment(myApp);
                isCalculate=false;
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,f_fragment).commit();
                break;
            case R.id.remove_all_y:
                if (isCalculate){
                    //remove all y
                    DaoSession daoSession = myApp.getDaoSession();
                    YValueDao mYValueDao = daoSession.getYValueDao();
                    mYValueDao.deleteAll();
                    Snackbar.make(mLayout,"All y deleted!",Snackbar.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this,"You are not in the calculate session!",Toast.LENGTH_SHORT).show();
                }

                break;

        }
        return super.onOptionsItemSelected(item);
    }



    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
