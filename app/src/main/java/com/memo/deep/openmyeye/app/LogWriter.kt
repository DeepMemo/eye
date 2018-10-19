package com.memo.deep.openmyeye.app

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter

object LogWriter {

    private val DEBUG = true
    val PATH = Environment.getExternalStorageDirectory().absolutePath + "/eye/crash/"
    val FILE_NAME = "crash"
    val FILE_NAME_SUFFIX = ".txt"
    private val file: File? = null

    @Synchronized
    fun writeLog(context: Context, ex: Throwable, file: File, time: String, writeCallback: WriteCallback) {
        if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
            if (DEBUG) {
                Log.e("","write crash exception failed")
                return
            }
        }

        val dir = File(PATH)
        if (!dir.exists()) {
            dir.mkdirs()
        }

        try {
            val pw = PrintWriter(BufferedWriter(FileWriter(file)))
            pw.println(time)
            writeMobileInfo(context, pw)
            pw.println()
            ex.printStackTrace(pw)
            pw.close()
            writeCallback.writeSuccess()
        } catch (e: Exception) {
            Log.e("","write crash exception failed")
        }

    }


    @Throws(PackageManager.NameNotFoundException::class)
    private fun writeMobileInfo(context: Context, pw: PrintWriter) {

        val pm = context.packageManager
        val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
        pw.print("App Version：")
        pw.print(pi.versionName)
        pw.print("_")
        pw.println(pi.versionCode)

        //Android版本
        pw.print("OS Version：")
        pw.print(Build.VERSION.RELEASE)
        pw.print("_")
        pw.println(Build.VERSION.SDK_INT)

        //手机制造商
        pw.print("Vendor：")
        pw.println(Build.MANUFACTURER)

        pw.print("Model：")
        pw.println(Build.MODEL)
    }

    interface WriteCallback {
        fun writeSuccess()
    }
}
