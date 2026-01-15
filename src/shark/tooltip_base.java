package shark;

import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//A tooltip object is created when the mouse moves over the object it is associated with.
//It is destroyed once the mouse is not over the object. It does not exist between
//different mouse over events.
public class tooltip_base extends BasicToolTipUI {
  static tooltip_base singleinstance = new tooltip_base();
  public static ComponentUI createUI(JComponent c) {return singleinstance;}
  static final int margin = 4;
  /**
   * Count of how many times the tooltip has been painted
   */
  public static int painted;

  public static Timer timer;
  public static JComponent c;
  static MouseEvent me;

  public static boolean tooltipsActive;

  public tooltip_base(){
      super();
      timer = (new Timer(shark.tooltipdelay, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(!tooltipsActive)return;
              ToolTipManager.sharedInstance().setEnabled(true);
              ToolTipManager.sharedInstance().mouseMoved(new MouseEvent(c,1,System.currentTimeMillis(),
                                        MouseEvent.MOUSE_MOVED,me.getX(),me.getY(),
                                        0,false,0));
          }
      }));
      timer.setRepeats(false);
  }

  static public void off() {
      ToolTipManager.sharedInstance().setEnabled(false);
      timer.stop();
  }

  static public void on(JComponent j, MouseEvent m) {
      if(!tooltipsActive){
          ToolTipManager.sharedInstance().setEnabled(false);
          timer.stop();
          return;
      }
      c=  j;
      me = m;
      timer.start();
  }

  public void paint(Graphics g, JComponent  c) {
     ++painted;

     FontMetrics m = runMovers.fm[0];
     g.setColor(sharkStartFrame.textbgcolor);
     g.fillRect(0,0,c.getWidth(),c.getHeight());
     g.setColor(sharkStartFrame.textfgcolor);
     String s[] = u.splitString(((JToolTip)c).getTipText());
     int len = s.length;
     int y = m.getAscent() + margin;
     int h = m.getHeight();
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
     for(short i = 0;i<len;++i,y+=h) {
        u.drawString(g,runMovers.f,runMovers.fm,s[i],margin,y);
     }
     ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
  }
  public Dimension getPreferredSize(JComponent c) {
     Dimension d  = u.getdim2(u.splitString(((JToolTip)c).getTipText()), runMovers.fm, margin);
     return new Dimension(d.width,d.height+margin*2);
  }
}
