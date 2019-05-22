package accelrecog;

import accelrecog.globalListener_actor.GlobalListener;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class Interface  extends JFrame implements ActionListener {
    private Panel accelSettings;
    private JButton test,learn,reInforce;
    private JLabel accelState;
    private JTextArea learnedSets;

    public char isLearning = 't';
    public String dataName;

    private LinkedList<Gesture> allgest;
    public Interface(LinkedList<Gesture> aGesture){
        super("Recog Interface");
        allgest = aGesture;

        this.setBounds(0,0,600,600);
        this.setLayout(null);
        accelSettings = new Panel();
        accelSettings.setLayout(null);
        accelSettings.setBackground(Color.black);
        accelSettings.setBounds(0,0,600,600);

        test = new JButton("Test values");
        test.setBackground(Color.red);
        test.setBounds(50,50,200,100);
        test.setEnabled(false);
        test.addActionListener(this);
        accelSettings.add(test);

        learn = new JButton("Learn more");
        learn.setBackground(Color.GREEN);
        learn.setBounds(50,250,200,100);
        learn.setEnabled(true);
        learn.addActionListener(this);
        accelSettings.add(learn);

        reInforce = new JButton("Reinforce existing Gesture");
        reInforce.setBackground(Color.GREEN);
        reInforce.setBounds(300,250,200,100);
        reInforce.setEnabled(true);
        reInforce.addActionListener(this);
        accelSettings.add(reInforce);


        accelState = new JLabel("Awaiting information");
        accelState.setForeground(Color.red);
        accelState.setBounds(50,175,300,45);
        accelSettings.add(accelState);

        learnedSets = new JTextArea();
        learnedSets.setBounds(50,400,500,100);
        accelSettings.add(learnedSets);

        this.add(accelSettings);


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                GlobalListener.closeListeners();
                System.out.println("Closing");
                System.exit(0);
            }
        });
    }

    public void setState(String input){
        accelState.setText(input);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==test) {
            setTest();
        }else if(e.getSource()==learn){
            String potName = JOptionPane.showInputDialog("Enter Name");
            if(!learnedSets.getText().contains(potName)) {
                test.setEnabled(true);
                learn.setEnabled(false);
                reInforce.setEnabled(false);
                dataName = potName;
                isLearning = 'g';
                setState("Ready to learn " + dataName);
            }
        }else if(e.getSource()==reInforce){
            String[] options = new String[allgest.size()];
            int cnt =0;
            for(Gesture g:allgest){
                options[cnt] = g.myName;
                cnt++;
            }

            JComboBox optionList = new JComboBox(options);
            JOptionPane.showMessageDialog(null, optionList, "Choose which",JOptionPane.QUESTION_MESSAGE);

            dataName =(String)optionList.getSelectedItem();
            isLearning='r';

            test.setEnabled(true);
            learn.setEnabled(false);
            reInforce.setEnabled(false);
            setState("Ready to reinforce " + dataName);
        }

    }

    public void setTest(){
        test.setEnabled(false);
        learn.setEnabled(true);
        reInforce.setEnabled(true);
        isLearning= 't';
    }

    public void showGestures(LinkedList<Gesture> mySets){
        String res="";
        for(Gesture g:mySets){
            res += g.myName + " : " + g.toString();
        }
        learnedSets.setText(res);
        setTest();
        setState("Awaiting information");
    }



}
