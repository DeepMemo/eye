package com.memo.deep.openmyeye.ui.fragment.first

import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.BarUtils
import com.trello.navi2.component.support.NaviFragment
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.navi.NaviLifecycle

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

}