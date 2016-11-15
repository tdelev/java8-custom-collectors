package mk.jug.collectors.lazy

import java.util.*

/**
 * Lazy stream demo
 */
object LazyStream {
    @JvmStatic fun main(args: Array<String>) {
        val numbers = Arrays.asList(1, 2, 3, 5, 4, 6, 7, 8, 9, 10)

        println("Notice the order of evaluation:")
        println(numbers.asSequence().
                filter { isGT3(it) }
                .filter { isEven(it) }
                .map { doubleIt(it) }
                .toList())

        println("Evaluation is Lazy:")
        numbers.asSequence()
                .filter { isGT3(it) }
                .filter { isEven(it) }
                .map { doubleIt(it) }
        println("No computation was done since we did not ask for the result")
    }

    fun isGT3(number: Int): Boolean {
        println("isGT3 " + number)
        return number > 3
    }

    fun isEven(number: Int): Boolean {
        println("isEven " + number)
        return number % 2 == 0
    }

    fun doubleIt(number: Int): Int {
        println("doubleIt " + number)
        return number * 2
    }
}
