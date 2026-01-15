/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shark;

import javax.swing.tree.*;
import java.io.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import javax.swing.filechooser.FileFilter;
import java.lang.ref.SoftReference.*;
import java.io.File;
import javax.swing.event.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

/**
 *
 * @author White Space
 */
public class ImportList extends JFrame {

    int borderthick = 5;
    Color green = Color.lightGray;//new Color(102, 204, 51);
    JList names = new JList();
    String currnames[];
    JFrame thisf;
    JPanel adminlistspn;
    JPanel droppn;
        JRadioButton rbFile;
        JRadioButton rbUsers;
        JPanel pnmainmid;
        JPanel pnblank;
        String strsellist = u.gettext("importlists", "selected");
        JLabel sellb;
        OwnWordLists owlists;
        ItemListener bglisten2 = new java.awt.event.ItemListener() {
           public void itemStateChanged(ItemEvent e) {
               if(rbFile.isSelected() || rbUsers.isSelected()){
                   btimportlist.setEnabled(false);
                   sellb.setText(strsellist);
                   setWordListVisible(false);
                   lists.clearSelection();
                   st = null;
                   swt = null;
                   pnmainmid.setVisible(true);
                   pnblank.setVisible(false);
               }
               if(rbFile.isSelected()){
                   adminlistspn.setVisible(false);
                   droppn.setVisible(true);
               }
               else if (rbUsers.isSelected()){
                 adminlistspn.setVisible(true);
                 droppn.setVisible(false);
               }
           }
        };
        JPanel scrollblank;
        JScrollPane scroller;
        wordlist wordTree;
        topicTree lists;
        saveTreeWordList swt;
        saveTree1 st;
        String stu = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
        topic oldcurrplay;
        JButton btimportlist;
        ArrayList importedlists = new ArrayList();
        String currname;
        JButton but_namesdel;

//        static boolean active = false;
        JLabel lbConfirmImport;
javax.swing.Timer confirmTimer;
javax.swing.Timer progressTimer;
JPanel scrollerpn;
String str_confirmimports = u.gettext("importlists", "confirmimports");
String str_confirmimport = u.gettext("importlists", "confirmimport");
Font plainfont;
jnode importednodes[] = new jnode[]{};
progress_base progbar;
String strname_def = u.gettext("ownwordlists", "definitions");
Thread importThread;
doListImport dli;
File[] fileToImport;

    public ImportList(OwnWordLists jd) {
        super();
        thisf = this;
        owlists = jd;
        currnames = new String[0];
//        active = true;

        UIDefaults uidef = UIManager.getDefaults();
        Font bfont = (Font)uidef.get("Button.font");
        plainfont = bfont.deriveFont(Font.PLAIN);

        oldcurrplay = sharkStartFrame.currPlayTopic;
        int border = 20;
        int thisWidth = sharkStartFrame.screenSize.width * 7 / 14;
        int thisHeight = sharkStartFrame.screenSize.height * 2 / 3;

//        this.setBounds(border, border, thisWidth, thisHeight);
        this.setBounds(u2_base.adjustBounds(new Rectangle(border, border, thisWidth, thisHeight)));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                doexit();
            }   

        });
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 1;
        grid.weighty = 1;
        grid.gridx = -1;
        grid.gridy = 0;



        JPanel pnnames = new JPanel(new GridBagLayout());
        JPanel pnmain = new JPanel(new GridBagLayout());
        pnnames.setBorder(BorderFactory.createEtchedBorder());
        pnmain.setBorder(BorderFactory.createEtchedBorder());

 //       pnnames.setPreferredSize(new Dimension(thisWidth * 7 / 20, thisHeight));
 //       pnnames.setMinimumSize(new Dimension(thisWidth * 7 / 20, thisHeight));
        pnmain.setPreferredSize(new Dimension(thisWidth, thisHeight));
        pnmain.setMinimumSize(new Dimension(thisWidth, thisHeight));


        btimportlist = u.sharkButton();
        btimportlist.setText(u.gettext("importlists", "importlist"));
        btimportlist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {            
                if(rbUsers.isSelected()){
                    importednodes = u.addnode(importednodes, lists.getSelectedNode());
                }
                if(saveImport()>=0){
                    setWordListVisible(false);
                    btimportlist.setEnabled(false);
                    lbConfirmImport.setText(str_confirmimport);
                    lbConfirmImport.setVisible(true);
                    confirmTimer.start();
                }
            }
        });



        JPanel pnmaintop = new JPanel(new GridBagLayout());
        JPanel pnmainbottom = new JPanel(new GridBagLayout());
        pnmainmid = new JPanel(new GridBagLayout());
        pnblank = new JPanel(new GridBagLayout());
        JPanel pnmaintop2 = new JPanel(new GridBagLayout());


        JButton exit = u.sharkButton();
        exit.setFont(plainfont);
        exit.setText(u.gettext("close", "label"));
