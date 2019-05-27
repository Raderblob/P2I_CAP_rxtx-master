package accelrecog;

import accelrecog.globalListener_actor.GlobalListener;
import org.jnativehook.GlobalScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;

public class Interface extends JFrame implements ActionListener {
    private Panel accelSettings;
    private JButton test, learn, reInforce, deleteMouvement;
    private JLabel accelState;
    private JList learnedSets;
    private DefaultListModel listOfGest = new DefaultListModel();

    public char isLearning = 't';
    public String userChoice;
    public String dataName;
    public boolean activated = true;

    private LinkedList<Gesture> allgest;

    public Interface(LinkedList<Gesture> aGesture) {
        super("Recog Interface");
        allgest = aGesture;

        this.setBounds(0, 0, 600, 600);
        this.setLayout(null);
        accelSettings = new Panel();
        accelSettings.setLayout(null);
        accelSettings.setBackground(Color.black);
        accelSettings.setBounds(0, 0, 600, 600);

        test = new JButton("Cancel");
        test.setBackground(Color.red);
        test.setBounds(50, 50, 200, 100);
        test.setEnabled(false);
        test.addActionListener(this);
        accelSettings.add(test);

        learn = new JButton("Learn more");
        learn.setBackground(Color.GREEN);
        learn.setBounds(50, 250, 200, 100);
        learn.setEnabled(true);
        learn.addActionListener(this);
        accelSettings.add(learn);

        reInforce = new JButton("Reinforce existing Gesture");
        reInforce.setBackground(Color.GREEN);
        reInforce.setBounds(300, 250, 200, 100);
        reInforce.setEnabled(true);
        reInforce.addActionListener(this);
        accelSettings.add(reInforce);


        accelState = new JLabel("Awaiting information");
        accelState.setForeground(Color.red);
        accelState.setBounds(50, 175, 300, 45);
        accelSettings.add(accelState);

        learnedSets = new JList(listOfGest);
        learnedSets.setBounds(50, 400, 500, 100);
        accelSettings.add(learnedSets);

        deleteMouvement = new JButton("Delete Mouvement");
        deleteMouvement.setBackground(Color.red);
        deleteMouvement.setBounds(300, 50, 200, 100);
        deleteMouvement.setEnabled(true);
        deleteMouvement.addActionListener(this);
        accelSettings.add(deleteMouvement);

        this.add(accelSettings);


        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent winEvt) {
                GlobalListener.closeListeners();
                System.out.println("Closing");
                System.exit(0);
            }
        });
    }

    public void setState(String input) {
        accelState.setText(input);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == test) {
            setTest();
        } else if (e.getSource() == learn) {
            String potName = JOptionPane.showInputDialog("Enter Name");
            JComboBox optionList = new JComboBox(new String[] {"MACRO","COMMAND"});
            JOptionPane.showMessageDialog(null, optionList, "Choose which", JOptionPane.QUESTION_MESSAGE);
            userChoice = (String) optionList.getSelectedItem();
            if (!alreadyExists(potName)) {
                test.setEnabled(true);
                learn.setEnabled(false);
                reInforce.setEnabled(false);
                dataName = potName;
                isLearning = 'g';
                setState("Ready to learn " + dataName);
            } else {
                System.out.println("Name already chosen");
            }
        } else if (e.getSource() == reInforce) {
            String[] options = new String[allgest.size()];
            int cnt = 0;
            for (Gesture g : allgest) {
                options[cnt] = g.myName;
                cnt++;
            }

            JComboBox optionList = new JComboBox(options);
            JOptionPane.showMessageDialog(null, optionList, "Choose which", JOptionPane.QUESTION_MESSAGE);

            dataName = (String) optionList.getSelectedItem();
            isLearning = 'r';

            test.setEnabled(true);
            learn.setEnabled(false);
            reInforce.setEnabled(false);
            setState("Ready to reinforce " + dataName);
        } else if (e.getSource() == deleteMouvement) {
            if (learnedSets.getSelectedIndex() != -1) {
                allgest.remove(learnedSets.getSelectedIndex());
            } else {
                System.out.println("Nothing selected");
            }
            showGestures();
        }

    }

    private boolean alreadyExists(String strToTest) {
        for (Gesture g : allgest) {
            if (g.myName.equals(strToTest)) {
                return true;
            }
        }

        return false;
    }

    public void setTest() {
        if (activated) {
            test.setEnabled(false);
            learn.setEnabled(true);
            reInforce.setEnabled(true);
            isLearning = 't';
        }
    }

    public void desactivate() {
        activated = false;
        test.setEnabled(false);
        learn.setEnabled(false);
        reInforce.setEnabled(false);
    }

    public void activate() {
        activated = true;
        setTest();
    }

    public void showGestures() {
        listOfGest.clear();
        for (Gesture g : allgest) {
            listOfGest.addElement(g.myName + " reinforces : " + g.mySets.size() + " : " + g.toString());
        }
        setTest();
        setState("Awaiting information");
    }


}
