package shark;


//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.border.*;
//import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.*;
import java.awt.*;

import java.util.*;

import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
//import java.lang.ref.SoftReference.*;
//import java.lang.ref.*;
//import com.sun.image.codec.jpeg.*;
//import java.awt.image.*;
import java.io.File;
import javax.swing.event.*;
import javax.swing.text.*;

  //--------------------------------------------------------------------
public class admin
//startPR2004-08-11^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public int screenwidth;
   public int screenheight;
   student teacher = sharkStartFrame.studentList[sharkStartFrame.currStudent];
//   BorderLayout layout1 = new BorderLayout();
//   GridBagLayout layout1g = new GridBagLayout();
//   GridBagLayout layout2 = new GridBagLayout();
//   GridBagLayout layout0 = new GridBagLayout();
//   GridBagLayout layout3 = new GridBagLayout();
//   GridBagLayout layout3a = new GridBagLayout();
//   GridBagLayout layout3b = new GridBagLayout();
//   GridBagLayout layout3c = new GridBagLayout();
//   GridBagLayout layout4 = new GridBagLayout();
//   ButtonGroup bg = new ButtonGroup();
   GridBagConstraints grid = new GridBagConstraints();
//   JPanel panel1= new JPanel(),panel2= new JPanel(),panel3= new JPanel(),panel3a= new JPanel(),panel3b = new JPanel(),panel3c= new JPanel();
//   mlabel_base lab1 = u.mlabel_base("stulist_h1");
//   mlabel_base gametreelab = u.mlabel_base("stulist_gametreelab");
//   mlabel_base lab2 = u.mlabel_base("");
//   mlabel_base lab2drag = u.mlabel_base(shark.macOS?  "stulist_drag_mac":"stulist_drag");
//   String stuoptionstext = u.gettext("stulist_h2","stuoptions");
//   mlabel_base labpg = u.mlabel_base("stulist_labpg");
//   static String allstu = u.gettext("stulist_","allstu");
   static String notstarted = u.gettext("stulist_","notstarted");
   static String completed = u.gettext("stulist_","completed");
   static String stepof = u.gettext("stulist_","stepof");
   static String games = u.gettext("stulist_","games");
   static String game = u.gettext("stulist_","game");
//   JList studentlc = new JList();
//   jnode currstu2;
//   jnode root=new jnode(teacher.name+allstu);
//   DefaultTreeModel model = new DefaultTreeModel(root);
//   JTree studenttc = new dropTree_base(model);
//    JButton btexisting;
   jnode userroot;
   dropTree_base usertree;
   dropTree_base capturetree;
   TreeCellRenderer capturerenderer;
   jnode admins_heading;
   jnode useruniversal;
   DefaultTreeModel usermodel;
   jnode younode;
   jnode currentnode;
   jnode otherstunode;
   jnode otherstunode_clickable;

   String proglist[];
   admin thisadmin =this;
   boolean simpleprog;
   String biglist[];
//   mbutton undobutton;
//   gamestoplay gameTree;
//   mlabel_base addgameh = u.mlabel_base("stulist_addgameh");
//   String addgameh1 = u.gettext("stulist_","addgameh1");
//   String addgameh2 = u.gettext("stulist_","addgameh2");
//   String addgamehh1 = u.gettext("stulist_","addgamehh1");
//   String addgamehh2 = u.gettext("stulist_","addgamehh2");
//   String addgamehr1 = u.gettext("stulist_","addgamehr1");
//   String addgamehr2 = u.gettext("stulist_","addgamehr2");
//   mbutton addgame = u.mbutton("stulist_addgame");
//   mbutton delgame = u.mbutton("stulist_delgame");
//   mlabel_base exwarning = u.mlabel_base("stulist_exwarning");
//   JList gamesjlist=new JList();
//   String glist[];
//   dragger_base dragger_base;
//   class undo {
//      Object prog;
//      String pupil;
//      String name;
//   }
//   Vector undos = new Vector();
   Color darkerdefaultcol;
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    long lastdialogstart;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    JCheckBox movestus = u.CheckBox("adminmovestu");
//    boolean capturing = false;
    TreeSelectionListener tsl = null;
    dragger_base capturedragger;
  //----------------------------------------------------------
 //  mlabel_base groupmess = u.mlabel_base("stulist_groupmess");
   JButton exit = u.Button("stulist_exit");
//   mbutton addprog = u.mbutton("stulist_addprog");
//   mbutton addprog2 = u.mbutton("stulist_addprog2");
//   String addprogtext = u.gettext("stulist_addprog","label");
//   JList programlc = new JList();
 //  int i,j;
//   int currstu1;
//   byte state;
//   static final byte NEWGROUP = 1;
//   static final byte NEWSTUDENT = 2;
//   static final byte ADDSTUDENT = 3;
//   static final byte REMOVESTUDENT = 4;
//   static final byte RENAMESTUDENT = 5;
//   static final byte STUDENTRECORD = 6;
//   static final byte STUDENTASSIGNP = 7;
//   static final byte STUDENTASSIGNP2 = 8;
//   static final byte STUDENTREMOVEP = 9;
//   static final byte STUDENTEXCLUDEG = 10;
//   static final byte STUDENTPASSWORD = 11;
//   static final byte NEWADMIN = 12;
//   static final byte OLDADMIN = 13;
//   static final byte NEWTEACHER = 14;
//   static final byte OLDTEACHER = 15;
//   static final byte DRAGSTU = 16;
//   static final byte STUOPTIONS = 17;

   String savecourses[];         // rb 1/2/08
//startPR2009-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   String rewardtitle = u.gettext("stulist_", "rewards");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   Color cream = sharkStartFrame.cream;
   JButton but_remove;
   JPanel capturepan;
   JPanel blankpan;
   JPanel profilepan;
          JPanel adminhelppan;
   settings settingspan;
//     JPanel dummysettingspan;
     JPanel workpan;
     JPanel dummyworkpan;
   DefaultTreeModel capturemodel;
   String str_currnone = u.gettext("admintree", "currentnone");
   String str_curr = u.gettext("admintree", "current");
   

   JLabel profile_status;
   JComboBox profile_statuscombo;
   JTextField profile_name;
   JTextField profile_password;
   JButton profile_passwordbut;
   JButton profile_statusbut;
   JButton profile_namebut;
   JLabel profilestatuslab;
   JLabel profilenamelab;
   JLabel profilepasswordlab;

   String str_change = u.gettext("change", "label");
   String str_save = u.gettext("save", "label");
   String str_add = u.gettext("add", "label");
   mainPan dropBox;
   JPanel picpan;
   JLabel piclabel;
//   String str_ownicon = u.gettext("adminprofile", "youricon");
   JButton boxDelete;
   Image anon = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                File.separator + "signonAdminLight_il96.png");
   Image anon_stu = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                File.separator + "signonStudLight_il96.png");
   int rightwidth;
   JPanel rightpan;
   int b1 = 5;
   int b2 = 10;
   Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
   Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
   Font evensmallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-2);
   Font bigfont = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+4);


   JLabel lbuserrecords;
   JLabel lbuserassignments;
   JRadioButton worktype1;
   JRadioButton worktype2;
   JLabel currsetworktype;
   JLabel currsetworktype2;
//   String setworktypes[] = new String[]{u.gettext("adminwork", "standard"), u.gettext("adminwork", "stepped_plural")};
   String setworktypes[] = new String[]{u.gettext("adminwork", "standard"), u.gettext("adminwork", "stepped")};
   String strSteppedSinglular = u.gettext("adminwork", "stepped");
   JLabel currsetwork;
   JLabel lbcurrsetwork;
   String currwork = null;
   String currwork_mixed[] = null;
   String strnone = u.gettext("none", "label");
   String strmix = u.gettext("adminwork", "mix");
   String lastnode = null;
   JButton btviewrecords;
   JButton btviewassignments;
   JPanel pninfolbviewassignments;
   int currworktype =-1;
   JLabel lbyourlist;
   JButton btManage;
   JLabel lbsetwork;
//   JButton btnew;
   LinedPanel workcontentpan;
   JPanel blankpanProSet;
   JPanel blankpanSetWor;
   JPanel pnlines[];
 //    String strcurrsetwork = u.gettext("adminwork", "lbcurrsetwork");
JPanel radioholder;
String nodename;
     JPanel adminhelpcontentpan;
     JPanel adminhelpcontentpan2;

   static final int ACTIVATEALL = 0;
   static final int ACTIVATESETTINGS = 1;
   static final int ACTIVATEWORK = 2;


public int updateonactivate = -1;


JPanel jpworktit;
JPanel jpsettingstit;
JPanel jpworkunder;
JPanel jpsettingsunder;
JPanel pnsetbuts;
JPanel worktitlepan;
int lastpanshow = 0;
JPanel admininfopn;
JPanel currworkpn;
JPanel jpworkmain;
JPanel jpsettingsmain;
JLabel lbsettingsmain;
     String strsettings = u.gettext("admintitles", "settings");
     String strunisettings = u.gettext("admintitles", "unisettings");
     String strviewtooltip = u.gettext("adminbuttonsother","viewtooltip");




    ButtonGroup bg2 = new ButtonGroup();
    JLabel settingstitlelabel;
    JLabel worktitlelabel;
JLabel profiletitlelabel;
static Color worksettingpancolor =  u.lighter(Color.gray, 1.3f) ;
JLabel profileinfo_allusers;
JLabel profileinfo_you;
JLabel profileinfo_currstu;
int longestw = -1;
int longestw_uni = -1;
int buttondim;
     JButton btExport;
     JButton btImport;
//javax.swing.Timer stuExportImportTimer;
progress_base progbar;

    javax.swing.Timer findSharedTimer;
    u.doGetStusFromOtherShark wc;
    Thread findSharedThread;
    
    javax.swing.Timer addStudentsTimer;
    addStudents_Work swc;
    Thread addStudentsThread;

    javax.swing.Timer deleteStudentsTimer;
    javax.swing.Timer reloadTimer;
    deleteStudents_Work dsw;
    Thread deleteStudentsThread;


    javax.swing.Timer userImportExportTimer;
    doUserImport_Work dui;
    doUserExport_Work due;
    Thread userImportExportThread;
    JPanel pnButtons;
    JPanel pnButtonsCurrWork;
    JButton btviewedit = u.sharkButton("btviewedit");
    JButton btremove = u.sharkButton("btremove");
    boolean keepRightPanelBlank = false;
    
    jnode usertreeselnodes[];
 //   boolean blockSelection = false;
    String strotherstudents_clickable = u.gettext("admintree", "otherstus_clickable");
    String strotherstudents_clickable_noneyet = u.gettext("admintree", "otherstus_clickable_noneyet");
    String userToDelete;
    String strotherstudents = u.gettext("admintree", "otherstus");
    JScrollPane scroller;
    String userTreeNoSelect[];
    String userExportExtension = "shu";
    
   public admin(){
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-07-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // set blank menu bar for Macs
    if(shark.macOS){
      sharkStartFrame.mainFrame.setJMenuBar(new javax.swing.JMenuBar());
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    
    darkerdefaultcol = u.darker(sharkStartFrame.defaultbg, 0.85f);
    savecourses = sharkStartFrame.courses; // rb 1/2/08
    sharkStartFrame.courses = new String[0]; // rb 1/2/08
//    state = -1;
    student.getadmin();    // make sure up to date  rb 6/2/06
 //   studenttc.putClientProperty("JTree.lineStyle","Angled");
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sharkStartFrame.mainFrame.setupGametree();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         dispose();
      }
      public void windowClosed(WindowEvent e) {
         sharkStartFrame.studentList[sharkStartFrame.currStudent].doupdates(true,false);
         savetree();
         sharkStartFrame.courses = savecourses; // rb 1/2/08
      }
      public void windowActivated(WindowEvent e) {
          if(updateonactivate>=0){
              TreePath tp;
             switch(updateonactivate) {
               case ACTIVATEALL:
                    tp = usertree.getSelectionPath();
                    if(tp!=null)changenodeselection((jnode)tp.getLastPathComponent());
                    break;
               case ACTIVATESETTINGS: 
                   settingspan.set();
                   break;
               case ACTIVATEWORK:
                    tp = usertree.getSelectionPath();
                    jnode jn = null;
                    if(tp!=null)jn = (jnode)tp.getLastPathComponent();
                    if(jn!=null)setupwork(jn, extractname2(jn));
                   break;
             }
             updateonactivate = -1;
          }
          thisadmin.repaint();
        }
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      // enables exiting screen via the ESC key
//      public void windowActivated(WindowEvent e) {
//        if(!u.focusBlock)
//         requestFocus();
//      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    });
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
    this.setResizable(false);
    if(teacher.students == null) teacher.students = new String[0];


    GridBagConstraints grid = new GridBagConstraints();
    grid.fill = GridBagConstraints.BOTH;
    grid.gridx = -1;
    grid.gridy = 0;
    grid.weightx = 1;
    grid.weighty = 1;
    


    boolean ismainadmin = true;
    younode = new jnode(teacher.name);
    younode.setIcon(teacher.teacher?jnode.SUBADMIN:jnode.TEACHER);
    younode.dontcollapse = true;
    younode.okdrag = false;
    String struniversal = u.gettext("admintree", "universal", shark.programName);
    if(ismainadmin){
        useruniversal = new jnode(struniversal);
        useruniversal.setIcon(jnode.MAINPROGRAM);
        useruniversal.addChild(younode);
        useruniversal.dontcollapse = true;
        useruniversal.okdrag = false;
        
        admins_heading = new jnode(shark.wantTeachers?u.gettext("admintree", "otheradmins_net"):u.gettext("admintree", "otheradmins"));
        admins_heading.setIcon(jnode.TEACHER);
        admins_heading.dontcollapse = true;
        admins_heading.okdrag = false;        
        
        otherstunode = new jnode(strotherstudents);
        otherstunode.setIcon(jnode.OTHERSTUS);
        otherstunode.okdrag = false;
        otherstunode.dontcollapse = true;
        
        userTreeNoSelect = new String[]{strotherstudents, admins_heading.get()};
        
        otherstunode_clickable = new jnode(strotherstudents_clickable);
        otherstunode_clickable.setIcon(jnode.OTHERSTUS);
        otherstunode_clickable.okdrag = false;
        useruniversal.addChild(otherstunode);
        otherstunode.addChild(otherstunode_clickable);
//        useruniversal.addChild(admins_heading);
        student.getadmin();    // refresh admin details
        if(teacher.administrator && !teacher.teacher){
            jnode tempn;
            for(int k = 0; k < student.adminlist.length; k++){
                if(student.adminlist[k].equalsIgnoreCase(teacher.name))continue;
                tempn = new jnode(student.adminlist[k]);
                tempn.setIcon(jnode.TEACHER);
                admins_heading.addChild(tempn);
                tempn.okdrag = false;
            }
            for(int k = 0; k < student.teacherlist.length; k++){
                if(student.teacherlist[k].equalsIgnoreCase(teacher.name))continue;
                tempn = new jnode(student.teacherlist[k]);
                tempn.setIcon(jnode.SUBADMIN);
                admins_heading.addChild(tempn);
                tempn.okdrag = false;
            }
        }
        if(admins_heading.getChildCount()>0){
            useruniversal.addChild(admins_heading);
        }
        usermodel = new DefaultTreeModel(useruniversal);
    }
    else{
        usermodel = new DefaultTreeModel(younode);
    }

    currentnode = new jnode(str_curr);
    currentnode.setIcon(jnode.MULTISTU);
    currentnode.dontcollapse = true;
    currentnode.okdrag = false;
    usertree = new dropTree_base(usermodel, currentnode);
    usertree.setRowHeight(-1);
    capturedragger = new dragger_base(thisadmin,new JComponent[]{usertree},new JComponent[]{usertree});
    
    
    younode.addChild(currentnode);
    student.buildstutree(usertree, currentnode, teacher.students, false, true);
 //   usertree.setCellRenderer(new treepainter());
    usertree.setCellRenderer(new treepainter2_base(usertree, -1, null, Color.red, new String[]{useruniversal.get(), admins_heading.get(), otherstunode.get()}));

    usertree.setSelectionRow(ismainadmin?1:0);
    usertree.addTreeWillExpandListener(new TreeWillExpandListener() {
        public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException {
           if(((jnode)ev.getPath().getLastPathComponent()).dontcollapse)
               throw new ExpandVetoException(ev);
        }
        public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException{}
     });
     usertree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
     userroot = (jnode)usermodel.getRoot();
     
     usertree.getModel().addTreeModelListener(new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {

          }
          public void treeNodesInserted(TreeModelEvent e) {
              changedUserTree();

            
          }
          public void treeNodesRemoved(TreeModelEvent e) {
              changedUserTree();

          }
          public void treeStructureChanged(TreeModelEvent e) {
          }
     });






     JPanel treepan = new JPanel(new GridBagLayout());
     JPanel treepan2 = new JPanel(new GridBagLayout());
     rightpan = new JPanel(new GridBagLayout());
     rightpan.setBorder(BorderFactory.createLineBorder(Color.gray, 6));
     JPanel butpan = new JPanel(new GridBagLayout());
     JPanel butpan2 = new JPanel(new GridBagLayout());
     JPanel butpanUpper = new JPanel(new GridBagLayout());
     JPanel butpanLower = new JPanel(new GridBagLayout());



     grid.gridx = 0;
     grid.gridy = -1;


     int sw = sharkStartFrame.mainFrame.getWidth();
     int sh = sharkStartFrame.mainFrame.getHeight();
     buttondim = (sw*14/22)/24;
     int buttonimdim =  buttondim- (buttondim/5);

     lbsettingsmain = new JLabel();
     lbsettingsmain.setBackground(worksettingpancolor);
     lbsettingsmain.setForeground(Color.white);



     adminhelppan = new JPanel(new GridBagLayout());
     adminhelppan.setVisible(false);
     JPanel adminhelptitlepan = new JPanel(new GridBagLayout());
     adminhelpcontentpan = new JPanel(new GridBagLayout());
     adminhelpcontentpan2 = new JPanel();
     JLabel adminhelptitlelabel = new JLabel(u.gettext("admintitles", "help").replaceFirst("%", shark.programName));
     adminhelptitlepan.setBackground(Color.gray);
     adminhelptitlelabel.setForeground(Color.white);
     grid.insets = new Insets(5,10,5,10);

      grid.gridx = -1;
     grid.gridy = 0;
//     adminhelptitlepan.add(adminhelptitlelabel, grid);
     
     admininfopn = new JPanel(new GridBagLayout());
     admininfopn.setBackground(Color.gray);
     JButton admininfobt = u.sharkButton();
     admininfobt.setPreferredSize(new Dimension(buttondim, buttondim));
     admininfobt.setMinimumSize(new Dimension(buttondim, buttondim));
     admininfobt.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
                if(admininfopn.getBackground() == Color.gray){
                    admininfopn.setBackground(Color.white);
                    adminhelpcontentpan.setVisible(true);
                    adminhelpcontentpan2.setVisible(false);
                }
                else{
                    admininfopn.setBackground(Color.gray);
                    adminhelpcontentpan.setVisible(false);
                    adminhelpcontentpan2.setVisible(true);
                }
           }
     });
     Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "infoSQ_il48.png");
     ImageIcon iiinfo = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     admininfobt.setIcon(iiinfo);



grid.weightx = 0;
grid.insets = new Insets(5,10,5,5);
     adminhelptitlepan.add(adminhelptitlelabel, grid);

          grid.insets = new Insets(5,5,5,5);
     admininfopn.add(admininfobt, grid);
     adminhelptitlepan.add(admininfopn, grid);
  //   grid.weightx = 1;
 //    JPanel adminhfiller = new JPanel();
 //    adminhfiller.setOpaque(false);
 //    adminhelptitlepan.add(adminhfiller, grid);

grid.weightx = 1;
          grid.gridx = 0;
     grid.gridy = -1;
     adminhelptitlelabel.setBackground(Color.gray);
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 0;
     adminhelppan.add(adminhelptitlepan, grid);
     grid.weighty = 1;
     adminhelppan.add(adminhelpcontentpan, grid);
     adminhelppan.add(adminhelpcontentpan2, grid);


     adminhelpcontentpan.setVisible(false);
     adminhelpcontentpan2.setVisible(true);
     String adminhelpmess;
     if(teacher.teacher)
        adminhelpmess = u.gettext("adminhelp", "mess1_sub");
     else
        adminhelpmess = u.gettext("adminhelp", "mess1");
     String adminhelpmess2;
     if(teacher.teacher)
        adminhelpmess2 = u.gettext("adminhelp", "mess2_sub");
     else
        adminhelpmess2 = u.gettext("adminhelp", "mess2");

     String addnew = u.gettext("adminbuttonpan", "addnew");
     if(!teacher.teacher)
        adminhelpmess = adminhelpmess.replaceFirst("%", shark.programName);
     adminhelpmess = adminhelpmess.replaceFirst("%", addnew);
     String adminhelpmess3 = u.gettext("adminhelp", "mess3");
     adminhelpmess3 = adminhelpmess3.replaceFirst("%", shark.programName);
     adminhelpmess3 = adminhelpmess3.replaceFirst("%", strotherstudents);
     adminhelpmess3 = adminhelpmess3.replaceFirst("%", struniversal);
     adminhelpmess3 = adminhelpmess3.replaceFirst("%", str_curr);



     JTextArea adminhelpmessta = new JTextArea();
     adminhelpmessta.setEditable(false);
     adminhelpmessta.setWrapStyleWord(true);
     adminhelpmessta.setLineWrap(true);
     adminhelpmessta.setOpaque(false);
     adminhelpmessta.setText(adminhelpmess);

     JTextArea adminhelpmessta2 = new JTextArea();
     adminhelpmessta2.setEditable(false);
     adminhelpmessta2.setWrapStyleWord(true);
     adminhelpmessta2.setLineWrap(true);
     adminhelpmessta2.setOpaque(false);
     adminhelpmessta2.setText(adminhelpmess2);

     JTextArea adminhelpmessta3 = new JTextArea();
     adminhelpmessta3.setEditable(false);
     adminhelpmessta3.setWrapStyleWord(true);
     adminhelpmessta3.setLineWrap(true);
     adminhelpmessta3.setOpaque(false);
     adminhelpmessta3.setText(adminhelpmess3);


     JLabel addstu = new JLabel(u.gettext("adminhelp", "addstu"));
     JLabel addmstu = new JLabel(u.gettext("adminhelp", "addmstu"));
     JLabel addadmin = new JLabel(u.gettext("adminhelp", "addadmin"));
     JLabel addsubadmin = new JLabel(u.gettext("adminhelp", "addsubadmin"));
     JLabel addgroup = new JLabel(u.gettext("adminhelp", "addgroup"));
     adminhelpmessta.setFont(plainfont);
     adminhelpmessta2.setFont(plainfont);
     adminhelpmessta3.setFont(plainfont);
     addstu.setFont(plainfont);
     addmstu.setFont(plainfont);
     addadmin.setFont(plainfont);
     addsubadmin.setFont(plainfont);
     addgroup.setFont(plainfont);

     grid.insets = new Insets(30,30,0,30);
     grid.weighty = 0;
     grid.anchor = GridBagConstraints.NORTH;
     adminhelpcontentpan.add(adminhelpmessta, grid);
     grid.insets = new Insets(30,60,0,30);
     if(!teacher.teacher){
         adminhelpcontentpan.add(addstu, grid);
         adminhelpcontentpan.add(addmstu, grid);
     }
     adminhelpcontentpan.add(addgroup, grid);
     if(!teacher.teacher){
         adminhelpcontentpan.add(addadmin, grid);
         if(shark.wantTeachers)
             adminhelpcontentpan.add(addsubadmin, grid);
     }
     
     grid.insets = new Insets(60,30,0,30);
     adminhelpcontentpan.add(adminhelpmessta3, grid);
     adminhelpcontentpan.add(adminhelpmessta2, grid);
     grid.weighty = 1;
     adminhelpcontentpan.add(new JPanel(), grid);
     grid.anchor = GridBagConstraints.CENTER;


     grid.insets = new Insets(0,0,0,0);

     profilepan = new JPanel(new GridBagLayout());
     JPanel profiletitlepan = new JPanel(new GridBagLayout());
     JPanel profilecontentpan = new JPanel(new GridBagLayout());
     profiletitlelabel = new JLabel();
     profiletitlelabel.setFont(bigfont);
     profiletitlepan.setBackground(Color.gray);
     profiletitlelabel.setForeground(Color.white);
     grid.insets = new Insets(5,10,5,10);

     grid.fill = GridBagConstraints.NONE;
     grid.gridx = -1;
     grid.gridy = 0;
     grid.weightx = 0;

     boolean displayleft = u.screenResWidthMoreThan(600)?false:true;
     profileinfo_allusers = u.infoLabel(u.gettext("adminprofile", teacher.teacher?"allusersinfo_teacher":"allusersinfo"), displayleft);
     profileinfo_you = u.infoLabel(u.gettext("adminprofile", "youinfo"), displayleft);
     profileinfo_currstu = u.infoLabel(u.gettext("adminprofile", "currstuinfo"), displayleft);

    
     
     profiletitlepan.add(profiletitlelabel, grid);
     profiletitlepan.add(profileinfo_allusers, grid);
     profiletitlepan.add(profileinfo_you, grid);
     profiletitlepan.add(profileinfo_currstu, grid);

     profiletitlelabel.setBackground(Color.gray);
     grid.fill = GridBagConstraints.BOTH;
     grid.weightx = 1;
     grid.gridx = 0;
     grid.gridy = -1;
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 0;
     profilepan.add(profiletitlepan, grid);
     grid.weighty = 1;
     grid.insets = new Insets(0,0,getBottomProfileInset(),0);
     profilepan.add(profilecontentpan, grid);
     grid.insets = new Insets(0,0,0,0);
     JPanel profilelabelspan = new JPanel(new GridBagLayout());
     profile_statuscombo = new JComboBox();
     profile_statuscombo.addItem(u.statuses[u.STATUS_ADMIN]);
     if(shark.wantTeachers)
         profile_statuscombo.addItem(u.statuses[u.STATUS_SUBADMIN]);
     profile_statuscombo.addItem(u.statuses[u.STATUS_STUDENT]);
     profile_statuscombo.setEditable(false);
     
     profile_statuscombo.addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
            String s = (String)((JComboBox)e.getSource()).getSelectedItem();
            TreePath tp = usertree.getSelectionPath();
            if(tp==null)return;
            jnode jn = (jnode)tp.getLastPathComponent();
            if(jn==null)return;
            profile_statusbut.setEnabled(jn.type == jnode.TEACHER && !s.equals(u.statuses[u.STATUS_ADMIN])
                    || jn.type == jnode.SUBADMIN && !s.equals(u.statuses[u.STATUS_SUBADMIN])
                    || jn.type == jnode.STUDENT && !s.equals(u.statuses[u.STATUS_STUDENT]));
      }
    });

     profile_status = new JLabel();
     profile_name = new JTextField();
     profile_name.addFocusListener(new FocusListener() {
               public void focusGained(FocusEvent e) {
                    keypad.dofullscreenkeypad(thisadmin, true);
               }
               public void focusLost(FocusEvent e) {
                    keypad.dofullscreenkeypad(thisadmin, false);
               }
     });
     profile_password = new JTextField();
  //   profile_name.setColumns(15);
  //   profile_password.setColumns(15);
     
     profile_statusbut = u.sharkButton();
     profile_statusbut.setFont(plainfont);
     profile_statusbut.setText(str_change);
     profile_statusbut.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
                jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
                String name = extractname2(jn);
                student stu =student.findStudent(name);
                  if(!stu.isLocked()) {
                    if(!stu.getlock()) {
                       u.okmess(u.gettext("stulist_statusloggedon","heading"),u.gettext("stulist_statusloggedon","text",stu.name),thisadmin);
                       return;
                    }
                  }
                String tobe = (String)profile_statuscombo.getSelectedItem();
                boolean toAdmin = tobe.equals(u.statuses[u.STATUS_ADMIN]);
                ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
                if(toAdmin){
                    if (al!=null && ((int)(al.get(0))) >= shark.maxUsers_Admins){
                        u.okmess(shark.programName, u.gettext("maxusers", "fulladmin_create", String.valueOf(shark.maxUsers_Admins)), thisadmin);
                        return;
                    }                  
                }
                else{
                    if (al!=null && ((int)(al.get(1))) >= shark.maxUsers_Students){
                        u.okmess(shark.programName, u.gettext("maxusers", "fullstu_create", String.valueOf(shark.maxUsers_Students)), thisadmin);
                        return;
                    }                   
                } 
                String mess;
//                if(tobe.equals(u.statuses[u.STATUS_ADMIN]))mess = u.gettext("convertuser", "toadmin");
                if(toAdmin)mess = u.gettext("convertuser", "toadmin");
                else if(tobe.equals(u.statuses[u.STATUS_SUBADMIN]))mess = u.gettext("convertuser", "tosubadmin");
                else mess = u.gettext("convertuser", "tostudent");
                mess = mess.replaceFirst("%", jn.get());
                if(u.yesnomess(u.gettext("convertuser", "title"), mess, thisadmin)){
                    student.verifyPassword vp = new student.verifyPassword(teacher);
                    if(!vp.passwordok){
                        if(stu != null) stu.releaselock();
                        return;
                    }
                    String givepassword = null;
                    String givepasswordhint = null;
                    if((!tobe.equals(u.statuses[u.STATUS_STUDENT])) && (stu.password==null || stu.password.trim().equals(""))){
                       jnode jnode2 = (jnode)usertree.getSelectionPath().getLastPathComponent();
                       newUser nu= new newUser(newUser.MODE_PASSWORDONLY_RETURNVAL, thisadmin, jnode2.get(), jnode2.type);
                        if(nu.returnpass==null){
                            if(stu != null) stu.releaselock();
                            return;
                        }
                        else givepassword = student.encrypt(nu.returnpass);
                        if(nu.returnpasswordhint!=null)
                            givepasswordhint = nu.returnpasswordhint;
                    }
                    if(givepassword!=null)stu.password = givepassword;
                    if(givepasswordhint!=null)stu.passwordhint = givepasswordhint;
                    jnode movedto = null;
                    if(tobe.equals(u.statuses[u.STATUS_SUBADMIN])){
                        if(jn.type == jnode.TEACHER){
                            stu.teacher = true;
                        }
                        else{
                            stu.administrator = true;
                            stu.teacher = true;
                            stu.teachers = null;
                            stu.workforteacher = null;
                            db.deleteAll(stu.name,db.PROGRAM);
                        }
                        movedto = admins_heading;
                    }
                    else if(tobe.equals(u.statuses[u.STATUS_ADMIN]))
                    {
                        if(jn.type == jnode.SUBADMIN){
                            stu.teacher = false;
                        }
                        else{
                            stu.administrator = true;
                            stu.teacher = false;
                            stu.teachers = null;
                            stu.workforteacher = null;
                            db.deleteAll(stu.name,db.PROGRAM);
                        }
                        movedto = admins_heading;
                    }
                    else if(tobe.equals(u.statuses[u.STATUS_STUDENT]))
                    {
                        stu.administrator = false;
                        stu.teacher = false;
                        movedto = currentnode;
                    }
                    if(movedto!=null){
                        stu.saveStudent();
                        jn.removeFromParent();
                        jnode jn2 = addtotree(stu, movedto);
                        student.checkadmin(stu, !(jn.type != jnode.STUDENT && !tobe.equals(u.statuses[u.STATUS_STUDENT])));
                        usertree.setSelectionPath(new TreePath(jn2.getPath()));
                        reloadUserModel_delayed();
                    }
                }
                else{
                    setstatuscombo(jn);
                    profile_statusbut.setEnabled(false);

                }
                if(stu != null) stu.releaselock();
           }
     });
     profile_statusbut.setEnabled(false);
     profile_statusbut.setToolTipText(u.gettext("adminbuttonsother", "statuschangetooltip"));

     profile_passwordbut = u.sharkButton();
     profile_passwordbut.setFont(plainfont);
     profile_passwordbut.setText(str_change);
     


     profile_namebut = u.sharkButton();
     profile_namebut.setFont(plainfont);
     profile_namebut.setText(str_save);
     profile_namebut.setToolTipText(u.gettext("adminbuttonsother", "savetooltip"));


     profile_passwordbut.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
                jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
                if(jn != younode){
                    jnode jnode2 = (jnode)usertree.getSelectionPath().getLastPathComponent();
                    new newUser(newUser.MODE_PASSWORDONLY, thisadmin, jnode2.get(), jnode2.type);
                    student stu =student.findStudent(jn.get());
                     if(!stu.isLocked()) {
                        if(!stu.getlock()) {
                           u.okmess(u.gettext("stulist_passwordloggedon","heading"),u.gettext("stulist_passwordloggedon","text",stu.name),thisadmin);
                           return;
                        }
                     }
                    if(stu!=null)
                       stu.doupdates(true,false);
                    if(stu != null) stu.releaselock();
               }
               else{
                    student stu =student.findStudent(jn.get());
                    if (stu != null){
                      stu.changePassword(thisadmin);
                    }
                    sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].password = stu.password;
                    sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].passwordhint = stu.passwordhint;
                }
                changenodeselection(jn);
           }
     });


     profile_namebut.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();           
               student stu =student.findStudent(jn.get());
