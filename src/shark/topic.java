package shark;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import static shark.MYSQLUpload.MODE_VIA_DUMP;

public class topic  extends sharkTree{
  static String types[] = new String[] {
      "Games:",
      "Homophones:",
      "Select words:",
      "Select groups:",
      "Heading:",
      "Pair:",
      "All or none:",
      "APNotInTest",
      "APNotInUnitOrTest",
      "APPriority1",
      "APPriority2",
      "Picture preference:",
      "Blends:",
      "Courses:",     
      "FL:",
      "Forcekeypad:",
      "Game options:",
      "Inorder:",
      "Justphonics:",
      "Mark games:",
      "Mark games 2:",
      "Mark games code:",
      "Mark games headings:",
      "Nonsense:",
      "Not games:",
      "NotMixedWords:",
      "Notphonics:",
      "Phonicdistractors:",
      "Phrases:",
      "Revise:",
      "Select distractors:",
      "Startnonphonics:",
      "Startphonics:",
      "Topic",
      "IsUnitRevision",
  };
  public static String sentencegames[],sentencegames2[];
  static final byte GAMES = 0;
  static final byte HOMOPHONES = 1;
  static final byte SELITEMS = 2;
  static final byte SELGROUPS = 3;
  static final byte HEADING = 4;
  static final byte PAIR = 5;
  static final byte ALLORNONE = 6;
  static final byte APNOTINTEST = 7;
  static final byte APNOTINUNITORTEST = 8;
  static final byte APPRIORITY1 = 9;
  static final byte APPRIORITY2 = 10;
  static final byte PICTUREPREFERENCE = 11;
  static final byte BLENDS = 12;
  static final byte COURSES = 13;
  static final byte FL = 14;
  static final byte FORCEKEYPAD = 15;
  static final byte GAMEOPTIONS = 16;
  static final byte INORDER = 17;
  static final byte JUSTPHONICS = 18;
  static final byte MARKGAMES = 19;
  static final byte MARKGAMES2 = 20;
  static final byte MARKGAMESCODE = 21;
  static final byte MARKGAMESHEADINGS = 22;
  static final byte NONSENSE = 23;
  static final byte NOTGAMES = 24;
  static final byte NOTMIXEDWORDS = 25;
  static final byte NOTPHONICS = 26;
  static final byte PHONICDISTRACT = 27;
  static final byte PHRASES = 28;
  static final byte REVISE = 29;
  static final byte SELECTDISTRACTORS = 30; 
  static final byte STARTNONPHONICS = 31;
  static final byte STARTPHONICS = 32;
  static final byte TOPIC = 33;
  static final byte ISUNITREVISION = 34;

    /*
  static String types[] = new String[] {
      "Games:",
      "Not games:",
      "Heading:",
      "Pair:",
      "Select groups:",
      "Select words:",
      "Homophones:",
      "All or none:",
      "Courses:",
      "Mark games:",
      "Mark games 2:",
      "FL:",
      "Inorder:",
      "Game options:",
      "Forcekeypad:",
      "Phrases:",
      "Phonicdistractors:",
      "Justphonics:",
      "Startphonics:",
      "Startnonphonics:",
      "Blends:",
      "Topic",
      "Mark games headings:",
      "Revise:",
      "Notphonics:",
      "NotMixedWords:",
      "Nonsense:",
      "APNotInTest",
      "APNotInUnitOrTest",
      "APPriority1",
      "APPriority2",
      "Select distractors:"
  };
  public static String sentencegames[],sentencegames2[];
  static final byte GAMES = 0;
  static final byte NOTGAMES = 1;
  static final byte HEADING = 2;
  static final byte PAIR = 3;
  static final byte SELGROUPS = 4;
  static final byte SELITEMS = 5;
  static final byte HOMOPHONES = 6;
  static final byte ALLORNONE = 7;
  static final byte COURSES = 8;
  static final byte MARKGAMES = 9;
  static final byte MARKGAMES2 = 10;
  static final byte FL = 11;
  static final byte INORDER = 12;
  static final byte GAMEOPTIONS = 13;
  static final byte FORCEKEYPAD = 14;
  static final byte PHRASES = 15;
  static final byte PHONICDISTRACT = 16;
  static final byte JUSTPHONICS = 17;
  static final byte STARTPHONICS = 18;
  static final byte STARTNONPHONICS = 19;
  static final byte BLENDS = 20;
  static final byte TOPIC = 21;
  static final byte MARKGAMESHEADINGS = 22;
  static final byte REVISE = 23;
  static final byte NOTPHONICS = 24;
  static final byte NOTMIXEDWORDS = 25;
  static final byte NONSENSE = 26;
  static final byte APNOTINTEST = 27;
  static final byte APNOTINUNITORTEST = 28;
  static final byte APPRIORITY1 = 29;
  static final byte APPRIORITY2 = 30;
  static final byte SELECTDISTRACTORS = 31;  
  */
  public String databaseName, name;
//startPR2008-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static String inwordsharkfont = topicTree.ISTOPIC + topicTree.
//      ISPATH + u.mult + u.div + u.normalhyphen + u.phonicsplit +
//      "/[]{}~@#<>%�$&()*:;\"'!_+=,.? |";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public boolean old, canEdit, allhomos, fl, nonsense, inorder,justphonics, startphonics,startnonphonics,phonics,phonicsw,blended,phrases,aredups,shortlist,singlesound,superlist, superchanged, nopictures,forcekeypad,unitrevision,revision, notmixedwords,apnotintest,apnotinunitortest,APPriority1,APPriority2;
  // added for lists which pointed to other lists to get the words. If only
  // one or two of those others were phonics then caused problems in phonics
  // mode.
  public String picturePrefence = null;
  public boolean notphonics = false;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public String markgheading;
  public boolean alphabetonly;
  public boolean definitions = false;
  public boolean translations = false;
  public boolean ownlist;
  public xres xrecs[];
  public xres recs[];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public String markgformula;
  public String markgformula2;

  public String phonicdistractors[];
  String notgames[];
  /**
   * True if 4 or more words have been split into syllables
   * (Some games are not worth playing with less than 4 split words))
   */
  public boolean split4;
  public boolean unsplit;
  public boolean multisyll;
  jnode linkedNode;
  topicTree linkedTree;
  boolean invalidRoot, hadword, wantextend, wantrefs, referencedlist;
  static String savedgamelist[] = new String[0];
  static String savedgameflags[] = new String[0];
  public String headings[];
  public String patterns[];
  String[] markgames,markgames2,gameoptions;
  String markgamescode;
  String splitwords[];
  boolean alreadysplit, needonset;
  short oktot;
  String teachingnotes[];
  String gameoptionlist[];
  boolean invalid;
  boolean initfinished;  // getWords has been performed at least once. So all initialization has been done.
  boolean phonicscourse;
  public ArrayList headingSelects = new ArrayList();
  public String lastHeading = null;
  // saved details for if this is a superlist

  supersave superd;
  topic supert[];
  word superwords[][];
  int wordsource[];   // topic number that produced word
  String wordval[];       // words
  int specialsource;
  boolean okgames[];
//startPR2008-11-03^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
 // boolean autostarted;    //
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static wordlist.wordpicture currpic;      // v5 rb 6/3/08
  static wordlist.wordpicture currpic2;
             // game types
  static final char SPELLING=1;
  static final char RECOGNITION=2;
  static final char ALPHABET=3;
  static final char SPECIAL=4;
  static final char c0=0;
//  static final String deflast3games = new String( new char[] {topic.SPELLING,topic.SPELLING,topic.SPELLING,topic.SPELLING});
  static final String deflast3games = new String( new char[] {topic.SPELLING,topic.SPELLING,topic.SPELLING});
  static final int superwordtot = 8;
  static final int MINOWLRECORDINGS = 4;
  static final int MINOWLIMAGES = 4;
  
  static boolean superForceSpelling = true;
  static String EXCLUDEDWORDPREFIX = "EXP_";
  
