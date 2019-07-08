package com.memo.deep.openmyeye.ui.mvp.presenter

import com.bkvito.beikeshequ.retrofit.BaseObserver
import com.bkvito.beikeshequ.retrofit.RetrofitFactory
import com.bkvito.beikeshequ.retrofit.RetrofitUtils
import com.memo.deep.openmyeye.`interface`.Constant
import com.memo.deep.openmyeye.ui.mvp.contract.ICategoryContract
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import okhttp3.ResponseBody
import retrofit2.Response

class CategoryPresenter(val view: ICategoryContract.View,
                        val provider: LifecycleProvider<ActivityEvent>) : ICategoryContract.Presenter {
    override fun getCategory() {
        RetrofitFactory.createInterface()
                .getCategory(Constant.URL_MAP)
                .compose(RetrofitUtils.setBase(provider))
                .subscribe(object : BaseObserver<Response<ResponseBody>>() {
                    override fun onNext(t: Response<ResponseBody>) {
                        val list = RetrofitUtils.transformData(t.body()?.string().toString())
                        view.onNext(list)
                    }
                })
    }
}