//               if(stu == null)return;
               if(stu!=null && !stu.isLocked()) {
                   if(!stu.getlock()) {
                       u.okmess(u.gettext("stulist_renameloggedon","heading"),u.gettext("stulist_renameloggedon","text",stu.name),thisadmin);
                       return;
                   }
               }
               
               progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("renaming", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));               
               
               
               final Thread renameAdminThread = new Thread(new doRename_Work(stu, jn));
               renameAdminThread.start();
               javax.swing.Timer renameAdminTimer = new javax.swing.Timer(500, new ActionListener() {
                     public void actionPerformed(ActionEvent e) {
                          if(renameAdminThread.isAlive())return;
                          stopProgressBar();
                          javax.swing.Timer thistimer = (javax.swing.Timer)e.getSource();
                          thistimer.stop();
                          thistimer = null;
                     }
               });
               renameAdminTimer.setRepeats(true);
               renameAdminTimer.start();               
               
               

               reloadUserModel_delayed();
               
               usertree.setSelectionPath(new TreePath(jn.getPath()));
           }
     });

     profile_name.setDocument(new keydoc(profile_name, KeyDoc_base.MAXNAMECHARS, profile_namebut));
     /*
       profile_name.getDocument().addDocumentListener(new DocumentListener() {
             public void changedUpdate(DocumentEvent e){}
             public void insertUpdate(DocumentEvent e){
                 try{
                    String t1  = e.getDocument().getText(0, e.getDocument().getLength());
                    String t2 = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
                    profile_namebut.setEnabled(!t1.equalsIgnoreCase(t2) && !t1.trim().equals(""));
                 }
                 catch(Exception e2){
                     profile_namebut.setEnabled(true);
                 }
             }
             public void removeUpdate(DocumentEvent e){
                 try{
                    String t1  = e.getDocument().getText(0, e.getDocument().getLength());
                    String t2 = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
                    profile_namebut.setEnabled(!t1.equalsIgnoreCase(t2) && !t1.trim().equals(""));
                 }
                 catch(Exception e2){
                     profile_namebut.setEnabled(true);
                 }
             }
          });
*/

     int dimh = (sharkStartFrame.screenSize.height/3)/4;
     dropBox = new mainPan(new Dimension(dimh,dimh));

     grid.fill = GridBagConstraints.NONE;
     grid.gridx = 0;
     grid.gridy = 0;
     grid.anchor = GridBagConstraints.EAST;


     profilestatuslab = new JLabel(u.gettext("adminprofile", "status"));
     profilenamelab = new JLabel(u.gettext("adminprofile", "name"));
     profilepasswordlab = new JLabel(u.gettext("adminprofile", "password"));
     profilestatuslab.setFont(smallerplainfont);
     profilenamelab.setFont(smallerplainfont);
     profilepasswordlab.setFont(smallerplainfont);

     grid.insets = new Insets(0,0,b1,b1);
     profilelabelspan.add(profilenamelab, grid);
     grid.gridy = 1;
     profilelabelspan.add(profilestatuslab, grid);
     grid.gridy = 2;
     grid.insets = new Insets(0,0,0,b1);
     profilelabelspan.add(profilepasswordlab, grid);
     grid.gridx = 1;
     grid.gridy = 0;
     grid.anchor = GridBagConstraints.WEST;
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.insets = new Insets(0,0,b1,b1);
     profilelabelspan.add(profile_name, grid);
     grid.gridy = 1;
     profilelabelspan.add(profile_statuscombo, grid);
     grid.fill = GridBagConstraints.NONE;
     profilelabelspan.add(profile_status, grid);
     grid.gridy = 2;
     grid.insets = new Insets(0,0,0,b1);
     grid.fill = GridBagConstraints.HORIZONTAL;
     profilelabelspan.add(profile_password, grid);
     grid.fill = GridBagConstraints.NONE;
     grid.anchor = GridBagConstraints.WEST;
     grid.gridx = 2;
     grid.gridy = 0;
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.insets = new Insets(0,0,b1,0);
     profilelabelspan.add(profile_namebut, grid);
     grid.gridy = 1;
     profilelabelspan.add(profile_statusbut, grid);
     grid.gridy = 2;
     grid.insets = new Insets(0,0,0,0);
     profilelabelspan.add(profile_passwordbut, grid);
     grid.fill = GridBagConstraints.NONE;



     grid.gridx = 0;
     grid.gridy = -1;

     picpan = new JPanel(new GridBagLayout());
     piclabel = new JLabel(u.gettext("adminprofile", "icon"));


     JPanel boxbutpan = new JPanel(new GridBagLayout());

     JButton boxChoose = u.sharkButton();
     boxChoose.setFont(plainfont);
          boxChoose.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
                new chooseAvatar_base(thisadmin);
           }
     });

     boxChoose.setText(u.gettext("choose", "label"));
     boxChoose.setToolTipText(u.gettext("adminbuttonsother", "choosetooltip"));

     JButton boxBrowse = u.sharkButton();
     boxBrowse.setFont(plainfont);

     boxBrowse.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
             JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(false);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileFilter() {
          public boolean accept(File f) {
            String s = f.getName();
            String ss = s.substring(s.lastIndexOf(".") + 1);
            if (f.isDirectory() ||
                ss.equalsIgnoreCase("jpg") ||
                ss.equalsIgnoreCase("gif") ||
                ss.equalsIgnoreCase("bmp") ||
                ss.equalsIgnoreCase("tif") ||
                ss.equalsIgnoreCase("png")) {
              return true;
            }
            return false;
          }

          public String getDescription() {
            return u.gettext("pickpicture", "allimagefiles");
          }
        });
        int returnVal = fc.showOpenDialog(thisadmin);
        if (returnVal == fc.APPROVE_OPTION) {
          File f[] = fc.getSelectedFiles();
          if (f.length == 0) {
            f = new File[] {fc.getSelectedFile()};
          }
          if (f.length > 0) {
            fileselected(f);
          }
        }
      }

     });

     boxBrowse.setText(u.gettext("browse", "label"));
     boxBrowse.setToolTipText(u.gettext("adminbuttonsother", "browsetooltip"));
     boxDelete = u.sharkButton();
     boxDelete.setFont(plainfont);
     boxDelete.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String n = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
               db.delete(n, PickPicture.ownpic, db.PICTURE);
               db.delete(n, PickPicture.ownpicstuset, db.PICTURE);
               if(n.equalsIgnoreCase(teacher.name)){
                    sharkStartFrame.mainFrame.currUserPic.refresh();
               }
               dropBox.wp.reposition();
               dropBox.wp.addPic(null, n);        
           }
     });

     boxDelete.setText(u.gettext("delete", "label"));
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.weighty = 1;
     grid.insets = new Insets(0,b1,0,0);
     boxbutpan.add(boxChoose, grid);
     boxbutpan.add(boxBrowse, grid);
     boxbutpan.add(boxDelete, grid);
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 0;
     grid.fill = GridBagConstraints.NONE;
     grid.gridx = 0;
     grid.gridy = -1;
     JPanel boxbottompan = new JPanel(new GridBagLayout());
     JPanel boxbottompan2 = new JPanel(new GridBagLayout());
     boxbottompan2.add(piclabel, grid);
     boxbottompan2.add(dropBox, grid);

     grid.gridx = -1;
     grid.gridy = 0;
     boxbottompan.add(boxbottompan2, grid);
     grid.anchor = GridBagConstraints.NORTH;
     grid.fill = GridBagConstraints.VERTICAL;
     boxbottompan.add(boxbutpan, grid);
     grid.fill = GridBagConstraints.NONE;
     grid.anchor = GridBagConstraints.CENTER;
     grid.gridx =0;
     grid.gridy = -1;
     grid.anchor = GridBagConstraints.WEST;
     grid.insets = new Insets(0,0,0,0);
 //    picpan.add(piclabel, grid);
     grid.fill = GridBagConstraints.NONE;
     picpan.add(boxbottompan, grid);
     grid.anchor = GridBagConstraints.CENTER;
     grid.gridx = -1;
     grid.gridy = 0;
     grid.insets = new Insets(15,b2,15,b2);
     grid.weightx = 1;
     profilecontentpan.add(profilelabelspan, grid);
     grid.insets = new Insets(15,0,15,b2);
     profilecontentpan.add(picpan, grid);

     JPanel profilesizer = new JPanel();
     profilesizer.setPreferredSize(new Dimension(1, sh/4));
     profilesizer.setMinimumSize(new Dimension(1, sh/4));


     grid.insets = new Insets(0,0,0,0);

     profilecontentpan.add(profilesizer, grid);
     grid.gridx = 0;
     grid.gridy = -1;

     grid.weightx = 1;
     grid.fill = GridBagConstraints.BOTH;
     settingspan = new settings(thisadmin, true);
 //    dummysettingspan = new JPanel(new GridBagLayout());


     workpan = new JPanel(new GridBagLayout());
     dummyworkpan = new JPanel(new GridBagLayout());
     worktitlepan = new JPanel(new GridBagLayout());
     workcontentpan = new LinedPanel(new GridBagLayout());







     worktitlepan.setBackground(worksettingpancolor);


     String pblbs[] = new String[]{u.gettext("admintitles", "work"), strsettings, strunisettings};

     worktitlelabel = new JLabel(pblbs[0]);
     worktitlelabel.setOpaque(true);

     FontMetrics lbfm = sharkStartFrame.treefontm;

     for(int i = 0; i < pblbs.length; i++){
         int t = lbfm.stringWidth(pblbs[i]);
         if(t>longestw_uni)
             longestw_uni =t;
     }
    longestw_uni+=15;

    pblbs = new String[]{u.gettext("admintitles", "work"), strsettings};
     for(int i = 0; i < pblbs.length; i++){
         int t = lbfm.stringWidth(pblbs[i]);
         if(t>longestw)
             longestw =t;
     }
    longestw+=15;


//    int longestw = -1;
//    int longestw_uni = -1;


     settingstitlelabel = new JLabel(pblbs[1]);
     settingstitlelabel.setOpaque(true);


     grid.gridx = -1;
     grid.gridy = 0;


     grid.weightx = 0;


     grid.fill = GridBagConstraints.NONE;

     jpworkmain = new JPanel(new GridBagLayout());
     jpworkmain.setPreferredSize(new Dimension(longestw, buttondim+10));
     jpworkmain.setMinimumSize(new Dimension(longestw, buttondim+10));

     jpworkunder = new JPanel();
     jpworkunder.setPreferredSize(new Dimension(longestw, 10));
     jpworkunder.setMinimumSize(new Dimension(longestw, 10));

     jpworktit = new JPanel(new GridBagLayout());
     jpworktit.setPreferredSize(new Dimension(longestw, buttondim));
     jpworktit.setMinimumSize(new Dimension(longestw, buttondim));

     jpworktit.add(worktitlelabel, grid);

     jpsettingsmain = new JPanel(new GridBagLayout());
     jpsettingsmain.setPreferredSize(new Dimension(longestw, buttondim+10));
     jpsettingsmain.setMinimumSize(new Dimension(longestw, buttondim+10));

     jpsettingsunder = new JPanel();
     jpsettingsunder.setPreferredSize(new Dimension(longestw, 10));
     jpsettingsunder.setMinimumSize(new Dimension(longestw, 10));

     jpsettingstit = new JPanel(new GridBagLayout());
     jpsettingstit.setPreferredSize(new Dimension(longestw, buttondim));
     jpsettingstit.setMinimumSize(new Dimension(longestw, buttondim));

     grid.insets = new Insets(0,0,0,0);

     jpsettingstit.add(settingstitlelabel, grid);



     jpworktit.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent me) {
            setWorkVisible(0);
        }
     });
     jpsettingstit.addMouseListener(new MouseAdapter() {
        public void mouseReleased(MouseEvent me) {
            setWorkVisible(1);
        }
     });

     grid.gridx = 0;
     grid.gridy = -1;
     jpworkmain.add(jpworktit, grid);
     jpworkmain.add(jpworkunder, grid);

     jpsettingsmain.setBackground(worksettingpancolor);

     
     jpsettingsmain.add(jpsettingstit, grid);
     jpsettingsmain.add(jpsettingsunder, grid);



     grid.gridx = -1;
     grid.gridy = 0;

     pnsetbuts = new JPanel(new GridBagLayout());
     grid.insets = new Insets(0,0,0,0);
     pnsetbuts.setOpaque(false);
     pnsetbuts.add(settingspan.hlsignon, grid);
     pnsetbuts.add(settingspan.hlnoise, grid);
     pnsetbuts.add(settingspan.hlfont, grid);
     pnsetbuts.add(settingspan.hlpicture, grid);
     pnsetbuts.add(settingspan.hlkeypad, grid);
     pnsetbuts.add(settingspan.hlreward, grid);
     pnsetbuts.add(settingspan.hlgame, grid);
     pnsetbuts.add(settingspan.hlcourse, grid);
     pnsetbuts.add(settingspan.hlmisc, grid);
     pnsetbuts.add(settingspan.hluserrecords, grid);
     

  //   jpsettingsunder.setBackground(Color.gray);
     jpworkunder.setBackground(sharkStartFrame.defaultbg);
     pnsetbuts.setVisible(false);

     grid.insets = new Insets(10,10,0,0);
     worktitlepan.add(jpworkmain, grid);
     worktitlepan.add(jpsettingsmain, grid);
     grid.insets = new Insets(0,10,0,0);
     worktitlepan.add(lbsettingsmain, grid);


     grid.insets = new Insets(0,10,0,0);
     grid.anchor = GridBagConstraints.SOUTH;
     worktitlepan.add(pnsetbuts, grid);
     grid.anchor = GridBagConstraints.CENTER;


     grid.weightx = 1;
     grid.fill = GridBagConstraints.BOTH;


     JPanel fillerpan = new JPanel();
     fillerpan.setOpaque(false);
     worktitlepan.add(fillerpan, grid);

     grid.gridx = 0;
     grid.gridy = -1;

     grid.fill = GridBagConstraints.BOTH;
     grid.insets = new Insets(0,0,0,0);
     grid.weighty = 0;
//     workpan.add(worktitlepan, grid);
     grid.weighty = 1;
     workpan.add(workcontentpan, grid);


     lbcurrsetwork = new JLabel(u.gettext("adminwork", "lbcurrsetwork"));
     lbcurrsetwork.setFont(smallerplainfont);
     lbsetwork = new JLabel(u.gettext("adminwork", "lbsetwork"));
     lbsetwork.setFont(smallerplainfont);
     lbuserrecords = new JLabel(u.gettext("adminwork", "userrecords"));
     lbuserrecords.setFont(smallerplainfont);
     lbuserassignments = new JLabel(u.gettext("adminwork", "assignments"));
     lbuserassignments.setFont(smallerplainfont);
//     JLabel currsetwork = new JLabel("27/07/10 15:27 (not started)");
     currsetwork = new JLabel();
     currsetworktype = new JLabel();
     currsetworktype2 = new JLabel();
     currsetworktype.setFont(plainfont);
     currsetworktype2.setFont(plainfont);
     worktype1 = u.RadioButton("worktypes1");
     worktype2 = u.RadioButton("worktypes2");
     String strworktypes2 = worktype2.getText();
     if(u.screenResWidthMoreThan(800))worktype2.setText(strworktypes2.replaceFirst("<br>", " "));
     worktype1.setFont(plainfont);
     worktype2.setFont(plainfont);
     bg2.add(worktype1);
     bg2.add(worktype2);
     worktype1.setSelected(true);

     btviewrecords = u.sharkButton("btview");
     btviewrecords.setFont(plainfont);
     btviewrecords.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
  //              new student.showStudentRecord(thisadmin, new String[]{((jnode)usertree.getSelectionPath().getLastPathComponent()).get()}, younode.get());
               jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
               String name = extractname2(jn);
               if(jn == currentnode || jn.type == jnode.GROUP){
                    jnode node=null,end = (jnode)jn.getLastLeaf();
                    String s[]=null;
                    for(node=(jnode)jn.getFirstLeaf();
                          node != null;
                          node = (jnode)node.getNextLeaf()) {
                       if(node.type ==
                               jnode.STUDENT) s = u.addStringSort(s,node.get());
                       if (node == end) break;
                    }
                    if(s!=null)
                      new student.showStudentRecord(thisadmin, s, jn.get());
                    else
                      u.okmess("adminnostudents", thisadmin); 
               }
               else
                   new student.showStudentRecord(thisadmin, student.findStudent(name));
           }
     });

     btviewassignments = u.sharkButton("btview");
     
     btviewassignments.setFont(plainfont);
     btviewassignments.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
               int k = -1;
               if(jn == currentnode) k = steppedmanaged.assignment_CURR;
               else if(jn == useruniversal) k = steppedmanaged.assignment_UNI;
               new steppedmanaged(thisadmin, u.gettext("adminmanagework", "titleview"), teacher, null, false, true, k);
           }
     });

     JButton btnew = u.sharkButton("btnew");
     btnew.setFont(plainfont);
     btnew.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               savetree();
               String stu[] = getselectedstu();
               if(worktype1.isSelected()){
                   new simpleprogram(thisadmin, teacher.name,
                           stu,
                           isgroup((jnode)usertree.getSelectionPath().getLastPathComponent()),
                           currsetwork.getText().equals(strmix),
                            null,
                            thisadmin, false,false);
               }
               else{
                   new simpleprogram(thisadmin, teacher.name,
                           stu,
                           isgroup((jnode)usertree.getSelectionPath().getLastPathComponent()),
                           currsetwork.getText().equals(strmix),
                            null,
                            thisadmin, true, false);
               }
           }
     });
//     JButton btremove = u.sharkButton("btremove");
     
     btremove.setFont(plainfont);
     btremove.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
               String stus[] = null;
               if(jn.type == jnode.GROUP || jn == currentnode){
                   jnode jns[] = getAllChildren(jn, false);
                   for(int i = 0; jns !=null && i < jns.length; i++){
                       String stuname = extractname2(jns[i]);
                       if(stus==null)stus = new String[]{};
                       stus = u.addString(stus, stuname);
                   }
               }
               else{
                  stus = new String[]{extractname2(jn)};
               }     
               
               String mess = null;
               if(currwork_mixed!=null || currwork!=null){
                    if(stus.length>1)mess = u.gettext("adminwork", "removewarn_p");
                    else mess =  u.gettext("adminwork", "removewarn", stus[0]); 
                    if(!u.yesnomess(shark.programName, mess))return;
               }
               if(currwork_mixed!=null){
                   for(int j = 0; j < currwork_mixed.length; j++){
//                        for(int i = 0; i < stus.length; i++){
                       int k = currwork_mixed[j].indexOf('|');
                       String user = currwork_mixed[j].substring(0, k);
                       String work = currwork_mixed[j].substring(k+1);
                       db.delete(user, work, db.PROGRAM);
//                        }                       
                   }
               }
               else if(currwork!=null){
                   for(int i = 0; i < stus.length; i++){
                       db.delete(stus[i], currwork, db.PROGRAM);
                   }
               }
               changenodeselection((jnode)usertree.getSelectionPath().getLastPathComponent());
           }
     });
     JButton btexisting = u.sharkButton("btexisting");
     btexisting.setToolTipText(u.gettext("adminbuttonsother","existingtooltip"));
     btexisting.setFont(plainfont);
     btexisting.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
               String stus[] = null;
               if(jn.type == jnode.GROUP || jn == currentnode){
                    for(Enumeration en = jn.depthFirstEnumeration();en.hasMoreElements();) {
                        jnode node = (jnode)en.nextElement();
                        if(node.equals(jn))continue;
                        String stuname = extractname2(node);
                        if(stus==null)stus = new String[]{};
                        if(node.type != jnode.GROUP)
                            stus = u.addString(stus, stuname);
                    }
               }
               else{
                  stus = new String[]{extractname2(jn)};
               }
               new steppedmanaged(thisadmin, u.gettext("adminmanagework", "title"), teacher, stus, worktype2.isSelected(), false);
           }
     });
//     JButton btviewedit = u.sharkButton("btviewedit");
     btviewedit.setFont(plainfont);
     btviewedit.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               savetree();
               String stu[] = getselectedstu();
               String shorter = currwork;
               if(shorter.indexOf("[")>=0){
//                 shorter = currwork.substring(0, currwork.indexOf("["+teacher.name+"]"));
                 shorter = currwork.substring(0, u.CaseInsensitiveGetIndexOf(currwork, "["+teacher.name+"]"));
               }
               if(currworktype==0){
                   new simpleprogram(thisadmin, teacher.name, stu,
                           isgroup((jnode)usertree.getSelectionPath().getLastPathComponent()),
                           currsetwork.getText().equals(strmix),
                            shorter,
                            thisadmin, false,false);
               }
               else if(currworktype==1){
                   new simpleprogram(thisadmin, teacher.name, stu,
                           isgroup((jnode)usertree.getSelectionPath().getLastPathComponent()),
                           currsetwork.getText().equals(strmix),
                            shorter,
                            thisadmin, true, false);
               }
           }
     });
     lbyourlist = new JLabel(u.gettext("adminwork", "lbyourwork"));
     lbyourlist.setFont(smallerplainfont);
     btManage = u.sharkButton("btmanage");
     btManage.setToolTipText(u.gettext("adminbuttonsother", "managetooltip"));

     btManage.setFont(plainfont);
     btManage.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {
               new steppedmanaged(thisadmin, u.gettext("adminmanagework", "title"), teacher, null, false, true);
           }
     });



     JPanel workcontentpan2 = new JPanel(new GridBagLayout());
     
     grid.weightx = 1;
     grid.weighty = 1;
     grid.anchor = GridBagConstraints.WEST;
     grid.fill = GridBagConstraints.BOTH;
     pninfolbviewassignments = new JPanel(new GridBagLayout());
     grid.gridx = -1;
     grid.gridy = 0;
     JLabel infolbviewassignments = u.infoLabel(u.gettext("adminwork", "infolabelassignments", shark.programName),!u.screenResWidthMoreThan(1200));
     grid.insets = new Insets(0,10,0,0);
     pninfolbviewassignments.add(infolbviewassignments, grid);
     grid.insets = new Insets(0,0,0,0);
 //    pnbtviewassignments = new JPanel(new GridBagLayout());
 //    pnbtviewassignments.add(btviewassignments, grid);
 //     pnbtviewassignments.add(infolbviewassignments, grid);
      
      grid.gridx = 0;
      grid.gridy = -1;
     workcontentpan.add(workcontentpan2, grid);


     blankpanProSet = new JPanel(new GridBagLayout());
     blankpanSetWor = new JPanel(new GridBagLayout());

        blankpanProSet.setBackground(sharkStartFrame.defaultbg);
    blankpanProSet.setOpaque(true);

          blankpanSetWor.setBackground(sharkStartFrame.defaultbg);
     blankpanSetWor.setOpaque(true);
     
     blankpanProSet.setPreferredSize(new Dimension(10, 1));
      blankpanProSet.setMinimumSize(new Dimension(10, 1));
     blankpanSetWor.setPreferredSize(new Dimension(10, 1));
      blankpanSetWor.setMinimumSize(new Dimension(10, 1));

     int ycurr = 0;

     radioholder = new JPanel(new GridBagLayout());

     grid.fill = GridBagConstraints.NONE;
grid.weightx = 0;



grid.insets = new Insets(0,0,20,0);

     radioholder.add(worktype1, grid);
     grid.insets = new Insets(0,0,0,0);
     radioholder.add(worktype2, grid);



     currworkpn = new JPanel(new GridBagLayout());
     grid.gridx = 0;
     grid.gridy = -1;




     currworkpn.add(currsetwork, grid);
     currworkpn.add(currsetworktype, grid);
     currworkpn.add(currsetworktype2, grid);

     grid.weightx = 1;
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.insets = new Insets(0,0,20,0);
     pnButtons = new JPanel(new GridBagLayout());
     pnButtons.add(btnew, grid);
     grid.insets = new Insets(0,0,0,0);
     pnButtons.add(btexisting, grid);
     grid.insets = new Insets(0,0,20,0);
     pnButtonsCurrWork = new JPanel(new GridBagLayout());
     pnButtonsCurrWork.add(btviewedit, grid);
     grid.insets = new Insets(0,0,0,0);
     pnButtonsCurrWork.add(btremove, grid);
     grid.weightx = 0;

     grid.insets = new Insets(10,20,0,0);
     grid.gridy = ycurr;
     grid.gridx = 0;
     workcontentpan2.add(lbcurrsetwork, grid);
     grid.insets = new Insets(10,10,0,0);
     workcontentpan2.add(lbyourlist, grid);
     grid.gridx = 1;
     workcontentpan2.add(currworkpn, grid);
     grid.gridx = 2;
     grid.fill = GridBagConstraints.HORIZONTAL;
     workcontentpan2.add(pnButtonsCurrWork, grid);
     workcontentpan2.add(btManage, grid);
     grid.gridx = 3;
     grid.insets = new Insets(10,10,0,20);
//     workcontentpan2.add(btremove, grid);
     grid.fill = GridBagConstraints.NONE;
//     grid.gridy = ++ycurr;
//     grid.insets = new Insets(0,10,0,0);
     grid.gridx = 1;
     grid.weighty = 1;


     
//     grid.anchor = GridBagConstraints.NORTHWEST;
//     workcontentpan2.add(currsetworktype, grid);
     grid.anchor = GridBagConstraints.WEST;
     grid.insets = new Insets(10,10,0,10);
     grid.weighty = 1;
     grid.gridy = ++ycurr;
     grid.weighty = 0;
     workcontentpan2.add(blankpanProSet, grid);
     grid.weighty = 1;
     grid.gridy = ++ycurr;
     grid.gridx = 0;
     grid.insets = new Insets(10,20,0,0);
     workcontentpan2.add(lbsetwork, grid);
     grid.gridx = 1;
     // to give a bit more x space between middle and button column
     grid.insets = new Insets(10,10,0,30);
     workcontentpan2.add(radioholder, grid);
     grid.insets = new Insets(10,10,0,0);
     grid.gridx = 2;
     grid.fill = GridBagConstraints.HORIZONTAL;
     workcontentpan2.add(pnButtons, grid);
     grid.gridx = 3;
 //    grid.insets = new Insets(10,10,0,20);
  //   workcontentpan2.add(btexisting, grid);
     grid.fill = GridBagConstraints.NONE;
     grid.gridy = ++ycurr;

     grid.weighty = 0;
     grid.insets = new Insets(10,20,0,0);
     workcontentpan2.add(blankpanSetWor, grid);
     grid.weighty = 1;
     grid.gridy = ++ycurr;
     int savey = grid.gridy;
