package shark;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.*;

public class findword
        extends JDialog implements Runnable {

    static final short MAXFIND = 400;
    GridBagConstraints grid = new GridBagConstraints();
//   GridBagConstraints grid2 = new GridBagConstraints();
    topicTree tree;
    topicTree treeDisplay;
    jnode gl[];
    static String treeheading = u.gettext("findword_topictree", "treeheading");
    static String gameheading = u.gettext("findword_games", "gameheading");
    AutoCompleteCombo wordname;
    String wordtextname = "search_wordtext";
    String wordtext = student.optionstring(wordtextname);
    JPanel pnNameType = new JPanel(new GridBagLayout());
    JPanel pnCourseAndTopics = new JPanel(new GridBagLayout());
    JPanel pnCourseAndTopics2 = new JPanel(new GridBagLayout());
    JPanel pnWordList = new JPanel(new GridBagLayout());
    JPanel pnMiddleCol = new JPanel(new GridBagLayout());
    JPanel pnBlankName = new JPanel(new GridBagLayout());
    JPanel pnBlankGame = new JPanel(new GridBagLayout());
    JPanel panelf = new JPanel();
    JPanel panell = new JPanel();
    JButton gameicons;
    Rectangle gamerect[];
    runMovers gamepanel;
    findword thisw = this;
    TreePath pp;
    jnode lastnode;
    gamestoplay gameTree;
    boolean showinggames, showingicons;
    Thread thread;
    JPanel paneltopics = new JPanel(new GridBagLayout());
    mlabel_base topiclabel = u.mlabel("findword_topiclabel");
    mlabel_base wordslabel1 = new mlabel_base(u.centreString(u.gettext("findword_wordlistwords", "label"), getFontMetrics(topiclabel.getFont())));
    String wordsl2 = u.centreString(u.gettext("findword_wordlistwords", "label2"), getFontMetrics(topiclabel.getFont()));
    String wordsl3 = u.centreString(u.gettext("findword_wordlistwords", "label3"), getFontMetrics(topiclabel.getFont()));
    mlabel_base wordslabel2 = new mlabel_base(wordsl2);
    mlabel_base treelabel = new mlabel_base(u.gettext("findword_", "selectcourse"));
    String strSelectcourse2 = u.gettext("findword_", "selectcourse2");
    String strSelectcourse2single = u.gettext("findword_", "selectcourse2single");
    mlabel_base treelabel2 = new mlabel_base(strSelectcourse2);
    JPanel panChooseCourse = new JPanel(new GridBagLayout());
    JPanel panChooseCourse2 = new JPanel(new GridBagLayout());
    JList lsChooseCourse = new JList();
    JList lsChooseCourse2 = new JList();
    JList indexlist_words;
    JList indexlist_titles;
    String paths_titles[], topics_titles[];
    String paths_words[], topics_words[];
    ArrayList words_duplicates;
    ArrayList titles_duplicates;
    wordlist wordTree;
    boolean savephonics;
    boolean savecheckboxphonics;
    JScrollPane scroller;
    JScrollPane scroller2;
    static final String toomany = u.gettext("findword_", "toomany");
    static final String nowords = u.gettext("findword_", "nowords");
    static final String ignorekeywords = u.gettext("findword_", "_ignorekeywords");
    static String gamelists[][];
    String lastcourselist;

    static class searchclass implements Serializable {

        static final long serialVersionUID = 8675367764162046784L;
        String v;
        int refs[];    // positions  in 'index'
    }
    searchclass selectedwords[], selectedindex[];

    static class indexclass implements Serializable { // one for each topic tree

        static final long serialVersionUID = 6720624286329362302L;
        searchclass words[];
        searchclass keywords[];
        searchclass index[];
        String name;
    }
    indexclass ix[], ax[];
    boolean indexlistchanging;
    program programbuild;
    simpleprogram simpleprogrambuild;
    // used to stop progress monitor when this dialog is closed
    boolean running = false;
    int lastrect = -1;
    JButton nextlist;
    String repeats[];
    String repeatsWithinCourse[];
    JLabel lbWord = new JLabel();
    JLabel lbTitle = new JLabel();
    Color panelcol = Color.gray;
    JButton btok = new JButton(u.gettext("select", "label"));
    JButton btCancel = new JButton(u.gettext("cancel", "label"));
    boolean changed = false;
    Object parent;
    String strwordlist = u.gettext("findword_", "wordlist");
    GamesIcon_base activeIcons[] = null;
    GamesIcon_base highlightedIcons = null;
    String strtabnextpage = u.gettext("tabs", "nextpage");
    String strtabprevpage = u.gettext("tabs", "prevpage");
    String activegames[] = null;
    boolean onfirstpage;
    mover.formattedtextmover pagemove;
    Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
    JPanel wtab1;
    JPanel wtab3;
    int lasttab = 0;
    String wordlisttab1 = u.gettext("wordlisttabs", "tab1");
    String wordlisttab2 = u.gettext("wordlisttabs", "tab2");
    String wordlisttab3 = u.gettext("wordlisttabs", "tab3");
    JCheckBox jcbu;
    boolean userwantphonics = false;
    final findword thissearch = this;
    topic oriTopic;
    boolean oriUsePhonics;
    boolean oriUsePhonemesCB;
    boolean videoactive = false;
    static int TYPE_GETLISTNAME = 0;
    static int TYPE_GETGAMENAME = 1;
    static int TYPE_GETCOURSELISTNAME = 2;
    static int TYPE_GETJUSTTOPIC = 3;
    int searchtype = -1;
    JPanel namePn;
    JPanel coursePn;
    JPanel gamesPn;
    JPanel topicLabelPn;
    JPanel topicLabelPn2;
    jnode lastselnode;
    String strClickMess = u.gettext("findword_", "clickmess");
    JPanel pnFoundLists;
    JPanel vidpn;
    JPanel pnVidSpacer = new JPanel();
    JTabbedPane jtp;
    String[] topicsUsed;
    String topicsUsedStrings[];
    String listofcourses[];
    
    public findword(Object programbuild1) {
        parent = programbuild1;
        addConstructor(programbuild1);
    }

    public findword(Object programbuild1, JFrame owner) {
        super(owner);
        parent = programbuild1;
        addConstructor(programbuild1);
    }

    public findword(Object programbuild1, JDialog owner) {
        super(owner);
        parent = programbuild1;
        addConstructor(programbuild1);
    }

    public findword(Object programbuild1, JFrame owner, int type) {
        super(owner);
        searchtype = type;
        parent = programbuild1;
        addConstructor(programbuild1);
    }

    public findword(Object programbuild1, JDialog owner, int type) {
        super(owner);
        searchtype = type;
        parent = programbuild1;
        addConstructor(programbuild1);
    }    
    
    public findword(Object programbuild1, JFrame owner, int type, String[] tps) {
        super(owner);
        searchtype = type;
        parent = programbuild1;
        topicsUsed = tps;
        addConstructor(programbuild1);
    }    
    
    
    private void addConstructor(Object programbuild1) {
        setOriTopic();
        short i;
        if (shark.macOS) {
            sharkStartFrame.mainFrame.setJMenuBar(new javax.swing.JMenuBar());
        }
        if (programbuild1 instanceof program) {
            programbuild = (program) programbuild1;
        } else if (programbuild1 instanceof simpleprogram) {
            simpleprogrambuild = (simpleprogram) programbuild1;
        }
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(sharkStartFrame.mainFrame.getBounds());
        this.addComponentListener(new ComponentAdapter() {

            public void componentMoved(ComponentEvent e) {
                removeComponentListener(this);
                setBounds(sharkStartFrame.mainFrame.getBounds());
                validate();
                addComponentListener(this);
            }
        });
        gameicons = u.sharkButton();
        topiclabel.setText(u.gettext("findword_", "clicklist"));
        gameicons.setText(u.gettext("findword_", "gamesearch"));
        gameicons.setToolTipText(u.gettext("findword_", "gamesearchtooltip"));
        wordslabel1.setText(strwordlist);
        this.setResizable(false);
        if (wordtext == null) {
            wordtext = "";
        }
        setVisible(true);
        requestFocus();
        settitle();
        namePn = new JPanel(new GridBagLayout());
        coursePn = new JPanel(new GridBagLayout());
        gamesPn = new JPanel(new GridBagLayout());
        jtp = new JTabbedPane();
//        jtp.add(u.gettext("findword_", "searchtabname"), namePn);
//        jtp.add(u.gettext("findword_", searchtype==TYPE_GETCOURSELISTNAME?"searchtabusedcourse":"searchtabcourse"), coursePn);
//        if(searchtype!=TYPE_GETCOURSELISTNAME)
//            jtp.add(u.gettext("findword_", "searchtabgame"), gamesPn);
        if(searchtype!=TYPE_GETCOURSELISTNAME){
            jtp.add(u.gettext("findword_", "searchtabname"), namePn);
            if(searchtype!=TYPE_GETJUSTTOPIC)
                jtp.add(u.gettext("findword_", "searchtabgame"), gamesPn);
        }
        else
            jtp.add(u.gettext("findword_", "searchtabusedcourse"), coursePn);

        jtp.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent evt) {
                if (thread != null && thread.isAlive()) {
                   running = false;
                }
                JTabbedPane pane = (JTabbedPane) evt.getSource();
                int sel = pane.getSelectedIndex();
                Component c = pane.getComponentAt(sel);
                if (c.equals(namePn)) {
                    createnamepanel();
                } else if (c.equals(coursePn)) {
                    createcoursepanel();
                } else if (c.equals(gamesPn)) {
                    setupgametree();
                    creategamepanel();
                    validate();
                }
            }
        });

        this.getContentPane().setLayout(new GridBagLayout());
        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    finish();
                }
            }
        });


        JPanel jpbottom = new JPanel(new GridBagLayout());
        jpbottom.setOpaque(false);
//         JPanel jpbottominner = new JPanel(new GridBagLayout());
        vidpn = new JPanel(new GridBagLayout());
        vidpn.setOpaque(false);
