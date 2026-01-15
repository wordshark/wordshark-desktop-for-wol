package shark;
import java.io.*;
import java.util.*;

import javax.swing.*;
class saveTree1 {

  static class saveTree2  implements Serializable{
    static final long serialVersionUID = 495239219864940861L;
      String names[];
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      UUID uuids[];
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      byte levels[];
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean adminlist = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      String languages[];
      short type;
   }
   saveTree2 curr;
   public saveTree1(JTree tt, jnode node) {
      short tot = countNodes(tt,node);
      curr = new saveTree2();
      curr.names = new String[tot];
      curr.levels = new byte[tot];
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//      curr.uuids = new UUID[tot];
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      saveNodes(tt,node,(short)0,(byte)0);
   }
//   public saveTree1(saveTree old) {
//      curr = new saveTree2();
//      curr.names = old.names;
//      curr.levels = old.levels;
//   }
   public saveTree1(saveTree2 st) {
      curr = st;
   }

   public saveTree1() {
       curr = new saveTree2();
   }

   public saveTree1(saveTreeWordList stwl){
      curr = new saveTree2();
      curr.names = stwl.names;
      curr.levels = stwl.levels;
      curr.languages = stwl.languages;
      curr.adminlist = stwl.adminlist;
      curr.type =  stwl.type;
   }

   short countNodes(JTree tt, jnode node) {
      short ret = 1;
      if(!node.isLeaf()) {
        for(Enumeration e = node.children();e.hasMoreElements();)
                 ret += countNodes(tt,(jnode)e.nextElement());
      }
      else if(node.get().equals("")) ret = 0;
      return(ret);
   }
   short saveNodes(JTree tt,jnode node, short pos1, byte level) {
      short pos = pos1;
      String  s =  node.get();
      if(!node.isLeaf()) {
         curr.names[pos] = s;
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(node.uuid==null)curr.uuids[pos] = UUID.randomUUID();
//         else curr.uuids[pos] = node.uuid;
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         curr.levels[pos++] = level;
         for(Enumeration e = node.children();e.hasMoreElements();) {
            pos = saveNodes(tt,(jnode)e.nextElement(),pos,(byte)(level+1));
         }
      }
      else if(!s.equals("")) {
         curr.names[pos] = s;
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(node.uuid==null)curr.uuids[pos] = UUID.randomUUID();
//         else curr.uuids[pos] = node.uuid;
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         curr.levels[pos++] = level;
      }
      return pos;
   }
      // add to tree, overwriting given node if required
   public void addToTree(sharkTree tt, jnode node) {
      if(curr.names.length>0 && curr.names[0] != null) {
         node.setUserObject(curr.names[0]);
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(curr.uuids == null)node.uuid = null;
//         else node.uuid = curr.uuids[0];
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         addit(tt,node,(short)0);
      }
   }
   public void addToTree(sharkTree tt, jnode node,String startfrom) {
      short pos = findname(startfrom,(short)0);
      if(pos < 0) {
         node.setUserObject("<Invalid path " + startfrom + ">");
      }
      else {
         node.setUserObject(curr.names[pos]);
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(curr.uuids!=null)
//             node.uuid = curr.uuids[pos];
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         addit(tt,node,pos);
      }
   }
   short addit(sharkTree tt, jnode node, short pos1) {
      short pos,i=0;
      for(pos  = (short)(pos1+1); pos < curr.names.length && curr.levels[pos] > curr.levels[pos1];) {
         jnode newnode = new jnode(curr.names[pos]);
//startUUID^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
//         if(curr.uuids!=null)
//            newnode.uuid = curr.uuids[pos];
//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
         tt.model.insertNodeInto(newnode,node,i++);
         pos = addit(tt, newnode,pos);
      }
      return(pos);
   }
   short findname(String s,short pos1) {
       short pos ;
       short len = (short)s.indexOf(topicTree .SEPARATOR);
       String ss = (len < 0)?s:s.substring(0,len);
       for(pos = (short)(pos1+1);
            pos < curr.names.length && curr.levels[pos] > curr.levels[pos1];
            ++pos) {
           if(curr.levels[pos] == curr.levels[pos1]+1 && (ss.equals(curr.names[pos]) || len<0 && (topicTree.ISTOPIC+ss).equals(curr.names[pos]))) {
              if(len >= 0) return findname(s.substring(len+1),pos);
              else return pos;
           }
       }
       return(-1);  // failed
   }
}