//     grid.insets = new Insets(10,20,20,10);
     grid.insets = new Insets(0,20,0,0);
     grid.gridx = 0;
     workcontentpan2.add(lbuserrecords, grid);  
     grid.gridy = ++ycurr;
     workcontentpan2.add(lbuserassignments, grid);
     grid.gridx = 2;
     grid.fill = GridBagConstraints.HORIZONTAL;
  //   grid.insets = new Insets(10,10,20,0);
     grid.insets = new Insets(10,10,10,0);
     grid.gridy = savey;
     workcontentpan2.add(btviewrecords, grid);
     grid.insets = new Insets(10,10,10,0);
     grid.gridy = ++savey;
     
     
     workcontentpan2.add(btviewassignments, grid);
     
     grid.gridx = 3;
     workcontentpan2.add(pninfolbviewassignments, grid);
     
     

     grid.fill = GridBagConstraints.NONE;
     grid.weightx = 1;
     grid.insets = new Insets(0,0,0,0);


     treepan.setPreferredSize(new Dimension(sw*9/22, sh));
  //   treepan.setMinimumSize(new Dimension(sw*8/22, sh));
     rightpan.setPreferredSize(new Dimension((rightwidth=sw*13/22), sh));
     rightpan.setMinimumSize(new Dimension(rightwidth, sh));


     JButton but_addstu = u.sharkButton();
     but_addstu.setToolTipText(u.gettext("adminbuttonpan", "butaddstutooltip"));
     JButton but_addmultstu = u.sharkButton();
     but_addmultstu.setToolTipText(u.gettext("adminbuttonpan", "butaddmultstutooltip"));
     JButton but_admin = u.sharkButton();
     but_admin.setToolTipText(u.gettext("adminbuttonpan", "butaddadmintooltip", shark.programName));
     JButton but_subadmin = u.sharkButton();
     but_subadmin.setToolTipText(u.gettext("adminbuttonpan", "butaddsubadmintooltip"));
     JButton but_subgroup = u.sharkButton();
     but_subgroup.setToolTipText(u.gettext("adminbuttonpan", "butaddgrouptooltip"));
     but_remove = u.sharkButton();
     but_remove.setToolTipText(u.gettext("adminbuttonpan", "butremovetooltip").replaceFirst("%", shark.programName));

     JButton multi_remove = u.sharkButton();
     multi_remove.setToolTipText(u.gettext("adminbuttonpan", "butmultiremovetooltip").replaceFirst("%", shark.programName));

     JButton but_vids = u.sharkButton();
     but_vids.setToolTipText(u.gettext("videotutorials", "admintooltip"));

     if(shark.macOS){
      exit.setForeground(Color.red);
     }
     else{
      exit.setBackground(Color.red);
      exit.setForeground(Color.white);
     }


     but_addstu.setPreferredSize(new Dimension(buttondim, buttondim));
     but_addstu.setMinimumSize(new Dimension(buttondim, buttondim));
     but_addmultstu.setPreferredSize(new Dimension(buttondim, buttondim));
     but_addmultstu.setMinimumSize(new Dimension(buttondim, buttondim));
     but_admin.setPreferredSize(new Dimension(buttondim, buttondim));
     but_admin.setMinimumSize(new Dimension(buttondim, buttondim));
     but_subadmin.setPreferredSize(new Dimension(buttondim, buttondim));
     but_subadmin.setMinimumSize(new Dimension(buttondim, buttondim));
     but_subgroup.setPreferredSize(new Dimension(buttondim, buttondim));
     but_subgroup.setMinimumSize(new Dimension(buttondim, buttondim));
     but_remove.setPreferredSize(new Dimension(buttondim, buttondim));
     but_remove.setMinimumSize(new Dimension(buttondim, buttondim));
     multi_remove.setPreferredSize(new Dimension(buttondim, buttondim));
     multi_remove.setMinimumSize(new Dimension(buttondim, buttondim));
     but_vids.setPreferredSize(new Dimension(buttondim, buttondim));
     but_vids.setMinimumSize(new Dimension(buttondim, buttondim));

     but_addstu.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
                if (al!=null && ((int)(al.get(1))>=shark.maxUsers_Students)){
                    u.okmess(shark.programName, u.gettext("maxusers", "fullstu", String.valueOf(shark.maxUsers_Students)), thisadmin);
                    return;
                }
               TreePath tp = usertree.getSelectionPath();
               jnode jnode2 = tp==null?currentnode:(jnode)tp.getLastPathComponent();
               new newUser(newUser.MODE_NEWSTU, thisadmin, jnode2.get(), jnode2.type);
          }
     });
     but_addmultstu.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               new MultStuOptions(thisadmin);
          }
     });
     but_admin.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
               if (al!=null && ((int)(al.get(0))>=shark.maxUsers_Admins)){
                   u.okmess(shark.programName, u.gettext("maxusers", "fulladmin", String.valueOf(shark.maxUsers_Admins)), thisadmin);
                   return;
               }
               TreePath tp = usertree.getSelectionPath();
               jnode jnode2 = tp==null?useruniversal:(jnode)tp.getLastPathComponent();
               new newUser(newUser.MODE_NEWADMIN, thisadmin, jnode2.get(), jnode2.type);
          }
     });
     but_subadmin.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               TreePath tp = usertree.getSelectionPath();
               jnode jnode2 = tp==null?useruniversal:(jnode)tp.getLastPathComponent();
               new newUser(newUser.MODE_NEWSUBADMIN, thisadmin, jnode2.get(), jnode2.type);
          }
     });
     but_subgroup.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               newgroup();
          }
     });
     but_remove.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               removeusers();
          }
     });
     multi_remove.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               removemultiusers();
          }
     });
     but_vids.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String base = u.gettext("videotutorials", "basesite", shark.getProgramShortName());
               new TutorialChoice_base(thisadmin,
                  new String[]{u.gettext("videotutorials", "addstudent"), 
                        u.gettext("videotutorials", "setwork")},
                  new String[]{base + u.gettext("mvidaddstu", "url"),
                        base + u.gettext("mvidsetwork", "url")});
           }
     });
     exit.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               dispose();
          }
     });

     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "student_il48.png");
     ImageIcon iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     but_addstu.setIcon(iistu);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "allstudents_il48.png");
     ImageIcon iimstu = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     but_addmultstu.setIcon(iimstu);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "admin_il48.png");
     ImageIcon iiadmin = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     but_admin.setIcon(iiadmin);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "subadmin_il48.png");
     ImageIcon iisubadmin = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     but_subadmin.setIcon(iisubadmin);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "group_il48.png");
     ImageIcon iigroup = new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH));
     but_subgroup.setIcon(iigroup);
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "deleteON_il48.png");
     but_remove.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "deletestudents_il48.png");
     multi_remove.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "video_il48.png");
     but_vids.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));
     addstu.setIcon(iistu);
     addmstu.setIcon(iimstu);
     addadmin.setIcon(iiadmin);
     addsubadmin.setIcon(iisubadmin);
     addgroup.setIcon(iigroup);


     mlabel_base treeheading = u.mlabel("admintreeheading");
     String ss1 = treeheading.getText();
     ss1 = ss1.replaceFirst("%", shark.programName);
     ss1 = ss1.replaceFirst("%", struniversal);
     treeheading.setText(ss1);
     usertree.getSelectionModel().addTreeSelectionListener((tsl=new
        TreeSelectionListener() {
          public void valueChanged(TreeSelectionEvent e) {
   //           if(blockSelection)
   //               return;
              TreePath tps[] = usertree.getSelectionPaths();
              boolean isCapturing = false;
              for(int i = 0; tps!=null && i < tps.length; i++){
                  
                  jnode sel = (jnode)tps[i].getLastPathComponent();
 //                 if(otherstunode.equals(sel) || admins_heading.equals(sel)){
                  if(u.findString(userTreeNoSelect, sel.get())>=0 && sel.getLevel()==1){
                       usertree.removeTreeSelectionListener(tsl);
     //                 blockSelection = true;
                      usertree.setSelectionPath(e.getOldLeadSelectionPath());
     //                 blockSelection = false;
                      usertree.addTreeSelectionListener(tsl);
                      return;
                  }
                  if(sel.equals(otherstunode_clickable))isCapturing = true;
              }
              TreePath tp = e.getNewLeadSelectionPath();
              if(tp==null)return;
              jnode jn = (jnode)tp.getLastPathComponent();
              if(jn==null)return;
              changenodeselection(isCapturing?otherstunode_clickable:jn);
//              tp = e.getOldLeadSelectionPath();
//              if(tp==null)return;
//              jn = (jnode)tp.getLastPathComponent();
//              if(jn==younode){
//                 sharkStartFrame.studentList[sharkStartFrame.currStudent].doupdates(true,false);
//              }
          }
     }));


       usertree.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseReleased(MouseEvent e) {
            if(capturedragger.droppedon != null) {
               capturedragger.droppedon = capturedragger.draggedfrom = null;
            }
            if(! Arrays.equals(savetree(currentnode,new String[0]), teacher.students)){
                savetree();
             }            
            if(usertreeselnodes!=null){
              TreePath usertreeselpaths[] = new TreePath[]{};
              for(int i = 0; i < usertreeselnodes.length; i++){
                 usertreeselpaths = u.addTreePaths(usertreeselpaths, new TreePath[]{new TreePath(usertreeselnodes[i].getPath())});
              }                
              usertree.setSelectionPaths(usertreeselpaths);
              usertreeselnodes = null;
            }          
            keepRightPanelBlank = false;
            setRightPanelBlank(false);
           }
          public void mousePressed(MouseEvent e) {
              TreePath usertreeselpaths[] = usertree.getSelectionPaths();
              if(usertreeselpaths!=null && usertreeselpaths.length>1){
                  for(int i = 0; i < usertreeselpaths.length; i++){
                      if(usertreeselnodes==null)usertreeselnodes = new jnode[]{};
                      usertreeselnodes = u.addnode(usertreeselnodes, (jnode)usertreeselpaths[i].getLastPathComponent());
                  }
                  setRightPanelBlank(true, true);
              }
              
              usertree.getSelectionModel().removeTreeSelectionListener(tsl);
              usertree.getSelectionModel().addTreeSelectionListener(tsl);
           }
       });

     but_remove.setEnabled(false);


     movestus.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              usertree.allowmovenode = movestus.isSelected();
           }
     });
     usertree.allowmovenode = false;



     JPanel labpan = new JPanel(new GridBagLayout());
     grid.insets = new Insets(5,10,5,10);
     labpan.add(treeheading, grid);
     grid.insets = new Insets(0,0,0,0);
     labpan.setBackground(cream);
     treeheading.setBackground(cream);
     treeheading.setFont(u.screenResWidthMoreThan(1000)?plainfont:smallerplainfont);

     grid.gridx = 0;
     grid.gridy = -1;
     grid.fill = GridBagConstraints.BOTH;
     grid.weighty = 0;

     
     grid.anchor = GridBagConstraints.CENTER;
     grid.weighty = 1;
     treepan.add(treepan2, grid);
     grid.fill = GridBagConstraints.NONE;

     int vmargin = 10;
    int vmarginbig = 30;

     grid.insets = new Insets(0,0,0,0);
     butpanUpper.add(new JLabel(addnew), grid);
     grid.insets = new Insets(vmargin,0,0,0);
     if(!teacher.teacher){
        grid.insets = new Insets(vmargin,0,0,0);
        butpanUpper.add(but_addstu, grid);
        grid.insets = new Insets(0,0,0,0);
        JLabel newstulb = new JLabel(u.statuses[u.STATUS_STUDENT]);
        newstulb.setFont(evensmallerplainfont);
        butpanUpper.add(newstulb, grid);

        if(!shark.licenceLimitsUsers()){
            grid.insets = new Insets(vmargin,0,0,0);
            butpanUpper.add(but_addmultstu, grid);
            grid.insets = new Insets(0,0,0,0);
            JLabel newmstulb = new JLabel(u.convertToHtml(u.statuses[u.STATUS_MULTIPLESTUDENT].replace(' ', '|'), true));
            newmstulb.setFont(evensmallerplainfont);
            butpanUpper.add(newmstulb, grid);
        }
        grid.insets = new Insets(vmargin,0,0,0);
        butpanUpper.add(but_admin, grid);
        grid.insets = new Insets(0,0,0,0);
        JLabel newadminlb = new JLabel(u.statuses[u.STATUS_ADMIN]);
        newadminlb.setFont(evensmallerplainfont);
        butpanUpper.add(newadminlb, grid);
        if(shark.wantTeachers){
            grid.insets = new Insets(vmargin,0,0,0);
            butpanUpper.add(but_subadmin, grid);
            grid.insets = new Insets(0,0,0,0);
            JLabel newteacherlb = new JLabel(u.statuses[u.STATUS_SUBADMIN]);
            newteacherlb.setFont(evensmallerplainfont);
            butpanUpper.add(newteacherlb, grid);
         }  
     }
     if(!shark.licenceLimitsUsers()){
         grid.insets = new Insets(vmargin,0,0,0);
         butpanUpper.add(but_subgroup, grid);
         grid.insets = new Insets(0,0,0,0);
         JLabel newgrouplb = new JLabel(u.statuses[u.STATUS_GROUP]);
         newgrouplb.setFont(evensmallerplainfont);
         butpanUpper.add(newgrouplb, grid);
     }
     grid.insets = new Insets(vmarginbig,0,0,0);
     butpanUpper.add(new JLabel(u.gettext("adminbuttonpan", "remove")), grid);
     grid.insets = new Insets(vmargin,0,0,0);
     butpanUpper.add(but_remove, grid);
     grid.insets = new Insets(0,0,0,0);
     JLabel deletelb = new JLabel(u.gettext("adminbuttonpan", "delusers"));
     deletelb.setFont(evensmallerplainfont);
     butpanUpper.add(deletelb, grid);
//     if(!teacher.teacher){
     if(!teacher.teacher && !shark.licenceLimitsUsers()){
         grid.insets = new Insets(vmargin,0,0,0);
         butpanUpper.add(multi_remove, grid);
         grid.insets = new Insets(0,0,0,0);
         JLabel multideletelb = new JLabel(u.convertToHtml(u.gettext("adminbuttonpan", "redundantusers"), true));
         multideletelb.setFont(evensmallerplainfont);
         butpanUpper.add(multideletelb, grid);
     }
     if(shark.wantTutVids){
         mlabel_base vidlab = u.mlabel("");
         vidlab.setText(u.gettext("adminbuttonpan", "vids"));
         grid.insets = new Insets(0,0,vmargin,0);
         butpanLower.add(vidlab, grid);
         grid.insets = new Insets(0,0,0,0);
         butpanLower.add(but_vids, grid);
        grid.insets = new Insets(vmarginbig,0,0,0);
         butpanUpper.add(butpanLower, grid);
     }
     grid.insets = new Insets(vmargin,0,vmargin,0);
     grid.fill = GridBagConstraints.HORIZONTAL;
     butpanLower.add(exit, grid);
     grid.fill = GridBagConstraints.NONE;
     grid.insets = new Insets(0,0,0,0);
     grid.anchor = GridBagConstraints.NORTH;
     grid.weighty = 1;
     grid.gridx = 0;
     grid.gridy = 0;
     grid.insets = new Insets(10,0,0,0);
     butpan2.add(butpanUpper, grid);
     grid.insets = new Insets(0,0,0,0);
     grid.gridx = 0;
     grid.gridy = 1;
     grid.anchor = GridBagConstraints.SOUTH;
     butpan2.add(exit, grid);
     grid.anchor = GridBagConstraints.CENTER;
     grid.insets = new Insets(10,10,10,10);
     grid.fill = GridBagConstraints.BOTH;
     butpan.add(butpan2, grid);
     grid.insets = new Insets(0,0,0,0);

     treepan2.setBackground(Color.white);
     grid.gridx = -1;
     grid.gridy = 0;
     grid.fill = GridBagConstraints.BOTH;
     grid.weightx = 0;
     treepan2.add(butpan, grid);
     grid.weightx = 1;

     grid.fill = GridBagConstraints.BOTH;
  
     JPanel treepan3 = new JPanel(new GridBagLayout());

     scroller = new JScrollPane(usertree);
     scroller.setAutoscrolls(true);
     scroller.setBorder(BorderFactory.createEmptyBorder());
     scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

//     JPanel jp = new JPanel(new GridBagLayout());
     grid.fill = GridBagConstraints.NONE;
//     grid.insets = new Insets(10,0,20,0);
//     jp.add(movestus, grid);
     

     grid.gridx = 0;
     grid.gridy = -1;
     
grid.weighty = 0;
grid.insets = new Insets(0,0,0,0);
      grid.anchor = GridBagConstraints.WEST;
      grid.fill = GridBagConstraints.HORIZONTAL;
     treepan3.add(labpan, grid);

     JPanel btarrows = new JPanel(new GridBagLayout());
     grid.fill = GridBagConstraints.NONE;
     btExport = u.sharkButton();
     btExport.setToolTipText(u.gettext("adminexportuser", "tooltip", shark.programName + " " + shark.versionNo));
    // btExport.setToolTipText("test1");
     btImport = u.sharkButton();
     btImport.setToolTipText(u.gettext("adminimportuser", "tooltip", shark.programName));
     

     
     btExport.addActionListener( new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            TreePath tp[] = usertree.getSelectionPaths();
            String users[] = new String[]{};
            for(int k = 0; k < tp.length; k++){
              jnode jn = (jnode)tp[k].getLastPathComponent();
              if(!jn.isLeaf() && jn!=younode){
                  for(Enumeration en = jn.depthFirstEnumeration();en.hasMoreElements();) {
                     jnode node = (jnode)en.nextElement();
                     if(node.equals(jn) || !node.isLeaf())continue;
                     String s = node.get();
                     if(u.findString(users, s)<0)users = u.addString(users, s);
                  }
              }
              else{
                  String s = jn.get();
                  if(u.findString(users, s)<0)users = u.addString(users, s);
              }
            }
            new exportuser(thisadmin, users);
        }
     });
     btImport.addActionListener( new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
            if (al!=null && ((int)(al.get(1))>=shark.maxUsers_Students) && ((int)(al.get(0))>=shark.maxUsers_Admins)){
                u.okmess(shark.programName, u.edit(u.gettext("maxusers", "fulluser"), new String[]{String.valueOf(shark.maxUsers_Students), String.valueOf(shark.maxUsers_Admins)}), thisadmin);
                return;
            }
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(true);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileFilter() {
          public boolean accept(File f) {
            String s = f.getName();
            String ss = s.substring(s.lastIndexOf(".") + 1);
            if (f.isDirectory() ||
                ss.equalsIgnoreCase(userExportExtension)) {
              return true;
            }
            return false;
          }

          public String getDescription() {
            return u.gettext("adminimportuser", "chooserdescription");
          }
        });
        int returnVal = fc.showOpenDialog(thisadmin);
        if (returnVal == fc.APPROVE_OPTION) {
          File toimport[] = fc.getSelectedFiles();
          if (toimport.length == 0) {
            toimport = new File[] {fc.getSelectedFile()};
          }




                        progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));

          
        userImportExportThread = new Thread(dui = new doUserImport_Work(toimport));
        userImportExportThread.start();
        userImportExportTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(userImportExportThread.isAlive())return;
                                    stopProgressBar();
                                    userImportExportTimer.stop();
                                    userImportExportTimer = null;
                                    dui = null;
                                    userImportExportThread = null;
                              }
                        });
                        userImportExportTimer.setRepeats(true);
                        userImportExportTimer.start();
            }
        }
     });
     btExport.setPreferredSize(new Dimension(buttondim, buttondim));
     btExport.setMinimumSize(new Dimension(buttondim, buttondim));
     btImport.setPreferredSize(new Dimension(buttondim, buttondim));
     btImport.setMinimumSize(new Dimension(buttondim, buttondim));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "export_il48.png");
     btExport.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "import_il48.png");
     btImport.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));
     grid.gridx = -1;
     grid.gridy = 0;
     grid.weightx = 0;
     int b = 10;
     grid.insets = new Insets(b,b,b,0);
     if(!shark.licenceLimitsUsers())
         btarrows.add(movestus, grid);
     grid.weightx = 1;
     btarrows.add(new JPanel(), grid);
     grid.weightx = 0;

     grid.insets = new Insets(b,0,b,b);

     if(!teacher.teacher){
//        JLabel lbexport = new JLabel("Export");
        JLabel lbexport = new JLabel(u.gettext("export", "label"));
        lbexport.setFont(smallerplainfont);
        btarrows.add(lbexport, grid);
        btarrows.add(btExport, grid);
//         JLabel lbimport = new JLabel("Import");
         JLabel lbimport = new JLabel(u.gettext("import", "label"));
         lbimport.setFont(smallerplainfont);
         btarrows.add(lbimport, grid);
         btarrows.add(btImport, grid);
     }
     grid.gridx = 0;
     grid.gridy = -1;
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.weightx = 1;

grid.fill = GridBagConstraints.BOTH;
grid.weighty = 1;
     grid.insets = new Insets(10,10,0,0);
     treepan3.add(scroller, grid);
     grid.weighty = 0;
     grid.insets = new Insets(0,0,0,0);
     if(btarrows.getComponentCount()>1)
         treepan3.add(btarrows, grid);
     grid.anchor = GridBagConstraints.CENTER;
//     treepan3.add(jp, grid);
     
     grid.weighty = 1;
     grid.gridx = -1;
     grid.gridy = 0;
     grid.insets = new Insets(0,0,0,0);
     treepan2.add(treepan3, grid);
     treepan3.setBorder(BorderFactory.createLineBorder(Color.black, 1));
     treepan3.setBackground(Color.white);

     blankpan = new JPanel(new GridBagLayout());
     capturepan = new JPanel(new GridBagLayout());
     JPanel capturetitlepan = new JPanel(new GridBagLayout());
     JLabel capturetitlelabel = new JLabel(u.gettext("admintitles", "capture"));
     capturetitlepan.setBackground(Color.gray);
     capturetitlelabel.setForeground(Color.white);
     grid.insets = new Insets(5,10,5,10);
     grid.fill = GridBagConstraints.NONE;
     capturetitlepan.add(capturetitlelabel, grid);
     grid.fill = GridBagConstraints.BOTH;



     capturetitlelabel.setBackground(Color.gray);
     capturetitlelabel.setOpaque(true);
     JTextArea capturemessta = new JTextArea();
     capturemessta.setEditable(false);
     capturemessta.setWrapStyleWord(true);
     capturemessta.setLineWrap(true);
     capturemessta.setOpaque(false);
     capturemessta.setText(u.convertToCR(u.gettext("admincapturemess", "label")));
//     mlabel_base capturemesslabel = u.mlabel_base("admincapturelab");
//     capturemesslabel.setBackground(cream);

     // admincapture
     capturetitlelabel.setFont(bigfont);
     JPanel capturemesspan = new JPanel(new GridBagLayout());
     capturemesspan.setBackground(cream);
     capturemesspan.add(capturemessta, grid);
     grid.insets = new Insets(0,0,0,0);
     JPanel capturetreepan = new JPanel(new GridBagLayout());
     
     
     jnode captureroot = new jnode(strotherstudents);
     captureroot.setIcon(jnode.MULTISTU);
     captureroot.dontcollapse = true;      
     capturemodel = new DefaultTreeModel(captureroot);
     capturetree = new dropTree_base(capturemodel, currentnode);
     capturetree.addTreeWillExpandListener(new TreeWillExpandListener() {
        public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException {
           if(((jnode)ev.getPath().getLastPathComponent()).dontcollapse)
               throw new ExpandVetoException(ev);
        }
        public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException{}
     });
     

     capturemodel.addTreeModelListener(new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {
          }
          public void treeNodesInserted(TreeModelEvent e) {

          }
          public void treeNodesRemoved(TreeModelEvent e) {

          }
          public void treeStructureChanged(TreeModelEvent e) {
            if(capturemodel.getChildCount(capturemodel.getRoot())==0){
                TreePath tps[] = usertree.getSelectionPaths();
                if(otherstunode.getParent()!=null)
                    usermodel.removeNodeFromParent(otherstunode);
              usertree.getSelectionModel().removeTreeSelectionListener(tsl);
              usertree.getSelectionModel().addTreeSelectionListener(tsl);
       //         usertree.setSelectionPath(new TreePath(currentnode.getPath()));
                usertree.setSelectionPaths(tps);
            }
            if(capturemodel.getChildCount(capturemodel.getRoot())>0 && !useruniversal.isNodeChild(otherstunode)){
                jnode nps[] = getNodes(usertree.getSelectionPaths());
                useruniversal.insert(otherstunode, 1);
                reloadUserModelNow();
                usertree.setSelectionPaths(getTreePaths(nps));
            }
          }
     });



     capturedragger = new dragger_base(thisadmin,new JComponent[]{capturetree},new JComponent[]{usertree});
     capturedragger.mode = new int[]{capturedragger.MODE_SELNEARESTDIR, capturedragger.MODE_HIDENODEEND};
     capturedragger.setActiveDestinations(new JComponent[]{usertree});


     capturetree.addMouseListener( new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            TreePath tp = capturetree.getSelectionPath();
            if(tp==null)return;
            jnode jn = ((jnode)tp.getLastPathComponent());
            if(jn==null)return;
            if(jn == capturemodel.getRoot())return;
            String sel = jn.get();
            if(sel != null) {
                if(e.getClickCount() == 2) {
                    usertree.getSelectionModel().removeTreeSelectionListener(tsl);
                    addstudent();
         //           usertree.getSelectionModel().addTreeSelectionListener(tsl);
                }
                else{
                    usertree.getSelectionModel().removeTreeSelectionListener(tsl);
                    jnode jns[] = null;
                    for(int i = 0; i < usertree.getRowCount(); i++){
                        TreePath tn = (TreePath)usertree.getPathForRow(i);
                        if(tn!=null){
                           jnode j = (jnode)tn.getLastPathComponent();
                           if(j.type == jnode.OTHERSTUS){
                               usertree.keepRowHighlighted = i;
                           }
                           else if(j.type == jnode.GROUP || j.type == jnode.MULTISTU){
                               if(jns == null) jns = new jnode[]{j};
                               else jns = u.addnode(jns, j);
                           }
                        }
                    }
                    if(jns!=null)
                        usertree.selectNearestNodes = jns;
                    capturedragger.startmotion(capturetree, e);
                }
            }
        }
        public void mouseReleased(MouseEvent e) {
            if(capturedragger.getCursor().equals(java.awt.dnd.DragSource.DefaultCopyDrop)){
                TreePath ts[] = capturetree.getSelectionPaths();
                for(int i = 0; i < ts.length; i++){
                    addstudent();
                }
            }
            else if(usertree.selectNearestNodes!=null)
                usertree.setSelectionPath(new TreePath(otherstunode_clickable.getPath()));
            usertree.selectNearestNodes = null;
            usertree.getSelectionModel().addTreeSelectionListener(tsl);
            usertree.keepRowHighlighted = -1;
            capturedragger.stopmotion(capturetree, e);
        }
     });
        capturetree.addMouseMotionListener( new MouseMotionAdapter() {
          public void mouseDragged(MouseEvent e) {
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(capturedragger, e.getID(), e.getWhen(),
                                           e.getModifiers(), e.getX(), e.getY(),
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
           // make usertree autoscroll while dragging
           float factor = (float)usertree.getHeight()/(float)scroller.getHeight();
           Point pp = SwingUtilities.convertPoint(capturetree, e.getX(), e.getY(), scroller);
           Rectangle r = new Rectangle(pp.x, (int)(pp.y*factor), 1, 1);
           usertree.scrollRectToVisible(r);           
          }
        });

     setstudentlc();
     capturetree.setCellRenderer( capturerenderer = new treepainter2_base(capturetree, rightwidth, new int[]{treepainter2_base.MODE_CAPTURE}, Color.red));
     capturetree.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
     JScrollPane capturescroll = new JScrollPane(capturetree);
     capturescroll.setBorder(BorderFactory.createEmptyBorder());
     capturescroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     capturescroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
     capturetreepan.setBackground(Color.white);
     grid.insets = new Insets(10,10,0,0);
     grid.weightx = 1;
     capturetreepan.add(capturescroll, grid);



     grid.fill = GridBagConstraints.BOTH;

     grid.insets = new Insets(0,0,0,0);
     grid.gridx = 0;
     grid.gridy = -1;

     grid.weighty = 1;
     capturepan.setVisible(false);
     blankpan.setVisible(false);
     rightpan.add(blankpan, grid);
     rightpan.add(capturepan, grid);
     rightpan.add(adminhelppan, grid);
     grid.weighty = 0;
     capturepan.add(capturetitlepan, grid);
     capturepan.add(capturemesspan, grid);
     grid.weighty = 1;
     capturepan.add(capturetreepan, grid);

     rightpan.setBackground(Color.gray);
     rightpan.setOpaque(true);
     grid.weighty = 0;
     rightpan.add(profilepan, grid);

  
//     grid.weighty = getWeightYForWorkScreen();


     grid.weighty = 0;
      rightpan.add(worktitlepan, grid);  
      grid.weighty = 1;
     rightpan.add(workpan, grid);
     rightpan.add(dummyworkpan, grid);
     rightpan.add(settingspan, grid);

/*
    grid.weighty = getWeightYForSettingScreen();
     rightpan.add(settingspan, grid);
     rightpan.add(dummysettingspan, grid);
*/




 
        reloadTimer = new javax.swing.Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doReloadUserTree();              
                reloadTimer.stop();
            }
        });
        reloadTimer.setRepeats(true);
        





     grid.gridx = -1;
     grid.gridy = 0;
     grid.weighty =1;
     grid.weightx =1;
     getContentPane().setLayout(new GridBagLayout());
     getContentPane().add(treepan,grid);
     getContentPane().add(rightpan,grid);
//     if(capturemodel.getChildCount(capturemodel.getRoot())==0){
//        usermodel.removeNodeFromParent(otherstunode);
//     }
     if(currentnode.getChildCount() == 0){
         currentnode.setUserObject(str_currnone);
     }
     reloadUserModel_delayed();
     if(currentnode.getChildCount() == 0){
         usertree.setSelectionPath(new TreePath(currentnode.getPath()));
     }
     else{
        usertree.setSelectionPath(new TreePath(younode.getPath()));
        String n = younode.get();
        dropBox.wp.addPic(null, n);
     }

     changenodeselection((jnode)usertree.getSelectionPath().getLastPathComponent());
     validate();
     setVisible(true);
     


     


     

