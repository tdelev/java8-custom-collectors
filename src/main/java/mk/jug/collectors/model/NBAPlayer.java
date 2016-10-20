package mk.jug.collectors.model;

/**
 * NBA Player
 */
public class NBAPlayer {
    private final String firstName;
    private final String lastName;
    private final int age;

    public NBAPlayer(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", firstName, lastName, age);
    }
}
