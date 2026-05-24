import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private TrainingSession session; // Instance našeho tréninku (Logika)
    private DefaultListModel<String> listModel; // Model pro grafický seznam cvičení
    private JLabel statusLabel; // Popisek pro zobrazení času

    public MainWindow() {
        // Inicializace tréninkového plánu s maximální délkou 90 minut
        session = new TrainingSession(90);
        listModel = new DefaultListModel<>();

        setTitle("Football Training Planner");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Křížek ukončí celou aplikaci
        setLocationRelativeTo(null); // Vycentrování okna na obrazovce

        initUI();
        refreshUI(); // Prvotní vykreslení textu o čase
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // HORNÍ PANEL: Titulek a stavový řádek s časem
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