package mk.jug.collectors.model

import java.util.*

/**
 * NBA Team
 */
class Team(private val name: String, private val city: String, private val arena: String) {

    private val players: MutableList<NBAPlayer>

    init {
        this.players = ArrayList<NBAPlayer>()
    }

    fun addPlayer(player: NBAPlayer) {
        this.players.add(player)
    }

    fun getPlayers(): List<NBAPlayer> {
        return players
    }

    fun addPlayers(players: List<NBAPlayer>) {
        this.players.addAll(players)
    }

    override fun toString(): String {
        val joiner = StringJoiner("\n")
        joiner.add(String.format("=== %s %s ===", city, name))
        joiner.add("Arena: " + arena)
        joiner.add("Players:")
        joiner.add(players.map { it.toString() }.joinToString("\n"))
        return joiner.toString()
    }
}
