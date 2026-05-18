public class WarmUpExercise extends Exercise {

    public WarmUpExercise(String name, int duration) {
        super(name, duration, "Warm-up");
    }
    @Override
    public String getExerciseDetails() {
        return "Warm-up: " + name + " (" + duration + " min) - Prepare for action!";
    }

    @Override
    public String toString() {
        return "WarmUpExercise{" +
                "name='" + name + '\'' +
                ", duration=" + duration +
                ", category='" + category + '\'' +
                '}';
    }
}