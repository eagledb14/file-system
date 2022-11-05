import entity.*

class Terminal {
    private val drives = HashMap<String, Entity>()
    private var currentEntity: Entity? = null

    fun create(type: String, name: String, path: String)  {
        type.lowercase()

        if (type == "drive") {
            this.drives[name] = Drive(name, path)
            return
        }

        if (!this.findPath(path.split("\\"))) {
            println("file path not found")
            return
        }

        if (this.drives.size == 0) {
            println("Error: No drives have been created yet")
            return
        }

        var newEntity: Entity? = null
        when(type) {
            "folder" -> newEntity = Folder(name, parent = this.currentEntity!!)
            "zip" -> newEntity = Zip(name, parent = this.currentEntity!!)
            "file" -> newEntity = File(name, parent = this.currentEntity!!)
        }

        if (this.currentEntity is Drive) {
            (this.currentEntity as Drive).create(newEntity!!)
        }
        else {
            return
        }
    }

    fun delete(path: String) : Boolean {
        val pathArray = path.split("\\") as ArrayList<String>
        pathArray.remove("")

        if (pathArray.size == 1) {
            this.drives.remove(pathArray[0]) ?: return false
            return true
        }
        else if (!this.findPath(pathArray)) {
            return false
        }

        (this.currentEntity as Drive).delete(pathArray[pathArray.size - 1]) ?: return false

        return true
    }

    fun move(source: String, destination: String) : Boolean {
        val pathArray = source.split("\\") as ArrayList<String>
        if (!this.findPath(source.split("\\"))) {
            return false
        }
        val movedEntity = (this.currentEntity as Drive).delete(pathArray[pathArray.size - 1])?: return false

        if (!this.findPath(destination.split("\\"))) {
            return false
        }

        if (this.currentEntity is Drive) {
            if (movedEntity is Folder) {
                movedEntity.parent = this.currentEntity as Drive
            } else if (movedEntity is File) {
                movedEntity.parent = this.currentEntity as Drive
            }
            (this.currentEntity as Drive).create(movedEntity)
        }
        else {
            return false
        }

        return true
    }

    fun writeToFile(path: String, newContent: String) : Boolean {
        val pathArray = path.split("\\")
        if (!findPath(pathArray)) {
            return false
        }

        if (!(this.currentEntity as Drive).writeToFile(pathArray[pathArray.size - 1], newContent)) {
            println("Error writing to file")
            return false
        }

        return true
    }

    private fun findPath(path: List<String>) : Boolean {
        path as ArrayList<String>
        path.remove("")

        this.currentEntity = drives[path[0]] ?: return false
        path.removeAt(0)

        for (i in 0 until path.size - 1) {
            if (currentEntity is Drive) {
                currentEntity = (currentEntity as Drive).entities[path[i]] ?: return false
            }
        }

        return true
    }

    fun readInput() {
        var exit = false

        while (!exit) {
            println("Input command")
            val input = readLine()?.split(" ")
            input!![0].lowercase()

            when(input[0]) {
                "quit" -> { exit = true}
                "create" ->  {
                    if (input.size < 4) {
                        println("Error: create needs input in form Type, Name, Path")
                        continue
                    }
                    this.create(input[1], input[2], input[3])
                }
                "delete" -> {
                    if (input.size < 2) {
                        println("Error: delete needs input in form Path")
                        continue
                    }
                    this.delete(input[1])
                }
                "move" -> {
                    if (input.size < 2) {
                        println("Error: move needs input in form Source, Destination")
                        continue
                    }
                    this.move(input[1], input[2])
                }
                "write" -> {
                    if (input.size < 2) {
                        println("Error: write needs input in form Size newContent")
                        continue
                    }
                    val path = input[1]
                    input as ArrayList<String>
                    input.removeAt(0)
                    input.removeAt(0)

                    val content = input.joinToString(separator = " ")
                    this.writeToFile(path, content)
                }
                "print" -> {
                    println(this.drives)
                }
                else -> {
                    println("Invalid command")
                }
            }
        }
    }
}