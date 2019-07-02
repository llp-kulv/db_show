package com.lingrui.db_show.manager;

import com.lingrui.db_show.DdApplication;
import com.lingrui.db_show.dbbean.InStockBean;
import com.lingrui.db_show.dbbean.OutStockBean;
import com.lingrui.db_show.dbbean.ProductInfoBean;
import com.lingrui.db_show.util.CollectionUtil;
import com.lingrui.db_show.util.Flog;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

public class DatabaseManager {
    private static final String TAG = "DatabaseManager";

    private static volatile DatabaseManager instance = null;

    private DatabaseManager() {

    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public synchronized void addInStock(List<InStockBean> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        try {
            DdApplication.getInstance().getDbManager().save(list);
        } catch (DbException e) {
            Flog.d(TAG, "save InStockBean fail exception:" + e);
        }
    }

    public synchronized void addOutStock(List<OutStockBean> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        try {
            DdApplication.getInstance().getDbManager().save(list);
        } catch (DbException e) {
            Flog.d(TAG, "save InStockBean fail exception:" + e);
        }
    }

    public synchronized void addInStock(InStockBean inStockBean) {
        if (inStockBean == null) {
            return;
        }

        Flog.d(TAG, "save InStockBean InStockBean:" + inStockBean);

        try {
            DdApplication.getInstance().getDbManager().save(inStockBean);

            ProductInfoBean productInfoBean = new ProductInfoBean();
            productInfoBean.setProduct_name(inStockBean.getProduct_name());
            productInfoBean.setCurrent_count(inStockBean.getIn_count());
            DdApplication.getInstance().getDbManager().save(productInfoBean);
        } catch (DbException e) {
            Flog.d(TAG, "save InStockBean one fail exception:" + e);
        }
    }

    public synchronized void addOutStock(OutStockBean outStockBean) {
        if (outStockBean == null) {
            return;
        }

        DbManager dbManager = DdApplication.getInstance().getDbManager();
        try {
            dbManager.save(outStockBean);

            WhereBuilder builder = WhereBuilder.b();
            builder.and("product_name", "=", outStockBean.getProduct_name());
            ProductInfoBean infoBean = dbManager.selector(ProductInfoBean.class).where(builder).findFirst();

            infoBean.setCurrent_count(infoBean.getCurrent_count() - outStockBean.getOut_count());
            Flog.d(TAG, "总量" + infoBean.getCurrent_count() + " 领取:" + outStockBean.getOut_count() + " infoBean:" + infoBean);
            dbManager.update(infoBean);
        } catch (DbException e) {
            Flog.d(TAG, "save addOutStock one fail exception:" + e);
        }
    }

    public synchronized List<ProductInfoBean> getSurplusInfo() {
        DbManager dbManager = DdApplication.getInstance().getDbManager();
        try {
            List<ProductInfoBean> productInfoBeans = dbManager.findAll(ProductInfoBean.class);
            return productInfoBeans;
        } catch (DbException e) {
            Flog.d(TAG, "getSurplusInfo fail exception:" + e);
        }
        return null;
    }

    public synchronized ProductInfoBean getSurplusInfo(WhereBuilder whereBuilder) {
        DbManager dbManager = DdApplication.getInstance().getDbManager();
        try {
            ProductInfoBean productInfoBeans = dbManager.selector(ProductInfoBean.class).where(whereBuilder).findFirst();
            return productInfoBeans;
        } catch (DbException e) {
            Flog.d(TAG, "getSurplusInfo fail exception:" + e);
        }
        return null;
    }

    public List<OutStockBean> getConsumptionInfo() {
        DbManager dbManager = DdApplication.getInstance().getDbManager();
        try {
            List<OutStockBean> list = dbManager.findAll(OutStockBean.class);
            return list;
        } catch (DbException e) {
            Flog.d(TAG, "getConsumptionInfo fail exception:" + e);
        }
        return null;
    }

    public List<InStockBean> getInStockInfo() {
        DbManager dbManager = DdApplication.getInstance().getDbManager();
        try {
            List<InStockBean> list = dbManager.findAll(InStockBean.class);
            return list;
        } catch (DbException e) {
            Flog.d(TAG, "getInStockInfo fail exception:" + e);
        }
        return null;
    }
}
