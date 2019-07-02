package com.lingrui.db_show.util;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.lingrui.db_show.BuildConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Flog {
    private static String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    private static final String TAG = PACKAGE_NAME;
    private static final boolean isDebug = BuildConfig.DEBUG;

    // 当前日志日志文件名
    private static String LOG_FILE_NAME = PACKAGE_NAME + ".log";
    private final static long LOG_SIZE = 5 * 1024 * 1024;

    // 日志格式，形如：[2019-04-22 13:39:1][D][com.a.c]error occur
    private final static String LOG_FORMAT = "[%tF %tT][%s][%s]%s";
    private static PrintStream mLogStream;
    private static boolean mInit = false;
    private static final boolean IS_NEED_CALLER = true;
    private static final boolean IS_ALL_CALLER = false;
    private static final boolean SINGLE_TAG = false;

    // log level , must >= log_level
    private static int LOG_LEVEL = isDebug ? 2 : 32;
    private final static int LOG_LEVEL_W = 8;
    private final static int LOG_LEVEL_I = 4;
    private final static int LOG_LEVEL_D = 2;

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.i(tag, msg);
        if (!SINGLE_TAG) {
            tag = Thread.currentThread().getName() + (IS_NEED_CALLER ? "[" + getCaller() + "]" : "") + ":" + tag;
        }
        if (LOG_LEVEL <= LOG_LEVEL_I) {
            write("I", tag, msg, null);
        }
    }

    public static void d(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.d(tag, msg);
        if (!SINGLE_TAG) {
            tag = Thread.currentThread().getName() + (IS_NEED_CALLER ? "[" + getCaller() + "]" : "") + ":" + tag;
        }
        if (LOG_LEVEL <= LOG_LEVEL_D) {
            write("D", tag, msg, null);
        }
    }

    public static void e(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        // do not save error msg
        Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.w(tag, msg);

        if (!SINGLE_TAG) {
            tag = Thread.currentThread().getName() + (IS_NEED_CALLER ? "[" + getCaller() + "]" : "") + ":" + tag;
        }
        if (LOG_LEVEL <= LOG_LEVEL_W) {
            write("W", tag, msg, null);
        }
    }

    public static void v(String tag, String msg) {
        if (!isDebug) {
            return;
        }
        Log.v(tag, msg);

        if (!SINGLE_TAG) {
            tag = Thread.currentThread().getName() + (IS_NEED_CALLER ? "[" + getCaller() + "]" : "") + ":" + tag;
        }
        if (LOG_LEVEL <= LOG_LEVEL_W) {
            write("V", tag, msg, null);
        }
    }

    public static void trace(String info) {
        if (!isDebug) {
            return;
        }
        String totalInfo = info + " " + Log.getStackTraceString(new Throwable());
        e(totalInfo);

        String tempTag = TAG;
        if (!SINGLE_TAG) {
            tempTag = Thread.currentThread().getName() + (IS_NEED_CALLER ? "[" + getCaller() + "]" : "") + ":" + tempTag;
        }
        if (LOG_LEVEL <= LOG_LEVEL_W) {
            write("W", tempTag, totalInfo, null);
        }
    }

    // about save to local
    private static String getCaller() {
        final StackTraceElement[] stack = new Throwable().getStackTrace();
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < stack.length; i++) {
            final StackTraceElement ste = stack[i];

            final String className = ste.getClassName();
            if (TextUtils.isEmpty(className) || !className.contains(PACKAGE_NAME) || className.contains(TAG)) {
                continue;
            }
            result.append(className.replace(PACKAGE_NAME + ".", "")).append(".").append(ste.getMethodName()).append("(")
                    .append(ste.getLineNumber()).append(")");
            if (!IS_ALL_CALLER) {
                break;
            } else {
                result.append("]<-");
            }
        }
        return result.toString();
    }

    private static void write(String level, String tag, String msg, Throwable error) {
        if (!mInit) {
            init();
        }
        if (mLogStream == null || mLogStream.checkError()) {
            mInit = false;
            return;
        }
        final Date now = new Date();

        mLogStream.printf(LOG_FORMAT, now, now, level, tag, " : " + msg);
        mLogStream.println();

        if (error != null) {
            error.printStackTrace(mLogStream);
            mLogStream.println();
        }
    }

    private static synchronized void init() {
        if (mInit) {
            return;
        }
        try {
            final File cacheRoot = getSDCacheFile(); // 改到应用目录

            if (cacheRoot != null) {
                final File logFile = new File(cacheRoot, LOG_FILE_NAME);
                logFile.createNewFile();
                if (mLogStream != null) {
                    mLogStream.close();
                }
                mLogStream = new PrintStream(new FileOutputStream(logFile, true), true);
                mInit = true;
            }
        } catch (final Exception e) {
            e("catch error:" + e);
        }
    }

    private static boolean isSdCardAvailable() {
        final File file = Environment.getExternalStorageDirectory();
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && file != null && file.exists();
    }

    private static File getSDCacheFile() {
        // 为什么要重复代码。1.log不确定能不能取到context 2.log时机不确定
        if (isSdCardAvailable()) {
            final File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
            final File appCacheDir = new File(new File(dataDir, PACKAGE_NAME), "cache");
            if (!appCacheDir.exists()) {
                if (!appCacheDir.mkdirs()) {
                    return null;
                }
            }
            return appCacheDir;
        } else {
            return null;
        }
    }

    /**
     * 根据文件大小手动切分日志文件 默认为大于5M
     */
    public static synchronized void commitByFileSize() {
        if (!isDebug) {
            return;
        }

        try {
            final File cacheRoot = getSDCacheFile(); // 改到应用目录

            if (cacheRoot != null) {
                final File logFile = new File(cacheRoot, LOG_FILE_NAME);
                if (logFile.length() > LOG_SIZE) {
                    final File backfile = new File(cacheRoot,
                            TAG + "_" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".log");
                    logFile.renameTo(backfile);
                    logFile.delete();
                    logFile.createNewFile();
                    if (mLogStream != null) {
                        mLogStream.close();
                    }
                    mLogStream = new PrintStream(new FileOutputStream(logFile, true), true);
                }
            }
        } catch (final IOException e) {
            e("Create back log file init log stream failed:" + e);
        }
    }

    public static void setPackageAndFileName(String packageName, String fileName) {
        PACKAGE_NAME = packageName;
        LOG_FILE_NAME = fileName;
    }

    public static boolean isDebug() {
        return isDebug;
    }
}
