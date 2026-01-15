package shark;
import java.io.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.*;

public class distribute
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  extends JDialog {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     topicTree topics;
     GridBagLayout gridBagLayout0 = new GridBagLayout();
     GridBagLayout gridBagLayout1 = new GridBagLayout();
     GridBagLayout gridBagLayout2 = new GridBagLayout();
     GridBagLayout gridBagLayout3 = new GridBagLayout();
     JList names = new JList();
     JList newlist = new JList();
     JPanel panel1 = new JPanel();
     JPanel panel23a = new JPanel(new GridBagLayout());
     JPanel panel23ab = new JPanel(new GridBagLayout());
     JPanel panel23b = new JPanel(new GridBagLayout());
     JPanel panel23 = new JPanel(new GridBagLayout());
     JPanel panel2 = new JPanel();
     JPanel panel3 = new JPanel();
     mlabel_base label1 = u.mlabel("dis_oldlist");
     mlabel_base label2 = u.mlabel("dis_newlist");
     mlabel_base label3 = new mlabel_base("");
     mlabel_base databasel = u.mlabel("dis_database");
     JButton browsebutton = new JButton(u.gettext("dis_browse", "label"));
     JButton soundbutton = new JButton(u.gettext("dis_sound", "label"));
//     String soundtext = soundbutton.text;
     mbutton undobutton = u.mbutton("dis_undo");
     mbutton delbutton = u.mbutton("dis_del");
     mbutton insbutton = u.mbutton("dis_ins");
     JButton exitbutton = u.Button("dis_exit");
     JButton savebutton = u.Button("dis_save");
     String database = sharkStartFrame.sharedPathplus + sharkStartFrame.extracourses+"1";
     JTextField databasef = new JTextField(database);
     String dbname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
     String currnames[] = new String[0];
     u.shownotes note;
     boolean changed;
     boolean ending,mustreset;
     GridBagConstraints grid2 = new GridBagConstraints();
     int endprivate;
     saveTree1 undo[] = new saveTree1[10];
     int currundo;
     String undopaths[] = new String[10];
     boolean dontsave;
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     distribute thisDistribute = this;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

     public distribute() {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       super(sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-07-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // set blank menu bar for Macs
       if(shark.macOS){
         sharkStartFrame.mainFrame.setJMenuBar(new javax.swing.JMenuBar());
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
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
//        soundbutton.setText(u.edit(soundtext, database));
        this.setResizable(false);
        setVisible(true);
        requestFocus();
        setTitle(u.gettext("dis_","title"));
        this.getContentPane().setLayout(gridBagLayout0);
        addKeyListener(new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
               int code = e.getKeyCode();
               if(databasef.hasFocus()) return;
               if(code == KeyEvent.VK_ESCAPE)
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 // enables exiting screen via the ESC key
                 if(saveit(true))
                   dispose();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               else  {topics.keypressed(e);}
           }
           public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                if(databasef.hasFocus()) return;
                if(code == KeyEvent.VK_ESCAPE) return;
                else  {topics.keyreleased(e);}
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowDeactivated(WindowEvent e) {
//             dispose();
          }
          public void windowClosed(WindowEvent e) {
              if(note !=null) {note.dispose();note=null;}
              db.closeAll();
              if(mustreset) {
                 sharkStartFrame.publicTopicLib = new String[0];
                 String s[] =  db.dblist(new File[]{sharkStartFrame.publicPath});
                 for(short i=0;i<s.length;++i) {
                    if(s[i].toLowerCase().indexOf("publictopics") >= 0)
                        sharkStartFrame.publicTopicLib = u.addString(sharkStartFrame.publicTopicLib,sharkStartFrame.updateCheck(s[i], sharkStartFrame.supdates, sharkStartFrame.updateFile, sharkStartFrame.publicTopicLib));
                 }
                 sharkStartFrame.publicTopicLib = u.addString(sharkStartFrame.publicTopicLib,db.dblist(sharkStartFrame.sharedPath,sharkStartFrame.extracourses));
                 sharkStartFrame.mainFrame.setupgames();
              }
              if(note !=null) {note.dispose();note=null;}
         }
          public void windowClosing(WindowEvent e) {
             if(saveit(true)) dispose();
          }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // enables exiting screen via the ESC key
          public void windowActivated(WindowEvent e) {
            requestFocus();
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        });

     browsebutton.setToolTipText(u.gettext("dis_browse", "tooltip"));
     soundbutton.setToolTipText(u.gettext("dis_sound", "tooltip"));

        int i;
        GridBagConstraints grid1 = new GridBagConstraints();
        panel1.setLayout(gridBagLayout1);
        panel2.setLayout(gridBagLayout2);
        panel3.setLayout(gridBagLayout3);
        panel1.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height*7/8));
        panel23.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width*2/3,sharkStartFrame.screenSize.height*7/8));
        panel2.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/4,sharkStartFrame.screenSize.height*7/8));
        panel3.setPreferredSize(new Dimension(sharkStartFrame.screenSize.width/3,sharkStartFrame.screenSize.height*7/8));
        currnames = u.addString(new String[]{u.gettext("dis_","private")},db.list(dbname,db.TOPIC));
        endprivate = currnames.length;
        currnames = u.addString(currnames,u.gettext("dis_","public"));
        currnames = u.addString(currnames,oktopics());
        names.setListData(currnames);
        names.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grid1.gridy = 0;
        grid1.weightx = grid1.weighty = 1;
        grid1.fill = GridBagConstraints.BOTH;
        grid2.gridy = -1;
        grid2.weightx = 1;
        grid2.weighty = 0;
        grid2.gridx = 0;
        grid2.gridy = -1;
        grid2.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(label1,grid2);
