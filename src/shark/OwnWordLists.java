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
 * @author Paul Rubie
 */
public class OwnWordLists extends JDialog{

    Color green = new Color(102,204,51);
    String inlist = u.gettext("ownwordlists", "wordsinlist");
    String theword = u.gettext("ownwordlists", "theword");
    JLabel lbinthelist = new JLabel(inlist);
    JLabel lbtheword = new JLabel(theword);
    int borderthick = 5;
    student dbstu = sharkStartFrame.studentList[sharkStartFrame.currStudent];
    String dbname = sharkStartFrame.studentList[sharkStartFrame.currStudent].name;
    String currnames[];
    JList names = new JList();
    Font plainfont = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
     ButtonGroup bg2 = new ButtonGroup();
     JDialog thisd;
     boolean ending;
     boolean started;
     static public String currname;
//     String startwords[];
     String maxWarnedLists[] = new String[]{};
     JPanel pnkeypad;
     keypad kk;
     
     
     JPanel xblankmid;
      JPanel xblankright;
     JPanel bluepn;
     JPanel resblankpn;
     JPanel respicpn;
     JPanel resrecpan;
     JRadioButton rbpic;
     JRadioButton rbrec;
     String currkeypads[][];
     String usermade = u.gettext("keypadsettings", "user");
     String defkey = u.gettext("keypad_", "defaultlanguage");
     boolean set_showkeypad;
     String set_keypadname;
     String curr_word;
     int lastFixedSelectedWordIndex = -1;
     recordtool rectool;
//     int lastsel =  -1;
     int buttondim ;
     JPanel collabels[];
     addflags addf = new addflags();
     Font smallerplainfont = plainfont.deriveFont((float)plainfont.getSize()-3);


//     String strchangepic = ;
//     String straddrec = u.gettext("ownwordlists", "addrec");
//     String strchangerec = u.gettext("ownwordlists", "changerec");
//     String strmixrec = u.gettext("ownwordlists", "mixrec");
     String foreignkeypads[];
     int lastnameindex = -1;
//     static public boolean resourcechanged = false;
     final static String POOLPREFIX = "pool";
     String otherwords[] = null;
     String strname_def = u.gettext("ownwordlists", "definitions");
     JPanel xresup;
     JPanel xwordsup;
     JButton but_namessaveas;
     JButton but_namesname;
     JButton but_namestype;
     JButton but_wordsedit;
     JButton but_namesdel;
     JButton but_namesexport;
     JButton savelist;
     JButton but_wordsddel;
     JButton but_wordsup;
     JButton but_wordsdown;
     JButton but_wordsprint;
     JButton but_wordsadd;
JButton butNextImage;
JButton butNextImage2;
     JLabel lbtrans;
     JLabel lbrec;
     JLabel lbrectrans;
     JLabel lbdef;
     String strlisttype = u.gettext("ownwordlists_edit", "type");

     public String lastImportedList;
     
     String lastnamesel;

   ItemListener bglisten2 = new java.awt.event.ItemListener() {
       public void itemStateChanged(ItemEvent e) {
        if(rbpic.isSelected()){
            editpicon();
        }
        else if(rbrec.isSelected()){
            editrecon();
        }
       }
    };
     sharklist words = new sharklist2(addf) {
       public void postdelete(){
           if(words.getSelectionRows()==null)words.setSelectionRow(words.root.getChildCount()-1);
       }
       public void newselection() {
         setbuttons();
       }
       public void editingstarted() {
         setbuttons();
       }
       public void editingstopped() {          
//           if(((jnode)words.model.getRoot()).getChildCount()==1 && ((jnode)((jnode)words.model.getRoot()).getChildAt(0)).get().trim().equals("")){
 //             startEdit(getLeadSelectionRow());
 //         }
 //          else 
           setbuttons();
       }       
       public boolean okedit() {
          int i;

          if(!ending) {
            words.requestFocus();
            if(words.getSelectionPath()==null)return false;
            String ss = null;
            ss = words.getSelectionPath().getLastPathComponent().toString();
            ss = u.stripspaces2(ss);
            String prevcurrword = curr_word;
            byte prevImportedImage[] = currImportedIm;
            int currRow = words.getSelectionRows()[0];
            checkCurrs(ss, currRow+1);
            if(onEditExistingWord(prevcurrword, ss, prevImportedImage)){
               if(currRow==lastFixedSelectedWordIndex){
                   words.stayOnCurrentSelection = true;
                   return false;
               }
            }     
            if(isAlreadyInList(ss)){
              words.stopEditing();
              words.delete();
              words.model.insertNodeInto(new jnode(), words.root,words.count());
              words.model.reload();
//              words.setSelectionRow(words.count());
              JOptionPane.showMessageDialog(thisd,u.gettext("alreadyinlist", "label"),
                                            u.gettext("alreadyinlist", "heading"),
                                            JOptionPane.INFORMATION_MESSAGE);
              stayOnCurrentSelection = true;
//              return true;


              words.startEditingAtPath(getPathForRow(words.count()));
              return false;            
            
            }
            if(ss != null && ss.length() > 36) {
                    u.okmess(u.gettext("uwl_word","title"),u.gettext("uwl_word","toolong"), thisd);
                ((jnode)words.getSelectionPath().getLastPathComponent()).setUserObject(ss.substring(0,36));
                words.model.reload();
                return false;
            }
            if(!iseasy(ss)){
             noise.beep();
             return  false;
            }
          }
          started = true;
          setbuttons();
          
          return true;
       }
        public boolean maxwarning() {
          if(!isAlreadyWarned(currname)){
            u.okmess(u.gettext("uwl_max", "heading"),u.gettext("uwl_max", "label"), thisd);
            return true;
          }
          return false;
        }
     };


        String userkeypadactive = null;
        String defaultkeypadactive = null;
        JPanel pnwordskeypad;
        String currkeypad;

        public static final short TYPE_NORMAL = 0;
        public static final short TYPE_DEFINITIONS = 1;
        public static final short TYPE_TRANSLATIONS = 2;

 
      ArrayList availablepics_all; 
      ArrayList availablepics_base;  
      String availablepics_default; 
      ArrayList haspublicpic;
      ArrayList haspublicrec;            
      public static saveTreeWordList workingList;
      saveTreeWordList oriList;
      ArrayList availablerecs; 
      public static ArrayList currrecs; 
        byte[] currImportedIm = null;

        short oricurrtype;
        short currtype;
        String currlangs[];
        mainPan dropBox;
        JButton boxDelete;
        JScrollPane sp;
        UIDefaults uidef = UIManager.getDefaults();
        int swidth = Integer.parseInt(uidef.get("ScrollBar.width").toString());
        JPanel mainrecpan;
        JPanel xtrarecpan;
        String allwords[];
        Font largerfont = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+1);
        Font largerfont2 = sharkStartFrame.treefont.deriveFont((float)sharkStartFrame.treefont.getSize()+6);
        String activeSymbolWord = null;
        int activeSymbolCol = -1;
        javax.swing.Timer testTimer;
        boolean allowsetpicture = sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator || sharkStartFrame.allowStuImportPics_OwnWords;
JButton but_namesimport;
JButton but_wordsimport;
JButton but_keypad;
int maxRecommendedWords = 10;

Thread exportThread;
doListExport dle;
File[] fileToExport;

progress_base progbar;
javax.swing.Timer exportTimer;

JFrame childFrame = null;


    public OwnWordLists(){
        super(sharkStartFrame.mainFrame);
        thisd = this;
        for(int i = 0; i < sharkStartFrame.publicLangKeypads.length; i++){
            if(sharkStartFrame.publicLangKeypads[i][0].equals(defkey))continue;
            if(foreignkeypads==null)foreignkeypads=new String[]{sharkStartFrame.publicLangKeypads[i][0]};
            else foreignkeypads= u.addString(foreignkeypads, sharkStartFrame.publicLangKeypads[i][0]);
        }
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
          public void windowClosing(WindowEvent e) {
//              sharkStartFrame.mainFrame.setupgames();
//            if(saveit(true)!=JOptionPane.CANCEL_OPTION){
//                  db.closeAll();
//                  sharkStartFrame.mainFrame.setupGametree();
//                   dispose();
//            }
                end();
          }


          public void windowActivated(WindowEvent e) {
//            if(!ImportList.active){
//                but_namesimport.setEnabled(true);
//            }
              if(sharkStartFrame.mainFrame.getState()==sharkStartFrame.mainFrame.ICONIFIED)return;
              if(childFrame!=null){
                  childFrame.dispose();
                  childFrame = null;
              }
              if(names.getSelectedIndex()<0 && names.getModel().getSize()>0){
                  if(lastImportedList!=null){
                      names.setSelectedValue(lastImportedList, true);
                      lastImportedList = null;
                  }
                  else names.setSelectedIndex(0);
              }
          }


        });
        setBounds(sharkStartFrame.mainFrame.getBounds());
        this.addComponentListener(new ComponentAdapter() {
          public void componentMoved(ComponentEvent e) {
            removeComponentListener(this);
            setBounds(sharkStartFrame.mainFrame.getBounds());
            validate();
            addComponentListener(this);
          }
        });
        if(keypad.keypadname!=null){          
            userkeypadactive = ((db.find(keypad.keypad_db,keypad.keypadname,db.SAVEKEYPAD))!=null)?keypad.keypadname:null;
            if(userkeypadactive==null && (db.find(keypad.publickeypad_db,keypad.keypadname,db.SAVEKEYPAD))!=null){
                defaultkeypadactive = defkey;
            }
        }


        ArrayList al = new ArrayList();
        if(userkeypadactive!=null){
            al.add(new String[]{keypad.keypadname+" "+usermade, sharkStartFrame.sharedPathplus+"keypad"});
        }
        for(int i = 0; i < sharkStartFrame.publicLangKeypads.length; i++){
            if(sharkStartFrame.publicLangKeypads[i][0].equals(defkey)){
                al.add(sharkStartFrame.publicLangKeypads[i]);
                break;
            }
        }
        for(int i = 0; i < sharkStartFrame.publicLangKeypads.length; i++){
            if(!sharkStartFrame.publicLangKeypads[i][0].equals(defkey)){
                al.add(sharkStartFrame.publicLangKeypads[i]);
            }
        }

/*
        saveTreeWordList workingList2 = (saveTreeWordList)db.find("Resources\\r-rs","photography",db.TOPIC);

        byte testrec[] = null;//currSpokenWord.data;
        byte testpic[] = null;//(byte[])db.find(sharkStartFrame.resourcesdb, "stop", db.PICTUREPLIST);
        for(int i = 1; i < workingList2.names.length; i++){
            if(workingList2.names[i].equals("stop"))
                testpic = workingList2.pics[i];
            if(workingList2.names[i].equals("enlarger"))
                testrec = workingList2.recs[i];
        }
        int counter = 0;
        for(int i = 0; i < 250; i++){
            String s = "test"+String.valueOf(counter++);
            workingList = new saveTreeWordList();
            workingList.names = new String[12];
            workingList.recs = new byte[12][];
            workingList.pics = new byte[12][];
            workingList.extrarecs = new byte[12][];
            workingList.names[0] = s;
            for(int j = 1; j < 11; j++){
                workingList.names[j] = s +String.valueOf(counter);
                workingList.recs[j] = testrec;
                workingList.pics[j] = testpic;
                workingList.extrarecs[j] = testrec;
            }
            workingList.adminlist = true;
            workingList.type = currtype;
            workingList.languages = currlangs;
 //           workingList.levels = st.curr.levels;
            db.update(sharkStartFrame.resourcesdb, s, workingList, db.TOPIC);
        }


*/



        setupRecord();

        currkeypads = new String[al.size()][];
        for(int i = 0; i < al.size(); i++){
           currkeypads[i] = (String[]) al.get(i);
        }        
        words.setLargeModel(true);
        words.setRowHeight(40);
        words.maxNumber = maxRecommendedWords;
        
         words.getModel().addTreeModelListener(new TreeModelListener() {
              public void treeNodesChanged(TreeModelEvent e) {
                updateNewSaveTree();
              }
              public void treeNodesInserted(TreeModelEvent e) {            
 //               updateNewSaveTree();
              }
              public void treeNodesRemoved(TreeModelEvent e) {
 //                 updateNewSaveTree();
              }
              public void treeStructureChanged(TreeModelEvent e) {
                  currImportedIm = null;
                  updateNewSaveTree();
              }
         });
        words.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged (TreeSelectionEvent e){
                wordsValueChanged(words.getSelectionPath());
            }
        });


       words.addMouseListener(new MouseAdapter() {
           public void mouseReleased(MouseEvent e) {
               setbuttons();
           }
             public void mousePressed(MouseEvent e) {
                 setbuttons();
                Point ps =  e.getPoint();
                ps.translate(words.getLocationOnScreen().x, words.getLocationOnScreen().y);
                Point psw =  words.getLocationOnScreen();
                int indexy = (ps.y - psw.y)/ words.getRowHeight();
                int xx = (ps.x - psw.x);

                int colxloc[] = new int[collabels.length];
                int colwid[] = new int[collabels.length];
                for(int n = 0; n < collabels.length; n++){
                   colxloc[n] = collabels[n].getLocation().x;
                   colwid[n] = collabels[n].getWidth();
                }
                int n;
                boolean found = false;
                for(n = 0; n < collabels.length; n++){
                    if(xx>colxloc[n] && xx<(colxloc[n]+colwid[n])){
                        found = true;
                        break;
                    }
                }


                activeSymbolWord = null;
                activeSymbolCol = -1;
                if(found){
                   TreePath i = words.getPathForRow(indexy);
                   jnode jn;
                   if(i==null || (jn=((jnode)i.getLastPathComponent()))==null || jn.get().trim().equals("")){
                        return;
                   }
                   boolean clickedresource = false;
                   if(n == 0 && hasrec(jn.get())){
                        words.setSelectionRow(indexy);
                        editrecon();
                        clickedresource = true;
                        ((recordtool.recpan)mainrecpan).dolisten(true);
                   }
                   else if(n == 1 && hasimage(jn.get())){
                        words.setSelectionRow(indexy);
                        editpicon();
                        clickedresource = true;
                   }
                   else if (n == 2 && hasrecextra(jn.get())){
                        words.setSelectionRow(indexy);
                        editrecon();
                        clickedresource = true;
                        ((recordtool.recpan)xtrarecpan).dolisten(false);
                   }
                   if(clickedresource){
                        activeSymbolWord = jn.get();
                        activeSymbolCol = n;
                        testTimer.restart();
                        words.repaint();
                   }
                   xresup.repaint();
                }
             }
        });

       testTimer = (new javax.swing.Timer(1000, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
               activeSymbolWord = null;
               activeSymbolCol = -1;
               words.repaint();
          }
        }));
        testTimer.setRepeats(false);



        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.BOTH;
        grid.weightx = 1;
        grid.weighty = 1;
        grid.gridx = -1;
        grid.gridy = 0;

        JPanel xlists = new JPanel(new GridBagLayout());
        JPanel xwords = new JPanel(new GridBagLayout());
        JPanel xres = new JPanel(new GridBagLayout());
        xblankmid = new JPanel(new GridBagLayout());
        xblankright = new JPanel(new GridBagLayout());



        JPanel lbpnlists = new JPanel(new GridBagLayout());
        JPanel lbpnwords = new JPanel(new GridBagLayout());
        JPanel lbpnres = new JPanel(new GridBagLayout());
        lbpnlists.setBackground(green);
        lbpnwords.setBackground(sharkStartFrame.topictreecolor);
        lbpnres.setBackground(Color.lightGray);

        grid.insets = new Insets(10-(borderthick/2),10,10+(borderthick/2),10);
        grid.anchor = GridBagConstraints.CENTER;
        grid.fill = GridBagConstraints.NONE;
        lbpnlists.add(new JLabel(u.gettext("ownwordlists", "yourlists")), grid);
        lbpnwords.add(lbinthelist, grid);
        lbpnres.add(lbtheword, grid);
        grid.insets = new Insets(0,0,0,0);
        grid.fill = GridBagConstraints.BOTH;


        int sw = sharkStartFrame.mainFrame.getWidth();
        int sh = sharkStartFrame.mainFrame.getHeight();
        xlists.setPreferredSize(new Dimension(sw*7/22, sh));
        xlists.setMinimumSize(new Dimension(sw*7/22, sh));
        xwords.setPreferredSize(new Dimension(sw*7/22, sh));
        xwords.setMinimumSize(new Dimension(sw*7/22, sh));
        xres.setPreferredSize(new Dimension(sw*8/22, sh));
        xres.setMinimumSize(new Dimension(sw*8/22, sh));
