package shark;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

public class options
//startPR2004-08-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public static boolean changed, active;
  public runMovers gamePanel;
  boolean changed1;
  boolean presetting;
  String[] values;
  boolean[] preset;
  JCheckBox presetcb[];

//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   Cursor prevCursor;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   String op[];
   GridBagLayout layout1 = new GridBagLayout();
   GridBagConstraints grid = new GridBagConstraints();
   options thisoptions;
   String mess;
   String gamename;
   Color bg = getContentPane().getBackground();
   String groupedoptions[];
   ArrayList groupedoptionscb;
   String title2;
   String options2[];
   String mess2;
   JButton doDefaults;

   public options(String title,String options1[],String mess1,String gg) {
       super(sharkStartFrame.mainFrame);
       title2 = title;
       options2 = options1;
       mess2 = mess1;
       presetting = true;
       gamename=gg;
       common(true, title2,options2,mess2);
   }
   public options(String title,String options1[], runMovers manager,String mess1) {
       super(manager.currgame);
       title2 = title;
       options2 = options1;
       mess2 = mess1;
       gamePanel = manager;
       gamename = runningGame.currGameRunner.gamename;
       common(true, title2,options2,mess2);
   }
   public options(String title,String options1[], String groupopt[], runMovers manager,String mess1) {
       super(manager.currgame);
       title2 = title;
       options2 = options1;
       mess2 = mess1;
       groupedoptions = groupopt;
       gamePanel = manager;
       gamename = runningGame.currGameRunner.gamename;
       common(true, title2,options2,mess2);
   }


   void common(boolean init, String title,String options1[],String mess1) {
      java.awt.Container inpanel = getContentPane();
      int i,j;
      String narr;
      mess=mess1;      
      if(presetting) {
         for(i=0;i<options1.length;++i) {
             j = options1[i].indexOf('=');
             if(options1[i].length()>j && options1[i].charAt(j+1) == '=') ++j;
             preset(options1[i].substring(0, j), options1[i].substring(j+1));
         }
         presetcb = new JCheckBox[op.length];
         if(preset == null) preset = new boolean[op.length];
      }
      else {
         op = options1;
      }      
      if(init){
          this.setResizable(false);
          if(presetting)this.setModal(true);
          else{
             if(shark.macOS){
              prevCursor = gamePanel.getCursor();
              gamePanel.setCursor(Cursor.getDefaultCursor());
            }              
             
          }
          thisoptions=this;
          setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
          getContentPane().setLayout(layout1);
          int border = 20;
//          if(op.length < 6)
//             setSize(sharkStartFrame.mainFrame.getSize().width*2/3,
//                    sharkStartFrame.mainFrame.getSize().height/2);
//             else setSize(sharkStartFrame.mainFrame.getSize().width*2/3,
//                    sharkStartFrame.mainFrame.getSize().height);
          if(op.length < 6){
//              setBounds(border, border, 
//                  u.screenResWidthMoreThan(1200)?sharkStartFrame.mainFrame.getSize().width*9/16:sharkStartFrame.mainFrame.getSize().width*2/3,
//                  (sharkStartFrame.mainFrame.getSize().height/2)-border);
              setBounds(u2_base.adjustBounds(new Rectangle(border, border, 
                  u.screenResWidthMoreThan(1200)?sharkStartFrame.mainFrame.getSize().width*9/16:sharkStartFrame.mainFrame.getSize().width*2/3,
                  (sharkStartFrame.mainFrame.getSize().height/2)-border)));
          }
          else{
//              setBounds(border, border, 
//                   u.screenResWidthMoreThan(1200)?sharkStartFrame.mainFrame.getSize().width*9/16:sharkStartFrame.mainFrame.getSize().width*2/3,
//                   sharkStartFrame.mainFrame.getSize().height-border*2); 
              setBounds(u2_base.adjustBounds(new Rectangle(border, border, 
                   u.screenResWidthMoreThan(1200)?sharkStartFrame.mainFrame.getSize().width*9/16:sharkStartFrame.mainFrame.getSize().width*2/3,
                   sharkStartFrame.mainFrame.getSize().height-border*2)));
          }
          setTitle(title);
          
    this.addWindowListener(new java.awt.event.WindowAdapter() {
        public void windowDeactivated(WindowEvent e) {
           if(changed1) changed = true;
           active = false;
           thisoptions.dispose();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(shark.macOS && !presetting){
//             gamePanel.setCursor(prevCursor);
//             gamePanel.refreshat = System.currentTimeMillis() + 2000;
//           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        public void windowClosing(WindowEvent e) {
         if(changed1) changed = true;
         active = false;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(shark.macOS && !presetting){
//           gamePanel.setCursor(prevCursor);
//           gamePanel.refreshat = System.currentTimeMillis() + 2000;
//         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
     });
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     addKeyListener(new java.awt.event.KeyAdapter() {
//      public void keyPressed(KeyEvent e) {
//         int code = e.getKeyCode();
//      }
//     });
        // enables exiting screen via the ESC key
        this.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if(code == KeyEvent.VK_ESCAPE)
                  dispose();
            }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR          
          
      }
      grid.fill = GridBagConstraints.NONE;
      grid.gridx = 0;
      grid.gridy = -1;
      grid.gridheight = 1;
      grid.weighty = 1;
      grid.weightx = 1;
      GridBagConstraints grid2 = new GridBagConstraints();
      grid2.gridx = 0;
      grid2.gridy = -1;
      grid2.gridheight = 1;
      grid2.weighty = 1;
      grid2.weightx = 1;
      grid2.fill = GridBagConstraints.NONE;
      for(i=0;i<op.length;++i) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        String removesuboption = null;
        short seloption = -1;
//startPR2010-05-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(runningGame.currGameRunner!=null && runningGame.currGameRunner.game!=null){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sharkGame sg = runningGame.currGameRunner.game;
        if((sg.unwantedOptions)!=null){
          for(int n = 0; n <sg.unwantedOptions.length; n++){
            if(sg.unwantedOptions[n].optionname.equals(op[i])){
              if(sg.unwantedOptions[n].suboption!=null){
                removesuboption = sg.unwantedOptions[n].suboption;
                seloption = sg.unwantedOptions[n].selected;
              }
              else
                continue;
            }
          }
        }
//startPR2010-05-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(op[i].length() == 0) {
           getContentPane().add(new JLabel(""),grid);
           continue;
        }
        if((narr=u.gettext(op[i],"label")) == null) {
           noise.beep();
           continue;
        }
        if(presetting) {
          inpanel = new JPanel(new GridBagLayout());
          ((JPanel)inpanel).setBorder(BorderFactory.createEtchedBorder());
          getContentPane().add(inpanel,grid);
          presetcb[i] = u.CheckBox("options_preset");
          presetcb[i].addActionListener(new presetli(i));
          presetcb[i].setSelected(preset[i]);
          inpanel.setBackground(preset[i]?Color.pink:bg);
          inpanel.add(presetcb[i],grid);
        }
        int k;
        if ((j = (short)op[i].indexOf("-image-")) >= 0) {
           JButton b = u.Button(op[i]);
           b.addActionListener(new chooseimagelistener(op[i],b.getText()));
           inpanel.add(b,grid);
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             b.addKeyListener(new KeyAdapter() {
                 public void keyPressed(KeyEvent e) {
                     int code = e.getKeyCode();
                     if(code == KeyEvent.VK_ESCAPE)
                       dispose();
                 }
             });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else if((k =  isslider(op[i])) >= 0) {
           String name = op[i];
           String value = values[i];
           if(value.startsWith("^")){
               value = value.substring(1);
           }
           int val = presetting?Integer.parseInt(value):optionval(name.substring(0,name.length()-1));
           if(val < 1) val = 50;
           JPanel p = new JPanel();
           p.setBorder(BorderFactory.createEtchedBorder());
           p.setToolTipText(u.gettext(op[i],"tooltip"));
           p.setLayout(new GridBagLayout());
           JLabel title1 = u.label(op[i]);
           JSlider opView;
           if(k==1){
               String s = defaultstring(op[i].substring(0, op[i].lastIndexOf('=')),gamename);
               opView = u.jumpToSlider(JSlider.HORIZONTAL, 1, 100, val, Integer.parseInt(s) , 25);
            }
           else
               opView =  new JSlider(JSlider.HORIZONTAL, 1, 100, val);
           opView.setToolTipText(title1.getToolTipText());
           opView.addChangeListener(new opChangeListener(name,opView));
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // enables exiting screen via the ESC key
           opView.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           p.add(title1, grid2);
           p.add(opView, grid2);
           inpanel.add(p, grid);
        }
        else if(narr.indexOf('|') >= 0) {
           String s[] = u.splitString(narr);
           short val = presetting?(short)Integer.parseInt(values[i]):optionval(op[i]);
           if(val <1) val = 1;
           val = (short)Math.min(val,s.length);
          --val;

           ButtonGroup bg = new ButtonGroup();
           JPanel cb = new JPanel();
           cb.setLayout(new GridBagLayout());
           GridBagConstraints gr = new GridBagConstraints();
           cb.setBorder(BorderFactory.createEtchedBorder());
           gr.gridx = 0;
           gr.gridy = -1;
           gr.weightx = gr.weighty = 1;
           gr.fill = GridBagConstraints.BOTH;
           String tt = u.gettext(op[i],"tooltip");
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           short m = -1;
           if(removesuboption!=null){
             for(j=0;j<s.length;++j) {
               if(removesuboption.equals(s[j])){
                 m = (short)j;
                 if (seloption >= 0) {
                   val = seloption;
                   val--;
                 }
               }
             }
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           for(j=0;j<s.length;++j) {
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(j==m)continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              JRadioButton jr = new JRadioButton(s[j]);
              jr.addItemListener(new gpSwitchListener(op[i],(short)j));
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              jr.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                  int code = e.getKeyCode();
                  if(code == KeyEvent.VK_ESCAPE)
                    dispose();
                }
              });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              cb.add(jr,gr);
              bg.add(jr);
              if(j==val) jr.setSelected(true);
              jr.setToolTipText(tt);
           }
           inpanel.add(cb, grid);
        }
        else {
           JCheckBox cb = u.CheckBox(op[i]);
           if(u.findString(groupedoptions, op[i])>=0){
               if(groupedoptionscb==null)groupedoptionscb = new ArrayList();
               groupedoptionscb.add(cb);
           }
           cb.setSelected(presetting?values[i].equals("y"):option(op[i]));
           cb.addItemListener(new opSwitchListener(op[i],cb));
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           // enables exiting screen via the ESC key
           cb.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(code == KeyEvent.VK_ESCAPE)
                 dispose();
             }
           });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           inpanel.add(cb,grid);
        }
     }
     if(presetting) {
       for(i=0;i<presetcb.length;++i) {
          for(j=0;j<presetcb[i].getParent().getComponentCount();++j) {
             ((JComponent)(presetcb[i].getParent().getComponent(j))).setOpaque(false);
          }
       }
     }
     JButton exit = u.Button("option-exit");
     exit.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
             if(changed1) changed = true;
             active = false;
             thisoptions.dispose();
           }
     });
     doDefaults = u.Button("option-dodefaults");
     setDefaultButton();
     doDefaults.addActionListener(new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
            for(int i=0;i<op.length;++i) {
                String s = (op[i].endsWith("="))?op[i].substring(0,op[i].length()-1):op[i];
                student.removeOption(s);
            }
            getContentPane().removeAll();
            changed1 = true;
            common(false, title2,options2,mess2);
          }
     });
     if(!presetting)
         exit.setPreferredSize(doDefaults.getPreferredSize());
     
     grid.gridx = -1;
     grid.gridy = 0; 
     JPanel buttonpn = new JPanel(new GridBagLayout());
     if(!shark.macOS){
        buttonpn.add(exit, grid);
        if(!presetting){
            grid.insets = new Insets(0,20,0,0);
            buttonpn.add(doDefaults, grid); 
        }
     }
     else{
         if(!presetting){
            grid.insets = new Insets(0,0,0,20);
            buttonpn.add(doDefaults, grid);
         }
         grid.insets = new Insets(0,0,0,0);
         buttonpn.add(exit, grid);
     }
     grid.insets = new Insets(0,0,0,0);
     grid.gridx = 0;
     grid.gridy = -1;
     
     
     
 //    if(!presetting)
 //       getContentPane().add(doDefaults,grid);
 //    getContentPane().add(exit,grid);
     getContentPane().add(buttonpn,grid); 
     
     if(mess != null) {
        getContentPane().add(new mlabel_base(mess),grid);
     }
     if(init){
       active = true;
       setVisible(true);
     }
     validate();
