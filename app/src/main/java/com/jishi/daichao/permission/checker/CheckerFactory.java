package com.jishi.daichao.permission.checker;

import android.content.Context;

import com.jishi.daichao.permission.PermissionTools;
import com.jishi.daichao.permission.bean.Special;


/**
 * @author cd5160866
 */
public class CheckerFactory {

    public static PermissionChecker create(Context context, Special permission) {
        return new SpecialChecker(context, permission);
    }

    public static PermissionChecker create(Context context, String permission) {
        if (PermissionTools.isOldPermissionSystem(context)) {
            return new AppOpsChecker(context, permission);
        } else {
            return new RunTimePermissionChecker(context, permission);
        }
    }
}