//        xblankmid.setPreferredSize(new Dimension((int)xwords.getPreferredSize().getWidth(), sh));
//        xblankmid.setMinimumSize(new Dimension((int)xwords.getPreferredSize().getWidth(), sh));
//        xblankright.setPreferredSize(new Dimension((int)xres.getPreferredSize().getWidth(), sh));
//        xblankright.setMinimumSize(new Dimension((int)xres.getPreferredSize().getWidth(), sh));

        xlists.setBorder(BorderFactory.createEtchedBorder());
        xwords.setBorder(BorderFactory.createEtchedBorder());
        xres.setBorder(BorderFactory.createEtchedBorder());
   
    
        JPanel xlistsup = new JPanel(new GridBagLayout());
        JPanel xlistsdown = new JPanel(new GridBagLayout());
        xwordsup = new JPanel(new GridBagLayout());
        JPanel xwordsdown = new JPanel(new GridBagLayout());
        xresup = new JPanel(new GridBagLayout());
        JPanel xresdown = new JPanel(new GridBagLayout());

        JPanel olistsup = new JPanel(new GridBagLayout());
        JPanel owordsup = new JPanel(new GridBagLayout());
        JPanel oresup = new JPanel(new GridBagLayout());


        xlistsdown.setPreferredSize(new Dimension((int)xlists.getPreferredSize().getWidth(), sh*3/22));
        xlistsdown.setMinimumSize(new Dimension((int)xlists.getPreferredSize().getWidth(), sh*3/22));
        xwordsdown.setPreferredSize(new Dimension((int)xwords.getPreferredSize().getWidth(), sh*3/22));
        xwordsdown.setMinimumSize(new Dimension((int)xwords.getPreferredSize().getWidth(), sh*3/22));
        xresdown.setPreferredSize(new Dimension((int)xres.getPreferredSize().getWidth(), sh*3/22));
        xresdown.setMinimumSize(new Dimension((int)xres.getPreferredSize().getWidth(), sh*3/22));


        JButton newlist = u.sharkButton();
        newlist.setText(u.gettext("ownwordlists", "new"));
        savelist = u.sharkButton();
        savelist.setText(u.gettext("ownwordlists", "save"));
        JButton exit = u.sharkButton();
        exit.setText(u.gettext("exit", "label"));
        if(shark.macOS){
            exit.setForeground(Color.red);
        }
        else{
            exit.setBackground(Color.red);
            exit.setForeground(Color.white);
        }

        grid.fill = GridBagConstraints.NONE;
        xlistsdown.add(newlist, grid);
        xwordsdown.add(savelist, grid);
        xresdown.add(exit, grid);
        grid.fill = GridBagConstraints.BOTH;

        grid.gridx = 0;
        grid.gridy = -1;


        grid.weighty = 0;
        newlist.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {

                    ListSelectionListener ls[] =  names.getListSelectionListeners();
                    if(ls!=null && ls.length>0) names.removeListSelectionListener(ls[0]);
                    boolean cancelled = !startnew(0);
                    if(ls!=null && ls.length>0) names.addListSelectionListener(ls[0]);
                    lastnamesel = (String)names.getSelectedValue();
                    if(cancelled)
                        nameselected(false);

              }
        });
        savelist.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 saveit(false);
                 words.clearSelection();
//                 startwords =words.getdata();
                 setbuttons();
              }
        });
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                end();
            }
        });
        
        xlistsup.add(lbpnlists, grid);
        xwordsup.add(lbpnwords, grid);
        xresup.add(lbpnres, grid);
        grid.weighty = 1;

        xlistsup.add(olistsup, grid);
        xwordsup.add(owordsup, grid);
        xresup.add(oresup, grid);

        setnames(true);
        removeunused();
        names.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


//       names.addMouseListener(new java.awt.event.MouseAdapter() {
//         public void mouseReleased(MouseEvent e) {
//             nameselected(false);
//         }
//        });
        
       names.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged (ListSelectionEvent e){
                Object o;
                if((o=names.getSelectedValue())==null)return;
                if(((String)o).equals(lastnamesel)){
                    return;
                }
                lastnamesel = ((String)o);
                setbuttons();
                ListSelectionListener ls[] =    names.getListSelectionListeners();
                if(ls!=null && ls.length>0) names.removeListSelectionListener(ls[0]);
                nameselected(false);
                if(ls!=null && ls.length>0) names.addListSelectionListener(ls[0]); 
//                lastnamesel = (String)names.getSelectedValue();
            }
        });        
        

//        names.addKeyListener(new KeyAdapter() {
//          public void keyPressed(KeyEvent e) {
//            int code = e.getKeyCode();
//            if (code == KeyEvent.VK_ESCAPE) {
//              if(saveit(true)){
//                sharkStartFrame.mainFrame.easywordlistpanel = null;
//                sharkStartFrame.mainFrame.setupgames();
//                db.closeAll();
//              }
 //           }
 //         }
//        });

        grid.gridx = -1;
        grid.gridy = 0;
        JPanel btpnlistsup = new JPanel(new GridBagLayout());
        grid.weightx = 1;
        JScrollPane jspnames = new JScrollPane(names);
        olistsup.add(jspnames, grid);
        grid.weightx = 0;
        olistsup.add(btpnlistsup, grid);
        grid.weightx = 1;
        buttondim = (sw*14/22)/24;
        int buttonimdim =  buttondim- (buttondim/5);
        but_namesname = u.sharkButton();
        but_namesname.setToolTipText(u.gettext("ownwordlists", "butrenamelisttooltip"));
        but_namesname.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namesname.setMinimumSize(new Dimension(buttondim, buttondim));
        Image im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "name_il48.png");
         ImageIcon iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namesname.setIcon(iistu);
         but_namesname.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   String nam = (String)names.getSelectedValue();
                   String oriname = nam;
                   if(db.query(dbname,(String)names.getSelectedValue(),db.TOPIC)<0 ){
                       String ww[] = words.getdata();
                       if(ww.length<1){
                           u.okmess(shark.programName, u.gettext("uwl_rename", "nowords"), thisd);
                           return;
                       }
                       else{
                            if(u.yesnomess(shark.programName,u.gettext("uwl_rename","saveneeded"), thisd)){
                                saveit(false);
                            }
                            else return;
                       }
                   }
                    else{
                        int res = saveit(true);
                        if(res==JOptionPane.CANCEL_OPTION){
                            return;
                        }                         
                    }

                   if(nam ==null)return;
                   String extratext = "";
                   int k;
                   if((k = nam.indexOf(u.phonicsplit))>=0){
                     nam = nam.substring(0, k).trim();
                     extratext = " " + oriname.substring(k).trim();
                   }
                   if(nam != null && !nam.equals("")) {
                     JOptionPane getpw = new JOptionPane(
                       u.gettext("uwl_rename","enter",nam),
                       JOptionPane.PLAIN_MESSAGE,
                       JOptionPane.OK_CANCEL_OPTION);
                     getpw.setWantsInput(true);
                     JDialog dialog2 = getpw.createDialog(sharkStartFrame.mainFrame,u.gettext("uwl_rename","heading"));
                     while(true) {
                      dialog2.setVisible(true);
                      Object result = getpw.getValue();
                      if(result == null
                          || result instanceof Integer
                          &&((Integer)result).intValue() != JOptionPane.OK_OPTION) return;
                      String newname = (String)getpw.getInputValue();
                      if(newname.length() == 0) continue;
                      newname += extratext;
                      if(db.query(dbname,newname,db.TOPIC) >= 0) {
                        u.okmess(u.gettext("uwl_rename","ren",nam), u.gettext("uwl_rename","already",newname), sharkStartFrame.mainFrame);
                          continue;
                      }
                     if(db.query(dbname,oriname,db.TOPIC) >=0) {
                        privateListRecord.listRenamed(dbname, nam, newname);
                        db.rename(dbname,oriname,newname,db.TOPIC);
                        db.rename(sharkStartFrame.resourcesdb,oriname,newname,db.TOPIC);
                        setnames();
//                        lastsel =u.findString(currnames, newname);
                        setupnewsel(newname);
                     }
                     refreshlist();
                      if(sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator) {
                        sharkStartFrame.mainFrame.gettopictreelist();
                      }
                      break;
                    }
                 }
              }
         });

         
         
         
        but_namessaveas = u.sharkButton();
        but_namessaveas.setToolTipText(u.gettext("ownwordlists", "butsaveastooltip"));
        but_namessaveas.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namessaveas.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "saveas_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namessaveas.setIcon(iistu);
         but_namessaveas.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                if(db.query(dbname,(String)names.getSelectedValue(),db.TOPIC)<0 ){
                    String ww[] = words.getdata();
                    if(ww.length<1){
                        u.okmess(shark.programName, u.gettext("uwl_saveas", "nowords"), thisd);
                        return;
                    }
                    else{
                        if(u.yesnomess(shark.programName,u.gettext("uwl_saveas","saveneeded"), thisd)){
                            saveit(false);
                        }
                        else return;
                    }
                } 
                else{
                    int res = saveit(true);
                    if(res==JOptionPane.CANCEL_OPTION){
                        return;
                    }                         
                }
               String op[] = new String[]{u.gettext("ok", "label"),u.gettext("cancel", "label")};
               JOptionPane getpw;
               getpw= new JOptionPane("Enter name for the new list:",JOptionPane.PLAIN_MESSAGE, 0, null, op, op[0]);
               getpw.setWantsInput(true);
               JDialog dialog;
               dialog = getpw.createDialog(thisd, "Save as");
               getpw.setInputValue("");
               String newimput = null;
               while(true) {
                  dialog.setVisible(true);
                  Object result = getpw.getValue();
                  String input = (String)getpw.getInputValue();
                  if(result==null || result == op[1] || input.equals(""))
                       return;
                  else if(input.length() > 0)  {  
                      int k;
                      String s1 = "";
                      if((k=currname.indexOf(u.phonicsplits))>0)
                        s1 = currname.substring(k-1);
                      newimput = input.concat(s1);
                      // is it in there already
                      for(int i = 0; i < names.getModel().getSize(); i++){
                          String ss = (String)names.getModel().getElementAt(i);
                          if(ss.equals(newimput)){
                              JOptionPane.showMessageDialog(thisd,"Name already in list",shark.programName,JOptionPane.WARNING_MESSAGE); 
                              continue;
                          }
                      }
                      break;
                  }                  
               }        
               if(newimput == null)return;
               saveTreeWordList stwl = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,currname,db.TOPIC);
               saveTree1 st1 = (saveTree1)db.find(dbname,currname,db.TOPIC);
               
               if(stwl!=null && stwl.names!=null && stwl.names.length>0)
                   stwl.names[0] = newimput;
               st1.curr.names[0] = newimput;

               if(stwl!=null)db.update(sharkStartFrame.resourcesdb,newimput, stwl, db.TOPIC);
               db.update(dbname, newimput, st1.curr, db.TOPIC);
               
               setnames();
//               lastsel =u.findString(currnames, newimput);
               setupnewsel(newimput);
       
               
             }
         });
         

        but_namestype = u.sharkButton();
        but_namestype.setToolTipText(u.gettext("ownwordlists", "buteditlisttooltip"));
        but_namestype.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namestype.setMinimumSize(new Dimension(buttondim, buttondim));
         im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "type_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namestype.setIcon(iistu);
         but_namestype.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   if(db.query(dbname,(String)names.getSelectedValue(),db.TOPIC)<0 ){
                       String ww[] = words.getdata();
                       if(ww.length<1){
 //                          u.okmess(shark.programName, u.gettext("uwl_type", "nowords"), thisd);
 //                          return;
                           saveit(false);
                       }
                       else{
                            if(u.yesnomess(shark.programName,u.gettext("uwl_type","saveneeded"), thisd)){
                                saveit(false);
                            }
                            else return;
                       }
                   }
                   
                else{
                    int res = saveit(true);
                    if(res==JOptionPane.CANCEL_OPTION){
                        return;
                    }                         
                }                   
                   String nam = (String)names.getSelectedValue();
                   if(nam ==null)return;
                   new editlist(thisd, nam);
              }
         });

         im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "edit_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
 //        but_namestype.setIcon(iistu);

        but_wordsedit = u.sharkButton();
        but_wordsedit.setToolTipText(u.gettext("ownwordlists", "buteditwordtooltip"));
        but_wordsedit.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsedit.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsedit.setIcon(iistu);
         but_wordsedit.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
              words.startEditingAtPath(words.getSelectionPath());
//             setbuttons();
              }
         });

        but_namesdel = u.sharkButton();
        but_namesdel.setToolTipText(u.gettext("ownwordlists", "butdellisttooltip"));
        but_namesdel.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namesdel.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "deleteON_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namesdel.setIcon(iistu);
         but_namesdel.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   int ii[];
                   if((ii = names.getSelectedIndices())!=null){
                       String ss = null;
                       if(ii.length>1 && !u.yesnomess(u.gettext("uwl_qdel","heading"),u.gettext("uwl_qdel","qp"), thisd))
                           return;
                      for(int i = ii.length-1; i>= 0; i--){    
                          currname = (String)names.getModel().getElementAt(ii[i]);
                          if(currname != null && !currname.equals("")
                                 && (ii.length>1?true:u.yesnomess(u.gettext("uwl_qdel","heading"),u.gettext("uwl_qdel","q", currname), thisd))){
                              db.delete(dbname,currname,db.TOPIC);
                              db.delete(sharkStartFrame.resourcesdb,currname,db.TOPIC);
                              int k = ii[i];
                              
                              

                              setnames();
                              names.clearSelection();
                              words.root.removeAllChildren();
                              words.model.reload();
                              
                              int b = names.getModel().getSize()-1;
                              if(b>=0){
                                  int g = Math.max(Math.min(k, b), 0); 
                                  ss = (String)names.getModel().getElementAt(g);
                              }
                              else ss = null;
                              
   
                              
                              privateListRecord.listDeleted(dbname, currname);
                              currname = "";
                          }
                      }
                              if(ss!=null)
                                setupnewsel(ss);
                              setbuttons();
                              if(sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator) {
                                sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics
                                                   = db.anyof(dbname,db.TOPIC);
                                if(sharkStartFrame.studentList[sharkStartFrame.currStudent].checkstu())           // rb 6/2/06
                                  student.checkadmin(sharkStartFrame.studentList[sharkStartFrame.currStudent]);   // rb 6/2/06
                                sharkStartFrame.mainFrame.gettopictreelist();
                              }                        
                   }
              }
         });

        but_wordsddel = u.sharkButton();
        but_wordsddel.setToolTipText(u.gettext("ownwordlists", "butdelwordtooltip"));
        but_wordsddel.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsddel.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsddel.setIcon(iistu);
         but_wordsddel.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
              words.stopEditing();
              words.delete();
              // remove pic / recording if not needed in other list
              if(otherwords!=null &&  u.findString(otherwords, currname)<0){
                 if(db.query(sharkStartFrame.resourcesdb, currname, db.PICTUREPLIST) >= 0)
                    db.delete(sharkStartFrame.resourcesdb, currname, db.PICTUREPLIST);
                 if(db.query(sharkStartFrame.resourcesdb, currname, db.WAV) >= 0)
                    db.delete(sharkStartFrame.resourcesdb, currname, db.WAV);
              }
              updateNewSaveTree();
              setbuttons();
              }
         });
         
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "up_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));         
        but_wordsup = u.sharkButton();
        but_wordsup.setToolTipText(u.gettext("ownwordlists", "butuptooltip"));
        but_wordsup.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsup.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsup.setIcon(iistu);
         but_wordsup.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    TreePath tp = words.getSelectionPath();
                    // if no node selected, show error msg and return
                    if(tp == null) {
                        return;
                    }
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)tp.getLastPathComponent();
                    DefaultTreeModel dtm = (DefaultTreeModel)words.getModel();
                    int index = dtm.getIndexOfChild(dtm.getRoot(), dmtn);
                    if(index != 0) {
                        dtm.insertNodeInto(dmtn, (DefaultMutableTreeNode)dtm.getRoot(), index-1);
                    }
                    else {
                        return;
                    }
                    dtm.reload();
                    words.setSelectionRow(index-1);
              }
         }); 

                 im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "down_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH)); 
        but_wordsdown = u.sharkButton();
        but_wordsdown.setToolTipText(u.gettext("ownwordlists", "butdowntooltip"));
        but_wordsdown.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsdown.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsdown.setIcon(iistu);
         but_wordsdown.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    TreePath tp = words.getSelectionPath();
                    // if no node selected, show error msg and return
                    if(tp == null) {
                        return;
                    }
                    DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)tp.getLastPathComponent();
                    DefaultTreeModel dtm = (DefaultTreeModel)words.getModel();
                    int index = dtm.getIndexOfChild(dtm.getRoot(), dmtn);
                    if(index != (words.getModel().getChildCount(words.root)-1)) {
                        dtm.insertNodeInto(dmtn, (DefaultMutableTreeNode)dtm.getRoot(), index+1);
                    }
                    else {
                        return;
                    }
                    dtm.reload();
                    words.setSelectionRow(index+1);                   
                   
              }
         });          
         
                  im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "print_il48.png"); 
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH)); 
        but_wordsprint = u.sharkButton();
        but_wordsprint.setToolTipText(u.gettext("ownwordlists","butprinttooltip"));
        but_wordsprint.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsprint.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsprint.setIcon(iistu);
         but_wordsprint.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent e) {
                  new PrintDialog(currname, words.getdata());
 //                 wordlist wl = new wordlist();
 //                 wl.setListData(words.getdata());
 //                 wl.setTopicName(currname);
 //                 wl.printlist(currname,wl.PRINT1);
              }
         });           
         
         
        but_namesimport = u.sharkButton();
        but_namesimport.setToolTipText(u.gettext("ownwordlists", "butimportlisttooltip"));
        but_namesimport.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namesimport.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "import_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namesimport.setIcon(iistu);
         but_namesimport.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
//                if(ImportList.active)return;
                int res = saveit(true);
                if(res==JOptionPane.CANCEL_OPTION){
                    return;
                } 
                setupRecord();
                ImportList il = new ImportList((OwnWordLists)thisd);
//                but_namesimport.setEnabled(false);
                
                sharkStartFrame.mainFrame.setState(sharkStartFrame.mainFrame.ICONIFIED);
                childFrame = il;
              }
         });
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "systemclip_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
        but_wordsimport = u.sharkButton();
        but_wordsimport.setToolTipText(u.gettext("ownwordlists", "butimportwordtooltip"));
        but_wordsimport.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsimport.setMinimumSize(new Dimension(buttondim, buttondim));
         but_wordsimport.setIcon(iistu);
         but_wordsimport.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
