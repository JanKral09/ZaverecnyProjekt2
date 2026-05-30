/**
 * Represents a tactical exercise
 * Inherits from the abstract Exercise class.
 */
public class TacticalExercise extends Exercise {
    public TacticalExercise(String name, int duration) {
        super(name, duration, "Tactics");
    }
    @Override
    public String getExerciseDetails(){
        return "[Tactics] " + name + " (" + duration + " min)";
    }
}