package accelrecog.globalListener_actor;

import java.awt.*;
import java.awt.event.InputEvent;

public class Actor {
    private Robot robot;

    public Actor() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.setAutoDelay(300);
        robot.setAutoWaitForIdle(true);
    }

    public void mouseMove(int x, int y) {
        robot.mouseMove(x, y);
    }

    public void keyPress(int code) {
        try {
            robot.keyPress(code);
        } catch (Exception e) {
            System.out.println(code);
        }

    }

    public void keyRelease(int code) {
        try {
            robot.keyRelease(code);
        } catch (Exception e) {
            System.out.println(code);
        }
    }

    public void mousePress(int code) {
        try {
            robot.mousePress(getjavaCode(code));
        } catch (Exception e) {
            System.out.println(code);
        }
    }

    public void mouseRelease(int code) {
        try {
            robot.mouseRelease(getjavaCode(code));
        } catch (Exception e) {
            System.out.println(code);
        }
    }

    private int getjavaCode(int code){
        switch (code){
            case 1:
                return InputEvent.BUTTON1_MASK;

            case 2:
                System.out.println("right click");
                return InputEvent.BUTTON3_MASK;
                default:
                    System.out.println("err " + code);
                    return InputEvent.BUTTON1_MASK;

        }
    }
}
