package mk.jug.collectors

import mk.jug.collectors.model.Player
import mk.jug.collectors.model.SummaryStatisticsInt
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import java.util.*
import kotlin.comparisons.compareBy

/**
 * Collection examples using Kotlin Collections
 */
class HelperCollectors : BaseTest() {

    @Test
    fun allPlayers() {
        players.forEach(::println)
    }

    @Test
    fun collectToList() {
        val moreThan25K = players.filter { it -> it.points > 25000 }
        moreThan25K.forEach(::println)

        assertThat(moreThan25K.size, `is`(20))
    }

    @Test
    fun groupByKPoints() {
        val byKPoints = players.groupBy { it.points / 1000 }

        byKPoints.entries.forEach {
            println("=== ${it.key}K points ===")
            it.value.forEach(::println)
        }
        assertThat(byKPoints.size, `is`(16))
    }


    @Test
    fun groupByKPointsOrdered() {
        val byKPoints = players.groupByTo(LinkedHashMap<Int, MutableList<Player>>()
                .toSortedMap(compareBy <Int> { it }.reversed()))
        { it.points / 1000 }

        byKPoints.forEach {
            println("== ${it.key}K Points ==")
            it.value.forEach(::println)
        }
        assertThat(byKPoints.size, `is`(16))
    }

    @Test
    fun mostPoints() {
        val max = players.maxBy { it.points }
        assertThat("Exist", max != null)
        max?.let { assertThat(it.points, `is`(38387)) }
    }

    inline fun Collection<Int>.summarizingInt(): SummaryStatisticsInt =
            this.fold(SummaryStatisticsInt()) { stats, it -> stats.accumulate(it) }

    inline fun <T : Any> Collection<T>.summarizingInt(transform: (T) -> Int): SummaryStatisticsInt =
            this.fold(SummaryStatisticsInt()) { stats, it -> stats.accumulate(transform.invoke(it)) }

    @Test
    fun pointsStatistics() {
        val pointsStatistics = players.fold(SummaryStatisticsInt()) { stats, it -> stats.accumulate(it.points) }
        println(pointsStatistics)

        val stats = players.summarizingInt(Player::points)
        println(stats)
    }

    @Test
    fun bestByFirstLetter() {

        val bestByFirstLetter = playersSeq()
                .groupBy { it -> it.name[0] }
                .mapValues { list -> list.value.maxBy { it.points } }

        bestByFirstLetter.forEach { entry ->
            println("${entry.key} -> ${entry.value}")
        }

    }

    @Test
    fun groupGOATContenderNames() {
        val goats = playersSeq()
                .filter { it.points > 30000 }
                .map { it.name }
                .joinToString(", ", "=> ", " <=")

        println(goats)
        assertThat(goats, containsString("Jordan"))
        assertThat(goats, containsString("Kobe"))
    }

}
