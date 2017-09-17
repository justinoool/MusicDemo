package com.cainiao5.cainiaomusic.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.cainiao5.cainiaomusic.BaseActivity;
import com.cainiao5.cainiaomusic.permission.PermissionBuilder;


/**
 * @desciption: 为 6.0 添加权限管理
 */

public abstract class PermissionActivity extends BaseActivity {

    private SparseArray<PermissionBuilder> builderMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builderMap = new SparseArray<>();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionBuilder permissionBuilder = builderMap.get(requestCode);
        permissionBuilder.onRequestPermissionsResult(permissions, grantResults);
    }

}
