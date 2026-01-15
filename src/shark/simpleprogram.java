
package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import shark.program.programitem;
//startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
import java.util.*;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

public class simpleprogram
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     String name,oldname,  teacher;
     String startitems[];
     String dbname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
     program.saveprogram sp;
     BorderLayout layout1a = new BorderLayout();
     BorderLayout layout1b = new BorderLayout();
     BorderLayout layout1c = new BorderLayout();
     GridBagLayout layout1d = new GridBagLayout();
     BorderLayout layout2 = new BorderLayout();
     GridBagLayout layout3 = new GridBagLayout();
     GridBagLayout gridBagLayout0 = new GridBagLayout();
     GridBagLayout gridBagLayout1 = new GridBagLayout();
     GridBagLayout gridBagLayout3 = new GridBagLayout();
     GridBagLayout gridBagLayout4 = new GridBagLayout();
     GridBagLayout gridBagLayout5 = new GridBagLayout();
     JPanel panel1 = new JPanel();
     JPanel panel1a = new JPanel();
     JPanel panel1b = new JPanel();
     JPanel panel1c = new JPanel();
     JPanel panel1d = new JPanel();
//     JPanel panel2 = new JPanel();
//     JPanel panel3 = new JPanel();

     JPanel panelright = new JPanel(new GridBagLayout());
     JPanel panelstu = new JPanel(new GridBagLayout());
     JPanel panelstepped = new JPanel(new GridBagLayout());
     JPanel paneltopics = new JPanel(new GridBagLayout());
     JPanel panelgames = new JPanel(new GridBagLayout());

     JPanel stupanel = new JPanel();
     JPanel steppedpanel = new JPanel();
     JPanel tpanel = new JPanel();
     JPanel tpanel2 = new JPanel();
     JPanel gpanel = new JPanel();
     JPanel gpanel2 = new JPanel();
     String alltopics = u.gettext("simprog_all","alltopics");
     String allstudents = u.gettext("simprog_all","allstudents");
     String allgames = u.gettext("simprog_all","allgames").replaceFirst("%", shark.programName);
     static String recgames = u.gettext("simprog_all","recgames");
     mlabel_base wordslabel1 = new mlabel_base(u.gettext("findword_wordlistwords","label"));
     String wordsl2 = u.gettext("findword_wordlistwords","label2");
     String wordsl3 = u.gettext("findword_wordlistwords","label3");
     mlabel_base wordslabel2 = new mlabel_base(wordsl2);
     mlabel_base treelabel = new mlabel_base(u.gettext("findword_topictree","label"));
//     mlabel label1a = u.mlabel("simprog_stu");
     JLabel btStepped = new JLabel(u.gettext("steppedprogram", "steps"));
     JLabel btStudents = new JLabel(u.gettext("simprog_", "selectstu"));
//     mlabel label1b = u.mlabel("simprog_topics");
     JLabel btTopics = new JLabel(u.gettext("simprog_", "selectlist"));
//     mlabel label1c = u.mlabel("simprog_games");
     JLabel btGames = new JLabel(u.gettext("simprog_", "selectgam"));
     Color steppedcol = new Color(153,102,153);
     mlabel_base steppedlabel = u.mlabel("simprog_stulist");
     mlabel_base stulabel = u.mlabel("simprog_stulist");
     mlabel_base tlabel = u.mlabel("simprog_topiclist");
     mlabel_base glabel = u.mlabel("simprog_gameslist");
     JList stujlist=new JList(),topicsjlist=new JList(),gamesjlist=new JList(),steppedjlist=new JList();
     topicTree topics = new topicTree();
     JScrollPane topscroller;
     JPanel whitetopp;
     gamestoplay games = new gamestoplay();
     jnode currstu2,root = new jnode();
     DefaultTreeModel model = new DefaultTreeModel(root);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     JTree studenttc = new JTree(model);
     dndTree_base studenttc = new dndTree_base(model);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     jnode lastnode;
     mbutton exitbutton = u.mbutton("simprog_exit");
     mbutton cancelbutton = u.mbutton("simprog_cancel");
//     mbutton searchbutton = u.mbutton("simprog_search");
//     mbutton changestu = u.mbutton("simprog_changestu");
//     mbutton changetopic = u.mbutton("simprog_changetopic");
//     mbutton changegame = u.mbutton("simprog_changegame");
//     mbutton addstu = u.mbutton("simprog_addstu");     
     JCheckBox hardsuper = shark.phonicshark?null:u.CheckBox("hardsuper");
     JCheckBox phonics = shark.phonicshark?null:u.CheckBox("simprog_phonics");
     mlabel_base phlabel = shark.phonicshark?null:new mlabel_base(u.gettext("simprog_phonics","label2"));
//     mbutton addtopic = u.mbutton("simprog_addtopic");
//     mbutton addgame = u.mbutton("simprog_addgame");
//     String changestutext = changestu.text;
//     String changegametext = changegame.text;
//     String changetopictext = changetopic.text;
     String delstu = u.gettext("simprog_delstu","label");
     String deltopic = u.gettext("simprog_deltopic","label");
     String delgame = u.gettext("simprog_delgame","label");
     simpleprogram thisprog = this;
     admin adm;
     boolean nosave;
     wordlist wordTree;
     JScrollPane scroller;
     JCheckBox orderck;
     boolean changed;
     String writename;   // rb 23/10/06
     dragger_base dragger;
//startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     String students1[];
     Color defaultpanel;
     int basketmargin = 5;
     
JButton btopGam;
JButton btopTop;
JButton btopStu;
JButton btopStepped;
JButton baddStepped;
JButton bupStepped;
JButton bdownStepped;
JButton btopSearch;
//JButton btopWarn;
JLabel lbStartTop = new JLabel();
JLabel lbStartGam = new JLabel();
      JScrollPane spStu;
      JScrollPane spStepped;
       JScrollPane spTop;
       JScrollPane spGam;
       boolean gamesfirstshow = true;
mlabel_base lbTopStu;
mlabel_base lbTopTop;
mlabel_base lbTopGam;
sharkImage gameiconim;
mover gameicontext;
mover gamedesctext;
runMovers gameiconpanel;
        int basketw = sharkStartFrame.screenSize.width*7/26;
        int basketrest = sharkStartFrame.screenSize.width*19/26;
topicTree courseList;
JList courseJList;
JPanel topmainlistpan;
JPanel emptylistpan;
JPanel emptylistpan_mess;
int mpanborderw = 15;
       // Color brown = new Color(139, 69, 19);
        Color blk = Color.black;//new Color(101, 67, 33);
        
      //  Color cream = new Color(255, 250, 205);
        Color cream = sharkStartFrame.cream;//new Color(250, 235, 215);
        JPanel jpstepped;
JPanel jpstu;
        JPanel jptop;
        JPanel jpgam;
        Color toplabelbg = u.lighter(Color.green, 0.9f);
        Color toplabelfg = blk;
        String strdefaultgames = u.gettext("simprog_", "defaultgames");
        JPanel pnlbstu = new JPanel(new GridBagLayout());
        JPanel pnlbstepped = new JPanel(new GridBagLayout());
        JPanel pnlbtop = new JPanel(new GridBagLayout());
        JPanel pnlbgam = new JPanel(new GridBagLayout());
        boolean isstepped;
        String stepitem = u.gettext("steppedprogram", "item");
        javax.swing.Timer rearrangeTimer;
           Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
                   JSpinner spincomplete;
        JSpinner spinerrors;
        JDialog thisd;
        JDialog parent;
        boolean frommanage;
        boolean mixedwork;
        String firstingroup;
        boolean group;
        Color stucolor = new Color(204,0,0);
        boolean savephonics;
        topic savecurrtopic;
        static String strmixedlists = u.gettext("stulist_", "mixedlist");
        boolean isnew;


     simpleprogram(JDialog owner, String teachername, String students[], boolean isgroup, boolean hasmixedwork, String programname, admin aa, boolean workprogram, boolean frommanagement) {
       super(aa);
       adm = aa;
       parent = owner;
       frommanage = frommanagement;
       oldname = programname;
       isnew = programname == null;
       if(programname==null)programname =  program.makename();
       name = programname;
       writename = name + "[" + teachername + "]";    // rb 23/10/06
       mixedwork = hasmixedwork;
       if(students!=null && students.length==1 && students[0].equals(topicTree.ISPATH+u.gettext("admintree", "current")))
           students1 = new String[]{allstudents};
       else
           students1 = students;
       teacher = teachername;
       isstepped = workprogram;
       group = isgroup;
       thisd = this;
       init();
     }

     void init(){
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          super(aa);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          this.setResizable(false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        int i,j;
//        jnode node;
//startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        adm = aa;
//        oldname = name = programname;
//        writename = name + "[" + teachername + "]";    // rb 23/10/06
//        teacher = teachername;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        savecurrtopic = sharkStartFrame.currPlayTopic;
        root.setUserObject(allstudents);
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
//startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       student.buildstutree(studenttc, root, aa.teacher.students, false);
        student.buildstutree(studenttc, root, adm.teacher.students, false, false);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        root.setIcon(jnode.TEACHER);
       if(students1!=null && students1.length>0){
           if(group && !mixedwork){
               String ss[] = expand(new String[]{students1[0]});
               if(ss!=null && ss.length>0){
                   firstingroup = ss[0];
               }
           }
       }
        studenttc.expandRow(0);
        currstu2 = root;
        studenttc.setSelectionRow(0);
    //    games.set(games.root, allgames);
        games.setup(sharkStartFrame.publicGameLib,
                   false,true,allgames,gamestoplay.categories);
        if(!isnew){
            if(students1==null){
                  try{sp = (program.saveprogram) db.find(teacher, name, db.PROGRAM);}
                  catch(ClassCastException e){sp = null;}
            }
            else if(students1.length > 0){
                String dbname = students1[0];
                if(dbname.indexOf(topicTree.ISPATH)>=0){
                    dbname = dbname.substring(dbname.lastIndexOf(topicTree.ISPATH)+1);
                }


                try{sp = (program.saveprogram) db.find(firstingroup!=null?firstingroup:dbname, writename, db.PROGRAM);}
                catch(ClassCastException e){sp = null;}
                if(sp==null){
                  try{sp = (program.saveprogram) db.find(firstingroup!=null?firstingroup:dbname, name, db.PROGRAM);}
                  catch(ClassCastException e){sp = null;}
                }
    //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //startPR2008-10-15^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                student s1;
    //startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if((s1=student.getStudent(firstingroup!=null?firstingroup:dbname))!=null)
    //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //            if(u.findString(student.getStudent(students1[0]).programOverride, writename)>=0) sp=null;  //3333
                  if(u.findString(s1.programOverride, writename)>=0) sp=null;  //3333
    //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //startPR2006-04-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if (sp != null) {
    //            if(sp.it[0].mixed==null) sp.it[0].mixed = new boolean[sp.it[0].topics.length];   //  rb 16/3/08  mmmm
                for(int ii = 0; ii < sp.it.length; ii++){
                    if(sp.it[ii].mixed==null) sp.it[ii].mixed = new boolean[sp.it[ii].topics.length];
                }
                Vector invalidTopicNames;
                privateListRecord plr = new privateListRecord();
                if ((invalidTopicNames = plr.getInvalidTopics(sp)) != null) {
    //startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //              sp = plr.updateSaveProgram(students1[0], writename, sp, invalidTopicNames);
                  sp = plr.updateSaveProgram(firstingroup!=null?firstingroup:dbname, writename, sp, invalidTopicNames);
    //endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                }
              }
            }
         }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        games.setSelectionPath(new TreePath(games.root.getFirstLeaf().getPath()));
        games.setToolTipText(glabel.getToolTipText());
//        panel2.removeAll();
//        topics.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),false,db.TOPICTREE,true,alltopics);
        
        if(sp==null) {
          sp = new  program.saveprogram();
//startPR2008-10-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    sp.teacher = teachername;
              sp.teacher = teacher;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          sp.it = new programitem[1];
          sp.it[0] = new programitem();
          sp.it[0].topics = new String[0];
          sp.it[0].games = new String[0];
          sp.it[0].mixed = new boolean[0];                     //  rb 16/3/08  mmmm
          sp.onlyone = true;
          sp.simple = !isstepped;
          sp.students = students1;
        }
        else {
          sp.students = students1;                               // start rb 2/5/08
//          for(i=0;i<sp.students.length;++i) {
//            jnode nn = root.find(sp.students[i]);
//            if(nn == null) {
//               if(!sp.v4 && !sp.students[i].startsWith(topicTree.ISPATH)
//                  && (nn = root.oldfind(sp.students[i])) != null) {
//                  sp.students[i] = admin.getadminpath(nn);
//               }
//               else {
//                  sp.students = u.removeString(sp.students, i);
//                   --i;
//               }
//            }
//         }                                                       // e rb 2/5/08
        }
        sp.v4 = true;
        if(!shark.phonicshark)
            hardsuper.setSelected(sp.hardsuper);
//        u.forcenotes(hardsuper,false,false);
//        u.forcenotes(phonics,true,false);
        sp.currstep=0;
        sp.completed=0;
        if(sp.students!=null){
            if(sp.students.length == 0) sp.students = new String[]{root.get()};
            if( sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get()))
                 stujlist.setListData(new String[] {allstudents});
            else {
                String ss[] = sp.students;
 //               if(group){
  //                  ss[0] = topicTree.ISPATH+ss[0];
 //               }
                stujlist.setListData(ss);
            }
         }
        stujlist.setCellRenderer(new program.stujpainter());
//         stujlist.setCellRenderer(new admin.stupainter());
        stujlist.setSelectedIndex(0);
        GridBagConstraints grid1 = new GridBagConstraints();
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

        this.setTitle(u.gettext("simprog_","title"));
        addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
              int code = e.getKeyCode();
              if(code == KeyEvent.VK_ESCAPE) {
                 if(!nosave) {
                    if(!assignit(true)) {return;}
                }
                 nosave = true;
                 dispose();
             }
          }
        });
        getContentPane().setLayout(gridBagLayout0);
        panel1.setLayout(gridBagLayout1);
//        panel1.setBorder(BorderFactory.createLineBorder(sharkStartFrame.gamescolor,2));
        panel1.setBorder(BorderFactory.createEmptyBorder());
        panel1a.setLayout(new GridBagLayout());
        panel1b.setLayout(new GridBagLayout());
        panel1c.setLayout(new GridBagLayout());
        panel1d.setLayout(new GridBagLayout());
//        panel2.setLayout(layout2);
//        panel3.setLayout(layout3);
        stupanel.setLayout(gridBagLayout3);
        gpanel.setLayout(gridBagLayout4);
        gpanel2.setLayout(new GridBagLayout());
        tpanel2.setLayout(new GridBagLayout());
        steppedpanel.setLayout(new GridBagLayout());
        tpanel.setLayout(gridBagLayout5);
