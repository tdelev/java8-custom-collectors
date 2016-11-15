package mk.jug.collectors

import mk.jug.collectors.model.Player
import org.junit.Before
import java.io.File

/**
 * Base class for loading data
 */
open class BaseTest {

    internal var players: List<Player> = listOf()

    fun playersSeq(): Sequence<Player> = players.asSequence()

    @Before
    fun loadPlayers() {
        val lines = File("src/test/resources/nba.csv").readLines()
        players = lines.map { it -> Player.fromString(it) }
    }

}