//startPR2011-03-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 //                  String s[] = u.getSystemClipboard();
                   stringedit_base se = new stringedit_base(u.gettext("ownwordlists", "clipboardtitle"), new String[]{}, thisd, stringedit_base.MODE_WORDLISTEDIT, false) {
                       public boolean update(String s[]) {
                           String alreadyin[] = new String[]{};
                           String existingw[] = words.getdata();
                         for(int i = s.length-1; i>=0; i--){
                             s[i] = s[i].trim();
                             if(s[i].equals("")){
                                 s = u.removeString(s, i);
                                 continue;
                             }
                             if(u.findString(alreadyin, s[i])>=0 || u.findString(existingw, s[i])>=0 ){
                                 s = u.removeString(s, i);
                                 continue;
                             }
                             alreadyin = u.addString(alreadyin, s[i]);
                         }
                         
                         String tot[] = u.addString(existingw, s);
                         words.putdata(tot);
                         
                         for(int i = existingw.length; i < tot.length; i++){
                             checkCurrs(tot[i], i+1);
                         }
                         
                         
                         
                         
                         setbuttons();
                         //  change extrarecs[][];
                         return true;
                       }
                     };
                     se.ignorexclick = true;
                     se.staythere = true;
                     
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              }
         });
        but_namesexport = u.sharkButton();
        but_namesexport.setToolTipText(u.gettext("ownwordlists", "butexportlisttooltip"));
        but_namesexport.setPreferredSize(new Dimension(buttondim, buttondim));
        but_namesexport.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "export_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_namesexport.setIcon(iistu);
         but_namesexport.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 saveit(true);
                 String exports[] = new String[]{};
                 Object os[] = names.getSelectedValues();
                 for(int i = 0; i < os.length; i++){
                     exports = u.addString(exports, (String)os[i]);
                 }
                 new exportlist(thisd, exports);
              }
         });


         JButton but_vids = u.sharkButton();
         but_vids.setPreferredSize(new Dimension(buttondim, buttondim));
         but_vids.setMinimumSize(new Dimension(buttondim, buttondim));
         but_vids.setToolTipText(u.gettext("videotutorials", "ownwordtooltip"));
         but_vids.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               new TutorialChoice_base(thisd,
                  new String[]{u.gettext("videotutorials", "addownlist")},
                  new String[]{u.gettext("videotutorials", "basesite", shark.getProgramShortName()) + u.gettext("mvidprivatelist", "url")});
           }
         });
         im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "video_il48.png");
         but_vids.setIcon(new ImageIcon(im.getScaledInstance(buttonimdim,
            buttonimdim, Image.SCALE_SMOOTH)));





        but_keypad = u.sharkButton();
        but_keypad.setToolTipText(u.gettext("ownwordlists", "butkeypadtooltip"));
        but_keypad.setPreferredSize(new Dimension(buttondim, buttondim));
        but_keypad.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "keypad_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_keypad.setIcon(iistu);
         but_keypad.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                    String tempkeypadname = set_keypadname;
                    new keypadsettings();
                    pnkeypad.setVisible(set_showkeypad);
                    if(set_keypadname!=null && !set_keypadname.equals(tempkeypadname) )
                        setupkeypad(set_keypadname);
              }
         });





        grid.gridx = 0;
        grid.gridy = -1;
         grid.insets = new Insets(10,10,0,10);
         grid.weighty = 0;
         grid.fill = GridBagConstraints.NONE;
         btpnlistsup.add(but_namesname, grid);
         btpnlistsup.add(but_namessaveas, grid);
         if(!shark.language.equals(shark.LANGUAGE_NL))
            btpnlistsup.add(but_namestype, grid);
         btpnlistsup.add(but_namesdel, grid);
         grid.insets = new Insets(buttondim,10,0,10);

         btpnlistsup.add(but_namesimport, grid);
         grid.insets = new Insets(10,10,0,10);
         btpnlistsup.add(but_namesexport, grid);


         grid.insets = new Insets(buttondim,10,0,10);
         btpnlistsup.add(but_vids, grid);
         grid.insets = new Insets(10,10,0,10);
         
         grid.insets = new Insets(0,0,0,0);
         grid.weighty = 1;
         btpnlistsup.add(new JPanel(), grid);
         grid.fill = GridBagConstraints.BOTH;


        JPanel btpnwordsup = new JPanel(new GridBagLayout());
        but_wordsadd = u.sharkButton();
        but_wordsadd.setToolTipText(u.gettext("ownwordlists", "butaddwordtooltip"));
        but_wordsadd.setPreferredSize(new Dimension(buttondim, buttondim));
        but_wordsadd.setMinimumSize(new Dimension(buttondim, buttondim));
        im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                           sharkStartFrame.separator +
                                           "addtext_il48.png");
         iistu = new ImageIcon(im.getScaledInstance(buttonimdim,
                buttonimdim, Image.SCALE_SMOOTH));
         but_wordsadd.setIcon(iistu);
         but_wordsadd.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                   if(words.getSelectionPath()==null){
                      words.putdata(u.addString(words.getdata(),new String("")));
                      words.startEditingAtPath(new TreePath(((jnode)words.root.getLastChild()).getPath()));
                   }
                   else{
//                      words.stopEditing();
//                      words.insert();
//                      words.startEditingAtPath(words.getSelectionPath());
                       int ii = words.getSelectionRows()[0]+1;

                       words.model.insertNodeInto(new jnode(), words.root, ii);

                       words.model.reload();
                       words.startEditingAtPath(words.getPathForRow(ii));
                   }
                  setbuttons();
              }
         });


         
         sp = new JScrollPane(words);
         sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
         sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

         lbrec = new JLabel(u.gettext("ownwordlists", "soundlabel"));
         lbrectrans = new JLabel(u.gettext("ownwordlists", "soundlabel_trans"));
         JLabel lbim = new JLabel(u.gettext("ownwordlists", "imagelabel"));
         lbtrans = new JLabel(u.gettext("ownwordlists", "translationlabel"));
         lbdef = new JLabel(u.gettext("ownwordlists", "definitionlabel"));



         
         lbrec.setFont(smallerplainfont);
         lbrectrans.setFont(smallerplainfont);
         lbim.setFont(smallerplainfont);
         lbtrans.setFont(smallerplainfont);
         lbdef.setFont(smallerplainfont);



         JPanel pnlbrec = new JPanel(new GridBagLayout());
         JPanel pnlbim = new JPanel(new GridBagLayout());
         JPanel pnlbtrans = new JPanel(new GridBagLayout());



         pnlbrec.setBackground(Color.gray);
         lbrec.setForeground(Color.white);
         lbrectrans.setForeground(Color.white);
         pnlbim.setBackground(Color.gray);
         lbim.setForeground(Color.white);
         pnlbtrans.setBackground(Color.gray);
         lbtrans.setForeground(Color.white);
         lbdef.setForeground(Color.white);
         lbrec.setOpaque(false);
         lbrectrans.setOpaque(false);
         lbim.setOpaque(false);
         lbtrans.setOpaque(false);
         lbdef.setOpaque(false);

         pnlbrec.setOpaque(true);
         pnlbim.setOpaque(true);
         pnlbtrans.setOpaque(true);



         grid.gridx = -1;
         grid.gridy = 0;
         grid.insets = new Insets(0, 3, 0, 3);
         grid.fill = GridBagConstraints.NONE;
         pnlbrec.add(lbrec, grid);
         pnlbrec.add(lbrectrans, grid);
         pnlbim.add(lbim, grid);

         grid.insets = new Insets(0, 3, 0, 3);
         pnlbtrans.add(lbtrans, grid);
         pnlbtrans.add(lbdef, grid);

         grid.fill = GridBagConstraints.BOTH;
         grid.insets = new Insets(0, 0, 0, 0);
         collabels = new JPanel[]{pnlbrec,pnlbim,pnlbtrans};
         grid.gridx = -1;
         grid.gridy = 0;
         JPanel lbpns = new JPanel(new GridBagLayout());
         grid.weighty = 0;
         grid.weightx = 1;
         lbpns.add(new JPanel(), grid);
         grid.weightx = 0;
         lbpns.add(pnlbrec, grid);
         lbpns.add(pnlbim, grid);
         grid.insets = new Insets(0, 0, 0, swidth);
         lbpns.add(pnlbtrans, grid);
         grid.insets = new Insets(0, 0, 0, 0);

         grid.weighty = 1;

         grid.weightx = 1;
         grid.gridx = 0;
         grid.gridy = -1;
         pnwordskeypad = new JPanel(new GridBagLayout());
         grid.weighty = 0;
         pnwordskeypad.add(lbpns, grid);
         grid.weighty = 1;
         pnwordskeypad.add(sp, grid);

         pnkeypad = new JPanel(new GridBagLayout());
         pnkeypad.setBorder(BorderFactory.createEmptyBorder());



         grid.weighty = 0;
         pnwordskeypad.add(pnkeypad, grid);
         grid.weighty = 1;
         grid.gridx = -1;
         grid.gridy = 0;
         grid.weightx = 1;
         owordsup.add(pnwordskeypad, grid);
         grid.weightx = 0;
         owordsup.add(btpnwordsup, grid);
         grid.fill = GridBagConstraints.NONE;
         grid.gridx = 0;
         grid.gridy = -1;
         grid.weighty = 0;
         grid.insets = new Insets(10,10,0,10);
         btpnwordsup.add(but_wordsadd, grid);
         btpnwordsup.add(but_wordsedit, grid);
         btpnwordsup.add(but_wordsup, grid);
         btpnwordsup.add(but_wordsdown, grid);
         btpnwordsup.add(but_wordsddel, grid);
         grid.insets = new Insets(buttondim,10,0,10);
         btpnwordsup.add(but_wordsprint, grid);
         btpnwordsup.add(but_wordsimport, grid);

         grid.weighty = 1;
         btpnwordsup.add(new JPanel(), grid);
         grid.weighty = 0;
         grid.insets = new Insets(10,10,10,10);
         btpnwordsup.add(but_keypad, grid);
         grid.weighty = 1;
         grid.fill = GridBagConstraints.BOTH;
         grid.insets = new Insets(0,0,0,0);
         grid.weightx = 1;
         grid.gridx = 0;
         grid.gridy = -1;
        

//bglisten2
         rbpic = new JRadioButton();
         rbpic.setText(u.gettext("ownwordlists", "changepic"));
         rbpic.addItemListener(bglisten2);
         rbpic.setOpaque(false);
         rbrec = new JRadioButton();
         rbrec.setText(u.gettext("ownwordlists", "changerec"));
         rbrec.addItemListener(bglisten2);
         rbrec.setOpaque(false);
         bg2.add(rbrec);
         bg2.add(rbpic);

         grid.anchor = GridBagConstraints.WEST;
         JPanel radiopn = new JPanel(new GridBagLayout());
         oresup.setBackground(Color.white);
         oresup.setOpaque(true);
         radiopn.setOpaque(false);
         grid.anchor = GridBagConstraints.WEST;
         grid.fill = GridBagConstraints.NONE;
         radiopn.add(rbrec, grid);
         radiopn.add(rbpic, grid);

        grid.fill = GridBagConstraints.NONE;
        grid.weighty = 0;

        JPanel radiopn2 = new JPanel(new GridBagLayout());

        grid.anchor = GridBagConstraints.CENTER;
        radiopn2.add(radiopn, grid);
        grid.fill = GridBagConstraints.HORIZONTAL;
        if(allowsetpicture){
            oresup.add(radiopn2, grid);
        }
        grid.fill = GridBagConstraints.NONE;
        radiopn2.setBorder(BorderFactory.createEtchedBorder());


        radiopn2.setBackground(Color.white);
        radiopn2.setOpaque(true);

        grid.weighty = 1;
        grid.fill = GridBagConstraints.BOTH;
        resblankpn = new JPanel(new GridBagLayout());
        respicpn = new JPanel(new GridBagLayout());
        resrecpan = new JPanel(new GridBagLayout());
        respicpn.setBackground(sharkStartFrame.col2);
        respicpn.setOpaque(true);
        resrecpan.setBackground(sharkStartFrame.col2);
        resrecpan.setOpaque(true);
        grid.weightx = 1;
        grid.fill = GridBagConstraints.HORIZONTAL;

        JPanel recordingcontainpan = new JPanel(new GridBagLayout());
        JPanel recordinglbpan = new JPanel(new GridBagLayout());
        JPanel recordingpan = new JPanel(new GridBagLayout());

        rectool = new recordtool(this, currtype);

        JLabel rclb = new JLabel(u.gettext("ownwordlists", "recordnew"));
        recordinglbpan.setBackground(sharkStartFrame.col2);
        recordinglbpan.setOpaque(true);
        int b;
        if(u.screenResHeightMoreThan(800))b = 30;
        else if(u.screenResHeightMoreThan(700))b = 10;
        else b =0;
        grid.insets = new Insets(10,10,b,10);
        recordinglbpan.add(rclb, grid);
        recordingpan.add(mainrecpan = rectool.makeRecPanel1(), grid);
        recordingpan.add(xtrarecpan = rectool.makeRecPanel2(), grid);
        recordingpan.setBorder(BorderFactory.createEtchedBorder());
        grid.insets = new Insets(0,0,0,0);
        recordingcontainpan.add(recordinglbpan, grid);
        recordingcontainpan.add(recordingpan, grid);

        JPanel controlcontainpan = new JPanel(new GridBagLayout());
        JPanel controllbpan = new JPanel(new GridBagLayout());
        JPanel controlpan = new JPanel(new GridBagLayout());

        int b2;
        if(u.screenResHeightMoreThan(800))b2 = 20;
        else if(u.screenResHeightMoreThan(700))b2 = 10;
        else b2 =0;        

        JLabel reclb = new JLabel(u.gettext("ownwordlists", "recordsettings"));
        controllbpan.setBackground(sharkStartFrame.col2);
        controllbpan.setOpaque(true);
        grid.insets = new Insets(b2,10,b2,10);
        controllbpan.add(reclb, grid);
        grid.insets = new Insets(0,0,0,0);
        controlpan.add(rectool.makeControlPanel(), grid);

        controlpan.setBorder(BorderFactory.createEtchedBorder());
        controlcontainpan.add(controllbpan, grid);
        controlcontainpan.add(controlpan, grid);

        grid.insets = new Insets(b2,10,b2,10);
        resrecpan.add(recordingcontainpan, grid);
        resrecpan.add(controlcontainpan, grid);
        grid.insets = new Insets(0,0,0,0);
        resblankpn.setBackground(Color.white);
        resblankpn.setOpaque(true);

  //   int dimh = (sharkStartFrame.screenSize.height/3)/4;
        int dimh = (int)xresdown.getPreferredSize().getWidth()*2/3;
        dropBox = new mainPan(new Dimension(dimh,dimh));

        
        
    
        

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
        int returnVal = fc.showOpenDialog(thisd);
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
     boxDelete = u.sharkButton();
     boxDelete.setFont(plainfont);
     boxDelete.addActionListener( new java.awt.event.ActionListener() {
           public void actionPerformed(ActionEvent e) {     
               for(int i = 1; workingList!=null && workingList.names!=null && i < workingList.names.length; i++){
                   if(workingList.names[i].equals(curr_word)){
                       int p = indexOfPicName(availablepics_all, curr_word);
                       if(p>=0){
                           picitem pi = (picitem)availablepics_all.get(p);
                           int k;
                           if((k=pi.indexOfBytes(workingList.pics[i]))>=0){
                               pi.removeBytes(k);
                           }
                           availablepics_all.set(p, pi);  // is this needed?
                           workingList.pics[i] = pi.getNextPic();
                           doChangePicture(0);
                       }
                       break;
                   }
               }                  
               dropBox.wp.reposition();
               boolean b = !dropBox.wp.addPic(curr_word, false);
//               dropBox.wp.mainPanel.pause = b;
               setPicPanelPause(b);
           }
     });
     boxDelete.setText(u.gettext("delete", "label"));
     
     
     int smallerdim = (int)boxDelete.getPreferredSize().getHeight();
     
     
     butNextImage = u.sharkButton();
     butNextImage.addActionListener( new java.awt.event.ActionListener() {      
         public void actionPerformed(ActionEvent e) {             
             doChangePicture(-1);             
         }
     });
     
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "left_il48.png");
     butNextImage.setIcon(new ImageIcon(im.getScaledInstance(smallerdim,
         smallerdim, Image.SCALE_SMOOTH)));     
     
//     butNextImage.setToolTipText(u.gettext("ownwordlists", "butkeypadtooltip"));
     butNextImage.setPreferredSize(new Dimension(smallerdim, smallerdim));
     butNextImage.setMinimumSize(new Dimension(smallerdim, smallerdim));      
     
     
     butNextImage2 = u.sharkButton();
     butNextImage2.addActionListener( new java.awt.event.ActionListener() {      
         public void actionPerformed(ActionEvent e) {               
               doChangePicture(1);
         }
     });
     
     im = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "right_il48.png");
     butNextImage2.setIcon(new ImageIcon(im.getScaledInstance(smallerdim,
         smallerdim, Image.SCALE_SMOOTH)));     
     
//     butNextImage2.setToolTipText(u.gettext("ownwordlists", "butkeypadtooltip"));
     butNextImage2.setPreferredSize(new Dimension(smallerdim, smallerdim));
     butNextImage2.setMinimumSize(new Dimension(smallerdim, smallerdim));     
     
     grid.fill = GridBagConstraints.NONE;



     grid.gridx =-1;
     grid.gridy =0;
     JPanel picbtpn = new JPanel(new GridBagLayout());
     grid.fill = GridBagConstraints.BOTH;
     grid.weightx = 0;
     grid.insets = new Insets(0,0,0,10);
     picbtpn.add(boxBrowse, grid);
     picbtpn.add(butNextImage, grid);
     picbtpn.add(butNextImage2, grid);
     grid.insets = new Insets(0,0,0,0);
     
     
     grid.weightx = 1;
     grid.fill = GridBagConstraints.HORIZONTAL;
     JPanel filler1 = new JPanel(new GridBagLayout());
     picbtpn.add(filler1, grid);
     grid.weightx = 0;
     
     picbtpn.add(boxDelete, grid);
     grid.fill = GridBagConstraints.NONE;


     grid.gridx =0;
     grid.gridy =-1;
     JPanel picpn = new JPanel(new GridBagLayout());
     picpn.add(dropBox, grid);
     grid.fill = GridBagConstraints.HORIZONTAL;
     grid.insets = new Insets(10,0,0,0);
     picpn.add(picbtpn, grid);
     grid.insets = new Insets(0,0,0,0);
     grid.fill = GridBagConstraints.NONE;
     respicpn.add(picpn, grid);

     picbtpn.setOpaque(false);
     picpn.setOpaque(false);
     filler1.setOpaque(false);

     grid.weightx = 1;
        grid.fill = GridBagConstraints.BOTH;
        oresup.add(resblankpn, grid);
        oresup.add(respicpn, grid);
        oresup.add(resrecpan, grid);

        oresup.setBackground(Color.red);

        resblankpn.setVisible(true);
        respicpn.setVisible(false);
        resrecpan.setVisible(false);

        grid.weightx = 1;
        grid.weighty = 1;
        xlists.add(xlistsup, grid);
        grid.weighty = 0;
        xlists.add(xlistsdown, grid);
        grid.weighty = 1;
        xwords.add(xwordsup, grid);
        xwords.add(xblankmid, grid);
        grid.weighty = 0;
        xwords.add(xwordsdown, grid);
        grid.weighty = 1;
        xres.add(xresup, grid);
        xres.add(xblankright, grid);

        grid.weighty = 0;
        xres.add(xresdown, grid);
        grid.weighty = 1;




        xlistsup.setBorder(BorderFactory.createLineBorder(green, borderthick));
        xwordsup.setBorder(BorderFactory.createLineBorder(sharkStartFrame.topictreecolor, borderthick));
        xresup.setBorder(BorderFactory.createLineBorder(Color.lightGray, borderthick));

        grid.gridx = -1;
        grid.gridy = 0;
        this.getContentPane().add(xlists, grid);
        this.getContentPane().add(xwords, grid);
        this.getContentPane().add(xres, grid);
        
 //       this.getContentPane().add(xblankright, grid);