//        if(sp.it[0].topics.length==0) topicsjlist.setListData(new String[] {topics.root.get()});
//        else topicsjlist.setListData(spellchange.spellchange(edit(sp.it[0].topics,topics,sp.it[0].mixed)));    //  rb 16/3/08  mmmm
  //  rb 16/3/08  mmmm

        stulabel.setBackground(stucolor);
        stulabel.setForeground(Color.white);

        steppedlabel.setBackground(steppedcol);
        steppedlabel.setForeground(Color.white);

        tlabel.setBackground(toplabelbg);
        tlabel.setForeground(toplabelfg);
//        searchbutton.setBackground(Color.yellow);
//        searchbutton.setForeground(Color.black);
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        searchbutton.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        searchbutton.setText("Search for word list");
        glabel.setBackground(sharkStartFrame.gamescolor);
        glabel.setForeground(Color.white);
        wordTree = new wordlist();

        jpstepped = new JPanel(new GridBagLayout());
        jpstu = new JPanel(new GridBagLayout());
        jptop = new JPanel(new GridBagLayout());
        jpgam = new JPanel(new GridBagLayout());

        panel1.setPreferredSize(new Dimension(basketw,sharkStartFrame.screenSize.height));
        panel1.setMinimumSize(new Dimension(basketw,sharkStartFrame.screenSize.height));

 //       paneltopics.setVisible(false);
        jptop.setVisible(false);
 //       panelgames.setVisible(false);
        jpgam.setVisible(false);
        panelright.setBackground(Color.white);
        panelstu.setBackground(Color.white);
        panelstepped.setBackground(Color.white);
        paneltopics.setBackground(Color.white);
        panelgames.setBackground(Color.white);
        panelright.setOpaque(true);
        panelstepped.setOpaque(true);
        panelstu.setOpaque(true);
        paneltopics.setOpaque(true);
        panelgames.setOpaque(true);
        panelright.setPreferredSize(new Dimension(basketrest,sharkStartFrame.screenSize.height));
        panelright.setMinimumSize(new Dimension(basketrest,sharkStartFrame.screenSize.height));

        grid1.weightx = grid1.weighty = 1;
        grid1.fill = GridBagConstraints.BOTH;

        grid1.gridx = 0;
        grid1.gridy = -1;
        grid1.weightx = 1;
        grid1.weighty = 1;
        JPanel pnrightlabel = new JPanel(new GridBagLayout());
        lbTopStu = new mlabel_base("");

        if(isstepped) lbTopStu.setText(u.gettext("simprog_", "steppedmess"));
        else lbTopStu.setText(u.gettext("simprog_", "stumess"));
        lbTopStu.setOpaque(true);
 //       lbTopStu.setBackground(Color.blue);
 //       lbTopStu.setForeground(Color.yellow);
        lbTopStu.setFont(plainfont);
         lbTopStu.setBackground(cream);
        lbTopStu.setForeground(blk);


        lbTopTop = new mlabel_base("");
        lbTopTop.setText(u.gettext("simprog_", "listmess"));
        lbTopTop.setOpaque(true);
  //      lbTopTop.setBackground(Color.blue);
  //      lbTopTop.setForeground(Color.yellow);
         lbTopTop.setBackground(cream);
         lbTopTop.setFont(plainfont);
        lbTopTop.setForeground(blk);

        lbTopTop.setVisible(false);

        lbTopGam = new mlabel_base("");
        lbTopGam.setText(u.gettext("simprog_", "gammess"));
        lbTopGam.setOpaque(true);
   //     lbTopGam.setBackground(Color.blue);
   //     lbTopGam.setForeground(Color.yellow);

         lbTopGam.setBackground(cream);
         lbTopGam.setFont(plainfont);
        lbTopGam.setForeground(blk);
        lbTopGam.setVisible(false);

        pnrightlabel.add(lbTopStu, grid1);
        pnrightlabel.add(lbTopTop, grid1);
        pnrightlabel.add(lbTopGam, grid1);



        grid1.weightx = 1;
        grid1.weighty = 1;
        grid1.gridx = -1;
        grid1.gridy = 0;
        grid1.fill = GridBagConstraints.BOTH;
//        JPanel topbuttonpan = new JPanel(new GridBagLayout());
        JPanel topmainpan = new JPanel(new GridBagLayout());

        topmainpan.setBorder(BorderFactory.createLineBorder(Color.green.darker(), mpanborderw));

        JPanel topmainchoosepan = new JPanel(new GridBagLayout());
        topmainlistpan = new JPanel(new GridBagLayout());



        topmainlistpan.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        topmainlistpan.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        emptylistpan = new JPanel(new GridBagLayout());
        emptylistpan.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        emptylistpan.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        emptylistpan.setOpaque(true);
        emptylistpan.setBackground(Color.white);
        
        JTextPane emptylistpan_messtp = new u.textpane();
        emptylistpan_messtp.setEditable(false);
        emptylistpan_messtp.setContentType("text/html");
        emptylistpan_messtp.setCaretPosition(0);
        emptylistpan_messtp.setSelectedTextColor(null);
        emptylistpan_messtp.setSelectionColor(wordlist.bgcoloruse);
        
        StringBuffer style = new StringBuffer("font-family:" + sharkStartFrame.treefont.getFamily() + ";");
        style.append("font-weight:" + (sharkStartFrame.treefont.isBold() ? "bold" : "normal") + ";");
        style.append("font-size:" + "20pt;"); 
        style.append("color: Gray;"); 
        String ss = "<html><body align=center style=\"" + style + "\">"
                + u.convertToInnerHtml(u.gettext("steppedprogram", "noheadings"))
                + "</body></html>";      
        emptylistpan_messtp.setText(ss);
        emptylistpan_mess = new JPanel(new GridBagLayout());
        emptylistpan_mess.setBackground(Color.white);
        emptylistpan_mess.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        emptylistpan_mess.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width*1/11,sharkStartFrame.screenSize.height));
        
        grid1.fill = GridBagConstraints.HORIZONTAL;
        emptylistpan_mess.add(emptylistpan_messtp, grid1);
        grid1.fill = GridBagConstraints.BOTH;
        setTopicPanel(0);
 //       topmainlistpan.setMaximumSize(new Dimension(sharkStartFrame.screenSize.width*2/11,sharkStartFrame.screenSize.height));

        topmainpan.setBackground(Color.white);
        topmainpan.setOpaque(true);

        topics = new topicTree();
        topics.onlyOneDatabase = "*";
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
        topics.setEditable(false);
        topics.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setRootVisible(false);
        topics.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),false,db.TOPICTREE,true,alltopics);

     //   setupTree(String course, jnode currsel)
//        topics.setVisible(false);

        if(sp.it[0].topics.length!=0){
            if(!shark.phonicshark)
                phonics.setSelected(sp.it[0].phonics);
            topicsjlist.setListData(spellchange.spellchange(edit(sp.it[0].topics,topics,sp.it[0].mixed)));
        }          

        courseList = new topicTree();
        courseList.onlyOneDatabase = "*";
        
        courseList.setEditable(false);
        courseList.setRootVisible(false);
        courseList.root.setIcon(jnode.ROOTTOPICTREE);
        courseList.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),false,
            db.TOPICTREE,true,topicTree.MODE_EXPAND_NONE,alltopics);
        String hidden[] = settings.getUniversalHiddenCourses();
        for(int k = courseList.root.getChildCount()-1; k >=0; k--){
            String jnos = ((jnode)courseList.root.getChildAt(k)).get().trim();
            if(jnos.equals("") || (u.findString(hidden, jnos)>=0))
            courseList.root.remove(k);
        }
        courseList.model.reload();
        String sa[] = new String[]{};
        for(int k = courseList.root.getChildCount()-1; k >=0; k--){
           sa = u.addString(sa, ((jnode)courseList.root.getChildAt(k)).get().trim(), 0);
        }
        courseJList = new JList();
        courseJList.setListData(sa);
        courseJList.setCellRenderer(new listpainter(jnode.icons[jnode.COURSE]));
        courseJList.setForeground(Color.blue);
        courseJList.setFont(courseList.getFont());
        courseJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        grid1.gridx = 0;
        grid1.gridy = -1;
        JLabel lbchoose1 = new JLabel(u.gettext("simprog_", "selcourse"));
        lbchoose1.setBackground(toplabelbg);
        lbchoose1.setForeground(toplabelfg);
        lbchoose1.setOpaque(true);
       

        JPanel coursep = new JPanel(new GridBagLayout());
        coursep.setBorder(BorderFactory.createBevelBorder(1));
         grid1.weighty = 0;
         grid1.insets = new Insets(0,0,0,0);
        coursep.add(lbchoose1, grid1);
        grid1.weighty = 0;
        coursep.add(courseJList, grid1);
        grid1.insets = new Insets(0,0,basketmargin,0);
        grid1.weighty = 0;
        topmainchoosepan.add(coursep, grid1);




        grid1.insets = new Insets(0,0,0,0);
        JLabel lbchoose2 = new JLabel(u.gettext("simprog_", "sellist"));
        lbchoose2.setBackground(toplabelbg);
        lbchoose2.setForeground(toplabelfg);
        lbchoose2.setOpaque(true);
        grid1.weighty = 0;
        topmainchoosepan.add(lbchoose2, grid1);
        grid1.weighty = 1;
        whitetopp = new JPanel();
        topscroller = u.uScrollPane(topics);
        setTopicTreePanel(0); 
        whitetopp.setBackground(Color.white);
        whitetopp.setOpaque(true);
        topmainchoosepan.add(whitetopp, grid1);
        topmainchoosepan.add(topscroller, grid1);
        

        


        grid1.weighty = 1;  
        grid1.gridx = -1;
        grid1.gridy = 0;
        topmainpan.setBackground(Color.green.darker());
        topmainpan.setOpaque(true);
        topmainchoosepan.setBackground(Color.green.darker());
        topmainchoosepan.setOpaque(true);


        topmainpan.add(topmainchoosepan, grid1);
        grid1.insets = new Insets(0,basketmargin,0,0);
        topmainpan.add(topmainlistpan, grid1);
        topmainpan.add(emptylistpan, grid1);
        topmainpan.add(emptylistpan_mess, grid1);
        
        
        grid1.gridx = 0;
        grid1.gridy = -1;
        grid1.weighty = 1;
        grid1.weightx = 1;
        
        JPanel steppedcontent = new JPanel(new GridBagLayout());
        JPanel spinnercontent = new JPanel(new GridBagLayout()); 
        JPanel messpanel = new JPanel(new GridBagLayout());
        spinnercontent.setBackground(Color.white);
        spinnercontent.setOpaque(true);

        Dimension d =new Dimension(basketrest,sharkStartFrame.screenSize.height/8);
        messpanel.setPreferredSize(d);
        messpanel.setMinimumSize(d);
        messpanel.setBackground(sharkStartFrame.col2);
        messpanel.setOpaque(true);

        JTextArea steppedmessta = new JTextArea();
        steppedmessta.setEditable(false);
        steppedmessta.setWrapStyleWord(true);
        steppedmessta.setLineWrap(true);
        steppedmessta.setOpaque(false);
        steppedmessta.setText(u.convertToCR(u.gettext("steppedprogram", "mess")));
        steppedmessta.setFont(plainfont);

        int rech = d.height*4/5;
        int hgap = (d.height/5)/2;
        int gap = (rech/8)+hgap;
        grid1.insets = new Insets(0,gap,0,0);
        grid1.fill = GridBagConstraints.HORIZONTAL;
        messpanel.add(steppedmessta, grid1);
        grid1.fill = GridBagConstraints.BOTH;
        grid1.insets = new Insets(0,0,0,0);
        steppedcontent.add(spinnercontent, grid1);
        grid1.weighty = 0;
        steppedcontent.add(messpanel, grid1);
        
        JPanel pnToComplete = new JPanel(new GridBagLayout());
        JPanel pnErrors = new JPanel(new GridBagLayout());
        JLabel lbToComplete = new JLabel(u.convertToHtml(u.gettext("steppedprogram", "tocomplete")));
        JLabel lbErrors = new JLabel(u.convertToHtml(u.gettext("steppedprogram", "errorsallowed")));
        lbToComplete.setFont(plainfont);
        lbErrors.setFont(plainfont);
        spincomplete =  new JSpinner(new SpinnerNumberModel(1,1,10,1));
        spinerrors =  new JSpinner(new SpinnerNumberModel(2,0,10,1));
        Dimension spinnerdim = new Dimension(((sharkStartFrame.screenSize.height/14)), ((sharkStartFrame.screenSize.height/6)/3));
        spincomplete.setPreferredSize(spinnerdim);
        spincomplete.setMinimumSize(spinnerdim);
        spinerrors.setPreferredSize(spinnerdim);
        spinerrors.setMinimumSize(spinnerdim);

        JSpinner.DefaultEditor de = ((JSpinner.DefaultEditor)spincomplete.getEditor());
        JTextField tf = de.getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.white);
        tf.setOpaque(true);
        tf.setFont(tf.getFont().deriveFont((float)tf.getFont().getSize()+4));
        de = ((JSpinner.DefaultEditor)spinerrors.getEditor());
        tf = de.getTextField();
        tf.setEditable(false);
        tf.setBackground(Color.white);
        tf.setOpaque(true);
        tf.setFont(tf.getFont().deriveFont((float)tf.getFont().getSize()+4));

        spincomplete.addChangeListener(new ChangeListener() {
           public void stateChanged(ChangeEvent e) {
               int step = steppedjlist.getSelectedIndex();
               SpinnerNumberModel mod = (SpinnerNumberModel)spincomplete.getModel();
               sp.it[step].mustcomplete = (short)mod.getNumber().intValue();
           }
         }
        );
        spinerrors.addChangeListener(new ChangeListener() {
           public void stateChanged(ChangeEvent e) {
               int step = steppedjlist.getSelectedIndex();
               SpinnerNumberModel mod = (SpinnerNumberModel)spinerrors.getModel();
               sp.it[step].maxerrors = (short)mod.getNumber().intValue();
           }
         }
        );




        grid1.gridx = -1;
        grid1.gridy = 0;
        grid1.fill = GridBagConstraints.NONE;

        int spinmargin = 20;
        grid1.anchor = GridBagConstraints.WEST;
        grid1.insets = new Insets(0,spinmargin,0,0);
        pnToComplete.add(lbToComplete, grid1);
        grid1.anchor = GridBagConstraints.EAST;
        grid1.insets = new Insets(0,0,0,spinmargin);
        pnToComplete.add(spincomplete, grid1);
        grid1.anchor = GridBagConstraints.WEST;
        grid1.insets = new Insets(0,spinmargin,0,0);
        pnErrors.add(lbErrors, grid1);
        grid1.anchor = GridBagConstraints.EAST;
        grid1.insets = new Insets(0,0,0,spinmargin);
        pnErrors.add(spinerrors, grid1);

        grid1.anchor = GridBagConstraints.CENTER;

        pnToComplete.setPreferredSize(new Dimension(basketrest/2, sharkStartFrame.screenSize.height/6));
        pnToComplete.setMinimumSize(new Dimension(basketrest/2, sharkStartFrame.screenSize.height/6));
        pnErrors.setPreferredSize(new Dimension(basketrest/2, sharkStartFrame.screenSize.height/6));
        pnErrors.setMinimumSize(new Dimension(basketrest/2, sharkStartFrame.screenSize.height/6));
        pnToComplete.setBorder(BorderFactory.createLineBorder(Color.darkGray));
        pnErrors.setBorder(BorderFactory.createLineBorder(Color.darkGray));

        grid1.anchor = GridBagConstraints.NORTHWEST;
        grid1.weighty = 0;
        grid1.gridx =0;
        grid1.gridy = -1;
        
        grid1.fill = GridBagConstraints.NONE;
        grid1.insets = new Insets(gap,gap,gap,0);
        spinnercontent.add(pnToComplete, grid1);
        grid1.insets = new Insets(0,gap,0,0);
        spinnercontent.add(pnErrors, grid1);
        grid1.insets = new Insets(0,0,0,0);
        grid1.weighty = 1;
        grid1.fill = GridBagConstraints.BOTH;
        JPanel blankpan = new JPanel(new GridBagLayout());
        blankpan.setOpaque(false);
        spinnercontent.add(blankpan, grid1);
        grid1.anchor = GridBagConstraints.CENTER;

        grid1.gridx = 0;
        grid1.gridy = -1;
        grid1.fill = GridBagConstraints.BOTH;



        grid1.insets = new Insets(0,0,0,0);
        grid1.fill = GridBagConstraints.BOTH;
        grid1.weighty = 1;
        paneltopics.add(topmainpan, grid1);
 //       grid1.weighty = 0;
 //       paneltopics.add(topbuttonpan, grid1);


        grid1.weighty = 1;
        grid1.fill = GridBagConstraints.BOTH;
        panelstu.add(u.uScrollPane(studenttc), grid1);
        panelstepped.add(steppedcontent, grid1);
        
        
       
        gameiconpanel = new runMovers();
        gameiconpanel.setPreferredSize(new Dimension(basketrest,sharkStartFrame.screenSize.height/8));
        gameiconpanel.setMinimumSize(new Dimension(basketrest,sharkStartFrame.screenSize.height/8));
        grid1.weighty = 1;
        panelgames.add(u.uScrollPane(games), grid1);
        grid1.weighty = 0;
        panelgames.add(gameiconpanel, grid1);


        

        gameiconpanel.clearWholeScreen = true;
        gameiconpanel.start1();
        gameiconpanel.startrunning();
        
        grid1.weighty = 1;
        grid1.weighty = 0;
        panelright.add(pnrightlabel,grid1);
        grid1.weighty = 1;


       if(isstepped){
           rearrangeTimer = (new javax.swing.Timer(1500, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rearrangeTimer.stop();
                rearrangesteps();

             }
         }));
         rearrangeTimer.setRepeats(false);
         jpstu.setVisible(false);
       }
       else jpstepped.setVisible(false);

        grid1.fill = GridBagConstraints.BOTH;
        grid1.weighty = 1;
        grid1.weightx = 1;
         grid1.gridy = -1;
        grid1.gridx = 0;
        grid1.insets = new Insets(basketmargin,0,basketmargin,basketmargin);
        jpstu.add(panelstu, grid1);
        jpstepped.add(panelstepped, grid1);
        jptop.add(paneltopics, grid1);
        jpgam.add(panelgames, grid1);

        grid1.insets = new Insets(0,0,0,0);
        if(isstepped)
            panelright.add(jpstepped,grid1);
        panelright.add(jpstu,grid1);
        panelright.add(jptop,grid1);
        panelright.add(jpgam,grid1);

        panelgames.setBorder(BorderFactory.createLineBorder(sharkStartFrame.gamescolor, mpanborderw));
        panelstu.setBorder(BorderFactory.createLineBorder(stucolor, mpanborderw));
        panelstepped.setBorder(BorderFactory.createLineBorder(steppedcol, mpanborderw));

        grid2.weightx = grid2.weighty = 1;
        grid1.gridx = -1;
        grid1.gridy = 0;
