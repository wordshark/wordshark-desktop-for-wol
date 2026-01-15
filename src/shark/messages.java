package shark;
import java.io.*;
import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class messages
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static class message implements Serializable{
    static final long serialVersionUID = 1;
    String text="";
    String from;
    String to="";
    String fontname;
    Date d;
  }
  message mess;
  String name;
  display disp;
  boolean nowordsf, donekeypad;
  messages textframe = this;
  String sellist[],lastword="";

  static String messfrom = u.gettext("messages_","from")+' ';
  static String messto = u.gettext("messages_","to")+' ';
  static String sent = u.gettext("messages_","sent");
  static String sentat = "   "+u.gettext("messages_","sentat")+"   ";
  static String savedat = "   "+u.gettext("messages_","savedat")+"   ";
  int w = sharkStartFrame.mainFrame.getSize().width*2/3;
  int h = sharkStartFrame.mainFrame.getSize().height*3/4;
  JButton send = u.Button("messages_send");
  JButton cancel = u.Button("messages_cancel");
  String myname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;

  public messages(String type) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    int i;
    this.getContentPane().setBackground(runMovers.tooltipbg);
     if(type.equals("new")) {
       mess = new message();
       mess.from = myname;
       setTitle(messfrom + mess.from);
//       setBounds(w/4,h/4,w,h);
        setBounds(u2_base.adjustBounds(new Rectangle(w/4,h/4,w,h)));
     }
     else if(type.equals("get")) {
       String list[] = db.list(myname,
                               db.MESSAGE);
       if(list.length==0) {dispose(); return;}
       mess = (message)db.find(myname,
                      list[list.length-1], db.MESSAGE);
       setTitle(messfrom + mess.from + sentat
                + (new SimpleDateFormat("HH:mm  EEE dd MMM yyyy")).format(mess.d));
       db.delete(myname,list[list.length-1],db.MESSAGE);
//       setBounds(Math.max(0,w/4-((list.length-1)*w/16)),Math.max(0,h/4-((list.length-1)*h/16)),
//                        w,h);
       setBounds(u2_base.adjustBounds(new Rectangle(Math.max(0,w/4-((list.length-1)*w/16)),Math.max(0,h/4-((list.length-1)*h/16)),
                        w,h)));
     }
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // was true
       this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     this.addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            dispose();
         }
         public void windowClosed(WindowEvent e) {
           // update main list
           String words2[] = (String[])db.find(sharkStartFrame.optionsdb, "messwords_",db.TEXT);
           db.update(sharkStartFrame.optionsdb, "messwords_", words2, db.TEXT);
         }
//         public void windowDeactivated(WindowEvent e) {
//            dispose();
//          }
     });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      // enables exiting screen via the ESC key
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_ESCAPE)
                  dispose();
            }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     this.getContentPane().setLayout(new BorderLayout());
     this.getContentPane().add(new JScrollPane(disp = new display()),BorderLayout.CENTER);
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(type.equals("get")){
       disp.setEditable(false);
       cancel.setText(u.gettext("OK", "label"));
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     disp.updating = true;
     disp.setText(mess.text);
     disp.updating = false;
     JPanel pp = new JPanel(new GridBagLayout());
     GridBagConstraints grid = new GridBagConstraints();
     grid.weightx = grid.weighty = 1;
     grid.fill = GridBagConstraints.BOTH;
     grid.gridx = -1;
     grid.gridy = 0;
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(shark.macOS){
        pp.add(cancel,grid);
        if(type.equals("new")) pp.add(send,grid);
      }
      else{
        if(type.equals("new")) pp.add(send,grid);
        pp.add(cancel,grid);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     this.getContentPane().add(pp,BorderLayout.SOUTH);
     validate();
     send.setRequestFocusEnabled(false);
     cancel.setRequestFocusEnabled(false);
     send.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            send();
         }
     });
     cancel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            dispose();
         }
     });
     setVisible(true);
//     if(type.equals("new")) {
//         keypad.activate(this, new char[] { (char) keypad.SHIFT, ' ',
//                         (char) keypad.BACKSPACE,
//                         (char) keypad.UP, (char) keypad.DOWN,
//                         (char) keypad.ENTER});
//      donekeypad=true;
//     }
  }

