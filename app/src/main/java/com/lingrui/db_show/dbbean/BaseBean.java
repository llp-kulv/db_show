package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

public class BaseBean implements Serializable {
    @Column(name = "_id", isId = true, autoGen = true)
    public int _id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "other_0" )
    public String other_0;

    @Column(name = "other_1" )
    public String other_1;

    @Column(name = "other_2" )
    public String other_2;

    @Column(name = "other_3" )
    public String other_3;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getOther_0() {
        return other_0;
    }

    public void setOther_0(String other_0) {
        this.other_0 = other_0;
    }

    public String getOther_1() {
        return other_1;
    }

    public void setOther_1(String other_1) {
        this.other_1 = other_1;
    }

    public String getOther_2() {
        return other_2;
    }

    public void setOther_2(String other_2) {
        this.other_2 = other_2;
    }

    public String getOther_3() {
        return other_3;
    }

    public void setOther_3(String other_3) {
        this.other_3 = other_3;
    }
}