//        getContentPane().add(panel1d,grid1);
        getContentPane().add(panel1,grid1);
        getContentPane().add(panelright,grid1);

        grid2.weighty = 1;
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.fill = GridBagConstraints.BOTH;
        JPanel pnBasket = new JPanel(new GridBagLayout());

        grid2.insets = new Insets(0,0,basketmargin,0);
        pnBasket.add(panel1a,grid2);
        pnBasket.add(panel1b,grid2);
        pnBasket.add(panel1c,grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.weighty = 0;
        pnBasket.add(panel1d,grid2);
        

        gamesjlist.setEnabled(false);
        topicsjlist.setEnabled(false);
        gamesjlist.clearSelection();
        topicsjlist.clearSelection();


        panel1a.setBorder(BorderFactory.createEtchedBorder());
        panel1b.setBorder(BorderFactory.createEtchedBorder());
        panel1c.setBorder(BorderFactory.createEtchedBorder());
//        panel1.setBorder(BorderFactory.createEtchedBorder());

        mlabel_base lbBasket = new mlabel_base("");
        lbBasket.setText(u.gettext("simprog_", "clickbasket")+"|  ");

        lbBasket.setBackground(cream);
        lbBasket.setFont(plainfont);
        lbBasket.setForeground(blk);
        lbBasket.setOpaque(true);

        panel1.add(lbBasket,grid2);
        grid2.weighty = 1;
        grid2.insets = new Insets(basketmargin,basketmargin,basketmargin,basketmargin);
        panel1.add(pnBasket,grid2);
        grid2.insets = new Insets(0,0,0,0);
        
        defaultpanel = panel1a.getBackground();
        panel1a.setBackground(isstepped?steppedcol:stucolor);
        if(!shark.phonicshark){
            phonics.setForeground(Color.white);
            phonics.setText(u.gettext("simprog_", "phonicson"));
        }
        pnlbstu.setOpaque(true);
        pnlbstepped.setOpaque(true);
        pnlbtop.setOpaque(true);
        pnlbgam.setOpaque(true);
        pnlbstu.setBackground(stucolor);
        pnlbstepped.setBackground(steppedcol);
        pnlbtop.setBackground(defaultpanel);
        pnlbgam.setBackground(defaultpanel);


        Dimension dim2 = new Dimension(basketw, sharkStartFrame.screenSize.height*7/20);
        Dimension dim3 = new Dimension(basketw, sharkStartFrame.screenSize.height*6/20);
        panel1a.setPreferredSize(dim3);
        panel1a.setMinimumSize(dim3);
        panel1b.setPreferredSize(dim2);
        panel1b.setMinimumSize(dim2);
        panel1c.setPreferredSize(dim2);
        panel1c.setMinimumSize(dim2);


        int sw = sharkStartFrame.mainFrame.getWidth();
        int buttondim = (sw*14/22)/24;
        int buttonimdim =  buttondim- (buttondim/5);

//ImageIcon warn = new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +
//                                       sharkStartFrame.separator +
//                                       "warn.jpg");
        Image list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "deleteON_il48.png");

