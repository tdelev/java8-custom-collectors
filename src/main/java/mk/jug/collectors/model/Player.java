package mk.jug.collectors.model;

/**
 * NBA Player
 */
public class Player {
    private final String name;
    private final Integer points;

    public Player(String name, Integer points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public Integer getPoints() {
        return points;
    }

    public static Player fromString(String line) {
        String[] parts = line.split(",");
        return new Player(parts[0], Integer.parseInt(parts[1]));
    }

    @Override
    public String toString() {
        return String.format("%-30s%5d", name, points);
    }
}
