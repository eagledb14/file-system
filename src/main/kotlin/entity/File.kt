package entity

class File(override var name: String, var parent: Entity, override var path: String = "", override var size: Int = 0) : Entity {
    var content: String = ""

    fun write(content: String) {
        this.content = content
        this.updateSize(this.content.length)
    }

    override fun updateSize(size: Int) {
        this.parent.updateSize(this.size * -1)
        this.size = size
        this.parent.updateSize(this.size)
    }
}