ImageIcon crossactive = new ImageIcon(list.getScaledInstance( buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));





        list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "warning_il48.png");
        ImageIcon warn = new ImageIcon(list.getScaledInstance( buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));

        list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "search_il48.png");
        ImageIcon search = new ImageIcon(list.getScaledInstance( buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));

        
        
        list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "up_il48.png");
        ImageIcon iiup = new ImageIcon(list.getScaledInstance( buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
        list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "down_il48.png");
        ImageIcon iidown = new ImageIcon(list.getScaledInstance( buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
        list = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "add_il48.png");
        ImageIcon iiadd = new ImageIcon(list.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));


        Dimension dim = new Dimension(buttondim ,buttondim );

        if(!shark.phonicshark)
            phonics.setEnabled(false);
        grid2.insets = new Insets(basketmargin,basketmargin,basketmargin,basketmargin);
        
        pnlbstu.add(btStudents, grid2);
        pnlbstepped.add(btStepped, grid2);
        pnlbtop.add(btTopics, grid2);
        pnlbgam.add(btGames, grid2);
        grid2.insets = new Insets(0,0,0,0);


        spStepped = u.uScrollPane(steppedjlist);
        spStu = u.uScrollPane(stujlist);
        spTop = u.uScrollPane(topicsjlist);
        spGam = u.uScrollPane(gamesjlist);


        lbStartTop.setForeground(Color.white);
        lbStartTop.setBackground(Color.white);
        lbStartTop.setHorizontalAlignment( SwingConstants.CENTER );
        lbStartTop.setOpaque(true);
        lbStartTop.setFont(lbStartTop.getFont().deriveFont((float)20));
        lbStartTop.setText(u.gettext("simprog_", "listdrag"));


//        lbStartGam.setForeground(Color.white);
        lbStartGam.setBackground(Color.white);
        lbStartGam.setHorizontalAlignment( SwingConstants.CENTER );
        lbStartGam.setOpaque(true);
        lbStartGam.setFont(lbStartTop.getFont().deriveFont((float)20));
        lbStartGam.setForeground(Color.lightGray);

        JPanel pnInnerStu = new JPanel(new GridBagLayout());
        JPanel pnInnerStepped = new JPanel(new GridBagLayout());
        grid2.insets = new Insets(0,basketmargin,basketmargin,basketmargin);
        if(!isstepped)
            panel1a.add(pnInnerStu, grid2);
        else panel1a.add(pnInnerStepped, grid2);
        pnInnerStu.setOpaque(false);
        pnInnerStepped.setOpaque(false);
        grid2.insets = new Insets(0,0,0,0);
        grid2.weighty = 0;
        grid2.anchor = GridBagConstraints.WEST;
        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.insets = new Insets(0,0,0,dim.width+basketmargin);
        pnInnerStu.add(pnlbstu, grid2);
        pnInnerStepped.add(pnlbstepped, grid2);
        grid2.fill = GridBagConstraints.NONE;
        grid2.insets = new Insets(0,0,0,0);
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 1;
        JPanel pnListStu = new JPanel(new GridBagLayout());
        JPanel pnListStepped = new JPanel(new GridBagLayout());
        pnListStu.setOpaque(false);
        pnListStepped.setOpaque(false);
        grid2.weightx = 1;
        grid2.gridx = -1;
        grid2.gridy = 0;
        pnListStu.add(spStu, grid2);
        pnListStepped.add(spStepped, grid2);
        grid2.fill = GridBagConstraints.NONE;
        grid2.weightx = 0;
        grid2.anchor = GridBagConstraints.NORTH;
        grid2.insets = new Insets(0,basketmargin,0,0);
        btopStu  = u.sharkButton();
        btopStepped  = u.sharkButton();


        baddStepped  = u.sharkButton();

        bupStepped  = u.sharkButton();

        bdownStepped  = u.sharkButton();

        btopStu.setPreferredSize(dim);
        btopStu.setMinimumSize(dim);
        btopStu.setIcon(crossactive);
        btopStu.setToolTipText(u.gettext("simprog_", "delstutooltip"));
        btopStepped.setPreferredSize(dim);
        btopStepped.setMinimumSize(dim);
        btopStepped.setIcon(crossactive);
  //      btopStepped.setToolTipText(u.gettext("simprog_", "delstutooltip"));

        baddStepped.setPreferredSize(dim);
        baddStepped.setMinimumSize(dim);
        baddStepped.setIcon(iiadd);
//        baddStepped.setToolTipText(u.gettext("simprog_", "delstutooltip"));

        bupStepped.setPreferredSize(dim);
        bupStepped.setMinimumSize(dim);
        bupStepped.setIcon(iiup);
 //       bupStepped.setToolTipText(u.gettext("simprog_", "delstutooltip"));


        bdownStepped.setPreferredSize(dim);
        bdownStepped.setMinimumSize(dim);
        bdownStepped.setIcon(iidown);
  //      bdownStepped.setToolTipText(u.gettext("simprog_", "delstutooltip"));






         grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.fill = GridBagConstraints.NONE;
        grid2.weightx = 0;
        grid2.anchor = GridBagConstraints.NORTH;
        JPanel topstupan = new JPanel(new GridBagLayout());
        topstupan.setOpaque(false);
        grid2.insets = new Insets(0,0,0,0);
        if(isstepped){
            topstupan.add(baddStepped, grid2);
         }
        if(isstepped){
  //          grid2.insets = new Insets(basketmargin,0,0,0);
            grid2.insets = new Insets(basketmargin,0,0,0);
            topstupan.add(btopStepped, grid2);
         }
        else
           topstupan.add(btopStu, grid2);
        if(isstepped){
    //        grid2.insets = new Insets(basketmargin,0,0,0);
            topstupan.add(bupStepped, grid2);
    //        grid2.insets = new Insets(basketmargin,0,0,0);
            topstupan.add(bdownStepped, grid2);
         }



         grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.insets = new Insets(0,basketmargin,0,0);

        if(!isstepped)pnListStu.add(topstupan, grid2);
        else pnListStepped.add(topstupan, grid2);


        grid2.insets = new Insets(0,0,0,0);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weightx = 0;
        grid2.gridx = 0;
        grid2.gridy = -1;    
        if(!isstepped)pnInnerStu.add(pnListStu, grid2);
         else pnInnerStepped.add(pnListStepped, grid2);
        grid2.weightx = 1;





        JPanel pnInnerTop = new JPanel(new GridBagLayout());
        grid2.insets = new Insets(0,basketmargin,basketmargin,basketmargin);
        panel1b.add(pnInnerTop, grid2);
        pnInnerTop.setOpaque(false);
        grid2.weighty = 0;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.anchor = GridBagConstraints.WEST;
        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.insets = new Insets(0,0,0,dim.width+basketmargin);
        pnInnerTop.add(pnlbtop, grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.weighty = 1;
        JPanel pnListTop = new JPanel(new GridBagLayout());
        pnListTop.setOpaque(false);
        grid2.weightx = 1;
        grid2.gridx = -1;
        grid2.gridy = 0;
        pnListTop.add(spTop, grid2);
        pnListTop.add(lbStartTop, grid2);
        grid2.fill = GridBagConstraints.NONE;
        grid2.weightx = 0;
        grid2.anchor = GridBagConstraints.NORTH;
        
        btopTop = u.sharkButton();
        btopTop.setPreferredSize(dim);
        btopTop.setMinimumSize(dim);
        btopTop.setIcon(crossactive);
        btopTop.setEnabled(false);
        btopTop.setToolTipText(u.gettext("simprog_", "dellisttooltip"));


        program.nogames.setPreferredSize(dim);
        program.nogames.setMinimumSize(dim);
        program.nogames.setIcon(warn);
        program.nogames.setEnabled(false);


        btopSearch = u.sharkButton();//new JButton();
        btopSearch.setPreferredSize(dim);
        btopSearch.setMinimumSize(dim);
        btopSearch.setIcon(search);
        btopSearch.setEnabled(false);
        btopSearch.setToolTipText(u.gettext("simprog_", "search"));






        grid2.gridx = 0;
        grid2.gridy = -1;
        JPanel topbutpan = new JPanel(new GridBagLayout());
        topbutpan.setOpaque(false);
        topbutpan.add(btopTop, grid2);
        grid2.insets = new Insets(basketmargin,0,0,0);
        topbutpan.add(btopSearch, grid2);
        grid2.gridx = -1;
        grid2.gridy = 0;


        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.insets = new Insets(0,basketmargin,0,0);
        pnListTop.add(topbutpan, grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weightx = 0;
        grid2.gridx = 0;
        grid2.gridy = -1;
        pnInnerTop.add(pnListTop, grid2);
        grid2.weightx = 1;
        if(topicsjlist.getModel().getSize()>0)
            lbStartTop.setVisible(false);
        else
            spTop.setVisible(false);

        grid2.weighty = 0;

        grid2.fill = GridBagConstraints.NONE;
        grid2.anchor = GridBagConstraints.WEST;
        if(!shark.phonicshark){
            phonics.setOpaque(true);
            grid2.insets = new Insets(basketmargin,0,0,0);
            pnInnerTop.add(phonics, grid2);
            grid2.insets = new Insets(0,0,0,0);
            phonics.setBackground(Color.green.darker().darker());
        }
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty = 1;

        JPanel pnInnerGames = new JPanel(new GridBagLayout());
        grid2.insets = new Insets(0,basketmargin,basketmargin,basketmargin);
        panel1c.add(pnInnerGames, grid2);
        pnInnerGames.setOpaque(false);
        grid2.weighty = 0;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.anchor = GridBagConstraints.WEST;
        grid2.gridx = -1;
        grid2.gridy = 0;
        grid2.insets = new Insets(0,0,0,dim.width+basketmargin);
        pnInnerGames.add(pnlbgam, grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.weighty = 1;
        JPanel pnListGam = new JPanel(new GridBagLayout());
        pnListGam.setOpaque(false);
        grid2.weightx = 1;
        grid2.gridx = -1;
        grid2.gridy = 0;
        pnListGam.add(spGam, grid2);
        pnListGam.add(lbStartGam, grid2);
        grid2.fill = GridBagConstraints.NONE;
        grid2.weightx = 0;
        grid2.anchor = GridBagConstraints.NORTH;
        grid2.insets = new Insets(0,0,0,0);
        btopGam  = u.sharkButton();
        btopGam.setPreferredSize(dim);
        btopGam.setMinimumSize(dim);
        btopGam.setIcon(crossactive);
        btopGam.setEnabled(false);
        btopGam.setToolTipText(u.gettext("simprog_", "delgametooltip"));

        grid2.gridx = 0;
        grid2.gridy = -1;
        JPanel gambutpan = new JPanel(new GridBagLayout());
        gambutpan.setOpaque(false);
        gambutpan.add(btopGam, grid2);
        grid2.insets = new Insets(basketmargin,0,0,0);
        gambutpan.add(program.nogames, grid2);
        grid2.gridx = -1;
        grid2.gridy = 0;

        grid2.insets = new Insets(0,basketmargin,0,0);
        pnListGam.add(gambutpan, grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.anchor = GridBagConstraints.CENTER;
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weightx = 0;
        grid2.gridx = 0;
        grid2.gridy = -1;
        pnInnerGames.add(pnListGam, grid2);
        grid2.weightx = 1;
        
        setgames();
        stujlist.clearSelection();
        gamesjlist.clearSelection();
        lbStartGam.setText(strdefaultgames);
        if(gamesjlist.getModel().getSize()>0){
            lbStartGam.setVisible(false);
            btopGam.setEnabled(false);
            program.nogames.setEnabled(false);
        }
        else
            spGam.setVisible(false);

        pnlbtop.setOpaque(true);


        panel1a.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
           pnlbstu.setBackground(stucolor);
           pnlbstepped.setBackground(steppedcol);
           btStepped.setForeground(Color.white);
           btStudents.setForeground(Color.white);
           if(!jptop.isVisible()){
            pnlbtop.setBackground(defaultpanel);
            btTopics.setForeground(Color.black);
           }
           if(!jpgam.isVisible()){
            pnlbgam.setBackground(defaultpanel);
            btGames.setForeground(Color.black);
            }
            }
            public void mouseExited(MouseEvent me) {
                if(isstepped){
                     if(!panel1a.contains(me.getPoint()) && !jpstepped.isVisible()){
                         pnlbstepped.setBackground(defaultpanel);
                         btStepped.setForeground(Color.black);
                    }
                }
                else{
                     if(!panel1a.contains(me.getPoint()) && !jpstu.isVisible()){
                         pnlbstu.setBackground(defaultpanel);
                         btStudents.setForeground(Color.black);
                    }
                }
            }
            public void mouseReleased(MouseEvent me) {
                if(isstepped) showsteppedpanel();
                else showstupanel();
            }
          });
     panel1b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                if(!isstepped){
                    if(!jpstu.isVisible()){
                            pnlbstu.setBackground(defaultpanel);
                         btStudents.setForeground(Color.black);
                    }
                }
                else{
                     if(!jpstepped.isVisible()){
                            pnlbstepped.setBackground(defaultpanel);
                         btStepped.setForeground(Color.black);
                    }
                }
           pnlbtop.setBackground(Color.green.darker());
           btTopics.setForeground(Color.white);
           if(!jpgam.isVisible()){
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);
           }
            }
            public void mouseExited(MouseEvent me) {
                if(!panel1b.contains(me.getPoint()) && !jptop.isVisible()){
                    pnlbtop.setBackground(defaultpanel);
                    btTopics.setForeground(Color.black);
                }
            }
            public void mouseReleased(MouseEvent me) {
                showtpanel();
            }
          });

     panel1c.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                if(!isstepped){
                if(!jpstu.isVisible()){
                    pnlbstu.setBackground(defaultpanel);
                        btStudents.setForeground(Color.black);
                }
                }
 else{
                 if(!jpstepped.isVisible()){
                    pnlbstepped.setBackground(defaultpanel);
                        btStepped.setForeground(Color.black);
                }
 }
                if(!jptop.isVisible()){
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
                }
           pnlbgam.setBackground(sharkStartFrame.gamescolor);
           btGames.setForeground(Color.white);
            }
            public void mouseExited(MouseEvent me) {
                if(!panel1c.contains(me.getPoint()) && !jpgam.isVisible()){
                    pnlbgam.setBackground(defaultpanel);
                    btGames.setForeground(Color.black);
                }
            }
            public void mouseReleased(MouseEvent me) {
                showgpanel(null);
            }
          });

     
        btopStu.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              if(sp.students.length>0)  delstuline();
     //         requestFocus();
          }
       });
         btopStepped.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               showsteppedpanel();
               int sel = steppedjlist.getSelectedIndex();
               if (sel < 0)return;              
               int tot = steppedjlist.getModel().getSize();
               String s[] = new String[]{};
               for(int i = 0; i < tot; i++){
                  if(i!=sel)s = u.addString(s, (String)steppedjlist.getModel().getElementAt(i));
               }
               steppedjlist.setListData(s);
               steppedjlist.setSelectedIndex(Math.max(0, sel-1));
               programitem news[] = new programitem[sp.it.length-1];
               System.arraycopy(sp.it,0,news,0,sel);
               System.arraycopy(sp.it,sel+1,news,sel,sp.it.length-sel-1);
               sp.it = news;
              if(rearrangeTimer.isRunning())
                  rearrangeTimer.restart();
              else
                  rearrangeTimer.start();

          }
       });
       baddStepped.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               showsteppedpanel();
              int tot = steppedjlist.getModel().getSize();
              String s[] = new String[]{};
              for(int i = 0; i < tot; i++){
                  s = u.addString(s, (String)steppedjlist.getModel().getElementAt(i));
              }
              s = u.addString(s, stepitem+" "+ String.valueOf(tot+1));
              newstep(steppedjlist.getModel().getSize());
              steppedjlist.setListData(s);
              steppedjlist.setSelectedIndex(tot);


              spincomplete.setValue(new Integer(1));
              spinerrors.setValue(new Integer(2));


              
          }
       });
         bdownStepped.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               showsteppedpanel();
               int sel = steppedjlist.getSelectedIndex();
               String selval = (String)steppedjlist.getSelectedValue();
               int tot = steppedjlist.getModel().getSize();
               if (sel < 0 || sel >= tot-1)return;
              String s[] = new String[]{};
              for(int i = 0; i < tot; i++){
                  if(i!=sel) s = u.addString(s, (String)steppedjlist.getModel().getElementAt(i));
              }
              s = u.addString(s, selval, sel+1);
              steppedjlist.setListData(s);
              steppedjlist.setSelectedIndex(sel+1);
              rearrangestepsinprogram(sel, sel+1);
              if(rearrangeTimer.isRunning())
                  rearrangeTimer.restart();
              else
                  rearrangeTimer.start();
          }
       });
         bupStepped.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               showsteppedpanel();
               int sel = steppedjlist.getSelectedIndex();
               String selval = (String)steppedjlist.getSelectedValue();
               int tot = steppedjlist.getModel().getSize();
               if (sel <= 0 || sel > tot-1)return;
              String s[] = new String[]{};
              for(int i = 0; i < tot; i++){
                  if(i!=sel) s = u.addString(s, (String)steppedjlist.getModel().getElementAt(i));
              }
              s = u.addString(s, selval, sel-1);
              steppedjlist.setListData(s);
              steppedjlist.setSelectedIndex(sel-1);
              rearrangestepsinprogram(sel, sel-1);

              if(rearrangeTimer.isRunning())
                  rearrangeTimer.restart();
              else
                  rearrangeTimer.start();
          }
       });
        btopTop.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              int step = getStep();
              if(step<0)return;
              if(sp.it[step].topics.length>0){
                  deltopicline(step);
                  showtpanel();
               }
         //     requestFocus();
          }
       });
        btopGam.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              int step = getStep();
              if(step<0 || sp.it[step].games.length==0)return;
                delgameline(step);
          //    requestFocus();
          }
       });
       lbStartGam.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            if(dragger.droppedon != null && dragger.droppedon == lbStartGam
               && dragger.draggedfrom == games) {
              dragger.droppedon = dragger.draggedfrom = null;
              lbStartGam.setText(strdefaultgames);
              lbStartGam.setVisible(false);
              gamesfirstshow = false;
              spGam.setVisible(true);
              addgame();
            }
              showgpanel(null);
           }
       });
       lbStartTop.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            if(dragger.droppedon != null && dragger.droppedon == lbStartTop
               && dragger.draggedfrom == topics) {
              dragger.droppedon = dragger.draggedfrom = null;
              lbStartTop.setVisible(false);
              spTop.setVisible(true);
              if(isstepped){
                int step = steppedjlist.getSelectedIndex();
                if(step<0)return;
                  addtopic(step);
              }
              else addtopic();
            }
            else
             showtpanel();
           }
       });
    /*
        pnListStu.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showstupanel();
           }
       });
        pnInnerStu.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showstupanel();
           }
       });
        panel1a.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showstupanel();
           }
       });
       btStudents.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showstupanel();
           }
       });
       phonics.setSelected(wordlist.usephonics = false);//sp.it[0].phonics);
       phonics.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showtpanel();
           }
       });
        pnListTop.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showtpanel();
           }
       });
        pnInnerTop.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showtpanel();
           }
       });
        panel1b.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showtpanel();
           }
       });
       lbStartTop.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            if(dragger.droppedon != null && dragger.droppedon == lbStartTop
               && dragger.draggedfrom == topics) {
              dragger.droppedon = dragger.draggedfrom = null;
              lbStartTop.setVisible(false);
              spTop.setVisible(true);
              addtopic();
            }
            else
             showtpanel();
           }
       });
       btTopics.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showtpanel();
           }
       });

        pnListGam.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showgpanel(null);
           }
       });
        pnInnerGames.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showgpanel(null);
           }
       });
        panel1c.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showgpanel(null);
           }
       });
       lbStartGam.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            if(dragger.droppedon != null && dragger.droppedon == lbStartGam
               && dragger.draggedfrom == games) {
              dragger.droppedon = dragger.draggedfrom = null;
              lbStartGam.setText(strdefaultgames);
              lbStartGam.setVisible(false);
              gamesfirstshow = false;
              spGam.setVisible(true);
              addgame();
            }
              showgpanel(null);
           }
       });
       btGames.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
              showgpanel(null);
           }
       });


       */


/*
        btStudents.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             showstupanel();
          }
        });
        btTopics.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               showtpanel();
          }
        });
        btGames.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              showgpanel(null);
          }
        });
 */
//        panel1a.add(label1a,BorderLayout.NORTH);
//        panel1a.add(stupanel2,BorderLayout.CENTER);
//        panel1a.add(changestu,BorderLayout.WEST);
//        panel1a.add(u.uScrollPane(stujlist),BorderLayout.CENTER);
//        btStudents.setBackground(Color.red);
       btStudents.setForeground(Color.white);
       btStepped.setForeground(Color.white);
//        btTopics.setBackground(Color.green);
//        btTopics.setForeground(Color.b);
//        btGames.setBackground(sharkStartFrame.gamescolor);
//        btGames.setForeground(Color.white);
 //       btStudents.setOpaque(true);
//        btTopics.setOpaque(true);
//        btGames.setOpaque(true);

        btStudents.setBorder(BorderFactory.createEmptyBorder());
        btTopics.setBorder(BorderFactory.createEmptyBorder());
        btGames.setBorder(BorderFactory.createEmptyBorder());
//        panel1a.setBorder(BorderFactory.createLineBorder(Color.red,2));

//        panel1.add(panel1b,grid2);
//        panel1b.add(label1b,BorderLayout.NORTH);
//        panel1b.add(changetopic,BorderLayout.WEST);
//        panel1b.add(u.uScrollPane(topicsjlist),BorderLayout.CENTER);

//        panel1b.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor,2));

        grid2.weighty = 0;
 //       panel1.add(program.nogames,grid2);
        grid2.weighty = 1;
 //       program.nogames.setBackground(Color.red);
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.macOS)
 //         program.nogames.setForeground(Color.red);
 //       else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  //        program.nogames.setForeground(Color.white);
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //       program.nogames.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        program.nogames.setVisible(false);
        ActionListener al[] = program.nogames.getActionListeners();
        for(i=0;i<al.length;++i) program.nogames.removeActionListener(al[i]);
        program.nogames.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
//              if(panel2.isAncestorOf(gpanel)) panel2.remove(gpanel);
              showgpanel(program.nogamestopic);
              games.setSelectionPath(new TreePath(((jnode)games.root.getFirstLeaf()).getPath()));
          }
        });