//startPR2004-08-27^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        // if running on a Macintosh
        if (shark.macOS){
 //         browsebutton.setForeground(Color.black);
 //         browsebutton.setBackground(Color.green);
          undobutton.setForeground(Color.black);
          undobutton.setBackground(new Color(220,255,220));
          delbutton.setForeground(Color.black);
          delbutton.setBackground(new Color(220,255,220));
          insbutton.setForeground(Color.black);
          insbutton.setBackground(new Color(220,255,220));
 //         soundbutton.setForeground(Color.black);
 //         soundbutton.setBackground(Color.green);
          savebutton.setForeground(Color.green);
          savebutton.setBackground(new Color(220,255,220));
          exitbutton.setForeground(Color.red);
          exitbutton.setBackground(new Color(220,255,220));
        }
        // if running on Windows
        else{
          exitbutton.setForeground(Color.white);
          undobutton.setBackground(sharkStartFrame.topictreecolor);
          delbutton.setBackground(sharkStartFrame.topictreecolor);
          insbutton.setBackground(sharkStartFrame.topictreecolor);
  //        soundbutton.setBackground(Color.orange);
  //        browsebutton.setBackground(Color.green.brighter());
          savebutton.setBackground(Color.green);
          exitbutton.setBackground(Color.red);
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        databasel.setForeground(Color.black);
        databasel.setOpaque(false);
        label1.setBackground(Color.orange);
        label1.setForeground(Color.black);
        panel2.setBackground(new Color(220,255,220));
        grid2.weighty = grid2.gridheight = 15;
        grid2.fill = GridBagConstraints.BOTH;
        JScrollPane sp = u.uScrollPane(names);
        sp.setBorder(BorderFactory.createLineBorder(Color.orange,2));
        panel1.add(sp,grid2);
        this.getContentPane().add(panel1,grid1);
        this.getContentPane().add(panel23,grid1);
        panel23b.add(panel2,grid1);
        panel23b.add(panel3,grid1);
        grid1.gridx = 0;grid1.gridy=-1;
        grid1.weighty = 0;
        panel23a.setBackground(Color.green);
        panel23a.setOpaque(true);
        panel23ab.setOpaque(false);
        panel23.add(panel23a,grid1);
        grid1.weighty = 1;
        panel23.add(panel23b,grid1);
        grid1.gridx = -1;grid1.gridy=0;

        grid2.fill = GridBagConstraints.BOTH;
        grid2.anchor =  GridBagConstraints.CENTER;
        grid2.weighty = grid2.gridheight = 15;
        panel2.add(label3,grid2);
        label3.setOpaque(false);
        grid2.weighty = 0; grid2.gridheight = 1;
        grid2.fill = GridBagConstraints.NONE;
        label2.setBackground(sharkStartFrame.topictreecolor);
        label2.setForeground(Color.black);
        panel2.add(undobutton,grid2);
        panel2.add(delbutton,grid2);
        panel2.add(insbutton,grid2);
        panel2.add(savebutton,grid2);
        panel2.add(exitbutton,grid2);
        newdataset();
        databasef.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String newdb = databasef.getText();
               String name = (new File(newdb)).getName();
               if(name.equalsIgnoreCase(topicTree.publictopics)) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  u.okmess("dis_cannot");
                 u.okmess("dis_cannot", thisDistribute);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  databasef.setText(database);
                  return;
               }
               if(newdb != database && saveit(true)) {
                  database = newdb;
                  newdataset();
//                  soundbutton.setText(u.edit(soundtext, database));
 //                 soundbutton.repaint();
              }
           }
        });
