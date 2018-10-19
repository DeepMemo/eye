package com.memo.deep.openmyeye.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.adapter.recycle.TestAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_test.*
import java.util.concurrent.TimeUnit

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
    }

        private lateinit var testAdapter: TestAdapter
//    private lateinit var testAdapter: OriginAdapter
    val list = arrayListOf("1", "2", "3")

    //    val list = arrayListOf<String>()
    private fun initView() {
        rv.layoutManager = LinearLayoutManager(this)
        testAdapter = TestAdapter(R.layout.item_find_text_card, list)
//        testAdapter = OriginAdapter(list)
        rv.adapter = testAdapter
    }

    override fun onResume() {
        super.onResume()
        Observable.just("")
                .delay(2, TimeUnit.SECONDS)
                .compose(RetrofitUtils.setThread())
                .subscribe {
                    onNext(list, arrayListOf("new1", "2", "3", "4"))
                }
    }

    override fun onRestart() {
        super.onRestart()

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    fun onNext(oldList: List<String>, newList: ArrayList<String>) {
        val diffResult = DiffUtil.calculateDiff(NewDiffCallback(oldList, newList))
        testAdapter.list.clear()
        testAdapter.list.addAll(newList)
        diffResult.dispatchUpdatesTo(testAdapter)
    }

    /**
     * 数据差异比较
     */
    class NewDiffCallback(private val oldList: List<String>,
                          private val newList: List<String>) : DiffUtil.Callback() {
        override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
            return true
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
