package mk.jug.collectors;

import mk.jug.collectors.model.NBAPlayer;
import mk.jug.collectors.model.Record;
import mk.jug.collectors.model.Team;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Custom collector
 */
public class CustomCollector {
    List<Record> records;

    static final BiConsumer<Map<String, Team>, Record> TEAM_CONSUMER = (stringTeam, record) -> {
        String key = record.getTeamName();
        Team team = stringTeam.getOrDefault(key,
                new Team(record.getTeamName(), record.getTeamCity(), record.getTeamArena()));
        team.addPlayer(new NBAPlayer(record.getFirstName(), record.getLastName(), record.getAge()));
        stringTeam.put(key, team);
    };

    static class TeamCollector implements Collector<Record, Map<String, Team>, List<Team>> {
        @Override
        public Supplier<Map<String, Team>> supplier() {
            return HashMap::new;
        }

        @Override
        public BiConsumer<Map<String, Team>, Record> accumulator() {
            return TEAM_CONSUMER;
        }

        @Override
        public BinaryOperator<Map<String, Team>> combiner() {
            return (left, right) -> Stream.concat(left.entrySet().stream(), right.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (l, r) -> {
                                l.addPlayers(r.getPlayers());
                                return l;
                            })
                    );
        }

        @Override
        public Function<Map<String, Team>, List<Team>> finisher() {
            return (stringTeamMap -> new ArrayList<>(stringTeamMap.values()));
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.emptySet();
            /*return Collections.unmodifiableSet(
                    EnumSet.of(Characteristics.UNORDERED, Characteristics.CONCURRENT)
            );*/
        }
    }

    @Before
    public void loadRecords() {
        try {
            records = Files.lines(Paths.get("src/test/resources/teams.csv"))
                    .map(Record::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void printRecords() {
        records.forEach(System.out::println);
    }

    @Test
    public void collectorWithRecordList() {
        Map<String, List<Record>> teamRecords = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getTeamName,
                        Collectors.toList()
                ));
        teamRecords.entrySet().forEach(
                stringListEntry -> {
                    System.out.printf("=== TEAM: %s\n", stringListEntry.getKey());
                    stringListEntry.getValue().forEach(System.out::println);
                }
        );
    }

    @Test
    public void collectorWithPlayerList() {
        Map<String, List<NBAPlayer>> teamRecords = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getTeamName,
                        Collectors.mapping(
                                record -> new NBAPlayer(record.getFirstName(), record.getLastName(), record.getAge()),
                                Collectors.toList()
                        )
                ));
        teamRecords.entrySet().forEach(
                stringListEntry -> {
                    System.out.printf("=== TEAM: %s\n", stringListEntry.getKey());
                    stringListEntry.getValue().forEach(System.out::println);
                }
        );
    }

    @Test
    public void collectorWithPlayerLastNames() {
        Map<String, String> teamRecords = records.stream()
                .collect(Collectors.groupingBy(
                        Record::getTeamName,
                        Collectors.mapping(
                                Record::getLastName,
                                Collectors.joining(", ")
                        )
                ));
        teamRecords.entrySet().forEach(
                entry -> {
                    System.out.println(entry.getKey());
                    System.out.println(entry.getValue());
                }
        );
    }

    @Test
    public void collectorInTeamMap() {
        Map<Team, List<NBAPlayer>> teamRecords = records.stream()
                .collect(Collectors.groupingBy(
                        record -> new Team(record.getTeamName(), record.getTeamCity(), record.getTeamArena()),
                        Collectors.mapping(
                                record -> new NBAPlayer(record.getFirstName(), record.getLastName(), record.getAge()),
                                Collectors.toList()
                        )
                ));
        teamRecords.entrySet().forEach(
                stringListEntry -> {
                    System.out.printf("TEAM: %s\n", stringListEntry.getKey());
                    stringListEntry.getValue().forEach(System.out::println);
                }
        );
    }

    @Test
    public void customCollector() {
        List<Team> teams = records.stream().collect(new TeamCollector());
        teams.forEach(System.out::println);
    }

    @Test
    public void customCollectorInline() {
        List<Team> teams = records.stream().collect(
                Collector.of(
                        HashMap::new,
                        TEAM_CONSUMER,
                        (left, right) -> null,
                        stringTeam -> new ArrayList<>(stringTeam.values())
                )
        );
        teams.forEach(System.out::println);
    }

}
