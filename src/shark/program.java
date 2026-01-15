package shark;
import java.io.*;
import java.text.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class program
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     static byte MARGIN=2;
     String name,  teacher, oldname;
     String dbname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
     String insstep2 = u.gettext("stuprog_insstep","label2");
     String insstep = u.gettext("stuprog_insstep","label");
      String deltext[] = u.splitString(u.gettext("stuprog_delline","label"));
      String seltext[] = u.splitString(u.gettext("stuprog_selline","label"));
     String startitems[];
     static int currstep=-1;
     saveprogram sp = new saveprogram();
     GridBagLayout gridBagLayout0 = new GridBagLayout();
     GridBagLayout gridBagLayout1 = new GridBagLayout();
     GridBagLayout gridBagLayout2 = new GridBagLayout();
     GridBagLayout gridBagLayout3 = new GridBagLayout();
     GridBagLayout gridBagLayoutstu = new GridBagLayout();
     GridBagLayout gridBagLayout4 = new GridBagLayout();
     GridBagLayout gridBagLayout5 = new GridBagLayout();
     JPanel panel2 = new JPanel();
     JPanel panel3 = new JPanel();
     JPanel panel3x = new JPanel();
     JPanel panel3xx = new JPanel(new GridBagLayout());
     JPanel panel3s = new JPanel(new GridBagLayout());
     JPanel panel4 = new JPanel();
     JPanel panel4x = new JPanel();
     static JPanel panel5;
     arrowc arrows = new arrowc();
     static int dely,delfrom,delto, arrowpos[];
     static int lastindex = 1;
     GridBagConstraints grid1 = new GridBagConstraints();
     JPanel panel6 = new JPanel(new GridBagLayout());
     JPanel panel7 = new JPanel(new GridBagLayout());
     JPanel exitoptions = new JPanel(new GridBagLayout());
     JPanel options2 = new JPanel(new GridBagLayout());
     JPanel exitoptionsx = new JPanel(new GridBagLayout());
     jnode currstu2,root = new jnode();
     DefaultTreeModel model = new DefaultTreeModel(root);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     JTree studenttc = new JTree(model);
     dndTree_base studenttc = new dndTree_base(model);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     JPanel panel1a = new JPanel();
     JList stujlist=new JList();
     BorderLayout layout1a = new BorderLayout();
     JPanel stupanel = new JPanel();
     wordlist wordTree;
     JScrollPane scroller;
     JCheckBox orderck;
     JCheckBox hardsuper = u.CheckBox("hardsuper");
     JCheckBox phonics = u.CheckBox("stuprog_phonics");
     mlabel_base phlabel = new mlabel_base(u.gettext("stuprog_phonics","label2"));
     mbutton addtopic = u.mbutton("stuprog_addtopic");
     mbutton addgame = u.mbutton("stuprog_addgame");
     mlabel_base wordslabel1 = new mlabel_base(u.gettext("findword_wordlistwords","label"));
     String wordsl2 = u.gettext("findword_wordlistwords","label2");
     String wordsl3 = u.gettext("findword_wordlistwords","label3");
     mlabel_base wordslabel2 = new mlabel_base(wordsl2);
     mlabel_base stulabel = u.mlabel("simprog_stulist");
     mbutton addstu = u.mbutton("simprog_addstu");
     mlabel_base label1a = u.mlabel("simprog_stu");
     JLabel label1 = u.label("stuprog_prog");
     mlabel_base label2 = u.mlabel("stuprog_steps");
     mlabel_base label3 = u.mlabel("stuprog_topics");
     mlabel_base label4 = u.mlabel("stuprog_games");
     mlabel_base labelerr = u.mlabel("stuprog_errors");
     mlabel_base labeldo = u.mlabel("stuprog_compl");
     programsteps steps;
     topicTree topics = new topicTree();
     gamestoplay games = new gamestoplay();
     mbutton newsbutton = u.mbutton("stuprog_addstep");
     JButton inssbutton = u.Button("stuprog_insstep");
     JButton delsbutton = u.Button("stuprog_delstep");
     mbutton exitbutton = u.mbutton("stuprog_exit");
     mbutton cancelbutton = u.mbutton("stuprog_cancel");
     mbutton searchbutton = u.mbutton("stuprog_search");
     ButtonGroup bg = new ButtonGroup();
     String writename;   // rb 23/10/06
     dragger_base dragger;
     static JButton nogames = new JButton();//u.mbutton("stuprog_nogames");
