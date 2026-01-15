package shark;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.SwingUtilities.*;



//         dragging
// if sucessful drag to destination draggedfrom and droppedon is set and mouseReleased event passed to destination which should then
// action it and clear draggedfrom and droppedon.
class dragger_base extends JComponent  {
  int mx, my, from = -1;
  JComponent dest[];
  JComponent source[];
  JComponent thisg = this;
  Container contentpane;
  int rowh;
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Rectangle startdrag;
  int oktodrag = -1;
  JTree st;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  Cursor drop;
  Cursor nodrop;
  Cursor normal;
  int[] dropZones;
  boolean overdrop = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
  MouseMotionListener mml;
  int cursorw =  (int)Toolkit.getDefaultToolkit().getBestCursorSize(32, 32).getWidth();
  int cursorh =  (int)Toolkit.getDefaultToolkit().getBestCursorSize(32, 32).getHeight();
  int mode[];
  static int MODE_SELNEARESTDIR = 0;
  static int MODE_HIDENODEEND = 1;

  Component c;

  public JComponent draggedfrom,droppedon;
  public dragger_base(Component win, JComponent sources[], JComponent destinations[]) {
      c = win;
    rowh = getFontMetrics(treepainter.normalfont).getHeight();
    dest = destinations;
    source = sources;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    drop = java.awt.dnd.DragSource.DefaultCopyDrop;
    nodrop = java.awt.dnd.DragSource.DefaultCopyNoDrop;
    normal = new Cursor(Cursor.DEFAULT_CURSOR);
    setActiveDestinations(destinations);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    if (win instanceof JDialog) {
      ( (JDialog) win).setGlassPane(this);
      contentpane = ( (JDialog)win).getContentPane();
    }
    if (win instanceof JFrame) {
        ( (JFrame) win).setGlassPane(this);
         contentpane = ( (JFrame)win).getContentPane();
    }
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    for(int i = 0; i < source.length; i++){
      if(source[i] instanceof dndTree_base){
        ((dndTree_base)source[i]).dragAndDropSelections = true;
      }
    }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
    mml = new java.awt.event.MouseMotionListener() {
          public void mouseDragged(MouseEvent e) {
             Point pp;
             if(from>=0){
                 pp = SwingUtilities.convertPoint(source[from], e.getX(), e.getY(), thisg) ;
             }else{
                 pp = new  Point(mx, my);
             }
            mx = (int)pp.getX();
            my = (int)pp.getY();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            TreePath tpa[] = new TreePath[]{};
            dndTree_base dndt = null;
            int ty = -1;
            if((dest[0] instanceof dndTree_base)){
                dndt = (dndTree_base)dest[0];
                if(!contentpane.isShowing() || !dndt.isShowing() )return;
                ty =dndt.getLocationOnScreen().y - contentpane.getLocationOnScreen().y;
              }
            if ((oktodrag>=0 && overdest()>=0)){
              if (overdrop){
                  changeCursor(drop);
                  if(mode !=null && u.inlist(mode, MODE_SELNEARESTDIR)){
                        if(dest[0] instanceof dndTree_base){
                                int m;
                                int shortest = -1;
                                int index = -1;
                                for(int i = 0; dndt.selectNearestNodes!=null && i < dndt.selectNearestNodes.length; i++){
                                     jnode jntemp = dndt.selectNearestNodes[i];
                                     Rectangle r = dndt.getUI().getPathBounds(dndt, new TreePath(jntemp.getPath()));
                                     m = r.y - (my-ty);
                                     m = Math.max(m, m*-1);
                                     if(shortest<0 || m < shortest){
                                         shortest = m;
                                         index = i;
                                     }
                                }
                                if(index >=0)
                                    tpa = u.addTreePaths(tpa, new TreePath[]{new TreePath(dndt.selectNearestNodes[index].getPath())});
                            if(dndt.keepRowHighlighted>=0)
                               tpa = u.addTreePaths(tpa, new TreePath[]{dndt.getPathForRow(dndt.keepRowHighlighted)});
                            dndt.setSelectionPaths(tpa);
                        }
                  }
                }
              else changeCursor(nodrop);
            }
            else {
                changeCursor(normal);
            }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if (from >= 0) {
              repaint();
            }
            else {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              from = oversource();
              if(oktodrag>=0)
                from = oktodrag;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//              if (from < 0)  passonevent(e);
              draggedfrom = droppedon = null;
            }
          }
          public void mouseMoved(MouseEvent e) {
//               if(from<0) passonevent(e);
          }
     };

        setVisible(true);
      }

  void mpressed(Component comp, MouseEvent e){
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            oktodrag = oversource(comp, e);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//            if(comp==null && from<0) passonevent(e);
  }

  void mreleased(Component comp, MouseEvent e){
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            changeCursor(normal);
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            startdrag = null;
            oktodrag = -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            Point pp = SwingUtilities.convertPoint(comp, e.getX(), e.getY(), thisg) ;
            mx = (int)pp.getX();
            my = (int)pp.getY();
            int i;
            if (from >= 0) {
              if ((i=overdest())>=0) {
                draggedfrom = source[from];
                droppedon = dest[i];
                passonevent(droppedon, e);
               }
              from = -1;
              repaint();
           }
//            else
//              passonevent(e);
  }

//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      int oversource() {
      int oversource(Component comp, MouseEvent e) {
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          int i;
//startPR2007-05-17^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(comp instanceof JTree){
            JTree jt = (JTree)comp;
            int row = jt.getRowForLocation((int)e.getX(), (int)e.getY());
            if(row<0) {
                return -1;
            }
//startPR2009-08-20^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
            if(jt.getSelectionPaths()==null)return -1;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for (i = 0; i < source.length; ++i)
            if (source[i] == comp)
                return i;
          return -1;
      }

