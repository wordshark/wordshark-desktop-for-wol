package shark;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.SwingUtilities.*;
/**
 * <p>Title: WordShark</p>
 * <p>Description: Your description</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: WhiteSpace</p>
 * @author Roger Burton
 * @version 1.0
 */
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//public class sharkTree extends JTree{
public class sharkTree extends dndTree_base{
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Topic is being updated
   */
  boolean updating;
  boolean copying,changed;
  public boolean clipboard[];
  public saveTree1 clipboardx[];
  public String clipdb[];
  public String clipnodeval[];
  public byte cliptype[];
  static final byte UNDOMAX = 20;
  Object undoList[] = new Object[UNDOMAX];
  short  undoNode[][] = new short[UNDOMAX][];
  short undocurr;
  String valueBeforeEdit;
  public byte onlyOneType;
  boolean wasedit;
  static String publictopics = u.absoluteToRelative(sharkStartFrame.publicTopicLib[0]);
  String publicname = publictopics;
  jnode root = new jnode("");
  DefaultTreeModel model = new DefaultTreeModel(root);
   boolean distribute;
   jnode editnode;
   String saveuserobject;
   String findstring;
   int findlast;

   jnode movenode,saveparent;
   int savepos;
   Point lastpt;
   boolean isMoveKeyDown = false;
      //---------------------------------------------------
  public sharkTree() {
     super();
     putClientProperty("JTree.lineStyle","Angled");
     setModel(model);
     root.dontcollapse=true;
     this.setExpandsSelectedPaths(true);
     this.setScrollsOnExpand(true);
     this.setCellRenderer(new treepainter());
//startPR2006-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setRowHeight(getFontMetrics(getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     this.addTreeWillExpandListener(new TreeWillExpandListener() {
        public void treeWillCollapse(TreeExpansionEvent ev) throws ExpandVetoException {
           if(((jnode)ev.getPath().getLastPathComponent()).dontcollapse)
               throw new ExpandVetoException(ev);
        }
        public void treeWillExpand(TreeExpansionEvent ev) throws ExpandVetoException{
          if(((jnode)ev.getPath().getLastPathComponent()).dontexpand)
              throw new ExpandVetoException(ev);
        }
     });
     addMouseListener(new MouseAdapter() {  // for drag & drop
       public void mouseReleased(MouseEvent e) {
           if(movenode != null) {
             movenode = null;
             setCursor(null);
           }
       }
     });
     addMouseMotionListener(new java.awt.event.MouseMotionAdapter() { // for drag & drop
       public void mouseDragged(MouseEvent e) {
           if(!updating) return;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(shark.specialfunc&&((e.getModifiers() & ActionEvent.ALT_MASK) == 0)) return;
           if(shark.specialfunc&&!isMoveKeyDown) return;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           if(movenode==null) {
             TreePath tp = getPathForLocation(e.getX(),e.getY()); // setup
             if(tp==null) return;
             jnode node = (jnode)tp.getLastPathComponent();
             if(node != null && node != getModel().getRoot()) {
                movenode = node;
                saveparent = (jnode)node.getParent();
                savepos = node.getPosition();
                lastpt=null;
                saveForUndo(root);
                try {
                  setCursor(Cursor.getSystemCustomCursor("MoveDrop.32x32"));
                }catch(AWTException ee) {}
              }
              return;
           }
           Point pt;
           TreePath tp;
           jnode node=null;
           jnode oldparent = (jnode)movenode.getParent();
           pt = new Point(e.getX(),e.getY());
           if(lastpt != null &&  pt.equals(lastpt)) return;
           lastpt=pt;
           if((tp = getClosestPathForLocation(pt.x,pt.y)) == null
               || (node = (jnode)tp.getLastPathComponent()) == null) {
               if(oldparent != null && oldparent != saveparent)
                     model.removeNodeFromParent(movenode);
               if(saveparent != oldparent) {
                  model.insertNodeInto(movenode,saveparent,savepos);
                  setSelectionPath(new TreePath(movenode.getPath()));
                  paintImmediately(0,0,getWidth(),getHeight());
               }
               return;
           }
           if(node == movenode) return;
           recordChange(node);
           jnode parent;
           jnode oldnode = movenode;
           if(node == root )
                parent = node;
           else parent = (jnode)node.getParent();
           int pos=node.getPosition();
           TreePath pa;
           if(!movenode.isNodeDescendant(parent)) {
              if(oldparent != null) model.removeNodeFromParent(movenode);
              model.insertNodeInto(movenode,parent,(node==parent)?0:(pos));
              expandPath(new TreePath(parent.getPath()));
              expandPath(pa = new TreePath(movenode.getPath()));
              setSelectionPath(pa);
              paintImmediately(0,0,getWidth(),getHeight());
           }
         }
     });
     this.addKeyListener(new KeyAdapter() {
       public void keyReleased(KeyEvent e) {
           isMoveKeyDown = false;
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if (updating && (e.getModifiers() == e.CTRL_MASK) ) {
         if (updating && (e.getModifiers() == Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()) ) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             int val = e.getKeyCode();
             if(val == e.VK_C) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.macBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.mainFrame.copy_actionPerformed(null);
               e.consume();
             }
             else if(val == e.VK_V) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.macBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.mainFrame.paste_actionPerformed(null);
               e.consume();
             }
             else if(val == e.VK_X) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.macBlock = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               sharkStartFrame.mainFrame.cut_actionPerformed(null);
               e.consume();
             }


//             else if(val == e.VK_Z) {
//               sharkStartFrame.mainFrame.undo_actionPerformed(null);
//               e.consume();
//             }
//             else if(val == e.VK_F) {
//               find();
//               e.consume();
//             }
//             else if(val == e.VK_N) {
//                 findnext();
//                 e.consume();
//             }
          }
        }

       
       public void keyPressed(KeyEvent e) {
             int val = e.getKeyCode();
             if(val == e.VK_F5) {
                isMoveKeyDown = true;
             }
          }
     });
     }
     void find() {
       findstring = JOptionPane.showInputDialog("Find:");
      int i;
      if(findstring==null || findstring.length() == 0)                                //If 1
          return;                           //Nothing has been entered in the dialogue box
      findlast=0;
      findstring = findstring.toLowerCase();
      findstring();

     }
     void findnext() {
       if(findstring == null) {noise.beep();return;}
       findstring();
     }
     //-----------------------------------------------------
     void findstring() {
       int i;
       jnode[] list = getallnodes(root);
       for(i=findlast;i<list.length;++i) {
          if(list[i].get().toLowerCase().indexOf(findstring) >= 0) {
            this.setSelectionPath(new TreePath(list[i].getPath()));
            this.scrollPathToVisible(new TreePath(list[i].getPath()));
            findlast = i+1;
            return;
          }
        }
        noise.beep();
        findlast = 0;
     }
    //---------------------------------------------------
  void saveForUndo(jnode node) {
     undoList[undocurr] = (new saveTree1(this, node));
     undoNode[undocurr] = (treepos(node));
     if(++undocurr >= UNDOMAX) undocurr = 0;
     recordChange(node);
  }
  //---------------------------------------------------------
  public boolean undo() {
     saveTree1 us;
     Object ob;
     jnode node;
     if(--undocurr < 0) undocurr = (short)(UNDOMAX-1);
     if(undoList[undocurr] == null) return false;
     node = treenode(undoNode[undocurr]);
     ob = undoList[undocurr];
     if(ob instanceof saveTree1) {
        us = (saveTree1)ob;
        copying = true;
        node.removeAllChildren();
        us.addToTree(this,node);
        addEmpty(node);
        copying=false;
     }
     else node.setUserObject((String)ob);
     undoList[undocurr] = null;
     undoNode[undocurr] = null;
     model.reload();
     return true;
  }
  //----------------------------------------------------------
  void saveOldValueForUndo(jnode node) {
     undoList[undocurr] = (valueBeforeEdit);
     undoNode[undocurr] = (treepos(node));
     if(++undocurr >= UNDOMAX) undocurr = 0;
     recordChange(node);
  }
  //----------------------------------------------------------
  short[] treepos(jnode node) {
     TreeNode p[] = node.getPath();
     short len =(short)( p.length-1),i;
     short pos[] = new short[len];
     for(i=0;i<len;++i) pos[i]=(short)p[i].getIndex(p[i+1]);
     return pos;
  }
  //------------------------------------------------------------
  jnode treenode(short pos[]) {
     jnode node = root;
     short i;
     for(i=0;i<pos.length;++i)  node = (jnode)node.getChildAt(pos[i]);
     return(node);
  }
  //---------------------------------------------------------
   public void stopEdit()  {
     if(isEditing()) getCellEditor().stopCellEditing();
   }
    //---------------------------------------------------------
  public String getCurrSelection() {
    jnode j = (jnode)getLastSelectedPathComponent();
    if(j != null) return j.get();
    else return null;
  }
    //---------------------------------------------------------
  public jnode getSelectedNode() {
    return (jnode)getLastSelectedPathComponent();
  }
    //---------------------------------------------------------
  public jnode[] getSelectedNodes() {
    TreePath p[] = getSelectionPaths();
    if(p == null) return null;
    jnode ret[] = new jnode[p.length];
    for(short i=0;i<p.length;++i) {
       ret[i] = (jnode)p[i].getLastPathComponent();
    }
    return ret;
  }
    //---------------------------------------------------------
  public void keypressed(KeyEvent e) {
      if(isEditing()) return;
      jnode pos;
      int cd = e.getKeyCode();
      if(cd == e.VK_INSERT || (!shark.production  &&  e.isControlDown() && e.getKeyCode() == KeyEvent.VK_I)) {
         TreePath p = getLeadSelectionPath();
         if(p != null) {
            pos = (jnode)p.getLastPathComponent();
            if (!pos.isRoot()) {
               jnode parent = (jnode)pos.getParent();
               if(canAddChild(parent))  {
                  jnode node=new jnode();
                  model.insertNodeInto(node, parent,parent.getIndex(pos));
                  this.setSelectionPath(new TreePath(node.getPath()));
                  saveForUndo(parent);
               }
            }
         }
      }
      else if(cd ==e.VK_DELETE) {
        TreePath p[] = getSelectionPaths();
        if(p != null) {
            if(p.length > 0) {
               jnode parent;
               for (short i=0;i<p.length;++i) {
                  pos = (jnode)p[i].getLastPathComponent();
                  if(!pos.isRoot() && canRemove(pos)) {
                        saveForUndo((jnode)pos.getParent());
                        model.removeNodeFromParent(pos);
                  }
               }
               afterRemove();
               addEmpty(root);
            }
        }
      }
      else {
         char ch = e.getKeyChar();
         if(ch != e.CHAR_UNDEFINED && !e.isControlDown()) {
//startPR2004-10-23^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          // if running on a Macintosh
          if (shark.macOS){
            if(e.getModifiers() != Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()){
              editnode = getSelectedNode();
              if(editnode != null && (editnode.get().length()==0 || distribute)) {
              }
              else editnode=null;
            }
          }
          // if running on Windows
          else{
            editnode = getSelectedNode();
            if(editnode != null && (editnode.get().length()==0 || distribute)) {
            }
            else editnode=null;
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         }
      }
   }
   //---------------------------------------------------------
 // start rb 11/6/05 -------------------------------------------------------------------------------------------------------
   public void keyreleased(KeyEvent e) {
     if(e.getKeyChar() != e.CHAR_UNDEFINED && !isEditing() && editnode != null && (editnode.get().length()==0 || distribute)) {
       saveuserobject = editnode.get();
       editnode.setUserObject("" + e.getKeyChar());
       startEditingAtPath(new TreePath(editnode.getPath()));
     }
   }
 //end rb 11/6/05 -------------------------------------------------------------------------------------------------------
   void afterRemove() {}
   //---------------------------------------------------------
   boolean canRemove(jnode node) {
       return u.yesnomess(
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                    "Deleting node","Do you want to delete the node(s) marked in the topic\nand all its children ?");
      "Deleting node","Do you want to delete the node(s) marked in the topic\nand all its children ?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
   }
   //---------------------------------------------------------
   boolean canCut(jnode node) {
      return (node != root);
   }
   //---------------------------------------------------------
   boolean canCopy(jnode node) {
      return (node != root);
   }
   //---------------------------------------------------------
   boolean canPaste(jnode node) {
      return (node != root);
   }
   //---------------------------------------------------------
   void selectOne(jnode node) {
     setSelectionPath(new TreePath(node.getPath()));
  }
  //---------------------------------------------------------
  boolean canAddChild(jnode node)  {
     String s = node.get();
     boolean ret = true;

     if(s.equals("")) ret = false;
     return ret;
  }
  //-------------------------------------------------------
  void addEmpty(jnode node) {
     copying=true;
     if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();) {
           jnode j = (jnode)e.nextElement();
           if(canAddChild(j)) addEmpty(j);
        }
        if(((jnode)node.getLastChild()).get() != "" && canAddChild(node)) {
           addChild(node,"");
        }
     }
     else {
        if (canAddChild(node)) addChild(node,"");
     }
     copying=false;
  }
  //----------------------------------------------
  public void cut() {
     if(copy()) {
        jnode pos;
        TreePath p[] = getSelectionPaths();
        if(p != null) {
            if(p.length > 0) {
               jnode parent;
               for (short i=0;i<p.length;++i) {
                  pos = (jnode)p[i].getLastPathComponent();
                  saveForUndo((jnode)pos.getParent());
                  model.removeNodeFromParent(pos);
               }
           }
        }
     }
  }
  //------------------------------------------------------
   public boolean copy() {
      boolean ret = false;
      TreePath p[] = getSelectionPaths();
      if(p != null && p.length > 0) {
        String s;
        ret = true;
        jnode pos;
        stopEdit();
        clipboard = new boolean[p.length];
        clipboardx = new saveTree1[p.length];
        cliptype = new byte[p.length];
        clipdb = new String[p.length];
        clipnodeval = new String[p.length];
        for(short i=0;i<p.length;++i) {
           pos = (jnode)p[i].getLastPathComponent();
           clipboardx[i] = new saveTree1(this,pos);
           if(!pos.isRoot() && canCopy(pos)) {
              s = ((jnode)pos.getParent()).get();
              if(s.equals(topicTree.TOPICLISTNAME)
                            || s.equals(topicTree.GAMELISTNAME)
                             || s.equals(topicTree.TEXTLISTNAME)
                            || s.equals(topicTree.GAMETREELISTNAME))  {
                  clipdb[i] = ((topicTree)this).findDatabase(pos);
                  if(s.equals(topicTree.TOPICLISTNAME)) cliptype[i] = db.TOPIC;
                  else if(s.equals(topicTree.GAMELISTNAME)) cliptype[i] = db.GAME;
                  else if(s.equals(topicTree.TEXTLISTNAME)) cliptype[i] = db.TEXT;
                  else if(s.equals(topicTree.GAMETREELISTNAME)) cliptype[i] = db.GAMETREE;
                  clipnodeval[i] = pos.get();
                  ret = true;
              }
              else if(onlyOneType == db.TEXT && !pos.isLeaf()) {
                 if(pos.getLevel() > 1 ) {
                    clipdb[i] = ((topicTree)this).findDatabase(pos);
                    cliptype[i] = db.TEXT;
                    clipnodeval[i] = pos.get();
                  }
                  else {
                    clipdb[i] = pos.get();
                    cliptype[i] = db.TEXT;
                  }
              }
              else if(onlyOneType == db.IMAGE) {
                 if(!pos.isLeaf()) {
                  clipdb[i] = pos.get();
                  cliptype[i] = db.IMAGE;
                 }
              }
              else if(onlyOneType == db.WAV) {
                 if(pos.isLeaf()) {
                  clipdb[i] = ((jnode)pos.getParent()).get();
                  clipnodeval[i] = pos.get();
                  cliptype[i] = db.WAV;
                 }
              }
              else {
                 clipboard[i] = true;   // simple node copy
                 ret = true;
              }
           }
        }
     }
     return ret;
   }
   public void paste() {
     jnode node = getSelectedNode(),newnode,wknode,parent;
     String newdb;
     stopEdit();
     copying = true;
     if(node != null && node.isRoot() && clipboard.length ==1 ) {
        saveForUndo(node);
        TreePath[] pp = getSelectionPaths();
        wknode=new jnode();
        model.setRoot(root = wknode);
        clipboardx[0].addToTree(this,wknode);
        model.reload();
        addEmpty(root);
        root.setUserObject(root.get() + "@");
        startEditingAtPath(new TreePath(root));
//        setSelectionPaths(pp);
//        this.expandPath(pp[0]);
     }
     else if(node != null && !node.isRoot() && clipboard!=null && clipboard.length > 0) {
        parent=(jnode)node.getParent();
        boolean saved = false;
        TreePath[] pp = getSelectionPaths();
        for(short i=0;i<clipboard.length;++i) {
           if(clipboard[i]) {
              if(!saved) {saveForUndo(parent); saved = true;}
              wknode=new jnode();
              model.insertNodeInto(wknode,parent,parent.getIndex(node));
              clipboardx[i].addToTree(this,wknode);
           }
           else if(clipdb[i] != null &&  (this instanceof topicTree)
                  && (newdb = ((topicTree)this).findDatabase(node)) != null) {
               String assignedname=copything(clipdb[i],clipnodeval[i],newdb,cliptype[i]);
               addtotype(node,cliptype[i],assignedname,clipboardx[i]);
          }
        }
        addEmpty(root);
//        setSelectionPaths(pp);
//        this.expandPath(pp[0]);
     }
     copying=false;
  }
  void addtotype(jnode node, byte type, String val, saveTree1 ss) {
      String s="***";
      jnode parent = node;
      short i;
      if(type == db.TEXT) {
         if(parent.getLevel() == 2)   parent = (jnode)parent.getParent();
      }
      else if(type == db.WAV) {
        if(parent.getLevel() == 2)   parent = (jnode)parent.getParent();
      }
      else {
       if(type == db.GAME) s = topicTree.GAMELISTNAME;
       else if(type == db.GAMETREE) s = topicTree.GAMETREELISTNAME;
       else if(type == db.TOPIC) s = topicTree.TOPICLISTNAME;
       else if(type == db.SAVESUM) s = topicTree.SUMLISTNAME;
       if (!parent.get().equalsIgnoreCase(s)) {
         if(!parent.isLeaf()){   // test
           for(Enumeration e = node.children();e.hasMoreElements();) {
               jnode j = (jnode)e.nextElement();
               if(s.equalsIgnoreCase(j.get())) {
                  parent = j; break;
               }
            }
         }
       }
       while(!parent.isRoot()
          && !(parent = (jnode)parent.getParent()).get().equalsIgnoreCase(s)) {}
       if(parent.isRoot()) {noise.beep(); return;}
      }
      i = 0;
      for(Enumeration e2 = parent.children();e2.hasMoreElements();++i) {
           jnode j2 = (jnode)e2.nextElement();
           if(j2.get().compareTo(val) > 0) break;
       }
       jnode newnode = new jnode();
       model.insertNodeInto(newnode,parent,i);
       ss.addToTree(this,newnode);
       set(newnode,val);
       this.expandPath(new TreePath(newnode.getPath()));
       return;
  }
  String copything(String from,String name,String to, byte type) {
     String toname = name;
     while(db.query(to,toname,type) >= 0) toname = toname+"x";
     if(type==db.TOPIC) {
        topic tt = new topic(from,name,null,null);
        tt.databaseName = to;
        tt.name = toname;
        tt.root.setUserObject(toname);
        db.update(to,toname,(new saveTree1(tt, tt.root)).curr,db.TOPIC);
     }
     else {
       if (type == db.WAV)
         db.updatewav(to, toname, db.findwav(from, name));
       else db.update(to, toname, db.find(from, name, type), type);
     }
     return toname;
  }
  void copythings(String from,String to, byte type) {
     boolean replaceall=false,replacenone = false, hadallmess = false;
     int res;
     String fromlist[] = db.list(from,type);
     if(u.yesnocancel("Copying from "+from+ " to "+to,
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          "About to copy "+String.valueOf(fromlist.length)+" "+db.typeString(type)+"s. This cannot be UNDONE. OK ?")==0)
                "About to copy "+String.valueOf(fromlist.length)+" "+db.typeString(type)+"s. This cannot be UNDONE. OK ?", sharkStartFrame.mainFrame)==0)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             return;

     mainloop: for(int i=0;i<fromlist.length;++i) {
       if(replacenone && db.query(to,fromlist[i],type) >= 0) continue mainloop;
       if(!replaceall && !replacenone  && db.query(to,fromlist[i],type) >= 0) {
         res = u.yesnocancel("Copying from "+from+ " to "+to,
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//          fromlist[i]+ " is already in "+to+". Replace it ?");
          fromlist[i]+ " is already in "+to+". Replace it ?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         if(res == 0) {
               if(!hadallmess) {
                  hadallmess = true;
                  res = u.yesnocancel("Copying from "+from+ " to "+to,
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                       "Replace all that are already there ?");
                         "Replace all that are already there ?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                 if(res == 0)  replaceall = true;
                  else if (res == -1)  break mainloop;
               }
         }
         else if(res == 0) {
              if(!hadallmess) {
                  hadallmess = true;
                  res = u.yesnocancel("Copying from "+from+ " to "+to,
//startPR2004-10-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                       "Leave old versions of those already there ?");
                       "Leave old versions of those already there ?", sharkStartFrame.mainFrame);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(res == 0)  replacenone = true;
                  else if(res == -1)  break mainloop;
              }
              continue mainloop;
         }
         else if(res == -1) break mainloop;
       }
       db.update(to,fromlist[i],db.find(from,fromlist[i],type), type);
     }
  }
  void reload(jnode node,String db1,byte type) {
     String list[]  = db.list(db1,type);
     removeAllChildren(root);
     for(short i=0;i<list.length;++i) {
        model.insertNodeInto(new jnode(list[i]),root,root.getChildCount());
     }
  }
  void recordChange(jnode node) {
     changed = true;
  }
  
  /**
   * @param s String to be found
   * @return Null if s not found and otherwise the node that has been found
   */
  public jnode find(String s) {
     return find(root,s);
  }
  public static jnode find(jnode node, String s) {
     for(Enumeration e = node.children();e.hasMoreElements();) {
         jnode c = (jnode)e.nextElement();
         if(c.get().equalsIgnoreCase(s)|| (c = find(c,s)) != null) return c;
     }
     return null;
  }
