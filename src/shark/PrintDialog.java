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
 * @author MacBook Air
 */
public class PrintDialog extends JDialog{
    
   public JRadioButton flash1;
   public JRadioButton flash2;
   public JRadioButton print1;    
   public JRadioButton print2;  
   public JRadioButton print3;  
   public JRadioButton print4;  
   public JButton printbutton; 
   ButtonGroup bg = new ButtonGroup();
   JDialog thisdia = this;
   String words[];
   String topic_name;
   JPanel showcolor;

   
    
    public PrintDialog(String topicname, String w[]){
     super();
     words = w;
     topic_name = topicname;
     this.setTitle(u.gettext("printdialog", "title", topicname));
     this.setModal(true);
     int border = 20;
     int thisWidth = sharkStartFrame.screenSize.width * 6 / 14;
     int thisHeight = sharkStartFrame.screenSize.height * 7 / 12;
//     this.setBounds((sharkStartFrame.screenSize.width-thisWidth)/2, (sharkStartFrame.screenSize.height-thisHeight)/2, thisWidth, thisHeight);
     this.setBounds(u2_base.adjustBounds(new Rectangle((sharkStartFrame.screenSize.width-thisWidth)/2, (sharkStartFrame.screenSize.height-thisHeight)/2, thisWidth, thisHeight)));
     
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(WindowEvent e) {
                showcolor.setBackground(sharkStartFrame.studentList[sharkStartFrame.currStudent].printfg);
                showcolor.repaint();
            }

        });
     
     flash1 = new JRadioButton(u.gettext("mprintflash", "label"));
     flash2 = new JRadioButton(u.gettext("mprintflash2", "label"));
     print1 = new JRadioButton(u.gettext("mprint1", "label"));
     print2 = new JRadioButton(u.gettext("mprint2", "label"));
     print3 = new JRadioButton(u.gettext("mprint4", "label"));
     print4 = new JRadioButton(u.gettext("mprint8", "label"));
     printbutton = u.sharkButton();
     printbutton.setText(u.gettext("print", "label"));
     printbutton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            wordlist wl = new wordlist();
            wl.setListData(words);
            wl.printWords = words;
            wl.setTopicName(topic_name);
            if(flash1.isSelected()){
                wl.printlist(topic_name,wordlist.FLASH);
            }
            else if(flash2.isSelected()){
                wl.printlist(topic_name,wordlist.FLASH2);
            }
            else if(print1.isSelected()){
                wl.printlist(topic_name,wordlist.PRINT1);
            }
            else if(print2.isSelected()){
                wl.printlist(topic_name,wordlist.PRINT2);
            }
            else if(print3.isSelected()){
                wl.printlist(topic_name,wordlist.PRINT4);
            }
            else if(print4.isSelected()){
                wl.printlist(topic_name,wordlist.PRINT8);
            }
            thisdia.dispose();
        }
     });    
     
     
     bg.add(flash1);
     bg.add(flash2);
     bg.add(print1);
     bg.add(print2);
     bg.add(print3);
     bg.add(print4);

        print1.setSelected(true);
        
     JButton colourbutton = u.sharkButton();
     colourbutton.setText(u.gettext("choose","label"));   
     colourbutton.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Color newColor = JColorChooser.showDialog(thisdia,
                                        u.gettext("printlistcolor", "label"),
                                         student.printfg());
          if(newColor != null) {
             sharkStartFrame.studentList[sharkStartFrame.currStudent].printfg = newColor;
          }                 
        }
      });    
     
     GridBagConstraints grid = new GridBagConstraints();
     grid.gridx= 0;
     grid.gridy= -1;
     grid.weightx= 1;
     grid.weighty= 1;
     grid.fill = GridBagConstraints.NONE;
     
     
     
     JPanel containerpanel = new JPanel(new GridBagLayout());
     
     JPanel mainpanel = new JPanel(new GridBagLayout());
     JPanel mainpanelborder = new JPanel(new GridBagLayout());
     mainpanelborder.setBorder(BorderFactory.createEtchedBorder());
     JPanel buttonpanel = new JPanel(new GridBagLayout());
    
     grid.anchor = GridBagConstraints.CENTER;
     JButton cancelbutton = u.sharkButton();
     cancelbutton.setText(u.gettext("cancel","label"));   
     cancelbutton.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(ActionEvent e) {
                 thisdia.dispose();
              }
      });

     grid.gridx= -1;
     grid.gridy= 0;    
     
     
     JPanel colorpanel = new JPanel(new GridBagLayout());
     
     showcolor = new JPanel();
     showcolor.setOpaque(true);
     int s = (int)colourbutton.getPreferredSize().getHeight();
     showcolor.setPreferredSize(new Dimension(s,s));
     showcolor.setMinimumSize(new Dimension(s,s));
     showcolor.setBackground(sharkStartFrame.studentList[sharkStartFrame.currStudent].printfg);
     
     JLabel printlab = new JLabel(u.gettext("printdialog", "fontlab"));
     printlab.setFont(sharkStartFrame.treefont.deriveFont(Font.PLAIN));
     int halfborder = border /2;
     
     colorpanel.add(printlab);
     grid.insets = new Insets(0, halfborder, 0, 0);
     colorpanel.add(showcolor, grid);
     
     colorpanel.add(colourbutton, grid);
     grid.insets = new Insets(0, 0, 0, 0);
     if(!shark.macOS){
         buttonpanel.add(printbutton, grid);
         buttonpanel.add(cancelbutton, grid);
     }
     else{
         buttonpanel.add(cancelbutton, grid);
         buttonpanel.add(printbutton, grid);         
     }
     
     
     grid.gridx= 0;
     grid.gridy= -1;   
     
     grid.anchor = GridBagConstraints.WEST;
     mainpanel.add(flash1, grid);
     mainpanel.add(flash2, grid);
     mainpanel.add(print1, grid);
     mainpanel.add(print2, grid);
     mainpanel.add(print3, grid);
     mainpanel.add(print4, grid);
     grid.anchor = GridBagConstraints.CENTER;
     grid.insets = new Insets(border*2, 0, 0, 0);
     mainpanel.add(colorpanel, grid);
     
     grid.insets = new Insets(border, border, border, border);
     mainpanelborder.add(mainpanel, grid);
      grid.insets = new Insets(0, 0, 0, 0);
      
      grid.weightx = 0;
    containerpanel.add(mainpanelborder, grid);
    grid.fill = GridBagConstraints.BOTH;
    containerpanel.add(buttonpanel, grid);
     grid.weightx = 1;
     this.getContentPane().setLayout(new GridBagLayout());
     this.getContentPane().add(containerpanel, grid);
     
     this.setModal(true);
     this.setVisible(true);
     
     
   }
    
}
