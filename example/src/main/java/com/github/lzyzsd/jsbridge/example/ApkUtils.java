package com.github.lzyzsd.jsbridge.example;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

public class ApkUtils {

    private static final String TAG = "ApkUtils";

    public static boolean checkAppInstalled(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        PackageManager packageManager = context.getPackageManager();
        // 获取已安装的app信息
        List<PackageInfo> pkgInfos = packageManager.getInstalledPackages(0);
        if (pkgInfos != null) {
            for (int i = 0; i < pkgInfos.size(); i++) {
                String pkg = pkgInfos.get(i).packageName;
                if (pkgName.equals(pkg)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkApkInstalled(Context context, String pkgName) {
        if (TextUtils.isEmpty(pkgName)) {
            return false;
        }
        try {
            context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception x) {
            return false;
        }
        return true;
    }

    public static boolean startApp(Context context, String pkgName, String activityName) {
        ComponentName componentName = new ComponentName(pkgName, activityName);
        Bundle bundle = new Bundle();
        Intent intent = new Intent();
        intent.putExtras(bundle);
        intent.setComponent(componentName);
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "startApp failed: " + ex.getMessage());
        }
        return false;
    }

    public static boolean joinYmsMeeting(Context context)
    {
        Uri.Builder builder = new Uri.Builder()
       .scheme("ymeetingonpremise")
       .authority("yms.yealinkops.com")
       .path("/app/joinmeeting");
//       .appendQueryParameter("number","88866656789")
//       .appendQueryParameter("password","123456")
//       .appendQueryParameter("isneedlogin","true")
//       .appendQueryParameter("authcode","asasfaf")
//       .appendQueryParameter("isskippreview","true")
//       .appendQueryParameter("camera","open")
//       .appendQueryParameter("mic","close");
        Intent intent = new Intent();
        intent.setData(builder.build());
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception ex) {
            Log.e(TAG, "joinYmsMeeting failed: " + ex.getMessage());
        }
        return false;
        //"ymeetingonpremise://yms.yealinkops.com/app/joinmeeting?number=88866656789&password=123456"
    }
}
