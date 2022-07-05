package com.github.lzyzsd.jsbridge.example.logger;

import android.util.Log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * AppLogger 日志类
 */
public class AppLogger {

    private static final String TAG = "AppLogger";

    private static final Logger LOGGER = LoggerFactory.getLogger(AppLogger.class);

    // use Log.v
    public static final int VERBOSE = 2;

    // use Log.d
    public static final int DEBUG = 3;

    // use Log.i
    public static final int INFO = 4;

    // use Log.w
    public static final int WARN = 5;

    // use Log.e
    public static final int ERROR = 6;

    // not used now
    public static final int ASSERT = 7;

    // 当前日志级别 todo: 发布时注意修改!!!
    public static final int CURRENT_LEVEL = DEBUG;

    // 是否写logback todo: 发布时注意修改!!!
    public static final boolean LOG_BACK = true;

    // 是否写logcat todo: 发布时注意修改!!!
    public static final boolean LOG_CAT = false;

    // 判断某个级别日志是否可用
    public static boolean isLevelEnabled(int level) {
        return level >= CURRENT_LEVEL;
    }

    // *** fatal ***
    public static void fatal(String tag, String msg, Throwable tr) {
        if (LOG_CAT) {
            Log.wtf(tag, msg, tr);
        }
        if (LOG_BACK) {
            LOGGER.error(tag + ": " + msg, tr);
        }
    }

    public static void fatal(String tag, String msg) {
        if (LOG_CAT) {
            Log.wtf(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.error(tag + ": " + msg);
        }
    }

    public static void fatal(String msg) {
        if (LOG_CAT) {
            Log.wtf(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.error(msg);
        }
    }

    // *** error ***
    public static void e(String tag, String msg, Throwable tr) {
        if (LOG_CAT) {
            Log.e(tag, msg, tr);
        }
        if (LOG_BACK) {
            LOGGER.error(tag + ": " + msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_CAT) {
            Log.e(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.error(tag + ": " + msg);
        }
    }

    public static void e(String msg) {
        if (LOG_CAT) {
            Log.e(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.error(msg);
        }
    }

    // *** warn ***
    public static void w(String tag, String msg, Throwable tr) {
        if (LOG_CAT) {
            Log.w(tag, msg, tr);
        }
        if (LOG_BACK) {
            LOGGER.warn(tag + ": " + msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_CAT) {
            Log.w(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.warn(tag + ": " + msg);
        }
    }

    public static void w(String msg) {
        if (LOG_CAT) {
            Log.w(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.warn(msg);
        }
    }

    // *** info ***
    public static void i(String tag, String msg) {
        if (LOG_CAT) {
            Log.i(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.info(tag + ": " + msg);
        }
    }

    public static void i(String msg) {
        if (LOG_CAT) {
            Log.i(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.info(msg);
        }
    }

    public static void info(String msg) {
        if (LOG_CAT) {
            Log.i(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.info(msg);
        }
    }

    public static void info(String fmt, Object... args) {
        //String msg = String.format(fmt, args);
        FormattingTuple tuple = MessageFormatter.arrayFormat(fmt, args);
        if (LOG_CAT) {
            Log.i(TAG, tuple.getMessage());
        }
        if (LOG_BACK) {
            LOGGER.info(tuple.getMessage());
        }
    }

    // *** debug ***
    public static void d(String tag, String msg) {
        if (LOG_CAT) {
            Log.d(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.debug(tag + ": " + msg);
        }
    }

    public static void d(String msg) {
        if (LOG_CAT) {
            Log.d(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.debug(msg);
        }
    }

    // *** trace / verbose ***
    public static void v(String tag, String msg) {
        if (LOG_CAT) {
            Log.v(tag, msg);
        }
        if (LOG_BACK) {
            LOGGER.trace(tag + ": " + msg);
        }
    }

    public static void v(String msg) {
        if (LOG_CAT) {
            Log.v(TAG, msg);
        }
        if (LOG_BACK) {
            LOGGER.trace(msg);
        }
    }
}
