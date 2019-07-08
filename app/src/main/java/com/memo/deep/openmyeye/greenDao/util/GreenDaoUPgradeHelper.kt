package com.memo.deep.openmyeye.greenDao.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.github.yuweiguocn.library.greendao.MigrationHelper
import com.memo.deep.openmyeye.greenDao.database.*
import org.greenrobot.greendao.database.Database

/**
 *     author : deep
 *     time   : 2019/06/15
 *     desc   :
 *     version: 1.0
 */
class GreenDaoUPgradeHelper(context: Context, name: String, facctory: SQLiteDatabase.CursorFactory?)
    : DaoMaster.OpenHelper(context, name, facctory) {
    override fun onUpgrade(db: Database?, oldVersion: Int, newVersion: Int) {
        MigrationHelper.migrate(db, object : MigrationHelper.ReCreateAllTableListener {
            override fun onCreateAllTables(db: Database?, ifNotExists: Boolean) {
                DaoMaster.createAllTables(db, ifNotExists)
            }

            override fun onDropAllTables(db: Database?, ifExists: Boolean) {
                DaoMaster.dropAllTables(db, ifExists)
            }
        }, NoteDao::class.java, Note2Dao::class.java, Note3Dao::class.java,Note4Dao::class.java)

        var oldVersionCopy = oldVersion
        while (oldVersionCopy < newVersion) {
            when (oldVersionCopy) {
                1 -> {
                }
                2 -> {
                }
                3 -> {
                    val newSession = DaoMaster(db).newSession()
                    val list = listOf<Note3>(Note3(0, "name0"), Note3(1, "name1"))
                    newSession.note3Dao.insertInTx(list)
                }
                4 -> {

                }
            }
            oldVersionCopy++
        }
    }

}