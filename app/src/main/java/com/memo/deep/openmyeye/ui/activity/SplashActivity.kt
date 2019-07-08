package com.memo.deep.openmyeye.ui.activity

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.blankj.utilcode.util.ScreenUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.memo.deep.openmyeye.R
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.app.AndroidCrash
import com.memo.deep.openmyeye.bean.ItemListBean.StartBean
import com.memo.deep.openmyeye.greenDao.dao.DaoMaster
import com.memo.deep.openmyeye.greenDao.util.GreenDaoUPgradeHelper
import com.memo.deep.openmyeye.ui.activity.mvp.MainActivity
import com.memo.deep.openmyeye.util.MyUtils
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * 启动页
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initView()
        initData()
    }


    private fun initView() {
        ScreenUtils.setFullScreen(this)
    }

    private fun initData() {
        RetrofitFactory.createInterface()
                .getStartPic(Constant.URL_MAP)
                .compose(RetrofitUtils.setBase(getProvider()))
                .subscribe(object : BaseObserver<StartBean>() {
                    override fun onNext(t: StartBean) {
                        startAni(t.startPage.imageUrl)
                        MyUtils.waitSecond(2100) {
                            startAc(MainActivity::class.java)
                            finish()
                        }
                    }
                })
    }

    private fun startAni(imageUrl: String) {
        ObjectAnimator.ofFloat(tv, "alpha", 0f, 1f)
                .setDuration(2000)
                .start()
        ObjectAnimator.ofFloat(tv2, "alpha", 0f, 1f)
                .setDuration(2000)
                .start()
        iv.setImageURI(imageUrl)
        val scaleY = ObjectAnimator.ofFloat(iv, "scaleY", 1F, 1.25F)
        val scalex = ObjectAnimator.ofFloat(iv, "scaleX", 1F, 1.25F)
        val animatorSet = AnimatorSet()
        animatorSet.duration = 2000
        animatorSet.interpolator = LinearInterpolator()
        animatorSet.playTogether(scaleY, scalex)
        animatorSet.start()

    }

    private fun initJar() {
        Fresco.initialize(this)
    }

    private fun initCrash() {
        AndroidCrash.instance.init(this)
    }

    private fun initDatabase() {
        // 创建实体类以后编译动态生成
        //创建数据库greendao_use.db"
        val greenDaoUPgradeHelper = GreenDaoUPgradeHelper(this, "greendao_use.db", null)
        //获取可写数据库
        val db = greenDaoUPgradeHelper.writableDb
        //获取数据库对象
        val daoMaster = DaoMaster(db)
        //获取Dao对象管理者
        val daoSession = daoMaster.newSession()
        val note3List = daoSession.note3Dao.loadAll()
    }

}
