package shark;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;

public abstract class stringedit_base
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

   public static final int MODE_NORMAL = 0;
   public static final int MODE_WORDLISTEDIT = 1;
   public static final int MODE_STUDENTEDIT = 2;
   public static final int MODE_EXCLUDEAUTOSIGNON = 3;
   int mode = MODE_NORMAL;
   public String s;
   int h,w;
   JLabel mm;
   boolean updating,finished;
   JButton ok = new sharkGame.topbutton(u.gettext("ok", "label"));
   JButton cancel = new sharkGame.topbutton(u.gettext("cancel", "label"));
   static String saved[],undo[]=new String[50];
   int pos[]=new int[50];
   int curr;   // last saved in undo
   JScrollPane scroll;
   display disp;
   public boolean ignorexclick,staythere;
   boolean allowBlankLinesAndOuterSpaces = true;
   boolean trimPaste = false;
   boolean usekeypad = false;
   JDialog thisd = this;

   
   public stringedit_base(String tit,String d[],JFrame parent, int mode1, boolean allowblanknodes){
     super(parent);
     allowBlankLinesAndOuterSpaces = allowblanknodes;
     mode = mode1;
     setup(tit, d);
  }   

   public stringedit_base(String tit,String d[],JDialog parent, int mode1, boolean allowblanknodes){
     super(parent);
     allowBlankLinesAndOuterSpaces = allowblanknodes;
     mode = mode1;
     setup(tit, d);
  }

   public stringedit_base(String tit,String d[],JDialog parent, int mode1, boolean allowblanknodes, boolean showkeypad){
     super(parent);
     allowBlankLinesAndOuterSpaces = allowblanknodes;
     mode = mode1;
     usekeypad = showkeypad;
     setup(tit, d);
  }   
   
   public stringedit_base(String tit,String d[],JDialog parent, int mode1){
     super(parent);
     mode = mode1;
     setup(tit, d);
  }
   
   public stringedit_base(String tit,String d[],JDialog parent, boolean allowblanknodes, boolean showkeypad){
     super(parent);
     allowBlankLinesAndOuterSpaces = allowblanknodes;
     usekeypad = showkeypad;
     setup(tit, d);
  }   

   public stringedit_base(String tit,String d[],JDialog parent, boolean allowblanknodes){
     super(parent);
     allowBlankLinesAndOuterSpaces = allowblanknodes;
     setup(tit, d);
  }

   public stringedit_base(String tit,String d[],JDialog parent){
     super(parent);
     setup(tit, d);
  }
   public stringedit_base(String tit,String d[]){
 //startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(sharkStartFrame.mainFrame);
 //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       setup(tit, d);
    }
  void setup(String tit,String d[])  {
    if(d==null || d.length==0)
      s = "";
    else
      s = u.combineString(d,"\n");
    setTitle(tit);
    this.getContentPane().setBackground(runMovers.tooltipbg);

//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //was true
    this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
         if(ignorexclick || update1()){
             if(usekeypad)keypad.dofullscreenkeypad(thisd, false);
             dispose();
         }
      }
      public void windowDeactivated(WindowEvent e) {
         if(!staythere && !updating && !finished)  {if (ignorexclick || update1()) dispose();}
       }
    });
    this.getContentPane().setLayout(new BorderLayout());

    if(mode==MODE_WORDLISTEDIT || mode==MODE_STUDENTEDIT || mode==MODE_EXCLUDEAUTOSIGNON){
        JPanel pp1 = new JPanel(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.weightx = 1;
        grid.weighty = 1;
        JPanel toppn = new JPanel(new GridBagLayout());
        JButton btPaste = u.sharkButton("paste");
        btPaste.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String s[] = disp.getText().split("\n");
                    s = u.addString(s, u.getSystemClipboard());
                    for(int i = s.length - 1; i >= 0; i--){
                        s[i] = s[i].trim().replace(',', '|');
                        if(s[i].equals(""))s = u.removeString(s, i);
                    }
                    disp.setText(u.convertToCR(u.combineString(s)));
              }
         });
        JTextArea steppedmessta = new JTextArea();
        steppedmessta.setEditable(false);
        steppedmessta.setWrapStyleWord(true);
        steppedmessta.setLineWrap(true);
        steppedmessta.setOpaque(false);
        String ss = "";
        if(mode==MODE_WORDLISTEDIT)
            ss = u.edit(u.convertToCR(u.gettext("ownwordlists", "clipboardmess")), u.gettext("paste", "label"), u.gettext("ok", "label"));
        else if(mode==MODE_STUDENTEDIT)
            ss = u.edit(u.convertToCR(u.gettext("multstu", "clipboardmess")), u.gettext("paste", "label"), u.gettext("ok", "label"));
        else if(mode==MODE_EXCLUDEAUTOSIGNON)
            ss = u.edit(u.convertToCR(u.gettext("adminsettings", "autoexclusionsmess")), u.gettext("paste", "label"), u.gettext("ok", "label"));
        steppedmessta.setText(ss);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
        int margin = 5;
        int margin2 = 10;
        grid.insets = new Insets(margin, margin, margin, margin);
        toppn.add(steppedmessta, grid);
        grid.weightx = 0;
        grid.insets = new Insets(margin2, margin, margin2, margin2);
        toppn.add(btPaste, grid);
        grid.weightx = 1;
        grid.insets = new Insets(0, 0, 0, 0);
        toppn.setBackground(sharkStartFrame.cream);
        toppn.setOpaque(true);
        pp1.add(toppn, grid);
        this.getContentPane().add(pp1,BorderLayout.NORTH);
    }

    this.getContentPane().add(scroll = new JScrollPane(disp=new display()),BorderLayout.CENTER);
    JPanel pp = new JPanel(new BorderLayout());
    this.getContentPane().add(pp,BorderLayout.SOUTH);
    String s_copy = u.gettext("textcontrols", "copy");
    String s_paste = u.gettext("textcontrols", "paste");
    String s_undo = u.gettext("textcontrols", "undo");
    String s_cut = u.gettext("textcontrols", "cut");
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on a Macintosh
    if (shark.macOS) {
      String commandSign = String.valueOf('\u2318');
      pp.add(mm = new JLabel(
          commandSign+"Z = "+s_undo+"    "+commandSign+"X = "+s_cut+"    "+commandSign+"C = "+s_copy+"    "+commandSign+"V = "+s_paste),
           BorderLayout.NORTH);
    }
    // if running on Windows
    else {
      pp.add(mm = new JLabel(
          "Ctrl-Z = "+s_undo+"  Ctrl-X = "+s_cut+"   Ctrl-C = "+s_copy+"   Ctrl-V = "+s_paste),
           BorderLayout.NORTH);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mm.setHorizontalAlignment(mm.CENTER);
    mm.setBackground(runMovers.tooltipbg);
    mm.setOpaque(true);
    mm.setForeground(Color.blue);
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS){
      pp.add(cancel,BorderLayout.WEST);
      pp.add(ok,BorderLayout.CENTER);
    }
    else{
      pp.add(ok,BorderLayout.CENTER);
      pp.add(cancel,BorderLayout.EAST);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    validate();
    ok.setRequestFocusEnabled(false);
    ok.setBackground(Color.blue);
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS)
      ok.setForeground(Color.blue);
    else
      ok.setForeground(Color.white);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    ok.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(update1())
           dispose();
      }
    });
    cancel.setRequestFocusEnabled(false);
    cancel.setBackground(Color.blue);
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if(shark.macOS)
      cancel.setForeground(Color.blue);
    else
      cancel.setForeground(Color.white);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    cancel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         finished = true;
         dispose();
      }
    });
    
    int kwid = -1;
    if(usekeypad){
        keypad.dofullscreenkeypad(thisd, true);
        kwid = keypad.keypadwidth(thisd)+keypad.usekeypad.borderInWindow;
    }
    w = sharkStartFrame.mainFrame.getSize().width*2/3;
    h = sharkStartFrame.mainFrame.getSize().height*3/4;
    int xx = (sharkStartFrame.mainFrame.getSize().width-w)/2;
    int yy = (sharkStartFrame.mainFrame.getSize().height-h)/2;
    if(usekeypad){
        w = Math.min(sharkStartFrame.mainFrame.getSize().width - kwid, w);
        xx = (sharkStartFrame.mainFrame.getSize().width-w)-kwid;   
    }
