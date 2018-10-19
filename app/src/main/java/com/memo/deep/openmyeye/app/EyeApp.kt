package com.memo.deep.openmyeye.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco

class EyeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initJar()
        AndroidCrash.instance.init(this)
    }

    private fun initJar() {
        Fresco.initialize(this)
    }
}