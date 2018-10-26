package com.memo.deep.openmyeye.ui.view.text_view

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.memo.deep.openmyeye.R

/**
 * baseQuickAdapter的尾部
 */
class CustomLoadMoreView : LoadMoreView() {

    override fun getLayoutId(): Int {
        return R.layout.custom_footer
    }

    /**
     * 注解了必须是id，不能使用layout
     */
    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

}