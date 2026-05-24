import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import com.google.gson.Gson;

public class DatabaseWindow extends JFrame {
    private MainWindow parent;
    private TrainingSession session;
    private DefaultListModel<Exercise> dbModel;

    public DatabaseWindow(MainWindow parent, TrainingSession session) {
        this.parent = parent;
        this.session = session;

        setTitle("Exercise Database");
        setSize(450, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        initUI();
        loadExercisesFromJson();
    }

    public DefaultListModel<Exercise> getDbModel() {
        return dbModel;
    }

    private void initUI() {
        setLayout(new BorderLayout());

        dbModel = new DefaultListModel<>();
        JList<Exercise> dbList = new JList<>(dbModel);

        dbList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Exercise) {
                    value = ((Exercise) value).getExerciseDetails();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        add(new JScrollPane(dbList), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton addButton = new JButton("Add to Training");
        addButton.addActionListener(e -> {
            Exercise selected = dbList.getSelectedValue();
            if (selected != null) {
                try {
                    session.addExercise(selected);
                    parent.refreshUI();
                } catch (TimeExceededException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Time Limit Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select an exercise first.", "Selection Error", JOptionPane.WARNING_MESSAGE);
            }
        });
        bottomPanel.add(addButton);

        JButton createNewButton = new JButton("Create New Exercise");
        createNewButton.addActionListener(e -> {
            AddExerciseWindow addWindow = new AddExerciseWindow(this);
            addWindow.setVisible(true);
        });
        bottomPanel.add(createNewButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadExercisesFromJson() {
        try {

            ExerciseData[] loadedData = loadExercisesFromFile("exercises.json");

            if (loadedData != null) {
                dbModel.clear();

                for (ExerciseData data : loadedData) {
                    if ("Warm-up".equals(data.category)) {
                        dbModel.addElement(new WarmUpExercise(data.name, data.duration));
                    } else if ("Tactics".equals(data.category)) {
                        dbModel.addElement(new TacticalExercise(data.name, data.duration));
                    } else if ("Fitness".equals(data.category)) {
                        dbModel.addElement(new FitnessExercise(data.name, data.duration));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database could not be loaded: " + e.getMessage(), "Loading Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static ExerciseData[] loadExercisesFromFile(String filePath) {
        Gson gson = new Gson();
        File file = new File(filePath);

        if (!file.exists()) {
            return new ExerciseData[0];
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, ExerciseData[].class);
        } catch (Exception e) {
            throw new RuntimeException("Error loading JSON: " + e.getMessage());
        }
    }
}