//        topics.addKeyListener(new java.awt.event.KeyAdapter() {
//           public void keyPressed(KeyEvent e) {
//              topics.keypressed(e);
//           }
//        });
        topics.model.addTreeModelListener( new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {
              saveforundo();
          }
          public void treeNodesInserted(TreeModelEvent e) {}
          public void treeNodesRemoved(TreeModelEvent e) {saveforundo();}
          public void treeStructureChanged(TreeModelEvent e) {}
       });
        exitbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
              if(saveit(true)){
                 dispose();
              }
          }
       });
        browsebutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               JFileChooser fc = new JFileChooser(sharkStartFrame.sharedPath);
               fc.setAcceptAllFileFilterUsed(false);
               fc.setFileFilter(new FileFilter() {
                   public boolean accept(File f) {
                     String s =  f.getName();
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    if(s.indexOf(".sha")  != s.length()-4
//                       || s.length() < sharkStartFrame.extracourses.length()+4
//                       || !s.substring(0,sharkStartFrame.extracourses.length()).equalsIgnoreCase(sharkStartFrame.extracourses)
                     if((s.indexOf(".sha")  != s.length()-4
                         || s.length() < sharkStartFrame.extracourses.length()+4
                         || !s.substring(0,sharkStartFrame.extracourses.length()).equalsIgnoreCase(sharkStartFrame.extracourses))
                        && !f.isDirectory()
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                     ) return false;
                      return true;
                   }
                   public String getDescription() {
                       return u.gettext("dis_","listtitle");
                   }
               });
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               int returnVal = fc.showOpenDialog(null);
                      int returnVal = fc.showOpenDialog(thisDistribute);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(returnVal == fc.APPROVE_OPTION) {
                  String newdb = fc.getSelectedFile().getName();
                  int i;
                  if((i=newdb.indexOf('.')) >= 0) newdb=newdb.substring(0,i);
                  if(newdb != database && saveit(true)) {
                     database = newdb;
                     databasef.setText(database);
                     newdataset();
//                     soundbutton.setText(u.edit(soundtext, database));
//                     soundbutton.repaint();
                 }
               }
          }
       });
       soundbutton.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(saveit(true)){
                 sharkStartFrame.mainFrame.startrecord(database,0);
                 dispose();
              }
          }
       });
       undobutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
            undo1();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            // enables exiting screen via the ESC key
            requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
       });
       delbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             jnode sel = topics.getSelectedNode(),parent = (jnode)sel.getParent();
             if(sel!= null
              && (sel.get().length()> 0 || sel.getNextSibling() != null)
              && (sel.isLeaf() || topics.canRemove(sel))) {
                   int pos = sel.getPosition();
                   ending=true;
                   topics.model.removeNodeFromParent(sel);
                   if(!parent.isLeaf()) {
                       topics.setSelectionPath(new TreePath(((jnode)parent.getChildAt(Math.min(parent.getChildCount()-1,pos))).getPath()));
                   }
                   else  topics.setSelectionPath(new TreePath(parent.getPath()));
                   topics.repaint();
                   ending=false;
                   addnote();
                   changed=true;
             }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
       insbutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             jnode sel = topics.getSelectedNode(), newnode;
             if(sel!=null && sel.getLevel() > 1 && sel.get().length()>0) {
                   ending=true;
                   topics.model.insertNodeInto(newnode=new jnode(),(jnode)sel.getParent(),sel.getPosition());
                   topics.setSelectionPath(new TreePath(newnode.getPath()));
                   ending=false;
                   addnote();
                   topics.repaint();
             }
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
        savebutton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
             if(saveit(false))  newdataset();
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             // enables exiting screen via the ESC key
             requestFocus();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
       });
       names.addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
            String sel = (String)names.getSelectedValue();
            int  selnum = names.getSelectedIndex();
            if(sel != null && selnum != endprivate && selnum != 0) {
               jnode sel2 = topics.getSelectedNode();
               if(sel2.isLeaf()) {
                  topics.stopEditing();
                  boolean add = (sel2.get().length() == 0);
                  if(selnum < endprivate)
                     sel2.setUserObject(topicTree.ISTOPIC+topicTree.SEPARATOR+sel);
                  else
                     sel2.setUserObject(topicTree.ISTOPIC+sel);
                  if(add) {
                     topics.addEmpty(topics.root);
                     if(sel2.getNextLeaf() != null)
                      topics.setSelectionPath(new TreePath(sel2.getNextLeaf().getPath()));
                  }
                  changed = true;
                  topics.model.reload(sel2);
                  saveforundo();
                  sel2.type = jnode.TOPIC;
                  topics.repaint();
                  topics.requestFocus();
               }
            }
         }
       });
