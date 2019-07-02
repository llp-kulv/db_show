package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "out_stock")
public class OutStockBean extends BaseBean {

    @Column(name = "product_id")
    private String product_id;

    @Column(name = "product_type")
    private String product_type;

    @Column(name = "out_count")
    private int out_count;

    @Column(name = "out_time")
    private String out_time;

    @Column(name = "out_info")
    private String out_info;

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

    public int getOut_count() {
        return out_count;
    }

    public void setOut_count(int out_count) {
        this.out_count = out_count;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getOut_info() {
        return out_info;
    }

    public void setOut_info(String out_info) {
        this.out_info = out_info;
    }

    @Override
    public String toString() {
        return "OutStockBean{" +
                "product_name='" + product_name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", product_type='" + product_type + '\'' +
                ", out_count=" + out_count +
                ", out_time=" + out_time +
                ", out_info='" + out_info + '\'' +
                '}';
    }
}
