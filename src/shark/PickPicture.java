package shark;

import java.io.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
//import javax.media.jai.*;
//import java.lang.ref.SoftReference.*;
//import com.sun.image.codec.jpeg.*;
import java.awt.image.*;
//import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.event.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class PickPicture extends JFrame {
  String chosenWord;
  GetPicture gp;
  mainPan dropBox;
  int border = 20;
  int panWidth = (sharkStartFrame.screenSize.width - (border * 2)) * 1 / 4;
//  int thisWidth = panWidth * 3;
  int thisWidth = panWidth * 2;
  int thisHeight = sharkStartFrame.screenSize.height * 2 / 3;
  Dimension panDim = new Dimension(panWidth, thisHeight);
  Dimension panDim2 = new Dimension(panWidth * 2, thisHeight);
  JButton btBrowse;
  JButton btBrowse2;
  JButton btChoose;
  JPanel pan1 = new JPanel();
  JPanel pan2 = new JPanel();
  JPanel pan3 = new JPanel();
  JPanel pan4 = new JPanel();
  JPanel pan5 = new JPanel();
  String nopicture = u.gettext("pickpicture", "nopicture");
  byte state, state2;
  topicTree topics;
  JScrollPane scrolltopics;
  JPanel pan1Blank = new JPanel();
  wordlist topicWords = new wordlist();
  JList lstWords;
  JList viewWords;
  JScrollPane wordTreeScroll, lstWordsScroll, viewWordsScroll;
  JPanel pnwlsearch;
  DropTarget dt;
  Vector importedpics;
  String adminpics[];
  public static String universalImageFile = "Universal"+shark.sep+"images";


  String dev_avatarfile = null;
  String dev_widgitfile = null;
//  String dev_avatarfile = "publicavatars";
//  String dev_widgitfile = "publicwidgit";



  class picitem {
    public byte[] bytes;
    public String name;
    picitem(String n, byte[] pic) {
      bytes = pic;
      name = n;
    }
  }

  ItemListener bglisten = new java.awt.event.ItemListener() {
    public void itemStateChanged(ItemEvent e) {
      byte oldstate = state;
      if (rb1.isSelected()) {
        state = RBWORDLIST;
      }
      else {
        if (rb2.isSelected()) {
          state = RBVIEW;
        }
        else {
          if (rb3.isSelected()) {
            state = RBUSERSLIST;
          }
          else {
            state = 0;
          }
        }
      }
      if (state != oldstate) {
        if (state == RBWORDLIST) {
          changestate(state);
        }
        else {
          if (state == RBVIEW) {
            changestate(state);
          }
          else {
            if (state == RBUSERSLIST) {
              changestate(state);
            }
          }
        }
      }
    }
  };

  JRadioButton rb1 = u.RadioButton("pickpicture_rb1", bglisten);
  JRadioButton rb2 = u.RadioButton("pickpicture_rb2", bglisten);
  JRadioButton rb3 = u.RadioButton("pickpicture_rb3", bglisten);
  final byte RBWORDLIST = 1;
  final byte RBVIEW = 2;
  final byte RBUSERSLIST = 3;
  final byte RBTOPICLIST = 4;
  final byte RBYOU = 1;
  final byte RBOTHERS = 2;
  JCheckBox orderck, multick, multpreview;
  JPanel multpan;
  JPanel pnDropBox;
  JPanel pnDropBoxSide;
  JPanel jp2;
  public static RenderedImage source;
  JFrame thisjd;
  JTextField tfWord;
  boolean drawwaiting = false;
  JPanel pnCheck;
  int oldsel_topicwords = -1;
  int oldsel_wordlist = -1;
  int oldsel_viewwordlist = -1;
  int oldsel_multwordlist = -1;
  topic oldsel_topics = new topic(new jnode());
  String oldsel_usersview = "";
  String oldsel_userslogo = "";
  String so2[];
  static final String ownpic = "PICFORPERSON";
  static final String ownpicstuset = "PICFORPERSONSTU";
  JCheckBox searchck;
  String currentstudents[];
  JButton btDelete;
  JButton btDelete2;
  boolean ctrldown = false;
  JTextArea epPan2 = new JTextArea();
  JTextArea epPan3 = new JTextArea();
  JTextArea epPan4 = new JTextArea();
  JTextArea epPan5 = new JTextArea();
  JTextArea apAdminPics = new JTextArea();
  String p2selword, p2sellist, p3setpicword, p3setmutlpicword, p3setpicuser;
  String p3setpicself, p3setpicuserself, p4assign, p5seluser, p2seluserword, p2seluserwordadmin;
  int insSmall = 5;
  int insComponent = 12;
  JPanel userspan = null;
  JButton btMultWordsSelectAll = new JButton(u.gettext("pickpicture", "selectall"));

  boolean universal;
  String studbs[];
  String stus[];
  String username;
  boolean wanticon;
  String currownpic;
//  static boolean active = false;


  public PickPicture(boolean global, String students[], String name, boolean wantseticon) {
    thisjd = this;
    universal = global;
    wanticon = wantseticon;
    username = name;
// active = true;

     Frame f[] = Frame.getFrames();
     for(int i = 0; i < f.length; i++){
         if(f[i] instanceof PickPicture && f[i].isVisible()){
             u.okmess(shark.programName, u.gettext("windowopen", "mess"),thisjd);
             end();
             return;
         }
     }

    if(universal){
        studbs = stus = new String[]{universalImageFile};
    }
     else{
        studbs = new String[students.length];
        stus = new String[students.length];
        for(int i = 0; i < students.length; i++){
            studbs[i] = sharkStartFrame.resourcesPlus+students[i]+sharkStartFrame.resourcesFileSuffix;
            stus[i] = students[i];
        }
     }
    currownpic = (!sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator &&
            stus[0].equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name))?ownpicstuset:ownpic;

    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        end();
      }
    });
    setIconImage(sharkStartFrame.sharkicon);
    try {
      jbInit();
    } catch (Exception e) {
      e.printStackTrace();
    }
    