/*
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        student.buildstutree(studenttc, root, teacher.students,false);
 //    student.buildstutree(studenttc, root, teacher.students,false, true);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    root.setIcon(jnode.TEACHER);
    studenttc.expandRow(0);
    studenttc.getSelectionModel().setSelectionMode(DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
    studentlc.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
    currstu2 = root;
    studentlc.setCellRenderer(new stupainter());    // rb 8/2/06
    studenttc.setSelectionRow(0);
    studentlc.setSelectedIndex(0);
    setstudentlc();
    lab1.setBackground(Color.green);
    getContentPane().setLayout(layout0);
    panel1.setLayout(layout1);
    panel2.setLayout(layout2);
    panel3.setLayout(layout3);
    panel3a.setLayout(layout3a);
    panel3b.setLayout(layout3b);
    panel3c.setLayout(layout3c);
    panel3a.setBorder(BorderFactory.createTitledBorder(
                  BorderFactory.createLineBorder(Color.red,2),
                  u.gettext("stulist_groupa","title"),
                  TitledBorder.CENTER, TitledBorder.BELOW_TOP));
    ((TitledBorder)panel3a.getBorder()).setTitleColor(Color.red);
    panel3a.setBackground(new Color(255,192,192));
    groupmess.setBackground(panel3a.getBackground());
    panel3b.setBorder(BorderFactory.createTitledBorder(
                  BorderFactory.createLineBorder(Color.blue,2),
                  u.gettext("stulist_groupb","title"),
                  TitledBorder.CENTER, TitledBorder.BELOW_TOP));
    ((TitledBorder)panel3b.getBorder()).setTitleColor(Color.red);
    panel3b.setBackground(new Color(192,192,255));
    panel3c.setBorder(BorderFactory.createTitledBorder(
                  BorderFactory.createLineBorder(Color.green,2),
                  u.gettext("stulist_groupc","title"),
                  TitledBorder.CENTER, TitledBorder.BELOW_TOP));
    ((TitledBorder)panel3c.getBorder()).setTitleColor(Color.red);
    panel3c.setBackground(new Color(210,255,210));
//startPR2004-07-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // if running on a Macintosh
     if (shark.macOS)
       exit.setForeground(Color.red);
    // if running on Windows
     else{
       exit.setBackground(Color.red);
       exit.setForeground(Color.white);
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    grid.fill = GridBagConstraints.BOTH;
    grid.gridx = -1;
    grid.gridy = 0;
    grid.gridheight = 1;
    grid.weighty = 1;
    grid.weightx = 1;

    bg.add(newgroup);
    bg.add(dragstu);
    bg.add(stuoptions);
    if(teacher.students == null || teacher.students.length == 0) {
       newstudent.setBackground(Color.white);
       newstudent.setOpaque(true);
    }
    bg.add(newstudent);
    bg.add(addstudent);
    bg.add(removestudent);
    bg.add(renamestudent);
    bg.add(record);
    bg.add(assignprog);
    bg.add(excludegames);
    bg.add(assignprog2);
    bg.add(removeprog);
//    bg.add(seepassword);                // rb 27/10/06
    bg.add(newadmin);
    bg.add(oldadmin);
    bg.add(newteacher);
    bg.add(oldteacher);
    bg.add(dummy);

    panel1.setMinimumSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width/4,sharkStartFrame.mainFrame.screenSize.height*7/8));
    panel2.setMinimumSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width*5/12,sharkStartFrame.mainFrame.screenSize.height*7/8));
    panel3.setMinimumSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width/3,sharkStartFrame.mainFrame.screenSize.height*7/8));
    panel1.setPreferredSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width/4,sharkStartFrame.mainFrame.screenSize.height*7/8));
    panel2.setPreferredSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width*5/12,sharkStartFrame.mainFrame.screenSize.height*7/8));
    panel3.setPreferredSize(new Dimension(sharkStartFrame.mainFrame.screenSize.width/3,sharkStartFrame.mainFrame.screenSize.height*7/8));
    addtree(Color.gray);  // put tree in panel 2
    grid.gridx=-1;
    grid.gridy=0;
    grid.weighty=1;
    getContentPane().add(panel3,grid);
    getContentPane().add(panel2,grid);
    getContentPane().add(panel1,grid);
    grid.gridx = 0;
    grid.gridy = -1;
    grid.fill = GridBagConstraints.NONE;
    grid.weighty = 0;
    grid.gridheight = 1;
    grid.weighty=1;
    grid.fill = GridBagConstraints.BOTH;
    if(!teacher.teacher) panel3a.add(newstudent,grid);
    panel3a.add(removestudent,grid);
    panel3a.add(renamestudent,grid);
//    panel3a.add(seepassword,grid);    // rb 27/10/06
    panel3a.add(newgroup,grid);
    panel3a.add(groupmess,grid);
    if(studentlc.getModel().getSize()>0) panel3a.add(addstudent,grid);
    panel3a.add(dragstu,grid);
    panel3a.add(stuoptions,grid);

    panel3b.add(record,grid);
    panel3b.add(assignprog,grid);
    panel3b.add(assignprog2,grid);
    panel3b.add(removeprog,grid);
    panel3b.add(excludegames,grid);

    panel3c.add(newadmin,grid);
    panel3c.add(oldadmin,grid);
    panel3c.add(newteacher,grid);
    panel3c.add(oldteacher,grid);
//    panel3c.add(dummy,grid);

    panel3.add(panel3a,grid);
    panel3.add(panel3b,grid);
    if(!teacher.teacher) panel3.add(panel3c,grid);
    grid.fill = GridBagConstraints.NONE;
    panel3.add(exit,grid);
    studenttc.setToggleClickCount(4);
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gamesjlist.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == e.VK_DELETE)
          delgameline();
      }
    });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    gamesjlist.addMouseListener(new java.awt.event.MouseAdapter() {
     public void mouseReleased(MouseEvent e) {
        if(dragger_base.droppedon != null && dragger_base.droppedon == gamesjlist
           && dragger_base.draggedfrom == gameTree) {
          dragger_base.droppedon = dragger_base.draggedfrom = null;
          addgameline();
        }
      }
   });
   studentlc.addMouseListener( new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
         int sel[] = studentlc.getSelectedIndices();
         if(sel.length > 0) {
             currstu1 =  sel[0];
             switch(state) {
               case OLDADMIN: oldadmin(); break;
               case OLDTEACHER: oldteacher(); break;
               case ADDSTUDENT: addstudent(); break;
             }
         }
      }
    });
    programlc.addMouseListener( new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        String pname = (String)programlc.getSelectedValue();
        if(pname != null)
             new program(teacher.name,
//startPR2010-02-19^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  new String[]{teacher.name},
                  new String[]{teacher.name}, null,
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  pname,thisadmin);
      }
    });
    studenttc.addMouseListener(new MouseAdapter() {
       public void mouseReleased(MouseEvent e) {
        Point pt = e.getPoint();
        TreePath tp = studenttc.getPathForLocation(pt.x,pt.y);
        if(tp == null) return;
        jnode node = (jnode)tp.getLastPathComponent();
        if(node== null) node = root;
        if(pt.x > ((treepainter)studenttc.getCellRenderer()).getHorizontalTextPosition()
               - ((treepainter)studenttc.getCellRenderer()).getIconTextGap()
//               - ((treepainter)studenttc.getCellRenderer()).getIcon().getIconWidth()
               +  studenttc.getPathBounds(new TreePath(node.getPath())).x) {
          currstu2 = node;
          switch(state) {
           case ADDSTUDENT:   setstudentlc(); addlist(); break;
           case NEWGROUP: newgroup(); break;
           case DRAGSTU: dragstu(); break;
           case STUOPTIONS: stuoptions(); break;
           case NEWSTUDENT: newstudent(); break;
           case REMOVESTUDENT: removestudent(); break;
           case RENAMESTUDENT: renamestudent(); break;
           case STUDENTRECORD: studentrecord(); break;
           case STUDENTREMOVEP:  studentremoveprog(); break;
           case STUDENTEXCLUDEG:  setgametree(); break;
           case STUDENTPASSWORD: studentpassword(); break;
           case STUDENTASSIGNP:  newsimple(); break;
           case STUDENTASSIGNP2:
             if(e.getModifiers() == e.BUTTON3_MASK)     newprog(true);
             else newprog(false);
             break;
        }
        }
      }
    });
    exit.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
           newteacher();
    //     dispose();
      }
    });
    addgame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         addgameline();
      }
    });
    addgameh.setForeground(Color.white);
    addgameh.setBackground(Color.blue);
    delgame.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
         delgameline();
      }
    });
 //startPR2004-08-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    // enables exiting screen via the ESC key
     studenttc.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           dispose();
       }
     });
     studentlc.addKeyListener(new KeyAdapter() {
       public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE)
           dispose();
       }
     });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(KeyEvent e) {
         int code = e.getKeyCode();
         if(code == KeyEvent.VK_ESCAPE) dispose();
      }
    });
    dummy.setSelected(true);
    root.setUserObject(teacher.name);
    setVisible(true);
    undobutton = u.mbutton("stulist_undo");
//startPR2006-12-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    undobutton.setForeground(Color.white);
//    undobutton.setBackground(Color.magenta);
    if (shark.macOS)
      undobutton.setForeground(Color.magenta);
    else{
      undobutton.setForeground(Color.white);
      undobutton.setBackground(Color.magenta);
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    undobutton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(!undos.isEmpty()) {
           undo un = (undo) (undos.lastElement());
           undos.removeElement(un);
           db.update(un.pupil, un.name, un.prog, db.PROGRAM);
           db.update(un.pupil, "_refresh", "", db.TEXT);
           treestripprograms();  // re-display
           addbothprograms();
           if(undos.isEmpty()) undobutton.setEnabled(false);
        }
       }
    });
    */
  }


    jnode[] getNodes(TreePath tps[]){
        if(tps==null)return null;
        jnode nps[] = new jnode[]{};
        for(int i = 0; i < tps.length; i++){
            nps = u.addnode(nps, (jnode)tps[i].getLastPathComponent());
        }
        return nps;
    }


     TreePath[] getTreePaths(jnode nps[]){
        if(nps==null)return null;
        TreePath[] tps = new TreePath[]{};
        for(int n = 0; n < nps.length; n++){
            tps = u.addTreePaths(tps, new TreePath[]{new TreePath(nps[n].getPath())});
        }
        return tps;
     }



public void iconchosen(byte[] im, String name) {
    String n = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
    db.delete(n, PickPicture.ownpicstuset, db.PICTURE);
    db.update(n, PickPicture.ownpic, im, db.PICTURE);
    if(n.equalsIgnoreCase(teacher.name)){
        if(sharkStartFrame.mainFrame.currUserPic!=null)
            sharkStartFrame.mainFrame.currUserPic.refresh();
    }
    dropBox.wp.addPic(null, n);
}


void reloadUserModel_delayed(){
    reloadTimer.start();
}

void reloadUserModelNow(){
    doReloadUserTree();
}

void doReloadUserTree(){
     TreePath tps[] = usertree.getSelectionPaths();
     usermodel.reload();
     usertree.expandAll((TreeNode)usermodel.getRoot());
     usertree.setSelectionPaths(tps);    
}




//void makeSettingsVisible(boolean visible){
//    settingspan.setVisible(visible);
//    dummysettingspan.setVisible(!visible);
//}
//void makeWorkVisible(boolean visible){
//     workpan.setVisible(visible);
//    dummyworkpan.setVisible(!visible);
//}

void stopProgressBar(){
//    stuExportImportTimer.stop();
    if(progbar!=null){
       progbar.dispose();
       progbar=null;
    }
}

void setProgressBarVisible(boolean visible){
    if(progbar!=null){
       progbar.setVisible(visible);
    }
}

void changedUserTree(){
    if(currentnode.getChildCount()>0 && !currentnode.getUserObject().equals(str_curr)){
        currentnode.setUserObject(str_curr);
    }
    else if(currentnode.getChildCount() == 0 && !currentnode.getUserObject().equals(str_currnone))
    {
        currentnode.setUserObject(str_currnone);
    }
    if(admins_heading.getChildCount()>0 && admins_heading.getParent()==null ){
        useruniversal.insert(admins_heading, useruniversal.getChildCount());
    }
    else if(admins_heading.getChildCount() == 0 && admins_heading.getParent() != null){
        useruniversal.remove(admins_heading);
    }
    otherstunode_clickable.setUserObject(currentnode.getChildCount()==0?strotherstudents_clickable_noneyet:strotherstudents_clickable);
    if(currentnode.getChildCount()>0 && !currentnode.getUserObject().equals(str_curr)){
        currentnode.setUserObject(str_curr);
    }
    if(currentnode.getChildCount()==0 && !currentnode.getUserObject().equals(str_currnone)){
        currentnode.setUserObject(str_currnone);
    }
}



void changenodeselection(jnode jn){
    if(keepRightPanelBlank)return;
    if(u.findString(userTreeNoSelect, jn.get())>=0 && jn.getLevel()==1)return;
              currwork = null;
              currwork_mixed = null;
              currworktype = -1;
              if(jn.type==jnode.GROUP){
                  usertree.expandPath(new TreePath(jn.getPath()));
              }
              String name = extractname2(jn);
              nodename = name;
 //             student stu =student.findStudent(name);
              picpan.setVisible(teacher.teacher && (jn.type==jnode.STUDENT || jn==younode) ||
                      (jn.type==jnode.STUDENT || jn.type==jnode.TEACHER|| jn.type==jnode.SUBADMIN) );
              dropBox.wp.stop();
              profiletitlelabel.setText(name);

              profileinfo_allusers.setVisible(jn == useruniversal);
              profileinfo_you.setVisible(jn == younode);
              profileinfo_currstu.setVisible(jn == currentnode);
              
              btviewrecords.setToolTipText(jn == younode?strviewtooltip:null);
              btExport.setEnabled(jn == younode || (jn.isNodeAncestor(currentnode) || jn.isNodeAncestor(admins_heading)));
              int childcount = currentnode.getChildCount();
              movestus.setEnabled(childcount>1 || (childcount==1 && !((jnode)currentnode.getChildAt(0)).isLeaf()));
              if(jn == currentnode && currentnode.getChildCount()==0){
                  adminhelppan.setVisible(true);
                  capturepan.setVisible(false);
                  profilepan.setVisible(false);
                  setWorkVisible(-1);
                  capturepan.setVisible(false);
              }
              else if(jn == otherstunode_clickable)
              {
                  adminhelppan.setVisible(false);
                  capturepan.setVisible(false);
                  profilepan.setVisible(false);
                  setWorkVisible(-1);
                  capturepan.setVisible(true);
              }
              else{
                  adminhelppan.setVisible(false);
                  profilepan.setVisible(true);
                  capturepan.setVisible(false);
                  if(jn == useruniversal){
                      setWorkVisible(0);
                  }
                if(((jn.type == jnode.TEACHER || jn.type == jnode.SUBADMIN) && jn != younode) ||
                          (jn.type==jnode.GROUP && jn.getChildCount()==0)){
                    setWorkVisible(-2);
                }
                else{
                    setWorkVisible(lastpanshow);
                }
              }
              TreePath sels[] = usertree.getSelectionPaths();
              if(sels!=null && sels.length==1 && picpan.isVisible()){
                  String n = jn.get();
                  dropBox.wp.addPic(null, n);
              }
              boolean othersel = false;
              boolean universalsel = false;
              boolean yousel = false;
//              boolean currentsel = false;
              for(int i = 0; sels!=null && i < sels.length; i++){
                  if(((jnode)sels[i].getLastPathComponent()).equals(otherstunode_clickable)){
                      othersel = true;
                      continue;
                  }
                  if(((jnode)sels[i].getLastPathComponent()).equals(useruniversal)){
                      universalsel = true;
                      continue;
                  }
                  if(((jnode)sels[i].getLastPathComponent()).equals(younode)){
                      yousel = true;
                      continue;
                  }
                  if(((jnode)sels[i].getLastPathComponent()).equals(currentnode)){
//                      currentsel = true;
                      continue;
                  }
              }
              but_remove.setEnabled(!yousel && !universalsel && !othersel);
              
              
              if((sels.length>1 && !(sels.length==2 && othersel))&& !capturepan.isVisible()){
                    setRightPanelBlank(true);
//                    dummysettingspan.setVisible(false);
//                    setWorkVisible(false);
//                    dummyworkpan.setVisible(false);
                    return;
              }
              else {
                  setRightPanelBlank(false);

              }



 //            lbuserrecords.setVisible(jn==younode);



              profile_name.setVisible(jn!=useruniversal&&jn!=currentnode);
              profilenamelab.setVisible(jn!=useruniversal&&jn!=currentnode);
              profile_namebut.setVisible(jn!=useruniversal&&jn!=currentnode&&jn!=younode);
              profile_name.setEditable(jn!=younode);
              profilepasswordlab.setVisible(jn!=useruniversal&&jn!=currentnode&&jn.type!=jnode.GROUP);
              profile_password.setVisible(jn!=useruniversal&&jn!=currentnode&&jn.type!=jnode.GROUP);
              profile_passwordbut.setVisible(jn!=useruniversal&&jn!=currentnode&&jn.type!=jnode.GROUP);
              
              boolean admin = (teacher.administrator && !teacher.teacher);
              boolean isuser = (jn.type == jnode.TEACHER ||jn.type == jnode.SUBADMIN ||jn.type == jnode.STUDENT);
              
              profile_statuscombo.setVisible(admin && isuser && jn!=younode);
              profile_status.setVisible(!profile_statuscombo.isVisible());
              profile_statusbut.setVisible(profile_statuscombo.isVisible());
                  if(jn == useruniversal){
                      profile_status.setText(u.statuses[u.STATUS_UNIVERSAL]);
                  }
                  else if(jn == currentnode){
                      profile_status.setText(u.statuses[u.STATUS_CURRENT]);
                  }
                  if(jn.type == jnode.TEACHER){
                      profile_status.setText(u.statuses[u.STATUS_ADMIN]);
                  }
                  else if(jn.type == jnode.SUBADMIN){
                      profile_status.setText(u.statuses[u.STATUS_SUBADMIN]);
                  }
                  else if(jn.type == jnode.STUDENT){
                      profile_status.setText(u.statuses[u.STATUS_STUDENT]);
                  }
                  else if(jn.type == jnode.GROUP){
                      // spaces to extend the group textbox
                      profile_status.setText(u.statuses[u.STATUS_GROUP]+"                               ");

                  }
              if(admin){
                  setstatuscombo(jn);
              }

              profile_name.setText(jn.get());
              profile_namebut.setEnabled(false);
              profile_password.setEditable(false);
              
              student stu = null;
              if(jn.type == jnode.TEACHER || jn.type == jnode.SUBADMIN || jn.type == jnode.STUDENT)
                stu =student.findStudent(jn.get());
              if(stu!=null){
                  stu.doupdates(true,false);
                  if(stu.password==null){
                      profile_passwordbut.setText(str_add);
                      profile_password.setText("");
                  }
                  else{
                      profile_passwordbut.setText(str_change);
                      String userpass = student.decrypt(stu.password);
//                     if(jn==younode){
//                        profile_password.setText(userpass);
//                      }
//                      else{
                        String pass = "";
                        while(pass.length()<userpass.length())
                          pass += "*";
                        profile_password.setText(pass);
//                      }
                  }
                  profile_passwordbut.setText(stu.password==null?str_add:str_change);
              }
              
//              but_remove.setEnabled(jn != currentnode && jn != younode
//                        && jn != useruniversal && jn != otherstunode);

              




              //work

              setupwork(jn, name);




              String stus[] = new String[]{};
              if(jn == useruniversal){
                  settingspan.set(new String[0],jn.get(),true);
              }
              else{
                  if(jn==currentnode || jn.type == jnode.GROUP){
                      stus = getstudentlist();
                  }
                 else{
                    stus = new String[]{jn.get()};
                 }
                 if(stus!=null && stus.length>0)
                  settingspan.set(stus,nodename,  false, stu);
              }


              rightpan.validate();
}
/*
void expandAll( JTree tree, TreeNode node, TreePath path ) {
    tree.expandPath( path );
    int i = node.getChildCount( );
    for ( int j = 0; j< i; j++ ) {
        TreeNode child = node.getChildAt( j );
        if(child instanceof jnode && ((jnode)child).type==jnode.GROUP)continue;
        expandAll( tree, child , path.pathByAddingChild( child ) );
    }
}
*/


void setstatuscombo(jnode jn){
                      if(jn == useruniversal){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_UNIVERSAL]);
                  }
                  else if(jn == currentnode){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_CURRENT]);
                  }
                  if(jn.type == jnode.TEACHER){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_ADMIN]);
                  }
                  else if(jn.type == jnode.SUBADMIN){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_SUBADMIN]);
                  }
                  else if(jn.type == jnode.STUDENT){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_STUDENT]);
                  }
                  else if(jn.type == jnode.GROUP){
                      profile_statuscombo.setSelectedItem(u.statuses[u.STATUS_GROUP]);
                  }
}


 void setupwork(jnode jn, String name){

     
     int longw = jn == useruniversal?longestw_uni:longestw;
     jpworkmain.setPreferredSize(new Dimension(longw, buttondim+10));
     jpworkmain.setMinimumSize(new Dimension(longw, buttondim+10));
     jpworkunder.setPreferredSize(new Dimension(longw, 10));
     jpworkunder.setMinimumSize(new Dimension(longw, 10));
     jpworktit.setPreferredSize(new Dimension(longw, buttondim));
     jpworktit.setMinimumSize(new Dimension(longw, buttondim));
     jpsettingsmain.setPreferredSize(new Dimension(longw, buttondim+10));
     jpsettingsmain.setMinimumSize(new Dimension(longw, buttondim+10));
     jpsettingsunder.setPreferredSize(new Dimension(longw, 10));
     jpsettingsunder.setMinimumSize(new Dimension(longw, 10));
     jpsettingstit.setPreferredSize(new Dimension(longw, buttondim));
     jpsettingstit.setMinimumSize(new Dimension(longw, buttondim));





            currsetworktype2.setVisible(false);

         jpsettingstit.setVisible(true);
         jpsettingsunder.setVisible(true);
     

              if(jn == useruniversal && teacher.teacher){
         jpsettingstit.setVisible(false);
         jpsettingsunder.setVisible(false);
              }


              String strworrktype = null;
              String worktype = null;
              jpworkmain.setVisible(true);
              jpsettingsmain.setVisible(true);

              lbsettingsmain.setVisible(false);
 //             lbsettingsmain.setText(jn == useruniversal?strunisettings:strsettings);

              settingstitlelabel.setText(jn == useruniversal?strunisettings:strsettings);
//              if(jn == useruniversal){
//                    worktitlelabel.setToolTipText(u.gettext("adminbuttonsother", "worktooltip"));
//                    settingstitlelabel.setToolTipText(u.gettext("adminbuttonsother", "settingstooltip"));
//              }
//              else {
//                    worktitlelabel.setToolTipText(null);
//                    settingstitlelabel.setToolTipText(null);
//              }


              int tlp = lastpanshow;
              if(!jpworkmain.isVisible()){
                  setWorkVisible(1);
                  lastpanshow = tlp;
             
              }
 

              if(jn.type == jnode.GROUP || jn == currentnode){
                  jnode node = jn;
                    String psname;
                    String savepsname= null;
                    boolean somenull = false;
                    boolean someset = false;
                    for(Enumeration e = node.depthFirstEnumeration();e.hasMoreElements();) {
                       node = (jnode)e.nextElement();
                       if(node.equals(jn))continue;
                       if(node.type == jnode.GROUP || node==currentnode)continue;
                       String nodename = node.get();
                       psname = program.getProgramName(nodename, teacher.name);
                       if(psname!=null){
                           String nn = nodename+"|"+psname;
                           if(currwork_mixed == null)currwork_mixed = new String[]{nn};
                           else if(u.findString(currwork_mixed, nn)<0) currwork_mixed = u.addString(currwork_mixed, nn);
                            someset = true;
                           if(savepsname == null || !savepsname.equals(psname)){
                               if(savepsname==null)
                                savepsname = psname;
                               else{
                                   strworrktype = strmix;
//                                   break;
                               }
                           }
                        }
                        else{
                            somenull = true;
                        }
                    }
                    if(somenull && someset)
                        strworrktype = strmix;
                    if(savepsname!=null && strworrktype==null && node.getChildCount()>0){
                        String sn = extractname2((jnode)node.getChildAt(0));
                        program.saveprogram setprogram = (program.saveprogram)db.find(sn, savepsname, db.PROGRAM);
                        if(setprogram!=null){
                            if(setprogram.simple){
                                currworktype = 0;
                                currsetworktype.setText(setworktypes[0]);
                            }
                            else{
                                currworktype = 1;
                                currsetworktype.setText(setworktypes[1]);
                            }
                        }
                        strworrktype = savepsname;
                        currwork = savepsname;
//                        if((strworrktype.indexOf("["+teacher.name+"]"))>=0)
//                            strworrktype = strworrktype.substring(0, strworrktype.indexOf("["+teacher.name+"]"));
                        if((u.CaseInsensitiveGetIndexOf(strworrktype, "["+teacher.name+"]"))>=0)
                            strworrktype = strworrktype.substring(0, u.CaseInsensitiveGetIndexOf(strworrktype, "["+teacher.name+"]"));
                    }
              }
            else{
              String progs[] = db.list(name,db.PROGRAM);                  
              for (int i=0;i<progs.length;++i) {
                program.saveprogram pp = null;
                try{pp = (program.saveprogram)db.find(name,progs[i],db.PROGRAM);}
                catch(ClassCastException e){pp=null;}
                if(pp==null){
                    db.delete(name,progs[i],db.PROGRAM);
                    continue;
                }
//                if(progs[i].endsWith("["+teacher.name+"]") || progs[i].endsWith(teacher.name)){
                if(u.CaseInsensitiveEndsWith(progs[i], "["+teacher.name+"]") || 
                        u.CaseInsensitiveEndsWith(progs[i], teacher.name)){
                    String shorter = progs[i];
                    if(shorter.indexOf("[")>=0){
//                       shorter = progs[i].substring(0, progs[i].indexOf("["+teacher.name+"]"));
                       shorter = progs[i].substring(0, u.CaseInsensitiveGetIndexOf(progs[i], "["+teacher.name+"]"));
                    }
                    if(pp.simple){
                        worktype = setworktypes[0];
                        currworktype = 0;
                    }
                    else{
                        worktype = strSteppedSinglular;
                        currworktype = 1;
                    }
                    String ss = getProgramAddOn(pp, progs[i]);
                    String s1 =worktype;// + (ss==null?"":" "+ss);
                    
                    if(ss!=null){
                        currsetworktype2.setVisible(true);
                        currsetworktype2.setText(ss);
                    }
                    currsetworktype.setText(s1);
                    strworrktype = shorter;
                    currwork = progs[i];
                    break;
                }
              }
              if(worktype==null){
                  currwork = null;
              }
            }
            if(strworrktype == null){
                  strworrktype = strnone;
            }
            currsetwork.setText(strworrktype);
            if((strworrktype.equals(strnone) || strworrktype.equals(strmix))){
                currsetwork.setFont(plainfont);
            }
            else{
                currsetwork.setFont(bigfont);
            }

            pninfolbviewassignments.setVisible(jn == useruniversal);
             lbcurrsetwork.setVisible(jn!=younode && jn!=useruniversal);
             lbyourlist.setVisible(jn==younode && jn!=useruniversal);
             currsetwork.setVisible(jn!=younode && jn!=useruniversal);
             btManage.setVisible(jn==younode && jn!=useruniversal);
             lbsetwork.setVisible(jn!=younode && jn!=useruniversal);
             radioholder.setVisible(jn!=younode && jn!=useruniversal);
             pnButtons.setVisible(jn!=younode && jn!=useruniversal);
//             btexisting.setVisible(jn!=younode && jn!=useruniversal);


              btviewassignments.setVisible(jn == useruniversal || jn==currentnode);
              lbuserassignments.setVisible(jn == useruniversal || jn==currentnode);
              btviewrecords.setVisible(jn.type == jnode.STUDENT || jn==younode || jn==currentnode || jn.type==jnode.GROUP);
              lbuserrecords.setVisible(jn.type == jnode.STUDENT || jn==younode || jn==currentnode || jn.type==jnode.GROUP);

              currsetworktype.setVisible(!strworrktype.equals(strnone) && !strworrktype.equals(strmix) && jn != useruniversal && jn!=younode);
//              pnButtonsCurrWork.setVisible(!strworrktype.equals(strnone) && !strworrktype.equals(strmix) && jn != useruniversal && jn!=younode);
              btviewedit.setVisible(!strworrktype.equals(strnone) && !strworrktype.equals(strmix) && jn != useruniversal && jn!=younode);
              btremove.setVisible(!strworrktype.equals(strnone) && jn != useruniversal && jn!=younode);
              pnButtonsCurrWork.setVisible(btviewedit.isVisible()||btremove.isVisible());
              
              if(lastnode!=null && !lastnode.equals(name)){
                worktype1.setSelected(true);
              }
              lastnode = name;
              workcontentpan.pnlines = new JPanel[]{radioholder.isVisible()?blankpanProSet:null,
                          (jn != useruniversal  &&  (btviewrecords.isVisible()||btviewassignments.isVisible()))?blankpanSetWor:null};

              currworkpn.setVisible(currsetwork.isVisible() || currsetworktype.isVisible());

 }
  //--------------------------------------------------------------
  String extractname(jnode node) {
      if(node == null || node.type != jnode.STUDENT && node.type > 0)
                   return null;
      return extractname2(node);
  }
  //--------------------------------------------------------------
  String extractname2(jnode node) {
      if(node == null)
                   return null;
      String name = node.get();
      int i;
      if((i=name.indexOf(topicTree.ISTOPIC)) >= 0) {
         return name.substring(0,i);
      }
      else return name;
  }
  //----------------------------------------------------------------------
  boolean aregroups() {
    jnode c[] = currentnode.getChildren();
    for (short i=0;i<c.length;++i) {
      if(c[i].type != jnode.STUDENT) return true;
    }
    return false;
  }

    //----------------------------------------------------------------------
    String[] getstudentlist() {
      jnode node,next,startnode;
      TreePath oldp = usertree.getSelectionPath();
      String s,name,list[] = new String[0];
      startnode = (jnode)oldp.getLastPathComponent();
      for ( node=(jnode)startnode.getFirstLeaf(); node != null && startnode.isparentof(node.get());node=next) {
        next = (jnode) node.getNextLeaf();
        if (node.type == jnode.STUDENT) {
          name = node.get();
          list = u.addString(list, node.get());
        }
      }
      return list;
//        return null;
     }

    //-------------------------------------------------------------------
    void addstudent() {    // capture a student
         int i;
         String olds;
         TreePath tps[] = capturetree.getSelectionPaths();
         if(tps == null || tps.length<1)return;

         TreePath treeps[] = usertree.getSelectionPaths();
         TreePath activetp = new TreePath(currentnode.getPath());
         for(i = 0; treeps.length>1 && i < treeps.length; i++){
             jnode jn = (jnode)treeps[i].getLastPathComponent();
             if(jn.type == jnode.GROUP || jn.type == jnode.MULTISTU){
                 activetp = treeps[i];
                 break;
             }
         }
         for(int k = 0; k < tps.length; k++){
             String s = olds = ((jnode)tps[k].getLastPathComponent()).get();
             if((i=s.indexOf(topicTree.ISTOPIC))>0) s = s.substring(0,i);
             student stu = student.findStudent(s);
             if(stu == null) {
               stu = new student();
               stu.name = s;
               stu.saveStudent();
               u.okmess(u.gettext("stucorrupt", "heading"),
                        u.gettext("stucorrupt", "message", s));
             }
             if(stu.which  == 'N') {
              stu= new student();
              stu.name = s;
              stu.saveStudent();
              u.okmess(u.gettext("stucorruptws", "heading"),
                       u.edit(u.gettext("stucorruptws", "message"), s, shark.otherProgram)
                       );
            }
             if(stu.administrator) student.checkadmin(stu);   // rb 6/2/06
             else if(stu!=null) {
                  TreePath treeps1[] = new TreePath[]{ new TreePath(addtotree(stu,((jnode)activetp.getLastPathComponent())).getPath())};
                  treeps = u.addTreePaths(treeps, treeps1);
                  usertree.setSelectionPaths(treeps);
             }
//             if(!nogroups()) {
//                lab2.setText(u.gettext(stulist_h2(),"addstu"));
//                addtree(Color.red);
//             }
             biglist = u.removeString(biglist,olds);
        }
         reloadUserModel_delayed();
         setstudentlc();
    }
    //--------------------------------------------------------------------

     // remove student -------------------------
     void removeusers() {
         TreePath currsels[] = usertree.getSelectionPaths();
         boolean showprogressbar = false;
         for(int i = 0; i < currsels.length; i++){
             if(i>0) {
                 showprogressbar = true;
                 break;
             }
             jnode jn = (jnode)currsels[i].getLastPathComponent();
             if(jn == currentnode || jn.type == jnode.GROUP){
                 showprogressbar = true;
                 break;                 
             }
         }
         if(showprogressbar)
             progbar = new progress_base(thisadmin, showprogressbar, shark.programName,
                 u.gettext("deleting", "label"),
                 new Rectangle(thisadmin.getWidth()/4,
                      thisadmin.getHeight()*2/5,
                      (thisadmin.getWidth()/2),
                      (thisadmin.getHeight()/5)));
        final jnode jns[] =  getNodes(usertree.getSelectionPaths());        
        deleteStudentsThread = new Thread(dsw = new deleteStudents_Work(showprogressbar,
                jns));
        deleteStudentsThread.start();
        
        setRightPanelBlank(true, true);
        deleteStudentsTimer = new javax.swing.Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(deleteStudentsThread.isAlive())return;
                keepRightPanelBlank = false;
                stopProgressBar();     
                usertree.setSelectionPaths(getTreePaths(jns));
                if(usertree.getSelectionCount()==0  ||  ((jnode)usertree.getSelectionPath().getLastPathComponent()).getParent()==null)
                   usertree.setSelectionPath(new TreePath(currentnode.getPath()));                
                deleteStudentsTimer.stop();
                deleteStudentsTimer = null;
                dsw = null;
                deleteStudentsThread = null;
            }
        });
        deleteStudentsTimer.setRepeats(true);
        deleteStudentsTimer.start();
    }

     void removemultiusers() {
         new removeUnwantedUsers(thisadmin);
    }



    //---------------------------------------------------------
//    void removefromtree(jnode node, TreePath forceselection) {
    //     student stu;
     //    jnode parent = (jnode)node.getParent();
  //       String parentname = parent.get();
  //       String name = node.get();
    //     short i;
   //      int row = usertree.getRowForPath(new TreePath(node.getPath()));
 //        usermodel.removeNodeFromParent(node);