//     static String nogamestext = nogames.text;
     static jnode nogamesnode2;
     static int nogamesstep;
     static jnode nogamestopic;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     /**
      * stores the y-coordinate of the top of the band where the arrows appear
      */
     static int arrowStepTop;
     /**
      * stores the y-coordinate of the bottom of the band where the arrows appear
      */
     static int arrowStepBottom;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   static String nogamesstr = u.gettext("program", "nogames");

    //----------------------------------------------------------
     ItemListener bglisten = new java.awt.event.ItemListener() {
       public void itemStateChanged(ItemEvent e) {
         if(savebutton.isSelected()) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
             if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
               stepchange=false;
               dispose();
             }
             dummy.setSelected(true);
             return;
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(!checkgames(sp,topics)) {
             u.okmess(u.gettext("stuprog_","valid"),
                      u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
            return;
           }
            saveit(false);
            dispose();
         }
          else if(addprog.isSelected()) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
              if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                stepchange=false;
                dispose();
              }
              dummy.setSelected(true);
              return;
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(!checkgames(sp,topics)) {
              u.okmess(u.gettext("stuprog_","valid"),
                       u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
              return;
            }
            saveit(false);
            assignit(false);
            dispose();
         }
         else if(cancel2.isSelected()) {
             stepchange=false;
             dispose();
         }
         else if(delprog.isSelected()) {
             stepchange=false;
             db.delete(dbname, oldname, db.PROGRAM);
             adm.delprog();
             dispose();
         }
         else if(renameprog.isSelected()) {
              renameprog();
              stepchange = true;
              dummy.setSelected(true);
         }
         else if(removeallprog.isSelected()) {
            adm.removeallprog(name);
            dummy.setSelected(true);
            removeallprog.setText(u.gettext("stulist_removeall","label2"));
         }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         // enables exiting screen via the ESC key
         requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
    };
     //-------------------------------------------------------------
     JRadioButton addprog = u.RadioButton("stulist_addprog",bglisten);
     JRadioButton savebutton = u.RadioButton("stuprog_save",bglisten);
     JRadioButton cancel2 = u.RadioButton("stuprog_cancel2",bglisten);
     JRadioButton delprog  = u.RadioButton("stuprog_delprog",bglisten);
     JRadioButton renameprog = u.RadioButton("stulist_renameprog",bglisten);
     JRadioButton removeallprog = u.RadioButton("stulist_removeall",bglisten);
     JRadioButton dummy = new JRadioButton("");
     static int mousepos=-1,selstep=-2,seltopic=-1,selgame=-1;
     static boolean selstepdet;
     class editnumberdo extends u.editnumber {
        public editnumberdo(String s,int a,int b,int c) {super(s,a,b,c);}
        public void vsignal(int i) {
          sp.it[currstep].mustcomplete = (short)i;
          steps.repaint();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // enables exiting screen via the ESC key
          focusForExit();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
     }
     class editnumbererr extends u.editnumber {
        public editnumbererr(String s,int a,int b,int c) {super(s,a,b,c);}
        public void vsignal(int i) {
          sp.it[currstep].maxerrors = (short)i;
          steps.repaint();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // enables exiting screen via the ESC key
          focusForExit();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
     }
     u.editnumber geterr  = new editnumbererr(u.gettext("stuprog_errors","label"),0,10,2);
     u.editnumber getdo  = new editnumberdo(u.gettext("stuprog_compl","label"),1,10,1);
     boolean stepchange=true;
     program thisprog = this;
     admin adm;
     static String alltopics = u.gettext("simprog_all","alltopics");
     static String allstudents = u.gettext("simprog_all","allstudents");
     static String allgames = u.gettext("simprog_all","allgames");
     static String recgames = u.gettext("simprog_all","recgames");

//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     program(String teachername, String students1[], String programname, admin aa ) {
     program(String teachername, String students1[], String groupfirst, String programname, admin aa ) {
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(aa);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        int i,j;
        arrowpos = new int[0];
        jnode node;
        adm = aa;
        oldname = name = programname;
        writename = name + "[" + teachername + "]";    // rb 23/10/06
        teacher = teachername;
        root.setUserObject(allstudents);
        topics.onlyOneDatabase = "*";
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),false,db.TOPICTREE,true,alltopics);
        topics.setRootVisible(false);
        studenttc.putClientProperty("JTree.lineStyle","Angled");
        studenttc.setCellRenderer(new treepainter());
//startPR2006-11-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        studenttc.setRowHeight(studenttc.getFontMetrics(studenttc.getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        root.dontcollapse=true;
        studenttc.addTreeWillExpandListener(new TreeWillExpandListener() {
           public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException {
              if(((jnode)ev.getPath().getLastPathComponent()).dontcollapse)
               throw new ExpandVetoException(ev);
           }
           public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException{}
        });
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        student.buildstutree(studenttc, root, aa.teacher.students, false);
        student.buildstutree(studenttc, root, aa.teacher.students, false, false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        root.setIcon(jnode.TEACHER);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        sp = (saveprogram)db.find(teacher,name,db.PROGRAM);
        try{sp = (saveprogram)db.find(teacher,name,db.PROGRAM);}
        catch(ClassCastException e){sp=null;}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if (sp != null) {
           Vector invalidTopicNames;
          privateListRecord plr = new privateListRecord();
          if ((invalidTopicNames = plr.getInvalidTopics(sp)) != null) {
//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            sp = plr.updateSaveProgram(students1[0], programname, sp, invalidTopicNames);
            sp = plr.updateSaveProgram(groupfirst!=null?groupfirst:students1[0], programname, sp, invalidTopicNames);
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(sp==null && students1 != null && students1.length > 0) {
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          student.getStudent(students1[0]);    // rb 2/2/08 - ensure up to date
//          student stud = student.getStudent(students1[0]);   //2222
//          if(stud==null || !stud.programOverride){    //2222
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          sp = (program.saveprogram) db.find(students1[0], writename, db.PROGRAM);
//          if(sp==null) sp = (program.saveprogram) db.find(students1[0], name, db.PROGRAM);
//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            try{sp = (program.saveprogram) db.find(students1[0], writename, db.PROGRAM);}
            try{sp = (program.saveprogram) db.find(groupfirst!=null?groupfirst:students1[0], writename, db.PROGRAM);}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          catch(ClassCastException e){sp = null;}
          if(sp==null){
//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              try{sp = (program.saveprogram) db.find(students1[0], name, db.PROGRAM);}
              try{sp = (program.saveprogram) db.find(groupfirst!=null?groupfirst:students1[0], name, db.PROGRAM);}
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            catch(ClassCastException e){sp = null;}
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          student s1;
//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if((s1=student.getStudent(students1[0]))!=null)
            if((s1=student.getStudent(groupfirst!=null?groupfirst:students1[0]))!=null)
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(u.findString(student.getStudent(students1[0]).programOverride, writename)>=0) sp=null;  //3333
            if(u.findString(s1.programOverride, writename)>=0) sp=null;  //3333
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        if(sp==null) {
          sp = new  saveprogram();
          sp.teacher = teachername;
          sp.students = students1;
        }
        else {
          for(i=0;i<sp.it.length;++i) {                                            //  rb 16/3/08  mmmm  start
            if(sp.it[i].mixed==null) sp.it[i].mixed = new boolean[sp.it[i].topics.length];
          }                                                                        //  rb 16/3/08  mmmm  end
//startPR2010-03-18^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sp.students = students1;
//          for(i=0;i<sp.students.length;++i) {
//             jnode nn = root.find(sp.students[i]);
//             if(nn == null) {
//                if(!sp.v4 && !sp.students[i].startsWith(topicTree.ISPATH)
//                   && (nn = root.oldfind(sp.students[i])) != null) {
//                   sp.students[i] = admin.getadminpath(nn);
//                }
//                else {
//                   sp.students = u.removeString(sp.students, i);
//                    --i;
//                }
//             }
//          }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        sp.v4 = true;
        sp.currstep = 0;
        sp.completed = 0;
        hardsuper.setSelected(sp.hardsuper);
//        u.forcenotes(hardsuper,true,false);
//        u.forcenotes(phonics,true,false);
        steps = new programsteps(sp);
        ((itempainter)steps.getCellRenderer()).topics = topics;
        if(sp.students.length == 0) sp.students = new String[]{root.get()};
        if( sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get()))
             stujlist.setListData(new String[] {allstudents});
        else stujlist.setListData(sp.students);
        stujlist.setCellRenderer(new stujpainter());
//         stujlist.setCellRenderer(new admin.stupainter());
        GridBagConstraints grid2 = new GridBagConstraints();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(sharkStartFrame.mainFrame.getBounds());
//startPR2009-08-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        this.addComponentListener(new ComponentAdapter() {
          public void componentMoved(ComponentEvent e) {
            removeComponentListener(this);
            setBounds(sharkStartFrame.mainFrame.getBounds());
            validate();
            addComponentListener(this);
          }
        });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        this.setEnabled(true);
        this.setVisible(true);
        this.setTitle(u.gettext("stuprog_","title",name));
        requestFocus();
        addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE) {
                if(stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                    if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                      stepchange=false;
                      dispose();
                    }
                    dummy.setSelected(true);
                    return;
                  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  setuserec();
                  if(!checkgames(sp,topics)) {
                    u.okmess(u.gettext("stuprog_","valid"),
                             u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                    return;
                  }
                   if(saveit(true))
                      assignit(true);
                 }
                 dispose();
              }
          }
        });
        this.getContentPane().setLayout(gridBagLayout0);
        panel1a.setLayout(layout1a);
        panel2.setLayout(gridBagLayout2);
        panel3.setLayout(new BorderLayout());
        panel4.setLayout(new GridBagLayout());
        panel3x.setLayout(gridBagLayout3);
        panel4x.setLayout(gridBagLayout4);
        panel5 = new JPanel(gridBagLayout5);
        studenttc.expandRow(0);
        currstu2 = root;
        studenttc.setSelectionRow(0);
        if( sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get()))
             stujlist.setListData(new String[] {allstudents});
        else stujlist.setListData(sp.students);
        stupanel.setLayout(gridBagLayoutstu);
        stupanel.setBorder(BorderFactory.createLineBorder(Color.red,2));
        stulabel.setBackground(Color.red);
        stulabel.setForeground(Color.white);
        wordTree = new wordlist();
        wordTree.font=null;
        scroller = u.uScrollPane(wordTree);
        scroller.setHorizontalScrollBarPolicy(scroller.HORIZONTAL_SCROLLBAR_NEVER);

        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.weightx=grid2.weighty = 1;
        grid2.fill = GridBagConstraints.BOTH;
        stupanel.add(stulabel,grid2);
        grid2.weighty=20;
        stupanel.add(u.uScrollPane(studenttc),grid2);
        grid2.weighty=0;
        grid2.fill = GridBagConstraints.NONE;
//        stupanel.add(addstu,grid2);
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(shark.macOS){
          addstu.setForeground(Color.red);
        }
        else{
          addstu.setBackground(Color.red);
          addstu.setForeground(Color.white);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        bg.add(addprog);
        bg.add(savebutton);
        bg.add(cancel2);
        bg.add(removeallprog);
        bg.add(delprog);
        bg.add(renameprog);
        bg.add(removeallprog);
        bg.add(dummy);

        dummy.setSelected(true);

        games.setup(sharkStartFrame.publicGameLib,
                   true,true,allgames,0);
        for(i=0;i<games.root.getChildCount();++i) {
            (node=(jnode)games.root.getChildAt(i)).setIcon(jnode.DATASET);
            node.dontcollapse=true;
            for(j=0;j<node.getChildCount();++j) {
              ((jnode)node.getChildAt(j)).dontcollapse = true;
            }
        }
        steps.setToolTipText(label2.getToolTipText());
        topics.setToolTipText(label3.getToolTipText());
        games.setToolTipText(label4.getToolTipText());
        geterr.setToolTipText(u.gettext("stuprog_errors","tooltip"));
        getdo.setToolTipText(u.gettext("stuprog_compl","tooltip"));
        geterr.setBackground(Color.yellow);
        getdo.setBackground(Color.yellow);
        panel5.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/12,sharkStartFrame.screenSize.height));
        panel5.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/12,sharkStartFrame.screenSize.height));
        panel2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*5/12,sharkStartFrame.screenSize.height));
        panel2.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*5/12,sharkStartFrame.screenSize.height));
        panel3.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height));
        panel3.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height));
        panel4.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/6,sharkStartFrame.screenSize.height));
        panel4.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/6,sharkStartFrame.screenSize.height));

        grid1.weightx = grid1.weighty = 1;
        grid1.gridx = -1;
        grid1.gridy = 0;
        grid1.fill = GridBagConstraints.BOTH;
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(shark.macOS){
          panel3xx.add(searchbutton,grid1);
//          panel3xx.add(addtopic,grid1);
        }
        else{
//          panel3xx.add(addtopic,grid1);
          panel3xx.add(searchbutton,grid1);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        searchbutton.setBackground(sharkStartFrame.topictreecolor);
        searchbutton.setForeground(Color.black);
        grid2.weightx = grid2.weighty = 1;
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weightx = 0;
        getContentPane().add(panel5,grid1);
        grid2.weightx = 1;
        panel5.add(arrows,grid2);
        getContentPane().add(panel2,grid1);
        getContentPane().add(panel3,grid1);
        getContentPane().add(panel4,grid1);
        grid2.weighty = 0;
        grid2.fill = GridBagConstraints.BOTH;
        panel2.add(label2,grid2);
        label2.setBackground(sharkStartFrame.topictreecolor);
        label2.setForeground(Color.black);
        panel3x.add(label3,grid2);
        label3.setBackground(sharkStartFrame.topictreecolor);
        label3.setForeground(Color.black);
        panel3x.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor,2));
        grid2.fill = GridBagConstraints.NONE;
        panel3s.add(getdo,grid2);
        panel3s.add(geterr,grid2);
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 1;
        label4.setBackground(sharkStartFrame.gamescolor);
        label4.setForeground(Color.white);
        panel4x.setBorder(BorderFactory.createLineBorder(sharkStartFrame.gamescolor,2));
        grid2.weighty = 0;
        panel4x.add(label4,grid2);
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty  = 15;
        JScrollPane sp1 = u.uScrollPane(steps);
        sp1.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor,2));
        panel2.add(sp1,grid2);
        grid2.weighty = 0;
        panel2.add(program.nogames,grid2);
        grid2.weighty = 1;
        program.nogames.setBackground(Color.red);
        program.nogames.setForeground(Color.white);
        program.nogames.setVisible(false);
        ActionListener al[] = program.nogames.getActionListeners();
        for(i=0;i<al.length;++i) program.nogames.removeActionListener(al[i]);
        program.nogames.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              if(getContentPane().isAncestorOf(panel4))getContentPane().remove(panel4);
              steps.setSelectedIndex(currstep = nogamesstep);
              showgames(nogamestopic);
              games.setSelectionPath(new TreePath(((jnode)games.root.getFirstLeaf()).getPath()));
          }
        });
        grid2.weighty = 0;
        panel2.add(panel6,grid2);
        grid2.weighty  = 4;
        panel2.add(panel1a,grid2);
        grid2.weighty = 0;
        panel2.add(panel7,grid2);
        grid2.weighty = 0;
        panel1a.add(label1a,BorderLayout.NORTH);
        panel1a.add(u.uScrollPane(stujlist),BorderLayout.CENTER);
        label1a.setBackground(Color.red);
        label1a.setForeground(Color.white);
        panel1a.setBorder(BorderFactory.createLineBorder(Color.red,2));
        grid2.weighty = 14;
        panel3x.add(u.uScrollPane(topics),grid2);
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.weighty = 0;
        addtopic.setBackground(sharkStartFrame.topictreecolor);
        hardsuper.setBackground(Color.orange.brighter());
        phonics.setBackground(Color.orange);
        phlabel.setBackground(Color.orange);
        addtopic.setForeground(Color.black);
        panel3x.add(phlabel,grid2);
        panel3x.add(phonics,grid2);
        panel3x.add(hardsuper,grid2);
        panel3x.add(panel3xx,grid2);
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 15;
        panel4x.add(u.uScrollPane(games),grid2);
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.weighty = 0;
        grid2.fill = GridBagConstraints.NONE;
        grid2.weighty = 1;
