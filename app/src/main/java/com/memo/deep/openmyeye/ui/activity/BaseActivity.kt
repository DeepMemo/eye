package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.gson.Gson
import com.memo.deep.openmyeye.`interface`.Constant
import com.trello.navi2.component.support.NaviAppCompatActivity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.navi.NaviLifecycle
import kotlinx.android.synthetic.main.toolbar.*
import pub.devrel.easypermissions.EasyPermissions


/**
 * 基础Activity提供常用的操作
 */
open class BaseActivity : NaviAppCompatActivity(), EasyPermissions.PermissionCallbacks, View.OnClickListener {
    fun getProvider(): LifecycleProvider<ActivityEvent> {
        return NaviLifecycle.createActivityLifecycleProvider(this)
    }

    /**
     * toolbar 的配置,Fragment默认不提供回退按钮
     *
     * @param title
     * @param menuId  0为不配置图标
     */
    protected fun setToolbar(title: Any, menuId: Int = 0, rightTitle: Int = 0, isCanBack: Boolean = true) {
        if (menuId != 0) {
            iv_right.setImageResource(menuId)
            iv_right.visibility = View.VISIBLE
        }
        if (rightTitle != 0) {
            tv_right.setText(rightTitle)
            tv_right.visibility = View.VISIBLE
        }

        // 如果不能回退，文字要重新设置margin
        if (!isCanBack) {
            iv_back.visibility = View.GONE
        }

        if (title is Int) {
            tv_toolbar_title.setText(title)
        } else if (title is String) {
            tv_toolbar_title.text = title
        }

        iv_back.setOnClickListener {
            onBackPressed()
        }

        tv_right.setOnClickListener {
            onRightTextClick()
        }

        iv_right.setOnClickListener {
            onMenuClick()
        }
    }


    private fun onRightTextClick() {
    }

    private fun onMenuClick() {
    }


    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
    }

    override fun onClick(v: View) {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    fun startAc(clazz: Class<*>, map: Map<String, String> = mapOf()) {
        val intent = Intent(this, clazz)
        val json = Gson().toJson(map)
        intent.putExtra(Constant.DATA, json)
        startActivity(intent)
    }

}
