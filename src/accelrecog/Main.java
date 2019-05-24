package accelrecog;

import accelrecog.globalListener_actor.GlobalListener;
import gnu.io.SerialPort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class Main {
    static int inc = 0;
    static LinkedList<Gesture> history = new LinkedList<>();
    static SerialConnexion myConnexion;

    public static void main(String[] args) {
        GlobalListener myListener = new GlobalListener();
        BlueTooth myBlueTooth = new BlueTooth();

        myConnexion = new SerialConnexion();


        try {
            myConnexion.connect("COM6");
        } catch (Exception e) {
            e.printStackTrace();
        }


        Interface testUI = new Interface(history);
        testUI.setVisible(true);


        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (myConnexion.dataIncoming()) {
                testUI.setState("Reading data");
                while (!myConnexion.dataEnding()) {
                    //System.out.println("Reading");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (myConnexion.isPing()) {
                    System.out.println("got ping");
                    myBlueTooth.receivedPing();
                    myConnexion.clearBuffer();
                } else if (myConnexion.isStartCamera()) {
                    //here
                    do {
                        //lect pos cam
                        //test click
                        //faire action

                    } while (false);//is end camera
                    System.out.println("Starting camera mouse control");
                    myConnexion.clearBuffer();
                } else if (myConnexion.isEndCamera()) {
                    System.out.println("Something went wrong end camera before start");
                    myConnexion.clearBuffer();
                } else if (myConnexion.isMouvement() && testUI.activated) {
                    System.out.println("computing");
                    testUI.setState("Computing Mouvement");
                    System.out.println("end msg");
                    //diff settings
                    switch (testUI.isLearning) {
                        case 'g':
                            Gesture newGesture = new Gesture(myConnexion.rawDataArr(), testUI.dataName + "", myListener);
                            newGesture.myShortCut.startRecord(testUI);
                            history.add(newGesture);
                            for (Gesture dS : history) {
                                for (int i = 0; i < dS.mySets.size(); i++) {
                                    System.out.println("Data set " + dS.mySets.get(i).myId + " " + dS.mySets.get(i).myData.size());
                                }
                            }
                            inc++;
                            break;
                        case 't':
                            DataSet dataTotest = myConnexion.rawData("testData");

                            Gesture sel = getClosest(dataTotest);
                            if(sel != null) {
                                sel.myShortCut.run();
                                System.out.println("matches with gesture " + sel.myName);
                            }else{
                                System.out.println("No recorded history");
                            }
                            break;
                        case 'r':
                            ArrayList<Data> rawD = myConnexion.rawDataArr();

                            String potRes = getClosest(new DataSet(rawD, "TestData")).myName;

                            for (Gesture dS : history) {
                                if (dS.myName.equals(testUI.dataName)) {
                                    if (dS.myName.equals(potRes)) {
                                        dS.reinforce(rawD);
                                        System.out.println("Reinforced");
                                    } else {
                                        System.out.println("Missmatch");
                                    }
                                }
                            }
                            break;
                    }
                    testUI.setState("New Mouvement read");
                    testUI.showGestures();
                } else {
                    System.out.println("err");
                    myConnexion.clearBuffer();
                }
            }

        } while (true);
        //  System.exit(0);
    }

    static private Gesture getClosest(DataSet dataTotest) {
        double minval = Double.MAX_VALUE;
        Gesture sel = null;

        for (Gesture dS : history) {
            double comp = dS.readDistance(dataTotest);
            System.out.println("Mouvement " + dS.myName + " " + comp);
            if (minval > comp) {
                minval = comp;
                sel = dS;
            }
        }
        return sel;
    }

}