//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public static void sendMessage(String to, String from, String text){
  public static void sendMessage(String to, String from, String text){
    sendMessage(to, from, text, null);
  }
  public static void sendMessage(String to, String from, String text, String name){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    message m = new message();
    m.to = to;
    if(from == null)
      m.from = shark.programName;
    else
      m.from = from;
    m.text = text;
    m.d = new Date();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    m.fontname = "Default";
    if(sharkStartFrame.wordfont!=null)
        m.fontname = sharkStartFrame.wordfont.getName();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String sysmess;
//startPR2006-08-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(name==null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      for (int j = 0; db.query(m.to, sysmess = "sysmess" + String.valueOf(j),db.MESSAGE) >= 0; ++j);
//startPR2006-08-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }
    else sysmess=name;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    db.update(m.to, sysmess, m, db.MESSAGE);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  void send() {
    mess.text = disp.getText();
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    new sendwindow();
    new sendwindow(this);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  }
  //--------------------------------------------------------------
class display extends JTextArea {
  public boolean updating, justhadchar,addedspace;
  FontMetrics m;
  int caretpos,delfrom;
  public display() {
    super();
    setBackground(runMovers.tooltipbg);
    this.setWrapStyleWord(true);
    this.setLineWrap(true);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE)
          dispose();
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    setFont(sharkGame.sizeFont(new String[]{"abc"},w/20,h/10));
//    if(mess.fontname != null && mess.fontname != sharkStartFrame.wordfont.getName()) {
//      setFont(u.fontFromString(mess.fontname,getFont().getStyle(),(float)getFont().getSize()));
//    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   if(keypad.keypadname != null) {
//       keypad.usekeypad u = keypad.find(sharkStartFrame.mainFrame);
//       if(u != null) {
//          Font f = u.kk.font;
//          if(!f.getName().equalsIgnoreCase("wordshark")) {
//             setFont(new Font(f.getName(),f.getStyle(),getFont().getSize()));
//             mess.fontname = f.getName();
//         }
//       }
//    }
    setFont(sharkGame.sizeFont(new String[]{"abc"},w/20,h/10));
    setForeground(Color.black);
    m=getFontMetrics(getFont());
    this.getCaret().setVisible(true);
  }
//startPR2007-12-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void paint(Graphics g) {
    ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    super.paint(g);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
}
class sendwindow
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  JButton exit = u.Button("messages_exit");
  JButton returnb = u.Button("messages_return");
  JList jl = new JList();
  String s[] = db.dblist(new File[]{sharkStartFrame.sharedPath});
  message mess2=new message();
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public sendwindow() {
  public sendwindow(JDialog owner) {
    super(owner);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     short i;
     student stu;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
    jl.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_ESCAPE)
          dispose();
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     mess2.from = myname;
     mess2.text=disp.getText();
     setTitle(u.gettext("messages_","sendtitle"));
     for(i=0;i<s.length;++i) {
        s[i] = s[i].substring(s[i].lastIndexOf(sharkStartFrame.separator) + 1);
        if(s[i].equals(myname)){
          s=u.removeString(s,i);
          --i;
        }
     }
     jl.setListData(s);
     jl.setSelectedIndex(0);
     jl.getSelectionModel().setSelectionMode(jl.getSelectionModel().SINGLE_SELECTION);
    JPanel border = new JPanel(new BorderLayout());
    border.setBorder(BorderFactory.createLineBorder(Color.black,2));
    border.add(new JScrollPane(jl), BorderLayout.CENTER);
    this.getContentPane().setLayout(new BorderLayout());
    this.getContentPane().add(border, BorderLayout.CENTER);
//    setBounds(w/4+w/24,h/4+h/24,w*2/3,h);
    setBounds(u2_base.adjustBounds(new Rectangle(w/4+w/24,h/4+h/24,w*2/3,h)));
    JPanel pp = new JPanel(new GridBagLayout());
    GridBagConstraints grid = new GridBagConstraints();
    grid.weightx = grid.weighty = 1;
    grid.fill = GridBagConstraints.NONE;
    grid.gridx = -1;
    grid.gridy = 0;
    pp.add(returnb,grid);
    pp.add(exit,grid);
    this.getContentPane().add(pp,BorderLayout.SOUTH);
    validate();
    int sel[] = (int[])jl.getSelectedIndices();
    jl.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        int sel[] = jl.getSelectedIndices();
        for (int i = 0; i < sel.length; ++i) {
          if (s[sel[i]].endsWith(sent))
            continue;
          String name = "mess";
          mess2.to = mess.to = s[sel[i]];
          mess2.d = new Date();
          for (int j = 0;
               db.query(s[sel[i]], name = "mess" + String.valueOf(j),
                        db.MESSAGE) >= 0; ++j);
          db.update(s[sel[i]], name, mess2, db.MESSAGE);
          s[sel[i]] = s[sel[i]] + sent;
        }
        jl.setListData(s);
      }
    });
    jl.addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {
            jl.setSelectedIndex(jl.locationToIndex(e.getPoint()));
      }
    });
    returnb.setRequestFocusEnabled(false);
    returnb.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         dispose();
      }
    });
    exit.setRequestFocusEnabled(false);
    exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         textframe.dispose();
         dispose();
      }
    });
    setVisible(true);
  }
}
}
