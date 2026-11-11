import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.util.function.Consumer;

public class ControlPanel extends Panel {
    private final int controlPanelWidth;
    private final int maxGridWidth;
    private final int maxGridHeight;
    private final int maxCellSize;
    private final Consumer<ControlPanel> refreshAction;
    private final AnalyticsPanel analyticsPanel;

    private int gridWidth;
    private int gridHeight;
    private int cellSize;

    public ControlPanel(
            int panelWidth,
            int panelHeight,
            int gridWidth,
            int gridHeight,
            int cellSize,
            int maxGridWidth,
            int maxGridHeight,
            int maxCellSize,
            Simulation simulation,
            Consumer<ControlPanel> refreshAction) {
        this.controlPanelWidth = panelWidth;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.cellSize = cellSize;
        this.maxGridWidth = maxGridWidth;
        this.maxGridHeight = maxGridHeight;
        this.maxCellSize = maxCellSize;
        this.refreshAction = refreshAction;

        setLayout(null);
        setSize(panelWidth, panelHeight);
        setBackground(new Color(44, 44, 44));
        add(createDimensionControl("Columns", 80, gridWidth));
        add(createDimensionControl("Rows", 200, gridHeight));
        add(createDimensionControl("Cell Size", 320, cellSize));
        add(createRefreshButton());
        analyticsPanel = new AnalyticsPanel(panelWidth, simulation);
        add(analyticsPanel);
    }

    private Panel createDimensionControl(String title, int y, int initialValue) {
        Panel attribute = new Panel();
        attribute.setLayout(null);
        attribute.setSize(controlPanelWidth, 100);
        attribute.setBackground(new Color(60, 60, 60));
        attribute.setLocation(0, y);

        Label titleLabel = new Label(title, Label.LEFT);
        titleLabel.setBounds(20, 0, controlPanelWidth, 100);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(new Color(255, 255, 255));

        Label valueLabel = new Label(Integer.toString(initialValue), Label.CENTER);
        valueLabel.setBounds(controlPanelWidth - 130, 30, 80, 40);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        valueLabel.setForeground(new Color(255, 255, 255));

        int maximum = maximumFor(title);
        Button decreaseButton = createValueButton("<", controlPanelWidth - 160);
        decreaseButton.setForeground(new Color(0,0,0));
        decreaseButton.addActionListener(event -> {
            int value = Math.max(1, currentValue(title) - 1);
            setCurrentValue(title, value);
            valueLabel.setText(Integer.toString(value));
        });

        Button increaseButton = createValueButton(">", controlPanelWidth - 60);
        increaseButton.setForeground(new Color(0,0,0));
        increaseButton.addActionListener(event -> {
            int value = Math.min(maximum, currentValue(title) + 1);
            setCurrentValue(title, value);
            valueLabel.setText(Integer.toString(value));
        });

        attribute.add(decreaseButton);
        attribute.add(increaseButton);
        attribute.add(valueLabel);
        attribute.add(titleLabel);
        return attribute;
    }

    private int maximumFor(String title) {
        if (title.equals("Columns")) {
            return maxGridWidth;
        }
        if (title.equals("Rows")) {
            return maxGridHeight;
        }
        return maxCellSize;
    }

    private int currentValue(String title) {
        if (title.equals("Columns")) {
            return gridWidth;
        }
        if (title.equals("Rows")) {
            return gridHeight;
        }
        return cellSize;
    }

    private void setCurrentValue(String title, int value) {
        if (title.equals("Columns")) {
            gridWidth = value;
        } else if (title.equals("Rows")) {
            gridHeight = value;
        } else {
            cellSize = value;
        }
    }

    private Button createValueButton(String text, int x) {
        Button button = new Button(text);
        button.setBounds(x, 30, 40, 40);
        button.setFont(new Font("SansSerif", Font.BOLD, 24));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(40, 40, 40));
        return button;
    }

    private Button createRefreshButton() {
        Button refreshButton = new Button("Refresh");
        refreshButton.setBounds(80, 440, controlPanelWidth - 160, 40);
        refreshButton.setFont(new Font("SansSerif", Font.BOLD, 24));
        refreshButton.setForeground(new Color(0,0,0));
        refreshButton.setBackground(new Color(40, 40, 40));
        refreshButton.addActionListener(event -> refreshAction.accept(this));
        return refreshButton;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void updateAnalytics(Simulation simulation) {
        analyticsPanel.update(simulation);
    }
}
