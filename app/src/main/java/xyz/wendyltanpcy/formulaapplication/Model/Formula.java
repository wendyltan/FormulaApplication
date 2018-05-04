package xyz.wendyltanpcy.formulaapplication.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Wendy on 2018/5/3.
 */

@Entity
public class Formula {

    private String formulaBody;
    private String formulaName;
    private boolean isChecked;



    @Id
    private long id;

    @Generated(hash = 930721883)
    public Formula(String formulaBody, String formulaName, boolean isChecked,
            long id) {
        this.formulaBody = formulaBody;
        this.formulaName = formulaName;
        this.isChecked = isChecked;
        this.id = id;
    }

    @Generated(hash = 775514140)
    public Formula() {
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getFormulaBody() {
        return formulaBody;
    }

    public void setFormulaBody(String formulaBody) {
        this.formulaBody = formulaBody;
    }

    public String getFormulaName() {
        return formulaName;
    }

    public void setFormulaName(String formulaName) {
        this.formulaName = formulaName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsChecked() {
        return this.isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
