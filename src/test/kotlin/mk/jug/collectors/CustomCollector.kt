package mk.jug.collectors

import mk.jug.collectors.model.NBAPlayer
import mk.jug.collectors.model.Record
import mk.jug.collectors.model.Team
import org.junit.Before
import org.junit.Test
import java.io.File

/**
 * Custom collector
 */
class CustomCollector {
    private var records: List<Record> = emptyList()

    @Before
    fun loadRecords() {
        val lines = File("src/test/resources/teams.csv").readLines()
        records = lines.map { it -> Record.fromString(it) }
    }

    @Test
    fun printRecords() {
        records.forEach(::println)
    }

    @Test
    fun collectorWithRecordList() {
        val teamRecords = records.groupBy { it.teamName }

        teamRecords.entries.forEach {
            println("=== TEAM: ${it.key} ===")
            it.value.forEach(::println)
        }
    }

    @Test
    fun collectorWithPlayerList() {
        val teamRecords = records.groupBy { it.teamName }
                .mapValues { it.value.map { NBAPlayer(it.firstName, it.lastName, it.age) } }

        teamRecords.entries.forEach {
            println("=== TEAM: ${it.key} ===")
            it.value.forEach(::println)
        }
    }

    @Test
    fun collectorWithPlayerLastNames() {
        val teamRecords = records.groupBy { it.teamName }
                .mapValues { it.value.map { it.lastName }.joinToString(", ") }

        teamRecords.entries.forEach {
            println("=== TEAM: ${it.key} ===")
            println(it.value)
        }
    }

    val accumulator = { map: MutableMap<String, Team>, record: Record ->
        val team = map.getOrElse(record.teamName) { Team(record.teamName, record.teamCity, record.teamArena) }
        team.addPlayer(NBAPlayer(record.firstName, record.lastName, record.age))
        map.put(record.teamName, team)
        map
    }

    @Test
    fun collectorInTeamMap() {
        val teamRecords = records.fold(mutableMapOf<String, Team>(), accumulator)

        teamRecords.entries.forEach {
            println("=== TEAM: ${it.key} ===")
            println(it.value)
        }
    }

    @Test
    fun customCollector() {
        val nameTeamMap = mutableMapOf<String, Team>()

        val teams = records.fold(nameTeamMap, accumulator)
        val teamsList = teams.values.toList()
        teamsList.forEach(::println)
    }

    @Test
    fun customCollectorInline() {
        val teams = null
    }
}
