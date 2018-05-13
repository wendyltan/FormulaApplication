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
    private String formulaUnit;



    @Id
    private long id;

    public Formula(String formulaBody, String formulaName, boolean isChecked,
            long id) {
        this.formulaBody = formulaBody;
        this.formulaName = formulaName;
        this.isChecked = isChecked;
        this.id = id;
    }


    public Formula() {
    }


    @Generated(hash = 1249028562)
    public Formula(String formulaBody, String formulaName, boolean isChecked,
            String formulaUnit, long id) {
        this.formulaBody = formulaBody;
        this.formulaName = formulaName;
        this.isChecked = isChecked;
        this.formulaUnit = formulaUnit;
        this.id = id;
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


    public String getFormulaUnit() {
        return this.formulaUnit;
    }


    public void setFormulaUnit(String formulaUnit) {
        this.formulaUnit = formulaUnit;
    }
}
