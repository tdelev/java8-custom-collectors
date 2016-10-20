package mk.jug.collectors;

import mk.jug.collectors.model.Player;
import org.junit.Before;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Base class for loading data
 */
public class BaseTest {

    List<Player> players;

    @Before
    public void loadPlayers() {
        try {
            players = Files.lines(Paths.get("src/test/resources/nba.csv"))
                    .map(Player::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    void print(List<Player> players) {
        players.forEach(System.out::println);
    }

    static final Consumer<Map.Entry<Integer, List<Player>>> PRINT_MAP = entry -> {
        System.out.printf("\n== %dK Points ==\n", entry.getKey());
        entry.getValue().forEach(System.out::println);
    };
}
