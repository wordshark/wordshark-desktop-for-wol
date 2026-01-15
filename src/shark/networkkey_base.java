package shark;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public class networkkey_base
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public static boolean changed, active;
   public runMovers gamePanel;
   boolean changed1;
      String op[];
   GridBagLayout layout1 = new GridBagLayout();
   GridBagConstraints grid = new GridBagConstraints();
   GridBagConstraints grid2 = new GridBagConstraints();
   JTextField serial = new JTextField(8);
   JTextField maxusers = new JTextField(4);
   JTextField key1,key2,key3,key4;
   JTextField installstart[];
   JTextField installend[];
   JLabel startlab[];
   mlabel_base netranges = u.mlabel("netkey_netranges");
   String netrangest = netranges.getText();
   String st1;
   String tt1;
   JLabel salab = u.label("netkey_sanumber");
   String st2 = salab.getText();
   String tt2 = salab.getToolTipText();
   JLabel endlab[];
   JRadioButton netbutton,sabutton;
   JLabel message = new JLabel("");
   JButton OK = u.Button("netkey_ok");
   JButton cancel;
   static final String cstring = "M67LP5N8KBJVH2UCGY4XFTZ9DRSE3AWQ";
   static boolean running,first1;
   boolean gettinginstall;
   int max,ser;
   String keyencr;
   JPanel p4=new JPanel(new GridBagLayout());
   int itot = 5;
   JPanel p2x[];
   int oldusers = sharkStartFrame.users;

   public networkkey_base(boolean first) {
 //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      short i,j;
      running=true;
      first1=first;
      setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
      if(!first) cancel = u.Button("netkey_cancel");
      else cancel = u.Button("netkey_exit");
      key1 = new JTextField("",4);
      key2 = new JTextField("",4);
      key3 = new JTextField("",4);
      key4 = new JTextField("",4);
      key1.setDocument(new keydoc(key1,key2));
      key2.setDocument(new keydoc(key2,key3));
      key3.setDocument(new keydoc(key3,key4));
      key4.setDocument(new keydoc(key4,null));
      serial.setDocument(new serialdoc());
      getContentPane().setLayout(layout1);
      setTitle(u.gettext("netkey", "title"));
      grid.fill = GridBagConstraints.NONE;
      grid.gridx = 0;
      grid.gridy = -1;
      grid.gridheight = 1;
      grid.weighty = 1;
      grid.weightx = 1;
      grid2.gridx = 0;
      grid2.gridy = -1;
      grid2.gridheight = 1;
      grid2.weighty = 1;
      grid2.weightx = 1;
      grid2.fill = GridBagConstraints.NONE;
      JPanel p1=new JPanel(new GridBagLayout()),p2=new JPanel(new GridBagLayout()),p3=new JPanel(new GridBagLayout());
      getContentPane().add(u.label("netkey_command"),grid2);
      getContentPane().add(p1, grid);
      getContentPane().add(p2, grid);
      getContentPane().add(p3, grid);
      getContentPane().add(message,grid);
      grid.fill = GridBagConstraints.HORIZONTAL;
      getContentPane().add(p4, grid);
      message.setForeground(Color.red);
      grid2.gridx = -1;
      grid2.gridy = 0;
      grid2.fill = GridBagConstraints.NONE;
      p1.add(u.label("netkey_serial"),grid2);
      p1.add(serial,grid2);
      serial.requestFocus();
      p2.add(u.label("netkey_maxusers"),grid2);
      p2.add(maxusers,grid2);
      p3.add(u.label("netkey_key"),grid2);
      p3.add(key1,grid2);
      p3.add(key2,grid2);
      p3.add(key3,grid2);
      p3.add(key4,grid2);
      p4.add(OK,grid2);
      p4.add(cancel,grid2);
      OK.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           check();
        }
      });
      cancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          running = false;
           dispose();
        }
      });
      serial.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(check()) maxusers.requestFocus();
        }
      });
      maxusers.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          if(check()) key1.requestFocus();
        }
      });
      key1.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(check()) key2.requestFocus();
        }
      });
      key2.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(check()) key3.requestFocus();
        }
      });
      key3.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           if(check()) key4.requestFocus();
        }
      });
      key4.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           check();
        }
      });
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowDeactivated(WindowEvent e) {
          if(!first1) {
            running = false;
            dispose();
          }
          else requestFocus();
        }
        public void windowClosing(WindowEvent e) {
           running = false;
       }
     });
     setBounds(sharkStartFrame.screenSize.width/6,
               sharkStartFrame.screenSize.height/4,
               sharkStartFrame.screenSize.width*2/3,
               sharkStartFrame.screenSize.height/2);
     setVisible(true);
     validate();
   }
   boolean check() {
     if(!running) return false;
     int i,j;
     message.setText("");
     if (!sharkStartFrame.serial.equalsIgnoreCase(serial.getText())) {
       message.setText(u.gettext("netkey", "badserial"));
       serial.requestFocus();
       return false;
      }
     else  {
       if(maxusers.getText().length() == 0) {
         maxusers.requestFocus();
         return false;
       }
       try {
         max = Integer.parseInt(maxusers.getText());
       }
       catch (NumberFormatException e) {
         message.setText(u.gettext("netkey", "badtot"));
         maxusers.requestFocus();
         return false;
       }
       ser = Integer.parseInt(sharkStartFrame.serial.substring(u.scanfor(sharkStartFrame.serial,u.numbers)));
       Random rr = new Random(ser);
       long lg = 0;

       String k1 = key1.getText();
       String k2 = key2.getText();
       String k3 = key3.getText();
       String k4 = key4.getText();

       if(k1.length() != 4 ||k2.length() != 4 ||k3.length() != 4 ||k4.length() != 4) return true;
       char ch[] = (k1+k2+k3+k4).toCharArray(),ch2[]=new char[11],cc;
       for(i=j=0;i<16;++i,j+=5) {
         cc = (char)(cstring.indexOf(ch[i]) << (11-j%8));
         int jj = cc;
         ch2[j/8] |= cc>>>8;
         ch2[j/8+1] |= cc & 255;
       }
       String s = keyencr = shark.decrypt1(String.valueOf(ch2).substring(0,10), sharkStartFrame.serial + sharkStartFrame.school);
       if (s == null) {
         message.setText(u.gettext("netkey", "badtotkey"));
         return false;
       }
       i = max;
       while (--i > 0) {
         lg = rr.nextLong();
       }
       String s2 = new String(new char[] { (char) ( (lg >> 40) & 255),
                              (char) ( (lg >> 32) & 255),
                              (char) ( (lg >> 24) & 255),
                              (char) ( (lg >> 16) & 255),
                              (char) ( (lg >> 8) & 255),
                              (char) (lg & 255)});
       if (!s2.equals(s)) {
         message.setText(u.gettext("netkey", "badtotkey"));
         return false;
       }
       db.update(sharkStartFrame.optionsdb, "kkkk",
                           encrypt(keyencr + String.valueOf(max),
                                   sharkStartFrame.serial+ sharkStartFrame.school),
                                         db.TEXT);
//startPR2010-03-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       Backup_base bu = new Backup_base(false);
       bu.oneOffBkup.BackupFiles("oneOff");
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       running=false;
       if(!first1) {
         String ss = (String) db.find(sharkStartFrame.optionsdb, "kkkk", db.TEXT);
         sharkStartFrame.users = sharkStartFrame.mainFrame.getusers(
             sharkStartFrame.serial,
             sharkStartFrame.school, ss);
         sharkStartFrame.licence = (sharkStartFrame.oldli1 != null) ?
             sharkStartFrame.oldli1 : sharkStartFrame.oldli;
         if ( (i = sharkStartFrame.licence.indexOf("##")) >= 0) {
           sharkStartFrame.licence = sharkStartFrame.licence.substring(0, i) +
               String.valueOf(sharkStartFrame.users) +
               sharkStartFrame.licence.substring(i + 2);
         }
         sharkStartFrame.settitles();
         sharkStartFrame.mainFrame.setTitle(sharkStartFrame.hometitle1);
       }
       dispose();
       return false;
     }

   }
   static String encrypt(String arg,String key) {
      char kk[] = key.toCharArray();
      char aa[] = addchek(arg).toCharArray();
      short k=0;
      int len2 = kk.length;
      short i;
      for(i=0;i<len2;++i) k += kk[i];
      for(i=0;i<aa.length;++i) aa[i] ^= (char)((k + i*3 + kk[i%len2]) & 0x00ff);
      return new String(aa);
   }
   static String addchek(String ss) {
      int hash=0;
      char cc[] = ss.toCharArray();
      int len = cc.length,i;
      for(i=0;i<len;++i) hash += (cc[i]*i) + ((cc[i]*(i+1))<<8) + ((cc[i]*(i+2))<<16)
                                  + ((cc[i]*(i+3))<<24);
     return ss + new String(new char [] {(char)((hash>>24) & 255),
                                      (char)((hash>>16) & 255),
                                      (char)((hash>>8) & 255),
                                      (char)(hash & 255) });
   }
   class keydoc extends PlainDocument {
     JTextField owner,next;
     keydoc(JTextField ow,JTextField ne) {
       super();
       owner = ow;
       next=ne;
     }
     public void insertString(int o, String s, AttributeSet a) {
       s = s.toUpperCase();
       int i = u.scanfornot(s,cstring);
       if(i>=0) {noise.beep(); return;}
       try{super.insertString(o,s,a);}
       catch(BadLocationException e) {}
       if(o==3 && getLength()==4) {
         if(check())
            if(next !=null) next.requestFocus();
       }
     }
   }
   class serialdoc extends PlainDocument {
     public void insertString(int o, String s, AttributeSet a) {
       try{super.insertString(o,s.toUpperCase(),a);}
       catch(BadLocationException e) {}
     }
   }
}
