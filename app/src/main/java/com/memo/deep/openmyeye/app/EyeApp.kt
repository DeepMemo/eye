package com.memo.deep.openmyeye.app

import android.app.Application
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.facebook.drawee.backends.pipeline.Fresco
import com.memo.deep.openmyeye.greenDao.database.DaoMaster
import com.memo.deep.openmyeye.greenDao.util.GreenDaoUPgradeHelper
import io.reactivex.Observable

class EyeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initJar()
        Observable.just("")
                .compose(RetrofitUtils.setThread())
                .subscribe {
                    initCrash()
                    initDatabase()
                }
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