package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "in_stock")
public class InStockBean extends BaseBean {
    @Column(name = "product_id")
    private String product_id;

    @Column(name = "product_type")
    private String product_type;

    @Column(name = "in_count")
    private int in_count;

    @Column(name = "in_time")
    private String in_time;

    @Column(name = "in_info")
    private String in_info;

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

    public int getIn_count() {
        return in_count;
    }

    public void setIn_count(int in_count) {
        this.in_count = in_count;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getIn_info() {
        return in_info;
    }

    public void setIn_info(String in_info) {
        this.in_info = in_info;
    }

    @Override
    public String toString() {
        return "InStockBean{" +
                "product_name='" + product_name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_type='" + product_type + '\'' +
                ", in_count=" + in_count +
                ", in_time='" + in_time + '\'' +
                ", in_info='" + in_info + '\'' +
                '}';
    }
}
