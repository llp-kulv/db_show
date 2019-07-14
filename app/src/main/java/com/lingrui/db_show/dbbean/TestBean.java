package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * create by liulp
 * on 2019-07-14 23:15
 */
@Table(name = "test_")
public class TestBean {

    @Column(name = "_id", isId = true)
    public int _id;

    @Column(name = "product_name")
    public String product_name;

    @Column(name = "product_price")
    public double product_price;

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
    }

    @Override
    public String toString() {
        return "TestBean{" +
                "product_name='" + product_name + '\'' +
                ", product_price=" + product_price +
                '}';
    }
}
