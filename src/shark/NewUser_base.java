/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.ref.SoftReference.*;
import javax.swing.text.*;
/**
 *
 * @author MacBook Air
 */
class NewUser_base extends JDialog {
      JButton ok;
      JButton cancel;
      String status;
      String warnmatch = u.gettext("adminnewuser", "matchwarn");
      String warnrequired = u.gettext("adminnewuser", "requiredwarn");
      String warnhint = u.gettext("so", "hintwarn");
      jnode node;
      JPasswordField passwordtf;
      JPasswordField confirmpasswordtf;
      JLabel passwordlab;
      JLabel matchwarnlab;
      JLabel passwordconfirmlab;
      JCheckBox addpasswordcb;
      JCheckBox removepasscb;
      JTextField usertf;

      JLabel showhintinfolab;
      JLabel showhintlab;
      JTextField showhinttf;

      String name;
      JDialog thisd;
      int mode = -1;
      static final int MODE_NEWSTU = 0;
      static final int MODE_NEWADMIN = 1;
      static final int MODE_NEWSUBADMIN = 2;
      static final int MODE_PASSWORDONLY = 3;
      static final int MODE_PASSWORDONLY_RETURNVAL = 4;
      public String returnpass =null;
      public String returnpasswordhint =null;
      student currstu;
   int nodetype;
   int b1 = 5;
   int b2 = 10;
   int b3 = 30;
   boolean removepass;
   String pass;
   String passwordhint;
   boolean exit;
   public int exitval = -1;
   boolean usingkeypad;

   NewUser_base(int type, JFrame owner, String nam, int nodetype1) {
       super(owner);
       mode = type;
       name = nam;
       nodetype = nodetype1;
       init();
   }

   NewUser_base(int type, JDialog owner, String nam, int nodetype1) {
       super(owner);
       mode = type;
       name = nam;
       nodetype = nodetype1;
       init();
   }

