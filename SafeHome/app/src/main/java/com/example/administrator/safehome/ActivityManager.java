package com.example.administrator.safehome;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class ActivityManager {
    private static List<Activity> list = new ArrayList<Activity>();
    public static void OnCreateActivity(Activity activity) {
        for (Activity a : list) {
            if (a.equals(list)) {
                return;
            }
        }
        list.add(activity);
    }
    public static void OnDestroyActivity(Activity activity) {
        list.remove(activity);
    }
    public static List<Activity> GetActivityList() {
        return list;
    }
}
