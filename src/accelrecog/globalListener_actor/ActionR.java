package accelrecog.globalListener_actor;

import java.awt.*;

public class ActionR {
    public AType myType;
    public Point mousePos;
    public int code = 0;
    public String cmd;
    public ActionR(AType type,Point mousePos){
        myType = type;
        this.mousePos = mousePos;
    }
    public ActionR(AType type,int code) {
        myType = type;
        this.code = code;
    }
    public ActionR(String nCmd){
        cmd = nCmd;
        myType = AType.COMMAND;
    }
}



