package com.example.androidfilesystem;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;

public class Utils {
    private final static String CACHE_DIR_PATH_PART = "/Android";

    @NonNull
    public static LinkedHashMap<String, String> getExternalStoragePaths(@NonNull Context context) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        ArrayList<File> paths = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            paths.addAll(Arrays.asList(context.getExternalCacheDirs()));
        } else {
            paths.add(context.getExternalCacheDir());
        }
        for (File dir : paths) {
            String path = dir.getPath().split(CACHE_DIR_PATH_PART)[0];
            result.put(path, new File(path).getName());
        }
        return result;
    }
    public static int attrToResId(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        return a.getResourceId(0, 0);
    }
}
