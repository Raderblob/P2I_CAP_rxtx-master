package accelrecog.globalListener_actor;

import accelrecog.Shortcut;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.util.LinkedList;

public class GlobalListener {
    private GlobalKeyListenerExample keyListener;
    private GlobalMouseListenerExample mouseListener;
    public LinkedList<ActionR> userActions = new LinkedList<>();
    public Shortcut myShortCut;

    public GlobalListener(){
        keyListener = new GlobalKeyListenerExample(this);
        mouseListener = new GlobalMouseListenerExample(this);
        GlobalScreen.addNativeKeyListener(keyListener);
        GlobalScreen.addNativeMouseListener(mouseListener);
        GlobalScreen.addNativeMouseMotionListener(mouseListener);
    }
    public static void closeListeners(){
        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
    }
    public void endIt(){
        if(myShortCut!=null){
            myShortCut.stopRecord();
            myShortCut=null;
        }
    }
}