//        if(shark.macOS){
//            exit.setForeground(Color.red);
//        }
//        else{
//            exit.setBackground(Color.red);
//            exit.setForeground(Color.white);
//        }
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doexit();
                thisf.dispose();
            }
        });


        grid.fill = GridBagConstraints.NONE;
        grid.insets = new Insets(30,0,30,0);
        pnmainbottom.add(exit, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;

        pnmaintop2.setBackground(sharkStartFrame.topictreecolor);
        pnmaintop2.setOpaque(true);
        
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.BOTH;


        rbFile = new JRadioButton();
        rbFile.setText(u.gettext("importlists", "fromfile"));
        rbFile.addItemListener(bglisten2);
        rbUsers = new JRadioButton();
        rbUsers.setText(u.gettext("importlists", "otherusers"));
        rbUsers.addItemListener(bglisten2);
        ButtonGroup bgr = new ButtonGroup();
        bgr.add(rbFile);

        bgr.add(rbUsers);


        adminlistspn = new JPanel(new GridBagLayout());
        adminlistspn.setOpaque(true);
        lists = new topicTree();
        lists.setCellRenderer(new listtreepainter());
        lists.setRootVisible(false);
        jnode admnode;
        boolean foundone = false;
         if(student.admintopiclist.length>0) {
            admnode=new jnode(u.gettext("importlists", "admins", shark.programName));
            admnode.setIcon(jnode.COURSE);
            admnode.color = Color.blue;
            boolean adminnodeinserted = false;
            for(int i=0;i<student.admintopiclist.length;++i) {
              if(stu.equals(student.admintopiclist[i]))continue;
              jnode admnode2=new jnode((new File(student.admintopiclist[i])).getName());
              admnode2.type = jnode.TEACHER;
              admnode2.color = Color.red;
              String privates2[]  = db.list(student.admintopiclist[i],db.TOPIC);
              if(privates2.length==0)continue;
              foundone = true;
              if(!adminnodeinserted){
                  lists.model.insertNodeInto(admnode,lists.root,lists.root.getChildCount());
                  adminnodeinserted = true;
              }
              lists.model.insertNodeInto(admnode2,admnode,admnode.getChildCount());
              for(int j=0;j<privates2.length;++j) {
                 lists.model.insertNodeInto(new jnode(privates2[j]),admnode2,admnode2.getChildCount());
              }
              lists.expandPath(new TreePath(admnode2.getPath()));
            }
            lists.expandPath(new TreePath(admnode.getPath()));
         }



        grid.insets = new Insets(0,0,0,0);
        grid.anchor = GridBagConstraints.WEST;
        grid.fill = GridBagConstraints.VERTICAL;
        JPanel pnlbcontrols = new JPanel(new GridBagLayout());
        JPanel pncontrols = new JPanel(new GridBagLayout());
        grid.weightx = 0;
        grid.insets = new Insets(0,0,0,10);
        pnlbcontrols.add(u.infoLabel(u.edit(u.gettext("ownwordlists", "importlistinfo"), shark.programName, shark.programName)), grid);
        grid.insets = new Insets(0,0,0,0);
        pnlbcontrols.add(new JLabel(foundone?u.gettext("importlists", "importlists"):u.gettext("importlists", "importlists2")), grid);
        grid.gridx = 0;
        grid.gridy = -1;
        pncontrols.add(rbUsers, grid);
        pncontrols.add(rbFile, grid);
        grid.gridx = -1;
        grid.gridy = 0;
        grid.weightx = 1;
        JPanel filler1 = new JPanel();
        filler1.setOpaque(false);
        pncontrols.add(filler1, grid);
        pncontrols.setOpaque(false);
        pnlbcontrols.setOpaque(false);
        pnmaintop2.setOpaque(false);
        pnmaintop.setOpaque(true);
        pnmaintop.setBackground(sharkStartFrame.topictreecolor);
        rbUsers.setOpaque(false);
        rbFile.setOpaque(false);
        grid.weightx = 1;
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.NONE;
        JPanel pntopcontainer = new JPanel(new GridBagLayout());
        pntopcontainer.setOpaque(false);
        grid.insets = new Insets(0,0,foundone?0:10,10);
        pntopcontainer.add(pnlbcontrols, grid);
        grid.insets = new Insets(0,0,0,0);
        if(foundone)
            pntopcontainer.add(pncontrols, grid);


        pnmaintop2.add(pntopcontainer, grid);

        grid.anchor = GridBagConstraints.CENTER;
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = 0;
        grid.gridy = -1;

        grid.weighty = 0;
        grid.insets = new Insets(10-(borderthick/2),10,10+(borderthick/2),10);
        grid.insets = new Insets(0,0,0,0);
        pnmaintop.add(pnmaintop2, grid);
        grid.weighty = 1;
        grid.insets = new Insets(0,0,0,0);
        pnmaintop.add(pnmainmid, grid);
        pnmaintop.add(pnblank, grid);

        pnblank.setVisible(true);
        pnmainmid.setVisible(false);

        pnmaintop.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, borderthick));
 //       pnmainbottom.setPreferredSize(new Dimension((int) pnmain.getPreferredSize().getWidth(), thisHeight * 3 / 22));
 //       pnmainbottom.setMinimumSize(new Dimension((int) pnmain.getPreferredSize().getWidth(), thisHeight * 3 / 22));


        scrollblank = new JPanel(new GridBagLayout());
        wordTree = new wordlist();
        scroller = u.uScrollPane(wordTree);


        JPanel listpn = new JPanel(new GridBagLayout());
        listpn.setBorder(BorderFactory.createEtchedBorder());


        JPanel listpnhead = new JPanel(new GridBagLayout());
        grid.fill = GridBagConstraints.NONE;
        sellb = new JLabel(strsellist);
        listpnhead.add(sellb, grid);
        grid.fill = GridBagConstraints.BOTH;

        grid.weighty = 0;

        listpn.add(listpnhead, grid);
        grid.weighty = 1;
        listpn.add(scrollblank, grid);

        confirmTimer = (new javax.swing.Timer(1500, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            lbConfirmImport.setVisible(false);
          }
        }));
        confirmTimer.setRepeats(false);


        lbConfirmImport = new JLabel();
        lbConfirmImport.setVisible(false);
        JPanel warnlb = new JPanel(new GridBagLayout());
        warnlb.setBackground(sharkStartFrame.cream);
        warnlb.setOpaque(true);
        grid.insets = new Insets(5,5,5,5);
        warnlb.add(lbConfirmImport, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.NONE;

        scrollblank.add(warnlb, grid);
        grid.fill = GridBagConstraints.BOTH;

        scrollerpn = new JPanel(new GridBagLayout());
        scrollerpn.add(scroller, grid);
        grid.fill = GridBagConstraints.NONE;
        grid.weighty = 0;
        grid.insets = new Insets(20,0,20,0);
        scrollerpn.add(btimportlist, grid);
//        btimportlist.setForeground(Color.red);
//        btimportlist.setForeground(Color.white);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;
        grid.weighty = 1;
        listpn.add(scrollerpn, grid);
        setWordListVisible(false);
        JPanel selectpn = new JPanel(new GridBagLayout());

          adminlistspn.add(  u.uScrollPane(lists)      , grid);
lists.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
         lists.getSelectionModel().addTreeSelectionListener(new
        TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
          

        jnode sel = lists.getSelectedNode();
        int j = lists.getSelectionCount();
          if(j>1)sel = null;

              for(int i = 0; i < importednodes.length; i++){
                  if(importednodes[i].equals(sel)){
                      lists.clearSelection();
                      return;
                  }
              }

        if (sel != null){
            String admin = ((jnode)sel.getParent()).get();
            String ssel = sel.get();
            topic t = new topic(admin,sel.get(),null,null);
            if(t!=null){
                wordTree.font = null;
//                t.ownlist = true;
                sharkStartFrame.currPlayTopic = t;
                wordTree.setup(t, null);
                wordTree.repaint();
                setWordListVisible(true);
                
                String namestrs[] = new String[0];
                
                for(int i =  0; i < names.getModel().getSize(); i++){
                    String ss = (String)names.getModel().getElementAt(i);
                    namestrs = u.addString(namestrs, ss);
                }
                
                boolean done = false;
                
                String curr = admin+ssel;
                for(int i =  0; i < importedlists.size(); i++){
                    String ss = (String)importedlists.get(i);
                    if(ss.equals(curr)){
                        done = true;
                        break;
                    }
                }
                btimportlist.setEnabled(sel.getLevel()==3 && !done);
                return;
            }

        }
        setWordListVisible(false);
      }
    });


    rbUsers.setVisible(foundone);

    droppn = new JPanel(new GridBagLayout());

    dragLabel dlbl = new dragLabel();
    dlbl.setOpaque(true);

    dlbl.setMinimumSize(new Dimension((int) pnmain.getPreferredSize().getWidth()/3,
            (int) pnmain.getPreferredSize().getWidth()/3));
    dlbl.setPreferredSize(new Dimension((int) pnmain.getPreferredSize().getWidth()/3,
            (int) pnmain.getPreferredSize().getWidth()/3));
    //dlbl.setIcon(new ImageIcon(sharkStartFrame.sharkicon));

    grid.fill = GridBagConstraints.NONE;
    JPanel droppn2 = new JPanel(new GridBagLayout());

    JButton browsebt = u.sharkButton();
    browsebt.setFont(plainfont);
    browsebt.setText(u.gettext("browse", "label"));

        browsebt.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setAcceptAllFileFilterUsed(false);
        fc.setMultiSelectionEnabled(true);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileFilter(new FileFilter() {
          public boolean accept(File f) {
            String s = f.getName();
            String ss = s.substring(s.lastIndexOf(".") + 1);
            if (f.isDirectory() ||
                ss.equalsIgnoreCase("shw")) {
              return true;
            }
            return false;
          }

          public String getDescription() {
            return u.gettext("importlists", "chooserdescription");
          }
        });
        int returnVal = fc.showOpenDialog(thisf);
        if (returnVal == fc.APPROVE_OPTION) {
          File f[] = fc.getSelectedFiles();
          if (f.length == 0) {
            f = new File[] {fc.getSelectedFile()};
          }
          if (f.length >= 1) {
              doImport(f);
          }
        }

             }
         } );

        grid.gridx = -1;
        grid.gridy = 0;
        JPanel browsepn = new JPanel(new GridBagLayout());
        grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
        JPanel filler = new JPanel();



        browsepn.add(filler, grid);
        grid.fill = GridBagConstraints.NONE;
        grid.insets = new Insets(0,0,0,10);
        grid.weightx = 0;
        browsepn.add(new JLabel(u.gettext("or", "label")), grid);
         grid.insets = new Insets(0,0,0,0);
        browsepn.add(browsebt, grid);

        grid.weightx = 1;

        grid.gridx = 0;
        grid.gridy = -1;
        droppn2.add(dlbl, grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        grid.insets = new Insets(10,0,0,0);
        droppn2.add(browsepn, grid);
        grid.fill = GridBagConstraints.NONE;
        grid.insets = new Insets(0,0,0,0);



        droppn.add(droppn2, grid);
        droppn.setOpaque(true);
        grid.fill = GridBagConstraints.BOTH;



        adminlistspn.setVisible(false);
        adminlistspn.setVisible(false);
        selectpn.add(adminlistspn, grid);
        selectpn.add(droppn, grid);

        grid.gridx = -1;
        grid.gridy = 0;

        selectpn.setPreferredSize(new Dimension((int) thisWidth/2, thisHeight * 3 / 22));
        selectpn.setMinimumSize(new Dimension((int) thisWidth/2, thisHeight * 3 / 22));
        listpn.setPreferredSize(new Dimension((int) thisWidth/2, thisHeight * 3 / 22));
        listpn.setMinimumSize(new Dimension((int) thisWidth/2, thisHeight * 3 / 22));
        pnmainmid.add(selectpn, grid);
        pnmainmid.add(listpn, grid);
        grid.weightx = 1;


        grid.weightx = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        pnmain.add(pnmaintop, grid);
        grid.weighty = 0;
        pnmain.add(pnmainbottom, grid);
        grid.weighty = 1;



        JPanel xlistsup = new JPanel(new GridBagLayout());
        xlistsup.setBorder(BorderFactory.createLineBorder(green, borderthick));
        JPanel xlistsdown = new JPanel(new GridBagLayout());
        xlistsdown.setPreferredSize(new Dimension((int) pnnames.getPreferredSize().getWidth(), thisHeight * 3 / 22));
        xlistsdown.setMinimumSize(new Dimension((int) pnnames.getPreferredSize().getWidth(), thisHeight * 3 / 22));

        JPanel lbpnlists = new JPanel(new GridBagLayout());
        lbpnlists.setBackground(green);
        grid.insets = new Insets(10-(borderthick/2),10,10+(borderthick/2),10);
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.NONE;
        grid.gridx = -1;
        grid.gridy = 0;
//        grid.insets = new Insets(10-(borderthick/2),10,10+(borderthick/2),10);
//        lbpnlists.add(u.infoLabel(u.edit(u.gettext("ownwordlists", "importlistinfo"), shark.programName, shark.programName)), grid);
        grid.insets = new Insets(10-(borderthick/2),0,10+(borderthick/2),10);
        lbpnlists.add(new JLabel(u.gettext("ownwordlists", "importedlists")), grid);
        grid.gridx = 0;
        grid.gridy = -1;

        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;
        names.setListData(new String[0]);

        names.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        names.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                currname = (String)names.getSelectedValue();
                but_namesdel.setEnabled(currname!=null);
                setbuttons();
            }
        });
        grid.gridx = 0;
        grid.gridy = -1;
        JPanel btpnlistsup = new JPanel(new GridBagLayout());

        grid.gridx = 0;
        grid.gridy = -1;
         grid.insets = new Insets(10,10,0,10);
         grid.weighty = 0;
         grid.fill = GridBagConstraints.NONE;

        int buttondim = (sharkStartFrame.screenSize.width*14/22)/24;
        int buttonimdim =  buttondim-(buttondim/5);

        but_namesdel = u.sharkButton();
        but_namesdel.setToolTipText(u.gettext("ownwordlists", "butdellisttooltip"));
        but_namesdel.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namesdel.setMinimumSize(new Dimension(buttondim, buttondim));
        Image  im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "deleteON_il48.png");
         ImageIcon iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namesdel.setIcon(iistu);
         but_namesdel.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                  if(currname==null)return;
                  if(currname != null && !currname.equals("")
                         && u.yesnomess(u.gettext("uwl_qdel","heading"),u.gettext("uwl_qdel","q", currname), thisf)){
                      db.delete(stu,currname,db.TOPIC);
                      db.delete(sharkStartFrame.resourcesdb,currname,db.TOPIC);
                      names.setListData(currnames=u.removeString(currnames,currname));
                      names.clearSelection();
                      privateListRecord.listDeleted(stu, currname);
                      currname = "";
                      if(sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator) {
                        sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics
                                           = db.anyof(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,db.TOPIC);
                        if(sharkStartFrame.studentList[sharkStartFrame.currStudent].checkstu())           // rb 6/2/06
                          student.checkadmin(sharkStartFrame.studentList[sharkStartFrame.currStudent]);   // rb 6/2/06
                        sharkStartFrame.mainFrame.gettopictreelist();
                      }
                  }
              }
         });

         grid.weighty = 0;
         btpnlistsup.add(but_namesdel, grid);
         grid.insets = new Insets(0,0,0,0);
         grid.weighty = 1;
         grid.fill = GridBagConstraints.BOTH;

         btpnlistsup.add(new JPanel(), grid);

        grid.gridx = -1;
        grid.gridy = 0;

        grid.weightx = 1;
        JScrollPane jspnames = new JScrollPane(names);
        JPanel olistsup = new JPanel(new GridBagLayout());
        olistsup.add(jspnames, grid);
        grid.weightx = 0;
        olistsup.add(btpnlistsup, grid);


        grid.weightx = 1;
        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 0;
        
        xlistsup.add(lbpnlists, grid);
        grid.weighty = 1;
        xlistsup.add(olistsup, grid);


        grid.gridx = 0;
        grid.gridy = -1;
        grid.weighty = 1;
        pnnames.add(xlistsup, grid);
        grid.weighty = 0;
        pnnames.add(xlistsdown, grid);
        grid.weighty = 1;



        btimportlist.setEnabled(false);


        grid.fill = GridBagConstraints.NONE;
     //   xlistsdown.add(exit, grid);

        grid.fill = GridBagConstraints.BOTH;
        grid.gridx = -1;

        grid.gridy = 0;
        this.getContentPane().add(pnmain, grid);
 //       this.getContentPane().add(pnnames, grid);
        this.setTitle(u.gettext("importlists", "import"));
        this.setIconImage(sharkStartFrame.sharkicon);
        this.setResizable(false);
        this.validate();

        if(!foundone){
            rbFile.setSelected(true);
        }
        setbuttons();
        setVisible(true);
        
        
        
    }
    
    
    void doexit(){
        owlists.setnames(false);
        sharkStartFrame.currPlayTopic = oldcurrplay;
        sharkStartFrame.mainFrame.setState(NORMAL);
        owlists.requestFocus();      
    }

     void setbuttons() {
        but_namesdel.setEnabled(names.getSelectedIndex()>=0);
     }

    void setWordListVisible(boolean on){
        sellb.setVisible(on);
        scrollerpn.setVisible(on);
        scrollblank.setVisible(!on);
    }

    public void setnames(String newlist){
        String namesstrs[] = new String[0];
        for(int i = 0; i < names.getModel().getSize(); i++){
            namesstrs = u.addString(namesstrs, (String)names.getModel().getElementAt(i));
        }
        namesstrs = u.addString(namesstrs, newlist);
        names.setListData(namesstrs);
    }

  private class dragLabel
      extends JLabel
      implements DropTargetListener, Serializable {
    DropTarget dropT;
    DataFlavor[] flavors = {
        DataFlavor.javaFileListFlavor};
    Color col;
    Color highCol = Color.yellow;
    boolean highlightBox = false;


    dragLabel() {
      super();
      dropT = new DropTarget(this, this);
      setBorder(BorderFactory.createLoweredBevelBorder());
      col = getBackground();

      
        setForeground(Color.lightGray);
        setBackground(Color.white);
        setHorizontalAlignment( SwingConstants.CENTER );
        setOpaque(true);
        setFont(sharkStartFrame.treefont.deriveFont((float)20));
        setText(u.convertToHtml(u.gettext("importlists", "dropmess")));

    }

    public void paint(Graphics g) {
      super.paint(g);
      if (highlightBox) {
        this.setBorder(BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED,
            highCol, highCol.darker()));
        this.setBackground(col.darker());
      }
      else {
        this.setBorder(BorderFactory.createLoweredBevelBorder());
        this.setBackground(col);
      }
    }

    public void dragEnter(DropTargetDragEvent dsde) {

      if (!this.hasFocus()) {
        this.requestFocusInWindow();
        this.requestFocus();
      }
      highlightBox = true;
      this.repaint();
    }

    public void dragExit(DropTargetEvent dse) {
      highlightBox = false;
      this.repaint();
    }

    public void dragOver(DropTargetDragEvent dsde) {}

    public void dropActionChanged(DropTargetDragEvent dsde) {}

    public void drop(DropTargetDropEvent dsde) {
        highlightBox = false;
      File ff[] = null;
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
              for(int j = 0; j < fileList.size(); j++){
                  File fi;
                  if(fileList.get(j) instanceof File && !(fi = (File)fileList.get(j)).isDirectory() &&
                          fi.getName().endsWith(".shw")  ){
                      if(ff==null)ff = new File[]{(File)fileList.get(j)};
                      else ff = u.addFile(ff, (File)fileList.get(j));
                  }
              }
            }
            else if (dataFlavor.equals(DataFlavor.stringFlavor)) {}
          }
          catch (Exception e) {}
        }
        if (ff!=null) {
            doImport(ff);
        }
        else {
          this.repaint();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  int saveImport(){
                String otherstu = null;
                String ssel = null;
                String otherstudb = null;
                if(rbUsers.isSelected()){
                  jnode sel = lists.getSelectedNode();
                  if(sel==null) return -1;
                  ssel = sel.get();

                  otherstu = ((jnode)sel.getParent()).get();
                  otherstudb = sharkStartFrame.resourcesPlus+
                          otherstu+sharkStartFrame.resourcesFileSuffix;
                  st = (saveTree1)db.find(otherstu, ssel, db.TOPIC);
                  swt = (saveTreeWordList)db.find(otherstudb, ssel, db.TOPIC);
                  if(st==null)return -1;
                }

                String slist[] = db.list(sharkStartFrame.resourcesdb, db.TOPIC);
                String name  = st.curr.names[0].trim();
/*
                 if(st.curr.type == OwnWordLists.TYPE_DEFINITIONS){
                     name = name + " " + String.valueOf(u.phonicsplit) + " " + strname_def;
                 }
                 else if(st.curr.type == OwnWordLists.TYPE_TRANSLATIONS)
                 {
                     name = name + " " + String.valueOf(u.phonicsplit) + " " + st.curr.languages[0]+"/"+st.curr.languages[1];
                 }
*/

                String oriname = name;
                int counter = 1;
                while(u.findString(slist, name)>=0){
                    int numberedend;
                    if((numberedend = name.lastIndexOf('_'))>=0){
                        try{
                            Integer.parseInt(name.substring(numberedend+1));
                        }
                        catch(Exception ed){
                             numberedend = -1;
                        }
                    }
                    if(numberedend>=0){
                        name = name.substring(0, numberedend);
                    }
                    name = name+"_"+counter;
                    counter++;
                }
                if(!oriname.equals(name)){
                    String mess = u.edit(u.gettext("importlists", "already"), oriname, name);
//                    u.okmess(shark.programName,  u.convertToCR(mess));
                    if(u.okcancel(shark.programName,  u.convertToCR(mess), thisf)==JOptionPane.OK_CANCEL_OPTION)
                        return -1;
                    st.curr.names[0] = name;
                    if(swt!=null)
                       swt.names[0] = name;
                }

              if(rbUsers.isSelected()){
                  for(int i = 0; i  < st.curr.names.length; i++){
                      String word = st.curr.names[i];
                     if(db.query(otherstudb, word, db.PICTUREPLIST) >= 0 &&
                        db.query(sharkStartFrame.resourcesdb, word, db.PICTUREPLIST) < 0)
                        db.update(sharkStartFrame.resourcesdb, word,
                            db.find(otherstudb, word, db.PICTUREPLIST),
                                db.PICTUREPLIST);
                     if(db.query(otherstudb, word, db.WAV) >= 0 &&
                        db.query(sharkStartFrame.resourcesdb, word, db.WAV) < 0)
                        db.updatewav(sharkStartFrame.resourcesdb, word, db.findwav(otherstudb, word));
                  }
                  lists.clearSelection();
              }
              sellb.setText(strsellist);
              db.update(stu, name, st.curr, db.TOPIC);
              if(swt!=null){            
                db.update(sharkStartFrame.resourcesdb, name, swt, db.TOPIC);
              }
              owlists.lastImportedList = name;
              setnames(st.curr.names[0]);
              st = null;
              swt = null;

              if(otherstu!=null && ssel!=null)
                  importedlists.add(otherstu+ssel);
              return 0;
  }

  void doImport(File ff[]){
          fileToImport = ff;

        if(ff.length>1){
            setWordListVisible(false);
        progbar = new progress_base(thisf, shark.programName,
                                           u.gettext("importing", "label"),
                                           new Rectangle(thisf.getWidth()/4,
                                                         thisf.getHeight()*2/5,
                                                         (thisf.getWidth()/2),
                                                         (thisf.getHeight()/5)));
        }

        importThread = new Thread(dli = new doListImport(ff));
        importThread.start();

        progressTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(importThread.isAlive())return;
                                   if(progbar!=null){
                                       progbar.dispose();
                                       progbar=null;
                                   }
                                    progressTimer.stop();
                                    progressTimer = null;
                                    dli = null;
                                    importThread = null;

                                   if(fileToImport.length>1){
                                       

                                            lbConfirmImport.setText(str_confirmimports);
                                            lbConfirmImport.setVisible(true);
                                            confirmTimer.start();
                                    }
                              }
       });
      
       progressTimer.setRepeats(true);
       progressTimer.start();


  }