//startPR2004-09-01^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       // enables exiting screen via the ESC key
       topics.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             if(saveit(true)){
               dispose();
             }
         }
       });
       databasef.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             if(saveit(true)){
               dispose();
             }
         }
       });
       names.addKeyListener(new KeyAdapter() {
         public void keyPressed(KeyEvent e) {
           int code = e.getKeyCode();
           if(code == KeyEvent.VK_ESCAPE)
             if(saveit(true)){
               dispose();
             }
         }
       });
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       addnote();
       saveforundo();
     }
     //----------------------------------------------------------------
     void saveforundo() {
      if(dontsave) return;
        if(++currundo >= undo.length) currundo = 0;
        undo[currundo] = new saveTree1(topics,topics.root);
        undopaths[currundo] =  null;
        jnode node = (jnode)topics.getSelectedNode();
        if(node != null)
           undopaths[currundo] = topics.savePath(node);
     }
     //----------------------------------------------------------------
     void undo1() {
       undo[currundo] = null;
       if(--currundo < 0) currundo = undo.length-1;
       if(undo[currundo] != null) {
        topics.root.removeAllChildren();
        topics.model.reload();
        dontsave = true;
        undo[currundo].addToTree(topics,topics.root);
        topics.model.reload();
        topics.expandAll();
        if(undopaths[currundo] != null) {
           jnode  node  = topics.expandPath(undopaths[currundo]);
           if(node != null) topics.setSelectionPath(new TreePath(node.getPath()));
        }
        dontsave = false;
       }
       else saveforundo();
    }
     //---------------------------------------------------------------
     String[] oktopics() {
      String s[] = new String[0];
      topicTree tree =  new topicTree();
      tree.dbnames = new String[0];
      tree.onlyOneDatabase = topicTree.publictopics;
      tree.root.setIcon(jnode.ROOTTOPICTREE);
      tree.setup(new String[] {topicTree.publictopics},false,db.TOPICTREE,true,"Topic lists");
      for(jnode node = (jnode)tree.root.getFirstLeaf(); node != null;
                                    node = (jnode)node.getNextLeaf()) {
          String str = node.get();
          if(!str.trim().equals("")){
            s = u.addStringSort(s,str);
          }
      }
      return s;
     }
     //----------------------------------------------------------------------
     void addnote() {
         jnode sel = topics.getSelectedNode();
         if(sel!=null) {
             if(sel.getLevel() == 1) {
                if(sel.get().length() == 0) setmessage("newcourse");
                else setmessage("changecourse");
             }
             else  if(!sel.isLeaf() && !sel.isRoot()) {
                setmessage("changeheading");
             }
             else {
                if(sel.get().length() == 0) setmessage("newleaf");
                else setmessage((sel.get().charAt(0) == topicTree.ISTOPIC.charAt(0))?"changeleaf1":"changeleaf2");
             }
         }
     }
     //------------------------------------------------------------
     void newdataset() {
        db.closeAll();
        jnode node;
        panel3.removeAll();
        panel23a.removeAll();
        topics = new topicTree();
        grid2.weighty = 0;grid2.gridheight = 1;
        grid2.fill = GridBagConstraints.HORIZONTAL;
        grid2.gridx=0;grid2.gridy=-1;
        panel23a.add(databasel,grid2);
        panel23a.add(databasef,grid2);
        panel23a.add(panel23ab,grid2);
        grid2.fill = GridBagConstraints.NONE;
        grid2.gridx=-1;grid2.gridy=0;
      //  grid2.anchor =  GridBagConstraints.WEST;
        grid2.insets = new Insets(10,0,10,0);
        panel23ab.add(browsebutton,grid2);
        grid2.anchor =  GridBagConstraints.CENTER;
        panel23ab.add(soundbutton,grid2);
        grid2.insets = new Insets(0,0,0,0);
        grid2.gridx=0;grid2.gridy=-1;
        grid2.fill = GridBagConstraints.HORIZONTAL;
        panel3.add(label2,grid2);
        grid2.weighty = grid2.gridheight = 15;
        grid2.fill = GridBagConstraints.BOTH;
        JScrollPane sp = u.uScrollPane(topics);
        sp.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor,2));
        panel3.setBorder(BorderFactory.createLineBorder(Color.black,2));
        panel3.add(sp,grid2);
        topics.onlyOneDatabase = database;
        topics.distribute=true;
        topics.root.setIcon(jnode.ROOTTOPICTREE);
        topics.setup(new String[]{database}, true, db.TOPICTREE,false,database);
        if(!db.exists(database) ) {
           topics.model.insertNodeInto(new jnode(u.gettext("dis_","dummycourse",sharkStartFrame.studentList[sharkStartFrame.currStudent].name)),
                                       topics.root, 0);
           topics.addEmpty(topics.root);
        }
        else if(db.list(database,db.TOPICTREE).length==0) {
           topics.root.removeAllChildren();
           topics.model.reload(topics.root);
           topics.addEmpty(topics.root);
        }
        for(node=(jnode)topics.root.getFirstLeaf();node != null; node = (jnode)node.getNextLeaf()) {
             String s = node.get();
             if(s.length()>1 && s.charAt(0)==topicTree.ISTOPIC.charAt(0)) {
               int pos = s.indexOf(topicTree.SEPARATOR);
               if(pos>0 && s.substring(1,pos).equalsIgnoreCase((new File(database)).getName())) {
                  node.setUserObject(topicTree.ISTOPIC + s.substring(pos));
               }
             }
        }
        topics.expandAll();
        topics.setSelectionPath(new TreePath(topics.root.getFirstLeaf().getPath()));
        topics.setShowsRootHandles(true);topics.setRootVisible(false);
        validate();
        addnote();
        topics.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
          public void valueChanged(TreeSelectionEvent e) {
             if(ending) return;
             jnode sel = topics.getSelectedNode();
             if(sel==null) {
               topics.setSelectionPath(new TreePath(topics.root.getFirstLeaf()));
               return;
             }
             addnote();
          }
        });
     }
     //----------------------------------------------------------------
     void setmessage(String s) {
          String s2;
          int yy=-1;
          Rectangle r= null;
          if(note !=null){ yy = note.getY(); note.dispose();}
          if((s2 = u.gettext("dis_",s)) == null) return;
          if(topics.getSelectionPath() == null) return;
          if(topics.getLocationOnScreen() == null) return;
          if((r = topics.getPathBounds(topics.getSelectionPath())) != null)
             yy = r.y +  topics.getLocationOnScreen().y;
          if(yy<0) return;
          note = new u.shownotes(u.splitString(s2),
                                 null, panel3.getLocationOnScreen().x,
                                 Math.min(undobutton.getLocationOnScreen().y
                                      - (u.count(s2,'|')+1)*getFontMetrics(label1.getFont()).getHeight(),
                                   yy),
                                   true );
     }
     //----------------------------------------------------------------
     boolean  saveit(boolean mess) {
        int i;
        if(!changed && topics.dbchanged.length()==0) return true;
        jnode node;
        String wanttopics[] = new String[0];
        if(topics.isEditing()) {ending=true; topics.stopEditing();  ending=false;}
        if(mess) {
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            i = u.yesnocancel(u.gettext("dis_","distrib"),u.gettext("dis_","save",database));
          i = u.yesnocancel(u.gettext("dis_","distrib"),u.gettext("dis_","save",database), thisDistribute);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(i < 0) return false;   // cancel
            if(i > 0) return true;    // no
        }
        ending = true;
        loop1: while(true) {
             for(node=(jnode)topics.root.getFirstLeaf();node != null; node = (jnode)node.getNextLeaf()) {
                 String s = node.get();
                 if(s.length() == 0) {
                    topics.model.removeNodeFromParent(node);
                    continue loop1;
                 }
             }
             break;
        }
        if(!topics.root.isLeaf())
             for(node=(jnode)topics.root.getFirstLeaf();node != null; node = (jnode)node.getNextLeaf()) {
             String s = node.get();
             if(s.charAt(0)!=topicTree.ISTOPIC.charAt(0)) {
                noise.beep();
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                u.okmess("dis_badleaf");
                u.okmess("dis_badleaf", thisDistribute);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                topics.addEmpty(topics.root);
                ending=false;
                return false;
             }
        }
        for(node=(jnode)topics.root.getFirstLeaf();node != null; node = (jnode)node.getNextLeaf()) {
             String s = node.get();
             if(s.length()>1 && s.substring(0,2).equals(topicTree.ISTOPIC+topicTree.SEPARATOR)) {
                  node.setUserObject(topicTree.ISTOPIC + (new File(database)).getName() + s.substring(1));
                  wanttopics = u.addString(wanttopics,s.substring(2));
             }
        }
        jnode courses[] = topics.root.getChildren();
        String coursenames[] = new String[0];
        for(i=0;i<courses.length;++i) {
             coursenames = u.addString(coursenames,courses[i].get());
        }
        String oldcourses[] = db.list(database,db.TOPICTREE);
        for(i=0;i<oldcourses.length;++i) {
             if(u.findString(coursenames,oldcourses[i]) < 0)
                 db.delete(database,oldcourses[i],db.TOPICTREE);
        }
        for(i=0;i<courses.length;++i) {
              db.update(database,coursenames[i],
                  (new saveTree1(topics,courses[i])).curr,  db.TOPICTREE);
        }
        String oldtopics[] = db.list(database,db.TOPIC);
        for(i=0;i<oldtopics.length;++i) {
             if(u.findString(wanttopics,oldtopics[i]) < 0)
                 db.delete(database,oldtopics[i],db.TOPIC);
        }





        /*
        for(i=0;i<wanttopics.length;++i) {
            saveTree1 tr = (saveTree1)db.find(dbname,wanttopics[i],db.TOPIC);
            if(tr != null) db.update(database,wanttopics[i],tr.curr,db.TOPIC);
        }
        String recordings[] = db.list(dbname,db.WAV);
//        String oldrecordings[] = db.list(database,db.WAV);
//        for(i=0;i<oldrecordings.length;++i) {
//             if(u.findString(recordings,oldrecordings[i]) < 0) {
//                 db.delete(database,oldrecordings[i],db.WAV);
//             }
//        }
        for(i=0;i<recordings.length;++i) {
              byte tr[] = (byte[])db.findwav(dbname,recordings[i]);
              db.updatewav(database,recordings[i],tr);
        }
         *
         */
        for(i=0;i<wanttopics.length;++i) {
//            saveTree1 tr = (saveTree1)db.find(dbname,wanttopics[i],db.TOPIC);
//            if(tr != null) db.update(database,wanttopics[i],tr.curr,db.TOPIC);
            saveTreeWordList tr = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,wanttopics[i],db.TOPIC);
            saveTree1 trold = null;
            if(tr==null)
                trold = (saveTree1)db.find(dbname,wanttopics[i],db.TOPIC);
            if(tr!=null)
                db.update(database,wanttopics[i],tr,db.TOPIC);
            else if(trold!=null)
                db.update(database,wanttopics[i],trold.curr,db.TOPIC);
        }
//        String recordings[] = db.list(dbname,db.WAV);
//        for(i=0;i<recordings.length;++i) {
//              byte tr[] = (byte[])db.findwav(dbname,recordings[i]);
//              db.updatewav(database,recordings[i],tr);
//        }





        changed = false;
        topics.dbchanged = "";
        mustreset = true;
        ending=false;
        return true;
     }
}