//         grid.fill =  GridBagConstraints.NONE;
        int sw = sharkStartFrame.mainFrame.getWidth();
        int buttondim = (sw * 14 / 22) / 24;
        int buttonimdim = buttondim - (buttondim / 5);
        JButton but_vids = u.sharkButton();
        but_vids.setPreferredSize(new Dimension(buttondim, buttondim));
        but_vids.setMinimumSize(new Dimension(buttondim, buttondim));
        but_vids.setToolTipText(u.gettext("videotutorials", "searchtooltip"));
        but_vids.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                videoactive = true;
                new TutorialChoice_base(thisw,
                        new String[]{u.gettext("videotutorials", "search")},
                        new String[]{u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidsearch", "url")});
            }
        });
        Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites"
                + sharkStartFrame.separator
                + "video_il48.png");
        but_vids.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH)));

        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites"
                + sharkStartFrame.separator
                + "infoCIRCLE_il48.png");

        grid.fill = GridBagConstraints.NONE;
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weighty = 1;
        grid.weightx = 1;
        mlabel_base vidlab = u.mlabel("");
        vidlab.setFont(plainfont);
        vidlab.setText(u.gettext("videotutorials", "title_single"));
        vidlab.setOpaque(false);
        grid.insets = new Insets(0, 0, 0, 0);
        grid.weightx = 0;
        
        if (searchtype != TYPE_GETGAMENAME){
            grid.insets = new Insets(15, 10, 15, 30);       
            btok.setEnabled(false);
            if (shark.macOS) {
                jpbottom.add(btCancel, grid);
            } else {
                jpbottom.add(btok, grid);
            }
            grid.insets = new Insets(15, 0, 15, 10);
            if (shark.macOS) {
                jpbottom.add(btok, grid);
            } else {
                jpbottom.add(btCancel, grid);
            }            
        }
        else{
            grid.insets = new Insets(10, 10, 10, 10);
            jpbottom.add(btCancel, grid);
        }
        if (shark.macOS) {
    //               btok.setForeground(Color.gray);
        } else {
    //                btok.setBackground(Color.gray);
    //                btok.setForeground(Color.white);
        }
        grid.weightx = 0;
        int margin = 10;
        int rmargin = margin*3;
        grid.insets = new Insets(margin, margin, margin, margin);
        vidpn.add(but_vids, grid);
        grid.insets = new Insets(margin, 0, margin, margin);
        vidpn.add(vidlab, grid);
        vidlab.setForeground(Color.white);
        grid.weightx = 1;
        rmargin += but_vids.getPreferredSize().getWidth();
        rmargin += vidlab.getPreferredSize().getWidth();
        grid.fill = GridBagConstraints.BOTH;
        Color col = u.lighter(Color.lightGray, 1.5f);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 0;
        grid.weighty = 1;
  //      grid.insets = new Insets(sharkStartFrame.screenSize.height/28, 0, 0, 0);
  //      if (searchtype != TYPE_GETGAMENAME)
  //          this.getContentPane().add(jtp, grid);
        
        
        if (searchtype != TYPE_GETGAMENAME){
            grid.insets = new Insets(sharkStartFrame.screenSize.height/28, 0, 0, 0);
            this.getContentPane().add(jtp, grid);
        }
        else{
            grid.insets = new Insets(0, 0, 0, 0);
            this.getContentPane().add(gamesPn, grid);
        }        
        
        
        grid.insets = new Insets(0, 0, 0, 0);
        grid.weighty = 0;
        grid.gridx = -1;
        grid.gridy = 0;
        JPanel btPn = new JPanel(new GridBagLayout());
        boolean wantvideos = shark.wantTutVids && (searchtype != TYPE_GETGAMENAME);
        if (wantvideos) {
            grid.weightx = 0;
            btPn.add(vidpn, grid);
            grid.weightx = 1;
        }  
        btPn.add(jpbottom, grid);
        
        if (wantvideos) {
            grid.weightx = 0;            
            pnVidSpacer.setOpaque(false);
            pnVidSpacer.setMinimumSize(new Dimension(rmargin, 5));
            pnVidSpacer.setPreferredSize(new Dimension(rmargin, 5));
            btPn.add(pnVidSpacer, grid);
            grid.weightx = 1;
        }   
        btPn.setBackground(new Color(150,150,150));
        btPn.setOpaque(true);
                
        grid.weightx = 0;
        grid.gridx = 0;
        grid.gridy = -1;
        this.getContentPane().add(btPn, grid);
        grid.weighty = 1;
        this.getContentPane().setBackground(col);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.gridx = -1;
        grid.gridy = 0;
        grid.insets = new Insets(0, 0, 0, 0);
        pnWordList.setBorder(BorderFactory.createLineBorder(Color.orange, 2));
        
        pnWordList.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width * 6 / 20, (int)sharkStartFrame.screenSize.height/2));
        pnWordList.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width * 6 / 20, (int)sharkStartFrame.screenSize.height/2)); 
                              
        pnCourseAndTopics.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, 2));
        pnCourseAndTopics2.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, 2));
        
      
        
        wordslabel1.setBackground(Color.orange);
        wordslabel1.setForeground(Color.black);
        wordslabel2.setBackground(Color.orange);
        wordslabel2.setForeground(Color.black);
        treelabel.setBackground(sharkStartFrame.topictreecolor);
        treelabel.setForeground(Color.black); 
        treelabel2.setOpaque(false);
        panelf.setLayout(new GridBagLayout());
        panell.setLayout(new GridBagLayout());

        paneltopics.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, 2));

        topiclabel.setForeground(Color.black);
        grid.fill = GridBagConstraints.NONE;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.gridheight = 1;
        grid.weighty = 1;
        grid.weightx = 1;
        tree = new topicTree();
        tree.dbnames = new String[0];
        tree.onlyOneDatabase = "*";
        tree.root.setIcon(jnode.ROOTTOPICTREE);
        tree.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent), false,
                db.TOPICTREE, true, u.gettext("findword_title", "topiclists"));

        treeDisplay = new topicTree();
        if(topicsUsed!=null){
            topicsUsedStrings = new String[]{};
            for(int k = 0; k < topicsUsed.length; k++){
                if(u.findString(topicsUsedStrings, topicsUsed[k])<0)
                    topicsUsedStrings = u.addString(topicsUsedStrings, topicsUsed[k]);
            }
            treeDisplay.setCellRenderer(new listtreepainter(topicsUsedStrings));
        }
        
        
        treeDisplay.setToggleClickCount(1);
        treeDisplay.onlyOneDatabase = "*";
        treeDisplay.root.setIcon(jnode.ROOTTOPICTREE);
        treeDisplay.setRootVisible(false);
        scroller2 = u.uScrollPane(treeDisplay);        
        gl = tree.root.getChildren();
        ix = new indexclass[gl.length];
        for (i = 0; i < ix.length; ++i) {
            ix[i] = (indexclass) db.find(tree.dbnames[i], gl[i].get(), db.WORDSEARCH);
            if (ix[i] == null  || shark.doTopicIndex) {     // this condition should be taken out before a release?
                ix[i] = setupwords(gl[i]);
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if (!ix[i].name.trim().equals("")) //endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                {
                    db.update(tree.dbnames[i], gl[i].get(), ix[i], db.WORDSEARCH);
                }
            }
        }
        wordname = new AutoCompleteCombo();
        wordname.setSelectedItem(wordtext);
        wordname.setForeground(Color.red);
        lbWord.setText("'" + wordtext + "'");
        lbTitle.setText("'" + wordtext + "'");
        wordname.setForeground(Color.black);
        wordname.setFont(wordname.getFont().deriveFont((float) wordname.getFont().getSize() * 5 / 4));
        wordname.setMinimumSize(wordname.getPreferredSize());
        grid.fill = GridBagConstraints.NONE;
        grid.weighty = 0;
        grid.weightx = 1;
        grid.gridx = -1;
        grid.gridy = 0;
        JLabel ll = new JLabel(u.gettext("findword_", "search") + "  ");
        ll.setBackground(Color.yellow);
        ll.setOpaque(true);
        
        

        
        JPanel pnSearchType = new JPanel(new GridBagLayout());
        
        pnSearchType.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width * 7 / 20, sharkStartFrame.screenSize.height/14));
        pnSearchType.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width * 7 / 20, sharkStartFrame.screenSize.height/14));      
                
        
        pnSearchType.setOpaque(false);
        grid.weightx = 0;
        pnSearchType.add(ll, grid);
        pnSearchType.add(wordname, grid);
        grid.weightx = 1;
        grid.insets = new Insets(0, 0, 0, 0);
        panelf.setBackground(Color.yellow);
        panelf.add(pnSearchType, grid);

        grid.weightx = 0;
        grid.insets = new Insets(0, 0, 0, 0);
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
        grid.weightx = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 0;
        pnNameType.add(panelf, grid);
        grid.weighty = 1;
        pnNameType.add(panell, grid);
//        nextlist = new JButton(u.gettext("findword_", "nextlist"));
        nextlist = new JButton(u.gettext("next", "label"));
        nextlist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String ss = treeDisplay.getCurrentTopicPath();