  public int sentenceDistractorNo = -1;
  public int mySQL_Topic_ID;
           public String HEADING_TEXT = "Heading:";
          String HEADING_NO_TEXT = "No";
//----------------------------------------------------
  public static void makeList(JList list) {
     list.setListData(types);
  }
  public static byte getTypePos(String type) {
     for(byte i = 0; i < types.length; ++i) {
       if(type.startsWith(types[i])) return i;
     }
     return (byte)-1;
  }
  //---------------------------------------------------
  public void plugTopic(String type) {
     if(type== null) return;
     int t = getTypePos(type);
     jnode sel = getSelectedNode();
     if(sel != null && sel.get().equals("")) {                       //Condition 1
        stopEdit();
        saveOldValueForUndo(sel);
        set(sel,type);
           // Heading:   Select items:  Select groups:
        if(t == HEADING || t == SELGROUPS || t == SELITEMS || t == SELECTDISTRACTORS)  {       //Condition 1.1
           addEmpty((jnode)sel.getParent());
           startEditingAtPath(new TreePath(sel.getPath()));
        }
                   // Pairs:
        if(t == PAIR && sel.isLeaf()) {                              //Condition 1.2
           selectOne(dummy(sel));
           addEmpty((jnode)sel.getParent());
        }
       if(t == ALLORNONE && sel.isLeaf()) {                          //Condition 1.3
           selectOne(dummy(sel));
           addEmpty((jnode)sel.getParent());
        }
                   // Homophones:
        else if(t == HOMOPHONES && sel.isLeaf()) {                   //Condition 1.4
           addEmpty((jnode)sel.getParent());
           selectOne(dummy(sel));
        }
        else if(t == GAMES || t == NOTGAMES ) {                      //Condition 1.5
           addEmpty((jnode)sel.getParent());
           sharkStartFrame.mainFrame.addGameTree();
        }
        else if(t==GAMEOPTIONS) {
           sharkStartFrame.mainFrame.addGameTree();
        }
        else if( t==MARKGAMES ||t==MARKGAMES2||t==MARKGAMESCODE) {                                     //Condition 1.6
            addEmpty((jnode)sel.getParent());
            sharkStartFrame.mainFrame.addGameAndFormulaTree(t);
        }
        else if(t == COURSES) {                                      //Condition 1.7
           addEmpty((jnode)sel.getParent());
           sharkStartFrame.mainFrame.addCourseList();
        }
        else if(t == NONSENSE || t == FL || t == INORDER || t == FORCEKEYPAD ||t == PHRASES || t == BLENDS
                                  || t == NOTMIXEDWORDS || t == JUSTPHONICS ||t==STARTPHONICS||t==STARTNONPHONICS|| t == PHONICDISTRACT
                                    || t == REVISE || t == NOTPHONICS) {                                      //Condition 1.7
          addEmpty( (jnode) sel.getParent());
        }
//        else if(t == FL) {                                      //Condition 1.7
//          addEmpty( (jnode) sel.getParent());
//        }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        else if(t == MARKGAMESHEADINGS) {
          sharkStartFrame.mainFrame.addMGHeadings();
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
  }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void addMGHeading() {
    jnode gnode =  sharkStartFrame.mainFrame.markgamestree.getSelectedNode();
    if(gnode==null)return;
    String name = gnode.get();
    if(name == null) return;
    changed = true;
    jnode sel = getSelectedNode();
    if(name != null && sel != null) {
       String s = (String)sel.get();
       int i =  getTypePos(s),j;
       if(i == MARKGAMESHEADINGS) {
          int k;
          if((k=s.indexOf(":"))>=0){
            s = s.substring(0, k+1);
            set(sel,s+name);
          }
       }
   }
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void addMGFormula() {
    jnode gnode =  sharkStartFrame.mainFrame.markgamesformulatree.getSelectedNode();
    if(gnode==null)return;
    String name = gnode.get();
    if(name == null) return;
    changed = true;
    jnode sel = getSelectedNode();
    if(name != null && sel != null && gnode.getLevel()==3) {
        sel.setUserObject(types[MARKGAMESCODE]+name.substring(0, name.indexOf("=")));
        model.nodeChanged(sel);        
        this.expandPath(new TreePath(sel.getPath()));
   }
  }
  //---------------------------------------------------
  public void addGame() {
     jnode gnode =  sharkStartFrame.mainFrame.publicGameTree.getSelectedNode();
     if(gnode==null)return;
     String name = gnode.get();
     if(name == null) return;
     changed = true;
     jnode sel = getSelectedNode();
     if(name != null && sel != null) {
        String s = (String)sel.get();
        int i =  getTypePos(s),j;
        if(i == GAMES || i == NOTGAMES || i==MARKGAMES || i==MARKGAMES2|| i==MARKGAMESCODE) {
           jnode c[] = ((jnode)(sharkStartFrame.mainFrame.publicGameTree.root.getChildAt(0))).getChildren();
//           if(i==MARKGAMES || i==MARKGAMES2  || i==MARKGAMESCODE) for(j=0;j<c.length;++j) {
//               if (gnode.isNodeAncestor(c[j])) {name += "^" + String.valueOf(j) + "^"; break; }
//           }
           int k;
           if((k=s.indexOf(":"))>0){
               String games = s.substring(k+1);
               String gamesArr[] = u.splitString(games, ",");
               if(u.findString(gamesArr, name)< 0){
                if(s.charAt(s.length()-1) == ':') 
                    set(sel,s+name);
                else {
                  if(getformula(s)==null)
                     set(sel,s+","+name);
                  else {
                     String ss[] = u.splitString(s.substring(s.indexOf(':')+1),',');
                     if(u.findString(ss,name) < 0)   set(sel,s+","+name);
                  }
                }                   
               }
           }
        }
        else if(i == GAMEOPTIONS) {
         jnode cc[] = root.getChildren();
         for(j=0;j<cc.length;++j) {    // check not already entry for this game
           s = (String)sel.get();
           int pos =  getTypePos(s);
           if(pos == GAMEOPTIONS &&  gamename(s).equals(name)) return;
         }
         String opt1,pa[];
         if ( (pa = sharkStartFrame.mainFrame.publicGameTree.getparms(name)) != null
             && pa.length > 0
             && (opt1 = sharkStartFrame.mainFrame.publicGameTree.getparm(pa, "options")) != null) {
          if(opt1.length()==0) {
              u.okmess("options-none");
              return;
           }
           String opt[] = u.splitString(opt1,',');
           i = s.indexOf(',') + 1;
           if(i>0) opt = u.addString(opt,u.splitString(s.substring(i),','));
           options oo = new options(u.edit("Set options for %", name),opt,"Change options if required",name);
           String ss = oo.getpreset();
           if(oo.changed) changed = true;
           stopEdit();
           if(ss.length() == 0)   set(sel,"");
           else set(sel, types[GAMEOPTIONS] + name + "," + ss);
         }
       }
    }
  }
  String gamename(String s) {   // get game name from gameoptions entry
   int start = s.indexOf(':')+1;
   int end = s.indexOf(',',start);
   if(end>0) return s.substring(start,end);
   else return "";
  }
  //---------------------------------------------------
  public void addCourse(String name) {
     if(name == null) return;
     jnode sel = getSelectedNode();
     if(name != null && sel!= null) {
        String s = sel.get();
        int i =  getTypePos(s);
        if(i == COURSES) {
           if(s.indexOf(name) < 0) {
              if(s.charAt(s.length()-1) == ':') set(sel,s+name);
              else              set(sel,s+","+name);
           }
        }
     }
  }
   //-------------------------------------------------------
  public void endedit(TreeModelEvent e) {
     jnode sel = (jnode)getSelectedNode();
     int type = getTypePos(sel.get());
     String newname = root.get();

     if(copying) return;
     if(sel==root) {
       if(!newname.equals(valueBeforeEdit)) {
        if(!newname.equalsIgnoreCase(name) && db.query(databaseName, newname, db.TOPIC) >= 0) {
            if(u.yesnomess(
                    "Renaming topic",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "New topic name already exists. Do you want to UNDO the change?")) {
                            "New topic name already exists. Do you want to UNDO the change?", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                copying = true;
                set(root,valueBeforeEdit);
                copying = false;
                invalidRoot = false;
             }
             else {
                invalidRoot = true;
                copying = true;
                startEditingAtPath(new TreePath(root.getPath()));
                copying = false;
             }
             return;
        }
        if(linkedNode != null) {
           linkedTree.copying = true;
           if(((jnode)linkedNode.getParent()).get().equals(topicTree.TOPICLISTNAME)) {
             set(linkedNode,newname);
           }
           else if(databaseName.equals(publicname))
              set(linkedNode,topicTree.ISTOPIC + root.get());
           else set(linkedNode,topicTree.ISTOPIC + databaseName + topicTree.SEPARATOR + root.get());
           linkedTree.addEmpty((jnode)linkedNode.getParent());
           linkedTree.recordChange(linkedNode);
           linkedTree.copying = false;
        }
        changed = true;
        invalidRoot = false;
        valueBeforeEdit =  newname;
        addEmpty(root);
        this.setSelectionPath(new TreePath(((jnode)(root.getFirstChild())).getPath()));
       }
       return;
     }
     if(type < 0) {
        stripspaces(sel);
        if(sel != root && !sel.get().equals("") && sel.getNextSibling() == null) {
           jnode parent = (jnode)sel.getParent(),c2;
           if(canAddChild(parent)) {
               selectOne(dummy(parent));
           }
        }
     }
     else if(checkok(sel,type)) {
        if(sel.isLeaf() && canAddChild(sel)) selectOne(dummy(sel));
     }
     saveOldValueForUndo(sel);
     changed = true;     // not sure this is right. automatically assumes a change has been made if node clicked into
     valueBeforeEdit =  newname;
}
 //-------------------------------------------------------
  public void selection() {
     jnode sel = getSelectedNode();
     if(sel == null) return;
     if(invalidRoot && !sel.isRoot()) {
         startEditingAtPath(new TreePath(root.getPath()));;
        return;
     }
     valueBeforeEdit = sel.get();
     if(valueBeforeEdit.length()>0){
         if( valueBeforeEdit.charAt(0)=='\\') {
            stripjustcomments();
            new stringedit_base("Edit teacher notes",teachingnotes) {
                  public boolean update(String s[]) {
                     teachingnotes = null;
                      jnode jj;
                     TreePath tt[] = new TreePath[s.length];
                     for(int i=0;i<s.length;++i) {
                         model.insertNodeInto(jj = new jnode("\\"+s[i]),root,root.getChildCount());
                         tt[i] = new TreePath(jj.getPath());
                     }
                    setSelectionPaths(tt);
                    changed = true;
                    return true;
                  }
            };
        }
        if(valueBeforeEdit.startsWith(topicTree.ISTOPIC)){    
            topic t = sharkStartFrame.mainFrame.topicList.checkTopic(sel);
            if(t != null)
               sharkStartFrame.mainFrame.addTopic2(t);
        }
     }
     
     
     if(!sel.isRoot() && valueBeforeEdit.equals(""))  {
        sharkStartFrame.mainFrame.addTopicOptions();
        return;
     }
     else {
        int i = getTypePos(valueBeforeEdit);
        if(i == GAMES || i == NOTGAMES) {
           sharkStartFrame.mainFrame.addGameTree();
           return;
        }
         else if(i == MARKGAMES || i == MARKGAMES2|| i == MARKGAMESCODE) {
           sharkStartFrame.mainFrame.addGameAndFormulaTree(i);
           return;
        }
        if(i == COURSES) {
           sharkStartFrame.mainFrame.addCourseList();
           return;
        }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        else if(i == MARKGAMESHEADINGS) {
          sharkStartFrame.mainFrame.addMGHeadings();
          return;
        }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(i==GAMEOPTIONS) {
          String s = valueBeforeEdit;
          String opt1,pa[];
          String name = gamename(s);
          if(name.length() == 0) {
            sharkStartFrame.mainFrame.addGameTree();
            return;
          }
          if ( (pa = sharkStartFrame.mainFrame.publicGameTree.getparms(name)) != null
              && pa.length > 0
              && (opt1 = sharkStartFrame.mainFrame.publicGameTree.getparm(pa, "options")) != null) {
            String opt[] = u.splitString(opt1,',');
            i = s.indexOf(',') + 1;
            if(i>0) opt = u.addString(opt,u.splitString(s.substring(i),','));
            options oo = new options(u.edit("Set options for %", name),opt,"Change options if required",name);
            String ss = oo.getpreset();
            stopEdit();
            if(oo.changed) changed = true;
            if(ss.length() == 0)   set(sel,"");
            else    set(sel, types[GAMEOPTIONS]  + name + "," +ss);
          }
        }
     }
     sharkStartFrame.mainFrame.removeExtra();
  }
  

    static Object[] getExcludedWord(String database, String[] path, String word){
        // positive number - the index of this path in stored array
        // -1  - not present in existing stored array
        // -2  - no existing stored array for this word
        String sss[][] = (String[][])db.find(database, topic.EXCLUDEDWORDPREFIX+word, db.TEXT);
        if(sss == null || sss.length==0)return new Object[]{-2, null};
        for(int i = 0; i < sss.length; i++){
            if(sss[i]==null) sss = u2_base.removeStringArray(sss, i);
        }
        if(sss.length==0)return new Object[]{-2, null};
        boolean found = false;
        int i;
        for(i = 0; i < sss.length; i++){
            if(Arrays.equals(sss[i], path)){
                found  = true;
                break;
            }
        }
        if(!found)return new Object[]{-1, sss};
        return new Object[]{i, sss};
    }  
  
    
  
  public void addWordExclusions(String path[]){
         for(Enumeration e = root.children();e.hasMoreElements();) {
            jnode c = (jnode)e.nextElement();
            String s  = c.get();
            String database = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
            if(s.length() > 0
                       && topic.getTypePos(s) < 0) {
                s = new word(s, database).v();
                Object o[] = topic.getExcludedWord(database, path, s);
                c.isExcludedWord = ((int)o[0]) >=0;
            }
         }
  }      
    
  //----------------------- validity checking for new keyword entry
  void stripspaces(jnode g) {
     String s = g.get();
     int i;
     boolean changed = false;
     while(s.length()>0 && s.charAt(0) == ' ') {s = s.substring(1);changed=true;}
     while(s.length()>1 && s.charAt(s.length()-1) == ' ')
          {s = s.substring(0,s.length()-1);changed=true;}
     while((i = s.indexOf("..")) > 0) {changed=true; s = s.substring(0,i) + u.phonicsplit + s.substring(i+2);}
     String special = null;
     if(s.indexOf(u.phonicsplit)>=0){
         int k ;
         if((k=s.indexOf("=@@"))>=0){
             special = s.substring(k);
         }
     }
//     if(s.indexOf(u.phonicsplit)>=0 && s.endsWith(special)) {
     if(special!=null) {
         changed=true;
         s = s.substring(0, s.lastIndexOf(special)+1);
         s = s + s.substring(0,s.length()-1);
         s = s += special.substring(1);
     }
     else if(s.indexOf(u.phonicsplit)>=0 && s.endsWith("=")) {changed=true;s = s + s.substring(0,s.length()-1);}
//     if(s.indexOf(u.phonicsplit)>=0 && s.endsWith("=")) {changed=true;s = s + s.substring(0,s.length()-1);}
     if(changed) set(g,s);
  }
  //----------------------- validity checking for new keyword entry
  boolean checkok(jnode g, int i) {
     String s = g.get();
     switch(i) {
        case SELGROUPS: case SELITEMS:
            String ss = s.substring(types[i].length());
               if(u.getint(ss) <= 0) return false;
            return true;
     }
     return true;
  }

  //---------------------------------------------------------
  public void setTopics(String value) {
     TreePath p[] = getSelectionPaths();
     for(short i =0; i < p.length; ++i) {
        jnode sel = (jnode)p[i].getLastPathComponent();
        if(!sel.isRoot() && sel.isLeaf())  {
           saveOldValueForUndo(sel);
           set(sel,valueBeforeEdit = value);
        }
     }
  }
  //---------------------------------------------------------
  boolean canAddChild(jnode node)  {
     String s = node.get();
     short i = getTypePos(s);
     boolean ret = true;

     if(s.equals("")) ret = false;
     else if(i == MARKGAMES|| i==MARKGAMES2|| i==MARKGAMESCODE || i==GAMEOPTIONS) ret = false;
     else if(i < 0 && node != root) ret = false;
     else if(s.substring(0,1).equals(topicTree.ISTOPIC) || s.substring(0,1).equals(topicTree.ISPATH))
                ret = false;
     else if(i == PAIR) { if(node.getChildCount() >= 2) ret = false;}
     else if(i == TOPIC) ret = false;
     else if(i == NONSENSE || i == FL || i ==INORDER || i == FORCEKEYPAD || i == PHRASES || i == BLENDS
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             || i == MARKGAMESHEADINGS
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             || i == NOTMIXEDWORDS || i == JUSTPHONICS||i==STARTPHONICS||i==STARTNONPHONICS ||i==PHONICDISTRACT
             ||i==REVISE||i==NOTPHONICS) return false;
     return ret;
  }
   //--------------------------------------------------------
  public static topic findtopic(String s) {
        int i;
        if((i=(short)s.indexOf(topicTree.SEPARATOR)) > 0) {
            if(s.charAt(0) == topicTree.ISTOPIC.charAt(0))
              return new topic(s.substring(1,i), s.substring(i+1),null,null);
            else
              return new topic(s.substring(0,i), s.substring(i+1),null,null);
        }
        else  return new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),s,null,null);
  }
  //-------------------------------------------------------------
  public topic(String database1,topicTree tree,jnode node) {
     name = new String("<enter name for new topic>");
     root.setIcon(jnode.ROOTUPDATETOPIC);
     databaseName = database1;
     linkedNode = node;
     linkedTree = tree;
//     setExpandByDefault(false);
     set(root,name);
     updating = true;
     this.setToggleClickCount(4);
     this.setEditable(true);
     setInvokesStopCellEditing(true);
     valueBeforeEdit = new String(name);
     dummy(root);
     this.expandPath(new TreePath(dummy(root).getPath()));
     getCellEditor().addCellEditorListener(new CellEditorListener() {
        public void editingStopped(ChangeEvent e) {
          wasedit=true;
          editnode = null;
        }
        public void editingCanceled(ChangeEvent e) {
          if (editnode != null) { // auto start from typing in char
            editnode.setUserObject(saveuserobject);
            editnode = null;
          }
        }
   });
   addKeyListener( new KeyAdapter()   {
     public void keyPressed(KeyEvent e) {
       keypressed(e);
     }

     public void keyReleased(KeyEvent e) {
       keyreleased(e);
     }
   });
     model.addTreeModelListener( new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {
              if(wasedit) {
                  wasedit=false; endedit(e);
              }
          }
          public void treeNodesInserted(TreeModelEvent e) {}
          public void treeNodesRemoved(TreeModelEvent e) {}
          public void treeStructureChanged(TreeModelEvent e) {}
     });
  }
  //-------------------------------------------------------------
  public topic(String database1,String name1,topicTree tree,jnode node) {
     name = name1;
     root.setIcon(jnode.ROOTUPDATETOPIC);
     while(name.length() > 0) {
        if(name.charAt(name.length()-1)=='+') {
           wantextend = true;
           name = name.substring(0,name.length()-1);
        }
        else if(name.charAt(name.length()-1)=='&') {
           wantrefs = true;
           name = name.substring(0,name.length()-1);
        }
        else break;
     }

     databaseName = database1;
     linkedNode = node;
     linkedTree = tree;
     if(updating = (sharkStartFrame.mainFrame.topicTreeList != null
        &&   sharkStartFrame.mainFrame.topicTreeList.includesDatabase(databaseName))) {
        this.setEditable(true);
       this.setToggleClickCount(4);
        setInvokesStopCellEditing(true);
      }
     set(root,name);

     saveTree1 s = null;
     Object o = db.find(database1,name,db.TOPIC);
     if(o instanceof saveTreeWordList){
        s = new saveTree1((saveTreeWordList)o);
     }
     else
        s = (saveTree1)o;     
     boolean extracourse;
     if( (extracourse = u.findString(sharkStartFrame.publicExtraCourseLib, database1)>=0 ) || (s!=null && s.curr!=null && s.curr.adminlist)){
         String db2 = extracourse?database1:sharkStartFrame.resourcesPlus+database1+sharkStartFrame.resourcesFileSuffix;
         o = db.find(db2,name,db.TOPIC);
         if(o instanceof saveTreeWordList){
             saveTreeWordList stwl =
                     (saveTreeWordList)db.find(db2,name,db.TOPIC);
             if(stwl==null && database1.startsWith("~"))
                 stwl = (saveTreeWordList)db.find(database1,name,db.TOPIC);

             for(int i = 1; stwl!=null && stwl.extrarecs!=null && i< stwl.names.length && i< stwl.extrarecs.length; i++){
                 if(stwl.extrarecs[i] != null){
                     if(xrecs==null)xrecs = new xres[]{new xres(stwl.names[i], stwl.extrarecs[i])};
                     else xrecs = u.addxres(xrecs, new xres(stwl.names[i], stwl.extrarecs[i]));
                 }
             }
             for(int i = 1; stwl!=null && stwl.recs!=null && i< stwl.names.length && i< stwl.recs.length; i++){
                 if(stwl.recs[i] != null){
                     if(recs==null)recs = new xres[]{new xres(stwl.names[i], stwl.recs[i])};
                     else recs = u.addxres(recs, new xres(stwl.names[i], stwl.recs[i]));
                 }
             }
         }
     }

     ownlist = true;
     for(short j=0;j<sharkStartFrame.publicTopicLib.length;++j) {
        if(databaseName.equals(topicTree.publictopics)){
            ownlist = false;
            break;
        }
     }
     if(s == null) {
        invalid = true;
        set(root,"<topic not found>");
        dummy(root);
     }
     else {
         if(s.curr.type == OwnWordLists.TYPE_TRANSLATIONS){
             translations = true;
             fl = true;
         }
         else if(s.curr.type == OwnWordLists.TYPE_DEFINITIONS){
             definitions = true;
             fl = true;
         }
        s.addToTree(this, root);
        old = true;
        valueBeforeEdit = new String(name);
     }
     setSelectionPath(new TreePath(root.getPath()));
     expandPath(new TreePath(root.getPath()));
     if(updating) {
       getCellEditor().addCellEditorListener(new CellEditorListener() {
          public void editingStopped(ChangeEvent e) {
            wasedit=true;
            editnode = null;
          }
          public void editingCanceled(ChangeEvent e) {
            if (editnode != null) { // auto start from typing in char
              editnode.setUserObject(saveuserobject);
              editnode = null;
            }
          }
        });
        addKeyListener( new KeyAdapter()   {
          public void keyPressed(KeyEvent e) {
            keypressed(e);
          }

          public void keyReleased(KeyEvent e) {
            keyreleased(e);
          }
        });
        model.addTreeModelListener( new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {
              if(wasedit) {
                  wasedit=false; endedit(e);
              }
          }
          public void treeNodesInserted(TreeModelEvent e) {}
          public void treeNodesRemoved(TreeModelEvent e) {}
          public void treeStructureChanged(TreeModelEvent e) {}
        });
     }
     else {
       stripcomments();
       if(superlist) setupsuper();
     }
  }
  //-------------------------------------------------------------
      // a superlist for a heading node
  public topic(String name1,String path) {
     name = name1;
     int pos = path.indexOf(topicTree.SEPARATOR,1);
     databaseName = path.substring(1,pos);
     set(root,name);
     root.addChild(new jnode(path));
     superlist = true;
     setupsuper();
  }
  //-------------------------------------------------------------
      // a superlist for a heading node in an assigned program
  public topic(jnode node) {
     int i;
     name = node.get();
     databaseName = this.publicname;
     set(root,name);
     supert = sharkStartFrame.mainFrame.topicList.getTopics(node);
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     boolean already = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     for(i=0;i<supert.length;++i) {
       if(!supert[i].initfinished)
           supert[i].getWords(null,false);
       if(supert[i].phonics && !supert[i].phonicsw)
         continue;
       if(supert[i].fl) fl = true;   // pick up 'foreign language' indicator
       if(supert[i].nonsense) nonsense = true;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(supert[i].markgheading!=null && !already){
         markgheading = supert[i].markgheading;
         already = true;
       }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(supert[i].markgformula!=null && !already){
         markgformula = supert[i].markgformula;
         already = true;
       }
       if(supert[i].markgformula2!=null && !already){
         markgformula2 = supert[i].markgformula2;
         already = true;
       }

       if(!supert[i].phonicsw)
           phonics = phonicsw = false;
       else if(i == 0)
           phonics = phonicsw = true;
     }
     superlist = true;
     setupsuper();
  }
  //----------------------------------------------------------
  
    public void getSplits() { 
      if(shark.wantPublicSplits && wordlist.splitsInDevMode){
         splitwords = (String[])db.find(sharkStartFrame.publicSplitsLib[0], name, db.TEXT);
     }
     else{
        if(databaseName.equals(publicname)) {
            splitwords = (String[])db.find(sharkStartFrame.optionsdb, name, db.TEXT);
        }
        else {
            splitwords = (String[])db.find(sharkStartFrame.optionsdb, databaseName + "+++" + name,db.TEXT);
        }         
     }       
    }   
  
  
  
  
       // this also checks for all pointers - ie a superlist
  void stripcomments() {
    String s;
    jnode c[] = root.getChildren();
    superlist=true;
    for(short i=0;i<c.length;++i) {
         if((s=c[i].get()).length()>0) {
           int type = this.getTypePos(s);
            if(s.charAt(0) =='\\') {
             teachingnotes = u.addString(teachingnotes, s.substring(1));
             this.model.removeNodeFromParent(c[i]);
           }
           else if(type == GAMEOPTIONS) {
               gameoptionlist = u.addStringSort(gameoptionlist, s.substring(s.indexOf(':')+1));
               this.model.removeNodeFromParent(c[i]);
           }
          else if(!s.substring(0,1).equals(topicTree.ISPATH)
                    && !s.substring(0,1).equals(topicTree.ISTOPIC)) superlist=false;
         }
    }
   }
   //----------------------------------------------------------
        // this also checks for all pointers - ie a superlist
   void stripjustcomments() {
     String s;
     jnode c[] = root.getChildren();
     superlist=true;
     for(short i=0;i<c.length;++i) {
          if((s=c[i].get()).length()>0) {
            int type = this.getTypePos(s);
            if (s.charAt(0) == '\\') {
              teachingnotes = u.addString(teachingnotes, s.substring(1));
              this.model.removeNodeFromParent(c[i]);
            }
          }
     }
    }
   //------------------------------------------------------------------------
  void setupsuper() {
    String s;
    int i, j;
    allhomos = true;
    if(supert == null) {
      jnode c[] = root.getChildren();
      supert = new topic[0];
      for (i = 0; i < c.length; ++i) {
        if ( (s = c[i].get()).length() <= 0)
          continue;
        if (s.substring(0, 1).equals(topicTree.ISPATH)) {
          topic t[] = topicTree.getTopics(s);
          for (i = 0; i < t.length; ++i) {
            if (t[i] != null && !t[i].topicinlist(supert) && !t[i].superlist) {
              t[i].getWords(null,false);  // complete initialization
              if((!(t[i].phonics && !t[i].phonicsw)) && !t[i].revision){
                  supert = u.addTopic(supert, t[i]);
                  if(t[i].fl)
                      fl = true;   // pick up 'foreign language' indicator
                  if(t[i].nonsense)
                      nonsense = true;
                  if(!t[i].phonicsw)
                      phonics = phonicsw = false;
                  else if(supert.length==1)
                      phonics = phonicsw = true;
              }
            }
          }
        }
        else if (s.substring(0, 1).equals(topicTree.ISTOPIC)) {
          int pos = s.substring(1).indexOf(topicTree.SEPARATOR) + 1;
          topic t = null;
          if (pos > 0)
            t = new topic(s.substring(1, pos), s.substring(pos + 1), null, null);
          if (t != null && !t.topicinlist(supert) && !t.superlist) {
              t.getWords(null,false);  // complete initialization
              if(!t.revision)
                 supert = u.addTopic(supert, t);
          }
        }
      }
    }
    superd = new supersave();
    superd.d = new superlistel[supert.length];
    superwords = new word[supert.length][];
    superd.name = name;
    for (i = 0; i < supert.length; ++i) {
      superd.d[i] = new superlistel();
      superd.d[i].topicname = supert[i].name;
    }
    wordsource = new int[superwordtot];
    wordval = new String[0];
    supergames();  // set up okgames for allowed games for this group
  }
  //----------------------------------------------------------------------------
  public String supersource(String val) {
    int i = u.findString(wordval,val);
    if(i<0) return "";
    return supert[wordsource[i]].name;
  }
  //--------------------------------------------------------------------------
  void supergames() {
    int i,j;
    String game;
    okgames = new boolean[sharkStartFrame.mainFrame.gamename.length];
    loop1:for(i=0; i<okgames.length;++i) {
      game = sharkStartFrame.mainFrame.gamename[i];
      sharkStartFrame.gameflag ff = sharkStartFrame.gameflags[i];
      if(ff.special || ff.pairedwords || ff.needbad
//           || ff.flonly || ff.notfl || ff.needonset) {                    // rb 18/2/06
        || ff.flonly || ff.notfl || ff.needonset || ff.needsentences1 || ff.needsentences3 || ff.needchunks) {  // Bug Fix: 97, 98
          for(j=0;j<supert.length;++j) {
              if(supert[j].testWord(game)) {okgames[i] = true; continue loop1;}
          }
          continue loop1;    // not allowed
      }
//      if(ff.phonics || ff.phonicsw) continue;            // rb 18/2/06
      if(ff.justpairedwords) continue loop1;
      okgames[i] = true;
    }
  }
  //-----------------------------------------------------------------------
  public void setForUpdate() {
     addEmpty(root);
     clearSelection();
     this.expandRow(0);
  }
  //---------------------------------------------------------
       // Get the normal or extended words as displayed on menu
       // and used in non-bucket games
  public word[] getWords(String[] gamelist,boolean extended) {
     if(superlist) {
       return getsuperwords(gamelist);
     }
     short i,j;
     byte type;

     word words[] = new word[0];
     wantrefs = true;
     topic alreadyHad[] = new topic[]{this};
     if(!root.isLeaf()) {
        if(extended) {      // include all except normal words directly under root
           for(Enumeration e = root.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              type = getTypePos(c.get());
              if(type == HOMOPHONES && c.isLeaf())
                allhomos = true;
              else if(type == NOTGAMES && c.isLeaf()) {
                String s = c.get();
                notgames = u.splitString(s.substring(s.indexOf(':')+1),',', true);
              }
              else if(type == FL)
                  fl = true;
              else if(type == NONSENSE)
                  nonsense = true;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == MARKGAMESHEADINGS){
                String s1 = c.get();
                markgheading = s1.substring(s1.indexOf(':')+1);
                if(markgheading.trim().equals(""))markgheading=null;
              }
//              else if(type == ALPHA)      // alphabet change
//                  alphabetonly = true;    // alphabet change
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == ISUNITREVISION)
                  unitrevision = true;
              else if(type == REVISE)
                  revision = true;
              else if(type == PICTUREPREFERENCE)
                  picturePrefence = c.get().substring(c.get().indexOf(':')+1);
              else if(type == APPRIORITY1)
                  APPriority1 = true;              
              else if(type == APPRIORITY2)
                  APPriority2 = true;    
              else if(type == APNOTINTEST)
                  apnotintest = true;
              else if(type == APNOTINUNITORTEST)
                  apnotinunitortest = true;
              else if(type == NOTPHONICS)
                  notphonics = true;              
              else if(type == INORDER) inorder = true;
              else if(type == FORCEKEYPAD) forcekeypad = true;
              else if(type == PHRASES) phrases = true;
              else if(type == BLENDS) blended = true;
              else if(type == JUSTPHONICS) justphonics = true;
              else if(type == NOTMIXEDWORDS) notmixedwords = true;
              else if(type == STARTPHONICS) startphonics = true;
              else if(type == STARTNONPHONICS) startnonphonics = true;
              else if(type == MARKGAMES){
                  String s1 = c.get();
                  markgformula = getformula(s1);
                  if(markgformula==null){
                      markgames = splitlist(c.get());
                  }
              }
              else if(type == MARKGAMES2) {
                  String s1 = c.get();
                  markgformula2 = getformula(s1);
                  if(markgformula2==null){
                      markgames2 = splitlist(c.get());
                  }
              }
              else if(type == MARKGAMESCODE) {
                  String s1 = c.get();
                  int p = s1.indexOf(":");
                  if(p >= 0){
                      markgamescode = s1.substring(p+1);
                  }
              }
              else if(type == SELITEMS || type == SELGROUPS || (gamelist != null && type == GAMES)) {
                  words = u.addWords(words,getWords(gamelist,c,alreadyHad));
              }
              else if(type == PHONICDISTRACT) {
                  String s = c.get();
                  phonicdistractors = u.addStringSort(phonicdistractors,s.substring(s.indexOf(':')+1));
              }

           }
        }
        else {        // include all except selection parts
           for(Enumeration e = root.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              type = getTypePos(c.get());
              if(type == HOMOPHONES && c.isLeaf())
                allhomos = true;
              else if(type == NOTGAMES && c.isLeaf()) {
               String s = c.get();
               notgames = u.splitString(s.substring(s.indexOf(':')+1),',');
             }
             else if(type == FL) fl = true;
             else if(type == NONSENSE) nonsense = true;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             else if(type == MARKGAMESHEADINGS){
               String s1 = c.get();
               markgheading = s1.substring(s1.indexOf(':')+1);
               if(markgheading.trim().equals(""))markgheading=null;
             }
//               else if(type == ALPHA)     // alphabet change
//                   alphabetonly = true;   // alphabet change
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == ISUNITREVISION)
                  unitrevision = true;
              else if(type == REVISE)
                   revision = true;
              else if(type == PICTUREPREFERENCE)
                  picturePrefence = c.get().substring(c.get().indexOf(':')+1);
              else if(type == APPRIORITY1)
                  APPriority1 = true;              
              else if(type == APPRIORITY2)
                  APPriority2 = true;    
              else if(type == APNOTINTEST)
                  apnotintest = true;
              else if(type == APNOTINUNITORTEST)
                  apnotinunitortest = true;
              else if(type == NOTPHONICS)
                  notphonics = true; 
              else if(type == FORCEKEYPAD) forcekeypad = true;
              else if(type == PHRASES) phrases = true;
                else if(type == BLENDS) blended = true;
               else if(type == JUSTPHONICS) justphonics = true;
              else if(type == NOTMIXEDWORDS) notmixedwords = true;
              else if(type == STARTPHONICS) startphonics = true;
              else if(type == STARTNONPHONICS) startnonphonics = true;
              else if(type == INORDER) inorder = true;
              else if(type == MARKGAMES){
                  String s1 = c.get();
                  markgformula = getformula(s1);
                  if(markgformula==null){
                      markgames = splitlist(c.get());
                  }
              }
              else if(type == MARKGAMES2) {
                  String s1 = c.get();
                  markgformula2 = getformula(s1);
                  if(markgformula2==null){
                      markgames2 = splitlist(c.get());
                  }
              }
              else if(type == MARKGAMESCODE) {                  
                  String s1 = c.get();
                  int p = s1.indexOf(":");
                  if(p >= 0){
                      markgamescode = s1.substring(p+1);
                  }
              }
              else if(type == GAMEOPTIONS) {
                   String s = c.get();
                   gameoptionlist = u.addStringSort(gameoptionlist, s.substring(s.indexOf(':')+1));
              }
              else if(referencedlist && type==PAIR) continue;
              else if(type == PHONICDISTRACT) {
                   String s = c.get();
                   phonicdistractors = u.addStringSort(phonicdistractors,s.substring(s.indexOf(':')+1));
              }
              else if(type != SELITEMS && type != SELGROUPS && (gamelist != null || type != GAMES))  {
                 words = u.addWords(words,getWords(gamelist,c,alreadyHad));
              }

           }
        }
