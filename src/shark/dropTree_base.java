/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import java.util.*;

/**
 *
 * @author White Space
 */
 class dropTree_base extends dndTree_base  {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    jnode movenode[],saveparent[];
    int savepos[];
    TreePath paths[];
    Point lastpt;
    DefaultTreeModel model1;
    jnode dragparentnode;
    public boolean allowmovenode = true;


    dropTree_base(DefaultTreeModel model, jnode dragparent) {
        super(model);
       dragparentnode = dragparent;
       model1=model;
        init();
    }


    dropTree_base(DefaultTreeModel model, jnode dragparent, boolean allowmove) {
        super(model);
       dragparentnode = dragparent;
       model1=model;
       allowmovenode = allowmove;
        init();
    }

    void init() {
       

//startPR2006-11-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       setRowHeight(getFontMetrics(getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       addMouseListener(new MouseAdapter() {
         public void mouseReleased(MouseEvent e) {
             if(movenode != null) {
               movenode = null;
               setCursor(null);
             }
         }
       });
       addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
         public void mouseDragged(MouseEvent e) {
             int i,j;
             if(!allowmovenode)return;
             if(movenode==null) {
               int[] rr =getSelectionRows();
               if(rr==null)return;
               Arrays.sort(rr);
               TreePath tp[] = new TreePath[rr.length];
               for(i=0;i<rr.length;++i) tp[i] = getPathForRow(rr[i]);
               if(tp==null || tp.length==0) return;
               jnode jj[] = new jnode[tp.length];
               int wantm = 0;
               for(i=0;i<tp.length;++i) {
                 jj[i] = (jnode) tp[i].getLastPathComponent();
                 if(jj[i].type!=jnode.STUDENT && jj[i].type!=jnode.GROUP)return;
                 if (jj[i] != getModel().getRoot()
                     && (jj[i].type == jnode.STUDENT || tp.length==1)) ++wantm;
               }
               if(wantm == 0) return;
               movenode = new jnode[wantm];
               saveparent = new jnode[wantm];
               savepos = new int[wantm];
               paths = new TreePath[wantm];
               for(i=j=0;i<tp.length;++i) {
                 if (jj[i] != getModel().getRoot()
                     && (jj[i].type == jnode.STUDENT || tp.length==1)) {
                   movenode[j] = jj[i];
                   saveparent[j] = (jnode) jj[i].getParent();
                   savepos[i] = jj[i].getPosition();
                   ++j;
                 }
                 lastpt = null;
                 try {
                     setCursor(Cursor.getSystemCustomCursor("MoveDrop.32x32"));
                 } catch (AWTException ee) {}
               }
                return;
             }

             Point pt;
             TreePath tp=null;
             jnode node=null;
             pt = new Point(e.getX(),e.getY());
             if(lastpt != null &&  pt.equals(lastpt)) return;
             lastpt=pt;
             if(pt != null) {
               tp = getClosestPathForLocation(pt.x, pt.y);
               int row = getRowForPath(tp);
               if(row < getRowCount()-1-movenode.length) scrollRowToVisible(row+1+movenode.length);
                 if(row>0) scrollRowToVisible(row-1);
             }

             if(pt == null
                 || tp == null
                 || (node = (jnode)tp.getLastPathComponent()) == null) {
                for(i=0;i<movenode.length;++i) {
                  jnode oldparent = (jnode)movenode[i].getParent();
                  if (oldparent != null && oldparent != saveparent[i])
                    model1.removeNodeFromParent(movenode[i]);
                  if (saveparent[i] != oldparent) {
                    model1.insertNodeInto(movenode[i], saveparent[i], savepos[i]);
                  }
                }
                setSelectionPath(new TreePath(movenode[0].getPath()));
                paintImmediately(0, 0, getWidth(), getHeight());
                return;
             }
             if(node == movenode[0]) return;
             jnode parent;
             jnode oldnode = movenode[0];
             if(node == (jnode)model1.getRoot() || oldnode.type == jnode.STUDENT
                                 && node.type != 0 && node.type != jnode.STUDENT)
                  parent = node;
             else parent = (jnode)node.getParent();
             int pos=node.getPosition();
                // special for multi-move getting near end of class
             if(movenode.length>1 && parent != node && pos+movenode.length > parent.getChildCount()
                      && movenode[1].getParent() == parent) {
                jnode next = (jnode)((jnode)parent.getLastChild()).getNextLeaf();
                if(next != null) {
                  node = next;
                  if(node == (jnode)model1.getRoot() || oldnode.type == jnode.STUDENT
                                      && node.type != 0 && node.type != jnode.STUDENT)
                       parent = node;
                  else parent = (jnode)node.getParent();
                  pos=node.getPosition();
                }
                else return;
             }
             if(!parent.isNodeAncestor(dragparentnode)){
                 return;
             }
             TreePath pa;
             for(i=0;i<movenode.length;++i) {
               jnode oldparent = (jnode) movenode[i].getParent();
               if (!movenode[i].isNodeDescendant(parent)) {
                 if (oldparent != null)
                   model1.removeNodeFromParent(movenode[i]);
               }
             }
             for(i=0;i<movenode.length;++i) {
                  jnode oldparent = (jnode) movenode[i].getParent();
                  if (!movenode[i].isNodeDescendant(parent)) {
                    model1.insertNodeInto(movenode[i], parent, (node == parent) ? i : (pos + i));
                    paths[i] = new TreePath(movenode[i].getPath());
                  }
             }
             expandPath(new TreePath(parent.getPath()));
             expandPath(pa = new TreePath(movenode[0].getPath()));
             setSelectionPaths(paths);
             paintImmediately(0, 0, getWidth(), getHeight());
           }
       });
    }
 }