//                if(ss==null)return;
                ss = ss.replaceAll(topicTree.ISTOPIC, "");
                int i;
                if(treeDisplay.getSelectionCount()<2){
                    for (i = 0; i < repeatsWithinCourse.length; i++) {
                        if (repeatsWithinCourse[i].equalsIgnoreCase(ss)) {
                            break;
                        }
                    }
                }
                else{
                    for (i = 0; i < repeatsWithinCourse.length; i++) {
                        jnode jn = treeDisplay.expandPath(repeatsWithinCourse[i]);                         
                        if (jn.equals(lastselnode)) {
                            break;
                        }
                    }                                      
                }
                if (i >= repeatsWithinCourse.length - 1) {
                    i = 0;
                } else {
                    i++;
                }
           //     if(treeDisplay.getSelectionCount()<2){
            //        setupTree(treeDisplay.getCourse(repeatsWithinCourse[i]));
             //       setTreeSelection(repeatsWithinCourse[i]);
               // }
              //  else{
         //           // scroll to parent first to hopefully have that visible too
                    jnode jn = lastselnode = treeDisplay.expandPath(repeatsWithinCourse[i]);
          //          TreePath pp = new TreePath(((jnode)jn.getParent()).getPath());
          //          treeDisplay.scrollPathToVisible(pp);        
                    pp = new TreePath(jn.getPath());
                    treeDisplay.setSelectionPath(pp);
                    treeDisplay.scrollPathToVisible(pp);                    
          //      }
            }
        });
        nextlist.setOpaque(true);
        nextlist.setVisible(false);
        gameicons.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!showinggames) {
                    setupgametree();
                    creategamepanel();
                    validate();
                }
            }
        });
        wordname.setToolTipText(u.gettext("findword_typein", "tooltip"));
        final JTextComponent tc = (JTextComponent) wordname.getEditor().getEditorComponent();
        tc.getDocument().addDocumentListener(new DocumentListener() {

            public void changedUpdate(DocumentEvent e) {
                changetext();
            }

            public void insertUpdate(DocumentEvent e) {
                changetext();
            }

            public void removeUpdate(DocumentEvent e) {
                changetext();
            }
        });

        indexlist_words = new JList();
        indexlist_words.setCellRenderer(new itempainter());

        indexlist_titles = new JList();
        indexlist_titles.setCellRenderer(new itempainter());
        indexlist_words.setToolTipText(u.gettext("findword_topiclabel", "tooltip"));
        indexlist_titles.setToolTipText(u.gettext("findword_topiclabel", "tooltip"));
        indexlistchanging = true;
        indexlist_words.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (indexlist_words.getModel().getSize() == 1) {
                    String s = (String) indexlist_words.getModel().getElementAt(0);
                    if (s.equals(toomany) || s.equals(nowords)) {
                        indexlist_words.clearSelection();
                    }
                }
                if (indexlist_titles.getSelectedIndex() < 0
                        && indexlist_words.getSelectedIndex() < 0) {
                    pnMiddleCol.setVisible(false);
                    pnBlankName.setVisible(true);
                } else {
                    pnMiddleCol.setVisible(true);
                    pnBlankName.setVisible(false);
                }
            }
        });
        indexlist_words.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (indexlistchanging || topics_words == null || topics_words.length == 0) {
                    return;
                }
                int sel = -1;
                for (int i = 0; i < topics_words.length; i++) {
                    int k;
                    String s = topics_words[i];
                    if (shark.production && ((k = s.indexOf('@')) >= 0)) {
                        s = s.substring(0, k);
                    }
                    if (spellchange.spellchange(s).equals((String) indexlist_words.getSelectedValue())) {
                        sel = i;
                        break;
                    }
                }
                if (sel >= 0) {
                    indexlist_titles.clearSelection();
                    String s1 = treeDisplay.getCourse(paths_words[sel]);
                    if (s1 != null) {
                        setupTree(s1);
                    }
                    doMultiCourse(false, topics_words[sel], paths_words[sel]);
                }
            }
        });
        indexlist_titles.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                if (indexlist_titles.getModel().getSize() == 1) {
                    String s = (String) indexlist_titles.getModel().getElementAt(0);
                    if (s.equals(toomany) || s.equals(nowords)) {
                        indexlist_titles.clearSelection();
                    }
                }

            }
        });
        indexlist_titles.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent e) {
                if (indexlistchanging || topics_titles == null || topics_titles.length == 0) {
                    return;
                }
                if (indexlist_titles.getSelectedIndex() < 0
                        && indexlist_words.getSelectedIndex() < 0) {
                    pnMiddleCol.setVisible(false);
                    pnBlankName.setVisible(true);
//                    pnInitBlankEnd.setVisible(true);
                } else {
                    pnMiddleCol.setVisible(true);
                    pnBlankName.setVisible(false);
//                    pnInitBlankEnd.setVisible(false);
                }

                int sel = -1;
                for (int i = 0; i < topics_titles.length; i++) {
                    int k;
                    String s = topics_titles[i];
                    if (shark.production &&   ((k = s.indexOf('@')) >= 0)) {
                        s = s.substring(0, k);
                    }
                    if (spellchange.spellchange(s).equals((String) indexlist_titles.getSelectedValue())) {
                        sel = i;
                        break;
                    }
                }
                if (sel >= 0) {
                    indexlist_words.clearSelection();
                    String s1 = treeDisplay.getCourse(paths_titles[sel]);
                    if (s1 != null) {
                        setupTree(s1);
                    }
                    doMultiCourse(true, topics_titles[sel], paths_titles[sel]);
                }
            }
        });

        wordTree = new wordlist();
        // needed so that you can do search from topic screen.
        wordTree.setModel(wordTree.model);
        wordTree.font = null;

        savephonics = wordlist.usephonics;
        savecheckboxphonics = wordlist.phonemes.isSelected();
        wordlist.usephonics = false;
        wordlist.phonemes.setSelected(false);
        // enables exiting screen via the ESC key
        wordTree.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    finish();
                }
            }
        });
        lsChooseCourse.setForeground(Color.blue);
        lsChooseCourse.setCellRenderer(new listpainter(jnode.icons[jnode.COURSE]));
        lsChooseCourse.addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                String s = String.valueOf(lsChooseCourse.getSelectedValue());
                String added[] = new String[]{};
                setupTree(s);
                for (int i = 0; i < repeats.length; i++) {
                    if (repeats[i].startsWith(s)) {
                        added = u.addString(added, repeats[i]);
                        int counter = 0;
                        int k;
                        for (k = 0; k < repeatsWithinCourse.length; k++) {
                            if (repeatsWithinCourse[k].startsWith(s)) {
                                counter++;
                            }
                        }
                        nextlist.setVisible(counter > 1);
                    }
                }
                setTreeExpand(added);
            }
        });
        lsChooseCourse.setFont(treeDisplay.getFont());
        lsChooseCourse2.setForeground(Color.blue);
        lsChooseCourse2.setCellRenderer(new listpainter(jnode.icons[jnode.COURSE]));
        lsChooseCourse2.setFont(treeDisplay.getFont()); 
        
        
        topicTree courseList = new topicTree();
        courseList.onlyOneDatabase = "*";
        
        courseList.setEditable(false);
        courseList.setRootVisible(false);
        courseList.root.setIcon(jnode.ROOTTOPICTREE);
        courseList.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent),false,
            db.TOPICTREE,true,topicTree.MODE_EXPAND_NONE,u.gettext("simprog_all","alltopics"));
        String hidden[] = settings.getUniversalHiddenCourses();
        for(int k = courseList.root.getChildCount()-1; k >=0; k--){
            String jnos = ((jnode)courseList.root.getChildAt(k)).get().trim();
            if(jnos.equals("") || (u.findString(hidden, jnos)>=0))
            courseList.root.remove(k);
        }
        courseList.model.reload();
        listofcourses = new String[]{};
        for(int k = courseList.root.getChildCount()-1; k >=0; k--){
           listofcourses = u.addString(listofcourses, ((jnode)courseList.root.getChildAt(k)).get().trim(), 0);
        }
        lsChooseCourse2.setListData(listofcourses);
        wordTree.parent = this;
        scroller = u.uScrollPane(wordTree);
        scroller.setHorizontalScrollBarPolicy(scroller.HORIZONTAL_SCROLLBAR_NEVER);
        pnBlankName.setBorder(BorderFactory.createEtchedBorder());
        pnBlankName.setBackground(Color.white);
        pnBlankGame.setBorder(BorderFactory.createEtchedBorder());
        pnBlankGame.setBackground(Color.white);        
        
        pnCourseAndTopics.setLayout(new GridBagLayout());
        pnCourseAndTopics2.setLayout(new GridBagLayout());
        grid.weighty = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.insets = new Insets(0, 0, 0, 0);
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 1;
        panell.add(paneltopics, grid);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.BOTH;
        JPanel jpchoosecourse = new JPanel(new GridBagLayout());
        jpchoosecourse.setBackground(Color.white);
        jpchoosecourse.setOpaque(true);

        jpchoosecourse.add(treelabel, grid);
        grid.insets = new Insets(0, 0, 20, 0);
        jpchoosecourse.add(lsChooseCourse, grid);
        grid.insets = new Insets(0, 0, 0, 0);
        jpchoosecourse.setBorder(BorderFactory.createBevelBorder(1));
        panChooseCourse.add(jpchoosecourse, grid);
        panChooseCourse.setVisible(false);

        
        JPanel jpchoosecourse2 = new JPanel(new GridBagLayout());
        jpchoosecourse2.setBackground(Color.yellow);
        jpchoosecourse2.setOpaque(true);
        grid.weighty = 0;
        grid.insets = new Insets(10, 10, 10, 10);
        jpchoosecourse2.add(treelabel2, grid);
        grid.weighty = 1;
        grid.insets = new Insets(0, 0, 0, 0);
        jpchoosecourse2.add(lsChooseCourse2, grid);
        jpchoosecourse2.setBorder(BorderFactory.createBevelBorder(1));
        panChooseCourse2.add(jpchoosecourse2, grid);
        panChooseCourse2.setVisible(false);        
        panChooseCourse2.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width * 7 / 20, (int)sharkStartFrame.screenSize.height/2));
        panChooseCourse2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width * 7 / 20, (int)sharkStartFrame.screenSize.height/2)); 
                  
          
        
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
        grid.weightx = 1;
        grid.gridy = -1;
        grid.gridx = 0;
        JPanel jpexitoptions2 = new JPanel(new GridBagLayout());
        grid.weighty = 0;
        grid.weighty = 1;
        jpexitoptions2.setOpaque(true);
        int basketmargin = 5;
        JButton btCancel2 = new JButton(u.gettext("cancel", "label"));
        btCancel2.setForeground(Color.white);
        grid.insets = new Insets(basketmargin, basketmargin, basketmargin, basketmargin);
        jpexitoptions2.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
        btCancel2.setBackground(Color.gray);
        btCancel2.setOpaque(true);
        btCancel2.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                thissearch.finish();
            }
        });

        grid.insets = new Insets(0, 0, 0, 0);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 0;
        grid.weightx = 1;
        pnCourseAndTopics.add(panChooseCourse, grid);
        mlabel_base mlb = new mlabel_base("");
        mlb.setText(u.gettext("findword_", "location"));
        mlb.setOpaque(false);
  //      mlabel_base mlb2 = new mlabel_base("");
  //      if(searchtype==TYPE_GETCOURSELISTNAME)
  //          mlb2.setText(u.gettext("findword_", "locationusedlists"));
  //      else
  //          mlb2.setText(u.gettext("findword_", "location2"));
  //      mlb2.setOpaque(false);
        
        
        JTextArea mlb2 = new JTextArea();
        mlb2.setEditable(false);
        mlb2.setWrapStyleWord(true);
        mlb2.setLineWrap(true);
        if(searchtype==TYPE_GETCOURSELISTNAME)
            mlb2.setText(u.convertToCR(u.gettext("findword_", "locationusedlists")));
        else
            mlb2.setText(u.gettext("findword_", "location2"));
        mlb2.setOpaque(false);
        mlb2.setFont(mlb.getFont());
       
        
        
        
        topicLabelPn = new JPanel(new GridBagLayout());
        topicLabelPn.setBackground(sharkStartFrame.topictreecolor);
        topicLabelPn2 = new JPanel(new GridBagLayout());
        topicLabelPn2.setBackground(sharkStartFrame.topictreecolor);
        grid.gridx = -1;
        grid.gridy = 0;
        topicLabelPn.add(mlb, grid);
        topicLabelPn2.add(mlb2, grid);
        grid.fill = GridBagConstraints.NONE;
        grid.weightx = 0;
        grid.insets = new Insets(5,0,5,5);
        topicLabelPn.add(nextlist, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.weightx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.gridx = 0;
        grid.gridy = -1;        
        
        
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
        grid.weightx = 1;
        grid.gridx = -1;
        grid.gridy = 0;
        pnBlankName.setVisible(true);
        pnMiddleCol.setVisible(false);

        grid.gridx = -1;
        grid.gridy = 0;

        btok.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                changed = true;
                thissearch.finish();
            }
        });
        btCancel.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(ActionEvent e) {
                thissearch.finish();
            }
        });

        jcbu = new JCheckBox(u.gettext("findword_", "phonemes"));
        jcbu.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (wordlist.usephonics) {
                    userwantphonics = false;
//                    usernonwantphonics = true;
                    wordlist.usephonics = false;
                } else {
                    userwantphonics = true;
//                    usernonwantphonics = false;
                    wordlist.usephonics = true;
                }
                changeWordTree(treeDisplay.getSelectedNode(), null);
                wordTree.newselection(lasttab);
            }
        });
        
        treeDisplay.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                jnode snode = (jnode) treeDisplay.getLastSelectedPathComponent();
                changeWordTree(snode, e);
            }
        });

        lsChooseCourse2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                doCourse2Change();
            }
        });

        if(searchtype==TYPE_GETCOURSELISTNAME){
            createcoursepanel();
        }
        else{
            createnamepanel();
        }
        

        // enables exiting screen via the ESC key
        treeDisplay.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    finish();
                }
            }
        });
        indexlist_words.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    finish();
                }
            }
        });
        indexlist_titles.addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == KeyEvent.VK_ESCAPE) {
                    finish();
                }
            }
        });
        if (programbuild != null) {
            jnode node;
            if ((node = tree.setCurrentTopicPath(programbuild.topics.getCurrentTopicPath())) != null) {
                TreePath pp;
                tree.setSelectionPath(pp = new TreePath(node.getPath()));
                tree.scrollPathToVisible(pp);
            }
        } else if (simpleprogrambuild != null) {
            jnode node;
            if ((node = tree.setCurrentTopicPath(simpleprogrambuild.topics.getCurrentTopicPath())) != null) {
                TreePath pp = new TreePath(node.getPath());
                tree.setSelectionPath(pp);
                tree.scrollPathToVisible(pp);
            }
        } else if (sharkStartFrame.mainFrame.playingGames) {
            jnode node;
            if ((node = tree.setCurrentTopicPath(sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic)) != null) {
                TreePath pp = new TreePath(node.getPath());
                tree.setSelectionPath(pp);
                tree.scrollPathToVisible(pp);
            }
        }
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowActivated(WindowEvent e) {
                videoactive = false;
            }

            public void windowDeactivated(WindowEvent e) {
                if (!videoactive && wordTree.currpic == null && !showinggames && !shark.testing) {
                    thissearch.finish();
                }
            }

            public void windowClosed(WindowEvent e) {
                if (changed) {
                    trysave();
                }
            }

            public void windowClosing(WindowEvent e) {
                running = false;
                wordlist.usephonics = savephonics;
                wordlist.phonemes.setSelected(savecheckboxphonics);
                if (changed) {
                    trysave();
                }
                thissearch.finish();
            }
        });
        setactivelist();
        newwordlist();
        newindexlist();
        if (searchtype == TYPE_GETGAMENAME) {
            setupgametree();
            creategamepanel();
        }
        validate();
        wordTree.setfont();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // enables exiting screen via the ESC key
        wordname.requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(searchtype==TYPE_GETCOURSELISTNAME){
            treeDisplay.expandAll();
        }
    }

    void changeWordTree(jnode snode, TreeSelectionEvent e){
                 pnWordList.setVisible(false);
                
                pnWordList.removeAll();
                if (e!=null && snode != null && snode.get().trim().equals("")) {
                    treeDisplay.setSelectionPath(e.getOldLeadSelectionPath());
                    return;
                }
                if (snode == null || !snode.isLeaf()) {
                    if(snode != null && !snode.isLeaf() && searchtype==TYPE_GETCOURSELISTNAME)
                        btok.setEnabled((searchtype!=TYPE_GETCOURSELISTNAME || jtp.getSelectedIndex()!=1 ) 
                                || u.findString(topicsUsedStrings, snode.get())>=0);
                    pnWordList.repaint();
                    return;
                }
                topic sel = treeDisplay.getCurrentTopic();
                if (sel == null) {
                    pnWordList.validate();
                    return;
                }
                if(jtp.getSelectedIndex()==1){
                   lastcourselist = treeDisplay.getCurrentTopicPath(treeDisplay.getSelectedNode());
                }
                pnWordList.setVisible(true);
                grid.gridy = -1;
                grid.gridx = 0;
                grid.weighty = 0;
                JPanel wordlistpan = new JPanel(new GridBagLayout());
                wordlistpan.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
      btok.setEnabled(searchtype!=TYPE_GETCOURSELISTNAME
                        || u.findString(topicsUsedStrings, snode.get())>=0);
                sharkStartFrame.currPlayTopic = sel;
                wordTree.reset();
                wordTree.setup(sel, null, true);
                wordTree.font = null;

                if (wordTree.model.getSize() > 0) {
                    if (snode.inphonics() && sel.phonics && sel.phonicsw) {
                        wordlist.usephonics = true;
                        wordlist.phonemes.setSelected(true);
                        jcbu.setSelected(true);
                    } else {
                        if (sel.phonics && sel.phonicsw) {
                            if (sel.justphonics || userwantphonics) {
                                wordlist.usephonics = true;
                                wordlist.phonemes.setSelected(true);
                                jcbu.setSelected(true);
                            }
                        } else {
                            wordlist.usephonics = false;
                            wordlist.phonemes.setSelected(false);
                            jcbu.setSelected(false);
                        }
                    }
                    JTabbedPane jtp2 = new JTabbedPane();
                    wtab1 = new JPanel(new BorderLayout());
                    wtab3 = new JPanel(new BorderLayout());
                    if (wordTree.areGroupings) {
                        if (wordTree.canextend) {
                            if (!wordTree.nostandard) {
                                jtp2.addTab(wordlisttab1, wtab1);
                            }
                            jtp2.addTab(wordlisttab3, wtab3);
                        }
                    } else {
                        if (wordTree.canextend) {
                            if (!wordTree.nostandard) {
                                jtp2.addTab(wordlisttab1, wtab1);
                            }
                            jtp2.addTab(wordlisttab3, wtab3);
                        }
                    }
                    jtp2.addChangeListener(new ChangeListener() {

                        public void stateChanged(ChangeEvent evt) {
                            JTabbedPane pane = (JTabbedPane) evt.getSource();
                            int sel = pane.getSelectedIndex();
                            if (pane.getTitleAt(sel).equals(wordlisttab1)) {
                                wtab1.add(scroller, BorderLayout.CENTER);
                                lasttab = 0;
                                wordTree.newselection(0);
                            } else if (pane.getTitleAt(sel).equals(wordlisttab3)) {
                                wtab3.add(scroller, BorderLayout.CENTER);
                                lasttab = 2;
                                wordTree.newselection(2);
                            }
                            pane.setSelectedIndex(sel);

                        }
                    });



                    boolean showtabs = (jtp2.getTabCount() > 0);
                    Component c = (showtabs ? (Component) jtp2 : (Component) scroller);
                    if (showtabs) {
                        Component c2 = jtp2.getComponentAt(0);
                        boolean found = false;
                        if (c2.equals(wtab1)) {
                            wtab1.add(scroller, BorderLayout.CENTER);
                            found = true;
                        } else if (c2.equals(wtab3)) {
                            wtab3.add(scroller, BorderLayout.CENTER);
                            found = true;
                        }
                        if (found) {
                            wordTree.newselection(0);
                        }
                        jtp2.setSelectedIndex(0);

                    }



                    grid.weightx = 1;
                    grid.weighty = 0;

                    grid.insets = new Insets(0, 0, 0, 0);
                    JPanel optionsPan = new JPanel(new GridBagLayout());
                    JPanel phonicPan = new JPanel(new GridBagLayout());
                    JPanel cbPan = new JPanel(new GridBagLayout());
                    JPanel butPan = new JPanel(new GridBagLayout());
                    grid.fill = GridBagConstraints.NONE;
                    grid.weighty = 0;
                    JPanel phonicPanInner = new JPanel(new GridBagLayout());
                    grid.insets = new Insets(wordlist.border, wordlist.border, wordlist.border, wordlist.border);



                    phonicPanInner.add(jcbu, grid);
                    grid.insets = new Insets(0, 0, 0, 0);
                    phonicPan.add(phonicPanInner, grid);
                    phonicPan.setBorder(BorderFactory.createLineBorder(Color.gray, 1));
                    grid.gridx = -1;
                    grid.gridy = 0;
                    grid.anchor = GridBagConstraints.SOUTH;
                    grid.insets = new Insets(wordlist.border, wordlist.border, wordlist.border, 0);
                    boolean showop = false;

                    if (!sel.phonics || sel.phonicsw) {
                        if (sel.phonicsw && !sel.blended && !sel.justphonics) {
                            cbPan.add(phonicPan, grid);
                            showop = true;
                        }
                        grid.anchor = GridBagConstraints.CENTER;
                        grid.insets = new Insets(wordlist.border, wordlist.border, wordlist.border, wordlist.border);
                    }
                    grid.anchor = GridBagConstraints.CENTER;
                    grid.insets = new Insets(0, 0, wordlist.border, wordlist.border);
                    grid.fill = GridBagConstraints.NONE;
                    grid.anchor = GridBagConstraints.WEST;
                    grid.gridx = -1;
                    grid.gridy = 0;
                    optionsPan.add(cbPan, grid);
                    grid.anchor = GridBagConstraints.SOUTHEAST;
                    optionsPan.add(butPan, grid);
                    grid.anchor = GridBagConstraints.CENTER;
                    grid.fill = GridBagConstraints.BOTH;
                    grid.weighty = 1;
                    grid.gridx = 0;
                    grid.gridy = -1;
                    grid.insets = new Insets(0, 0, 0, 0);
                    wordlistpan.add(c, grid);
                    grid.weighty = 0;
                    grid.insets = new Insets(wordlist.border, 0, 0, 0);
                    wordlistpan.add(optionsPan, grid);
                    grid.insets = new Insets(0, 0, 0, 0);
                    optionsPan.setVisible(showop);
                }

                GridBagConstraints grid2 = new GridBagConstraints();
                grid2.gridy = -1;
                grid2.gridx = 0;
                grid2.fill = GridBagConstraints.BOTH;
                grid2.weighty = 0;
                grid2.weightx = 1;
                wordslabel2.setText(strwordlist);                 
                pnWordList.add(wordTree.canextend ? wordslabel2 : wordslabel1, grid2);
                grid2.weighty = 30;
                pnWordList.add(wordlistpan, grid2);
                grid2.weighty = 0; //wordTree.orderCB.getLabels().length*2;
                grid2.fill = GridBagConstraints.HORIZONTAL;

//                JPanel jpexitoptions = new JPanel(new GridBagLayout());
//                bevelPanel3.add(jpexitoptions, grid2);
//                jpexitoptions.setOpaque(true);
                grid2.weighty = 1;
                grid2.weightx = 1;
                grid2.gridy = 0;
                grid2.gridx = -1;

                grid2.fill = GridBagConstraints.BOTH;
                grid2.weightx = grid2.weighty = 1;
//                int basketmargin = 5;

                grid2.insets = new Insets(0, 0, 0, 0);
                pnWordList.validate();
                pnWordList.repaint();       
    }
    
    
    void setOriTopic() {
        oriTopic = sharkStartFrame.currPlayTopic;
        oriUsePhonics = wordlist.usephonics;
        oriUsePhonemesCB = wordlist.phonemes.isSelected();
    }

    void resetOriTopic() {
        if (oriTopic != null) {
            sharkStartFrame.currPlayTopic = oriTopic;
            wordlist.usephonics = oriUsePhonics;
            wordlist.phonemes.setSelected(oriUsePhonemesCB);
            sharkStartFrame.mainFrame.wordTree.reset();
            sharkStartFrame.mainFrame.wordTree.setup(oriTopic, null, true);
            sharkStartFrame.mainFrame.wordTree.font = null;
        }
    }
    
    void doCourse2Change(){
                String s = String.valueOf(lsChooseCourse2.getSelectedValue());
                setupTree2(s);  
 
               setTreeSelection(lastcourselist);
               if(treeDisplay.getSelectionCount()==0)
                   treeDisplay.setSelectionPath(new TreePath(((jnode)treeDisplay.root.getFirstLeaf()).getPath())); 
    }

    void setTreeSelection(String s) {
        jnode node;
        if ((node = treeDisplay.setCurrentTopicPath(s)) != null) {
            TreePath pp;
            treeDisplay.setSelectionPath(pp = new TreePath(node.getPath()));
            treeDisplay.scrollPathToVisible(pp);
        }
    }
    
    void setTreeExpand(String s[]) { 
        jnode jn;
        if(s==null || s.length<=0)return;
        TreePath tps[] = new TreePath[]{};
        TreePath pp1;
        for(int i = 0; i < s.length; i++){
            jn = treeDisplay.expandPath(s[i]);
            if(jn==null)continue;
            pp1 = new TreePath(jn.getPath());
            tps = u.addTreePaths(tps, new TreePath[]{pp1});
            treeDisplay.expandPath(new TreePath(((jnode)jn.getParent()).getPath()));   
         }
         jn = lastselnode = treeDisplay.expandPath(s[0]);
         if(jn!=null){
             pp1 = new TreePath(jn.getPath());
             treeDisplay.setSelectionPath(pp1);
             treeDisplay.scrollPathToVisible(pp1);
        }
    }    
    
    
    //--------------------------------------------------------------------------------

    void setupgametree() {
        int i;
        lastnode = null;
        showinggames = true;
        gameTree = new gamestoplay();
        if (gamelists == null) {
            gamelists = new String[4][];
            for (i = 0; i < gamestoplay.categories.length; ++i) {
                gameTree.setup(sharkStartFrame.publicGameLib, true, true, gameheading, new int[]{i});
                gamelists[i] = gameTree.titlesinorder();
            }
        }
        gameTree.setup(sharkStartFrame.publicGameLib, true, true, gameheading, gamestoplay.categories);
        String list[] = new String[0];
        jnode node;
        for (node = (jnode) gameTree.root.getFirstLeaf(); node != null; node = (jnode) node.getNextLeaf()) {
            i = u.findString(sharkStartFrame.gamename, node.get());
            if (i >= 0) {
                list = u.addStringSort(list, node.get());
            }
        }
        jnode alph = new jnode(u.gettext("findword_", "Alphabetic"));
        gameTree.model.insertNodeInto(alph, gameTree.root, gameTree.root.getChildCount());
        for (i = 0; i < list.length; ++i) {
            node = new jnode(list[i]);
            node.setIcon(jnode.GAME);
            gameTree.model.insertNodeInto(node, alph, alph.getChildCount());
        }
    }
    //--------------------------------------------------------------------

    public void run() {
        int i;
        running = true;
        jnode node = (jnode) pp.getLastPathComponent(), node2, node3, parent;
        topic tt;
        String gamename = node.get(), tn;
        boolean first = true;
        ProgressMonitor pm = new ProgressMonitor(this,
                u.gettext("findword_", "scanningh", gamename), u.gettext("findword_", "scanning"), 0, 1000);
        if (node.isLeaf() && node.type == jnode.GAME) {
            topicTree tree2 = new topicTree();
            tree2.dbnames = new String[0];
            tree2.onlyOneDatabase = "*";
            tree2.root.setIcon(jnode.ROOTTOPICTREE);
            tree2.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent), false, db.TOPICTREE, true,
                    treeheading);

            int total = tree2.root.getLeafCount();