xblankmid.setBackground(Color.white);
xblankmid.setOpaque(true);
xblankright.setBackground(Color.white);
xblankright.setOpaque(true);
//xwordsup.setVisible(false);
xresup.setVisible(false);


if(names.getModel().getSize()>0){
    names.setSelectedIndex(0);
}
   blankmidon(true);
   blankrighton(true); 

setbuttons();

        if(!allowsetpicture){
           editrecon();
        }

        this.setTitle(u.gettext("mownwords", "label"));
        this.setResizable(false);
        this.validate();
        setVisible(true); 
    }

    

    void doChangePicture(int inc){
             int k;
             if((k=indexOfPicName(availablepics_all, curr_word)) >=0){
                 picitem pi = (picitem)availablepics_all.get(k);
                 byte nextpic[] = null;
                 String secondsharki = null;
                 int currwordindex = -1;
                 for(int i = 1; workingList.names!=null && i < workingList.names.length; i++){
                    if(workingList.names[i].equals(curr_word)){
                        currwordindex = i;
                        break;
                    }
                 }                
                 
                 if(currwordindex>=0 && inc!=0){
                     workingList.preferredPic[currwordindex] = null;
                     workingList.pics[currwordindex] = null;
                 }
                 
                 if(pi.bytes!=null){
                    int size = pi.bytes.size();
                    
                    
                    if(inc>0)dropBox.wp.currentbn++;
                    else if(inc<0) dropBox.wp.currentbn--;
        
                    if(dropBox.wp.currentbn>=size){
                        dropBox.wp.currentbn = size-1;
//                        if(haspublicpic!=null && haspublicpic.contains(curr_word))nextpic = null;
//                        else nextpic = (byte[])pi.bytes.get(0);  
                    }
                    else if (dropBox.wp.currentbn < 0){
                        dropBox.wp.currentbn = 0;
//                        if(pi.bytes.size()>0){
//                            if(pi.bytes.get(dropBox.wp.currentbn) instanceof String){
//                                String ss;
//                                if(!((ss = (String)pi.bytes.get(dropBox.wp.currentbn)).equals(curr_word))){
//                                    secondsharki = ss;
//                                }
//                            }
//                        }
                    }
                    if(size > dropBox.wp.currentbn && dropBox.wp.currentbn>=0){
                        Object s2;
                        if(pi.bytes.get(dropBox.wp.currentbn) instanceof byte[]){
                            nextpic = (byte[])pi.bytes.get(dropBox.wp.currentbn);
                        }
    //                    else if(pi.bytes.get(dropBox.wp.currentbn) instanceof String && pi.bytes.get(dropBox.wp.currentbn).equals(curr_word)){
    //                        secondsharki = (String)pi.bytes.get(dropBox.wp.currentbn);
    //                    }
    //                    else{
    //                        secondsharki = (String)pi.bytes.get(dropBox.wp.currentbn);
    //                    }

//                        else if(pi.bytes.get(dropBox.wp.currentbn) instanceof String && dropBox.wp.currentbn>0){
                        else if((s2=pi.bytes.get(dropBox.wp.currentbn))   instanceof String){
                              if(availablepics_default!=null && !availablepics_default.equals((String)s2))  // don't have preferred im if it's going to be the default anyway
                                secondsharki = (String)s2;
                        }
                    }

                    
                    if(currwordindex>=0){
                        dropBox.wp.showsaved = false;
                        if(inc!=0){
                            workingList.pics[currwordindex] = nextpic;
                            workingList.preferredPic[currwordindex] = secondsharki;
                        }
                        converted(secondsharki!=null?secondsharki:curr_word, inc);
                    }                 
                    
             }   
             }
    }
        
    sharkImage.saveSharkImage getsss(String word){
        sharkImage.saveSharkImage im;
        for(int i = 0;i < sharkStartFrame.publicImageLib.length;++i) {
            if((im = (sharkImage.saveSharkImage)db.find(sharkStartFrame.publicImageLib[i],word,db.IMAGE)) != null) {
                return im;
            }
         }  
        return null;
    }
  
    
    // add the resources in the current list to the base
    void setupAvailables(String currword){

      currImportedIm = null;
      availablepics_all = (ArrayList)availablepics_base.clone(); 
//      arlistrec_words_all = (ArrayList)arlistrec_words_base.clone();
 //     arlistpic_words_all = (ArrayList)arlistpic_words_base.clone();
      availablepics_default = null;
      String ss[] = sharkImage.findAll(currword, null, true, false, null, true);
      ArrayList alt = new ArrayList();
      if(ss!=null){
        // newer pictures (@@ etc) shown first
        loop1: for(int i = 0; i < ss.length; i++){
            // remove duplicates
            sharkImage.saveSharkImage im1 = getsss(ss[i]);
//            for(int j = 0; j < ss.length; j++){
            for(int j = 0; j < alt.size(); j++){
//                if(j == i)continue;
//                sharkImage.saveSharkImage im2 = getsss(ss[j]);
                sharkImage.saveSharkImage im2 = getsss((String)alt.get(j));
                if(!sharkImage.addnew(im1,im2)){   
                    continue loop1;
                }
            }
            alt.add(ss[i]);
            if(availablepics_default==null)availablepics_default = ss[i];
        }  
        int k;
        if((k = indexOfPicName(availablepics_all, currword)) < 0){
            availablepics_all.add(new picitem(currword, alt));
        }
        else{
            picitem pi = (picitem)availablepics_all.get(k);
            for(int i = alt.size() - 1; i >= 0; i--){
               pi.addStringAt(((String)alt.get(i)), 0); 
            }
            availablepics_all.set(k, pi);
        }
      }      
      currrecs = new ArrayList();
      boolean currhasimportedim = false;
      dropBox.wp.currentbn = -1;
      if(workingList!=null){
              for(int i = 1; workingList.names!=null && i < workingList.names.length; i++){
                  int k;  
                  boolean currWord = workingList.names[i].equals(currword);
                  if(workingList.recs[i]!=null){
                      currrecs.add(new recitem(workingList.names[i], workingList.recs[i]));
                  } 
                  if(workingList.pics[i]!=null){
                      if((k=indexOfPicName(availablepics_all, workingList.names[i])) >=0){
                          picitem pi = (picitem)availablepics_all.get(k);
                          if(pi.indexOfBytes(workingList.pics[i])<0){             
                              pi.add(workingList.pics[i]);
                              availablepics_all.set(k, pi);
                              if(currWord)dropBox.wp.currentbn = pi.bytes.size()-1;                             
                          }
                       }
                       else{
                          // adding the imported image
                          ArrayList al = new ArrayList();
                          al.add(workingList.pics[i]);
                          availablepics_all.add(new picitem(workingList.names[i], al));
                          if(currWord)dropBox.wp.currentbn = 0;
                       }
                      if(currWord){
                          currhasimportedim = true;
                          currImportedIm = workingList.pics[i];
                      }
                  }

                  if(!currhasimportedim && currWord){
                      if(workingList.preferredPic!=null && workingList.preferredPic[i]!=null){
                          for(int j = 0; j < availablepics_all.size(); j++){
                              picitem pi = (picitem)availablepics_all.get(j);
                              for(int m = 0; m < pi.bytes.size(); m++){
                                  if(workingList.preferredPic[i].equals(pi.bytes.get(m))){
                                      dropBox.wp.currentbn = m;
                                  }
                              }
                          }
                      }
/*                      else{
                          k = indexOfPicName(availablepics_all, curr_word);
                          if(k>=0){
                            picitem pi = (picitem)availablepics_all.get(k);
                            for(int j = 0; j < pi.bytes.size(); j++){
                                if(curr_word.equals(pi.bytes.get((j)))){
                                    dropBox.wp.currentbn = j;
                                    break;
                                }
                            }
                          }
                      }*/
                  }  
              }
         }  
      doChangePicture(0);
    }
    
    

   
    void wordsValueChanged(TreePath i){
               jnode jn;
               if(i==null){
                   blankrighton(true);
                   return;
               }
               else if((jn=((jnode)i.getLastPathComponent()))==null || jn.get().trim().equals("")){
                   blankrighton(true);
                   return;
               }
               else{
                   blankrighton(false);
                   curr_word = jn.get();
                   if(!words.isEditing()){
//                       lastFixedSelectedWord = curr_word;
                       lastFixedSelectedWordIndex = words.getSelectionRows()[0];
                   }
                   setupAvailables(curr_word);
                   dropBox.wp.stop();
                   rectool.setupword(curr_word);
                   boolean b = !dropBox.wp.addPic(curr_word, false);
//                   dropBox.wp.mainPanel.pause = b;
                   setPicPanelPause(b);
                   lbtheword.setText(theword.replaceFirst("%", curr_word));
//                   if(!rbpic.isSelected())dropBox.wp.mainPanel.pause = true;
               }
    }
    
    
    void setPicPanelPause(boolean on){
 //       if(!rbpic.isSelected())dropBox.wp.mainPanel.pause = true;
        if(!rbpic.isSelected() ||xblankright.isVisible() )dropBox.wp.mainPanel.pause = true;
        else dropBox.wp.mainPanel.pause = on;
    }   
    
    //build list of resources from user's lists
    void setupRecord(){

        

//      arlistrec_words_all = new ArrayList(); 
//      arlistpic_words_all = new ArrayList(); 
//      arlistrec_words_base = new ArrayList();
//      arlistpic_words_base = new ArrayList(); 

      availablerecs = new ArrayList(); 
      availablepics_all = new ArrayList(); 
      availablepics_base = new ArrayList();

      
      String topicnames[] = db.list(sharkStartFrame.resourcesdb, db.TOPIC);
      loop1: for(int i = 0; topicnames!=null && i < topicnames.length; i++){
          saveTreeWordList newsave = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,topicnames[i],db.TOPIC);
          if(newsave!=null && newsave.names!=null && newsave.names.length>0 &&
              oriList!=null && oriList.names!=null && oriList.names.length>0
                  ){
              if(newsave.names[0].equals(oriList.names[0]))continue loop1;
          }

          for(int a = 1; newsave!=null && newsave.names!=null && a < newsave.names.length; a++){
              if(newsave.recs!=null && newsave.recs[a]!=null){
                 boolean found = false;
                 for(int n = 0; n < availablerecs.size(); n++){
                     if(((recitem)availablerecs.get(n)).name.equals(newsave.names[a])){
                         found = true;
                         break;
                     }
                 }
                 if(!found) availablerecs.add(new recitem(newsave.names[a], newsave.recs[a]));
              }
              if(newsave.pics!=null && newsave.pics[a]!=null){
                  

                int j;
                if((j=indexOfPicName(availablepics_base, newsave.names[a]))>=0){
                    picitem pi = (picitem)availablepics_base.get(j);
                    if(pi.indexOfBytes(newsave.pics[a])<0){ 
                        pi.add(newsave.pics[a]);
                    }
                    availablepics_base.set(j, pi);// is this needed?
                }
                else{
                    ArrayList l = new ArrayList();
                    l.add(newsave.pics[a]);
                    availablepics_base.add(new picitem(newsave.names[a], l));
                }
              }       
//              checkPublicResource(newsave.names[a]);
          }
      }     
      availablepics_all = (ArrayList)availablepics_base.clone();
    }

    
    void checkPublicResource(String s){
        if(s==null)return;
           sharkImage si = sharkImage.find(null, s, true, true);  // end param should be true - otherwise don't get the @@ words
           if(si!=null){
               if(!haspublicpic.contains(s))
                 haspublicpic.add(s);
           }          
          for(int j = 0; j < sharkStartFrame.publicSoundLib.length; j++){
              if (db.findwav(sharkStartFrame.publicSoundLib[j], s) !=null) {   
                  if(!haspublicrec.contains(s))
                        haspublicrec.add(s);
                 break;
              }            
          }            
    }
    
    public void checkCurrs(String newstr, int i){
        curr_word = newstr;
    //    currImportedIm = null;
        updateNewSaveTree();
        setupAvailables(newstr);
        checkPublicResource(newstr);
        if(workingList.recs[i]==null){
           for(int n = 0; n < availablerecs.size(); n++){
               recitem ri;
               if((ri = (recitem)availablerecs.get(n)).name.equals(newstr)){
                   workingList.recs[i] = ri.rec;
                   break;
               }
           }
        }
        if(workingList.pics[i]==null){
           int k;
           if((k=indexOfPicName(availablepics_all, workingList.names[i])) >=0){
               picitem pi = (picitem)availablepics_all.get(k);
               if(pi.bytes!=null && pi.bytes.size()>0){
                   Object s2;
                   if(pi.bytes.get(0) instanceof byte[])
                    workingList.pics[i] = (byte[])pi.bytes.get(0);
                   else if ((s2=pi.bytes.get(0)) instanceof String){
                       if(availablepics_default!=null && !availablepics_default.equals((String)s2)) // don't have preferred im if it's going to be the default anyway
                        workingList.preferredPic[i] = (String)s2;
                   }
               }
                
           }                        
        }
    }
    
 //   void nameselected(boolean newlist){
 //       nameselected(newlist, false);
 //   }

    void nameselected(boolean newlist){
        
               int sel = names.getSelectedIndex();
               if(sel<0){
                   blankmidon(true);
                   blankrighton(true);
                   return;
               }
    //           blankmidon(false);
    //           xwordsup.setVisible(true);
    //           xblankmid.setVisible(false);
    //           dropBox.wp.mainPanel.pause = true;
               setPicPanelPause(true);

//            int prev = lastsel;

            if(!newlist && sel != -1 ) {
//               lastsel = sel;
               String newname = (String)names.getSelectedValue();
               int res = saveit(true);
               if(res==JOptionPane.CANCEL_OPTION){
                   names.setSelectedValue(currname, true);
//                   lastsel = prev;
                   return;
               }

               if(res==JOptionPane.NO_OPTION){
                   String ss[] = null;
                   for(int i = 0; i < currnames.length; i++){
                       if(!currnames[i].equals(currname)){
                           if(ss==null)ss = new String[]{currnames[i]};
                           else ss = u.addString(ss, currnames[i]);
                       }
                   }
  //                 names.setListData(ss);
               }
 //              xwordsup.setVisible(true);
 //              xblankmid.setVisible(false);
               boolean namesmult = names.getSelectedIndices().length>1;
               if(!namesmult){

/*
                    ListSelectionListener ls[] =    names.getListSelectionListeners();
                    if(ls!=null && ls.length>0) names.removeListSelectionListener(ls[0]);
                    setnames();
                    if(ls!=null && ls.length>0) names.addListSelectionListener(ls[0]);

*/
                   
                   setupnewsel(newname);
                   if(words.count()>0)
                    words.setSelectionRow(0);
               }
               
            }

    }

    public void setnames(){
        setnames(true);
    }    
    
    public void setnames(boolean deleteEmptyLists){
//        String namelist[] = db.list(dbname,db.TOPIC);
//        allwords = null;
//        currnames = new String[0];
//        for(int i=0;i<namelist.length;++i) {
//           saveTree1 st = (saveTree1)db.find(dbname, namelist[i],db.TOPIC);
//           st.curr.names[0] ="aa";   // in case a funny name
//           if(iseasy(st.curr.names))
//               currnames = u.addString(currnames,namelist[i]);
//           if(st.curr.names!=null && st.curr.names.length>1){
//               if(allwords==null)allwords = new String[]{};
//               String tstr[] = new String[st.curr.names.length-1];
//               System.arraycopy(st.curr.names,1,tstr,0,st.curr.names.length-1);
//               allwords = u.addString(allwords, tstr);
//           }
//        }
//        lastsel = -1;
        fillWordData(deleteEmptyLists);
        names.setListData(currnames);

    }

    void end(){
      if(saveit(true)!=JOptionPane.CANCEL_OPTION){



       //               if(sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator) {
                        sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics
                                           = db.anyof(sharkStartFrame.studentList[sharkStartFrame.currStudent].name,db.TOPIC);
                        sharkStartFrame.studentList[sharkStartFrame.currStudent].checkstu();//          // rb 6/2/06
                        student.checkadmin(sharkStartFrame.studentList[sharkStartFrame.currStudent]);   // rb 6/2/06
                        
          //            }



        sharkStartFrame.mainFrame.setupgames();
        workingList = null;
        spokenWord.currrecord = null;
        spokenWord.currrectool = null;
       db.closeAll();
        thisd.dispose();
      }
    }


    void removeunused(){
        if(!shark.debug)return;
        String ss[] = db.list(sharkStartFrame.resourcesdb, db.PICTUREPLIST);
        for(int i = 0; i < ss.length; i++){
            if(u.findString(allwords, ss[i])<0){
                db.delete(sharkStartFrame.resourcesdb, ss[i], db.PICTUREPLIST);
            }
        }
        ss = db.list(sharkStartFrame.resourcesdb, db.WAV);
        for(int i = 0; i < ss.length; i++){
            if(u.findString(allwords, ss[i])<0){
                 db.delete(sharkStartFrame.resourcesdb, ss[i], db.WAV);
            }
        }
    }

      boolean iseasy(String words[]) {
         short i,j;
         if(words==null)return false;
         for(i=0;i<words.length;++i) {
            if(words[i].length() == 0) return false;
            for(j=0; j<words[i].length();++j) {
               if(topic.getTypePos(words[i]) >= 0) return false;
            }
         }
         return true;
     }
     boolean iseasy(String s) {
        return iseasy(new String[] {s});
     }
     
     void setupnewsel(String newname){
         currname = newname;
         names.setSelectedValue(newname, false);
//         currname = newname;
         newlist(0);
         setbuttons();
     }

     public void stopWordsEdit() {
         if(words.isEditing()){
             words.simpleStopEdit = true;
             words.stopEditing();
             words.simpleStopEdit = false;

             jnode jn = (jnode)words.model.getChild(words.root, words.oldselection);
             if(jn !=null){
                 wordsValueChanged(new TreePath(jn.getPath()));
             }


  //           
  //           rectool.setupword(jn.get());
         }
     }
     
     boolean onEditExistingWord(String prevWord, String newWord, byte[] impIm){
         if(prevWord== null || newWord == null || prevWord.equals(newWord))return false;
         
         if(workingList!=null && workingList.names !=null && workingList.names.length>1 && impIm!=null){
             int currindex = -1;
             for(int i = 1; i < workingList.names.length; i++){
                 if(workingList.names[i].equals(newWord)){
                     currindex = i;
                     break;
                 }
             }
             if(u.yesnomess(shark.programName, u.convertToHtml(u.gettext("ownwordlists", "keepimportedim")), thisd)){    
                 workingList.pics[currindex] = impIm;
             }
         }
         return true;
     }

     public void setbuttons() {
         
               int ii[];        
               boolean mult = false;
                if((ii = names.getSelectedIndices())!=null && ii.length > 1) 
                    mult = true;
//                int namescount = names.getModel().getSize();
 //               int wnamescount = words.count();


                blankmidon(names.getSelectedIndex() < 0 || (currname==null||currname.trim().equals("")) || mult);
                blankrighton(names.getSelectedIndex() < 0 || (currname==null||currname.trim().equals("")) || mult || (words.getSelectionCount()==0 || words.isEditing() ||
                        
                        words.getSelectionPath() == null || words.getSelectionPath().getLastPathComponent() == null  || ((jnode)words.getSelectionPath().getLastPathComponent()).get().trim().equals("")
                        
                        ));

 //               blankmidon(wnamescount ==0 && namescount ==0);
 //               if(ii==null || ii.length==0 || namescount==0)
 //                   blankrighton(true);

//                blankmidon((ii==null || ii.length==0 || namescount==0) || mult);
//                if(ii==null || ii.length==0 || namescount==0)
//                    blankrighton(true);
        but_namessaveas.setEnabled(!mult && names.getSelectedIndex()>=0);       
        but_namesname.setEnabled(!mult && names.getSelectedIndex()>=0);
        but_namestype.setEnabled(!mult && names.getSelectedIndex()>=0);
        but_namesdel.setEnabled(names.getSelectedIndex()>=0);
        but_namesexport.setEnabled(names.getSelectedIndex()>=0);
        savelist.setEnabled(!mult && changed());
        
        
        butNextImage.setVisible(false);
        butNextImage2.setVisible(false);         
        int k;
//        ArrayList al2 = null;        
        if((k=indexOfPicName(availablepics_all, curr_word)) >=0){
            picitem pi = (picitem)availablepics_all.get(k); 
//            al2 = pi.bytes;
            if(pi.bytes!=null){
                // dropBox.wp.currentbn is for the new / updated image
                butNextImage.setEnabled(pi.bytes.size()>0 && dropBox.wp.currentbn>0);
                butNextImage2.setEnabled(dropBox.wp.currentbn<pi.bytes.size()-1);  
            }
            butNextImage.setVisible(pi.bytes!=null && pi.bytes.size()>1);
            butNextImage2.setVisible(pi.bytes!=null && pi.bytes.size()>1);            
        }        
//        int count = (haspublicpic!=null && (haspublicpic.contains(curr_word))?1:0) + (al2==null?0:al2.size());
        
        int sels[] = words.getSelectionRows();
        but_wordsup.setEnabled(sels!=null && sels.length>0 && sels[0] != 0  && !words.isEditing());
        but_wordsdown.setEnabled(sels!=null && sels.length>0 && sels[0] != words.getModel().getChildCount(words.root)-1  && !words.isEditing());
        
        but_wordsddel.setEnabled(!mult && words.getSelectionCount()==1  && !words.isEditing());
        but_wordsadd.setEnabled(!mult && !words.isEditing());
//        but_wordsimport.setEnabled(!mult  && !words.isEditing());
        but_wordsimport.setEnabled(!mult);
 //       but_keypad.setEnabled(!mult  && !words.isEditing());
        but_keypad.setEnabled(!mult);
        but_wordsedit.setEnabled(!mult && words.getSelectionCount()==1  && !words.isEditing());
        
        but_wordsprint.setEnabled(!words.isEditing());
     }

     public void refreshwords() {
        words.repaint();
     }
     

     int saveit(boolean mess) {
        int i,j;
        if(currname==null || currname.length()==0) return -2;
        if(words.isEditing()) {
          jnode jn;
          if((jn = (jnode)words.getUI().getEditingPath(words).getLastPathComponent()).get().trim().equals("")){
              try{
                ((DefaultTreeModel)words.getModel()).removeNodeFromParent(jn);
              }
              catch(Exception e){}
          }
          ending=true;
          words.stopEditing();
          ending=false;
        }
        String ww[] = words.getdata(), ss;
        if(ww.length>0 && !iseasy(ww)) return JOptionPane.CANCEL_OPTION;
        if(ww.length>0 && changed()) {
          if(mess) {
              i = u.yesnocancel(u.gettext("uwl_qsave","heading"),u.gettext("uwl_qsave","q",currname ), thisd);
              if(i < 0) return JOptionPane.CANCEL_OPTION;   // cancel
              if(i > 0) {
                  setnames(true);
                  return JOptionPane.NO_OPTION;
              }    // no 
          }
          
          tooFewResourcesCheck(ww);
          oricurrtype = currtype;
          write();
//          startwords =words.getdata();
 //         names.setListData(currnames=u.addString(currnames,currname));
 //         currname = null;
        }
        return JOptionPane.YES_OPTION;
     }

     
     void tooFewResourcesCheck(String s[]){
         int imcount = 0;
         int reccount = 0;
         int recextracount = 0;
         for(int i = 0; i < s.length; i++){
             if(hasimage(s[i]))imcount++;
             if(hasrec(s[i]))reccount++;
             if(hasrecextra(s[i]))recextracount++;
         }
//         boolean imalert = false;
         boolean recalert = false;
         boolean recextraalert = false;
//         if(imcount!=s.length){
//             imalert = imcount < topic.MINOWLIMAGES;
//         }
         if(reccount!=s.length){
             recalert = reccount < topic.MINOWLRECORDINGS;
         } 
         if(currtype!=0 && recextracount!=s.length){
             recextraalert = recextracount < topic.MINOWLRECORDINGS;
         }
         String mess = null;
         if((recalert && recextraalert)||recalert){
             mess = u.gettext("ownwordlists", "listalertrec");
         }
         else if(recextraalert){
             String type = "";
             if(currtype == TYPE_TRANSLATIONS)type = u.gettext("translation", "label").toLowerCase() +" ";
             else if(currtype == TYPE_DEFINITIONS)type = u.gettext("definition", "label").toLowerCase()+" ";
             mess = u.gettext("ownwordlists", "listalertextrarec", type);
         }
         if(mess!=null)
            JOptionPane.showMessageDialog(sharkStartFrame.mainFrame,
                                        u.convertToHtml(mess),
                                        shark.programName,
                                        JOptionPane.WARNING_MESSAGE);
     }
     
     
     
        boolean isAlreadyInList(String s) {
          for (int i = 0; i < words.count(); i++) {
            jnode jn = ( (jnode) words.getPathForRow(i).getLastPathComponent());
            if ( (words.getSelectionPath()) != null) {
              if (jn != ( (jnode) words.getSelectionPath().getLastPathComponent())) {
                if (s.equals(jn.get())) {
                  return true;
                }
              }
            }
            else {
              if (s.equals(jn.get())) {
                return true;
              }
            }
          }
          return false;
        }

      boolean isAlreadyWarned(String s){
        if(maxWarnedLists.length>0){
          for(int i = 0; i < maxWarnedLists.length; i++){
            if(maxWarnedLists[i].equals(s)){
              return true;
            }
          }
        }
        maxWarnedLists = u.addString(maxWarnedLists, s);
        return false;
      }

     void write() {

        sharkTree tt = new sharkTree();
        tt.set(tt.root,currname);
        String ww[] = words.getdata();
        boolean phonics = false;
        for(int i=0;i<ww.length;++i) {
           if(phonics) ww[i] = ww[i] + "=" + ww[i];
           tt.addChild(tt.root,ww[i]);
        }
        saveTree1 st = new saveTree1(tt,tt.root);
        st.curr.adminlist = true;
        st.curr.type = currtype;
        st.curr.languages = currlangs;
        
        fillWordData();

        workingList.names[0] = currname;
        workingList.adminlist = true;
        workingList.type = currtype;
        workingList.languages = currlangs;
        workingList.levels = st.curr.levels;
        

        db.update(dbname, currname, st.curr,db.TOPIC);
        db.update(sharkStartFrame.resourcesdb, currname, workingList,db.TOPIC);


        sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics = true;
        if(sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator) {
          sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics
                             = db.anyof(dbname,db.TOPIC);
          if(sharkStartFrame.studentList[sharkStartFrame.currStudent].checkstu())
                           student.checkadmin(sharkStartFrame.studentList[sharkStartFrame.currStudent]);
          sharkStartFrame.mainFrame.gettopictreelist();
          sharkStartFrame.mainFrame.setupgames();
        }
        

        oriList = workingList = null; 
        setupnewsel(currname);
      }


     boolean changed() {
         if(workingList == null || workingList.names == null || workingList.names.length < 2 || (workingList.names.length == 2 && workingList.names[1].trim().equals(""))){
             return false;
         }
         if(currtype != oricurrtype) return true;
         if(oriList == null){
             return workingList!=null;
         }
//         if(workingList == null){
//             return false;
//         }
         if(workingList.names!=null && oriList.names!=null && (workingList.names.length!=oriList.names.length))return true;

         if(workingList.names!=null && workingList.names.length>1 && oriList.names==null)return true;
         if(!Arrays.equals(currlangs, oriList.languages))return true;
         for(int i = 1; workingList.names!=null && oriList.names!=null && i < workingList.names.length; i++){
             if(!workingList.names[i].equals(oriList.names[i]))return true;
             if(workingList.preferredPic[i]==null && oriList.preferredPic[i]!=null)
                 return true;  
             else if(workingList.preferredPic[i]!=null && !workingList.preferredPic[i].equals(oriList.preferredPic[i]))
                 return true;            
             if(workingList.recs[i] instanceof byte[] && oriList.recs[i] instanceof byte[]){
                if(!Arrays.equals(workingList.recs[i],oriList.recs[i]))
                    return true;
             }
             else if(workingList.recs[i]!=oriList.recs[i])
                 return true;
             
             if(workingList.pics[i] instanceof byte[] && oriList.pics[i] instanceof byte[]){
                if(!Arrays.equals(workingList.pics[i],oriList.pics[i]))
                    return true;
             }
             else if(workingList.pics[i]!=oriList.pics[i])
                 return true;
             
             if(workingList.extrarecs[i] instanceof byte[] && oriList.extrarecs[i] instanceof byte[]){
                if(!Arrays.equals(workingList.extrarecs[i],oriList.extrarecs[i]))
                    return true;
             }
             else if(workingList.extrarecs[i]!=oriList.extrarecs[i])
                 return true;             
         }
         return false;
     }


     boolean startnew(int type) { // 0=normal, 1 = phonicsounds, 2 = phonicwords
       boolean hassetup = false;
       if(saveit(true)!=JOptionPane.CANCEL_OPTION){
         currname = "";
         words.clear();
         curr_word = null;
         setbuttons();
         JOptionPane getpw = new JOptionPane(
            u.gettext("uwl_name","label") ,
            JOptionPane.PLAIN_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION);
         getpw.setWantsInput(true);
         JDialog dialog = getpw.createDialog(thisd,u.gettext("uwl_name","title"));
          while(true) {
             keypad.dofullscreenkeypad(dialog, true);
             dialog.setVisible(true);
             keypad.dofullscreenkeypad(dialog, false);
             Object result = getpw.getValue();
             String newname = (String)getpw.getInputValue();
             if(result == null || newname.length()==0
                        || result instanceof Integer
                             &&((Integer)result).intValue() != JOptionPane.OK_OPTION){
                 hassetup = false;
                 break;
             }
             if(newname.length() > 30) {
                  u.okmess(u.gettext("uwl_name","title"),u.gettext("uwl_name","toolong"), thisd);
                  continue;
             }
             currname = newname;
             if(db.query(dbname,currname,db.TOPIC) >= 0) {  // see if exists already
                 if(type>0) {     // cannot pick up old one if request for phonics list
                   u.okmess(u.gettext("uwl_name","title"),u.gettext("uwl_name","isthere"), thisd);
                   continue;
                 }
                 boolean got = false;
                 String ww[] = currnames;
                 for(int i=0;i<ww.length;++i) {
                    if(ww[i].equalsIgnoreCase(currname)) {
                       names.setSelectedIndex(i);
                       got = true;
                       setbuttons();
                       hassetup = true;
                       break;
                    }
                 }
                 if(!got) {
                    u.okmess(u.gettext("uwl_name","title"),u.edit(u.gettext("uwl_name","toohard"), currname), thisd);
                    continue;
                 }
             }
             else {
                 String ss[] = null;
                 for(int i = 0; i <  names.getModel().getSize(); i++){
                     if(ss==null)ss=new String[]{(String)names.getModel().getElementAt(i)};
                     else ss = u.addString(ss, (String)names.getModel().getElementAt(i));
                 }
                 if(ss==null){
                     names.setListData(new String[]{currname});
                     names.setSelectedIndex(0);
                 }
                 else{
                     ss = u.addString(ss, currname);
                     u.sort(ss);
                     names.setListData(ss);
                     names.setSelectedIndex(u.findString(ss, currname));
                 }
             }
             newlist(type);
             setbuttons();
             return true;
           }
       }
       return hassetup;
     }

     void refreshlist() {
         if(currtype == TYPE_TRANSLATIONS && currlangs!=null){
             if(u.findString(foreignkeypads, currlangs[0])>=0){
                 setupkeypad(currlangs[0]);
                 pnkeypad.setVisible(true);
             }
         }
         xtrarecpan.setVisible(currtype != OwnWordLists.TYPE_NORMAL);
         if(curr_word!=null)
             rectool.setupword(curr_word);
     }


     void newlist(int type) {
         currtype = TYPE_NORMAL;
//         resourcechanged = false;
         String forlab = currname;
         int k;
         if((k=forlab.indexOf(u.phonicsplits))>=0)
             forlab = forlab.substring(0, k).trim();

        lbinthelist.setText(inlist.replaceFirst("%", forlab));
        words.setEditable(true);
        words.setEnabled(true);
        xresup.setVisible(false);

        lbtrans.setVisible(false);
        lbdef.setVisible(false);
        lbrec.setVisible(true);
        lbrectrans.setVisible(false);


        if(userkeypadactive!=null){
            setupkeypad(userkeypadactive);
        }
        else if(defaultkeypadactive!=null)
            setupkeypad(defkey);
         pnkeypad.setVisible(defaultkeypadactive!=null || userkeypadactive!=null);
         set_showkeypad = pnkeypad.isVisible();
         currlangs = null;

         
           
        haspublicpic = new ArrayList();
        haspublicrec = new ArrayList();
          
         saveTree1 newsave = (saveTree1)db.find(dbname,currname,db.TOPIC);
         
         fillWordData();  //must be after the load of newsave, otherwise changes in the definitions/translations dialog are not picked up for unsaved lists.
         
         if(newsave != null) {
           String startwords[] =  new String[newsave.curr.names.length-1];
           System.arraycopy(newsave.curr.names,1,startwords,0,newsave.curr.names.length-1);
           if(startwords.length==0){
                words.addItem("");
                words.startEditingAtPath(words.getPathForRow(0));
                workingList = new saveTreeWordList(); 
           }
           else    
            words.putdata(startwords);
//           oricurrtype = currtype = newsave.curr.type;
//           if(!justEdited)

           oricurrtype = newsave.curr.type;
           currtype = newsave.curr.type;
           currlangs = newsave.curr.languages;
           if(currlangs==null && currtype==TYPE_TRANSLATIONS)
               oricurrtype = currtype = TYPE_NORMAL;

           if(currtype==TYPE_TRANSLATIONS){
                lbtrans.setVisible(true);
           }
           else if(currtype==TYPE_DEFINITIONS){
                lbdef.setVisible(true);
           }
           lbrec.setVisible(currtype!=TYPE_TRANSLATIONS);
           lbrectrans.setVisible(currtype==TYPE_TRANSLATIONS);         
           rectool.changeLang(currlangs);
           rectool.changeType(currtype);
           
           workingList = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,currname,db.TOPIC);
           if(workingList == null || newsave.curr==null || newsave.curr.names== null || 
                   (workingList.names==null || (workingList.names.length != newsave.curr.names.length))){
               workingList = new saveTreeWordList();
               if(newsave.curr !=null){
                   workingList.names = newsave.curr.names;
                   workingList.recs = new byte[newsave.curr.names.length][];
                   workingList.pics = new byte[newsave.curr.names.length][];
                   workingList.preferredPic = new String[newsave.curr.names.length];
                   workingList.extrarecs = new byte[newsave.curr.names.length][];
               }
               if(workingList.names==null)workingList.names = new String[]{};
           }
           else{
               if(workingList.recs==null)workingList.recs = new byte[workingList.names.length][];
               if(workingList.pics==null)workingList.pics = new byte[workingList.names.length][];
               if(workingList.preferredPic==null)workingList.preferredPic = new String[newsave.curr.names.length];
               if(workingList.extrarecs==null)workingList.extrarecs = new byte[workingList.names.length][];
           }

            for(int i = 1; newsave.curr!=null && newsave.curr.names!=null && i < newsave.curr.names.length; i++){
                 checkPublicResource(newsave.curr.names[i]);
            }           

        }
        else {   // new list
//           startwords = new String[0];
           nameselected(true);
           words.addItem("");
           words.startEditingAtPath(words.getPathForRow(0));
           
           workingList = new saveTreeWordList(); 
        }
        oriList = workingList.doclone();
        setupRecord();
