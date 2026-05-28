import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private TrainingSession session;
    private DefaultListModel<String> listModel;
    private JLabel statusLabel;

    public MainWindow() {
        session = new TrainingSession(90);
        listModel = new DefaultListModel<>();

        setTitle("Football Training Planner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
        refreshUI();
    }

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
        JScrollPane scrollPane = new JScrollPane(exerciseList);
        add(scrollPane, BorderLayout.CENTER);

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
}