//         if(forceselection== null) usertree.setSelectionRow(Math.min(usertree.getRowCount()-1,row));
//         else usertree.setSelectionPath(forceselection);
  //       currstu2 = (jnode)usertree.getLastSelectedPathComponent();
 //   }

    //-----------------------------------------------------------------
                                 // rename student
     void renamestudent(jnode jn, String newname) {
        student stu = null;
  //      if (currstu2==root) return;
        String name = extractname2(jn);
        if(name==null) return;
 //       String newname=name;
        if(jn.type == jnode.STUDENT) {
          if(teacher.teacher) {
           u.okmess(u.gettext("stulist_cannotren","heading"),u.gettext("stulist_cannotren","text"),thisadmin);
           return;
         }   // rb 16/11/06
          stu = student.findStudent(name);
           if(stu==null) return;
//           if(hasTeachers(stu.name)) { // start rb  8/2/06   xxxx
//             u.okmess(u.gettext("stulist_hasteachers","heading"),u.gettext("stulist_hasteachers","text",stu.name),thisadmin);
//             return;
//           }                    // end rb  8/2/06
          String otherteachers[];
           if((otherteachers = getOtherTeachers(stu.name))!=null) {
             String mess = u.gettext("stulist_hasteachers","text",stu.name);
             mess += "||";
             for(int i = 0; i < otherteachers.length; i++){
                 mess += otherteachers[i];
                 if(i < otherteachers.length-1)
                    mess += ", ";
             }
             mess += ".||";
             mess = u.convertToHtml(mess);
             u.okmess(u.gettext("stulist_hasteachers","heading"),mess,thisadmin);
             return;
           }
//           if(!stu.isLocked()) {
//               if(!stu.getlock()) { // start rb  8/2/06
//                 u.okmess(u.gettext("stulist_stuloggedon_rename","heading"),u.gettext("stulist_stuloggedon_rename","text",stu.name),thisadmin);
//                 return;
//               }                    // end rb  8/2/06
//           }
          // end rb  8/2/06
        }
//        JOptionPane getpw = new JOptionPane(
//               u.gettext("stulist_","newname",name),
//               JOptionPane.PLAIN_MESSAGE,
//               JOptionPane.OK_CANCEL_OPTION);
//        getpw.setWantsInput(true);
//        JDialog dialog = getpw.createDialog(thisadmin, u.gettext("stulist_","renametitle"));
//        while(true) {
//           dialog.setVisible(true);
//           Object result = getpw.getValue();
//           if(result == null
//                || result instanceof Integer
//                 &&((Integer)result).intValue() != JOptionPane.OK_OPTION) break;   // rb 8/2/06
//           newname = (String)getpw.getInputValue();
//           if(newname.length() == 0)  continue;
           if(jn.type == jnode.STUDENT) {
              if(student.findStudent(newname) != null) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 u.okmess( u.gettext("stulist_","renaming",name),  u.gettext("stulist_","already",newname));
                u.okmess( u.gettext("stulist_","renaming",stu.name),  u.gettext("stulist_","already",newname), thisadmin);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(stu != null) stu.releaselock();
                 return;
              }
//              String dbpath = sharkStartFrame.sharedPath.toString();
//startPR2008-06-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              PickPicture.renamed(stu.name, newname);
              renameRecords(stu.name, newname);
//endPRimagesandvids^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              db.rename(stu.name,newname);
              renameinlists(stu.name,newname);
              stu.releaselock();        // rb  8/2/06
              stu.name = newname;
              stu.saveStudent();
 //          } 
           }
           jn.setUserObject(newname);
  //         model.reload(jn);
  //         usertree.expandPath(new TreePath(jn.getPath()));
           savetree();   // rb  9/2/06
//           break;    // rb  8/2/06
      
        if(stu != null) stu.releaselock();        //  just in case rb  8/2/06
    }
     
    
     void renameRecords(String oldname, String newname){
         String strsturecfolder = u.gettext("sturec_", "folder");      
         String start = u.gettext("sturec_", "file");
         userToDelete = u.edit(start.substring(0,5), oldname, shark.USBprefix);
         File f = new File(sharkStartFrame.sharedPath, strsturecfolder);
         if (f.exists()) {
             File list[] = f.listFiles(new java.io.FileFilter() {
                 public boolean accept(File file) {
                     return file.getName().startsWith(userToDelete);
                 }
             });          
             if(list.length > 0){
                 String start2 = u.edit(start.substring(0,5), newname, shark.USBprefix);
                 for(int i = 0; i < list.length; i++){
                    list[i].renameTo(new File(f.getAbsolutePath()+shark.sep+list[i].getName().replaceAll(userToDelete, start2)));
                 }
             }
         }           
     }
     
                                // view/change games palyed
    //----------------------------------------------------------------
     void studentrecord() {
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(System.currentTimeMillis() > lastdialogstart+2000)
         lastdialogstart = System.currentTimeMillis();
       else return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       jnode currstu2 = (jnode)usertree.getSelectionPath().getLastPathComponent();
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(currstu2.type==jnode.GROUP && currstu2.isLeaf()){
         return;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(currstu2.type != jnode.STUDENT) {
            jnode node=null,end = (jnode)currstu2.getLastLeaf();
            String s[]=null;
            for(node=(jnode)currstu2.getFirstLeaf();
                  node != null ;
                  node = (jnode)node.getNextLeaf()) {
               if(node.type == jnode.STUDENT) s = u.addStringSort(s,node.get());
               if (node == end) break;
            }
//startPR2005-10-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            new student.showStudentRecord(s);
            if(s!=null)
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              new student.showStudentRecord(s, currstu2==root?u.gettext("sturec_","your") : currstu2.get());
              new student.showStudentRecord(thisadmin, s, currstu2==younode?u.gettext("sturec_","your") : currstu2.get());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            else
              u.okmess("adminnostudents", thisadmin);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
        else {
           String name = extractname(currstu2);
           if(name==null) return;
           student stu = student.findStudent(name);
           if(stu != null)
//startPR2007-03-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             new student.showStudentRecord(stu);
             new student.showStudentRecord(thisadmin, stu);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        }
    }
    //----------------------------------------------------------------
//     void newsimple() {
//       if(System.currentTimeMillis() > lastdialogstart+2000)
//         lastdialogstart = System.currentTimeMillis();
//       else return;
//       if(currstu2.type==jnode.GROUP && currstu2.isLeaf()){
//         u.okmess(u.gettext("stulist_emptygroup","heading"),u.gettext("stulist_emptygroup","text"),thisadmin);
//         return;
//       }
//        savetree();   // rb 9/2/06
//        String names[];
//        if(currstu2.get().indexOf(topicTree.ISTOPIC) >= 0) {
//           String pname = extractprogram(currstu2);
//           new simpleprogram(thisadmin, teacher.name,
//                  new String[]{extractname(currstu2)},null,
//                  pname,thisadmin, false,false);
//        }
//        else  if(currstu2.getChildCount() > 0) {
//            jnode[] jj = currstu2.getChildren();
//            new simpleprogram(thisadmin, teacher.name,
//                  getstulist(), extractname(jj[0]),
//                   getGroupProg(jj),thisadmin, false, false);
//        }
//        else new simpleprogram(thisadmin, teacher.name, getstulist(),
//                            program.makename(), null,
//                            thisadmin, false, false);
//     }
     // only returns a program if all the students in the class have the
     // same work.
     String getGroupProg(jnode[] jn){
         if(jn==null)return null;
         String s = null;
         for(int i = 0; i < jn.length; i++){
             String s1 = extractprogram(jn[i]);
             if(s1 == null)return program.makename();
             if(s!=null && s1!=null && !s1.equals(s))
                return program.makename();
             s = s1;
         }
         if(s==null)return program.makename();
         return s;
     }

     public static String getadminpath(jnode node) {
       String ret = topicTree.ISPATH + node.get();
       if(node.isRoot()) return ret;
       while(true) {
          node = (jnode)node.getParent();
          if(node.isRoot()) break;
          else ret = topicTree.ISPATH + node.get() + ret;
       }
       return ret;
     }

     String getadminpath2(jnode node) {
       String ret = topicTree.ISPATH + node.get();
       if(node == currentnode) return ret;
       while(true) {
          node = (jnode)node.getParent();
          if(node == currentnode)break;
          else ret = topicTree.ISPATH + node.get() + ret;
       }
       return ret;
     }
    //----------------------------------------------------------------
     void removeallprog(String programname) {
//        jnode node,node2;
//        String s,prog;
//        String programname2 = programname+"["+teacher.name+"]"; // rb 23/10/06
//        for(node = (jnode)root.getFirstLeaf();node !=null;node = node2) {
//            s = node.get();
//            node2 = (jnode)node.getNextLeaf();   // early, in case we delete
//            int i = s.indexOf(topicTree.ISTOPIC);
//            int j;
//            if(i>=0) {
//              for(j=s.length()-1; j>i && s.charAt(j) != '('; --j);
//              prog = s.substring(i+1,j);
//              if(prog.equalsIgnoreCase(programname) || prog.equalsIgnoreCase(programname2)) { // rb 23/10/06
//                   String name = getstudentname(node);
//                   db.delete(name,prog,db.PROGRAM);  // rb 23/10/06
//                   db.update(name,"_refresh","",db.TEXT);
//                   if(i==0) model.removeNodeFromParent(node);
//                   else {
//                      node.setUserObject(name);
//                      model.reload(node);
//                   }
//               }
//            }
//        }
 //       studenttc.repaint();
     }
     //----------------------------------------------------------------
     String extractprogram(jnode node) {
            String s = node.get();
            int i = s.indexOf(topicTree.ISTOPIC),j;
            if(i>=0) {
              for(j=s.length()-1; j>i && s.charAt(j) != '('; --j);
              return s.substring(i+1,j);
            }
            else return null;
     }
    //----------------------------------------------------------------
     void delprog() {  //  program was deleted or added
              proglist = db.list(teacher.name,db.PROGRAM);
              u.sortdate(proglist);
//              programlc.setListData(proglist);
//              programlc.clearSelection();
     }

     //---------------------------------------------------------------
     String getstudentname(jnode node) {
          jnode newnode=node;
          while(newnode.type < 0) {
             newnode = (jnode)newnode.getPreviousLeaf();
             if(newnode == null) return null;
          }
          return stripprogram(newnode.get());
     }
     //---------------------------------------------------------------
     jnode getstudentnode(jnode node) {
          jnode newnode=node;
          while(newnode.type < 0) {
             newnode = (jnode)newnode.getPreviousLeaf();
             if(newnode == null) break;
          }
          return newnode;
     }



   void setWorkVisible(int on){
       if(on>=0)
           lastpanshow = on;
       dummyworkpan.setVisible(on<-1);
       workpan.setVisible(on>-1 && on<=0 && on==0);
       settingspan.setVisible(on>-1 && !workpan.isVisible());
       worktitlepan.setVisible(on>-1 && on>=0);
       if(on<-1){
           return;
       }

       worktitlelabel.setForeground(Color.gray);
       settingstitlelabel.setForeground(Color.gray);

       settingstitlelabel.setBackground(on==0?darkerdefaultcol:sharkStartFrame.defaultbg);
       worktitlelabel.setBackground(on!=0?darkerdefaultcol:sharkStartFrame.defaultbg);


       jpsettingstit.setBackground(on==0?darkerdefaultcol:sharkStartFrame.defaultbg);
       jpworktit.setBackground(on!=0?darkerdefaultcol:sharkStartFrame.defaultbg);

       

 //      jpsettingsunder.setForeground(Color.pink);
       jpworkunder.setBackground(on!=0?darkerdefaultcol:sharkStartFrame.defaultbg);
       jpsettingsunder.setBackground(on==0?darkerdefaultcol:sharkStartFrame.defaultbg);



       pnsetbuts.setVisible(on==1);
       worktitlepan.setVisible(on>=0);
       if(on == 0){
 //        worktitlelabel.setForeground(Color.darkGray);
  //       worktitlelabel.setForeground(sharkStartFrame.defaultbg);
 //        settingstitlelabel.setBackground(darkerdefaultcol);
  //       settingstitlelabel.setForeground(Color.darkGray);
 //        jpsettingsunder.setBackground(darkerdefaultcol);
//         jpworkunder.setBackground(sharkStartFrame.defaultbg);
       }
       else{
   //      worktitlelabel.setForeground(Color.darkGray);
  //       worktitlelabel.setForeground(darkerdefaultcol);
  //       settingstitlelabel.setBackground(darkerdefaultcol);
  //       settingstitlelabel.setForeground(sharkStartFrame.defaultbg);
 //        jpsettingsunder.setBackground(sharkStartFrame.defaultbg);
  //       jpworkunder.setBackground(darkerdefaultcol);
       }
   }


     int getBottomProfileInset(){
         if( sharkStartFrame.screendim.height<700)return 0;
         else if(sharkStartFrame.screendim.height<800)return 10;
         else return 40;
     }

     void newstudent(String name, String pass, String passwordhint) {
        jnode currsel = (jnode)usertree.getSelectionPath().getLastPathComponent();
        if(!currsel.isNodeAncestor(currentnode))currsel = currentnode;

        student stu = new student();
             if(name.length() > 0) {
              name = u.stripspaces2(name);
              stu.name = name;
              String dbpath = sharkStartFrame.sharedPath.toString();
              String dbname = dbpath + shark.sep+stu.name;

              if(pass!=null) {
                stu.password = student.encrypt(pass);
              }
              if(passwordhint!=null) {
                stu.passwordhint = passwordhint;
              }              
              db.create(dbname);
              stu.saveStudent();
              student.checkadmin();    // rb 6/2/06
              if(aregroups())
                 usertree.setSelectionPath(new TreePath(addtotree(stu,currsel).getPath()));
              else
                 usertree.setSelectionPath(new TreePath(addtotree(stu,currentnode).getPath()));
              reloadUserModel_delayed();
              return;
           }
           else return;
        
    }

     void inputStuList(){
        String ob[] = new String[]{u.gettext("OK","label"),u.gettext("cancel","label")};
        JOptionPane getn = new JOptionPane(
               u.gettext("stulist_", "typelist"),
               JOptionPane.PLAIN_MESSAGE,0,null,
               ob,ob[0]);
        getn.setWantsInput(true);
        String newstutitle = u.gettext("stulist_","newstutitle");
        JDialog dialog = getn.createDialog(thisadmin,newstutitle);

       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2 = sw*9/12;
       int sh2;
       if(sharkStartFrame.screendim.height>800)
        sh2 = sh*2/12;
       else
        sh2 = sh*3/12;
       if(!ChangeScreenSize_base.isActive){
            dialog.setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);
        }
        else{
            dialog.setBounds(sharkStartFrame.mainFrame.getLocation().x+(sw-sw2)/2, 
                    sharkStartFrame.mainFrame.getLocation().y+(sh-sh2)/2, 
                    sw2, sh2);
        }

       loop1: while(true) {
            if(!ChangeScreenSize_base.isActive){
              dialog.setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);
            }
            else{
                dialog.setBounds(sharkStartFrame.mainFrame.getLocation().x+(sw-sw2)/2, 
                        sharkStartFrame.mainFrame.getLocation().y+(sh-sh2)/2, 
                        sw2, sh2);
            }
           keypad.dofullscreenkeypad(dialog, true);
           dialog.setVisible(true);
           keypad.dofullscreenkeypad(dialog, false);

       Object result = getn.getValue();
       String input = (String)getn.getInputValue();
           if(result == null) return;
           if(result instanceof String) switch(u.findString(ob,(String)result)) {
                case 1: return;
           }
           if(result instanceof Integer){
               Integer g = (Integer)result;
               if(g.intValue()<0)return;
           }
        jnode node = currentnode;
        String parent = teacher.name;
        jnode currsel = (jnode)usertree.getSelectionPath().getLastPathComponent();
        if(!currsel.isNodeAncestor(currentnode))currsel = currentnode;



        if(aregroups()) {
           node = (jnode)((currsel.type != jnode.STUDENT)?currsel:currsel.getParent());
           parent = node.get();
        }
        student stu = new student();




             String sss[] = u.splitString(input,',');
             String longnames[] = null;
             for(int n = 0; n < sss.length; n++){
                 String st;
                 if((st=sss[n].trim()).length()>KeyDoc_base.MAXNAMECHARS){
                     if(longnames==null)longnames = new String[]{st};
                     else longnames = u.addString(longnames, st);
                 }
             }
             if(longnames!=null){
               dialog.dispose();
               String s1 = longnames.length>1?u.gettext("nametoolong", "mess_p"):u.gettext("nametoolong", "mess_s");
               s1 = s1.replaceFirst("%", String.valueOf(KeyDoc_base.MAXNAMECHARS));
               String combinestr = u.combineString(longnames, "|");
               s1 = s1.replaceFirst("%", "||"+combinestr);
               u.okmess(u.gettext("nametoolong", "title"), s1, this);
               getn.setInitialSelectionValue(input);
               dialog = getn.createDialog(thisadmin,newstutitle);
               continue loop1;
             }



             String s[]  = null;
             String invc = "";
             Vector v = getBadNames(u.splitString(input,','));
             if(v!=null){
                 s = (String[])v.get(0);
                 String sc[] = (String[])v.get(1);
                 for(int i = 0; i < sc.length; i++){
                     invc = invc.concat(String.valueOf(sc[i]));
                     if(i < sc.length-1)invc = invc.concat(" ");
                 }
             }
             if(s!=null){
               dialog.dispose();
               String sa[] = new String[]{" "};
               String s1 = s.length>1?u.gettext("invalidchars", "mess_p"):u.gettext("invalidchars", "mess_s");
               s1 = s1.replaceFirst("%", invc);
               sa = u.addString(u.splitString(s1), sa);
               sa = u.addString(sa, s);
               u.okmess(u.gettext("invalidchars", "title"), sa, this);
               getn.setInitialSelectionValue(input);
               dialog = getn.createDialog(thisadmin,newstutitle);
               continue loop1;
             }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             if(input.length() > 0) {
             if(input.indexOf(',') >= 0) {
                  addstudentlist(u.splitString(input,','),stu);
                  return;
//                  stu = new student();   // refresh dialogue
//                  getn = new JOptionPane(
//                         u.splitString(u.gettext("stulist_","entername2",parent)),
//                         JOptionPane.QUESTION_MESSAGE,0,null,
//                         ob,ob[0]);
//                  getn.setWantsInput(true);
//                  dialog = getn.createDialog(thisadmin,u.gettext("stulist_","newstutitle"));
//                  continue;
              }
              input = u.stripspaces2(input);
              if(input.length()==0) continue;
              stu.name = input;
              String dbpath = sharkStartFrame.sharedPath.toString();

              String dbname = dbpath + sharkStartFrame.sharedPath.separatorChar+stu.name;
              student st1;
              if((st1 = student.findStudent(input)) != null) {
                  String errmess = getAlreadyText(st1);
                  if(errmess!=null)
                    u.okmess(u.gettext("stulist_","newstutitle"),
                            u.convertToHtml(errmess), thisadmin);
                 continue;
              }

              /*
              String op[] =  new String[]{u.gettext("stulist_","passop1"),u.gettext("stulist_","passop2")};
              JOptionPane getpw = new JOptionPane(
                 u.gettext("stulist_","passmsg"),
                 JOptionPane.QUESTION_MESSAGE,
                 0,
                 null,op,op[0]);
             getpw.setWantsInput(true);
             dialog = getpw.createDialog(thisadmin,u.gettext("stulist_","newstutitle"));
              while(true) {
                   if(shark.macOS && dialog!=null)
                     dialog.dispose();
                   dialog.setVisible(true);
                   result = getpw.getValue();
                   input = (String)getpw.getInputValue();
                   if(result == op[1]) break; // no passwd
                   if(result == op[0] && input.length() > 0) {
                     stu.password = student.encrypt(input);
                     break;
                   }
              }
              */
              newUser nu = new newUser(newUser.MODE_PASSWORDONLY_RETURNVAL, thisadmin, stu.name, jnode.STUDENT);
              if(nu.returnpass!=null)
                stu.password = student.encrypt(nu.returnpass);
              stu.passwordhint = nu.returnpasswordhint;
              db.create(dbname);
              stu.saveStudent();
              student.checkadmin();    // rb 6/2/06
              if(aregroups())
                 usertree.setSelectionPath(new TreePath(addtotree(stu,currsel).getPath()));
              else
                 usertree.setSelectionPath(new TreePath(addtotree(stu,currentnode).getPath()));
              reloadUserModel_delayed();
              return;
           }
           else return;
       }

     }

     void importFromFile(){
               String ss[] = u.importtext();
               if(ss==null || ss.length==0) return;
               stringedit_base se = new stringedit_base(u.gettext("stulist_import", "label2"), ss,
                       this, false, student.optionstring("keypad") != null) {
                    public boolean update(String s[]) {
                     String s1[] = null;
                     String invc = "";
                     Vector v = getBadNames(s);
                     if(v!=null){
                         s1 = (String[])v.get(0);
                         String sc[] = (String[])v.get(1);
                         for(int i = 0; i < sc.length; i++){
                             invc = invc.concat(String.valueOf(sc[i]));
                             if(i < sc.length-1)invc = invc.concat(" ");
                         }
                     }
                     if(s1!=null){
                        String sa[] = new String[]{" "};
                        String s2 = s.length>1?u.gettext("invalidchars", "mess_p"):u.gettext("invalidchars", "mess_s");
                        s2 = s2.replaceFirst("%", invc);
                        sa = u.addString(u.splitString(s2), sa);
                        sa = u.addString(sa, s1);
                        u.okmess(u.gettext("invalidchars", "title"), sa, this);
                        return false;
                     }
                     addstudentlist(s, null);
                     return true;
                   }
               };
               se.ignorexclick = true;
               se.staythere = true;      
     }

     void importFromClipboard(){
            stringedit_base se = new stringedit_base(u.gettext("multstu", "clipboardtitle"),
                    new String[]{}, thisadmin, stringedit_base.MODE_STUDENTEDIT,
                    false, student.optionstring("keypad") != null) {

                    public boolean update(String s[]) {
                        progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));
                         String s1[] = null;
                         String invc = "";
                         Vector v = getBadNames(s);
                         if(v!=null){
                             s1 = (String[])v.get(0);
                             String sc[] = (String[])v.get(1);
                             for(int i = 0; i < sc.length; i++){
                                 invc = invc.concat(String.valueOf(sc[i]));
                                 if(i < sc.length-1)invc = invc.concat(" ");
                             }
                         }
                         if(s1!=null){
                           String sa[] = new String[]{" "};
                           String s2 = s.length>1?u.gettext("invalidchars", "mess_p"):u.gettext("invalidchars", "mess_s");
                           s2 = s2.replaceFirst("%", invc);
                                    sa = u.addString(u.splitString(s2), sa);
                                    sa = u.addString(sa, s1);
                                    u.okmess(u.gettext("invalidchars", "title"), sa, this);
                                    return false;
                                  }
                                  addstudentlist(s, null);
                        addStudentsTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(addStudentsThread.isAlive())return;
                                    stopProgressBar();
                                    addStudentsTimer.stop();
                                    addStudentsTimer = null;
                                    swc = null;
                                    addStudentsThread = null;
                              }
                        });
                        addStudentsTimer.setRepeats(true);
                        addStudentsTimer.start();
                        return true;

                    }
               };
               se.trimPaste = true;
               se.ignorexclick = true;
               se.staythere = true;
               return;
     }

     
     
     
    void importFromProgram(){
         progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));
          findSharedThread = new Thread(wc = new u.doGetStusFromOtherShark());
          findSharedThread.start();
       findSharedTimer = new javax.swing.Timer(500, new ActionListener() {
      public void actionPerformed(ActionEvent e) {

            if(wc==null)return;
            if(findSharedThread.isAlive())return;

            Vector v = wc.retval;
            stopProgressBar();
            String path = null;
                boolean browsechoice = false;
                if(v != null){
                    path = (String)v.get(0);
                    v.remove(0);
                    if(v.size()>0){
                        String browsetext = u.gettext("browse", "label");
                        String importtext = u.gettext("import", "label");
                        String canceltext = u.gettext("cancel", "label");
                        String otherprogtext = u.gettext("stulist_import2", "searchforfolder");
                        String titletext = u.gettext("multstu", "programtitle", shark.otherProgram);
                        String messtext = u.convertToCR(u.gettext("multstu", "programmess"));

                        String mta[] = u.splitString(messtext, '%');
                        messtext = mta[0]+shark.otherProgram+mta[1]+path+mta[2]+importtext+mta[3]+browsetext+mta[4]+otherprogtext+mta[5];
                        int res1 = u.choose(titletext, messtext,
                                     new String[] {importtext,browsetext,canceltext},
                                     thisadmin);
                        if (res1 == 0){
                            // if Import clicked

                        }
                        else if (res1 == 1){
                            // if Browse clicked
                            v = doBrowse();
                        }
                        else if (res1 == 2){
                            // if Cancel clicked
                            findSharedTimer.stop();
                            findSharedTimer = null;
                            wc = null;
                            findSharedThread = null;     
                            return;
                        }
                    }
                    else if (v.size()==0){
                        browsechoice = true;
                    }
                }
                else{
                    browsechoice = true;
                }
                if(browsechoice){
                  String browse = u.gettext("browse", "label");
                  String[]  choices = {browse, u.gettext("cancel", "label")};
                  boolean restart = true;
                  while(restart){
                    restart = false;
                    int res = JOptionPane.showOptionDialog(thisadmin,
                                                           u.gettext("stulist_import2_dialog", "message", shark.otherProgram),
                                                           u.gettext("stulist_import2_dialog", "title"),
                                                           JOptionPane.YES_NO_CANCEL_OPTION,
                                                           JOptionPane.PLAIN_MESSAGE,
                                                           null,
                                                           choices,
                                                           browse);
                    if (res == 0) {
                        v = doBrowse();
                        if(v==null)restart = true;
                    }
                    else if (res == 1) {
                        findSharedTimer.stop();
                        findSharedTimer = null;
                        wc = null;
                        findSharedThread = null;
                        return;
                    }
                  }
                }
                if (v == null) {
                    
                        findSharedTimer.stop();
                        findSharedTimer = null;
                        wc = null;
                        findSharedThread = null;
                    u.okmess("stulist_noother", thisadmin);
                    return;
                }
                else if (v.size()==0) {
                    
                        findSharedTimer.stop();
                        findSharedTimer = null;
                        wc = null;
                        findSharedThread = null;
                    u.okmess("stulist_empty", thisadmin);
                    return;
                }
                final Vector v2 = v;
                String ss[] = null;
                if(v2!=null){
                    for(int k = 0; k < v.size(); k++){
                        ss = u.addString(ss, ((simpleStu_base)v.get(k)).name);
                    }
                }
                stringedit_base se = new stringedit_base(u.gettext("stulist_import2", "label2"), ss, thisadmin, false) {
                    public boolean update(String s[]) {
                        progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));
                        addstudentlist(s, null, v2);
                        addStudentsTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(addStudentsThread.isAlive())return;
                                    stopProgressBar();
                                    addStudentsTimer.stop();
                                    addStudentsTimer = null;
                                    swc = null;
                                    addStudentsThread = null;
                              }
                        });
                        addStudentsTimer.setRepeats(true);
                        addStudentsTimer.start();
                        return true;
                    }
                };
                se.ignorexclick = true;
                se.staythere = true;
          findSharedTimer.stop();
          findSharedTimer = null;
          wc = null;
          findSharedThread = null;
      }
    });
    findSharedTimer.setRepeats(true);
    findSharedTimer.start();
    }


    Vector doBrowse(){
        String dirs[] = u.splitString(u.gettext("stulist_import2","allowfolders"));
                        Vector v = new Vector();
                      int returnVal = JFileChooser.APPROVE_OPTION;
                      while (returnVal == JFileChooser.APPROVE_OPTION) {
                        String prePath = sharkStartFrame.sharedPathplus.substring(0, 3);
                        if(new File(prePath+"Program Files (x86)").exists())
                            prePath = prePath+"Program Files (x86)";
                        else prePath = prePath+"Program Files";

                        JFileChooser fc = new JFileChooser(shark.macOS ? "/Applications/" :
                                                           prePath);
                        fc.setAcceptAllFileFilterUsed(false);
                        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        fc.setFileFilter(new javax.swing.filechooser.FileFilter() {
                          public boolean accept(File f) {
                            if (! (f.isDirectory())) {
                              return false;
                            }
                            return true;
                          }
                          public String getDescription() {
                            return u.gettext("alldirectories", "label");
                          }
                        });
                        returnVal = fc.showOpenDialog(thisadmin);
                        if (returnVal == fc.APPROVE_OPTION) {
                          File selectedFile = fc.getSelectedFile();
 //                         String dir = selectedFile.getAbsolutePath()+File.separator;
                          if (u.findString(dirs, selectedFile.getName()) >= 0) {
  //                          String s[] = db.dblistnames(selectedFile);
  //                          File f[] = new File[]{};
  //                          for(int k = 0; k < s.length; k++){
  //                              f = u.addFile(f, new File(dir+s[k]));
  //                          }
 //                           returnVal = -1;

                            returnVal = -1;
                            v = u.getstulist(dirs, selectedFile);
                          }
                          else {
                            u.okmess(u.gettext("stulist_import2_dialog", "title"),u.gettext("stulist_import2_dialog", "notshared", shark.otherProgram));
                          }
                        }
                        else if(returnVal == fc.CANCEL_OPTION){
                          return null;
                        }
                      }
                      return v;
    }

//startPR2009-09-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    Vector getBadNames(String[] s){
      String ret[] = null;
      String chs[] = null;
      loop1:for(int i = 0; i < s.length; i++){
        for(int j = 0; j < s[i].length(); j++){
          if((u.notAllowedInFileNames.indexOf(s[i].charAt(j)) >= 0)){
            if(ret==null)ret = new String[]{};
            if(chs==null)chs = new String[]{};
            ret = u.addString(ret, s[i]);
            if(u.findString(chs, String.valueOf(s[i].charAt(j)))<0){
                chs = u.addString(chs, String.valueOf(s[i].charAt(j)));
            }
            continue loop1;
          }
        }
      }
      Vector v = null;
      if(ret!=null && ret.length>0){
          v = new Vector();
          v.add(ret);
          v.add(chs);
      }
      return v;
    }

//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-02-02^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     void addstudentlist(String ss[], student stu) {
        addstudentlist(ss, stu, null);
     }

    void addstudentlist(String ss[], student stu, Vector v) {
        addStudentsThread = new Thread(swc = new addStudents_Work(ss, stu, v));
        addStudentsThread.start();
    }