//    this.setBounds(border, border, thisWidth, thisHeight);
    this.setBounds(u2_base.adjustBounds(new Rectangle(border, border, thisWidth, thisHeight)));
    this.setResizable(false);
    this.pack();
    rb3.setSelected(true);
    if (tfWord.isShowing()) {
      tfWord.requestFocus();
    }
    this.setVisible(true);
  }

  private void jbInit() throws Exception {
    GridBagConstraints grid = new GridBagConstraints();
    setTitle(u.gettext("pickpicture", "title", universal? u.gettext("pickpicture", "title2", shark.programName)  :username ));
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    pan1.setLayout(new GridBagLayout());
    pan2.setLayout(new GridBagLayout());
    pan3.setLayout(new GridBagLayout());
    pan4.setLayout(new GridBagLayout());
    pan5.setLayout(new GridBagLayout());
    pan1.setMinimumSize(panDim);
    pan1.setMaximumSize(panDim);
    pan1.setPreferredSize(panDim);
    pan1.setBorder(BorderFactory.createEtchedBorder());
    pan2.setMinimumSize(panDim);
    pan2.setMaximumSize(panDim);
    pan2.setPreferredSize(panDim);
    pan2.setBorder(BorderFactory.createEtchedBorder());
    pan3.setMinimumSize(panDim);
    pan3.setMaximumSize(panDim);
    pan3.setPreferredSize(panDim);
    pan3.setBorder(BorderFactory.createEtchedBorder());
    pan4.setMinimumSize(panDim);
    pan4.setMaximumSize(panDim);
    pan4.setPreferredSize(panDim);
    pan4.setBorder(BorderFactory.createEtchedBorder());
    pan5.setPreferredSize(panDim);
    pan5.setBorder(BorderFactory.createEtchedBorder());
    getContentPane().setLayout(new GridBagLayout());
    Dimension dim = new Dimension(panWidth * 8 / 9, panWidth * 8 / 9);
    dropBox = new mainPan(dim);
    dropBox.setPreferredSize(dim);
    dropBox.setMaximumSize(dim);
    dropBox.setMinimumSize(dim);
    epPan2.setEditable(false);
    epPan3.setEditable(false);
    epPan4.setEditable(false);
    epPan5.setEditable(false);

    apAdminPics.setEditable(false);
    epPan2.setWrapStyleWord(true);
    epPan3.setWrapStyleWord(true);
    epPan4.setWrapStyleWord(true);
    epPan5.setWrapStyleWord(true);
    apAdminPics.setWrapStyleWord(true);
    epPan2.setLineWrap(true);
    epPan3.setLineWrap(true);
    epPan4.setLineWrap(true);
    epPan5.setLineWrap(true);
    apAdminPics.setLineWrap(true);
    epPan2.setBorder(BorderFactory.createEtchedBorder());
    epPan3.setBorder(BorderFactory.createEtchedBorder());
    epPan4.setBorder(BorderFactory.createEtchedBorder());
    epPan5.setBorder(BorderFactory.createEtchedBorder());
    epPan2.setOpaque(false);
    epPan3.setOpaque(false);
    epPan4.setOpaque(false);
    epPan5.setOpaque(false);
    apAdminPics.setOpaque(false);
    p2selword = u.gettext("pickpicturehelp", "p2selword");
    p2sellist = u.gettext("pickpicturehelp", "p2sellist");
    p3setpicword = u.gettext("pickpicturehelp", "p3setpicword");
    p3setmutlpicword = u.gettext("pickpicturehelp", "p3setmutlpicword");
    p3setpicuser = u.gettext("pickpicturehelp", "p3setpicuser");
    p3setpicself = u.gettext("pickpicturehelp", "p3setpicself");
    p3setpicuserself = u.gettext("pickpicturehelp", "p3setpicuserself");
    p4assign = u.gettext("pickpicturehelp", "p4assign");
    p5seluser = u.gettext("pickpicturehelp", "p5seluser");
    p2seluserword = u.gettext("pickpicturehelp", "p2seluserword");
    p2seluserwordadmin = u.gettext("pickpicturehelp", "p2seluserwordadmin");
    Font f = new Font(sharkStartFrame.treefont.getName(), Font.PLAIN, epPan2.getFont().getSize() - 2);
    epPan3.setText(p3setpicword);
    epPan4.setText(p4assign);
    epPan5.setText(p5seluser);
    apAdminPics.setText(u.gettext("pickpicture", "adminpicset"));
    epPan2.setFont(f);
    epPan3.setFont(f);
    epPan4.setFont(f);
    epPan5.setFont(f);
    apAdminPics.setFont(f);
    apAdminPics.setForeground(Color.red);
    apAdminPics.setVisible(false);

    dropBox.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
        dropBox.wp.reposition();
      }
    });
    pan3.addComponentListener(new ComponentAdapter() {
      public void componentMoved(ComponentEvent e) {
        dropBox.wp.reposition();
      }
    });

    grid.weighty = 0;
    grid.weightx = 1;
    grid.gridx = 0;
    grid.gridy = -1;
    grid.fill = GridBagConstraints.BOTH;
    pan5.add(epPan5, grid);
    grid.weighty = 1;

    // pan3 setup
    JPanel exitpan = new JPanel();
    JButton btExit = u.sharkButton(u.gettext("ok", "label"));
    btExit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        end();
      }
    });

    exitpan.setBorder(BorderFactory.createEtchedBorder());
    exitpan.add(btExit);

