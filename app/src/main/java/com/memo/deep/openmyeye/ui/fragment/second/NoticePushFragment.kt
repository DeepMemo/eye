package com.memo.deep.openmyeye.ui.fragment.second

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.ItemListBean.NoticePushBean
import com.memo.deep.openmyeye.ui.adapter.recycle.NoticePushAdapter
import com.memo.deep.openmyeye.ui.fragment.base.BaseFragment
import com.memo.deep.openmyeye.ui.view.textView.CustomLoadMoreView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import kotlinx.android.synthetic.main.fragment_notice_push.*

/**
 * 通知推送的fragment
 */
class NoticePushFragment : BaseFragment() {

    val list = mutableListOf<NoticePushBean.Message>()
    var adapter: NoticePushAdapter = NoticePushAdapter(list)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_notice_push, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        rv.adapter = adapter
        initSrl()
        initRotation()
    }

    private fun initData() {
        getContent()
    }

    var start = 0
    var num = 10
    private fun getContent() {
        val map = mutableMapOf<String, String>()
        start = 0
        map["start"] = start.toString()
        map["num"] = num.toString()
        map.putAll(Constant.URL_MAP)
        RetrofitFactory.createInterface()
                .getPush(map)
                .compose(RetrofitUtils.setFragmentBase(getProvider()))
                .subscribe(object : BaseObserver<NoticePushBean>() {
                    override fun onNext(t: NoticePushBean) {
                        onNext(t.messageList)
                    }
                })
    }

    private fun getMoreContent() {
        val map = mutableMapOf<String, String>()
        map["start"] = (start + num).toString()
        map["num"] = num.toString()
        map.putAll(Constant.URL_MAP)
        RetrofitFactory.createInterface()
                .getPush(Constant.URL_MAP)
                .compose(RetrofitUtils.setFragmentBase(getProvider()))
                .subscribe(object : BaseObserver<NoticePushBean>() {
                    override fun onNext(t: NoticePushBean) {
                        onNextMore(t.messageList)
                    }
                })
    }

    protected lateinit var objectAnimator: ObjectAnimator
    protected fun initRotation() {
        objectAnimator = ObjectAnimator.ofFloat(
                iv_common_refresh_header, "rotation", 0f, 360f)
                .setDuration(600)
        // 差值器，是其平滑，默认的中间快，两头慢
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.repeatMode = ObjectAnimator.RESTART
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
    }

    /**
     * 初始化smartRefreshLayout
     */
    protected open fun initSrl() {
        srl.setEnableLoadMore(false)
        srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean,
                                        percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                val angle = 360f * percent
                iv_common_refresh_header.rotation = angle
            }
        })

        srl.setOnRefreshListener {
            objectAnimator.start()
            getContent()
        }
    }

    fun onNext(t: List<NoticePushBean.Message>) {
        list.clear()
        list.addAll(t)
        adapter.notifyDataSetChanged()
        srl.finishRefresh()
        objectAnimator.cancel()

        // 数据居然动态变化，可能内容获取就添加了评论
        initLoadMore()
    }

    /**
     * 重置可以刷新数据
     */
    protected fun initLoadMore() {
        // 刷新一次，数据重新获取，可以进行加载更多操作
        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnLoadMoreListener({
            getMoreContent()
        }, rv)
    }

    fun onNextMore(t: List<NoticePushBean.Message>) {
        val size = list.size
        list.addAll(t)
        adapter.notifyItemRangeInserted(size, t.size)
        adapter.loadMoreComplete()
    }
}