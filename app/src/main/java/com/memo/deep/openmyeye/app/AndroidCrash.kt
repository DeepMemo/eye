package com.memo.deep.openmyeye.app

import android.content.Context
import android.os.Looper
import android.os.Process
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

 class AndroidCrash : Thread.UncaughtExceptionHandler {
    private lateinit var mDefaultCrashHandler: Thread.UncaughtExceptionHandler
    private lateinit var mContext: Context

    //初始化
    fun init(context: Context) {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler(this)
        mContext = context.applicationContext
    }

    //应用异常系统会调用此方法
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        val current = System.currentTimeMillis()
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(current))
        val file = File(LogWriter.PATH + LogWriter.FILE_NAME + time + LogWriter.FILE_NAME_SUFFIX)

        //为了防止上传时文件没有写入完成
        LogWriter.writeLog(mContext, ex, file, time, object : LogWriter.WriteCallback {
            override fun writeSuccess() {
            }
        })


        //如果系统提供了默认的一场处理，则交给系统去结束异常，否则自己处理
        Thread(Runnable {
            Looper.prepare()
            try {
                //处理错误
                if (mDefaultCrashHandler != null) {
                    mDefaultCrashHandler.uncaughtException(thread, ex)
                } else {
                    Process.killProcess(Process.myPid())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Looper.loop()
        }).start()

    }


    companion object {
        val instance = AndroidCrash()
    }
}
