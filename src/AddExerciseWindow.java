import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Window with a form to create and save a completely new exercise.
 * Saves the resulting data into the JSON database file.
 */
public class AddExerciseWindow extends JFrame {
    private JTextField nameField;
    private JSpinner durationSpinner;
    private JComboBox<String> categoryBox;
    private DatabaseWindow dbWindow;
    /**
     * Constructs the window for adding new exercises.
     * @param dbWindow Reference to the database window to trigger UI updates.
     */
    public AddExerciseWindow(DatabaseWindow dbWindow) {
        this.dbWindow = dbWindow;

        setTitle("Create New Exercise");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(dbWindow);

        initUI();
    }
    /**
     * Initializes the input form UI.
     */
    private void initUI() {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Exercise Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Duration (min):"));
        durationSpinner = new JSpinner(new SpinnerNumberModel(15, 1, 120, 5));
        add(durationSpinner);

        add(new JLabel("Category:"));
        String[] categories = {"Warm-up", "Tactics", "Fitness"};
        categoryBox = new JComboBox<>(categories);
        add(categoryBox);

        JButton saveButton = new JButton("Save to Database");
        saveButton.addActionListener(e -> saveExercise());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }
    /**
     * Validates input, updates the JSON file, and instantly refreshes the database window.
     */
    private void saveExercise() {
        String name = nameField.getText().trim();
        int duration = (int) durationSpinner.getValue();
        String category = (String) categoryBox.getSelectedItem();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a name!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {

            ExerciseData[] existingArray = DatabaseWindow.loadExercisesFromFile("exercises.json");
            List<ExerciseData> exerciseList = new ArrayList<>();
            if (existingArray != null) {
                exerciseList.addAll(Arrays.asList(existingArray));
            }

            ExerciseData newData = new ExerciseData();
            newData.name = name;
            newData.duration = duration;
            newData.category = category;
            exerciseList.add(newData);

            saveListToJson(exerciseList);

            if ("Warm-up".equals(category)) {
                dbWindow.getDbModel().addElement(new WarmUpExercise(name, duration));
            } else if ("Tactics".equals(category)) {
                dbWindow.getDbModel().addElement(new TacticalExercise(name, duration));
            } else if ("Fitness".equals(category)) {
                dbWindow.getDbModel().addElement(new FitnessExercise(name, duration));
            }

            JOptionPane.showMessageDialog(this, "Exercise saved successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Saves the provided list of exercises to a JSON file.
     * @param list The updated list of ExerciseData.
     * @throws IOException If file writing fails.
     */
    private void saveListToJson(List<ExerciseData> list) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter("exercises.json")) {
            gson.toJson(list, writer);
        }
    }
}