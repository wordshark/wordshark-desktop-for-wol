/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.awt.*;
import javax.swing.*;
//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.awt.event.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

/**
 *
 * @author White Space
 */
public class pasttense implements sharkImage.imagefunc {
    sharkImage.imagefunc arrowpart;
    sharkImage.imagefunc clockpart;
//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkImage.saveTextImage svt[];
    boolean noshowbackground;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public void setup(String[] arg) {
      Class funcClassArrow;
      Class funcClassClock;
      try {
         funcClassArrow = Class.forName("shark.arrow");
         funcClassClock = Class.forName("shark.clock_base");
       }
       catch(ClassNotFoundException ee) {
          return;
       }
       try {
          arrowpart = (sharkImage.imagefunc) funcClassArrow.newInstance();
          clockpart = (sharkImage.imagefunc) funcClassClock.newInstance();
        }
       catch (InstantiationException ee) {                                //Catch 6
         return;
       }
       catch (IllegalAccessException ee) {                                //Catch 6
         return;
      }
//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(int i=1;i<arg.length;++i) {
        if(arg[i].startsWith("noshowbackground")) {
          noshowbackground = true;
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      arrowpart.setup(new String[]{"","","","","50","50","0","0","","-1"});
      clockpart.setup(new String[]{"", "marknum=0", "markint=0", "time=4:0:0"});
  }

  public void paint(Graphics g, int x1, int y1, int w1, int h1) {
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    int clocky = y1+w1*2/9;
    int arrowx = x1-w1*18/100;
    int arroww = w1*23/24;
    int arrowh = w1*23/24;
    if(!noshowbackground){
        g.setColor(Color.lightGray);
        int squarew = w1*10/7;
        int squareh = w1*20/13;
        int squaremargin = w1/5;
        g.fillRect(arrowx-squaremargin, y1-(squaremargin*2),
                squarew+(squaremargin*2), squareh+(squaremargin*2));
    }
    clockpart.paint(g, x1, clocky, w1, w1);
    arrowpart.paint(g, arrowx, y1, arroww, arrowh);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
  }

//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public JDialog edit(sharkImage.saveTextImage[] sv) {
     svt = sv;
     return new edit();
  }

  class edit extends JDialog {
    JCheckBox jdig = new JCheckBox("Show grey backgound?",!noshowbackground);

    edit() {
      super(sharkStartFrame.mainFrame);
      this.setTitle("Settings for the pasttense symbol");
      this.setResizable(false);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(0,
            sharkStartFrame.screenSize.height/6,
            sharkStartFrame.screenSize.width/2,
            sharkStartFrame.screenSize.height*2/3));
      jdig.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           noshowbackground = !jdig.isSelected();
           save();
        }
      });
      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints grid1 = new GridBagConstraints();
      grid1.fill = GridBagConstraints.NONE;
      grid1.weightx = 1;
      grid1.weighty = 1;
      grid1.gridx = 0;
      grid1.gridy = -1;
      getContentPane().add(jdig,grid1);
      validate();
      setVisible(true);
    }

    void save() {
      String s[] = new String[]{svt[0].text[0]};
      if(noshowbackground) s = u.addString(s,"noshowbackground");
      svt[0].text = s;
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public boolean keepnodeorder() {return false;}

  public void interpolate(String from[], String to[], long start, long end,long currtime) {

  }

  public Rectangle getBounds() {return null;}
}