//        panel4x.add(addgame,grid2);
        games.setSelectionPath(new TreePath(games.root.getFirstLeaf().getPath()));
        steps.setToolTipText(label2.getToolTipText());
        topics.setToolTipText(label3.getToolTipText());
        games.setToolTipText(label4.getToolTipText());

        grid2.weighty = 1;
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.gridx=-1;
        grid2.gridy=0;
        newsbutton.setBackground(sharkStartFrame.topictreecolor);
        inssbutton.setBackground(sharkStartFrame.topictreecolor);
        delsbutton.setBackground(sharkStartFrame.topictreecolor);
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
        if (shark.macOS) {
          exitbutton.setForeground(Color.red);
          cancelbutton.setForeground(Color.red);
          addgame.setForeground(sharkStartFrame.gamescolor);
          panel3xx.setBackground(sharkStartFrame.topictreecolor);
        }
        // if running on Windows
        else {
          exitbutton.setBackground(Color.red);
          exitbutton.setForeground(Color.white);
          cancelbutton.setBackground(Color.red);
          cancelbutton.setForeground(Color.white);
          addgame.setForeground(Color.yellow);
          addgame.setBackground(sharkStartFrame.gamescolor);
          searchbutton.setBackground(sharkStartFrame.topictreecolor);

          addtopic.setBackground(sharkStartFrame.topictreecolor);

        }
        searchbutton.setForeground(Color.black);
        addtopic.setForeground(Color.black);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        cancelbutton.setOpaque(true);
        exitbutton.setOpaque(true);
        delsbutton.setOpaque(true);
        newsbutton.setOpaque(true);
        inssbutton.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(shark.macOS){
          panel6.add(delsbutton,grid2);
          panel6.add(inssbutton,grid2);
          panel6.add(newsbutton,grid2);
          panel7.add(cancelbutton,grid2);
          panel7.add(exitbutton,grid2);
        }
        else{
          panel6.add(newsbutton,grid2);
          panel6.add(inssbutton,grid2);
          panel6.add(delsbutton,grid2);
          panel7.add(exitbutton,grid2);
          panel7.add(cancelbutton,grid2);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        grid2.gridx=0;
        grid2.gridy=-1;
        grid2.fill = GridBagConstraints.BOTH;

        grid2.fill = GridBagConstraints.HORIZONTAL;
        exitoptions.add(addprog,grid2);
        exitoptions.add(savebutton,grid2);
        exitoptions.add(cancel2,grid2);
        exitoptions.add(delprog,grid2);
        options2.add(renameprog,grid2);
        options2.add(removeallprog,grid2);
        exitoptions.setBorder(BorderFactory.createTitledBorder(
                  BorderFactory.createLineBorder(Color.red,2),
                  u.gettext("stuprog_exitoptions","label"),
                  TitledBorder.CENTER, TitledBorder.BELOW_TOP));
         ((TitledBorder)exitoptions.getBorder()).setTitleColor(Color.red);
        exitoptions.setBackground(new Color(255,192,192));
        options2.setBorder(BorderFactory.createTitledBorder(
                  BorderFactory.createLineBorder(Color.green,2),
                  u.gettext("stuprog_otheroptions","label"),
                  TitledBorder.CENTER, TitledBorder.BELOW_TOP));
         ((TitledBorder)options2.getBorder()).setTitleColor(Color.red);
        options2.setBackground(new Color(192,255,192));
        grid2.fill = GridBagConstraints.BOTH;
        exitoptionsx.add(exitoptions,grid2);
        exitoptionsx.add(options2,grid2);
        grid2.fill = GridBagConstraints.NONE;

        hardsuper.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              sp.hardsuper = hardsuper.isSelected();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
        phonics.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              if(currstep>=0) {
                sp.it[currstep].phonics = phonics.isSelected();
                wordlist.usephonics = sp.it[currstep].phonics || wordTree != null && wordTree.forcephonics;
              }
              stepchange = true;
             if(wordTree != null) {
                wordTree.font = null;
              }
              repaint();
              requestFocus();
              setuserec();
              checkgames(sp,topics);
          }
        });
        addtopic.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              addtopic();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
        addgame.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              addgame();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
        newsbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              newstep(steps.getModel().getSize());
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
        });
        addstu.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              addstu();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // enables exiting screen via the ESC key
              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
        steps.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            mousepos = e.getY();
            if(mouseposition()) {
               if(seltopic >= 0)
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 showtopics();
                 showtopics(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               else if(selgame >= 0) showgames(null);
               else if(selstepdet) showstepdet();
               steps.repaint();
               panel5.repaint();
            }
         }
         public void mouseReleased(MouseEvent e) {
              if(dragger.droppedon != null && dragger.droppedon == steps) {
                for(short j=0;j<sp.it.length;++j) {
                  Rectangle r  = steps.getCellBounds(j,j);
                  if(r.contains(e.getX(),e.getY())) {
                     selstep = currstep = j;
                     steps.setSelectedIndex(j);
                     break;
                  }
                }
                if(dragger.draggedfrom == topics) {
                   addtopic();
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                   showtopics();
                   showtopics(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   steps.repaint();
                   panel5.repaint();
                }
                else if(dragger.draggedfrom == games) {
                   addgame();
                   showgames(null);
                   steps.repaint();
                   panel5.repaint();
                }
                dragger.droppedon = dragger.draggedfrom = null;
              }
        }
       });
       steps.addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
           if(e.getKeyCode() == e.VK_DELETE) {
               deleteline();
           }
          }
       });
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       topics.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
           jnode snode = topics.getSelectedNode();
           if(snode!=null && snode.get().trim().equals("")){
             topics.setSelectionPath(e.getOldLeadSelectionPath());
             return;
           }
         }
       });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       topics.addMouseListener(new java.awt.event.MouseAdapter() {
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         public void mouseReleased(MouseEvent e) {
         public void mousePressed(MouseEvent e) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            jnode snode = (jnode)topics.getLastSelectedPathComponent();
            addtopic.setEnabled(snode != null
                   && !(snode.getLevel() == 2
                          && ((jnode)snode.getParent()).get().equals(topicTree.adminlists))
                   && !(snode.getLevel() == 1
                            && snode.get().equals(topicTree.adminlists)));
            panel4.removeAll();
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(snode == null || !snode.isLeaf())
            if(snode == null || !snode.isLeaf() || snode.get().trim().equals(""))
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                          { panel4.repaint();return;}
            topic sel = topics.getCurrentTopic();
            if(sel==null ) {panel4.validate();return;}
            wordTree.reset();
            wordTree.setup(sel,null,true);
            wordlist.usephonics = wordTree.forcephonics || phonics.isSelected();
            wordTree.font=null;
            GridBagConstraints grid2 = new GridBagConstraints();
            grid2.gridy = -1;grid2.gridx = 0 ;
            grid2.fill = GridBagConstraints.BOTH;
            grid2.weighty = 1;
            grid2.weightx = 1;
            wordslabel2.setText(wordTree.wholeextended?wordsl3:wordsl2);
            wordslabel1.setBackground(Color.orange);
            wordslabel1.setForeground(Color.black);
            wordslabel2.setBackground(Color.orange);
            wordslabel2.setForeground(Color.black);
            panel4.add(wordTree.canextend?wordslabel2:wordslabel1,grid2);
            grid2.weighty = 30;
             panel4.add(scroller,grid2);
            grid2.weighty =  0; //wordTree.orderCB.getLabels().length*2;
            if(wordTree.canextend) {
               orderck = u.CheckBox("wl_extendedw",
                   new ItemListener() {
                       public void itemStateChanged(ItemEvent e) {
                          if(wordTree.wholeextended = orderck.isSelected()) {
                              wordslabel2.setText(wordsl3);
                              topic sel = topics.getCurrentTopic();
                              if(sel==null ) return;
                              DefaultListModel mm = (DefaultListModel)wordTree.getModel();
                              mm.removeAllElements();
                              word words1[] = sel.getAllWords(true);
                              for(short i=0;i<words1.length;++i) {
                                 mm.addElement(words1[i]);
                              }
                          }
                          else {
                             wordslabel2.setText(wordsl2);
                             wordTree.buildTree(true);
                          }
                          wordTree.setfont();
                        }
                   }
                );
                orderck.setBackground(Color.orange);
                orderck.setForeground(Color.black);
               panel4.add(orderck,grid2);
            }
            panel4.validate();
            wordTree.setfont();
        }
       });
       stujlist.addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
           if(e.getKeyCode() == e.VK_DELETE) {
               delstu();
             }
         }
        });
        stujlist.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            dely = 0;
            seltopic=selgame=-1;
            selstep = -1;
            showstupanel();
            panel5.repaint();
          }
          public void mouseReleased(MouseEvent e) {
              if(dragger.droppedon != null && dragger.droppedon == stujlist
                 && dragger.draggedfrom == studenttc) {
                dragger.droppedon = dragger.draggedfrom = null;
                addstu();
              }
            }

        });
         inssbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             newstep((currstep>=0)?currstep:0);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
        });
        delsbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              if(currstep>=0) {
                 programitem news[] = new programitem[sp.it.length-1];
                 System.arraycopy(sp.it,0,news,0,currstep);
                 System.arraycopy(sp.it,currstep+1,news,currstep,sp.it.length-currstep-1);
                 sp.it = news;
                 steps.setListData(sp.it);
                 steps.setSelectedIndex(currstep);
                 if(currstep>=sp.it.length) currstep = sp.it.length-1;
                 if(currstep >=0)   setupstep();
                 dely=0;
                 panel5.repaint();
               }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               // if only step is deleted replace it with new one
               if (sp.it.length == 0){
                 newstep(0);
                 panel5.repaint();
               }
               // enables exiting screen via the ESC key
               requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowClosing(WindowEvent e) {
              if(stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                  if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                    stepchange=false;
                    dispose();
                  }
                  dummy.setSelected(true);
                  return;
                }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(!checkgames(sp,topics)) {
                  u.okmess(u.gettext("stuprog_","valid"),
                           u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                  return;
                }
                 if(saveit(true))
                    assignit(true);
              }
              dispose();
           }
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           // enables exiting screen via the ESC key
//           public void windowActivated(WindowEvent e) {
//             requestFocus();
//           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        });
        exitbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             showexit();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
        cancelbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               stepchange=false;
               dispose();
          }
       });
        searchbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             new findword(thisprog);
             new findword(thisprog, thisprog);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
       steps.addListSelectionListener(new ListSelectionListener() {
           public void valueChanged(ListSelectionEvent e) {
            int sel = steps.getLeadSelectionIndex();
            if(sel>=0) {
               currstep = sel;
               if(currstep != selstep) {
                 setupstep();
               }
            }
         }
       });
       arrows.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
           int y = e.getY();
           if(dely>0) {
              if (y >= delfrom && y <= delto) deleteline();
           }
           else  {
              if (y >= delfrom && y <= delto) delstu();
           }
         }
       });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // enables exiting screen via the ESC key
       stujlist.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
               return;
               }
                if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       studenttc.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                u.okmess(u.gettext("stuprog_","valid"),
                         u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       exitoptions.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                 return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       wordTree.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                 return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       steps.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                 return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       topics.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       studenttc.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       games.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
               return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
       exitoptionsx.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE) {
             if (stepchange) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.it.length == 0 || notopics()) {   // rb 23/11/06
                 if(u.yesnomess(u.gettext("stuprog_notopics", "heading"), u.gettext("stuprog_notopics", "message"),thisprog)){
                   stepchange=false;
                   dispose();
                 }
                 dummy.setSelected(true);
                 return;
               }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(!checkgames(sp,topics)) {
                 u.okmess(u.gettext("stuprog_","valid"),
                          u.gettext("stuprog_","cantplay", nogamesname()), sharkStartFrame.mainFrame);//  rb 16/3/08  mmmm
                return;
               }
               if (saveit(true)) {
                 assignit(true);
               }
             }
             dispose();
           }
         }
       });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

       if(sp.it.length==0) newstep(0);
       steps.setSelectedIndex(currstep=0);
       setupstep();
       dely=0;
       panel5.repaint();
       dragger = new dragger_base(this,new JComponent[]{topics,games,studenttc},new JComponent[]{steps,stujlist});

     }
     static String nogamesname() {                                          //  rb 16/3/08  mmmm  start
       String nodename1 = program.nogamestopic.get();
       String nodename2 = program.nogamesnode2.get();
       if(nodename1.equals(nodename2)) return nodename2;
       else return nodename2 + "(" + nodename1 + ")";
     }                                                              //  rb 16/3/08  mmmm  end
     //-------------------------------------------------------------------
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     // enables exiting screen via the ESC key
     void focusForExit(){
       requestFocus();
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     //-------------------------------------------------------------------
     void delstu() {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               int curr = stujlist.getSelectedIndex();
//               if(curr < 0) return;
//               sp.students = u.removeString(sp.students,curr);
       int curr[] = stujlist.getSelectedIndices();
       if(curr.length <= 0) return;
       for(int i = curr.length-1; i >= 0; i--){
         sp.students = u.removeString(sp.students,curr[i]);
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.students.length == 0) sp.students = new String[]{allstudents};
               if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get()))
                 stujlist.setListData(new String[] {allstudents});
               else stujlist.setListData(sp.students);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               curr = Math.min(curr,sp.students.length-1);
               curr[0] = Math.min(curr[0],sp.students.length-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(sp.students.length > 0 && !sp.students[0].equals(allstudents)) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              stujlist.setSelectedIndex(curr);
                 stujlist.setSelectedIndex(curr[0]);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
               else {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  curr=-1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  selgame = seltopic = selstep = -1;
                  dely = 0;
               }
               panel5.repaint();
     }
     //-----------------------------------------------------------------
     void deleteline() {
               if(selstep == currstep && seltopic >= 0) {
                  int len = sp.it[currstep].topics.length-1;
                  sp.it[currstep].topics = u.removeString(sp.it[currstep].topics,seltopic);
                  saveTree1.saveTree2 treesx[] = new saveTree1.saveTree2[len];
                  System.arraycopy(sp.it[currstep].trees,0,treesx,0,seltopic);
                  if(seltopic<len) System.arraycopy(sp.it[currstep].trees,seltopic+1,treesx,seltopic,len-seltopic);
                  sp.it[currstep].trees = treesx;
                  boolean mixedx[] = new boolean[len];                          //  rb 16/3/08  mmmm  start
                  System.arraycopy(sp.it[currstep].mixed,0,mixedx,0,seltopic);
                  if(seltopic<len) System.arraycopy(sp.it[currstep].mixed,seltopic+1,mixedx,seltopic,len-seltopic);
                  sp.it[currstep].mixed = mixedx;                               //  rb 16/3/08  mmmm  end
                  if(sp.it[currstep].topics.length > 0) {
                    seltopic = Math.min(seltopic,sp.it[currstep].topics.length-1);
                  }
                  else {seltopic = -1;dely = 0;}
                  steps.setListData(sp.it);
                  steps.setSelectedIndex(currstep);
               }
               else if(selstep == currstep && selgame >= 0) {
                  sp.it[currstep].games = u.removeString(sp.it[currstep].games,selgame);
                  if(sp.it[currstep].games.length > 0) {
                    selgame = Math.min(selgame,sp.it[currstep].games.length-1);
                  }
                  else {selgame = -1;dely=0;}
                  steps.setListData(sp.it);
                  steps.setSelectedIndex(currstep);
               }
               setuserec();
               checkgames(sp,topics);
     }
     //----------------------------------------------------------------
         // expand student list
     String[] expand(String s[]) {
        return expand(s,new String[0],root,s.length==0 || s[0].equals(root.get()));
     }
     String[] expand(String s[],String news[],jnode node,boolean all) {
        jnode c[] = node.getChildren();
        for(short i=0; i<c.length;++i) {
           String name = c[i].get();
           if(c[i].isLeaf()) {
              if(c[i].type==jnode.STUDENT && (all || u.findString(s,name)>=0)) {
                news =u.addStringSort(news,name);
              }
           }
           else {
             news = expand(s, news, c[i], all || u.findString(s, admin.getadminpath(c[i])) >= 0);
           }
        }
        return news;
     }
     //----------------------------------------------------------------
     void renameprog() {
        String newname = name;
        JOptionPane getpw = new JOptionPane(
               u.gettext("stuprog_","enternew")+ ' ' + name,
               JOptionPane.PLAIN_MESSAGE,
               JOptionPane.OK_CANCEL_OPTION);
        getpw.setWantsInput(true);
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        JDialog dialog = getpw.createDialog(null,u.gettext("stuprog_","ren"));
        JDialog dialog = getpw.createDialog(thisprog,u.gettext("stuprog_","ren"));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        while(true) {
            dialog.setVisible(true);
            Object result = getpw.getValue();
            if(result == null
                || result instanceof Integer
                 &&((Integer)result).intValue() != JOptionPane.OK_OPTION) return;
            newname = (String)getpw.getInputValue();
            if(newname.length() == 0) continue;
            if(db.query(teacher,newname,db.PROGRAM) >=0) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                   JOptionPane.showMessageDialog(null,u.gettext("stuprog_","already")+newname,u.gettext("stuprog_","renaming")+name,JOptionPane.WARNING_MESSAGE);
              JOptionPane.showMessageDialog(thisprog,u.gettext("stuprog_","already")+newname,u.gettext("stuprog_","renaming")+name,JOptionPane.WARNING_MESSAGE);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                   continue;
            }
            if(newname.length() > 0 && !newname.equals(name)) {
              name = newname;
              setTitle(u.gettext("stuprog_","title",name));
            }
            return;
        }
     }
     //-------------------------------------------------------------------
     void addtopic() {
       int i;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = topics.getSelectionPaths();
          TreePath sel[] = u.sortPathSelections(topics, topics.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sel == null || sel.length==0) return;
       for(i=0;i<sel.length;++i) {
         jnode node = (jnode)sel[i].getLastPathComponent();
         if(node.dummy) continue;             // rb 26-11-07
         String name = node.get();
         programitem spit = sp.it[currstep];
         if(name==null) continue;
//         if(!node.isLeaf() && node.inphonics()) {      //  rb 16/3/08  mmmm start
//           u.okmess("simprog_nophonicshead");
//           continue;
//         }                                          //  rb 16/3/08  mmmm  end
         String tp = topics.savePath(node);
         if(node == topics.root) {
            spit.topics = new String[0];
            spit.trees = new saveTree1.saveTree2[0];
            spit.mixed = new boolean[0];               //  rb 16/3/08  mmmm
            steps.setListData(sp.it);
            steps.setSelectedIndex(currstep);
            return;
         }
         if(u.findString(spit.topics,tp) < 0) {
           boolean ismixed = false;                                    //  rb 16/3/08  mmmm start
           if(!node.isLeaf() && !node.inphonics()) {
             String choices[] = u.splitString(u.gettext("simprog_","qmixedch"));
             int ret = JOptionPane.showOptionDialog(sharkStartFrame.mainFrame,
                                                   u.splitString(u.gettext("simprog_","qmixed",node.get())),null,
                                                   JOptionPane.NO_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE, null,
                                                   choices, choices[0]);
             if(ret < 0 || ret == 2) continue;
             else if(ret == 1) ismixed = true;
           }                                                             //  rb 16/3/08  mmmm  end
           spit.topics = u.addString(spit.topics, tp);
           if(spit.topics.length == 1 && node.isLeaf() && node.inphonics()) {
             phonics.setSelected(true);
             spit.phonics  = true;
             wordlist.usephonics = true;
           }
           saveTree1.saveTree2 nt[] = new saveTree1.saveTree2[spit.trees.length +
               1];
           System.arraycopy(spit.trees, 0, nt, 0, spit.trees.length);
           spit.trees = nt;
           spit.trees[spit.trees.length - 1]
               = (new saveTree1(topics, node)).curr;
           boolean mx[] = new boolean[spit.mixed.length + 1];           //  rb 16/3/08  mmmm  start
           System.arraycopy(spit.mixed, 0, mx, 0, spit.mixed.length);
           mx[mx.length-1] = ismixed;
           spit.mixed = mx;                                             //  rb 16/3/08  mmmm  end
         }
       }
       steps.setListData(sp.it);
       steps.setSelectedIndex(currstep);
       setuserec();
       checkgames(sp,topics);
       panel5.repaint();
     }
     //-------------------------------------------------------------------
     void addgame() {
       int i;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = games.getSelectionPaths();
       TreePath sel[] = u.sortPathSelections(games, games.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sel == null || sel.length==0) return;
       for(i=0;i<sel.length;++i) {
         jnode node = (jnode)sel[i].getLastPathComponent();
         String name = node.get();
         if(u.findString(sharkStartFrame.gametreeheadings,name)>=0) {
           u.okmess("simprog_notblue");
           continue;
         }
//         String tp = games.savePath(node);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         i= tp.indexOf(topicTree.CSEPARATOR);
//         if(i>=0) tp = tp.substring(i+1);
//         int p = tp.indexOf(topicTree.CSEPARATOR);
//         if(p>=0) tp = tp.substring(p+1);          // strip non-phonics/phonics part
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         programitem spit = sp.it[currstep];
         if(name==null) continue;
         if(node==games.root) {
             spit.games = new String[0];
             steps.setListData(sp.it);
             steps.setSelectedIndex(currstep);
             return;
         }
         if(u.findString(spit.games,name) < 0) {
           spit.games = u.addString(spit.games, name);
         }
       }
       steps.setListData(sp.it);
       steps.setSelectedIndex(currstep);
       setuserec();
       checkgames(sp,topics);
       panel5.repaint();
     }
      //----------------------------------------------------------------
     void addstu() {
       int i;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = studenttc.getSelectionPaths();
       TreePath sel[] = u.sortPathSelections(studenttc, studenttc.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sel == null || sel.length==0) return;
       for(i=0;i<sel.length;++i) {
            jnode node = (jnode)sel[i].getLastPathComponent();
            String name2 = node.get();
            if(node==root) {
               if(sp.students.length > 0 && (sp.students.length >1 || !sp.students[0].equals(allstudents))) {  //  rb 16/3/08  mmmm
                 if (!u.yesnomess("simprog_allstu",sharkStartFrame.mainFrame)) return;                //  rb 16/3/08  mmmm
               }                                                                                       //  rb 16/3/08  mmmm
               sp.students = new String[]{allstudents};
               stujlist.setListData(new String[] {allstudents});
               stujlist.setSelectedIndex(0);
                return;
            }
            if(node.type != jnode.STUDENT) name2 = admin.getadminpath(node);
            if(u.findString(sp.students,name2) < 0) {
              if (sp.students.length == 1 && sp.students[0].equalsIgnoreCase(root.get()))
                sp.students = new String[] {
                    name2};
              else
                sp.students = u.addString(sp.students, name2);
            }
       }
       if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get()))
            stujlist.setListData(new String[] {allstudents});
       else stujlist.setListData(sp.students);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       stujlist.setSelectedIndex(sp.students.length-1);
       stujlist.ensureIndexIsVisible(sp.students.length-1);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
     //-------------------------------------------------------------------
     void showstepdet() {
        if(!getContentPane().isAncestorOf(panel4)) {
              getContentPane().add(panel4,grid1);
              getContentPane().validate();
        }
        panel3.removeAll();
        geterr.setvalue(sp.it[currstep].maxerrors);
        getdo.setvalue(sp.it[currstep].mustcomplete);
        panel3.add(panel3s,BorderLayout.CENTER);
        panel4.removeAll();
        panel3.validate();
        panel4.validate();
        panel3.repaint();
        panel4.repaint();
      }
     //-------------------------------------------------------------------
     void showexit() {
        if(getContentPane().isAncestorOf(panel4)) {
            getContentPane().remove(panel4);
            getContentPane().validate();
        }
        panel3.removeAll();
        panel3.add(exitoptionsx,BorderLayout.CENTER);
        panel4.removeAll();
        panel3.validate();
        panel4.validate();
        panel3.repaint();
        panel4.repaint();
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dummy.setSelected(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      }
     //-------------------------------------------------------------------
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   void showtopics() {
     void showtopics(boolean selecttopic) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jnode node;
       if(!getContentPane().isAncestorOf(panel4)) {
         getContentPane().add(panel4,grid1);
         getContentPane().validate();
       }
       panel3.removeAll();
       panel3.add(panel3x,BorderLayout.CENTER);
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(selecttopic){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         for(short i=0;i<sp.it[currstep].topics.length;++i) {
           node = topics.expandPath(sp.it[currstep].topics[i]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      if(i==seltopic && node != null)  {
           if(topics.getSelectionCount()<2 && i==seltopic && node != null)  {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             topics.setSelectionPath(null);
             topics.setSelectionPath(new TreePath(node.getPath()));
             topics.scrollPathToVisible(new TreePath(node.getPath()));
           }
         }
//startPR2007-11-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       dragger.setActiveDestinations(new JComponent[]{steps});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           panel4.removeAll();
           panel3.validate();
           panel4.validate();
           panel3.repaint();
           panel4.repaint();
     }
     //-------------------------------------------------------------------
     void showgames(jnode justtopic) {
        jnode node;
        if(!getContentPane().isAncestorOf(panel4)) {
              getContentPane().add(panel4,grid1);
              getContentPane().validate();
        }
        TreePath selg = games.getLeadSelectionPath();
        String savecurr = null;
        if(selg != null) savecurr = games.savePath((jnode)selg.getLastPathComponent());
        panel4.removeAll();
        GridBagConstraints grid2 = new GridBagConstraints();
        grid2.gridy = -1;grid2.gridx = 0 ;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weightx = 1;
        panel3.removeAll();
        setgames(justtopic);
        panel3.add(panel4x,BorderLayout.CENTER);
//        userecbutton.setVisible(recgamesok());
        setuserec();
        panel3.validate();
        panel4.validate();
        if(savecurr != null && (node = games.expandPath(savecurr)) != null)
                  selg = new TreePath(node.getPath());
        else selg = new TreePath(games.root.getFirstLeaf().getPath());
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(games.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          games.setSelectionPaths(new TreePath[]{selg});
        games.scrollPathToVisible(selg);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dragger.setActiveDestinations(new JComponent[]{steps});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        panel3.repaint();
        panel4.repaint();
     }
     //------------------------------------------------------------------------
     void setuserec() {
//       if(sp.it[currstep].topics.length != 1 || sp.it[currstep].games.length > 0 ||!recgamesok())
//             sp.it[currstep].userecommended = false;
//       userecbutton.setSelected(sp.it[currstep].userecommended);
     }
     //-------------------------------------------------------------------
     void showstupanel() {
        jnode node;
        if(!getContentPane().isAncestorOf(panel4)) {
              getContentPane().add(panel4,grid1);
              getContentPane().validate();
        }
        if(!panel3.isAncestorOf(stupanel)) {
           panel3.removeAll();
           panel4.removeAll();
           panel3.add(stupanel,BorderLayout.CENTER);
        }
        if(sp.students.length == 0)  {
           panel3.validate();
           panel4.validate();
             return;
        }
        int sel = stujlist.getSelectedIndex();
        if(sel<0) sel=0;
        node = sharkTree.find(root,sp.students[sel]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(node != null)
        if(node != null && studenttc.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          studenttc.setSelectionPath(new TreePath(node.getPath()));
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dragger.setActiveDestinations(new JComponent[]{stujlist});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        panel3.validate();
        panel4.validate();
        panel3.repaint();
        panel4.repaint();
        return;
     }
     //------------------------------------------------------------------
     boolean recgamesok() {
       if(sp.it[currstep].topics.length == 1) {
         topic tt;
         jnode node;
         String tn;
         node = topics.expandPath(sp.it[currstep].topics[0]);
         if(node!= null && node.isLeaf() && (tn = node.get()).length()>0) {
           tt = topic.findtopic((tn.charAt(0)==topicTree.ISTOPIC.charAt(0))?tn.substring(1):tn);
           if(tt != null) {
             if(!tt.initfinished) tt.getWords(null,false);
             if(tt.markgames() != null) return true;
           }
         }
       }
       return false;
     }
     //----------------------------------------------------------------
     void newstep(int step) {
        if(!getContentPane().isAncestorOf(panel4)) {
              getContentPane().add(panel4,grid1);
              getContentPane().validate();
        }
        selgame=seltopic=-1;
        selstep = -1;
        dely=0;
        programitem newit[] = new programitem[sp.it.length+1];
        if(games.getSelectedNode() == null)
           games.setSelectionPath(new TreePath(games.root.getFirstLeaf().getPath()));
        if(topics.getSelectedNode() == null)
           topics.setSelectionPath(new TreePath(topics.root.getFirstLeaf().getPath()));
        System.arraycopy(sp.it,0,newit,0,step);
        if(step<sp.it.length)
           System.arraycopy(sp.it,step,newit,step+1,sp.it.length-step);
        sp.it = newit;

        currstep = step;
        sp.it[step] = new programitem();
        sp.it[step].maxerrors = 2;
        sp.it[step].mustcomplete = 1;
        sp.it[step].phonics = phonics.isSelected();
        getdo.setvalue(newit[step].mustcomplete);
        geterr.setvalue(newit[step].maxerrors);
        steps.setListData(sp.it);
        steps.setSelectedIndex(currstep=step);
     }
     //----------------------------------------------------------------
     void setupstep() {
        jnode node;
        panel4.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width/6,sharkStartFrame.screenSize.height));
        dely=0;
        selgame=seltopic=-1;
        selstep = -1;
        if(currstep <0 || currstep >= sp.it.length) return;
        short i;
        if(steps.getSelectedIndex() != currstep) {
           steps.setSelectedIndex(currstep);
           return;   // rely on listselectionlistener
        }
        panel3.removeAll();
        panel4.removeAll();
        panel3.validate();
        panel4.validate();
        geterr.setvalue(sp.it[currstep].maxerrors);
        getdo.setvalue(sp.it[currstep].mustcomplete);
        phonics.setSelected(sp.it[currstep].phonics);
        wordlist.usephonics  = sp.it[currstep].phonics  || wordTree != null && wordTree.forcephonics;
     }
     //---------------------------------------------------------------
     void setgames(jnode justtopic) {
        jnode node,node2;
        int i,j;
        String tn;
        games.setup(sharkStartFrame.publicGameLib,
                  true,true,allgames,gamestoplay.categories);
        if(sp.it[currstep].topics.length > 0) {
              topic tt[] = new topic[justtopic==null? sp.it[currstep].topics.length:1];
              for(i=0;i<sp.it[currstep].topics.length;++i) {
                  if(justtopic!=null && !topics.savePath(justtopic).equals(sp.it[currstep].topics[i])) continue;
                  int ii = justtopic==null ? i : 0;
                  node = topics.expandPath(sp.it[currstep].topics[i]);
                  if(node!= null && node.isLeaf() && (tn = node.get()).length()>0) {
                    tt[ii] = topic.findtopic((tn.charAt(0)==topicTree.ISTOPIC.charAt(0))?tn.substring(1):tn);
                    tt[ii].phonicscourse = u.phonicscourse(sp.it[currstep].topics[i]);
                  }
                  else return;  // if no good or group, allow all games
              }
              games.reduceGames(tt,sp.it[currstep].phonics);
              if(tt.length == 1) {
                games.reduceGames(tt[0]);
                games.addrecommended(tt[0]);
              }
        }
        games.root.dontcollapse=true;
        for(i=0;i<games.root.getChildCount();++i) {
               node2 = (jnode)games.root.getChildAt(i);
               node2.dontcollapse=true;
               for(j=0;j<node2.getChildCount();++j) {
                 ((jnode)node2.getChildAt(j)).dontcollapse = true;
               }
        }
     }
     //----------------------------------------------------------------
     boolean  saveit(boolean ask ) {
        int i;
        stepchange=false;
        if(sp.it.length >0 && sp.it.length>=1) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(!ask || u.yesnomess(u.gettext("stuprog_","program"),u.gettext("stuprog_","save") + name + "'?")) {
              if(!ask || u.yesnomess(u.gettext("stuprog_","program"),u.gettext("stuprog_","save") + name + "'?", thisprog)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                write();
                adm.delprog();  // rebuild prog list
                return true;
          }
        }
        return false;
     }
     boolean notopics() {
        int i;
        for(i=0;i<sp.it.length;++i) {
          if(sp.it[i].topics == null || sp.it[i].topics.length == 0) return true;
        }
        return false;
     }
     //----------------------------------------------------------------
     boolean  assignit(boolean ask ) {
        int i;
        if(sp.it.length >0 && sp.it.length>=1) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(!ask || u.yesnomess(u.gettext("stuprog_","program"),u.gettext("stuprog_","assign",name))) {
              if(!ask || u.yesnomess(u.gettext("stuprog_","program"),u.gettext("stuprog_","assign",name), thisprog)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if((sp.students.length == 0 || sp.students[0].equals(allstudents))   //  rb 16/3/08  mmmm
                          && !u.yesnomess("simprog_allstu2")) return false;          //  rb 16/3/08  mmmm
               String ss[] = expand(sp.students); // get actual students
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sp.version = shark.versionNoDetailed;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               for(i=0;i<ss.length;++i) {
                 //   start rb 23/10/06
                  if(!name.endsWith("["+teacher+"]")) {              //  start rb 4/3/08
                    db.delete(ss[i],name,db.PROGRAM);                // in case old version there
                    db.update(ss[i], name + "[" + teacher + "]", sp, db.PROGRAM);
                  }                                                  //  end rb 4/3/08
                  else db.update(ss[i],name,sp,db.PROGRAM);
                //   end rb 23/10/06
                  db.update(ss[i],"_refresh","",db.TEXT);
                }
                adm.treestripprograms();  // refresh admin tree
                adm.addprograms();
          }
        }
        return true;
     }


     static boolean programnamecheck(String s) {
        return s!=null && !s.trim().equals("");

     }

     //------------------------------------ keep only required parts of game tree  // start changed rb 1/2/08
     static void keepselected(gamestoplay gameTree,saveprogram  sp, int currstep) {
       int i,j;
       jnode jj,jjnext;
       if(currstep >= sp.it.length || sp.it[currstep].games == null) return;
       String ss[] = new String[sp.it[currstep].games.length];
       for (i = 0; i < sp.it[currstep].games.length; ++i) {
          j = sp.it[currstep].games[i].lastIndexOf(topicTree.CSEPARATOR);
          if(j>=0)  ss[i] = sp.it[currstep].games[i].substring(j+1);
          else ss[i] = sp.it[currstep].games[i];
       }
       loop1: for(jj = (jnode)gameTree.root.getFirstLeaf(); jj != null; jj = jjnext) {
         jjnext = (jnode) jj.getNextLeaf();
         jnode jjp = (jnode) jj.getParent();
         for (i = 0; i < ss.length; ++i) {
           if (jjp.get().equals(ss[i]))
             continue loop1;
           if (jj.get().equals(ss[i]))
             continue loop1;
         }
         gameTree.model.removeNodeFromParent(jj);
         if (jjp.isLeaf() && !jjp.isRoot() && u.findString(sharkStartFrame.gametreeheadings,jjp.get())<0) {
           gameTree.model.removeNodeFromParent(jjp);
         }
       }
     }                                                                     // end changed rb 1/2/08
     //---------------------------------------------------------------
        // check all topics have something to play
     static boolean checkgames(saveprogram  sp, topicTree topics) {
         long starttime = System.currentTimeMillis();
         boolean saveph = wordlist.usephonics;
         if(sp.it.length >0 && sp.it.length>=1) {
            u.info mess = null;
            for(int i=0;i<sp.it.length;++i) {
              if(sp.it[i].games==null || sp.it[i].games.length==0) continue;
              if(u.findString(sp.it[i].games,gamestoplay.treeheading) >= 0
                 || u.findString(sp.it[i].games,gamestoplay.treeheading2) >= 0) continue;
              if(sp.it[i].topics==null || sp.it[i].topics.length == 0) continue;
              for(int j=0;j<sp.it[i].topics.length;++j) {
                 jnode node = topics.expandPath(sp.it[i].topics[j]),node2;
//                 if (node == null) {
//                    int iiii=0;
//                 }
                 if(node != null && !node.isLeaf()) {
                   if(mess == null ) {
                        if(!ChangeScreenSize_base.isActive){
                            mess = new u.info(u.gettext("stuprog_","valid"),
                                u.gettext("stuprog_","check"),
                                sharkStartFrame.mainFrame.getWidth()/4,sharkStartFrame.mainFrame.getHeight()/3,sharkStartFrame.mainFrame.getWidth()/2,sharkStartFrame.mainFrame.getHeight()/3);
                       }
                       else{
                            mess = new u.info(u.gettext("stuprog_","valid"),
                                u.gettext("stuprog_","check"),
                                sharkStartFrame.mainFrame.getLocation().x+sharkStartFrame.mainFrame.getWidth()/4,
                                sharkStartFrame.mainFrame.getLocation().y+sharkStartFrame.mainFrame.getHeight()/3,
                                    sharkStartFrame.mainFrame.getWidth()/2,
                                    sharkStartFrame.mainFrame.getHeight()/3);
                       }
                     u.pause(10);
                     mess.paintComponents(mess.getGraphics());
                   }
                 }
                 if(node != null && !checknode(node,sp,i)) {if (mess!= null) mess.dispose();return false;}
              }
           }
           if(mess != null) mess.dispose();
           wordlist.usephonics = saveph;
           nogames.setVisible(false);
           return true;   // all topics ok
        }
        else {
          wordlist.usephonics = saveph;
          return false;
        }
     }
     //--------------------------------------------------------------
     static boolean checknode(jnode node,saveprogram sp, int i) {
        String tn;
        topic tt;
        jnode node2,node3,node4;
        gamestoplay gameTree = new gamestoplay();
        for(node2 = (jnode)node.getFirstLeaf();
                         node2 != null && node.isNodeDescendant(node2);
                         node2 = (jnode)node2.getNextLeaf()) {
           gameTree.setup(sharkStartFrame.publicGameLib, true,true,"",gamestoplay.categories);
           keepselected(gameTree,sp,i);
           if((tn = node2.get()).length()>0
                &&  (tt = topic.findtopic((tn.charAt(0)==topicTree.ISTOPIC.charAt(0))?tn.substring(1):tn)) != null) {
               tt.phonicscourse = u.phonicscourse(node2);
              wordlist.usephonics =  sp.it[i].phonics || tt.justphonics;
              gameTree.reduceGames(new topic[]{tt},sp.it[i].phonics);
              if((node4 = (jnode)gameTree.root.getFirstLeaf()).get().length()==0) {
//                 u.okmess(u.gettext("stuprog_","valid"),
//                          u.gettext("stuprog_","cantplay", node2.get()), sharkStartFrame.mainFrame);
                 nogamesstep = i;
                 nogamestopic = node;
                 nogamesnode2 = node2;
//                 nogames.setText(u.edit(nogamestext,nogamesname()));             //  rb 16/3/08  mmmm

                 nogames.setToolTipText(nogamesstr.replaceAll("%", nogamesname()));
                 nogames.setVisible(true);
                 return false;
              }
           }
        }
        return true;
     }
    //------------------------------------------------------------------
     void write() {
        if(!oldname.equalsIgnoreCase(name)) db.delete(dbname, oldname, db.PROGRAM);
        db.update(dbname, name, sp, db.PROGRAM);

     }
     static saveTree1.saveTree2[]  gettopic(saveprogram sp) {
        int i,j,len;
        if(sp.currstep < sp.it.length && sp.it[sp.currstep].topics != null) {
           return sp.it[sp.currstep].trees;
        }
        return null;
     }
     static boolean[]  getmixed(saveprogram sp) {       //  rb 16/3/08  mmmm start
        int i,j,len;
        if(sp.currstep < sp.it.length && sp.it[sp.currstep].topics != null) {
           if(sp.it[sp.currstep].mixed != null) return sp.it[sp.currstep].mixed;
           else return new boolean[sp.it[sp.currstep].topics.length];
        }
        return null;
     }                                                  //  rb 16/3/08  mmmm end
     static String[]  gettopicpath(saveprogram sp) {
        int i,j,len;
        if(sp.currstep < sp.it.length && sp.it[sp.currstep].topics != null) {
           return sp.it[sp.currstep].topics;
        }
        return null;
     }
     static String[] getgames(saveprogram sp) {
        int i,j,len;
        if(sp.currstep < sp.it.length && sp.it[sp.currstep].games != null) {
           String s[] = new String[len = sp.it[sp.currstep].games.length];
           for(i=0;i<len;++i) {
              String ss = sp.it[sp.currstep].games[i];
              if(ss != null) {
                 while((j=ss.indexOf(topicTree.CSEPARATOR)) >= 0)
                  ss = ss.substring(0,j) + topicTree.SEPARATOR + ss.substring(j+1);
                 s[i] = ss;
              }
           }
           return s;
        }
        return null;
     }
     static String[] getgames(saveprogram sp,int currstep) {
        int i,j,len;
        if(currstep < sp.it.length && sp.it[currstep].games != null) {
           String s[] = new String[len = sp.it[currstep].games.length];
           for(i=0;i<len;++i) {
              String ss = sp.it[currstep].games[i];
              if(ss != null) {
                 while((j=ss.indexOf(topicTree.CSEPARATOR)) >= 0)
                  ss = ss.substring(0,j) + topicTree.SEPARATOR + ss.substring(j+1);
                 s[i] = ss;
              }
           }
           return s;
        }
        return null;
     }
     /**
      * @param sp Program to be saved
      * @param err Number of errors student made in the game
      * @return True if the student does not have a play program
      * or they have not made too many errors and enough of the game has been played
      * otherwise the returned value is false.
      */
     static boolean endgame(saveprogram sp,int err) {
        int i,j,len;
        boolean ret = false;
        if(db.query(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
                         sharkStartFrame.playprogramname, db.PROGRAM) < 0) {//Condition 1
                                                 // was it removed by teacher?
            return true;
        }
        if(sp.currstep < sp.it.length) {                            //Condition 2
           if(err <= sp.it[sp.currstep].maxerrors) {                //Condition 2.1
              if (++sp.completed >= sp.it[sp.currstep].mustcomplete){//Condition 2.1.1
                 if(sp.currstep < sp.it.length-1) {                 //condition 2.1.1.1
                    ++sp.currstep;
                    sp.completed = 0;
                    ret=true;
                 }
              }
              db.update(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,
                         sharkStartFrame.playprogramname,  sp, db.PROGRAM);
           }
        }
        return ret;
     }

  static String getProgramName(String stu, String teachername) {
    String ss[] = db.list(stu,db.PROGRAM);
    String ret = null;
    for(int i = 0; i < ss.length; i++){
//        if(ss[i].endsWith("["+teachername+"]")){
        if(u.CaseInsensitiveEndsWith(ss[i], "["+teachername+"]")){
            program.saveprogram setprogram = (program.saveprogram)db.find(stu, ss[i], db.PROGRAM);
            if(setprogram.simple){
                ret = ss[i];
            }
            else{
                ret = ss[i];
            }
        }
//        else if(ss[i].endsWith(teachername)){
        else if(u.CaseInsensitiveEndsWith(ss[i], teachername)){    
            program.saveprogram setprogram = (program.saveprogram)db.find(stu, ss[i], db.PROGRAM);
            if(setprogram.simple){
                ret = ss[i];
            }
            else{
                ret = ss[i];
            }
        }
    }
    for(int i = 0; ret!=null && i < ss.length; i++){
//        if(ss[i].endsWith("["+teachername+"]")){
        if(u.CaseInsensitiveEndsWith(ss[i], "["+teachername+"]")){    
            if(!ret.equals(ss[i])){
                db.delete(stu,ss[i], db.PROGRAM);
            }
        }
//        else if(ss[i].endsWith(teachername)){
        else if(u.CaseInsensitiveEndsWith(ss[i], teachername)){    
            if(!ret.equals(ss[i])){
                db.delete(stu,ss[i], db.PROGRAM);
            }
        }
    }
    return ret;
}



   //----------------------------------------------------------
//   void addtostudenttree(jnode node, String[] s) {
//     int i;
//     student stu;
//     loop1: for(i=0;i<s.length;++i) {
//        if((stu = student.findStudent(s[i])) != null) {
//           jnode newnode=new jnode(s[i]);
//           student.seticon(newnode,stu);
//           model.insertNodeInto(newnode,node,node.getChildCount());
//           if(stu.students != null && stu.students.length > 0) {
//             addtostudenttree(newnode, stu.students);
//           }
//        }
//     }
//   }
   //-----------------------------------------------------------------
   boolean mouseposition() {
      if (mousepos < 0) return false;
      int my = mousepos;
      int i;
      int incy = getFontMetrics(steps.getFont()).getHeight();
      dely=0;
      for(short j=0;j<sp.it.length;++j) {
        Rectangle r  = steps.getCellBounds(j,j);
        if(r==null) continue;
        programitem  step = sp.it[j];
        int y1 = r.y + MARGIN;
        if(my >=y1 && my < y1 + incy) {
            selstep=currstep=j;
            seltopic=-1;
            selgame = -1;
            selstepdet = true;
            return true;
        }
        y1 += incy;
        if(step.topics.length == 0) {
          if(my >=y1 && my < y1 + incy) {
            selstep=currstep=j;
            seltopic=0;
            selgame = -1;
            selstepdet = false;
            return true;
          }
          y1 += incy;
        }
        else for(i=0;i<step.topics.length;++i) {
          if(my >=y1 && my < y1 + incy) {
            selstep=currstep=j;
            seltopic=i;
            selgame = -1;
            selstepdet = false;
            return true;
          }
          y1 += incy;
        }
        if(step.games.length == 0) {
          if(my >=y1 && my < y1 + incy) {
            selstep=currstep=j;
            seltopic=-1;
            selgame = 0;
            selstepdet = false;
            return true;
          }
          y1 += incy;
        }
        else for(i=0;i<step.games.length;++i) {
          if(my >=y1 && my < y1  + incy) {
            selstep=currstep=j;
            selgame=i;
            seltopic = -1;
            selstepdet = false;
            return  true;
          }
          y1 += incy;
        }
      }
      return false;
   }
   class arrowc extends JLabel {
      int h,w,a;
      FontMetrics m;
      arrowc() {
        super(" ");
      }
      public void paint(Graphics g) {
         int delydif = steps.getLocationOnScreen().y - this.getLocationOnScreen().y;
         m = getFontMetrics(getFont());
         h = m.getHeight();
         a = m.getAscent();
         g.setColor(Color.white);
         w = this.getWidth();
         g.fillRect(0,0,w,this.getHeight());
         g.setColor(Color.black);
         g.setFont(getFont());
         if(panel3.isAncestorOf(stupanel)) {
           int sel = stujlist.getSelectedIndex();
           if(sel>=0 && sp.students.length>0 && !sp.students[0].equals(allstudents)) {
              Rectangle r = stujlist.getCellBounds(sel,sel);
              int tlen = deltext.length;
              int y = stujlist.getLocationOnScreen().y + r.y + r.height/2
                        - this.getLocationOnScreen().y;
              arrow(g,y);
              delto = y + h/2;
              y -= (tlen)*h + h/2;
              text(g,y,deltext);
              delfrom = y;
           }
         }
         else if(dely>0) {
            int tlen = deltext.length,y;
            if((tlen+1)*h < dely +delydif)  {
                y = dely  +delydif - (tlen)*h - h/2;
                delfrom=y; delto=y+(tlen+1)*h;
            }
            else {
                y = dely +delydif + h/2;
                delfrom=y - h; delto=delfrom + (tlen+1)*h;
            }
            text(g,y,deltext);
            arrow(g,dely +delydif);
         }
         else if(arrowpos != null && arrowpos.length > 0) {
            text(g,0,seltext);
            for(short i=0;i<arrowpos.length;++i) {
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              // just the arrows next to the selected step appear
              if((arrowpos[i] > program.arrowStepTop) &&
                 (arrowpos[i] < program.arrowStepBottom)){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                arrow(g,arrowpos[i]+delydif);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
            arrow(g,stujlist.getLocationOnScreen().y - this.getLocationOnScreen().y
                  + stujlist.getFontMetrics(stujlist.getFont()).getHeight()/2);
         }
      }
      void arrow(Graphics g, int y) {
        g.drawLine(0,y,w,y);
        g.fillPolygon(new int[]{w,w-h/2,w-h/2},
                      new int[]{y,y-h/4,y+h/4},3);
      }
      void text(Graphics g, int y,String s[]) {
        y += a;
        for(short i=0;i<s.length;++i) {
          g.drawString(s[i], w/2 - m.stringWidth(s[i])/2, y);
          y+=h;
        }
      }
   }
   //----------------------------------------------------------------
   static String makename() {
      Date d = new Date();
      return (new SimpleDateFormat("dd/MM/yy")).format(d)+ "  "
                   + (new SimpleDateFormat("HH:mm:ss")).format(d);

   }
   //start rb 23/10/06
//   static String makename(String teachername) {
//      Date d = new Date();
//      int i;
//      return makename() + " " + teachername;
//   }
   //end rb 23/10/06

  public static  class programsteps extends JList {
    saveprogram sp;
    String steps[][];
    programsteps(saveprogram sp2) {
       super();
       int i,j;
       setCellRenderer(new itempainter());
       sp =sp2;
       if(sp!=null && sp.it != null)
           setListData(sp.it);
    }
  }
  //-----------------------------------------------------------
  static class itempainter extends JLabel implements ListCellRenderer {
     Object o;
     boolean selected;
     Rectangle r1=new Rectangle();
     int w;
     topicTree topics;
     itempainter() {setOpaque(true);}
     public Component getListCellRendererComponent( JList list,Object oo,
             int index,boolean isSelected,boolean cellhasFocus) {
        int w = list.getWidth();
        o = oo;
        selected=isSelected;
        if(index<=lastindex) arrowpos=new int[0];
        lastindex=index;
        return this;
     }
     public void paint(Graphics g) {
        int screeny  = this.getY();
        int x1 = MARGIN;
        short i;
//        g.clearRect(0,0,this.getWidth(),this.getHeight());
        g.setFont(getFont());
        FontMetrics m = g.getFontMetrics();
        int ydiff = m.getMaxAscent();
        int y1 = MARGIN + ydiff;
        int onechar = m.charWidth(' '),incy = m.getHeight();
        String s;
        int j, sy, w =this.getWidth(),h = this.getHeight();
        programitem  step = (programitem)o;
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(selected){
          program.arrowStepTop = screeny;
          program.arrowStepBottom = screeny + h;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        g.setColor(selected?Color.yellow:Color.white);
        g.fillRect(0,0,w,h);
        g.setColor(Color.lightGray);
        g.drawLine(0,0,w,0);
        if(selected && selstepdet) {
          g.setColor(Color.orange);
          g.fillRect(0,MARGIN,w,incy);
        }
        g.setColor(Color.black);
        g.drawString(heading(step), x1,y1);
        sy = y1-ydiff+screeny;
        arrowpos = u.addint(arrowpos,sy + incy/2);
        y1 += incy;
        g.setColor(sharkStartFrame.topictreecolor.darker());
        if(step.topics.length == 0) {
          if(selected && seltopic == 0) {
            g.setColor(Color.orange);
            g.fillRect(0,y1-ydiff,w,incy);
            g.setColor(sharkStartFrame.topictreecolor.darker().darker());
          }
          g.drawString(alltopics,x1,y1);
          arrowpos = u.addint(arrowpos,y1-ydiff+screeny + incy/2);
          y1 +=incy;
        }
        else for(i=0;i<step.topics.length;++i) {
          s = step.topics[i];
          sy = y1-ydiff+screeny;
          arrowpos = u.addint(arrowpos,sy + incy/2);
          if(selected && seltopic == i) {
            dely = sy + incy/2;
            g.setColor(Color.orange);
            g.fillRect(0,y1-ydiff,w,incy);
            g.setColor(sharkStartFrame.topictreecolor.darker().darker());
          }
          g.drawString(spellchange.spellchange(simpleprogram.edit(new String[]{s},topics,step.mixed)[0]),x1,y1); //  rb 16/3/08  mmmm
          y1 +=incy;
        }
        g.setColor(sharkStartFrame.gamescolor);
        if(step.games.length == 0) {
          if(selected && selgame == 0) {
            g.setColor(Color.orange);
            g.fillRect(0,y1-ydiff,w,incy);
            g.setColor(sharkStartFrame.gamescolor);
          }
          g.drawString(allgames,x1,y1);
          arrowpos = u.addint(arrowpos,y1-ydiff+screeny + incy/2);
          y1 +=incy;
        }
        else for(i=0;i<step.games.length;++i) {
          s = step.games[i];
          if(s==null) s = allgames;
          sy = y1-ydiff+screeny;
          arrowpos = u.addint(arrowpos,sy + incy/2);
          if(selected && selgame == i) {
            dely = sy + incy/2;
            g.setColor(Color.orange);
            g.fillRect(0,y1-ydiff,w,incy);
            g.setColor(sharkStartFrame.gamescolor);
          }
          while((j=s.indexOf(topicTree.CSEPARATOR))>=0) s = s.substring(j+1);
          g.drawString(s,x1,y1);
          y1 +=incy;
        }
        panel5.repaint(); // make sure
     }
     String heading(programitem  step) {
        return  u.gettext("stuprog_","play")+String.valueOf(step.mustcomplete)+u.gettext("stuprog_","games")
            + String.valueOf(step.maxerrors) + " ---";
     }

     public Dimension getPreferredSize() {
        FontMetrics m = getFontMetrics(getFont());
        programitem  step = (programitem)o;
        int i,j;
        String s;
        int gh =  m.getHeight();
        int h =  MARGIN*2 + gh*(1+Math.max(1,step.topics.length)+Math.max(1,step.games.length));
        int w = MARGIN*2+m.stringWidth(heading(step));
        for(i=0;i<step.topics.length;++i) {
          s = step.topics[i];
          if(s==null) continue;
          while((j=s.indexOf(topicTree.CSEPARATOR))>=0) s = s.substring(0,j)+" / "+s.substring(j+1);
          w = Math.max(w,MARGIN*2 + m.stringWidth(s));
        }
        for(i=0;i<step.games.length;++i) {
          s = step.games[i];
          if(s==null) continue;
          while((j=s.indexOf(topicTree.CSEPARATOR))>=0) s = s.substring(j+1);
          w = Math.max(w,MARGIN*2 + m.stringWidth(s));
        }
        return  new Dimension(w, h);
    }
  }   // end of program display class
public static class saveprogram  implements Serializable{
    static final long serialVersionUID = 8887644653120980253L;
    programitem it[] = new programitem[0];
    short currstep,completed;
    String teacher;
    String[] students;
    boolean simple,onlyone,hardsuper,v4;
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    String version;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
}
  public static class programitem  implements Serializable{
      static final long serialVersionUID = 8369073967857812438L;
      String topics[] = new String[0];
      saveTree1.saveTree2 trees[] = new saveTree1.saveTree2[0];
      boolean mixed[] = new boolean[0];                  //  rb 16/3/08  mmmm
      String games[] = new String[0];
      short maxerrors = 2, mustcomplete = 1;
      boolean phonics;
  }
//  public static class oldprogramitem  implements Serializable{
//      static final long serialVersionUID = -4773289384999757894L;
//      String topics[] = new String[0];
//      saveTree1.saveTree2 trees[] = new saveTree1.saveTree2[0];
//      String games[] = new String[0];
//      short maxerrors = 2, mustcomplete = 1;
//      boolean phonics;
//  }
  public static class stujpainter extends JLabel implements ListCellRenderer {  // for student list
     stujpainter() {setOpaque(true);}
     String o;
     FontMetrics m;
     public Component getListCellRendererComponent( JList list,Object oo,
           int index,boolean isSelected,boolean cellhasFocus) {
        o = (String)oo;
        this.setBackground(isSelected?list.getSelectionBackground():Color.white);
        if(o.startsWith(topicTree.ISPATH)) {
          setForeground(Color.red);
          setText(u.stringreplace(o.substring(1),topicTree.ISPATH.charAt(0),'/'));
        }
        else {
          setForeground(Color.black);
          setText(o);
        }
        if(!list.isEnabled()){
          setForeground(Color.lightGray);
        }
        return this;
     }
  }
}