//        panel1c.add(label1c,BorderLayout.NORTH);
//        panel1c.add(changegame,BorderLayout.WEST);
//        panel1c.add(u.uScrollPane(gamesjlist),BorderLayout.CENTER);

 //       panel1c.setBorder(BorderFactory.createLineBorder(sharkStartFrame.gamescolor,2));
 //       panel1.add(panel1c,grid2);
        grid2.weighty = 0;
 //       panel1.add(panel1d,grid2);

        gpanel.setBorder(BorderFactory.createLineBorder(sharkStartFrame.gamescolor,2));
        tpanel.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor,2));
        stupanel.setBorder(BorderFactory.createLineBorder(stucolor,2));
        steppedpanel.setBorder(BorderFactory.createLineBorder(steppedcol,2));

        grid2.weighty=0;
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.fill = GridBagConstraints.NONE;
        grid2.weightx=grid2.weighty = 1;
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(shark.macOS){
          grid1.insets = new Insets(0,0,0,basketmargin/2);
          panel1d.add(cancelbutton,grid1);
          grid1.insets = new Insets(0,basketmargin/2,0,0);
  //        grid1.insets = new Insets(0,0,0,0);
          panel1d.add(exitbutton,grid1);
        }
        else{
          grid1.insets = new Insets(0,0,0,basketmargin/2);
          panel1d.add(exitbutton,grid1);
          grid1.insets = new Insets(0,basketmargin/2,0,0);
  //        grid1.insets = new Insets(0,0,0,0);
          panel1d.add(cancelbutton,grid1);
        }
        grid1.insets = new Insets(0,0,0,0);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        grid2.weighty=1;
 //       stupanel.setBorder(BorderFactory.createLineBorder(Color.red,2));
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.weighty=0;
        grid2.gridx = 0;
        grid2.gridy=-1;
        stupanel.add(stulabel,grid2);
        steppedpanel.add(steppedlabel,grid2);
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty=0;
        grid2.fill = GridBagConstraints.NONE;
//        stupanel.add(addstu,grid2);
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.weighty=0;
        tpanel.add(tlabel,grid2);
        grid2.fill = GridBagConstraints.BOTH;
        grid2.weighty=20;
  //      tpanel.add(u.uScrollPane(topics),grid2);
        grid2.weighty=0;
        grid2.fill = GridBagConstraints.BOTH;
        if(!shark.phonicshark){
     //       phonics.setSelected(wordlist.usephonics = sp.it[0].phonics);
            tpanel.add(phlabel,grid2);
       //     tpanel.add(phonics,grid2);
            tpanel.add(hardsuper,grid2);
        }
        tpanel.add(tpanel2,grid2);
//startPR2005-01-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(shark.macOS){
//          tpanel2.add(searchbutton,grid1);
//          tpanel2.add(addtopic,grid1);
//        }
//        else{
//          tpanel2.add(addtopic,grid1);
//          tpanel2.add(searchbutton,grid1);
//        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!shark.phonicshark){
            hardsuper.setBackground(Color.orange.brighter());
     //       phonics.setBackground(Color.orange);
            phlabel.setBackground(Color.orange);
        }
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
        if (shark.macOS) {
          exitbutton.setForeground(Color.darkGray);
          cancelbutton.setForeground(Color.darkGray);
        }
        // if running on Windows
        else {
          exitbutton.setForeground(Color.white);
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          exitbutton.setBackground(Color.red);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          cancelbutton.setForeground(Color.white);
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          cancelbutton.setBackground(Color.red);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-02-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        exitbutton.setBackground(Color.gray);
        cancelbutton.setBackground(Color.gray);
        cancelbutton.setOpaque(true);
        exitbutton.setOpaque(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        savephonics = wordlist.usephonics;
        addWindowListener(new java.awt.event.WindowAdapter() {
           public void windowClosing(WindowEvent e) {
             if(!nosave) {
                  if(!assignit(true)) {return;}
             }
             end();
           }
        });
        exitbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             if(assignit(false))  {end();}
          }
        });
        cancelbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              end();
          }
       });
        btopSearch.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             new findword(thisprog);
             new findword(thisprog, thisprog, findword.TYPE_GETJUSTTOPIC);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
       if(!shark.phonicshark){
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
                  int step = getStep();
                  if(step<0)return;
                sp.it[step].phonics  = phonics.isSelected();
    //            wordlist.usephonics = false;//sp.it[0].phonics || wordTree != null && wordTree.forcephonics;
                 changed = true;
                if(wordTree != null) {
                  wordTree.font = null;
                 }
                 setgames();
                repaint();
                requestFocus();
                setuserec();
                program.checkgames(sp,topics);
            }
          });
       }
//        changestu.addActionListener(new ActionListener() {
//           public void actionPerformed(ActionEvent e) {
//              if(!panel2.isAncestorOf(stupanel)) showstupanel();
//              else if(sp.students.length>0)  delstuline();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              // enables exiting screen via the ESC key
//              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          }
//       });
//        changetopic.addActionListener(new ActionListener() {
//           public void actionPerformed(ActionEvent e) {
//              if(!panel2.isAncestorOf(tpanel)) showtpanel();
//              else if(sp.it[0].topics.length>0)  deltopicline(-1);
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              // enables exiting screen via the ESC key
//              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          }
//       });
//       changegame.addActionListener(new ActionListener() {
//           public void actionPerformed(ActionEvent e) {
//              if(!panel2.isAncestorOf(gpanel)) showgpanel(null);
//              else if(sp.it[0].games.length>0)  delgameline();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              // enables exiting screen via the ESC key
//              requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          }
//       });

       steppedjlist.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
               int step = steppedjlist.getSelectedIndex();
               if(step<0)return;
               spincomplete.setValue(new Integer(sp.it[step].mustcomplete));
               spinerrors.setValue(new Integer(sp.it[step].maxerrors));
               setgames(step, true);
               setuptopics(step, true);
//               addtopic(step);
            }
        });

        if(isstepped){
            if(sp.it.length==1)steppedjlist.setListData(new String[] {stepitem+" 1"});
            else if(sp.it.length>1){
                String s1[] = new String[]{};
                for(int k = 0; k < sp.it.length; k++){
                    s1 = u.addString(s1, stepitem+" "+String.valueOf(k+1));
                }
                steppedjlist.setListData(s1);
            }
            
            steppedjlist.setSelectedIndex(0);
            steppedjlist.setCellRenderer(new program.stujpainter());
         }
        
       steppedjlist.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            showsteppedpanel();
          }
          public void mouseEntered(MouseEvent e) {
           pnlbstu.setBackground(stucolor);
           pnlbstepped.setBackground(steppedcol);
           btStepped.setForeground(Color.white);
           if(!jptop.isVisible()){
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
           }
           if(!jpgam.isVisible()){
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);
           }
           }
       });
       steppedjlist.addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            int code = e.getKeyCode();
            if(code == e.VK_DELETE)
              delstuline();
            else if(code == KeyEvent.VK_ESCAPE)
              dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }

       });



       stujlist.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            showstupanel();
          }
          public void mouseReleased(MouseEvent e) {
             if(dragger.droppedon != null && dragger.droppedon == stujlist
                && dragger.draggedfrom == studenttc) {
               dragger.droppedon = dragger.draggedfrom = null;
               addstu();
             }
           }
          public void mouseEntered(MouseEvent e) {
           pnlbstu.setBackground(stucolor);
           btStudents.setForeground(Color.white);
           if(!jptop.isVisible()){
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
           }
           if(!jpgam.isVisible()){
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);
           }
           }
       });
       stujlist.addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            int code = e.getKeyCode();
            if(code == e.VK_DELETE)
              delstuline();
            else if(code == KeyEvent.VK_ESCAPE)
              dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }

       });
       topicsjlist.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
//             panel1b.dispatchEvent(e);
            if(dragger.droppedon != null && dragger.droppedon == topicsjlist
               && dragger.draggedfrom == topics) {
              dragger.droppedon = dragger.draggedfrom = null;
              if(isstepped){
                int step = steppedjlist.getSelectedIndex();
                if(step<0)return;
                  addtopic(step);
              }
              else addtopic();
            }
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            showtpanel();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          public void mousePressed(MouseEvent e) {
            showtpanel();
          }

          public void mouseEntered(MouseEvent e) {
             if(!isstepped){
              if(!jpstu.isVisible()){
           pnlbstu.setBackground(defaultpanel);
           btStudents.setForeground(Color.black);
              }
              }
 else{
               if(!jpstepped.isVisible()){
           pnlbstepped.setBackground(defaultpanel);
           btStepped.setForeground(Color.black);
              }
 }

           pnlbtop.setBackground(Color.green.darker());
           btTopics.setForeground(Color.white);
           if(!jpgam.isVisible()){
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);
           }
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       });
       topicsjlist.addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            int code = e.getKeyCode();
            if(code == e.VK_DELETE){
               int step = getStep();
               if(step<0)return;
               deltopicline(step);
            }
            else if(code == KeyEvent.VK_ESCAPE)
              dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }

       });
       topicsjlist.setCellRenderer(new itempainter2());
       gamesjlist.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            showgpanel(null);
         }
         public void mouseReleased(MouseEvent e) {
            if(dragger.droppedon != null && dragger.droppedon == gamesjlist
               && dragger.draggedfrom == games) {
              dragger.droppedon = dragger.draggedfrom = null;
              addgame();
            }
         }
          public void mouseEntered(MouseEvent e) {
              if(!isstepped){
                  if(!jpstu.isVisible()){
               pnlbstu.setBackground(defaultpanel);
               btStudents.setForeground(Color.black);
                  }
              }
            else{
                   if(!jpstepped.isVisible()){
               pnlbstepped.setBackground(defaultpanel);
               btStepped.setForeground(Color.black);
                  }
             }
              if(!jptop.isVisible()){
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
              }
                pnlbgam.setBackground(sharkStartFrame.gamescolor);
                btGames.setForeground(Color.white);
          }
       });

//       courseList.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
//         public void valueChanged(TreeSelectionEvent e) {
//            jnode sel = courseList.getSelectedNode();
//            if(sel.get().trim().equals("")){
//                courseList.setSelectionPath(e.getOldLeadSelectionPath());
//                return;
//            }
//            if(sel != null){
//                if(!courseList.exitValueChanged)
//                    topicsjlist.clearSelection();
//                setupTree(sel.get(), courseList.exitValueChanged?topics.getSelectedNode():null);
//            }
//        }
//       });
       courseJList.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
            String sel = (String)courseJList.getSelectedValue();

            if(sel != null){
         //       if(!courseList.exitValueChanged)
                topicsjlist.clearSelection();
                setupTree(sel, courseList.exitValueChanged?topics.getSelectedNode():null);
            }
            }
        });

       topics.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
            jnode snode = (jnode)topics.getLastSelectedPathComponent();
           if(snode == null || !snode.isLeaf()) {
               setTopicPanel((snode!=null && snode.type == jnode.TOPICGROUP)?-1:0);
              return;
            }
            if(snode.get().trim().equals("")){
              setTopicPanel(0);
              topics.setSelectionPath(e.getOldLeadSelectionPath());
              return;
            }
            setTopicPanel(1);
            topic sel = topics.getCurrentTopic();
            if(sel==null ) {paneltopics.validate();paneltopics.repaint();return;}

            sharkStartFrame.currPlayTopic = sel;

            if(!topics.exitValueChanged)
                topicsjlist.clearSelection();
            topmainlistpan.removeAll();
            if(shark.phonicshark)
                wordTree.phonicsw = wordlist.usephonics = true;
            else
                wordTree.phonicsw = wordlist.usephonics = phonics.isSelected();
           // wordlist.usephonics = true;
            wordTree.font = null;
            wordTree.parent = thisprog;
            scroller = u.uScrollPane(wordTree);
            scroller.setHorizontalScrollBarPolicy(scroller.HORIZONTAL_SCROLLBAR_NEVER);

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
            wordTree.setfont();
            wordTree.repaint();
            paneltopics.validate();
            GridBagConstraints grid1 = new GridBagConstraints();
        grid1.weighty = 0;
        grid1.weightx = 1;
        grid1.gridx = 0;
        grid1.gridy = -1;

        grid1.fill =  GridBagConstraints.BOTH;
        JLabel lbwl = new JLabel(u.gettext("simprog_", "wordlist"));
        lbwl.setBackground(toplabelbg);
        lbwl.setForeground(toplabelfg);
        lbwl.setOpaque(true);
        topmainlistpan.add(lbwl, grid1);
        grid1.weighty = 1;
        topmainlistpan.add(scroller, grid1);
        grid1.weighty = 0;
        topmainlistpan.add(orderck, grid1);



            wordTree.reset();
            wordTree.setup(sel,null,true);
            wordTree.font = null;
            
            orderck.setVisible(wordTree.canextend);

            if(wordTree.canextend) {
                   orderck.addItemListener(
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
                   });

            }

               paneltopics.validate();
                paneltopics.repaint();

            
        }
       });

       gamesjlist.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             dispose();
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           else if(code == e.VK_DELETE) {
              int step = getStep();
              if(step<0 || sp.it[step].games.length==0)return;
                delgameline(step);
//             delgameline();
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
       });
       studenttc.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE)
             dispose();
         }
       });
       games.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE)
             dispose();
         }
       });
       games.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
         public void valueChanged(TreeSelectionEvent e) {
             jnode jn = games.getSelectedNode();
             if(jn!=null){
                 if(gameiconpanel.screenwidth==0){
                     gameiconpanel.reset();
                 }
                dogameicon(games.getSelectedNode().get());
             }
        }
       });

       topics.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE)
             dispose();
         }
       });
       wordTree.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if (code == KeyEvent.VK_ESCAPE)
             dispose();
         }
       });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        topics.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                jnode jn;
                if((jn=topics.getSelectedNode())==null)return;
                if(isstepped && jn.type == jnode.TOPICGROUP)return;
                String sel = jn.get();
                if(sel != null) {
                    if(e.getClickCount() == 2) {
                      if(isstepped){
                        int step = steppedjlist.getSelectedIndex();
                        if(step<0)return;
                          addtopic(step);
                      }
                      else addtopic();
                    }
                    else{
                        gameiconpanel.pause = true;
                        dragger.startmotion(topics, e);
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                gameiconpanel.pause = false;
                gameiconpanel.repaint();
                dragger.stopmotion(topics, e);
            }

        });
        topics.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(dragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
          }
        });



        games.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                String sel = games.getSelectedNode().get();
                if(sel != null) {
                    if(e.getClickCount() == 2) {
                        lbStartGam.setText(strdefaultgames);
                        lbStartGam.setVisible(false);
                        gamesfirstshow = false;
                        spGam.setVisible(true);
                        addgame();
                    }
                    else{
                        gameiconpanel.pause = true;
                        dragger.startmotion(games, e);
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                gameiconpanel.pause = false;
                gameiconpanel.repaint();
                dragger.stopmotion(games, e);
            }
        });
         games.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(dragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
          }
        });
        studenttc.addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                String sel = ((jnode)studenttc.getSelectionPath().getLastPathComponent()).get();
                if(sel != null) {
                    if(e.getClickCount() == 2) {
                        addstu();
                    }
                    else{
                        gameiconpanel.pause = true;
                        dragger.startmotion(studenttc, e);
                    }
                }
            }
            public void mouseReleased(MouseEvent e) {
                gameiconpanel.pause = false;
                gameiconpanel.repaint();
                dragger.stopmotion(studenttc, e);
            }
        });
         studenttc.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(dragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
          }
        });
       validate();
      if(isstepped)
             dragger = new dragger_base(this, new JComponent[]{topics, games, studenttc}, new JComponent[]{topicsjlist, gamesjlist, steppedjlist, lbStartTop, lbStartGam});
      else
             dragger = new dragger_base(this, new JComponent[]{topics, games, studenttc}, new JComponent[]{topicsjlist, gamesjlist, stujlist, lbStartTop, lbStartGam});
