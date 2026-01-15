package shark;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.SwingUtilities.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.util.*;

public class dndTree_base extends JTree {
   int dragrow = -1;
   TreePath oldselections[];
   boolean doingdrag = false;
   TreePath predragselections[];
   int row;
   int startboxrow;
   JTree thisjt;
   // if true selection behaviour is appropriate for dragging and dropping.
   boolean dragAndDropSelections = false;
   int keepRowHighlighted = -1;
   jnode selectNearestNodes[] = null;

  public dndTree_base() {
    super();
    init2();
  }

  public dndTree_base(DefaultTreeModel model) {
    super(model);
    init2();
  }

  void init2(){
    thisjt = this;
    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if(!dragAndDropSelections) return;
        dragrow = -1;
        startboxrow = -1;
        int row = getRowForLocation(e.getX(),e.getY());
        // not null if clicked path is already selected
        TreePath currPathSelected = null;
        if(row<0){
          startboxrow = getyrow(e.getY());
          if(startboxrow>=0 && e.isControlDown())
            predragselections = oldselections;
          return;
        }
        if(oldselections!=null){
          for (int i = 0; i < oldselections.length; i++) {
            if (getRowForPath(oldselections[i]) == row) {
              currPathSelected = oldselections[i];
            }
          }
        }
        if (!e.isControlDown()) {
          dragrow = row;
          if (oldselections!=null && oldselections.length > 1 && currPathSelected!=null) {
            setSelectionPaths(oldselections);
          }
        }
        oldselections = getSelectionPaths();
      }
      public void mouseReleased(MouseEvent e) {
         if(!dragAndDropSelections) return;
         if(!e.isControlDown() && !e.isShiftDown()) {
          if(getRowForLocation(e.getX(),e.getY())>=0 && !doingdrag){
            if (dragrow >= 0 && getSelectionCount() > 1) {
              oldselections = new TreePath[]{getPathForRow(dragrow)};
              setSelectionRow(dragrow);
            }
            dragrow = -1;
          }
        }
        doingdrag = false;
        predragselections = null;
      }
    });
    addMouseMotionListener(new java.awt.event.MouseMotionListener() {
          public void mouseDragged(MouseEvent e) {
            if(!dragAndDropSelections) return;
            if(startboxrow<0)return;
            Point p = e.getPoint();
            int i = getyrow((int)p.getY());
            if(i<0)return;
            TreePath dragselections[];
            dragselections = getPathBetweenRows(i, startboxrow);
            if(predragselections!=null)
              oldselections = u.addTreePaths(predragselections, dragselections);
            else
              oldselections = dragselections;
            setSelectionPaths(oldselections);
            doingdrag = true;
          }
          public void mouseMoved(MouseEvent e) {}
     });
     addKeyListener(new KeyAdapter() {
       public void keyReleased(KeyEvent e) {
         if(!dragAndDropSelections) return;
         int code = e.getKeyCode();
         if (e.isShiftDown() && (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_UP)){
           oldselections = getSelectionPaths();
         }
       }
     });
  }
  int getyrow(int y){
    BasicTreeUI btui = (BasicTreeUI)this.getUI();
    int inset = btui.getLeftChildIndent()+btui.getRightChildIndent();
    if(y>(int)thisjt.getRowBounds(getRowCount()-1).getMaxY()) return getRowCount()-1;
    for(int i = 0; i < 5; i++){
      int p;
      if((p=getRowForLocation(inset/2+i*inset, y))>=0){
        return p;
      }
    }
    return -1;
  }

  public void expandAll(TreeNode node) {
      TreePath path = new TreePath(node);
    this.expandPath( path );
    int i = node.getChildCount( );
    for ( int j = 0; j< i; j++ ) {
        TreeNode child = node.getChildAt( j );
        if(child instanceof jnode && ((jnode)child).type==jnode.GROUP)continue;
        expandAll( child , path.pathByAddingChild( child ) );
    }
}

    jnode[] getallnodes(jnode node) {
      jnode list[] = new jnode[0];
      for(Enumeration e = node.children();e.hasMoreElements();) {
        jnode c = (jnode) e.nextElement();
        list = u.addnode(list,c);
        if(!c.isLeaf()) list = u.addnode(list,getallnodes(c));
      }
      return list;
    }  
  
  void expandAll(TreeNode node, TreePath path) {
    this.expandPath( path );
    int i = node.getChildCount( );
    for ( int j = 0; j< i; j++ ) {
        TreeNode child = node.getChildAt( j );
        if(child instanceof jnode && ((jnode)child).type==jnode.GROUP)continue;
        expandAll( child , path.pathByAddingChild( child ) );
    }
}
  
  public DefaultMutableTreeNode searchNode(String nodeStr) {
    DefaultMutableTreeNode node = null;
    if(!(this.getModel().getRoot() instanceof DefaultMutableTreeNode))return null;
    Enumeration e = ((DefaultMutableTreeNode)this.getModel().getRoot()).breadthFirstEnumeration();
    while (e.hasMoreElements()) {
      node = (DefaultMutableTreeNode) e.nextElement();
      if (nodeStr.equals(node.getUserObject().toString())) {
        return node;
      }
    }
    return null;
  }  
  
 /*
public void moveNodes(int step, TreePath[] treep){
    DefaultTreeModel dtm = (DefaultTreeModel)this.getModel();
    TreePath tps[] = new TreePath[]{};
    dtm.reload();

    // need to reorder them so bottommost is move first when moving down and etc
    for(int k = 0; k < treep.length; k++){
        int row = this.getRowForPath(treep[k]);
    }


    for(int k = treep.length-1; k >= 0; k--){
        jnode selectedNode = (jnode)treep[k].getLastPathComponent();
        jnode parent = (jnode)selectedNode.getParent();
        int pos = selectedNode.getPosition();
        int newpos = pos+step;
        jnode newparent;

        // if off the top
        if(newpos<0){
            int pos2 = parent.getPosition();
            parent = (jnode)parent.getParent();
            newpos = pos2;
        }
        // if off the bottom
        else if(newpos >= parent.getChildCount()){
            int pos2 = parent.getPosition();
            parent = (jnode)parent.getParent();
            newpos = pos2+step;
        }
        // if going into a group
        else if(!(newparent = (jnode)parent.getChildAt(newpos)).isLeaf() || newparent.type == jnode.GROUP){
            parent = newparent;
            if(step>0)newpos = 0;
            else newpos = parent.getChildCount();
        }
        dtm.removeNodeFromParent(selectedNode);
        dtm.insertNodeInto(selectedNode, parent, newpos);
        tps = u.addTreePaths(tps, new TreePath[]{new TreePath(selectedNode.getPath())});
    }
    dtm.reload();
    this.setSelectionPaths(tps);
}
   */
}
