package breaktime1.Input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
 
public class MouseManager implements MouseMotionListener, MouseListener{
     
    public static int mouseX;
    public static int mouseY;
     
    public static boolean isClicked;
     
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
    @Override
    public void mouseClicked(MouseEvent e) {
         
    }
    @Override
    public void mouseEntered(MouseEvent e) {
         
    }
    @Override
    public void mouseExited(MouseEvent e) {
         
    }
    @Override
    public void mousePressed(MouseEvent e) {
        isClicked = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        isClicked = false;
    }
 
}