//        blankrighton(true);
        if(!rbrec.isSelected() && !rbpic.isSelected())rbrec.setSelected(true);
        refreshlist();
        
     }

     void blankmidon(boolean on){
        xblankmid.setVisible(on);
        xwordsup.setVisible(!on);
     }
     void blankrighton(boolean on){
       xblankright.setVisible(on);
       xresup.setVisible(!on);
       if(on){
//           dropBox.wp.mainPanel.pause = true;
           setPicPanelPause(true);
        }
     }

     void updateNewSaveTree(){
         String existingw[] = words.getdata();
         saveTreeWordList tempTree = new saveTreeWordList();
         tempTree.names = u.addString(existingw, currname, 0);
         existingw = u.addString(existingw, currname, 0);
         tempTree.pics = new byte[tempTree.names.length][];
         tempTree.preferredPic = new String[tempTree.names.length];
         tempTree.recs = new byte[tempTree.names.length][];
         tempTree.extrarecs = new byte[tempTree.names.length][];
   ///      tempTree.names[0] = currname;
         loop1: for(int i = 1; i < existingw.length; i++){
             for(int j = 1; workingList!=null && workingList.names!=null && j < workingList.names.length; j++){
                if(existingw[i].equalsIgnoreCase(workingList.names[j])){
                    tempTree.pics[i] = workingList.pics[j]; 
                    tempTree.preferredPic[i] = workingList.preferredPic[j];
                    tempTree.recs[i] = workingList.recs[j]; 
                    tempTree.extrarecs[i] = workingList.extrarecs[j]; 
                    continue loop1;  
                }
             }
         }
         workingList = tempTree;
         /*
          * for testing
          * 
          for(int i = 0; i < existingw.length; i++){
             System.out.println(existingw[i]);
         } 
          System.out.println("------------------");
         for(int i = 0; i < workingList.names.length; i++){
             System.out.println(workingList.names[i]);
         }
         System.out.println(" ");
         System.out.println(" ");
          * 
          */
     }




     void setupkeypad(String s){
          xwordsup.revalidate();
         pnkeypad.removeAll();
         currkeypad = s;
         int i;
         boolean found = false;
         for(i = 0; i < currkeypads.length; i++){
             String s2 = currkeypads[i][0];
             if(s2.endsWith(" "+usermade))s2 = s2.substring(0, s2.indexOf(" "+usermade));
             String ss = currkeypads[i].length>2?currkeypads[i][2]:s2;
             if(s.equals(ss)){
              found = true;
              break;
             }
         }
         if(found){
             if(currkeypads[i].length>2)s = currkeypads[i][2];
             kk = new keypad(s);
             
             kk.kp = (keypad.savekeypad)db.find(currkeypads[i][1], s, db.SAVEKEYPAD);
             kk.extrakeys(new char[] {(char)keypad.SHIFT,' ',(char)keypad.BACKSPACE,(char)keypad.ENTER});
             kk.sendto = thisd;
             kk.setFocusable(false);
             kk.setShowVerticalLines(false);
             kk.setShowHorizontalLines(false);
             int sw = sharkStartFrame.mainFrame.getWidth();
             int xwordsupwidth = sw*7/22;
//             kk.setRowHeight((xwordsup.getWidth()/kk.maxlen)-((kk.maxlen-1)*kk.keyspacing));
//             kk.kw =xwordsup.getWidth()/kk.maxlen;
             kk.setRowHeight((xwordsupwidth/kk.maxlen)-((kk.maxlen-1)*kk.keyspacing));
             kk.kw =xwordsupwidth/kk.maxlen;
             GridBagConstraints grid1 = new GridBagConstraints();
             grid1.gridx = 0;
             grid1.gridy = 0;
             grid1.weightx = 1;
             grid1.weighty = 1;
             grid1.fill = GridBagConstraints.BOTH;
             pnkeypad.add(kk, grid1);
         }
         pnkeypad.validate();
         if(found){
             kk.validate();
         }

         xwordsup.revalidate();
     }

     boolean hasrecextra(String s) {
         if(workingList==null)return false;
         int i = u.findString(workingList.names, s);
         return i>=0 && workingList.extrarecs[i]!=null;
     }
     
     boolean hasrec(String s) {
         if(haspublicrec!=null && haspublicrec.contains(s))return true;
         for(int n = 0; n < availablerecs.size(); n++){
             if(((recitem)availablerecs.get(n)).name.equals(s)){
                 return true;
             }
         }          
         for(int n = 0; currrecs!=null && n < currrecs.size(); n++){
             if(((recitem)currrecs.get(n)).name.equals(s)){
                 return true;
             }
         }          
         return false;
     }
     
     boolean hasimage(String s) {
         if(haspublicpic!=null && haspublicpic.contains(s))return true;       
         for(int k = 1; workingList!=null && workingList.names!=null && k < workingList.names.length; k++){
             if(s.equals(workingList.names[k])){
                if(workingList.pics[k] != null)return true;
                break;
             }
         }
         return false;
     }

     void editpicon(){
//            dropBox.wp.mainPanel.pause = false;
            setPicPanelPause(false);
            resblankpn.setVisible(false);
            respicpn.setVisible(true);
            resrecpan.setVisible(false);
            rbpic.setSelected(true);
     }

     
     void fillWordData(){
         fillWordData(true);
     }

     void fillWordData(boolean deleteEmptyLists){
        String namelist[] = db.list(dbname,db.TOPIC);
        otherwords = null;
        allwords = null;
        currnames = new String[]{};
        for(int i=0;i<namelist.length;++i) {
           saveTree1 st = (saveTree1)db.find(dbname, namelist[i],db.TOPIC);
           if(st==null || st.curr == null || st.curr.names==null)continue;
           if(deleteEmptyLists){
            if(st.curr.names.length<2){
                db.delete(dbname, namelist[i],db.TOPIC);
                db.delete(sharkStartFrame.resourcesdb, namelist[i],db.TOPIC);
                continue;
            }
           }
           st.curr.names[0] ="aa";   // in case a funny name
           if(iseasy(st.curr.names))
               currnames = u.addString(currnames,namelist[i]);
           if(st != null) {
               if(st.curr.names!=null && st.curr.names.length>1){
                   String tstr[] = new String[st.curr.names.length-1];
                   System.arraycopy(st.curr.names,1,tstr,0,st.curr.names.length-1);
                   if(currname!=null && !currname.equals(namelist[i])){
                       if(otherwords==null)otherwords = new String[]{};                 
                       otherwords = u.addString(otherwords, tstr);                       
                   }
                   if(allwords==null)allwords = new String[]{};
                   allwords = u.addString(allwords, tstr);  
               }
           }
        }     
     }


     void editrecon(){
//            dropBox.wp.mainPanel.pause = true;
         setPicPanelPause(true);
         resblankpn.setVisible(false);
            respicpn.setVisible(false);
            resrecpan.setVisible(true);
            rbrec.setSelected(true);
     }


     class keypadsettings extends JDialog {
         JDialog thiskps;
         JCheckBox showkp;
         ArrayList rbs = new ArrayList();
         JRadioButton nullrb = new JRadioButton();
 //       String thissetkeypad;
         String oriKeypad;
 //        boolean thissetshowkeypad;
         boolean oriShowKeypad;
         int currpad = -1;
         JRadioButton selectedrb = null;
         JScrollPane scrollp;
         ItemListener bglisten = new java.awt.event.ItemListener() {
             public void itemStateChanged(ItemEvent e) {
                JRadioButton jrb = ((JRadioButton)e.getSource());
                if(jrb.isSelected()){
                    if(jrb.equals(nullrb)){
                        set_showkeypad = false;
                    }
                    else{
                        set_showkeypad = true;
                        String s = jrb.getText();
                        if(s.endsWith(" "+usermade))
                            s = s.substring(0, s.indexOf(" "+usermade));
                        for(int i = 0; i < currkeypads.length; i++){
                            if(currkeypads[i][0].equals(s)){
                                if(currkeypads[i].length>2){
                                    s = currkeypads[i][2];
                                }
                                break;
                            }
                        }
                        set_keypadname = s;
                    }
                }
                checkForChange();
             }
         };
         JButton okbt;

         public keypadsettings(){
             super(thisd);
             thiskps = this;
             setTitle(u.gettext("keypadsettings", "title"));
             setResizable(false);
             setModal(true);
             setLayout(new GridBagLayout());
             GridBagConstraints grid1 = new GridBagConstraints();
             
             
    this.addWindowListener(new java.awt.event.WindowAdapter() {
      public void windowClosing(WindowEvent e) {

      }
      public void windowActivated(WindowEvent e) {
             if(selectedrb!=null){
                 int rx = selectedrb.getX();
                 int ry = (int)selectedrb.getBounds().getY() - (int)selectedrb.getBounds().getY();
                 int rw = (int)selectedrb.getBounds().getWidth();
                 int rh = (int)selectedrb.getBounds().getHeight();
                    selectedrb.scrollRectToVisible(new Rectangle(rx, ry, rw ,rh));
             }
      }
    });             
             
             grid1.fill = GridBagConstraints.BOTH;
             grid1.gridx = 0;
             grid1.gridy = -1;
             grid1.weightx = 1;
             grid1.weighty = 1;
             set_showkeypad = pnkeypad.isVisible();
             oriKeypad = currkeypad;
             oriShowKeypad = set_showkeypad;
             showkp = u.CheckBox("cbshowkeypad");
             showkp.setSelected(pnkeypad.isVisible());
             showkp.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    radioButtonsSetEnabled(showkp.isSelected());
                    set_showkeypad = showkp.isSelected();
                    if(!showkp.isSelected())set_keypadname = null;
                    else{
                        for(int i = 0; i < rbs.size(); i++){
                            if(((JRadioButton)rbs.get(i)).isSelected()){
                                checkForChange();
                                return;
                            }
                        }
                        for(int i = 0; i < rbs.size(); i++){
                            if(currkeypads[i][0].equals(currkeypad)){
                                ((JRadioButton)rbs.get(i)).setSelected(true);
                                checkForChange();
                                return;
                            }
                        }
                        ((JRadioButton)rbs.get(0)).setSelected(true);
                    }
                    checkForChange();
                }
             });


             JPanel uppanel = new JPanel(new GridBagLayout());
             JPanel midpanel = new JPanel(new GridBagLayout());
             JPanel bottompanel = new JPanel(new GridBagLayout());


             JLabel selectlb = new JLabel(u.gettext("keypadsettings", "select"));
