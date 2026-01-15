/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package shark;
import java.io.*;

/**
 *
 * @author White Space
 */
public class saveTreeWordList  implements Serializable{
    static final long serialVersionUID = 439939213842941571L;
      String names[];
      String languages[];
      byte levels[];
//startPR2007-07-12^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      boolean adminlist = false;
//endPR^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^PR
      byte recs[][];
      byte pics[][];
      String preferredPic[];
      byte extrarecs[][];
      short type;
      
      
      public saveTreeWordList doclone(){
        saveTreeWordList ret = new saveTreeWordList();
        if(this.names!=null)ret.names = (String[])this.names.clone();
        if(this.languages!=null)ret.languages = (String[])this.languages.clone();
        if(this.levels!=null)ret.levels = (byte[])this.levels.clone();
        ret.adminlist = this.adminlist;
        if(this.recs!=null)ret.recs = (byte[][])this.recs.clone();
        if(this.pics!=null)ret.pics = (byte[][])this.pics.clone();
        if(this.preferredPic!=null)ret.preferredPic = (String[])this.preferredPic.clone();
        else if(ret.names!=null)ret.preferredPic = new String[ret.names.length];
        if(this.extrarecs!=null)ret.extrarecs = (byte[][])this.extrarecs.clone();
        ret.type = this.type;
        return ret;
      }
   }