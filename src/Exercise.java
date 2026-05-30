/**
 * Abstract base class for all types of football exercises.
 * Implements the Planable interface and provides core properties.
 */
public abstract class Exercise implements Planable {
    protected String name;
    protected int duration;
    protected String category;
    /**
     * Constructor for creating a base exercise.
     * @param name Name of the exercise.
     * @param duration Duration in minutes.
     * @param category Category type of the exercise.
     */
    public Exercise(String name, int duration, String category) {
        this.name = name;
        this.duration = duration;
        this.category = category;
    }

    @Override
    public int getDurationInMinutes() {
        return duration;
    }

    @Override
    public String getName() {
        return name;
    }
    /**
     * Abstract method to get detailed formatted information about the exercise.
     * @return Formatted string containing exercise details.
     */
    public abstract String getExerciseDetails();

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", category='" + category + '\'' +
                '}';
    }
}