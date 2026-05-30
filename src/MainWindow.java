import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
/**
 * The main dashboard window for the application.
 * Displays the current training session and the team roster.
 */
public class MainWindow extends JFrame {
    private TrainingSession session;
    private DefaultListModel<String> listModel;
    private DefaultListModel<String> playerListModel;
    private JLabel statusLabel;
    /**
     * Constructs the main application window and prompts for session duration.
     */
    public MainWindow() {
        session = new TrainingSession(90);
        listModel = new DefaultListModel<>();

        setTitle("Football Training Planner");
        setSize(1000, 600);
        playerListModel = new DefaultListModel<>();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        refreshUI();
        refreshPlayers();
    }
    /**
     * Initializes the user interface components.
     */
    private void initUI() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new GridLayout(2, 1));
        JLabel titleLabel = new JLabel("Training Session Plan", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        northPanel.add(titleLabel);
        northPanel.add(statusLabel);
        add(northPanel, BorderLayout.NORTH);

        JList<String> exerciseList = new JList<>(listModel);
        exerciseList.setFont(new Font("Arial", Font.PLAIN, 14));
        exerciseList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = exerciseList.locationToIndex(e.getPoint());

                    if (index >= 0) {
                        session.removeExercise(index);
                        refreshUI();
                    }
                }
            }
        });

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        // Left Column: Training
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Current Training Session"));
        leftPanel.add(new JScrollPane(exerciseList), BorderLayout.CENTER);
        centerPanel.add(leftPanel);

         // Right Column: Players
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Your Team / Squad"));
        JList<String> playerList = new JList<>(playerListModel);
        playerList.setFont(new Font("Arial", Font.PLAIN, 14));
        rightPanel.add(new JScrollPane(playerList), BorderLayout.CENTER);
        centerPanel.add(rightPanel);


        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton addPlayerButton = new JButton("Add Player");
        addPlayerButton.setFont(new Font("Arial", Font.BOLD, 14));
        addPlayerButton.addActionListener(e -> {
            AddPlayerWindow playerWindow = new AddPlayerWindow(this);
            playerWindow.setVisible(true);
        });
        bottomPanel.add(addPlayerButton);

        JButton openDbButton = new JButton("Open Exercise Database");
        openDbButton.setFont(new Font("Arial", Font.BOLD, 14));
        openDbButton.addActionListener(e -> {
            DatabaseWindow dbWindow = new DatabaseWindow(this, session);
            dbWindow.setVisible(true);
        });
        bottomPanel.add(openDbButton);

        add(bottomPanel, BorderLayout.SOUTH);
    }
    /**
     * Refreshes the training list and time status label based on session data.
     */
    public void refreshUI() {
        listModel.clear();

        for (Exercise e : session.getExercises()) {
            listModel.addElement(e.getExerciseDetails());
        }

        statusLabel.setText("Total Duration: " + session.getCurrentDuration() + " / " + session.getMaxDuration() + " minutes");

        if (session.getCurrentDuration() >= session.getMaxDuration()) {
            statusLabel.setForeground(Color.RED);
        } else {
            statusLabel.setForeground(Color.BLACK);
        }
    }
    /**
     * Reloads the player roster from the JSON file and refreshes the GUI list.
     */
    public void refreshPlayers() {
        playerListModel.clear();
        try {
            Player[] loadedPlayers = AddPlayerWindow.loadPlayersFromFile("players.json");
            if (loadedPlayers != null) {
                for (Player p : loadedPlayers) {
                    playerListModel.addElement(p.getDetails());
                }
            }
        } catch (Exception e) {
            playerListModel.addElement("No players found in database.");
        }
    }
}