package shark;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

public class sharklist extends JTree {
  jnode root = new jnode();
  DefaultTreeModel model = new DefaultTreeModel(root);
  boolean wasedit;
  boolean simpleStopEdit = false;
  int editpos=-1;
  public int oldselection = -1;
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * When true stops selection from moving on when editing/typing is finished.
   */
  public boolean stayOnCurrentSelection = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public int maxNumber = 9999999;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
int removenodepos = -1;
  public sharklist(TreeCellRenderer  cc) {
     super();

     setModel(model);
     setCellRenderer(cc);
     setup();
  }
  public sharklist() {
     super();
     setModel(model);
     setCellRenderer(new treepainter());
     setup();
  }
  void setup() {
     setShowsRootHandles(false);
     setRootVisible(false);
     setExpandedState(new TreePath(root.getPath()),true);
     this.setBorder(BorderFactory.createEtchedBorder());
     setEditable(true);
     this.setInvokesStopCellEditing(true);
     multiselect(false);
//startPR2006-10-10^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     setRowHeight(getFontMetrics(getFont()).getHeight());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     addKeyListener( new KeyAdapter()   {
        public void keyPressed(KeyEvent e){
           int cd = e.getKeyCode();
           int pos = getLeadSelectionRow();
           if(cd == e.VK_INSERT || (!shark.production  &&  e.isControlDown() && e.getKeyCode() == KeyEvent.VK_I)) {
              insert();
            }
            else if(cd ==e.VK_DELETE) {
              delete();
            }
            else if(cd == e.VK_UP) {
               if(isEditing()) {
                  stopEditing();
               }
            }
            else if(cd == e.VK_DOWN) {
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              if(root.getChildCount()>=maxNumber && pos == root.getChildCount()-1 &&
                 !((jnode)root.getChildAt(pos)).get().equals("")
                 ){
                if(maxwarning())
                  return;
              }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
               if(isEditing())  stopEditing();
               if(pos == root.getChildCount()-1) {
                  if(!clearnode(pos)) {
                     jnode node=new jnode("");
                     model.insertNodeInto(node,root,pos+1);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  setSelectionRow(pos+1);
                     startEditingAtPath(getPathForRow(pos+1));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  }
                  else return;
               }
            }
            else {
              int ch = e.getKeyChar();
              if(!isEditing() && pos >=0 && ch != e.CHAR_UNDEFINED) {
                   if(clearnode(pos)) {
                     editpos=pos;
                  }
              }
            }
        }
  // start rb 11/6/05 -------------------------------------------------------------------------------------------------------
        public void keyTyped(KeyEvent e){
          if(editpos>=0){
            char ch = e.getKeyChar();
            if(ch != e.CHAR_UNDEFINED) {
              ( (jnode) getPathForRow(editpos).getLastPathComponent()).
                  setUserObject("" + ch);
              startEditingAtPath(getPathForRow(editpos));
            }
          }
        }
     });
 // end rb 11/6/05 -------------------------------------------------------------------------------------------------------
     model.addTreeModelListener(new TreeModelListener() {
          public void treeNodesChanged(TreeModelEvent e) {
             int pos = getLeadSelectionRow();
             if(wasedit && !simpleStopEdit) {
                TreePath pp = getPathForRow(pos);
                jnode jn1;
                if(pp!=null && (jn1=((jnode)pp.getLastPathComponent()))!=null){
                    if(jn1.get().trim().equals("")){
                        startEditingAtPath(getPathForRow(pos));
                        return;
                    }                       
                }
               if(clearnode(pos)) {
                   removenodepos = pos;
               }
               else if(okedit()) {
                  wasedit = false;
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  if(root.getChildCount()>=maxNumber&&
                     !((jnode)root.getChildAt(pos)).get().equals("")
                     ){
                    if(maxwarning())
                      return;
                  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  ++pos;
                  if(root.getChildCount() == pos) model.insertNodeInto(new jnode(),root,pos);
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//                  setSelectionRow(pos);
                  startEditingAtPath(getPathForRow(pos));
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
                  datachange();
               }
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//               else startEdit(getLeadSelectionRow());
               else if(!stayOnCurrentSelection){
                 startEdit(getLeadSelectionRow());
               }
               else stayOnCurrentSelection = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             }
             else {
               datachange();
             }
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             editingstopped();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
          public void treeNodesInserted(TreeModelEvent e) {}
          public void treeNodesRemoved(TreeModelEvent e) {
             if(((jnode)e.getChildren()[0]).get().length()>0) datachange();
          }
          public void treeStructureChanged(TreeModelEvent e) {}
     });


     addMouseListener(new java.awt.event.MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            if(root.isLeaf()) {
               model.insertNodeInto(new jnode(),root,0);
               model.reload();
               setSelectionRow(0);
            }
            else {
             clickselection(fromdisplay(getClosestRowForLocation(e.getX(),e.getY())-1));
            }
         }
     });
     addTreeSelectionListener(new TreeSelectionListener() {
        public void valueChanged(TreeSelectionEvent e) {
           jnode node;
           int sel = getRowForPath(e.getNewLeadSelectionPath());
            if(editpos<0 && oldselection != sel && oldselection >= 0 && oldselection < root.getChildCount()) {
              node=((jnode)getPathForRow(oldselection).getLastPathComponent());
              String s = u.stripspaces2(node.get());
              node.setUserObject(s);
              if (s.length() == 0) {
                model.removeNodeFromParent(node);
                sel = getLeadSelectionRow();
              }
           }
//startPR2007-09-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//           if(sel>=0)
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           oldselection = sel;
           newselection();
        }
     });
     
     
     getCellEditor().addCellEditorListener(new CellEditorListener() {
          public void editingStopped(ChangeEvent e) {
              try{
              if(removenodepos>=0){
                  TreePath tp = getPathForRow(removenodepos);
                  if(tp!=null){
                      model.removeNodeFromParent((jnode)tp.getLastPathComponent());
                      removenodepos = -1;
                  }
              }
              }
              catch(Exception ee){
                  removenodepos = -1;
              }
              wasedit=true;
              editpos = -1;
          }
          public void editingCanceled(ChangeEvent e) {
              if(editpos >= 0) {
                 ((jnode)getPathForRow(editpos).getLastPathComponent()).setUserObject("");
                 editpos = -1;
              }
          }
     });
  }
  int todisplay(int in) {
     int lim =root.getChildCount();
     short i,j;
     for(i=0,j=0; j<in && i<lim; ++i) if(!clearnode(i)) ++j;
     return i;
  }
  int fromdisplay(int in) {
     int lim = Math.min(root.getChildCount()-1,in);
     short i,j;
     for(i=0,j=0; i<lim; ++i) if(!clearnode(i)) ++j;
     return j;
  }
  public void multiselect(boolean m) {
      getSelectionModel().setSelectionMode(m?TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION:TreeSelectionModel.SINGLE_TREE_SELECTION);
  };
  public void datachange() {};   // to override
  public boolean okedit() {return true;}   // to override
  public void clickselection(int i) {}; // to override
  public void newselection() {}; // to override
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public void editingstopped() {}; // to override
  public void editingstarted() {}; // to override
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-02-16^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  public boolean maxwarning() {return true;}; // to override
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR

  public String[] getdata() {
     String s[] = new String[count()];
     short i,j;
     for(i=0,j=0;j<s.length;++i)
          if(!clearnode(i)) s[j++] = ((jnode)root.getChildAt(i)).get();
     return s;
  }
  public int count() {
     int lim = root.getChildCount();
     short i,j;
     for(i=0,j=0;i < lim;++i) if(!clearnode(i)) ++j;
     return j;
  }
  public void putdata(String s[]) {
     clear();
     for(short i=0;i<s.length;++i) model.insertNodeInto(new jnode(s[i]),root,i);
     model.reload();
  }
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  /**
   * Fills up nodes with the supplied String arrays.
   */
  public void putdataphonicswords(String ss[]) {
     clear();
     jnode jn;
     String s = "";
     for(short i=0;i<ss.length;++i) {
       int k = ss[i].lastIndexOf("=");
       if(k>=0)
         s = ss[i].substring(0, k);
//startPR2007-09-04^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//       else continue;
       else if (ss[i].equals(""))
         s = "";
       else
         continue;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
       model.insertNodeInto(jn=(new jnode(s)),root,i);
       jn.userObject2 = ss[i];
     }
     model.reload();
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  void select(int[] num) {
      for(short i=0;i<num.length;++i) num[i] = todisplay(num[i]);
      setSelectionRows(num);
  }
  void startEdit(int pos) {
     if(root.isLeaf()) {
         jnode node=new jnode();
         model.insertNodeInto(node,root,0);
         model.reload();
      }
      startEditingAtPath(getPathForRow(todisplay(0)));
  }
  public int[] selection() {
     int pos[] = getSelectionRows(),len;
     if(pos == null) return new int[0];
     len = pos.length;
     for(short i=0;i<len;++i) {
         if(!clearnode(pos[i])) pos[i] = fromdisplay(pos[i]);
         else {
            if (len == 0) return new int[0];
            int pos2[] = new int[--len];
            if(i>0) System.arraycopy(pos,0,pos2,0,i);
            if(len-i > 0) System.arraycopy(pos,i+1,pos2,i,len-i);
            pos = pos2;
            --i;
         }
     }
     return pos;
  }
  void clear() {
     while(!root.isLeaf()){
         jnode jn = (jnode)root.getFirstChild();
         if(jn!=null){
             try{
                model.removeNodeFromParent(jn);
             }
             catch(Exception e){}
         }
         else break;
     }
  }
  public void addItem(String s) {
     model.insertNodeInto(new jnode(s),root,root.getChildCount());
     try{
        model.reload();
     }
     catch(Exception e){}
  }
  public void insert() {
     int pos = this.getLeadSelectionRow();
     if(pos >= 0 && !isEditing()) {
           jnode node=new jnode();
           model.insertNodeInto(node,root,pos);
           setSelectionRow(pos);
    }
  }
  public void insert(int pos) {
     if(pos >= 0 && !isEditing()) {
           jnode node=new jnode();
           model.insertNodeInto(node,root,pos);
           setSelectionRow(pos);
    }
  }
  public void delete() {
     int pos = this.getLeadSelectionRow();
     if(pos >= 0 && !isEditing()) {
              model.removeNodeFromParent((jnode)getPathForRow(pos).getLastPathComponent());
              setSelectionRow(pos);
     }
  }

  public void clearSelect() {
     int pos[] = this.getSelectionRows();
     if(pos != null && (pos.length > 1 || (pos.length > 0 && !clearnode(pos[0])))) {
        clearSelection();
     }
  }
  boolean clearnode(int pos) {
     TreePath pp = getPathForRow(pos);
     return pp != null && ((jnode)pp.getLastPathComponent()).get().length() == 0;
  }
  public TreePath getNextMatch(String prefix, int startIndex, javax.swing.text.Position.Bias bias) {
    return null;
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
    removenodepos = -1;
    editingstarted();
  }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
}
