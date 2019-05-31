package accelrecog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GraphicDisplay extends JFrame {
    private JPanel mainPanel;
    private CustomCanvas drawingCanvas;
    public GraphicDisplay(Gesture comparator, Gesture comparable){
        super("Graphics Display");
        this.setBounds(0,0,500,300);
        this.setLayout(null);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBounds(0,0,500,250);
        drawingCanvas = new CustomCanvas(0,0,500,200,comparator,comparable);
        mainPanel.add(drawingCanvas);


        this.add(mainPanel);
    }

}
class CustomCanvas extends JPanel{
    private int myWidth,myHeight;
    private Gesture hist,test;
    private int maxlen;
    public CustomCanvas(int x,int y,int w, int h,Gesture comparator,Gesture comparable){
        super();
        myWidth = w;
        myHeight=h;
        this.setBounds(x,y,w,h);
        this.setLayout(null);
        hist = comparator;
        test = comparable;
        maxlen = Math.min(comparator.mySets.get(0).myData.size(),comparable.mySets.get(0).myData.size());

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0,0,myWidth,myHeight);
        g.setColor(Color.BLACK);
        g.drawLine(5,myHeight-5,myWidth,myHeight-5);
        g.drawLine(5,myHeight-5,5,0);
        for(int i = 0;i<maxlen-1;i++){
            g.setColor(Color.RED);
            Point p1,p2;
            p1 = getScreenCoord(i,hist.mySets.get(0).myData.get(i).x);
            p2 =  getScreenCoord(i+1,hist.mySets.get(0).myData.get(i+1).x);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);
            g.setColor(Color.GREEN);
            p1 = getScreenCoord(i,test.mySets.get(0).myData.get(i).x);
            p2 =  getScreenCoord(i+1,test.mySets.get(0).myData.get(i+1).x);
            g.drawLine(p1.x,p1.y,p2.x,p2.y);
        }
    }

    private  Point getScreenCoord(double x, double y){
        return new Point((int)((x/maxlen)*myWidth)+5,(int)((1-y)*myHeight*0.95-5));
    }
}