//    if (shark.macOS) {
//      btExit.setForeground(Color.red);
//    }
//    else {
//      btExit.setForeground(Color.white);
//      btExit.setBackground(Color.red);
//    }

    btBrowse = u.sharkButton("browse");
    btBrowse.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doBrowseAction();
      }
    });

    btBrowse2 = u.sharkButton("browse");
    btBrowse2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doBrowseAction();
      }
    });


    btChoose = u.sharkButton("choose");
    btChoose.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
            new chooseAvatar_base(thisjd);
      }
    });

    btMultWordsSelectAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int begining = 0;
        oldsel_multwordlist = -1;
        int end = dropBox.convertedWords.getModel().getSize() - 1;
        if (end >= 0) {
          dropBox.convertedWords.setSelectionInterval(begining, end);
        }
      }
    });
    btMultWordsSelectAll.setEnabled(false);

    btDelete = u.sharkButton("delete");
    btDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doDeleteAction(btDelete);
      }
    });

    btDelete2 = u.sharkButton("delete");
    btDelete2.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        doDeleteAction(btDelete2);
      }
    });
    btDelete2.setEnabled(false);

    multpan = new JPanel(new GridBagLayout());
    multick = u.CheckBox("pickpicture_multi", new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (multick.isSelected()) {
          setPan2List(null);
          epPan3.setText(p3setmutlpicword);
          btBrowse.setEnabled(true);
          dropBox.wp.addPic(" ");
          btMultWordsSelectAll.setVisible(true);
          btMultWordsSelectAll.setEnabled(false);
          pnwlsearch.setVisible(false);
          if (state == RBTOPICLIST) {
            setPan1Comp(pan1Blank);
            pnCheck.setVisible(false);
          }
          else {
            if (state == RBWORDLIST) {
              tfWord.setVisible(false);
            }
          }
          dropBox.convertedWords.setListData(new Vector());
        }
        else {
          importedpics = null;
          changestate(state);
        }
        dropBox.wp.reposition();
      }
    }
    );
    multick.setSelected(false);
    multpan.add(multick);

    grid.weighty = 0;
    grid.weightx = 1;
    grid.gridx = -1;
    grid.gridy = 0;
    grid.fill = GridBagConstraints.NONE;
    jp2 = new JPanel(new GridBagLayout());
    grid.insets = new Insets(0, 0, 0, 0);
    grid.anchor = GridBagConstraints.WEST;
    jp2.add(btBrowse, grid);
    grid.anchor = GridBagConstraints.EAST;
    jp2.add(btDelete, grid);
    grid.anchor = GridBagConstraints.CENTER;
    grid.anchor = GridBagConstraints.WEST;
    grid.gridy = 1;
    grid.insets = new Insets(insComponent, 0, 0, 0);
    jp2.add(btMultWordsSelectAll, grid);
    grid.fill = GridBagConstraints.NONE;

    grid.gridx = 0;
    grid.gridy = -1;
    JPanel jp1 = new JPanel(new GridBagLayout());
    grid.fill = GridBagConstraints.NONE;

    multpreview = u.CheckBox("pickpicture_multpreview", new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        oldsel_multwordlist = -1;
        if (multpreview.isSelected()) {
          dropBox.activate(dropBox.wp);
          Object o[] = new Object[dropBox.convertedWords.getModel().getSize()];
          for (int i = 0; i < o.length; i++) {
            o[i] = dropBox.convertedWords.getModel().getElementAt(i);
          }
          showmultpics(o, null);
          btDelete.setEnabled(false);
          btMultWordsSelectAll.setEnabled(false);
        }
        else {
          if (dropBox.convertedWords.getModel().getSize() > 0) {
            if (dropBox.convertedWords.getSelectedIndex() >= 0) {
              btDelete.setEnabled(true);
            }
            btMultWordsSelectAll.setEnabled(true);
          }
          dropBox.activate(dropBox.listwordsscroll);
        }
      }
    }
    );

    grid.fill = GridBagConstraints.BOTH;
    grid.anchor = GridBagConstraints.WEST;
    grid.insets = new Insets(0, insSmall, insComponent, insSmall);
    jp1.add(apAdminPics, grid);

    grid.fill = GridBagConstraints.NONE;
    if (universal) {
      grid.weightx = 0;
      grid.anchor = GridBagConstraints.WEST;
      grid.insets = new Insets(0, 0, 0, 0);
    }
    grid.insets = new Insets(0, 0, 0, 0);
    jp1.add(multpan, grid);
    jp1.add(multpreview, grid);


    grid.insets = new Insets(0, 0, 0, 0);

    pnDropBoxSide = new JPanel(new GridBagLayout());

    pnDropBox = new JPanel(new GridBagLayout());

    grid.fill = GridBagConstraints.HORIZONTAL;
    grid.anchor = GridBagConstraints.NORTH;
    grid.insets = new Insets(0, insSmall, insSmall, insSmall);
    pnDropBoxSide.add(btChoose, grid);
    pnDropBoxSide.add(btBrowse2, grid);
    pnDropBoxSide.add(btDelete2, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 1;
    pnDropBoxSide.add(new JPanel(), grid);

    grid.weightx = 1;
    grid.anchor = GridBagConstraints.CENTER;

    grid.fill = GridBagConstraints.BOTH;
    grid.weighty = 1;
    grid.gridx = -1;
    grid.gridy = 0;
    

    pnDropBox.add(dropBox, grid);
    pnDropBox.add(pnDropBoxSide, grid);


    grid.gridx = 0;
    grid.gridy = -1;
    
    jp1.add(pnDropBox, grid);

    grid.insets = new Insets(insComponent, 0, 0, 0);
    jp1.add(jp2, grid);
    grid.insets = new Insets(0, 0, 0, 0);

    grid.weighty = 0;
    grid.fill = GridBagConstraints.BOTH;
    pan3.add(epPan3, grid);

    grid.fill = GridBagConstraints.NONE;
    grid.weighty = 1;
    grid.fill = GridBagConstraints.NONE;
    pan3.add(jp1, grid);
    grid.weighty = 0;
    grid.fill = GridBagConstraints.BOTH;

    pan3.add(exitpan, grid);

    // pan2 setup
    so2 = new String[0];

    topicTree tree = new topicTree();
    tree.dbnames = new String[0];
    tree.onlyOneDatabase = "*";
    tree.setRootVisible(false);
    tree.root.setIcon(jnode.ROOTTOPICTREE);
    tree.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent), false,
               db.TOPICTREE, true, " ");

    jnode gl[] = tree.root.getChildren();
    findword.indexclass ix[] = new findword.indexclass[gl.length];
    for (int i = 0; i < ix.length; ++i) {
      ix[i] = (findword.indexclass) db.find(tree.dbnames[i], gl[i].get(), db.WORDSEARCH);
      if (ix[i] != null) {
        for (int p = 0; p < ix[i].words.length; p++) {
          so2 = u.addStringSort(so2, ix[i].words[p].v);
        }
      }
    }
    String namelist[] = db.list(stus[0],db.TOPIC);
    for(int i=0;i<namelist.length;++i) {
       saveTree1 st = (saveTree1)db.find(stus[0], namelist[i],db.TOPIC);
       for(int k = 1; k < st.curr.names.length; k++){
         so2 = u.addStringSort(so2, st.curr.names[k]);
       }
    }
    grid.weighty = 0;
    grid.weightx = 1;
    grid.gridx = 0;
    grid.gridy = -1;
    grid.fill = GridBagConstraints.BOTH;
    tfWord = new JTextField();
    tfWord.getDocument().addDocumentListener(new DocumentListener() {
      public void changedUpdate(DocumentEvent e) {changetext(); }

      public void insertUpdate(DocumentEvent e) {changetext(); }

      public void removeUpdate(DocumentEvent e) {changetext(); }
    });

    lstWords = new JList();

    lstWords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    lstWords.setAutoscrolls(true);
    lstWords.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        lstWordsValueChanged();
      }
    });
    lstWords.setListData(so2);
    lstWordsScroll = new JScrollPane(lstWords);
    viewWords = new JList();
    viewWords.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    viewWords.setAutoscrolls(true);
    viewWords.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        viewWordsValueChanged();
      }
    });
    viewWords.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        if (e.isControlDown()) {
          ctrldown = true;
        }
      }

      public void keyReleased(KeyEvent e) {
        if (e.isControlDown()) {
          return;
        }
        showmultpics(viewWords.getSelectedValues(), student.findStudent(stus[0]));
        ctrldown = false;
      }
    });

    viewWords.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if (ctrldown) {
          int pp;
          pp = viewWords.locationToIndex(e.getPoint());
          String user = stus[0];
          String word = String.valueOf(viewWords.getModel().getElementAt(pp));
          sharkImage simage = getImage(user, word);
          dropBox.wp.addPic(new sharkImage[] {simage}, null, false);
        }
      }
    });
    viewWordsScroll = new JScrollPane(viewWords);
    topicWords = new wordlist();
    topicWords.saywords = false;
    topicWords.font = null;
    topicWords.parent = this;
    wordTreeScroll = new JScrollPane(topicWords);

    pnCheck = new JPanel();
    orderck = u.CheckBox("wl_extendedw",
                         new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (topicWords.wholeextended = orderck.isSelected()) {
          topic sel = topics.getCurrentTopic();
          if (sel == null) {
            return;
          }
          DefaultListModel mm = (DefaultListModel) topicWords.getModel();
          mm.removeAllElements();
          word words1[] = sel.getAllWords(true);
          for (short i = 0; i < words1.length; ++i) {
            mm.addElement(words1[i]);
          }
        }
        else {
          topicWords.buildTree(true);
        }
        topicWords.setfont();
      }
    }
    );

    orderck.setBackground(Color.orange);
    orderck.setForeground(Color.black);
    pnCheck.setBackground(Color.orange);
    pnCheck.add(orderck);
    topics = new topicTree();
    topics.onlyOneDatabase = "*";
    topics.root.setIcon(jnode.ROOTTOPICTREE);
    topics.setup(new String[] {u.absoluteToRelative(sharkStartFrame.publicTopicLib[0])}, false, db.TOPICTREE, true,
                 u.gettext("pickpicture", "topictitle"));
    topics.getSelectionModel().addTreeSelectionListener(new
        TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        topic sel = topics.getCurrentTopic();
        if (sel == null) {return; }
        if (oldsel_topics != sel) {
          oldsel_topics = sel;
          topicWords.reset();
          topicWords.setup(sel, null, true);
          topicWords.font = null;
          orderck.setSelected(false);
          if (topicWords.canextend) {
            pnCheck.setVisible(true);
          }
          else {
            pnCheck.setVisible(false);
          }
          dropBox.wp.addPic(" ");
          oldsel_topicwords = -1;
        }
      }
    });

    topicWords.addListSelectionListener(new ListSelectionListener() {
      public void valueChanged(ListSelectionEvent e) {
        topicWordsValueChanged();
      }
    });

    pnwlsearch = new JPanel(new GridBagLayout());
    searchck = u.CheckBox("pickpicture_wlsearch",
                          new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        if (searchck.isSelected()) {
          epPan2.setText(p2sellist);
          changestate(RBTOPICLIST);
        }
        else {
          epPan2.setText(p2selword);
          changestate(RBWORDLIST);
        }
      }
    }
    );
    searchck.setToolTipText(null);
    searchck.setBackground(Color.yellow);
    searchck.setForeground(Color.black);
    pnwlsearch.setBackground(Color.yellow);
    pnwlsearch.add(searchck);

    topicWords.font = null;
    pan2.add(epPan2, grid);
    grid.insets = new Insets(insSmall, insSmall, insSmall, insSmall);
    pan2.add(tfWord, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 1;
    pan2.add(wordTreeScroll, grid);
    pan2.add(lstWordsScroll, grid);
    pan2.add(viewWordsScroll, grid);
    grid.weighty = 0;
    pan2.add(pnCheck, grid);
    pan2.add(pnwlsearch, grid);

    if (!universal) {
      rb3.setText(u.gettext("pickpicture",  username.equals(sharkStartFrame.studentList[sharkStartFrame.currStudent].name)?"yourself":"icon"));
      rb2.setText(u.gettext("pickpicture", "view"));
    }
    grid.weighty = 0;
    grid.weightx = 1;
    grid.gridx = 0;
    grid.gridy = -1;
    grid.fill = GridBagConstraints.BOTH;
    JLabel lbImport = new JLabel(u.gettext("pickpicture", "importtitle"));
    JLabel lbView = new JLabel(u.gettext("pickpicture", "viewtitle"));
    ButtonGroup bg = new ButtonGroup();
    bg.add(rb1);
    bg.add(rb2);
    bg.add(rb3);
    scrolltopics = new JScrollPane(topics);
    grid.insets = new Insets(insSmall, insSmall, 0, insSmall);
    pan1.add(lbImport, grid);
    grid.insets = new Insets(0, insComponent, 0, insSmall);
    pan1.add(rb1, grid);
    if(stus.length<=1){
        if(wanticon)
            pan1.add(rb3, grid);
    grid.insets = new Insets(0, insSmall, 0, insSmall);
    pan1.add(lbView, grid);
    grid.insets = new Insets(0, insComponent, 0, insSmall);
    
        pan1.add(rb2, grid);
      }
    grid.insets = new Insets(insComponent * 2, 0, 0, 0);
    rb1.setSelected(true);
    grid.weighty = 1;
    pan1.add(scrolltopics, grid);
    pan1.add(pan1Blank, grid);
    grid.insets = new Insets(0, 0, 0, 0);
    grid.weighty = 1;
    grid.weightx = 1;
    grid.gridx = -1;
    grid.gridy = 0;
    grid.fill = GridBagConstraints.BOTH;
//    getContentPane().add(pan1, grid);
    getContentPane().add(pan2, grid);
    getContentPane().add(pan5, grid);
    getContentPane().add(pan3, grid);
    getContentPane().add(pan4, grid);
    pan4.setVisible(false);
    pan5.setVisible(false);
  }

  void end(){
//      active = false;
      thisjd.dispose();
      sharkStartFrame.mainFrame.setState(NORMAL);
      sharkStartFrame.mainFrame.toFront();
  }

  public void iconchosen(byte[] im, String name) {
      dbupdate(currownpic, im, true);
      chosenWord = currownpic;
      dropBox.wp.addPic(null, sharkStartFrame.studentList[sharkStartFrame.currStudent].name, true);
}

  void doDeleteAction(JButton delbut){
        String nameword[] = new String[] {};
        int lead = -1;
        if (dropBox.convertedWords.isShowing()) {
          int k[];
          lead = dropBox.convertedWords.getLeadSelectionIndex();
          if ( (k = dropBox.convertedWords.getSelectedIndices()).length > 0) {
            for (int i = k.length - 1; i >= 0; i--) {
              String s = (String) dropBox.convertedWords.getModel().
                  getElementAt(k[i]);
              dbdelete(s, false);
              dropBox.convertedWords.removefromList(k[i]);
              int r = dropBox.convertedWords.getModel().getSize();
              if (lead >= 0 && r > lead) {
                dropBox.convertedWords.setSelectedIndex(lead);
              }
              else {
                if (r > 0) {
                  dropBox.convertedWords.setSelectedIndex(r - 1);

                }
              }
            }
            if (dropBox.convertedWords.getModel().getSize() == 0) {
              btMultWordsSelectAll.setEnabled(false);
              if (multpreview.isShowing()) {
                multpreview.setVisible(false);
              }
              dropBox.wp.reposition();
            }

          }
          return;
        }
        else {
          if (state == RBUSERSLIST) {
            nameword = u.addString(nameword, ownpic);
            nameword = u.addString(nameword, ownpicstuset);
          }
          else {
            if (state == RBVIEW) {
              Object strs[] = new Object[] {};
              lead = viewWords.getLeadSelectionIndex();
              if ( (strs = viewWords.getSelectedValues()).length > 0) {
                for (int i = 0; i < strs.length; i++) {
                  nameword = u.addString(nameword, (String) strs[i]);
                }
              }
            }
            else {
              nameword = u.addString(nameword, chosenWord);
            }
          }
        }
        for (int i = 0; i < nameword.length; i++) {
            dbdelete(nameword[i], state == RBUSERSLIST);
        }
        sharkImage shim = getImage(stus[0], nameword[0]);
        delbut.setEnabled(false);
        dropBox.wp.mainPanel.removeAllMovers();
        if (shim == null) {
          dropBox.wp.addPic(nopicture);
        }
        else {
          dropBox.wp.addPic(new sharkImage[] {shim}, null, state == RBUSERSLIST);
          delbut.setEnabled(shim.isimport && (universal || !shim.univseralImport));
        }
        if (state == RBVIEW) {
          refreshlist(studbs[0], lead);
        }
  }

  void doBrowseAction(){
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(multick.isSelected());
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
        int returnVal = fc.showOpenDialog(thisjd);
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

  void showmultpics(Object[] obs, student s) {
    dropBox.wp.reposition();
    sharkImage images[];
    if (s == null && importedpics != null && importedpics.size() > 0) {
      int k = importedpics.size();
      images = new sharkImage[k];
      for (int i = 0; i < k; i++) {
        picitem pp = (picitem) importedpics.get(i);
        images[i] = new sharkImage(sharkStartFrame.t.createImage(pp.bytes), String.valueOf(i));
      }
    }
    else {
      if (s == null) {
        if (multick.isSelected()) {
          s = student.findStudent(stus[0]);
        }
        else {
          return;
        }
      }
      images = new sharkImage[] {};
      for (int i = 0; i < obs.length; i++) {
        sharkImage ima = getImage(s.name, String.valueOf(obs[i]));
        if (ima != null) {
          images = u.addsharkImage(images, ima);
        }
      }
    }
    if (images != null) {
      dropBox.wp.addPic(images, null, false);
    }
  }

  static public void renamed(String oldnam, String newnam) {
    db.rename(sharkStartFrame.resourcesPlus+oldnam+sharkStartFrame.resourcesFileSuffix,
              sharkStartFrame.resourcesPlus+newnam+sharkStartFrame.resourcesFileSuffix);
    File f = null;
    if ( (f = new File(sharkStartFrame.sharedPathplus + oldnam)).isDirectory()) {
      f.renameTo(new File(sharkStartFrame.sharedPathplus + newnam));
    }
  }

  void refreshlist(String name, int pos) {
      String ss2[] = db.list(name, db.PICTURE);
      String ss[] = new String[] {};
      loop1: for (int i = 0; i < ss2.length; i++) {
          ss = u.addStringSort(ss, ss2[i]);
      }
      epPan3.setText(p3setpicword);
      if (ss.length == 0) {
        ss = new String[] {nopicture};
        dropBox.wp.addPic(" ");
        dropBox.setVisible(false);
        if (state != RBVIEW) {
          btBrowse.setVisible(false);
        }
        btDelete.setVisible(false);
      }
      else {
        dropBox.setVisible(true);
        if (state != RBVIEW) {
          btBrowse.setVisible(true);
        }
        btDelete.setVisible(true);
      }
      viewWords.setListData(ss);
      int r = viewWords.getModel().getSize();
      if (pos < 0) {
        return;
      }
      else {
        if (r > pos) {
          oldsel_viewwordlist = -1;
          viewWords.setSelectedIndex(pos);
        }
        else {
          if (r > 0) {
            viewWords.setSelectedIndex(r - 1);
          }
        }
      }

      oldsel_viewwordlist = -1;
      viewWordsValueChanged();
      oldsel_viewwordlist = -1;

  }

  void changetext() {
    String s = tfWord.getText();
    JList currlist;
    if (lstWords.isShowing()) {
      currlist = lstWords;
    }
    else {
      currlist = viewWords;
    }
    int i;
    for (i = 0; i < currlist.getModel().getSize(); i++) {
      String ss = String.valueOf(currlist.getModel().getElementAt(i));
      if (ss.indexOf(s) == 0) {
        break;
      }
    }
    if (i == currlist.getModel().getSize()) {
      return;
    }
    currlist.setSelectedIndex(i);
    currlist.ensureIndexIsVisible(lstWords.getModel().getSize() - 1);
    currlist.ensureIndexIsVisible(i);
    chosenWord = String.valueOf(currlist.getSelectedValue());
  }

  public void setPan2List(JComponent pan) {
    if (pan == null) {
      tfWord.setVisible(false);
      epPan2.setVisible(false);
      pnwlsearch.setVisible(false);
      pnCheck.setVisible(false);
    }
    viewWordsScroll.setVisible(pan == null ? false : pan.equals(viewWordsScroll));
    wordTreeScroll.setVisible(pan == null ? false : pan.equals(wordTreeScroll));
    lstWordsScroll.setVisible(pan == null ? false : pan.equals(lstWordsScroll));
  }

  public void setPan1Comp(JComponent pan) {
    boolean isblank = true;
    if (userspan != null) {
      userspan.setVisible(pan == null ? false : pan.equals(userspan));
      if(userspan.isVisible())isblank=false;
    }
    scrolltopics.setVisible(pan == null ? false : pan.equals(scrolltopics));
    if(scrolltopics.isVisible())isblank=false;
    pan1Blank.setVisible(isblank);
  }

  String[] dblist(String s){
      String ret[] = null;
      for(int i = 0; i < studbs.length; i++){
          if(ret==null)ret = db.list(s, db.PICTURE);
          else ret = u.addString(ret, db.list(s, db.PICTURE));
      }
      return ret;
  }

  void dbupdate(String s, byte[] bs, boolean ownpic){
      for(int i = 0; i < studbs.length; i++){
          if(dev_avatarfile!=null){
              if(!(new File(sharkStartFrame.sharedPathplus+dev_avatarfile+".sha")).exists())
                db.create(dev_avatarfile);
              db.update(dev_avatarfile, s, bs, db.PICTURE);
          }
          else if(dev_widgitfile != null){
              if(!(new File(sharkStartFrame.sharedPathplus+dev_widgitfile)).exists())
                db.create(dev_widgitfile);
              db.update(dev_widgitfile, s, bs, db.PICTURE);
          }
          else
            db.update(ownpic?stus[i]:studbs[i], s, bs, db.PICTURE);
      }
      if(ownpic && sharkStartFrame.mainFrame.currUserPic!=null){
            sharkStartFrame.mainFrame.currUserPic.refresh();
      }
  }

  void dbdelete(String s, boolean ownpic){
      for(int i = 0; i < studbs.length; i++){
          db.delete(ownpic?stus[i]:studbs[i], s, db.PICTURE);
      }
      if(ownpic && sharkStartFrame.mainFrame.currUserPic!=null){
          sharkStartFrame.mainFrame.currUserPic.refresh();
      }
  }

  byte[] dbfind(String s){
      return (byte[]) db.find(studbs[0], s, db.PICTURE);
  }

  void changestate(byte st) {
    state = st;
    oldsel_topicwords = -1;
    oldsel_wordlist = -1;
    oldsel_viewwordlist = -1;
    oldsel_multwordlist = -1;
    oldsel_topics = new topic(new jnode());
    oldsel_usersview = "";
    oldsel_userslogo = "";
    tfWord.setText("");
    dropBox.wp.addPic(" ");



    pnDropBoxSide.setVisible(state == RBUSERSLIST);
    jp2.setVisible(state != RBUSERSLIST);


    dropBox.setVisible(true);
    btBrowse.setVisible(true);
    btBrowse.setEnabled(multick.isSelected());
    btDelete.setEnabled(false);
    btDelete.setVisible(true);
    epPan3.setVisible(true);
    multpan.setVisible(false);
    pan5.setVisible(false);
    pan4.setVisible(false);
    dropBox.setDropTarget(dt);
    pan2.setVisible(true);
    pnwlsearch.setVisible(true);
    pnCheck.setVisible(false);
    pan3.setMinimumSize(panDim);
    pan3.setMaximumSize(panDim);
    pan3.setPreferredSize(panDim);
    setPan1Comp(pan1Blank);
    dropBox.activate(dropBox.wp);
    multpreview.setVisible(false);
    btMultWordsSelectAll.setVisible(false);
    if (!epPan2.isShowing()) {
      epPan2.setVisible(true);
    }
    if (state == RBWORDLIST) {
      epPan3.setText(p3setpicword);
      multpan.setVisible(true);
      if (searchck.isSelected()) {
        changestate(RBTOPICLIST);
        epPan2.setText(p2sellist);
      }
      else {
        lstWordsValueChanged();
        epPan2.setText(p2selword);
        setPan1Comp(pan1Blank);
        tfWord.setVisible(true);
        setPan2List(lstWordsScroll);
        orderck.setVisible(false);
        pnCheck.setVisible(false);
      }
    }
    else {
      if (state == RBVIEW) {
        epPan3.setVisible(false);
        setPan2List(viewWordsScroll);
        btBrowse.setEnabled(false);
        tfWord.setVisible(true);
        epPan2.setText(p2seluserword);
        dropBox.setDropTarget(null);
        if (universal) {
          epPan2.setText(p2seluserwordadmin);
        }
        btBrowse.setVisible(false);
        refreshlist(studbs[0], -1);
        pnwlsearch.setVisible(false);
        if (universal) {
          setPan1Comp(userspan);
        }
        oldsel_viewwordlist = -1;
        tfWord.requestFocus();
      }
      else {
        if (state == RBUSERSLIST) {
          setPan1Comp(pan1Blank);
          pan2.setVisible(false);
          epPan3.setText(p3setpicword);
          if (universal) {
            pan5.setVisible(true);
          }
          else {
            pan3.setMinimumSize(panDim2);
            pan3.setMaximumSize(panDim2);
            pan3.setPreferredSize(panDim2);
            btBrowse.setEnabled(true);
            sharkImage image = getImage(stus[0], ownpic);
            if (image != null) {
              dropBox.wp.addPic(new sharkImage[] {image}, stus[0], true);
              btDelete.setEnabled(true);
            }
            else {
              dropBox.wp.addPic(nopicture);
              btDelete.setEnabled(false);
            }
          }
        }
        else {
          if (state == RBTOPICLIST) {
            multpan.setVisible(true);
            setPan1Comp(scrolltopics);
            tfWord.setVisible(false);
            setPan2List(wordTreeScroll);
            if (topicWords.canextend) {
              pnCheck.setVisible(true);
            }
            else {
              pnCheck.setVisible(false);
            }
            orderck.setVisible(true);
            topicWordsValueChanged();
          }
        }
      }
    }
    if ( (state == RBWORDLIST || state == RBTOPICLIST) && multick.isSelected()) {
      setPan2List(null);
      setPan1Comp(pan1Blank);
      btMultWordsSelectAll.setVisible(true);
      if (dropBox.convertedWords.getModel().getSize() > 0) {
        btMultWordsSelectAll.setEnabled(true);
        dropBox.activate(dropBox.listwordsscroll);
      }
      else {
        btMultWordsSelectAll.setEnabled(false);
      }
    }

    if (tfWord.isShowing()) {
      tfWord.requestFocus();
    }
    dropBox.wp.reposition();
  }

  class GetPicture implements Runnable {
    File fths[];
    GetPicture thispic;
    public boolean stop = false;

    public GetPicture(File[] fthred) {
      thispic = this;
      fths = fthred;
    }

    public void run() {
      adminpics = null;
      Vector v = new Vector();
      String n;
      boolean importtofile = (dev_widgitfile!=null || dev_avatarfile!=null);
      if (!multick.isSelected()) {
        importedpics = null;
      }
      if (state == RBUSERSLIST) {
          n = stus[0];
      }
      else {
        n = studbs[0];
      }
      String dbname = n;
      for (int i = 0; i < fths.length && !stop; i++) {
        try {

          if (importtofile || fths.length > 1 ||
              (state == RBWORDLIST || state == RBTOPICLIST) &&
              multick.isSelected()) {
            dropBox.lbProgress.setText(u.edit(u.gettext("pickpicture",
                "processing"), String.valueOf(i + 1),
                                              String.valueOf(fths.length)));
            String nam = fths[i].getName();
            chosenWord = nam.substring(0, nam.lastIndexOf("."));
          }
          else {
            dropBox.lbProgress.setText(u.gettext("pickpicture", "processing1"));
          }
          source = null;
          ImageUtil_base iu = new ImageUtil_base();
          byte buf[] = iu.compressToBytes(fths[i]);
            if (!importtofile && state == RBUSERSLIST) {
              chosenWord = currownpic;
            }
            dbupdate(chosenWord, buf, state == RBUSERSLIST);
          
          v.add(chosenWord);
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
      converted(v);
    }
  }



  sharkImage getImage(String user, String word) {
    sharkImage simage = null;
      simage = sharkImage.find(word, student.findStudent(user), true, universal);
    return simage;
  }


  void doAdminWarning(boolean show) {
    if (show && !apAdminPics.isVisible()) {
      apAdminPics.setVisible(true);
      dropBox.wp.reposition();
    }
    else {
      if (!show && apAdminPics.isVisible()) {
        apAdminPics.setVisible(false);
        dropBox.wp.reposition();
      }
    }
  }

  void converted(Vector v) {
    drawwaiting = false;
    oldsel_multwordlist = -1;
    if (v == null) {
      dropBox.activate(dropBox.wp);
      multpreview.setVisible(false);
      return;
    }
//    if(dev_avatarfile!=null && (new File(sharkStartFrame.sharedPathplus+dev_avatarfile)).exists())

    if (! ( (state == RBWORDLIST || state == RBTOPICLIST) && multick.isSelected())) {
      if (state == RBUSERSLIST) {
        dropBox.wp.addPic(null, stus[0], true);
        btDelete.setEnabled(true);
      }
      else {
        dropBox.wp.addPic(null, studbs[0], state == RBUSERSLIST);
      }
    }
    else {
      dropBox.pBar.setVisible(false);
      dropBox.btCancel.setVisible(false);
      dropBox.lbProgress.setText("");
      if (dropBox.convertedWords.getModel().getSize() > 0) {
        dropBox.convertedWords.addtoList(v);
      }
      else {
        dropBox.convertedWords.setListData(v);

      }
      oldsel_multwordlist = -1;
      dropBox.activate(dropBox.listwordsscroll);
      if (v.size() > 0) {
        if (!multpreview.isShowing()) {
          multpreview.setVisible(true);
          btMultWordsSelectAll.setEnabled(true);
        }
      }
    }
    dropBox.wp.reposition();
  }

  class dropList extends JList implements DropTargetListener, Serializable {
    DropTarget dropT;
    public dropList() {
      super();
      dropT = new DropTarget(this, this);
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

    public void addtoList(Vector vv) {
      Vector v = new Vector();
      String str;
      for (int i = 0; i < dropBox.convertedWords.getModel().getSize(); i++) {
        if ( (str = (String) dropBox.convertedWords.getModel().getElementAt(i)) != null) {
          v.add(str);
        }
      }
      for (int k = 0; k < vv.size(); k++) {
        boolean add = true;
        for (int i = 0; i < v.size(); i++) {
          if (String.valueOf(v.get(i)).equals(String.valueOf(vv.get(k)))) {
            add = false;
          }
        }
        if (add) {
          v.add(vv.get(k));
        }
      }
      oldsel_multwordlist = -1;
      dropBox.convertedWords.setListData(v);
    }

    public void removefromList(int p) {
      Vector v = new Vector();
      String str;
      for (int i = 0; i < dropBox.convertedWords.getModel().getSize(); i++) {
        if ( (str = (String) dropBox.convertedWords.getModel().getElementAt(i)) != null
            && i != p) {
          v.add(str);
        }
      }
      oldsel_multwordlist = -1;
      dropBox.convertedWords.setListData(v);
    }
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

  class mainPan extends JPanel implements DropTargetListener, Serializable {
    public JButton btCancel = new JButton(u.gettext("cancel", "label"));
    public JLabel lbProgress = new JLabel();
    public JPanel jpProgress;
    public wordpicture wp;
    public JScrollPane listwordsscroll;
    public dropList convertedWords;
    GridBagConstraints grid;
    JProgressBar pBar;
    File ff[];

    public mainPan(Dimension d) {
      super();
      dt = new DropTarget(this, this);
      drawwaiting = false;
      setLayout(new GridBagLayout());
      jpProgress = new JPanel();
      jpProgress.setLayout(new GridBagLayout());
      setBorder(BorderFactory.createLoweredBevelBorder());
      convertedWords = new dropList();
      convertedWords.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      convertedWords.addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent e) {
          int sel = convertedWords.getLeadSelectionIndex();
          if (sel >= 0 && oldsel_multwordlist != sel) {
            oldsel_multwordlist = sel;
            btDelete.setEnabled(convertedWords.getSelectedIndex() >= 0);
          }
        }
      });
      setMaximumSize(d);
      setPreferredSize(d);
      setMinimumSize(d);
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
      grid.insets = new Insets(0, 0, 0, 0);
      jpProgress.setOpaque(true);
      wp = new wordpicture(0, 0, (int) d.getWidth(), (int) d.getHeight());
      wp.setMaximumSize(d);
      wp.setPreferredSize(d);
      wp.setMinimumSize(d);
      grid.weighty = 1;
      grid.weightx = 1;
      grid.gridx = 0;
      grid.gridy = -1;
      grid.fill = GridBagConstraints.BOTH;
      add(listwordsscroll = new JScrollPane(convertedWords), grid);
      add(jpProgress, grid);
      add(wp, grid);
      setMaximumSize(d);
      setPreferredSize(d);
      setMinimumSize(d);
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
      listwordsscroll.setVisible(pan == null ? false : pan.equals(listwordsscroll));
    }

    public void dragEnter(DropTargetDragEvent dsde) {
      doDragEnter(this);
    }

    public void dragExit(DropTargetEvent dse) {}

    public void dragOver(DropTargetDragEvent dsde) {}

    public void dropActionChanged(DropTargetDragEvent dsde) {}

    public void drop(DropTargetDropEvent dsde) {
      if ( (drawwaiting) ||
          state == RBVIEW ||
          ( (lstWords.getSelectedIndex() < 0
             && topicWords.getLeadSelectionIndex() < 0 
             ) &&
           ! ( (state == RBWORDLIST || state == RBTOPICLIST) &&
              multick.isSelected())
           && ! (state == RBUSERSLIST && !universal)
           )) {
      if(state==RBUSERSLIST)
        u.okmess(shark.programName, u.gettext("pickpicture", "notselected_user"));
      else
        u.okmess(shark.programName, u.gettext("pickpicture", "notselected_word"));
        return;
      }
      doDrop(dsde);
    }
  }

  void fileselected(File[] ff) {
    if ( (state == RBWORDLIST || state == RBTOPICLIST) && !multick.isSelected() &&
        ff.length > 1) {
      String ss = "<html>" + u.splitStringToHtml(u.gettext("pickpicture", "usemultiple")) + "</html>";
      u.okmess(shark.programName, ss);
      dropBox.activate(dropBox.wp);
      return;
    }

    boolean dev = dev_avatarfile!=null || dev_widgitfile!=null;

    if (!dev &&  (state == RBWORDLIST || state == RBTOPICLIST) && multick.isSelected()) {
      String s[] = new String[] {};
      // search to see if name of file is a word in Wordshark
      for (int i = ff.length - 1; i >= 0; i--) {
        String nam = ff[i].getName().substring(0,
                                               ff[i].getName().lastIndexOf("."));
        if (u.findString(so2, nam) < 0) {
          s = u.addString(s, nam);
          ff = u.removeFile(ff, i);
        }
      }
      if (s.length > 0) {
        String mess = "<html>" + u.splitStringToHtml(u.gettext("pickpicture", "noconvert"));
        for (int i = 0; i < s.length; i++) {
          mess += s[i] + "<br>";
        }
        mess += "</html>";
        u.okmess(shark.programName, mess);
      }
      if (ff.length == 0) {
        if (!multick.isSelected()) {
          dropBox.activate(dropBox.wp);
        }
        return;
      }
      else {
        dropBox.activate(dropBox.jpProgress);
      }
    }
    if (multpreview.isSelected()) {
      multpreview.setSelected(false);
    }
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
    drawwaiting = true;
    dropBox.wp.mainPanel.removeAllMovers();
    dropBox.wp.mainPanel.stoprun = true;
    Thread myThread;
    myThread = new Thread(gp = new GetPicture(ff));
    myThread.start();
  }

  class wordpicture extends JPanel {
    public runMovers mainPanel;
    boolean isshowingimport = false;

    public wordpicture(int x1, int y1, int w1, int h1) {
      mainPanel = new runMovers();
      add(mainPanel, BorderLayout.CENTER);
      setLayout(new BorderLayout());
      setBounds(x1, y1, w1, h1);
      mainPanel.setBounds(0, 0, w1, h1);

      this.addComponentListener(new ComponentAdapter() {
        public void componentMoved(ComponentEvent e) {
          reposition();
        }
      });
    }

    public void reposition() {
      thisjd.pack();
      if (dropBox.listwordsscroll.isVisible()) {
        return;
      }
      mainPanel.stoprun = true;
      if (isShowing()) {
        mainPanel.setLocation(new Point(0, 0));
      }
      mainPanel.reset();
      revalidate();
      mainPanel.stoprun = false;
      mainPanel.start1();
    }

    public void addPic(sharkImage imarr[], String dbs, boolean logo) {
      String dbname;
      if(universal)
          dbname =  universalImageFile;
      else
          dbname =  dbs;

      mainPanel.removeAllMovers();
      isshowingimport = false;
      if (imarr == null) {
        byte buf[] = null;
        if (importedpics == null) {
          buf = (byte[]) db.find(dbname, chosenWord, db.PICTURE);
        }
        else {
          if (importedpics.size() > 0) {
            picitem pitem = (picitem) importedpics.get(0);
            buf = pitem.bytes;
          }
        }

        sharkImage si;
        if (buf != null) {
          si = new sharkImage(sharkStartFrame.t.createImage(buf), chosenWord);
        }
        else {
            picitem pi;
           if(importedpics==null)
               return;
           if((pi=(picitem) importedpics.get(0))==null)
               return;
            if(chosenWord==null)
                return;
          si = new sharkImage(sharkStartFrame.t.createImage(pi.bytes),
                              chosenWord);
        }
        imarr = new sharkImage[] {si};
        imarr[0].isimport = true;
        isshowingimport = true;
      }
      int totims = imarr.length;
      int across = (int) Math.ceil(Math.sqrt(totims));
      int down = totims / across;
      if (totims % across != 0) {
        down++;
      }
      int x = 0;
      int y = 0;
      for (int i = 0; i < totims; i++) {
        if (imarr[i].isimport) {
          isshowingimport = true;
        }
        if (i != 0 && i % across == 0) {
          x = 0;
          y += mover.HEIGHT / across;
        }
        imarr[i].w = mover.WIDTH / across;
        imarr[i].h = mover.HEIGHT / across;
        imarr[i].adjustSize(dropBox.wp.getWidth() / across,
                            dropBox.wp.getHeight() / across);
        imarr[i].keepMoving = true;
        int k;
        if (imarr[i].w > imarr[i].h) {
          k = (mover.HEIGHT / across - imarr[i].h) / 2;
          mainPanel.addMover(imarr[i], x, y + k);
        }
        else {
          k = (mover.WIDTH / across - imarr[i].w) / 2;
          mainPanel.addMover(imarr[i], x + k, y);
        }
        x += mover.WIDTH / across;
      }
      addPic2(imarr);
    }

    // show text
    public void addPic(String mess) {
      doAdminWarning(false);
      mainPanel.removeAllMovers();
      isshowingimport = false;
      mover.simpletextmover tt = new mover.simpletextmover(mess,
          mover.WIDTH / 2, mover.HEIGHT / 2);
      mainPanel.addMover(tt, tt.w / 2, mover.HEIGHT / 4);
      addPic2(null);
    }

    void addPic2(sharkImage[] si) {
      dropBox.activate(dropBox.wp);
      mainPanel.stoprun = false;
      mainPanel.start1();
      dropBox.validate();
      dropBox.repaint();
      if (si != null) {
        btDelete2.setEnabled(si[0].isimport && (universal || !si[0].univseralImport));
        btDelete.setEnabled(si[0].isimport && (universal || !si[0].univseralImport));
        if (!si[0].isimport) {
          si = si;
        }
        return;
      }
    }
  }
  void topicWordsValueChanged() {
    if (multick.isSelected()) {
      return;
    }
    int sel = topicWords.getLeadSelectionIndex();
    if (sel >= 0 && oldsel_topicwords != sel && topicWords.getSelectedValue() != null) {
      oldsel_topicwords = sel;
      if (!btBrowse.isEnabled()) {
        btBrowse.setEnabled(true);
      }
      String s = String.valueOf(topicWords.getSelectedValue());
      if (s != null) {
        chosenWord = s;
        sharkImage image = sharkImage.find(chosenWord, true, universal);
        if (image != null) {
          dropBox.wp.addPic(new sharkImage[] {image}, null, false);
        }
        else {
          dropBox.wp.addPic(nopicture);
        }
      }
    }
  }

  void viewWordsValueChanged() {
    int sel = viewWords.getLeadSelectionIndex();
    if (sel >= 0 && oldsel_viewwordlist != sel && viewWords.getSelectedValue() != null) {
      oldsel_viewwordlist = sel;
      String s = String.valueOf(viewWords.getSelectedValue());
      if (s != null) {
        if (viewWords.getSelectedValues().length < 2) {
          sharkImage simage = null;
          if (stus[0] != null) {
            simage = getImage(stus[0], s);
          }
          if (simage != null) {
            dropBox.wp.addPic(new sharkImage[] {simage}, null, false);
            btDelete.setEnabled(simage.isimport && (universal || !simage.univseralImport));
          }
          else {
            dropBox.wp.addPic(nopicture);
            btDelete.setEnabled(false);
          }
        }
      }
      if(!btDelete.isEnabled())btDelete.setEnabled(true);
    }
  }

  void lstWordsValueChanged() {
    if (multick.isSelected()) {
      return;
    }
    int sel = lstWords.getLeadSelectionIndex();
    if (sel >= 0 && oldsel_wordlist != sel && lstWords.getSelectedValue() != null) {
      if (!btBrowse.isEnabled()) {
        btBrowse.setEnabled(true);
      }
      oldsel_wordlist = sel;
      String s = String.valueOf(lstWords.getSelectedValue());
      if (s != null) {
        chosenWord = s;
        importedpics = null;
        sharkImage simage;
        if(stus.length<=1){
            simage = sharkImage.find(s, student.findStudent(stus[0]), true, universal);
        }
        else
            simage = sharkImage.find(s, true, universal);
        if (simage != null) {
          dropBox.wp.addPic(new sharkImage[] {simage}, null,false);
          btDelete.setEnabled(simage.isimport && (universal || !simage.univseralImport));
        }
        else {
          dropBox.wp.addPic(nopicture);
          btDelete.setEnabled(false);
        }
      }
    }
  }
}
