package shark;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <p>Title: Your Product Name</p>
 * <p>Description: Your description</p>
 * <p>Copyright: Copyright (c) 1997</p>
 * <p>Company: Your Company</p>
 * @author Your Name
 * @version 1.0
 */

public class progress_base extends JDialog{
  String title;
  String message;
  Rectangle r;
  boolean wantCancelButton;
  JButton btCancel;
  boolean startVisible = true;
  JDialog thisprogress = this;

  public progress_base(JDialog owner, String heading, String mess, Rectangle rect) {
    super(owner);
    title = heading;
    message = mess;
    r = rect;
    wantCancelButton = false;
    init();
  }

  public progress_base(JDialog owner, boolean startvisible, String heading, String mess, Rectangle rect) {
    super(owner);
    title = heading;
    message = mess;
    r = rect;
    wantCancelButton = false;
    startVisible = startvisible;
    init();
  }

  public progress_base(JFrame owner, String heading, String mess, Rectangle rect) {
    super(owner);
    title = heading;
    message = mess;
    r = rect;
    wantCancelButton = false;
    init();
  }
  public progress_base(JDialog owner, String heading, String mess, Rectangle rect, boolean wantcancel) {
    super(owner);
    title = heading;
    message = mess;
    r = rect;
    wantCancelButton = wantcancel;
    init();
  }
  public progress_base(JFrame owner, String heading, String mess, Rectangle rect, boolean wantcancel) {
    super(owner);
    title = heading;
    message = mess;
    r = rect;
    wantCancelButton = wantcancel;
    init();
  }

  void init(){
    setResizable(false);
    setBounds(r);
    setTitle(title);
    getContentPane().setLayout(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    JProgressBar pBar;
    JPanel mainpanel = new JPanel(new GridBagLayout());
    JLabel label = new JLabel(message);
    label.setFont(sharkStartFrame.treefont);
    int hborder = (int)r.getHeight()/8;
    int wborder = (int)r.getWidth()/6;
    pBar = new JProgressBar();
    pBar.setIndeterminate(true);
    grid.gridx = 0;
    grid.gridy = -1;
    grid.weighty = 0;
    grid.weightx = 1;
    grid.fill = GridBagConstraints.NONE;
    mainpanel.add(label, grid);
    grid.fill = GridBagConstraints.HORIZONTAL;
    grid.weighty = 1;
    mainpanel.add(pBar, grid);
    if(wantCancelButton){
        grid.fill = GridBagConstraints.NONE;
        btCancel = new JButton(u.gettext("cancel", "label"));
        btCancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            System.exit(200);
        }
        });
        mainpanel.add(btCancel, grid);
    }
    grid.gridy = 0;
    grid.fill = GridBagConstraints.BOTH;
    grid.insets = new Insets(hborder, wborder, hborder, wborder);
    getContentPane().add(mainpanel, grid);
    if(startVisible)
        setVisible(true);
  }
  public void disposeIn(int t){
              Timer tnTimer = new Timer(t, new ActionListener() {
                public void actionPerformed(ActionEvent e) { //Event 7.1.1
                  thisprogress.dispose();
                }
              });
              tnTimer.setRepeats(false);
              tnTimer.start();      
  }
}