//startPR2007-12-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        if(Demo.isDemo)
//          notgames = u.addString(notgames, sharkStartFrame.nodemogames);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     if(allhomos) for(i=0;i<words.length;++i) words[i].homophone=true;
     loop1:for(i=0;i<words.length;++i) {
       if(!words[i].paired && words[i].companions==0)
         for(j=(short)(i+1);j<words.length;++j) {
           if (!words[j].paired && words[j].companions==0
               && words[i].value.equals(words[j].value)) {aredups = true; break loop1; }
       }
     }
//     if(words.length<4) shortlist = true;   // rb 24/1/07
     shortlist = words.length<4;
     if(!initfinished) {
       boolean notsingle = false;
       word wordsx[];
       if(!extended) wordsx = getAllWords(true);
         else wordsx = words;

       for (i = 0; i < wordsx.length; ++i) { // do phonics on big list first in case only extended
         if (wordsx[i].value.indexOf('=') > 0) {
          if(!notphonics) phonics = true;
           if (wordsx[i].value.indexOf(u.phonicsplit) > 0) {
              if(!notphonics) phonicsw = true;
           }
           if (!(phonics && !phonicsw && wordsx[i].value.length() == 3 && wordsx[i].value.indexOf('=') == 1))
             notsingle = true; // indicate single phonics letter
           if(notsingle) break;
         }
       }
       int pictot = 0;
       if (!superlist) { // phonics && pictures check is done on non-extended list
         initfinished = true;
         word ww[] = getWords(null, false);
         if (ww.length > 0) {
           phonicsw = phonics = blended = singlesound = multisyll = false;
           int multisylltot = 0;
           for (i = 0; i < ww.length; ++i) {
             if (u.syllabletot(ww[i].v()) > 1 && ++multisylltot >= 4) {
                 multisyll = true; break; 
             }
           }
           for (i = 0; i < ww.length; ++i) {
              if (ww[i].value.indexOf('=') > 0) {
               if(!notphonics) phonics = true;
               if (ww[i].value.indexOf(u.phonicsplit) > 0) {
                 if(!notphonics)phonicsw = true;
               }
               if (!(phonics && !phonicsw && ww[i].value.length() == 3 && ww[i].value.indexOf('=') == 1))
                 notsingle=true; // indicate single phonics letter
               if(notsingle) break;
             }
           }
         }
         for (i = 0; i < ww.length; ++i) {
           if (!ww[i].bad && ww[i].companions==0 && sharkImage.exists(ww[i].vpic(), this)) {
             ++pictot;
             if (pictot >= MINOWLIMAGES)
               break;
           }
         }
         if (pictot < MINOWLIMAGES)      nopictures = true;
         if(phonics && !phonicsw && !notsingle) singlesound = true;
       }
       else  nopictures = true;
     }
                        // filter the marked games according to game subset
     initfinished = true;
     return words;
  }
  //---------------------------------------------------------------
  int maxused() {
    int min=0x7fffffff,max=0,i;
    for(i=0;i<superd.d.length;++i) {
      min = Math.min(superd.d[i].used,min);
      max = Math.max(superd.d[i].used,max);
    }
    if(min==max) {
      for(i=0;i<superd.d.length;++i) {
        superd.d[i].used=0;
      }
      return 1;
    }
    else return min + 1;

  }
  //----------------------------------------------------------
  word[] getsuperwords(String[] gamelist) {
    int max = maxused();
    int i, j, k, kk, m, o[];
    word words[] = new word[0], words2[],ww;
    wordval = new String[0];
    String soundlists[] = new String[]{};
    for(int n = 0; n < supert.length; n++){
        if(supert[n].phonics && !supert[n].phonicsw)
            soundlists = u.addString(soundlists, supert[n].name);
    }
    for(i=0;words.length<superwordtot && i<superd.d.length;++i) {
       if(superwords[i] == null) superwords[i] = supert[i].getAllWordsBoth();
       if(u.findString(soundlists, supert[i].name)>=0 && (supert.length-soundlists.length>1))
            continue;
       if (superd.d[i].error) {
           words2 = superwords[i];
           o = u.shuffle(u.select(words2.length, words2.length));
           int got=0;
           kloop:for (kk = 0; kk < words2.length && words.length<superwordtot && got<2; ++kk) {
             k = o[kk];
             String vv = words2[k].v();
             for (m = 0; m < words.length; ++m) {
               if (vv.equalsIgnoreCase(words[m].v()))
                 continue kloop;
             }
             wordsource[words.length] = i;
             ww = new word(words2[k]);
             ww.homophone=true;
             ww.paired=false;
             ww.companions=0;
             words = u.addWords(words, ww);
             wordval = u.addString(wordval,vv );
             ++got;
           }
       }
     }
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(supert[0].phonicsw) {
      if(supert.length>0&&supert[0].phonicsw) {                     // start rb 25/1/08
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        for(i=0; i<superd.d.length;++i) {
          if (superwords[i] == null)  superwords[i] = supert[i].getAllWordsBoth();
          if(u.findString(soundlists, supert[i].name)>=0 && (supert.length-soundlists.length>1))
              continue;
          if(superwords[i].length>0 && !superwords[i][0].phonicsw) {
              for(j=0; j<superd.d.length;++j) {    // one list failed - turn off phonics requests
                   supert[j].phonics = supert[j].phonicsw = false;
              }
              phonics = phonicsw = false;
              break;
          }
        }
     }                                              // end rb 15/1/08
     int lastlen=-1;
     boolean repeating = false;
     for(i=0;words.length<superwordtot && i<superd.d.length;++i) {
       if(superwords[i] == null) superwords[i] = supert[i].getAllWordsBoth();
       if(u.findString(soundlists, supert[i].name)>=0 && (supert.length-soundlists.length>1))
           continue;
       if ((!superd.d[i].error || repeating) && superd.d[i].used < max) {
           words2 = superwords[i];
           o = u.shuffle(u.select(words2.length, words2.length));
           kloop:for (kk = 0; kk < words2.length; ++kk) {
             k = o[kk];
             if(supert[0].phonicsw && !words2[k].phonicsw ) {
                  continue kloop;
             }
             String vv = words2[k].v();
             for (m = 0; m < words.length; ++m) {
               if (vv.equalsIgnoreCase(wordval[m]))
                 continue kloop;
             }
             if(supert[0].phonicsw && !words2[k].phonicsw ) {
                continue kloop;
             }
             ww = new word(words2[k]);
             ww.homophone=true;
             ww.paired=false;
             ww.companions=0;
             wordsource[words.length] = i;
             wordval = u.addString(wordval,vv);
             words = u.addWords(words, ww);
             break;
           }
       }
       if(i == superd.d.length-1 && words.length<superwordtot && words.length>lastlen) {
          ++max;
          lastlen = words.length;
          i=-1;
          repeating=true;
       }
     }
     initfinished = true;
      return u.shuffle(words);
  }
  //----------------------------------------------------------
  public word[] getsupertestwords(int wantmax) {
    int max = maxused();
    int i, j, k, kk, m, o[], lastlen=-1;
    word words[] = new word[0], words2[], ww;
    wordval = new String[0];
    wordsource = new int[wantmax];
    boolean already[] = new boolean[supert.length];
    for(i=0;words.length<wantmax && i<superd.d.length;++i) {
       if(superwords[i] == null) superwords[i] = supert[i].getAllWordsBoth();
       if ( superd.d[i].used < max && !already[i]) {
           already[i] = true;
           words2 = superwords[i];
           o = u.shuffle(u.select(words2.length, words2.length));
           kloop:for (kk = 0; kk < words2.length && words.length<wantmax; ++kk) {
             k = o[kk];
             String vv = words2[k].v();
             for (m = 0; m < words.length; ++m) {
               if (vv.equalsIgnoreCase(words[m].v()))
                 continue kloop;
             }
             wordsource[words.length] = i;
             ww = new word(words2[k]);
             ww.homophone = true;
             ww.paired = false;
             ww.companions = 0;
             words = u.addWords(words, ww);
             wordval = u.addString(wordval, vv);
             already[i] = true;
             break;
           }
       }
       if(i == superd.d.length-1 && words.length<wantmax && words.length>lastlen) {
          ++max;
          i=-1;
          lastlen = words.length;
       }
     }
      return u.shuffle(words);
  }
  //--------------------------------------------------------------
  topic topicforspecial(String game) {
    int ii,i;
    int o[] = u.shuffle(u.select(wordval.length,wordval.length));

    for(ii=0;ii<o.length;++ii) {
      i=wordsource[o[ii]];
      if(supert[i].testWord(game)) {
         specialsource = i;
         return supert[i];
      }
    }
    o = u.shuffle(u.select(supert.length,supert.length));
    for(ii=0;ii<o.length;++ii) {
      i=o[ii];
      if(supert[i].testWord(game)) {
         specialsource = i;
         return supert[i];
      }
    }
    return this;
  }
  //--------------------------------------------------------------
  void superendgame(String game,String[] errors, boolean incomplete,int errortot) {
    int i, j;
//startPR2008-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkStartFrame.mainFrame.superSetup(game);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    sharkStartFrame.gameflag ff = sharkStartFrame.gameflag.get(game);
    boolean spelling = ff.spelling;
    boolean recognition = ff.recognition;
    boolean special = ff.special;
    boolean alphabet =  ff.alphabet;
    boolean wasusetopic = (superd.usetopic >= 0);
    superchanged = true;
    if (superd.usetopic >= 0
//          && (spelling && errors.length <= 2 && !incomplete || sharkStartFrame.simplesuper)) {
          && (spelling && errors.length <= 2 && !incomplete)) {
        superd.d[superd.usetopic].error = false;
        ++superd.d[superd.usetopic].used;
        superd.usetopic = -1;
        superd.last3games = deflast3games;
//        sharkStartFrame.mainFrame.sametopic = true;
        sharkStartFrame.mainFrame.setupGametree(true);
        sharkStartFrame.mainFrame.addteachingnotes(false);
    }
    else if(spelling && !incomplete && superd.last3games.indexOf(SPELLING)<0) {
      superd.last3games = SPELLING
                       + superd.last3games.substring(0,superd.last3games.length()-1);
      if(sharkStartFrame.spellingonly) {
 //       sharkStartFrame.mainFrame.sametopic = true;
        sharkStartFrame.mainFrame.setupGametree(true);
      }
    }
    else if(spelling && !incomplete
       || !spelling ) {
      superd.last3games = (spelling?SPELLING:(recognition?RECOGNITION:(alphabet?ALPHABET:c0)))
                       + superd.last3games.substring(0,superd.last3games.length()-1);
    }
//    if(sharkStartFrame.spellingonly && sharkStartFrame.simplesuper) {
    if(sharkStartFrame.spellingonly) {   
 //     sharkStartFrame.mainFrame.sametopic = true;
      sharkStartFrame.mainFrame.setupGametree(true); // notsure
    }
    if (!wasusetopic ) {
      if(special) {
//         if (errortot>2  && !sharkStartFrame.simplesuper) {
        if (errortot>2) {
           superd.usetopic = specialsource;
           superd.last3games = deflast3games;
//           sharkStartFrame.mainFrame.sametopic = true;
           sharkStartFrame.mainFrame.setupGametree(true);
           sharkStartFrame.mainFrame.addteachingnotes(false);
         }
      }
      else if(!alphabet) {   // not alphabet & not special
        for (i = 0; i < wordval.length; ++i) {
          if (u.findString(errors, wordval[i]) >= 0) {
//            if (superd.d[wordsource[i]].error && !sharkStartFrame.simplesuper) {
            if (superd.d[wordsource[i]].error) {
              superd.usetopic = wordsource[i];
              superd.last3games = deflast3games;
//              sharkStartFrame.mainFrame.sametopic = true;
              sharkStartFrame.mainFrame.setupGametree(true);
              sharkStartFrame.mainFrame.addteachingnotes(false);
            }
            else
              superd.d[wordsource[i]].error = true;
          }
          else  {
            if(!incomplete) superd.d[wordsource[i]].error = false;
//            if((spelling || sharkStartFrame.simplesuper)  && !incomplete) {
            if((spelling)  && !incomplete) {
              ++superd.d[wordsource[i]].used;
            }
          }
        }
      }
    }
   }
  //------------------------------------------------------------
       // get all words, extended or normal, including references
       // This supplies the list for 'challenge'
  public word[] getAllWordsBoth() {
     short i;
     byte type;
     word words[] = new word[0];
     wantrefs = true;
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              type = getTypePos(c.get());
              if(type < 0 || type == SELITEMS || type == SELGROUPS) {
                  words = u.addWords(words,getAllWords(c,true));
              }
        }
     }
     return u.stripdups2(words);
  }
 //----------------------------------------------------------
     // get all good,real words  (not those in references or sentences)
     // Only used by findword, so no need to flag homophones etc
  public word[] getAllWordsNoSent() {
       return getAllWordsNoSent(root);
  }
  //----------------------------------------------------------
  public word[] getAllWordsNoSent(jnode node) {
     String s = node.get();
     word words[] = new word[0],ww;
     short i,j,selcount;
     int t = getTypePos(s);
     if(!node.isLeaf())  {
//        if(u.findString(sentencegames,s)  < 0) {
        if(t != GAMES) {
           for(Enumeration e = node.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              words = u.addWords(words,getAllWordsNoSent(c));
           }
        }
     }
     else if(s.length()>0 && t != HOMOPHONES && t != FL && t != NONSENSE && t != INORDER  && t != FORCEKEYPAD && t != PHRASES && t != BLENDS
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             && t != MARKGAMESHEADINGS
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-08-06^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//             && t != JUSTPHONICS && t != STARTPHONICS && t != STARTNONPHONICS ) {
               && t != NOTMIXEDWORDS && t != JUSTPHONICS && t != STARTPHONICS && t != STARTNONPHONICS
               && t != PHONICDISTRACT && t != MARKGAMES && t != MARKGAMES2 && t != MARKGAMESCODE
               && t != SELITEMS && t != SELECTDISTRACTORS
               && t != GAMES && t != NOTGAMES && t != REVISE && t != NOTPHONICS) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        if(!s.substring(0,1).equals(topicTree.ISTOPIC)
               && s.charAt(0) != '\\'
               && !s.substring(0,1).equals(topicTree.ISPATH)
               && s.indexOf('*') < 0  && s.charAt(0) != '(') {
            ww = new word(s,databaseName);
            return new word[] {ww};
        }
     }
     return words;
  }
  //---------------------------------------------------------------
  static void buildsentgames() {
      String lists[] = sharkStartFrame.searchListGame(sharkStartFrame.currStudent);
      String games[],values[];
      int i,j,k;
      for(i=0;i<lists.length;++i) {
         games = db.list(lists[i],db.GAME);
         for(j=0;j<games.length;++j) {
            if((values = (String[])db.find(lists[i],games[j],db.GAME)) != null) {
               for(k=0;k<values.length;++k) {
                  if(values[k].startsWith("sentencetype=")) {
                     int ty = Integer.parseInt(values[k].substring(values[k].indexOf('=')+1));
                     if(sentencegames == null || sentencegames.length != 3) {
                        sentencegames = new String[3];
                        sentencegames2 = new String[3];
                     }
                     sentencegames[ty] = types[0]+games[j];
                     sentencegames2[ty] = games[j];
                     break;
                  }
               }
            }
         }
      }
  }
 //----------------------------------------------------------
     // get all good,real words and sentences (not those in references)
     // also includes headings treated as sentences
     // Only used by recordWords, so no need to flag homophones etc
     // 'bad' flag is used to show sentences
  public word[] getAllWordsAndSent() {
     return getAllWordsAndSent(root,false, false);
  }
  
  public word[] getAllWordsAndSent( boolean justHeliAndPattern) {
     return getAllWordsAndSent(root,false,justHeliAndPattern);
  }
  //----------------------------------------------------------
  public word[] getAllWordsAndSent(jnode node,boolean allsentences, boolean justHeliAndPattern) {
     String s = node.get();
     word words[] = new word[0],ww;
     short i,j,selcount;
     int m,n;
     int t = getTypePos(s);
     
     if(s.startsWith(MYSQLUpload.GTX_GAMES) && u2_base.findString(MYSQLUpload.GAMESBLOCKSTOIGNORE,s.substring(MYSQLUpload.GTX_GAMES.length()), true) >= 0){
         return words;
     }
     if(s.startsWith(sentence.TEST_PREFIX)){
         return words;
     }    
     if(t == HEADING && s.length() > s.indexOf(':') + 1 ) {
         words=u.addWords(words, ww = new word(s.substring(s.indexOf(':')),databaseName));
         ww.bad=true;
     }

     if(u2_base.findString(sentencegames,s, true) >= 0 && !justHeliAndPattern) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           words = u.addWords(words,getAllWordsAndSent(c,true, justHeliAndPattern));
        }
        if(u2_base.findString(new String[]{sentencegames[2]},s, true) >= 0) {       // need simple sentences
           words = u.addWords(words, wordlist.getphrases(getSpecials(new String[]{topic.sentencegames2[2]})));
        }
