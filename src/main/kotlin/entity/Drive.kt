package entity

open class Drive(override var name: String, override var path: String, override var size: Int = 0) : Entity {
    var entities = HashMap<String, Entity>()

    fun create(entity: Entity): Boolean {
        if (entities.containsKey(entity.name)) {
            println("Entity already exists")
            return false
        }

        entity.path = this.path + "\\" + entity.name
        entities[entity.name] = entity
        this.updateSize(entity.size)

        return true
    }

    fun delete(name: String): Entity? {
        val entity = this.entities[name] ?: return null

        updateSize(entity.size * -1)
        this.entities.remove(name)

        return entity
    }

    fun writeToFile(name: String, newContent: String): Boolean {
        val file = this.entities[name] ?: return false

        return if (file is File) {
            file.write(newContent)
            true
        } else {
            false
        }
    }

    override fun updateSize(size: Int) {
        this.size += size
    }

}