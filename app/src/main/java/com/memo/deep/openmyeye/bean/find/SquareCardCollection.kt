package com.memo.deep.openmyeye.bean.find


data class SquareCardCollection(
    var type: String = "",
    var data: Data = Data(),
    var tag: Any = Any(),
    var id: Int = 0,
    var adIndex: Int = 0
) : BaseMuti() {
    override fun getItemType(): Int {
        return BaseMuti.squareCardCollection
    }

    data class Data(
        var dataType: String = "",
        var header: Header = Header(),
        var itemList: List<Item> = listOf(),
        var count: Int = 0,
        var adTrack: Any = Any()
    ) {

        data class Header(
            var id: Int = 0,
            var title: String = "",
            var font: String = "",
            var subTitle: Any = Any(),
            var subTitleFont: Any = Any(),
            var textAlign: String = "",
            var cover: Any = Any(),
            var label: Any = Any(),
            var actionUrl: String = "",
            var labelList: Any = Any(),
            var rightText: Any = Any()
        )


        data class Item(
            var type: String = "",
            var data: Data = Data(),
            var tag: Any = Any(),
            var id: Int = 0,
            var adIndex: Int = 0
        ) {

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
                var labelList: List<Any> = listOf(),
                var header: Any = Any()
            ) {

                data class Label(
                    var text: String = "",
                    var card: String = "",
                    var detail: Any = Any()
                )
            }
        }
    }
}