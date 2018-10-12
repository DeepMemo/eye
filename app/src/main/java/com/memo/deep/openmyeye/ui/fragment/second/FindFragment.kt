package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.memo.deep.openmyeye.bean.find.BaseMuti
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.memo.deep.openmyeye.ui.mvp.presenter.FindPresenter
import com.trello.rxlifecycle2.navi.NaviLifecycle
import kotlinx.android.synthetic.main.fragment_second.view.*

class FindFragment : SecondFragment<BaseMuti>(), IFindContract.View {
    override fun onNext(t: List<Any>) {
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        inflate.rv.adapter = FindAdapter(this, t as List<BaseMuti>)
    }

    private lateinit var inflate: View
    override fun initView(inflate: View) {
        this.inflate = inflate
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        val presenter = FindPresenter(this, NaviLifecycle.createFragmentLifecycleProvider(this))
        presenter.getFind()
    }

}