//             selectlb.setFont(smallerplainfont);
             JPanel pnkeypadlist = new JPanel(new GridBagLayout());
             pnkeypadlist.setBorder(BorderFactory.createEtchedBorder());
             ButtonGroup bgr = new ButtonGroup();
             grid1.anchor = GridBagConstraints.WEST;
             
             for(int i = 0; i < currkeypads.length; i++){
                 JRadioButton rb = new JRadioButton(currkeypads[i][0]);
                 if(currkeypads[i][0].equals(  currkeypad)){
                     rb.setSelected(true);
                     selectedrb = rb;
                 }
                 else if(currkeypads[i].length>2 && currkeypads[i][2].equals(currkeypad)){
                     rb.setSelected(true);
                     selectedrb = rb;
                 }
                 else if(currkeypads[i][0].equals(currkeypad+" "+usermade)){
                     rb.setSelected(true);
                     selectedrb = rb;
                 }
                 bgr.add(rb);
                 rb.addItemListener(bglisten);
                 rbs.add(rb);
                 grid1.insets = new Insets(0,20,0,20);
                 pnkeypadlist.add(rb, grid1);
                 grid1.insets = new Insets(0,0,0,0);
             }
             set_keypadname = currkeypad;
             grid1.anchor = GridBagConstraints.CENTER;
             nullrb.addItemListener(bglisten);
             bgr.add(nullrb);
             radioButtonsSetEnabled(showkp.isSelected());
             grid1.gridx = -1;
             grid1.gridy = 0;
             JPanel butpn = new JPanel(new GridBagLayout());
             okbt = u.sharkButton();
             okbt.setText(u.gettext("ok", "label"));
             okbt.setEnabled(false);
             JButton cancelbt = u.sharkButton();
             cancelbt.setText(u.gettext("cancel", "label"));
             okbt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    thiskps.dispose();
                }
             });
             cancelbt.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    set_keypadname = oriKeypad;
                    set_showkeypad = oriShowKeypad;
                    thiskps.dispose();
                }
             });
             int border = 5;
             int border2 = 30;
             grid1.insets = new Insets(border,border,border,border);
             grid1.anchor = GridBagConstraints.CENTER;
             grid1.fill = GridBagConstraints.NONE;
             grid1.weightx = 0;
             
             butpn.add(shark.macOS?cancelbt:okbt, grid1);
             grid1.insets = new Insets(0,border2,0,0);
             butpn.add(shark.macOS?okbt:cancelbt, grid1);
             grid1.insets = new Insets(0,0,0,0);
             grid1.fill = GridBagConstraints.BOTH;
             grid1.weightx = 1;
             grid1.gridx = 0;
             grid1.gridy = -1;
             grid1.anchor = GridBagConstraints.CENTER;
             grid1.fill = GridBagConstraints.NONE;
             JPanel mainpanel = new JPanel(new GridBagLayout());
             grid1.weightx = 1;
             grid1.insets = new Insets(border,0,border,0);
             uppanel.add(showkp, grid1);
             grid1.insets = new Insets(0,0,0,0);
             grid1.weightx = 0;
             grid1.anchor = GridBagConstraints.WEST;
             grid1.fill = GridBagConstraints.BOTH;
             
             grid1.insets = new Insets(0,border2,0,border2);
             grid1.weighty = 0;
             midpanel.add(selectlb, grid1);
             grid1.weightx = 1;
             grid1.weighty = 1;
             grid1.anchor = GridBagConstraints.CENTER;
             
             midpanel.add(scrollp = new JScrollPane(  pnkeypadlist), grid1);
             grid1.insets = new Insets(0,0,0,0);
             grid1.fill = GridBagConstraints.HORIZONTAL;
             bottompanel.add(butpn, grid1);
             grid1.fill = GridBagConstraints.BOTH;
             grid1.weighty = 0;
             grid1.insets = new Insets(border,border,border,border);
             mainpanel.add(uppanel, grid1);
             grid1.weighty = 1;
             grid1.insets = new Insets(0,border,border,border);
             mainpanel.add(midpanel, grid1);
             grid1.weighty = 0;
             grid1.weightx = 0;
             mainpanel.add(bottompanel, grid1);
             grid1.insets = new Insets(border,border,border,border);
             grid1.weighty = 1;
             grid1.weightx = 1;

             this.getContentPane().add(mainpanel, grid1);

             int sw = sharkStartFrame.mainFrame.getWidth();
             int sh = sharkStartFrame.mainFrame.getHeight();
             int sw2 = sw*3/10;
             int sh2 = sh*6/12;
             int xx = (sw-sw2)/2;
             int yy = (sh-sh2)/2;
             setBounds(xx, yy, sw2, sh2);

             validate();
             setVisible(true);

         }

         void radioButtonsSetEnabled(boolean enabled){
            for(int i = 0; i < rbs.size(); i++){
                ((JRadioButton)rbs.get(i)).setEnabled(enabled);
            }
            if(!enabled)nullrb.setSelected(true);
         }


         void checkForChange(){
             if(okbt==null)return;
             boolean changed = false;
             if(set_showkeypad != oriShowKeypad) changed= true;
             if(oriKeypad==null){
                 if(set_keypadname!=null) changed= true;
             }
             else if(!oriKeypad.equals(set_keypadname))changed= true;
             okbt.setEnabled(changed);
         }
     }


  public static class xpic {
    public String name;
    public byte pic[];

    xpic(String n, byte pica[]) {
        name = n;
        pic = pica;
    }
    xpic(String n) {
        name = n;
    }
  }


   //---------------------------------------------------------------
