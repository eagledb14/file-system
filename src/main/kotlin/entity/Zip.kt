package entity

class Zip(name: String, parent: Entity, path: String = "", size: Int = 0) : Folder(name, parent, path, size) {
    override fun updateSize(size: Int) {
        super.updateSize(size / 2)
    }
}