package com.memo.deep.openmyeye.ui.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback
import com.chad.library.adapter.base.listener.OnItemDragListener
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import com.memo.deep.openmyeye.ui.adapter.recycle.CategoryAdapter
import com.memo.deep.openmyeye.ui.mvp.contract.ICategoryContract
import com.memo.deep.openmyeye.ui.mvp.presenter.CategoryPresenter
import kotlinx.android.synthetic.main.activity_category.*

/**
 * 分类
 */
class CategoryActivity : BaseActivity(), ICategoryContract.View, OnItemDragListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        initView()
        initData()
    }

    val list = mutableListOf<BaseMuti>()
    val categoryAdapter = CategoryAdapter(R.layout.item_find_brief_card, list)
    private fun initView() {
        setToolbar(R.string.category_title)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = categoryAdapter
        val itemDragAndSwipeCallback = ItemDragAndSwipeCallback(categoryAdapter)
        val itemTouchHelper = ItemTouchHelper(itemDragAndSwipeCallback)
        itemTouchHelper.attachToRecyclerView(rv)
        categoryAdapter.enableDragItem(itemTouchHelper, R.id.ll_title, true)
        categoryAdapter.setOnItemDragListener(this)
    }

    val presenter = CategoryPresenter(this, getProvider())
    private fun initData() {
        presenter.getCategory()
    }

    override fun onItemDragMoving(source: RecyclerView.ViewHolder?, from: Int, target: RecyclerView.ViewHolder?, to: Int) {
    }

    override fun onItemDragStart(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
    }

    override fun onItemDragEnd(viewHolder: RecyclerView.ViewHolder?, pos: Int) {
    }


    override fun onNext(list: List<BaseMuti>) {
        this.list.addAll(list)
        categoryAdapter.notifyDataSetChanged()
    }
}
