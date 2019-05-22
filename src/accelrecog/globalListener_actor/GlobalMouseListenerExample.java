package accelrecog.globalListener_actor;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import java.awt.*;

public class GlobalMouseListenerExample implements NativeMouseInputListener {
    GlobalListener listener;
    public GlobalMouseListenerExample(GlobalListener globalListener){
        super();
        listener = globalListener;
    }

    public void nativeMouseClicked(NativeMouseEvent e) {

    }

    public void nativeMousePressed(NativeMouseEvent e) {
        listener.userActions.add(new ActionR(AType.MOUSEPRESS,e.getButton()));
        System.out.println(e.getButton());
    }

    public void nativeMouseReleased(NativeMouseEvent e) {
        listener.userActions.add(new ActionR(AType.MOUSERELEASE,e.getButton()));
        System.out.println(e.getButton());
    }

    public void nativeMouseMoved(NativeMouseEvent e) {
        listener.userActions.add(new ActionR(AType.MOUSEMOVE,new Point(e.getX(),e.getY())));
    }

    public void nativeMouseDragged(NativeMouseEvent e) {
        listener.userActions.add(new ActionR(AType.MOUSEMOVE,new Point(e.getX(),e.getY())));
    }


}