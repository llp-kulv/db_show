package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "surplus_info")
public class ProductInfoBean extends BaseBean {
    @Column(name = "product_id")
    private String product_id;

    @Column(name = "product_type")
    private String product_type;

    @Column(name = "current_count")
    private int current_count;

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public int getCurrent_count() {
        return current_count;
    }

    public void setCurrent_count(int current_count) {
        this.current_count = current_count;
    }

    @Override
    public String toString() {
        return "ProductInfoBean{" +
                "product_name='" + product_name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_type='" + product_type + '\'' +
                ", current_count=" + current_count +
                '}';
    }
}
