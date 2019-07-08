package com.memo.deep.openmyeye.ui.fragment.second

import android.animation.ObjectAnimator
import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.LinearInterpolator
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.*
import com.memo.deep.openmyeye.cache.ConstantCache
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
open class FindFragment : SecondFragment<BaseMuti>(), IFindContract.View {

    protected lateinit var presenter: IFindContract.Presenter
    protected lateinit var adapter: FindAdapter
    override fun initView() {
        initAdapter()
        initSrl()
        initData()
        initRotation()
        initListener()
    }

    protected fun initAdapter() {
        inflate.rv.layoutManager = LinearLayoutManager(activity)
        adapter = FindAdapter(this, list, onViewPagerClick = onViewPagerClick)
        inflate.rv.adapter = adapter
    }


    /**
     * 初始化smartRefreshLayout
     */
    protected open fun initSrl() {
        inflate.srl.setEnableLoadMore(false)
        inflate.srl.setOnMultiPurposeListener(object : SimpleMultiPurposeListener() {
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean,
                                        percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                val angle = 360f * percent
                inflate.iv_common_refresh_header.rotation = angle
                onRecycle.invoke(inflate, percent)

            }
        })

        inflate.srl.setOnRefreshListener {
            objectAnimator.start()
            getContent()
        }
    }

    protected fun initData() {
        presenter = FindPresenter(this,
                NaviLifecycle.createFragmentLifecycleProvider(this))
        // 网络请求，使用懒加载的回调，因第一个Fragment可见，第二个Fragment不可见，所以必须这样写
        isFragmentVisibe(userVisibleHint)
    }

    protected lateinit var objectAnimator: ObjectAnimator
    protected fun initRotation() {
        objectAnimator = ObjectAnimator.ofFloat(
                inflate.iv_common_refresh_header, "rotation", 0f, 360f)
                .setDuration(600)
        // 差值器，是其平滑，默认中间快，两头慢
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.repeatMode = ObjectAnimator.RESTART
        objectAnimator.repeatCount = ObjectAnimator.INFINITE
    }

    protected fun initListener() {
        // item 的整体点击你以为
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = list.get(position)
            when (item) {
                is FollowCard -> {
                    ConstantCache.data = presenter.setFollowCardData(item)
                    val intent = Intent(activity, PlayDetailActivity::class.java)
                    startActivity(intent)
                }
                is VideoSmallCard -> {
                    ConstantCache.data = presenter.setVideoSmallCardData(item)
                    val intent = Intent(activity, PlayDetailActivity::class.java)
                    startActivity(intent)
                }
            }

        }

        // 子View 的点击事件，是那种不是嵌套的，例如一个item下面有图片和文字，其中文字的点击
        adapter.setOnItemChildClickListener { adapter, view, position ->
            val item = list.get(position)
            when (item) {
                is VideoCollectionWithBrief -> {
                    when (view.id) {
                        R.id.tv_watch -> {
                            ToastUtils.showShort("关注了")
                        }
                    }
                }
                is DynamicInfoCard -> {
                    val intent = Intent(activity, PlayDetailActivity::class.java)
                    intent.putExtra(Constant.INTENT_ID, item.data.simpleVideo.id)
                    startActivity(intent)
                }

            }
        }

    }

    /**
     * 嵌套的viewpager的点击事件的回调，当做一个全局变量使用
     */
    protected val onViewPagerClick: (item: Any) -> Unit = {
        when (it) {
            is VideoCollectionWithBrief.Data.Item -> {
                ConstantCache.data = presenter.setVideoCollectionWithBriefData(it)
                val intent = Intent(activity, PlayDetailActivity::class.java)
                startActivity(intent)
            }
            is FollowCard -> {
                ConstantCache.data = presenter.setFollowCardData(it)
                val intent = Intent(activity, PlayDetailActivity::class.java)
                startActivity(intent)
            }
            is HorizontalScrollCard.Data.Item -> {
            }
        }

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
     * 重置可以刷新数据
     */
    protected fun initLoadMore() {
        // 刷新一次，数据重新获取，可以进行加载更多操作
        adapter.setLoadMoreView(CustomLoadMoreView())
        adapter.setOnLoadMoreListener({
            getMoreContent()
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
    protected fun handleChange() {
        inflate.srl.finishRefresh()
        objectAnimator.cancel()
        adapter.loadMoreComplete()
    }

    /**
     * 数据差异比较
     */
    class NewDiffCallback(protected val oldList: List<BaseMuti>,
                          protected val newList: List<BaseMuti>) : DiffUtil.Callback() {
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


    /**
     * 给子类覆写的方法，提供获取网络数据的入口
     */
    open fun getContent() {
        presenter.getFindContent()
    }

    /**
     * 同上
     */
    open fun getMoreContent() {
        presenter.getMoreComment()
    }

    /**
     * 懒加载的回调
     */
    override fun isFragmentVisibe(isVisible: Boolean) {
        if (isVisible) {
            inflate.srl.autoRefresh()
            // 网络请求过一次，就不再是第一次了
            isFirst = false
        } else {

        }
    }

    var onRecycle = fun(view: View, percent: Float) {

    }
}