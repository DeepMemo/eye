package com.memo.deep.openmyeye.ui.fragment.second

import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.LinearInterpolator
import com.memo.deep.openmyeye.bean.beanBase.BaseMuti
import com.memo.deep.openmyeye.bean.beanItem.FollowCard
import com.memo.deep.openmyeye.bean.beanItem.TextCard
import com.memo.deep.openmyeye.bean.my.PlayDetail
import com.memo.deep.openmyeye.ui.activity.PlayDetailActivity
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.IFindContract
import com.memo.deep.openmyeye.ui.mvp.presenter.FindPresenter
import com.memo.deep.openmyeye.ui.view.textView.CustomLoadMoreView
import com.scwang.smartrefresh.layout.api.RefreshHeader
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener
import com.trello.rxlifecycle2.navi.NaviLifecycle
import kotlinx.android.synthetic.main.fragment_second.view.*

/**
 * 发现页面
 */
class FindFragment : SecondFragment<BaseMuti>(), IFindContract.View {

    private lateinit var presenter: FindPresenter
    private lateinit var adapter: FindAdapter
    override fun initView(view: View) {
        inflate = view
        initAdapter()
        initSrl()
        initData()
        initRotation()
        initListener()
    }

    private fun initAdapter() {
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        adapter = FindAdapter(this, list)
        inflate.rv.adapter = adapter
        adapter.setPreLoadNumber(3)
    }


    /**
     * 初始化smartRefreshLayout
     */
    private fun initSrl() {
        inflate.srl.setEnableLoadMore(false)
        inflate.srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean,
                                        percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                val angle = 360f * percent
                inflate.iv_header.rotation = angle
            }
        })

        inflate.srl.setOnRefreshListener {
            objectAnimator.start()
            presenter.getFindContent()
        }
    }

    private fun initData() {
        presenter = FindPresenter(this,
                NaviLifecycle.createFragmentLifecycleProvider(this))
        inflate.srl.autoRefresh()
    }

    private lateinit var objectAnimator: ObjectAnimator
    private fun initRotation() {
        objectAnimator = ObjectAnimator.ofFloat(
                inflate.iv_header, "rotation", 0f, 360f)
                .setDuration(600)
        // 差值器，是其平滑，默认中间快，两头慢
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.repeatMode = ObjectAnimator.RESTART
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
    }

    private fun initListener() {
        adapter.setOnItemClickListener { adapter, view, position ->
            val playDetail = setPlayDetail(list.get(position))
            val intent = Intent(activity, PlayDetailActivity::class.java)
            intent.putExtra("data", playDetail)
            startActivity(intent)
        }
    }

    fun setPlayDetail(item: BaseMuti): PlayDetail {
        val playDetail = PlayDetail()
        when (item) {
            is FollowCard -> setFollowData(item, playDetail)
        }

        return playDetail
    }

    private fun setFollowData(item: FollowCard, playDetail: PlayDetail) {
        playDetail.id = item.data.header.id
        playDetail.playUrl = item.data.content.data.playUrl
        playDetail.coverUrl = item.data.content.data.cover.detail
        playDetail.bgUrl = item.data.content.data.cover.blurred
        playDetail.title = item.data.content.data.title
        playDetail.type = item.data.header.description
        playDetail.description = item.data.content.data.description
        playDetail.collectionCount = item.data.content.data.consumption.collectionCount
        playDetail.shareCount = item.data.content.data.consumption.shareCount
        playDetail.replyCount = item.data.content.data.consumption.replyCount
        playDetail.pic1 = item.data.content.data.tags.get(0).headerImage
        playDetail.pic2 = item.data.content.data.tags.get(1).headerImage
        playDetail.pic3 = item.data.content.data.tags.get(2).headerImage
        playDetail.name1 = item.data.content.data.tags.get(0).name
        playDetail.name2 = item.data.content.data.tags.get(1).name
        playDetail.name3 = item.data.content.data.tags.get(2).name
        playDetail.author = item.data.content.data.author.name
        playDetail.authorPicUrl = item.data.content.data.author.icon
        playDetail.authorType = item.data.content.data.category
    }


    override fun onNext(t: List<BaseMuti>) {
        val diffResult = DiffUtil.calculateDiff(NewDiffCallback(list, t), true)
        list.clear()
        list.addAll(t)
        diffResult.dispatchUpdatesTo(adapter)
        inflate.srl.finishRefresh()
        objectAnimator.cancel()

        // 数据居然动态变化，可能内容获取就添加了评论
        initLoadMore()
        val last = t.last()
        if (last is TextCard) {
            adapter.loadMoreEnd()
        }

    }

    /**
     *
     */
    private fun initLoadMore() {
        // 刷新一次，数据重新获取，可以进行加载更多操作
        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnLoadMoreListener({
            presenter.getMoreComment()
        }, inflate.rv)
    }

    override fun onNextMore(t: List<BaseMuti>) {
        val size = list.size
        list.addAll(t)
        adapter.notifyItemRangeInserted(size, t.size)
        adapter.loadMoreComplete()
    }

    override fun onMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun onError() {
        handleChange()
    }

    /**
     *  处理刷新，错误，的各种情况
     */
    private fun handleChange() {
        inflate.srl.finishRefresh()
        objectAnimator.cancel()
        adapter.loadMoreComplete()
    }

    /**
     * 数据差异比较
     */
    class NewDiffCallback(private val oldList: List<BaseMuti>,
                          private val newList: List<BaseMuti>) : DiffUtil.Callback() {
        override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
            return oldList.get(p0).itemType == newList.get(p1).itemType
        }

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
            return oldList.get(p0) == newList.get(p1)
        }

    }

}