//       dragger.addMouseListener(new java.awt.event.MouseListener() {
 //         public void mouseReleased(MouseEvent e) {
 //           gameiconpanel.pause = false;
 //           panelgames.repaint();
 //           dragger.passonevent(e);
 //         }
 //         public void mousePressed(MouseEvent e) {
 //           gameiconpanel.pause = true;
 //           dragger.passonevent(e);
 //         }
//          public void mouseClicked(MouseEvent e) {dragger.passonevent(e);}
//          public void mouseEntered(MouseEvent e) {
//              dragger.passonevent(e);
//          }
//          public void mouseExited(MouseEvent e) {
//              dragger.passonevent(e);
//          }
 //       });


       if(!isstepped)showtpanel();
        this.setEnabled(true);
        this.setVisible(true);
  //     requestFocus();
       getContentPane().repaint();
     }
     //--------------------------------------------------------------------
     void end(){
             wordlist.usephonics = savephonics;
             sharkStartFrame.currPlayTopic = savecurrtopic;
             nosave = true;
             dispose();
     }

     void newstep(int step) {

  //      selgame=seltopic=-1;
  //      selstep = -1;
  //      dely=0;
        programitem newit[] = new programitem[sp.it.length+1];
        System.arraycopy(sp.it,0,newit,0,step);
        if(step<=sp.it.length)
           System.arraycopy(sp.it,step,newit,step+1,sp.it.length-step);
        sp.it = newit;
   //     currstep = step;
        sp.it[step] = new programitem();
        sp.it[step].maxerrors = 2;
        sp.it[step].mustcomplete = 1;
        sp.it[step].phonics = false;

   //     getdo.setvalue(newit[step].mustcomplete);
    //    geterr.setvalue(newit[step].maxerrors);
     }

     void setTopicPanel(int st){
         if(!isstepped && st==-1)st = 0;
         topmainlistpan.setVisible(st==1);
         emptylistpan_mess.setVisible(st==-1);
         emptylistpan.setVisible(st==0);
     }
     
     void setTopicTreePanel(int st){
        topscroller.setVisible(st==1);
        whitetopp.setVisible(st==0);
     }     
     
void rearrangesteps() {
  int tot= steppedjlist.getModel().getSize();
  int sel = steppedjlist.getSelectedIndex();
  String s[] = new String[]{};
  for(int i = 0; i < tot; i++){
      s = u.addString(s, stepitem+" "+ String.valueOf(i+1));
  }
  steppedjlist.setListData(s);
  steppedjlist.setSelectedIndex(sel);
}

void rearrangestepsinprogram(int oldpos, int newpos) {
    programitem tpi = sp.it[newpos];
    sp.it[newpos] = sp.it[oldpos];
    sp.it[oldpos] = tpi;
}

     void deltopicline(int step) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               int curr = topicsjlist.getSelectedIndex();
//               if(curr < 0) return;
//               sp.it[0].topics = u.removeString(sp.it[0].topics,curr);
//       int len = sp.it[0].topics.length;
//       saveTree1.saveTree2 treesx[] = new saveTree1.saveTree2[len];
//       System.arraycopy(sp.it[0].trees,0,treesx,0,curr);
//       if(curr<len) System.arraycopy(sp.it[0].trees,curr+1,treesx,curr,len-curr);
//               sp.it[0].trees = treesx;
       int curr[] = topicsjlist.getSelectedIndices();
       if(curr.length <= 0) return;
       for(int i = curr.length-1; i >= 0; i--){
         sp.it[step].topics = u.removeString(sp.it[step].topics,curr[i]);
         int len = sp.it[step].topics.length;
         saveTree1.saveTree2 treesx[] = new saveTree1.saveTree2[len];
         System.arraycopy(sp.it[step].trees,0,treesx,0,curr[i]);
         if(curr[i]<len) System.arraycopy(sp.it[step].trees,curr[i]+1,treesx,curr[i],len-curr[i]);
         sp.it[step].trees = treesx;
         boolean mixedx[] = new boolean[len];                          //  rb 16/3/08  mmmm  start
         System.arraycopy(sp.it[step].mixed,0,mixedx,0,curr[i]);
         if(curr[i]<len) System.arraycopy(sp.it[step].mixed,curr[i]+1,mixedx,curr[i],len-curr[i]);
         sp.it[step].mixed = mixedx;                               //  rb 16/3/08  mmmm  end

       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sp.it[step].topics.length==0){
           btopTop.setEnabled(false);
           phonics.setSelected(false);
           topicsjlist.setListData(new String[] {});
       }
       else {
           phonics.setSelected(sp.it[step].phonics);
           topicsjlist.setListData(spellchange.spellchange(edit(sp.it[step].topics,topics,sp.it[step].mixed)));
       }    //  rb 16/3/08  mmmm
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               topicsjlist.setSelectedIndex(Math.min(curr, sp.it[0].topics.length-1));
       topicsjlist.setSelectedIndex(Math.min(curr[0],sp.it[step].topics.length-1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(sp.it[step].topics.length==0)changetopic.setText("");
//       if(sp.it[step].topics.length<1) changetopic.setVisible(false);
//       if(!changestu.isVisible()) changestu.setVisible(true);
//       if(!changegame.isVisible()) changegame.setVisible(true);
       program.checkgames(sp,topics);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    }

     public void setCourseListSelection(String s){
 //          jnode js[] = courseList.root.getChildren();
 //          for(int k = 0; k < js.length; k++){

//               if(js[k].get().equals(s)){
//                TreePath path2 = new TreePath(js[k].getPath());
  //              courseList.exitValueChanged = true;
  //              courseList.setSelectionPath(path2);
  //              courseList.exitValueChanged = false;
                courseJList.setSelectedValue(s, false);
 //               break;
  ///             }
   //        }
           }

    public void setupTree(String course, jnode currsel){
        setTopicTreePanel(1); 
        topics.onlyOneDatabase = "*";
        topics.onlyshowcourse = course;
        topics.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),
         false,
         db.TOPICTREE,
         true, topicTree.MODE_NO_COMPRESS_HEADING, "");
         
  /*
        topics.onlyOneDatabase = "*";
        topics.onlyshowcourse = course;
        topics.dbnames = new String[0]; // we need dbnames for revision list generation
    //    topics.setEditable(false);
    //    topics.root.setIcon(jnode.ROOTTOPICTREE);
    //    topics.setRootVisible(false);
        topics.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),
                false,
                db.TOPICTREE,
                true,
                alltopics);
*/




        topics.model.reload();
        topics.exitValueChanged = true;
        TreePath path;
     if(currsel==null)
        topics.setSelectionPath(path = new TreePath(topics.root.getFirstLeaf().getPath()));
     else{
         path = new TreePath(topics.find(currsel.get()).getPath());
         topics.setSelectionPath(path);
     }
        topics.scrollPathToVisible(path);
        topics.exitValueChanged = false;
        paneltopics.validate();
        paneltopics.repaint();
    }

     void dogameicon(String game){
         int panelheight = sharkStartFrame.screenSize.height/8;
        int jj = u.findString(sharkStartFrame.gamename, game);

        jnode jn = games.getSelectedNode();
        boolean isleaf = true;
        if(jn!=null){
            isleaf = jn.isLeaf();
        }
        gameiconpanel.setVisible(isleaf && jn!=null);
        
        gameiconim = null;
        if(jj<0)return;
        if(sharkStartFrame.gameiconlist[jj] != null) {
          gameiconim = new sharkImage(sharkStartFrame.gameiconlist[jj], false);
        }
        gameiconim.dontclear = true;
        int recw  = (mover.HEIGHT*panelheight/basketrest)*4/5;
        int rech = mover.HEIGHT*4/5;
        int sw = recw*3/4;
        int x = recw/2;
        gameiconim.w = sw;
        gameiconim.h = rech * 5/8;

        gameiconpanel.removeAllMovers();
        gameiconpanel.addMover(
                new mover.rectMover(new Rectangle(mover.WIDTH,mover.HEIGHT),
                sharkStartFrame.col2, sharkStartFrame.col2),0,0);
//          String ti = sharkStartFrame.mainFrame.publicGameTree.getparm(sharkStartFrame.mainFrame.publicGameTree.getparms(game),"icontitle");

          int ymarg = mover.HEIGHT*3/20;
          new GamesIcon_base(gameiconpanel,
                     new Rectangle(recw/4, ymarg/2, mover.HEIGHT*panelheight/basketrest, mover.HEIGHT-ymarg),
                      gameiconim,
                      game, sharkStartFrame.col2, Color.gray, false,false);

        String notes = sharkStartFrame.mainFrame.publicGameTree.getparm(sharkStartFrame.mainFrame.publicGameTree.getparms(game),"tooltiph");
        if(notes!=null && !notes.equals("") && gameiconpanel.screenwidth!=0){
            gamedesctext = new mover.formattedtextmover(
                  notes,
                  Color.black,
                  sharkStartFrame.treefont,
                  gameiconpanel, false, Font.PLAIN);
            gameiconpanel.addMover(gamedesctext, x+(x/2)+recw,
                    (mover.HEIGHT-gamedesctext.h)/2);
        }

     }

     void delgameline() { 
         delgameline(0);
     }
     //--------------------------------------------------------------------
     void delgameline(int step) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               int curr = gamesjlist.getSelectedIndex();
