package accelrecog;

import accelrecog.globalListener_actor.GlobalListener;

import java.util.ArrayList;
import java.util.LinkedList;

public class Gesture {
    public LinkedList<DataSet> mySets = new LinkedList<>();
    public String myName;
    public Shortcut myShortCut;
    public Gesture(ArrayList<Data> data, String n, GlobalListener globalListener){
        myName = n;
        mySets.add(new DataSet(data,n+"0"));
        myShortCut = new Shortcut(globalListener);
    }

    public void reinforce(ArrayList<Data> data){
        mySets.add(new DataSet(data,myName + mySets.size()));
    }
    public double readDistance(DataSet testSet){
        double res=0;
        for(int i =0;i<mySets.size();i++){
            res+=mySets.get(i).compareTo(testSet);
        }
        res/=mySets.size();
        return res;
    }

    public String toString(){
        String res="";

        for(DataSet ds:mySets){
            res+=ds.toString() + "\n";
        }

        return res;
    }
}
