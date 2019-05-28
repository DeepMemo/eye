package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.bean.itemBean.FollowCard
import com.memo.deep.openmyeye.bean.my.SearchMuti
import com.memo.deep.openmyeye.bean.my.SearchTitleMuti
import com.memo.deep.openmyeye.ui.adapter.recycle.FindAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.ISearchContract
import com.memo.deep.openmyeye.ui.mvp.presenter.SearchPresenter
import com.oushangfeng.pinnedsectionitemdecoration.PinnedHeaderItemDecoration
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity(), ISearchContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initData()
    }


    val list = mutableListOf<BaseMuti>()
    val adapter = FindAdapter(list = list)
    private fun initView() {
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
        // RecycleView 的往下动画
        val slideInDownAnimator = SlideInDownAnimator(LinearInterpolator())
        slideInDownAnimator.addDuration = 200
        rv.itemAnimator = slideInDownAnimator
        // 这是列表滚动的效果，并不是第一次加载的效果
//        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)
        setSticky()
        setListener()

    }

    private fun setSticky() {
        rv.addItemDecoration(
                // 设置粘性标签对应的类型
                PinnedHeaderItemDecoration.Builder(BaseMuti.searchTitle)
                        // 设置分隔线资源ID
//                        .setDividerId(R.drawable.divider)
                        // 开启绘制分隔线，默认关闭
//                        .enableDivider(true)
                        // 通过传入包括标签和其内部的子控件的ID设置其对应的点击事件
                        .setClickIds(R.id.tv)
                        // 是否关闭标签点击事件，默认开启
//                        .disableHeaderClick(false)
                        // 设置标签和其内部的子控件的监听，若设置点击监听不为null，但是disableHeaderClick(true)的话，还是不会响应点击事件
//                        .setHeaderClickListener(clickAdapter)
                        .create())
    }

    // 开始搜索的标志，不然就是预搜索
    var isStartSearch = false

    private fun setListener() {
        adapter.setOnItemClickListener { adapter, view, position ->
            val key = et_search.text.toString()
            val item = list.get(position)
            if (item is SearchMuti) {
                val htmlColor = "<font color='#4687D7'>%s</font>"
                val keyWithColor = String.format(htmlColor, key)
                // 去除html标签的颜色
                val originText = item.name.replace(keyWithColor, key)
                startSearch(originText)
                et_search.setText(originText)
            }

            if (item is FollowCard) {
                val intent = Intent(this, PlayDetailActivity::class.java)
                intent.putExtra(Constant.INTENT_ID, item.data.header.id)
                startActivity(intent)
            }
        }
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                // 删除历史搜索记录
                R.id.tv_del -> {
                    // 去除标题
                    list.removeAt(0)
                    // 不要使用indices的for循环,因为indices会不断增加，结果removeAt也增加，删除的就不对应了
                    // 本身都是从头开始删除的，
                    for (item in searchHistorySet) {
                        list.removeAt(0)
                    }
                    val size = searchHistorySet.size
                    searchHistorySet.clear()
                    SPUtils.getInstance().put("search", searchHistorySet)
                    adapter.notifyItemRangeRemoved(0, size + 1)
                }
                else -> {
                    ToastUtils.showShort(position.toString())
                }
            }
        }

        et_search.setOnKeyListener { v, keyCode, event ->
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                //隐藏键盘
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
                //搜索
                val key = et_search.text.toString()
                startSearch(key, true)
            }
            return@setOnKeyListener false
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (TextUtils.isEmpty(text)) {
                    list.clear()
                    //搜索的的数据动态添加
                    addHistorySearch(list)
                    list.addAll(hotList)
                    adapter.notifyDataSetChanged()
                    iv_del.visibility = View.INVISIBLE
                } else {
                    iv_del.visibility = View.VISIBLE
                }

                if (!isStartSearch) {
                    presenter.getPreSearch(text)
                }
                // 只在用户确认搜索时，不进行预搜索，取反都是预搜索
                isStartSearch = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private val presenter = SearchPresenter(this, getProvider())
    private val searchHistorySet = LinkedHashSet<String>()
    private fun initData() {
        presenter.getHotSearch()
        searchHistorySet.addAll(SPUtils.getInstance().getStringSet("search"))
    }

    /**
     * 开始搜索的预处理
     */
    fun startSearch(name: String, isEnter: Boolean = false) {
        presenter.getSearch(name)
        addHistrory(name)
        // 如果使用enter键，顺序是错误的，因为先改变了文字，
        // 然后再搜索，导致下一次输入文字，不进行预搜索了
        if (!isEnter) {
            isStartSearch = true
        }
    }

    override fun onPreNext(transformArray: List<BaseMuti>) {
        // 如果页面删除到最后一个字，但是最后一个字还是有预搜索，但不刷新页面
        if (et_search.text.isEmpty()) {
            return
        }
        list.clear()
        list.addAll(transformArray)
        if (transformArray.isEmpty()) {
            //搜索的的数据动态添加
            addHistorySearch(list)
            list.addAll(hotList)
        }
        adapter.notifyDataSetChanged()
    }

    var hotList = mutableListOf<BaseMuti>()
    override fun onHotNext(transformArray: List<BaseMuti>) {
        // 复制一份
        hotList.addAll(transformArray)
        addHistorySearch(list)
        list.addAll(transformArray)
        adapter.notifyDataSetChanged()
    }

    override fun onNext(transformArray: List<BaseMuti>) {
        list.clear()
        list.addAll(transformArray)
        adapter.notifyDataSetChanged()
    }

    fun addHistorySearch(list: MutableList<BaseMuti>) {
        if (searchHistorySet.isNotEmpty()) {
            val element = createSearchTitle(R.string.search_history)
            list.add(element)
            for (s in searchHistorySet) {
                // 倒序排列
                list.add(1, SearchMuti(s))
            }
        }
        list.add(createSearchTitle(R.string.hot_search_key))
    }

    /**
     * 搜索历史和关键词为TextCard 类型
     */
    private fun createSearchTitle(resId: Int): BaseMuti {
        val element = SearchTitleMuti()
        element.name = getString(resId)
        return element
    }

    fun addHistrory(name: String) {
        searchHistorySet.add(name)
        SPUtils.getInstance().put("search", searchHistorySet)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.iv_del -> {
                et_search.text.clear()
            }
            R.id.tv_cancel -> {
                onBackPressed()
            }
            else -> {
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.item_bottom_to_top)
    }

}
