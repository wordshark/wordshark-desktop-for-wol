package shark;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class treepainter extends JLabel implements TreeCellRenderer {
      static Font normalfont = UIManager.getFont("Tree.font");
//startPR2007-11-09^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      static Font altfont = new Font(normalfont.getName(),Font.ITALIC|Font.BOLD,
//                                     normalfont.getSize());
      static Font altfont = normalfont.deriveFont(Font.ITALIC|Font.BOLD,(float)normalfont.getSize());
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      static jnode nde;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      Object o1;
      String emptygroup = u.gettext("stulist_emptygroup", "label");
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      public Font overridefont = null;
      
      public treepainter() {

         setFont(normalfont);
         setOpaque(true);
      }
      public Component getTreeCellRendererComponent(JTree tree,
                  Object o,boolean selected,boolean expanded,boolean leaf,
                  int row,boolean hasfocus) {
          String s;
          int i;
          if(o == null) return this;
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          o1 = o;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          if(!spellchange.active || (tree instanceof sharkTree && ((sharkTree)tree).updating)) s = o.toString();
          else s = spellchange.spellchange(o.toString());
          setText(s);
          TreePath tp = tree.getPathForRow(row);
          if(tp!=null) {
             jnode node = (jnode)tp.getLastPathComponent();
//startPR2006-08-22^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             nde = node;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
             int pos;
             if(s.length()>0
                  && ((jnode)node.getRoot()).type ==jnode.ROOTTOPICTREE) {
               if((i=s.indexOf('@')) >= 0  && node.isLeaf())
                  setText(s = s.substring(0,i));
               if( s.charAt(0) == topicTree.ISTOPIC.charAt(0)) {
                  if ( (pos = s.indexOf(topicTree.SEPARATOR)) > 0)
                    setText(s.substring(pos + 1));
                  else
                    setText(s.substring(1));
                }
             }
             if(node.type==-2) node.setIcon();
             if(node.type == node.STUDENT || node.type == node.TEACHER) {
               s =  o.toString();
               setText(s);
             }
             if(node.isLeaf() && s.length() == 0) {
                setIcon(selected?jnode.edit:jnode.empty);
             }
             else setIcon(node.icon);
             int n;
             if(node.icon == jnode.icons[jnode.PROJECT] && ((n=(node.get()).indexOf("[")))>=0){
                 setText(node.get().substring(0, n));
             }
             if(selected) {
                setBackground(UIManager.getColor("Tree.selectionBackground"));
                setForeground(node.color);
             }
             else {
                if(node.bgcolor != null)
                     setBackground(node.bgcolor);
                else setBackground(UIManager.getColor("Tree.textBackground"));
                setForeground(node.color);
             }
             if(overridefont==null)
              setFont(normalfont);
             else
                 setFont(overridefont);
//              if(tree == sharkStartFrame.mainFrame.gameTree
//                  && sharkStartFrame.extraflash != null) {
//                 if (node.type == jnode.GAME) {
//                   if (sharkStartFrame.extraflash.indexOf(sharkStartFrame.currCourse +
//                                                          '.' + node.get()) >= 0)
//                     setFont(altfont);
//                 }
//                 else if(node.type == jnode.GAMEGROUP) {
//                   jnode ch[] = node.getChildren();
//                   for(i=0;i<ch.length;++i) {
//                     if (sharkStartFrame.extraflash.indexOf(sharkStartFrame.currCourse +
//                                                            '.' + ch[i].get()) >= 0)
//                       setFont(altfont);
//                       break;
//                   }
//                 }
//               }
           }
          return this;
      }
      public Dimension getPreferredSize() {
//        boolean istallnode= false;
//startPR2008-06-07^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//        String s = getText();
        String s;
        if(o1 instanceof jnode){
          jnode jn = (jnode) o1;
          if (jn.type == jnode.GROUP && jn.isLeaf())
            jn.extratext = "  " + emptygroup;
          s = getText() + jn.extratext;
//          if(this instanceof treepainter2){
//              treepainter2 tpw = ((treepainter2)this);
//              if(tpw.tallnodes!=null){
//                  for(int k = 0; k < tpw.tallnodes.length; k++){
//                      if(tpw.tallnodes[k].equals(o1)){
//                          istallnode = true;
//                          break;
//                      }
//                  }
//              }
//          }
        }
        else
          s = getText();
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
          int offset=32,depth=16;
          FontMetrics m = getFontMetrics(getFont());
          Icon  icon = this.getIcon();
          if(icon != null) {
             offset = icon.getIconWidth() + this.getIconTextGap();
             depth = icon.getIconHeight();
          }
          int hght = Math.max(depth,m.getHeight());
          return(new Dimension(offset+Math.max(32,m.stringWidth(s)+32),
                        hght));
      }
}
