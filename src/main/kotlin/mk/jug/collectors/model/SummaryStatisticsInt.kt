package mk.jug.collectors.model

/**
 * Summary statistics holder
 */
data class SummaryStatisticsInt(var count: Int = 0,
                                var sum: Int = 0,
                                var min: Int = Int.MAX_VALUE,
                                var max: Int = Int.MIN_VALUE,
                                var avg: Double = 0.0) {
    fun accumulate(n: Int): SummaryStatisticsInt {
        ++count
        sum += n
        min = min.coerceAtMost(n)
        max = max.coerceAtLeast(n)
        avg = sum.toDouble() / count
        return this
    }
}