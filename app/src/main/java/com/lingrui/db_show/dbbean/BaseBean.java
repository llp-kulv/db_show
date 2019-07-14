package com.lingrui.db_show.dbbean;

import com.lingrui.panellistlibrary.defaultcontent.IDBData;

import org.xutils.db.annotation.Column;

import java.io.Serializable;

public class BaseBean implements Serializable, IDBData {
    @Column(name = "_id", isId = true)
    public int _id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "product_price")
    public double product_price;

    @Column(name = "product_total_price")
    public double product_total_price;

    @Column(name = "product_count")
    public int product_count;

    @Column(name = "product_time")
    public String product_time;

    @Column(name = "info")
    public String info;

    @Column(name = "other_0")
    public String other_0;

    @Column(name = "other_1")
    public String other_1;

    @Column(name = "other_2")
    public String other_2;

    @Column(name = "other_3")
    public String other_3;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_price() {

        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
        setProduct_total_price(product_price * product_count);
    }

    public double getProduct_total_price() {
        return product_total_price;
    }

    public void setProduct_total_price(double product_total_price) {
        this.product_total_price = product_total_price;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
        setProduct_total_price(product_price * product_count);
    }

    public String getProduct_time() {
        return product_time;
    }

    public void setProduct_time(String product_time) {
        this.product_time = product_time;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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
