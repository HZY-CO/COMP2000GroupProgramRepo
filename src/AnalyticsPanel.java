import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;

public class AnalyticsPanel extends Panel {
    private final Label treeAmountText;
    private final Label treesOnFireText;
    private final Label burntTreesText;
    private final Label weatherNameText;
    private final Label ticksPassedText;
    private final Label animalsAliveText;
    private final Label animalsDeadText;

    public AnalyticsPanel(int panelWidth, Simulation simulation) {
        setLayout(null);
        setSize(panelWidth, 300);
        setBackground(new Color(60, 60, 60));
        setLocation(0, 510);

        Label analyticsTitle = new Label("Analytics", Label.LEFT);
        analyticsTitle.setBounds(20, 4, panelWidth, 28);
        styleLabel(analyticsTitle, 24);

        treeAmountText = createMetricLabel(panelWidth, 34);
        treesOnFireText = createMetricLabel(panelWidth, 56);
        burntTreesText = createMetricLabel(panelWidth, 78);
        weatherNameText = createMetricLabel(panelWidth, 100);
        ticksPassedText = createMetricLabel(panelWidth, 122);
        animalsAliveText = createMetricLabel(panelWidth, 144);
        animalsDeadText = createMetricLabel(panelWidth, 166);

        add(analyticsTitle);
        add(treeAmountText);
        add(treesOnFireText);
        add(burntTreesText);
        add(weatherNameText);
        add(ticksPassedText);
        add(animalsAliveText);
        add(animalsDeadText);
        update(simulation);
    }

    public void update(Simulation simulation) {
        treeAmountText.setText("Amount of Trees: " + simulation.getTreeAmount());
        treesOnFireText.setText("Trees burning: " + simulation.getTreesBurning());
        burntTreesText.setText("Total Trees Burnt: " + simulation.getTreesBurnt());
        weatherNameText.setText("Current Weather: " + simulation.getWeatherName());
        ticksPassedText.setText("Ticks passed: " + simulation.getTicksPassed());
        animalsAliveText.setText("Animals alive: " + simulation.getAnimalsAlive());
        animalsDeadText.setText("Animals dead: " + simulation.getAnimalsDead());
    }

    private Label createMetricLabel(int panelWidth, int y) {
        Label label = new Label("", Label.LEFT);
        label.setBounds(20, y, panelWidth, 22);
        styleLabel(label, 18);
        return label;
    }

    private void styleLabel(Label label, int size) {
        label.setFont(new Font("SansSerif", Font.BOLD, size));
        label.setForeground(new Color(255, 255, 255));
    }
}
