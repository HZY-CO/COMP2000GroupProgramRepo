import java.awt.*;
import javax.swing.*;

public class WindAppTest {
    public static void main(String[] args) {
        Forest forest = new Forest(10, 10);
        Wind wind = new Wind(60, 10, 30);
        forest.addWind(wind);

        JFrame frame = new JFrame("Wind Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(540, 540);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();

                g2.setColor(new Color(235, 245, 250));
                g2.fillRect(0, 0, getWidth(), getHeight());

                int width = getWidth();
                int height = getHeight();
                int cx = width / 2;
                int cy = height / 2;

                double vectorX = wind.getX();
                double vectorY = wind.getY();
                double length = Math.sqrt(vectorX * vectorX + vectorY * vectorY);
                if (length == 0) {
                    length = 1;
                }

                double ux = vectorX / length;
                double uy = vectorY / length;

                int lineLength = Math.min(width, height) / 4;
                int powerScale = Math.max(30, Math.min(160, wind.getWindPower() * 3));
                int endX = cx + (int) Math.round(ux * (lineLength + powerScale / 3));
                int endY = cy + (int) Math.round(uy * (lineLength + powerScale / 3));

                g2.setColor(new Color(0, 120, 255));
                g2.setStroke(new BasicStroke(2 + wind.getWindPower() / 14));
                g2.drawLine(cx, cy, endX, endY);

                g2.setColor(new Color(30, 30, 30));
                g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
                g2.drawString("Wind direction: (" + wind.getX() + ", " + wind.getY() + ")", 18, 24);
                g2.drawString("Power: " + wind.getWindPower(), 18, 44);

                g2.dispose();
            }
        };

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

        Timer timer = new Timer(120, e -> {
            wind.update();
            panel.repaint();
        });
        timer.start();
    }
}
