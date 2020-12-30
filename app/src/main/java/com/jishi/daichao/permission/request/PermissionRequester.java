package com.jishi.daichao.permission.request;
import android.annotation.TargetApi;
import android.app.Activity;

import com.jishi.daichao.permission.bean.Permission;
import com.jishi.daichao.permission.bean.Special;
import com.jishi.daichao.permission.callbcak.RequestPermissionListener;
import com.jishi.daichao.permission.callbcak.SpecialPermissionListener;
import com.jishi.daichao.permission.request.fragment.FragmentProxy;
import static android.os.Build.VERSION_CODES.M;

/**
 * 运行时权限请求者
 *
 * @author cd5160866
 */
public class PermissionRequester {

    private FragmentProxy permissionFragment;

    private String[] permissions;

    private Special permissionSpecial;

    public PermissionRequester(Activity activity) {
        this.permissionFragment = new FragmentProxy(PermissionFragmentFactory.create(activity));
    }

    /**
     * 权限名称
     *
     * @param permissionNames 如 Manifest.permission.CAMERA
     */
    public PermissionRequester withPermission(String... permissionNames) {
        this.permissions = permissionNames;
        return this;
    }

    /**
     * 包含权限的实体类
     */
    public PermissionRequester withPermission(Permission... permissions) {
        this.permissions = new String[permissions.length];
        for (int i = 0, size = permissions.length; i < size; i++) {
            this.permissions[i] = permissions[i].permissionName;
        }
        return this;
    }

    /**
     * 特殊权限
     *
     * @param permissionSpecial 特殊权限
     *
     */
    public PermissionRequester withPermission(Special permissionSpecial) {
        this.permissionSpecial = permissionSpecial;
        return this;
    }

    /**
     * 请求运行时权限
     */
    @TargetApi(M)
    public void request(RequestPermissionListener listener) {
        if (permissionFragment == null || permissions == null) {
            throw new IllegalArgumentException("fragment or params permission is null");
        }
        permissionFragment.requestPermissions(permissions, listener);
    }

    /**
     * 请求特殊权限
     */
    public void request(SpecialPermissionListener listener) {
        if (permissionFragment == null || permissionSpecial == null) {
            throw new IllegalArgumentException("fragment or params special permission is null");
        }
        permissionFragment.requestSpecialPermission(permissionSpecial, listener);
    }
}
