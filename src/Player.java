/**
 * Data model representing a football player.
 */
public class Player {
    private String firstName;
    private String lastName;
    private int age;
    /**
     * Constructs a new Player.
     * @param firstName Player's first name.
     * @param lastName Player's last name.
     * @param age Player's age.
     */
    public Player(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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
    /**
     * Gets a formatted string of the player's full name and age.
     * @return Formatted player details.
     */
    public String getDetails() {
        return firstName + " " + lastName + " (Age: " + age + ")";
    }
}