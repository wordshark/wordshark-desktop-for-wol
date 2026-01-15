/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
/**
 *
 * @author White Space
 */
public class homonym implements sharkImage.imagefunc {
//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkImage.saveTextImage svt[];
    int defaultborder = 2;
    int ringborder = defaultborder;
    int lastsmallw1;
    String text = "h";
    int lastw1 = -1;
    Font f;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public void setup(String[] arg) {

//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for(int i=1;i<arg.length;++i) {
        if(arg[i].startsWith("homonymborder=")) {
          ringborder = Integer.parseInt(arg[i].substring(arg[i].indexOf("=")+1));
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }

  public void paint(Graphics g, int x1, int y1, int w1, int h1) {
      Graphics2D g2d = ((Graphics2D)g);
      g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

    
    if(ringborder>0){
        g.setColor(Color.red);
        g.fillOval(x1, y1, w1, w1);
    }
    g.setColor(sharkStartFrame.col1);
    int smallw1 = w1-(2*ringborder);
    g.fillOval(x1+ringborder,y1+ringborder, smallw1, smallw1);
    if(w1!=lastw1 || lastsmallw1!=smallw1){
        f = sharkGame.sizePlainFont(new String[] {text},smallw1, smallw1, sharkStartFrame.MAXFONTPOINTS_4, sharkStartFrame.defaultfontname.deriveFont(Font.BOLD));
        lastw1 =w1;
        lastsmallw1 = smallw1;
    }
  //  Font f = sharkStartFrame.defaultfontname.deriveFont((float)40);
    g.setFont(f);
    FontMetrics fm = g.getFontMetrics(f);
    g.setColor(Color.black);
//    int charh = (int)fm.getStringBounds(text, g).getHeight();
    int charh = fm.getAscent();
    g.setColor(Color.black);
    g2d.drawString(text, x1+(w1/2)-(fm.stringWidth(text)/2), y1+ringborder+charh);

    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
  }

//startPR2010-04-08^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public JDialog edit(sharkImage.saveTextImage[] sv) {
     svt = sv;
     return new edit();
  }

  class edit extends JDialog {
      SpinnerNumberModel model = new SpinnerNumberModel(ringborder, 0, 15, 1);
      final JSpinner js = new JSpinner(model);


    edit() {
      super(sharkStartFrame.mainFrame);
      this.setTitle("Settings for the homonym symbol");
      this.setResizable(false);
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      setBounds(new Rectangle(0,
            sharkStartFrame.screenSize.height/6,
            sharkStartFrame.screenSize.width/2,
            sharkStartFrame.screenSize.height*2/3));

      js.addChangeListener(new ChangeListener() {
        public void stateChanged(ChangeEvent evt) {
          ringborder = ( (Number) ( ( (JSpinner) evt.getSource()).
                                   getValue())).intValue();
          save();
        }
      });

      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints grid1 = new GridBagConstraints();
      grid1.fill = GridBagConstraints.NONE;
      grid1.weightx = 1;
      grid1.weighty = 1;
      grid1.gridx = -1;
      grid1.gridy = 0;
      getContentPane().add(new JLabel("Set border size:"),grid1);
      getContentPane().add(js,grid1);
      validate();
      setVisible(true);
    }

    void save() {
      String s[] = new String[]{svt[0].text[0]};
      if(defaultborder!=ringborder) s = u.addString(s,"homonymborder="+ringborder);
      svt[0].text = s;
    }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public boolean keepnodeorder() {return false;}

  public void interpolate(String from[], String to[], long start, long end,long currtime) {

  }

  public Rectangle getBounds() {return null;}
}
