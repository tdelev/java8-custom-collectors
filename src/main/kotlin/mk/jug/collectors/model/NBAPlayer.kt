package mk.jug.collectors.model

/**
 * NBA Player
 */
class NBAPlayer(private val firstName: String, private val lastName: String, private val age: Int) {

    override fun toString(): String {
        return String.format("%s %s %d", firstName, lastName, age)
    }
}