//startPR2008-09-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        return words;  // CHECK
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     }
     if(!node.isLeaf())  {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
          words = u.addWords(words,getAllWordsAndSent(c,allsentences, justHeliAndPattern));
       }
       return words;
     }
     else if(s.length()>0 && t == -1 && !justHeliAndPattern) {
        if(!s.substring(0,1).equals(topicTree.ISTOPIC)
               && !s.substring(0,1).equals(topicTree.ISPATH)
 //              && s.indexOf('*') < 0  && s.charAt(0) != '(') {
                && s.indexOf('*') < 0  && (s.charAt(0) != '(' || s.indexOf(" ") > 0)) {
            if(!allsentences && (i=(short)s.indexOf('+'))>1 && i < s.length()-1) {
               if(s.charAt(i+1) == '+')
                   s = s.substring(0,i)+s.substring(i+2);
               else if((m=s.indexOf('='))>0) {
                   if ((n=s.indexOf('(',m+1)) > 0)
                     s = s.substring(m+1,n);
                   else s = s.substring(m+1);
               }
               else s = u.addsuffix(s.substring(0, i), s.substring(i + 1));
            }
            else if(!allsentences && s.indexOf('+')<0  && (j=(short)s.indexOf('=')) > 0) {  // phonics
              word ret[] = new word[0];
              if(s.indexOf(u.phonicsplit)>0) {
                ww = new word(s.substring(0, j), databaseName);
                if(databaseName.equals(publicname) && u.scanfor(ww.vsay(),u.vowelsy)<0 && ww.value.indexOf('!')<0 ) {  // check for consonent blend
                    ww = new word(s.substring(0, j) + '~', databaseName);
                    ret = new word[]{ww};
                }
                else {
                     if(!spokenWord.query(ww.vsay()+'~',databaseName.equals(publicname)?null:databaseName)) {
                      ww = new word(s.substring(0, j), databaseName);
                      ret = new word[] {ww};
                    }
                }
                if(s.indexOf('/',j) >= 0) {    // split in phoneme mode
                   String ss = s.substring(j+1);
                   ww = new word(s,databaseName);
                   for(m=0;m<ww.phonicsplitlist.length;++m) {
                      ret = u.addWords(ret, new word(ww.phpart(ww.phonicsplitlist[m])+"~~",databaseName));
                   }
                }
              }
              String ss[] = new word(s, databaseName).phonics();
              for(j=0;j<ss.length;++j) {
                if(ss[j].length()>0 && !ss[j].equals("-"))
                 ret = u.addWords(ret,new word(ss[j]+'~',databaseName));
              }
              return ret;
            }
            if((j=(short)s.indexOf("{")) > 0 && s.indexOf("}") > 0)
                s = s.substring(0,j);
            if((j=(short)s.indexOf("@@")) > 0)
                s = s.substring(0,j);
            ww = new word(s,databaseName);
            if(!allsentences && phrases) {
               word ret[] = new word[] {ww};
               String ss[] = u.splitPhrase(s);
               for(j=0;j<ss.length;++j) {
                ret = u.addWords(ret,new word(ss[j],databaseName));
              }
              return ret;
            }
            ww.bad = allsentences;
            return new word[] {ww};
        }
     }
     return words;
  }
  
  public String[] getTestSentences(String prefix) {
     jnode jj[] = this.root.getChildren();
     String ss[] = null;
     for(int i = 0; i < jj.length; i++){
         String s = jj[i].get();
         if(s.startsWith(prefix)){
             if(ss==null)ss= new String[]{};
             ss = u.addString(ss, s.substring(prefix.length()));
         }
     }
     return ss;
  }  
//----------------------------------------------------------
//----------------------------------------------------------
    // all real words under top nodes of type select words: or select groups:
    // (this does include referenced groups)
    // (for wordlist to show full extended list)
    // This also supplies the 'extra' list used in 'buckets' to
    // provide words in addition to those listed specifically for
    // the buckets
  public word[] getAllWords(boolean extended) {  
      return getAllWords(extended, false);
  }  
  
 // public word[] getAllWords(boolean extended) {
