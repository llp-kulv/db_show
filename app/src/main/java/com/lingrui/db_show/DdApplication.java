package com.lingrui.db_show;

import android.app.Application;

import com.lingrui.db_show.manager.DbInfo;
import com.lingrui.db_show.manager.ProductManager;
import com.lingrui.db_show.util.Flog;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

public class DdApplication extends Application {
    private static final String TAG = "DdApplication";
    private DbManager mDbManager;
    private ProductManager mProductManager;
    private static DdApplication mDdApplication;

    public static DdApplication getInstance() {
        return mDdApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDdApplication = this;

        initXUtils();
        mProductManager = new ProductManager(this);
    }

    private void initXUtils() {
        x.Ext.init(this);
        x.Ext.setDebug(Flog.isDebug());

        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName(DbInfo.DB_NAME)//设置数据库名称
                // 不设置dbDir时, 默认存储在app的私有目录.
                // .setDbDir(new File("sdcard/SitSmice/iDEvent/DownLoad/dbPath")) // 数据库存储路径
                .setDbVersion(DbInfo.DB_VERSION)//设置数据库版本
                .setTableCreateListener(new DbManager.TableCreateListener() {

                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Flog.d(TAG, "onTableCreated");
                    }
                })

                //设置数据库更新的监听
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
//                        try {
//                            db.addColumn(Sign.class,"test");
//                        } catch (DbException e) {
//                            Flog.e(TAG, "exception :" +e);
//                        }
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb()
                    }
                })
                //设置数据库打开的监听
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        //开启数据库支持多线程操作，提升性能
                        db.getDatabase().enableWriteAheadLogging();
                    }
                });

        //db还有其他的一些构造方法，比如含有更新表版本的监听器的
        mDbManager = x.getDb(daoConfig);//获取数据库单例
    }

    public DbManager getDbManager() {
        return mDbManager;
    }

    public ProductManager getProductManager() {
        return mProductManager;
    }
}
