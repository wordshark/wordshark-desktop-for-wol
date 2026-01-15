
package shark;

import java.io.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.tree.*;

class topicTree extends sharkTree{
   public String dbname;
   public String memname;
   public String dbnames[];  // non-null to collect db names of topictrees
   public static final String SEPARATOR = "\u00BB";
   public static final char CSEPARATOR = '\u9999';
   public static final String ISTOPIC = "\u00AB";
   public static final String ISPATH  = "\u00BB";
   public static final String TOPICLISTNAME = "<list of topics>";
   public static final String TEXTLISTNAME = "<list of text entries>";
   public static final String GAMELISTNAME = "<list of games>";
   public static final String GAMETREELISTNAME = "<game trees>";
   public static final String SUMLISTNAME = "<list of sums>";
   boolean expandAll,isGameTree;
   public static String lastSelection;
   public byte lastSelectionType;
   public String onlyOneDatabase;
   String dblist[] = new String[0];
   String dbchanged = new String("");
   String dontwant[],onlywant;
   long lastsubfocuschange;
   jnode editsel;
   static String privatelists = u.gettext("topics","privatelists");
   static String adminlists = u.gettext("topics","adminlists");
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//    public boolean isInitialSelection = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public String onlyshowcourse;
   public boolean exitValueChanged = false;
   public static final int MODE_EXPAND_NONE = 0;
   public static final int MODE_NO_COMPRESS_HEADING = 1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   public String onlyNamedLists[];
   public boolean isAbsoluteDb = false;
   public boolean updatingTopics = false;

   static String MARKGAMESCODES = "Codes";
   static String MARKGAMESSEQUENCES = "Sequences";
   //----------------------------------------------------
   protected void finalize() {
      if(updating) {
         saveChanges();
//         db.closeAll();
      }
   }
   //------------------------------------------------------
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//   public void setup(String lists1[], boolean updating1, byte wantType,
//                  boolean expandAll1, String title) {

   public void setup(String lists1[], boolean updating1, byte wantType,
                  boolean expandAll1, String title) {
       setup(lists1, updating1, wantType,
                  expandAll1, -1, title, null);
   }



   public void setup(String lists1[], boolean updating1, byte wantType,
                  boolean expandAll1, int expand, String title) {
       setup(lists1, updating1, wantType,
                  expandAll1, expand, title, null);
   }

   public void setup(String lists1[], boolean updating1, byte wantType,
                  boolean expandAll1, int expand, String title, jnode startnode) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String lists[] = lists1;
      updating = updating1;
      this.setToggleClickCount(updating?4:1);
      copying = true;
      onlyOneType = wantType;
      if(root.type==-2  && onlyOneType == db.TOPIC)
              root.setIcon(jnode.ROOTUPDATETOPICTREE);
      else if(root.type==-2 && onlyOneType == db.GAME)
              root.setIcon(jnode.ROOTUPDATEGAMES);
      else if(onlyOneType == db.GAME)
              root.setIcon(jnode.ROOTGAMETREE);
      else if(updating && onlyOneType == db.IMAGE)
              root.setIcon(jnode.ROOTUPDATEPICTURES);
      else if(updating && onlyOneType == db.TEXT)
              root.setIcon(jnode.ROOTUPDATETEXT);