//    setBounds(xx,yy,w,h);
    setBounds(u2_base.adjustBounds(new Rectangle(xx,yy,w,h)));
    setVisible(true);
    disp.requestFocus();
    
   }

  boolean update1() {
     String s2[] = u.splitString(disp.getText(),'\n');
//      while(s2.length>0 && s2[s2.length-1].length()==0)
//        s2 = u.removeString(s2,s2.length-1);
     for(int i = s2.length-1; i>=0; i--){
         if(!allowBlankLinesAndOuterSpaces){
             s2[i] = u.stripspaces3(s2[i]);
             if(s2[i].length()==0)
                s2 = u.removeString(s2,i);
         }
     }
     updating = true;
     boolean ok = update(s2);
     if(ok) finished=true;
     updating = false;
     return ok;
  }
  public abstract boolean update(String s[]);
  //--------------------------------------------------------------
  class display extends JTextArea {
    public boolean updating, justhadchar,addedspace;
    FontMetrics m;
    int caretpos,delfrom;
    public display() {
      super();
      setBackground(runMovers.tooltipbg);
      setDocument(new serialdoc());
      setText(s);
//    this.setWrapStyleWord(true);
      this.setLineWrap(false);
      if(keypad.keypadname != null) {
//startPR2007-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         keypad.usekeypad u = keypad.find(sharkStartFrame.mainFrame);
//         if(u != null) {
//            Font f = u.kk.font;
//            if(!f.getName().equalsIgnoreCase("wordshark")) {
           keypad.usekeypad use = keypad.find(sharkStartFrame.mainFrame);
           if(use != null) {
              Font f = use.kk.font;
              if(!u.isdefaultfont(f.getName())) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                setFont(new Font(f.getName(),f.getStyle(),getFont().getSize()));
                setFont(f.deriveFont((float)getFont().getSize()));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
         }
      }
      setForeground(Color.black);
      this.getCaret().setVisible(true);
      addKeyListener(new KeyAdapter() {
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            boolean isctl = (e.getModifiers() & e.CTRL_MASK) != 0;
            boolean isctl = (e.getModifiers() & Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) != 0;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(code == KeyEvent.VK_ESCAPE) {
              if(update1()) dispose();
            }
            s=getText();
            if(!s.equals(undo[curr])) {
                undo[curr = (curr+1)%undo.length] = s;
                pos[curr] = getCaretPosition();
            }
            if(code == 90 && isctl) {          // undo
                if(undo[curr] != null) {
                  undo[curr]=null;
                  if (--curr < 0) curr = undo.length-1;
                  if (undo[curr] != null) {
                     setText(s = undo[curr]);
                     setCaretPosition(pos[curr]);
                  }
                  else noise.beep();
                }
             }
        }
      });
    }

   class serialdoc extends PlainDocument {
     public void insertString(int o, String s, AttributeSet a) {
       try{
           if(trimPaste){
               char c = '\n';
               String ss[] = u.splitString(s, c);
               if(ss.length>2){
                   for(int i = ss.length - 1; i >= 0; i--){
                       ss[i] = ss[i].trim();
                       if(ss[i].equals(""))ss = u.removeString(ss, i);
                   }
                   s = u.combineString(ss, String.valueOf(c));
               }
           }
           super.insertString(o,s,a);
       }
       catch(BadLocationException e) {}
     }
   }

    
  }

}
