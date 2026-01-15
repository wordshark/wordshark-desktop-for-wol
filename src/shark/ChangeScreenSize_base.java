/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
/**
 *
 * @author paulr
 */
public class ChangeScreenSize_base extends JDialog {
    JDialog thisjd = this;
    static boolean isActive = false;
    JComboBox combo;
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   int resolutions[][] = new int[][] {
       {800, 600},
       {1024, 768},
       {1152, 864},
       {1280, 600},
       {1280, 720},
       {1280, 800},
       {1280, 960},
       {1280, 1024},
       {1360, 768},
       {1366, 768},
       {1400, 900},
       {1400, 1050},
       {1440, 900},
       {1600, 900},
       {1600, 1200},
       {1680, 1050},
       {1792, 1344},
       {1856, 1392},
       {1920, 1080},
       {1920, 1200},    
       {1920, 1440}, 
       {2048, 1152}, 
       {2048, 1536}, 
       {2560, 1600},
       {3200, 1800}
   };    
   String numberJoin = " x ";

      public ChangeScreenSize_base(JFrame parentFrame) {
          super(parentFrame);
          getContentPane().setLayout(new GridBagLayout());
          JPanel pnBase = new JPanel(new GridBagLayout());
          GridBagConstraints grid = new GridBagConstraints();
          grid.gridx = -1;
          grid.gridy = 0;
          grid.weightx = 1;
          grid.weighty = 0;
          grid.fill = GridBagConstraints.HORIZONTAL;

          this.setTitle(u.gettext("change_screen_size", "title", shark.programName));
          combo = new JComboBox(getAllResolutions(resolutions)); 
          int sel = -1;
          int ssw = -1;
          int ssh = -1;
          
          String lastres = (String)db.find(sharkStartFrame.optionsdb, "changeScreenSize_last", db.TEXT);
          if(lastres==null){
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            ssw = (int)d.getWidth();
            ssh = (int)d.getHeight();              
          }
          else{
            String ls[] = u.splitString(lastres);
            ssw = Integer.parseInt(ls[0]);
            ssh = Integer.parseInt(ls[1]);
          }
          for(int i = 0; i < resolutions.length; i++){
              if(resolutions[i][0] == ssw && resolutions[i][1] == ssh){
                  sel = i;
                  break;
              }
          }
          combo.setSelectedIndex(sel>0?sel:0);  
          String btTextClose = u.gettext("change_screen_size", "btclose", shark.programName);
   
          
          JTextArea ta_1 = new JTextArea();
            ta_1.setWrapStyleWord(true);
            ta_1.setLineWrap(true);
            ta_1.setBorder(BorderFactory.createEmptyBorder());
            ta_1.setOpaque(false);
            ta_1.setEditable(false);
            ta_1.setFont(smallerplainfont);
            ta_1.setHighlighter(null);
            ta_1.setText(u.convertToCR(u.gettext("change_screen_size", "text1", shark.programName)));      
  
          JTextArea ta_2 = new JTextArea();
            ta_2.setWrapStyleWord(true);
            ta_2.setLineWrap(true);
            ta_2.setBorder(BorderFactory.createEmptyBorder());
            ta_2.setOpaque(false);
            ta_2.setEditable(false);
            ta_2.setFont(smallerplainfont);
            ta_2.setHighlighter(null);
            ta_2.setText(u.convertToCR(u.gettext("change_screen_size", "text2"))); 

          JTextArea ta_3 = new JTextArea();
            ta_3.setWrapStyleWord(true);
            ta_3.setLineWrap(true);
            ta_3.setBorder(BorderFactory.createEmptyBorder());
            ta_3.setOpaque(false);
            ta_3.setEditable(false);
            ta_3.setFont(smallerplainfont);
            ta_3.setHighlighter(null);
            ta_3.setText(u.convertToCR(u.gettext("change_screen_size", "text3", shark.programName))); 
            
          JTextArea ta_4 = new JTextArea();
            ta_4.setWrapStyleWord(true);
            ta_4.setLineWrap(true);
            ta_4.setBorder(BorderFactory.createEmptyBorder());
            ta_4.setOpaque(false);
            ta_4.setEditable(false);
            ta_4.setFont(smallerplainfont);
            ta_4.setHighlighter(null);
            ta_4.setText(u.convertToCR(shark.network?u.edit(u.gettext("change_screen_size", "text4net"), btTextClose, shark.programName):
                    u.edit(u.gettext("change_screen_size", "text4"), btTextClose, shark.programName)));      
       
            
            
          JTextArea ta_5 = new JTextArea();
            ta_5.setWrapStyleWord(true);
            ta_5.setLineWrap(true);
            ta_5.setBorder(BorderFactory.createEmptyBorder());
            ta_5.setOpaque(false);
            ta_5.setEditable(false);
            ta_5.setFont(smallerplainfont);
            ta_5.setHighlighter(null);
            ta_5.setText(u.convertToCR(u.gettext("change_screen_size", "text5", shark.programName)));      
                        
            
            

 
          JTextArea ta_6 = new JTextArea();
            ta_6.setWrapStyleWord(true);
            ta_6.setLineWrap(true);
            ta_6.setBorder(BorderFactory.createEmptyBorder());
            ta_6.setOpaque(false);
            ta_6.setEditable(false);
            ta_6.setFont(smallerplainfont);
            ta_6.setHighlighter(null);
            ta_6.setText(u.convertToCR(u.gettext("change_screen_size", "text6", shark.programName)));      
            
            



          JPanel pnMain = new JPanel(new GridBagLayout()); 
          JPanel pnMain2 = new JPanel(new GridBagLayout()); 
          JPanel pnMainSurround = new JPanel(new GridBagLayout()); 
          grid.gridx = -1;
          grid.gridy = 0;
         // grid.anchor = GridBagConstraints.WEST; 
          grid.fill = GridBagConstraints.NONE; 
          grid.weightx = 0;
          grid.insets = new Insets(0,0,0,15);
          pnMain.add(new JLabel(u.gettext("change_screen_size", "windowsize")), grid);
          grid.insets = new Insets(0,0,0,0);

          pnMain.add(combo, grid);
      //    grid.anchor = GridBagConstraints.CENTER;
          grid.weightx = 1;
          grid.gridx = 1;
          grid.fill = GridBagConstraints.HORIZONTAL; 
          grid.gridy = 1;
          grid.gridx = 0;
          JButton jbChangeAndExit = new JButton(btTextClose);
          JButton jbCancel = new JButton(u.gettext("cancel", "label"));
          
     jbChangeAndExit.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String sval = (String)combo.getSelectedItem();
                setData(sval.substring(0, sval.indexOf(numberJoin)), sval.substring(sval.indexOf(numberJoin)+numberJoin.length()));
                try{
                   sharkStartFrame.mainFrame.finalize();
                }
                catch(Exception ee){
                   System.exit(100);
                }               
           }
     });       
     
     
     jbCancel.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               thisjd.dispose();
           }
     });      
     
 
          grid.insets = new Insets(0,0,0,0);
          grid.gridx = 0;
          grid.gridy = -1;  
          

          pnMain2.add(ta_1, grid);
          grid.insets = new Insets(10,0,10,0);
          pnMain2.add(ta_2, grid);
          grid.insets = new Insets(0,50,0,0);
          pnMain2.add(ta_3, grid);
          grid.anchor = GridBagConstraints.WEST; 
          grid.weightx = 0;
          grid.fill = GridBagConstraints.NONE;

          grid.insets = new Insets(10,80,10,0);
          pnMain2.add(pnMain, grid);
          grid.insets = new Insets(0,0,0,0);
          grid.anchor = GridBagConstraints.CENTER;
          grid.weightx = 1;
          grid.fill = GridBagConstraints.HORIZONTAL;  
          grid.insets = new Insets(0,50,10,0);
          pnMain2.add(ta_4, grid); 
          pnMain2.add(ta_5, grid);
          pnMain2.add(ta_6, grid);
          grid.insets = new Insets(0,0,0,0);
          pnMain2.setBorder(BorderFactory.createEtchedBorder());

          grid.fill = GridBagConstraints.HORIZONTAL; 
          grid.insets = new Insets(0,0,20,0);
          pnMainSurround.add(new JLabel( u.convertToHtml(u.gettext("change_screen_size", "heading", shark.programName))),grid);
          grid.insets = new Insets(0,0,0,0);

          pnMainSurround.add(pnMain2, grid);
          
          
          grid.weightx = 0;

          grid.gridy = 0;
          grid.gridx = -1;     
          grid.fill = GridBagConstraints.NONE;           
          if(!shark.macOS){
            pnBase.add(jbChangeAndExit, grid);
            grid.insets = new Insets(0,50,0,0);
            pnBase.add(jbCancel, grid);
          }
          else{
          pnBase.add(jbCancel, grid);
          grid.insets = new Insets(0,50,0,0);
          pnBase.add(jbChangeAndExit, grid);              
          }
          
          grid.weightx = 1;
          grid.fill = GridBagConstraints.HORIZONTAL; 
          grid.gridy = -1;
          grid.gridx = 0;  
          grid.insets = new Insets(0,0,0,0);

          JPanel jpAll = new JPanel(new GridBagLayout());
          grid.insets = new Insets(20,20,20,20);
          jpAll.add(pnMainSurround, grid);
          grid.insets = new Insets(0,0,0,0);
          jpAll.add(pnBase, grid);
          
          JScrollPane jsp = new JScrollPane(jpAll);
          jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
          jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
          grid.fill = GridBagConstraints.BOTH;
          grid.weightx = 1;
          grid.weighty = 1;
          add(jsp, grid);
          
       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2 = sw*12/24;
       int sh2;
       if(u.screenResHeightMoreThan(1000)){
            sh2 = sh*12/24;           
       }
       else{
            sh2 = sh*15/24;           
       }
       setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);       
          
          this.setVisible(true);
      }
      
      
      
      String[] getAllResolutions(int ii[][]){
          String ret[] = new String[]{};
          for(int i  = 0; i < ii.length; i++){
              String s = String.valueOf(ii[i][0]) + numberJoin+String.valueOf(ii[i][1]);  
              ret = u.addString(ret, s);
          }
          return ret;
      }
      
      public static void setData(String w, String h){
                db.update(sharkStartFrame.optionsdb, "changeScreenSize_w", w, db.TEXT);
               db.update(sharkStartFrame.optionsdb, "changeScreenSize_h", h, db.TEXT);
               db.update(sharkStartFrame.optionsdb, "changeScreenSize_currTime", String.valueOf(System.currentTimeMillis()), db.TEXT);
               db.update(sharkStartFrame.optionsdb, "changeScreenSize_user", sharkStartFrame.studentList[sharkStartFrame.currStudent].name, db.TEXT);  
               db.update(sharkStartFrame.optionsdb, "changeScreenSize_last", w+"|"+h, db.TEXT);
      }
     
      public static String getUserData(){
          try{
            String time = (String)db.find(sharkStartFrame.optionsdb, "changeScreenSize_currTime", db.TEXT);
            if(time!=null) db.delete(sharkStartFrame.optionsdb, "changeScreenSize_currTime",  db.TEXT);
            else return null;
            // 5 mins
            if(System.currentTimeMillis() > Double.parseDouble(time)+(60000*5))return null;
            String user = (String)db.find(sharkStartFrame.optionsdb, "changeScreenSize_user", db.TEXT);
            if(user==null)return null;
            else{
              db.delete(sharkStartFrame.optionsdb, "changeScreenSize_user", db.TEXT); 
              return user;
            }
          }
          catch(Exception e){}
          return null;
      }
      
      public static Point getResolutionData(){
          try{
            String w = (String)db.find(sharkStartFrame.optionsdb, "changeScreenSize_w", db.TEXT);
            if(w!=null) db.delete(sharkStartFrame.optionsdb, "changeScreenSize_w", db.TEXT);
            String h = (String)db.find(sharkStartFrame.optionsdb, "changeScreenSize_h", db.TEXT);
            if(h!=null) db.delete(sharkStartFrame.optionsdb, "changeScreenSize_h", db.TEXT);  
            return new Point(Integer.parseInt(w), Integer.parseInt(h));
          }
          catch(Exception e){}
          return null;
      }
      
  }