//startPR2007-03-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          for (i=0,node2 = (jnode) tree2.root.getFirstLeaf(); node2 != null; node2 = node3,++i) {
            for (i = 0, node2 = (jnode) tree2.root.getFirstLeaf(); node2 != null && running; node2 = node3, ++i) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                node3 = (jnode) node2.getNextLeaf();
                boolean tryboth = false;
                if ((tn = node2.get()).length() > 0
                        && (tt = topic.findtopic((tn.charAt(0) == topicTree.ISTOPIC.charAt(0)) ? tn.substring(1) : tn)) != null) {
                    wordlist wl = new wordlist();
                    wl.setup(tt, null, true);
                    boolean reject = false;
                    if (node.isNodeAncestor(gameTree.root.getChildAt(0))) {   // non-phonics game
                        if (tt.phonics && !tt.phonicsw || tt.justphonics || tt.phrases) {
                            reject = true;    // if just sounds, reject
                        }
                        wl.usephonics = false;
                    } else if (node.isNodeAncestor(gameTree.root.getChildAt(1))) {   // phonics sounds game
                        if (!tt.phonics || tt.phonicsw) {
                            reject = true;    // if not phonics sounds, reject
                        }
                        wl.usephonics = true;
                    } else if (node.isNodeAncestor(gameTree.root.getChildAt(2))) {   // phonics words game
                        if (!tt.phonicsw) {
                            reject = true;    // if doenn't have phonicsw words, reject
                        }
                        wl.usephonics = true;
                    } else if (gamestoplay.categories.length > 3 && node.isNodeAncestor(gameTree.root.getChildAt(3))) {   // phrases words game
                        if (!tt.phrases) {
                            reject = true;    // if doenn't have phonicsw words, reject
                        }
                    } else {                      // alphabetic - try both
                        tryboth = true;
                    }
                    if (!reject) {
                        if (tryboth) {
                            if (tt.phonics && !tt.phonicsw) {
                                if (u.findString(gamelists[1], gamename) < 0) {
                                    reject = true;
                                } else {
                                    wl.usephonics = true;
                                    reject = !tt.testWord(gamename);
                                }
                            } else if (tt.phonicsw && tt.justphonics) {
                                if (u.findString(gamelists[2], gamename) < 0) {
                                    reject = true;
                                } else {
                                    wl.usephonics = true;
                                    reject = !tt.testWord(gamename);
                                }
                            } else if (tt.phrases) {
                                if (u.findString(gamelists[3], gamename) < 0) {
                                    reject = true;
                                } else {
                                    wl.usephonics = false;
                                    reject = !tt.testWord(gamename);
                                }
                            } else if (!tt.phonics) {
                                if (u.findString(gamelists[0], gamename) < 0) {
                                    reject = true;
                                } else {
                                    wl.usephonics = false;
                                    reject = !tt.testWord(gamename);
                                }
                            } else {
                                wl.usephonics = false;
                                if (u.findString(gamelists[0], gamename) < 0 || !tt.testWord(gamename)) {
                                    if (!tt.phonicsw) {
                                        reject = true;
                                    } else {
                                        wl.usephonics = true;
                                        if (u.findString(gamelists[2], gamename) < 0 || !tt.testWord(gamename)) {
                                            reject = true;
                                        }
                                    }
                                }
                            }
                        } else if (!tt.testWord(gamename)) {
                            reject = true;
                        }
                    }
                    if (!reject && tt.notgames != null) {                // start rb 20/12/07
                        if (u.findString(tt.notgames, gamename) >= 0) {
                            reject = true;
                        } else {
                            jnode jj, pa;
                            jjloop:
                            for (jj = (jnode) gameTree.root.getFirstLeaf(); jj != null; jj = (jnode) jj.getNextLeaf()) {
                                if (jj.get().equals(gamename)) {
                                    pa = (jnode) jj.getParent();
                                    while (pa != null) {
                                        if (u.findString(tt.notgames, pa.get()) >= 0) {
                                            reject = true;
                                            break jjloop;
                                        }
                                        pa = (jnode) pa.getParent();
                                    }
                                }
                            }
                        }
                    }                                                   // end rb 20/12/07
                    if (reject) {
                        parent = (jnode) node2.getParent();
                        tree2.model.removeNodeFromParent(node2);
                        while (parent != tree2.root && parent.isLeaf()) {
                            jnode p2 = (jnode) parent.getParent();
                            tree2.model.removeNodeFromParent(parent);
                            parent = p2;
                        }
                    } else if (first) {
                        first = false;
                    }
                    if (pm.isCanceled()) {
                        return;
                    }
                    pm.setProgress(1000 * i / total);
                }
            }
            if (!running) {
                pm.close();
                thread = null;
                return;
            }
            treeDisplay.model.setRoot(treeDisplay.root = tree2.root);
            treeDisplay.expandAll = true;
            treeDisplay.model.reload();
            if (treeDisplay.root != null) {
                treeDisplay.setSelectionPath(new TreePath(treeDisplay.root.getFirstLeaf().getPath()));
            }
            pm.close();
            pnFoundLists.setVisible(true);
            pnBlankGame.setVisible(false);
            if (showingicons) {
                gamepanel.startrunning();
            }
            thread = null;
            validate();
        }
    }

    //------------------------------------------------------------
    void settitle() {
//        if (programbuild != null) {
//            setTitle(u.gettext("findword_title", "label1"));
//        }
//        if (simpleprogrambuild != null) {
//            setTitle(u.gettext("findword_title", "label1"));
//        } else if (sharkStartFrame.mainFrame.playingGames) {
//            setTitle(u.gettext("findword_title", "label2"));
//        } else {
//            setTitle(u.gettext("findword_title", "label3"));
//        }
        setTitle(u.gettext("findword_title", "label"));
    }
    //------------------------------------------------------------

    void trysave() {
        if (programbuild != null) {
            setOtherTree(programbuild.topics);
        } else if (simpleprogrambuild != null) {
            setOtherTree(simpleprogrambuild.topics);
        } else if (searchtype == TYPE_GETCOURSELISTNAME) {
            jnode sel = (jnode) treeDisplay.getSelectedNode();
            if (sel == null) {
                return;
            }
            ((student.showStudentRecord) parent).doFilter(treeDisplay.getSelectedNode().get(), TYPE_GETLISTNAME);
        } else if (sharkStartFrame.mainFrame.playingGames) {
            setMainTree();
        } else if (sharkStartFrame.mainFrame.topicTreeList != null) {
            setOtherTree(sharkStartFrame.mainFrame.topicTreeList, u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]));
        }
    }
    //----------------------------------------------------------

    synchronized void changetext() {
        javax.swing.Timer tt = new javax.swing.Timer(0,
                new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        changetext1();
                    }
                });
        tt.setRepeats(false);
        tt.start();
    }
    //----------------------------------------------------------

    synchronized void changetext1() {
        String newtext = spellchange.reverse(wordname.getText());
        while (newtext.length() > 0
                && newtext.charAt(newtext.length() - 1) == ' ') {
            newtext = newtext.substring(0, newtext.length() - 1);
        }
        while (newtext.length() > 0
                && newtext.charAt(0) == ' ') {
            newtext = newtext.substring(1);
        }
        if (!newtext.equalsIgnoreCase(wordtext)) {
            wordtext = newtext.toLowerCase();
            lbWord.setText("'" + wordtext + "'");
            lbTitle.setText("'" + wordtext + "'");
            newwordlist();
            newindexlist();
            student.setOption(wordtextname, wordtext);
            if(newtext.trim().equals("")){
                    pnMiddleCol.setVisible(false);
                    pnBlankName.setVisible(true);
            }
        }
    }
    //--------------------------------------------------------------------

    void buildtopiclist_titles(searchclass[] list, int sel[]) {
        int i, j, k, tot = 0, ints[];
        String s = null;
        if (sel == null) {
            sel = u.select(list.length, list.length);
        }
        for (i = 0; i < sel.length; ++i) {
            tot += list[sel[i]].refs.length;
        }
        ints = new int[tot];
        for (i = 0, tot = 0; i < sel.length; ++i) {
            loop:
            for (j = 0; j < list[sel[i]].refs.length; ++j) {
                ints[tot] = list[sel[i]].refs[j];
                for (k = 0; k < tot; ++k) {
                    if (ints[k] == ints[tot]) {
                        continue loop;
                    }
                }
                ++tot;
            }
        }
        topics_titles = new String[tot];
        paths_titles = new String[tot];
        for (i = 0; i < tot; ++i) {
            s = ax[ints[i] >> 24].index[ints[i] & 0x00ffffff].v;
            topics_titles[i] = s;
            paths_titles[i] = getTopicPath(ints[i]);
        }
    }

    void buildtopiclist_words(searchclass[] list, int sel[]) {
        int i, j, k, tot = 0, ints[];
        String s = null;
        if (list == null) {
            return;
        }
        if (sel == null) {
            sel = u.select(list.length, list.length);
        }
        for (i = 0; i < sel.length; ++i) {
            tot += list[sel[i]].refs.length;
        }
        ints = new int[tot];
        for (i = 0, tot = 0; i < sel.length; ++i) {
            loop:
            for (j = 0; j < list[sel[i]].refs.length; ++j) {
                ints[tot] = list[sel[i]].refs[j];
                for (k = 0; k < tot; ++k) {
                    if (ints[k] == ints[tot]) {
                        continue loop;
                    }
                }
                ++tot;
            }
        }
        topics_words = new String[tot];
        paths_words = new String[tot];
        for (i = 0; i < tot; ++i) {
            s = ax[ints[i] >> 24].index[ints[i] & 0x00ffffff].v;
            topics_words[i] = s;
            paths_words[i] = getTopicPath(ints[i]);
        }
    }
    //-------------------------------------------------------------------

    String[] addcourses_titles() {
        titles_duplicates = new ArrayList();
        String ss[] = new String[0], last = "", s;
        for (int i = 0; i < topics_titles.length; ++i) {
            if (paths_titles[i] == null) {
                continue;
            }
            if (treeDisplay.getCourse(paths_titles[i]) == null) {
                continue;
            }
            s = paths_titles[i];

            String topicname = topics_titles[i];
            int k;
            if ( shark.production && ((k = topicname.indexOf('@')) >= 0)) {
                topicname = topicname.substring(0, k);
            }
            doDuplicateTitles(topicname, s);
            if (u.findString(ss, topicname) >= 0) {
                continue;
            }
            ss = u.addString(ss, topicname);
        }
        for (int i = titles_duplicates.size() - 1; i >= 0; i--) {
            String arr[] = (String[]) titles_duplicates.get(i);
            if (arr.length < 3) {
                titles_duplicates.remove(i);
            }
        }
        return spellchange.spellchange(ss);
    }

    String[] addcourses_words() {
        String ss[] = new String[0], last = "", s;
        words_duplicates = new ArrayList();
        for (int i = 0; i < topics_words.length; ++i) {
            if (paths_words[i] == null) {
                continue;
            }
            if (treeDisplay.getCourse(paths_words[i]) == null) {
                continue;
            }
            s = paths_words[i];

            String topicname = topics_words[i];
            int k;
            if (shark.production && ((k = topicname.indexOf('@')) >= 0)) {
                topicname = topicname.substring(0, k);
            }

            doDuplicateWords(topicname, s);
            if (u.findString(ss, topicname) >= 0) {
                continue;
            }
            ss = u.addString(ss, topicname);
        }
        for (int i = words_duplicates.size() - 1; i >= 0; i--) {
            String arr[] = (String[]) words_duplicates.get(i);
            if (arr.length < 3) {
                words_duplicates.remove(i);
            }
        }
        return spellchange.spellchange(ss);
    }

    void doDuplicateWords(String topic, String path) {
        for (int i = 0; i < words_duplicates.size(); i++) {
            String s[] = (String[]) words_duplicates.get(i);
            if (s[0].equals(topic)) {
                if (u.findString(s, path) < 0) {
                    s = u.addString(s, path);
                    words_duplicates.set(i, s);
                }
                return;
            }
        }
        words_duplicates.add(new String[]{topic, path});
    }

    void doDuplicateTitles(String topic, String path) {
        for (int i = 0; i < titles_duplicates.size(); i++) {
            String s[] = (String[]) titles_duplicates.get(i);
            if (s[0].equals(topic)) {
                if (u.findString(s, path) < 0) {
                    s = u.addString(s, path);
                    titles_duplicates.set(i, s);
                }
                return;
            }
        }
        titles_duplicates.add(new String[]{topic, path});
    }

    void setupTree(String course) {
        treeDisplay.onlyOneDatabase = "*";
        treeDisplay.onlyshowcourse = course;
        treeDisplay.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent), false,
                db.TOPICTREE, true, topicTree.MODE_NO_COMPRESS_HEADING, "");
        treeDisplay.model.reload();
        pnCourseAndTopics.validate();
        pnCourseAndTopics.repaint();
    }
    
    void setupTree2(String course) {
        treeDisplay.onlyOneDatabase = "*";
        treeDisplay.onlyshowcourse = course;
        if(searchtype == findword.TYPE_GETCOURSELISTNAME && 
                treeDisplay.onlyNamedLists == null && topicsUsed!=null){
            treeDisplay.onlyNamedLists = new String[]{};
            for(int i = 0; i < topicsUsed.length; i++){
                if(u.findString(treeDisplay.onlyNamedLists, topicsUsed[i])<0)
                    treeDisplay.onlyNamedLists = u.addString(treeDisplay.onlyNamedLists, topicsUsed[i]);
            }
        }  
        treeDisplay.setup(sharkStartFrame.mainFrame.searchList2(sharkStartFrame.mainFrame.currStudent), false,
                db.TOPICTREE, true, topicTree.MODE_NO_COMPRESS_HEADING, "");
        treeDisplay.onlyNamedLists = null;
        treeDisplay.model.reload();
        pnCourseAndTopics2.validate();
        pnCourseAndTopics2.repaint();
    }    
    //--------------------------------------------------------------------

    void setMainTree() {
        topicTree exittree;
        exittree = treeDisplay;
        jnode sel = (jnode) exittree.getSelectedNode();
        boolean savegameicons = sharkStartFrame.mainFrame.gameicons;  // rb 1/11/07
        if (sel == null) {
            return;
        }
        if (sharkStartFrame.mainFrame.gameicons && !sel.isLeaf()) {
            return;
        }
        String s = exittree.getCurrentTopicPath();
        if (sharkStartFrame.mainFrame.topicList.getCurrentTopic() != null
                && sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic != null
                && s.equals(sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic)) {
            return;
        }
        sharkStartFrame.mainFrame.studentList[sharkStartFrame.mainFrame.currStudent].currTopic = s;

        String course = exittree.getCourse(s);
        sharkStartFrame.mainFrame.setTopicList(course, sel.get());
        sharkStartFrame.mainFrame.setCourseListSelection(course);


        jnode node = sharkStartFrame.mainFrame.topicList.setCurrentTopicPath(s);
        sharkStartFrame.mainFrame.topicList.setSelectionPath(new TreePath(node.getPath()));
        if (sel.isLeaf()) {
            sharkStartFrame.mainFrame.usingsuperlist = null;
            sharkStartFrame.mainFrame.gameicons = savegameicons;  // rb 1/11/07
            sharkStartFrame.mainFrame.setupGametree();
        }
    }
    //--------------------------------------------------------------------

    void setOtherTree(topicTree t) {
        setOtherTree(t, null);
    }

    void setOtherTree(topicTree t, String startatnode) {
        jnode sel = (jnode) treeDisplay.getSelectedNode();
        if (sel == null) {
            return;
        }

        String s = treeDisplay.getCurrentTopicPath();
        String course = treeDisplay.getCourse(s);
        if (parent instanceof simpleprogram) {
            ((simpleprogram) parent).setupTree(course, sel);
            ((simpleprogram) parent).setCourseListSelection(course);
        }
        jnode node = t.setCurrentTopicPath(s, startatnode);
        if (node != null) {
            TreePath tp;
            t.setSelectionPath(tp = new TreePath(node.getPath()));
            t.scrollPathToVisible(tp);
        }
    }
    //-------------------------------------------------------------------------

    void setactivelist() {
        ax = ix;
    }
    //-----------------------------------------------------------------------

    void newwordlist() {
        if(searchtype == TYPE_GETGAMENAME)return;
        int i, j, k, m, tot = 0;
        Point p[] = new Point[ax.length];
        if (wordtext.trim().equals("")) {
            wordname.setItems(new String[]{});
            return;
        }
        for (i = 0; i < ax.length; ++i) {
            p[i] = extract(ax[i].words, wordtext);
            tot += p[i].y - p[i].x;
        }
        if (tot <= 0) {
            selectedwords = null;
            wordname.setItems(new String[]{});
            indexlist_words.setListData(new String[]{nowords});
        } else if (tot > MAXFIND) {
            selectedwords = null;
            wordname.setItems(new String[]{});
        }
        if (tot > 0) {
            selectedwords = new searchclass[tot];
            k = 0;
            for (i = 0; i < ax.length; ++i) {
                for (j = p[i].x; j < p[i].y; ++j) {
                    selectedwords[k] = new searchclass();
                    selectedwords[k].v = ax[i].words[j].v;
                    selectedwords[k].refs = new int[ax[i].words[j].refs.length];
                    System.arraycopy(ax[i].words[j].refs, 0, selectedwords[k].refs, 0, ax[i].words[j].refs.length);
                    for (m = 0; m < selectedwords[k].refs.length; ++m) {
                        selectedwords[k].refs[m] |= (i << 24);
                    }
                    ++k;
                }
            }
            if (ax.length > 1) {
                selectedwords = sortcombine2(selectedwords, tot);
            }
            wordname.setItems(spellchange.spellchange(listv(selectedwords)));
            int f = -1;
            for (i = 0; i < selectedwords.length; i++) {
                if (selectedwords[i].v.equals(wordtext)) {
                    f = i;
                    break;
                }
            }
            if (f >= 0) {
                buildtopiclist_words(selectedwords, new int[]{f});
                indexlist_words.setListData(addcourses_words());
            } else {
                indexlist_words.setListData(new String[]{nowords});
                selectedwords = null;
            }
        }
        getContentPane().repaint();
    }
    //------------------------------------------------

    String[] listv(searchclass ll[]) {
        int len = ll.length;
        String s[] = new String[len];
        for (short i = 0; i < len; ++i) {
            s[i] = ll[i].v;
        }
        return s;
    }
    //-----------------------------------------------------------------------

    void newindexlist() {
        if (wordtext.length() == 0) {
            if (paneltopics.getComponentCount() > 0) {
                paneltopics.removeAll();
                paneltopics.validate();
                paneltopics.repaint();
            }
            return;
        }
        indexlistchanging = true;
        if (wordtext.indexOf(' ') < 0) {
            newindexlist2(wordtext, MAXFIND);
        } else {
            String text[] = u.splitString(wordtext, ' ');
            newindexlist2(text[0], 100000);
            String t2[] = topics_titles;
            String p2[] = paths_titles;
            /*
            for (short i = 1; i < text.length; ++i) {
                newindexlist2(text[i], 100000);
                loopj:
                for (short j = 0; j < t2.length; ++j) {
                    for (short k = 0; k < topics_titles.length; ++k) {
                        if (t2[j].equals(topics_titles[k]) && p2[j].equals(paths_titles[k])) {
                            continue loopj;
                        }
                    }
                    t2 = u.removeString(t2, j);
                    p2 = u.removeString(p2, j);
                    --j;
                }
            }
             *
             */
            for(int i = t2.length-1; i >= 0; i--){
                if(t2[i].indexOf(wordtext)<0){
                    t2 = u.removeString(t2, i);
                    p2 = u.removeString(p2, i);
                }
            }
            
            topics_titles = t2;
            paths_titles = p2;
        }
        int noneselected = 0;
        int tot = topics_titles.length;
        if (tot <= 0) {
            noneselected++;
            indexlist_titles.setListData(new String[]{nowords});
            indexlist_titles.setForeground(Color.red);
        } else if (tot > MAXFIND) {
            noneselected++;
            indexlist_titles.setListData(new String[]{toomany});
            indexlist_titles.setForeground(Color.red);
        } else {
            indexlist_titles.setForeground(Color.black);
            indexlist_titles.setListData(addcourses_titles());
        }
        tot = selectedwords == null ? 0 : topics_words.length;
        if (noneselected > 1) {
            selectedindex = null;
        }
        if (paneltopics.getComponentCount() == 0) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.gridy = 0;
            gbc.gridx = -1;

            JPanel pnW = new JPanel(new GridBagLayout());
            lbWord.setForeground(Color.red);
            JLabel lbWord2 = new JLabel(" " + u.gettext("findword_", "asaword"));

            pnW.setBackground(sharkStartFrame.topictreecolor);
            pnW.setOpaque(true);

            gbc.insets = new Insets(5, 10, 5, 0);
            pnW.add(lbWord, gbc);
            gbc.insets = new Insets(5, 0, 5, 10);
            pnW.add(lbWord2, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);


            JPanel pnT = new JPanel(new GridBagLayout());
            lbTitle.setForeground(Color.red);
            JLabel lbTitle2 = new JLabel(" " + u.gettext("findword_", "inthetitle"));
            gbc.insets = new Insets(5, 10, 5, 0);
            pnT.add(lbTitle, gbc);
            gbc.insets = new Insets(5, 0, 5, 10);
            pnT.add(lbTitle2, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            pnT.setBackground(sharkStartFrame.topictreecolor);
            pnT.setOpaque(true);

            gbc.fill = GridBagConstraints.VERTICAL;
            gbc.anchor = GridBagConstraints.WEST;
            gbc.weightx = 0;
            gbc.weighty = 1;
            gbc.gridy = 0;
            gbc.gridx = -1;
            JPanel pnWords = new JPanel(new GridBagLayout());
            pnWords.setBackground(Color.orange);
            pnWords.add(pnW, gbc);
            gbc.insets = new Insets(0, 0, 0, 10);
            pnWords.add(u.infoLabel(u.gettext("findword_", "asword_tooltip"), Color.orange), gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
            JPanel blankpn1 = new JPanel();
            blankpn1.setBackground(Color.white);
            blankpn1.setOpaque(true);
            pnWords.add(blankpn1, gbc);
            gbc.fill = GridBagConstraints.VERTICAL;

            gbc.weightx = 0;
            JPanel pnTitle = new JPanel(new GridBagLayout());
            pnTitle.setBackground(Color.orange);
            pnTitle.add(pnT, gbc);
            gbc.insets = new Insets(0, 0, 0, 10);
            pnTitle.add(u.infoLabel(u.gettext("findword_", "astitle_tooltip"), Color.orange), gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.BOTH;
            JPanel blankpn2 = new JPanel();
            blankpn2.setBackground(Color.white);
            blankpn2.setOpaque(true);
            pnTitle.add(blankpn2, gbc);


            gbc.fill = GridBagConstraints.BOTH;
            gbc.weightx = 1;
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = -1;
            gbc.anchor = GridBagConstraints.NORTH;

            gbc.gridx = 0;
            gbc.gridy = -1;
            JPanel totPan = new JPanel(new GridBagLayout());
            totPan.setBackground(Color.white);
            totPan.setOpaque(true);
            totPan.add(pnWords, gbc);
            gbc.insets = new Insets(0, 0, 10, 0);
            totPan.add(indexlist_words, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            totPan.add(pnTitle, gbc);
            totPan.add(indexlist_titles, gbc);
            gbc.weighty = 1;
            JPanel spacefiller = new JPanel(new GridBagLayout());
            spacefiller.setBackground(Color.white);
            totPan.add(spacefiller, gbc);
            gbc.weighty = 0;

            gbc.anchor = GridBagConstraints.CENTER;
            JScrollPane ilscroll = u.uScrollPane(totPan);
            ilscroll.setBorder(BorderFactory.createEmptyBorder());
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = -1;
            gbc.insets = new Insets(0, 0, 10, 0);
            paneltopics.add(topiclabel, gbc);
            gbc.insets = new Insets(0, 0, 0, 0);
            gbc.weighty = 1;
            paneltopics.setBackground(Color.white);
            paneltopics.setOpaque(true);
            paneltopics.add(ilscroll, gbc);
            paneltopics.validate();
            paneltopics.repaint();
        }
        paneltopics.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, 2));
        topiclabel.setBackground(Color.white);
        indexlist_words.clearSelection();
        indexlist_titles.clearSelection();
        indexlistchanging = false;
    }

    void doMultiCourse(boolean titles, String s, String path) {
        int p;
        if (shark.production &&  ((p = s.indexOf('@')) >= 0)) {
            s = s.substring(0, p);
        }
        repeats = new String[]{};
        repeatsWithinCourse = new String[]{};
        if (titles) {
            for (int i = 0; i < titles_duplicates.size(); i++) {
                String ss[] = (String[]) titles_duplicates.get(i);
                if (ss[0].equals(s)) {
                    repeats = new String[ss.length - 1];
                    System.arraycopy(ss, 1, repeats, 0, repeats.length);
                    break;
                }
            }
        } else {
            for (int i = 0; i < words_duplicates.size(); i++) {
                String ss[] = (String[]) words_duplicates.get(i);
                if (ss[0].equals(s)) {
                    repeats = new String[ss.length - 1];
                    System.arraycopy(ss, 1, repeats, 0, repeats.length);
                    break;
                }
            }
        }
        String r[] = new String[]{};
        String r2[] = new String[]{};
        String homeCourse = treeDisplay.getCourse(sharkStartFrame.mainFrame.topicList.getCurrentTopicPath());
        String currPath = null;
        if (repeats.length > 0) {
            for (int i = 0; i < repeats.length; i++) {
                s = treeDisplay.getCourse(repeats[i]);
                if (s == null) {
                    continue;
                }
                int k;
                if ((k = u.findString(r, s)) < 0) {
                    if (homeCourse != null && homeCourse.equals(s)) {
                        currPath = repeats[i];  //homeCourse nullpointer
                    }
                    r = u.addString(r, s);
                    r2 = u.addString(r2, repeats[i]);
                } else {
                    if (u.findString(repeatsWithinCourse, r2[k]) < 0) {
                        repeatsWithinCourse = u.addString(repeatsWithinCourse, r2[k]);
                    }
                    repeatsWithinCourse = u.addString(repeatsWithinCourse, repeats[i]);
                }
            }
        }
        if (r.length > 1) {
            lsChooseCourse.setListData(r);
            if (currPath != null) {
                lsChooseCourse.setSelectedValue(treeDisplay.getCourse(currPath), true);
            } else {
                lsChooseCourse.setSelectedValue(treeDisplay.getCourse(path), true);
            }
            panChooseCourse.setVisible(true);
        } else {
            panChooseCourse.setVisible(false);
        }

        String path2;
        String course2;
        if (currPath != null) {
            setTreeSelection(currPath);
            path2 = currPath;
        } else {
            setTreeSelection(path);
            path2 = path;
        }
        if(repeatsWithinCourse!=null)
            setTreeExpand(repeatsWithinCourse);
        course2 = treeDisplay.getCourse(path2);


        nextlist.setVisible(repeatsWithinCourse.length > 0 && repeatsWithinCourse[0].startsWith(course2));
    }

    //-----------------------------------------------------------------------
    void newindexlist2(String wordtext, int maxfind) {
        int i, j, k, m, n, tot = 0;
        Point p[] = new Point[ax.length];
        for (i = 0; i < ax.length && tot <= maxfind; ++i) {
            p[i] = extract(ax[i].keywords, wordtext);
            for (j = p[i].x; j < p[i].y; ++j) {
                tot += ax[i].keywords[j].refs.length;
            }
        }
        if (tot <= 0) {
            topics_titles = new String[0];
            paths_titles = new String[0];
            return;
        } else if (tot > maxfind) {
            topics_titles = new String[maxfind + 1];
            return;
        } else {
            selectedindex = new searchclass[tot];
            k = 0;
            for (i = 0; i < ax.length; ++i) {
                for (j = p[i].x; j < p[i].y; ++j) {
                    for (m = 0; m < ax[i].keywords[j].refs.length; ++m) {
                        selectedindex[k] = ax[i].index[ax[i].keywords[j].refs[m]];
                        selectedindex[k] = new searchclass();
                        selectedindex[k].v = ax[i].keywords[j].v;
                        selectedindex[k].refs = new int[ax[i].keywords[j].refs.length];
                        System.arraycopy(ax[i].keywords[j].refs, 0, selectedindex[k].refs, 0, ax[i].keywords[j].refs.length);
                        for (n = 0; n < selectedindex[k].refs.length; ++n) {
                            selectedindex[k].refs[n] |= (i << 24);
                        }
                        ++k;
                    }
                }
            }
            // this gives title info
            buildtopiclist_titles(selectedindex, null);

        }
    }
    //------------------------------------------------------------
    // return new Point(start,end)

    Point extract(searchclass sc[], String name) {
        if (sc.length == 0) {
            return new Point(0, 0);
        }
        int i, j, k, low = 0, high = sc.length - 1, mid = (high + low) / 2;
        int len = name.length();
        String s2;
        while (low <= high) {
            i = (s2 = sc[mid].v).compareTo(name);
            if (i < 0) {
                low = mid + 1;
            } else if (i == 0 || s2.length() > len && s2.substring(0, len).equals(name)) {
                break;
            } else {
                high = mid - 1;
            }
            mid = (high + low) / 2;
        }
        if (sc[mid].v.length() < len || !sc[mid].v.substring(0, len).equals(name)) {
            return new Point(mid, mid);
        }
        for (i = mid - 1; i >= 0 && sc[i].v.compareTo(name) >= 0 && mid - i <= 101; --i);
        for (j = mid + 1; j < sc.length && sc[j].v.length() >= len
                && sc[j].v.substring(0, len).equals(name) && j - i <= 101; ++j);
        ++i;
        return new Point(i, j);
    }
    //------------------------------------------------------

    indexclass setupwords(jnode node) {
        indexclass ic = new indexclass();
        short sep, i, j, k;
        int totindex = -1;
        int totwords = 0;
        int totkeywords = 0;
        ic.words = new searchclass[200];
        ic.keywords = new searchclass[200];
        ic.index = new searchclass[200];

        totindex = addchildren(node, ic, totindex);

        ic.index = newarray(ic.index, ++totindex);       // resize index array
        ic.name = node.get();
        for (i = 0; i < totindex; ++i) {  // extract words and keywords
            if (ic.index[i].refs[0] == i) {
                totwords = getwordlist(ic.index[i].v, ic, totwords, i);
            }
            totkeywords = addkeywords(ic.index[i].v, ic, totkeywords, i);
        }
        ic.words = sortcombine(ic.words, totwords);
        ic.keywords = sortcombine(ic.keywords, totkeywords);
        return ic;
    }
    //------------------------------------------------------------

    searchclass[] sortcombine(searchclass w[], int tot) {
        int out[] = u.select(tot, tot), newrefs[];
        int i, j, k, lost = 0, tot2;
        Arrays.sort(w, 0, tot, new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((searchclass) o1).v.compareTo(((searchclass) o2).v);
            }
        });
        for (i = 0; i < tot - 1; ++i) {
            tot2 = 0;
            for (j = i + 1; j < tot && w[out[i]].v.equals(w[out[j]].v); ++j);
            if (j > i + 1) {
                k = w[out[i]].refs[0];
                w[out[i]].refs = new int[j - i];
                w[out[i]].refs[0] = k;
                for (k = i + 1; k < j; ++k) {
                    w[out[i]].refs[k - i] = w[out[k]].refs[0];
                    out[k] = -1;
                    ++lost;
                }
                i = j - 1;
            }
        }
        searchclass ret[] = new searchclass[tot - lost];
        for (i = j = 0; i < tot; ++i) {
            if (out[i] >= 0) {
                ret[j++] = w[out[i]];
            }
        }
        return ret;
    }

    //------------------------------------------------------------
    searchclass[] sortcombine2(searchclass w[], int tot) {
        int out[] = u.select(tot, tot), tempi[];
        int i, j, k, lost = 0, tot2, tot3;
        Arrays.sort(w, 0, tot, new Comparator() {

            public int compare(Object o1, Object o2) {
                return ((searchclass) o1).v.compareTo(((searchclass) o2).v);
            }
        });
        for (i = 0; i < tot - 1; ++i) {
            tot2 = 0;
            for (j = i + 1; j < tot && w[out[i]].v.equals(w[out[j]].v); ++j);
            if (j > i + 1) {
                tot3 = 0;
                for (k = i; k < j; ++k) {
                    tot3 += w[out[k]].refs.length;
                }
                tempi = new int[tot3];
                tot3 = 0;
                for (k = i; k < j; ++k) {
                    System.arraycopy(w[out[k]].refs, 0, tempi, tot3, w[out[k]].refs.length);
                    tot3 += w[out[k]].refs.length;
                    if (k > i) {
                        out[k] = -1;
                        ++lost;
                    }
                }
                w[out[i]].refs = tempi;
                i = j - 1;
            }
        }
        searchclass ret[] = new searchclass[tot - lost];
        for (i = j = 0; i < tot; ++i) {
            if (out[i] >= 0) {
                ret[j++] = w[out[i]];
            }
        }
        return ret;
    }

    //---------------------------------------------------------------------
    searchclass[] newarray(searchclass old[], int tot) {
        searchclass newa[] = new searchclass[tot];
        System.arraycopy(old, 0, newa, 0, Math.min(tot, old.length));
        return newa;
    }
    //---------------------------------------------------------------------

    int addchildren(jnode node, indexclass ic, int totindex) {
        int i, tot = 0, parent = totindex;
        jnode node2, lastnode;
        for (node2 = (jnode) node.getFirstLeaf(), lastnode = (jnode) node.getLastLeaf();
                node2 != null;
                node2 = (jnode) node2.getNextLeaf()) {
            if (ic.index.length <= ++totindex) {
                ic.index = newarray(ic.index, totindex + 200);
            }
            ic.index[totindex] = new searchclass();
            ic.index[totindex].v = node2.get();
            ic.index[totindex].refs = new int[]{totindex};
            if (node2 == lastnode) {
                break;
            }
        }

        for (i = parent + 1; i <= totindex; ++i) {
            if (ic.index[i].refs[0] == i) {
                ++tot;
            }
        }
        if (parent >= 0) {
            ic.index[parent].refs = new int[tot];
            for (i = parent + 1, tot = 0; i <= totindex; ++i) {
                if (ic.index[i].refs[0] == i) {
                    ic.index[parent].refs[tot++] = i;
                }
            }
        }
        return totindex;
    }
    //---------------------------------------------------------------------

    int getwordlist(String name, indexclass ic, int totwords, int indexnum) {
        short sep = (short) name.indexOf(topicTree.SEPARATOR);
        int i;
        topic top;
        if (sep >= 0) {
            if (name.charAt(0) == topicTree.ISTOPIC.charAt(0)) {
                top = new topic(name.substring(1, sep),
                        name.substring(sep + 1), null, null);
            } else {
                top = new topic(name.substring(0, sep),
                        name.substring(sep + 1), null, null);
            }
        } else {
            top = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), name, null, null);
        }
        if (top == null) {
            return 0;
        }

        word w[] = top.getAllWordsNoSent();
        for (i = 0; i < w.length; ++i) {
            if (totwords >= ic.words.length) {
                ic.words = newarray(ic.words, totwords + 400);
            }
            ic.words[totwords] = new searchclass();
            ic.words[totwords].v = w[i].v().toLowerCase();
            ic.words[totwords++].refs = new int[]{indexnum};
        }
        return totwords;
    }
    //---------------------------------------------------------------------

    int addkeywords(String v, indexclass ic, int totkeywords, int indexnum) {
        int i, j;
        String s;

        for (i = 0; i < v.length(); ++i) {
            if (u.lettershyphen.indexOf(v.charAt(i)) >= 0) {
                for (j = i + 1; j < v.length() && u.lettershyphenslash.indexOf(v.charAt(j)) >= 0; ++j);
                s = v.substring(i, j).toLowerCase();
                if ((j > i + 1 || v.charAt(i) != '-')
                        && ignorekeywords.indexOf("/" + s + "/") < 0) {
                    if (totkeywords >= ic.keywords.length) {
                        ic.keywords = newarray(ic.keywords, totkeywords + 400);
                    }
                    ic.keywords[totkeywords] = new searchclass();
                    ic.keywords[totkeywords].v = s;
                    ic.keywords[totkeywords++].refs = new int[]{indexnum};
                }
                if (v.charAt(i) != '-') {
                    i = j;
                }
            }
        }
        return totkeywords;
    }

    public String getTopicPath(int ref) {
        int top = ref >> 24;
        int lookfor = ref & 0x00ffffff;
        jnode node = (jnode) tree.find(ax[top].name).getFirstLeaf();
        for (int i = 0; i < lookfor; ++i) {
            node = (jnode) node.getNextLeaf();
        }
        if (node != null) {
            return tree.getCurrentTopicPath(node);
        } else {
            return null;
        }
    }
    //-----------------------------------------------------------

    void finish() {
        resetOriTopic();
        if (showingicons) {
            gamepanel.stop();
        }
        dispose();
    }
    //-----------------------------------------------------------

    void createnamepanel() {
        btok.setEnabled(false);
        vidpn.setVisible(true);
        pnVidSpacer.setVisible(vidpn.isVisible()); 
        coursePn.removeAll();
        gamesPn.removeAll();
        namePn.removeAll();
        pnMiddleCol.removeAll();
        grid.insets = new Insets(0, 0, 0, 0);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 0;
        grid.weightx = 1;
        pnCourseAndTopics.add(panChooseCourse, grid);
        pnCourseAndTopics.add(topicLabelPn, grid);
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
    //    scroller2.setVisible(true);
        pnCourseAndTopics.add(scroller2, grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 0;
        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 1;
        grid.weighty = 1; 
        pnMiddleCol.add(pnCourseAndTopics, grid);
        grid.weightx = 0;
        pnMiddleCol.add(pnWordList, grid);
        grid.weightx = 0;
        namePn.add(pnNameType, grid);
        grid.weightx = 1;
        namePn.add(pnMiddleCol, grid);
        namePn.add(pnBlankName,grid);
        indexlist_words.clearSelection();
        indexlist_titles.clearSelection();
        validate();
//        treeDisplay = treeDisplayLastName; 
    }

    void createcoursepanel() {
        btok.setEnabled(false);
        vidpn.setVisible(false);
        pnVidSpacer.setVisible(vidpn.isVisible());
        namePn.removeAll();
        gamesPn.removeAll();
        coursePn.removeAll();
        int k = lsChooseCourse2.getSelectedIndex();
        if(k<0)lsChooseCourse2.setSelectedIndex(0);
        grid.insets = new Insets(0, 0, 0, 0);
        grid.gridx = 0;
        grid.gridy = -1;
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.weighty = 0;
        grid.weightx = 1;
        pnCourseAndTopics2.add(topicLabelPn2, grid);
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
        pnCourseAndTopics2.add(scroller2, grid);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 0;
        coursePn.add(panChooseCourse2, grid);
        grid.weightx = 1;
        coursePn.add(pnCourseAndTopics2, grid);
        grid.weightx = 0;
        coursePn.add(pnWordList, grid);
        panChooseCourse2.setVisible(true);
        pnWordList.setVisible(true);
        pnCourseAndTopics2.setVisible(true); 
        String courses2[] = listofcourses;
        if(searchtype == findword.TYPE_GETCOURSELISTNAME ){       
            loopc:for(int i = 0; i < listofcourses.length; i++){
                jnode c = tree.find(listofcourses[i]);
                for(int j = 0; j < topicsUsed.length; j++){
                    jnode jn;
                    if((jn = tree.findTopic(topicsUsed[j], c, false))!=null){
                        continue loopc;
                    }
                }
                courses2 = u.removeString2(courses2, listofcourses[i]);
            }
            if(courses2.length==1)treelabel2.setText(strSelectcourse2single);
            lsChooseCourse2.setListData(courses2);
        }
        lsChooseCourse2.setSelectedIndex(0);
        validate();
        doCourse2Change();
    }
    
    void creategamepanel() {
        btok.setEnabled(false);
        vidpn.setVisible(false);
        pnVidSpacer.setVisible(vidpn.isVisible());
        namePn.removeAll();
        coursePn.removeAll();
        gamesPn.removeAll();
        gamepanel = new runMovers();
        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.insets = new Insets(0, 0, 0, 0);
        JPanel gppan = new JPanel(new GridBagLayout());
        grid.weighty = 0;
        JPanel gppanlnpn = new JPanel(new GridBagLayout());
        gppanlnpn.setMinimumSize(new Dimension(sharkStartFrame.screenSize.width * 8 / 20, sharkStartFrame.screenSize.height/14));
        gppanlnpn.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width * 8 / 20, sharkStartFrame.screenSize.height/14));          
        gppanlnpn.setBackground(Color.yellow);
        gppanlnpn.setOpaque(true);
        grid.insets = new Insets(10, 10, 10, 10);
        JLabel clickmess = new JLabel(u.convertToHtml(strClickMess));
        clickmess.setOpaque(false);
        grid.weightx = 1;
        gppanlnpn.add(clickmess, grid);       
        grid.insets = new Insets(0, 0, 0, 0);
        gppan.add(gppanlnpn, grid);
        grid.weighty = 1;
        gppan.add(gamepanel, grid);
        grid.gridx = -1;
        grid.gridy = 0;
        pnBlankGame.setVisible(true);
        grid.weightx = (searchtype == TYPE_GETGAMENAME)?1:0;
        gamesPn.add(gppan, grid);
        grid.weightx = 1;
        if (searchtype != TYPE_GETGAMENAME) {
            gamesPn.add(pnBlankGame, grid); 
            pnFoundLists = new JPanel(new GridBagLayout());
            JLabel lbFoundLists = new JLabel(u.convertToHtml(u.gettext("findword_", "locationgame")));
            pnFoundLists.setBackground(sharkStartFrame.topictreecolor);
            pnFoundLists.setOpaque(true);
            lbFoundLists.setOpaque(false);
            grid.weighty = 0;
            grid.gridx = 0;
            grid.gridy = -1;
            grid.insets = new Insets(5, 5, 5, 5);
            pnFoundLists.add(lbFoundLists, grid);
            grid.insets = new Insets(0, 0, 0, 0);
            grid.weighty = 1;
            pnFoundLists.add(scroller2, grid);
            grid.gridx = -1;
            grid.gridy = 0;
            gamesPn.add(pnFoundLists, grid);
            grid.weightx = 0;
            gamesPn.add(pnWordList, grid);
            pnFoundLists.setVisible(false);
        }
        grid.gridx = -1;
        grid.gridy = 0;
        pnWordList.setVisible(false);
        validate();
        setupGamePanel(true, true);
        gamepanel.clearWholeScreen = true;
        gamepanel.start1();
        showingicons = true;
        gamepanel.startrunning();
    }

    void setupGamePanel(boolean firstpage, boolean init) {
        int cols;
        int rows = 8;
        if (searchtype == TYPE_GETGAMENAME) {
            cols = 12;
        } else {
            cols = 6;
        }
        onfirstpage = firstpage;

        jnode jj[] = ((jnode) gameTree.root.getLastChild()).getChildren();
        activeIcons = new GamesIcon_base[jj.length];
        int d = ((rows - 1) * (cols));
        gamerect = new Rectangle[jj.length];
        int i, j;
        gamepanel.removeAllMovers();
        gamepanel.reset();
        gamepanel.mtot = 0;
        int giconh = mover.HEIGHT / 8;
        int gicongap = giconh / 7;

        int hh;
        if (searchtype == TYPE_GETGAMENAME) {
            hh = (mover.HEIGHT - ((rows - 2) * gicongap)) / rows;
        } else {
            hh = (mover.HEIGHT - ((rows + 1) * gicongap)) / rows;
        }
        int ww = (mover.WIDTH - ((cols + 1) * gicongap)) / cols;
        Color col = u.lighter(sharkStartFrame.col1, 1.07f);
        gamepanel.addMover((new mover.rectMover(new Rectangle(mover.WIDTH, mover.HEIGHT), col, col)), 0, 0);

        activegames = new String[0];
        for (i = 0; i < jj.length; ++i) {
            if (firstpage) {
                if (i >= d) {
                    continue;
                }
            } else {
                if (i < d) {
                    continue;
                }
            }
            int ii = i;
            if (!firstpage) {
                ii = i - d;
            }
            int x = gicongap + ((ii % cols) * ww) + ((ii % cols) * gicongap);
            int y = gicongap + ((ii / cols) * hh) + ((ii / cols) * gicongap);
            gamerect[i] = new Rectangle(x, y, ww, hh);
            String s = jj[i].get();
            j = u.findString(sharkStartFrame.gamename, s);
            if (sharkStartFrame.gameiconlist[j] != null) {
                activegames = u.addString(activegames, s);
                activeIcons[i] = new GamesIcon_base(gamepanel,
                        gamerect[i],
                        new sharkImage(sharkStartFrame.gameiconlist[j], false),
                        s,
                        sharkStartFrame.col2, Color.gray, true, false, false);
            }
        }
        if(searchtype != TYPE_GETGAMENAME){
            pagemove = new mover.formattedtextmover(firstpage ? strtabnextpage : strtabprevpage,
                    new Color(70,70,70),
                    sharkStartFrame.treefont.deriveFont((float) sharkStartFrame.treefont.getSize() + 4),
                    gamepanel,
                    false,
                    Font.BOLD);
            pagemove.handcursor = true;

            int movx = (mover.WIDTH / 2) - (pagemove.w / 2);
            int movy = gicongap + ((rows - 1) * hh) + ((rows - 1) * gicongap) + (giconh / 2) - pagemove.h;
            if (searchtype != TYPE_GETGAMENAME) {
                gamepanel.addMover(pagemove, movx, movy);
            }
        }

        if (init) {
            gamepanel.addMouseListener(new MouseAdapter() {

                public void mousePressed(MouseEvent e) {
                    int i;
                    int x = gamepanel.mousex;
                    int y = gamepanel.mousey;
                    if (thread != null && thread.isAlive()) {
                        return;
                    }
                    if (pagemove != null && pagemove.mouseOver) {
                        setupGamePanel(!onfirstpage, false);
                        return;
                    }

                    for (i = 0; i < gamerect.length; ++i) {
                        if (gamerect[i] == null) {
                            continue;
                        }
                        if (gamerect[i].contains(x, y)) {
                            jnode node = (jnode) ((jnode) gameTree.root.getLastChild()).getChildAt(i);
                            gameTree.setSelectionPath(pp = new TreePath(node.getPath()));
                            if (node != lastnode) {
                                if (searchtype == TYPE_GETGAMENAME) {
                                    ((student.showStudentRecord) parent).doFilter(node.get(), TYPE_GETGAMENAME);
                                    finish();
                                } else {
                                    lastnode = node;
                                    highlightedIcons = activeIcons[i];
                                    highlightgame(node.get());
                                    (thread = new Thread(thisw)).start();
                                }
                                return;
                            }
                        }
                    }
                }
            });
            gamepanel.addMouseMotionListener(new MouseMotionAdapter() {

                public void mouseMoved(MouseEvent e) {
                    int mousex = gamepanel.mousex;
                    int mousey = gamepanel.mousey;
                    boolean overanyrect = false;
                    for (short i = 0; i < gamerect.length; ++i) {
                        if (gamerect[i] != null
                                && gamerect[i].x <= mousex
                                && gamerect[i].y <= mousey
                                && gamerect[i].x + gamerect[i].width >= mousex
                                && gamerect[i].y + gamerect[i].height >= mousey) {
                            overanyrect = true;
                            if ((lastrect != i && lastrect > 0 && gamerect.length > gamerect.length && gamerect[lastrect] != null
                                    && (lastrect < 0 || gamerect[i].x != gamerect[lastrect].x
                                    || gamerect[i].y != gamerect[lastrect].y)
                                    || gamepanel.tooltipmover1 == null)) {
                                lastrect = i;
                                gamepanel.addtooltipmover(sharkStartFrame.mainFrame.gametooltip[i], gamerect[i].x, gamerect[i].y,
                                        gamerect[i].x + gamerect[i].width, gamerect[i].y + gamerect[i].height);
                                return;
                            }
                        }
                    }
                    if (!overanyrect) {
                        gamepanel.removeTooltip();
                    }
                }
            });
        }

    }

    void highlightgame(String game) {
        for (int i = 0; i < activeIcons.length; i++) {
            if (activeIcons[i] == null) {
                continue;
            }
            activeIcons[i].defaultcolor();
        }
        if (highlightedIcons != null) {
            highlightedIcons.changecolour(Color.red);
        }
    }

