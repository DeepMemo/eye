package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.view.View
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.navi.NaviLifecycle
import pub.devrel.easypermissions.EasyPermissions


/**
 * 基础Activity提供常用的操作
 */
open class BaseActivity : NaviAppCompatActivity(), EasyPermissions.PermissionCallbacks, View.OnClickListener {
    fun getProvider(): LifecycleProvider<ActivityEvent> {
        return NaviLifecycle.createActivityLifecycleProvider(this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onClick(v: View?) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
