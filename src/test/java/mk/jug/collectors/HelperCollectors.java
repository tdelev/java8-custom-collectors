package mk.jug.collectors;

import mk.jug.collectors.model.Player;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Some examples with {@link java.util.stream.Stream} and {@link Collectors}
 */
public class HelperCollectors extends BaseTest {

    @Test
    public void allPlayers() {
        print(players);
    }

    @Test
    public void collectToList() {
        List<Player> moreThan25K = players.stream()
                .filter(each -> each.getPoints() > 25_000)
                .collect(Collectors.toList());
        print(moreThan25K);

        assertThat(moreThan25K.size(), is(20));
    }


    @Test
    public void groupByKPoints() {
        Map<Integer, List<Player>> byKPoints = players.stream()
                .collect(Collectors.groupingBy(player -> player.getPoints() / 1_000));

        byKPoints.entrySet().forEach(PRINT_MAP);
        assertThat(byKPoints.size(), is(16));
    }

    @Test
    public void groupByKPointsOrdered() {
        Map<Integer, List<Player>> byKPoints = players.stream()
                .collect(Collectors.groupingBy(
                        player -> player.getPoints() / 1_000,
                        () -> new TreeMap<>(Comparator.<Integer>naturalOrder().reversed()),
                        Collectors.toList())
                );

        byKPoints.entrySet().forEach(PRINT_MAP);
        assertThat(byKPoints.size(), is(16));
    }

    @Test
    public void mostPoints() {
        Optional<Player> max = players.stream()
                .max(Comparator.comparing(Player::getPoints));
        max.ifPresent(System.out::println);

        assertThat("Exist", max.isPresent());
        max.ifPresent(player -> assertThat(player.getPoints(), is(38_387)));
    }

    @Test
    public void pointsStatistics() {
        IntSummaryStatistics pointsStatistics = players.stream()
                .collect(Collectors.summarizingInt(Player::getPoints));

        /*pointsStatistics = players.stream()
                .mapToInt(Player::getPoints)
                .summaryStatistics();*/

        System.out.println(pointsStatistics);
    }

    @Test
    public void bestByFirstLetter() {
        Comparator<Player> byPoints = Comparator.comparing(Player::getPoints);

        Map<Character, Optional<Player>> bestByFirstLetter = players.stream()
                .collect(
                        Collectors.groupingBy(
                                each -> each.getName().charAt(0),
                                //Collectors.reducing(BinaryOperator.maxBy(byPoints))
                                Collectors.maxBy(byPoints)
                        )
                );
        bestByFirstLetter.entrySet().forEach(each ->
                System.out.printf("%c -> %s\n", each.getKey(), each.getValue().orElse(null)));

    }

    @Test
    public void groupGOATContenderNames() {
        String goats = players.stream()
                .filter(each -> each.getPoints() > 30_000)
                .map(Player::getName)
                .collect(Collectors.joining(", ", "=> ", " <="));

        System.out.println(goats);
        assertThat(goats, containsString("Jordan"));
        assertThat(goats, containsString("Kobe"));
    }

}
