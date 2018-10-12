package com.memo.deep.openmyeye.bean.find


data class Banner(
        var type: String = "",
        var data: Data = Data(),
        var tag: Any = Any(),
        var id: Int = 0,
        var adIndex: Int = 0
): BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.banner
    }

    data class Data(
            var dataType: String = "",
            var id: Int = 0,
            var title: String = "",
            var description: String = "",
            var image: String = "",
            var actionUrl: String = "",
            var adTrack: Any = Any(),
            var shade: Boolean = false,
            var label: Label = Label(),
            var labelList: List<LabelItem> = listOf(),
            var header: Header = Header()
    ) {

        data class LabelItem(
                var text: String = "",
                var actionUrl: Any = Any()
        )


        data class Header(
                var id: Int = 0,
                var title: String = "",
                var font: Any = Any(),
                var subTitle: Any = Any(),
                var subTitleFont: Any = Any(),
                var textAlign: String = "",
                var cover: Any = Any(),
                var label: Any = Any(),
                var actionUrl: Any = Any(),
                var labelList: Any = Any(),
                var rightText: Any = Any(),
                var icon: String = "",
                var description: String = ""
        )


        data class Label(
                var text: String = "",
                var card: String = "",
                var detail: Any = Any()
        )
    }
}