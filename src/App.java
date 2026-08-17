
import javax.swing.*;
import java.awt.*;

public class App {
    public static void main(String[] Args) throws Exception{
        JFrame frame = new JFrame("Test App");
        JPanel panel = new JPanel(){

            @Override
            protected void paintComponent(Graphics g){
                g.fillRect(50,50,100,100);
            }
        };
    }
}