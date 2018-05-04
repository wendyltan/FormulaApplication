package xyz.wendyltanpcy.formulaapplication.Model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Wendy on 2018/5/3.
 */

@Entity
public class YValue {

    private double y;
    @Id
    private long id;

  

    public YValue() {
    }

    @Generated(hash = 2123523075)
    public YValue(double y, long id) {
        this.y = y;
        this.id = id;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