//startPR2004-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     if(init)
         this.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   
   void doChanged(){
       changed1 = true;
       setDefaultButton();
   }
   
   void setDefaultButton(){
       if(!presetting){
           boolean b = werechanged(runningGame.currGameRunner.game);
           doDefaults.setEnabled(b);
       }
   }  
   
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     public void dispose(){
       super.dispose();
       if(shark.macOS && gamePanel.currgame!=null){
         gamePanel.currgame.setsprite();
         gamePanel.refreshat = System.currentTimeMillis() + 2000;
       }
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   //---------------------------------------------------------------------------------
   void preset(String opt,String val) {
       int i = u.findString(op,opt);
       if(i<0) {  // first time in list, from game spec "options="
          op = u.addString(op,opt);
          values = u.addString(values,val);
       }
       else {     // second time in list, preset
          if(preset == null) preset = new boolean[op.length];
          preset[i] = true;
          values[i] = val;
       }
   }
   class presetli implements java.awt.event.ActionListener {
     int which;
     presetli(int which1) {
       which = which1;
     }
     public void actionPerformed(ActionEvent e) {
        preset[which] = presetcb[which].isSelected();
        presetcb[which].getParent().setBackground(preset[which]?Color.pink:bg);
     }
   }
   //------------------------------------------------------------------
   String getpreset() {
       int i;
       String s= "";
       for(i=0;i<op.length;++i) {
          if(preset[i]) {
            if(s.length()>0) s += ",";
            s += op[i]+'='+values[i];
          }
       }
       return s;
   }
   //------------------------------------------------------------
   class opChangeListener implements ChangeListener {
     String op1;
     JSlider master;
     public opChangeListener(String opx,JSlider m1) { op1 = opx;master=m1;}
     public void stateChanged(ChangeEvent e){
        if(presetting) values[u.findString(op,op1)] = String.valueOf(master.getValue());
        else {
          String ss = String.valueOf(master.getValue());
          if(defaultstring(op1,gamename).equals(ss)) {
            if (op1.endsWith("="))   student.removeOption(op1.substring(0, op1.length() - 1));
            else student.removeOption(op1);
          }
          else {
            if(op1.endsWith("=")) student.setOption(op1.substring(0,op1.length()-1), ss);
            else student.setOption(op1, ss);
          }
        }
        doChanged();
     }
  }
  //------------------------------------------------------------
  class opSwitchListener implements java.awt.event.ItemListener {
     String op1;
     JCheckBox cb;
     public opSwitchListener(String opx,JCheckBox cbx) { op1 = opx;cb=cbx;}
     public void itemStateChanged(ItemEvent e) {
        boolean sel = cb.isSelected();
         if(groupedoptionscb!=null){
             boolean anyselected = false;
             boolean groupmember = false;
             for(int i = 0; i < groupedoptionscb.size(); i++){
                 JCheckBox gpcb = (JCheckBox)groupedoptionscb.get(i);
                 if(gpcb.isSelected())anyselected=true;
                 if(cb.equals(gpcb))groupmember = true;
             }
             if(groupmember && !anyselected){
                 cb.setSelected(true);
                 return;
             }
         }
         
        if(presetting) {
           int pos = u.findString(op,op1);
           String ss = sel?"y":"n";
           if(!values[pos].equals(ss)) {
              values[pos] = ss;
              doChanged();
           }
        }
        else {
          if (sel != option(op1)) {
            String ss = sel?"y":"n";
            if(defaultstring(op1,gamename).equals(ss)) student.removeOption(op1);
            else student.setOption(op1,ss);
            doChanged();
          }
        }
     }
  }
  //------------------------------------------------------------
  class gpSwitchListener implements java.awt.event.ItemListener {
     String op1;
     short which;
     public gpSwitchListener(String opx,short which1) {
        op1 = opx;
        which=which1;
    }
     public void itemStateChanged(ItemEvent e) {
       if(presetting) values[u.findString(op,op1)] = String.valueOf(which+1);
       else {
         if(which +1 == optionval(op1)) return;   // no change
         if(defaultstring(op1,gamename).equals(String.valueOf(which+1))) student.removeOption(op1);  // default val
         else student.setOption(op1, (short) (which + 1));
       }
       doChanged();
     }
  }
  //------------------------------------------------------------
  class chooseimagelistener implements java.awt.event.ActionListener {
     String op1;
     int j;
     String narra;
     public chooseimagelistener(String opx, String na) {
        op1 = opx;
        narra = na;
     }
     public void actionPerformed(ActionEvent e) {
        j = op1.indexOf("-image-");
        narra = op1.substring(j+7);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        chooseImage c = new chooseImage(narra,op1);
        if(presetting) {
          chooseImage c = new chooseImage(narra, op1);
        }
        else {
          chooseImage c = new chooseImage(narra, op1, gamePanel.currgame);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     }
  }
  //--------------------------------------------------------------
  class chooseImage extends chooser_base {
   int w = sharkStartFrame.screenSize.width/18;
   int h = sharkStartFrame.screenSize.height/12;
   String dir[]= new String[0];
   String name[]= new String[0], s;
   String op1;
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   String narra;
   String optionname;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public chooseImage (String narr,String optionnme) {
//     super(u.gettext("chooseimage","heading")+ ' '+ narra);
     super(u.edit(u.gettext("chooseimage","heading"), narr));
     narra = narr;
     optionname = optionnme;
     addConstructor();
   }
   public chooseImage (String narr,String optionnme, JFrame owner) {
     super(u.edit(u.gettext("chooseimage","heading"), narr), owner);
     narra = narr;
     optionname = optionnme;
     addConstructor();
   }
   public chooseImage (String narr,String optionnme, JDialog owner) {
     super(u.edit(u.gettext("chooseimage","heading"),narr), owner);
     narra = narr;
     optionname = optionnme;
     addConstructor();
   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   private void addConstructor(){
     String s[];
     File d;
     short i,j=0,k;
     op1 = optionname;

     if(sharkStartFrame.currStudent>=0) {
        s = db.list(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,db.IMAGE,narra+"_");
        for(j= 0; j < s.length; ++j) {
           dir = u.addString(dir,sharkStartFrame.studentList[sharkStartFrame.currStudent].name);
           name = u.addString(name, s[j]);
        }
     }
     for(i=0; i<sharkStartFrame.publicImageLib.length;++i)  {
        s = db.list(sharkStartFrame.publicImageLib[i],db.IMAGE,narra+"_");
        for(j= 0; j < s.length; ++j) {
           dir = u.addString(dir,sharkStartFrame.publicImageLib[i]);
           name = u.addString(name, s[j]);
        }
     }
     for(i=0;i<dir.length;++i) {
         add(dir[i],name[i],w,h);
     }
     showit();

   }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

    public void selected(int item) {
      if(presetting)
        values[u.findString(op,op1)] = String.valueOf(dir[item]+"^"+name[item]);
      else   {
        card.backimage = new sharkImage(dir[item], name[item], false, false);
        String ss = dir[item] + "^" + name[item];
        if(defaultstring(op1,gamename).equals(ss)) student.removeOption(ss);
        else   student.setOption(op1, ss);
        gamePanel.currgame.restart();
      }
      mainPanel.stop();
      active = false;
      thisoptions.dispose();
      dispose();
   }
  }
  //-------------------------------------------------------------------------------
  // at start of game
  static void gamestart(topic t, String game) {
     int i,j,k,m;
     student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
     String currtopic = stu.optionstring2("currtopic_");  // topic name
           // no topic saved so student came from earlier version - convert old boolean options to 'option=y/n'
     if(currtopic==null) {
       stu.setOption2("currtopic_",currtopic=t.name);
       String gg[] = sharkStartFrame.mainFrame.publicGameTree.titles();  // get list of games
       String op[] = new String[0];
       for(i=0;i<gg.length;++i) {   // build list of boolean options
          String pp[] = defaults(gg[i]);
          for(j=0;j<pp.length;++j) {
            if(pp[j].endsWith("=y") || pp[j].endsWith("=n"))
              op = u.addStringSort(op, pp[j].substring(0,pp[j].length()-2));
          }
       }
       for(i=0;i<op.length;++i) {   // set up new option assignments if setting is nor default value
              if (stu.option2(op[i])) {
                stu.clearOption2(op[i]);
                if (op[i].endsWith("=n"))   stu.setOption2(op[i], "y");
              }
              else {
                if (op[i].endsWith("=y"))   stu.setOption2(op[i], "n");
              }
       }
     }
     // if change of topic - restore options
     if(!currtopic.equals(t.name)) {
       stu.setOption2("currtopic_",currtopic=t.name);
       if(stu.optionstring2("presetgames_")!=null) {
         stu.removeOption2("presetgames_");
         for (i = 0; i < stu.options.length; ++i) {
           if (stu.options[i].startsWith(">")) { // overridden option not subsequenty changed - restore
             String name = stu.options[i];
             j = name.indexOf(">", 1);
             if (j <= 0) {
               stu.options = u.removeString(stu.options, i); --i; continue; }
             String oldgame = name.substring(1, j);
             k = name.indexOf('=', j + 1);
             if (k > 0) { // value was saved
               String oldname = name.substring(j + 1, k);
               String oldval = name.substring(k + 1); // saved option value
               String newval = stu.optionstring(oldname); // get current value
               if (newval == null)
                 stu.setOption2(oldname, oldval); // no new val - restore old
               else if (newval.equals(actualdefault(oldname, oldgame))) // otherwise leave new value
                 stu.removeOption2(oldname); // except delete it if is the default
               stu.removeOption2(name.substring(0, k)); // remove this entry
             }
             else { // no saved value
               String oldname = name.substring(j + 1);
               String newval = stu.optionstring(oldname); // get current value
               if (newval != null && newval.equals(actualdefault(oldname, oldgame))) // leave new value
                 stu.removeOption2(oldname); // except delete it if is the default
               stu.clearOption2(name); // remove this entry
             }
             i = Math.max( -1, i - 2);
           }
         }
       }
     }
     if(t.gameoptionlist != null) {
       String gamec = game+',';
       String alreadygames[];
       for(i=0;i<t.gameoptionlist.length;++i) {
          if(t.gameoptionlist[i].startsWith(gamec)) {
              alreadygames = u.splitString(stu.optionstring2("presetgames_"));
              if(alreadygames == null) {
                alreadygames = new String[0];
             }
             if(u.findString(alreadygames,game) < 0) {
               stu.setOption2("presetgames_", u.combineString(u.addString(alreadygames, game)));
               String options1[] = u.splitString(t.gameoptionlist[i].substring(gamec.length()), ',');
               for (k = 0; k < options1.length; ++k) {
                 m = options1[k].indexOf('=');
                 saveandset(options1[k].substring(0, m),game);
               }
             }
             break;
        }
       }
     }
  }
  //---save prev value and remove
  static void saveandset(String name,String game) {
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    String oldval = stu.optionstring2(name);
    String savename = ">"+game+">"+name;
    if (stu.optionval2(savename) < 0 && !stu.option2(savename)) {
      if (oldval != null)   stu.setOption2(savename, oldval);
      else   stu.setOption2(savename); // note that no old option set
    }
    stu.removeOption2(name);
  }
  //-------------------------------------------------------------------------------------------------
  static boolean werechanged(sharkGame g) {
     String presets[]  = sharkStartFrame.currPlayTopic.gameoptionlist;
     String game = runningGame.currGameRunner.gamename;
     String defaults[] = defaults(game);
     String gamec = game+',';
     String[] options1 = g.optionlist;
     int i,j;
     boolean got = false;
     if(defaults==null || defaults.length == 0) return false;
     if(presets != null) for(i=0;i<presets.length;++i) {
        if(presets[i].startsWith(gamec)) {
          presets = u.splitString(presets[i].substring(gamec.length()),',');
          got = true;
          break;
        }
     }
     if(!got) presets = null;
     loop1:for(i=0;i<options1.length;++i) {
       if(presets != null) {
          for(j=0;j<presets.length;++j) {
            if (presets[j].startsWith(options1[i] + '=')) {
               if(didchange(presets[j])) return true;
               continue loop1;
            }
          }
       }
       for(j=0;j<defaults.length;++j) {
         if (defaults[j].startsWith((!options1[i].endsWith("="))?options1[i] + '=':options1[i])) {
            if(didchange(defaults[j]))
              return true;
            continue loop1;
         }
       }
     }
     return false;
  }
  static boolean didchange(String opt) {
    int m;
    String s;
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    if ( (m = opt.indexOf("==")) > 0) {  // value as in defaults list
      s = stu.optionstring2(opt.substring(0,m));
      return s!=null && !opt.substring(m + 2).equals(s);
     }
    else if ( (m = opt.indexOf("=^")) > 0) {  // value as in defaults list
      s = stu.optionstring2(opt.substring(0,m));
      return s!=null && !opt.substring(m + 2).equals(s);
     }
     else {                     // all others
       m = opt.indexOf('=');
       s = stu.optionstring2(opt.substring(0,m));
       return s!=null && !opt.substring(m + 1).equals(s);
     }
  }
  //-----------------------------------------------
  public int isslider(String opt) {          // see if == in default val to show slider
    String defaults[] = defaults(gamename);
    String opte[] = new String[]{opt+'=', opt+'^'};
    int i;
    for(i=0;i<defaults.length;++i) {
        for(int j=0;j<opte.length;++j) {
          if (defaults[i].startsWith(opte[j])) {
              if(defaults[i].indexOf("==") >= 0)
                  return 0;
              else if (defaults[i].indexOf("=^") >= 0)
                  return 1;
          }
        }
    }
    return -1;
  }
  //-----------------------------------------------------------------------------------------------

  public static boolean option(String opt) {               // returns value of an option as boolean
     return optionstring(opt).equals("y");
  }
  public static short optionval(String opt) {              // value as short
     return (short)Integer.parseInt(optionstring(opt));
  }
  public static String optionstring(String opt) {          // value as string
    student stu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    String game = runningGame.currGameRunner.gamename;
    String s = stu.optionstring2(opt);
    if (s != null)   return s;
    return defaultstring(opt,game);
  }
  public static String defaultstring(String opt,String game) {          // value as from presets or default vals
    String presets[]  = sharkStartFrame.currPlayTopic.gameoptionlist;
    String gamec = game+',';
    int i,j,m;
    boolean got = false;
    String opte = opt+'=';
    if(presets != null) for(i=0;i<presets.length;++i) {
       if(presets[i].startsWith(gamec)) {
         presets = u.splitString(presets[i].substring(gamec.length()),',');
         for(j=0;j<presets.length;++j) {
            if(presets[j].startsWith(opte)) {
              if ( (m = presets[j].indexOf("==")) > 0) {  // value
                return presets[j].substring(m + 2);
              }
              if((m = presets[j].indexOf("=^")) > 0) {  // value
                return presets[j].substring(m + 2);
              }
              if ( (m = presets[j].indexOf('=')) >= 0) {  // value
                return presets[j].substring(m + 1);
              }
            }
         }
         break;
       }
    }
    return actualdefault(opt,game);
  }
  public static String actualdefault(String opt,String game) {
    String defaults[] = defaults(game);
    String opte = opt+'=';
    int i,m;
    for(i=0;i<defaults.length;++i) {
           if(defaults[i].startsWith(opte)) {
            if ( (m = defaults[i].indexOf("==")) > 0) {  // value
              return defaults[i].substring(m + 2);
            }
            if((m = defaults[i].indexOf("=^")) > 0) {  // value
              return defaults[i].substring(m + 2);
            }
            if ( (m = defaults[i].indexOf('=')) >= 0) {
              return defaults[i].substring(m + 1);
            }
          }
    }
    return "0";
  }
  static String[] defaults(String game) {
    String pa[],opt1;
    if ( (pa = sharkStartFrame.mainFrame.publicGameTree.getparms(game)) != null
      && pa.length > 0
      && (opt1 = sharkStartFrame.mainFrame.publicGameTree.getparm(pa, "options")) != null) {
        return  u.splitString(opt1,',');
    }
    return new String[0];
  }
}