      expandAll = expandAll1;
      jnode node;
      short i,j;
      root.setUserObject(title);
      removeAllChildren(root);
      model.reload();
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String dbnam = "";
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(onlyOneDatabase != null && onlyOneDatabase.equals("*") && onlyOneType != 0) {
        jnode prinode = null;
        if(lists.length>0
//startPR2004-10-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            && sharkStartFrame.currStudent >= 0
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            && !sharkStartFrame.studentList[sharkStartFrame.currStudent].isnew
            && !sharkStartFrame.studentList[sharkStartFrame.currStudent].administrator
            && sharkStartFrame.studentList[sharkStartFrame.currStudent].hastopics
            && onlyOneType == db.TOPICTREE) {
           String privates[]  = db.list(lists[0],db.TOPIC);
           if(privates != null && privates.length > 0) {
              prinode=new jnode(privatelists);
              for(i=0;i<privates.length;++i) {
                 model.insertNodeInto(new jnode(ISTOPIC+(new File(lists[0])).getName()
                         +SEPARATOR+privates[i]),prinode,prinode.getChildCount());
              }
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if(dbnames != null) dbnames = new String[]{lists[0]};
              if(dbnames != null) dbnam = lists[0];
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           }
         }

         outer: for(i = 0; i < lists.length; ++i) {
            String tlist[] = db.listsort(lists[i],onlyOneType);
            boolean redo = true;
            int count = 0; 
            redoloop: for(int p = 0; redo; ++p) {
                redo = false;
                for(j=0;j<tlist.length;++j) {
                   if(onlyOneType==db.TOPICTREE &&
                     u.findString(sharkStartFrame.courses,tlist[j]) >= 0) continue;
//                   if(onlyOneType==db.TOPICTREE &&
//                     tlist[j].trim().equals("")) continue;
                   if((dontwant==null || u.findString(dontwant,tlist[j])<0)
                      && (onlywant==null || tlist[j].equalsIgnoreCase(onlywant))) {
                      onlyOneDatabase = getDBName(lists[i]);
                      if(onlyOneType==db.GAMETREE)  {
                         dblist = new String[] {onlyOneDatabase};
                         set(root,getDBName(onlyOneDatabase));
                         expandNode(root,new String[0]);
                         set(root,title);
                         lists = new String[] {onlyOneDatabase};
                         break outer;
                      }
                      else {
                        int r = root.getChildCount();  
                        expandNode(root.addChild(new jnode(getDBName(onlyOneDatabase))),new String[0]);
                        if(root.getChildCount() == 1 && ((jnode)root.getChildAt(0)).get().equals(onlyOneDatabase)){
                            sharkStartFrame.courses = settings.getUniversalHiddenCourses();
                            u.okmess(shark.programName, u.gettext("adminsettings", "reset"), sharkStartFrame.mainFrame);
                            student.setOption("s_courses", "");
                            sharkStartFrame.mainFrame.clearStudentCourses();
                            count++;
                            if(count<2){
                                redo=true;
                                p = 0;
                                continue redoloop;
                            }
                        }
                        if(dbnames != null && onlyOneType==db.TOPICTREE){
                 //         if(i>0)dbnames = u.addString(dbnames, "");
                          jnode jns[] = root.getChildren();
                          for(int jj=r;jj<jns.length;++jj){
                              dbnames = u.addString(dbnames, jns[jj].get().trim().equals("")?"":lists[i]);
                          }
                        }                        
                        continue outer;
                      }
                   }
                }
            }
         }
         jnode jj[] = root.getChildren();         // start rb 26-11-07
         for(i=(short)(jj.length-1); i>=0 && u.findString(sharkStartFrame.mainFrame.publicCourses,jj[i].get()) < 0; --i);
         if(i < jj.length - 1 && i >=0) {
            model.insertNodeInto(new jnode("  "),root,i+1);
            if(dbnames != null) dbnames = u.addString(dbnames,"", i+1);
         }                                         // end rb 26-11-07
         if(prinode != null) {
           model.insertNodeInto(new jnode("  "),root,root.getChildCount());  // rb 26-11-07
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(dbnames != null) dbnames = u.addString(dbnames,"", dbnames.length);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           model.insertNodeInto(prinode,root,root.getChildCount());
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(dbnames != null) dbnames = u.addString(dbnames,dbnam, dbnames.length);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
         if(!updating && student.admintopiclist.length>0 && onlyOneType == db.TOPICTREE) {
            if(prinode == null)
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              model.insertNodeInto(new jnode("  "), root, root.getChildCount()); // rb 26-11-07
//startPR2007-11-28^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(dbnames != null) dbnames = u.addString(dbnames,"");
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            jnode admnode=new jnode(adminlists);
            boolean adminnodeinserted = false;
            for(i=0;i<student.admintopiclist.length;++i) {
              jnode admnode2=new jnode((new File(student.admintopiclist[i])).getName());
              admnode2.type = jnode.TEACHER;
              admnode2.color = Color.red;
              String privates2[]  = db.list(student.admintopiclist[i],db.TOPIC);
              if(privates2.length==0)continue;
              if(!adminnodeinserted){
                  model.insertNodeInto(admnode,root,root.getChildCount());
                  adminnodeinserted = true;
              }
              model.insertNodeInto(admnode2,admnode,admnode.getChildCount());
              for(j=0;j<privates2.length;++j) {
                 model.insertNodeInto(new jnode(ISTOPIC+(new File(student.admintopiclist[i])).getName()
                        +SEPARATOR+privates2[j]),admnode2,admnode2.getChildCount());
              }
              if(dbnames != null) dbnames = u.addString(dbnames,student.admintopiclist[i]);
            }
         }
      }
      else {
         dblist = new String[lists.length];
         System.arraycopy(lists,0,dblist,0,lists.length);
         for(i = 0; i < lists.length; ++i) {
            String fname = getDBName(lists[i]);
            if(!(onlyOneType != db.WAV && fname.length() >= 9 && (fname.substring(0,9).equalsIgnoreCase("publicsay") || fname.substring(0,9).equalsIgnoreCase("publicdef")))
                && !(onlyOneType != db.IMAGE && fname.length() >= 11 && fname.substring(0,11).equalsIgnoreCase("publicimage"))
                && !(onlyOneType != db.IMAGE && fname.length() >= 11 && fname.substring(0,11).equalsIgnoreCase("publicsigns"))
                && !(onlyOneType != db.IMAGE && fname.length() >= 13 && fname.substring(0,13).equalsIgnoreCase("publicfingers"))) {
               node = root.addChild(startnode==null?new jnode(distribute?lists[i]:fname):startnode);
               expandNode(node,new String[0]);
            }
         }
         if(updating) addEmpty(root);
      }
      expandPath(new TreePath(root.getPath()));
      if(updating) {
         setEditable(true);
         setInvokesStopCellEditing(true);
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
      copying = false;
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean reload = false; 
      if(onlyshowcourse!=null){
          jnode jns[] = root.getChildren();
          for(int p = jns.length-1; p >= 0; p--){
              if(!jns[p].get().equals(onlyshowcourse)){
                  root.remove(p);
                  if(dbnames!=null && dbnames.length>p)dbnames = u.removeString(dbnames, p);
                  reload = true;
              }
          }
      }
      jnode jns1[] = root.getChildren();
      String lastnode = null;
      boolean dodbnamecheck = dbnames!=null && dbnames.length==jns1.length;
      for(int p = jns1.length-1; p >= 0; p--){
          String newstr = jns1[p].get().trim();
          boolean doremove = false;
          if(lastnode!=null && lastnode.equals("") && newstr.equals("")){
              if(dbnames != null && dodbnamecheck)
                  dbnames = u.removeString(dbnames, p);
              doremove = true;
          }
          lastnode = newstr;
          if(doremove){
              reload = true;
              root.remove(p);
              continue;
          }
          if(expand==MODE_EXPAND_NONE){
             jns1[p].dontexpand = true;
          }
          else if (expand==MODE_NO_COMPRESS_HEADING){
             jns1[p].dontcollapse = true;
          }
      }
      if(onlyNamedLists!=null){
          reload = true;
          jnode inode = (jnode)root.getLastLeaf();
          jnode nnode = null;
 //         jnode parents[] = null;
//          boolean hadchildren = false;
          
          
          String tokeep[] = new String[]{};
          while(inode!=null){   
//              boolean orihadchildren = hadchildren;
              nnode = (jnode)inode.getPreviousNode();
//              hadchildren = nnode!=null && nnode.getChildCount()>0;
//              if((u.findString(onlyNamedLists, inode.get())>=0 && !orihadchildren )|| isParent(inode, parents)){
              String ss[] = isNeeded(inode,tokeep);
              if(ss!=null)tokeep = u.addString(tokeep, ss);
              if(u.findString(tokeep, inode.get())<0){
                  if(inode.getParent()!=null)
                    model.removeNodeFromParent(inode);
              } 
              inode = nnode;
          }  
      }
      /*
      if(onlyNamedLists!=null){
          reload = true;
          jnode inode = (jnode)root.getLastLeaf();
          jnode nnode = null;
          jnode parents[] = null;
          boolean hadchildren = false;
          while(inode!=null){    
              boolean orihadchildren = hadchildren;
              nnode = (jnode)inode.getPreviousNode();
              hadchildren = nnode!=null && nnode.getChildCount()>0;
//              if((u.findString(onlyNamedLists, inode.get())>=0 && !orihadchildren )|| isParent(inode, parents)){
              if((u.findString(onlyNamedLists, inode.get())>=0 && !orihadchildren )|| isParent(inode, parents)){
                  parents = getParents(inode);
              }    
              else {
                  if(inode.getParent()!=null)
                    model.removeNodeFromParent(inode);
              } 
              inode = nnode;
          }  
      }
       * 
       */
      if(reload)
        model.reload();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   
   String getDBName(String db){
        if(db.indexOf(shark.sep+sharkStartFrame.updatesFolderName+shark.sep)>=0)
            return db;
        else
            return new File(db).getName();
   }   
   
   String[] isNeeded(jnode jn, String[] alreadyhad){
       boolean found = false;
       String news[] = new String[]{jn.get()};
       if(u.findString(onlyNamedLists, jn.get())>=0){  
           found = true;
       }
       jnode par = (jnode)jn.getParent();
       while(par!=null){
           if(u.findString(alreadyhad, par.get()) < 0)
            news = u.addString(news, par.get());
           if(u.findString(onlyNamedLists, par.get())>=0){
               found = true;
           }
           par = (jnode)par.getParent();
       }
       return (!found||news.length==0)?null:news;
   }
   
   jnode[] getParents(jnode jn){
       jnode par = (jnode)jn.getParent();
       jnode p[] = new jnode[]{};
       while(par!=null){
           p = u.addnode(p, par);
           par = (jnode)par.getParent();
       }
       return p;
   }
   
   /*
    *    boolean isParent(jnode jn, jnode[] ps){
       if(ps==null)return false;
       for(int i = 0; i < ps.length; i++){
           if(jn.equals(ps[i]))return true;
       }
       return false;
   }
   
   jnode[] getParents(jnode jn){
       jnode par = (jnode)jn.getParent();
       jnode p[] = new jnode[]{};
       while(par!=null){
           p = u.addnode(p, par);
           par = (jnode)par.getParent();
       }
       return p;
   }
    */

   //------------------------------------------------------
      // set the background of particular leafs
   public void setbg(String database,String name[], Color bg) {
      jnode jj[] = root.getChildren();
      int i;
      for(i=0;i<jj.length;++i) {
        if(jj[i].get().equalsIgnoreCase(database)) {
          for(jnode j =  (jnode)jj[i].getFirstLeaf(); j != null; j= (jnode)j.getNextLeaf()) {
            if(u.findString(name,j.get()) >= 0) {j.bgcolor = bg;}
            if(j == (jnode)jj[i].getLastLeaf()) return;
          }
        }
      }
   }
   //-------------------------------------------------------
   public boolean saveChanges() {
       boolean ret = false;
      int i;
      if(updating) {
          boolean isPublicTopics =false;
          for(int j = 0; j < this.dblist.length; j++){
              if(this.dblist[j].equals( sharkStartFrame.getPrimarySoundDb(sharkStartFrame.publicTopicLib)))isPublicTopics = true;
          }
         if(isPublicTopics)checkExcludedWords();
         copying = true;
              // scan databases
         for(Enumeration e = root.children();e.hasMoreElements();) {
            jnode c = (jnode)e.nextElement();
            String s  = c.get();
            jnode node  = null;
            if(s.length() > 0
                       && dbchanged.indexOf(s+SEPARATOR) >= 0) {
               db db1 = db.get(s,true);
               if(!c.isLeaf()) {
                  jnode c2[] = c.getChildren();
                  if(onlyOneType == db.TOPIC) {
                    deleteUnwanted(db1, c2, db.TOPICTREE);

                    String order[] = new String[0];
                    for (i = 0; i < c2.length; ++i) {
                      String ss = c2[i].get();
                      if (ss.length() > 0 && ss.charAt(0) != '<')
                        order = u.addString(order, ss);
                    }
                    db1.update("order", order, db.TEXT);
                    ret = true; 
                  }
                   if(onlyOneType==db.TEXT) {
                        saveText(s,c);
                        ret =  true; 
                  }
                  else for(short j = 0; j<c2.length;++j) {
                     String val =  c2[j].get();
                     if(val.equals("") || val.equals(TOPICLISTNAME)) continue;
                     else if(val.equals(GAMELISTNAME)) {
                        saveGames(s,c2[j]);
                        ret =  true; 
                     }
                     else if( val.equals(GAMETREELISTNAME)) {
                        saveGameTrees(s,c2[j]);
                        ret =  true; 
                     }
                    else if(val.equals(TEXTLISTNAME)) {
                        saveText(s,c2[j]);
                        ret =  true; 
                     }
                     else if(!c2[j].isLeaf()) {
                        db1.update(val,(new saveTree1(this,c2[j])).curr,db.TOPICTREE);
                        ret =  true; 
                     }
                  }
               }
               else {
                   deleteUnwanted(db1,new jnode[0],db.TOPICTREE);
                   ret =  true; 
               }
            }
         }
         dbchanged = new String("");
         clearundo();
         copying = false;
      }
      return ret;
   }
   
   
   
   public void checkExcludedWords() {
       String db1 = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
        String ss[] = db.list(db1, db.TEXT);
        String problems[] = new String[]{};
        for(int i = 0; i < ss.length; i++){
            int n;
            if((n=ss[i].indexOf(topic.EXCLUDEDWORDPREFIX))<0)continue;
            Object o = db.find(db1, ss[i], db.TEXT);
            String sss[][] = null;
            if(o instanceof String[][]){
                sss = (String[][])o;
                loop1: for(int j = sss.length-1; j >= 0; j--){
                    String path = "";
                    String pathdisplay = "";
                    for(int k = sss[j].length-1; k>=0 ; k--){
                        if(k < sss[j].length-1) path += topicTree.CSEPARATOR;
                        path += sss[j][k];
                    }
                    pathdisplay = (ss[i].substring(n+topic.EXCLUDEDWORDPREFIX.length()) + "    (" + path  + ")").replace(String.valueOf(topicTree.CSEPARATOR)   , "     /     ");
                    jnode startnode = findNode(sss[j][sss[j].length-1]);
                    if(startnode == null){
                        if(u.findString(problems, pathdisplay)<0)
                            problems = u.addString(problems, pathdisplay);
                        sss = deleteExcludedWords(ss[i], sss, j);
                        continue loop1;
                    }
                    if(expandPath((jnode)startnode.getParent(), path) == null){
                        if(u.findString(problems, pathdisplay)<0)
                            problems = u.addString(problems, pathdisplay);
                        sss = deleteExcludedWords(ss[i], sss, j);
                    }
                }
            }
        } 
        if(problems.length>0){
            String s[] = new String[]{u.gettext("excludedwords", "mess"), ""};
            s= u.addString(s, problems);
           stringedit_base se = new stringedit_base(u.gettext("excludedwords", "title"),s) {
              public boolean update(String s[]) {
                 return true;
              }
           };
           se.staythere = true;
        }
   }
   
   public String[][] deleteExcludedWords(String item, String ss[][], int pos) {
       if(ss.length>1)
       {
           String temp[][] = u2_base.removeStringArray(ss, pos);
           db.update(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), item, temp, db.TEXT);
           return temp;
       }
       db.delete(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), item, db.TEXT);
       return new String[][]{};
   }
  //----------------------------------------------------
  void deleteUnwanted(db db1,jnode c[],byte type) {
      for(short i=0;  i<db1.indexTot; ++i) {
         if(db1.index[i].type == type) {
            boolean found = false;
            if(!db1.index[i].name.equals(""))
             for(short j=0;  j<c.length; ++j) {
               if(c[j].get().equals(db1.index[i].name))
                  {found=true; break;}
            }
            if(!found) db1.delete(i--);
         }
      }
  }
  //----------------------------------------------------
  topic checkTopic(jnode node) {
    String s = node.get();
    String el=null,database=null;
    int pos = s.substring(1).indexOf(SEPARATOR) + 1;
    if(node.getLevel() < 2 || s.substring(0,1).equals(ISPATH))
                             return null;
    if(s.substring(0,1).equals(ISTOPIC))  {
       if(pos >  0) {
          database = s.substring(1,pos);
          el = s.substring(pos+1);
       }
       else  {
          el = s.substring(1);
          database = publicname;
       }
    }
    else if (((jnode)node.getParent()).get().equals(TOPICLISTNAME)) {
       el = s;
       database = findDatabase(node);
    }
    if(database != null) {
       return new topic(database, el,this,node);
    }
    return null;
  }
  //-----------------------------------------------------
  public void setCurr(String value) {
     jnode sel = getSelectedNode();
     if(sel == null) {return;}
     set(sel, value);
  }
  //-----------------------------------------------------
  public sharkImage[] picClip(boolean remove) {
     jnode sel[] = getSelectedNodes();
     if(sel.length == 0) return new sharkImage[0];
     sharkImage im[] = new sharkImage[sel.length];
     for (short i=0;i<sel.length;++i) {
       if(sel[i].getParent() != null && sel[i].getParent().getParent() != null) {
          im[i] = new sharkImage(findDatabase(sel[i]),sel[i].get(),true,false);
          if(remove) {
                db.delete(im[i].database,im[i].name,db.IMAGE);
                model.removeNodeFromParent(sel[i]);
          }
       }
     }
     return im;
  }
  
