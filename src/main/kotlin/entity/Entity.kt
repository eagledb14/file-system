package entity

interface Entity {
    var name: String
    var path: String
    var size: Int

    fun updateSize(size: Int)
}