public class listtreepainter extends treepainter {
    String usedLists[];
      public listtreepainter(String[] ss) {
          usedLists = ss;
      }
      public Component getTreeCellRendererComponent(JTree tree,
                  Object o,boolean selected,boolean expanded,boolean leaf,
                  int row,boolean hasfocus) {
          super.getTreeCellRendererComponent(tree,o,selected,expanded,leaf,row,hasfocus);
          if(o == null) return this;
          jnode node = ((jnode)o);
          if(searchtype==TYPE_GETCOURSELISTNAME && usedLists!=null){
                  if(u.findString(usedLists, node.get())<0 && node.type!=jnode.COURSE)
                      setForeground(Color.gray);
          }
          return this;
      }
  }    
    
    //-----------------------------------------------------------
    class itempainter extends JLabel implements ListCellRenderer {

        itempainter() {
            setOpaque(true);
        }
        Object o;
        FontMetrics m;

        public Component getListCellRendererComponent(JList list, Object oo,
                int index, boolean isSelected, boolean cellhasFocus) {
            o = oo;
            this.setBackground(isSelected ? indexlist_words.getSelectionBackground() : Color.white);
            setText((String) o);
            return this;
        }

        public void paint(Graphics g) {
            Rectangle r = getBounds();
            if (m == null) {
                m = getFontMetrics(getFont());
            }
            g.setFont(getFont());
            g.setColor(getBackground());
            g.fillRect(0, 0, r.width, r.height);
            g.setColor(getForeground());
            String s = (String) o;
            int i;
            if (shark.production &&  ((i = s.indexOf("@")) > 0)) {
                s = s.substring(0, i);
            }
            if (s.equals(toomany) || s.equals(nowords)) {
                g.setColor(Color.blue);
            }

            if ((i = s.indexOf("***")) > 0) {

                g.drawString(s.substring(0, i), 0, r.height / 2 - m.getHeight() / 2 + m.getAscent());
                g.setColor(Color.red);
                g.drawString(s.substring(i), m.stringWidth(s.substring(0, i)), r.height / 2 - m.getHeight() / 2 + m.getAscent());
            } else {
                g.drawString(s, 0, r.height / 2 - m.getHeight() / 2 + m.getAscent());
            }
        }
    }

    class AutoCompleteCombo extends JComboBox {

        boolean arrowKeyPressed;

        public AutoCompleteCombo() {
            setEditable(true);
            setPrototypeDisplayValue("WWWWWWWWW");
            setMaximumRowCount(5);
            addMouseListener(new MouseAdapter() {

                public void mouseEntered(MouseEvent me) {
                    tooltip_base.on((JComponent) me.getComponent(), me);
                }

                public void mouseExited(MouseEvent me) {
                    tooltip_base.off();
                }
            });

            getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    int key = e.getKeyCode();
                    if (key == KeyEvent.VK_UP
                            || key == KeyEvent.VK_DOWN) {
                        arrowKeyPressed = true;
                    } else {
                        arrowKeyPressed = false;
                    }

                    if (key == KeyEvent.VK_ESCAPE) {
                        finish();
                    }
                }

                public void keyTyped(KeyEvent e) {
                    arrowKeyPressed = false;
                }
            });


        }

        public String getText() {
            return getEditor().getItem().toString();
        }

        public void setItems(String s[]) {
            if (arrowKeyPressed) {
                return;
            }
            String text = getText();
            removeAllItems();
            for (int i = 0; i < s.length; i++) {
                if (!s[i].equalsIgnoreCase(text)) {
                    addItem(s[i]);
                }
            }


            if (s.length <= 0 || (s.length == 1 && s[0].equalsIgnoreCase(text))) {
                hidePopup();
            } else {
                // needed for it to paint properly
                hidePopup();
                if(this.isShowing())
                    showPopup();
            }

            setSelectedItem(text);

        }
    }
}