  //-----------------------------------------------------
  public void newSelection() {
     jnode sel = getSelectedNode();

     if(sel == null) {lastSelection = null;return;}                   //Condition 1
     
//     System.out.println(sel.uuid);     
     
     if(onlyOneType == db.WAV) {
       lastSelection = sel.get();
       return;
     }
     if(onlyOneType == db.IMAGE) {                                    //Condition 2
       if(sel.getLevel() < 2) {                                       //Condition 2.1
          this.setEditable(false);
          return;
       }
       setEditable(true);
       lastSelection = sel.get();
       if(shark.showPhotos) sharkStartFrame.mainFrame.newPicture(findDatabase(sel),lastSelection, sel);
       else sharkStartFrame.mainFrame.newPicture(findDatabase(sel),lastSelection);
       return;
     }
     if(onlyOneType == db.IMAGE) {                                    //Condition 3
       if(sel.getLevel() < 2) {                                       //Condition 3.1
          this.setEditable(false);
        }
       else setEditable(true);
     }
     if(!updating)
       lastSelection = getPath(sel);
     else {                                                        //Else 4
        String s;
        if(sel.isLeaf() && (s = sel.get()).indexOf('|') >= 0) {       //Condition 4.1
           editsel = sel;
           new stringedit_base("Edit multi-line text",u.splitString(s)) {
              public boolean update(String s[]) {
                 editsel.setUserObject(u.combineString(s));
                 model.reload(editsel);
                 recordChange(editsel);
                 return true;
              }
           };
        }
     }
     byte type = findType(sel);
     if(type == db.GAME || type == db.GAMETREE)                        //Condition 5
                          lastSelectionType = db.GAMETREE;
     else lastSelectionType = db.TOPICTREE;                            //Else 5

     if(!updating || sel.getLevel() < 2                                //Condition 6
           || sel.get().equals(TOPICLISTNAME)
           || ((jnode)sel.getParent()).get().equals(TOPICLISTNAME)) {
            setEditable(false);
     }
     else  ; setEditable(true);                                        //Else 6
       // if leaf, check if a topic
     if(updating && sel.getLevel() > 2) {                              //Condition 7
        valueBeforeEdit = sel.get();   // in case edited
     }
     if(sharkStartFrame.mainFrame.isAncestorOf(sharkStartFrame.mainFrame.markgamesformulatree)){
          sharkStartFrame.mainFrame.markgamesformulatree.saveChanges();
          sharkStartFrame.mainFrame.markgameformulas = sharkStartFrame.mainFrame.getMarkGameFormulas();
     }
     if(sharkStartFrame.mainFrame.isAncestorOf(sharkStartFrame.mainFrame.markgamestree)){
          sharkStartFrame.mainFrame.markgamestree.saveChanges();
          sharkStartFrame.mainFrame.markgameheadings = sharkStartFrame.mainFrame.getMarkGameHeadings();
     }
     if(sharkStartFrame.mainFrame.isAncestorOf(sharkStartFrame.mainFrame.markgamescoursetree)){
    //     sharkStartFrame.mainFrame.bevelPanel6.removeAll();
          sharkStartFrame.mainFrame.saveMarkCourseChanges();
          
     }
     if(sel.isLeaf()  && type != db.GAME  && type != db.TEXT           //Condition 8
                                   && type != db.GAMETREE) {
         if(sel.get().equals("")) {                                    //Condition 8.1
            if(updating && sel.getLevel() > 2)                         //Condition 8.1.1
              sharkStartFrame.mainFrame.addTopic(new topic(findDatabase(sel), this,sel));
            else                                                     //Else 8.1.1
              sharkStartFrame.mainFrame.removeTopic();
         }
         else {                                                        //Else 8.1
            topic t = checkTopic(sel);
            if(updating) {                                             //Condition 8.1.1
               if(t != null)                                         //Condition 8.1.1.1
                 sharkStartFrame.mainFrame.addTopic(t);
               else                                                   //Else 8.1.1.1
                 sharkStartFrame.mainFrame.removeTopic();
            }
            else {                                                     //Else 8.1.1
               if(t != null)                                         //Condition 8.1.1.1
                 sharkStartFrame.mainFrame.addTopic2(t);
               else                                                   //Else 8.1.1.1
                 sharkStartFrame.mainFrame.removeTopic2();
            }
         }
     }
     else if(sel.getLevel() == 2){
         sharkStartFrame.mainFrame.addGameTree();
         sharkStartFrame.mainFrame.addMarkGameCourses(sel.get());
     }
     else {                                                            //Else 8
        if(updating)                                                 //Condition 8.1
          sharkStartFrame.mainFrame.removeTopic();
        else
            sharkStartFrame.mainFrame.removeTopic2();                  //Else 8.1
     }
  }
  //-----------------------------------------------------
  public topic getCurrentTopic() {
     jnode sel = getSelectedNode();
     short i;
     if(sel==null)
       return null;
     String s = sel.get();
     String databaset,namet;
     if(s.length() > 0 && s.substring(0,1).equals(ISTOPIC)) {
        if((i=(short)s.indexOf(SEPARATOR)) > 0)  {
          databaset=s.substring(1,i);
          namet =  s.substring(i+1);
        }
        else {databaset=publicname; namet=s.substring(1);}
     }
     else if(s.length() > 0 && expandAll && sel.isLeaf()
         && (sel.getLevel()>1 || onlyOneDatabase != null)){
         databaset=publicname; namet=s;
     }
     else
       return null;
     topic ret = new topic(databaset,namet,null,null);
     ret.getSplits();
     ret.phonicscourse = u.phonicscourse(sel);
     if(ret.invalid) {
       // RB 20/1/06 start
       return null;
       // RB 20/1/06 end
     }

     if(ret.splitwords != null) {ret.alreadysplit = true;}
     return ret;
  }
  

  
  