public word[] getAllWords(boolean extended, boolean excludeteachingnotes) {
     short i;
     byte type;
     wantrefs = true;   // all refs within this topic
     if(!canextend()) extended = false;
     word words[] = new word[0];
     if(!root.isLeaf()) {
        if(extended) {      // include all except normal words directly under root
           for(Enumeration e = root.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              type = getTypePos(c.get());
              if(type == HOMOPHONES && c.isLeaf())
                     allhomos = true;
              else if(type == FL)
                  fl = true;
              else if(type == NONSENSE)
                  nonsense = true;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == MARKGAMESHEADINGS){
                String s1 = c.get();
                markgheading = s1.substring(s1.indexOf(':')+1);
                if(markgheading.trim().equals(""))markgheading=null;
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == ISUNITREVISION)
                  unitrevision = true;
              else if(type == REVISE)
                  revision = true;
              else if(type == PICTUREPREFERENCE)
                  picturePrefence = c.get().substring(c.get().indexOf(':')+1);
              else if(type == APPRIORITY1)
                  APPriority1 = true;              
              else if(type == APPRIORITY2)
                  APPriority2 = true;    
              else if(type == APNOTINTEST)
                  apnotintest = true;
              else if(type == APNOTINUNITORTEST)
                  apnotinunitortest = true;
              else if(type == NOTPHONICS)
                  notphonics = true; 
              else if(type == INORDER) inorder = true;
              else if(type == FORCEKEYPAD) forcekeypad = true;
              else if(type == PHRASES) phrases = true;
              else if(type == BLENDS) blended = true;
              else if(type == JUSTPHONICS) justphonics = true;
              else if(type == NOTMIXEDWORDS) notmixedwords = true;
              else if(type == STARTPHONICS) startphonics = true;
              else if(type == STARTNONPHONICS) startnonphonics = true;
             if(type == SELITEMS || type == SELGROUPS) {
                  words = u.addWords(words,getAllWords(c,extended));
              }
           }
        }
        else {        // include all except normal words
           for(Enumeration e = root.children();e.hasMoreElements();) {
              jnode c = (jnode)e.nextElement();
              type = getTypePos(c.get());
              if(type == HOMOPHONES && c.isLeaf())
                allhomos = true;
              else if(type == FL)
                  fl = true;
              else if(type == NONSENSE)
                  nonsense = true;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == MARKGAMESHEADINGS){
                String s1 = c.get();
                markgheading = s1.substring(s1.indexOf(':')+1);
                if(markgheading.trim().equals(""))markgheading=null;
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              else if(type == ISUNITREVISION)
                  unitrevision = true;
              else if(type == REVISE)
                  revision = true;
              else if(type == NOTPHONICS)
                  notphonics = true; 
              else if(type == INORDER) inorder = true;
              else if(type == FORCEKEYPAD) forcekeypad = true;
              else if(type == PHRASES) phrases = true;
              else if(type == BLENDS) blended = true;
              else if(type == JUSTPHONICS) justphonics = true;
              else if(type == NOTMIXEDWORDS) notmixedwords = true;
              else if(type == STARTPHONICS) startphonics = true;
              else if(type == STARTNONPHONICS) startnonphonics = true;
//              if(type < 0) {
              if(type < 0 && (!excludeteachingnotes || !c.get().startsWith("\\"))) {
                 words = u.addWords(words,getAllWords(c,extended));
              }
           }
        }
     }
     return u.stripdups2(words);
  }
  //----------------------------------------------------------
  public word[] getBadWords() {
     String s;
     word words[] = new word[0];
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
            jnode c = (jnode)e.nextElement();
            if((s=c.get()).charAt(0) == '(') {
                words = u.addWords(words, new word(s,databaseName));
            }
        }
     }
     return words;
  }
  //---------------------------------------------------
      // get normal and extended words, including references
  public word[] getAllWords(jnode node,boolean extended) {
     word words[] = new word[0];
     String s = node.get();
     if(s.length()< 1)         return words;                        // rb 4/2/08
     short i,j,k,selcount;
     if(s.substring(0,1).equals(topicTree.ISPATH)
          || s.substring(0,1).equals(topicTree.ISTOPIC)) {
              // add extended list from referenced topic
        return getWords(null,node,new topic[] {this});
     }
     byte type = getTypePos(s);
     if(type == GAMES) return words;
     if(type == COURSES && !incourse(s)) {
         return words;
     }
     if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           words = u.addWords(words,getAllWords(c,extended));
        }
     }
     else {
        if(type < 0 && s.indexOf('*') < 0 && s.charAt(0) != '(')
            return new word[]{new word(s,databaseName)};
     }
     switch(type) {
        case PAIR:
           if(words.length == 2)  {
              words[0].paired = true;
              words[0].companions = 1;
              words[1].companions = 1;
              words[1].paired = true;
            }
            break;
         case HOMOPHONES:
            if(node.isLeaf() && node.getLevel() == 1) {
               allhomos = true;
            }
            else for(i=0;i<words.length;++i) words[i].homophone=true;
            break;
          case FL:
              fl = true; break;
          case NONSENSE:
              nonsense = true; break;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case MARKGAMESHEADINGS:
            markgheading = s.substring(s.indexOf(':')+1);
            if(markgheading.trim().equals(""))markgheading=null;
            break;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case ISUNITREVISION:
              unitrevision = true; break;      
          case REVISE:
              revision = true; break;
          case PICTUREPREFERENCE:              
              picturePrefence = s.substring(s.indexOf(':')+1); break;
          case APPRIORITY1:
              APPriority1 = true; break;
          case APPRIORITY2:
              APPriority2 = true; break; 
          case APNOTINTEST:
              apnotintest = true; break;
          case APNOTINUNITORTEST:
              apnotinunitortest = true; break;
          case NOTPHONICS:
              notphonics = true; break;    
          case INORDER: inorder = true; break;
          case FORCEKEYPAD: forcekeypad = true; break;
          case PHRASES: phrases = true; break;
          case BLENDS: blended = true;break;
          case JUSTPHONICS: justphonics = true; break;
          case NOTMIXEDWORDS: notmixedwords = true; break;
          case STARTPHONICS: startphonics = true; break;
          case STARTNONPHONICS: startnonphonics = true; break;
          case MARKGAMES:
                  markgformula = getformula(s);
                  if(markgformula==null){
                      markgames = splitlist(s);
                  }
            break;
          case MARKGAMES2:
                  markgformula2 = getformula(s);
                  if(markgformula2==null){
                      markgames2 = splitlist(s);
                  }
            break;
          case MARKGAMESCODE:
//            markgamescode = splitlist(s);
            int p = s.indexOf(":");
            if(p >= 0){
                markgamescode = s.substring(p+1);
            }     
            break;
          case GAMEOPTIONS:
             gameoptionlist = u.addStringSort(gameoptionlist, s.substring(s.indexOf(':')+1));
             break;
           case PHONICDISTRACT:
             phonicdistractors = u.addStringSort(phonicdistractors,s.substring(s.indexOf(':')+1));
             break;
     }
     if(allhomos) for(i=0;i<words.length;++i) words[i].homophone=true;
     return words;
  }
  //---------------------------------------------------------
  public word[] getWords(String[] gamelist,jnode node,
                                           topic[] alreadyHad) {
      
      return getWords(gamelist,node, alreadyHad, false, null);
  }
  
  public word[] getWords(String[] gamelist,jnode node,
                                           topic[] alreadyHad, boolean noselect, getGroups groupOb) {
     word words[] = new word[0];
     String s = node.get(),s2;
     short i,j,k,selcount;
     byte type;
     if(s.length() == 0) return words;
     if(s.substring(0,1).equals(topicTree.ISPATH)) {
        if(!wantrefs) return words;
        topic t[] = topicTree.getTopics(s);
        for(i=0;i<t.length;++i) {
          if(t[i] != null && !t[i] .topicinlist(alreadyHad)) {
              alreadyHad = u.addTopic(alreadyHad,t[i]);
              words = u.addWords(words,t[i].referencedList(gamelist,alreadyHad));
          }
        }
        return words;
      }
     else  if(s.substring(0,1).equals(topicTree.ISTOPIC)) {
        if(!wantrefs) return words;
        int pos = s.substring(1).indexOf(topicTree.SEPARATOR)+1;
        topic t = null;
        if(pos>0)  t = new topic(s.substring(1,pos),s.substring(pos+1),null,null);
        if(t != null  && !t.topicinlist(alreadyHad)) {
           alreadyHad = u.addTopic(alreadyHad,t);
           words = u.addWords(words,t.referencedList(gamelist,alreadyHad));
        }
        return words;
     }
     type = getTypePos(s);
     if(type ==  HEADING && gamelist == null) return words;
     if(gamelist != null
         && (type == GAMES && !gameinlist(s,gamelist)
             || type == NOTGAMES && allgamesinlist(s,gamelist))) {
         return words;
     }
     if(type == COURSES && !incourse(s)) {
         return words;
     }
     if(referencedlist && type == PAIR) return words;
     if(!node.isLeaf()) {
        if(!referencedlist && type == SELGROUPS) {
           int groupSelectNo = u.getint(s.substring(s.indexOf(":")+1));
           int chosen[] = null;
       //    int ct = node.getChildCount();
            int ct = stripBlankNodes(node.getChildren()).length;
           if(groupOb!=null){
               chosen = groupOb.getChosen(ct, groupSelectNo, node);
           }
           else chosen = u.select((short)ct, groupSelectNo);
           for(i=0;chosen!=null && i<chosen.length;++i) {
              words = u.addWords(words,getWords(gamelist,(jnode)node.getChildAt(chosen[i]),alreadyHad, noselect, groupOb));
           }
        }
        else {
           for(Enumeration e = node.children();e.hasMoreElements();) {
              words = u.addWords(words,getWords(gamelist,(jnode)e.nextElement(),alreadyHad, noselect, groupOb));
           }
        }
     }
     else {
        if(node != root && type < 0 && (s.length()==0 || s.charAt(0) !='\\'))
           return new word[]{new word(s,databaseName)};
        else return words;
     }
     switch(type) {
        case PAIR:
           if(words.length == 2)  {
              words[0].paired = true;
              words[0].companions = 1;
              words[1].companions = 1;
              words[1].paired = true;
            }
            break;
         case ALLORNONE:
            if(words.length > 1 && words.length < 20) {
               for(i=0;i<words.length;++i)
                  words[i].companions = (byte)(words.length-1);
            }
            break;
         case SELITEMS:
            if(referencedlist) break;
            selcount = (short)u.getint(s.substring(s.indexOf(":")+1));
            words = u.stripdups(words);
            if(selcount < words.length  && !noselect)  {
               words = selitems(words,selcount,inspecialgame(node));
            }
            break;
         case HEADING:
            for(i=0;i<words.length;++i) words[i].heading=s;
            break;
         case HOMOPHONES:
            if(node.isLeaf() && node.getLevel() == 1) {
               allhomos = true;
            }
            else for(i=0;i<words.length;++i) words[i].homophone=true;
            break;
          case FL:
              fl = true; break;
          case NONSENSE:
              nonsense = true; break;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case MARKGAMESHEADINGS:
            markgheading = s.substring(s.indexOf(':')+1);
            if(markgheading.trim().equals(""))markgheading=null;
            break;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case ISUNITREVISION:
              unitrevision = true; break;  
          case REVISE:
              revision = true; break;
          case PICTUREPREFERENCE:              
              picturePrefence = s.substring(s.indexOf(':')+1); break;
          case APPRIORITY1:
              APPriority1 = true; break;
          case APPRIORITY2:
              APPriority2 = true; break; 
          case APNOTINTEST:
              apnotintest = true; break;
          case APNOTINUNITORTEST:
              apnotinunitortest = true; break;
          case NOTPHONICS:
              notphonics = true; break; 
          case INORDER: inorder = true; break;
          case FORCEKEYPAD: forcekeypad = true;break;
          case PHRASES: phrases = true; break;
          case BLENDS: blended = true;break;
          case JUSTPHONICS: justphonics = true; break;
          case NOTMIXEDWORDS: notmixedwords = true; break;
          case STARTPHONICS: startphonics = true; break;
          case STARTNONPHONICS: startnonphonics = true; break;
          case MARKGAMES:
                  markgformula = getformula(s);
                  if(markgformula==null){
                      markgames = splitlist(s);
                  }
            break;
          case MARKGAMES2:
                  markgformula2 = getformula(s);
                  if(markgformula2==null){
                      markgames2 = splitlist(s);
                  }
             break;
           case MARKGAMESCODE:
//              markgamescode = splitlist(s);
                int p = s.indexOf(":");
                if(p >= 0){
                    markgamescode = s.substring(p+1);
                }     
              break;
           case PHONICDISTRACT:
             phonicdistractors = u.addStringSort(phonicdistractors,s.substring(s.indexOf(':')+1));
             break;
     }
     if(allhomos) for(i=0;i<words.length;++i) words[i].homophone=true;
     return words;
  }
  //-------------------------------------------------------------
  word[] referencedList(String[] gamelist, topic[] alreadyHad) {
              byte type;
              short j;
              String s2;
              word words[] = new word[0];
              referencedlist = true;
              if(canextend() && wantextend) {  // use extended list if possible and wanted
                 if(!root.isLeaf()) {
                    for(Enumeration e = root.children(); e.hasMoreElements();) {
                       jnode c = (jnode)e.nextElement();
                       s2 = c.get();
                       type = getTypePos(s2);
                       if(type==PAIR) continue;
                       if(type == SELITEMS || type == SELGROUPS)
                         words = u.addWords(words,onlywords(getWords(gamelist,c, alreadyHad)));
                    }
                 }
              }
              else {       // use normal words
                 if(!root.isLeaf()) {
                    for(Enumeration e = root.children();e.hasMoreElements();) {
                       jnode c = (jnode)e.nextElement();
                       s2 = c.get();
                       type = getTypePos(s2);
                       if(type < 0) {
                          words = u.addWords(words,onlywords(getWords(gamelist,c, alreadyHad)));
                       }
                    }
                }
            }
            return words;
  }
  //-----------------------------------------------------------------
  word[] selitems(word words[],short selcount,boolean inspecialgame) {
      short groups = 0,gottot=0, pos,i,j,k;
      if(!inspecialgame && phonicsw && wordlist.usephonics) {
         for(i=0;i<words.length;++i) {  // strip non-phonics in phonics list before selecting
            if(!words[i].phonicsw) {
                words = u.removeword(words,i); --i;
            }
         }
      }
      boolean wantword[]=new boolean[words.length];
      for(i=0;i<words.length;++i) {
         ++groups;
         i += words[i].companions;
      }
      short chosen[] = u.shuffle(u.select(groups, groups));
      for(i=0;i<groups && gottot<selcount;++i) {
         for(j=0,pos=0;j<chosen[i];++j) {
             pos += 1 + words[pos].companions;
         }
         if(gottot + (k = (short)(1 + words[pos].companions)) <= selcount) {
            for(j=0;j<k;++j) wantword[pos+j] = true;
            gottot += k;
         }
      }
      word newwords[] = new word[gottot];
      for(i=j=0;i<words.length;++i) {
          if(wantword[i]) newwords[j++] = words[i];
      }
      return newwords;
  }
  //---------------------------------------------------------
  static word[] onlywords(word[] in)  {
     word out[] = new word[0];
     for(short i = 0; i < in.length;++i) {
        if(!in[i].bad && in[i].value.indexOf('*') < 0)
             out = u.addWords(out,in[i]);
     }
     return out;
  }
  //---------------------------------------------------------
  public boolean canextend() {
     short i;
     byte type;
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
               type = getTypePos(((jnode)e.nextElement()).get());
               if(type == SELITEMS || type == SELGROUPS){
                   if(wordlist.usephonics && !extendedphonics())
                       return false;
                   else
                       return true;
               }
        }
     }
     return false;
  }
  //---------------------------------------------------------
  public boolean extendedphonics() {
     short i,count=0;
     byte type;
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
               type = getTypePos(((jnode)e.nextElement()).get());
               if(type == SELITEMS || type == SELGROUPS) {
                 word ww[] = getWords(null,true);
                 if(ww == null || ww.length==0) return false;
                 for(i=0;i<ww.length;++i) 
                     if(ww[i].phonics && ++count>=3)
                         return true;
                 return false;
               }
        }
     }
     return false;
  }
  //---------------------------------------------------------
  public boolean specialgame(String g) {
     short i;
     byte type;
     String s;
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
            type = getTypePos(s = ((jnode)e.nextElement()).get());
            if(type == GAMES && gameinlist(s,new String[]{g})) 
                return true;
        }
     }
     return false;
  }
  //---------------------------------------------------------
  boolean inspecialgame(jnode node) {
     for(node = (jnode)node.getParent(); node !=null && node != root; node = (jnode)node.getParent())  {
       if(getTypePos(node.get()) == GAMES) return true;
     }
     return false;
  }
  //---------------------------------------------------------
  public word[] getSpecials(String[] gamelist) {
     short i,j;
     byte type;
     String s;
     word words[] = new word[0];
     topic alreadyHad[] = new topic[]{this};
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           type = getTypePos(s=c.get());

           if(type == GAMES && gameinlist(s,gamelist)) {
               String s2;
                if(c.getChildCount() == 1 && getTypePos((s2=((jnode)c.getChildAt(0)).get())) == SELECTDISTRACTORS) {
                     sentenceDistractorNo = (short)u.getint(s2.substring(s2.indexOf(":")+1));
                }
               return getWords(gamelist,c,alreadyHad);
           }
        }
     }
      return words;
  }
  //---------------------------------------------------------
 


       // words under headings
       // This supplies the list for 'buckets' game
       // It returns a list for each bucket

       // This first routine is called if we want whole extended list
       //    to supply extra words
       // The second can be called directly to use a different set
       //   of extra words
  public word[][] getHeadedWords(String[] gamelist) {
     return getHeadedWords(gamelist, getAllWords(true), false);
  }
  
  public word[][] getHeadedWords(String[] gamelist, boolean noselect) {
     return getHeadedWords(gamelist, getAllWords(true), noselect);
  }
  
  public word[][] getHeadedWords(String[] gamelist, boolean noselect, boolean excludeTeachingNotes, getGroups groupOb) {
     return getHeadedWords(gamelist, getAllWords(true, excludeTeachingNotes), noselect, groupOb);
  }  
  
  //---------------------------------------------------------
  public word[][] getHeadedWords(String[] gamelist, word extrawords[]) {
      return getHeadedWords(gamelist, extrawords, false);
  }  
  
  public word[][] getHeadedWords(String[] gamelist, word extrawords[], boolean noselect) {  
      return getHeadedWords(gamelist, extrawords, noselect, null);
  }
  
  
  
  public word[][] getHeadedWords(String[] gamelist, word extrawords[], boolean noselect, getGroups groupOb) {
     short i,j;
     byte type;
     String s;

     headings = new String[0];
     wantrefs = true;
     word words[] = new word[0];
     topic alreadyHad[] = new topic[]{this};
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           type = getTypePos(s=c.get());
           if(type == GAMES && gameinlist(s,gamelist)) {
               words = getHeadedWords(gamelist,c,alreadyHad,extrawords,getpatterns(c), noselect, groupOb);
               break;
           }
        }
     }
     word ret[][] =  new word[headings.length][];
     for(i=0;i<ret.length;++i) {
         ret[i] = new word[0];
         for(j=0;j<words.length;++j) {
            if(words[j].heading != null && words[j].heading.equals(headings[i]))
               ret[i] = u.addWords(ret[i],words[j]);
         }
         ret[i] = u.stripdups2(ret[i]);
     }
     return ret;
  }
  
  public void clearHeadingLists() {
    lastHeading = null;
    headingSelects = new ArrayList();
   }
  
  //---------------------------------------------------------
  public word[] getHeadedWords(String[] gamelist,jnode node,topic[] alreadyHad, word extrawords[], String[] patterns, boolean noselect, getGroups groupOb) {
     
     word words[] = new word[0];
     String s = node.get();
     short i,j,k,selcount;
     if(!shark.production && node.get().trim().equals(""))return new word[]{};
     if(s.substring(0,1).equals(topicTree.ISPATH)) {
        topic t[] = topicTree.getTopics(s);
        for(i=0;i<t.length;++i) {
           if(t[i] != null && !t[i].topicinlist(alreadyHad)) {
              alreadyHad = u.addTopic(alreadyHad,t[i]);
//              words = u.addWords(words,onlywords(t[i].getWords(gamelist,t[i].root,    // removed rb 21/2/06
//                                           alreadyHad)));                              // removed rb 21/2/06
              t[i].wantextend = true;                                          // added rb 21/2/06
              words = u.addWords(words,onlywords(t[i].referencedList(null,alreadyHad)));          // added rb 21/2/06
           }
        }
        return words;
     }
     else  if(s.substring(0,1).equals(topicTree.ISTOPIC)) {
        int pos = s.substring(1).indexOf(topicTree.SEPARATOR)+1;
        topic t = null;
        if(pos>0)  t = new topic(s.substring(1,pos),s.substring(pos+1),null,null);
        if(t != null  && !t.topicinlist(alreadyHad)) {
           alreadyHad = u.addTopic(alreadyHad,t);
//           return onlywords(t.getWords(gamelist,t.root,alreadyHad));   // removed rb 21/2/06
           t.wantextend = true;                                          // added rb 21/2/06
           return onlywords(t.referencedList(null,alreadyHad));          // added rb 21/2/06
        }
        return words;
     }
     byte type = getTypePos(s);
     if(gamelist != null
         && (type == GAMES && !gameinlist(s,gamelist)
             || type == NOTGAMES && allgamesinlist(s,gamelist))) {
         return words;
     }
     if(type == COURSES && !incourse(s)) {
         return words;
     }
     if(type == HEADING) {
            if (u.findString(headings,s) < 0){
                headings = u.addString(headings,s);
                lastHeading = s;
            }
     }
     if(!node.isLeaf()) {
        int ct = stripBlankNodes(node.getChildren()).length;
        if(type == SELGROUPS) {
           int chosen[] = null;
           short groupSelectNo = (short)u.getint(s.substring(s.indexOf(":")+1));
           if(groupOb!=null){
               chosen = groupOb.getChosen(ct, groupSelectNo, node);
           }
           else chosen = u.select(ct, groupSelectNo);         
           if(chosen!=null){
            String newpat[] = null;
            for(i=0;i<chosen.length;++i) {
               newpat = u.addString(newpat,getpatterns((jnode)node.getChildAt(chosen[i])));
            }
            if(newpat == null || newpat.length == 0) newpat = patterns;
            for(i=0;i<chosen.length;++i) {

                word wws[] = getHeadedWords(gamelist,(jnode)node.getChildAt(chosen[i]),alreadyHad,extrawords,newpat,noselect, groupOb);
               words = u.addWords(words,wws);
            }
           }
           return words;
        }
        else {
           boolean ispat = false, isnotpat = false;
           word newwords[] = new word[0];
           for(Enumeration e = node.children();e.hasMoreElements();) {
             jnode c = (jnode)e.nextElement();
             if(c.get().trim().equals(""))continue;
             String st;
             byte ctype = getTypePos(st=c.get());
             if(ctype == -1 && st.indexOf('*') >= 0) {ispat = true; isnotpat = st.indexOf('!') >= 0;}
             else {
                 word wws[] = getHeadedWords(gamelist,c,alreadyHad,extrawords,patterns,noselect, groupOb);
                 newwords = u.addWords(newwords,onlywords(wws));
             }
           }
           if(ispat) {
             newwords = u.addWords(newwords, extrawords);
             String ss;
             Enumeration e;
             if(isnotpat) for(e=node.children();e.hasMoreElements();) {
               jnode c = (jnode)e.nextElement();
               byte ctype = getTypePos(ss=c.get());
                if(ctype == -1 && ss.indexOf('*') >= 0 && ss.indexOf('!') >= 0) {
                   ss = u.strip(ss,'!');
                   for(j = 0;j<newwords.length;++j)  {
                      if(wordlist.fits(newwords[j].v(),ss) >= 0
                           && (newwords[j].pat==null || newwords[j].pat.length() < ss.length())) {
                         newwords = u.removeword(newwords,j);
                         --j;
                      }
                   }
                }
             }
             for(e = node.children();e.hasMoreElements();) {
               jnode c = (jnode)e.nextElement();
               byte ctype = getTypePos(ss=c.get());
                if(ctype == -1 && ss.indexOf('*') >= 0 && ss.indexOf('!')< 0) {
                   for(j = 0;j<newwords.length;++j)  {
                      if(wordlist.fits(newwords[j].v(),ss) >= 0
                         && (newwords[j].pat==null || newwords[j].pat.length() < ss.length())) {
                         newwords[j].pat = ss;
                         words = u.addWords(words,newwords[j]);
                      }
                   }
                }
             }
           }
           else words = newwords;
        }
     }
     else {
         if(node != root && type < 0) return new word[]{new word(s,databaseName)};
        else return words;
     }
     switch(type) {
        case PAIR:
           if(words.length == 2)  {
              words[0].paired = true;
              words[0].companions = 1;
              words[1].companions = 1;
              words[1].paired = true;
            }
            break;
         case ALLORNONE:
            if(words.length > 1 && words.length < 20) {
               for(i=0;i<words.length;++i)
                  words[i].companions = (byte)(words.length-1);
            }
            break;
         case SELITEMS:
            jnode parent = (jnode)node.getParent();
            if(getTypePos(parent.get()) == HEADING &&
                 parent.getChildCount() == 1)    {
               words = tryaddextras(words,extrawords,patterns);
            }
            selcount = (short)u.getint(s.substring(s.indexOf(":")+1));
            if(lastHeading != null){
                ArrayList tempList = new ArrayList();
                tempList.add(lastHeading);
                tempList.add(selcount);
                headingSelects.add(tempList);
            }
            
            if(selcount < words.length && !noselect)  {
               words = selitems(u.stripdups2(words),selcount,inspecialgame(node));
         }
            break;
         case HOMOPHONES:
            if(node.isLeaf() && node.getLevel()==1) {
               allhomos = true;
            }
            else for(i=0;i<words.length;++i) words[i].homophone=true;
            break;
          case FL:
              fl = true; break;
          case NONSENSE:
              nonsense = true; break;
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case MARKGAMESHEADINGS:
            markgheading = s.substring(s.indexOf(':')+1);
            if(markgheading.trim().equals(""))markgheading=null;
            break;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          case ISUNITREVISION:
              unitrevision = true; break;  
          case REVISE:
              revision = true; break;
          case PICTUREPREFERENCE:              
              picturePrefence = s.substring(s.indexOf(':')+1); break;
          case APPRIORITY1:
              APPriority1 = true; break;
          case APPRIORITY2:
              APPriority2 = true; break;              
          case APNOTINTEST:
              apnotintest = true; break;
          case APNOTINUNITORTEST:
              apnotinunitortest = true; break;
          case NOTPHONICS:
              notphonics = true; break; 
          case INORDER: inorder = true; break;
          case FORCEKEYPAD: forcekeypad = true;break;
          case PHRASES: phrases = true; break;
          case BLENDS: blended = true;break;
          case JUSTPHONICS: justphonics = true; break;
          case NOTMIXEDWORDS: notmixedwords = true; break;
          case STARTPHONICS: startphonics = true; break;
          case STARTNONPHONICS: startnonphonics = true; break;
          case MARKGAMES:
                  markgformula = getformula(s);
                  if(markgformula==null){
                      markgames = splitlist(s);
                  }
            break;
         case MARKGAMES2:
                  markgformula2 = getformula(s);
                  if(markgformula2==null){
                      markgames2 = splitlist(s);
                  }
             break;
        case MARKGAMESCODE:
//            markgamescode = splitlist(s);
            int p = s.indexOf(":");
            if(p >= 0){
                markgamescode = s.substring(p+1);
            }                       
            break;
         case GAMEOPTIONS:
              gameoptionlist = u.addStringSort(gameoptionlist, s.substring(s.indexOf(':')+1));
              break;
         case HEADING:
            if(node.getChildCount() != 1
               || getTypePos(((jnode)node.getChildAt(0)).get()) != SELITEMS)   {
                     words = tryaddextras(words,extrawords,patterns);
            }
            for(i=0;i<words.length;++i) words[i].heading=s;
            break;
          case PHONICDISTRACT:
            phonicdistractors = u.addStringSort(phonicdistractors,s.substring(s.indexOf(':')+1));
            break;
     }
     if(allhomos) for(i=0;i<words.length;++i) words[i].homophone=true;
     return words;
  }
  //---------------------------------------------------------
       // Words under heading marked as satisfy pattern(s) or not.
       // Used via wordlist.getPatternList by 'growflower'  and
       // other programs that want a simple yes/no decision.
  public word[] getHeadedWords2(String[] gamelist) {
      return getHeadedWords2(gamelist, false, false, null);      
  }