//endPRtoupdate^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    //----------------------------------------------------------------
     void newgroup() {
        jnode selnode;
        TreePath tp = usertree.getSelectionPath();
        if(tp == null || !(selnode =(jnode)tp.getLastPathComponent()).isNodeAncestor(currentnode))selnode = currentnode;
        jnode node = (jnode)((selnode.type != jnode.STUDENT)?selnode:selnode.getParent());
        String parent = node.get();
        JOptionPane getn = new JOptionPane(
               u.gettext("stulist_","gpname",parent),
               JOptionPane.QUESTION_MESSAGE,
               JOptionPane.OK_CANCEL_OPTION);
       getn.setWantsInput(true);
       JDialog dialog = getn.createDialog(thisadmin,u.gettext("stulist_","gphead"));
        while(true) {
            keypad.dofullscreenkeypad(dialog, true);
           dialog.setVisible(true);
           keypad.dofullscreenkeypad(dialog, false);
           Object result = getn.getValue();
           String input = (String)getn.getInputValue();
           if(result==null
               || result instanceof Integer && ((Integer)result).intValue() != JOptionPane.OK_OPTION) return;
           if(input.length() > 0) {
               if(selnode.type == jnode.GROUP){
                   boolean subsub = u.yesnomess(shark.programName, u.gettext("stulist_","subsubgroup"), thisadmin);
                   if(!subsub)node = currentnode;
               }
              jnode newnode = new jnode(input);
              newnode.setIcon(jnode.GROUP);
              newnode.color = sharkStartFrame.fastmodecolor;
              usermodel.insertNodeInto(newnode,node,node.getChildCount());
              reloadUserModel_delayed();
              usertree.setSelectionPath(new TreePath(newnode.getPath()));
              return;
           }
        }
    }
    //----------------------------------------------------------------
    void dragstu() {
    }
    //----------------------------------------------------------------
    /*
    void stuoptions() {
       String students[] = getstudentlist();
       student firststu;
       if(students.length>0 && (firststu = student.findStudent(students[0])) != null)
            new stuoptions(students,firststu);
    }
*/
    void newadmin(String name, String pass, String passwordhint) {
        student stu = new student();
        name = u.stripspaces2(name);
        if(name.length() > 0) {
            stu.name = name;

            String dbpath = sharkStartFrame.sharedPath.toString();
            String dbname = dbpath + shark.sep+stu.name;

              pass = pass.trim();
              if(pass.length() > 0) {
                     biglist = null;
                     stu.password = student.encrypt(pass);
                     if(passwordhint!=null) {
                        stu.passwordhint = passwordhint;
                     }  
                     stu.administrator = true;
                     stu.teacher = false;
                     db.create(dbname); // will fail if already there
                     stu.saveStudent();
                     addtotree(stu, admins_heading);
                     reloadUserModel_delayed();
                     student.checkadmin(stu);    // rb 6/2/06
                     sharkStartFrame.mainFrame.gettopictreelist();
                   }
              }
           }





    void newteacher(String name, String pass, String passwordhint) {
        student stu = new student();
            name = u.stripspaces2(name);
            if(name.length() > 0) {
              stu.name = name;
              String dbpath = sharkStartFrame.sharedPath.toString();
              String dbname = dbpath + shark.sep+stu.name;
                   pass = pass.trim();
                   if(pass.length() > 0) {
                     stu.password = student.encrypt(pass);
                     if(passwordhint!=null) {
                        stu.passwordhint = passwordhint;
                     }  
                     stu.administrator = true;
                     stu.teacher = true;
                     db.create(dbname);
                     stu.saveStudent();
                     
                     student.checkadmin(stu); 
                     biglist = null;     

                     addtotree(stu, admins_heading);
                     reloadUserModel_delayed();
//                     student.checkadmin();    // rb 6/2/06
                     sharkStartFrame.mainFrame.gettopictreelist();
                     return;
                   }
              }
           }


   //------------------------------------------------------------------
  /*
   void addtree(Color c) {
      if(!panel2.isAncestorOf(lab2)) {
       grid.fill = GridBagConstraints.BOTH;
       grid.gridx=0;
       grid.gridy=-1;
       grid.weighty=0;
       panel2.setToolTipText(lab2.getToolTipText());
       panel2.add(lab2,grid);
       grid.weighty=20;
       panel2.add(u.uScrollPane(usertree),grid);
       grid.weighty=0;
       panel2.setBorder(BorderFactory.createLineBorder(c,2));
       if(state == STUDENTREMOVEP) {
         panel2.add(undobutton, grid);
         undobutton.setEnabled(!undos.isEmpty());
        }
        else if(state == STUDENTEXCLUDEG) {
         }
       lab2.setBackground(c);
       lab2.setForeground(Color.yellow);
       panel2.validate();
       panel2.repaint();
      }
      else {
       panel2.setBorder(BorderFactory.createLineBorder(c,2));
       lab2.setBackground(c);
      }
    }
*/

  //----------------------------------------------------- // start rb 8/2/06  xxxx
  void renameinlists(String oldname,String newname) {
    String proglist[] = db.list(teacher.name,db.PROGRAM);
    for (int i=0;i<proglist.length;++i) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      program.saveprogram pp = (program.saveprogram)db.find(teacher.name,proglist[i],db.PROGRAM);
      program.saveprogram pp = null;
      try{pp = (program.saveprogram)db.find(teacher.name,proglist[i],db.PROGRAM);}
      catch(ClassCastException e){pp=null;}
      if(pp==null){
        db.delete(teacher.name,proglist[i],db.PROGRAM);
        continue;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int j;
       if((j=u.findString(pp.students,oldname))>=0) {
         pp.students[i] = newname;
         db.update(teacher.name,proglist[i],pp,db.PROGRAM);
       }
    }
    proglist = db.list(newname,db.PROGRAM);
    for (int i=0;i<proglist.length;++i) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      program.saveprogram pp = (program.saveprogram)db.find(newname,proglist[i],db.PROGRAM);
      program.saveprogram pp = null;
      try{pp = (program.saveprogram)db.find(newname,proglist[i],db.PROGRAM);}
      catch(ClassCastException e){pp=null;}
      if(pp==null){
        db.delete(newname,proglist[i],db.PROGRAM);
        continue;
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      int j;
       if((j=u.findString(pp.students,oldname))>=0) {
         pp.students[i] = newname;
         db.update(newname,proglist[i],pp,db.PROGRAM);
       }
    }
  }
                                                     // end rb 8/2/06
  //--------------------------------------------------------------------
  void treestripprograms() {
//    jnode node,next;
//    String s,name,list[];
//    for (node=(jnode)root.getFirstLeaf();node != null;node=next) {
//       next=(jnode)node.getNextLeaf();
//       if((node.type == jnode.STUDENT || node.type<0)
//            && (name=node.get()).indexOf(topicTree.ISTOPIC) >=0) {
//          name = stripprogram(name);
//          if(name == null) {
//             model.removeNodeFromParent(node);
//          }
//          else {
//              node.setUserObject(name);
//              model.reload(node);
//          }
//       }
//    }
  }
  //--------------------------------------------------------------------
  void addprograms() {
//    boolean hadone = false;
//    TreePath oldp = studenttc.getSelectionPath();
//    jnode node,next;
//    String s,name,list[];
//    for (node=(jnode)root.getFirstLeaf();node != null;node=next) {
//       next=(jnode)node.getNextLeaf();
//       if(node.type == jnode.STUDENT) {
//           name = node.get();
//           list = db.list(name, db.PROGRAM);
//           hadone = false;
//           for(short i=0;i<list.length;++i) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              program.saveprogram sp = (program.saveprogram)db.find(name,list[i],db.PROGRAM);
//             program.saveprogram sp = null;
//             try{sp = (program.saveprogram)db.find(name,list[i],db.PROGRAM);}
//             catch(ClassCastException e){sp = null;}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if(sp == null) {
//                db.delete(name,list[i],db.PROGRAM);
//                continue;
//              }
//              s = list[i];
//              if(sp.simple || s.endsWith("]") && !s.endsWith("["+teacher.name+"]")) continue; // rb 23/10/06
//              if(s.endsWith("["+teacher.name+"]")) s = s.substring(0,s.length()-teacher.name.length()-2); // rb 23/10/06
//              if(sp.currstep == sp.it.length-1
//                        && sp.completed >= sp.it[sp.currstep].mustcomplete)
//                   s = s + completed;
//              else  if(sp.currstep == 0  && sp.completed == 0) s = s+notstarted;
//              else s = s + u.edit(stepof,String.valueOf(sp.currstep+1),String.valueOf(sp.it.length)) ;
//              String endchar = topicTree.SEPARATOR;
//              if(!hadone) {
//                 node.setUserObject(name+topicTree.ISTOPIC+s+endchar);
//                 model.reload(node);
//                 hadone = true;
//              }
//              else model.insertNodeInto(new jnode(topicTree.ISTOPIC+s+endchar),
//                                         (jnode)node.getParent(),
//                                         node.getPosition()+1);
//           }
//         }
//      }
//      studenttc.repaint();
 //     if(oldp != null && !((jnode)oldp.getLastPathComponent()).isLeaf())  //expand to leaf
 //       studenttc.setSelectionPath(new TreePath(((jnode)oldp.getLastPathComponent()).getFirstLeaf().getPath()));
 //     else studenttc.setSelectionPath(oldp);
  }

  //--------------------------------------------------------------------

  String getProgramAddOn(program.saveprogram sp, String s){
      String ret = null;
 //        if(s.endsWith("["+teacher.name+"]"))
 //            s = s.substring(0,s.length()-teacher.name.length()-2);
         if(sp.simple) {
 //           if(sp.completed == 0) s = s + notstarted;
             if(sp.completed == 0) ret = notstarted;
 //           else s = s + u.edit(games, String.valueOf(sp.completed));
            else {
                 if(sp.completed < 2)ret = u.edit(game, String.valueOf(sp.completed));
                 else ret = u.edit(games, String.valueOf(sp.completed));
            }
         }
         else {
            if(sp.currstep == sp.it.length-1
                  && sp.completed >= sp.it[sp.currstep].mustcomplete)
//                s = s + completed;
                ret = completed;
            else  if(sp.currstep == 0  && sp.completed == 0)
 //               s = s+notstarted;
                ret = notstarted;
            else
 //               s = s + u.edit(stepof, String.valueOf(sp.currstep+1),String.valueOf(sp.it.length));
                ret =  u.edit(stepof, String.valueOf(sp.currstep+1),String.valueOf(sp.it.length));
        }
      return ret;
  }


  boolean isgroup(jnode jn){
     if(jn==null)return false;
     if(jn.type == jnode.GROUP || jn == currentnode){
        return true;
     }
     return false;
  }

  String[] getselectedstu(){
     jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
     if(jn==null)return null;
  //   String stu = extractname2(jn);
 //    return new String[]{isgroup(jn)?topicTree.ISPATH+stu:stu};
 //    return new String[]{stu};
     if(jn.isLeaf())return new String[]{extractname2(jn)};
     else return new String[]{getadminpath2(jn)};
  }


  void addsimpleprograms() {
//    boolean hadone = false;
//    TreePath oldp = studenttc.getSelectionPath();
 //   jnode oldnode = (oldp != null)?((jnode)oldp.getLastPathComponent()):null;
//    jnode node,next;
//    String s,name,list[];
//    for (node=(jnode)root.getFirstLeaf();node != null;node=next) {
//       next=(jnode)node.getNextLeaf();
//       if(node.type == jnode.STUDENT) {
//           name = node.get();
//           list = db.list(name, db.PROGRAM);
//           hadone = false;
//           for(short i=0;i<list.length;++i) {
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              program.saveprogram sp = (program.saveprogram)db.find(name,list[i],db.PROGRAM);
//             program.saveprogram sp = null;
//             try{sp = (program.saveprogram)db.find(name,list[i],db.PROGRAM);}
//             catch(ClassCastException e){sp = null;}
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if(sp == null) {
//                db.delete(name,list[i],db.PROGRAM);
//                continue;
//              }
//              s = list[i];
//              if(!sp.simple || s.endsWith("]") && !s.endsWith("["+teacher.name+"]")) continue;  // rb 23/10/06
//              if(s.endsWith("["+teacher.name+"]")) s = s.substring(0,s.length()-teacher.name.length()-2); // rb 23/10/06
//              if(sp.completed == 0) s = s + notstarted;
//              else s = s + u.edit(games, String.valueOf(sp.completed));
//              String endchar = topicTree.SEPARATOR;
//              if(!hadone) {
//                 node.setUserObject(name+topicTree.ISTOPIC+s+endchar);
//                 model.reload(node);
//                 hadone = true;
//              }
//              else model.insertNodeInto(new jnode(topicTree.ISTOPIC+s+endchar),
//                                         (jnode)node.getParent(),
//                                         node.getPosition()+1);
//           }
//         }
//      }
//      studenttc.repaint();
//      if(oldp != null)  //expand to leaf
//        studenttc.setSelectionPath(new TreePath(((jnode)oldnode.getFirstLeaf()).getPath()));
//      else
 //       studenttc.setSelectionPath(new TreePath(root.getFirstLeaf().getPath()));
  }
    //-------------------------------------------------------------------
  String stripprogram(String s) {
     int i=s.indexOf(topicTree.ISTOPIC);
     if(i==0) return null;
     if(i>0) return s.substring(0,i);
     return s;
  }

   //------------------------------------------------------------------
   void setstudentlc() {
    int i,j,k;
    student stu;
    savetree();            // rb 9/2/06
    if(biglist==null) {
        //  special for Rik to fix corrupted students ---------------------
      String special = sharkStartFrame.sharedPathplus + "corrupt";
      File specialdir = new File(special);
      if(specialdir.exists() && specialdir.isDirectory()) {
         FileOutputStream corrupt = null;
         String s[] = db.dblistnames(specialdir);
         student st[] = new student[s.length];
          for(i=0;i<s.length;++i) {
              st[i] = student.getStudent(special + sharkStartFrame.separator + s[i]);
              if(st[i] == null) {
                st[i] = new student();
                st[i].name = s[i];
                st[i].saveStudent(special + sharkStartFrame.separator + s[i]);
                u.okmess(u.gettext("stucorrupt", "heading"),
                         u.gettext("stucorrupt", "message", s[i]));
                if(corrupt == null) {
                  try {corrupt = new FileOutputStream(sharkStartFrame.sharedPathplus +
                        "restored students.txt");
                  }
                  catch (IOException e) {}
                }
                try {
                  corrupt.write( (byte) '\r');
                  corrupt.write( (byte) '\n');
                  corrupt.write(s[i].getBytes());
                }
                catch (IOException e) {}
              }
         }
         try{
             corrupt.close();
         }
         catch (IOException e) {}
       }    //------------------- end of Rik special
       biglist = new String[0];
       String s[] = student.alllist;
                                   // start rb 9/6/06   xxxx
       for(i=0;i<s.length;++i) {
          if(u.findString(student.adminlist,s[i]) <0  && u.findString(student.teacherlist,s[i]) <0) {
             String name = s[i];
             if(teacher.students != null && u.findString(teacher.students,s[i]) >= 0) continue;
             for(j=0;j<student.adminstudents.length;++j) {
                if(student.adminstudents[j] != null
                      && !teacher.name.equalsIgnoreCase(student.adminstudents[j][0])    // rb 9/2/06
                      && (
                      (u.findString(student.adminlist, student.adminstudents[j][0]) >= 0)||
                      (u.findString(student.teacherlist, student.adminstudents[j][0]) >= 0)  // for numbershark
                      )
                      ) {
                    for(k=1;k<student.adminstudents[j].length;++k) {
                      if(s[i].equalsIgnoreCase(student.adminstudents[j][k])) {
//                         v.add(new String[]{name, student.adminstudents[j][0]});
                        name = name + topicTree.ISTOPIC + student.adminstudents[j][0];
                        break;
                      }
                   }
                }
             }
             biglist = u.addStringSort(biglist,name);
          }
       }
                                // end rb 9/6/06
    }

    jnode rootc = (jnode)capturemodel.getRoot();
    rootc.removeAllChildren();
    for(int p = 0 ; p < biglist.length; p++){
        jnode childnode = new jnode(biglist[p]);
        childnode.setIcon(jnode.STUDENT);
        rootc.addChild(childnode);
    }
    capturemodel.reload();
    if(capturerenderer!=null)
        ((treepainter2_base)capturerenderer).startx = -1;
//    studentlc.setListData(biglist);
//    studentlc.setSelectedIndex(currstu1= Math.min(currstu1,studentlc.getModel().getSize()));
   }
   //------------------------------------------------------------------
        // see if student is in anybody elses list   // start rb 9/2/06
   boolean hasTeachers(String name) {
     int i,j;
     student.getadmin();
     for(i=0;i<student.adminstudents.length;++i) {
       if (student.adminstudents[i] != null
             && !student.adminstudents[i][0].equalsIgnoreCase(teacher.name)) {
           for(j=1;j<student.adminstudents[i].length;++j) {
                if (student.adminstudents[i][j].equalsIgnoreCase(name)) return true;
           }
       }
     }
     return false;
   }


   String[] getOtherTeachers(String name) {
       String ret[] = null;
          if(u.findString(student.adminlist,name) <0  && u.findString(student.teacherlist,name) <0) {
             for(int j=0;j<student.adminstudents.length;++j) {
                if(student.adminstudents[j] != null
                      && !teacher.name.equalsIgnoreCase(student.adminstudents[j][0])
                      && (
                      (u.findString(student.adminlist, student.adminstudents[j][0]) >= 0)||
                      (u.findString(student.teacherlist, student.adminstudents[j][0]) >= 0)  // for numbershark
                      )
                      ) {
                    for(int k=1;k<student.adminstudents[j].length;++k) {
                      if(name.equalsIgnoreCase(student.adminstudents[j][k])) {
                          if(ret==null)ret = new String[]{student.adminstudents[j][0]};
                          else ret = u.addString(ret, student.adminstudents[j][0]);
                        break;
                      }
                   }
                }
             }
          }
       return ret;

   }


  //-------------------------------------------------------------------------
      
   jnode addtotree(student newstu, jnode node) {
         if(node.type==jnode.STUDENT) node = (jnode)node.getParent();
//         String parent = node.get();
//         if(node==root) parent = teacher.name;
//         short i;
//         student stu;
         jnode jn;
         if((jn=find(userroot, newstu.name))!=null)return jn;
//         usertree.expandPath(new TreePath(node.getPath()));
         jnode newnode=new jnode(newstu.name);
//         student.seticon(newnode,newstu);
         if(newstu.administrator){
             if(newstu.teacher) newnode.setIcon(jnode.SUBADMIN);
             else newnode.setIcon(jnode.TEACHER);
         }
         else
             newnode.setIcon(jnode.STUDENT);
         usermodel.insertNodeInto(newnode,node,node.getChildCount());
         savetree();
         reloadUserModelNow();
         return newnode;
   }
   //---------------------------------------------------------------------


   
  jnode find(jnode node, String s) {
     for(Enumeration e = node.children();e.hasMoreElements();) {
         jnode c = (jnode)e.nextElement();
         if(c.get().equalsIgnoreCase(s)|| (c = find(c,s)) != null) 
             return c;
     }
     return null;
  }   
   
   
   
   void renamesubadmin(jnode jn, String newname){
       student stu = student.findStudent(jn.get());
       if(student.findStudent(newname) != null) {
           u.okmess(u.gettext("stulist_","renaming",jn.get()),u.gettext("stulist_","already",newname), thisadmin);
                 return;
       }
       PickPicture.renamed(stu.name, newname);
       db.rename(stu.name,newname);
       privateListRecord.adminRenamed(stu.name, newname);
       renameRecords(stu.name, newname);
       stu.releaselock();
       stu.name = newname;
       stu.saveStudent();
       student.checkadmin(stu);   // rb 6/2/06
       jn.setUserObject(newname);
       savetree();
       sharkStartFrame.mainFrame.setupgames();
       biglist = null;         // rb 8/2/06     
   }   
   void renameadmin(jnode jn, String newname){
       student stu = student.findStudent(jn.get());
       if(student.findStudent(newname) != null) {
           u.okmess(u.gettext("stulist_","renaming",jn.get()),u.gettext("stulist_","already",newname), thisadmin);
                 return;
       }
       PickPicture.renamed(stu.name, newname);
       db.rename(stu.name,newname);
       privateListRecord.adminRenamed(stu.name, newname);
       renameRecords(stu.name, newname);
       stu.releaselock();
       stu.name = newname;
       stu.saveStudent();
       student.checkadmin();   // rb 6/2/06
       jn.setUserObject(newname);
       savetree();
       sharkStartFrame.mainFrame.setupgames();
       biglist = null;         // rb 8/2/06
   }


   int deleteuser(student stu) {
       if(stu==null)return -1;
       try{
       boolean isadmin = stu.administrator;
         if(!stu.isLocked()) {
            if(!stu.getlock()) {
               return -1;
            }
         }                            // end rb 8/2/02
         db.destroy(stu.name);
         db.destroy(sharkStartFrame.resourcesPlus + stu.name + sharkStartFrame.resourcesFileSuffix);
         new File(sharkStartFrame.sharedPathplus+stu.name).delete();
         String strsturecfolder = u.gettext("sturec_", "folder");      
         String start = u.gettext("sturec_", "file");
         userToDelete = u.edit(start.substring(0,5), stu.name, shark.USBprefix);
         File f = new File(sharkStartFrame.sharedPath, strsturecfolder);
         if (f.exists()) {
             File list[] = f.listFiles(new java.io.FileFilter() {
                 public boolean accept(File file) {
                     return file.getName().startsWith(userToDelete);
                 }
             });          
             if(list.length > 0){
                 for(int i = 0; i < list.length; i++){
                    list[i].delete();
                 }
             }
         }        
         stu.releaselock();           //  rb 8/2/02
         biglist = null;    // force rebuild of student list
         if(isadmin)
            privateListRecord.adminDeleted(stu.name);
         student.checkadmin();
         if(isadmin)
           sharkStartFrame.mainFrame.gettopictreelist();
         return 0;
       }
       catch(Exception e){
        if(stu!=null)stu.releaselock();
        return -1;
       }
   }


 //------------------------------------------------------------------------
 void savetree() {
    teacher.students = savetree(currentnode,new String[0]);
    teacher.saveStudent();
    student.checkadmin(teacher);     // rb 9/2/06     make sure up-to-date
    sharkStartFrame.mainFrame.signlist.setEnabled(
       teacher.students != null
         && teacher.students.length > 0);
 }
 String[] savetree(jnode node,String s[]) {
    jnode c[] =  node.getChildren();
    for(short i=0;i<c.length;++i) {
      if(c[i].type == jnode.GROUP) {
        s = u.addString(s,topicTree.ISTOPIC+c[i].get());
        s = savetree(c[i],s);
        s = u.addString(s,new String(topicTree.ISTOPIC));
      }
      else   s = u.addString(s,this.extractname(c[i]));
    }
    return s;
 }



   String getAlreadyText(student user1){
      String mess;
      if(user1 != null){
          if(user1.teacher) return u.gettext("stulist_already", "alreadyteacher", user1.name);
          else if(user1.administrator) return u.gettext("stulist_already", "alreadyadmin", user1.name);
          else if(u.findString(teacher.students, user1.name)>=0){
             return u.gettext("stulist_already", "alreadystu2", user1.name);
          }
          else{
              mess = u.gettext("stulist_already", "alreadystu1");
              mess = mess.replaceAll("%", user1.name);
              mess = mess.replaceFirst("@", otherstunode_clickable.get());
              mess = mess.replaceFirst("@", str_curr);
              return mess.replaceFirst("@", strotherstudents);
          }
      }
      return u.gettext("stulist_", "already", user1.name);
  }


                        // start rb 8/2/06
 public static class stupainter extends JLabel implements ListCellRenderer {  // for student list
    stupainter() {setOpaque(true);}
    Object o;
    FontMetrics m;
    public Component getListCellRendererComponent( JList list,Object oo,
          int index,boolean isSelected,boolean cellhasFocus) {
       o = oo;
       this.setBackground(isSelected?list.getSelectionBackground():Color.white);
       setText((String)o);
       return this;
    }
    public void paint(Graphics g) {
          Rectangle r = getBounds();
          if(m==null) {
            m = getFontMetrics(getFont());
          }
          g.setFont(getFont());
          g.setColor(getBackground());
          g.fillRect(0,0,r.width,r.height);
          g.setColor(getForeground());
          String s = (String) o;
          int i;
          if((i=s.indexOf(topicTree.ISTOPIC))>=0)   {
            g.drawString(s.substring(0,i),0, r.height/2 - m.getHeight()/2 + m.getAscent());
            g.setColor(Color.red);
            String s2 = u.stringreplace(s.substring(i+1),topicTree.ISTOPIC.charAt(0),',');
            g.drawString(s2, m.stringWidth(s.substring(0,i)+"  "),r.height/2 - m.getHeight()/2 + m.getAscent());
          }
          else g.drawString(s,0,r.height/2 - m.getHeight()/2 + m.getAscent());
     }
 }


  class newUser extends NewUser_base {
   newUser(int type, JDialog owner, String nam, int nodetype1) {
       super(type, owner, nam, nodetype1);
    }
   public void okpressed() {  
        super.okpressed();
        if(exit)return;
               if((addpasswordcb.isVisible() && addpasswordcb.isSelected()) ||
                       (!addpasswordcb.isVisible() && passwordtf.isVisible())){                    
                    if(mode==MODE_PASSWORDONLY){
                        if(currstu != null) {
                                if(pass == null){
                                    student.queueupdate(new String[]{currstu.name},new String[]{"password"});
                                }
                                else{
                                  student.queueupdate(new String[]{currstu.name},new String[]{"password",pass});
                                }
                                
                              if(passwordhint==null){
                                    student.queueupdate(new String[]{currstu.name},new String[]{"passwordhint"});
                                }
                                else{
                                    student.queueupdate(new String[]{currstu.name},new String[]{"passwordhint",passwordhint});
                                }
                            
                                if(!removepass && currstu.name.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name)){
                                    sharkStartFrame.studentList[sharkStartFrame.currStudent].password = pass==null?null:student.encrypt(pass);
                                    sharkStartFrame.studentList[sharkStartFrame.currStudent].passwordhint = passwordhint;
                                }
                            }
                        }
                        else if(mode==MODE_PASSWORDONLY_RETURNVAL){
                            returnpass = pass;
                            returnpasswordhint = passwordhint;
                        }
                        TreePath tp = usertree.getSelectionPath();
                        if(tp!=null)
                            changenodeselection((jnode)tp.getLastPathComponent());
 //                       thisd.dispose();
                    
                }
               if(mode!=MODE_PASSWORDONLY && mode!=MODE_PASSWORDONLY_RETURNVAL){
                    String s = usertf.getText().trim();
                    if(s.equals(""))return;
                    student st1;
                    if((st1 = student.findStudent(s)) != null) {
                       clearEntries();
                       String errmess = getAlreadyText(st1);
                       if(errmess!=null)
                           u.okmess(u.gettext("adminnewuser", "alreadytitle"),
                             u.convertToHtml(errmess), thisd);
                      return;
                    }
                    if(mode==MODE_NEWSTU){
                        newstudent(s, pass, passwordhint);
                    }
                    else if(mode==MODE_NEWADMIN){
                        newadmin(s, pass, passwordhint);
                    }
                    else if(mode==MODE_NEWSUBADMIN){
                        newteacher(s, pass, passwordhint);
                    }
               }              
        dispose();
   }
  }
 

  class removeUnwantedUsers extends JDialog {
      JDialog thiswin = this;
      Thread scanThread;
      Thread removeThread;
      scanUsersThread sut;
      removeUsersThread rut;
      JPanel pnProgress;
      JPanel mainpan;
      String users[];
      Date dates[];
      JList jlUsers;
      JButton btCancel;
      JButton btArchiveUsers;
      JButton btDeleteUsers;
      JButton bSelectAll;
      JComboBox jcbPeriod;
      int dTimes[];
      JLabel lbProgress;
      int insets_small = 5;
      int insets_medium = 10;
      int insets_big = 15;
       
   removeUnwantedUsers(JDialog owner) {
       super(owner);
       setResizable(false);
       setModal(true);
       getContentPane().setLayout(new GridBagLayout());
       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2 = sw*5/12;
       int sh2 = sh*8/12;
            if(!ChangeScreenSize_base.isActive){
               setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);
            }
            else{
               setBounds(sharkStartFrame.mainFrame.getLocation().x+(sw-sw2)/2, 
                        sharkStartFrame.mainFrame.getLocation().y+(sh-sh2)/2, 
                        sw2, sh2);
            }
       GridBagConstraints g = new GridBagConstraints();
       setTitle(u.gettext("redundantusers", "title"));
       g.gridx = 0;
       g.gridy = -1;
       g.weightx = 1;
       g.weighty = 0;
       g.fill = GridBagConstraints.NONE;
       JProgressBar pBar = new JProgressBar();
       pBar.setIndeterminate(true);
       Dimension d2 = new Dimension((int)this.getWidth()*2/5, (int)this.getHeight()*1/20);
       pBar.setPreferredSize(d2);
       pBar.setMinimumSize(d2);
       pBar.setMaximumSize(d2);       
       pnProgress = new JPanel(new GridBagLayout());
       g.insets = new Insets(0,0,20,0);
       lbProgress = new JLabel(u.gettext("redundantusers", "scanning"));
       pnProgress.add(lbProgress, g);
       pnProgress.add(pBar, g);    
       g.insets = new Insets(0,0,0,0);
       mainpan = new JPanel(new GridBagLayout());
       g.fill = GridBagConstraints.BOTH;
       g.weightx = 1;
       g.weighty = 1;
       mainpan.setVisible(false);
       getContentPane().add(mainpan, g);
       getContentPane().add(pnProgress, g);
       scanThread = new Thread(sut = new scanUsersThread());
       scanThread.start();       
       setVisible(true);
   }
   
   void setupUI(){
       GridBagConstraints g = new GridBagConstraints();
       g.gridx = 0;
       g.gridy = -1;
       g.weightx = 0;
       g.weighty = 0;
       g.fill = GridBagConstraints.NONE;       
       
       btCancel = u.sharkButton(u.gettext("cancel", "label"));
       btArchiveUsers = u.sharkButton();
       String strtooltip = u.gettext("redundantusers", "btarchive_tooltip");
       strtooltip = strtooltip.replaceFirst("%", shark.programName);
       strtooltip = strtooltip.replaceFirst("%", u.gettext("redundantusers", "folder"));
       btArchiveUsers.setText(u.gettext("redundantusers", "btarchive"));
       btArchiveUsers.setToolTipText(strtooltip);
       btDeleteUsers = u.sharkButton();
       btDeleteUsers.setText(u.gettext("redundantusers", "btdelete"));
       btDeleteUsers.setToolTipText(u.gettext("redundantusers", "btdelete_tooltip", shark.programName));
       bSelectAll = u.sharkButton();
       bSelectAll.setText(u.gettext("redundantusers", "btselectall"));
       btCancel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            thiswin.dispose();
           }
       });
       btArchiveUsers.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               removeThread = new Thread(rut = new removeUsersThread(true));
               removeThread.start(); 
           }
       });
       btDeleteUsers.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               removeThread = new Thread(rut = new removeUsersThread(false));
               removeThread.start();                
           }
       });
       bSelectAll.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
                int start = 0;
                int end = jlUsers.getModel().getSize() - 1;
                if (end >= 0) {
                  jlUsers.setSelectionInterval(start, end);
                }
           }
       });
  
       JLabel lbTimePeriod = new JLabel(u.gettext("redundantusers", "lbtimeperiod"));  
       JTextArea taInfo = new JTextArea();
       taInfo.setEditable(false);
       taInfo.setWrapStyleWord(true);
       taInfo.setLineWrap(true);
       taInfo.setText(u.convertToCR(u.gettext("redundantusers", "lbshowusers", shark.programName + " " + shark.versionNo)));
       taInfo.setOpaque(false);      
       String strTimes = u.gettext("redundantusers", "timeperiods", shark.programName + " " + shark.versionNo);
       String periods[] = u.splitString(strTimes);
       dTimes = new int[]{-1,-1,1,2,3,4,5,6,7,8,9,10,11,12,24,36,48,60};
       jcbPeriod = new JComboBox(periods);
       jcbPeriod.setSelectedIndex(0);
       jcbPeriod.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               setUsers();
               setButtons();
           }
       });
       
       jlUsers = new JList();
       jlUsers.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                setButtons();
            }
        });       
       JPanel pnInner = new JPanel(new GridBagLayout());
