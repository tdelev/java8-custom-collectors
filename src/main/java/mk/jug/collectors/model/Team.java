package mk.jug.collectors.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * NBA Team
 */
public class Team {
    private final String name;
    private final String city;
    private final String arena;

    private final List<NBAPlayer> players;

    public Team(String name, String city, String arena) {
        this.name = name;
        this.city = city;
        this.arena = arena;
        this.players = new ArrayList<>();
    }

    public void addPlayer(NBAPlayer player) {
        this.players.add(player);
    }

    public List<NBAPlayer> getPlayers() {
        return players;
    }

    public void addPlayers(List<NBAPlayer> players) {
        this.players.addAll(players);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(String.format("=== %s %s ===", city, name));
        joiner.add("Arena: " + arena);
        joiner.add("Players:");
        joiner.add(players.stream()
                .map(NBAPlayer::toString)
                .collect(Collectors.joining("\n")));
        return joiner.toString();
    }
}
