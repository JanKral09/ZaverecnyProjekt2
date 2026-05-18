import java.util.ArrayList;
import java.util.List;

public class TrainingSession {
    private List<Exercise> exercises;
    private int maxDuration;
    private int currentDuration;

    public TrainingSession(int maxDuration) {
        this.maxDuration = maxDuration;
        this.exercises = new ArrayList<>();
        this.currentDuration = 0;
    }

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

    @Override
    public String toString() {
        return "TrainingSession{" +
                "exercises=" + exercises +
                ", maxDuration=" + maxDuration +
                ", currentDuration=" + currentDuration +
                '}';
    }
}