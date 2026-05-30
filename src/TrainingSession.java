import java.util.ArrayList;
import java.util.List;
/**
 * Manages the logic of a single training session.
 * Handles the collection of exercises and ensures time limits are respected.
 */
public class TrainingSession {
    private List<Exercise> exercises;
    private int maxDuration;
    private int currentDuration;
    /**
     * Initializes a new training session with a specific maximum duration.
     * @param maxDuration Maximum allowed time in minutes.
     */
    public TrainingSession(int maxDuration) {
        this.maxDuration = maxDuration;
        this.exercises = new ArrayList<>();
        this.currentDuration = 0;
    }
    /**
     * Adds a new exercise to the session.
     * @param exercise The exercise to add.
     * @throws TimeExceededException If adding the exercise exceeds the max duration.
     */
    public void addExercise(Exercise exercise) throws TimeExceededException {
        if (currentDuration + exercise.getDurationInMinutes() > maxDuration) {
            throw new TimeExceededException("Cannot add exercise! Max time limit of " + maxDuration + " mins exceeded.");
        }
        exercises.add(exercise);
        currentDuration += exercise.getDurationInMinutes();
    }
    public List<Exercise> getExercises() {
        return exercises;
    }

    public int getCurrentDuration() {
        return currentDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }
    /**
     * Removes an exercise from the session by its index.
     * Automatically deducts the time of the removed exercise.
     * @param index The list index of the exercise to remove.
     */
    public void removeExercise(int index) {
        if (index >= 0 && index < exercises.size()) {
            Exercise removed = exercises.remove(index);
            currentDuration -= removed.getDurationInMinutes();
        }
    }

    @Override
    public String toString() {
        return "TrainingSession{" +
                "exercises=" + exercises +
                ", maxDuration=" + maxDuration +
                ", currentDuration=" + currentDuration +
                '}';
    }
}