  public String getCurrentTopicPath() {
     jnode sel = getSelectedNode();
     short i;
     if(sel == null ||  sel.isRoot()) {return null;}
     String s = sel.get();
     jnode node = (jnode)sel.getParent();
     while(node != null && node != root) {
        s = node.get() + String.valueOf(CSEPARATOR) + s;
        node = (jnode)node.getParent();
     }
     return s;
  }
  public String getCurrentTopicPath(jnode sel) {
     short i;
     if(sel == root) return null;
     String s = sel.get();
     jnode node = (jnode)sel.getParent();
     while(node != null && node != root) {
        s = node.get() + String.valueOf(CSEPARATOR) + s;
        node = (jnode)node.getParent();
     }
     return s;
  }
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public jnode setCurrentTopicPath(String path) {
    return setCurrentTopicPath(path, null);
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  public jnode setCurrentTopicPath(String path) {
  public jnode setCurrentTopicPath(String path, String startnode) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     short i;
     jnode node = root;
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//     if(!shark.production){
      if(startnode!=null&&!shark.production){
        jnode nn[] = root.getChildren();
        for (int k = 0; k < nn.length; k++) {
//         if (String.valueOf(nn[k].getUserObject()).equalsIgnoreCase("publictopics"))
          if (String.valueOf(nn[k].getUserObject()).equalsIgnoreCase(startnode))
            node = nn[k];
        }
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     String s[] = u.splitString(path,CSEPARATOR);
     int curr = 0;
     collapseAll();
     mainloop:  while(curr < s.length) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
            node = (jnode)e.nextElement();
//startPR2007-10-30^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            String nval;
//startPR2007-11-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(!shark.production)nval=node.get().replaceAll(topicTree.ISTOPIC, "");
//            if(startnode!=null&&!shark.production)nval=node.get().replaceAll(topicTree.ISTOPIC, "");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            else nval = node.get();
            nval = node.get();
            if(nval.startsWith( String.valueOf(ISTOPIC)))nval = nval.substring(1);
            if(s[curr].startsWith( String.valueOf(ISTOPIC)))s[curr] = s[curr].substring(1);
            if(s[curr].equals(nval)) {
//            if(s[curr].equals(node.get())) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if(curr == s.length-1) {
                   expandPath(new TreePath(node.getPath()));
                   return node;
                }
                else if(!node.isLeaf())
                              {++curr;  continue mainloop;}
            }
        }
        return null;
      }
      return null;
  }



  public void expandAtString(String searchs) {
     jnode ininode = (jnode)root.getChildAt(0);
     collapseAll();
     this.expandPath(new TreePath(root.getPath()));
     boolean foundone = false;
     loop1: for(int j = 0; j < ininode.getChildren().length; j++){
         jnode currnode = (jnode)ininode.getChildAt(j);
        for(Enumeration e = currnode.children();e.hasMoreElements();) {
            jnode node = (jnode)e.nextElement();
            String nval = node.get();
            int k = nval.indexOf('=')+1;
            if(k>0)nval = nval.substring(k);
            nval = nval.toLowerCase();
            searchs = searchs.toLowerCase();
            if(nval.indexOf(searchs)>=0) {
                   expandPath(new TreePath(currnode.getPath()));
                   foundone = true;
                   continue loop1;
            }
            else collapsePath(new TreePath(currnode.getPath()));
        }
     }
     if(!foundone)collapseAll();
  }


  public void rightClick() {
     if(lastSelection != null && !lastSelection.equals("")) {
        if(sharkStartFrame.mainFrame.currTopicView != null
           && sharkStartFrame.mainFrame.lastSelect
               == sharkStartFrame.mainFrame.currTopicView
           && lastSelectionType == db.TOPICTREE)
            sharkStartFrame.mainFrame.currTopicView.setTopics(lastSelection);
        else  {
           if(lastSelectionType ==   sharkStartFrame.mainFrame.topicTreeList.lastSelectionType) {
              sharkStartFrame.mainFrame.topicTreeList.addReference(lastSelection);
              sharkStartFrame.mainFrame.topicTreeList.newSelection();
           }
        }
     }
  }
  
  public String[] getAncestors(jnode ttnode, String stopAt){
        jnode parents[] =  getParents(ttnode);
        String topicname = ttnode.get();
        if(topicname.startsWith(topicTree.ISTOPIC))topicname = ttnode.get().substring(1);
        String path[] = new String[]{topicname};
        for(int i = 0; i < parents.length; i++){
        String s1 = parents[i].get();
            if(s1.equals(stopAt))break;
            path = u.addString(path, s1);
        }
        return path;
  }
  

  
  
  public String savePath(jnode nodex) {
     short i;
     if(nodex==null || nodex==root) return null;
     String s = nodex.get();
     jnode node = (jnode)nodex.getParent();
     while(node != null && !node.isRoot()) {
        s = node.get() + String.valueOf(CSEPARATOR) + s;
        node = (jnode)node.getParent();
     }
     return s;
  }

  public String savePathIncRoot(jnode nodex) {
     short i;
     if(nodex==null || nodex==root) return null;
     String s = nodex.get();
     if(s.startsWith( String.valueOf(ISTOPIC)))s = s.substring(1);
     jnode node = (jnode)nodex.getParent();
     while(node != null) {
        s = node.get() + String.valueOf(CSEPARATOR) + s;
        node = (jnode)node.getParent();
     }
     return s;
  }

  /*
  public jnode expandPath(String path) {
     short i;
     jnode node2 = root;
     if(path.startsWith("Barrow"))return null;
     String s[];
     s = u.splitString(path,CSEPARATOR);
     int curr = 0;
     jnode children[];
     mainloop:  while(curr < s.length) {
        loop1: for(Enumeration e = node2.children();e.hasMoreElements();) {
//         loop1:for(Enumeration e = node2.depthFirstEnumeration();e.hasMoreElements();) {
            jnode node = (jnode)e.nextElement();
            if(node.equals(node2))
                continue loop1;
            String nval = node.get();
            if(nval.startsWith( String.valueOf(ISTOPIC)))
                nval = nval.substring(1);
            if(s[curr].startsWith( String.valueOf(ISTOPIC)))
                s[curr] = s[curr].substring(1);
            if(s[curr].equals(nval)) {
                if(curr == s.length-1) {
                   expandPath(new TreePath(node.getPath()));
                   return node;
                }
                if(!node.isLeaf())
                              {
                                  ++curr;
                                  continue mainloop;
                              }
            }
         }
         return null;
      }
      return null;
  }
   */


    public jnode expandPath(String path) {
        return expandPath(null, path);
    }
  
  public jnode expandPath(jnode start, String path) {
     short i;
     jnode node = root;
     if(start!=null)node = start;
     String s[];
     s = u.splitString(path,CSEPARATOR);
     int curr = 0;
     jnode children[];
     mainloop:  while(curr < s.length) {
         for(Enumeration e = node.children();e.hasMoreElements();) {
            node = (jnode)e.nextElement();
            String nval = node.get();
            if(nval.startsWith( String.valueOf(ISTOPIC)))
                nval = nval.substring(1);
            if(s[curr].startsWith( String.valueOf(ISTOPIC)))
                s[curr] = s[curr].substring(1);
            if(s[curr].equals(nval)) {
                if(curr == s.length-1) {
                   expandPath(new TreePath(node.getPath()));
                   return node;
                }
                if(!node.isLeaf())
                              {
                                  ++curr;
                                  continue mainloop;
                              }
            }
         }
         return null;
      }
      return null;
  }

  //------------------------------------------------------------
  byte findType(jnode node) {
     jnode parent = node;
     if(onlyOneType != 0 ) return(onlyOneType);
     while(!parent.isRoot()) {
        parent = (jnode)parent.getParent();
        String s = parent.get();
        if(s.equals(GAMELISTNAME)) return  db.GAME;
        if(s.equals(GAMETREELISTNAME)) return  db.GAMETREE;
        if(s.equals(TEXTLISTNAME)) return  db.TEXT;
        if(s.equals(TOPICLISTNAME)) return  db.TOPIC;

     }
     return db.TOPICTREE;
  }
  //-------------------------------------------------------------
  public void addReference(String value) {
     jnode sel = getSelectedNode();
     if(sel == null || !sel.isLeaf() || sel.getLevel() < 2
           || ((jnode)sel.getParent()).get().equals(TOPICLISTNAME)) return;
     saveOldValueForUndo(sel);
     recordChange(sel);
     if(value.substring(0,1).equals(ISPATH)) {
             short j = (short)(value.substring(1).indexOf(SEPARATOR) + 1);
             if(j>0 && value.substring(1,j).equals(publicname))
                set(sel,ISPATH+value.substring(j));
             else set(sel,value);
     }
     else  {     // must be topic
             short j = (short)(value.substring(1).indexOf(SEPARATOR) + 1);
             if(j>0 && value.substring(1,j).equals(publicname)) {
                set(sel,ISTOPIC+value.substring(j+1));
             }
             else set(sel,value);
     }
     addEmpty(root);
  }
  //--------------------------------------------------------------
  public boolean includesDatabase(String name) {
     for(short i=0;i<dblist.length;++i)
        if((new File(dblist[i])).getName().equalsIgnoreCase(name)) return true;
    return false;
  }
  /**
   * Returns the database that the node passed belongs to
   * @param node1 The node that needs placing
   * @return Name of object(database) which the node belongs to
   */
  String findDatabase(jnode node1) {
     jnode parent,node=node1;
     if(onlyOneDatabase != null) return(onlyOneDatabase);
     if(node==root) return null;
     return((String)((jnode)node.getPath()[1]).getUserObject());
  }
  //-----------------------------------------------------
   public void endedit(TreeModelEvent e) {
      jnode pos;
      int i;
      if(copying) return;
      pos = getSelectedNode();
      if(pos != null) {
        if(onlyOneType == db.WAV) {
           String newSelection = pos.get();
           if((i=newSelection.indexOf(".."))>=0) {
             while ( (i = newSelection.indexOf("..")) >= 0) {
               newSelection = newSelection.substring(0, i) + u.phonicsplits + newSelection.substring(i+2);
             }
             pos.setUserObject(newSelection);
           }
           String db1 = findDatabase(pos);
           db.rename(db1,lastSelection,newSelection,db.WAV);
           lastSelection = newSelection;
           return;
        }
        if(onlyOneType == db.IMAGE ) {
           String newSelection = pos.get();
           if(sharkStartFrame.mainFrame.newPicture(findDatabase(pos),
                                                   lastSelection, newSelection,pos)) {
              lastSelection = newSelection;
           }
           addEmpty(root);
           return;
        }
         recordChange(pos);
         int oldchildren = pos.getChildCount();
         addEmpty(root);
         if(oldchildren==0 && pos.getChildCount()==1) {
             setSelectionPath(new TreePath(((jnode)pos.getChildAt(0)).getPath()));
         }
         expandPath(new TreePath(pos.getPath()));
      }
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      if(!publicname.equals(sharkStartFrame.publicMarkGamesLib)&&
              !publicname.equals(sharkStartFrame.publicMarkGamesFormulaLib)&&
              !publicname.equals(sharkStartFrame.publicMarkGamesCoursesLib[0])
              )
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
        sharkStartFrame.mainFrame.removeTopic();
      if(publicname.equals(sharkStartFrame.publicMarkGamesCoursesLib[0])){
          if(pos != null){
              if(pos.ischildof(topicTree.MARKGAMESSEQUENCES)){
              jnode codeNodes = sharkStartFrame.mainFrame.markgamescoursetree.find(topicTree.MARKGAMESCODES);
              String codes[] = new String[0];
              jnode jns[] = codeNodes.getChildren();
              for(int ii = 0; ii < jns.length; ii++){
                  String s = jns[ii].get();
                  if(s.indexOf("=")>=0){
                    String s2 = s.substring(0, s.indexOf("="));
                    codes = u.addString(codes, s2);                      
                  }
              }
              String spos = pos.get();
              if(spos.indexOf("=")>=0){
                String sseq = spos.substring(spos.indexOf("=")+1);
                if(!sseq.trim().equals("")){
                    String sseq2[] = u.splitString(sseq, ",");
                    for(int ii = 0; ii < sseq2.length; ii++){
                        String s = sseq2[ii];
                        if(s.trim()!="" &&u.findString(codes, s) < 0){
                            u.okmess(shark.programName, "Code does not exist", sharkStartFrame.mainFrame);
                        }
                    }
                }
              }
              codeNodes = sharkStartFrame.mainFrame.markgamescoursetree.find(topicTree.MARKGAMESSEQUENCES);
              jns = codeNodes.getChildren();
              if(spos.indexOf("=")>=0){
                String sseq = spos.substring(0, spos.indexOf("="));
                for(int ii = 0; ii < jns.length; ii++){
                    String s = jns[ii].get();
                    if(s.indexOf("=")>=0){
                        String sseq2 = s.substring(0, s.indexOf("="));
                        if(!jns[ii].equals(pos) && sseq2.equals(sseq)){
                            u.okmess(shark.programName, "Duplicate sequence name", sharkStartFrame.mainFrame);
                        }
                    }
                }                    
              }
              else if (!pos.get().trim().equals("")){
                  u.okmess(shark.programName, "Misformed sequence", sharkStartFrame.mainFrame);
              }
          }
          if(pos.ischildof(topicTree.MARKGAMESCODES)){
              String spos = pos.get();
              if(spos.indexOf("=")>=0){
                jnode codeNodes = sharkStartFrame.mainFrame.markgamescoursetree.find(topicTree.MARKGAMESCODES);
                jnode jns[] = codeNodes.getChildren();
                String sseq = spos.substring(0, spos.indexOf("="));
                for(int ii = 0; ii < jns.length; ii++){
                    String s = jns[ii].get();
                    if(s.indexOf("=")>=0){
                        String sseq2 = s.substring(0, s.indexOf("="));
                        if(!jns[ii].equals(pos) && sseq2.equals(sseq)){
                            u.okmess(shark.programName, "Duplicate code name", sharkStartFrame.mainFrame);
                        }
                    }
                } 
              }              
          }
          }
      }
      if(pos != root && pos.getParent() == root) {} //check_db();
      else saveOldValueForUndo(pos);
   }
   //----------------------------------------------------
   boolean canRemove(jnode node) {
      if(node == root) return false;
      jnode parent =  (jnode)node.getParent();
      String s = node.get();
      if(parent.get().equals(TOPICLISTNAME))  {
         if(u.yesnomess(
                    "Deleting topic",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            "Do you want to delete " + s + " ?   Caution - this cannot be undone")) {
                 "Do you want to delete " + s + " ?   Caution - this cannot be undone", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            db.delete(findDatabase(parent),s,db.TOPIC);
            clearundo();
            model.removeNodeFromParent(node);
         }
         return false; // we have already done it
      }
      if(onlyOneType == db.IMAGE && node.getLevel() == 2)  {
         if(u.yesnomess(
                    "Deleting image",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "Do you want to delete " + s + " ?   Caution - this cannot be undone")) {
                                 "Do you want to delete " + s + " ?   Caution - this cannot be undone", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            sharkStartFrame.mainFrame.cancelPicture();
            db.delete(findDatabase(parent),s,db.IMAGE);
            clearundo();
            model.removeNodeFromParent(node);
         }
         return false; // we have already done it
      }
      if(onlyOneType == db.WAV && node.getLevel() == 2)  {
         if(u.yesnomess(
                    "Deleting speech item",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "Do you want to delete " + s + " ?   Caution - this cannot be undone")) {
               "Do you want to delete " + s + " ?   Caution - this cannot be undone", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            db.delete(findDatabase(parent),s,db.WAV);
            clearundo();
            model.removeNodeFromParent(node);
         }
         return false; // we have already done it
      }
       else return(super.canRemove(node));
   }
   void afterRemove() {/*check_db();*/}
  //----------------------------------------------------
   boolean canCut(jnode node) {
      return(isEditable() && node.getLevel() > 1);
   }
 //----------------------------------------------------
   boolean canCopy(jnode node) {
      return( node != root && (node.getParent() != root || onlyOneType != 0));
   }
 //----------------------------------------------------
   boolean canPaste(jnode node) {
      return(isEditable() && node != root && (node.getParent() != root || onlyOneType != 0));
   }
   //----------------------------------------------------
   void check_db() {
      jnode c[] = root.getChildren();
      String newdb[];
      short oldnum = (short)dblist.length,newnum=0, i, j;
      boolean failed = false;

      for(i = 0;  i<c.length; ++i) {
         if(!c[i].get().equals(sharkStartFrame.publicPathplus)) ++newnum;
      }
      newdb = new String[newnum];
      for(i = 0,j=0;  i<c.length; ++i) {
         String s = c[i].get();
         if(!s.equals(sharkStartFrame.publicPathplus)) newdb[j++] = s;
      }
      if(newnum == oldnum) {     // rename---------------------
         for(i = 0; i < oldnum; ++i) {
            if(!newdb[i].equalsIgnoreCase(dblist[i])) {
               if(!u.yesnomess(
                    "Renaming database",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "Do you want to rename " + dblist[i]+" to "+newdb[i]+" ?")) {
                               "Do you want to rename " + dblist[i]+" to "+newdb[i]+" ?", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  failed = true;
               }
               else {
                  File f = new File(newdb[i] + ".sha");
                  File oldfile = new File(dblist[i] + ".sha");
                  db.closeAll();
                  if(oldfile.renameTo(f) == false)  {
                     failed = true;
                  }
                  else dblist[i] = newdb[i];
               }
               if(failed) {
                  boolean res = u.yesnomess(
                         "Rename failed or was cancelled",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                         "Rename failed - reset it as it was before?");
                                        "Rename failed - reset it as it was before?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  for(j=0;  j<c.length; ++j) {
                     String s = c[j].get();
                     if(s == newdb[i]) {
                        if(res)
                           set(c[j],dblist[i]);
                        else startEditingAtPath(new TreePath(c[j].getPath()));
                        break;
                     }
                  }
               }
            }
         }
      }
      else if(newnum > oldnum) {   //------------insertion
         for(i = 0; i < oldnum; ++i) if(!newdb[i].equalsIgnoreCase(dblist[i])) break;
         if(!u.yesnomess(
                 "New database",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                 "Do you want to create a new database called " + newdb[i]+" ?")) {
                           "Do you want to create a new database called " + newdb[i]+" ?", sharkStartFrame.mainFrame)) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            failed = true;
         }
         else {
            File f = new File(newdb[i] + ".sha");
            clearundo();
            if (f.exists() || db.get(newdb[i],true) == null) {
               failed = true;
            }
            else {
               dblist = newdb;
               addChild(c[i],TOPICLISTNAME);
               addChild(c[i],GAMELISTNAME);
               addChild(c[i],GAMETREELISTNAME);
//               addChild(c[i],TEXTLISTNAME);
               addEmpty(root);
            }
         }
         if(failed) {
            boolean res = u.yesnomess(
                         "Failed to set up new database",
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                         "No good - cancel it ?");
                     "No good - cancel it ?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            for(j=0;  j<c.length; ++j) {
               String s = c[j].get();
               if(s == newdb[i]) {
                   if(res)
                       model.removeNodeFromParent(c[j]);
                   else startEditingAtPath(new TreePath(c[j].getPath()));
                   break;
               }
            }
         }
      }
      else  {   //------------deletion
         for(i = 0; i < newnum; ++i) {
            if(!newdb[i].equalsIgnoreCase(dblist[i])) break;
         }
         File f = new File(dblist[i] + ".sha");
         db.closeAll();
         f.delete();
         dblist = newdb;
         clearundo();
      }
   }
  //-----------------------------------------------------------
  void addEmpty(jnode node) {
     copying = true;
     boolean intopiclist  = (node.get().equals(TOPICLISTNAME));
     if(!node.isLeaf()) {
        if(!intopiclist) {
           jnode c[] = node.getChildren();
           for(short i=0;i<c.length;++i) {
              if(canAddChild(c[i])) addEmpty(c[i]);
           }
        }
        if(node != root || distribute)  addEmptyNode(node);
     }
     else {
         addEmptyNode(node);
     }
     copying=false;
  }
  void addEmptyNode(jnode node) {
     String s;
     if(!node.isLeaf()) {
         if ((s = (String)((jnode)node.getLastChild()).get()).length() ==0) return;
         if(s.equals(GAMETREELISTNAME)) return;
     }
     if(canAddChild(node)){
         jnode newnode=(addChild(node,""));
     }
  }
  //---------------------------------------------------------
  boolean canAddChild(jnode node)  {
     String s = node.get();
     boolean ret = true;

     if(s.equals("")) ret = false;
     else if(s.equals(sharkStartFrame.publicPathplus)) ret = false;
     else if(s.substring(0,1).equals(ISTOPIC) || s.substring(0,1).equals(ISPATH))
                           ret = false;
     else if(findType(node)==db.GAME && node.getLevel() > 3) return false;
     else if(findType(node)==db.TEXT && node.getLevel() > 2) return false;
     else if(findType(node)==db.IMAGE&& node.getLevel() > 1) return false;
     else if(findType(node)==db.WAV && node.getLevel() > 1) return false;
     return ret;
  }

  public String getCourse(String s){
      if(s==null) return null;
         int j = s.indexOf(topicTree.CSEPARATOR);
         if(j<0) return null;
         return s.substring(0, j);
   }
  //-----------------------------------------------------
   public jnode getNode(String s) {    // start search
      return getNode(s, root) ;
   }
  //----------------------------------- continue search
   public jnode getNode(String s, jnode node) {
      int pos = s.substring(0).indexOf(SEPARATOR);
      String ss = (pos < 0) ? s : s.substring(0,pos);

      if(!node.isLeaf()) {
         jnode children[] = node.getChildren();
         for(short i = 0; i < children.length; ++i) {
            if(children[i].get().equals(ss)) {
               if(pos >= 0) return(getNode(s.substring(pos+1),children[i]));
               else return children[i];
            }
         }
      }
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      u.okmess("Invalid path","Cannot find " + ss);
        u.okmess("Invalid path","Cannot find " + ss, sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      return null;
   }
  //-----------------------------------------------------  // start rb 1/2/08
   public jnode findNode(String s) {    // start search for any node with given name
      return findNode(s, root) ;
   }
  //----------------------------------- continue search for any node with given name
   public jnode findNode(String s, jnode node) {
      jnode newnode;
      if(node.get().equals(s)) return node;
      if(!node.isLeaf()) {
         jnode children[] = node.getChildren();
         for(short i = 0; i < children.length; ++i) {
            if(children[i].get().equals(s))  return children[i];
            else  if((newnode = findNode(s, children[i])) != null) return newnode;
         }
      }
      return null;
   }                                                     // end rb 1/2/08
  //-----------------------------------------------------
   public jnode findTopic(String s) {    // start search
      return findTopic(s, root, false) ;
   }

   public jnode findTopic(String s, boolean insistleaf) {    // start search
      return findTopic(s, root, insistleaf) ;
   }
  //----------------------------------- continue search
   public jnode findTopic(String s, jnode node, boolean insistleaf) {
      if(s == null || s.length()==0 || node==null) return null;
      if(!node.isLeaf()) {
         jnode children[] = node.getChildren(), node1;
         for(short i = 0; i < children.length; ++i) {
            if(children[i].get().equalsIgnoreCase(s) && (!insistleaf || children[i].isLeaf()))
               return children[i];
            else if((node1=findTopic(s,children[i], insistleaf)) != null) return node1;
         }
      }
      return null;
   }
   //---------------------------------- get all topics in node
   public topic[] getTopics(jnode node) {
      topic top[] = new topic[0], newtop;
      String list[] = nodeList(node);
      for(short i=0;i<list.length;++i) {
          String s =  list[i];
          if(s.length() > 0 && !s.substring(0,1).equals(ISPATH)) {
            if (s.substring(0,1).equals(ISTOPIC))
               s = s.substring(1);
            int pos2 =  s.indexOf(SEPARATOR);
            if(pos2>0) newtop = new topic(s.substring(0,pos2),
                               s.substring(pos2+1),null,null);
            else newtop = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),
                               s,null,null);
            if(newtop != null) top = u.addTopic(top,newtop);
          }
      }
      return top;
   }
   
  // 0 fast mode ok
  // -1 - not an appropriate course
  // -2 - not enough topics under heading
  // -3 - contains a notmixedwords list
  // -4 - in set work
   public int isNodeFastModeable(jnode node, String course) {
      if(Demo_base.isDemo)return (u.findString(Demo_base.phonicstopics, node.get())>=0)?-1:0;
      if(sharkStartFrame.wantplayprogram)return -4;
      if(u.findString(u.nonfastmodecourses, course)>=0) return -1;
      if(u.findString((db.listsort(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]), db.TOPICTREE)), course)<0) return -1;
      if(!node.isLeaf()){
          int count = 0;
          topic supert[] = new topic[0];
          topic t[] = getTopics(node);
          for (int i = 0; i < t.length; ++i) {
             if (t[i] != null && !t[i].topicinlist(supert) && !t[i].superlist) {
                t[i].getWords(null,false);  // complete initialization
                if(t[i].notmixedwords)
                    return -3;
                if((!(t[i].phonics && !t[i].phonicsw)) && !t[i].revision){
                    supert = u.addTopic(supert, t[i]);
                    count++;
                }
             }
          }
          if(count<2)
              return -2;
      }
      return 0;
   }
   
   
   //---------- get topics from string path starting with db name
   public static topic[] getTopics(String s) {
      int pos = s.indexOf(SEPARATOR)+1;
      String ss = (pos < 0) ? s : s.substring(0,pos);
      topicTree tt = new topicTree();
      tt.expandAll = true;
      tt.onlyOneType = db.TOPICTREE;
      tt.set(tt.root,s);
      tt.expandNode(tt.root,new String[0]);
      String list[] = tt.nodeList(tt.root);
      topic top[] = new topic[list.length];
      for(short i=0;i<list.length;++i) {
         int pos2 = list[i].indexOf(SEPARATOR);
         if(pos2>0) top[i] = new topic(list[i].substring(0,pos2),
                               list[i].substring(pos2+1),null,null);
         else top[i] = new topic(u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]),list[i],null,null);
      }
      return(top);
   }
   //---------- get games from string path starting with db name
   public static String[] getGames(String s) {
      int pos = s.indexOf(SEPARATOR)+1;
      String ss = (pos < 0) ? s : s.substring(0,pos);
      topicTree tt = new topicTree();
      tt.expandAll = true;
      tt.onlyOneType = db.GAMETREE;
      tt.set(tt.root,s);
      tt.expandNode(tt.root,new String[0]);
      return(tt.nodeList(tt.root));
   }
   //----------------return node as string path
   public String getPath(jnode node) {
      String s = node.get(), ps;
      jnode node2,parent;
      short i;
      if(node==root
         || s.equals("")
         || (parent= (jnode)node.getParent()) == root) return null;
      ps = parent.get();
      if(ps.equals(TOPICLISTNAME))
         return ISTOPIC + ((jnode)parent.getParent()).get() + SEPARATOR + s;
      if(ps.equals(GAMELISTNAME))
         return ISTOPIC + ((jnode)parent.getParent()).get() + SEPARATOR + s;
      if(ps.equals(TEXTLISTNAME))
         return ISTOPIC + ((jnode)parent.getParent()).get() + SEPARATOR + s;
      i = (short)s.substring(1).indexOf(SEPARATOR);
      if(s.substring(0,1).equals(ISTOPIC)) {
         if(i < 0)
            return ISTOPIC + publicname + SEPARATOR + s.substring(1);
         else  return s;
      }
      else if(s.substring(0,1).equals(ISPATH)) return s;
      else {
         for(; parent != root; parent = (jnode)parent.getParent()) {
           String ss = parent.get();
           if(!ss.equals(GAMETREELISTNAME))
               s = ss + SEPARATOR + s;
         }
         return ISPATH + s;
      }
   }
   //----------------return node as string path plus database from dbname
   public String getPath2(jnode node) {
     jnode nn=node;
      while(nn.getLevel()>1 ) nn = (jnode)nn.getParent();
      int pos = nn.getPosition();
      if(pos>=dbnames.length) {  // special for 'all topics' in student program
        String s = getPath(node);
        return ISPATH+publictopics+ISPATH + s.substring(s.indexOf(SEPARATOR,1)+1);
      }
      String database = dbnames[pos];
      if(node.getLevel()<2) return ISPATH+database+ISPATH+node.get();
      return ISPATH+database+getPath(node);
   }
   //---------------------copy and expand a node
   public void expandNode(jnode node,String[] alreadyhad) {
      String s = node.get(),newdatabase,ss;

      short pos;
      saveTree1 tt;
      if(s.substring(0,1).equals(ISTOPIC)) {
         if((pos = (short)s.indexOf(SEPARATOR)) < 0 ) {
            if(isGameTree || expandAll) set(node,s.substring(1));
         }
         else if(s.substring(1,pos).equals(publicname)) {
            if(isGameTree || expandAll) set(node,s.substring(pos+1));
            else set(node,ISTOPIC+ s.substring(pos+1));
         }
         return;
      }
      if(node != root && node.getParent() != root
             && (!expandAll || !s.substring(0,1).equals(ISPATH))) return;
      if(s.substring(0,1).equals(ISPATH)) ss = s.substring(1);
      else ss = s;
      pos = (short)ss.indexOf(SEPARATOR);
      if(pos<0) {
         newdatabase = ss; ss= null;
      }
      else if(pos==0) {newdatabase = publicname; ss = ss.substring(1);}
      else {newdatabase = ss.substring(0,pos); ss = ss.substring(pos+1);}
             // first part must be database name
      if(isAbsoluteDb)
          newdatabase = publicname;
      if(db.exists(newdatabase)) {
         if(ss == null) {
            byte wanttype = (onlyOneType == 0 || onlyOneType==db.TOPIC|| onlyOneType==db.TOPICTREE)?db.TOPICTREE:onlyOneType;
            jnode newnode = null;
            String tlist[] = db.listsort(newdatabase,wanttype);
            if(onlyOneType != db.GAME && onlyOneType != db.TEXT)
//startPR2008-01-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            {
              boolean firstnode = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              String last = null;
              for(short i=0;i<tlist.length;++i) {
//startPR2010-05-24^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                if(onlyshowcourse!=null && !onlyshowcourse.equals(tlist[i]))
//                    continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  if((onlyOneType==db.TOPICTREE
//                     && u.findString(sharkStartFrame.courses,tlist[i]) >= 0 )) continue;
                if (onlyOneType == db.TOPICTREE
                    &&((tlist[i].trim().equals("") && firstnode) ||
                       u.findString(sharkStartFrame.courses, tlist[i]) >= 0))continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                if((dontwant!=null && u.findString(dontwant,tlist[i])>=0)
                   || (onlywant!=null && !tlist[i].equalsIgnoreCase(onlywant)))
                  continue;
                if(wanttype == db.IMAGE) {
                  addChild(node,tlist[i]);
                }
                else if(wanttype == db.WAV) {
                  addChild(node,tlist[i]);
                }
                else if(wanttype == db.TEXT) {
                  newnode = addChild(node,tlist[i]);
                  scanForExpand(newnode,alreadyhad);
                }
                else {
                  if(last!= null && last.equals("") && tlist[i].trim().equals(""))
                        continue;
                  last = tlist[i].trim();
                  tt = (saveTree1)db.find(newdatabase,tlist[i],wanttype);
                  if(node == root || node.getParent() == root) {
                    if(onlyOneDatabase!=null && onlyOneType != db.GAMETREE ) {
                      if(newnode==null) newnode=node;
                      else newnode = addChild(root,"");
                    }
                    else newnode = addChild(node,"");
                  }
                  else {
                      newnode = node;
                  }
                  tt.addToTree(this,newnode);
                  if(expandAll) scanForExpand(newnode,alreadyhad);
                }
//startPR2008-01-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                firstnode = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              }
//startPR2008-01-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
          else {
            short pos2 = (short)ss.indexOf(SEPARATOR);
            String ttname;
            if(pos2 < 0) { ttname = ss; ss = null;}
            else          { ttname = ss.substring(0,pos2); ss = ss.substring(pos2+1);}
            if(findType(node)==db.GAMETREE) {
               tt = (saveTree1)db.find(newdatabase,ttname,db.GAMETREE);
               if(tt == null) {
                 set(node,"<missing game tree "+ttname+">");
                 return;
               }
            }
            else {
               tt = (saveTree1)db.find(newdatabase,ttname,db.TOPICTREE);
               if(tt == null) {
                 set(node,"<missing topic tree "+ttname+">");
                 return;
               }
            }
            if(ss == null)    // element(s) specified
                 tt.addToTree(this, node);
            else  tt.addToTree(this,node,ss);
            if(expandAll) scanForExpand(node,alreadyhad);
         }
         if(pos <= 0) {
           if(onlyOneType == 0 || onlyOneType==db.TOPIC)
                   addTopicList(newdatabase,node);
           if(onlyOneType == 0 || onlyOneType==db.GAME)
                   addGames(newdatabase,node);
           if(onlyOneType == 0 || onlyOneType==db.GAME)
                   addGameTrees(newdatabase,node);
           if(onlyOneType == 0 || onlyOneType==db.TEXT)
                   addText(newdatabase,node);
         }
      }
      else {       // file not found
         if(!distribute)set(node,"<missing database " + newdatabase + ">");
         else set(node,"");
      }
   }
   //--------------------------------------------------------
   void addTopicList(String ss, jnode node) {
      jnode node2 = (onlyOneType==0 || onlyOneType == db.TOPIC)?addChild(node,TOPICLISTNAME):node;
      db db1 = db.get(ss,false);
      if(db1==null)
        return;
      for(short i=0;  i<db1.indexTot; ++i) {
         if(db1.index[i].type == db.TOPIC) {
            addChild(node2,db1.index[i].name);
         }
      }
   }
   //--------------------------------------------------------
   public void refreshTopicList(String dbname) {
      jnode node = this.getNode(dbname);
      if(node == null) return;
      jnode node2 = this.getNode(TOPICLISTNAME,node);
      db db1 = db.get(dbname,false);
      removeAllChildren(node2);
      for(short i=0;  i<db1.indexTot; ++i) {
         if(db1.index[i].type == db.TOPIC) {
            addChild(node2,db1.index[i].name);
         }
      }
   }
   //--------------------------------------------------------
   void addGames(String ss, jnode node) {
      jnode node2 = (onlyOneType==0 || onlyOneType==db.GAME)?addChild(node,GAMELISTNAME):node, node3;
      String list[] = db.list(ss,db.GAME);
      for(short i=0;  i<list.length; ++i) {
            node3 = addChild(node2,list[i]);
            String[]  s = (String[]) db.find(ss, list[i],db.GAME);
            if(s!=null) for(short j=0;j<s.length;++j) {
               addChild(node3,s[j]);
            }
      }
   }
   //--------------------------------------------------------
   void addText(String ss, jnode node) {
      String list[] = db.list(ss,db.TEXT);
      for(short i=0;  i<list.length; ++i) {
            jnode node3 = addChild(node,list[i]);
            Object o = db.find(ss, list[i],db.TEXT);
            String[]  s = null;
            if(o instanceof String[]){
                s = (String[]) db.find(ss, list[i],db.TEXT);
            }
            if(s!=null) for(short j=0;j<s.length;++j) {
               addChild(node3,s[j]);
            }
      }
   }
   //--------------------------------------------------------
   void saveGames(String ss, jnode node) {
      jnode c[] = node.getChildren();
      short i;
      db db1 = db.get(ss,false);
      deleteUnwanted(db1,c,db.GAME);
      for(i=0;  i<c.length; ++i) {
         if(!c[i].equals("") && !c[i].isLeaf()) {
            jnode c2[] = c[i].getChildren();
            short tot=0;
            for(short j=0;j<c2.length;++j) {
               if(!c2[j].get().equals("")) ++tot;
            }
            String[]  s = new String[tot];
            short k = 0;
            for(short j=0;j<c2.length;++j) {
                if(!c2[j].get().equals(""))
                         s[k++] = c2[j].get();
            }
            db1.update(c[i].get(),s,db.GAME);
         }
      }
   }
   //--------------------------------------------------------
   void saveText(String ss, jnode node) {
      jnode c[] = node.getChildren();
      short i;
      String sss;
      db db1 = db.get(ss,false);
      deleteUnwanted(db1,c,db.TEXT);
      for(i=0;  i<c.length; ++i) {
         if(!c[i].equals("") && !c[i].isLeaf()) {
            jnode c2[] = c[i].getChildren();
            short tot=0;
            for(short j=0;j<c2.length;++j) {
               if(!c2[j].get().equals("")) ++tot;
            }
            String[]  s = new String[tot];
            short k = 0;
            for(short j=0;j<c2.length;++j) {
                if(!(sss=c2[j].get()).equals(""))
                         s[k++] = sss;
            }
            db1.update(c[i].get(),s,db.TEXT);
         }
      }
   }
   //--------------------------------------------------------
   void addGameTrees(String ss, jnode node) {
      jnode node2 = addChild(node,GAMETREELISTNAME),node3;
      saveTree1 tt;
      String[] tlist = db.listsort(ss,db.GAMETREE);
      db db1 = db.get(ss,false);
      for(short i=0;  i<tlist.length ; ++i) {
            node3 = addChild(node2,tlist[i]);
            tt = (saveTree1)db1.find(tlist[i],db.GAMETREE);
            if(tt != null) tt.addToTree(this,node3);
      }
   }
   //--------------------------------------------------------
   void saveGameTrees(String ss, jnode node) {
      jnode c[] = node.getChildren();
      db db1 = db.get(ss,false);
      short i;
      String na;
      String order[] = new String[0];
      deleteUnwanted(db1,c,db.GAMETREE);
      for(i=0;  i<c.length; ++i) {
         if(!c[i].get().equals("")) {
            saveTree1.saveTree2 tt = (new saveTree1(this,c[i])).curr;
            na = c[i].get();
            if(na.length()>0) {
              db1.update(na = c[i].get(), tt, db.GAMETREE);
              order = u.addString(order, na);
            }
         }
      }
      db1.update("gorder", order, db.TEXT);
   }
   //---------------------copy and expand a node
   public void scanForExpand(jnode node,String alreadyHad[]) {
      short i;
      if(!node.isLeaf()) {   // node already has children
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
            scanForExpand(c,alreadyHad);
         }
      }    // leaf - expand if dataset name or part of another tree
      else {
         String s = node.get();
         if(s.substring(0,1).equals(ISTOPIC)){
               i = (short)(s.substring(1).indexOf(SEPARATOR) + 1);
               if(i<=0) {
                  if(isGameTree||expandAll) set(node,s.substring(1));
               }
               else if(s.substring(1,i).equals(publicname)) {
                  if(isGameTree || expandAll) set(node,s.substring(i+1));
                  else set(node, ISTOPIC + s.substring(i+1));
               }
         }
         else if(s.substring(0,1).equals(ISPATH)) {
            for(i = 0; i<alreadyHad.length; ++i) {
               if(alreadyHad[i].equals(s)) {
                  set(node,"<recursive reference>");
                  return;
               }
            }
            expandNode(node,u.addString(alreadyHad,s));
         }
      }
   }
   public String[] nodeList(jnode node) {
      if(!node.isLeaf()) {
         String s[] = new String[0];
         jnode c[] = node.getChildren();
         for(short i = 0; i < c.length; ++i) {
            s = u.addString(s,nodeList(c[i]));
         }
         return(s);
      }
      else {
         String s = node.get();
         if(s.length() == 0)  return new String[0];
         if(s.substring(0,1).equals(ISTOPIC)) {
            int pos = s.indexOf(SEPARATOR);
            if(pos>=0) return new String[] {s.substring(1)};
            else       return new String[] {publicname+SEPARATOR+s.substring(1)};
         }
         else if(s.substring(0,1).equals(ISPATH)) return new String[0];
         else return new String[]{s};
      }
   }
    //----------------------------------------------
   void recordChange(jnode nodein) {
      jnode node=nodein,parent;
      if(node == null) return;
      if(node != root) for(; (parent = (jnode)node.getParent()) != root;
            node = parent);
      String s = node.get() + SEPARATOR;
      if(dbchanged.indexOf(s) < 0)
              dbchanged = new String(dbchanged + s);
   }
   //-------------------------------------------------------
   short countTopicRefs(topic tt,jnode node) {
      short ret = 0;
      if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
                     ret += countTopicRefs(tt,c);
        }
      }
      else {
         String s = node.get();
         short i = (short)(s.substring(1).indexOf(SEPARATOR)+1);
         if(s.substring(0,1).equals(ISTOPIC)){
            if(i<=0) {
               if(s.substring(1).equals(tt.name)
                    && tt.databaseName.equals(publicname))
                       ret=1;
            }
            else {
              if(s.substring(1,i).equals(tt.databaseName)
               && s.substring(i+1).equals(tt.name)) ret = 1;
            }
         }
         else if(((jnode)node.getParent()).get().equals(TOPICLISTNAME)) {
             if(s.equals(tt.name)
                  && tt.databaseName.equals(findDatabase(node)))
                       ret=1;
         }
      }
      return(ret);
   }
  //-------------------------------------------------------
   public short fixTopicRefs(topic tt,String newname) {
      return fixTopicRefs(tt,root,newname);
   }
  //-------------------------------------------------------
   short fixTopicRefs(topic tt,jnode node,String newname) {
      short ret = 0;
      copying = true;
      if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           ret += fixTopicRefs(tt,c,newname);
        }
      }
      else {
         String s = node.get();
         if(s.length() < 2 ) return 0;
         short i = (short)(s.substring(1).indexOf(SEPARATOR) + 1);
         if(s.substring(0,1).equals(ISTOPIC)){
            if(i<=0) {
               if(s.substring(1).equals(tt.name)
                    && tt.databaseName.equals(publicname)) {
                  set(node,ISTOPIC+newname);
                  recordChange(node);
                  ret = 1;
               }
            }
            else if(s.substring(1,i).equals(tt.databaseName)
               && s.substring(i+1).equals(tt.name)) {
              set(node, ISTOPIC + tt.databaseName+SEPARATOR+newname);
              recordChange(node);
              ret = 1;
            }
         }
         else if(((jnode)node.getParent()).get().equals(TOPICLISTNAME)) {
             if(s.equals(tt.name)
                  && tt.databaseName.equals(findDatabase(node))) {
                 set(node, newname);
                 recordChange(node);
                 ret=1;
             }
         }
      }
      copying=false;
      return(ret);
   }
   void clearundo() {
      Arrays.fill(undoList,null);
      Arrays.fill(undoNode,null);
      undocurr=0;
   }
  public byte type(jnode node) {
     jnode parent = (jnode)node.getParent();
     int level = node.getLevel();
//     if(level == 0) return ROOT;
     if(level == 1 && onlyOneDatabase != null)  {

     }
     return 0;
  }
  void stopatlevel2(String addmessage) {
    int i,j;
 //   ((javax.swing.plaf.metal.MetalTreeUI)(this.getUI())).setCollapsedIcon(null);
    jnode jj[] = root.getChildren();
    for (i=0;i<jj.length;++i) {
      jj[i].dontcollapse= true;
//      jnode kk[] = jj[i].getChildren();                                    //  rb 16/3/08  mmmm start
//      for (j=0;j<kk.length;++j) {
//         kk[j].dontexpand = true;
//         if(!kk[j].isLeaf()) kk[j].setUserObject(kk[j].get()+addmessage);
//      }                                                                     //  rb 16/3/08  mmmm end
    }
  }
  void setmixed(jnode node, String addmessage) {                             //  rb 16/3/08  mmmm start
         node.dontexpand = true;
         node.setUserObject(node.get()+" "+addmessage);
  }                                                                          //  rb 16/3/08  mmmm end
//startPR2008-09-26^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public jnode getMixedParent(jnode jn){
    if(jn==null)return null;
    while((jn=(jnode)jn.getParent())!=null){
      if(jn.dontexpand)return jn;
    }
    return null;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  
  
  

  public boolean addListItem(jnode jn, String s, String splitStrChar) {
    String name = jn.get();
    int k;
    if((k=name.indexOf(splitStrChar))>=0){
        String prefix = name.substring(0, k+1);
        String suffix = name.substring(k+1);
        if(suffix.trim().length()>0){
            String ss[] = u.splitString(suffix, ",");
            if(u.findString(ss, s)>=0)return false;
            ss = u.addString(ss, s);
            jn.setUserObject(prefix+u.combineString(ss, ","));
        }
        else{
            jn.setUserObject(prefix+s);
        }
        model.nodeChanged(jn);    
        recordChange(jn);
        this.expandPath(new TreePath(jn.getPath()));
        return true;
    }
    return false; 
   } 
  
}
