package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.view.View
import com.trello.navi2.component.support.NaviAppCompatActivity
import pub.devrel.easypermissions.EasyPermissions


/**
 * 基础Activity提供常用的操作
 */
abstract class BaseActivity : NaviAppCompatActivity(), EasyPermissions.PermissionCallbacks, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
