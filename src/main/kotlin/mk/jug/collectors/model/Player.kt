package mk.jug.collectors.model

/**
 * NBA Player
 */
class Player(val name: String, val points: Int) {

    override fun toString(): String {
        return String.format("%-30s%5d", name, points)
    }

    companion object {
        fun fromString(line: String): Player {
            val parts = line.split(",")
            return Player(parts[0], Integer.parseInt(parts[1]))
        }
    }
}
