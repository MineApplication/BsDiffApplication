package com.body.bsdiffapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

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


    public static void executeDiff(Activity activity) {
        File oldFile = new File(activity.getExternalFilesDir("apk"), "lotb-2.33.00.apk");
        File newFile = new File(activity.getExternalFilesDir("apk"), "lotb-2.34.00.apk");
        File patchFile = new File(activity.getExternalFilesDir("apk"), "patch-33-34.apk");
        System.out.println("zengbobo executeDiff oldFile.getAbsolutePath():"+oldFile.getAbsolutePath());
        int result = BsPatchUtils.diff(oldFile.getAbsolutePath(), newFile.getAbsolutePath(),
                patchFile.getAbsolutePath());
        System.out.println("zengbobo executeDiff result:"+result);
    }

    public static void executePatch(Activity activity) {
        File newFile = new File(activity.getExternalFilesDir("apk"), "apk.apk");
        File patchFile = new File(activity.getExternalFilesDir("apk"), "patch-33-34.apk");
        File oldFile = new File(activity.getExternalFilesDir("apk"), "lotb-2.33.00.apk");
        int result = BsPatchUtils.patch(oldFile.getAbsolutePath(), newFile.getAbsolutePath(),
                patchFile.getAbsolutePath());
//        int result = BsPatchUtils.patch(activity.getApplicationInfo().sourceDir, newFile.getAbsolutePath(),
//                patchFile.getAbsolutePath());
        System.out.println("zengbobo executePatch result:" + result);
        if (result == 0) {
            install(activity, newFile);
        }
    }


    public static void install(Activity activity) {
        File newFile = new File(activity.getExternalFilesDir("apk"), "apk.apk");
//        File newFile = new File(activity.getExternalFilesDir("apk"), "app-debug.apk");
        install(activity, newFile);
    }

    private static void install(Activity activity, File file) {
        setPermission(file.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // 7.0+以上版本
            Uri apkUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        activity.startActivity(intent);
    }

    private static void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