      int overdest() {
          int i;
          Component c = over();
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          overdrop = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          for (i = 0; i < dest.length; ++i)
            if (dest[i]==c){
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              for (int k = 0; k < dropZones.length; ++k)
                if(i==dropZones[k])overdrop = true;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
              return i;
            }
          return -1;
      }

      Component over() {
        Point pp = SwingUtilities.convertPoint(thisg, mx, my, contentpane) ;
        return SwingUtilities.getDeepestComponentAt(contentpane,pp.x,pp.y);
      }

      public void passonevent(Component jc, MouseEvent e) {
         mx = e.getX();
         my = e.getY();
 //        Point pp = SwingUtilities.convertPoint(thisg, mx, my, contentpane) ;
 //        Component jc = SwingUtilities.getDeepestComponentAt(contentpane,pp.x,pp.y);
         if(jc != null) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           int dist;
           if (e.getID() == e.MOUSE_PRESSED && jc instanceof JTree) {
             dist = ((JTree)jc).getRowHeight();
             startdrag = new Rectangle(mx-(dist/2), (my-dist/2), dist, dist);
           }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
           Point pp = SwingUtilities.convertPoint(thisg, mx, my, jc);
           Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new MouseEvent(jc, e.getID(), e.getWhen(),
                                           e.getModifiers(), pp.x, pp.y,
                                           e.getClickCount(), e.isPopupTrigger(),
                                           e.getButton()));
         }
      }

      public void startmotion(Component comp, MouseEvent e) {
          ToolTipManager.sharedInstance().setEnabled(false);
          tooltip_base.tooltipsActive = false;
          if(this.getMouseMotionListeners().length==0){
              this.addMouseMotionListener(mml);   
          }
          mpressed(comp, e);
      }

      public void stopmotion(Component comp, MouseEvent e) {
          ToolTipManager.sharedInstance().setEnabled(true);
          tooltip_base.tooltipsActive = true;
          this.removeMouseMotionListener(mml);
          mreleased(comp, e);
      }

//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public void setActiveDestinations(JComponent activedestinations[]) {
        dropZones = new int[]{};
        for(int i = 0; i < dest.length; i++){
          for(int k = 0; k < activedestinations.length; k++){
            if(dest[i]==activedestinations[k]){
              dropZones = u.addint(dropZones, i);
            }
          }
        }
      }
      void changeCursor(Cursor c){
        if(c != getCursor()) setCursor(c);
      }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public void paint(Graphics g) {
        if(from<0) return;
        if (source[from] instanceof JTree) {
//startPR2007-03-05^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(source[from] instanceof dndTree_base ){
            st = (dndTree_base)source[from];
            if (startdrag != null && startdrag.contains(mx, my))
              return;
            else
              startdrag = null;
          }
//          TreePath p[] = ( (JTree) source[from]).getSelectionPaths();
              TreePath p[] = u.sortPathSelections(st,  ( (JTree) source[from]).getSelectionPaths());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(p == null) return;
          jnode jj[] = new jnode[p.length];
          for(short i=0;i<p.length;++i) {
             jj[i] = (jnode)p[i].getLastPathComponent();
          }
          int i;
          g.setFont(treepainter.normalfont);
          for (i = 0; i < jj.length; ++i) {
            g.setColor(jj[i].color);
            String st = jj[i].get();
            int n;
            if(mode !=null && u.inlist(mode, MODE_HIDENODEEND)){
                if((n=st.indexOf(topicTree.ISTOPIC)) >= 0) {
                    st = st.substring(0,n);
                }
            }
            if((n=st.indexOf('@'))>=0)st = st.substring(0,n);
            g.drawString(st, mx+cursorw, my + cursorh+i * rowh);
          }
        }
        else if (source[from] instanceof JList) {
          if (startdrag != null && startdrag.contains(mx, my))
            return;
          else
            startdrag = null;
            JList jl=((JList) source[from]);
       //   TreePath p[] = u.sortPathSelections(st,  ( (JTree) source[from]).getSelectionPaths());
       //   if(p == null) return;
        //  jnode jj[] = new jnode[p.length];
        //  for(short i=0;i<p.length;++i) {
        //     jj[i] = (jnode)p[i].getLastPathComponent();
        //  }
        //  int i;
          g.setFont(jl.getFont());
   //       for (i = 0; i < jj.length; ++i) {
            g.setColor(Color.black);
            String st = (String)jl.getSelectedValue();
            if(st==null)return;
     //       if(mode !=null && u.inlist(mode, MODE_HIDENODEEND)){
     //           int n;
     //           if((n=st.indexOf(topicTree.ISTOPIC)) >= 0) {
      //              st = st.substring(0,n);
      //          }
      //      }
            int n;
            if((n=st.indexOf('@'))>=0)st = st.substring(0,n);
            g.drawString(st, mx+cursorw, my + cursorh);
 //         }
        }
      }
  }
