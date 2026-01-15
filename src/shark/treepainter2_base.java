/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;

import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
 *
 * @author White Space
 */
 public class treepainter2_base extends treepainter  {
     int width;
     String b = u.gettext("admincapturemess", "treemess") + " ";
     public int startx = -1;
     int mode[];
     final static int MODE_CAPTURE = 0;
     JTree tree;
     int maxstuwidth;
     int containerwidth = -1;
     int indent;
     int maxlevel = 0;
     Color maincol;
     Color maincol2;
     public String tallnodes[];

     public treepainter2_base(JTree ownertree, int containerw, int modes[], Color col, String tallernodes[]) {
       super();
       init(ownertree, containerw, modes, col, null, tallernodes);
     }     
     
     public treepainter2_base(JTree ownertree, int containerw, int modes[], Color col) {
       super();
       init(ownertree, containerw, modes, col, null, null);
     }

     public treepainter2_base(JTree ownertree, int containerw, int modes[], Color col1, Color col2) {
       super();
       init(ownertree, containerw, modes, col1, col2, null);
     }
     
     void init(JTree ownertree, int containerw, int modes[], Color col1, Color col2,String tallernodes[]){
       indent = Integer.parseInt(String.valueOf(UIManager.get("Tree.rightChildIndent")));
       indent += Integer.parseInt(String.valueOf(UIManager.get("Tree.leftChildIndent")));
       tree = ownertree;
       maincol = col1;
       if(col2!=null)
           maincol2 = col2;
       mode = modes;
       if(mode==null)mode = new int[]{};
       containerwidth = containerw; 
       tallnodes = tallernodes;
       
     }

   public void paint(Graphics g) {
     int i;
     String s = getText();
     if((i=s.indexOf(topicTree.ISTOPIC)) >= 0) {
       setText(s.substring(0,i));
       super.paint(g);
       setText(s);
       Font f1 = getFont();
       FontMetrics m = getFontMetrics(f1);
       if(startx<0){
           jnode node2 = (jnode)tree.getModel().getRoot();
           for(Enumeration e = node2.depthFirstEnumeration();e.hasMoreElements();) {
               jnode node = (jnode)e.nextElement();
               if(node.equals(node2))continue;
               String s2 = node.get();
               int h;
               if((h=s2.indexOf(topicTree.ISTOPIC)) >= 0)
                    s2 = s2.substring(0, h).trim();
               int lev = node.getLevel();
               maxstuwidth = Math.max(maxstuwidth, m.stringWidth(s2));
               maxlevel = Math.max(lev, maxlevel);
           }
       }
       int k;
       if((k=s.lastIndexOf("{"))>=0 && s.charAt(k+2)=='}'){
           if(s.charAt(k+1)=='0')g.setColor(maincol);
           else if(s.charAt(k+1)=='1')g.setColor(maincol2);
           s = s.substring(0, k);
       }
       else
        g.setColor(maincol);
       s = s.substring(i+1);
       if(u.inlist(mode, MODE_CAPTURE))
          s = b+s.replaceAll(topicTree.ISTOPIC, ", ");
       g.drawString(s, (maxstuwidth+(maxlevel*indent)) - ((nde.getLevel())*indent) + (indent*2), m.getAscent());

     }
//startPR2008-01-29^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     else if(nde.type == jnode.GROUP && nde.isLeaf()){
       Font f1 = getFont();
       FontMetrics m = getFontMetrics(f1);
       int curr = m.stringWidth(s)+nde.icon.getIconWidth()+getIconTextGap();
       super.paint(g);
       g.setColor(sharkStartFrame.fastmodecolor);
       Rectangle r = getBounds();
       g.drawString(nde.extratext, curr, r.height/2 - m.getHeight()/2 + m.getAscent() );
       this.setPreferredSize(new Dimension(this.getWidth()*2, this.getHeight()));
       this.setMinimumSize(new Dimension(this.getWidth()*2, this.getHeight()));
       return;
     }
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
     else {
         g.setColor(Color.black);
         super.paint(g);
     }
   }
   public Dimension getPreferredSize() {
       jnode jn2 = null;
       if(o1 instanceof jnode){
          jn2 = (jnode) o1;
       }
       if(jn2==null){
           return super.getPreferredSize();
       }
     Dimension d = super.getPreferredSize();
     int i;
     String s = getText();
     if((i=s.indexOf(topicTree.ISTOPIC)) >= 0) {
       Font f1 = getFont();
       FontMetrics m = getFontMetrics(f1);
       jnode node2 = (jnode)tree.getModel().getRoot();
       for(Enumeration e = node2.depthFirstEnumeration();e.hasMoreElements();) {
           jnode node = (jnode)e.nextElement();
           if(node.equals(node2))continue;
           String s2 = node.get();
           int h;
           if((h=s2.indexOf(topicTree.ISTOPIC)) >= 0)
                s2 = s2.substring(0, h).trim();
           int lev = node.getLevel();
           maxstuwidth = Math.max(maxstuwidth, m.stringWidth(s2));
           maxlevel = Math.max(lev, maxlevel);
       }
       int maxstuwidth2 =  (maxstuwidth+(maxlevel*indent)) - ((jn2.getLevel())*indent) + indent;
       int k;
       if((k=s.lastIndexOf("{"))>=0 && s.charAt(k+2)=='}'){
           s = s.substring(0, k);
       }
       s = s.substring(i+1);
       if(u.inlist(mode, MODE_CAPTURE))
       s = b+s.replaceAll(topicTree.ISTOPIC, ", ");
       maxstuwidth2+=m.stringWidth(s);
        d.width = maxstuwidth2+indent;
        return d;
      }
      if(u.findString(tallnodes, getText())>=0)  {
         d = new Dimension((int)d.getWidth(),(int)(d.getHeight()*1.5));
      }
      return d;
     }
    
   
 }