//   void setsel(JList jj,String[] li) {
//                int sel;
//                if(currword != null && currword.length()>0
//                   && (sel = u.findString(li,currword))>=0) {
///                    jj.setSelectedIndex(sel);
//                    jj.scrollRectToVisible(jj.getCellBounds(sel,sel));
//                }
//                else jj.clearSelection();
//   }


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
          if(buf != null){
              String whichpic = picname==null?fths[i].getName():picname;
              for(int k = 1; workingList!=null && workingList.names!=null && k < workingList.names.length; k++){
                  if(whichpic.equals(workingList.names[k])){
                      workingList.pics[k] = buf;
                      int j;
                      if((j=indexOfPicName(availablepics_all, whichpic)) >=0){
                        picitem pi = (picitem)availablepics_all.get(j);
                        int n;
                        if((n=pi.indexOfBytes(buf))>=0){ 
                            pi.removeBytes(n);
                        }
                        pi.add(buf);
                        dropBox.wp.currentbn = pi.bytes.size()-1;
                        availablepics_all.set(j, pi);  // is this needed?
                      }  
                      else{
                          ArrayList al = new ArrayList();
                          al.add(buf);
                          availablepics_all.add(new picitem(whichpic, al));
                      }
                      break;
                  }
              }
//              resourcechanged = true;
          }
          v.add(picname);//dbname.equals(dbname)?PickPicture.ownpic:PickPicture.adminownpic);
          buf = null;
        } catch (Exception e) {
//          u.okmess(shark.programName, u.gettext("pickpicture", "error"));
        }
      }
      if (stop) {
        for (int p = 0; p < v.size(); p++) {
          db.delete(dbname, (String) v.get(p), db.PICTURE);
        }
        v = null;
      }
      if(add)
          converted(dbname, 1);
    }
  }

  public void converted(String name, int type) {
      if(type!=0){
           boolean b = !dropBox.wp.addPic(name, true);
           dropBox.wp.mainPanel.pause = b;
      }
//        btDelete.setEnabled(true);
    
  }

    public class wordpicture extends JPanel {
    public runMovers mainPanel;
//    boolean isshowingimport = false;
    mover m;
    javax.swing.Timer savedTimer;
    String strsaved = u.gettext("pickpicture", "saved");
    int saved1delay = 250;
    int saved2delay = 1100;
    String nopicture = u.gettext("pickpicture", "nopicture");
    public int currentbn = -1;
    public boolean showsaved = true;

    public wordpicture(int x1, int y1, int w1, int h1) {
      mainPanel = new runMovers();
      add(mainPanel, BorderLayout.CENTER);
      setLayout(new BorderLayout());
      setBounds(x1, y1, w1, h1);
      mainPanel.setBounds(0, 0, w1, h1);
      savedTimer = (new javax.swing.Timer(saved1delay, new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              if(!dropBox.wp.mainPanel.isMover(m) && savedTimer.getInitialDelay()==saved1delay){
                  if(showsaved){
                      m = new mover.textMover(strsaved,
                        mover.WIDTH,
                        mover.HEIGHT/7,
                        Color.darkGray,
                        Color.white,
                        new Font[]{sharkStartFrame.treefont});
                      dropBox.wp.mainPanel.addMover(m, 0, (mover.HEIGHT-m.h)/2);
                      dropBox.wp.mainPanel.bringtotop(m);
                  }
                  savedTimer.setInitialDelay(saved2delay);
                  savedTimer.restart();
              }
              else if(savedTimer.getInitialDelay()==saved2delay){
                  savedTimer.setInitialDelay(saved1delay);
                  savedTimer.stop();
                  if(dropBox.wp.mainPanel.isMover(m)){
                      dropBox.wp.mainPanel.removeMover(m);
                  }
              }
              showsaved = true;
          }
       }));
       savedTimer.setRepeats(true);
       savedTimer.setInitialDelay(saved1delay);
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


    public boolean addPic(String dbs, boolean userset) {
      mainPanel.removeAllMovers();
//      isshowingimport = false;

      sharkImage si =null;
//      boolean found = false;
      /*
      for(int i = 0; currpics!=null && i < currpics.size(); i++){
          xpic xp = (xpic)currpics.get(i);
          if(xp.name.equals(curr_word)
               ){
            si = new sharkImage(sharkStartFrame.t.createImage((currentb=xp.pic)), curr_word);
            si.isimport = true;
            found = true;
          }
      }
       * */
      String prefPic = null;
       for(int i = 1; workingList!=null && workingList.names!=null && i < workingList.names.length; i++){
           if(curr_word.equals(workingList.names[i])){
               if(workingList.pics[i]!=null){                    
                    si = new sharkImage(sharkStartFrame.t.createImage(workingList.pics[i]), curr_word);
                    si.isimport = true;
//                    found = true;            
               }
               if(workingList.preferredPic[i]!=null){
                   prefPic = workingList.preferredPic[i];
               }  
           }
       }
     
//      if(!found){
//          si = sharkImage.find(sharkStartFrame.resourcesdb, curr_word, true, true);
//          currentb=si;
//      }
      if(si==null){
          si = sharkImage.find(prefPic!=null?prefPic:curr_word, true, false, true);
      }

      boxDelete.setEnabled(si!=null && si.isimport);
  //    if(si==null)return false;

      if(si==null){
                mover.simpletextmover tt = new mover.simpletextmover(nopicture,
          mover.WIDTH / 2, mover.HEIGHT / 2);
          mainPanel.addMover(tt, tt.w / 2, mover.HEIGHT / 4);
          addPic2(false);
      }
      else{

      sharkImage im = si;
//      isshowingimport = true;
      int x = 0;
      int y = 0;
//        if (im.isimport) {
//          isshowingimport = true;
//        }
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
        addPic2(true && userset);
        }
      dropBox.wp.reposition();
      setbuttons();
      return true;
    }

//    public void addPic(String mess) {
//      mainPanel.removeAllMovers();
//      isshowingimport = false;
//      mover.simpletextmover tt = new mover.simpletextmover(mess,
//          mover.WIDTH / 2, mover.HEIGHT / 2);
//      mainPanel.addMover(tt, tt.w / 2, mover.HEIGHT / 4)//;
//
//      addPic2();
//    }

    void addPic2(boolean showmess) {
      dropBox.activate(dropBox.wp);
      mainPanel.stoprun = false;
      mainPanel.start1();
      dropBox.validate();



     if(showmess)
       savedTimer.start();

      dropBox.repaint();
 //     if (si != null) {
//        btDelete.setEnabled(si[0].isimport);
//    }
    }

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
    myThread = new Thread(dropBox.gp = new GetPicture(ff,
            sharkStartFrame.resourcesdb,
            curr_word, true));
    myThread.start();
  }


   class editlist extends JDialog {
         ItemListener bglisten = new java.awt.event.ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                lang1cb.setEnabled(false);
                lang2cb.setEnabled(false);
                lang1lb.setEnabled(false);
                lang2lb.setEnabled(false);
                setothers(false);
                setothers2(false);
                if(rbNormal.isSelected()){
                    changedtype = TYPE_NORMAL;
                }
                else if(rbTrans.isSelected())
                {
                    changedtype = TYPE_TRANSLATIONS;
                    lang1cb.setEnabled(true);
                    lang2cb.setEnabled(true);
                    lang1lb.setEnabled(true);
                    lang2lb.setEnabled(true);
                }
                else if(rbDef.isSelected())
                {
                    changedtype = TYPE_DEFINITIONS;
                }
            }
          };

 //      JTextField tfield;
       JRadioButton rbNormal;
       JRadioButton rbTrans;
       JRadioButton rbDef;
       JPanel langpn;
       short changedtype = -1;
       String changedlangs[] = new String[2];
       String orilangs[] = new String[2];
       JButton okbut;
       JButton cancelbut;
       JDialog thisjd;
       JComboBox lang1cb;
       JComboBox lang2cb;
       JLabel lang1lb;
       JLabel lang2lb;
       String specify = u.gettext("ownwordlists_edit", "specify");
       JLabel sp1;
       JLabel sp2;
       JTextField tf1;
       JTextField tf2;
       String oriname;

       public editlist(JDialog owner, String currlist) {
         super(owner);
         oriname = currlist;
         thisjd = this;
         this.setModal(true);
         this.setTitle(u.gettext("ownwordlists_edit", "title"));
         this.setResizable(false);
         int w = sharkStartFrame.mainFrame.getSize().width;
         int h = sharkStartFrame.mainFrame.getSize().height;
         int w2;
         int h2;
         w2 = w*5/8;
         h2 = h*1/2;
//             w2 = w*4/8;
//             h2 = h*11/30;
//         setBounds((w-w2)/2,(h-h2)/2,w2,h2);
         setBounds(u2_base.adjustBounds(new Rectangle((w-w2)/2,(h-h2)/2,w2,h2)));
         this.getContentPane().setLayout(new GridBagLayout());
         
         GridBagConstraints grid = new GridBagConstraints();
         grid.gridx = -1;
         grid.gridy = 0;
         grid.weightx = 1;
         grid.weighty = 1;
         grid.fill = GridBagConstraints.NONE;
//         JPanel renamepn = new JPanel(new GridBagLayout());
         grid.weightx = 0;
//         JLabel lblistname = new JLabel(u.gettext("ownwordlists_edit", "listname"));
//         lblistname.setFont(largerfont);
//         renamepn.add(lblistname, grid);
//         tfield = new JTextField(20);
//         tfield.setFont(largerfont2);

//         String s1 = currlist;
//         int k;
//         if((k = currlist.indexOf(u.phonicsplit))>=0){
//           s1 = currlist.substring(0, k).trim();
//         }
//         tfield.setText(s1);
//         tfield.setSelectionStart(0);
//         tfield.setSelectionEnd(s1.length());
//         grid.insets =  new Insets(10,10,10,10);
//         renamepn.add(tfield, grid);
//         grid.insets =  new Insets(0,0,0,0);
//         grid.weightx = 1;
//         grid.fill = GridBagConstraints.BOTH;
//         renamepn.add(new JPanel(), grid);
//         grid.fill = GridBagConstraints.NONE;

         String langstrs[] = new String[]{u.gettext("ownwordlists_edit", "select")};
         for(int i = 0; i < currkeypads.length; i++){
            if(currkeypads[i].length>2){
                langstrs = u.addString(langstrs, currkeypads[i][0]);
            }
            else {
                langstrs = u.addString(langstrs, currkeypads[i][0]);
            }
          }
         langstrs = u.addString(langstrs, u.gettext("ownwordlists_edit", "other"));
         grid.anchor = grid.WEST;
         langpn = new JPanel(new GridBagLayout());
         grid.gridx =0;
         grid.gridy = 0;
         grid.insets =  new Insets(10,40,10,0);
         langpn.add(lang1lb = new JLabel(u.gettext("ownwordlists_edit", "wordsin")), grid);

         grid.gridx =1;
         grid.insets =  new Insets(10,10,10,0);
         langpn.add(lang1cb = new JComboBox(langstrs), grid);
         grid.gridx =2;
         grid.insets =  new Insets(10,40,10,0);
         langpn.add(lang2lb = new JLabel(u.convertToHtml(u.gettext("ownwordlists_edit", "translationsin"))), grid);
         grid.gridx =3;
         grid.insets =  new Insets(10,10,10,0);
         langpn.add(lang2cb =new JComboBox(langstrs), grid);
         lang1lb.setFont(smallerplainfont);
         lang2lb.setFont(smallerplainfont);
         grid.gridy = 1;
         grid.gridx =0;
         grid.insets =  new Insets(0,40,10,0);
         langpn.add(sp1 = new JLabel(specify), grid);
         grid.gridx =1;
         grid.fill = GridBagConstraints.BOTH;
         grid.insets =  new Insets(0,10,10,0);
         langpn.add(tf1 = new JTextField(), grid);
         grid.fill = GridBagConstraints.NONE;
         grid.gridx =2;
         grid.insets =  new Insets(0,40,10,0);
         langpn.add(sp2 = new JLabel(specify), grid);
         grid.gridx =3;
         grid.fill = GridBagConstraints.BOTH;
         grid.insets =  new Insets(0,10,10,0);
         langpn.add(tf2 = new JTextField(), grid);
         grid.fill = GridBagConstraints.NONE;
         grid.insets =  new Insets(0,0,0,0);
         sp1.setFont(smallerplainfont);
         sp2.setFont(smallerplainfont);

         langpn.setVisible(true);

         tf1.setEnabled(false);
         tf2.setEnabled(false);
         sp1.setEnabled(false);
         sp2.setEnabled(false);
         if(currlangs!=null)
             orilangs = (String[])currlangs.clone();
         if(currlangs!=null){
             if(currlangs[0]!=null){
                 if(u.findString(langstrs, currlangs[0])>=0){
                    lang1cb.setSelectedItem(currlangs[0]);
                 }
                 else {
                     tf1.setText(currlangs[0]);
                     setothers(true);
                     lang1cb.setSelectedIndex(lang1cb.getItemCount()-1);
                 }
             }
             if(currlangs[1]!=null){
                 if(u.findString(langstrs, currlangs[1])>=0){
                    lang2cb.setSelectedItem(currlangs[1]);
                 }
                 else {
                     tf2.setText(currlangs[1]);
                     setothers2(true);
                     lang2cb.setSelectedIndex(lang2cb.getItemCount()-1);
                 }
             }
         }
         lang1cb.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
              JComboBox jcb =(JComboBox)e.getSource();
              int k;
              tf1.setText("");
              if((k=jcb.getSelectedIndex())>=0){
                if(k==jcb.getItemCount()-1){
                    changedlangs[0] = null;
                    setothers(true);
                    tf1.requestFocus();
                }
                else{
                    setothers(false);
                    if(k>0)
                        changedlangs[0] = (String)jcb.getSelectedItem();
                    else changedlangs[0] = null;
                }
              }
          }
         });
         lang2cb.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
              JComboBox jcb =(JComboBox)e.getSource();
              int k;
              tf2.setText("");
              if((k=jcb.getSelectedIndex())>=0){
                if(k==jcb.getItemCount()-1){
                    setothers2(true);
                    changedlangs[1] = null;
                    tf2.requestFocus();
                }
                else{
                    setothers2(false);
                    if(k>0)
                     changedlangs[1] = (String)jcb.getSelectedItem();
                    else changedlangs[1] = null;
                }
              }
          }
         });
         grid.gridx =-1;
         grid.gridy = 0;
         JPanel redpn = new JPanel(new GridBagLayout());
         redpn.setBorder(BorderFactory.createEtchedBorder());
         rbNormal = new JRadioButton(u.gettext("ownwordlists_edit", "normal"));
         rbTrans = new JRadioButton(u.gettext("ownwordlists_edit", "translationlist"));
         rbDef = new JRadioButton(u.gettext("ownwordlists_edit", "definitionlist"));

         JPanel transpn = new JPanel(new GridBagLayout());
         JPanel defpn = new JPanel(new GridBagLayout());

         JLabel tranlbex = new JLabel(u.gettext("ownwordlists_edit", "translationlist2"));
         JLabel deflbex = new JLabel(u.gettext("ownwordlists_edit", "definitionlist2"));

         tranlbex.setFont(smallerplainfont);
         deflbex.setFont(smallerplainfont);
         transpn.add(rbTrans, grid);
         transpn.add(tranlbex, grid);
         defpn.add(rbDef, grid);
         defpn.add(deflbex, grid);
         grid.gridx =0;
         grid.gridy = -1;
         ButtonGroup bg = new ButtonGroup();
         bg.add(rbNormal);
         bg.add(rbTrans);
         bg.add(rbDef);
         rbNormal.addItemListener(bglisten);
         rbTrans.addItemListener(bglisten);
         rbDef.addItemListener(bglisten);grid.weighty = 0;
         grid.insets = new Insets(10,10,0,10);
         redpn.add(rbNormal, grid);
    //     grid.insets = new Insets(0,10,0,10);
         redpn.add(defpn, grid);
       //  grid.insets = new Insets(0,10,10,10);
         redpn.add(transpn, grid);
         grid.insets = new Insets(10,10,10,10);
         redpn.add(langpn, grid);        
         grid.weighty = 1;
         if(currtype==TYPE_NORMAL)
             rbNormal.setSelected(true);
         else if (currtype==TYPE_TRANSLATIONS)
             rbTrans.setSelected(true);
         else if (currtype==TYPE_DEFINITIONS)
             rbDef.setSelected(true);
         okbut = u.sharkButton();
         okbut.setText(u.gettext("ok", "label"));
         cancelbut = u.sharkButton();
         cancelbut.setText(u.gettext("cancel", "label"));
         okbut.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 boolean changed = false;
                 boolean namechanged = false;
                 boolean typechanged = false;
                 lbtrans.setVisible(false);             
                 lbdef.setVisible(false);
                 if(rbTrans.isSelected()){
                    lbtrans.setVisible(true);
                     boolean onother1 =lang1cb.getSelectedIndex() == lang1cb.getItemCount()-1;
                     boolean onother2 =lang2cb.getSelectedIndex() == lang2cb.getItemCount()-1;
                     if(!Arrays.equals(currlangs, changedlangs) || onother1 || onother2){
                         boolean gotother1 = false;
                         boolean gotother2 = false;
                         String s;
                         if( onother1&&!(s = tf1.getText().trim()).equals("")){
                             changedlangs[0] = s;
                             gotother1 = true;
                         }
                         if( onother2&&!(s=tf2.getText().trim()).equals("")){
                             changedlangs[1] = s;
                             gotother2 = true;
                         }
                         if((changedlangs[0]!=null||gotother1)&& (changedlangs[1]!=null||gotother2)){
                             currlangs = changedlangs;
                             rectool.changeLang(currlangs);
                             changed = true;
                             namechanged = true;
                         }
                         else return;
                     }
                 }
                 else currlangs = null;
                 if(rbDef.isSelected()){
                    lbdef.setVisible(true);
                 }
                 if(currtype!=changedtype){
                     namechanged = changed = typechanged = true;
                 }
                 if(!Arrays.equals(currlangs, orilangs)){
                     namechanged = changed = true;
                 }
                 currtype = changedtype;
                 int k;
                 String s1 = oriname;
                 if((k = oriname.indexOf(u.phonicsplit))>=0){
                     s1 = oriname.substring(0, k).trim();
                 }