//  public word[] getHeadedWords2(String[] gamelist) {
  public word[] getHeadedWords2(String[] gamelist, boolean excludeteachingnotes, boolean noselect, getGroups groupOb) {
     short i,j;
     byte type;
     String s,s2;
//     word extrawords[] = getAllWords(true);
     word extrawords[] = getAllWords(true, excludeteachingnotes);
     wantrefs = true;
     word words[] = new word[0];
     topic alreadyHad[] = new topic[]{this};
     String patts[];
     if(!root.isLeaf()) {
        for(Enumeration e = root.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           if(c.get().trim().equals(""))continue;
           type = getTypePos(s=c.get());
           if(type == GAMES && gameinlist(s,gamelist)) {
              jnode[] c1 = stripBlankNodes(c.getChildren());
                 // special ---  select groups:1 is allowed here
              if(c1.length==1 && getTypePos(s2=c1[0].get()) == SELGROUPS
                  && u.getint(s2.substring(s2.indexOf(":")+1)) == 1 ) {
                 c1 = stripBlankNodes(c1[0].getChildren());
                 int chosen = -1;
                 if(groupOb!=null){
                     chosen = groupOb.getChosen(c1.length, 1, c1[0])[0];
                     for(int p = 0; p < extrawords.length; p++){
                         extrawords[p].heading = c1[chosen].get();
                     }
                 }
                 else{
                   chosen = u.rand(c1.length);
                 }
                 c = c1[chosen];
                 c1 = stripBlankNodes(c.getChildren());
              }
              if(getTypePos(c1[0].get()) == HEADING)
                  headings = new String[]{c1[0].get().substring(c1[0].get().indexOf(':')+1)};
              else if(getTypePos(c.get()) == HEADING)
                  headings = new String[]{c.get().substring(c.get().indexOf(':')+1)};
                             // 2 headings means no patterns, just 2 lists
              if(c1.length == 2 && getTypePos(c1[0].get()) == HEADING
                                && getTypePos(c1[1].get()) == HEADING) {
                 words = onlywords(getWords(gamelist,c1[0],alreadyHad));
                 for(i=0;i<words.length;++i) words[i].pat = "";
                 words = u.addWords(words,onlywords(getWords(gamelist,c1[1],alreadyHad, noselect, groupOb)));
              }
              else {
                 patts = getpatterns(c);
                 patterns = patts;
                 words = u.addWords(extrawords,onlywords(getWords(gamelist,c,alreadyHad, noselect, groupOb)));
                 for(i=0;i<words.length;++i) {
                    for(j=0;j<patts.length;++j) {
                      if(patts[j].indexOf('!') >= 0) {
                         if(wordlist.fits(words[i].v(),u.strip(patts[j],'!'))>=0) {
                           words = u.removeword(words,i);
                           --i;
                           break;
                         }
                      }
                      else if(wordlist.fits(words[i].v(),patts[j])>=0
                            && (words[i].pat == null || words[i].pat.length()< patts[j].length())){
                         words[i].pat = patts[j];
                      }
                    }
                 }
              }
              return words;
            }
        }
     }
     return words;
  }
  //---------------------------------------------------------
  private jnode[] stripBlankNodes(jnode[] jns){
      for(int i = jns.length-1; i>= 0; i--){
          if(jns[i].get().trim().equals(""))jns = u.removeNode(jns, i);
      }
      return jns;
  }
  
  
  public String[] getpatterns(jnode node) {
     String patts[] = new String[0];
     if(!shark.production && node.get().trim().equals(""))return patts;
     short i;
     if(!node.isLeaf())  {
        for(Enumeration e = node.children();e.hasMoreElements();) {
          patts = u.addString(patts,getpatterns((jnode)e.nextElement()));
       }
     }
     else {
        String s = node.get();
        if(!s.substring(0,1).equals(topicTree.ISTOPIC)
             && !s.substring(0,1).equals(topicTree.ISPATH)
               && s.indexOf('*') >= 0 )
             return new String[]{s};
     }
     return spellchange.spellchange(patts);
  }
  //------------------------------------------------------------------
  word[] tryaddextras(word wordsin[], word extrawords[],String[] patterns) {
               if(patterns.length ==0) return wordsin;
               short i,j;
               for(i=0;i<wordsin.length;++i) {
                   if (wordsin[i].pat != null) {
                      int patlen =  wordsin[i].pat.length();
                      for(j=0;j<patterns.length;++j) {
                        if (patterns[j].length() > patlen) {
                          for (i = 0; i < wordsin.length; ++i) {
                            if (wordlist.fits(wordsin[i].v(), u.strip(patterns[j],'!')) >= 0) {
                              wordsin = u.removeword(wordsin, i);
                              --i;
                            }
                          }
                        }
                      }
                      return wordsin;
                  }
               }
                  // if no patterns, add extended words before select
               word newwords[] = u.addWords(wordsin,extrawords);
               word words[] = new word[0];
               loop1:for(i=0;i<newwords.length;++i) {
                     // exclude words satisfying other patterns
                   for(j=0;j<patterns.length;++j) {
                      if(wordlist.fits(newwords[i].v(),u.strip(patterns[j],'!')) >= 0 ) continue loop1;
                   }
                   words = u.addWords(words,newwords[i]);
                }
               return words;
  }
  //--------------------------------------------------------------
  public boolean testWord(String game) {
    byte type;
     int i;
     oktot=0;
     needonset=false;
     topic alreadyHad[] = new topic[]{this};
     hadword = false;
     sharkStartFrame.gameflag ff = sharkStartFrame.gameflag.get(game);
     String gamelist[] = new String[]{game};
     if(superlist) {
//startPR2008-08-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       if(ff != null && ff.multisyllable) return false;
       if(ff != null && ff.multisyllable && !sharkStartFrame.mainFrame.wordTree.splitCB.isSelected()) return false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       if(!supercheck(game,this)) return false;
       return okgames[u.findString(sharkStartFrame.mainFrame.gamename,game)];
     }
     else if(sharkStartFrame.mainFrame.usingsuperlist != null && this == sharkStartFrame.currPlayTopic) {
       if(!supercheck(game,sharkStartFrame.mainFrame.usingsuperlist) || ff != null && ff.multisyllable) return false;
     }
     boolean specialprivate = translations || definitions;
     boolean specialprivateon = (wordlist.usetranslations && translations) || (wordlist.usedefinitions && definitions);
     if(game.equalsIgnoreCase("Find picture (from written)")){
         int h;
         h = 0;
     }
     
     if(ff.needanysplit && MYSQLUpload.doingPort && !MYSQLUpload.gotSplits){
         return false;
     }

     if(ff==null)
       return false;
     if(ff.flonly) {
        if(!fl || (specialprivate && !specialprivateon)) return false;
     }
     boolean isalpha = false;

     if(ff.notfl) {
        if(fl || (specialprivate && specialprivateon)) return false;
     }
     if(ff.nottranslations && (translations && specialprivateon)){
        return false;
     }
     if(ff.notdefinitions && (definitions && specialprivateon)){
        return false;
     }
     if(ff.notmultisyllable && multisyll)
         return false;
     if(ff.alphabet) {
//        if(!alphabetonly) return false;   // alphabet change
//        else                              // alphabet change
            isalpha = true;
     }
     if(ff.notifdups) {
        if(aredups || shortlist) return false;
     }
     if(ff.phonics) {
        if(!phonics || (phonicsw && !wordlist.usephonics)) return false;
     }
     if(ff.notphonics) {
        if(phonics  &&  !(phonicsw && !wordlist.usephonics)) return false;
     }
     if(ff.phonicsingles) {
        if((phonics && !phonicsw  && !singlesound) && !isalpha) return false;
     }
     if(ff.notblended) {
        if(blended) return false;
     }
     if(ff.phonicsw) {
        if(!phonicsw || !wordlist.usephonics) return false;
     }
     if(ff.notphonicsw) {
        if(phonicsw && wordlist.usephonics ) return false;
     }
     if(ff.needsentences1) {
        return specialgame(sentencegames2[0]);
     }
     if(ff.needsentences3 && !phrases) {
        return specialgame(sentencegames2[2]);
     }
     if(ff.needchunks) {
        boolean ok = false;
        word ww[] = getSpecials(new String[]{topic.sentencegames2[0]});
        if(ww==null) return false;
        for(i=0;i<ww.length;++i) if(ww[i].value.indexOf('�') >= 0) {ok = true; break;}
        if(!ok) return false;
     }
     if(ff.avwordlen4 && avwordlen() < 4) return false;
     if(ff.special)   {               //"games:")) {
       for(Enumeration e = root.children();e.hasMoreElements();) {
           String s = ((jnode)e.nextElement()).get();
           type = getTypePos(s);
           if(type == GAMES  && gameinlist(s,gamelist)) return true;
        }
        return false;
     }
     if(ff.justpairedwords) {
       for(Enumeration e = root.children();e.hasMoreElements();) {
           String s = ((jnode)e.nextElement()).get();
           type = getTypePos(s);
           if(type==-1) return false;
        }
     }
     if(ff.notpairedwords) {  // not if list is ONLY paired words
       boolean ok1 = true;
       for(Enumeration e = root.children();e.hasMoreElements();) {
           String s = ((jnode)e.nextElement()).get();
           type = getTypePos(s);
           if(type==-1) {ok1 = true; break;}
           else if (type==PAIR) ok1 = false;  //may fail
        }
        if(!ok1) return false;
     }
     boolean ownwordlackpics = false;
     if(ff.needpictures) {
        if(nopictures){
            if(!ownlist)
               return false;
            else ownwordlackpics = true;
        }
     }
     int k = u.findString(sharkStartFrame.gamename,game);
     if(k>=0){
         sharkStartFrame.gameflags[k].owllackpics = ownwordlackpics;
         short okrectot = 0;
         short okextrarectot = 0;
         boolean active = false;
         boolean extraactive = false;
         if(ownlist){
             if(!specialprivateon){
               for(i = 0; i < root.getChildCount(); i++){
                 if(ff.owlneedrec){
                    String s = ((jnode)root.getChildAt(i)).get();
                    s = s.replaceAll("/", "");
                    active = true;
                    boolean gotrec = false;
                    for(int n = 0; n < sharkStartFrame.publicSoundLib.length; n++){
                        if (db.findwav(sharkStartFrame.publicSoundLib[n], s) !=null) {
                            gotrec = true;
                            break;
                        }
                    }
                    if(!gotrec){
                        for(int n = 0; recs!=null && n< recs.length; n++){
                            if(recs[n] != null && recs[n].name.equals(s)){
                                gotrec = true;
                                break;
                            }
                        }
                    }
                    if(gotrec)okrectot++;
                 }           
               }
             }
             else{
               for(i = 0; i < root.getChildCount(); i++){
                  String s = ((jnode)root.getChildAt(i)).get();
                  s = s.replaceAll("/", "");
                  extraactive = true;
                  for(int n = 0; xrecs!=null && n< xrecs.length; n++){
                     if(xrecs[n] != null && xrecs[n].name.equals(s)){
                        okextrarectot++;
                        break;
                     }
                  }
               }                   
             }
       }
       sharkStartFrame.gameflags[k].owllackrecs = (active && okrectot < MINOWLRECORDINGS && root.getChildCount()>okrectot);
       sharkStartFrame.gameflags[k].owllackextrarecs = (extraactive && okextrarectot < MINOWLRECORDINGS && root.getChildCount()>okextrarectot);
     }
     oktot=0;
     return testWord(new String[]{game},root,alreadyHad);

 }
 static boolean supercheck(String game,topic t) {
    int i;
    sharkStartFrame.gameflag gf = sharkStartFrame.gameflag.get(game);
    if(gf==null)return false;
    boolean spelling = gf.spelling;
//    boolean spelling = sharkStartFrame.gameflag.get(game).spelling;
//    if( !spelling && t.superd.last3games.indexOf(SPELLING)<0 && !sharkStartFrame.simplesuper)
    if(superForceSpelling && !spelling && t.superd.last3games.indexOf(SPELLING)<0)
                           return false;
    return true;
 }
  //---------------------------------------------------------
  public boolean testWord(String[] gamelist,jnode node,
                                 topic[] alreadyHad) {
     boolean ret = false;
     String s = node.get();
     short i,j,selcount;
    if(s.length()==0) return false;
     if(s.substring(0,1).equals(topicTree.ISPATH)) {
        topic t[] = topicTree.getTopics(s);
        for(i=0;i<t.length;++i) {
           if(t[i] != null && !t[i].topicinlist(alreadyHad)) {
              alreadyHad = u.addTopic(alreadyHad,t[i]);
              t[i].referencedlist=true;
              if(t[i].testWord(gamelist,t[i].root,
                         alreadyHad)) return true;
           }
        }
        return false;
     }
     else  if(s.substring(0,1).equals(topicTree.ISTOPIC)) {
        int pos = s.indexOf(topicTree.SEPARATOR);
        topic t = null;
        if(pos>0)  t = new topic(s.substring(1,pos),s.substring(pos+1),null,null);
        if(t != null  && !t.topicinlist(alreadyHad)) {
          alreadyHad = u.addTopic(alreadyHad,t);
          t.referencedlist=true;
          return t.testWord(gamelist,t.root,alreadyHad);
        }
        return false;
     }
     byte type = getTypePos(s);
     if(type == GAMES && !gameinlist(s,gamelist)
             || type == NOTGAMES && allgamesinlist(s,gamelist)) {
         return false;
     }
     if(type == COURSES && !incourse(s)) {
         return false;
     }
     if(type == PAIR) {
       if (referencedlist || needonset)  return false;
     }
     if(type == HEADING) {
         for(i=0;i<gamelist.length;++i) {
             if(allowed(gamelist[i],"heading:")) return true;
         }
         if(hadword) return false;
     }
     if(type == PAIR) {
         for(i=0;i<gamelist.length;++i) {
              if (sharkStartFrame.gameflag.get(gamelist[i]).pairedwords) return true;
         }
         for(i=0;i<gamelist.length;++i) {
              if (!sharkStartFrame.gameflag.get(gamelist[i]).notpairedwords) break;
         }
         if(i==gamelist.length)
           return false;  // all failed
     }
     if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
              if(testWord(gamelist,(jnode)e.nextElement(),alreadyHad)) return true;   // need to override this for gathering available game sets for split words - add splits to words?
        }
     }
     else {
        if(!node.isRoot() && type < 0) {
                     // individual game check
           hadword=true;
           for(i=0;i<gamelist.length;++i) {
              if (allowed(gamelist[i],s)) return true;
           }
        }
     }
     return false;
  }
  /**
   * <li>Saves flags for the game passed.
   * <li>Returns true if the flag indicated using wordval is true.
   * @param game Name of a game
   * @param wordval String giving value to be tested
   * @return True if the flag indicated using wordval is true or else false
   */
  boolean allowed(String game, String wordval) {
     int i,j,k;
     sharkStartFrame.gameflag flags = sharkStartFrame.gameflag.get(game);
     if (wordval.equals("split4")) return(!flags.needanysplit || split4);//Condition 7
     if (wordval.equals("unsplit")) return(!flags.needsyllsplit || !unsplit);//Condition 8
     if(wordval.indexOf("*")>= 0) return(flags.usepattern);  //Condition 10
     if(flags.usepattern) return false;                       //Condition 11
     if(flags.pairedwords) return false;                       //Condition 12
     if(flags.nosingleletters                                       //Condition 13
        && (wordval.length()<2 || wordval.indexOf('@')==1) ) return false;
     if(flags.needbad && (this.referencedlist ||            //Condition 14
                                  wordval.charAt(0) != '(')) return false;
//startPR2008-08-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(flags.multisyllable && !multisyll && (!phonics || !wordlist.usephonics)) return false;//Condition 15
        if((!wordlist.splitCB.isSelected()) && flags.multisyllable && !multisyll && (!phonics || !wordlist.usephonics)) return false;//Condition 15
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(flags.needshapes && !sharkStartFrame.wantshapes) return false;
//     if(flags.notshapes && sharkStartFrame.wantshapes) return false;                 // start rb 27-11-07
     if(flags.needonset) {                                    //Condition 16
        if (sharkStartFrame.notwantonsetrime) return false;//Condition 8
        needonset = true;
//        if((u.vowels.indexOf(wordval.charAt(0))>=0)) return false;    //Condition 16.1 
        if((u.vowels.indexOf(wordval.charAt(0))>=0) || u.syllabletot(new word(wordval,databaseName).v())!=1) return false;
        else return(++oktot >= 5);               //Else 16.1
     }                                                                               // end  rb 27-11-07
     return true;
  }
  //-------------------------------------------------------------
  public boolean topicinlist(topic list[]) {
     for(short i=0;i<list.length;++i) {
        if(name.equals(list[i].name)
           && databaseName.equals(list[i].databaseName)) return true;
     }
     return false;
  }
  //-------------------------------------------------------------
  boolean gameinlist(String s, String[] g) {
    String okgames[] = splitlist(s);
    short i,j;
    for(i=0;i<g.length;++i) {
       for(j=0;j<okgames.length;++j) {
          if(okgames[j].equalsIgnoreCase(g[i])) return true;
       }
    }
    return false;
  }
  //-------------------------------------------------------------
  boolean incourse(String s) {
    String list[] = splitlistc(s);
    short i;
    for(i=0;i<list.length;++i) {
          if(sharkStartFrame.currCourse != null
             && list[i].equalsIgnoreCase(sharkStartFrame.currCourse)) return true;
    }
    return false;
  }
  //-------------------------------------------------------------
  boolean allgamesinlist(String s, String[] g) {
    String okgames[] = splitlist(s);
    short i,j;
    outer:for(i=0;i<g.length;++i) {
       for(j=0;j<okgames.length;++j) {
          if(okgames[j].equals(g[i])) continue outer;
       }
       return false;  // one of the games is not in the wordlist tlist
    }
    return true;
  }
  //-----------------------------------------------------------
                   // split list of games - adds all games below
                   // selected node
  static String[] splitlist(String s) {
     String news[] = new String[0], s1;
     short curr=(short)(s.indexOf(":")+1), i;
     boolean last = false;
     jnode node = null;
     while(!last) {
        i = (short) s.substring(curr).indexOf(",");
        if(i >= 0) {s1 = s.substring(curr,curr+i);curr+=i+1;}
        else        {s1 = s.substring(curr); last=true;}
        String ending = "";
        if(s1.endsWith("^")) {
           jnode nodes[] = ((jnode)(sharkStartFrame.mainFrame.publicGameTree.root.getChildAt(0))).getChildren();
           ending = s1.substring(s1.length()-3);
           int ty = Integer.parseInt(ending.substring(1,2));
           if(ty<nodes.length)
               node = sharkStartFrame.mainFrame.publicGameTree.find(
                       nodes[ty], s1.substring(0,s1.length()-3));
        }
        else node = sharkStartFrame.mainFrame.publicGameTree.find(s1);
        if(node != null) {
           String ss[] = sharkStartFrame.mainFrame.publicGameTree.nodeList(node);
//           for(i=0;i<ss.length;++i) ss[i] += ending;
           for(int n=ss.length-1;n>=0;n--){
               ss[n] += ending;
               if(u.findString(news, ss[n])>=0){
                   ss = u.removeString(ss, n);
               }
           }
           news = u.addString(news,ss);
        }
     }
     return news;
  }
  //-----------------------------------------------------------
                   // split list of courses
  static String[] splitlistc(String s) {
     String news[] = new String[0], s1;
     short curr=(short)(s.indexOf(":")+1), i;
     boolean last = false;
     jnode node;
     while(!last) {
        i = (short) s.substring(curr).indexOf(",");
        if(i >= 0) {s1 = s.substring(curr,curr+i);curr+=i+1;}
        else        {s1 = s.substring(curr); last=true;}
        news = u.addString(news,s1);
     }
     return news;
  }

  static String getformula(String s) {
    if(s==null)return null;
    String entry = s.substring(s.indexOf(':')+1).trim();
    for (int p = 0; sharkStartFrame.mainFrame.markgameformulas!=null &&
         p < sharkStartFrame.mainFrame.markgameformulas.length; p++) {
       if(sharkStartFrame.mainFrame.markgameformulas[p][0].equals(entry)){
           return entry;
        }
    }
    return null;
  }

  //-----------------------------------------------------------
  public void save() {
     String newname = root.get();
     if(!changed || invalidRoot) return;
     db.update(databaseName,newname,(new saveTree1(this,root)).curr,db.TOPIC);
     if(!newname.equals(name) && old)  {
        if(!newname.equalsIgnoreCase(name)) db.delete(databaseName,name,db.TOPIC);
        sharkStartFrame.mainFrame.topicTreeList.fixTopicRefs(this,newname);
        sharkStartFrame.mainFrame.topicTreeList.saveChanges();
        fixrefsintopics(name,newname);
      }
      if(!newname.equals(name)) {
         sharkStartFrame.mainFrame.topicList.refreshTopicList(databaseName);
         sharkStartFrame.mainFrame.topicTreeList.refreshTopicList(databaseName);
      }
      changed = false;
      name = newname;
  }
  //---------------------------------------------------------------------
  void fixrefsintopics(String oldname, String newname) {
      String s[] = db.list(databaseName,db.TOPIC);
      String fullname, newfullname;
      int i;
      fullname = topicTree.ISTOPIC + databaseName + topicTree.SEPARATOR + oldname;
      newfullname = topicTree.ISTOPIC + databaseName + topicTree.SEPARATOR + newname;
      topic tt;
      for(i = 0; i < s.length; ++i) {
        tt = new topic(databaseName,s[i],null,null);
        if(tt == null) continue;
        for(jnode j=(jnode)tt.root.getFirstLeaf(); j!=null ;j = (jnode)j.getNextLeaf()) {
           if(fullname.equals((String)j.get())) {
              j.setUserObject(newfullname);
              tt.changed=true;
           }
        }
        if(tt.changed) tt.save();
     }
  }
  //-------------------------------------------------------------------
      // remove list of words set by user
      void removesplitwords(String newdb) {
          int j,k;
          
          if(wordlist.splitsInDevMode){
              db.delete(sharkStartFrame.publicSplitsLib[0],name,db.TEXT);
          }else{
              if(newdb.equals(sharkStartFrame.optionsdb)
              && !databaseName.equals(sharkStartFrame.optionsdb)
              && !databaseName.equals(publicname))
               db.delete(newdb,databaseName+"+++"+name,db.TEXT);
             else db.delete(newdb,name,db.TEXT);              
          }
             return;
       }
  //-------------------------------------------------------------------
       // save list of words split by user
 void savesplitwords(String newdb,String ww2[]) {
     int j,k;
     for(int i=0;i<ww2.length;++i) {
        j =ww2[i].indexOf('/');
        if(j < 0 || (k=ww2[i].indexOf('=')) >0 && j>k) {ww2 = u.removeString(ww2,i); --i;}
     }
     if(wordlist.splitsInDevMode){
        if(ww2.length > 0) {
           db.update(sharkStartFrame.publicSplitsLib[0],name,ww2,db.TEXT);
        }
        else{
            db.delete(sharkStartFrame.publicSplitsLib[0],name,db.TEXT);
        }
     }
     else{
        if(ww2.length == 0) {
           if(newdb.equals(sharkStartFrame.optionsdb)
            && !databaseName.equals(sharkStartFrame.optionsdb)
            && !databaseName.equals(publicname))
             db.delete(newdb,databaseName+"+++"+name,db.TEXT);
           else db.delete(newdb,name,db.TEXT);
           return;
        }
        if(newdb.equals(sharkStartFrame.optionsdb)
            && !databaseName.equals(sharkStartFrame.optionsdb)
            && !databaseName.equals(publicname))
           db.update(newdb,databaseName+"+++"+name,ww2,db.TEXT);
        else db.update(newdb,name,ww2,db.TEXT);         
     }
     
     

  }
