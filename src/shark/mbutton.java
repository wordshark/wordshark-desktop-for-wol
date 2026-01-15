package shark;

import java.awt.*;
import javax.swing.*;

public class mbutton extends JButton {
  public final static int TYPE_NOMIKE = 0;
  int type = -1;
  static final int margin = 4;
  public boolean round;
  Color bordercol,fillcolor;  // if round set: border and fill color for the area left outside the rounded border
  public String text;
  Color cols[];


  String off;
  String on;
  Image imic;
  Image im;
  Image imic_off;
  Image im_off;  
  
  Image activeIm;
  boolean ison;
  
  public mbutton(String s) { 
      super();
      text=s;
  }
  public mbutton(String s, int typ) {
      super();
      text=s;
      type = typ;
    if(type==TYPE_NOMIKE){
        off = u.gettext("off", "label").toUpperCase();
        on = u.gettext("on", "label").toUpperCase();
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "recordingAVAIL_il48.png");
        im_off = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "recordingUNAVAIL_il48.png");     
        MediaTracker tracker=new MediaTracker(this);
        tracker.addImage(im,1);
        tracker.addImage(im_off,2);
        try{
            tracker.waitForAll();
        }
        catch (InterruptedException ie)
        {
        }     
        

    }
  }  
  
  
  u.showbuttonnotes popup;
  
  public void paint(Graphics g) {
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(type==TYPE_NOMIKE && imic == null){
      imic = (new ImageIcon(im.getScaledInstance(-1,(getHeight()*6/10),Image.SCALE_SMOOTH))).getImage();
      imic_off = (new ImageIcon(im_off.getScaledInstance(-1,(getHeight()*6/10),Image.SCALE_SMOOTH))).getImage();
    }
    
    
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
     int longest = -1;
     if(type==TYPE_NOMIKE){    
           for(int n = 0; n < s.length; n++){
               if(m.stringWidth(s[n])>longest)longest = m.stringWidth(s[n]);
           }
       }
     for(short i = 0;i<len;++i,y+=h) {
       if(cols != null && cols.length > i && cols[i] != null)  g.setColor(cols[i]);
       if(type==TYPE_NOMIKE){
         g.drawString(s[i], (getWidth()/2)-(longest), y);
       }
       else
         g.drawString(s[i],getWidth()/2-m.stringWidth(s[i])/2,y);
     }
     if(type==TYPE_NOMIKE){
         int xx = (getWidth()/2)+longest-(imic.getWidth(this));
         int yy = getHeight()*1/10;
         int yyy = getHeight()*3/20;
        g.drawImage((ison?imic:imic_off), xx, yy, this);
        g.setColor(Color.black);
        Dimension d = this.getPreferredSize(); 
        g.drawString((ison?on:off),xx+((ison?imic:imic_off).getWidth(this)/2) -(m.stringWidth(on)/2), this.getHeight()-yyy);
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
       if (text.indexOf('|') < 0) {
         Dimension d = new Dimension(u.getdim(text, getFontMetrics(getFont()), margin));
         d.setSize(d.width, d.height + macMargin);
         return d;
       }
       else {
         Dimension d = new Dimension(u.getdim2(u.splitString(text),
                                               getFontMetrics(getFont()), margin));
         d.setSize(d.width, d.height + macMargin);
         return d;
       }
     }
     //if running on Windows
     else{
       if (text.indexOf('|') < 0)
         return u.getdim(text, getFontMetrics(getFont()), margin);
       else
         return u.getdim2(u.splitString(text), getFontMetrics(getFont()), margin);
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
      if (text.indexOf('|') < 0) {
        Dimension d = new Dimension(u.getdim(text, getFontMetrics(getFont()), margin));
        d.setSize(d.width, d.height + macMargin);
        return d;
      }
      else {
        Dimension d = new Dimension(u.getdim2(u.splitString(text),
                                              getFontMetrics(getFont()), margin));
        d.setSize(d.width, d.height + macMargin);
        return d;
      }
    }
    //if running on Windows
    else{
      if (text.indexOf('|') < 0)
        return u.getdim(text, getFontMetrics(getFont()), margin);
      else
        return u.getdim2(u.splitString(text), getFontMetrics(getFont()), margin);
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
  
  public void setOn(boolean on){
      ison = !on;
  }
}