//       pnInner.setBorder(BorderFactory.createEtchedBorder());
       g.fill = GridBagConstraints.HORIZONTAL;
       g.insets = new Insets(0,0,insets_small,0);
       pnInner.add(taInfo, g);
       g.insets = new Insets(0,0,0,0);
       g.fill = GridBagConstraints.NONE;
       g.gridx = -1;
       g.gridy = 0;       
       JPanel pnCombo = new JPanel(new GridBagLayout());
       g.insets = new Insets(0,0,0,insets_medium);
       pnCombo.add(lbTimePeriod, g);
       g.insets = new Insets(0,0,0,0);
       pnCombo.add(jcbPeriod, g);
       g.gridx = 0;
       g.gridy = -1;   
       g.anchor = GridBagConstraints.WEST;
       g.insets = new Insets(0,0,insets_medium,0);
       pnInner.add(pnCombo, g);
       g.insets = new Insets(0,0,0,0);
       g.anchor = GridBagConstraints.CENTER;
       JPanel pnList = new JPanel(new GridBagLayout());
       g.gridx = -1;
       g.gridy = 0;  
       g.fill = GridBagConstraints.BOTH;
       g.weightx = 1;
       g.weighty = 1;
       JScrollPane scroller = new JScrollPane(jlUsers);
       JPanel pnInnerScroller = new JPanel(new GridBagLayout());
       
       g.gridx = 0;
       g.gridy = -1;  
       g.weighty = 1;
       pnInnerScroller.add(scroller, g);
       g.weighty = 0;
       JLabel lbSelect = new JLabel();
       if(shark.macOS)
           lbSelect.setText(u.convertToHtml(u.gettext("redundantusers", "lbclicktosel_mac")));
       else
           lbSelect.setText(u.convertToHtml(u.gettext("redundantusers", "lbclicktosel")));
       Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
       Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-1);
       lbSelect.setFont(smallerplainfont);
       pnInnerScroller.add(lbSelect, g);           
       g.gridx = -1;
       g.gridy = 0;  
       g.weighty = 1;           
       pnList.add(pnInnerScroller, g);
//       g.fill = GridBagConstraints.NONE;
       g.weightx = 0;
       g.fill = GridBagConstraints.NONE;
       g.anchor = GridBagConstraints.NORTH;
       g.insets = new Insets(0,insets_medium,0,0);
       pnList.add(bSelectAll, g);
       g.insets = new Insets(0,0,0,0);
       g.anchor = GridBagConstraints.CENTER;
       g.fill = GridBagConstraints.BOTH;
       g.weightx = 1;
       g.gridx = 0;
       g.gridy = -1; 
       g.weightx = 1;
       g.weighty = 1;
       pnInner.add(pnList, g);    
       g.fill = GridBagConstraints.NONE;
       
       g.weighty = 0;
       JPanel pnButtons = new JPanel(new GridBagLayout());
       g.gridx = -1;
       g.gridy = 0; 
       g.insets = new Insets(0,0,0,insets_big); 
       if(!shark.macOS){
          pnButtons.add(btDeleteUsers, g); 
          pnButtons.add(btArchiveUsers, g); 
          g.insets = new Insets(0,0,0,0); 
          pnButtons.add(btCancel, g); 
       }
       else{
          pnButtons.add(btCancel, g);
          pnButtons.add(btArchiveUsers, g); 
          g.insets = new Insets(0,0,0,0); 
          pnButtons.add(btDeleteUsers, g);            
       }
       g.gridx = 0;
       g.gridy = -1;
       g.insets = new Insets(insets_big,0,0,0); 
       pnInner.add(pnButtons, g);      
       g.insets = new Insets(0,0,0,0);
       g.fill = GridBagConstraints.BOTH;
       g.insets = new Insets(20,20,20,20);
       g.weighty = 1;
       mainpan.add(pnInner, g);
       setButtons();
       pnProgress.setVisible(false);
       mainpan.setVisible(true);
   }
   
   
    void setButtons(){
      boolean periodselected =  jcbPeriod.getSelectedIndex()!=0;
      boolean empty = jlUsers.getModel().getSize()==0;
      int selLength = jlUsers.getSelectedIndices().length;
      btArchiveUsers.setEnabled(selLength>0);
      btDeleteUsers.setEnabled(selLength>0);
      bSelectAll.setEnabled(!empty);      
   }  
   
   
   void setUsers(){
       int sel = jcbPeriod.getSelectedIndex();
       if(sel<1)return;
       String ss[] = new String[]{};
       boolean findnulls = sel == 1;
       if(findnulls){
           for(int i = 0; i < dates.length; i++){
               if(dates[i]==null)ss = u.addString(ss, users[i]);
           }
       }
       else{
           Calendar c = Calendar.getInstance();
           c.add(Calendar.MONTH, -dTimes[sel]);
           Date limitDate = c.getTime();
           for(int i = 0; i < dates.length; i++){
               if(dates[i]==null || dates[i].before(limitDate))ss = u.addString(ss, users[i]);
           }
       }
       jlUsers.setListData(ss);
   }

   
   
    public class scanUsersThread implements Runnable{
            public void run(){
                 users = student.alllist;
                 for(int i = users.length-1; i >= 0; i--){
                     if(u.findString(student.adminlist,users[i]) >= 0  || 
                             u.findString(student.teacherlist,users[i]) >= 0){
                         users = u.removeString(users, i);
                     }
                 }
                 dates = new Date[users.length];
                 for(int i = 0; i < users.length; i++){
                     student stu = student.findStudent(users[i]);
                     if(stu==null)continue;
                     if(stu.lastplayed==0)dates[i] = null;
                     else{
                         try{
                            dates[i] = new Date(stu.lastplayed);
                         }
                         catch(Exception e){
                            dates[i] = null;
                         }
                     }
                 }                    
                setupUI();
            }
        }
    
    public class removeUsersThread implements Runnable{
        boolean toArchive;
        File destf;
           public removeUsersThread(boolean archive) {
               toArchive = archive;
               if(toArchive){
                   Calendar now = new GregorianCalendar();
                   String uu = String.valueOf(now.get(Calendar.MONTH)+1);
                   while(uu.length()<2)uu = "0"+uu;
                   String u2 = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
                   while(u2.length()<2)u2 = "0"+u2;   
                   String destFolder = sharkStartFrame.sharedPathplus+u.gettext("redundantusers", "folder")+shark.sep+
                           String.valueOf(now.get(Calendar.YEAR)+ "-" + uu + "-" + u2); 
                   destf =  new File(destFolder);
               }
           }
       
            public void run(){
                int sels[] = jlUsers.getSelectedIndices();
                String selusers[] = new String[]{};
                for(int i = 0; i < sels.length; i++){
                   selusers = u.addString(selusers, (String)jlUsers.getModel().getElementAt(sels[i]));
                }
                if(selusers.length>0){          
                    student stu;
                    String otherteachers[];
                    boolean delProb = false;
                    lbProgress.setText(u.gettext("redundantusers", "removing"));
                    pnProgress.setVisible(true);
                    mainpan.setVisible(false);
                    for(int i = 0; i < selusers.length; i++){
                        stu = student.findStudent(selusers[i]);
                        if((otherteachers = getOtherTeachers(stu.name))!=null){
                             String mess2 = "";
                             for(int k = 0; k < otherteachers.length; k++){
                                 mess2 += otherteachers[k];
                                 if(k < otherteachers.length-1)
                                    mess2 += ", ";
                             }
                             String s2 = u.gettext("redundantusers", "alreadycollected");
                             s2 = s2.replaceFirst("%", stu.name);
                             s2 = s2.replaceFirst("%", mess2);
                             s2 = s2.replaceFirst("%", shark.programName);
                             if(!u.yesnomess(shark.programName,s2, thiswin)){
                                continue;   
                             }
                         }
                         if(toArchive){
                             if(!destf.exists())destf.mkdirs();        
                             doUserExport_Work duew = new doUserExport_Work(new String[]{selusers[i]}, destf, false);
                             Thread archiveThread = new Thread(duew);
                             archiveThread.start();
                             while(archiveThread.isAlive())u.pause(500);
                         }
                         delProb = deleteuser(stu)<0;                           
                         if(delProb){
                             if(toArchive && destf!=null){
                                 File f2;
                                 if((f2=new File(destf.getAbsolutePath()+shark.sep+selusers[i]+"."+userExportExtension)).exists()){
                                     f2.delete();
                                 }
                             }
                             u.okmess(u.gettext("stulist_stuloggedon_del","heading"), u.gettext("stulist_stuloggedon_del","text",stu.name), thiswin);
                             continue;
                         }  
                         jnode jn = (jnode)usertree.searchNode(selusers[i]);
                         if(jn !=null){
                           jnode parent = (jnode)jn.getParent();
                           if(parent!=null){
                               usermodel.removeNodeFromParent(jn);
                               reloadUserModelNow();
                           }
                         }                         
                         setstudentlc();
                    }            
                }
                thiswin.dispose();
            }
        }    
    
    }
  
  
  
  class MultStuOptions extends JDialog {
      JDialog thiswin;

   MultStuOptions(JDialog owner) {
       super(owner);
       setResizable(false);
       setModal(true);
       thiswin = this;
       getContentPane().setLayout(new GridBagLayout());
       int sw = sharkStartFrame.mainFrame.getWidth();
       int sh = sharkStartFrame.mainFrame.getHeight();
       int sw2 = sw*5/12;
       int sh2 = sh*5/12;
            if(!ChangeScreenSize_base.isActive){
               setBounds((sw-sw2)/2, (sh-sh2)/2, sw2, sh2);
            }
            else{
               setBounds(sharkStartFrame.mainFrame.getLocation().x+(sw-sw2)/2, 
                        sharkStartFrame.mainFrame.getLocation().y+(sh-sh2)/2, 
                        sw2, sh2);
            }
       GridBagConstraints g = new GridBagConstraints();
       setTitle(u.gettext("multstu", "title"));
       g.gridx = 0;
       g.gridy = -1;
       g.weightx = 1;
       g.weighty = 1;
       g.fill = GridBagConstraints.NONE;
       JButton type = u.sharkButton("multstu_type");
       JButton program = u.sharkButton("multstu_program");
       JButton file = u.sharkButton("multstu_file");
       JButton csv = u.sharkButton("multstu_csv");

       type.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            thiswin.dispose();
            inputStuList();
           }
       });
       program.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               thiswin.dispose();
            importFromProgram();
           }
       });
       file.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               thiswin.dispose();
               importFromClipboard();
           }
       });
       csv.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               importFromFile();
               thiswin.dispose();
           }
       });

       program.setToolTipText(program.getToolTipText().replaceFirst("%", shark.otherProgram));
       program.setToolTipText(program.getToolTipText().replaceFirst("%", shark.programName));
       file.setToolTipText(file.getToolTipText().replaceFirst("%", shark.programName));
       JPanel typepan = new JPanel(new GridBagLayout());
       JPanel programpan = new JPanel(new GridBagLayout());
       JPanel filepan = new JPanel(new GridBagLayout());
       JPanel csvpan = new JPanel(new GridBagLayout());
       JPanel labelspan = new JPanel(new GridBagLayout());
       JPanel buttonspan = new JPanel(new GridBagLayout());

       g.gridx = -1;
       g.gridy = 0;
       g.anchor = GridBagConstraints.WEST;
       g.fill = GridBagConstraints.BOTH;
       typepan.add(new JLabel(u.gettext("multstu", "type")), g);
       programpan.add(new JLabel(u.gettext("multstu", "program").replaceFirst("%", shark.otherProgram)), g);
       filepan.add(new JLabel(u.gettext("multstu", "file")), g);
       csvpan.add(new JLabel(u.gettext("multstu", "csv")), g);

       g.anchor = GridBagConstraints.WEST;
       g.fill = GridBagConstraints.NONE;
       g.gridx = 0;
       g.gridy = -1;

       g.insets = new Insets(0,0,0,0);
       labelspan.add(typepan, g); 
       labelspan.add(programpan, g);
       labelspan.add(filepan, g);
       labelspan.add(csvpan, g);
       g.fill = GridBagConstraints.HORIZONTAL;
       g.anchor = GridBagConstraints.WEST;
       buttonspan.add(type, g);
       buttonspan.add(program, g);
       buttonspan.add(file, g);
       buttonspan.add(csv, g);
       g.fill = GridBagConstraints.NONE;
       JPanel mainpan = new JPanel(new GridBagLayout());
       JPanel mainpanborder = new JPanel(new GridBagLayout());
       g.gridx = -1;
       g.gridy = 0;
       g.fill = GridBagConstraints.VERTICAL;
       g.anchor = GridBagConstraints.EAST;
       g.insets = new Insets(20,0,0,20);
       mainpan.add(labelspan, g);
       g.anchor = GridBagConstraints.WEST;
       g.insets = new Insets(0,0,0,0);
       mainpan.add(buttonspan, g);
       g.anchor = GridBagConstraints.CENTER;
       JPanel buttonpn = new JPanel(new GridBagLayout());
       JButton btCancel = new JButton(u.gettext("cancel", "label"));
       btCancel.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               thiswin.dispose();
           }
       });
       mainpanborder.setBorder(BorderFactory.createEtchedBorder());
       g.insets = new Insets(5,5,5,5);
       mainpanborder.add(mainpan, g);
       g.gridx = 0;
       g.gridy = -1;
       g.weighty = 0;
       g.insets = new Insets(20,0,20,0);
       buttonpn.add(btCancel, g);
       g.insets = new Insets(20,0,0,0);
       g.weighty = 1;
       getContentPane().add(mainpanborder, g);
       g.weighty = 0;
       g.insets = new Insets(0,0,0,0);
       getContentPane().add(buttonpn, g);
       setVisible(true);
   }
 }

   class LinedPanel extends JPanel {
       public JPanel pnlines[];


       public LinedPanel(GridBagLayout gbl) {
        super(gbl);
       }

       public void paint(Graphics g) {
           super.paint(g);
        g.setColor(worksettingpancolor);
        if(pnlines!=null){
          for(int i = 0; i < pnlines.length; i++){
              if(pnlines[i]==null)continue;
              int yy =  (int)pnlines[i].getLocationOnScreen().getY() + (pnlines[i].getHeight()/2)
                      - this.getLocationOnScreen().y;

              yy = (int)pnlines[i].getLocationOnScreen().getY()- (int)this.getLocationOnScreen().getY();
              g.drawLine(this.getLocation().x, yy,
                      this.getWidth(), yy);
              }
          }
        }
     }
  


  class keydoc extends KeyDoc_base {
     JTextField owner;
     JButton enablebut =  null;
     keydoc(JTextField ow, int max, JButton button) {
       super(max);
       enablebut = button;
       owner = ow;
     }
     public void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
        try{
            String t1  = ((JTextField)this.owner).getText();
            String t2 = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
            if(enablebut!=null)
                enablebut.setEnabled(!t1.equalsIgnoreCase(t2) && !t1.trim().equals(""));
        }
        catch(Exception e2){
            if(enablebut!=null)
                enablebut.setEnabled(true);
        }
     }
     public void removeUpdate(DefaultDocumentEvent chng) {
        try{
            super.removeUpdate(chng);
            String t1  = ((JTextField)this.owner).getText();
            String tt2 = t1.substring(0, chng.getOffset());
            String tt3 = t1.substring(chng.getOffset()+chng.getLength());
            t1 = tt2+tt3;
            String t2 = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
            if(enablebut!=null)
                enablebut.setEnabled(!t1.equalsIgnoreCase(t2) && !t1.trim().equals(""));
        }
        catch(Exception e2){
            if(enablebut!=null)
                enablebut.setEnabled(true);
        }
     }
     
     public void insertString(int o, String s, AttributeSet a) {
       if(s.equals(currentnode.get()))return;
       super.insertString(o, s, a);
     }
   }


    public class wordpicture extends JPanel {
    public runMovers mainPanel;
    boolean isshowingimport = false;

    public wordpicture(int x1, int y1, int w1, int h1) {
      mainPanel = new runMovers();
      add(mainPanel, BorderLayout.CENTER);
      setLayout(new BorderLayout());
      setBounds(x1, y1, w1, h1);
      mainPanel.setBounds(0, 0, w1, h1);
    }

    public void stop() {
      mainPanel.stoprun = true;
      if (isShowing()) {
        mainPanel.setLocation(new Point(0, 0));
      }
      mainPanel.reset();
      revalidate();
    }

    public void reposition() {
      stop();
      mainPanel.stoprun = false;
      mainPanel.start1();
    }

    public void addPic(sharkImage im, String dbs) {
        String dbname = dbs;
      mainPanel.removeAllMovers();
      isshowingimport = false;
  //      byte buf[] = (byte[]) db.find(dbname, PickPicture.ownpic, db.PICTURE);
        sharkImage si =null;
  //      if (buf != null) {
  //          boxDelete.setEnabled(true);

  //        si = new sharkImage(sharkStartFrame.t.createImage(buf), PickPicture.ownpic);
  //      }

        si = sharkImage.find(PickPicture.ownpic, new student(dbname), true);
        boxDelete.setEnabled(si!=null);
        if(si==null){
//            student st1 = student.findStudent(dbname);
//            boolean isstu = st1!=null && !st1.administrator;
            boolean isstu = (!(u.findString(student.adminlist, dbname)>=0 || u.findString(student.teacherlist, dbname)>=0));  
            si = new sharkImage(isstu?anon_stu:anon, "anon");
        }
        if(si==null)return;
        im = si;
        isshowingimport = true;
 //     int totims = imarr.length;
//      int across = (int) Math.ceil(Math.sqrt(totims));
//      int down = totims / across;
//      if (totims % across != 0) {
//        down++;
//      }
      int x = 0;
      int y = 0;
//      for (int i = 0; i < totims; i++) {
        if (im.isimport) {
          isshowingimport = true;
        }
 //       if (i != 0 && i % across == 0) {
 //         x = 0;
 //         y += mover.HEIGHT / across;
 //       }
        im.w = mover.WIDTH;
        im.h = mover.HEIGHT;
        im.adjustSize((int)dropBox.dim.getWidth(),
                            (int)dropBox.dim.getHeight());
        im.keepMoving = true;
        int k;
        if (im.w > im.h) {
          k = (mover.HEIGHT - im.h) / 2;
          mainPanel.addMover(im, x, y + k);
        }
        else {
          k = (mover.WIDTH - im.w) / 2;
          mainPanel.addMover(im, x + k, y);
        }
        x += mover.WIDTH;
 //     }
      addPic2();
    }

    public void addPic(String mess) {
      mainPanel.removeAllMovers();
      isshowingimport = false;
      mover.simpletextmover tt = new mover.simpletextmover(mess,
          mover.WIDTH / 2, mover.HEIGHT / 2);
      mainPanel.addMover(tt, tt.w / 2, mover.HEIGHT / 4);

      addPic2();
    }

    void addPic2() {
      dropBox.activate(dropBox.wp);
      mainPanel.stoprun = false;
      mainPanel.start1();
      dropBox.validate();
      dropBox.repaint();
 //     if (si != null) {
//        btDelete.setEnabled(si[0].isimport);
//    }
  }

    }

    void setRightPanelBlank(boolean blank){
        setRightPanelBlank( blank, false);
    }

    void setRightPanelBlank(boolean blank, boolean keepblank){
        if(keepRightPanelBlank)return;
        if(keepblank) keepRightPanelBlank = true;
        if(blank ){
                  dropBox.wp.mainPanel.pause = true;
                    blankpan.setVisible(true);
                    capturepan.setVisible(false);
                    adminhelppan.setVisible(false);
                    profilepan.setVisible(false);
                    setWorkVisible(-2);
        }
        else{
                  dropBox.wp.mainPanel.pause = false;
                   blankpan.setVisible(false);
        }

    }





    

    
    
    
    
   class GetPicture implements Runnable {
    File fths[];
    GetPicture thispic;
    public boolean stop = false;
    String picname;
    String dbname;
    boolean add;

    public GetPicture(File[] fthred, String databname, String name, boolean wantadd) {
      thispic = this;
      fths = fthred;
      picname = name;
      dbname = databname;
      add = wantadd;
    }

    public void run() {
      Vector v = new Vector();

      for (int i = 0; i < fths.length && !stop; i++) {
        try {
  //        dropBox.lbProgress.setText(u.gettext("pickpicture", "processing1"));
          ImageUtil_base iu = new ImageUtil_base();
          byte buf[] = iu.compressToBytes(fths[i]);
          if(picname==null)
             db.update(dbname, fths[i].getName(), buf, db.PICTURE);
          else
             db.update(dbname, picname, buf, db.PICTURE);

          if(usertree.getSelectionCount()==1){
            if((jnode)usertree.getSelectionPath().getLastPathComponent()==younode
                    &&  sharkStartFrame.mainFrame.currUserPic!=null)
                sharkStartFrame.mainFrame.currUserPic.refresh();
          }
          v.add(picname);//dbname.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name)?PickPicture.ownpic:PickPicture.adminownpic);
          buf = null;
        } catch (Exception e) {
          u.okmess(shark.programName, u.gettext("pickpicture", "error"));
        }
      }
      if (stop) {
        for (int p = 0; p < v.size(); p++) {
          db.delete(dbname, (String) v.get(p), db.PICTURE);
        }
        v = null;
      }
      if(add)
          converted(v, dbname);
    }
  }

  void converted(Vector v, String name) {
    dropBox.wp.addPic(null, name);
//        btDelete.setEnabled(true);
    dropBox.wp.reposition();
  }

  class mainPan extends JPanel implements DropTargetListener, Serializable {
    public JButton btCancel = new JButton(u.gettext("cancel", "label"));
    public JLabel lbProgress = new JLabel();
    public JPanel jpProgress;
    public wordpicture wp;
 //   public JScrollPane listwordsscroll;

    GridBagConstraints grid;
    JProgressBar pBar;
    File ff[];
    DropTarget dt;
//    boolean drawwaiting = false;
    int insComponent = 12;
    public GetPicture gp;
    public Dimension dim;

    public mainPan(Dimension d) {
      super();
      dim = d;
      dt = new DropTarget(this, this);
//      drawwaiting = false;
      setLayout(new GridBagLayout());
      jpProgress = new JPanel();
      jpProgress.setLayout(new GridBagLayout());
      setBorder(BorderFactory.createLoweredBevelBorder());
      setMaximumSize(dim);
      setPreferredSize(dim);
      setMinimumSize(dim);
      grid = new GridBagConstraints();
      grid.insets = new Insets(0, 0, 0, 0);
      grid.weighty = 0;
      grid.weightx = 1;
      grid.gridx = 0;
      grid.gridy = -1;
      grid.fill = GridBagConstraints.NONE;
      pBar = new JProgressBar();
      pBar.setIndeterminate(true);
      grid.insets = new Insets(0, 0, insComponent, 0);
      jpProgress.add(lbProgress, grid);
      jpProgress.add(pBar, grid);
      jpProgress.add(btCancel, grid);
      Dimension d2 = new Dimension((int)dim.getWidth()*4/5, (int)dim.getHeight()*1/8);
      pBar.setPreferredSize(d2);
      pBar.setMinimumSize(d2);
      pBar.setMaximumSize(d2);

      grid.insets = new Insets(0, 0, 0, 0);
      jpProgress.setOpaque(true);
      wp = new wordpicture(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
      wp.setMaximumSize(dim);
      wp.setPreferredSize(dim);
      wp.setMinimumSize(dim);
      grid.weighty = 1;
      grid.weightx = 1;
      grid.gridx = 0;
      grid.gridy = -1;
      grid.fill = GridBagConstraints.BOTH;
      add(jpProgress, grid);
      add(wp, grid);
  //    setMaximumSize(dim);
   //   setPreferredSize(dim);
  //    setMinimumSize(dim);
      activate(wp);
      btCancel.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          gp.stop = true;
        }
      });
    }

    public void activate(JComponent pan) {
      wp.setVisible(pan == null ? false : pan.equals(wp));
      if (!wp.isVisible()) {
        wp.mainPanel.stoprun = true;
        revalidate();
      }
      else {
        wp.mainPanel.stoprun = false;
        wp.mainPanel.start1();
      }
      jpProgress.setVisible(pan == null ? false : pan.equals(jpProgress));
      pBar.setVisible(pan == null ? false : pan.equals(jpProgress));
      btCancel.setVisible(pan == null ? false : pan.equals(jpProgress));
//      listwordsscroll.setVisible(pan == null ? false : pan.equals(listwordsscroll));
    }

    public void dragEnter(DropTargetDragEvent dsde) {
      doDragEnter(this);
    }

    public void dragExit(DropTargetEvent dse) {}

    public void dragOver(DropTargetDragEvent dsde) {}

    public void dropActionChanged(DropTargetDragEvent dsde) {}

    public void drop(DropTargetDropEvent dsde) {
      doDrop(dsde);
    }

      void doDragEnter(Component c) {
    if (!c.hasFocus()) {
      c.requestFocusInWindow();
      c.requestFocus();
    }
  }

  void doDrop(DropTargetDropEvent dsde) {
    DataFlavor[] flavors = {DataFlavor.javaFileListFlavor};
    File ff[] = new File[] {};
    try {
      Transferable te = dsde.getTransferable();
      dsde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
      flavors = te.getTransferDataFlavors();
      for (int i = 0; i < flavors.length; i++) {
        DataFlavor dataFlavor = flavors[i];
        try {
          if (dataFlavor.equals(DataFlavor.javaFileListFlavor)) {
            java.util.List fileList = (java.util.List) te.getTransferData(
                dataFlavor);
            for (int k = 0; k < fileList.size(); k++) {
              Object o = fileList.get(k);
              if (o instanceof File) {
                ff = u.addFile(ff, (File) o);
              }
            }
          }
          else {
            if (dataFlavor.equals(DataFlavor.stringFlavor)) {}
          }
        } catch (Exception e) {}
      }
      fileselected(ff);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  }

    void fileselected(File[] ff) {
    if (ff.length < 1) {
      return;
    }
    dropBox.activate(dropBox.jpProgress);
    if (ff.length > 1) {
      dropBox.btCancel.setVisible(true);
    }
    else {
      dropBox.btCancel.setVisible(false);
    }
    dropBox.wp.mainPanel.removeAllMovers();
    dropBox.wp.mainPanel.stoprun = true;
    dropBox.wp.mainPanel.removeAllMovers();
    dropBox.wp.mainPanel.stoprun = true;
    Thread myThread;
    String name = ((jnode)usertree.getSelectionPath().getLastPathComponent()).get();
    myThread = new Thread(dropBox.gp = new GetPicture(ff, 
            name,
            PickPicture.ownpic, true));
    myThread.start();
  }
    
    
    
   class exportuser extends JDialog {

       JTextField tfield;
       JButton okbut;
       JButton cancelbut;
       JButton browsebut;
       JDialog thisel;
       String exusers[];
       String tofile;


       public exportuser(JDialog owner, String[] users) {
         super(owner);
         thisel = this;
         exusers = users;
         this.setTitle(u.gettext("adminexportuser", "exporttitle"));
         this.setResizable(false);
         int w = sharkStartFrame.mainFrame.getSize().width;
         int h = sharkStartFrame.mainFrame.getSize().height;
         int w2 = w*4/9;
         int h2 = h*9/32;
            if(!ChangeScreenSize_base.isActive){
               setBounds((w-w2)/2,(h-h2)/2,w2,h2);
            }
            else{
              setBounds(sharkStartFrame.mainFrame.getLocation().x+(w-w2)/2, 
                        sharkStartFrame.mainFrame.getLocation().y+(h-h2)/2, 
                        w2,h2);
            }
         this.getContentPane().setLayout(new GridBagLayout());
         GridBagConstraints grid = new GridBagConstraints();
         grid.gridx = -1;
         grid.gridy = 0;
         grid.weightx = 1;
         grid.weighty = 1;
         grid.fill = GridBagConstraints.NONE;
         JPanel renamepn = new JPanel(new GridBagLayout());
         okbut = u.sharkButton();
         okbut.setText(u.gettext("ok", "label"));
         cancelbut = u.sharkButton();
         cancelbut.setText(u.gettext("cancel", "label"));
         JPanel butpn = new JPanel(new GridBagLayout());
         butpn.add(shark.macOS? cancelbut:okbut, grid);
         butpn.add(shark.macOS?okbut: cancelbut, grid);
         grid.weighty = 0;
         grid.gridx = 0;
         grid.gridy = -1;
         grid.anchor = GridBagConstraints.WEST;
         String ss = exusers.length>1?u.gettext("adminexportuser", "exportlb_p"):u.gettext("adminexportuser", "exportlb_s", exusers[0]);
         renamepn.add(new JLabel(ss), grid);
         tfield = new JTextField(20);
         grid.fill = GridBagConstraints.HORIZONTAL;
         tfield.setText(System.getProperty("user.home"));
         browsebut = u.sharkButton();
         browsebut.setText(u.gettext("browse", "label"));
         grid.gridx = -1;
         grid.gridy = 0;
         JPanel tfPanel = new JPanel(new GridBagLayout());
         tfPanel.add(tfield, grid);
         grid.insets = new Insets(0,10,0,0);
         grid.weightx = 0;
         tfPanel.add(browsebut, grid);
         grid.weightx = 1;
         grid.gridx = 0;
         grid.gridy = -1;
         grid.insets = new Insets(10,0,15,0);
         renamepn.add(tfPanel, grid);
         grid.insets = new Insets(0,0,0,0);
        okbut.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                tofile = tfield.getText();
                if(tofile.endsWith(shark.sep)) tofile = tofile.substring(0, tofile.length()-1);
                File exportto = new File(tofile);
                if(!exportto.exists())return;
        progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("exporting", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));
        userImportExportThread = new Thread(due = new doUserExport_Work(exusers, exportto, true));
        userImportExportThread.start();
        userImportExportTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(userImportExportThread.isAlive())return;
                                    stopProgressBar();
                                    userImportExportTimer.stop();
                                    userImportExportTimer = null;
                                    due = null;
                                    userImportExportThread = null;
                if(u.yesnomess(shark.programName, u.gettext("adminexportuser", "showexported"), thisadmin)){
                      try {
                        if(shark.macOS)
                          Runtime.getRuntime().exec(new String[]{"open",tofile});
                        else
                          Runtime.getRuntime().exec(new String[]{"explorer.exe",tofile});
                      }
                      catch(Exception ee){}

                }
                              }
                        });
                        userImportExportTimer.setRepeats(true);
                        userImportExportTimer.start();



                thisel.dispose();
             }
         } );

        cancelbut.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                thisel.dispose();
             }
         } );
         browsebut.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                JFileChooser fc;
                fc = new JFileChooser(tfield.getText());
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.setFileFilter(new FileFilter() {
                  public boolean accept(File f) {
                    if (!(f.isDirectory())) {
                      return false;
                    }
                    return true;
                  }

                  public String getDescription() {
                    return u.gettext("alldirectories", "label");
                  }
                });
                int returnVal = fc.showOpenDialog(thisel);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                   File selectedFile = fc.getSelectedFile();
                   String path2 = String.valueOf(selectedFile.getAbsolutePath());
                   tfield.setText(path2);
                }
             }
         } );
         grid.gridx = 0;
         grid.gridy = -1;
         grid.fill = GridBagConstraints.NONE;
         grid.anchor = GridBagConstraints.WEST;
         JPanel mainpn = new JPanel(new GridBagLayout());
         mainpn.add(u.infoLabel(u.gettext("adminexportuser", "exportinfo", shark.programName)), grid);
         grid.anchor = GridBagConstraints.CENTER;
         grid.fill = GridBagConstraints.BOTH;
         mainpn.add(renamepn, grid);
         grid.weighty = 0;
         JPanel mainpn2 = new JPanel(new GridBagLayout());
         mainpn2.add(butpn, grid);
         grid.insets = new Insets(10, 25, 0, 25);
         grid.weighty = 1;
         this.getContentPane().add(mainpn, grid);
         grid.insets = new Insets(0, 25, 10, 25);
         this.getContentPane().add(mainpn2, grid);
         this.setVisible(true);
      }
   } 
    
