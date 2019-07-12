package com.memo.deep.openmyeye.ui.fragment.second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.base.BaseFragment


/**
 * 第二层次的通用fragment
 */
abstract class SecondFragment<T> : BaseFragment() {

    //第一次网络请求
    protected var isFirst = true
    // 是否是否初始化了View
    private var isInitView = false
    lateinit var inflate: View
    protected open var list = ArrayList<T>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        inflate = inflater.inflate(R.layout.fragment_second, container, false)
        isInitView = true
        initView()
        return inflate
    }

    /**
     * 考虑使用不同的presenter和使用不同的adapter
     */
    abstract fun initView()

    /**
     * 考虑使用不同的presenter和使用不同的adapter
     */
    abstract fun isFragmentVisibe(isVisible: Boolean)


    /**
     * 实现懒加载的关键方法
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        when {
            // 可见，但是未初始化view
            isVisibleToUser && !isInitView -> {
            }
            // 可见，出初始化了view，并且是第一次网络请求
            isVisibleToUser && isInitView && isFirst -> {
                isFragmentVisibe(true)
            }
            // 不可见，初始化view
            // 这种是直接点击上面的tab页面
            // 应该直接走onCreateView，好像啥都不用做啊.
            !isVisibleToUser && isInitView -> {
            }
            // 不可见，为初始化view
            !isVisibleToUser && !isInitView -> {
            }
        }
    }

}
