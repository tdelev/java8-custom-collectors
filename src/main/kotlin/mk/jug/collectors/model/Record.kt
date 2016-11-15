package mk.jug.collectors.model

/**
 * DB record
 */
class Record(val teamName: String, val teamCity: String, val teamArena: String,
             val firstName: String, val lastName: String, val age: Int) {

    override fun toString(): String {
        return String.format("%-10s%-15s%-20s%-10s%-10s%2d", teamCity, teamName, teamArena, firstName, lastName, age)
    }

    companion object {
        fun fromString(line: String): Record {
            val parts = line.split(",")
            return Record(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]))
        }
    }
}
