package com.memo.deep.openmyeye.ui.fragment.second

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.memo.deep.openmyeye.bean.FindBean
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.memo.deep.openmyeye.ui.mvp.presenter.FindPresenter
import com.trello.rxlifecycle2.navi.NaviLifecycle
import kotlinx.android.synthetic.main.fragment_second.view.*

class FindFragment : SecondFragment<FindBean>(), IFindContract.View {
    override fun onNext(t: FindBean) {
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        inflate.rv.adapter = FindAdapter(this,t.itemList)
    }

    private lateinit var inflate: View
    override fun initView(inflate: View) {
        this.inflate = inflate
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        val presenter = FindPresenter(this, NaviLifecycle.createFragmentLifecycleProvider(this))
        presenter.getFind()
    }

}