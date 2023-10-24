package com.body.bsdiffapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * $
 *
 * @date: 2023/10/24 11:27
 * @author: zengbobo
 */
public class BsPatchUtils {

    static{
        System.loadLibrary("bsdiffapplication");   //此处的library的名字未您在CMakeLists.text中指定的名称
    }

    public static native String stringFromJNI();

    //组装差分包
    public static native int patch(String oldApk,String newApk,String patchFile);

    //获取差分包
    public static native int diff(String oldApk ,String newApk,String dissFile);



    public void patch(Activity activity) {
        File newFile = new File(activity.getExternalFilesDir("apk"), "app.apk");
        File patchFile = new File(activity.getExternalFilesDir("apk"), "patch.apk");
        int result = BsPatchUtils.patch(activity.getApplicationInfo().sourceDir, newFile.getAbsolutePath(),
                patchFile.getAbsolutePath());
        if (result == 0) {
            install(activity,newFile);
        }
    }

    private void install(Activity activity,File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }
}
