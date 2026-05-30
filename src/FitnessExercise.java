/**
 * Represents a fitness and conditioning exercise.
 * Inherits from the abstract Exercise class.
 */
public class FitnessExercise extends Exercise {
    public FitnessExercise(String name, int duration) {
        super(name, duration, "Fitness");
    }

    @Override
    public String getExerciseDetails() {
        return "[Fitness] " + name + " (" + duration + " min)";
    }
}