/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Paul Rubie
 */
public abstract class InfoRemindChoiceDialog_base extends JDialog{
    public static final int ANSWER_YES = 0;
    public static final int ANSWER_NO = 1;
    public int result = -1;
    JDialog thisd = this;

    public InfoRemindChoiceDialog_base(String title, String message, String[] buttontexts){
        super();
        this.setModal(true);
        this.setTitle(title);
        this.getContentPane().setLayout(new GridBagLayout());
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        int width = (int) env.getMaximumWindowBounds().getWidth();
        int height = (int) env.getMaximumWindowBounds().getHeight();
        int ww = width*7/16;
        int hh = height*7/16;
//        this.setBounds((width-ww)/2, (height-hh)/2, ww, hh);
        this.setBounds(u2_base.adjustBounds(new Rectangle((width-ww)/2, (height-hh)/2, ww, hh)));

        JCheckBox cb = new JCheckBox(u.gettext("noaskagain", "label"));
        cb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            doCheckboxAction(((JCheckBox)e.getSource()).isSelected());
          }
        });

        JButton jb1 = new JButton(buttontexts[0]);
        jb1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              result = ANSWER_YES;
            doButtonAction(result);
            thisd.dispose();

          }
        });
        JButton jb2 = new JButton(buttontexts[1]);
         jb2.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              result = ANSWER_NO;
            doButtonAction(result);
            thisd.dispose();
          }
        });

        this.getRootPane().setDefaultButton(jb1);

        JTextArea ta = new JTextArea();
        ta.setWrapStyleWord(true);
        ta.setLineWrap(true);
        ta.setBorder(BorderFactory.createEmptyBorder());
        ta.setOpaque(false);
        ta.setEditable(false);
        ta.setHighlighter(null);
        ta.setText(message);
        ta.setFont(jb1.getFont());


        GridBagConstraints grid = new GridBagConstraints();
        grid.weightx = 1;
        grid.weighty = 1;
        grid.gridx = -1;
        grid.gridy = 0;
        JPanel buttonp = new JPanel(new GridBagLayout());
        grid.fill = GridBagConstraints.NONE;
        buttonp.add(shark.macOS?jb2:jb1, grid);
        buttonp.add(shark.macOS?jb1:jb2, grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = -1;
        JPanel mainp = new JPanel(new GridBagLayout());
        
        
        grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;        
        
        grid.insets = new Insets(10,10,10,10);
        this.getContentPane().add(mainp, grid);
        grid.insets = new Insets(0,0,0,0);

        JPanel textpanel = new JPanel(new GridBagLayout());

        
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(0,10,0,10);
        textpanel.add(ta, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;

        mainp.add(textpanel, grid);
        grid.weighty = 0;
        grid.fill = GridBagConstraints.HORIZONTAL;
        mainp.add(buttonp, grid);
        grid.anchor = GridBagConstraints.CENTER;
        grid.weightx = 0;
        grid.fill = GridBagConstraints.NONE;
        grid.insets = new Insets(20,0,0,0);
        mainp.add(cb, grid);
        this.setVisible(true);
    }

    public abstract void doCheckboxAction(boolean checked);
    public abstract void doButtonAction(int option);
}
