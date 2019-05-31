package accelrecog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicDisplay extends JFrame {
    private JPanel mainPanel;
    private CustomCanvas drawingCanvas1, drawingCanvas2;
    private JLabel infoLabel;
    public GraphicDisplay(DataSet comparator, DataSet comparable){
        super("Graphics Display");
        this.setBounds(0,0,1000,475);
        this.setLayout(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0,0,1000,475);
        drawingCanvas1 = new CustomCanvas(0,0,1000,200,comparator);
        mainPanel.add(drawingCanvas1);
        drawingCanvas2 = new CustomCanvas(0,200,1000,200,comparable);
        mainPanel.add(drawingCanvas2);

        infoLabel = new JLabel();
        infoLabel.setBounds(5,375,1000,100);
        infoLabel.setText("X Axis : Time  Y Axis : Magnitude  ");
        mainPanel.add(infoLabel);

        this.add(mainPanel);
        this.setResizable(false);
    }

}
class CustomCanvas extends JPanel{
    private int myWidth,myHeight;
    private DataSet hist;
    private int maxlen;
    public CustomCanvas(int x,int y,int w, int h,DataSet series){
        super();
        myWidth = w;
        myHeight=h;
        this.setBounds(x,y,w,h);
        this.setLayout(null);
        hist = series;
        maxlen =series.myData.size();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,myWidth,myHeight);
        g.setColor(Color.BLACK);
        g.drawLine(5,myHeight-5,myWidth,myHeight-5);
        g.drawLine(5,myHeight-5,5,0);

        Point p1,p2;
        for(int i = 0;i<maxlen-1;i++){
            g.setColor(Color.RED);
            p1 = getScreenCoord(i,hist.myData.get(i).x);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).x);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);

            g.setColor(Color.GREEN);
            p1 = getScreenCoord(i,hist.myData.get(i).y);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).y);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);

            g.setColor(Color.BLACK);
            p1 = getScreenCoord(i,hist.myData.get(i).z);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).z);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);

            g.setColor(Color.BLUE);
            p1 = getScreenCoord(i,hist.myData.get(i).a);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).a);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);

            g.setColor(Color.CYAN);
            p1 = getScreenCoord(i,hist.myData.get(i).b);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).b);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);

            g.setColor(Color.GRAY);
            p1 = getScreenCoord(i,hist.myData.get(i).c);
            p2 =  getScreenCoord(i+1,hist.myData.get(i+1).c);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);
        }
    }


    private  Point getScreenCoord(double x, double y){
        return new Point((int)((x/maxlen)*myWidth)+5,(int)((1-y)*myHeight*0.95-5));
    }
}