   void init(){
       thisd = this;
       setResizable(false);
       setModal(true);
     this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(WindowEvent e) {
           keypad.dofullscreenkeypad(thisd, false);
       }
     });
       currstu = student.findStudent(name);
       getContentPane().setLayout(new GridBagLayout());
       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2;
       if(u.screenResWidthMoreThan(1200)) sw2 = sw*10/24;
       else if(u.screenResWidthMoreThan(1000)) sw2 = sw*14/24;
       else sw2 = sw*16/24;
       int sh2;
       if(u.screenResHeightMoreThan(850)) sh2 = sh*9/24;
       else if(u.screenResHeightMoreThan(700)) sh2 = sh*9/24;
       else sh2 = sh*11/24;
       setBounds(u2_base.adjustBounds(new Rectangle((sw-sw2)/2, (sh-sh2)/2, sw2, sh2)));
       GridBagConstraints g = new GridBagConstraints();
       if(mode == MODE_NEWSTU)status=u.statuses[u.STATUS_STUDENT];
       else if(mode == MODE_NEWADMIN)status=u.statuses[u.STATUS_ADMIN];
       else if(mode == MODE_NEWSUBADMIN)status=u.statuses[u.STATUS_SUBADMIN];
       else if(nodetype == jnode.TEACHER)status=u.statuses[u.STATUS_ADMIN];
       else if(nodetype == jnode.SUBADMIN)status=u.statuses[u.STATUS_SUBADMIN];
       else if(nodetype == jnode.GROUP)status=u.statuses[u.STATUS_GROUP];
       else status=u.statuses[u.STATUS_STUDENT];
       String title;
       if(mode==MODE_PASSWORDONLY || mode==MODE_PASSWORDONLY_RETURNVAL)
           title = u.gettext("adminnewuser", "titlepassword");
       else
           title = u.gettext("adminnewuser", "title").replaceFirst("%", status.toLowerCase());
       setTitle(title);
       ok = u.sharkButton();
       ok.setText(u.gettext("ok", "label"));
       cancel = u.sharkButton();
       cancel.setText(u.gettext("cancel", "label"));
       ok.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              okpressed();
           }
       });
       cancel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                thisd.dispose();
           }
       });
       JLabel userlab = new JLabel(u.gettext("adminnewuser", "user").replaceFirst("%", status));
       JLabel passmess = new JLabel(u.gettext("verifypassword", "entermess").replaceFirst("%", name));
       usertf = new JTextField(KeyDoc_base.MAXNAMECHARS);
       usertf.setDocument(new KeyDoc_base(KeyDoc_base.MAXNAMECHARS));
       passwordlab = new JLabel(u.gettext("adminnewuser", "password"));
       passwordtf = new JPasswordField(KeyDoc_base.MAXNAMECHARS);
       passwordtf.setDocument(new KeyPasswordDoc(KeyDoc_base.MAXNAMECHARS));
       passwordconfirmlab = new JLabel(u.gettext("adminnewuser", "confirmpassword"));
       showhinttf = new JTextField();
       showhinttf.setDocument(new KeyDoc_base(KeyDoc_base.MAXNAMECHARSPASSHINT));
       showhintinfolab = new JLabel(u.gettext("adminnewuser", "showhint"));
       showhintlab = u.infoLabel(this, u.gettext("adminnewuser", "showhinttooltip"));
       confirmpasswordtf = new JPasswordField(KeyDoc_base.MAXNAMECHARS);
       confirmpasswordtf.setDocument(new KeyPasswordDoc(KeyDoc_base.MAXNAMECHARS));
       matchwarnlab = new JLabel(warnmatch);
       matchwarnlab.setForeground(thisd.getContentPane().getBackground());
       JPanel mainpan = new JPanel(new GridBagLayout());
       JPanel butpan = new JPanel(new GridBagLayout());
       removepasscb = u.CheckBox("cbremovepassword");
       removepasscb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            if(!((JCheckBox)e.getSource()).isSelected()){
                setPasswordActive(true);
            }
            else{
                setPasswordActive(false);
            }
          }
       });
       addpasswordcb = u.CheckBox("cboptionalpassword");
       addpasswordcb.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              matchwarnlab.setForeground(thisd.getContentPane().getBackground());
            if(((JCheckBox)e.getSource()).isSelected()){
                setPasswordActive(true);
            }
            else{
                setPasswordActive(false, true);
            }
          }
       });
       removepasscb.setVisible(mode==MODE_PASSWORDONLY && nodetype == jnode.STUDENT &&
               (currstu != null && currstu.password!=null));
       addpasswordcb.setVisible(mode==MODE_NEWSTU);
       if(mode==MODE_NEWSTU){
           setPasswordActive(false);
       }
       if(mode==MODE_PASSWORDONLY || mode==MODE_PASSWORDONLY_RETURNVAL){
           userlab.setVisible(false);
           usertf.setVisible(false);
       }
       g.weightx = 0;
       g.weighty = 1;
       g.fill = GridBagConstraints.HORIZONTAL;
       g.gridy = 0;
       g.gridx = 0;
       mainpan.add(passmess, g);
       g.anchor = GridBagConstraints.EAST;
       g.insets = new Insets(0,0,b1*3,b1);
       g.fill = GridBagConstraints.NONE;
       mainpan.add(userlab, g);
       g.gridx = 1;
       g.insets = new Insets(0,0,b1*3,0);
       g.fill = GridBagConstraints.HORIZONTAL;
       mainpan.add(usertf, g);
       g.fill = GridBagConstraints.NONE;
       g.gridx = 0;
       g.gridy = 1;
       g.insets = new Insets(0,0,b1,0);
       g.weightx = 0;
       mainpan.add(addpasswordcb, g);
       g.anchor = GridBagConstraints.EAST;
       g.gridy = 2;
       g.insets = new Insets(0,0,b1,b1);
       g.weightx = 0;
       mainpan.add(passwordlab, g);
       g.gridx = 1;
       g.anchor = GridBagConstraints.WEST;
       g.insets = new Insets(0,0,b1,0);
       g.fill = GridBagConstraints.HORIZONTAL;
       mainpan.add(passwordtf, g);
       g.fill = GridBagConstraints.NONE;
       g.gridx = 0;
       g.gridy = 3;
       g.anchor = GridBagConstraints.EAST;
       g.insets = new Insets(0,0,b1,b1);
       g.weightx = 0;
       mainpan.add(passwordconfirmlab, g);
       g.gridx = 1;
       g.anchor = GridBagConstraints.WEST;
       g.insets = new Insets(0,0,b1,0);
       g.fill = GridBagConstraints.HORIZONTAL;
       mainpan.add(confirmpasswordtf, g);
       g.gridx = -1;
       g.gridy = 0;
       JPanel passhintpn = new JPanel(new GridBagLayout());
       passhintpn.add(showhintlab, g);
       g.insets = new Insets(0,b1,0,0);
       passhintpn.add(showhintinfolab, g);
       g.fill = GridBagConstraints.NONE;
       g.gridx = 0;
       g.gridy = 4;
       g.anchor = GridBagConstraints.EAST;
       g.insets = new Insets(0,0,b1,b1);
       g.weightx = 0;
       mainpan.add(passhintpn, g);
       g.gridx = 1;
       g.anchor = GridBagConstraints.WEST;
       g.insets = new Insets(0,0,b1,0);
       g.fill = GridBagConstraints.HORIZONTAL;
       mainpan.add(showhinttf, g);
       g.fill = GridBagConstraints.HORIZONTAL;
       g.gridx = 1;
       g.gridy = 5;
       g.anchor = GridBagConstraints.EAST;
       g.insets = new Insets(0,0,0,0);
       mainpan.add(matchwarnlab, g);
       g.fill = GridBagConstraints.NONE;
       g.gridy = 6;
       mainpan.add(removepasscb, g);
       g.gridx = -1;
       g.gridy = 0;
       g.anchor = GridBagConstraints.CENTER;
       g.weightx = 1;
       butpan = new JPanel(new GridBagLayout());
       butpan.add(!shark.macOS?ok:cancel, g);
       butpan.add(!shark.macOS?cancel:ok, g);
       passmess.setVisible(mode==MODE_PASSWORDONLY_RETURNVAL);
       g.weighty = 1;
       g.anchor = GridBagConstraints.CENTER;
       g.gridx = 0;
       g.gridy = -1;
       g.fill = GridBagConstraints.BOTH;
       JPanel maincontentpan = new JPanel(new GridBagLayout());
       maincontentpan.add(mainpan, g);
       g.fill = GridBagConstraints.NONE;
       if(mode==MODE_PASSWORDONLY_RETURNVAL){
           g.insets = new Insets(b3,b3,0,b3);
           this.getContentPane().add(passmess, g);
           g.insets = new Insets(0,b3,0,b3);
       }
       else
            g.insets = new Insets(b3,b3,0,b3);
       g.fill = GridBagConstraints.BOTH;
       getContentPane().add(maincontentpan, g);
       g.weighty = 0;
       g.insets = new Insets(b2,b3,b3,b3);
       getRootPane().setDefaultButton(ok);
       getContentPane().add(butpan, g);
       keypad.dofullscreenkeypad(thisd, true);
       setVisible(true);
    }

   void setPasswordActive(boolean on){
       setPasswordActive(on, false);
   }

   void setPasswordActive(boolean on, boolean leaveMainName){
       if(on){
          passwordlab.setEnabled(true);
          passwordtf.setEnabled(true);
          passwordconfirmlab.setEnabled(true);
          confirmpasswordtf.setEnabled(true);
          showhintinfolab.setEnabled(true);
          showhintlab.setEnabled(true);
          showhinttf.setEnabled(true);
       }
       else{
          passwordlab.setEnabled(false);
          passwordtf.setEnabled(false);
          clearEntries(leaveMainName);
          passwordconfirmlab.setEnabled(false);
          confirmpasswordtf.setEnabled(false);
          showhintinfolab.setEnabled(false);
          showhintlab.setEnabled(false);
          showhinttf.setEnabled(false);
       }
   }

   void clearEntries(){
       clearEntries(false);
   }

   void clearEntries(boolean leaveMainName){
       if(!leaveMainName)
           usertf.setText("");
       passwordtf.setText("");
       confirmpasswordtf.setText("");
       showhinttf.setText("");
   }
     //---------------------------------- to be overridden
  public void okpressed() {
      exit = false;
      removepass = false;
      pass = null;
      passwordhint = null;
      matchwarnlab.setForeground(thisd.getContentPane().getBackground());
      if((addpasswordcb.isVisible() && addpasswordcb.isSelected()) ||
        (!addpasswordcb.isVisible() && passwordtf.isVisible())){
        pass = String.valueOf(passwordtf.getPassword());
        String pwh;
        if(!(pwh=showhinttf.getText().trim()).equals(("")))
            passwordhint = pwh;
        pass = pass.trim().equals("")?null:pass.trim();
        
        if(pass!=null && pass.equalsIgnoreCase(passwordhint)){
            matchwarnlab.setText(warnhint);
            matchwarnlab.setForeground(Color.red);  
            exit = true;
        }
        else if(removepasscb.isVisible() && removepasscb.isSelected()){
            removepass = true;
        }
        else{
            if(pass == null){
                matchwarnlab.setText(warnrequired);
                matchwarnlab.setForeground(Color.red);
                exit = true;
             }
            else if((!pass.equals(String.valueOf(confirmpasswordtf.getPassword())))){
                matchwarnlab.setText(warnmatch);
                matchwarnlab.setForeground(Color.red);
                exit = true;
            }
        }
      }
   }
  
  
  class KeyPasswordDoc extends PlainDocument {
     int maxchars;
     KeyPasswordDoc(int max) {
       super();
       maxchars=max;
     }
     
     public void insertString(int o, String s, AttributeSet a) {
       if(o>=maxchars){noise.beep(); return;}
       if(s.equals(" ")) return;
       try{super.insertString(o,s,a);}
       catch(BadLocationException e) {}
     }
   }  
  
  
 }
