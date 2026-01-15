package shark;

import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 * <p>Title: Your Product Name</p>
 * <p>Description: Your description</p>
 * <p>Copyright: Copyright (c) 1997</p>
 * <p>Company: Your Company</p>
 * @author Your Name
 * @version 1.0
 */

public class IntroFrame_base extends JFrame {

  Font f;
//  Font orifont;
  int bordermain;
  int border = 20;
  GridBagConstraints grid2;
  private JPanel base;
  String htmlfonttext;
  Color outercolor = sharkStartFrame.col1;
  Color innercolor = sharkStartFrame.col2;
  public boolean running;
  boolean closeonexit = true;
 // boolean preStartUp;
  JFrame thisframe = this;

  public IntroFrame_base(boolean beforeInit) {
//    preStartUp = beforeInit;
    running = true;
    setIconImage(Toolkit.getDefaultToolkit().createImage(getClass().getResource("/sharkicon.gif")));
//    orifont = getContentPane().getFont();
//    f = orifont.deriveFont(Font.BOLD, (float) 14);
    f = getContentPane().getFont();
 //   if(preStartUp){
 //     setMenuFont(f);
 //   }
    htmlfonttext = "<font face='" + f.getName() + "' style='font-size:" + String.valueOf(f.getSize()) + "px'>";

    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    bordermain = (int) env.getMaximumWindowBounds().getHeight() * 1 / 20;
    int borderplus = bordermain + border;

    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    getContentPane().setLayout(new GridBagLayout());
    basePanel jpBase = new basePanel(new GridBagLayout());
    base = new JPanel(new GridBagLayout());
    base.setBackground(innercolor);

    grid2 = new GridBagConstraints();
    grid2.fill = grid2.BOTH;
    grid2.gridx = 0;
    grid2.gridy = 0;
    grid2.weightx = 1;
    grid2.weighty = 1;
    grid2.insets = new Insets(borderplus, borderplus, borderplus, borderplus);
    jpBase.add(base, grid2);
    grid2.insets = new Insets(0, 0, 0, 0);
    getContentPane().add(jpBase, grid2);



    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        if(closeonexit)
          System.exit(0);
      }
    });
    if(!ChangeScreenSize_base.isActive)
        setBounds(env.getMaximumWindowBounds());
    else 
        setBounds(u2_base.adjustBounds(new Rectangle((int)sharkStartFrame.originalbounds.x, (int)sharkStartFrame.originalbounds.y, 
                (int)sharkStartFrame.originalbounds.width, (int)sharkStartFrame.originalbounds.height)));
    this.setResizable(false);


  }

  public void setMenuFont(Font f) {
    UIDefaults ui = UIManager.getDefaults();
    Enumeration keys = ui.keys();
    while (keys.hasMoreElements()) {
      String sss = keys.nextElement().toString();
      if (sss.indexOf(".font") > 0) {
        UIManager.put(sss, f);
      }
    }
  }

  public void dispose() {
//    if(preStartUp)
//      setMenuFont(orifont);
    running = false;
    super.dispose();
    if(closeonexit)
      System.exit(0);
  }

  public void addToBase(Component c, GridBagConstraints grid) {
    base.add(c,grid);
  }


  public class basePanel extends JPanel {
    public basePanel(GridBagLayout gbl) {
      super(gbl);
      this.setBackground(innercolor);
    }
    public void paintComponent(Graphics g) {
      ( (Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g.setColor(outercolor);
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
      g.setColor(innercolor);
      g.fillRoundRect(0 + bordermain, 0 + bordermain, this.getWidth() - (bordermain * 2), this.getHeight() - (bordermain * 2), bordermain, bordermain);
    }
  }

  public void setColor() {
    setPanelColor(base.getComponents());
  }


  private void setPanelColor(Component c[]) {
    for (int i = 0; i < c.length; i++) {
      JPanel jp = null;
      JButton jb = null;
      if (c[i] instanceof JPanel) {
        (jp = ( (JPanel) c[i])).setOpaque(false);
        Component cs[] = jp.getComponents();
        setPanelColor(cs);
      }
      else if(shark.macOS && c[i] instanceof JButton){
        (jb = ( (JButton) c[i])).setOpaque(false);
       Component cs[] = jb.getComponents();
       setPanelColor(cs);
      }
    }
  }
}