//                 if(!s1.equals(tfield.getText())){
//                     currname = tfield.getText();
//                     namechanged = changed = true;
//                 }
                 if(rbDef.isSelected()){
                     currname = s1 + " " + String.valueOf(u.phonicsplit) + " " + strname_def;
                 }
                 else if(rbTrans.isSelected())
                 {
                     currname = s1 + " " + String.valueOf(u.phonicsplit) + " " + currlangs[0]+"/"+currlangs[1];
                     if(!oriname.equals(currname))
                         changed = true;
                 }
                 else if(rbNormal.isSelected()){
                     currname = s1;
                 }
                 if(changed){
                     saveTree1 newsave = (saveTree1)db.find(dbname,oriname,db.TOPIC);
                     if(newsave==null){
                         newsave = new saveTree1();
                         newsave.curr.names = new String[]{oriname};
                     }
                     saveTreeWordList newsave3 = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb,oriname,db.TOPIC);
                     if(newsave3==null)newsave3 = new saveTreeWordList();
                     if(newsave != null) {
                         if(namechanged){
                             db.delete(dbname, oriname, db.TOPIC);
                             db.delete(sharkStartFrame.resourcesdb, oriname, db.TOPIC);
                         }
                         newsave.curr.type = currtype;
                         newsave.curr.languages = currlangs;
                         newsave3.type = currtype;
                         newsave3.languages = currlangs;
                         if(namechanged){
                             if(currlangs!=null && orilangs!=null){
                                 if(!currlangs[0].equals(orilangs[0])){
                                     for(int i = 0; newsave3.recs!=null && i < newsave3.recs.length; i++){
                                         newsave3.recs[i] = null;
                                     }                                        
                                 }
                                 if(!currlangs[1].equals(orilangs[1]) ||
                                         Arrays.equals(currlangs, orilangs)){
                                     for(int i = 0; newsave3.extrarecs!=null && i < newsave3.extrarecs.length; i++){
                                         newsave3.extrarecs[i] = null;
                                     }
                                 }
                             }
                             if(typechanged){
                                     for(int i = 0; newsave3.extrarecs!=null && i < newsave3.extrarecs.length; i++){
                                         newsave3.extrarecs[i] = null;
                                     }                                 
                             }
                         }
                         db.update(dbname, currname, newsave.curr, db.TOPIC);
                         db.update(sharkStartFrame.resourcesdb, currname, newsave3, db.TOPIC);
//                         oricurrtype = currtype;
                         setnames(false);

                        ListSelectionListener ls[] =  names.getListSelectionListeners();
                        if(ls!=null && ls.length>0) names.removeListSelectionListener(ls[0]);
                         setupnewsel(currname);
                        if(ls!=null && ls.length>0) names.addListSelectionListener(ls[0]); 
                        lastnamesel = (String)names.getSelectedValue();
                     }
                     
                 }
                 rectool.changeType(currtype);
                 refreshlist();
                 if(words.count()>0)
                    words.setSelectionRow(0);
                 thisjd.dispose();
             }
         } );

        cancelbut.addActionListener( new java.awt.event.ActionListener() {
             public void actionPerformed(ActionEvent e) {
                thisjd.dispose();
             }
         } );

         grid.fill = GridBagConstraints.NONE;
         grid.anchor = GridBagConstraints.CENTER;
         grid.gridx =-1;
         grid.gridy = 0;
         JPanel butpn = new JPanel(new GridBagLayout());
         grid.weightx = 1;
         grid.insets = new Insets(0,40,0,0);
         butpn.add(shark.macOS? cancelbut:  okbut, grid);
         grid.insets = new Insets(0,0,0,40);
         butpn.add(shark.macOS? okbut: cancelbut, grid);
         grid.insets = new Insets(0,0,0,0);
         grid.anchor = GridBagConstraints.WEST;
         grid.gridx = 0;
         grid.gridy = -1;
         changedlangs = currlangs==null?new String[2]:currlangs;
         grid.anchor = grid.CENTER;
//         JPanel toppn = new JPanel(new GridBagLayout());
         JPanel bottompn = new JPanel(new GridBagLayout());
         JPanel midinnerpn = new JPanel(new GridBagLayout());
         grid.fill = GridBagConstraints.BOTH;
         grid.weightx = 1;
         grid.weighty = 0;
         grid.anchor = grid.WEST;
 //        toppn.add(renamepn, grid);
         grid.fill = GridBagConstraints.BOTH;
         JLabel lblang = new JLabel(strlisttype);
         lblang.setFont(largerfont);
         midinnerpn.add(lblang, grid);
         grid.weighty = 1;
         grid.insets =  new Insets(0,0,0,0);
         grid.anchor = grid.CENTER;
         grid.weightx = 1;
         grid.insets =new Insets(10,0,0,0);
         midinnerpn.add(redpn, grid);
         grid.insets =new Insets(0,0,0,0);
         bottompn.add(butpn, grid);
         JPanel mainpn = new JPanel(new GridBagLayout());
         grid.fill = GridBagConstraints.BOTH;
         mainpn.add(midinnerpn, grid);
         grid.weighty = 0;
         grid.insets =new Insets(20,0,0,0);
         mainpn.add(bottompn, grid);
         grid.weighty = 1;
         grid.fill = GridBagConstraints.BOTH;
         int insetw = u.screenResWidthMoreThan(1000)?50:20;
         grid.insets =new Insets(20,insetw,20,insetw);
         this.getContentPane().add(mainpn, grid);
         grid.insets =new Insets(0,0,0,0);
         this.setVisible(true);
      }

       void setothers(boolean on){
            sp1.setEnabled(on);
            tf1.setEnabled(on);
       }
       void setothers2(boolean on){
            sp2.setEnabled(on);
            tf2.setEnabled(on);
       }
   }



   class exportlist extends JDialog {

       JTextField tfield;
       JButton okbut;
       JButton cancelbut;
       JButton browsebut;
       JDialog thisel;
       String currexportnames[];
       String tofile;

       public exportlist(JDialog owner, String[] currlist) {
         super(owner);
         thisel = this;
         currexportnames = currlist;
         this.setTitle(u.gettext("ownwordlists_edit", "export"));
         this.setResizable(false);
         int w = sharkStartFrame.mainFrame.getSize().width;
         int h = sharkStartFrame.mainFrame.getSize().height;
         int w2 = w*4/9;
         int h2 = h*9/32;
//         setBounds((w-w2)/2,(h-h2)/2,w2,h2);
         setBounds(u2_base.adjustBounds(new Rectangle((w-w2)/2,(h-h2)/2,w2,h2)));
         this.setLayout(new GridBagLayout());
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
//         grid.insets = new Insets(0,0,10,0);
         String smess;
         if(currlist.length > 1)
             smess = u.gettext("ownwordlists_edit", "exportlbp");
         else 
             smess = u.gettext("ownwordlists_edit", "exportlb", currlist[0]);
         renamepn.add(new JLabel(smess), grid);
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
                File f = new File(tofile);
                if(!f.exists())return;
                

        if(currexportnames.length>1)
        progbar = new progress_base(thisd, shark.programName,
                                           u.gettext("exporting", "label"),
                                           new Rectangle(thisd.getWidth()/4,
                                                         thisd.getHeight()*2/5,
                                                         (thisd.getWidth()/2),
                                                         (thisd.getHeight()/5)));

        exportThread = new Thread(dle = new doListExport(currexportnames, tofile));
        exportThread.start();
        exportTimer = new javax.swing.Timer(500, new ActionListener() {
                              public void actionPerformed(ActionEvent e) {
                                    if(exportThread.isAlive())return;
                                   if(progbar!=null){
                                       progbar.dispose();
                                       progbar=null;
                                   }
                                    exportTimer.stop();
                                    exportTimer = null;
                                    boolean atleastonesuccess = dle!=null && dle.success;
                                    dle = null;
                                    exportThread = null;

                if(atleastonesuccess && u.yesnomess(shark.programName, u.gettext("ownwordlists", "showexported"), thisd)){
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

       exportTimer.setRepeats(true);
       exportTimer.start();




                
                

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
                int returnVal = fc.showOpenDialog(thisd);
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
         mainpn.add(u.infoLabel(u.gettext("ownwordlists", "exportlistinfo", shark.programName)), grid);
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



   class sharklist2 extends sharklist {

       String justedited = null;

       public sharklist2(TreeCellRenderer  cc) {
         super(cc);
/*
       addTreeSelectionListener(new TreeSelectionListener() {
          public void valueChanged(TreeSelectionEvent e) {
              TreePath tp = words.getPathForRow(words.oldselection);
              if(tp!=null){
                  String newtext = ((jnode)tp.getLastPathComponent()).get();
                  if(justedited!=null && newtext!=null && !newtext.trim().equals("") &&
                          !justedited.equals(newtext)){
                      for(int n = 0; currrecs!=null && n < currrecs.size(); n++){
                          xrec xe = (xrec)currrecs.get(n);
                          if(xe.name.equals(justedited)){
                              xe.name = newtext;
                              break;
                          }
                      }
                      for(int n = 0; currpics!=null && n < currpics.size(); n++){
                          xpic xp = (xpic)currpics.get(n);
                          if(xp.name.equals(justedited)){
//                              xp.name = newtext;
                              currpics.remove(xp);
                              break;
                          }
                      }
                      for(int n = 0; currextrarecs!=null && n < currextrarecs.size(); n++){
                          xrec xe = (xrec)currextrarecs.get(n);
                          if(xe.name.equals(justedited)){
                              xe.name = newtext;
                              break;
                          }
                      }
                  }
              }
              justedited = null;
          }
       });


     getCellEditor().addCellEditorListener(new CellEditorListener() {
          public void editingStopped(ChangeEvent e) {
              TreePath tp = words.getSelectionPath();
              if(tp!=null){
                String text = ((jnode)tp.getLastPathComponent()).get();
                justedited = text;
              }
          }
          public void editingCanceled(ChangeEvent e) {}
     });
*/

      }
       public void paint(Graphics g) {
         super.paint(g);
         for(int n = 0; n < collabels.length; n++){
             int k = collabels[n].getLocation().x;
            g.drawLine(k, 0, k, this.getHeight());
         }
       }
        public TreeCellRenderer getCellRenderer() {
            return super.getCellRenderer();
        }



   }


    int indexOfPicName(ArrayList al, String name){
        for(int i = 0; i < al.size(); i++){
            if(((picitem)al.get(i)).name.equals(name)){
                return i;
            }
        }
        return -1;
    }   
   
   class picitem{
       String name;
       ArrayList bytes;

       public picitem(String nam){
           name = nam;
       }       
       public picitem(String nam, ArrayList recbytes){
           name = nam;
           bytes = recbytes;
       }
       
       public int indexOfBytes(Object item){
        if(!(item instanceof byte[]) && !(item instanceof String))return -1;
        for(int i = 0; bytes!=null && i < bytes.size(); i++){
            if(bytes.get(i) instanceof byte[] && Arrays.equals((byte[])bytes.get(i),(byte[])item))
                return i;
            else if(bytes.get(i) instanceof String && ((String)bytes.get(i)).equals(item)){
                return i;
            }
        }
        return -1;
       }
       
       public void removeBytes(int index){
           if(bytes!=null){
               bytes.remove(index);
               if(bytes.isEmpty())bytes = null;
           }
       }       
       
       public byte[] getNextPic(){
           for(int n = 0; bytes!=null && n < bytes.size(); n++){
                if((bytes.get(n)) instanceof byte[]){
                    return (byte[])bytes.get(n);
                }
           }
           return null;
       }
       
//       public void add(int i, byte[] b){
//           bytes.add(i, b);
//       } 
       public void addStringAt(String b, int pos){
           bytes.add(pos, b);
       }        
       public void add(byte[] b){
           bytes.add(b);
       } 
   }   
   
   
   static public class recitem{
       String name;
       byte[] rec;
    
       public recitem(String nam, byte[] recbytes){
           name = nam;
           rec = recbytes;
       }
   }


    class addflags extends treepainter {
        int sw = sharkStartFrame.mainFrame.getWidth();
        Image impic = sharkStartFrame.t.createImage(sharkStartFrame.publicPathplus + "sprites" +
                                sharkStartFrame.separator + "picopts_il48.png");
        Image imrec = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPathplus + "sprites" +
                                       sharkStartFrame.separator +
                                       "recordingAVAIL_il48.png");
        FontMetrics m;
        int recwid;
        int picwid;
        int rech;
        int pich;
        Font f;
        int h;
        int picsize;

        public addflags(){
           m = getFontMetrics(getFont());
           picsize = m.getHeight();
           impic = impic.getScaledInstance(picsize, picsize, Image.SCALE_SMOOTH);
           imrec = imrec.getScaledInstance(picsize, picsize, Image.SCALE_SMOOTH);

           MediaTracker tracker = new MediaTracker(this);
           tracker.addImage(impic, 1);
           tracker.addImage(imrec, 2);
           try {tracker.waitForAll();}
           catch (InterruptedException ie){}
           picwid = impic.getWidth(this);
           recwid = imrec.getWidth(this);
           pich = impic.getHeight(this);
           rech = imrec.getHeight(this);
         
        }

       public Dimension getPreferredSize() {
           String s = getText();
           int depth=16;
           int longest = -1;
           while(words==null ||  collabels==null || collabels[0].getLocation().x == 0)
               return new Dimension(m.stringWidth(s),
                         Math.max(depth,m.getHeight()));
           h = words.getRowHeight();
           String strlongest = null;
           f = sharkStartFrame.treefont.deriveFont(Font.PLAIN);
           m = getFontMetrics(f);
           addf.overridefont = null;
           jnode roo = (jnode)words.getModel().getRoot();
           for(int i = 0; i < roo.getChildCount() ; i++){
               jnode roo2 = (jnode)roo.getChildAt(i);
              int j = m.stringWidth(roo2.get());
              if(longest<j){
                  longest = j;
                  strlongest = roo2.get();
              }
           }
           int wid = collabels[0].getLocation().x;

           boolean changemade = false;
           while (longest > wid){
               changemade = true;
               f = f.deriveFont((float)f.getSize()-1);
               m = getFontMetrics(f);
               longest =  m.stringWidth(strlongest);
           }
           if(changemade)
             addf.overridefont = f;
           return(new Dimension(wid,
                         Math.max(depth,m.getHeight())));
       }
 
 public void paint(Graphics g) {
         String s = getText(),pic;

         super.paint(g);
         if(s.trim().equals(""))return;
         g.setClip(null);
         g.setColor(Color.black);
         int colxloc[] = new int[collabels.length];
         int colwid[] = new int[collabels.length];
         for(int n = 0; n < collabels.length; n++){
            colxloc[n] = collabels[n].getLocation().x;
            colwid[n] = collabels[n].getWidth();
         }
         g.setColor(Color.yellow);
         for(int n = 0; n < collabels.length; n++){
             int k = colxloc[n];
             int w = colwid[n];
             int xx;
             int yy;
             if(n==0 && hasrec(s)){
                xx = k + w/2 - (recwid/2);
                yy = (h-rech)/2;
                if(activeSymbolWord!=null && activeSymbolWord.equals(s) && activeSymbolCol==n)
                   g.fillRect(xx, yy, picsize, picsize);
                g.drawImage(imrec, xx, yy, this);
             }
             else if(n==1 && hasimage(s)){
                 xx = k + w/2 - (picwid/2);
                 yy = (h-rech)/2;
                if(activeSymbolWord!=null && activeSymbolWord.equals(s) && activeSymbolCol==n)
                   g.fillRect(xx, yy, picsize, picsize);
                g.drawImage(impic, xx, yy, this);
             }
             else if(collabels.length == 3 && n==2 && hasrecextra(s))
             {
                 xx = k + w/2 - (recwid/2);
                 yy = (h-rech)/2;
                if(activeSymbolWord!=null && activeSymbolWord.equals(s) && activeSymbolCol==n)
                   g.fillRect(xx, yy, picsize, picsize);
                g.drawImage(imrec, xx, yy, this);
             }
          }
        }
     }


public class doListExport implements Runnable{

    String[] currexportnames;
    String tofile;
    public boolean success = false;
    public doListExport(String names[], String tofile1){
        currexportnames = names;
        tofile = tofile1;
    }

  public void run(){
                for(int p = 0; p < currexportnames.length; p++){

                    String exportname = currexportnames[p];
                    String scurrnam = "";
                    for(int j = 0; j < exportname.length(); j++){
                      if((u.notAllowedInFileNames.indexOf(exportname.charAt(j)) < 0)){
                          scurrnam+=exportname.charAt(j);
                      }
                    }
//                     String s1 = scurrnam;
//                     if((k = s1.indexOf(u.phonicsplit))>=0){
//                         s1 = s1.substring(0, k).trim();
//                    }
                    String s1 = scurrnam;
                    s1 = s1.replaceAll(String.valueOf(u.phonicsplit), " ");
                    s1 = s1.replaceAll("/", " ");
                    s1 = s1.replaceAll("   ", " ");
                    s1 = s1.replaceAll("  ", " ");
                    String tempName = "~"+s1+String.valueOf(System.currentTimeMillis());
                    saveTreeWordList swt = (saveTreeWordList)db.find(sharkStartFrame.resourcesdb, exportname, db.TOPIC);



                    if(swt==null){
                        saveTree1 st = (saveTree1)db.find(dbname, exportname,db.TOPIC);
                        if(st==null){
                            u.okmess(shark.programName, u.gettext("ownwordlists_edit", "unable"), thisd);
                            return;
                        }
                        swt = new saveTreeWordList();
                        swt.names = st.curr.names;
                        // in case name shortened because bad file name
    //                    swt.names[0] = scurrnam;
                        swt.levels = st.curr.levels;
                        swt.adminlist = st.curr.adminlist;
                    }
                    db.update(tempName, exportname, swt,db.TOPIC);
                    for(int i = 1; i < swt.names.length; i++){
                        byte buf[] = (byte[])db.find(sharkStartFrame.resourcesdb, swt.names[i], db.PICTUREPLIST);
                        if(buf != null){
                            db.update(tempName, swt.names[i], buf, db.PICTUREPLIST);
                        }
                        else{
                            buf = (byte[])db.find(sharkStartFrame.resourcesdb, swt.names[i], db.PICTURE);
                            if(buf != null){
                                db.update(tempName, swt.names[i], buf, db.PICTURE);
                            }
                        }
                        if(db.query(sharkStartFrame.resourcesdb, swt.names[i], db.WAV) >= 0){
                            db.updatewav(tempName, swt.names[i], db.findwav(sharkStartFrame.resourcesdb, swt.names[i]));
                        }
                    }
                    File todelete =  new File(sharkStartFrame.sharedPathplus+tempName+".sha");
                    u.copyfile(
                            todelete,
                            new File(tofile+shark.sep+s1+".shw"));
                    success = true;
                    todelete.delete();
                }
  }
}

}