public class addStudents_Work implements Runnable{
    String ss[];
    student stu;
    Vector v;


    public addStudents_Work(String ss1[], student stu1, Vector v1){
     ss = ss1;
     stu = stu1;
     v = v1;
    }

  public void run(){
      

       setRightPanelBlank(true);
       usertree.getSelectionModel().removeTreeSelectionListener(tsl);
             
      
      boolean genericstu = stu==null;
      for(int n=0;n<ss.length;n++) {
         ss[n] = u.stripspaces2(ss[n]);
      }
      boolean adminadded = false;
      boolean useradded = false;
      TreePath tp = usertree.getSelectionPath();
      for(int n=0;n<ss.length;n++) {
        if(ss[n].length()==0) continue;
        
        if(n==ss.length-1)usertree.getSelectionModel().addTreeSelectionListener(tsl);
        
        if(genericstu)stu = new student();
        stu.name = ss[n];
        String dbpath = sharkStartFrame.sharedPath.toString();
        String dbname = dbpath + File.separatorChar + stu.name;
        student st1;
        if ((st1 = student.findStudent(stu.name)) != null) {
            String serrmess = getAlreadyText(st1);
            if(serrmess!=null)
                u.okmess(u.gettext("stulist_", "newstutitle"),u.convertToHtml(serrmess), thisadmin);
          continue;
        }
        db.create(dbname);
        simpleStu_base sks = null;
        if(v != null){
            for(int k = 0; k < v.size(); k++){
                if((v.get(k)) instanceof simpleStu_base){
                    if(stu.name.equalsIgnoreCase(((simpleStu_base)v.get(k)).name)){
                        sks = (simpleStu_base)v.get(k);
                        stu.password = sks.password;
                        stu.passwordhint = sks.passwordhint;
                        if(stu.password != null && stu.password.length()>0) {
                            if(sks.type>0)
                                stu.administrator = true;
                            if(sks.type>1)
                                stu.teacher = true;
                        }
                        break;
                    }
                }
            }
        }
        stu.saveStudent();
        if(sks ==null || sks.type==0){
//            jnode jn = (jnode)usertree.getSelectionPath().getLastPathComponent();
//            TreePath tp = usertree.getSelectionPath();
            jnode jn = tp==null?currentnode:(jnode)tp.getLastPathComponent();
            if(!jn.isNodeAncestor(currentnode))jn = currentnode;
            if(aregroups())
                usertree.setSelectionPath(new TreePath(addtotree(stu,jn).getPath()));
            else
                usertree.setSelectionPath(new TreePath(addtotree(stu,currentnode).getPath()));
            useradded = true;
        }
        else if(stu.administrator){
             addtotree(stu, admins_heading);
             useradded = true;
             student.checkadmin(stu);
             adminadded = true;
        }
      }
      
      if(adminadded)sharkStartFrame.mainFrame.gettopictreelist();
      student.checkadmin();     // rb 6/2/06
      
         setRightPanelBlank(false);
         if(useradded)
           reloadUserModel_delayed();
      
  }




    }





public class deleteStudents_Work implements Runnable{

jnode groups[];
boolean showProgress = false;
jnode nps[];
         String str_erase = u.gettext("stulist_qdel2","erase");
         String str_leave = u.gettext("stulist_qdel2","leave", shark.programName);
         String str_remove = u.gettext("remove","label");
         String str_cancel = u.gettext("cancel","label");
         String str_ok = u.gettext("ok","label");
         String str_showwarning = u.gettext("stulist_qdel3","showwarnings");

    public deleteStudents_Work(boolean showprog, jnode selnodes[]){
        showProgress = showprog;
        nps = selnodes;
    }

    int[] selectChildren(jnode jn, int[] currentselrows){
        usertree.expandAll(jn, new TreePath(jn.getPath()));
        int oriLevel = jn.getLevel();
        while((((jn = (jnode)jn.getNextNode()) !=null) && (jn.getLevel()) > oriLevel)){
           int i = usertree.getRowForPath(new TreePath(jn.getPath()));
           if(!u.inlist(currentselrows, i))
               currentselrows = u.addint(currentselrows, i);
           if(jn.type== jnode.GROUP)
               selectChildren(jn, currentselrows);
        }
        return currentselrows;
    }
    
    boolean isSubGroup(jnode grp){
        for(int i = 0; i < groups.length; i++){
            if(!groups[i].equals(grp) &&  groups[i].isNodeChild(grp))return true;
        }
        return false;
    }  
    
    int showDialog(int type, String message, String heading, String vals[], JCheckBox checkbox){
             Object[] params;
             message = u.convertToHtml(message);
             if(checkbox==null ) params = new Object[]{new String[]{message, " "}};
             else params = new Object[]{new String[]{message, " "}, checkbox};
             setProgressBarVisible(false);
             JOptionPane get = new JOptionPane(
                 params,
                 type, 0, null, vals, vals[0]);
             JDialog dialog = get.createDialog(thisadmin, heading);
             JButton jb;
             if(shark.macOS && (jb=get.getRootPane().getDefaultButton())!=null)
                jb.requestFocus();
             dialog.setVisible(true);
             dialog.dispose();
             if(showProgress)
                 setProgressBarVisible(true);
             return u.findString(vals, (String) get.getValue());
    }
    
    
    
     public void run(){
         boolean fulldelete_all = false;
         boolean delete_all = false;
         boolean showWarning = true;
         int tpsprerows[] = usertree.getSelectionRows();
         int currnoderow = -1;
         int index = -1;
         jnode groupRows[] = new jnode[]{};
         boolean currentnodeselected = false;
         boolean exceedingUserLimit = false;
         ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
         if(al !=null){
            int adminNo = (int)al.get(0);
            int stuNo = (int)al.get(1);
            if(adminNo > shark.maxUsers_Admins || stuNo > shark.maxUsers_Students){
                exceedingUserLimit = true;
            }        
         }  
         for(int i = 0; i < tpsprerows.length; i++){
             jnode jn = (jnode)(usertree.getPathForRow(tpsprerows[i])).getLastPathComponent();
             if(jn == currentnode){
                 currentnodeselected = true;
                 currnoderow = tpsprerows[index = i];
             }
         }   
         if(currnoderow>=0){
             tpsprerows = selectChildren(currentnode, tpsprerows);
             tpsprerows = u.removeint(tpsprerows, index);
             usertree.setSelectionRows(tpsprerows);
             usertree.repaint();
             tpsprerows = usertree.getSelectionRows();
         }
         for(int i = 0; i < tpsprerows.length; i++){
             jnode jn = (jnode)(usertree.getPathForRow(tpsprerows[i])).getLastPathComponent();
             if(jn.type == jnode.GROUP){
                 groupRows = u.addnode(groupRows, jn);
             }
         } 
         for(int i = 0; i < groupRows.length; i++){
             tpsprerows = selectChildren(groupRows[i], tpsprerows);
         }        
         if(groupRows.length > 0){
             usertree.setSelectionRows(tpsprerows);
             usertree.repaint();              
         }

         if(currnoderow>=0){ 
            String vals[];
            if(teacher.teacher) vals = new String[]{str_remove, str_cancel};
            else vals = new String[]{str_leave,str_erase,str_cancel};
            int a = showDialog(JOptionPane.QUESTION_MESSAGE, u.gettext("stulist_qdel3", "removeallinfo"),
                    u.gettext("stulist_qdel2","heading"),
                    vals, null);
            if(teacher.teacher){
                if(a==1 || a<0) return;  // CANCEL
            }
            else{
                if(a==2 || a<0) return;  // CANCEL
                if(a==1) fulldelete_all = true;
                delete_all = true;
            }
         }
         TreePath tps[] = usertree.getSelectionPaths();
         groups = new jnode[]{};
         if(currentnodeselected){
             groups = u.addnode(groups, currentnode);
         }
         for(int n = 0; n < tps.length; n ++){
             jnode currnode = (jnode)tps[n].getLastPathComponent();
             if(currnode.type == jnode.GROUP)
                groups = u.addnode(groups, currnode);
         }
         for(int n = groups.length - 1; n >= 0; n --){
             if(isSubGroup(groups[n]))groups = u.removeNode(groups, n);
         }

         String str_subgroups = u.gettext("stulist_qdel3","subgroupsinfo");

         for(int n = groups.length - 1; n >= 0; n --){
             boolean fulldelete = false;
             jnode groupnode = groups[n];
             if(!delete_all){
                 String message;
                 String vals[];
                 if(teacher.teacher) vals = new String[]{str_remove, str_cancel};
                 else vals = new String[]{str_leave, str_erase, str_cancel};
                 if(groupnode.getDepth() > 1) {
                     message = str_subgroups;
                 }
                 else if(groupnode.getChildCount()==0){
                     message =  u.gettext("stulist_qdel3","emptygroup", groupnode.get());
                     vals = new String[]{str_remove, str_cancel};
                 }
                 else message = u.gettext("stulist_removeclass","h2");
                 int a = showDialog(JOptionPane.QUESTION_MESSAGE, message, u.gettext("stulist_qdel2","heading"), vals, null);
                 if(vals.length==2){
                     if(a==1 || a<0) return;  // CANCEL                   
                 }
                 else{
                     if(a==2 || a<0) return;  // CANCEL
                     if(a==1) fulldelete = true;                 
                 }              
             }
             jnode gs[] =  getAllChildren(groupnode, false);
             // remove the studnets and delete is required
             boolean fulldel = fulldelete || fulldelete_all;
             for(int i = 0; gs!=null && i < gs.length; i++){
                 student stu3 = null;
                 String otherteachers[];
                 boolean delProb = false;
                 if(fulldel){
                     if((stu3 = student.findStudent(gs[i].get())) == null){
                         if(showWarning){
                            JCheckBox checkbox = new JCheckBox(str_showwarning);
                            checkbox.setSelected(true);
                            showDialog(JOptionPane.WARNING_MESSAGE, 
                                    u.gettext("stulist_notload","text", stu3.name), 
                                    u.gettext("stulist_notload","heading"), 
                                    new String[]{str_ok}, checkbox);
                            showWarning = checkbox.isSelected();
                         }
                     }
                     else if((otherteachers = getOtherTeachers(stu3.name))!=null){
                         if(showWarning){
                             String mess = u.gettext("stulist_hasteachers_delete","text");
                             mess = mess.replaceAll("%", stu3.name);
                             String mess2 = "";
                             for(int k = 0; k < otherteachers.length; k++){
                                 mess2 += otherteachers[k];
                                 if(k < otherteachers.length-1)
                                    mess2 += ", ";
                             }
                             mess = mess.replaceFirst("@", mess2);
           //                  mess = u.convertToHtml(mess);
                             JCheckBox checkbox = new JCheckBox(str_showwarning);
                             checkbox.setSelected(true);
                             showDialog(JOptionPane.WARNING_MESSAGE, mess, u.gettext("stulist_hasteachers_delete","heading"), new String[]{str_ok}, checkbox);
                             showWarning = checkbox.isSelected();
                         }
                     }
                     else{
                         delProb = deleteuser(stu3)<0;
                     }
                     if(delProb){
                         if(showWarning){
                            JCheckBox checkbox = new JCheckBox(str_showwarning);
                            checkbox.setSelected(true);
                            showDialog(JOptionPane.WARNING_MESSAGE, u.gettext("stulist_stuloggedon_del","text",stu3.name), u.gettext("stulist_stuloggedon_del","heading"), new String[]{str_ok}, checkbox);
                            showWarning = checkbox.isSelected();
                         }
                     }
                     if(showProgress)
                         setProgressBarVisible(true);
                 }
                 if(!delProb)
                     usermodel.removeNodeFromParent(gs[i]);
             }
             // check whether any empty groups also need removing
             gs = getAllChildren(groupnode, true);
             if(gs!=null){
                 for(int i = gs.length-1; i >= 0; i--){
                     if(gs[i].type == jnode.GROUP && gs[i].getChildCount()==0)
                         usermodel.removeNodeFromParent(gs[i]);
                 }
             }
             // if group node itself is now empty, remove it
             if(groupnode.type == jnode.GROUP && groupnode.getChildCount()==0)
                 usermodel.removeNodeFromParent(groupnode);
         }


         jnode freeStudents[] = new jnode[]{};
         jnode teachers[] = new jnode[]{};
         for(int i = 0; nps!=null && i < nps.length; i++){
             if(nps[i].type == jnode.STUDENT){
                 freeStudents = u.addnode(freeStudents, nps[i]);
             }
             else if(nps[i].type == jnode.TEACHER || nps[i].type == jnode.SUBADMIN)     
                 teachers = u.addnode(teachers, nps[i]);
         }

         String str_removestus = u.gettext("stulist_qdel3","confirmdel");

         boolean doforrest = false;
         boolean fulldelete = false;
         for(int i = 0; i < freeStudents.length; i++){
             boolean showforrest = i<freeStudents.length-1;
             if(!doforrest && !delete_all){
                 String message = str_removestus;
                 String vals[];
                 if(teacher.teacher) vals = new String[]{str_remove, str_cancel};
                 else vals = new String[]{str_leave, str_erase, str_cancel};
                 JCheckBox checkbox = null;
                 if(showforrest)
                    checkbox = new JCheckBox(u.gettext("stulist_qdel2","forrest", String.valueOf(freeStudents.length - i)));
                 int a = showDialog(JOptionPane.QUESTION_MESSAGE, message, u.gettext("stulist_qdel2","heading"), vals, checkbox);
                 if(teacher.teacher){
                     if(a==1 || a<0) return;  // CANCEL                   
                 }
                 else{
                     if(a==2 || a<0) return;  // CANCEL
                     if(a==1) fulldelete = true;                 
                 }                        
                 if(showforrest)
                     doforrest = checkbox.isSelected(); 
             }
             student stu3 = null;
             boolean fulldel = fulldelete || fulldelete_all;
             String otherteachers[];
             boolean delProb = false;
             if(fulldel){
                if((stu3 = student.findStudent(freeStudents[i].get())) == null){
                    if(showWarning){
                        JCheckBox checkbox = new JCheckBox(str_showwarning);
                        checkbox.setSelected(true);
                        showDialog(JOptionPane.WARNING_MESSAGE, u.gettext("stulist_notload","text", stu3.name), u.gettext("stulist_notload","heading"), new String[]{str_ok}, checkbox);
                        showWarning = checkbox.isSelected();
                    }
                }
//                else if((otherteachers = getOtherTeachers(stu3.name))!=null){
                else if((otherteachers = getOtherTeachers(stu3.name))!=null && !exceedingUserLimit){
                    if(showWarning){
                        String mess = u.gettext("stulist_hasteachers_delete","text");
                        mess = mess.replaceAll("%", stu3.name);
                        String mess2 = "";
                        for(int k = 0; k < otherteachers.length; k++){
                            mess2 += otherteachers[k];
                            if(k < otherteachers.length-1)
                                mess2 += ", ";
                        }
                        mess = mess.replaceFirst("@", mess2);
     //                   mess = u.convertToHtml(mess);
                        JCheckBox checkbox = new JCheckBox(str_showwarning);
                        checkbox.setSelected(true);
                        showDialog(JOptionPane.WARNING_MESSAGE, mess, u.gettext("stulist_hasteachers_delete","heading"), new String[]{str_ok}, checkbox);
                        showWarning = checkbox.isSelected();
                    }
                }
                else{
                    delProb = deleteuser(stu3)<0;
                }
                if(delProb){
                    if(showWarning){
                        JCheckBox checkbox = new JCheckBox(str_showwarning);
                        checkbox.setSelected(true);
                        showDialog(JOptionPane.WARNING_MESSAGE, u.gettext("stulist_stuloggedon_del","text",stu3.name), u.gettext("stulist_stuloggedon_del","heading"), new String[]{str_ok}, checkbox);
                        showWarning = checkbox.isSelected();
                    }
                }
                if(showProgress)
                    setProgressBarVisible(true);
            }
            if(!delProb)
                usermodel.removeNodeFromParent(freeStudents[i]);
         }
         doforrest = false;
         fulldelete = false;
         for(int i = 0; i < teachers.length; i++){
             boolean showforrest = i<teachers.length-1;
             if(!doforrest && !delete_all){
                 String message;
                 if(shark.wantTeachers)
                     message = u.gettext("stulist_qdel3", "confirmdeladmin_net");
                 else
                     message = u.gettext("stulist_qdel3", "confirmdeladmin");
                 String vals[] = new String[]{str_remove, str_cancel};
                 JCheckBox checkbox = null;
                 if(showforrest)
                     checkbox = new JCheckBox(u.gettext("stulist_qdel2","forrest", String.valueOf(teachers.length - i)));
                 int a = showDialog(JOptionPane.QUESTION_MESSAGE, message, u.gettext("stulist_qdel2","heading"), vals, checkbox);
                 if(a==1 || a<0) return;  // CANCEL
                 fulldelete = true;
                 if(showforrest)
                     doforrest = checkbox.isSelected();
             }
             student stu3 = null;
             boolean fulldel = fulldelete || fulldelete_all;
             boolean delProb = false;
             if(fulldel){
                if((stu3 = student.findStudent(teachers[i].get())) == null){
                    if(showWarning){
                        JCheckBox checkbox = new JCheckBox(str_showwarning);
                        checkbox.setSelected(true);
                        showDialog(JOptionPane.WARNING_MESSAGE, u.gettext("stulist_notload","text", stu3.name), u.gettext("stulist_notload","heading"), new String[]{str_ok}, checkbox);
                        showWarning = checkbox.isSelected();
                    }
                }
                else{
                    delProb = deleteuser(stu3)<0;
                }
                if(delProb){
                    if(showWarning){
                        JCheckBox checkbox = new JCheckBox(str_showwarning);
                        checkbox.setSelected(true);
                        showDialog(JOptionPane.WARNING_MESSAGE, u.gettext("stulist_stuloggedon_del","text",stu3.name), u.gettext("stulist_stuloggedon_del","heading"), new String[]{str_ok}, checkbox);
                        showWarning = checkbox.isSelected();
                    }
                }
                if(showProgress)
                    setProgressBarVisible(true);
             }
             if(!delProb)
                usermodel.removeNodeFromParent(teachers[i]);
         }         
         reloadUserModel_delayed();
         keepRightPanelBlank = false;
         student.checkadmin();   // rb 6/2/06
         biglist = null;     // rb 8/2/06
         setstudentlc();                                        // moved end 9/2/06
  }
    }

    jnode[] getAllChildren(jnode jn, boolean includeGroups){
        jnode allChildren[] = new jnode[]{};
        jnode children[] = jn.getChildren();
        for(int i = 0; i < children.length; i++){
            if(includeGroups || children[i].type != jnode.GROUP)
                allChildren = u.addnode(allChildren, children[i]);
        }

        for(int i = 0; i < children.length; i++){
            if(children[i].type == jnode.GROUP){
                jnode js[] = getAllChildren(children[i], includeGroups);
                if(js!=null)
                    allChildren = u.addnode(allChildren, js);
            }
        }
        return allChildren.length==0?null:allChildren;
    }

public class doRename_Work implements Runnable{

    student stu;
    jnode jn;
    public doRename_Work(student stu1, jnode jn1){
        stu = stu1;
        jn = jn1;
    }

  public void run(){
               try{
                   // find this admin's students
                   ArrayList tochange = new ArrayList();
                   String newname = profile_name.getText().trim();

                   student lockedstus[] = new student[]{};
                   String cantdostus[] = new String[]{};
                   if(stu!=null){
                       String thisadministrator = stu.name;

                   loop1:for(int i = 0; stu.students!=null &&  i < stu.students.length; i++){
                       student stu2 =student.findStudent(stu.students[i]);
                       if(stu2!=null) {
                           String stutochange;
                           stutochange = stu2.name;
                           String sprogs[] = db.list(stu2.name, db.PROGRAM);
                           String progstochange[] = new String[]{};
                           String progstochangeto[] = new String[]{};
                           ArrayList changetocollection = new ArrayList();
                           loop2:for(int n = 0; n < sprogs.length; n++){
                               program.saveprogram sp = (program.saveprogram)db.find(stu2.name, sprogs[n], db.PROGRAM);
                               if(sp==null)continue loop2;
                               if(sp.teacher!=null && sp.teacher.equals(thisadministrator)){
                                   String ending = "["+thisadministrator+"]";
                                   if(sprogs[n].endsWith(ending)){
                                       progstochange = u.addString(progstochange, sprogs[n]);
                                       String stemp = sprogs[n];
                                       stemp = stemp.substring(0,stemp.length()-ending.length())+"["+ newname+ "]";
                                       progstochangeto = u.addString(progstochangeto, stemp);
                                   }
                                   else if(sprogs[n].endsWith(thisadministrator)){
                                       progstochange = u.addString(progstochange, sprogs[n]);
                                       String stemp = sprogs[n];
                                       stemp = stemp.substring(0,stemp.length()-(thisadministrator.length()))+
                                               newname;
                                       progstochangeto = u.addString(progstochangeto, stemp);
                                   }
                               }
                           }
                           if(progstochange.length>0){
                               if(!stu2.getlock()) {
                                   cantdostus = u.addString(cantdostus, stutochange);
                                   continue loop1;
                               }
                               else {
                                   lockedstus = u.addStudent(lockedstus, stu2);
                               }
                               changetocollection.add(stutochange);
                               changetocollection.add(progstochange);
                               changetocollection.add(progstochangeto);
                               tochange.add(changetocollection);
                           }
                       }
                   }
                   if(cantdostus.length==0){
                       for(int i = 0; i < tochange.size(); i++){
                           ArrayList collection = (ArrayList)tochange.get(i);
                           String stuname = (String)collection.get(0);
                           String prog_pre[] = (String[])collection.get(1);
                           String prog_post[] = (String[])collection.get(2);
                           for(int k = 0; k < prog_pre.length; k++){
                               program.saveprogram sp = (program.saveprogram)db.find(stuname, prog_pre[k], db.PROGRAM);
                               if(sp.teacher.equals(thisadministrator))sp.teacher = newname;
                               db.update(stuname, prog_post[k], sp, db.PROGRAM);
                               db.delete(stuname, prog_pre[k], db.PROGRAM);
                           }
                       }
                   }
                   for(int i = 0; i < lockedstus.length; i++){
                       lockedstus[i].releaselock();
                   }
                   if(cantdostus.length>0){
                       stopProgressBar();
                       String ss = u.convertToHtml(
                               u.edit(u.gettext("renameadmin", "mess"),
                               thisadministrator, u.combineString(cantdostus)));
                       u.okmess(shark.programName, ss, thisadmin);
                       if(stu != null) stu.releaselock();
                       return;
                       }

                   }
                   if(jn.type == jnode.STUDENT || jn.type == jnode.GROUP){
                        renamestudent(jn, profile_name.getText().trim());
                   }
                   else if(jn.type == jnode.TEACHER){
                        renameadmin(jn, profile_name.getText().trim());
                   }
                   else if(jn.type == jnode.SUBADMIN){
                       renamesubadmin(jn, profile_name.getText().trim());
                   }
               }
               catch(Exception e){}
               if(stu != null) stu.releaselock();
    }
    }


    
public class doUserImport_Work implements Runnable{

File toimport[];
jnode parent;

    public doUserImport_Work(File toimport1[]){
        toimport = toimport1;
    }

  public void run(){
      setRightPanelBlank(true);
      usertree.getSelectionModel().removeTreeSelectionListener(tsl);
               for(int j = 0; j < toimport.length; j++){
                   if(j==toimport.length-1)usertree.getSelectionModel().addTreeSelectionListener(tsl);
                   String filename = toimport[j].getName();
                   filename = u.stripspaces2(filename);
                   int k;
                   if((k=filename.lastIndexOf("."))>=0){
                       filename = filename.substring(0, k);
                   }
                   String scurrnam = "";
                   for(int h = 0; h < filename.length(); h++){
                     if((u.notAllowedInFileNames.indexOf(filename.charAt(h)) < 0)){
                         scurrnam+=filename.charAt(h);
                     }
                   }
                   filename = scurrnam;
                   if(u.findString(db.dblistnames(sharkStartFrame.sharedPath), filename)>=0){
                       Object[] options = {
                          u.gettext("adminimportuser", "overwrite"),
                          u.gettext("adminimportuser", "keep"),
                          u.gettext("cancel", "label")};
                          stopProgressBar();
                          int n = JOptionPane.showOptionDialog(thisadmin,
                             u.edit(u.gettext("adminimportuser", "alreadyexists"), filename, shark.programName),
                             shark.programName,
                             JOptionPane.YES_NO_CANCEL_OPTION,
                             JOptionPane.WARNING_MESSAGE,
                             null,
                             options,
                             options[0]);
                          progbar = new progress_base(thisadmin, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisadmin.getWidth()/4,
                                                         thisadmin.getHeight()*2/5,
                                                         (thisadmin.getWidth()/2),
                                                         (thisadmin.getHeight()/5)));
                          if(n==JOptionPane.NO_OPTION){
                              continue;
                          }
                          else if(n==JOptionPane.CANCEL_OPTION){
                             stopProgressBar();
                             setRightPanelBlank(false);
                             if(j!=toimport.length-1)usertree.getSelectionModel().addTreeSelectionListener(tsl);
                              return;
                          }
                   }
                    File destF = new File(sharkStartFrame.sharedPathplus + filename + ".sha");
                    u.copyfile(toimport[j], destF);
                    student stu = student.findStudent(filename);
                    if(stu !=null){
                        ArrayList al = u.restrictedUserCount(sharkStartFrame.sharedPath);
                         if (al!=null){
                             if(stu.administrator && (int)(al.get(0))>=shark.maxUsers_Admins){
                                 if(destF.exists())destF.delete();
                                 u.okmess(shark.programName, u.gettext("maxusers", "fulladmin_create", String.valueOf(shark.maxUsers_Admins)), thisadmin);
                                 return;                            
                             }
                             else if(!stu.administrator && (int)(al.get(1))>=shark.maxUsers_Students){
                                 if(destF.exists())destF.delete();
                                 u.okmess(shark.programName, u.gettext("maxusers", "fullstu_create", String.valueOf(shark.maxUsers_Students)), thisadmin);
                                 return;                            
                             }
                        }  
                        if(stu.administrator){
                           usertree.setSelectionPath(new TreePath(addtotree(stu, admins_heading).getPath()));
                           student.checkadmin(stu);
                        }
                        else{
                          if(parent==null)parent = (jnode)usertree.getSelectionPath().getLastPathComponent();
                          if(!parent.isNodeAncestor(currentnode))
                              parent = currentnode;
                          usertree.setSelectionPath(new TreePath(addtotree(stu,parent).getPath()));
                        }
                        String resdb2 = sharkStartFrame.resourcesPlus+filename+sharkStartFrame.resourcesFileSuffix;
                        String items2[] = db.list(filename, db.PICTURE);
                        for(int i = 0; i < items2.length; i++){
                            Object o = db.find(filename, items2[i], db.PICTURE);
                            db.update(resdb2, items2[i], o, db.PICTURE);
                        }
                        // will this delete icon?
                        db.deleteAll(filename, db.PICTURE);
                        items2 = db.list(filename, db.PICTUREPLIST);
                        for(int i = 0; i < items2.length; i++){
                            Object o = db.find(filename, items2[i], db.PICTUREPLIST);
                            db.update(resdb2, items2[i], o, db.PICTUREPLIST);
                        }
                        db.deleteAll(filename, db.PICTUREPLIST);
                        items2 = db.list(filename, db.TOPICPLIST);
                        for(int i = 0; i < items2.length; i++){
                            Object o = db.find(filename, items2[i], db.TOPICPLIST);
                            db.update(resdb2, items2[i], o, db.TOPIC);
                        }
                        db.deleteAll(filename, db.TOPICPLIST);
                        items2 = db.list(filename, db.WAV);
                        for(int i = 0; i < items2.length; i++){
                            byte bs[] = db.findwav(filename, items2[i]);
                            db.updatewav(resdb2, items2[i], bs);
                        }
                        db.deleteAll(filename, db.WAV);
                    }
                    else if(destF.exists()){
                        destF.delete();
                    }
               }
               reloadUserModel_delayed();

      setRightPanelBlank(false);
  }
    }
 



public class doUserExport_Work implements Runnable{

String exusers[];
File tofile;
boolean doUi;

    public doUserExport_Work(String exportnames[], File tofile1, boolean UI){
        exusers = exportnames;
        tofile = tofile1;
        doUi = UI;
    }

  public void run(){
              if(doUi){
                  setRightPanelBlank(true, true);
                  usertree.getSelectionModel().removeTreeSelectionListener(tsl);
              }
               for(int k = 0; k < exusers.length; k++){
                   if(doUi && k==exusers.length-1)usertree.getSelectionModel().addTreeSelectionListener(tsl);
                   String n = exusers[k];
                   String scurrnam = "";
                   for(int j = 0; j < n.length(); j++){
                     if((u.notAllowedInFileNames.indexOf(n.charAt(j)) < 0)){
                         scurrnam+=n.charAt(j);
                     }
                   }
                   n = scurrnam;
                   String tempName = "~"+n+String.valueOf(System.currentTimeMillis());
                   String tempdb = sharkStartFrame.sharedPathplus+tempName;
                   String resdb = sharkStartFrame.resourcesPlus+n+sharkStartFrame.resourcesFileSuffix;
                   u.copyfile(new File(sharkStartFrame.sharedPathplus+n+".sha"),
                           new File(tempdb+".sha"));
                   String items[] = db.list(resdb, db.PICTURE);
                   for(int i = 0; i < items.length; i++){
                       Object o = db.find(resdb, items[i], db.PICTURE);
                       db.update(tempName, items[i], o, db.PICTURE);
                   }
                   items = db.list(resdb, db.PICTUREPLIST);
                   for(int i = 0; i < items.length; i++){
                       Object o = db.find(resdb, items[i], db.PICTUREPLIST);
                       db.update(tempName, items[i], o, db.PICTUREPLIST);
                   }
                   items = db.list(resdb, db.TOPIC);
                   for(int i = 0; i < items.length; i++){
                       Object o = db.find(resdb, items[i], db.TOPIC);
                       db.update(tempName, items[i], o, db.TOPICPLIST);
                   }
                   items = db.list(resdb, db.WAV);
                   for(int i = 0; i < items.length; i++){
                       byte bs[] = db.findwav(resdb, items[i]);
                       db.updatewav(tempName, items[i], bs);
                   }
                   File todelete =  new File(tempdb+".sha");
                   u.copyfile(todelete,new File(tofile+shark.sep+n+"."+userExportExtension));
                   todelete.delete();
               }
            if(doUi){
                keepRightPanelBlank = false;
                setRightPanelBlank(false);
                if(exusers.length==1)changenodeselection((jnode)usertree.getSelectionPath().getLastPathComponent());
            }
        }
    }


}