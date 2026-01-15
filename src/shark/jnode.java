package shark;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import java.util.*;

public class jnode extends DefaultMutableTreeNode {
  public ImageIcon icon;
  public Color color=Color.black,bgcolor;
  public boolean forceColor;
  public byte  type=-2;
  public boolean dontcollapse,dontexpand,dontforcerevision,dummy;    // rb 26-11-07
  static Color datasetcolor = new Color(128,64,0);
  static Color refcolor = new Color(0,128,0);
  static ImageIcon icons[] = new ImageIcon[] {
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"student_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"group_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"admin_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"dataset.gif"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"course_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"topicgroup.png"),
       null,     /// topic
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"gamegroup.gif"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"gamesON_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"list.gif"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"reference.gif"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"topickey.gif"),
       null,
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"picopts_il16.png"),
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"textgroup"),
       null,    // text
       new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"admin_il16.png"),
       null,null,null,null,null,null,null,
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"sharksmall.gif"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"subadmin_il16.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"allstudents_il16.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"allstudents_il16.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"gamesOFFgrey_il16.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"steppedprog.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"standardprog.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"blank_il16.png"),
  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"font_il16.png"),
  null};

  static ImageIcon empty =  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"empty.gif");
  static ImageIcon edit =  new ImageIcon(sharkStartFrame.publicPathplus+"sprites" +sharkStartFrame.separator+"edit.gif");
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Used to store extra data in addition to what is displayed in the tree.
   * Used in making private phonic word lists where it stores the sounds of the
   * words that aren't displayed to the user.
   */
  public String userObject2 = null;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public String extratext = "";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public boolean okdrag = true;
  public static final byte STUDENT = 0;
  public static final byte GROUP = 1;
  public static final byte TEACHER = 2;
  public static final byte DATASET = 3;
  public static final byte COURSE = 4;
  public static final byte TOPICGROUP = 5;
  public static final byte TOPIC = 6;
  public static final byte GAMEGROUP = 7;
  public static final byte GAME = 8;
  public static final byte LIST = 9;
  public static final byte REFERENCE = 10;
  public static final byte TOPICKEY = 11;
  public static final byte TOPICEL = 12;
  public static final byte PICTURE = 13;
  public static final byte TEXTGROUP = 14;
  public static final byte TEXT = 15;
  public static final byte PROJECT = 16;
  public static final byte ROOTUPDATETOPICTREE = 17;
  public static final byte ROOTUPDATEGAMES = 18;
  public static final byte ROOTUPDATEPICTURES = 19;
  public static final byte ROOTUPDATETEXT = 20;
  public static final byte ROOTUPDATETOPIC = 21;
  public static final byte ROOTTOPICTREE = 22;
  public static final byte ROOTGAMETREE = 23;
  public static final byte MAINPROGRAM = 24;
  public static final byte SUBADMIN = 25;
  public static final byte MULTISTU = 26;
  public static final byte OTHERSTUS = 27;
  public static final byte GAMEBLANK = 28;
  public static final byte STEPPEDPROGRAM = 29;
  public static final byte STANDARDPROGRAM = 30;
  public static final byte BLANK = 31;
  public static final byte NOTES = 32;
  
  
  public boolean isExcludedWord = false;
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      public UUID uuid = null;
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  
          
          
          
  public jnode(String s) {
     super(s);
  }
  public jnode() {
     super("");
  }
       // replicate subbranch
  public jnode(jnode old) {
     super(old.get());
     icon = old.icon;
     color = old.color;
     bgcolor = old.bgcolor;
     type = old.type;
     dontcollapse = old.dontcollapse;
     if(!old.isLeaf()) {
        jnode c[] =old.getChildren();
        for (short i=0;i<c.length;++i) {
          old.addChild(new jnode(c[i]));
        }
     }
  }
       // replicate expansion
  public static void replicate(jnode old,jnode newn,JTree oldtree,JTree newtree) {
     if(oldtree.isExpanded(new TreePath(old.getPath())))
           newtree.expandPath(new TreePath(newn.getPath()));
     if(!old.isLeaf()) {
        jnode c[] =old.getChildren();
        jnode c2[] =newn.getChildren();
        for (short i=0;i<c.length;++i) {
          replicate(c[i],c2[i],oldtree,newtree);
        }
     }
  }
  public String get(){
    return (String) (getUserObject());
  }
  public jnode addChild(jnode j) {
     add(j); return j;
  }
  public jnode[] getChildren() {
     int lim=getChildCount(),i;
     jnode c[] = new jnode[lim];
     for(i = 0; i<lim; ++i) {
         c[i] =  (jnode)getChildAt(i);
     }
     return c;
  }
  public int getPosition() {
    jnode parent = (jnode)getParent();
    if(parent==null) return(0);
    jnode j[] = parent.getChildren();
    for(int i =0;i<j.length;++i) {
       if(j[i] == this) return i;
    }
     return j.length;
  }
  public void setIcon(byte sel) {
      icon = icons[type=sel];
  }
  public void setIcon() {   // find type and icon from tree position
     String s = get();
     if (s.length() == 0) return;
     if(s.startsWith("  ")) {dummy=true;return;}     // rb 26-11-07
     type = -1;
     jnode parent = (jnode)getParent();
     jnode root = (jnode)getRoot();
     int level = getLevel();

     if(level==0 && !forceColor) color = new Color(128,64,64);
     else if(root.type == ROOTUPDATETOPIC) {
        if(topic.getTypePos(s) >=0) {setIcon(TOPICKEY);color = Color.red;}
        else if(s.charAt(0) == topicTree.ISTOPIC.charAt(0))
                       {setIcon(REFERENCE); color = refcolor;}
        else {
            setIcon(TOPIC); color = Color.black;
            if(isExcludedWord){
               color = sharkStartFrame.col_ExcludedWords;
            }
        }
     }
     else if(root.type == ROOTUPDATEGAMES) {
        if(level ==1) {setIcon(DATASET); color = datasetcolor;}
        else if(level == 2) {
           color = Color.magenta;
          setIcon(LIST);
        }
        else if(level==3 && ((jnode)getParent()).get().equals(topicTree.GAMELISTNAME))
            { setIcon(GAME);  color = Color.black;}
        else if(level==4 && ((jnode)getParent().getParent()).get().equals(topicTree.GAMELISTNAME))
            { color = Color.black;}
        else if(isLeaf()) {
           color = Color.black;
           setIcon(GAME);        }
        else {setIcon(GAMEGROUP); color = Color.red;}

     }
     else if(root.type == ROOTUPDATEPICTURES) {
        if(level ==1) {setIcon(DATASET); color = datasetcolor;}
        else {setIcon(PICTURE); color = Color.black;}

     }
     if(root.type == ROOTUPDATETEXT) {
        if(level ==1) {setIcon(DATASET); color = datasetcolor;}
        else if(!isLeaf()) {setIcon(TEXTGROUP); color = Color.red;}
        else  {setIcon(TEXT); color = Color.black;}
     }
     else if(root.type == ROOTUPDATETOPICTREE) {
        if(level ==1) {setIcon(DATASET); color = datasetcolor;}
        else if(level == 2) {
           if(s.equals(topicTree.TOPICLISTNAME))
                 {setIcon(LIST); color = Color.magenta;}
           else {setIcon(COURSE); color = Color.blue;}
        }
        else if(s.charAt(0) == topicTree.ISTOPIC.charAt(0))
             {setIcon(TOPIC); color = Color.black;}
        else if(s.charAt(0) == topicTree.ISPATH.charAt(0))
                       {setIcon(REFERENCE); color = refcolor;}
        else    {setIcon(TOPICGROUP); color = Color.red;}
      }
     else {
        if(root.type == ROOTGAMETREE) {
           if(isLeaf()) {setIcon(GAME); color = Color.black;}
           else if(getFirstChild().isLeaf()) {setIcon(GAMEGROUP); color = sharkStartFrame.fastmodecolor;}
           else {color = Color.blue;}
        }
        else if(root.type==ROOTTOPICTREE) {
           if(level ==1) {setIcon(COURSE); color = Color.blue;}
           else if(isLeaf()) {setIcon(TOPIC);color = Color.black;}
//           else         {setIcon(TOPICGROUP);  color = Color.red;}
           else         {setIcon(TOPICGROUP);  color = sharkStartFrame.fastmodecolor;}           
           
           
        }
     }
  }
  public boolean isparentof(String ss) {
    if(ss.equalsIgnoreCase(get())) return true;
    else  {
      jnode[] cc = getChildren();
      for (int i = 0; i < cc.length; ++i)
        if (cc[i].isparentof(ss)) return true;
      return false;
    }
  }
  
  public boolean ischildof(String ss) {
    if(ss.equalsIgnoreCase(get())) return true;
    else  {
      jnode jn = ((jnode)getParent());
      try{
      if (jn.ischildof(ss)) return true;          
      }
      catch(Exception e){   
      }
      return false;
    }
  }
  public jnode oldfind(String ss) {
    if(ss.equalsIgnoreCase(get())) return this;
    else  {
      jnode cc[] = getChildren(),ret;
      for (int i = 0; i < cc.length; ++i)
        if ((ret = cc[i].oldfind(ss))!= null) return ret;
      return null;
    }
  }
  public jnode find(String ss) {
    int i;
    jnode node;
    if(ss.startsWith(topicTree.ISPATH)) {
       if(type == STUDENT) return null;
       String sss = ss.substring(1),s1=null;
       i=sss.indexOf(topicTree.ISPATH);
       if(i>0) {
        s1 = sss.substring(i);
        sss = sss.substring(0, i);
        }
      jnode[] cc = getChildren();
       for (i = 0; i < cc.length; ++i) {
           if (cc[i].type != STUDENT && sss.equalsIgnoreCase(cc[i].get())) {
               if(s1 == null) return this;
               else return cc[i].find(s1);
           }
       }
       return null;
    }
    else {
      if (type==STUDENT && ss.equalsIgnoreCase(get()))
        return this;
      else {
        jnode[] cc = getChildren();
        for (i = 0; i < cc.length; ++i) {
          jnode ret = cc[i].find(ss);
          if (ret != null)   return ret;
        }
        return null;
      }
    }
  }
  //----------------------------------------------------------------------------------
  public boolean inphonics() {
    if(Demo_base.isDemo)return (u.findString(Demo_base.phonicstopics, get())>=0);
    jnode node = this;
     while(node != null) {
       if(u.findString(u.phonicscourses, node.get())>=0) return true;
       node=(jnode)node.getParent();
     }
    return false;
  }
  //---------------------------------------------------------------------
  

 
 
}
