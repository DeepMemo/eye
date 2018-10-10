package com.bkvito.beikeshequ.retrofit

import android.util.Log
import com.blankj.utilcode.util.ToastUtils
import com.memo.deep.openmyeye.R
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException

/**
 * retrofit使用的subscriber需要继承此类，
 * 默认处理了网络错误，覆写onError时需要调用super方法
 */
abstract class BaseObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        Log.i("===>", "=====-=>bug")
        e.printStackTrace()
        when (e) {
            is HttpException -> ToastUtils.showShort(R.string.net_error)
            is WebException -> ToastUtils.showShort(e.message)
            is IOException -> ToastUtils.showShort(R.string.connect_web_fail)
            else -> ToastUtils.showShort(R.string.system_error)
        }
    }

    override fun onComplete() {
    }

}