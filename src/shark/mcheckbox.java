package shark;

import java.awt.*;
import javax.swing.*;

public class mcheckbox extends JCheckBox {
  static final int margin = 4;
  public boolean round;
  Color bordercol,fillcolor;  // if round set: border and fill color for the area left outside the rounded border
  public String text;
  Color cols[];
  int xmarg;
  int wmarg;

  public mcheckbox(String s) {
      super();
      text=s;
      Insets ins = UIManager.getInsets("CheckBox.margin");
      int labelx = UIManager.getIcon("CheckBox.icon").getIconWidth();
      xmarg = labelx;
      wmarg = labelx + ins.left + ins.right;
  }
  u.showbuttonnotes popup;
  public void paint(Graphics g) {
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(round) {
      g.setColor(fillcolor);
      g.fillRect(0, 0, getWidth(), getHeight());
      g.setColor(getBackground());
      int cor = getHeight() / 2;
      g.fillRoundRect(0, 0, getWidth(), getHeight(), cor, cor);
      g.setColor(bordercol);
      g.drawRoundRect(0, 0, getWidth(), getHeight(), cor, cor);
      g.setFont(getFont());
    }
    else      super.paint(g);
     Font f = getFont();
     FontMetrics m = getFontMetrics(f);
     if(isEnabled())    g.setColor(getForeground());
     else g.setColor(getBackground().darker());
     String s[] = u.splitString(text);
     int len = s.length;
     int y = m.getAscent()+(getHeight()-len*m.getHeight())/2;
     int h = m.getHeight();
     for(short i = 0;i<len;++i,y+=h) {
       if(cols != null && cols.length > i && cols[i] != null)  g.setColor(cols[i]);
        g.drawString(s[i],getWidth()/2-m.stringWidth(s[i])/2 + xmarg ,y);
     }
  }
  public Dimension getPreferredSize() {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(text==null){
          return super.getPreferredSize();
      }
     // if running on a Macintosh
     if (shark.macOS) {
       // the extra height added to the button needed to fit in button text
       int macMargin = 10;
       Dimension d;
       if (text.indexOf('|') < 0) {
         d = new Dimension(u.getdim(text, getFontMetrics(getFont()), margin));
       }
       else {
         d = new Dimension(u.getdim2(u.splitString(text),
                                               getFontMetrics(getFont()), margin));
       }
       d.setSize(d.width+wmarg, d.height + macMargin);
       return d;
     }
     //if running on Windows
     else{
       Dimension d;
       if (text.indexOf('|') < 0){
         d = u.getdim(text, getFontMetrics(getFont()), margin);
         }
       else{
         d = u.getdim2(u.splitString(text), getFontMetrics(getFont()), margin);
         }
       d.setSize(d.width+wmarg, d.height);
       return d;
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  public Dimension getMinimumSize() {
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(text==null){
          return super.getMinimumSize();
      }
    // if running on a Macintosh
    if (shark.macOS) {
      // the extra height added to the button needed to fit in button text
      int macMargin = 10;
      Dimension d;
      if (text.indexOf('|') < 0) {
        d = new Dimension(u.getdim(text, getFontMetrics(getFont()), margin));
      }
      else {
        d = new Dimension(u.getdim2(u.splitString(text),
                                              getFontMetrics(getFont()), margin));
      }
      d.setSize(d.width+wmarg, d.height + macMargin);
      return d;
    }
    //if running on Windows
     else{
       Dimension d;
       if (text.indexOf('|') < 0){
         d = u.getdim(text, getFontMetrics(getFont()), margin);
         }
       else{
         d = u.getdim2(u.splitString(text), getFontMetrics(getFont()), margin);
         }
       d.setSize(d.width+wmarg, d.height);
       return d;
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  public void setText(String s) {
     String oldtext = text;
     text=s;
     if(oldtext != null) {
      Dimension d = getMinimumSize(), d2 = getSize();
      if(d.width > d2.width || d.height > d2.height) setSize(d);
     }
  }
}
