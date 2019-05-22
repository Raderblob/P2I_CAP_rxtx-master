package accelrecog;

import accelrecog.dtw.TimeWarpInfo;
import accelrecog.timeseries.TimeSeries;
import accelrecog.util.DistanceFunction;
import accelrecog.util.DistanceFunctionFactory;

public class Data {
    public double x,y,z;
    public byte a,b,c;

    public Data(double x, double y , double z,byte a,byte b, byte c){
        this.x=x;
        this.y=y;
        this.z=z;
        this.a=a;
        this.b=b;
        this.c=c;
    }
    public Data(double x, double y , double z){
        this.x=x;
        this.y=y;
        this.z=z;
    }

    public double compareTo(Data d){

        return Math.sqrt( Math.pow(x-d.x,2) + Math.pow(y-d.y,2)+Math.pow(z-d.z,2));

    }

    public String toString(){
        String res="";
        res = x + " " + y + " " + c + " , " + a+b+c;
        return res;
    }
}
