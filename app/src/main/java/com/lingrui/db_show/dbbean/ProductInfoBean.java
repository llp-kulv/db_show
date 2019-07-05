package com.lingrui.db_show.dbbean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "surplus_info")
public class ProductInfoBean extends BaseBean {
    @Column(name = "product_id")
    private String product_id;

    @Column(name = "product_type")
    private String product_type;

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

    @Override
    public String toString() {
        return "ProductInfoBean{" +
                "product_id='" + product_id + '\'' +
                ", product_type='" + product_type + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price='" + product_price + '\'' +
                ", product_total_price='" + product_total_price + '\'' +
                ", product_count=" + product_count +
                ", product_time='" + product_time + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
