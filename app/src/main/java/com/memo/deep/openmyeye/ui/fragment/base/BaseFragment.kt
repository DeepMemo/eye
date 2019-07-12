package com.memo.deep.openmyeye.ui.fragment.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.memo.deep.openmyeye.`interface`.Constant
import com.trello.navi2.component.support.NaviFragment
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.navi.NaviLifecycle
import kotlinx.android.synthetic.main.toolbar.*

open class BaseFragment : NaviFragment(), View.OnClickListener {

    fun getProvider(): LifecycleProvider<FragmentEvent> {
        return NaviLifecycle.createFragmentLifecycleProvider(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun setSatusBar() {
        BarUtils.setStatusBarAlpha(activity!!, 0)
    }

    override fun onClick(v: View) {
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
            activity?.onBackPressed()
        }

        tv_right.setOnClickListener {
            onRightTextClick()
        }

        iv_right.setOnClickListener {
            onMenuClick()
        }
    }


    open fun onRightTextClick() {
    }

    open fun onMenuClick() {
    }

    fun startAc(clazz: Class<*>, map: Map<String, String> = mapOf()) {
        val intent = Intent(activity, clazz)
        val json = Gson().toJson(map)
        intent.putExtra(Constant.DATA, json)
        startActivity(intent)
    }
}