//               if(curr < 0) return;
//               sp.it[0].games = u.removeString(sp.it[0].games,curr);
       int curr[] = gamesjlist.getSelectedIndices();
       if(curr.length <= 0) return;
       for(int i = curr.length-1; i >= 0; i--){
 //          for(int j = 0; j < sp.it.length; j++){
               sp.it[step].games = u.removeString(sp.it[step].games,curr[i]);
  //         }
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       setgames(step, false);
//       int k = getStep();
       if(step>=0)
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               gamesjlist.setSelectedIndex(Math.min(curr,sp.it[0].games.length-1));
       gamesjlist.setSelectedIndex(Math.min(curr[0],sp.it[step].games.length-1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(sp.it[0].games.length==0)changegame.setText("");
//       if(sp.it[0].games.length<1) changegame.setVisible(false);
//       if(!changetopic.isVisible()) changetopic.setVisible(true);
//       if(!changestu.isVisible()) changestu.setVisible(true);
       program.checkgames(sp,topics);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     //--------------------------------------------------------------------
     void delstuline() {
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
               if(sp.students.length == 0) sp.students = new String[]{root.get()};
               if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get())){
                   btopStu.setEnabled(false);
                 stujlist.setListData(new String[] {allstudents});
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 changestu.setText("");
//                 if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get())) changestu.setVisible(false);
//                 if(!changetopic.isVisible()) changetopic.setVisible(true);
//                 if(!changegame.isVisible()) changegame.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               }
               else stujlist.setListData(sp.students);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               stujlist.setSelectedIndex(Math.min(curr,sp.students.length-1));
               stujlist.setSelectedIndex(Math.min(curr[0],sp.students.length-1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }



     //------------------------------------------------------------------------
     void setuserec() {
//       if(sp.it[0].topics.length != 1 || sp.it[0].games.length > 0 ||!recgamesok())
//             sp.it[0].userecommended = false;
//       userecbutton.setSelected(sp.it[0].userecommended);
 //      setgames();
     }
     //-------------------------------------------------------------------
     void showsteppedpanel() {
         topics.clearSelection();
         gameiconpanel.pause = true;
 //        gameiconpanel.stop();
        panel1a.setBackground(steppedcol);
        panel1b.setBackground(defaultpanel);
        panel1c.setBackground(defaultpanel);
            panel1a.setBorder(BorderFactory.createLoweredBevelBorder());
            panel1b.setBorder(BorderFactory.createEtchedBorder());
            panel1c.setBorder(BorderFactory.createEtchedBorder());
            btopGam.setEnabled(false);
            btopTop.setEnabled(false);
            btopSearch.setEnabled(false);
            btopStepped.setEnabled(steppedjlist.getModel().getSize()>1);
            program.nogames.setEnabled(false);
            phonics.setEnabled(false);
            if(steppedjlist.getSelectedIndex()<0)steppedjlist.setSelectedIndex(0);

            steppedjlist.setEnabled(true);
            topicsjlist.setEnabled(false);
            gamesjlist.setEnabled(false);
            if(!topics.exitValueChanged)
                topicsjlist.clearSelection();
            gamesjlist.clearSelection();
            jpstepped.setVisible(true);
  
        jptop.setVisible(false);
        jpgam.setVisible(false);
            lbTopStu.setVisible(true);
           lbTopTop.setVisible(false);
           lbTopGam.setVisible(false);

           pnlbstu.setBackground(stucolor);
                      pnlbstepped.setBackground(steppedcol);
           btStepped.setForeground(Color.white);
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);




            if(lbStartTop.isVisible())lbStartTop.setForeground(Color.white);
            lbStartGam.setText(strdefaultgames);
//            if(lbStartGam.isVisible() &&
//                    lbStartGam.getText().equals("<html><center>Drag and drop<br>Games here<br>default: all games</center></html>"))
//                lbStartGam.setForeground(Color.white);
//        jnode node;
        changed=true;
//        if(!panel2.isAncestorOf(steppedpanel)) {
//           panel2.removeAll();
//           panel2.add(steppedpanel,BorderLayout.CENTER);
//           changetopic.setText(changetopictext);
//           changegame.setText(changegametext);
 //          if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get())) changestu.setVisible(false);
//           if(!changetopic.isVisible()) changetopic.setVisible(true);
//           if(!changegame.isVisible()) changegame.setVisible(true);
//        }
//        if(sp.students.length > 0)  {
//            changestu.setText(delstu);
//        }
 //       else {
 //           changestu.setText("");
 //       }
        validate();
        getContentPane().repaint();
//        int sel = stujlist.getSelectedIndex();
//        if(sel<0) sel=0;
 //       node = sharkTree.find(root,sp.students[sel]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(node != null)
 //        if(node != null && studenttc.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //          studenttc.setSelectionPath(new TreePath(node.getPath()));
//        panel3.removeAll();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dragger.setActiveDestinations(new JComponent[]{steppedjlist});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        validate();
        getContentPane().repaint();
        return;
     }



     void showstupanel() {
         gameiconpanel.pause = true;
 //        gameiconpanel.stop();
        panel1a.setBackground(stucolor);
        panel1b.setBackground(defaultpanel);
        panel1c.setBackground(defaultpanel);
            panel1a.setBorder(BorderFactory.createLoweredBevelBorder());
            panel1b.setBorder(BorderFactory.createEtchedBorder());
            panel1c.setBorder(BorderFactory.createEtchedBorder());
            btopGam.setEnabled(false);
            btopTop.setEnabled(false);
            btopSearch.setEnabled(false);
            btopStu.setEnabled(sp.students.length>0);
            program.nogames.setEnabled(false);
            phonics.setEnabled(false);
            if(stujlist.getSelectedIndex()<0&&sp.students.length>0)stujlist.setSelectedIndex(0);

            stujlist.setEnabled(true);
            topicsjlist.setEnabled(false);
            gamesjlist.setEnabled(false);
            if(!topics.exitValueChanged)
                topicsjlist.clearSelection();
            gamesjlist.clearSelection();

         jpstu.setVisible(true);
        jptop.setVisible(false);
        jpgam.setVisible(false);
            lbTopStu.setVisible(true);
           lbTopTop.setVisible(false);
           lbTopGam.setVisible(false);

           pnlbstu.setBackground(stucolor);
                      pnlbstepped.setBackground(steppedcol);
           btStudents.setForeground(Color.white);
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);




            if(lbStartTop.isVisible())lbStartTop.setForeground(Color.white);
            lbStartGam.setText(strdefaultgames);
//            if(lbStartGam.isVisible() &&
//                    lbStartGam.getText().equals("<html><center>Drag and drop<br>Games here<br>default: all games</center></html>"))
//                lbStartGam.setForeground(Color.white);
        jnode node;
        changed=true;
//        if(!panel2.isAncestorOf(stupanel)) {
//           panel2.removeAll();
//           panel2.add(stupanel,BorderLayout.CENTER);
//           changetopic.setText(changetopictext);
//           changegame.setText(changegametext);
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(sp.students.length==1 && sp.students[0].equalsIgnoreCase(root.get())) changestu.setVisible(false);
//           if(!changetopic.isVisible()) changetopic.setVisible(true);
//           if(!changegame.isVisible()) changegame.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        }
//        if(sp.students.length > 0)  {
//            changestu.setText(delstu);
//        }
//        else {
//            changestu.setText("");
//        }
        validate();
        getContentPane().repaint();
        int sel = stujlist.getSelectedIndex();
        if(sel<0) sel=0;
        node = sharkTree.find(root,sp.students[sel]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(node != null)
         if(node != null && studenttc.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           studenttc.setSelectionPath(new TreePath(node.getPath()));
//        panel3.removeAll();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dragger.setActiveDestinations(new JComponent[]{stujlist});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        validate();
        getContentPane().repaint();
        return;
     }
     //-------------------------------------------------------------------
     int getStep() {
         int step = 0;
         if(isstepped){
           step = steppedjlist.getSelectedIndex();
         }
         return step;
     }


     void showtpanel() {
         int step = getStep();
         if(step<0)return;
         gameiconpanel.pause = true;
 //        gameiconpanel.stop();
            panel1a.setBackground(defaultpanel);
            panel1b.setBackground(Color.green.darker());
            panel1c.setBackground(defaultpanel);
            panel1a.setBorder(BorderFactory.createEtchedBorder());
            panel1b.setBorder(BorderFactory.createLoweredBevelBorder());
            panel1c.setBorder(BorderFactory.createEtchedBorder());
            btopGam.setEnabled(false);
            btopTop.setEnabled(sp.it[step].topics.length>0);
            btopSearch.setEnabled(true);
            program.nogames.setEnabled(false);
            if(topicsjlist.getSelectedIndex()<0&&sp.it[step].topics.length>0)topicsjlist.setSelectedIndex(0);
            gamesjlist.clearSelection();

            stujlist.setEnabled(false);
            topicsjlist.setEnabled(true);
            gamesjlist.setEnabled(false);



         if(isstepped)jpstepped.setVisible(false);
             else jpstu.setVisible(false);
         jptop.setVisible(true);
        jpgam.setVisible(false);

            lbTopStu.setVisible(false);
           lbTopTop.setVisible(true);
           lbTopGam.setVisible(false);

            btopStu.setEnabled(false);
            btopStepped.setEnabled(false);
            if(!shark.phonicshark)
                phonics.setEnabled(true);

           pnlbstu.setBackground(defaultpanel);
                      pnlbstepped.setBackground(defaultpanel);
           btStudents.setForeground(Color.black);
           pnlbtop.setBackground(Color.green.darker());
           btTopics.setForeground(Color.white);
           pnlbgam.setBackground(defaultpanel);
           btGames.setForeground(Color.black);




            if(lbStartTop.isVisible())lbStartTop.setForeground(Color.lightGray);
//            if(lbStartGam.isVisible() &&
            lbStartGam.setText(strdefaultgames);
//                lbStartGam.setForeground(Color.white);
      changed = true;
//      if(!panel2.isAncestorOf(tpanel)) {
//        panel2.removeAll();
//        panel2.add(tpanel,BorderLayout.CENTER);
//        changestu.setText(changestutext);
//        changegame.setText(changegametext);
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(sp.it[step].topics.length<1) changetopic.setVisible(false);
//        if(!changestu.isVisible()) changestu.setVisible(true);
//        if(!changegame.isVisible()) changegame.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      }
      if(sp.it[step].topics.length > 0) {
         int i =topicsjlist.getSelectedIndex();
//         changetopic.setText(deltopic);
         if(i<0) i=0;
         String course = topics.getCourse(sp.it[step].topics[i]);
         setupTree(course, null);
         jnode node = topics.expandPath(sp.it[step].topics[i]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(node != null){
         if(node != null && topics.getSelectionCount()<2) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           TreePath path = new TreePath(node.getPath());
           
           topics.exitValueChanged = true;
           topics.setSelectionPath(null);
           topics.setSelectionPath(path);
           topics.exitValueChanged = false;
           topics.scrollPathToVisible(path);


           ListSelectionListener lsl[] = courseJList.getListSelectionListeners();
           for(int ii = 0; ii < lsl.length; ii++){
               courseJList.removeListSelectionListener(lsl[ii]);
           }
           setCourseListSelection(course);
           for(int ii = 0; ii < lsl.length; ii++){
               courseJList.addListSelectionListener(lsl[ii]);
           }
         }
      }
//      else changetopic.setText("");
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      dragger.setActiveDestinations(new JComponent[]{topicsjlist, lbStartTop});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

      validate();
      getContentPane().repaint();
     }
     //------------------------------------------------------------------

     // not was this ever used??
     boolean recgamesok() {
       if(sp.it[0].topics.length == 1) {
         topic tt;
         jnode node;
         String tn;
         node = topics.expandPath(sp.it[0].topics[0]);
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
     //-------------------------------------------------------------------
     void showgpanel(jnode justtopic) {
         int step = getStep();
         if(step<0)return;
              gameiconpanel.pause = false;
panelgames.repaint();
            panel1a.setBackground(defaultpanel);
            panel1b.setBackground(defaultpanel);
            panel1c.setBackground(sharkStartFrame.gamescolor);
            panel1a.setBorder(BorderFactory.createEtchedBorder());
            panel1b.setBorder(BorderFactory.createEtchedBorder());
            panel1c.setBorder(BorderFactory.createLoweredBevelBorder());
            btopGam.setEnabled(sp.it[step].games.length>0);
            program.nogames.setEnabled(sp.it[step].games.length>0);
            if(gamesjlist.getSelectedIndex()<0&&sp.it[step].games.length>0)gamesjlist.setSelectedIndex(0);

            btopTop.setEnabled(false);
            btopStu.setEnabled(false);
            btopStepped.setEnabled(false);
            phonics.setEnabled(false);
            btopSearch.setEnabled(false);
            

            stujlist.setEnabled(false);
            topicsjlist.setEnabled(false);
            gamesjlist.setEnabled(true);

            stujlist.clearSelection();
            if(!topics.exitValueChanged)
                topicsjlist.clearSelection();

         if(isstepped)jpstepped.setVisible(false);
             else jpstu.setVisible(false);
         jptop.setVisible(false);
        jpgam.setVisible(true);

                    lbTopStu.setVisible(false);
           lbTopTop.setVisible(false);
           lbTopGam.setVisible(true);

           pnlbstu.setBackground(defaultpanel);
                      pnlbstepped.setBackground(defaultpanel);
           btStudents.setForeground(Color.black);
           pnlbtop.setBackground(defaultpanel);
           btTopics.setForeground(Color.black);
           pnlbgam.setBackground(sharkStartFrame.gamescolor);
           btGames.setForeground(Color.white);


            if(gamesfirstshow){
                lbStartGam.setText(u.gettext("simprog_", "gamedrag"));
            }
            else
                lbStartGam.setText(strdefaultgames);

            if(lbStartTop.isVisible())lbStartTop.setForeground(Color.white);
            if(lbStartGam.isVisible())lbStartGam.setForeground(Color.lightGray);
      int i;
      jnode node;
      GridBagConstraints grid1 = new GridBagConstraints();
      changed=true;
      grid1.weightx = grid1.weighty = 1;
      grid1.anchor = grid1.WEST;
      grid1.gridx = 0;
      grid1.gridy = -1;


//      if(!panel2.isAncestorOf(gpanel)) {
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(sp.it[step].games.length<1) changegame.setVisible(false);
//        if(!changetopic.isVisible()) changetopic.setVisible(true);
//        if(!changestu.isVisible()) changestu.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        changetopic.setText(changetopictext);
//        changestu.setText(changestutext);
        games.setup(sharkStartFrame.publicGameLib,
                   true,true,allgames,gamestoplay.categories);
        topic tt[] = new topic[justtopic==null? sp.it[step].topics.length: 1];
        for(i=0;i<sp.it[step].topics.length;++i) {
            if(justtopic!=null && !courseList.savePathIncRoot(justtopic).equals(sp.it[step].topics[i])) continue;
            int ii = justtopic==null ? i : 0;
            node = courseList.expandPath(sp.it[step].topics[i]);
            if(node!= null && node.isLeaf() && tt.length>0) {
               tt[ii] = topic.findtopic(node.get());
               tt[ii].phonicscourse = u.phonicscourse(sp.it[step].topics[i]);
            }
            else {
                tt = null;
                break;}
        }
        if(tt != null) {                                    // start rb 27/12/07
          games.reduceGames(tt, sp.it[step].phonics);
          if (tt.length == 1) {
            games.reduceGames(tt[0]);
            games.addrecommended(tt[0]);
          }
        }                                                   // end rb 27/12/07
        gpanel.removeAll();
        grid1.weighty=0;
        grid1.fill = GridBagConstraints.HORIZONTAL;
        gpanel.add(glabel,grid1);
        grid1.fill = GridBagConstraints.BOTH;
        grid1.weighty=15;
        grid1.fill = GridBagConstraints.HORIZONTAL;
        grid1.weighty = 0;
        grid1.fill = GridBagConstraints.NONE;
        grid1.anchor = GridBagConstraints.CENTER;
        grid1.weighty=1;
//        gpanel.add(addgame,grid1);
//        panel2.removeAll();
//        panel2.add(gpanel,BorderLayout.CENTER);
//        panel3.removeAll();
        if(sp.it[step].games.length>0) {
           int h =  gamesjlist.getSelectedIndex();
          for(i=0;i<sp.it[step].games.length;++i) {
             node = games.expandPath(sp.it[step].games[i]);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             if(node != null && i==0)
             if(node != null && i==h && games.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               games.setSelectionPath(new TreePath(node.getPath()));
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             else if(node ==null && i==0)
             else if(node ==null && i==h && games.getSelectionCount()<2)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               games.setSelectionPath(new TreePath((node = (jnode)games.root.getFirstLeaf()).getPath()));

          }
        }
        else {
//          changegame.setText("");
          games.setSelectionPath(new TreePath((node = (jnode)games.root.getFirstLeaf()).getPath()));
//          if(node.getLevel() > 0
//              && (node = (jnode)((jnode)node.getParent()).getNextSibling()) != null
//              && !node.isLeaf())
//            games.setSelectionPath(new TreePath(((jnode)node.getChildAt(0)).getPath()));
        }
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        dragger.setActiveDestinations(new JComponent[]{gamesjlist, lbStartGam});
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        validate();
        getContentPane().repaint();
//      }

           jnode jn = games.getSelectedNode();
           if(jn!=null){
                 if(gameiconpanel.screenwidth==0){
                     gameiconpanel.reset();
                 }
                dogameicon(games.getSelectedNode().get());
           }

    }
    //-------------------------------------------------------------------
     void addgame() {
       int i, j, k;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = games.getSelectionPaths();
          TreePath sel[] = u.sortPathSelections(games, games.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if (sel==null || sel.length==0) return;
       btopGam.setEnabled(true);
       program.nogames.setEnabled(true);

       int progitem = 0;
       if(isstepped){
           progitem=steppedjlist.getSelectedIndex();
           if (progitem < 0)return;
         }
       for(k=0;k<sel.length;++k) {
         jnode node = (jnode) sel[k].getLastPathComponent();
         if (node == games.root) {
           sp.it[progitem].games = new String[0];
           setgames();
           return;
         }
         String name = node.get();
         if(u.findString(sharkStartFrame.gametreeheadings,name)>=0) {
           u.okmess("simprog_notblue");
           continue;
         }
//         String tp = games.savePath(node);
//         i= tp.indexOf(topicTree.CSEPARATOR);
//         if(i>=0) tp = tp.substring(i+1);               // strip non-phonics/phonics part
         programitem spit = sp.it[progitem];
         if (name == null)
           continue;
         if (u.findString(spit.games, name) < 0) {
           spit.games = u.addString(spit.games, name);
           setgames(progitem, false);
         }
       }
       setuserec();
       program.checkgames(sp,topics);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       int p = gamesjlist.getModel().getSize()-1;
       gamesjlist.setSelectedIndex(p);
       gamesjlist.ensureIndexIsVisible(p);
//       if(!changegame.isVisible()) changegame.setVisible(true);
//       if(sp.it[0].games.length==1) {
//       if(sp.it[0].games.length>=1) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         changegame.setText(delgame);panel1c.validate();panel1c.repaint();
//       }
     }
     //----------------------------------------------------------------
     void setgames() {
        setgames(0, false);
     }
     void setgames(int item, boolean clearselection) {
         programitem spit = sp.it[item];
         if(spit.games.length==0) {
             btopGam.setEnabled(false);
             program.nogames.setEnabled(false);
            gamesjlist.setListData(new String[]{});
            lbStartGam.setVisible(true);
            spGam.setVisible(false);
         }
         else {
            lbStartGam.setVisible(false);
            spGam.setVisible(true);
            gamesjlist.setListData(strip(spit.games));
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(!changegame.isVisible()) changegame.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
        if(clearselection) gamesjlist.clearSelection();
        else 
            gamesjlist.setSelectedIndex(0);
     }
      //----------------------------------------------------------------

     void addtopic() {
         addtopic(0);
     }

     void addtopic(int item) {
       int i;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = topics.getSelectionPaths();
       TreePath sel[] = u.sortPathSelections(topics, topics.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sel == null || sel.length==0) return;
       btopTop.setEnabled(true);
       btopSearch.setEnabled(true);
       for(i=0;i<sel.length;++i) {
          jnode node = (jnode) sel[i].getLastPathComponent();
          if(node.dummy) continue;      // rb 26-11-07
          if (node == topics.root) {
           sp.it[item].topics = new String[0];
           sp.it[item].trees = new saveTree1.saveTree2[0];
           sp.it[item].mixed = new boolean[0];               //  rb 16/3/08  mmmm
           phonics.setSelected(false);
           topicsjlist.setListData(new String[] {topics.root.get()});
           topicsjlist.setSelectedIndex(0);
           return;
         }
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         String name = node.get();
         String name = topics.savePath(node);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if (name == null)
           continue;
   //         if(!node.isLeaf() && node.inphonics()) {      //  rb 16/3/08  mmmm start
//           u.okmess("simprog_nophonicshead");
//           continue;
//         }                                                 //  rb 16/3/08  mmmm  end
         if (u.findString(sp.it[item].topics, name) < 0) {
           boolean ismixed = false;                                            //  rb 16/3/08  mmmm start
//           String noMixedCourses[] = u.splitString(u.gettext("coursespecifics", "nonfastmodecourses"));
           int ii = topics.isNodeFastModeable(topics.getSelectedNode(), ((jnode)topics.root.getChildAt(0)).get());
//           boolean mixedOk = u.findString(noMixedCourses, name.substring(0, name.indexOf(topicTree.CSEPARATOR)))<0;
           if(!isstepped && node.getLevel()!=1 && !node.isLeaf() && ii>=0) {
             String choices[] = u.splitString(u.gettext("simprog_","qmixedch"));
             int ret = JOptionPane.showOptionDialog(sharkStartFrame.mainFrame,
                                                   u.splitString(u.gettext("simprog_","qmixed",node.get())),null,
                                                   JOptionPane.NO_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE, null,
                                                   choices, choices[0]);
             if(ret < 0 || ret == 2) continue;
             else if(ret == 1) ismixed = true;
           }                                                                  //  rb 16/3/08  mmmm  end
           sp.it[item].topics = u.addString(sp.it[item].topics,topics.savePath(node));
           if(sp.it[item].topics.length == 1 && node.isLeaf() && node.inphonics()) {
               if(!shark.phonicshark)
                  phonics.setSelected(true);
             sp.it[item].phonics  = true;
             wordlist.usephonics = false;//true;
           }
           saveTree1.saveTree2 nt[] = new saveTree1.saveTree2[sp.it[item].trees.
               length + 1];
           System.arraycopy(sp.it[item].trees, 0, nt, 0, sp.it[item].trees.length);
           sp.it[item].trees = nt;
           sp.it[item].trees[sp.it[item].trees.length - 1]
               = (new saveTree1(topics, node)).curr;
           boolean mx[] = new boolean[sp.it[item].mixed.length + 1];           //  rb 16/3/08  mmmm  start
           System.arraycopy(sp.it[item].mixed, 0, mx, 0, sp.it[item].mixed.length);
           mx[mx.length-1] = ismixed;
           sp.it[item].mixed = mx;                                             //  rb 16/3/08  mmmm  end
         }
       }
        setuptopics(item, false);
     }

     void setuptopics(int item, boolean clearselection) {

       if(sp.it[item].topics.length==0) {
           if(!shark.phonicshark)
            phonics.setSelected(false);
           topicsjlist.setListData(new String[] {topics.root.get()});
       }
       else {
           if(!shark.phonicshark)
               phonics.setSelected(sp.it[item].phonics);
           topicsjlist.setListData(spellchange.spellchange(edit(sp.it[item].topics,topics,sp.it[item].mixed)));
       }       //  rb 16/3/08  mmmm
       if(sp.it[item].topics.length==1) {
              lbStartTop.setVisible(false);
              spTop.setVisible(true);
//           changetopic.setText(deltopic);panel1b.validate();panel1b.repaint();
       }
       if(!clearselection)topicsjlist.setSelectedIndex(sp.it[item].topics.length-1);
       else topicsjlist.clearSelection();
       setuserec();
       program.checkgames(sp,topics);
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(!changetopic.isVisible()) changetopic.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       topicsjlist.ensureIndexIsVisible(topicsjlist.getModel().getSize()-1);
//       if(sp.students!=null && sp.students.length>=1) {
//         changetopic.setText(deltopic);panel1b.validate();panel1b.repaint();
//       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     }

      //----------------------------------------------------------------
     void addstu() {
       int i;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       TreePath sel[] = studenttc.getSelectionPaths();
       TreePath sel[] = u.sortPathSelections(studenttc, studenttc.getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(sel==null || sel.length==0) return;
       btopStu.setEnabled(true);
       btopStepped.setEnabled(true);
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
       else {
         stujlist.setListData(sp.students);
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(!changestu.isVisible()) changestu.setVisible(true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       }
       stujlist.setSelectedIndex(sp.students.length-1);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       stujlist.ensureIndexIsVisible(sp.students.length-1);
//       if(!changestu.isVisible()) changestu.setVisible(true);
//       if(sp.students.length==1) {
       if(sp.students.length>=1) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         changestu.setText(delstu);panel1a.validate();panel1a.repaint();
       }
     }




     //----------------------------------------------------------------
     boolean  assignit(boolean forceend) {
        int i,j;
//startPR2006-11-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!isstepped && (sp.it.length == 0 || sp.it[0].topics == null || sp.it[0].topics.length == 0 && changed)) {
          if(u.yesnomess(u.gettext("simprog_notopics", "heading"), u.gettext("simprog_notopics", "message"),thisprog)){
            nosave=true;
            return true;
          }
          else{
              showtpanel();
            return false;
            }
        }
        if(isstepped){
            if(sp.it.length==1){
                String heading = u.gettext("stepprog_littlesteps", "heading");
                if(forceend){
                    String mess = u.gettext("stepprog_littlesteps", "message");
                    mess = mess.replaceFirst("%", u.gettext("steppedprogram", "steps"));
                    if(u.yesnomess(u.gettext("stepprog_littlesteps", "heading"), mess,thisprog)){
                        nosave=true;
                        return true;
                    }
                    else
                      return false;
                }
                else{
                    String mess = u.gettext("stepprog_littlesteps", "message2");
                    mess = mess.replaceFirst("%", u.gettext("steppedprogram", "steps"));
                    u.okmess(heading, mess,thisprog);
                    return false;
                }
            }
            boolean nowords = false;
            for(int n =0; n <sp.it.length; n++ )
            {
                if(sp.it[n].topics.length==0){
                    nowords = true;
                    steppedjlist.setSelectedIndex(n);
                    showtpanel();
                    break;
                }
            }
            if(nowords){
                String mess = u.gettext("stepprog_nolists", "message");
                mess = mess.replaceFirst("%", u.gettext("simprog_", "selectlist"));
                if(u.yesnomess(u.gettext("stepprog_nolists", "heading"), mess,thisprog)){
                    nosave=true;
                    return true;
                }
                else
                  return false;
            }
        }



//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(sp.it.length >0 && sp.it.length>=1 && changed ) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          if(!ask || u.yesnomess("simprog_assign")) {
              if(!forceend || u.yesnomess("simprog_assign", thisprog)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if((sp.students!=null && (sp.students.length == 0 || sp.students[0].equals(allstudents))   //  rb 16/3/08  mmmm
                          && !u.yesnomess("simprog_allstu2"))) return false;          //  rb 16/3/08  mmmm
                if(!program.checkgames(sp,topics)) {
                   u.okmess(u.gettext("stuprog_","valid"),
                            u.gettext("stuprog_","cantplay", program.nogamesname()), adm);//  rb 16/3/08  mmmm
                   return false;
               }

                if(oldname==null)name = program.makename();   //rb 23/10/06
                else name = oldname;
   //            name = program.makename();   //rb 23/10/06
               if(isstepped && oldname==null){



               String op[] =  new String[]{u.gettext("ok","label")};
               JOptionPane getpw = new JOptionPane(
                  u.gettext("steppedprogram","entername"),
                  JOptionPane.PLAIN_MESSAGE,
                  0,
                  null,op,op[0]);
               getpw.setWantsInput(true);
                JTextField jtf = u.getjtextfieldcomp(getpw);
                if(jtf!=null){
                    jtf.setText(name);
                    String str1 = u.gettext("steppedprogram","enternametitle");
                    JDialog dialog = getpw.createDialog(thisd,str1);
                    dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    while(true) {
                        jtf.selectAll();
                        keypad.dofullscreenkeypad(dialog, true);
                        dialog.setVisible(true);
                        keypad.dofullscreenkeypad(dialog, false);
                        String input = (String)getpw.getInputValue();
                        while(input.length() > 0 && input.charAt(input.length()-1) == ' ')
                            input = input.substring(0,input.length()-1);

                        String proglist[] = db.list(teacher,db.PROGRAM);
                        boolean already = u.findString(proglist, input)>=0 && !input.equals(oldname);
                        if((!already) && program.programnamecheck(input)){
                            name = input;
                            break;
                        }
                        dialog.setVisible(false);
                        if(already)
                            u.okmess(u.gettext("steppedprogram","alreadyusedtitle"), u.gettext("steppedprogram","alreadyused"), thisd);
                    }
                }


               }
              if(sp.students!=null){
               String ss[] = expand(sp.students); // get actual students
               for(i=0;i<ss.length;++i) {
                  String list[] = db.list(ss[i],db.PROGRAM);
                  if(list !=null) for(j=0;j<list.length;++j) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    if(((program.saveprogram)db.find(ss[i],list[j],db.PROGRAM)).simple
//                           && (!list[j].endsWith("]") || list[j].endsWith("["+teacher+"]")))     //rb 23/10/06
                    program.saveprogram spr=null;
                    try{spr = (program.saveprogram)db.find(ss[i],list[j],db.PROGRAM);}
                    catch(ClassCastException e){spr=null;}
//                    if(spr==null||(  ((spr.simple&&!isstepped)|| (!spr.simple&&isstepped)  )
//                                   && (!list[j].endsWith("]") || list[j].endsWith("["+teacher+"]"))))     //rb 23/10/06
//                    if(spr==null||(!list[j].endsWith("]") || list[j].endsWith("["+teacher+"]")))     //rb 23/10/06
                    if(spr==null||(!u.CaseInsensitiveEndsWith(list[j], "]") || 
                            u.CaseInsensitiveEndsWith(list[j], "["+teacher+"]")))     //rb 23/10/06
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                       db.delete(ss[i],list[j],db.PROGRAM);
                  }
//startPR2008-09-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  sp.version = shark.versionNoDetailed;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                    //   start rb 23/10/06
                  String s1 = isstepped?name:program.makename();
//                  if(!name.endsWith("["+teacher+"]")) {              //  start rb 4/3/08
                  if(!u.CaseInsensitiveEndsWith(name, "["+teacher+"]")) {              //  start rb 4/3/08
                      db.delete(ss[i],s1,db.PROGRAM);                // in case old version there
                      db.update(ss[i], s1 + "[" + teacher + "]", sp, db.PROGRAM);

                  }                                                  //  end rb 4/3/08
                  else {
                      db.update(ss[i],s1,sp,db.PROGRAM);
                  }
                    //   end rb 23/10/06
                  db.update(ss[i],"_refresh","",db.TEXT);
                }
              }
              if(frommanage){
                steppedmanaged.activatedName = name;
                steppedmanaged.activatedProgram = sp;
                parent.requestFocus();                            
              }
              if(isstepped)
                 db.update(dbname, name, sp, db.PROGRAM);
              adm.updateonactivate = admin.ACTIVATEWORK;
              adm.treestripprograms();  // refresh admin tree
              adm.addsimpleprograms();
          }
        }
        nosave=true;
        return true;
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
    //------------------------------------------------------------------  rb 23/10/06
//     void write() {
//            if(!oldname.equalsIgnoreCase(name)) db.delete(dbname, oldname, db.PROGRAM);
//            db.update(dbname, name, sp, db.PROGRAM);
//
//     }
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
   public static String[] edit(String[] ss, topicTree topics,boolean ismixed[]) {     //  rb 16/3/08  mmmm
      int i,j;
      String s[] = new String[ss.length];
      for(i=0;i<s.length;++i) {
        s[i] = new String(ss[i]);
        if((j=s[i].lastIndexOf(topicTree.CSEPARATOR))>=0) {
          s[i] = s[i].substring(j+1);// + " (" + s[i].substring(0,j) + ")";
//          while((j=s[i].indexOf(topicTree.CSEPARATOR))>=0)
//              s[i] = s[i].substring(0,j)+" / "+s[i].substring(j+1);
        }
        if(topics != null) {
          jnode node = topics.expandPath(ss[i]);
          if (node != null && !node.isLeaf() && ismixed[i])                             //  rb 16/3/08  mmmm
            s[i] += " "+ strmixedlists;
        }
      }
      return s;
    }

    static String[] strip(String ss[]) {
      int j,i;
      String news[] = new String[ss.length];
      for(i=0;i<ss.length;++i) {
        String s = new String(ss[i]);
        while((j=s.indexOf(topicTree.CSEPARATOR))>=0) s = s.substring(j+1);
        news[i] = s;
      }
      return news;
   }

  class itempainter2 extends JLabel implements ListCellRenderer {
     itempainter2() {setOpaque(true);}
     Object o;
     FontMetrics m;
     public Component getListCellRendererComponent( JList list,Object oo,
           int index,boolean isSelected,boolean cellhasFocus) {
        o = oo;
        this.setBackground(isSelected?stujlist.getSelectionBackground():Color.white);
        String s = (String)o;
        int n;
        if((n=s.indexOf('@'))>=0)s = s.substring(0,n);
        setText(s);
        return this;
     }
  }
/*
      public class MyCellRenderer extends DefaultListCellRenderer {
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		// Let superclass deal with most of it...
		super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		final ImageIcon imageIcon = new ImageIcon(sharkStartFrame.publicPathplus + "sprites" +
                                          sharkStartFrame.separator +
                                          "course.gif");
		Image image = imageIcon.getImage();
		final Dimension dimension = this.getPreferredSize();
		final double height = dimension.getHeight();
		final double width = (height / imageIcon.getIconHeight()) * imageIcon.getIconWidth();
		image = image.getScaledInstance((int)width, (int)height, Image.SCALE_SMOOTH);
		final ImageIcon finalIcon = new ImageIcon(image);
		setIcon(finalIcon);
		return this;
	}
}*/
}
