package com.jishi.daichao.permission.request.fragment;


import androidx.annotation.NonNull;

import com.jishi.daichao.permission.bean.Special;
import com.jishi.daichao.permission.callbcak.RequestPermissionListener;
import com.jishi.daichao.permission.callbcak.SpecialPermissionListener;
import com.jishi.daichao.permission.debug.PermissionDebug;
import com.jishi.daichao.permission.request.IPermissionActions;


/**
 * @author cd5160866
 */
public class FragmentProxy implements IPermissionActions {

    private static final String TAG = FragmentProxy.class.getSimpleName();

    private IPermissionActions fragmentImp;

    public FragmentProxy(IPermissionActions fragmentImp) {
        this.fragmentImp = fragmentImp;
    }

    @Override
    public void requestPermissions(@NonNull String[] permissions, RequestPermissionListener listener) {
        this.fragmentImp.requestPermissions(permissions, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " request:" + hashCode());
    }

    @Override
    public void requestSpecialPermission(Special permission, SpecialPermissionListener listener) {
        this.fragmentImp.requestSpecialPermission(permission, listener);
        PermissionDebug.d(TAG, fragmentImp.getClass().getSimpleName() + " requestSpecial:" + hashCode());
    }

}
