package entity

open class Folder(name: String, var parent: Entity, path: String = "", size: Int = 0) : Drive(name, path, size) {
    override fun updateSize(size: Int) {
        this.parent.updateSize(size)
        super.updateSize(size)
    }
}
