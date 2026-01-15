package shark;

import java.util.*;

import javax.swing.tree.*;

public class gamestoplay extends topicTree {
  boolean already;
  String types[];
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//  static String treeheading  = u.gettext("showallgames", "treeheading");
//  static String treeheading2  = u.gettext("showallgames", "treeheading2");
  static String treeheading;
  static String treeheading2;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  static int categories[] = shark.language.equals(shark.LANGUAGE_EN)?new int[]{0,1,2,3}:new int[]{0,1,2};
  boolean overrideSuperSpelling = false;
 //-------------------------- constructor --------------------
   public gamestoplay() {
//startPR2009-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     int z;
     for (z = 0; z < sharkStartFrame.mainFrame.markgameheadings.length; z++) {
       if (sharkStartFrame.mainFrame.markgameheadings[z][0].indexOf("default")>=0) {
         break;
       }
     }
      isGameTree = true;
   }
   //----------------------------------------------------------
   public void setup(String lists[],boolean onlyOneDatabase1,
                    boolean expandAll,String title, int[] type) {
    if(already) {
       root = new jnode("");
       model = new DefaultTreeModel(root);
       setModel(model);
    }
    already = true;
    if(types==null) {
       types = db.listsort(u.absoluteToRelative(sharkStartFrame.publicGameLib[0]),db.GAMETREE);
    }
    dontwant=new String[0];
    for(int i=0;i<types.length;++i) {
      if(!u.inlist(type,i)) dontwant = u.addString(dontwant,types[i]);
    }
    root.setIcon(jnode.ROOTGAMETREE);
    if(onlyOneDatabase1) onlyOneDatabase = "*";
    publicname = u.absoluteToRelative(sharkStartFrame.publicGameLib[0]);
    super.setup(lists,false,db.GAMETREE,expandAll,title);
  }
   //----------------------------------------------------------
   public void setup(String lists[],boolean onlyOneDatabase1,
                     boolean expandAll,String title, int type) {
      if(already) {
         root = new jnode("");
         model = new DefaultTreeModel(root);
         setModel(model);
      }
      already = true;
      if(types==null) {
         types = db.listsort(u.absoluteToRelative(sharkStartFrame.publicGameLib[0]),db.GAMETREE);
      }
      dontwant=u.removeString(types,type);
      root.setIcon(jnode.ROOTGAMETREE);
      if(onlyOneDatabase1) onlyOneDatabase = "*";
      publicname = u.absoluteToRelative(sharkStartFrame.publicGameLib[0]);
      super.setup(lists,false,db.GAMETREE,expandAll,title);
   }
   //----------------------------------------------------------
   public void setuprewards(String lists[],boolean onlyOneDatabase1,
                    boolean expandAll,String title) {
      onlywant=title;
      if(onlyOneDatabase1) onlyOneDatabase = "*";
      super.setup(lists,false,db.GAMETREE,expandAll,title);
   }
   //---------------------------------------------------------
   public void reduceGames(topic tt) {
      reduceGames(tt, root);
   }
   public void reduceGames(topic tt, jnode node) {
      if(!tt.initfinished) tt.getWords(null,false);
      if(dontwant != null && u.findString(dontwant,node.get())>=0)
        model.removeNodeFromParent(node);
      else if(onlywant != null && node.getLevel() == 1
                        && !onlywant.equalsIgnoreCase(node.get())){
                model.removeNodeFromParent(node);
      }
      else if(tt.notgames != null  && u.findString(tt.notgames,node.get())>=0)
        model.removeNodeFromParent(node);
      else if(!node.isLeaf()) {
        jnode c[] = node.getChildren();
        for(short i=0;i<c.length;++i) {
            reduceGames(tt,c[i]);
         }
         if(node.isLeaf() && !node.isRoot())
           model.removeNodeFromParent(node);
      }
      else {
         if(node != root && !tt.testWord(node.get()))
                 model.removeNodeFromParent(node);
      }
   }
      //---------------------------------------------------------
   public void reduceGames(topic tt[], boolean usephonics) {
      if(tt == null || tt.length == 0) return;
      int i,j;
      boolean wantsounds=false, wantnormal = false, wantphw = false, wantphrases = false;
      for(i=0;i<tt.length;++i) {
        if(!tt[i].initfinished) tt[i].getWords(null,false);
        if(tt[i].phrases) wantphrases = true;
        else if(tt[i].phonics && !tt[i].phonicsw) wantsounds = true;
        else if(tt[i].phonicsw && (usephonics || tt[i].justphonics))  wantphw = true;
        else wantnormal = true;
      }
      if(categories.length>3 && !wantphrases) model.removeNodeFromParent((jnode)root.getChildAt(3));
      if(!wantphw) model.removeNodeFromParent((jnode)root.getChildAt(2));
      if(!wantsounds) model.removeNodeFromParent((jnode)root.getChildAt(1));
      if(!wantnormal) model.removeNodeFromParent((jnode)root.getChildAt(0));
      if(tt.length == 1) {reduceGames(tt[0],root); return;}

      jnode badnodes[][] = new jnode[tt.length][];
      for(i=0;i<tt.length;++i) {
        if(!tt[i].initfinished) tt[i].getWords(null,false);
        wordlist.usephonics = usephonics || tt[i].justphonics;
        badnodes[i] = getbadnodes(tt[i], root, badnodes[i]);
      }
      loopi: for(i=0;i<badnodes[0].length;++i) {
        loopj: for(j=1;j<badnodes.length;++j) {
           for(short k=0;k<badnodes[j].length;++k) {
              if(badnodes[j][k] == badnodes[0][i]) continue loopj; // bad in this topic - so may be bad in all
           }
           continue loopi;    // not in this topic - so OK - leave in tree
        }
        jnode parent = (jnode)(badnodes[0][i].getParent());
        model.removeNodeFromParent(badnodes[0][i]);
        if(!parent.isRoot() && parent.isLeaf())  model.removeNodeFromParent(parent);
      }
  }
  jnode[] getbadnodes(topic tt, jnode node,jnode badnodes[]) {
      if(dontwant != null && u.findString(dontwant,node.get())>=0)
                badnodes=u.addnode(badnodes,node);
      else if(onlywant != null && node.getLevel() == 1
                        && !onlywant.equalsIgnoreCase(node.get())){
                badnodes=u.addnode(badnodes,node);
      }
      else if(!node.isLeaf()) {
        jnode c[] = node.getChildren();
        for(short i=0;i<c.length;++i) {
             badnodes = getbadnodes(tt, c[i],badnodes);
         }
      }
      else {
         if(!tt.testWord(node.get()))
                 badnodes=u.addnode(badnodes,node);
      }
      return badnodes;
   }
   //----------------------------------------------------------
   void addrecommended(topic tt) {
     if (!root.isLeaf()) { // rb 13/11/06
       int pos = 0, i ;
       if (tt.markgames() != null) {
         jnode hdg = null;
         String mg[] = tt.markgames();
         for (i = 0; i < mg.length; ++i) {
           if (find(mg[i]) != null) {
             jnode gg = addChild(hdg, mg[i]);
             if(gg!=null)
                gg.setIcon();
           }
         }
         if (hdg != null) {
           hdg.setIcon(); ++pos; }
       }
     }
   }
   //---------------------------------------------------------
   public String selectedName() {
     jnode sel = getSelectedNode();
     if(sel!=null)  {
        return(getPath(sel));
     }
     return null;
   }
   //---------------------------------------------------------
       // list of games in game tree
   String[] titles() {
      jnode node;
      String s[] = new String[0];
      for(node = (jnode)root.getFirstLeaf();node!=null;node=(jnode)node.getNextLeaf()) {
            s = u.addStringSort(s,node.get());
     }
     return s;
   }
   //---------------------------------------------------------
       // list of games in game tree
   String[] titlesinorder() {
      jnode node;
      String s[] = new String[0];
      for(node = (jnode)root.getFirstLeaf();node!=null;node=(jnode)node.getNextLeaf()) {
            s = u.addString(s,node.get());
     }
     return s;
   }
   //-----------------------------------------------------------
   String[] games(String title) {
      jnode node = find(title);
      String s[] = new String[0],s2;
      for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode c = (jnode)e.nextElement();
           if(c.isLeaf()) {
              s = u.addString(s,c.get());
           }
     }
     return s;
   }
   //--------------------------------------------------------------------
   static String getparm(String gamename, String parmname) {
      String[] lists = sharkStartFrame.searchList(sharkStartFrame.currStudent);
      String values[],name = parmname+"=";
      short i,k,len = (short)(name.length());
      for(i=0;i<lists.length;++i) {
         if((values = (String[])db.find(lists[i],gamename,db.GAME)) != null) {
            for(k = 0;k<values.length;++k) {
               if(values[k].length() > len && values[k].substring(0,len).equalsIgnoreCase(name)) {
                  return values[k].substring(len);
               }
            }
        }
     }
     return "";
  }
   //--------------------------------------------------------------------
   static String getparm(String[] values, String parmname) {
      String name = parmname+"=";
      short k,len = (short)(name.length());
     for(k = 0;k<values.length;++k) {
       if(values[k].length() > len && values[k].substring(0,len).equalsIgnoreCase(name)) {
          return values[k].substring(len);
       }
     }
     return "";
  }
  /**
   * Returns the parameters associated with the game - these can be changed from the
   * Admin/Advanced features for experienced users/Change game details menu when the
   * program is running.
   * @param gamename Array containing the names of games in the public game tree
   * @return String array containing the parameters associated with the program
   */
  static String[] getparms(String gamename) {
      String[] lists = sharkStartFrame.searchListGame(sharkStartFrame.currStudent);
      String values[];
      short i,k;
      for(i=0;i<lists.length;++i) {
         if((values = (String[])db.find(lists[i],gamename,db.GAME)) != null) {
            return values;
        }
     }
     return null;
  }
}

