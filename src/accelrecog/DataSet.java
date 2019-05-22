package accelrecog;

import accelrecog.dtw.DTW;
import accelrecog.dtw.TimeWarpInfo;
import accelrecog.timeseries.TimeSeries;
import accelrecog.util.DistanceFunction;
import accelrecog.util.DistanceFunctionFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class DataSet {
    public ArrayList<Data> myData;
    public String myId;

    public DataSet(ArrayList<Data> data,String n){
        myData=data;
        myId=n;
        this.normalize();
    }
    public int myLength(){
        return myData.size();
    }
    public double compareTo(DataSet otherSet){
        double res =0;



        String a = myId+".csv";
        String b = otherSet.myId+".csv";

        try {
            this.generateCSV();
            otherSet.generateCSV();
        }catch(Exception e){

        }


        final TimeSeries tsI = new TimeSeries(a, false, false, ',');
        final TimeSeries tsJ = new TimeSeries(b, false, false, ',');

        final DistanceFunction distFn;

        distFn = DistanceFunctionFactory.getDistFnByName("EuclideanDistance");


        final TimeWarpInfo info = DTW.getWarpInfoBetween(tsI, tsJ, distFn);

        return info.getDistance();
        //temporal compression to make the variations in acceleration smoother
        //dtw
        //compressive sensing
        //affinity propogation
        //k closest nei
        //uwave
    }

    public void generateCSV() throws IOException {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(myId+".csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        for (Data donnée: myData
             ) {
            builder.append(donnée.x+","+donnée.y+","+donnée.z + "," + donnée.a + "," + donnée.b + "," + donnée.c);
            builder.append('\n');
        }

        pw.write(builder.toString());
        pw.close();

    }

    private void normalize(){
        double max = 0;
        double normeSqrd = 0;
        for(Data donnee: myData){
            normeSqrd = Math.pow(donnee.x,2)+Math.pow(donnee.y,2)+Math.pow(donnee.z,2);
            max=Math.max(normeSqrd,max);
        }
        for(int i = 0;i<myData.size();i++){
            myData.get(i).x=myData.get(i).x/Math.sqrt(max);
            myData.get(i).y=myData.get(i).y/Math.sqrt(max);
            myData.get(i).z=myData.get(i).z/Math.sqrt(max);
        }

    }

    public String toString(){
        String res ="";
        for(int i = 0;i<myData.size()-1;i++){
            res += myData.get(i).toString() + ";";
        }
        res+=myData.get(myData.size()-1).toString();
        return res;
    }

}
