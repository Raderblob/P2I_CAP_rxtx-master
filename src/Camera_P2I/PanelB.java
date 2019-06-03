package Camera_P2I;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class PanelB extends JPanel {

    private Image backgroundImage;

    public PanelB() throws IOException {
        backgroundImage = Toolkit.getDefaultToolkit().createImage("imageFond.png");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("imageFond.png").getImage(), 0,0,this.getWidth(),this.getHeight(), this);
    }
}