//startPR2008-10-21^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public boolean okforwordfont() {
//    jnode n;
//    String s;
//    for(n = (jnode)root.getFirstLeaf(); n != null; n = (jnode)n.getNextLeaf()) {
//      if(u.scanfornot(s=n.get(),inwordsharkfont) >= 0)
//             return false;
//    }
//    return true;
//  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public int avwordlen() {
     word ww[] = getAllWordsBoth();
     if(ww.length==0) return 0;
     int mm =0;
     for(int i=0;i<ww.length;++i) mm += ww[i].v().length();
     return mm/ww.length;
  }
  String[] markgames() {
    String formulagames[] = null;
    for (int i = 0; sharkStartFrame.mainFrame.markgameformulas!=null &&
            i < sharkStartFrame.mainFrame.markgameformulas.length; i++) {
      if(sharkStartFrame.mainFrame.markgameformulas[i][0].equals(markgformula)){
          for(int k = 1; k < sharkStartFrame.mainFrame.markgameformulas[i].length; k++){
              if(u.findString(formulagames, sharkStartFrame.mainFrame.markgameformulas[i][k])<0)
                formulagames = u.addString(formulagames, sharkStartFrame.mainFrame.markgameformulas[i][k]);
          }
      }
    }
      if(formulagames!=null){
          return formulagames;
      }

      if(markgames == null) return null;
      String[] ret = null;
      String ending = "^" + (phrases?"3":(!phonics || !wordlist.usephonics?"0":(phonicsw ? "2": "1"))) + "^";
      for(int i=0; i<markgames.length; ++i) {
         if(markgames[i].endsWith(ending)) {
            ret = u.addString(ret,markgames[i].substring(0,markgames[i].length()-3));
         }
         else if(!markgames[i].endsWith("^")) {
            ret = u.addString(ret,markgames[i]);
         }
      }
      return ret;
  }
  String[] markgames2() {
    String formulagames[] = null;
    for (int i = 0; sharkStartFrame.mainFrame.markgameformulas!=null &&
            i < sharkStartFrame.mainFrame.markgameformulas.length; i++) {
      if(sharkStartFrame.mainFrame.markgameformulas[i][0].equals(markgformula2)){
          for(int k = 1; k < sharkStartFrame.mainFrame.markgameformulas[i].length; k++){
              if(u.findString(formulagames, sharkStartFrame.mainFrame.markgameformulas[i][k])<0)
                formulagames = u.addString(formulagames, sharkStartFrame.mainFrame.markgameformulas[i][k]);
          }
      }
    }
      if(formulagames!=null)
          return formulagames;

      if(markgames2 == null) return null;
      String[] ret = null;
      String ending = "^"+ (phrases?"3":(!phonics || !wordlist.usephonics?"0":(phonicsw ? "2": "1"))) + "^";
      for(int i=0; i<markgames2.length; ++i) {
         if(markgames2[i].endsWith(ending)) {
            ret = u.addString(ret,markgames2[i].substring(0,markgames2[i].length()-3));
         }
         else if(!markgames2[i].endsWith("^")) {
            ret = u.addString(ret,markgames2[i]);
         }
      }
      return ret;
  }
  
  jnode[] getHeadingNodes(jnode jn){
      jnode hnodes[] = new jnode[]{};
      jnode[] c1 = jn.getChildren();
      for(int jj = 0; jj < c1.length; jj++){
          if(getTypePos(c1[jj].get()) == HEADING){
             hnodes =  u.addnode(hnodes, c1[jj]);
          }
          else if (!c1[jj].isLeaf()){
              hnodes =  u.addnode(hnodes, getHeadingNodes(c1[jj]));
          }
      }
      return hnodes;
  }
  
  
  void rightClickpic(MouseEvent e) {   // v5 start rb 6/3/08
    jnode sel = getSelectedNode();
    String name;
    int i,j,k;
    int w1 = sharkStartFrame.screenSize.width * 3 / 8;
    int h1 = sharkStartFrame.screenSize.height / 3;
//    if (sel != null && sel != root && !(name = sel.get()).equals("") && getTypePos(name) < 0) {
    if (sel != null && sel != root && !(name = sel.get()).equals("")) {
      if(!shark.production && getTypePos(name) == 0){
          String games[] = splitlist(name);
          
          
          
          
          
          
        boolean helicop = (games.length==1 && games[0].indexOf("elicopter") >= 0);
        selectGameDetails bucks[] = getSelectGameBuckets(games, helicop, -1, -1, -1, -1, null);


        StringBuffer style = new StringBuffer("font-family:" + sharkStartFrame.treefont.getFamily() + ";");
        style.append("font-weight:" + "normal" + ";");
        style.append("font-size:" + sharkStartFrame.treefont.getSize() + "pt;");
             String outp = "";    
             for(int mm = 0; mm < bucks.length; mm++){
                for(int kk = 0; kk < bucks[mm].groups.length; kk++){
                    String wor[] = bucks[mm].groups[kk].words;
         //           wor = u.stripdups(wor);
                    outp +=  "<font color=\"red\">";
                    outp += bucks[mm].groups[kk].heading;
                    outp +=  "</font>";
                    outp += "<br>";
                     for(int jj = 0; jj < wor.length; jj++){
            //             outp += wor[jj].v();
                         outp += wor[jj];
                         outp += "<br>";
                     }  
                     outp += "<br>";
                }
             }
        String outp2 = "<html><body style=\"" + style + "\">" + outp + "</body></html>";                 
             Rectangle r = new Rectangle(sharkStartFrame.mainFrame.getWidth()/5,sharkStartFrame.mainFrame.getHeight()/20,
                 sharkStartFrame.mainFrame.getWidth()/5, sharkStartFrame.mainFrame.getHeight()*18/20);
             u.showtext(sharkStartFrame.mainFrame, "Words for "+ u.combineString(games, ","),outp2, r);          
      

      }  
      else if(getTypePos(name) < 0){        
      sharkImage im = null;
      sharkImage im2 = null;
      String imName= null;
      if(getTypePos(((jnode)sel.getParent()).get()) == GAMES) {
        if((j = name.indexOf('{')) >= 0 && (k = name.indexOf('}',j+1)) > 0) {
          for(i=0;i<sharkStartFrame.publicImageLib.length && im == null;++i) {
            im = new sharkImage((imName=name.substring(j+1,k)), sharkStartFrame.publicImageLib[i]);
            if(im.p == null) im = null;
          }
          if(im != null) {
              w1 =  sharkStartFrame.screenSize.width / 3;
              h1 = sharkStartFrame.screenSize.height * 7/8;
          }
         }
        
        
          if(!shark.production && shark.showPhotos && imName!=null){
            int h;
                if ((h=u.getPhotographIndex(imName))>=0){
                      Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                              + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                      im2 = new sharkImage(im1, imName);
                      im2.isimport = true;              
                }      
          }        
        
        
         if(fl) spokenWord.findandsaysentence3(name);
         else  spokenWord.findandsaysentence2(name);
      }
      else  {
          boolean isWordsharkInTest = this.name.startsWith(MYSQLUpload.WORDSHARKTESTPREFIX);
        word ww = new word(name, this.databaseName);
        if(!ww.bad) {
//startPR2009-02-25^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(ww.phonics)
            spokenWord.sayPhonicsWord(ww, 400, true, false, !singlesound, (JComponent)e.getSource());
          else
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            ww.say();
          if(!isWordsharkInTest){
                byte buf[];
                String vpic = ww.vpic();
                int p;
                if((p = vpic.indexOf('@'))>0)vpic = vpic.substring(0, p);
                if ( name.endsWith("@@w") &&   (buf = (byte[]) db.find(sharkStartFrame.publicWidgitLib, vpic, db.PICTURE)) != null) {
                    im = new sharkImage(sharkStartFrame.t.createImage(buf), name);
                }
                for(i=0;i<sharkStartFrame.publicImageLib.length && im == null;++i) {
                  im = new sharkImage(ww.vpic(), sharkStartFrame.publicImageLib[i]);
                  if(im.p == null) im = null;
                }
                for(i=0;i<sharkStartFrame.publicSignLib.length && im == null;++i) {
                  im = new sharkImage(ww.vpic(), sharkStartFrame.publicSignLib[i]);
                  if(im.p == null) im = null;
                }
                for(i=0;i<sharkStartFrame.publicFingerLib.length && im == null;++i) {
                 im = new sharkImage(ww.vpic(), sharkStartFrame.publicFingerLib[i]);
                 if(im.p == null) im = null;
               }
          }
          
          if(shark.showPhotos){
            String pic = ww.vpic();
            if(isWordsharkInTest  && !pic.trim().endsWith("@@none")){
                pic = u.getPhotoNameInWordsharkTestCourse(pic);    
            }
            int h;
//            int h = u.getPhotographIndex(pic);
            if ((h=u.getPhotographIndex(pic))>=0){
                  Image im1 = Toolkit.getDefaultToolkit().getImage(sharkStartFrame.publicPhotoNamesFolder
                          + sharkStartFrame.separator + sharkStartFrame.publicPhotoNamesPlusExt[h]); 
                  im2 = new sharkImage(im1, ww.vpic());
                  im2.isimport = true;              
            }                
          }
       }
      }
      if(currpic != null) currpic.stop();
      currpic = null;
      if(currpic2 != null) currpic2.stop();
      currpic2 = null;
      if(im != null || im2 != null) {
        int x1 = this.getLocationOnScreen().x - w1;
        int y1 = Math.max(0, Math.min(sharkStartFrame.screenSize.height-h1, e.getY() + this.getLocationOnScreen().y - h1/2));
        if(im!=null)currpic = new wordlist.wordpicture((Window)getTopLevelAncestor(),name,im, x1,y1,w1,h1);
        if(shark.showPhotos && im2 != null)currpic2 = new wordlist.wordpicture((Window)getTopLevelAncestor(),name,im2, x1-w1,y1,w1,h1, true);
      }
      }
    }
  }                           // v5 end rb 6/3/08
  
  
  
  public selectGameDetails[] getSelectGameBuckets(String games[], boolean isHelicop, int selectNumberTarget, int selectNumberDistractor, int allocGood, int allocBad, 
          MYSQLUpload mysqlup) {
          word ww[][] = null;
          selectGameDetails groups[] = null;

          
          ArrayList sets = new ArrayList();
          ArrayList setsSelectNos = new ArrayList();
          ArrayList setsHeadings = new ArrayList();
          getGroups gg = new getGroups();
          
          
          String heads[] = new String[]{};
          do{
                    if(name.equals("a and i, (introduce n)")){
                        int h;
                        h  = 0;
                    } 
            if(isHelicop){
               ww = getHeadedWords(games, true, true,gg);  
               heads = headings;
            }else{
               ww = new word[][]{getHeadedWords2(games, true, true, gg)};    
               heads = headings;
            }
            sets.add(ww);
            for(int i = 0; i < headingSelects.size(); i++){
                ArrayList al = (ArrayList)headingSelects.get(i);
                try{
                    if( al.get(0)instanceof String[]) {
                        al.set(0, stripHeadingText((String[])al.get(0)));
                    }
                    else{
                        al.set(0, stripHeadingText(new String[]{(String)al.get(0)}));
                    } 
                }
                catch(Exception e){
                    int h = 0;
                }
            } 
            setsSelectNos.add(headingSelects);
            setsHeadings.add(stripHeadingText(heads));
            
            
            
          }while(!gg.completed());
          if(ww.length > 0  && Collections.disjoint(Arrays.asList(games), Arrays.asList(sentencegames2))){
                for(int kk = 0; kk < sets.size(); kk++){
                    if(name.equals("a and i, (introduce n)")){
                        int h;
                        h  = 0;
                    }
                    ArrayList headingPatterns = new ArrayList(); 
                    String headings[] = (String[])setsHeadings.get(kk);  
                    String headingSounds[] = new String[]{};      
                    ArrayList words = new ArrayList();
                    word www[][] = (word[][])sets.get(kk);
                    
                    word matching[][] = new word[headings.length][];
                    word notmatching[] = new word[]{};                      
                    
                    for(int h = 0; h < headings.length; h++){
                        String headingText = headings[h];
                        String s1 = null;
                        if(mysqlup!=null && headingText!=null){
                            s1 = mysqlup.tor.findJsonRecording(mysqlup.jsonRecResults, "publicsent1", headingText);
                            if(s1 == null){
                                s1 = mysqlup.tor.findJsonRecording(mysqlup.jsonRecResults, "publicsent1", headingText.toLowerCase());
                            }  
                        }
                        headingSounds = u.addString(headingSounds, s1);
                        jnode jjn = find(root, HEADING_TEXT+headingText);
                        if(jjn==null){
                            int g;
                            g = 0;
                        }
                        String patterns[] = getpatterns(jjn);
                        if(patterns.length>0){
                            headingPatterns.add(patterns);
                        }
                        else{
                            headingPatterns.add(null);
                        } 
                        if(isHelicop){
                            words.add(www[h]);
                        }
                        else{ 
                            for(int ii = 0; ii < www.length; ii++){
                                word[] w2 = www[ii];     
                                for(int ii2 = 0; ii2 < w2.length; ii2++){
                                    int ih = -1;
                                    for(int jj = 0; jj < headingPatterns.size(); jj++){
                                        if(headingPatterns.get(jj)!=null &&  u.findString((String[])headingPatterns.get(jj), w2[ii2].pat)>=0){
                                            ih = jj;
                                            break;
                                        }
                                    }                                    
                                    if(w2[ii2].pat == null ){
                                        if(notmatching==null || notmatching.length == 0)
                                            notmatching = new word[]{w2[ii2]};
                                        else 
                                            notmatching = u.addWords(notmatching, w2[ii2]);
                                    }
                                    else{
                                        ih = Math.max(ih, 0);// for no pattern, e.g. short forms 1
                                        if(matching[ih]==null || matching[ih].length == 0)
                                            matching[ih] = new word[]{w2[ii2]};
                                        else 
                                            matching[ih] = u.addWords(matching[ih], w2[ii2]);                                           
                                    }
                                }
                            }                            
                        }
                        
                        
                        
                        
                        
                        
                        
                    }                        
                    if(!isHelicop){
                        if(matching!=null){
                             for(int ii = matching.length-1; ii >=0 ; ii--){
                                word ww1[] = matching[ii];
                                if(ww1==null){
                                    matching = u2_base.removeWordArray(matching, ii);   
                                }
                            }
                        }
                        if(notmatching!=null && headings.length>1){
                            headings[headings.length-1] = HEADING_NO_TEXT;
                        }      
                        if(headings.length==1){
                            headings = u.addString(headings, HEADING_NO_TEXT);
                            headingSounds = u.addString(headingSounds, (String)null);
                            headingPatterns.add(null);
                        } 
                        for(int i = 0; matching!=null && i < matching.length; i++){
                            words.add(matching[i]);
                        }                
                        if(notmatching!=null){
                            words.add(notmatching);
                        }       
                    }
                // if no select in a helicopter game, add one of select 9
                // if select no exists for helicopter game and is over 9, change it to 9.   
                if(isHelicop && (selectNumberTarget<0 || selectNumberTarget>9))
                    selectNumberTarget = 9;
                selectGameDetails group = new selectGameDetails(selectNumberTarget, selectNumberDistractor, allocGood, allocBad);
                for(int i = 0; i < headings.length; i++){
                    String wordIDs[] = new String[]{};
                    word wrds[] = (word[])words.get(i);
                    wrds = u.stripdups(wrds);
                    if(mysqlup!=null){
                        if(!isHelicop){
                            boolean isTarget = i==0;
                            int minWords = isTarget?selectNumberTarget:selectNumberDistractor;                       
                            if(minWords>0)
                                wrds = makeUpTheNumbers(wrds, minWords);                            
                        }
                        for(int ip = 0;ip < wrds.length; ip++){  
                            if(MYSQLUpload.CURRENT_MODE == MYSQLUpload.MODE_VIA_DUMP){
                                int ggg = mysqlup.doWordNew(wrds[ip], null, getTargetWithAnyPattern(wrds[ip].v(), (String[])headingPatterns.get(i)), mysqlup.t.mySQL_Topic_ID, null, new int[]{mysqlup.WORD_TYPE_PATTERNGAME});
//                            if(u.findString(wordIDs, String.valueOf(ggg)) < 0)   don't strip out duplicates - mucks up Pattern
                                wordIDs = u.addString(wordIDs, String.valueOf(ggg));                 
                            }
                            else{
                       //     int ggg = mysqlup.doWordNew(wrds[ip], null, getTargetWithAnyPattern(wrds[ip].v(), (String[])headingPatterns.get(i)), mysqlup.t.mySQL_Topic_ID, null, new int[]{mysqlup.WORD_TYPE_PATTERNGAME});
//                            if(u.findString(wordIDs, String.valueOf(ggg)) < 0)   don't strip out duplicates - mucks up Pattern
                                if(u.findString(wordIDs, wrds[ip].v())<0)
                                    wordIDs = u.addString(wordIDs, wrds[ip].v());
                                }
 
                        }                        
                    }
                    else{
                        for(int ip = 0;ip < wrds.length; ip++){
                            if(u.findString(wordIDs, wrds[ip].v())<0)
                                wordIDs = u.addString(wordIDs, wrds[ip].v());
                        }                              
                    }               
                    group.newBucket(wordIDs, headings[i], getSelectFromHeadings(headings[i], (ArrayList)setsSelectNos.get(kk), isHelicop), headingSounds[i], (isHelicop || i==0));
                }            
                groups = u2_base.addSelectGameDetails(groups, group);     
            }
          }
      return groups;
  }  
  
      word[] makeUpTheNumbers(word s[], int wanted){
        word ss[] = s;
        while (ss.length<wanted){
            for(int i = 0; i < s.length; i++){
                ss = u.addWords(ss, s[i]);
            }
        }
        return ss;
    } 
      
  String[] stripHeadingText(String[] s){  
        for(int i = 0; i < s.length; i++){
                if(s[i].startsWith(HEADING_TEXT))
                    s[i] = s[i].substring(HEADING_TEXT.length());
         }
      return s;
  }    
      
   short getSelectFromHeadings(String heading, ArrayList headingDetails, boolean isHelicop){  
        for(int i = 0; i < headingDetails.size(); i++){
            ArrayList al = (ArrayList)headingDetails.get(i);
            String headings[] = (String[])al.get(0);
            if(u.findString(headings, heading)>=0){
                return (short)al.get(1);
            }
         }
      return -1;
  } 
     
     
  String getTargetWithAnyPattern(String wval, String ss[]){
        if(ss==null || ss.length==0)return null;
        boolean b[] = null;
        for(int i = 0; i < ss.length; i++){
            boolean b2[] = null;
            if(b == null) b = wordlist.fitsPattern(wval,ss[i], new boolean[wval.length()], 0);
            else b2 = wordlist.fitsPattern(wval,ss[i], new boolean[wval.length()], 0);
            if(b2 != null){
                for(int j = 0; j < b.length; j++){
                    if(b2[j])b[j] = true;
                }
            }
        }
        boolean found = false;
        for(int j = 0; j < b.length; j++){
            if(b[j]){
                found = true;break;
            }  
        }
        if(found){
            String s = wval;
            boolean rightBracketNext = true;
            for(int j = b.length-1; j >=0; j--){
                if(rightBracketNext && b[j]){
                    s = s.substring(0, j+1) + "]" + s.substring(j+1);
                    rightBracketNext = false;
                }
                if(!rightBracketNext && !b[j]){
                    s = s.substring(0, j+1) + "[" + s.substring(j+1);
                    rightBracketNext = true;
                }
            }                
            if(!rightBracketNext)s = "["+s;
            return s;
        }
        return null;
    }  
  
  
}




