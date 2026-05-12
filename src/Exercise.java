public abstract class Exercise implements Planable {
    protected String name;
    protected int duration;
    protected String category;

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

    public abstract String getExerciseDetails();
}