public class listtreepainter extends treepainter {
      public listtreepainter() {
         setFont(normalfont);
         setOpaque(true);
      }
      public Component getTreeCellRendererComponent(JTree tree,
                  Object o,boolean selected,boolean expanded,boolean leaf,
                  int row,boolean hasfocus) {
          super.getTreeCellRendererComponent(tree,o,selected,expanded,leaf,row,hasfocus);
          if(o == null) return this;
          jnode node = ((jnode)o);
          if(node!=null) {
              
              
              for(int i = 0; i < importednodes.length; i++){
                  if(importednodes[i].equals(node)){
                      setForeground(Color.lightGray);
                      break;
                  }
              }
              
     //         node.dontexpand = false;
     //        if (u.findString((universal||xexcludegames)?currexcludes:stucurrexcludes, node.get())>=0 ){
     //            setForeground(Color.lightGray);
     //            setIcon(jnode.icons[jnode.GAMEBLANK]);
     //            if(!node.dontcollapse && !node.isLeaf())
     //                node.dontexpand = true;
     //        }
           }
          return this;
      }
  }




public class doListImport implements Runnable{

    File[] ff;

    public doListImport(File file[]){
        ff = file;
    }

  public void run(){
    File[] fdelete = null;
    for(int j = 0; j < ff.length; j++){


      File f = ff[j];
      String filename = f.getName();
      int k;
      if((k=filename.lastIndexOf("."))>=0){
          filename = filename.substring(0, k);
      }
      String tempNameSuf = "~"+filename+String.valueOf(System.currentTimeMillis());
      String tempName = sharkStartFrame.sharedPathplus + tempNameSuf;
      File tempf;
      u.copyfile(f, (tempf =  new File(tempName+".sha")));
      String s2[] = db.list(tempName, db.TOPIC);
      if(s2 == null || s2.length != 1){
          tempf.delete();
          continue;
      }
      swt = (saveTreeWordList)db.find(tempName, s2[0], db.TOPIC);
      if(swt!=null){
            String ss[] = db.list(tempName, db.PICTUREPLIST);
            for(int i = 0; i < ss.length; i++){
                if(db.query(sharkStartFrame.resourcesdb, ss[i], db.PICTUREPLIST)<0){
                    byte b[] = (byte[])db.find(tempName, ss[i], db.PICTUREPLIST);
                    if(b!=null)
                        db.update(sharkStartFrame.resourcesdb, ss[i], b, db.PICTUREPLIST);
                }
            }
            ss = db.list(tempName, db.WAV);
            for(int i = 0; i < ss.length; i++){
                if(db.query(sharkStartFrame.resourcesdb, ss[i], db.WAV)<0){
                    byte b[] = (byte[])db.findwav(tempName, ss[i]);
                    if(b!=null)
                        db.updatewav(sharkStartFrame.resourcesdb, ss[i], b);
                }
            }
            sharkTree tt = new sharkTree();
            tt.set(tt.root,s2[0]);
            for(int i = 1; i < swt.names.length; i++){
                tt.addChild(tt.root,swt.names[i]);
            }
            st = new saveTree1(tt,tt.root);
            st.curr.adminlist = true;
            st.curr.type = swt.type;
            st.curr.languages = swt.languages;

            if(ff.length==1){
                topic t = new topic(tempNameSuf,s2[0],null,null);
                if(t!=null){
                    wordTree.font = null;
                    sharkStartFrame.currPlayTopic = t;
                    wordTree.setup(t, null);
                    wordTree.repaint();
                     setWordListVisible(true);
                }
                sellb.setText(u.convertToHtml(strsellist+"|"+s2[0]));
                btimportlist.setEnabled(true);
            }
            if(fdelete==null)fdelete = new File[]{tempf};
            else fdelete = u.addFile(fdelete, tempf);

            if(ff.length>1){
                saveImport();
            }
      }
    }
    for(int i =0; fdelete!=null && i < fdelete.length; i++){
        fdelete[i].delete();
    }
  }
}


}