class getGroups {
  jnode groupNode[];
  ArrayList doneGroups = new ArrayList();

  int addGroup(int tot, jnode jn){
      groupNode = u.addnode(groupNode, jn);
      doneGroups.add(new boolean[tot]);
      return groupNode.length-1;
  }
  /*
  void chosen(jnode jn){
      jnode c[] = jn.getChildren();
      int pos = getIndex(c.length, jn);
      boolean pp[] = (boolean[])doneGroups.get(pos);
      for(int i = 0; i < pp.length; i++){
              pp[i] = true;
              doneGroups.set(pos, pp);
      }
      checkIfAncestor(jn);
  }
  */
  int[] getChosen(int tot, int selectNo, jnode jn){
      jnode c[] = jn.getChildren();
      int pos = getIndex(tot, jn);
      boolean pp[] = (boolean[])doneGroups.get(pos);
      int i;
      int got[] = new int[]{};
      int alreadyDone[] = new int[]{};
      for(i = 0; got.length<selectNo&& i < pp.length; i++){
          if(!pp[i]){
              pp[i] = true;
              got = u.addint(got, i);
              doneGroups.set(pos, pp);
              if(got.length==selectNo)
                return got;
          }
          else{
              alreadyDone = u.addint(alreadyDone, i);
          }
      }
      
      while(got.length < selectNo){
          alreadyDone = u.shuffle(alreadyDone);
          i = alreadyDone[0];
          if(!u.inlist(got, i)){
              pp[i] = true;
              got = u.addint(got, i);  
              if(got.length==selectNo)
                return got;
          }
          
      }
      
      checkIfAncestor(jn);
      return null;
  }
  
  void checkIfAncestor(jnode jn){
      int k = findNodePos(jn);
      for(int i = 0; groupNode!=null && i < groupNode.length; i++){
          if(i == k) continue;
          if(jn.isNodeChild(groupNode[i]) && !isNodeDone(i)){
              boolean b[] = (boolean[])doneGroups.get(k);
              b = new boolean[b.length];
              doneGroups.set(k, b);
          }
      }        
  }
  
boolean isNodeDone(int i){
          boolean it[] = (boolean[])doneGroups.get(i);
          for(int k = 0; k < it.length; k++){
              if(!it[k])return false;
          } 
          return true;
}

int findNodePos(jnode jn){
       for(int i = 0; groupNode!=null && i < groupNode.length; i++){
          if(groupNode[i].equals(jn)){
              return i;
          }
      } 
       return -1;
}
 
  int getIndex(int tot, jnode jn){
      int i;
        if((i=findNodePos(jn))>=0)return i;
      return addGroup(tot, jn);
  }
  
  boolean completed(){
      for(int i = 0; i < doneGroups.size(); i++){
          if(!isNodeDone(i))return false;
      }
      return true;
  }
}

class superlistel {
  String topicname;
  int used; // count at last time topics used in spelling games,
  boolean error; // error causes 2 words from this list
}

class supersave {
  int usetopic = -1;   // force this topicnumber to be used
  String last3games = topic.deflast3games;
  String name;
  superlistel d[];
}

