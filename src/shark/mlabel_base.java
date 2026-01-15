package shark;
import java.awt.*;
import javax.swing.*;

public class mlabel_base extends JLabel {
  static final int margin = 4;
  public boolean varyfont;
  public boolean checkwidth;
  public boolean centre;
  public mlabel_base(String s) {
    super(s);
    setOpaque(true);
  }
  public mlabel_base(String s,boolean tooltip) {
    super(s);
    setFont(runMovers.tooltipfont);
    varyfont = (s.indexOf('^') >= 0);
    setOpaque(true);
  }
  public void paint(Graphics g) {
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String s2;
     Font f1 = getFont();
     FontMetrics m = getFontMetrics(f1);
     if(this.isOpaque()) {
        g.setColor(getBackground());
        g.fillRect(0,0,getWidth(),getHeight());
     }
     g.setColor(getForeground());
     String s[] = u.splitString(s2=getText());
     int len = s.length;
     int y = m.getAscent();
     int h = m.getHeight();
     int ww = getWidth();
     g.setFont(f1);
     for(short i = 0;i<len;++i,y+=h) {
         if(!varyfont)  g.drawString(s[i],centre?getWidth()/2 - m.stringWidth(s[i])/2 : margin,y);
        else {
          if(checkwidth && u.getdim2(s, runMovers.fm, margin).width >ww) {
            g.setFont(runMovers.smalltooltipfont);
            u.drawString(g, runMovers.smallf, runMovers.smallfm, s[i], margin, y);
          }
          else u.drawString(g, runMovers.f, runMovers.fm, s[i], margin, y);
        }
     }
  }
  public Dimension getPreferredSize() {
     if(!varyfont)
       return u.getdim2(u.splitString(getText()),getFontMetrics(getFont()), margin);
     else return u.getdim2(u.splitString(getText()),runMovers.fm, margin);
  }
  public Dimension getMinimumSize() {
     if(!varyfont)
      return u.getdim2(u.splitString(getText()),getFontMetrics(getFont()), margin);
     return u.getdim2(u.splitString(getText()),runMovers.fm, margin);
  }
}
