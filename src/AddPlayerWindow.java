import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
/**
 * Window with a form to add a new player to the team roster.
 * Saves the data directly to the players JSON file.
 */
public class AddPlayerWindow extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JSpinner ageSpinner;
    private MainWindow parent;
    /**
     * Constructs the window for adding new players.
     * @param parent Reference to the main window to update the roster view upon saving.
     */
    public AddPlayerWindow(MainWindow parent) {
        this.parent = parent;
        setTitle("Add New Player");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        initUI();
    }
    /**
     * Initializes the player creation UI form.
     */
    private void initUI() {
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        add(lastNameField);

        add(new JLabel("Age:"));
        ageSpinner = new JSpinner(new SpinnerNumberModel(20, 5, 50, 1));
        add(ageSpinner);

        JButton saveButton = new JButton("Save to Team");
        saveButton.addActionListener(e -> savePlayer());
        add(saveButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }
    /**
     * Validates input, saves the new player to the JSON file, and updates the main window.
     */
    private void savePlayer() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        int age = (int) ageSpinner.getValue();
        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both first and last name!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Player[] existingArray = loadPlayersFromFile("players.json");
            List<Player> playerList = new ArrayList<>();
            if (existingArray != null) {
                playerList.addAll(Arrays.asList(existingArray));
            }

            playerList.add(new Player(firstName, lastName, age));
            saveListToJson(playerList);
            parent.refreshPlayers();
            JOptionPane.showMessageDialog(this, "Player saved successfully!");
            dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error saving: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Parses the JSON file into an array of Player objects.
     * @param filePath The local path to the players JSON file.
     * @return Array of players, or an empty array if file does not exist.
     */
    public static Player[] loadPlayersFromFile(String filePath) {
        Gson gson = new Gson();
        File file = new File(filePath);

        if (!file.exists()) {
            return new Player[0];
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, Player[].class);
        } catch (Exception e) {
            throw new RuntimeException("Error loading players: " + e.getMessage());
        }
    }
    /**
     * Writes the updated roster to the JSON file.
     * @param list Current list of all players.
     * @throws IOException If file writing fails.
     */
    private void saveListToJson(List<Player> list) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter("players.json")) {
            gson.toJson(list, writer);
        }
    }
}