package com.memo.deep.openmyeye.bean.my

import com.memo.deep.openmyeye.bean.baseBean.BaseMuti
import java.io.Serializable

/**
 * 播放页面的数据承载bean
 */
class PlayDetail : Serializable, BaseMuti() {
    // 视频id
    var id: Int = 0
    var playUrl: String = ""
    var coverUrl: String = ""
    var bgUrl: String = ""
    var title: String = ""
    var type: String = ""
    var picList = mutableListOf<String>()
    var nameList = mutableListOf<String>()
    var author: String = ""
    var authorPicUrl: String = ""
    var authorType: String = ""
    var description: String = ""
    var collectionCount: Int = 0
    var shareCount: Int = 0
    var replyCount: Int = 0

    override fun getItemType(): Int {
        return BaseMuti.playDetail
    }
}