package xyz.wendyltanpcy.formulaapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import xyz.wendyltanpcy.formulaapplication.Fragment.AboutFragment;
import xyz.wendyltanpcy.formulaapplication.Fragment.CaculateFragment;
import xyz.wendyltanpcy.formulaapplication.Fragment.FormulaFragment;

public class MainActivity extends AppCompatActivity {

    private MyApp myApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myApp = (MyApp) getApplication();
        CaculateFragment fragment = new CaculateFragment(myApp);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,fragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about_menu:
                AboutFragment a_fragment = new AboutFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,a_fragment).commit();
                break;
            case R.id.caculate_menu:
                CaculateFragment c_fragment = new CaculateFragment(myApp);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,c_fragment).commit();
                break;
            case R.id.formula_db_menu:
                FormulaFragment f_fragment = new FormulaFragment(myApp);
                getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,f_fragment).commit();
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
