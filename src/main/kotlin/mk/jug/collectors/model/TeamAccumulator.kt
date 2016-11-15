package mk.jug.collectors.model

/**
 * Summary statistics holder
 */
data class TeamAccumulator(var teamPlayersMap: MutableMap<String, Team> = mutableMapOf()) {
    fun accumulate(record: Record): Team {
        val team = teamPlayersMap.getOrElse(record.teamName) { Team(record.teamName, record.teamCity, record.teamArena) }
        team.addPlayer(NBAPlayer(record.firstName, record.lastName, record.age))
        return team
    }
}