//startPR2024-04-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR  
  public jnode findLeaf(String s) {
     return findLeaf(root,s);
  }
  public static jnode findLeaf(jnode node, String s) {
     for(Enumeration e = node.children();e.hasMoreElements();) {
         jnode c = (jnode)e.nextElement();
         if((c.get().equalsIgnoreCase(s) && c.isLeaf()) || (c = findLeaf(c,s)) != null)
             return c;
     }
     return null;
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public jnode[] find2(String s) {
     if(root.get().equals(s)) return new jnode[]{root};
     return find2(root,s);
  }
  public static jnode[] find2(jnode node, String s) {
     String ss;
     int i;
     jnode list[] =  new jnode[0];
     for(Enumeration e = node.children();e.hasMoreElements();) {
         jnode c = (jnode)e.nextElement();
         ss = c.get();
         if((i=ss.indexOf(topicTree.ISTOPIC))>=0) {
            if(ss.substring(0,i).equals(s))
                list = u.addnode(list,c);
            else list = u.addnode(list,find2(c,s));
         }
         else if(ss.equals(s))
                list = u.addnode(list,c);
         else list = u.addnode(list,find2(c,s));
     }
     return list;
  }
  public jnode dummy(jnode node) {
     int i = node.getChildCount();
     jnode child;
     if (i == 0 || ((String)(child = (jnode)node.getLastChild()).get()).length() !=0) {
        child = new jnode();
        model.insertNodeInto(child,node,i);
     }
     return child;
  }
  void set(jnode node,String s) {
     node.setUserObject(s);
     model.nodeChanged(node);
     this.expandPath(new TreePath(node.getPath()));
  }
  void collapseAll() {
    int len = (short)getRowCount();
     for(int i = 0;i<len;++i) collapseRow(i);
  }
  void expandAll() {
    int len = (short)getRowCount();
     for(int i = 0;i<len;++i)
         expandRow(i);
  }
  void removeAllChildren(jnode node) {
     while(!node.isLeaf())
         model.removeNodeFromParent((jnode)node.getFirstChild());
  }
  jnode addChild(jnode parent,String s) {
      if(s==null)return null;
      if(parent == null)return null;
     jnode child = new jnode(s);
     if(child == null)return null;
     model.insertNodeInto(child,parent,parent.getChildCount());
     return child;
  }
  jnode addChild(jnode parent,String s,jnode before) {
     jnode child = new jnode(s);
     model.insertNodeInto(child,parent,parent.getIndex(before));
     return child;
  }
//startPR2008-01-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  // prevents highlighting of text
  public void startEditingAtPath(TreePath path) {
    if(path==null)return;
    jnode n = (jnode)path.getLastPathComponent();
    java.awt.Component c = getCellEditor().getTreeCellEditorComponent(this,n.getUserObject(), false, false, true, getRowForPath(path));
    super.startEditingAtPath(path);
    javax.swing.tree.DefaultTreeCellEditor.EditorContainer ec = (javax.swing.tree.DefaultTreeCellEditor.EditorContainer)c;
    JTextField textField= (JTextField) ec.getComponent(0);
    textField.setCaretPosition(textField.getSelectionEnd());
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
}
