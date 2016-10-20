package mk.jug.collectors.model;

/**
 * DB record
 */
public class Record {
    private final String teamName;
    private final String teamCity;
    private final String teamArena;

    private final String firstName;
    private final String lastName;
    private final int age;

    public Record(String teamName, String teamCity, String teamArena,
                  String firstName, String lastName, int age) {
        this.teamName = teamName;
        this.teamCity = teamCity;
        this.teamArena = teamArena;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getTeamCity() {
        return teamCity;
    }

    public String getTeamArena() {
        return teamArena;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public static Record fromString(String line) {
        String[] parts = line.split(",");
        return new Record(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5]));
    }

    @Override
    public String toString() {
        return String.format("%-10s%-15s%-20s%-10s%-10s%2d", teamCity, teamName, teamArena, firstName, lastName, age);
    }
}
