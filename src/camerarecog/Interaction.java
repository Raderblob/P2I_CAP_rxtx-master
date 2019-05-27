package camerarecog;

import java.awt.*;
import java.util.ArrayList;

public class Interaction {

    private ArrayList<Point> accelerationMeasures;
    private double speed;
    private double slowingCoef;

    public Interaction(){
        accelerationMeasures = new ArrayList<Point>();
    }

    public double getScrollSpeed (boolean scroll, DetectionMain hand){
        while (scroll== true){
            Point handCoordinate= new Point( ( hand.getHandCoordinates()[0]), hand.getHandCoordinates()[1]);
            accelerationMeasures.add(handCoordinate);
        }
        speed= (accelerationMeasures.get(accelerationMeasures.size()).y - accelerationMeasures.get(0).y)/ accelerationMeasures.size();

        return speed;
    }

    public void decreasingScrollSpeed () throws AWTException{
        Robot r = new Robot();
        int avgSpeed= (int)(speed*slowingCoef);
        r.mouseWheel(avgSpeed);
        speed= speed*slowingCoef;
    }

}
