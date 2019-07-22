package com.memo.deep.openmyeye.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Color
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import com.bkvito.beikeshequ.IMyService
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.google.gson.Gson
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.ui.fragment.second.DailyReportFragment
import com.memo.deep.openmyeye.ui.fragment.second.FindFragment
import com.memo.deep.openmyeye.ui.fragment.second.RecommendFragment
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {
    val ACTION_BIND_SERVER = "com.bkvito.beikeshequ.MyService"
    var mIMyService: IMyService? = null
    val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mIMyService = IMyService.Stub.asInterface(service)
            try {
                val student = mIMyService?.student?.get(0)
                showDialog(student.toString())
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mIMyService = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        initView()
        testChart()
//        bindSer()
//        Glide.with(this).load(R.drawable.love).into(iv)
//        startAnimatorSet(tv)

    }

    private fun testChart() {
        val xAxis = line_chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)

        val xLimitLine = LimitLine(4f, "xL 测试");
        xLimitLine.setLineColor(Color.GREEN);
        xLimitLine.setTextColor(Color.GREEN);
        xAxis.addLimitLine(xLimitLine)
        val listOf = mutableListOf<String>()
        listOf.add("content1")
        listOf.add("content2")
        listOf.add("content3")
        val mapOf = mapOf("lable" to listOf)
        Log.i("===>", "=====-=>" + Gson().toJson(mapOf))
    }

    fun startAnimatorSet(tv: View) {
        val animator1 = ObjectAnimator.ofFloat(tv, "scaleX", 1F, 3F)
        val animator2 = ObjectAnimator.ofFloat(tv, "scaleY", 1F, 3F)
        val animator = ObjectAnimator.ofFloat(tv, "translationX", 400F)
        val animator4 = ObjectAnimator.ofFloat(tv, "translationY", -600F)
        animator1.repeatCount = 520
        animator2.repeatCount = 520
        animator.repeatCount = 520
        animator4.repeatCount = 520


        val animatorSet = AnimatorSet()
        animatorSet.setDuration(1000)
        animatorSet.setInterpolator(LinearInterpolator())
        animatorSet.playTogether(animator1, animator2, animator, animator4)
//        animatorSet.playSequentially(animator1, animator2, animator, animator4)
        animatorSet.start()
    }

    private fun bindSer() {
        val intent = Intent()
        intent.action = ACTION_BIND_SERVER
        intent.setPackage("com.bkvito.beikeshequ")
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)
    }

    fun showDialog(message: String) {
        AlertDialog.Builder(this)
                .setTitle("scott")
                .setMessage(message)
                .setPositiveButton("确定", null)
                .show()
    }

    override fun onDestroy() {
        if (mIMyService != null) {
            unbindService(mServiceConnection)
        }
        super.onDestroy()

    }

    private fun initView() {
        // 初始化tab
        val list = arrayListOf<Fragment>(
                FindFragment(),
                RecommendFragment(),
                DailyReportFragment()
        )
        val titleList = arrayOf("发现", "推荐", "日报")
//        vp.adapter = CardAdapter(list, supportFragmentManager)
//        stl.setViewPager(vp, titleList)

    }


}
